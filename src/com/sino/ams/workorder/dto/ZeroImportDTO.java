package com.sino.ams.workorder.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.dto.DTO;

public class ZeroImportDTO implements DTO{
	private int rowNum = 0;
	private String barcode = "" ;
	private String itemName = "";
	private String itemSpec = "";
	private String manufacturerName = "";
	private String contentCode = "";
	private String itemQty = "";
	private String lifeInYears = "";
	private String cost = "";
	private String startDate = "";
	private String opeName = "";
	private String nleName = "";
	private String constructStatus = "";
	private String locationSegment1  = "";
	private String locationSegment2Name  = "";
	private String locationSegment2  = "";
	private String locationSegment3  = "";
	private String employeeNumber  = "";
	private String employeeName  = "";
	private String applyType  = "";
	private String contactPerson  = "";
	private String contactNumber  = "";
	private String expectArrivalTime  = "";
	private String errorMsg  = "";
	private String act  = "";
	private String excel="";
	
	public String getExcel() {
		return excel;
	}
	public void setExcel(String excel) {
		this.excel = excel;
	}
	public String getExpectArrivalTime() {
		return expectArrivalTime;
	}
	public void setExpectArrivalTime(String expectArrivalTime) {
		this.expectArrivalTime = expectArrivalTime;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String getContentCode() {
		return contentCode;
	}
	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}
	public String getItemQty() {
		return itemQty;
	}
	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}
	public String getLifeInYears() {
		return lifeInYears;
	}
	public void setLifeInYears(String lifeInYears) {
		this.lifeInYears = lifeInYears;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getOpeName() {
		return opeName;
	}
	public void setOpeName(String opeName) {
		this.opeName = opeName;
	}
	public String getNleName() {
		return nleName;
	}
	public void setNleName(String nleName) {
		this.nleName = nleName;
	}
	public String getConstructStatus() {
		return constructStatus;
	}
	public void setConstructStatus(String constructStatus) {
		this.constructStatus = constructStatus;
	}
	public String getLocationSegment1() {
		return locationSegment1;
	}
	public void setLocationSegment1(String locationSegment1) {
		this.locationSegment1 = locationSegment1;
	}
	public String getLocationSegment2Name() {
		return locationSegment2Name;
	}
	public void setLocationSegment2Name(String locationSegment2Name) {
		this.locationSegment2Name = locationSegment2Name;
	}
	public String getLocationSegment2() {
		return locationSegment2;
	}
	public void setLocationSegment2(String locationSegment2) {
		this.locationSegment2 = locationSegment2;
	}
	public String getLocationSegment3() {
		return locationSegment3;
	}
	public void setLocationSegment3(String locationSegment3) {
		this.locationSegment3 = locationSegment3;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
