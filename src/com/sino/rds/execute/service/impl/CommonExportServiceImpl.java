package com.sino.rds.execute.service.impl;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CompressException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.ReportException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.RDSConstantConfigManager;
import com.sino.rds.execute.helper.commonlist.CommonDataPatternProcessor;
import com.sino.rds.execute.service.ExportPrepareService;
import com.sino.rds.execute.service.ReportExportService;
import com.sino.rds.foundation.util.CompressConfig;
import com.sino.rds.foundation.util.FileCompressor;
import com.sino.rds.foundation.util.XLSMerger;
import com.sino.rds.share.form.ReportDefineFrm;
import com.sino.rds.share.form.ReportViewFrm;
import com.sino.rds.share.form.SearchParameterFrm;
import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommonExportServiceImpl extends DefaultExportServiceImpl implements ReportExportService {
    private boolean isFirstFile = true;

    public CommonExportServiceImpl(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public File getExportReport() throws ReportException {
        File file = null;
        Connection dataSource = null;
        ExportPrepareService service = null;
        long limitMergeSize = RDSConstantConfigManager.getLimitMergeSize();
        try {
            List<File> files = new ArrayList<File>();
            long mergeSize = 0L;
            List<List<File>> mergeList = new ArrayList<List<File>>();
            SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
            service = new ExportPrepareService(userAccount, reportFrm, conn);
            service.setSearchFrm(frm);
            service.prepareExecuteFrm();
            dataSource = getDataSource();
            service.initDynamicDAO(dataSource);
            executeFrm = service.getExecuteFrm();
            while (service.nextPage()) {
                service.prepareReportData();
                CommonDataPatternProcessor.formatReportData(executeFrm);
                File singleXLS = writeSingleXLS();
                mergeSize += singleXLS.length();
                files.add(singleXLS);
                if (mergeSize >= limitMergeSize) {
                    mergeList.add(files);
                    files = new ArrayList<File>();
                    isFirstFile = true;
                    mergeSize = 0L;
                }
            }
            if (!files.isEmpty()) {
                mergeList.add(files);
            }
            file = processedExportedXLSFiles(mergeList);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        } finally {
            try {
                freeResource();
                if (service != null) {
                    service.freeResource();
                }
                if (dataSource != null) {
                    dataSource.close();
                }
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return file;
    }

    private File processedExportedXLSFiles(List<List<File>> mergeList) throws CompressException {
        File file = null;
        XLSMerger xlsMerger = new XLSMerger();
        int mergeCount = mergeList.size();
        List<File> mergedFiles = new ArrayList<File>();
        for (int i = 0; i < mergeCount; i++) {
            List<File> files = mergeList.get(i);
            if (files.size() > 1) {
                xlsMerger.setFiles(files);
                File mergedFile = getReportMergeFile();
                xlsMerger.merge(mergedFile, true);
                mergedFiles.add(mergedFile);
            } else {
                mergedFiles.add(files.get(0));
            }
        }
        if (mergeCount > 1) {
            CompressConfig fcc = new CompressConfig();
            fcc.setDeleteSrc(true);
            fcc.setFiles(mergedFiles);
            fcc.setTargetPath(getExportPath());
            fcc.setFileName(getReportZipFile().getName());
            FileCompressor fc = new FileCompressor();
            fc.setConfig(fcc);
            fc.compress();
            file = fc.getCompressedFile();
        } else {
            file = mergedFiles.get(0);
        }
        return file;
    }

    private File writeSingleXLS() throws ReportException {
        File file = null;
        HSSFWorkbook wb = null;
        OutputStream out = null;
        try {
            file = getReportFile();
            wb = new HSSFWorkbook();
            ReportDefineFrm definedReport = executeFrm.getDefinedReport();
            String sheetName = definedReport.getReportName();
            HSSFSheet sheet = wb.createSheet(sheetName);
            sheet.setDisplayGridlines(false);//不显示网格线
            HSSFCellStyle dataStyle = createDataStyle(wb);
            HSSFCellStyle headerStyle = createHeaderStyle(wb);
            if (isFirstFile) {
                writeDataHeader(sheet, headerStyle);
            }
            writeData2Excel(sheet, dataStyle, headerStyle);
            autoReSizeSheet(sheet);
            out = new FileOutputStream(file);
            wb.write(out);
            isFirstFile = false;
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        }
        return file;
    }

    private void writeData2Excel(HSSFSheet sheet, HSSFCellStyle dataStyle, HSSFCellStyle headerStyle) throws ContainerException {
        RowSet searchResult = executeFrm.getSearchResult();
        if (searchResult != null && !searchResult.isEmpty()) {
            List<ReportViewFrm> definedViews = executeFrm.getDefinedViews();
            int fieldCount = definedViews.size();
            int dataCount = searchResult.getSize();
            int xlsRowNum = 0;
            for (int i = 0; i < dataCount; i++) {
                Row row = searchResult.getRow(i);
                xlsRowNum = i;
                if (isFirstFile) {
                    xlsRowNum++;
                }
                HSSFRow xlsRow = sheet.createRow(xlsRowNum);
                xlsRow.setHeightInPoints(18);
                for (short j = 0; j < fieldCount; j++) {
                    ReportViewFrm viewFrm = definedViews.get(j);
                    String fieldName = viewFrm.getFieldName();
                    HSSFCell xlsCell = xlsRow.createCell(j);
                    xlsCell.setCellStyle(dataStyle);
                    HSSFRichTextString richText = new HSSFRichTextString(row.getStrValue(fieldName));
                    xlsCell.setCellValue(richText);
                }
            }
        }
    }

    private void writeDataHeader(HSSFSheet sheet, HSSFCellStyle headerStyle) throws ReportException {
        try {
            sheet.setDisplayGridlines(false);//不显示网格线
            HSSFRow xlsRow = sheet.createRow(0);
            xlsRow.setHeightInPoints(18);
            List<ReportViewFrm> definedViews = executeFrm.getDefinedViews();
            int fieldCount = definedViews.size();
            for (short j = 0; j < fieldCount; j++) {
                ReportViewFrm viewFrm = definedViews.get(j);
                HSSFCell xlsCell = xlsRow.createCell(j);
                xlsCell.setCellStyle(headerStyle);
                HSSFRichTextString richText = new HSSFRichTextString(viewFrm.getFieldDesc());
                xlsCell.setCellValue(richText);
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        }
    }

    private File getReportZipFile() {
        File file = null;
        String filePath = getExportPath();
        String tmpFileName = reportFrm.getReportName() + "_zip";
        String fileName = tmpFileName + ".zip";
        file = new File(filePath, fileName);
        int i = 1;
        while (file.exists()) {
            fileName = tmpFileName + "_" + i + ".zip";
            file = new File(filePath, fileName);
            i++;
        }
        return file;
    }

    private File getReportMergeFile() {
        File file = null;
        String filePath = getExportPath();
        String tmpFileName = reportFrm.getReportName() + "_merge";
        String fileName = tmpFileName + ".xls";
        file = new File(filePath, fileName);
        int i = 1;
        while (file.exists()) {
            fileName = tmpFileName + "_" + i + ".xls";
            file = new File(filePath, fileName);
            i++;
        }
        return file;
    }
}