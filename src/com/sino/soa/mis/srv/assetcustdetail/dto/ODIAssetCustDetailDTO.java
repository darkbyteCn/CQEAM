package com.sino.soa.mis.srv.assetcustdetail.dto;

import com.sino.base.dto.DTO;

/**
 * User: zhoujs
 * Date: 2009-8-22 17:03:22
 * Function:ODI转资清单DTO
 */
public class ODIAssetCustDetailDTO implements DTO {
    private String envCode = "";
    private String bookTypeCode = "";
    private String projectNumber = "";
    private String taskNum = "";
    private String capitalizedDateFrom = "";
    private String capitalizedDateTo = "";

    private String assetsType = "";
    private String projectName = "";
    private String orgOption = "";

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public String getCapitalizedDateFrom() {
        return capitalizedDateFrom;
    }

    public void setCapitalizedDateFrom(String capitalizedDateFrom) {
        this.capitalizedDateFrom = capitalizedDateFrom;
    }

    public String getCapitalizedDateTo() {
        return capitalizedDateTo;
    }

    public void setCapitalizedDateTo(String capitalizedDateTo) {
        this.capitalizedDateTo = capitalizedDateTo;
    }

    public String getAssetsType() {
        return assetsType;
    }

    public void setAssetsType(String assetsType) {
        this.assetsType = assetsType;
    }

    public String getOrgOption() {
        return orgOption;
    }

    public void setOrgOption(String orgOption) {
        this.orgOption = orgOption;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
