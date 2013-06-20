package com.sino.rds.execute.service.impl;

import com.sino.base.data.RowSet;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReportException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.execute.helper.intersect.IntersectDataPatternProcessor;
import com.sino.rds.execute.helper.intersect.IntersectProducerFactory;
import com.sino.rds.execute.service.ExportPrepareService;
import com.sino.rds.execute.service.IntersectProducer;
import com.sino.rds.execute.service.ReportExportService;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.IntersectReportExecuteFrm;
import com.sino.rds.share.form.ReportCategoryFrm;
import com.sino.rds.share.form.ReportDefineFrm;
import com.sino.rds.share.form.SearchParameterFrm;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class IntersectExportServiceImpl extends DefaultExportServiceImpl implements ReportExportService {
    private IntersectProducer rptProducer = null;

    public IntersectExportServiceImpl(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public File getExportReport() throws ReportException {
        File file = null;
        HSSFWorkbook wb = null;
        OutputStream out = null;
        try {
            if (reportFrm != null) {
                file = getReportFile();
                wb = new HSSFWorkbook();
                prepareReport();
                formatReportPattern();
                ReportDefineFrm definedReport = executeFrm.getDefinedReport();
                String sheetName = definedReport.getReportName();
                HSSFSheet sheet = wb.createSheet(sheetName);
                sheet.setDisplayGridlines(false);//不显示网格线
                out = new FileOutputStream(file);
                writeData2Excel(wb, sheet);
                wb.write(out);
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                freeResource();
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        }
        return file;
    }

    private void prepareReport() throws QueryException {
        ExportPrepareService service = null;
        Connection dataSource = null;
        try {
            SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
            service = new ExportPrepareService(userAccount, reportFrm, conn);
            service.setSearchFrm(frm);
            service.prepareExecuteFrm();
            dataSource = getDataSource();
            service.initDynamicDAO(dataSource);
            service.produceReportDimension();
            service.produceReportExpression();
            executeFrm = service.getExecuteFrm();
            while (service.nextPage()) {
                service.prepareReportData();
                processLocalData();
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        } finally {
            try {
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
    }

    private void processLocalData() throws ReportException {
        IntersectReportExecuteFrm intersectFrm = (IntersectReportExecuteFrm) executeFrm;
        if (rptProducer == null) {
            rptProducer = IntersectProducerFactory.getProducer(intersectFrm);
        }
        rptProducer.processIntersectData();
    }

    private void formatReportPattern() throws ReportException {
        IntersectReportExecuteFrm intersectFrm = (IntersectReportExecuteFrm) executeFrm;
        IntersectDataPatternProcessor.formatReportData(intersectFrm);
    }

    private void writeData2Excel(HSSFWorkbook wb, HSSFSheet sheet) {
        IntersectReportExecuteFrm intersectFrm = (IntersectReportExecuteFrm) executeFrm;
        if (intersectFrm.hasData()) {
            exportIntersectData(wb, sheet, intersectFrm);
            autoReSizeSheet(sheet);
            hiddenReportCategory(sheet, intersectFrm);
            mergeBottomSumArea(sheet, intersectFrm);
            mergeLeftCategories(sheet, intersectFrm);
            mergeVerticalSumArea(sheet, intersectFrm);
            mergeAboveCategories(sheet, intersectFrm);
            mergeLeftEmptyArea(sheet, intersectFrm);
        }
    }

    private int createReportHeader(HSSFSheet sheet, IntersectReportExecuteFrm intersectFrm) {
        int offsetRow = 1;
        String[][] reportValue = intersectFrm.getReportValue();
        short cellCount = (short) reportValue[0].length;
        HSSFRow xlsRow = sheet.createRow(0);
        for (short i = 0; i < cellCount; i++) {
            xlsRow.createCell(i);
        }
        ReportDefineFrm definedReport = intersectFrm.getDefinedReport();
        xlsRow.getCell((short) 0).setCellValue(definedReport.getReportName());
        return offsetRow;
    }


    /**
     * 功能：计算总计数量
     * @param intersectFrm 报表运行时表单对象
     */
    private void computeTotalSummary(IntersectReportExecuteFrm intersectFrm){
        String[][] reportValue = intersectFrm.getReportValue();
        String sumPosition = reportFrm.getSumPosition();
        if (sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) {

        } else if (sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)) {

        }
    }

    private void exportIntersectData(HSSFWorkbook wb,
                                     HSSFSheet sheet,
                                     IntersectReportExecuteFrm intersectFrm) {
        HSSFCellStyle dataStyle = createDataStyle(wb);
        HSSFCellStyle dimensionStyle = createHeaderStyle(wb);
        String[][] reportValue = intersectFrm.getReportValue();
        int leftDimensionCount = 0;
        RowSet leftDimensions = intersectFrm.getLeftDimensions();
        if (leftDimensions != null && !leftDimensions.isEmpty()) {
            leftDimensionCount = leftDimensions.getSize();
        }
        int leftCategoryCount = 0;
        List<ReportCategoryFrm> leftCategories = intersectFrm.getLeftCategories();
        if (leftCategories != null && !leftCategories.isEmpty()) {
            leftCategoryCount = leftCategories.size();
        }
        int rowCount = reportValue.length;
        int aboveCount = 0;
        List<ReportCategoryFrm> aboveCategories = intersectFrm.getAboveCategories();
        if (aboveCategories != null && !aboveCategories.isEmpty()) {
            aboveCount = aboveCategories.size();
        }
        boolean hasBottomExpression = false;
        if (rowCount > (aboveCount + leftDimensionCount + 1)) {
            hasBottomExpression = true;
        }
        for (int i = 0; i < rowCount; i++) {
            HSSFRow xlsRow = sheet.createRow(i);
            xlsRow.setHeightInPoints(18);
            String[] rowValue = reportValue[i];
            if (rowValue != null) {
                for (short j = 0; j < rowValue.length; j++) {
                    HSSFCell xlsCell = xlsRow.createCell(j);
                    HSSFRichTextString cellValue = new HSSFRichTextString(rowValue[j]);
                    xlsCell.setCellValue(cellValue);
                    if (i >= aboveCount) {
                        if (hasBottomExpression) {
                            if ((i == rowCount - 1) && (j < leftCategoryCount)) {
                                xlsCell.setCellStyle(dimensionStyle);
                            } else {
                                xlsCell.setCellStyle(dataStyle);
                            }
                        } else {
                            xlsCell.setCellStyle(dataStyle);
                        }
                    } else {
                        xlsCell.setCellStyle(dimensionStyle);
                    }
                }
            }
        }
    }

    private void hiddenReportCategory(HSSFSheet sheet, IntersectReportExecuteFrm intersectFrm) {
        List<ReportCategoryFrm> aboveCategories = intersectFrm.getAboveCategories();
        if (aboveCategories != null && !aboveCategories.isEmpty()) {
            for (int i = 0; i < aboveCategories.size(); i++) {
                ReportCategoryFrm category = aboveCategories.get(i);
                if (category.getDisplayFlag().equals("N")) {//该行隐藏
                    HSSFRow xlsRow = sheet.getRow(i);
                    xlsRow.setZeroHeight(true);
                }
            }
        }

        List<ReportCategoryFrm> leftCategories = intersectFrm.getLeftCategories();
        if (leftCategories != null && !leftCategories.isEmpty()) {
            for (int i = 0; i < leftCategories.size(); i++) {
                ReportCategoryFrm category = leftCategories.get(i);
                if (category.getDisplayFlag().equals("N")) {//该行隐藏
                    sheet.setColumnHidden((short) i, true);
                }
            }
        }
    }

    private void mergeAboveCategories(HSSFSheet sheet, IntersectReportExecuteFrm intersectFrm) {
        int startColumn = 0;
        int endRow = 0;
        List<ReportCategoryFrm> leftCategories = intersectFrm.getLeftCategories();
        if (leftCategories != null) {
            startColumn = leftCategories.size();
        }
        List<ReportCategoryFrm> aboveCategories = intersectFrm.getAboveCategories();
        if (aboveCategories != null) {
            endRow = aboveCategories.size();
        }
        String startValue = "";
        String endValue = "";
        short mergeStart = 0;
        for (int i = 0; i < endRow; i++) {
            HSSFRow xlsRow = sheet.getRow(i);
            short cellCount = xlsRow.getLastCellNum();
            for (short j = (short) startColumn; j < cellCount; j++) {
                HSSFCell xlsCell = xlsRow.getCell(j);
                if (StrUtil.isEmpty(startValue)) {
                    startValue = xlsCell.getRichStringCellValue().getString();
                    mergeStart = j;
                }
                endValue = xlsCell.getRichStringCellValue().getString();
                if (!endValue.equals(startValue)) {
                    if (canMergeAbove(sheet, i,  mergeStart, (short)(j - 1))) {
                        Region region = new Region(i, mergeStart, i, (short)(j - 1));
                        sheet.addMergedRegion(region);
                        startValue = endValue;
                    }
                    mergeStart = j;
                } else {
                    if (needMergeAbove(sheet, i,  mergeStart, j)) {
                        Region region = new Region(i, mergeStart, i, j);
                        sheet.addMergedRegion(region);
                        startValue = "";
                        mergeStart = j;
                    } else  if (j == (cellCount - 1) && (mergeStart < j)) {
                        Region region = new Region(i, mergeStart, i, j);
                        sheet.addMergedRegion(region);
                    }
                }
            }
            startValue = "";
        }
    }

    private boolean canMergeAbove(HSSFSheet sheet, int rowNumber, short startColumn, short endColumn){
        boolean canMerge = false;
        int mergeRegionCount = sheet.getNumMergedRegions();
        if(rowNumber == 0){
            canMerge = true;
        } else {
            for(int i = 0; i < mergeRegionCount; i++){
                Region region = sheet.getMergedRegionAt(i);
                int rowFrm = region.getRowFrom();
                if(rowFrm != (rowNumber -1)){
                    continue;
                }
                short fromColumn = region.getColumnFrom();
                short toColumn = region.getColumnTo();
                if(startColumn >= fromColumn && endColumn <= toColumn){
                    canMerge = true;
                    break;
                }
            }
        }
        return canMerge;
    }

    private boolean needMergeAbove(HSSFSheet sheet, int rowNumber, short startColumn, short endColumn){
        boolean canMerge = false;
        int mergeRegionCount = sheet.getNumMergedRegions();
        if(rowNumber > 0 && endColumn > startColumn){
            for(int i = 0; i < mergeRegionCount; i++){
                Region region = sheet.getMergedRegionAt(i);
                int rowFrm = region.getRowFrom();
                if(rowFrm != (rowNumber -1)){
                    continue;
                }
                short fromColumn = region.getColumnFrom();
                short toColumn = region.getColumnTo();
                if(startColumn >= fromColumn && endColumn == toColumn){
                    canMerge = true;
                    break;
                }
            }
        }
        return canMerge;
    }
    
    private void mergeLeftCategories(HSSFSheet sheet, IntersectReportExecuteFrm intersectFrm) {
        int startRow = 0;
        int endRow = sheet.getLastRowNum();
        int endColumn = 0;
        List<ReportCategoryFrm> aboveCategories = intersectFrm.getAboveCategories();
        if (aboveCategories != null) {
            startRow += aboveCategories.size();
        }
        startRow++;//因为存在度量值字段描述所以需要加1
        List<ReportCategoryFrm> leftCategories = intersectFrm.getLeftCategories();
        if (leftCategories != null) {
            endColumn = leftCategories.size();
        }
        String startValue = "";
        String endValue = "";
        int mergeStart = 0;
        for (short i = 0; i < (short) endColumn; i++) {
            for (int j = startRow; j <= endRow; j++) {
                HSSFRow xlsRow = sheet.getRow(j);
                HSSFCell xlsCell = xlsRow.getCell(i);
                if (StrUtil.isEmpty(startValue)) {
                    startValue = xlsCell.getRichStringCellValue().getString();
                    mergeStart = j;
                }
                endValue = xlsCell.getRichStringCellValue().getString();
                if (!endValue.equals(startValue)) {
                    if(canMergeLeft(sheet, i, mergeStart, j - 1)){
                        Region region = new Region(mergeStart, i, j - 1, i);
                        sheet.addMergedRegion(region);
                        startValue = endValue;
                    }
                    mergeStart = j;
                } else {
                    if(needMergeLeft(sheet, i, mergeStart, j)){
                        Region region = new Region(mergeStart, i, j, i);
                        sheet.addMergedRegion(region);
                        startValue = "";
                        mergeStart = j;
                    } else if (j == endRow) {
                        if (mergeStart < j - 1) {
                            Region region = new Region(mergeStart, i, j, i);
                            sheet.addMergedRegion(region);
                        }
                    }
                }
            }
            startValue = "";
        }
    }

    private boolean canMergeLeft(HSSFSheet sheet, short columnNumber, int startRow, int endRow){
        boolean canMerge = false;
        int mergeRegionCount = sheet.getNumMergedRegions();
        if(columnNumber == 0){
            canMerge = true;
        } else {
            for(int i = 0; i < mergeRegionCount; i++){
                Region region = sheet.getMergedRegionAt(i);
                short columnFrm = region.getColumnFrom();
                if(columnFrm != (columnNumber -1)){
                    continue;
                }
                int fromRow = region.getRowFrom();
                int toRow = region.getRowTo();
                if(startRow >= fromRow && endRow <= toRow){
                    canMerge = true;
                    break;
                }
            }
        }
        return canMerge;
    }

    private boolean needMergeLeft(HSSFSheet sheet, short columnNumber, int startRow, int endRow){
        boolean canMerge = false;
        int mergeRegionCount = sheet.getNumMergedRegions();
        if(columnNumber > 0 && endRow > startRow){
            for(int i = 0; i < mergeRegionCount; i++){
                Region region = sheet.getMergedRegionAt(i);
                short columnFrm = region.getColumnFrom();
                if(columnFrm != (columnNumber -1)){
                    continue;
                }
                int fromRow = region.getRowFrom();
                int toRow = region.getRowTo();
                if(startRow >= fromRow && endRow == toRow){
                    canMerge = true;
                    break;
                }
            }
        }
        return canMerge;
    }

    private void mergeLeftEmptyArea(HSSFSheet sheet, IntersectReportExecuteFrm intersectFrm) {
        List<ReportCategoryFrm> leftCategories = intersectFrm.getLeftCategories();
        if (leftCategories != null && !leftCategories.isEmpty()) {
            int startRow = 0;
            int endRow = 0;
            short startColumn = 0;
            short endColumn = 0;
            List<ReportCategoryFrm> aboveCategories = intersectFrm.getAboveCategories();
            if (aboveCategories != null) {
                endRow = aboveCategories.size();
            }
            endColumn = (short) (leftCategories.size() - 1);
            Region region = new Region(startRow, startColumn, endRow, endColumn);
            sheet.addMergedRegion(region);
        }
    }

    private void mergeBottomSumArea(HSSFSheet sheet, IntersectReportExecuteFrm intersectFrm) {
        String sumPosition = reportFrm.getSumPosition();
        if (sumPosition.equals(RDSDictionaryList.POSITION_BOTTOM)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)) {
            int startRow = sheet.getLastRowNum();
            short startColumn = 0;
            short endColumn = 0;
            List<ReportCategoryFrm> leftCategories = intersectFrm.getLeftCategories();
            if (leftCategories != null) {
                endColumn = (short) (leftCategories.size() - 1);
            }
            if (endColumn > startColumn) {
                Region region = new Region(startRow, startColumn, startRow, endColumn);
                sheet.addMergedRegion(region);
            }
        }
    }

    private void mergeVerticalSumArea(HSSFSheet sheet, IntersectReportExecuteFrm intersectFrm) {
        String sumPosition = reportFrm.getSumPosition();
        if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)
                || sumPosition.equals(RDSDictionaryList.POSITION_RIGHT)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)
                || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_RIG)
                ) {
            int startRow = 0;
            int endRow = 0;
            List<ReportCategoryFrm> aboveCategories = intersectFrm.getAboveCategories();
            if (aboveCategories != null) {
                endRow = aboveCategories.size() - 1;
            }
            short startColumn = 0;
            short endColumn = 0;
            String[][] reportValue = intersectFrm.getReportValue();
            if (sumPosition.equals(RDSDictionaryList.POSITION_LEFT)
                    || sumPosition.equals(RDSDictionaryList.POSITION_BOT_AND_LEF)) {
                List<ReportCategoryFrm> leftCategories = intersectFrm.getLeftCategories();
                if (leftCategories != null) {
                    startColumn = (short) (leftCategories.size());
                }
            } else {
                startColumn = (short) (reportValue[0].length - intersectFrm.getMeasureValueFields().size());
            }
            endColumn = (short) (startColumn + intersectFrm.getMeasureValueFields().size() - 1);
            if (endRow > startRow) {
                Region region = new Region(startRow, startColumn, endRow, endColumn);
                sheet.addMergedRegion(region);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\mshtang\\Desktop\\导出测试\\公司资产分布报表_2.xls");
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet=wb.getSheetAt(0);
//        merge(sheet,0,1,)
//            int num=getMergeRowNumber(sheet,(short)0,7);
//        System.out.println("num = " + num);
    }
}