package com.sino.ams.yearchecktaskmanager.dto;


import com.sino.ams.appbase.dto.AMSBaseDTO;

/**
 * ������Ϣ
 * @author Administrator
 *
 */
public class AssetsYearCheckTaskLineDTO extends AMSBaseDTO{
	
	private String taskOrderId ="";//����ID
	private String headerId = "";//ͷ��ID
	private String orderName = "" ;//��������
	private String orderNumber = "";//������
	private int implementBy ;//������ִ����USER_ID
	private String implementName ="";//������ִ��������
	private String orderType = "";//��������
	private String orderStatus = "";//����״̬
	private String orderLevel = "";//�����㼶
	private String  isLastLevel = "";//�Ƿ����һ��
	private int implementOrganizationId ;//������������
	private String implementDeptId = "";//������������ID
	private String implementDeptName= "";//����������������
	private String implementRoleName = "";//ִ���˵Ľ�ɫ
	private int lastUpdateDateBy ;//��������
	
	private String company="";
	private String companyCode="";
	
	private String employeeNumber="";//Ա�����
	private String orderTypeName="";//����������������
	
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
