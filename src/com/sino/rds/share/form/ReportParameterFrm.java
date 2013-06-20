package com.sino.rds.share.form;

import com.sino.rds.foundation.web.component.WebOptions;
import com.sino.rds.foundation.web.component.WebRadio;


/**
 * <p>Title: 报表查询条件 RDS_REPORT_PARAMETER</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class ReportParameterFrm extends ModelFieldFrm {

    private String fieldAlignName = "";
    private String inputTypeName = "";
    private String nullAble = "";
    private String nullAbleName = "";
    private String sortNumber = "";
    private String reportId = "";
    private String defaultValue = "";
    private String reportCode = "";
    private String reportName = "";
    private String parameterUrl = "";
    private String lookUpName = "";
    private String lovId = "";
    private String lovName = "";
    private String inputType = "";
    private String fieldAlign = "";
    private String fieldWidth = "";
    private String fieldIds = "";
    private String parameterId = "";
    private String parameterIds = "";

    private WebOptions fieldAlignOptions = null;
    private WebOptions inputTypeOptions = null;
    private WebOptions nullAbleOptions = null;
    private WebRadio nullAbleRadio = null;
    private WebOptions reportOptions = null;
    private WebOptions lovOptions = null;
    private WebOptions lookUpOptions = null;

    private String calendarType = "";
    private String calendarTypeName = "";
    private WebOptions calendarTypeOptions = null;

    public ReportParameterFrm() {
        super();
        primaryKeyName = "parameterId";
    }

    /**
     * 功能：设置报表查询条件属性 参数ID
     *
     * @param parameterId String
     */
    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }

    /**
     * 功能：设置报表查询条件属性 字段宽度
     *
     * @param fieldWidth String
     */
    public void setFieldWidth(String fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    /**
     * 功能：设置报表查询条件属性 水平布局
     *
     * @param fieldAlign String
     */
    public void setFieldAlign(String fieldAlign) {
        this.fieldAlign = fieldAlign;
    }

    /**
     * 功能：设置报表查询条件属性 输入类型（1：普通；2：日历；3：LOV；4：LookUp；5：URL弹出窗口返回参数）
     *
     * @param inputType String
     */
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    /**
     * 功能：设置报表查询条件属性 值列表查询SQL
     *
     * @param lovId String
     */
    public void setLovId(String lovId) {
        this.lovId = lovId;
    }

    /**
     * 功能：设置报表查询条件属性 其他参数URL
     *
     * @param parameterUrl String
     */
    public void setParameterUrl(String parameterUrl) {
        this.parameterUrl = parameterUrl;
    }

    /**
     * 功能：设置报表查询条件属性 报表ID
     *
     * @param reportId String
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * 功能：获取报表查询条件属性 参数ID
     *
     * @return String
     */
    public String getParameterId() {
        return this.parameterId;
    }

    /**
     * 功能：获取报表查询条件属性 字段宽度
     *
     * @return String
     */
    public String getFieldWidth() {
        return this.fieldWidth;
    }

    /**
     * 功能：获取报表查询条件属性 水平布局
     *
     * @return String
     */
    public String getFieldAlign() {
        return this.fieldAlign;
    }

    /**
     * 功能：获取报表查询条件属性 输入类型（1：普通；2：日历；3：LOV；4：LookUp；5：URL弹出窗口返回参数）
     *
     * @return String
     */
    public String getInputType() {
        return this.inputType;
    }

    /**
     * 功能：获取报表查询条件属性 值列表查询SQL
     *
     * @return String
     */
    public String getLovId() {
        return this.lovId;
    }

    /**
     * 功能：获取报表查询条件属性 其他参数URL
     *
     * @return String
     */
    public String getParameterUrl() {
        return this.parameterUrl;
    }

    /**
     * 功能：获取报表查询条件属性 报表ID
     *
     * @return String
     */
    public String getReportId() {
        return this.reportId;
    }

    public String getFieldAlignName() {
        return fieldAlignName;
    }

    public void setFieldAlignName(String fieldAlignName) {
        this.fieldAlignName = fieldAlignName;
    }

    public WebOptions getFieldAlignOptions() {
        return fieldAlignOptions;
    }

    public void setFieldAlignOptions(WebOptions fieldAlignOptions) {
        this.fieldAlignOptions = fieldAlignOptions;
    }

    public String getInputTypeName() {
        return inputTypeName;
    }

    public void setInputTypeName(String inputTypeName) {
        this.inputTypeName = inputTypeName;
    }

    public WebOptions getInputTypeOptions() {
        return inputTypeOptions;
    }

    public void setInputTypeOptions(WebOptions inputTypeOptions) {
        this.inputTypeOptions = inputTypeOptions;
    }

    public String getNullAble() {
        return nullAble;
    }

    public void setNullAble(String nullAble) {
        this.nullAble = nullAble;
    }

    public String getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public WebOptions getNullAbleOptions() {
        return nullAbleOptions;
    }

    public void setNullAbleOptions(WebOptions nullAbleOptions) {
        this.nullAbleOptions = nullAbleOptions;
    }

    public WebOptions getReportOptions() {
        return reportOptions;
    }

    public void setReportOptions(WebOptions reportOptions) {
        this.reportOptions = reportOptions;
    }

    public String getParameterIds() {
        return parameterIds;
    }

    public void setParameterIds(String parameterIds) {
        this.parameterIds = parameterIds;
    }

    public String getFieldIds() {
        return fieldIds;
    }

    public void setFieldIds(String fieldIds) {
        this.fieldIds = fieldIds;
    }

    public WebRadio getNullAbleRadio() {
        return nullAbleRadio;
    }

    public void setNullAbleRadio(WebRadio nullAbleRadio) {
        this.nullAbleRadio = nullAbleRadio;
    }

    public String getLookUpName() {
        return lookUpName;
    }

    public void setLookUpName(String lookUpName) {
        this.lookUpName = lookUpName;
    }

    public String getLovName() {
        return lovName;
    }

    public void setLovName(String lovName) {
        this.lovName = lovName;
    }

    public WebOptions getLovOptions() {
        return lovOptions;
    }

    public void setLovOptions(WebOptions lovOptions) {
        this.lovOptions = lovOptions;
    }

    public WebOptions getLookUpOptions() {
        return lookUpOptions;
    }

    public void setLookUpOptions(WebOptions lookUpOptions) {
        this.lookUpOptions = lookUpOptions;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getNullAbleName() {
        return nullAbleName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setNullAbleName(String nullAbleName) {
        this.nullAbleName = nullAbleName;
    }

    public String getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(String calendarType) {
        this.calendarType = calendarType;
    }

    public String getCalendarTypeName() {
        return calendarTypeName;
    }

    public void setCalendarTypeName(String calendarTypeName) {
        this.calendarTypeName = calendarTypeName;
    }

    public WebOptions getCalendarTypeOptions() {
        return calendarTypeOptions;
    }

    public void setCalendarTypeOptions(WebOptions calendarTypeOptions) {
        this.calendarTypeOptions = calendarTypeOptions;
    }
}