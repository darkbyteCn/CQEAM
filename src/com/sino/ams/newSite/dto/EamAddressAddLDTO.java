package com.sino.ams.newSite.dto;

import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.util.StrUtil;

/**
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 12, 2011 10:24:45 AM
 *          类说明 :新增地点流程表单  行信息
 */
public class EamAddressAddLDTO extends CheckBoxDTO {

    private String lineId = "";
    private String transId = "";              //单据ID
    private String workorderObjectCode = "";  //地点代码  1
    private String workorderObjectName = "";  //地点名称  2
    private String objectCategory = "";       //地点专业  3
    private String countyCode = "";           //所属区县
    private String areaType = "";             //区域代码  5
    private String btsNo = "";               //基站或营业厅编码

    private String city = "";                 //市
    private String county = "";               //区县 4

    private String remark = "";               //备注
    private int organizationId;
    private String addrMaintainType = ""; //地点维护类型
    private String errorMessage = "";
    
    private String shareType = ""; //是否共享

    private String ExcelLineId = "";//Excel行号

    public String getExcelLineId() {
        return ExcelLineId;
    }

    public void setExcelLineId(String excelLineId) {
        ExcelLineId = excelLineId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getWorkorderObjectCode() {
        return workorderObjectCode;
    }

    public void setWorkorderObjectCode(String workorderObjectCode) {
        this.workorderObjectCode = workorderObjectCode;
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getObjectCategory() {
        return objectCategory;
    }

    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddrMaintainType() {
        return addrMaintainType;
    }

    public void setAddrMaintainType(String addrMaintainType) {
        this.addrMaintainType = addrMaintainType;
    }

    public String getBtsNo() {
        return btsNo;
    }

    public void setBtsNo(String btsNo) {
        this.btsNo = btsNo;
    }

    public boolean isAddLocation(){
        return (StrUtil.isNotEmpty(addrMaintainType) && addrMaintainType.equals("新增"));
    }

    public boolean isUpdateLocation(){
        return (StrUtil.isNotEmpty(addrMaintainType) && addrMaintainType.equals("修改"));
    }

    public boolean isDisableLocation(){
        return (StrUtil.isNotEmpty(addrMaintainType) && addrMaintainType.equals("失效"));
    }

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
}
