package com.sino.ams.newasset.lease.dto;

import com.sino.ams.appbase.dto.AMSBaseDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

public class LeaseLineDTO extends AMSBaseDTO {
	private String itemName = ""; // 资产名称
	private String itemSpec = ""; // 规格型号
	private String workorderObjectLocation = ""; // 资产地点描述
	private String responsibilityUserName = ""; // 责任人

	private SimpleCalendar rentDate = null; // 起租日期
	private SimpleCalendar rentEndDate = null; // 止租日期
	private String rentPerson = ""; // 签约单位,
	private String contractNumber = ""; // 合同编号
	private String contractName = ""; // 合同名称

	private float tenancy = 0; // 租期
	private String yearRental = ""; // 年租金
	private String monthRental = ""; // 月租金
	private String remark = "";

	private String lineId = "";
	private String transId = "";
	private String barcode = "";
	private String responsibilityUser = "";
	// TENANCY",
	// "YEAR_RENTAL",
	// "MONTH_REANTAL",
	// "REMARK"
	
	public LeaseLineDTO() {
		this.rentDate = new SimpleCalendar();
		this.rentEndDate = new SimpleCalendar();
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

	public String getWorkorderObjectLocation() {
		return workorderObjectLocation;
	}

	public void setWorkorderObjectLocation(String workorderObjectLocation) {
		this.workorderObjectLocation = workorderObjectLocation;
	}

	public String getResponsibilityUserName() {
		return responsibilityUserName;
	}

	public void setResponsibilityUserName(String responsibilityUserName) {
		this.responsibilityUserName = responsibilityUserName;
	}

	public SimpleCalendar getRentDate() throws CalendarException {
		rentDate.setCalPattern(getCalPattern());
		return rentDate;
	}

	public void setRentDate(String rentDate) throws CalendarException {
		this.rentDate.setCalendarValue(rentDate);
//		this.rentDate = rentDate;
	}

	public SimpleCalendar getRentEndDate() throws CalendarException {
		this.rentEndDate.setCalPattern(getCalPattern());   
		return this.rentEndDate;
	}
	
	public void setRentEndDate(String rentEndDate) throws CalendarException {
		this.rentEndDate.setCalendarValue(rentEndDate);
//		this.endDate = endDate;
	}

	public String getRentPerson() {
		return rentPerson;
	}

	public void setRentPerson(String rentPerson) {
		this.rentPerson = rentPerson;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public float getTenancy() {
		return tenancy;
	}

	public void setTenancy(float tenancy) {
		this.tenancy = tenancy;
	}

	public String getYearRental() {
		return yearRental;
	}

	public void setYearRental(String yearRental) {
		this.yearRental = yearRental;
	}

	public String getMonthRental() {
		return monthRental;
	}

	public void setMonthRental(String monthRental) {
		this.monthRental = monthRental;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getResponsibilityUser() {
		return responsibilityUser;
	}

	public void setResponsibilityUser(String responsibilityUser) {
		this.responsibilityUser = responsibilityUser;
	}

}
