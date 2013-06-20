package com.sino.rds.execute.model.sybase;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ReflectException;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.RDSConstantConfigManager;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.execute.model.DynamicSQLProduceModel;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.ReportDefineFrm;
import com.sino.rds.share.form.ReportExecuteFrm;
import com.sino.rds.share.form.ReportParameterFrm;
import com.sino.rds.share.form.SearchParameterFrm;

import java.util.ArrayList;
import java.util.List;

public class SybaseDynamicSQLProduceModel extends DefaultRDSBaseSQLModelImpl implements DynamicSQLProduceModel {

    private ReportExecuteFrm executeFrm = null;
    private SearchParameterFrm searchFrm = null;


    public SybaseDynamicSQLProduceModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public void setExecuteFrm(ReportExecuteFrm executeFrm) {
        this.executeFrm = executeFrm;
        this.searchFrm = executeFrm.getSearchFrm();
    }

    public SQLModel getActualSQLModel() throws ReflectException {
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        String actualSql = reportFrm.getActualSql();
        return constructSQLModel(actualSql);
    }

    public SQLModel getAboveDimensionSQLModel() throws ReflectException {
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        String actualSql = reportFrm.getAboveDimensionSql();
        return constructSQLModel(actualSql);
    }

    public SQLModel getLeftDimensionSQLModel() throws ReflectException {
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        String actualSql = reportFrm.getLeftDimensionSql();
        return constructSQLModel(actualSql);
    }

    public SQLModel getBottomExpressionSQLModel() throws ReflectException {
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        String actualSql = reportFrm.getBottomExpressionSql();
        return constructSQLModel(actualSql);
    }

    public SQLModel getVerticalExpressionSQLModel() throws ReflectException {
        ReportDefineFrm reportFrm = (ReportDefineFrm) dtoParameter;
        String actualSql = reportFrm.getVerticalExpressionSql();
        return constructSQLModel(actualSql);
    }

    private SQLModel constructSQLModel(String inputSQL) throws ReflectException {
        SQLModel leftSQLModel = null;
        if (!inputSQL.equals("")) {
            String sqlStr = inputSQL;
            List sqlArgs = new ArrayList();
            List<ReportParameterFrm> parameters = executeFrm.getDefinedParameters();
            if (parameters != null && !parameters.isEmpty()) {
                if (sqlStr.indexOf("{") > 0) {
                    int containNumber = StrUtil.containNum(sqlStr, "{");
                    String parameterStr = "";
                    int sessionLength = RDSDictionaryList.SESSION_PREFIX.length();
                    String parameterValue = "";
                    for (int i = 0; i < containNumber; i++) {
                        boolean beginWithStart = false;
                        boolean beginWithEnd = false;
                        int existTime = i + 1;
                        parameterStr = StrUtil.getBetwStr(inputSQL, "{", existTime, "}", existTime);
                        parameterStr = parameterStr.trim();
                        String fieldName = parameterStr;
                        int index = parameterStr.indexOf(RDSDictionaryList.SESSION_PREFIX);
                        if (index == 0) {//如果是Session变量，则需要检查用户类中是否含有指定属性
                            String userProp = parameterStr.substring(sessionLength);
                            if (RDSConstantConfigManager.contains(userProp)) {
                                parameterStr = "{" + parameterStr + "}";
                                sqlStr = StrUtil.replaceFirst(sqlStr, parameterStr, "?");
                                parameterValue = StrUtil.nullToString(ReflectionUtil.getProperty(userAccount, userProp));
                                sqlArgs.add(parameterValue);
                            }
                        } else {//非Session变量
                            parameterStr = "{" + parameterStr + "}";
                            sqlStr = StrUtil.replaceFirst(sqlStr, parameterStr, "?");
                            parameterValue = searchFrm.getParameter(StrUtil.getJavaField(fieldName));
                            ReportParameterFrm parameter = getReportParameter(fieldName, parameters);
                            if (parameter != null) {
                                sqlArgs.add(parameterValue);
                            } else {
                                if (fieldName.startsWith("START_")) {
                                    fieldName = fieldName.substring(6);
                                    beginWithStart = true;
                                } else if (fieldName.startsWith("END_")) {
                                    fieldName = fieldName.substring(4);
                                    beginWithEnd = true;
                                }
                                parameter = getReportParameter(fieldName, parameters);
                                if (parameter != null) {
                                    if (!parameterValue.equals("")) {
                                        if (beginWithEnd) {
                                            parameterValue += " 23:59:59";
                                        } else if (beginWithStart) {
                                            parameterValue += " 00:00:00";
                                        }
                                    }
                                    if (CalendarUtil.isValidCalendar(parameterValue) || parameterValue.equals("")) {
                                        SimpleCalendar cal = new SimpleCalendar(parameterValue);
                                        sqlArgs.add(cal);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            leftSQLModel = new SQLModel();
            leftSQLModel.setSqlStr(sqlStr);
            leftSQLModel.setArgs(sqlArgs);
        }
        return leftSQLModel;
    }


    /**
     * 功能:根据参数名称获取参数对象
     *
     * @param fieldName  参数名称
     * @param parameters 本报表定义的参数字段列表
     * @return 参数对象
     */
    private ReportParameterFrm getReportParameter(String fieldName, List<ReportParameterFrm> parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            for (ReportParameterFrm parameter : parameters) {
                if (parameter.getFieldName().equals(fieldName)) {
                    return parameter;
                }
            }
        }
        return null;
    }
}
