package com.sino.rds.share.form;

import com.sino.rds.appbase.form.RDSBaseFrm;
import com.sino.rds.foundation.web.component.WebOptions;

/**
 * <p>Title: RDS_LOOKUP_DEFINE</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class LookUpDefineFrm extends RDSBaseFrm {
    private String lookUpIds = "";
    private String lookUpName = "";
    private String lookUpTitle = "";
    private String reportId = "";
    private String reportCode = "";
    private String reportName = "";
    private String reportType = "";
    private String returnField = "";
    private String returnFieldName = "";
    private String otherReturnValue = "";

    private WebOptions reportOptions = null;
    private WebOptions returnFieldOptions = null;

    public LookUpDefineFrm(){
        this.primaryKeyName = "lookUpId";
    }

    public String getLookUpTitle() {
        return lookUpTitle;
    }

    public void setLookUpTitle(String lookUpTitle) {
        this.lookUpTitle = lookUpTitle;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReturnField() {
        return returnField;
    }

    public void setReturnField(String returnField) {
        this.returnField = returnField;
    }

    public String getOtherReturnValue() {
        return otherReturnValue;
    }

    public void setOtherReturnValue(String otherReturnValue) {
        this.otherReturnValue = otherReturnValue;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getLookUpIds() {
        return lookUpIds;
    }

    public void setLookUpIds(String lookUpIds) {
        this.lookUpIds = lookUpIds;
    }

    public String getLookUpName() {
        return lookUpName;
    }

    public void setLookUpName(String lookUpName) {
        this.lookUpName = lookUpName;
    }

    public WebOptions getReportOptions() {
        return reportOptions;
    }

    public void setReportOptions(WebOptions reportOptions) {
        this.reportOptions = reportOptions;
    }

    public WebOptions getReturnFieldOptions() {
        return returnFieldOptions;
    }

    public void setReturnFieldOptions(WebOptions returnFieldOptions) {
        this.returnFieldOptions = returnFieldOptions;
    }

    public String getReturnFieldName() {
        return returnFieldName;
    }

    public void setReturnFieldName(String returnFieldName) {
        this.returnFieldName = returnFieldName;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
