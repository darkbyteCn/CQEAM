package com.sino.rds.share.form;

import com.sino.rds.foundation.web.component.WebOptions;

public class FixedCategoryFrm extends ReportCategoryFrm {
    private String sortNumber = "";
    private String defineValue = "";
    private String defineId = "";
    private String parentId = "";
    private String parentValue = "";
    private WebOptions parentIdOptions = null;
    
    public FixedCategoryFrm() {
        super();
        primaryKeyName = "defineId";
    }
    
    public String getSortNumber() {
        return this.sortNumber;
    }

    public String getDefineValue() {
        return this.defineValue;
    }

    public String getDefineId() {
        return this.defineId;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public void setDefineValue(String defineValue) {
        this.defineValue = defineValue;
    }

    public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentValue() {
        return parentValue;
    }

    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    public WebOptions getParentIdOptions() {
        return parentIdOptions;
    }

    public void setParentIdOptions(WebOptions parentIdOptions) {
        this.parentIdOptions = parentIdOptions;
    }
}