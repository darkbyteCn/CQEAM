package com.sino.soa.common;

/**
 * User: zhoujs
 * Date: 2009-4-22 9:53:23
 * Function:接口服务返回信息
 */
public class SrvReturnMessage {
    private String errorFlag = ""; //Y
    private String errorMessage = "";

    public String getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(String errorFlag) {
        this.errorFlag = errorFlag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;

    }
}
