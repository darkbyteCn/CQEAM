package com.sino.rds.share.form;

import com.sino.base.ExtendedArrayList;
import com.sino.rds.appbase.form.RDSBaseFrm;
import com.sino.rds.foundation.web.component.WebOptions;

import java.util.List;

public class ReportCategoryProcessFrm extends RDSBaseFrm {
    private ReportDefineFrm report = null;
    private List<ReportCategoryFrm> categoryFields = new ExtendedArrayList(ReportCategoryFrm.class);
    private String availableField = null;
    private String aboveField = null;
    private String leftField = null;
    private WebOptions availableFieldsOptions = null;
    private WebOptions aboveFieldsOptions = null;
    private WebOptions leftFieldsOptions = null;
    private WebOptions reportOptions = null;
    private String reportId = "";
    private String fieldIds = "";
    private String categoryIds = "";
    private WebOptions fieldAlignOptions = null;
    private WebOptions displayFlagOptions = null;

    private String referenceEnabled = "";
    private String referenceDisplayFlag = "";

    public ReportCategoryProcessFrm() {
        primaryKeyName = "reportId";
    }

    public ReportDefineFrm getReport() {
        return report;
    }

    public void setReport(ReportDefineFrm report) {
        this.report = report;
        if(report != null){
            this.reportId = report.getReportId();
        }
    }

    public List<ReportCategoryFrm> getCategoryFields() {
        return categoryFields;
    }

    public void setCategoryFields(List<ReportCategoryFrm> categoryFields) {
        this.categoryFields = categoryFields;
    }

    public WebOptions getReportOptions() {
        return reportOptions;
    }

    public void setReportOptions(WebOptions reportOptions) {
        this.reportOptions = reportOptions;
    }

    public String getAvailableField() {
        return availableField;
    }

    public void setAvailableField(String availableField) {
        this.availableField = availableField;
    }

    public String getAboveField() {
        return aboveField;
    }

    public void setAboveField(String aboveField) {
        this.aboveField = aboveField;
    }

    public WebOptions getAvailableFieldsOptions() {
        return availableFieldsOptions;
    }

    public void setAvailableFieldsOptions(WebOptions availableFieldsOptions) {
        this.availableFieldsOptions = availableFieldsOptions;
    }

    public WebOptions getAboveFieldsOptions() {
        return aboveFieldsOptions;
    }

    public void setAboveFieldsOptions(WebOptions aboveFieldsOptions) {
        this.aboveFieldsOptions = aboveFieldsOptions;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
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

    public String getReferenceEnabled() {
        return referenceEnabled;
    }

    public void setReferenceEnabled(String referenceEnabled) {
        this.referenceEnabled = referenceEnabled;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public WebOptions getLeftFieldsOptions() {
        return leftFieldsOptions;
    }

    public void setLeftFieldsOptions(WebOptions leftFieldsOptions) {
        this.leftFieldsOptions = leftFieldsOptions;
    }

    public String getLeftField() {
        return leftField;
    }

    public void setLeftField(String leftField) {
        this.leftField = leftField;
    }

    public String getReferenceDisplayFlag() {
        return referenceDisplayFlag;
    }

    public void setReferenceDisplayFlag(String referenceDisplayFlag) {
        this.referenceDisplayFlag = referenceDisplayFlag;
    }

    public WebOptions getDisplayFlagOptions() {
        return displayFlagOptions;
    }

    public void setDisplayFlagOptions(WebOptions displayFlagOptions) {
        this.displayFlagOptions = displayFlagOptions;
    }
}