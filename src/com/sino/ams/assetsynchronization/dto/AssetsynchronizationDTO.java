package com.sino.ams.assetsynchronization.dto;

import com.sino.ams.bean.CommonRecordDTO;

public class AssetsynchronizationDTO extends CommonRecordDTO {
//	private String act="";
	private int userId;
    private String userName;
    private int organizationId=0;
    private String employeeNumber="";
	private String respId;
	private String respApplId;
	private String companyCode;
	private String company;
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	private String isDefault;
    
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	//	public String getAct() {
//		return act;
//	}
//	public void setAct(String act) {
//		this.act = act;
//	}
	public String getRespId() {
		return respId;
	}
	public void setRespId(String respId) {
		this.respId = respId;
	}
	public String getRespApplId() {
		return respApplId;
	}
	public void setRespApplId(String respApplId) {
		this.respApplId = respApplId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
}
