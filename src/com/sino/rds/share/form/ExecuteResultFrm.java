package com.sino.rds.share.form;

public class ExecuteResultFrm {
    private String reportType = "";
    private String reportName = "";
    private String parameterHTML = "";
    private String reportDataHTML = "";
    private String hiddenHTML = "";
    private String leftCategorySize = "0";
    private String fromLookUp = "N";
    private String drillStartColumn = "-1";
    private String sumPosition = "";
    private String supportSubSummary = "";

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getParameterHTML() {
        return parameterHTML;
    }

    public void setParameterHTML(String parameterHTML) {
        this.parameterHTML = parameterHTML;
    }

    public String getReportDataHTML() {
        return reportDataHTML;
    }

    public void setReportDataHTML(String reportDataHTML) {
        this.reportDataHTML = reportDataHTML;
    }

    public String getHiddenHTML() {
        return hiddenHTML;
    }

    public void setHiddenHTML(String hiddenHTML) {
        this.hiddenHTML = hiddenHTML;
    }

    public String getLeftCategorySize() {
        return leftCategorySize;
    }

    public void setLeftCategorySize(String leftCategorySize) {
        this.leftCategorySize = leftCategorySize;
    }

    public String getFromLookUp() {
        return fromLookUp;
    }

    public void setFromLookUp(String fromLookUp) {
        this.fromLookUp = fromLookUp;
    }

    public String getDrillStartColumn() {
        return drillStartColumn;
    }

    public void setDrillStartColumn(String drillStartColumn) {
        this.drillStartColumn = drillStartColumn;
    }

    public String getSumPosition() {
        return sumPosition;
    }

    public void setSumPosition(String sumPosition) {
        this.sumPosition = sumPosition;
    }

    public String getSupportSubSummary() {
        return supportSubSummary;
    }

    public void setSupportSubSummary(String supportSubSummary) {
        this.supportSubSummary = supportSubSummary;
    }
}
