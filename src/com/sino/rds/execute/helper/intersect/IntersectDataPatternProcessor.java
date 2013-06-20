package com.sino.rds.execute.helper.intersect;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.DataTypeConstant;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ReportException;
import com.sino.base.log.Logger;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.StrUtil;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.IntersectReportExecuteFrm;
import com.sino.rds.share.form.ReportCategoryFrm;
import com.sino.rds.share.form.ReportViewFrm;
import com.sino.rds.share.util.ReflectionProcessor;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public abstract class IntersectDataPatternProcessor {

    public static void formatReportData(IntersectReportExecuteFrm intersectDataFrm) throws ReportException {
        if(!needProcess(intersectDataFrm)){
            return;
        }
        List<ReportViewFrm> reportViews = intersectDataFrm.getDefinedViews();
        List<ReportCategoryFrm> leftCategories = intersectDataFrm.getLeftCategories();
        List<ReportCategoryFrm> aboveCategories = intersectDataFrm.getAboveCategories();
        List<ReportViewFrm> measureValueFields = intersectDataFrm.getMeasureValueFields();
        int dataFrmCount = intersectDataFrm.getDataFrmCount();
        int dataStartRow = intersectDataFrm.getDataStartRow();
        int dataStartColumn = intersectDataFrm.getDataStartColumn();
        Object[][] reportValue = intersectDataFrm.getReportValue();
        if (reportValue != null && reportValue.length > 0) {
            int rowCount = reportValue.length;
            for (int i = 0; i < rowCount; i++) {
                Object[] rowData = reportValue[i];
                if (rowData != null && rowData.length > 0) {
                    int columnCount = rowData.length;
                    for (int j = 0; j < columnCount; j++) {
                        Object cellData = rowData[j];
                        ReportViewFrm reportView = null;
                        if (i < dataStartRow - 1) {
                            if (j >= dataStartColumn) {
                                ReportCategoryFrm categoryFrm = aboveCategories.get(i);
                                String fieldName = categoryFrm.getFieldName();
                                reportView = getReportViewFrm(reportViews, fieldName);
                            }
                        } else if(i >= dataStartRow){
                            if (j < dataStartColumn) {
                                ReportCategoryFrm categoryFrm = leftCategories.get(j);
                                String fieldName = categoryFrm.getFieldName();
                                reportView = getReportViewFrm(reportViews, fieldName);
                            } else {
                                int index = (j - dataStartColumn) % dataFrmCount;
                                reportView = measureValueFields.get(index);
                            }
                        }
                        if(reportView == null){
                            continue;
                        }
                        String dataPattern = reportView.getDataPattern();
                        if (dataPattern.equals("")) {
                            continue;
                        }
                        if(StrUtil.isEmpty(cellData)){
                            continue;
                        }
                        String fieldType = reportView.getFieldType();
                        if (fieldType.equals(DataTypeConstant.DATE) || fieldType.equals("DATETIME")) {
                            rowData[j] = processCalendarPattern(cellData, reportView);
                        } else if (ArrUtil.contains(DataTypeConstant.LIMIT_NUMBER_TYPE, fieldType)) {
                            rowData[j] = processNumberPattern(cellData, reportView);
                        }
                    }
                }
            }
        }
    }

    private static ReportViewFrm getReportViewFrm(List<ReportViewFrm> reportViews, String fieldName) {
        for (ReportViewFrm reportView : reportViews) {
            if (reportView.getFieldName().equals(fieldName)) {
                return reportView;
            }
        }
        return null;
    }

    private static boolean needProcess(IntersectReportExecuteFrm intersectDataFrm) {
        boolean needProcess = false;
        needProcess = (intersectDataFrm != null);
        if(needProcess){
            List<ReportViewFrm> dataFrms = intersectDataFrm.getMeasureValueFields();
            needProcess = (dataFrms != null);
        }
        return needProcess;
    }

    private static String processCalendarPattern(Object cellData, ReportViewFrm reportView) throws ReportException {
        String fieldValue = StrUtil.nullToString(cellData);
        try {
            if (!fieldValue.equals("")) {
                String calPattern = reportView.getDataPattern();
                if(calPattern.equals("")){
                    if(fieldValue.length() <= 10){
                        calPattern = CalendarConstant.LINE_PATTERN;
                    } else {
                        calPattern = CalendarConstant.CAL_PATT_14;
                    }
                }
                calPattern = StrUtil.nullToString(ReflectionProcessor.getProperty(CalendarConstant.class, calPattern));
                SimpleCalendar cal = new SimpleCalendar(fieldValue);
                cal.setCalPattern(calPattern);
            }
        } catch (CalendarException ex) {
            ex.printLog();
            throw new ReportException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        }
        return fieldValue;
    }

    private static String processNumberPattern(Object cellData, ReportViewFrm reportView) throws ReportException {
        String fieldValue = StrUtil.nullToString(cellData);
        try {
            if(!fieldValue.equals("")){
                String numberPattern = getNumberPattern(reportView);
                if(!numberPattern.equals("")){
                    DecimalFormat df = new DecimalFormat();
                    df.applyPattern(numberPattern);
                    fieldValue = df.format(Double.parseDouble(fieldValue));
//                    fieldValue = StrUtil.trim(fieldValue, "0", true);
                    if(fieldValue.startsWith(".")){
                        fieldValue = "0" + fieldValue;
                    }
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ReportException(ex.getMessage());
        }
        return fieldValue;
    }

    private static String getNumberPattern(ReportViewFrm reportView) {
        String numberPattern = "";
        String dataPattern = reportView.getDataPattern();
        String dotNumber = reportView.getDotNumber();
        int dotCount = 0;
        if (!StrUtil.isEmpty(dotNumber)) {
            dotCount = Integer.parseInt(dotNumber);
        }
        String dotPattern = StrUtil.getStrByCount("0", dotCount);
        if (dataPattern.equals(RDSDictionaryList.NUMBER_PATTERN_THOUSAND)) {
            numberPattern = "#,000";
            if (!dotPattern.equals("")) {
                numberPattern += "." + dotPattern;
            }
        } else if (dataPattern.equals(RDSDictionaryList.NUMBER_PATTERN_PERCENT)) {
            numberPattern = "0";
            if (!dotPattern.equals("")) {
                numberPattern += "." + dotPattern;
            }
            numberPattern += "%";
        } else if (dataPattern.equals(RDSDictionaryList.NUMBER_PATTERN_CURRENCY)) {
            numberPattern = "гд#,000.00";
        } else if (dataPattern.equals(RDSDictionaryList.NUMBER_PATTERN_INTEGER)) {
            numberPattern = "0";
        } else if (dataPattern.equals(RDSDictionaryList.NUMBER_PATTERN_PERMILLAGE)) {
            numberPattern = "0";
            if (!dotPattern.equals("")) {
                numberPattern += "." + dotPattern;
            }
            numberPattern += "бы";
        }
        return numberPattern;
    }

    public static void main(String[] args){
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("111");
        String fieldValue = df.format(Double.parseDouble("12"));
        System.out.println(fieldValue);
    }
}
