package com.sino.rds.execute.service;

import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.db.DataTypeConstant;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.datamodel.dao.DBConnectionDAO;
import com.sino.rds.design.report.dao.*;
import com.sino.rds.foundation.db.structure.ConnParser;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActualSQLConstructService extends RDSBaseService {

    private ReportDefineProcessDAO reportDAO = null;
    private boolean hasExpressionField = false;

    private List<ReportViewFrm> definedViews = null;
    private List<ReportCategoryFrm> definedCategories = null;
    private List<ReportCategoryFrm> aboveCategories = null;
    private List<ReportCategoryFrm> leftCategories = null;
    private StringBuilder conditionalSQL = null;
    private boolean conditionProduced = false;
    private boolean reportRunInOracle = false;
    private boolean reportRunInSybase = false;
    private String selectNVLStr = "";

    public ActualSQLConstructService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        aboveCategories = new ArrayList<ReportCategoryFrm>();
        leftCategories = new ArrayList<ReportCategoryFrm>();
        conditionalSQL = new StringBuilder();
    }

    public void constructReportActualSQL() throws DataHandleException {
        try {
            init();
            if (reportDAO.hasSQLUpdated()) {
                constructReportRunEnvironment();
                constructNVLStr();
                constructActualSQL();
                constructIntersectSQL();
                reportDAO.setDTOParameter(dtoParameter);
                reportDAO.confirmActualSQL();
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            freeResource();
        }
    }

    public void finalize() throws Throwable {
        super.finalize();
        freeResource();
    }

    private void freeResource() {
        if (definedViews != null) {
            definedViews.clear();
        }
        if (definedCategories != null) {
            definedCategories.clear();
        }
        if (aboveCategories != null) {
            aboveCategories.clear();
        }
        if (leftCategories != null) {
            leftCategories.clear();
        }
        definedViews = null;
        aboveCategories = null;
        leftCategories = null;
        conditionalSQL = null;
    }

    private void constructActualSQL() throws QueryException {
        constructActualSQLSelectPart();
        constructActualSQLParameter();
        if (hasExpressionField) {
            constructActualSQLGroupByPart();
        }
        constructActualSQLOrderByPart();
    }

    private void constructIntersectSQL() throws QueryException {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        if (dto.isIntersectReport()) {
            constructDimensionSQL();
            constructExpressionSQL();
        } else {
            dto.setAboveDimensionSql("");
            dto.setLeftDimensionSql("");
            dto.setBottomExpressionSql("");
            dto.setVerticalExpressionSql("");
        }
    }

    private void init() throws QueryException {
        initReport();
        initReportView();
        initReportCategory();
    }

    private void initReport() throws QueryException {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        if (dto.getReportId().equals("") && dto.getReportCode().equals("")) {
            LookUpDefineFrm lookUpFrm = new LookUpDefineFrm();
            lookUpFrm.setLookUpId(dto.getLookUpId());
            lookUpFrm.setLookUpCode(dto.getLookUpCode());
            LookUpDefineDAO lookDAO = new LookUpDefineDAO(userAccount, lookUpFrm, conn);
            dto.setReportId(lookDAO.getReportIdByPrimaryKey());
        }
        reportDAO = new ReportDefineProcessDAO(userAccount, dtoParameter, conn);
        dtoParameter = reportDAO.searchDTOByPrimaryKey();
    }

    private void initReportView() throws QueryException {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        ReportViewFrm viewFrm = new ReportViewFrm();
        viewFrm.setReportId(dto.getReportId());
        ReportViewProcessDAO viewDAO = new ReportViewProcessDAO(userAccount, viewFrm, conn);
        definedViews = viewDAO.searchListByForeignKey("reportId");
        hasExpressionField = viewDAO.hasExpressionField();
    }

    private void initReportCategory() throws QueryException {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        if (dto.isIntersectReport()) {
            ReportCategoryFrm frm = new ReportCategoryFrm();
            frm.setReportId(dto.getReportId());
            ReportCategoryProcessDAO categoryDAO = new ReportCategoryProcessDAO(userAccount, frm, conn);
            definedCategories = categoryDAO.searchListByForeignKey("reportId");
            for (ReportCategoryFrm categoryFrm : definedCategories) {
                String viewLocation = categoryFrm.getViewLocation();
                if (viewLocation.equals(RDSDictionaryList.VIEW_LOCATION_ABOVE)) {
                    aboveCategories.add(categoryFrm);
                } else {
                    leftCategories.add(categoryFrm);
                }
            }
        }
    }

    private void constructActualSQLSelectPart() throws QueryException {
        StringBuilder modelSQL = new StringBuilder();
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        if (definedViews != null && !definedViews.isEmpty()) {
            int fieldCount = definedViews.size();
            modelSQL.append("SELECT");
            if (dto.isIntersectReport()) {
                for (int i = 0; i < fieldCount; i++) {
                    ReportViewFrm viewField = definedViews.get(i);
                    if (i > 0) {
                        modelSQL.append(",");
                    }
                    modelSQL.append(WorldConstant.ENTER_CHAR);
                    String computeEx = viewField.getComputeExpression();
                    if (StrUtil.isEmpty(computeEx)) {
                        modelSQL.append("TMP_V.");
                        modelSQL.append(viewField.getFieldName().toUpperCase());
                    } else {
                        modelSQL.append(computeEx);
                        modelSQL.append("(");
                        modelSQL.append(selectNVLStr);
                        modelSQL.append("(TMP_V.");
                        modelSQL.append(viewField.getFieldName().toUpperCase());
                        modelSQL.append(", 0)");
                        modelSQL.append(") ");
                        modelSQL.append(viewField.getFieldName().toUpperCase());
                    }
                }
            } else {
                for (int i = 0; i < fieldCount; i++) {
                    ReportViewFrm viewField = definedViews.get(i);
                    if (i > 0) {
                        modelSQL.append(",");
                    }
                    modelSQL.append(WorldConstant.ENTER_CHAR);
                    modelSQL.append("TMP_V.");
                    modelSQL.append(viewField.getFieldName().toUpperCase());
                }
            }
        }
        modelSQL.append(WorldConstant.ENTER_CHAR);
        modelSQL.append("FROM (");
        modelSQL.append(dto.getModelSql());
        modelSQL.append(") TMP_V ");
        dto.setActualSql(modelSQL.toString());
    }

    /**
     * 功能：将定义的参数解析到SQL中。
     *
     * @throws QueryException
     */
    private void constructActualSQLParameter() throws QueryException {
        produceParameterSQL();
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        StringBuilder actualSQL = new StringBuilder(dto.getActualSql());
        actualSQL.append(conditionalSQL);
        dto.setActualSql(actualSQL.toString());
    }

    private void produceParameterSQL() throws QueryException {
        if (conditionProduced) {
            return;
        }
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        ReportParameterFrm frm = new ReportParameterFrm();
        frm.setReportId(dto.getReportId());
        ReportParameterProcessDAO parameterDAO = new ReportParameterProcessDAO(userAccount, frm, conn);
        List<ReportParameterFrm> sqlParameters = parameterDAO.searchListByForeignKey("reportId");
        if (sqlParameters != null && !sqlParameters.isEmpty()) {
            conditionalSQL.append(WorldConstant.ENTER_CHAR);
            conditionalSQL.append("WHERE ");
            int parameterCount = sqlParameters.size();
            for (int i = 0; i < parameterCount; i++) {
                ReportParameterFrm sqlParameter = sqlParameters.get(i);
                String fieldType = sqlParameter.getFieldType();
                String fieldName = sqlParameter.getFieldName();
                String inputType = sqlParameter.getInputType();
                String nullAble = sqlParameter.getNullAble();
                if (i > 0) {
                    conditionalSQL.append(WorldConstant.ENTER_CHAR);
                    conditionalSQL.append("AND ");
                }
                if (inputType.equals(RDSDictionaryList.INPUT_TYPE_TEXT)) {
                    if (nullAble.equals("N")) {
                        if (reportRunInOracle) {
                            conditionalSQL.append("TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(" LIKE {");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append("}");
                        } else if (reportRunInSybase) {
                            if (needConvert(fieldType)) {
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" = CONVERT(");
                                conditionalSQL.append(fieldType);
                                conditionalSQL.append(",{");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("})");
                            } else {
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" LIKE {");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("}");
                            }
                        }
                    } else {
                        if (reportRunInOracle) {
                            conditionalSQL.append("TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(" LIKE NVL({");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append("}, TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(")");
                        } else if (reportRunInSybase) {
                            if (needConvert(fieldType)) {
                                conditionalSQL.append("({");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("} = '' OR TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" = CONVERT(");
                                conditionalSQL.append(fieldType);
                                conditionalSQL.append(",{");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("}))");
                            } else {
                                conditionalSQL.append("({");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("} = '' OR TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" LIKE {");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("})");
                            }
                        }
                    }
                } else if (inputType.equals(RDSDictionaryList.INPUT_TYPE_CALENDAR)) {
                    String calendarType = sqlParameter.getCalendarType();
                    String startDateField = "START_" + fieldName;
                    String endDateField = "END_" + fieldName;
                    if (calendarType.equals(RDSDictionaryList.CALENDAR_TYPE_START)) {
                        if (nullAble.equals("N")) {
                            conditionalSQL.append("TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(" >= {");
                            conditionalSQL.append(startDateField);
                            conditionalSQL.append("}");
                        } else {
                            if (reportRunInOracle) {
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" >= NVL({");
                                conditionalSQL.append(startDateField);
                                conditionalSQL.append("}, TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(")");
                            } else if (reportRunInSybase) {
                                conditionalSQL.append("({");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("} = '' OR TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" >= {");
                                conditionalSQL.append(startDateField);
                                conditionalSQL.append("})");
                            }
                        }
                    } else if (calendarType.equals(RDSDictionaryList.CALENDAR_TYPE_END)) {
                        if (nullAble.equals("N")) {
                            conditionalSQL.append("TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(" <= {");
                            conditionalSQL.append(endDateField);
                            conditionalSQL.append("}");
                        } else {
                            if (reportRunInOracle) {
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" <= NVL({");
                                conditionalSQL.append(endDateField);
                                conditionalSQL.append("}, TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(")");
                            } else if (reportRunInSybase) {
                                conditionalSQL.append("({");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("} = '' OR TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" <= {");
                                conditionalSQL.append(endDateField);
                                conditionalSQL.append("})");
                            }
                        }
                    } else if (calendarType.equals(RDSDictionaryList.CALENDAR_TYPE_INTERVAL)) {
                        if (nullAble.equals("N")) {

                            conditionalSQL.append("TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(" >= {");
                            conditionalSQL.append(startDateField);
                            conditionalSQL.append("}");

                            conditionalSQL.append(WorldConstant.ENTER_CHAR);
                            conditionalSQL.append("AND ");

                            conditionalSQL.append("TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(" <= {");
                            conditionalSQL.append(endDateField);
                            conditionalSQL.append("}");
                        } else {
                            if (reportRunInOracle) {
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" >= NVL({");
                                conditionalSQL.append(startDateField);
                                conditionalSQL.append("}, TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(")");

                                conditionalSQL.append(WorldConstant.ENTER_CHAR);
                                conditionalSQL.append("AND ");

                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" <= NVL({");
                                conditionalSQL.append(endDateField);
                                conditionalSQL.append("}, TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(")");
                            } else if (reportRunInSybase) {
                                conditionalSQL.append("({");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("} = '' OR TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" >= {");
                                conditionalSQL.append(startDateField);
                                conditionalSQL.append("})");

                                conditionalSQL.append(WorldConstant.ENTER_CHAR);
                                conditionalSQL.append("AND ");

                                conditionalSQL.append("({");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("} = '' OR TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" <= {");
                                conditionalSQL.append(endDateField);
                                conditionalSQL.append("})");

                            }
                        }
                    }
                } else if (inputType.equals(RDSDictionaryList.INPUT_TYPE_LOV)
                        || inputType.equals(RDSDictionaryList.INPUT_TYPE_LOOKUP)
                        || inputType.equals(RDSDictionaryList.INPUT_TYPE_URL)) {
                    if (nullAble.equals("N")) {
                        if (reportRunInOracle) {
                            conditionalSQL.append("TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(" = {");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append("}");
                        } else if (reportRunInSybase) {
                            if (needConvert(fieldType)) {
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" = CONVERT(");
                                conditionalSQL.append(fieldType);
                                conditionalSQL.append(",{");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("})");
                            } else {
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" = {");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("}");
                            }
                        }
                    } else {
                        if (reportRunInOracle) {
                            conditionalSQL.append("TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(" = NVL({");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append("}, TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(")");
                        } else if (reportRunInSybase) {
                            if (needConvert(fieldType)) {
                                conditionalSQL.append("({");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("} = '' OR TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" = CONVERT(");
                                conditionalSQL.append(fieldType);
                                conditionalSQL.append(", {");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("}))");
                            } else {
                                conditionalSQL.append("({");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("} = '' OR TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" = {");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append("})");
                            }
                        }
                    }
                } else if (inputType.equals(RDSDictionaryList.INPUT_TYPE_SESSION)) {
                    if (nullAble.equals("N")) {
                        if (reportRunInOracle) {
                            conditionalSQL.append("TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(" = {SESSION.");
                            conditionalSQL.append(StrUtil.getJavaField(fieldName));
                            conditionalSQL.append("}");
                        } else if (reportRunInSybase) {
                            if (needConvert(fieldType)) {
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" = CONVERT(");
                                conditionalSQL.append(fieldType);
                                conditionalSQL.append(",{SESSION.");
                                conditionalSQL.append(StrUtil.getJavaField(fieldName));
                                conditionalSQL.append("})");
                            } else {
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" = {SESSION.");
                                conditionalSQL.append(StrUtil.getJavaField(fieldName));
                                conditionalSQL.append("}");
                            }
                        }
                    } else {
                        if (reportRunInOracle) {
                            conditionalSQL.append("TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(" = NVL({SESSION.");
                            conditionalSQL.append(StrUtil.getJavaField(fieldName));
                            conditionalSQL.append("}, TMP_V.");
                            conditionalSQL.append(fieldName);
                            conditionalSQL.append(")");
                        } else {
                            if (needConvert(fieldType)) {
                                conditionalSQL.append("({SESSION.");
                                conditionalSQL.append(StrUtil.getJavaField(fieldName));
                                conditionalSQL.append("} = '' OR TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" = CONVERT(");
                                conditionalSQL.append(fieldType);
                                conditionalSQL.append(",{SESSION.");
                                conditionalSQL.append(StrUtil.getJavaField(fieldName));
                                conditionalSQL.append("}))");
                            } else {
                                conditionalSQL.append("({SESSION.");
                                conditionalSQL.append(StrUtil.getJavaField(fieldName));
                                conditionalSQL.append("} = '' OR TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" = {SESSION.");
                                conditionalSQL.append(StrUtil.getJavaField(fieldName));
                                conditionalSQL.append("})");
                            }
                        }
                    }
                } else if (inputType.equals(RDSDictionaryList.INPUT_TYPE_HIDDEN)) {
                    String defaultValue = sqlParameter.getDefaultValue();
                    defaultValue = StrUtil.nullToString(defaultValue).trim();
                    if (!defaultValue.equals("")) {
                        defaultValue = StrUtil.trim(defaultValue, ",");
                        if(defaultValue.contains("%") && ArrUtil.contains(DataTypeConstant.LIMIT_STRING_TYPE, fieldType)){//包含%且字段类型为字符串，用like查询
                            String[] defaultArr = StrUtil.splitStr(defaultValue, ",");
                            conditionalSQL.append("(");
                            for(int index = 0; index < defaultArr.length; index++){
                                String defaultStr = defaultArr[index];
                                defaultStr = StrUtil.replaceStr(defaultStr, "'", "");
                                defaultStr = "'" + defaultStr + "'";
                                if(index > 0){
                                    conditionalSQL.append(" OR ");
                                }
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" LIKE ");
                                conditionalSQL.append(defaultStr);
                            }
                            conditionalSQL.append(")");
                        } else {//不包含%，用in查询
                            if (reportRunInOracle) {
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" IN (");
                                if (ArrUtil.contains(DataTypeConstant.LIMIT_NUMBER_TYPE, fieldType)) {
                                    defaultValue = StrUtil.replaceStr(defaultValue, "'", "");
                                    conditionalSQL.append(defaultValue);
                                } else if (ArrUtil.contains(DataTypeConstant.LIMIT_STRING_TYPE, fieldType)) {
                                    if (!defaultValue.contains("'")) {
                                        defaultValue = StrUtil.replaceStr(defaultValue, ",", "', '");
                                    }
                                    defaultValue = "'" + defaultValue + "'";
                                    conditionalSQL.append(defaultValue);
                                } else if (ArrUtil.contains(DataTypeConstant.LIMIT_DATE_TYPE, fieldType)) {
                                    String[] defaultValArr = StrUtil.splitStr(defaultValue, ",");
                                    int defaultCount = defaultValArr.length;
                                    for (int j = 0; j < defaultCount; j++) {
                                        String defaultVal = defaultValArr[j];
                                        defaultVal = StrUtil.replaceStr(defaultVal, "'", "");
                                        if(CalendarUtil.isValidDate(defaultVal)){
                                            conditionalSQL.append("TO_DATE('");
                                            conditionalSQL.append(defaultVal);
                                            conditionalSQL.append("', 'YYYY-MM-DD')");
                                        } else if(CalendarUtil.isValidCalendar(defaultVal)){
                                            conditionalSQL.append("TO_DATE('");
                                            conditionalSQL.append(defaultVal);
                                            conditionalSQL.append("', 'YYYY-MM-DD HH24:MI:SS')");
                                        }
                                        if(j < defaultCount - 1){
                                            conditionalSQL.append(",");
                                        }
                                    }
                                }
                                conditionalSQL.append(")");
                            } else if (reportRunInSybase) {
                                conditionalSQL.append("TMP_V.");
                                conditionalSQL.append(fieldName);
                                conditionalSQL.append(" IN (");
                                if (ArrUtil.contains(DataTypeConstant.LIMIT_NUMBER_TYPE, fieldType)) {
                                    defaultValue = StrUtil.replaceStr(defaultValue, "'", "");
                                    conditionalSQL.append(defaultValue);
                                } else if (ArrUtil.contains(DataTypeConstant.LIMIT_STRING_TYPE, fieldType)) {
                                    if (!defaultValue.contains("'")) {
                                        defaultValue = StrUtil.replaceStr(defaultValue, ",", "', '");
                                    }
                                    defaultValue = "'" + defaultValue + "'";
                                    conditionalSQL.append(defaultValue);
                                } else if (ArrUtil.contains(DataTypeConstant.LIMIT_DATE_TYPE, fieldType)) {
                                    String[] defaultValArr = StrUtil.splitStr(defaultValue, ",");
                                    int defaultCount = defaultValArr.length;
                                    for (int j = 0; j < defaultCount; j++) {
                                        String defaultVal = defaultValArr[j];
                                        defaultVal = StrUtil.replaceStr(defaultVal, "'", "");
                                        if(CalendarUtil.isValidDate(defaultVal)){
                                            conditionalSQL.append("CONVERT(DATE, '");
                                            conditionalSQL.append(defaultVal);
                                            conditionalSQL.append("')");
                                        } else if(CalendarUtil.isValidCalendar(defaultVal)){
                                            conditionalSQL.append("CONVERT(DATETIME, '");
                                            conditionalSQL.append(defaultVal);
                                            conditionalSQL.append("')");
                                        }
                                        if(j < defaultCount - 1){
                                            conditionalSQL.append(",");
                                        }
                                    }
                                }
                                conditionalSQL.append(")");
                            }
                        }
                    }
                }
            }
        }
        conditionProduced = true;
    }

    private void constructActualSQLGroupByPart() throws QueryException {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        if (dto.isIntersectReport()) {
            if (definedCategories != null && !definedCategories.isEmpty()) {
                StringBuilder groupBySQL = new StringBuilder(dto.getActualSql());
                groupBySQL.append(WorldConstant.ENTER_CHAR);
                groupBySQL.append("GROUP BY ");
                for (ReportCategoryFrm categoryFrm : definedCategories) {
                    groupBySQL.append(WorldConstant.ENTER_CHAR);
                    groupBySQL.append("TMP_V.");
                    groupBySQL.append(categoryFrm.getFieldName().toUpperCase());
                    groupBySQL.append(",");
                }
                groupBySQL = new StringBuilder(groupBySQL.substring(0, groupBySQL.length() - 1));
                dto.setActualSql(groupBySQL.toString());
            }
        }
    }

    private void constructActualSQLOrderByPart() throws QueryException {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        if (dto.isIntersectReport()) {
            if (definedCategories != null && !definedCategories.isEmpty()) {
                StringBuilder orderBySQL = new StringBuilder(WorldConstant.ENTER_CHAR);
                orderBySQL.append("ORDER BY");
                for (ReportCategoryFrm categoryFrm : definedCategories) {
                    orderBySQL.append(WorldConstant.ENTER_CHAR);
                    orderBySQL.append("TMP_V.");
                    orderBySQL.append(categoryFrm.getFieldName().toUpperCase());
                    orderBySQL.append(",");
                }
                orderBySQL = new StringBuilder(orderBySQL.substring(0, orderBySQL.length() - 1));
                StringBuilder actualSQL = new StringBuilder(dto.getActualSql());
                actualSQL.append(orderBySQL);
                dto.setActualSql(actualSQL.toString());
            }
        }
    }

    private void constructDimensionSQL() throws QueryException {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        if (aboveCategories != null && !aboveCategories.isEmpty()) {
            StringBuilder dimensionSQL = getDimensionSQL(aboveCategories);
            dto.setAboveDimensionSql(dimensionSQL.toString());
        } else {
            dto.setAboveDimensionSql("");
        }
        if (leftCategories != null && !leftCategories.isEmpty()) {
            StringBuilder dimensionSQL = getDimensionSQL(leftCategories);
            dto.setLeftDimensionSql(dimensionSQL.toString());
        } else {
            dto.setLeftDimensionSql("");
        }
    }

    private StringBuilder getDimensionSQL(List<ReportCategoryFrm> categories) throws QueryException {
        StringBuilder dimensionSQL = new StringBuilder();
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        int fieldCount = categories.size();
        dimensionSQL.append("SELECT ");
        StringBuilder groupBySQL = new StringBuilder("GROUP BY");
        StringBuilder orderBySQL = new StringBuilder("ORDER BY");
        for (int i = 0; i < fieldCount; i++) {
            ReportCategoryFrm categoryFrm = categories.get(i);
            String fieldName = categoryFrm.getFieldName();
            if (i > 0) {
                dimensionSQL.append(",");
                orderBySQL.append(",");
                groupBySQL.append(",");
            }
            dimensionSQL.append(WorldConstant.ENTER_CHAR);
            dimensionSQL.append("TMP_V.");
            dimensionSQL.append(fieldName);

            groupBySQL.append(WorldConstant.ENTER_CHAR);
            groupBySQL.append("TMP_V.");
            groupBySQL.append(fieldName);

            orderBySQL.append(WorldConstant.ENTER_CHAR);
            orderBySQL.append("TMP_V.");
            orderBySQL.append(fieldName);
        }
        dimensionSQL.append(WorldConstant.ENTER_CHAR);
        dimensionSQL.append("FROM (");
        dimensionSQL.append(dto.getModelSql());
        dimensionSQL.append(") TMP_V");
        dimensionSQL.append(conditionalSQL);
        dimensionSQL.append(WorldConstant.ENTER_CHAR);
        dimensionSQL.append(groupBySQL);
        dimensionSQL.append(WorldConstant.ENTER_CHAR);
        dimensionSQL.append(orderBySQL);
        return dimensionSQL;
    }

    private void constructExpressionSQL() {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        String sumPosition = dto.getSumPosition();
        if (!sumPosition.equals("")) {
            processBottomExpressionSQL();
            processVerticalExpressionSQL();
        } else {
            dto.setBottomExpressionSql("");
            dto.setVerticalExpressionSql("");
        }
    }

    private void processBottomExpressionSQL() {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        StringBuilder bottomSQL = new StringBuilder();
        StringBuilder selectPart = getBottomExpressionSQLSelectPart();
        StringBuilder groupByPart = getExpressionSQLGroupByPart(aboveCategories);
        bottomSQL.append(selectPart);
        bottomSQL.append(conditionalSQL);
        bottomSQL.append(WorldConstant.ENTER_CHAR);
        bottomSQL.append(groupByPart);
        dto.setBottomExpressionSql(bottomSQL.toString());
    }

    private void processVerticalExpressionSQL() {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        StringBuilder rightSQL = new StringBuilder();
        StringBuilder selectPart = getVerticalExpressionSQLSelectPart();
        StringBuilder groupByPart = getExpressionSQLGroupByPart(leftCategories);
        rightSQL.append(selectPart);
        rightSQL.append(conditionalSQL);
        rightSQL.append(WorldConstant.ENTER_CHAR);
        rightSQL.append(groupByPart);
        dto.setVerticalExpressionSql(rightSQL.toString());
    }


    private StringBuilder getBottomExpressionSQLSelectPart() {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        StringBuilder expressionSQL = new StringBuilder();
        int fieldCount = definedViews.size();
        expressionSQL.append("SELECT");
        boolean addComman = false;
        for (int i = 0; i < fieldCount; i++) {
            ReportViewFrm rvf = definedViews.get(i);
            String fieldName = rvf.getFieldName();
            if (addComman) {
                expressionSQL.append(",");
                addComman = false;
            }
            if (needExpression(rvf)) {
                expressionSQL.append(WorldConstant.ENTER_CHAR);
                expressionSQL.append("SUM(");
                expressionSQL.append(selectNVLStr);
                expressionSQL.append("(TMP_V.");
                expressionSQL.append(fieldName);
                expressionSQL.append(", 0)) ");
                expressionSQL.append(fieldName);
                addComman = true;
            } else {
                boolean found = false;
                for (ReportCategoryFrm category : aboveCategories) {
                    if (category.getFieldName().equals(fieldName)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    expressionSQL.append(WorldConstant.ENTER_CHAR);
                    expressionSQL.append("TMP_V.");
                    expressionSQL.append(fieldName);
                    addComman = true;
                }
            }
        }
        String expSQL = expressionSQL.toString();
        if (expSQL.endsWith(",")) {
            expSQL = expSQL.substring(0, expSQL.length() - 1);
            expressionSQL = new StringBuilder(expSQL);
        }
        expressionSQL.append(WorldConstant.ENTER_CHAR);
        expressionSQL.append("FROM (");
        expressionSQL.append(dto.getModelSql());
        expressionSQL.append(") TMP_V ");
        return expressionSQL;
    }

    private boolean needExpression(ReportViewFrm rvf) {
        boolean needExpression = false;
        String fieldType = rvf.getFieldType();
        String computeEx = rvf.getComputeExpression();
        if (!StrUtil.isEmpty(computeEx)) {
            needExpression = true;
        } else {
            if (!hasExpressionField) {
                if (ArrUtil.isInArr(DataTypeConstant.LIMIT_NUMBER_TYPE, fieldType)) {
                    needExpression = true;
                }
            }
        }
        return needExpression;
    }

    private StringBuilder getVerticalExpressionSQLSelectPart() {
        ReportDefineFrm dto = (ReportDefineFrm) dtoParameter;
        StringBuilder expressionSQL = new StringBuilder();
        int fieldCount = definedViews.size();
        expressionSQL.append("SELECT");
        boolean addComman = false;
        for (int i = 0; i < fieldCount; i++) {
            ReportViewFrm rvf = definedViews.get(i);
            String fieldName = rvf.getFieldName();
            if (addComman) {
                expressionSQL.append(",");
                addComman = false;
            }
            if (needExpression(rvf)) {
                expressionSQL.append(WorldConstant.ENTER_CHAR);
                expressionSQL.append("SUM(");
                expressionSQL.append(selectNVLStr);
                expressionSQL.append("(TMP_V.");
                expressionSQL.append(fieldName);
                expressionSQL.append(", 0)) ");
                expressionSQL.append(fieldName);
                addComman = true;
            } else {
                boolean found = false;
                for (ReportCategoryFrm category : leftCategories) {
                    if (category.getFieldName().equals(fieldName)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    expressionSQL.append(WorldConstant.ENTER_CHAR);
                    expressionSQL.append("TMP_V.");
                    expressionSQL.append(fieldName);
                    addComman = true;
                }
            }
        }
        String expSQL = expressionSQL.toString();
        if (expSQL.endsWith(",")) {
            expSQL = expSQL.substring(0, expSQL.length() - 1);
            expressionSQL = new StringBuilder(expSQL);
        }
        expressionSQL.append(WorldConstant.ENTER_CHAR);
        expressionSQL.append("FROM (");
        expressionSQL.append(dto.getModelSql());
        expressionSQL.append(") TMP_V ");
        return expressionSQL;

    }

    private StringBuilder getExpressionSQLGroupByPart(List<ReportCategoryFrm> categories) {
        StringBuilder groupBySQL = new StringBuilder();
        if (categories != null && !categories.isEmpty()) {
            int fieldCount = categories.size();
            groupBySQL.append("GROUP BY ");
            StringBuilder orderBySQL = new StringBuilder(WorldConstant.ENTER_CHAR);
            orderBySQL.append("ORDER BY");
            for (int i = 0; i < fieldCount; i++) {
                ReportCategoryFrm categoryFrm = categories.get(i);
                groupBySQL.append(WorldConstant.ENTER_CHAR);
                groupBySQL.append("TMP_V.");
                groupBySQL.append(categoryFrm.getFieldName().toUpperCase());
                groupBySQL.append(",");

                orderBySQL.append(WorldConstant.ENTER_CHAR);
                orderBySQL.append("TMP_V.");
                orderBySQL.append(categoryFrm.getFieldName().toUpperCase());
                orderBySQL.append(",");
            }
            groupBySQL = new StringBuilder(groupBySQL.substring(0, groupBySQL.length() - 1));
            groupBySQL.append(orderBySQL.substring(0, orderBySQL.length() - 1));
        }
        return groupBySQL;
    }

    private void constructReportRunEnvironment() throws QueryException {
        Connection dataSource = null;
        try {
            DBConnectionFrm dbcFrm = new DBConnectionFrm();
            ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;
            dbcFrm.setConnectionId(frm.getConnectionId());
            DBConnectionDAO connectionDAO = new DBConnectionDAO(userAccount, dbcFrm, conn);
            dbcFrm = connectionDAO.searchDTOByPrimaryKey();
            if (dbcFrm != null) {
                dataSource = dbcFrm.getDBConnection();
                ConnParser parser = new ConnParser(dataSource);
                reportRunInOracle = parser.isOracleDBProduct();
                reportRunInSybase = parser.isSybaseDBProduct();
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        } finally {
            try {
                if (dataSource != null) {
                    dataSource.close();
                }
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
    }

    private boolean needConvert(String fieldType) {
//        return (!fieldType.equals("VARCHAR") && !fieldType.equals("DATE") && !fieldType.equals("DATETIME"));
        return (fieldType.equals("INT")
                || fieldType.equals("NUMERIC")
                || fieldType.equals("DECIMAL")
                || fieldType.equals("BIGINT")
                || fieldType.equals("FLOAT")
        );
    }

    private void constructNVLStr() {
        if (reportRunInOracle) {
            selectNVLStr = "NVL";
        } else if (reportRunInSybase) {
            selectNVLStr = "ISNULL";
        }
    }
}