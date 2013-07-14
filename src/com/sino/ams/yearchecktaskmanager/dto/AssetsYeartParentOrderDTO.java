package com.sino.ams.yearchecktaskmanager.dto;

import com.sino.ams.appbase.dto.AMSBaseDTO;

public class AssetsYeartParentOrderDTO extends AMSBaseDTO {
	private String parentOrderName= "";
	private String paretnOrderNumber="";
	private String parentImplementDeptName="";
	private String parentOrderType = ""; //父级任务类型
	private String parentOrderTypeName = "";//父级任务类型名称
	
	public String getParentOrderTypeName() {
		return parentOrderTypeName;
	}
	public void setParentOrderTypeName(String parentOrderTypeName) {
		this.parentOrderTypeName = parentOrderTypeName;
	}
	public String getParentOrderName() {
		return parentOrderName;
	}
	public void setParentOrderName(String parentOrderName) {
		this.parentOrderName = parentOrderName;
	}
	public String getParetnOrderNumber() {
		return paretnOrderNumber;
	}
	public void setParetnOrderNumber(String paretnOrderNumber) {
		this.paretnOrderNumber = paretnOrderNumber;
	}
	public String getParentImplementDeptName() {
		return parentImplementDeptName;
	}
	public void setParentImplementDeptName(String parentImplementDeptName) {
		parentImplementDeptName = parentImplementDeptName;
	}
	public String getParentOrderType() {
		return parentOrderType;
	}
	public void setParentOrderType(String parentOrderType) {
		this.parentOrderType = parentOrderType;
	}
	
}
