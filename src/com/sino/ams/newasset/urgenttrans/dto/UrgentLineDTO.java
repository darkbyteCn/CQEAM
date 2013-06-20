package com.sino.ams.newasset.urgenttrans.dto;

import com.sino.ams.appbase.dto.AMSBaseDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * 
 * @系统名称: 紧急调拨单
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Aug 1, 2011
 */
public class UrgentLineDTO extends AMSBaseDTO {
	private String itemName = ""; // 资产名称
	private String itemCode = ""; // 资产名称
	private String itemCategory = "";
	private String itemSpec = ""; // 规格型号
	
	private String workorderObjectLocation = ""; // 资产地点描述
	private String responsibilityUserName = ""; // 责任人

//	private SimpleCalendar rentDate = null; // 起租日期
//	private SimpleCalendar rentEndDate = null; // 止租日期
//	private String rentPerson = ""; // 签约单位,
//	private String contractNumber = ""; // 合同编号
//	private String contractName = ""; // 合同名称

//	private float tenancy = 0; // 租期
//	private String yearRental = ""; // 年租金
//	private String monthRental = ""; // 月租金
	private String remark = "";

	private String lineId = "";
	private String transId = "";
	private String barcode = "";
	private String responsibilityUser = "";
	// TENANCY",
	// "YEAR_RENTAL",
	// "MONTH_REANTAL",
	// "REMARK"
	
	public UrgentLineDTO() {
//		this.rentDate = new SimpleCalendar();
//		this.rentEndDate = new SimpleCalendar();
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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

 

}
