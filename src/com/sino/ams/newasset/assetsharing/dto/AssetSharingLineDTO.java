package com.sino.ams.newasset.assetsharing.dto;

import com.sino.ams.appbase.dto.AMSBaseDTO;

public class AssetSharingLineDTO extends AMSBaseDTO {
	private String lineId="";
	private String lineStatus="";
	private String transId="";
	private String systemId="";
	private String barcode="";
	private String itemCode="";
	private   String itemName="";
	private String itemSpec="";
	private String adressId="";
	private String localtion="";
	private String workorderObjectLocation="";//WORKORDER_OBJECT_LOCATION
	private String responsibilityUser="";//RESPONSIBILITY_USER
	private String userName="";
	private String shareStatus="";//资产共享状态SHARE_STATUS
	private String shareStatusDesc="";//资产共享状态SHARE_STATUS
	private String employeeNumber="";//EMPLOYEE_NUMBER
	private String contentCode="";//
	private String contentName="";//
	private String responsibilityDept="";//责任人部门
	
	private String specialityDept="";//实物管理部门
	 
	 private String shareStatusOpt="";//共享状态
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemSpec() {
		return itemSpec;
	}
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}
	public String getAdressId() {
		return adressId;
	}
	public void setAdressId(String adressId) {
		this.adressId = adressId;
	}
	public String getLocaltion() {
		return localtion;
	}
	public void setLocaltion(String localtion) {
		this.localtion = localtion;
	}
 
	public String getResponsibilityUser() {
		return responsibilityUser;
	}
	public void setResponsibilityUser(String responsibilityUser) {
		this.responsibilityUser = responsibilityUser;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getContentCode() {
		return contentCode;
	}
	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getWorkorderObjectLocation() {
		return workorderObjectLocation;
	}
	public void setWorkorderObjectLocation(String workorderObjectLocation) {
		this.workorderObjectLocation = workorderObjectLocation;
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
	public String getLineStatus() {
		return lineStatus;
	}
	public void setLineStatus(String lineStatus) {
		this.lineStatus = lineStatus;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getShareStatus() {
		return shareStatus;
	}
	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}
	public String getShareStatusOpt() {
		return shareStatusOpt;
	}
	public void setShareStatusOpt(String shareStatusOpt) {
		this.shareStatusOpt = shareStatusOpt;
	}
	public String getShareStatusDesc() {
		return shareStatusDesc;
	}
	public void setShareStatusDesc(String shareStatusDesc) {
		this.shareStatusDesc = shareStatusDesc;
	}
	public String getResponsibilityDept() {
		return responsibilityDept;
	}
	public void setResponsibilityDept(String responsibilityDept) {
		this.responsibilityDept = responsibilityDept;
	}
	public String getSpecialityDept() {
		return specialityDept;
	}
	public void setSpecialityDept(String specialityDept) {
		this.specialityDept = specialityDept;
	}
 
 	 
}
