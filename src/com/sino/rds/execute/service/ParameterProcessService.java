package com.sino.rds.execute.service;

import com.sino.base.constant.WorldConstant;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DatabaseException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReflectException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.RDSConstantConfigManager;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.datamodel.dao.DBConnectionDAO;
import com.sino.rds.design.report.dao.LovDefineDAO;
import com.sino.rds.foundation.exception.WebException;
import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.foundation.web.util.option.OptionProduceRule;
import com.sino.rds.foundation.web.util.option.OptionProducer;
import com.sino.rds.foundation.web.util.option.OptionProducerFactory;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.DBConnectionFrm;
import com.sino.rds.share.form.LovDefineFrm;
import com.sino.rds.share.form.ReportParameterFrm;
import com.sino.rds.share.form.SearchParameterFrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ParameterProcessService extends RDSBaseService {
    private List<ReportParameterFrm> parameters = null;

    private static int defaultRowColumns = 8;
    private static int defaultRowHeight = 23;
    private int currentRowColumns = 0;
    private int searchDIVHeight = 0;
    private int tdWidth = 0;

    public ParameterProcessService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        tdWidth = 100 / defaultRowColumns;
    }

    public void setParameters(List<ReportParameterFrm> parameters) {
        this.parameters = parameters;
        calculateSearchDIVHeight();
    }

    private void calculateSearchDIVHeight() {
        int notSessionCount = getNotSessionParameters();
        if (notSessionCount > 0) {
            int parameterCount = notSessionCount * 2;
            searchDIVHeight = parameterCount / defaultRowColumns;
            if (parameterCount % defaultRowColumns > 0) {
                searchDIVHeight += 1;
            }
            searchDIVHeight = searchDIVHeight * defaultRowHeight;
        }
    }

    private int getNotSessionParameters() {
        int notSessionCount = parameters.size();
        if (parameters != null && !parameters.isEmpty()) {
            for (ReportParameterFrm parameter : parameters) {
                String inputType = parameter.getInputType();
                if (inputType.equals(RDSDictionaryList.INPUT_TYPE_SESSION)) {
                    notSessionCount--;
                }
            }
        }
        return notSessionCount;
    }

    public StringBuilder getParameterHTML() throws QueryException {
        StringBuilder parameterHTML = new StringBuilder();
        if (parameters != null && !parameters.isEmpty()) {
            parameterHTML.append(getStartTable());
            int startTrTimes = 0;
            int endTrTimes = 0;
            for (int i = 0; i < parameters.size(); i++) {
                ReportParameterFrm parameter = parameters.get(i);
                String inputType = parameter.getInputType();
                if (inputType.equals(RDSDictionaryList.INPUT_TYPE_SESSION)) {
                    continue;
                }
                if (currentRowColumns % defaultRowColumns == 0) {
                    parameterHTML.append(getStartTr());
                    startTrTimes++;
                }
                parameterHTML.append(getParameterHTML(parameter));
                if (currentRowColumns % defaultRowColumns == 0 && currentRowColumns > 0) {
                    parameterHTML.append(getEndTr());
                    endTrTimes++;
                }
            }
            if (startTrTimes > endTrTimes) {
                parameterHTML.append(appendTd());
                parameterHTML.append(getEndTr());
            }
            parameterHTML.append(getEndTable());
        }
        return parameterHTML;
    }

    private StringBuilder getStartTable() {
        StringBuilder tableHTML = new StringBuilder();
        int searchDIVTop = defaultRowHeight * 2;
        tableHTML.append("<div id=\"searchDiv\" style=\"position:absolute;top:");
        tableHTML.append(searchDIVTop);
        tableHTML.append("px;height:");
        tableHTML.append(searchDIVHeight);
        tableHTML.append("px;left:1px;width:100%\">");
        tableHTML.append("<table  border=\"0\" width=\"100%\" id=\"searchTable\" class=\"queryHeadBg\">");
        return tableHTML;
    }

    private StringBuilder getStartTr() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append("<tr style=\"height:");
        tableHTML.append(defaultRowHeight);
        tableHTML.append("px\">");
        currentRowColumns = 0;

        return tableHTML;
    }

    private StringBuilder getEndTr() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append("</tr>");
        return tableHTML;
    }

    private StringBuilder getStartTd() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append("<td width=\"");
        tableHTML.append(tdWidth);
        tableHTML.append("%\" align=\"right\">");
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);

        currentRowColumns++;

        return tableHTML;
    }

    private StringBuilder appendTd() {
        StringBuilder tdHTML = new StringBuilder();
        while (currentRowColumns < defaultRowColumns) {
            tdHTML.append(getStartTd());
            tdHTML.append(getEndTd());
        }
        return tdHTML;
    }

    private StringBuilder getEndTd() {
        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append(WorldConstant.ENTER_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append(WorldConstant.TAB_CHAR);
        tableHTML.append("</td>");
        return tableHTML;
    }

    private StringBuilder getParameterHTML(ReportParameterFrm parameter) throws QueryException {
        StringBuilder parameterHTML = new StringBuilder();
        String inputType = parameter.getInputType();
        if (inputType.equals(RDSDictionaryList.INPUT_TYPE_TEXT)) {
            parameterHTML = getTextParameter(parameter);
        } else if (inputType.equals(RDSDictionaryList.INPUT_TYPE_CALENDAR)) {
            parameterHTML = getCalendarParameter(parameter);
        } else if (inputType.equals(RDSDictionaryList.INPUT_TYPE_LOV)) {
            parameterHTML = getLOVParameter(parameter);
        } else if (inputType.equals(RDSDictionaryList.INPUT_TYPE_LOOKUP)) {
            parameterHTML = getLookUpParameter(parameter);
        } else if (inputType.equals(RDSDictionaryList.INPUT_TYPE_URL)) {
            parameterHTML = getURLParameter(parameter);
        } else if (inputType.equals(RDSDictionaryList.INPUT_TYPE_HIDDEN)) {
            parameterHTML = getHiddenParameter(parameter);
        }
        return parameterHTML;
    }

    private StringBuilder getTextParameter(ReportParameterFrm parameter) {
        StringBuilder parameterHTML = new StringBuilder();
        String fieldName = parameter.getFieldName();
        fieldName = StrUtil.getJavaField(fieldName);
        String fieldAlign = parameter.getFieldAlign();
        String nullAble = parameter.getNullAble();

        parameterHTML.append(getStartTd());
        if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
            parameterHTML.append("<font color=\"red\">*</font>");
        }
        parameterHTML.append(parameter.getFieldDesc());
        parameterHTML.append("：");
        parameterHTML.append(getEndTd());
        parameterHTML.append(getStartTd());
        parameterHTML.append("<input type=\"text\" fieldLabel=\"");
        parameterHTML.append(parameter.getFieldDesc());
        parameterHTML.append("\" name=\"");
        parameterHTML.append(fieldName);
        parameterHTML.append("\"");
        if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
            parameterHTML.append(" required=\"true\"");
        }
        parameterHTML.append(" id=\"");
        parameterHTML.append(fieldName);
        parameterHTML.append("\" style=\"width:100%;text-align:");
        parameterHTML.append(fieldAlign);
        parameterHTML.append("\"");

        SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
        parameterHTML.append(" value=\"");
        if(frm.getAct().equals("")){
            parameterHTML.append(parameter.getDefaultValue());
        } else {
            parameterHTML.append(frm.getParameter(fieldName));
        }
        parameterHTML.append("\">");
        parameterHTML.append(getEndTd());
        return parameterHTML;
    }

    private StringBuilder getHiddenParameter(ReportParameterFrm parameter) {
        StringBuilder parameterHTML = new StringBuilder();
        String fieldName = parameter.getFieldName();
        fieldName = StrUtil.getJavaField(fieldName);
        String nullAble = parameter.getNullAble();

        parameterHTML.append(getStartTd());
        parameterHTML.append(getEndTd());
        parameterHTML.append(getStartTd());
        parameterHTML.append("<input type=\"hidden\" fieldLabel=\"");
        parameterHTML.append(parameter.getFieldDesc());
        parameterHTML.append("\" name=\"");
        parameterHTML.append(fieldName);
        parameterHTML.append("\"");
        if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
            parameterHTML.append(" required=\"true\"");
        }
        parameterHTML.append(" id=\"");
        parameterHTML.append(fieldName);
        parameterHTML.append("\"");

        SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
        parameterHTML.append(" value=\"");
        if(frm.getAct().equals("")){
            parameterHTML.append(parameter.getDefaultValue());
        } else {
            parameterHTML.append(frm.getParameter(fieldName));
        }
        parameterHTML.append("\">");
        parameterHTML.append(getEndTd());
        return parameterHTML;
    }

    private StringBuilder getCalendarParameter(ReportParameterFrm parameter) {
        StringBuilder parameterHTML = new StringBuilder();
        String fieldName = parameter.getFieldName();
        String startDate = "START_" + fieldName;
        startDate = StrUtil.getJavaField(startDate);
        String endDate = "END_" + fieldName;
        endDate = StrUtil.getJavaField(endDate);
        String fieldAlign = parameter.getFieldAlign();
        String nullAble = parameter.getNullAble();

        parameterHTML.append(getStartTd());

        if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
            parameterHTML.append("<font color=\"red\">*</font>");
        }
        parameterHTML.append(parameter.getFieldDesc());
        parameterHTML.append("：");
        parameterHTML.append(getEndTd());

        SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;

        String calendarType = parameter.getCalendarType();
        if (calendarType.equals(RDSDictionaryList.CALENDAR_TYPE_START)) {
            parameterHTML.append(getStartTd());

            parameterHTML.append("<input type=\"text\" fieldLabel=\"");
            parameterHTML.append(parameter.getFieldDesc());
            parameterHTML.append("\" name=\"");
            parameterHTML.append(startDate);
            parameterHTML.append("\"");
            if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
                parameterHTML.append(" required=\"true\"");
            }
            parameterHTML.append(" id=\"");
            parameterHTML.append(startDate);
            parameterHTML.append("\" title=\"点击选择“开始日期”\" onclick=\"gfPop.fPopCalendar(");
            parameterHTML.append(startDate);
            parameterHTML.append(");\" style=\"width:100%;text-align:");
            parameterHTML.append(fieldAlign);
            parameterHTML.append(";cursor:pointer\"");

            parameterHTML.append(" value=\"");
            if(frm.getAct().equals("")){
                parameterHTML.append(parameter.getDefaultValue());
            } else {
                parameterHTML.append(frm.getParameter(startDate));
            }
            parameterHTML.append("\">");

            parameterHTML.append(getEndTd());
        } else if (calendarType.equals(RDSDictionaryList.CALENDAR_TYPE_END)) {
            parameterHTML.append(getStartTd());
            parameterHTML.append("<input type=\"text\" fieldLabel=\"");
            parameterHTML.append(parameter.getFieldDesc());
            parameterHTML.append("\" name=\"");
            parameterHTML.append(endDate);
            parameterHTML.append("\"");

            if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
                parameterHTML.append(" required=\"true\"");
            }
            parameterHTML.append(" id=\"");
            parameterHTML.append(endDate);
            parameterHTML.append("\" title=\"点击选择“截止日期”\" onclick=\"gfPop.fPopCalendar(");
            parameterHTML.append(endDate);
            parameterHTML.append(");\" style=\"width:100%;text-align:");
            parameterHTML.append(fieldAlign);
            parameterHTML.append(";cursor:pointer\"");
            parameterHTML.append(" value=\"");
            if(frm.getAct().equals("")){
                parameterHTML.append(parameter.getDefaultValue());
            } else {
                parameterHTML.append(frm.getParameter(endDate));
            }
            parameterHTML.append("\">");

            parameterHTML.append(getEndTd());
        } else {
            parameterHTML.append(getStartTd());
            parameterHTML.append("<input type=\"text\" fieldLabel=\"");
            parameterHTML.append(parameter.getFieldDesc());
            parameterHTML.append("\" name=\"");
            parameterHTML.append(startDate);
            parameterHTML.append("\"");
            if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
                parameterHTML.append(" required=\"true\"");
            }
            parameterHTML.append(" id=\"");
            parameterHTML.append(startDate);
            parameterHTML.append("\" title=\"点击选择“开始日期”\" onclick=\"gfPop.fStartPop(");
            parameterHTML.append(startDate);
            parameterHTML.append(", ");
            parameterHTML.append(endDate);
            parameterHTML.append(");\" style=\"width:100%;text-align:");
            parameterHTML.append(fieldAlign);
            parameterHTML.append(";cursor:pointer\"");

            parameterHTML.append(" value=\"");
            parameterHTML.append(frm.getParameter(startDate));
            parameterHTML.append("\">");

            parameterHTML.append(getEndTd());
            parameterHTML.append(getStartTd());

            parameterHTML.append("到：");
            parameterHTML.append(getEndTd());
            parameterHTML.append(getStartTd());

            parameterHTML.append("<input type=\"text\" fieldLabel=\"");
            parameterHTML.append(parameter.getFieldDesc());
            parameterHTML.append("\" name=\"");
            parameterHTML.append(endDate);
            parameterHTML.append("\"");

            if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
                parameterHTML.append(" required=\"true\"");
            }
            parameterHTML.append(" id=\"");
            parameterHTML.append(endDate);
            parameterHTML.append("\" title=\"点击选择“截止日期”\" onclick=\"gfPop.fEndPop(");
            parameterHTML.append(startDate);
            parameterHTML.append(", ");
            parameterHTML.append(endDate);
            parameterHTML.append(");\" style=\"width:100%;text-align:");
            parameterHTML.append(fieldAlign);
            parameterHTML.append(";cursor:pointer\"");
            parameterHTML.append(" value=\"");
            parameterHTML.append(frm.getParameter(endDate));
            parameterHTML.append("\">");

            parameterHTML.append(getEndTd());
        }
        return parameterHTML;
    }

    private StringBuilder getLOVParameter(ReportParameterFrm parameter) throws QueryException {
        StringBuilder parameterHTML = new StringBuilder();
        String fieldName = parameter.getFieldName();
        fieldName = StrUtil.getJavaField(fieldName);
        String fieldAlign = parameter.getFieldAlign();
        String nullAble = parameter.getNullAble();

        parameterHTML.append(getStartTd());

        if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
            parameterHTML.append("<font color=\"red\">*</font>");
        }
        parameterHTML.append(parameter.getFieldDesc());
        parameterHTML.append("：");

        parameterHTML.append(getEndTd());
        parameterHTML.append(getStartTd());

        parameterHTML.append("<select fieldLabel=\"");
        parameterHTML.append(parameter.getFieldDesc());
        parameterHTML.append("\" name=\"");
        parameterHTML.append(fieldName);
        parameterHTML.append("\"");

        if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
            parameterHTML.append(" required=\"true\"");
        }
        parameterHTML.append(" id=\"");
        parameterHTML.append(fieldName);
        parameterHTML.append("\" style=\"width:100%;text-align:");
        parameterHTML.append(fieldAlign);
        parameterHTML.append("\">");
        parameterHTML.append(getLOVOption(parameter));
        parameterHTML.append("</select>");

        parameterHTML.append(getEndTd());

        return parameterHTML;
    }

    private String getLOVOption(ReportParameterFrm parameter) throws QueryException {
        String parameterHTML = "";
        try {
            LovDefineFrm lovDefineFrm = new LovDefineFrm();
            lovDefineFrm.setLovId(parameter.getLovId());
            LovDefineDAO lovDefineDAO = new LovDefineDAO(userAccount, lovDefineFrm, conn);
            lovDefineFrm = lovDefineDAO.searchDTOByPrimaryKey();
            String lovType = lovDefineFrm.getLovType();
            if (lovType.equals(RDSDictionaryList.LOV_TYPE_SQL)) {
                parameterHTML = getSQLOptions(lovDefineFrm, parameter);
            } else if (lovType.equals(RDSDictionaryList.LOV_TYPE_CONS)) {
                parameterHTML = getConstantOptions(lovDefineFrm, parameter);
            } else if (lovType.equals(RDSDictionaryList.LOV_TYPE_ACCOUNT)) {
                parameterHTML = getAccountPeriodOptions(lovDefineFrm, parameter);
            }
        } catch (ReflectException ex) {
            ex.printLog();
            throw new QueryException(ex);
        } catch (WebException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return parameterHTML;
    }

    private String getSQLOptions(LovDefineFrm lovDefineFrm, ReportParameterFrm parameter) throws WebException, ReflectException {
        String lovSQL = lovDefineFrm.getLovSql();
        SQLModel sqlModel = processLOVSQL(lovSQL);
        return produceLOVOption(sqlModel, lovDefineFrm, parameter);
    }

    private SQLModel processLOVSQL(String lovSQL) throws ReflectException {
        SQLModel sqlModel = new SQLModel();
        if (lovSQL.indexOf("{") > 0) {
            int containNumber = StrUtil.containNum(lovSQL, "{");
            String parameterStr = "";
            List<String> sqlArgs = new ArrayList<String>();
            for (int i = 0; i < containNumber; i++) {
                int existTime = i + 1;
                parameterStr = StrUtil.getBetwStr(lovSQL, "{", existTime, "}", existTime);
                parameterStr = parameterStr.trim();
                int index = parameterStr.indexOf(RDSDictionaryList.SESSION_PREFIX);
                if (index == 0) {//如果是Session变量，则需要检查用户类中是否含有指定属性
                    String userProp = parameterStr.substring(RDSDictionaryList.SESSION_PREFIX.length());
                    if (RDSConstantConfigManager.contains(userProp)) {
                        parameterStr = "{" + parameterStr + "}";
                        lovSQL = StrUtil.replaceFirst(lovSQL, parameterStr, "?");
                        sqlArgs.add(StrUtil.nullToString(ReflectionUtil.getProperty(userAccount, userProp)));
                        i--;
                    }
                }
            }
            sqlModel.setArgs(sqlArgs);
        }
        sqlModel.setSqlStr(lovSQL);
        return sqlModel;
    }


    /**
     * @param sqlModel     查询SQLModel
     * @param lovDefineFrm LOV定义对象
     * @param parameter    查询字段对应的参数对象
     * @return 返回下拉框列表
     * @throws com.sino.rds.foundation.exception.WebException
     *          构造下拉框列表出错时抛出该异常。
     */
    private String produceLOVOption(SQLModel sqlModel, LovDefineFrm lovDefineFrm, ReportParameterFrm parameter) throws WebException {
        String optionHTML = "";
        Connection dataSource = null;
        try {
            SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
            String fieldName = parameter.getFieldName();
            fieldName = StrUtil.getJavaField(fieldName);
            String fieldValue = frm.getParameter(fieldName);
            if(frm.getAct().equals("")){
                fieldValue = parameter.getDefaultValue();
            }
            OptionProduceRule optRule = new OptionProduceRule();
            dataSource = getDataSource(lovDefineFrm);
            optRule.setConnection(dataSource);
            optRule.setDataSource(sqlModel);
            optRule.setSelectedValue(fieldValue);
            OptionProducer optProducer = OptionProducerFactory.getOptionProducer(optRule);
            WebOptions options = optProducer.getOptions();
            if (options != null) {
                if(options.getSize() > 1){
                    if(lovDefineFrm.getAddBlank().equals(RDSDictionaryList.TRUE_VALUE)){
                        options.addBlankOption();
                    }
                }
                optionHTML = options.toString();
            }
        } catch (DatabaseException ex) {
            ex.printLog();
            throw new WebException(ex);
        } finally {
            try {
                if (dataSource != null) {
                    dataSource.close();
                }
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return optionHTML;
    }

    private Connection getDataSource(LovDefineFrm lovDefineFrm) throws DatabaseException {
        Connection dataSource = null;
        try {
            DBConnectionFrm dbcFrm = new DBConnectionFrm();
            dbcFrm.setConnectionId(lovDefineFrm.getConnectionId());
            DBConnectionDAO connectionDAO = new DBConnectionDAO(userAccount, dbcFrm, conn);
            dbcFrm = connectionDAO.searchDTOByPrimaryKey();
            dataSource = dbcFrm.getDBConnection();
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DatabaseException(ex.getMessage());
        }
        return dataSource;
    }

    /**
     * @param lovDefineFrm LOV定义对象
     * @param parameter    查询字段对应的参数对象
     * @return 返回下拉框列表
     */
    private String getConstantOptions(LovDefineFrm lovDefineFrm, ReportParameterFrm parameter) {
        StringBuilder optionHTML = new StringBuilder();
        String lovValue = lovDefineFrm.getLovValue();
        if (!StrUtil.isEmpty(lovValue)) {
            SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
            String fieldName = parameter.getFieldName();
            fieldName = StrUtil.getJavaField(fieldName);
            String fieldValue = frm.getParameter(fieldName);
            if(frm.getAct().equals("")){
                fieldValue = parameter.getDefaultValue();
            }
            if (lovDefineFrm.getAddBlank().equals(RDSDictionaryList.TRUE_VALUE)) {
                optionHTML.append("<option value=\"\">----请选择----</option>");
            }
            String[] lovArr = StrUtil.splitStr(lovValue, "&");
            for (String valueLabel : lovArr) {
                int index = valueLabel.indexOf("=");
                if (index <= 0) {
                    continue;
                }
                String value = valueLabel.substring(0, index);
                String label = valueLabel.substring(index + 1);
                optionHTML.append("<option value=\"");
                optionHTML.append(value);
                optionHTML.append("\"");
                if (value.equals(fieldValue)) {
                    optionHTML.append(" selected");
                }
                optionHTML.append(">");
                optionHTML.append(label);
                optionHTML.append("</option>");
            }
        }
        return optionHTML.toString();
    }

    /**
     * @param lovDefineFrm LOV定义对象
     * @param parameter    查询字段对应的参数对象
     * @return 返回下拉框列表
     */
    private String getAccountPeriodOptions(LovDefineFrm lovDefineFrm, ReportParameterFrm parameter) {
        StringBuilder optionHTML = new StringBuilder();
        SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
        String fieldName = parameter.getFieldName();
        fieldName = StrUtil.getJavaField(fieldName);
        String fieldValue = frm.getParameter(fieldName);
        if(frm.getAct().equals("")){
            fieldValue = parameter.getDefaultValue();
            if(fieldValue.equals("")){
                fieldValue = CalendarUtil.getCurrCalendar();
                fieldValue = fieldValue.substring(0, 7);
            }
        }
        if (lovDefineFrm.getAddBlank().equals(RDSDictionaryList.TRUE_VALUE)) {
            optionHTML.append("<option value=\"\">----请选择----</option>");
        }
        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        int startYear = currYear - 3;
        for(int i = startYear; i<= currYear; i++){
            String yearPart = String.valueOf(i);
            for(int j = 1; j <= 12; j++){
                String monthPart = String.valueOf(j);
                if(j < 10){
                    monthPart = "0" + monthPart;
                }
                String accountPeriod = yearPart + "-" + monthPart;
                optionHTML.append("<option value=\"");
                optionHTML.append(accountPeriod);
                optionHTML.append("\"");
                if(accountPeriod.equals(fieldValue)){
                    optionHTML.append(" selected");
                }
                optionHTML.append(">");
                optionHTML.append(accountPeriod);
                optionHTML.append("</option>");
            }
        }
        return optionHTML.toString();
    }


    private StringBuilder getLookUpParameter(ReportParameterFrm parameter) {
        StringBuilder parameterHTML = new StringBuilder();
        String fieldName = parameter.getFieldName();
        fieldName = StrUtil.getJavaField(fieldName);
        SearchParameterFrm frm = (SearchParameterFrm) dtoParameter;
        String fieldAlign = parameter.getFieldAlign();
        String nullAble = parameter.getNullAble();
        parameterHTML.append(getStartTd());
        if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
            parameterHTML.append("<font color=\"red\">*</font>");
        }
        parameterHTML.append(parameter.getFieldDesc());
        parameterHTML.append("：");
        parameterHTML.append(getEndTd());
        parameterHTML.append(getStartTd());
        parameterHTML.append("<input type=\"text\" fieldLabel=\"");
        parameterHTML.append(parameter.getFieldDesc());
        parameterHTML.append("\" name=\"");
        parameterHTML.append(fieldName);
        if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
            parameterHTML.append(" required=\"true\"");
        }
        parameterHTML.append("\"");
        parameterHTML.append(" id=\"");
        parameterHTML.append(fieldName);
        parameterHTML.append("\" readonly=\"true\" style=\"width:100%;text-align:");
        parameterHTML.append(fieldAlign);
        parameterHTML.append("; cursor:pointer\" title=\"点击选择“");
        parameterHTML.append(parameter.getFieldDesc());
        parameterHTML.append("”\" onclick=\"do_Lookup('");
        parameterHTML.append(parameter.getLookUpId());
        parameterHTML.append("', '");
        parameterHTML.append(parameter.getLookUpCode());
        parameterHTML.append("')\" value=\"");
        if(frm.getAct().equals("")){
            parameterHTML.append(parameter.getDefaultValue());
        } else {
            parameterHTML.append(frm.getParameter(fieldName));
        }
        parameterHTML.append("\">");
        parameterHTML.append(getEndTd());

        return parameterHTML;
    }

    private StringBuilder getURLParameter(ReportParameterFrm parameter) {
        StringBuilder parameterHTML = new StringBuilder();
        String fieldName = parameter.getFieldName();
        fieldName = StrUtil.getJavaField(fieldName);
        String fieldAlign = parameter.getFieldAlign();
        String nullAble = parameter.getNullAble();

        parameterHTML.append(getStartTd());
        if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
            parameterHTML.append("<font color=\"red\">*</font>");
        }
        parameterHTML.append(parameter.getFieldDesc());
        parameterHTML.append("：");
        parameterHTML.append(getEndTd());
        parameterHTML.append(getStartTd());
        parameterHTML.append("<input type=\"text\" name=\"");
        parameterHTML.append(fieldName);
        parameterHTML.append("\"");

        if (nullAble.equals(RDSDictionaryList.FALSE_VALUE)) {
            parameterHTML.append(" required=\"true\"");
        }
        parameterHTML.append(" id=\"");
        parameterHTML.append(fieldName);
        parameterHTML.append("\" style=\"width:100%;text-align:");
        parameterHTML.append(fieldAlign);

        parameterHTML.append("\" onclick=\"do_ProcessURLParameter('");
        parameterHTML.append(parameter.getParameterUrl());
        parameterHTML.append("')\">");

        parameterHTML.append(getEndTd());

        return parameterHTML;
    }

    private StringBuilder getEndTable() {
        StringBuilder resultHTML = new StringBuilder();
        resultHTML.append(WorldConstant.TAB_CHAR);
        resultHTML.append("</table>");
        resultHTML.append("</div>");
        return resultHTML;
    }
}