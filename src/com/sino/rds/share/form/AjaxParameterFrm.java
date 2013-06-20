package com.sino.rds.share.form;

import com.sino.rds.appbase.form.RDSBaseFrm;

public class AjaxParameterFrm extends RDSBaseFrm {
    private String serviceClass = "";
    private String frmName = "";
    private String methodName = "";
    private String listFieldName = "";
    private String resType = "";

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getFrmName() {
        return frmName;
    }

    public void setFrmName(String frmName) {
        this.frmName = frmName;
    }

    public String getListFieldName() {
        return listFieldName;
    }

    public void setListFieldName(String listFieldName) {
        this.listFieldName = listFieldName;
    }
}
