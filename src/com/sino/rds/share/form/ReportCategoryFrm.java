package com.sino.rds.share.form;

import com.sino.rds.foundation.web.component.WebOptions;


/**
 * <p>Title: 报表分组字段定义 RDS_REPORT_CATEGORY</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class ReportCategoryFrm extends ModelFieldFrm {
    private String categoryId = "";
    private String categoryIds = "";
    private String categoryName = "";
    private String reportId = "";
    private String reportCode = "";
    private String reportName = "";
    private String fieldId = "";
    private String fieldIds = "";
    private String categoryLevel = "";
    private String viewLocation = "";
    private String viewLocationName = "";
    private String fieldWidth = "";
    private String fieldAlign = "";
    private String fieldAlignName = "";
    private String summaryFlagName = "";
    private String displayFlag = "Y";
    private String displayFlagName = "";
    private WebOptions displayFlagOptions = null;
    private WebOptions reportOptions = null;
    private WebOptions fieldAlignOptions = null;
    private String viewIds = "";

    public ReportCategoryFrm() {
        super();
        primaryKeyName = "categoryId";
    }

    /**
     * 功能：设置报表分组字段定义属性 分组ID
     *
     * @param categoryId String
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 功能：设置报表分组字段定义属性 分组名称
     *
     * @param categoryName String
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 功能：设置报表分组字段定义属性 报表ID
     *
     * @param reportId String
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * 功能：设置报表分组字段定义属性 分组字段
     *
     * @param fieldId String
     */
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 功能：设置报表分组字段定义属性 分组层级
     *
     * @param categoryLevel String
     */
    public void setCategoryLevel(String categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    /**
     * 功能：设置报表分组字段定义属性 分组类型(纵向或横向，用于报表展示)
     *
     * @param viewLocation String
     */
    public void setViewLocation(String viewLocation) {
        this.viewLocation = viewLocation;
    }


    /**
     * 功能：获取报表分组字段定义属性 分组ID
     *
     * @return String
     */
    public String getCategoryId() {
        return this.categoryId;
    }

    /**
     * 功能：获取报表分组字段定义属性 分组名称
     *
     * @return String
     */
    public String getCategoryName() {
        return this.categoryName;
    }

    /**
     * 功能：获取报表分组字段定义属性 报表ID
     *
     * @return String
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * 功能：获取报表分组字段定义属性 分组字段
     *
     * @return String
     */
    public String getFieldId() {
        return this.fieldId;
    }

    /**
     * 功能：获取报表分组字段定义属性 分组层级
     *
     * @return String
     */
    public String getCategoryLevel() {
        return this.categoryLevel;
    }

    /**
     * 功能：获取报表分组字段定义属性 分组类型(纵向或横向，用于报表展示)
     *
     * @return String
     */
    public String getViewLocation() {
        return this.viewLocation;
    }

    public String getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(String fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public String getFieldAlign() {
        return fieldAlign;
    }

    public void setFieldAlign(String fieldAlign) {
        this.fieldAlign = fieldAlign;
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

    public String getFieldAlignName() {
        return fieldAlignName;
    }

    public void setFieldAlignName(String fieldAlignName) {
        this.fieldAlignName = fieldAlignName;
    }

    public String getViewLocationName() {
        return viewLocationName;
    }

    public void setViewLocationName(String viewLocationName) {
        this.viewLocationName = viewLocationName;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getSummaryFlagName() {
        return summaryFlagName;
    }

    public void setSummaryFlagName(String summaryFlagName) {
        this.summaryFlagName = summaryFlagName;
    }

    public WebOptions getReportOptions() {
        return reportOptions;
    }

    public void setReportOptions(WebOptions reportOptions) {
        this.reportOptions = reportOptions;
    }

    public String getFieldIds() {
        return fieldIds;
    }

    public void setFieldIds(String fieldIds) {
        this.fieldIds = fieldIds;
    }

    public WebOptions getFieldAlignOptions() {
        return fieldAlignOptions;
    }

    public void setFieldAlignOptions(WebOptions fieldAlignOptions) {
        this.fieldAlignOptions = fieldAlignOptions;
    }

    public String getDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(String displayFlag) {
        this.displayFlag = displayFlag;
    }

    public String getDisplayFlagName() {
        return displayFlagName;
    }

    public void setDisplayFlagName(String displayFlagName) {
        this.displayFlagName = displayFlagName;
    }

    public WebOptions getDisplayFlagOptions() {
        return displayFlagOptions;
    }

    public void setDisplayFlagOptions(WebOptions displayFlagOptions) {
        this.displayFlagOptions = displayFlagOptions;
    }

    public String getViewIds() {
        return viewIds;
    }

    public void setViewIds(String viewIds) {
        this.viewIds = viewIds;
    }
}