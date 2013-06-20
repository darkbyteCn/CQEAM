package com.sino.rds.execute.helper.commonlist;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.DataTypeConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.ContainerException;
import com.sino.base.log.Logger;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.StrUtil;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.ReportExecuteFrm;
import com.sino.rds.share.form.ReportViewFrm;
import com.sino.rds.share.util.ReflectionProcessor;

import java.text.DecimalFormat;
import java.util.List;

public abstract class CommonDataPatternProcessor {

    public static void formatReportData(ReportExecuteFrm executeFrm) throws ContainerException {
        RowSet reportData = executeFrm.getSearchResult();
        List<ReportViewFrm> reportViews = executeFrm.getDefinedViews();
        if (needProcess(reportData, reportViews)) {
            for (ReportViewFrm reportView : reportViews) {
                String dataPattern = reportView.getDataPattern();
                if (dataPattern.equals("")) {
                    continue;
                }
                String fieldType = reportView.getFieldType();
                if (fieldType.equals(DataTypeConstant.DATE) || fieldType.equals("DATETIME")) {
                    processCalendarPattern(reportData, reportView);
                } else if (ArrUtil.contains(DataTypeConstant.LIMIT_NUMBER_TYPE, fieldType)) {
                    processNumberPattern(reportData, reportView);
                }
            }
        }
    }

    private static boolean needProcess(RowSet reportData, List<ReportViewFrm> reportViews) {
        return (reportViews != null
                && !reportViews.isEmpty()
                && reportData != null
                && !reportData.isEmpty());
    }

    private static void processCalendarPattern(RowSet reportData, ReportViewFrm reportView) throws ContainerException {
        try {
            String fieldName = reportView.getFieldName();
            int rowCount = reportData.getSize();
            String calPattern = reportView.getDataPattern();
            calPattern = StrUtil.nullToString(ReflectionProcessor.getProperty(CalendarConstant.class, calPattern));
            for (int i = 0; i < rowCount; i++) {
                Row row = reportData.getRow(i);
                String fieldValue = row.getStrValue(fieldName);
                SimpleCalendar cal = new SimpleCalendar(fieldValue);
                cal.setCalPattern(calPattern);
                row.setField(fieldName, cal.toString());
            }
        } catch (CalendarException ex) {
            ex.printLog();
            throw new ContainerException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ContainerException(ex.getMessage());
        }
    }

    private static void processNumberPattern(RowSet reportData, ReportViewFrm reportView) throws ContainerException {
        try {
            String fieldName = reportView.getFieldName();
            DecimalFormat df = new DecimalFormat();
            String numberPattern = getNumberPattern(reportView);
            df.applyPattern(numberPattern);
            int rowCount = reportData.getSize();
            for (int i = 0; i < rowCount; i++) {
                Row row = reportData.getRow(i);
                String fieldValue = row.getStrValue(fieldName);
                if(StrUtil.isEmpty(fieldValue)){
                    continue;
                }
                fieldValue = df.format(Double.parseDouble(fieldValue));
                row.setField(fieldName, fieldValue);
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ContainerException(ex.getMessage());
        }
    }

    private static String getNumberPattern(ReportViewFrm reportView){
        String numberPattern = "";
        String dataPattern = reportView.getDataPattern();
        String dotNumber = reportView.getDotNumber();
        int dotCount = 0;
        if(!StrUtil.isEmpty(dotNumber)){
            dotCount = Integer.parseInt(dotNumber);
        }
        String dotPattern = StrUtil.getStrByCount("0", dotCount);
        if (dataPattern.equals(RDSDictionaryList.NUMBER_PATTERN_THOUSAND)) {
            numberPattern = "#,000";
            if(!dotPattern.equals("")){
                numberPattern += "." + dotPattern;
            }
        } else if (dataPattern.equals(RDSDictionaryList.NUMBER_PATTERN_PERCENT)) {
            numberPattern = "0";
            if(!dotPattern.equals("")){
                numberPattern += "." + dotPattern;
            }
            numberPattern += "%";
        } else if (dataPattern.equals(RDSDictionaryList.NUMBER_PATTERN_CURRENCY)) {
            numberPattern = "гд#,000.00";
        } else if (dataPattern.equals(RDSDictionaryList.NUMBER_PATTERN_INTEGER)) {
            numberPattern = "0";
        } else if (dataPattern.equals(RDSDictionaryList.NUMBER_PATTERN_PERMILLAGE)) {
            numberPattern = "0";
            if(!dotPattern.equals("")){
                numberPattern += "." + dotPattern;
            }
            numberPattern += "бы";
        }
        return numberPattern;
    }
}
