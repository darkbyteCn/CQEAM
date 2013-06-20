package com.sino.rds.share.form;

import com.sino.base.ExtendedArrayList;
import com.sino.rds.appbase.form.RDSBaseFrm;
import com.sino.rds.foundation.web.component.WebOptions;

import java.util.List;

public class ReportViewProcessFrm extends RDSBaseFrm {
    private ReportDefineFrm report = null;
    private List<ReportViewFrm> dataFields = new ExtendedArrayList(ReportViewFrm.class);
    private String availableField = null;
    private String alreadyField = null;
    private WebOptions availableFieldsOptions = null;
    private WebOptions alreadyFieldsOptions = null;
    private WebOptions reportOptions = null;
    private String reportId = "";
    private String fieldIds = "";
    private String viewIds = "";
    private WebOptions datePatternOptions = null;
    private WebOptions numberPatternOptions = null;
    private WebOptions fieldAlignOptions = null;
    private WebOptions backGroundToOptions = null;
    private WebOptions computeExpressionOptions = null;

    private String referenceFieldAlign = "";
    private String referenceDatePattern = "";
    private String referenceNumberPattern = "";
    private String referenceEnabled = "";
    private String referenceBackGroundTo = "";
    private String referenceComputeExpression = "";

    public ReportViewProcessFrm() {
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

    public List<ReportViewFrm> getDataFields() {
        return dataFields;
    }

    public void setDataFields(List<ReportViewFrm> dataFields) {
        this.dataFields = dataFields;
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

    public String getAlreadyField() {
        return alreadyField;
    }

    public void setAlreadyField(String alreadyField) {
        this.alreadyField = alreadyField;
    }

    public WebOptions getAvailableFieldsOptions() {
        return availableFieldsOptions;
    }

    public void setAvailableFieldsOptions(WebOptions availableFieldsOptions) {
        this.availableFieldsOptions = availableFieldsOptions;
    }

    public WebOptions getAlreadyFieldsOptions() {
        return alreadyFieldsOptions;
    }

    public void setAlreadyFieldsOptions(WebOptions alreadyFieldsOptions) {
        this.alreadyFieldsOptions = alreadyFieldsOptions;
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

    public WebOptions getDatePatternOptions() {
        return datePatternOptions;
    }

    public void setDatePatternOptions(WebOptions datePatternOptions) {
        this.datePatternOptions = datePatternOptions;
    }

    public WebOptions getNumberPatternOptions() {
        return numberPatternOptions;
    }

    public void setNumberPatternOptions(WebOptions numberPatternOptions) {
        this.numberPatternOptions = numberPatternOptions;
    }

    public WebOptions getFieldAlignOptions() {
        return fieldAlignOptions;
    }

    public void setFieldAlignOptions(WebOptions fieldAlignOptions) {
        this.fieldAlignOptions = fieldAlignOptions;
    }

    public WebOptions getBackGroundToOptions() {
        return backGroundToOptions;
    }

    public void setBackGroundToOptions(WebOptions backGroundToOptions) {
        this.backGroundToOptions = backGroundToOptions;
    }

    public String getReferenceDatePattern() {
        return referenceDatePattern;
    }

    public void setReferenceDatePattern(String referenceDatePattern) {
        this.referenceDatePattern = referenceDatePattern;
    }

    public String getReferenceNumberPattern() {
        return referenceNumberPattern;
    }

    public void setReferenceNumberPattern(String referenceNumberPattern) {
        this.referenceNumberPattern = referenceNumberPattern;
    }

    public String getReferenceEnabled() {
        return referenceEnabled;
    }

    public void setReferenceEnabled(String referenceEnabled) {
        this.referenceEnabled = referenceEnabled;
    }

    public String getReferenceBackGroundTo() {
        return referenceBackGroundTo;
    }

    public void setReferenceBackGroundTo(String referenceBackGroundTo) {
        this.referenceBackGroundTo = referenceBackGroundTo;
    }

    public String getReferenceFieldAlign() {
        return referenceFieldAlign;
    }

    public void setReferenceFieldAlign(String referenceFieldAlign) {
        this.referenceFieldAlign = referenceFieldAlign;
    }

    public String getViewIds() {
        return viewIds;
    }

    public void setViewIds(String viewIds) {
        this.viewIds = viewIds;
    }

    public WebOptions getComputeExpressionOptions() {
        return computeExpressionOptions;
    }

    public void setComputeExpressionOptions(WebOptions computeExpressionOptions) {
        this.computeExpressionOptions = computeExpressionOptions;
    }

    public String getReferenceComputeExpression() {
        return referenceComputeExpression;
    }

    public void setReferenceComputeExpression(String referenceComputeExpression) {
        this.referenceComputeExpression = referenceComputeExpression;
    }
}