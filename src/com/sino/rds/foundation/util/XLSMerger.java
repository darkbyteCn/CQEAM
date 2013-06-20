package com.sino.rds.foundation.util;

import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.record.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.util.*;

public class XLSMerger {
    private List<File> files = null;

    public XLSMerger() {
        files = new ArrayList<File>();
    }

    /**
     * 功能：添加要合并的Excel文件
     *
     * @param file File
     */
    public void addFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            return;
        }
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return;
        }
        fileName = fileName.substring(index + 1).toLowerCase();
        if (!fileName.equalsIgnoreCase("xls") && !fileName.equalsIgnoreCase("xlsx")) {
            return;
        }
        if (!files.contains(file)) {
            files.add(file);
        }
    }

    /**
     * 功能：加入要合并的Excel文件。方法重载。
     *
     * @param file String
     */
    public void addFile(String file) {
        if (StrUtil.isEmpty(file)) {
            return;
        }
        File tFile = new File(file);
        addFile(tFile);
    }

    /**
     * 功能：设置要合并文件的目录。
     * 合并时将把该目录下的所有Excel文件合并，不包含其子目录。
     *
     * @param filePath String
     */
    public void setSrcPath(String filePath) {
        if (StrUtil.isEmpty(filePath)) {
            return;
        }
        File parFile = new File(filePath);
        File[] files = parFile.listFiles();
        if (files != null) {
            this.files.clear();
            for (File file : files) {
                addFile(file);
            }
        }
    }

    /**
     * 功能：设置要加入的文件列表
     *
     * @param files List
     */
    public void setFiles(List files) {
        if (files == null || files.isEmpty()) {
            return;
        }
        this.files.clear();
        int fileCount = files.size();
        for (int i = 0; i < fileCount; i++) {
            addFile((File) files.get(i));
        }
    }

    /**
     * 功能：将文件合并为目标文件
     *
     * @param targetFile String
     */
    public void merge(String targetFile) {
        merge(targetFile, false);
    }

    /**
     * 功能：将文件合并为目标文件
     *
     * @param targetFile String
     * @param deleteSrc  是否删除原文件
     */
    public void merge(String targetFile, boolean deleteSrc) {
        if (StrUtil.isEmpty(targetFile)) {
            return;
        }
        merge(new File(targetFile), deleteSrc);
    }

    /**
     * 功能：将文件合并为目标文件，方法重载。
     * 不删除原文件。
     *
     * @param targetFile File
     */
    public void merge(File targetFile) {
        merge(targetFile, false);
    }

    /**
     * 功能：将文件合并为目标文件
     *
     * @param targetFile File 目标文件
     * @param deleteSrc  是否删除原文件
     */
    public void merge(File targetFile, boolean deleteSrc) {
        try {
            if (targetFile == null) {
                return;
            }
            int fileCount = files.size();
            if (fileCount <= 1) {
                return;
            }
            if (!targetFile.exists()) {
                targetFile.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(targetFile);
            InputStream[] ins = new InputStream[files.size()];
            for (int i = 0; i < fileCount; i++) {
                ins[i] = new FileInputStream(files.get(i));
            }
            merge(ins, out);
            if (deleteSrc) {
                for (int i = 0; i < fileCount; i++) {
                    files.get(i).delete();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.logError(ex);
        }
    }

    /**
     * 功能：将多个Xls文件合并为一个，适用于只有一个sheet，并且格式相同的文档
     *
     * @param ins 输入的Xls文件
     * @param out 输出文件
     */
    private static void merge(InputStream[] ins, OutputStream out) {
        List rootRecords = getRecords(ins[0]);
        Workbook workbook = Workbook.createWorkbook(rootRecords);
        List<Sheet> sheets = getSheets(workbook, rootRecords);
        if (sheets == null || sheets.size() == 0) {
            throw new IllegalArgumentException("第一篇文档的格式错误，必须至少有一个sheet");
        }
        //以第一篇文档的最后一个sheet为根，以后的数据都追加在这个sheet后面
        Sheet rootSheet = sheets.get(sheets.size() - 1);
        int rootRows = getRowsOfSheet(rootSheet); //记录第一篇文档的行数，以后的行数在此基础上增加
        rootSheet.setLoc(rootSheet.getDimsLoc());
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(10000); //Integer, Integer
        for (int i = 1; i < ins.length; i++) { //从第二篇开始遍历
            List<Record> records = getRecords(ins[i]);
            int rowsOfCurXls = 0;
            for (Record record : records) { //遍历当前文档的每一个record
                if (record.getSid() == RowRecord.sid) { //如果是RowRecord
                    RowRecord rowRecord = (RowRecord) record;
                    rowRecord.setRowNumber(rootRows + rowRecord.getRowNumber()); //调整行号
                    rootSheet.addRow(rowRecord); //追加Row
                    rowsOfCurXls++; //记录当前文档的行数
                } else if (record.getSid() == SSTRecord.sid) { //SST记录，SST保存xls文件中唯一的String，各个String都是对应着SST记录的索引
                    SSTRecord sstRecord = (SSTRecord) record;
                    for (int j = 0; j < sstRecord.getNumUniqueStrings(); j++) {
                        int index = workbook.addSSTString(sstRecord.getString(j));
                        map.put(j, index); //记录原来的索引和现在的索引的对应关系
                    }
                } else if (record.getSid() == LabelSSTRecord.sid) {
                    LabelSSTRecord label = (LabelSSTRecord) record;
                    int index = Integer.parseInt(String.valueOf(map.get(label.getSSTIndex())));
                    label.setSSTIndex(index); //调整SST索引的对应关系
                }
                if (record instanceof CellValueRecordInterface) { //追加ValueCell
                    CellValueRecordInterface cell = (CellValueRecordInterface) record;
                    int cellRow = cell.getRow() + rootRows;
                    cell.setRow(cellRow);
                    rootSheet.addValueRecord(cellRow, cell);
                }
            }
            rootRows += rowsOfCurXls;
        }
        byte[] data = getBytes(workbook, (Sheet[]) (sheets.toArray(new Sheet[0])));
        write(out, data);
    }

    /**
     * 功能：将文件写入流中
     *
     * @param out  OutputStream
     * @param data byte[]
     */
    private static void write(OutputStream out, byte[] data) {
        POIFSFileSystem fs = new POIFSFileSystem();
        try {
            fs.createDocument(new ByteArrayInputStream(data), "Workbook");
            fs.writeFilesystem(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 功能：构造sheets
     *
     * @param workbook Workbook
     * @param records  List
     * @return List
     */
    private static List<Sheet> getSheets(Workbook workbook, List records) {
        int recOffset = workbook.getNumRecords();
        int sheetNum = 0;
        convertLabelRecords(records, recOffset, workbook);
        List<Sheet> sheets = new ArrayList<Sheet>();
        while (recOffset < records.size()) {
            Sheet sh = Sheet.createSheet(records, sheetNum++, recOffset);
            recOffset = sh.getEofLoc() + 1;
            if (recOffset == 1) {
                break;
            }
            sheets.add(sh);
        }
        return sheets;
    }

    /**
     * 功能：获取sheet的最后一行
     *
     * @param sheet Sheet
     * @return int
     */
    private static int getRowsOfSheet(Sheet sheet) {
        int rows = 0;
        sheet.setLoc(0);
        while (sheet.getNextRow() != null) {
            rows++;
        }
        return rows;
    }

    /**
     * 功能：获取记录集合
     *
     * @param in InputStream
     * @return List
     */
    private static List<Record> getRecords(InputStream in) {
        List<Record> records = Collections.EMPTY_LIST;
        try {
            POIFSFileSystem poifs = new POIFSFileSystem(in);
            InputStream stream = poifs.createDocumentInputStream("Workbook");
            records = RecordFactory.createRecords(stream);
        } catch (IOException ex) {
            Logger.logError(ex);
        }
        return records;
    }

    /**
     * 功能：转换标签行
     *
     * @param records  List
     * @param offset   int
     * @param workbook Workbook
     */
    private static void convertLabelRecords(List<LabelSSTRecord> records, int offset, Workbook workbook) {
        for (int k = offset; k < records.size(); k++) {
            Record rec = records.get(k);
            if (rec.getSid() == LabelRecord.sid) {
                LabelRecord oldrec = (LabelRecord) rec;
                records.remove(k);
                LabelSSTRecord newrec = new LabelSSTRecord();
                int stringid = workbook.addSSTString(new UnicodeString(oldrec.getValue()));
                newrec.setRow(oldrec.getRow());
                newrec.setColumn(oldrec.getColumn());
                newrec.setXFIndex(oldrec.getXFIndex());
                newrec.setSSTIndex(stringid);
                records.add(k, newrec);
            }
        }
    }

    /**
     * 功能：获取workbook的字节数组
     *
     * @param workbook Workbook
     * @param sheets   Sheet[]
     * @return byte[]
     */
    private static byte[] getBytes(Workbook workbook, Sheet[] sheets) {
        int nSheets = sheets.length;
        for (int i = 0; i < nSheets; i++) {
            sheets[i].preSerialize();
        }
        int totalsize = workbook.getSize();
        int[] estimatedSheetSizes = new int[nSheets];
        for (int k = 0; k < nSheets; k++) {
            workbook.setSheetBof(k, totalsize);
            int sheetSize = sheets[k].getSize();
            estimatedSheetSizes[k] = sheetSize;
            totalsize += sheetSize;
        }
        byte[] retval = new byte[totalsize];
        int pos = workbook.serialize(0, retval);
        for (int k = 0; k < nSheets; k++) {
            int serializedSize = sheets[k].serialize(pos, retval);
            if (serializedSize != estimatedSheetSizes[k]) {
                throw new IllegalStateException("Actual serialized sheet size ("
                        + serializedSize
												+ ") differs from pre-calculated size ("
												+ estimatedSheetSizes[k]
												+ ") for sheet ("
												+ k
												+ ")");
			}
			pos += serializedSize;
		}
		return retval;
	}
}
