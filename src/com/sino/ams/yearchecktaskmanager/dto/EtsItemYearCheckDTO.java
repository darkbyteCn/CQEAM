package com.sino.ams.yearchecktaskmanager.dto;

import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;

public class EtsItemYearCheckDTO extends AmsAssetsAddressVDTO {
	
	//确认状态
	private String checkStatus="";
	private String checkStatusOption ="";
	private String orderNumber ="";//盘点任务编码
	private String orderName = ""; //盘点任务名称
	private String orderType = "";//盘点类型
	private String excel="";
	private String ExcelLineId = "";//Excel行号
	  
	  
	//备注
	private String notes="";
	
	public String getExcelLineId() {
		return ExcelLineId;
	}
	public void setExcelLineId(String excelLineId) {
		ExcelLineId = excelLineId;
	}
	public String getExcel() {
		return excel;
	}
	public void setExcel(String excel) {
		this.excel = excel;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getCheckStatusOption() {
		return checkStatusOption;
	}
	public void setCheckStatusOption(String checkStatusOption) {
		this.checkStatusOption = checkStatusOption;
	}
	
	
	

}
