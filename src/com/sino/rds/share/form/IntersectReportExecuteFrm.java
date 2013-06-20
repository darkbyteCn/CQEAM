package com.sino.rds.share.form;

import com.sino.base.data.RowSet;

import java.util.List;

public class IntersectReportExecuteFrm extends ReportExecuteFrm {
    private RowSet aboveDimensions = null;
    private RowSet leftDimensions = null;
    private RowSet bottomExpressions = null;
    private RowSet verticalExpressions = null;

    private List<ReportCategoryFrm> definedCategories = null;
    private List<ReportCategoryFrm> aboveCategories = null;
    private List<ReportCategoryFrm> leftCategories = null;

    private List<ReportViewFrm> measureValueFields = null;
    private String[][] reportValue = null;
    private int dataStartRow = 0;
    private int dataStartColumn = 0;
    private int dataFrmCount = 0;

    public IntersectReportExecuteFrm() {
        super();
    }

    public RowSet getAboveDimensions() {
        return aboveDimensions;
    }

    public void setAboveDimensions(RowSet aboveDimensions) {
        this.aboveDimensions = aboveDimensions;
    }

    public RowSet getLeftDimensions() {
        return leftDimensions;
    }

    public void setLeftDimensions(RowSet leftDimensions) {
        this.leftDimensions = leftDimensions;
    }

    public RowSet getBottomExpressions() {
        return bottomExpressions;
    }

    public void setBottomExpressions(RowSet bottomExpressions) {
        this.bottomExpressions = bottomExpressions;
    }

    public RowSet getVerticalExpressions() {
        return verticalExpressions;
    }

    public void setVerticalExpressions(RowSet verticalExpressions) {
        this.verticalExpressions = verticalExpressions;
    }

    public void setAboveCategories(List<ReportCategoryFrm> aboveCategories) {
        this.aboveCategories = aboveCategories;
        if (aboveCategories != null) {
            dataStartRow = aboveCategories.size() + 1;
        }
    }

    public List<ReportCategoryFrm> getAboveCategories() {
        return aboveCategories;
    }

    public List<ReportCategoryFrm> getLeftCategories() {
        return leftCategories;
    }

    public void setLeftCategories(List<ReportCategoryFrm> leftCategories) {
        this.leftCategories = leftCategories;
        if (leftCategories != null) {
            dataStartColumn = leftCategories.size();
        }
    }

    public List<ReportViewFrm> getMeasureValueFields() {
        return measureValueFields;
    }

    public void setMeasureValueFields(List<ReportViewFrm> measureValueFields) {
        this.measureValueFields = measureValueFields;
        if (measureValueFields != null) {
            this.dataFrmCount = measureValueFields.size();
        }
    }

    public String[][] getReportValue() {
        return reportValue;
    }

    public void setReportValue(String[][] reportValue) {
        this.reportValue = reportValue;
    }

    public int getDataStartRow() {
        return dataStartRow;
    }

    public int getDataStartColumn() {
        return dataStartColumn;
    }

    public int getDataFrmCount() {
        return dataFrmCount;
    }

    public List<ReportCategoryFrm> getDefinedCategories() {
        return definedCategories;
    }

    public void setDefinedCategories(List<ReportCategoryFrm> definedCategories) {
        this.definedCategories = definedCategories;
    }

    public void freeResource() {
        super.freeResource();
        if(aboveDimensions != null){
            aboveDimensions.clearData();
        }
        if(leftDimensions != null){
            leftDimensions.clearData();
        }
        if(definedCategories != null){
            definedCategories.clear();
        }
        if(aboveCategories != null){
            aboveCategories.clear();
        }
        if(leftCategories != null){
            leftCategories.clear();
        }
        aboveDimensions = null;
        leftDimensions = null;
        definedCategories = null;
        aboveCategories = null;
        leftCategories = null;
        measureValueFields = null;
        reportValue = null;
    }
}
