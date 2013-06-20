package com.sino.rds.share.form;

import com.sino.base.ExtendedArrayList;
import com.sino.rds.appbase.form.RDSBaseFrm;
import com.sino.rds.foundation.web.component.WebOptions;

import java.util.List;

public class FixedCategoryProcessFrm extends RDSBaseFrm {
    private ReportDefineFrm report = null;
    private List<FixedCategoryFrm> fixedCategories = new ExtendedArrayList(FixedCategoryFrm.class);
    private String reportId = "";
    private WebOptions reportOptions = null;
    private String categoryId = "";
    private WebOptions categoryOptions = null;

    public FixedCategoryProcessFrm() {
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

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportId(){
        return this.reportId;
    }

    public List<FixedCategoryFrm> getFixedCategories() {
        return fixedCategories;
    }

    public void setFixedCategories(List<FixedCategoryFrm> fixedCategories) {
        this.fixedCategories = fixedCategories;
    }

    public WebOptions getReportOptions() {
        return reportOptions;
    }

    public void setReportOptions(WebOptions reportOptions) {
        this.reportOptions = reportOptions;
    }

    public WebOptions getCategoryOptions() {
        return categoryOptions;
    }

    public void setCategoryOptions(WebOptions categoryOptions) {
        this.categoryOptions = categoryOptions;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}