package com.sino.rds.share.form;

import com.sino.base.ExtendedArrayList;
import com.sino.rds.appbase.form.RDSBaseFrm;
import com.sino.rds.foundation.web.component.WebOptions;

import java.util.List;

public class ReportParameterProcessFrm extends RDSBaseFrm {
    private ReportDefineFrm report = null;
    private List<ReportParameterFrm> parameterFields = new ExtendedArrayList(ReportParameterFrm.class);
    private String availableField = null;
    private String alreadyField = null;
    private WebOptions availableFieldsOptions = null;
    private WebOptions alreadyFieldsOptions = null;
    private WebOptions reportOptions = null;
    private String reportId = "";
    private String fieldIds = "";
    private String parameterIds = "";

    private WebOptions inputTypeOptions = null;
    private WebOptions lovOptions = null;
    private WebOptions fieldAlignOptions = null;
    private WebOptions lookUpOptions = null;
    private WebOptions nullAbleOptions = null;
    private WebOptions calendarTypeOptions = null;

    private String referenceInputType = "";
    private String referenceLov = "";
    private String referenceFieldAlign = "";
    private String referenceLookUp = "";
    private String referenceNullAble = "";
    private String referenceEnabled = "";
    private String referenceCalendarType = "";

    public ReportParameterProcessFrm() {
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

    public List<ReportParameterFrm> getParameterFields() {
        return parameterFields;
    }

    public void setParameterFields(List<ReportParameterFrm> parameterFields) {
        this.parameterFields = parameterFields;
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

    public WebOptions getInputTypeOptions() {
        return inputTypeOptions;
    }

    public void setInputTypeOptions(WebOptions inputTypeOptions) {
        this.inputTypeOptions = inputTypeOptions;
    }

    public WebOptions getLovOptions() {
        return lovOptions;
    }

    public void setLovOptions(WebOptions lovOptions) {
        this.lovOptions = lovOptions;
    }

    public WebOptions getFieldAlignOptions() {
        return fieldAlignOptions;
    }

    public void setFieldAlignOptions(WebOptions fieldAlignOptions) {
        this.fieldAlignOptions = fieldAlignOptions;
    }

    public WebOptions getLookUpOptions() {
        return lookUpOptions;
    }

    public void setLookUpOptions(WebOptions lookUpOptions) {
        this.lookUpOptions = lookUpOptions;
    }

    public String getReferenceInputType() {
        return referenceInputType;
    }

    public void setReferenceInputType(String referenceInputType) {
        this.referenceInputType = referenceInputType;
    }

    public String getReferenceLov() {
        return referenceLov;
    }

    public void setReferenceLov(String referenceLov) {
        this.referenceLov = referenceLov;
    }

    public String getReferenceEnabled() {
        return referenceEnabled;
    }

    public void setReferenceEnabled(String referenceEnabled) {
        this.referenceEnabled = referenceEnabled;
    }

    public String getReferenceLookUp() {
        return referenceLookUp;
    }

    public void setReferenceLookUp(String referenceLookUp) {
        this.referenceLookUp = referenceLookUp;
    }

    public String getReferenceFieldAlign() {
        return referenceFieldAlign;
    }

    public void setReferenceFieldAlign(String referenceFieldAlign) {
        this.referenceFieldAlign = referenceFieldAlign;
    }

    public WebOptions getNullAbleOptions() {
        return nullAbleOptions;
    }

    public void setNullAbleOptions(WebOptions nullAbleOptions) {
        this.nullAbleOptions = nullAbleOptions;
    }

    public String getReferenceNullAble() {
        return referenceNullAble;
    }

    public void setReferenceNullAble(String referenceNullAble) {
        this.referenceNullAble = referenceNullAble;
    }

    public String getParameterIds() {
        return parameterIds;
    }

    public void setParameterIds(String parameterIds) {
        this.parameterIds = parameterIds;
    }

    public WebOptions getCalendarTypeOptions() {
        return calendarTypeOptions;
    }

    public void setCalendarTypeOptions(WebOptions calendarTypeOptions) {
        this.calendarTypeOptions = calendarTypeOptions;
    }

    public String getReferenceCalendarType() {
        return referenceCalendarType;
    }

    public void setReferenceCalendarType(String referenceCalendarType) {
        this.referenceCalendarType = referenceCalendarType;
    }
}