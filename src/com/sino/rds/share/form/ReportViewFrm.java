package com.sino.rds.share.form;

import com.sino.rds.foundation.web.component.WebOptions;


/**
 * <p>Title: 报表页面展示 RDS_REPORT_VIEW</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class ReportViewFrm extends ModelFieldFrm {
    private String viewId = "";
    private String viewIds = "";
    private String reportId = "";
    private String reportCode = "";
    private String reportName = "";
    private String fieldId = "";
    private String fieldAlignName = "";
    private String dataSrcType = "";
    private String dataPattern = "";
    private String dotNumber = "";
    private String fieldAlign = "";
    private String fieldWidth = "";
    private String sortNumber = "";
    private String computeExpression = "";
    private String computeExpressionName = "";
    private String fieldIds = "";

    private WebOptions fieldAlignOptions = null;
    private WebOptions dataPatternOptions = null;
    private WebOptions dataSrcTypeOptions = null;
    private WebOptions reportOptions = null;
    private WebOptions computeExpressionOptions = null;

    public ReportViewFrm() {
        super();
        primaryKeyName = "viewId";
    }

    /**
     * 功能：设置报表页面展示字段属性定义 展示定义ID
     *
     * @param viewId String
     */
    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    /**
     * 功能：设置报表页面展示字段属性定义 报表字段
     *
     * @param fieldId String
     */
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 功能：设置报表页面展示字段属性定义 字段宽度
     *
     * @param fieldWidth String
     */
    public void setFieldWidth(String fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    /**
     * 功能：设置报表页面展示字段属性定义 横向布局
     *
     * @param fieldAlign String
     */
    public void setFieldAlign(String fieldAlign) {
        this.fieldAlign = fieldAlign;
    }

    /**
     * 功能：设置报表页面展示字段属性定义 数据来源类型（结果集字段，结果集字段计算表达式）
     *
     * @param dataSrcType String
     */
    public void setDataSrcType(String dataSrcType) {
        this.dataSrcType = dataSrcType;
    }

    /**
     * 功能：设置报表页面展示字段属性定义 计算表达式
     *
     * @param computeExpression String
     */
    public void setComputeExpression(String computeExpression) {
        this.computeExpression = computeExpression;
    }

    /**
     * 功能：获取报表页面展示字段属性定义 展示定义ID
     *
     * @return String
     */
    public String getViewId() {
        return this.viewId;
    }

    /**
     * 功能：获取报表页面展示字段属性定义 报表字段
     *
     * @return String
     */
    public String getFieldId() {
        return this.fieldId;
    }

    /**
     * 功能：获取报表页面展示字段属性定义 字段宽度
     *
     * @return String
     */
    public String getFieldWidth() {
        return this.fieldWidth;
    }

    /**
     * 功能：获取报表页面展示字段属性定义 横向布局
     *
     * @return String
     */
    public String getFieldAlign() {
        return this.fieldAlign;
    }


    /**
     * 功能：获取报表页面展示字段属性定义 数据来源类型（结果集字段，结果集字段计算表达式）
     *
     * @return String
     */
    public String getDataSrcType() {
        return this.dataSrcType;
    }

    /**
     * 功能：获取报表页面展示字段属性定义 计算表达式
     *
     * @return String
     */
    public String getComputeExpression() {
        return this.computeExpression;
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

    public WebOptions getDataSrcTypeOptions() {
        return dataSrcTypeOptions;
    }

    public void setDataSrcTypeOptions(WebOptions dataSrcTypeOptions) {
        this.dataSrcTypeOptions = dataSrcTypeOptions;
    }

    public String getDataPattern() {
        return dataPattern;
    }

    public void setDataPattern(String dataPattern) {
        this.dataPattern = dataPattern;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getViewIds() {
        return viewIds;
    }

    public void setViewIds(String viewIds) {
        this.viewIds = viewIds;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public WebOptions getReportOptions() {
        return reportOptions;
    }

    public void setReportOptions(WebOptions reportOptions) {
        this.reportOptions = reportOptions;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public WebOptions getDataPatternOptions() {
        return dataPatternOptions;
    }

    public void setDataPatternOptions(WebOptions dataPatternOptions) {
        this.dataPatternOptions = dataPatternOptions;
    }

    public String getFieldIds() {
        return fieldIds;
    }

    public void setFieldIds(String fieldIds) {
        this.fieldIds = fieldIds;
    }

    public String getDotNumber() {
        return dotNumber;
    }

    public void setDotNumber(String dotNumber) {
        this.dotNumber = dotNumber;
    }

    public String getComputeExpressionName() {
        return computeExpressionName;
    }

    public void setComputeExpressionName(String computeExpressionName) {
        this.computeExpressionName = computeExpressionName;
    }

    public WebOptions getComputeExpressionOptions() {
        return computeExpressionOptions;
    }

    public void setComputeExpressionOptions(WebOptions computeExpressionOptions) {
        this.computeExpressionOptions = computeExpressionOptions;
    }
}