package com.sino.ams.yearchecktaskmanager.dto;


import com.sino.ams.appbase.dto.AMSBaseDTO;

/**
 * 任务信息
 * @author Administrator
 *
 */
public class AssetsYearCheckTaskLineDTO extends AMSBaseDTO{
	
	private String taskOrderId ="";//主键ID
	private String headerId = "";//头表ID
	private String orderName = "" ;//任务名称
	private String orderNumber = "";//任务编号
	private int implementBy ;//工单的执行人USER_ID
	private String implementName ="";//工单的执行人姓名
	private String orderType = "";//工单类型
	private String orderStatus = "";//工单状态
	private String orderLevel = "";//工单层级
	private String  isLastLevel = "";//是否最后一级
	private int implementOrganizationId ;//工单所属地市
	private String implementDeptId = "";//工单所属部门ID
	private String implementDeptName= "";//工单所属部门名称
	private String implementRoleName = "";//执行人的角色
	private int lastUpdateDateBy ;//最后更新人
	
	private String company="";
	private String companyCode="";
	
	private String employeeNumber="";//员工编号
	private String orderTypeName="";//工单类型中文名称
	
	public String toString(){
		return "[taskOrderId="+taskOrderId+",headerId="+headerId+",orderName="+orderName+
		",implementBy="+implementBy+",implementName="+implementName+",company="+company+",orderType="+orderType+
		",orderStatus="+orderStatus+",orderLevel="+orderLevel+",isLastLevel="+isLastLevel+
		",implementOrganizationId="+implementOrganizationId+",implementDeptId="+implementDeptId+
		",implementDeptName="+implementDeptName+",implementRoleName="+implementRoleName;
	}
	
	
	public String getOrderTypeName() {
		return orderTypeName;
	}


	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}


	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getTaskOrderId() {
		return taskOrderId;
	}
	public void setTaskOrderId(String taskOrderId) {
		this.taskOrderId = taskOrderId;
	}
	public String getHeaderId() {
		return headerId;
	}
	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public int getImplementBy() {
		return implementBy;
	}
	public void setImplementBy(int implementBy) {
		this.implementBy = implementBy;
	}
	public String getImplementName() {
		return implementName;
	}
	public void setImplementName(String implementName) {
		this.implementName = implementName;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderLevel() {
		return orderLevel;
	}
	public void setOrderLevel(String orderLevel) {
		this.orderLevel = orderLevel;
	}
	public String getIsLastLevel() {
		return isLastLevel;
	}
	public void setIsLastLevel(String isLastLevel) {
		this.isLastLevel = isLastLevel;
	}
	public int getImplementOrganizationId() {
		return implementOrganizationId;
	}
	public void setImplementOrganizationId(int implementOrganizationId) {
		this.implementOrganizationId = implementOrganizationId;
	}
	public String getImplementDeptId() {
		return implementDeptId;
	}
	public void setImplementDeptId(String implementDeptId) {
		this.implementDeptId = implementDeptId;
	}
	public String getImplementDeptName() {
		return implementDeptName;
	}
	public void setImplementDeptName(String implementDeptName) {
		this.implementDeptName = implementDeptName;
	}
	public String getImplementRoleName() {
		return implementRoleName;
	}
	public void setImplementRoleName(String implementRoleName) {
		this.implementRoleName = implementRoleName;
	}
	public int getLastUpdateDateBy() {
		return lastUpdateDateBy;
	}
	public void setLastUpdateDateBy(int lastUpdateDateBy) {
		this.lastUpdateDateBy = lastUpdateDateBy;
	}
	
	
}
