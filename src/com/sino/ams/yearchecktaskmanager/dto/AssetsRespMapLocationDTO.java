package com.sino.ams.yearchecktaskmanager.dto;

import com.sino.ams.appbase.dto.AMSBaseDTO;

public class AssetsRespMapLocationDTO extends AMSBaseDTO {
	private String excelLineId;//����excel�е��к�
	private String userName="";//
	private String employeeNumber = "";
	private String deptName = "";//
	private String workOrderObjectCode = "";//��ϵص����
	private String workOrderObjectName = "";//��ϵص�����
	private String errorMsg = "";
	public String getExcelLineId() {
		return excelLineId;
	}
	public void setExcelLineId(String excelLineId) {
		this.excelLineId = excelLineId;
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getWorkOrderObjectCode() {
		return workOrderObjectCode;
	}
	public void setWorkOrderObjectCode(String workOrderObjectCode) {
		this.workOrderObjectCode = workOrderObjectCode;
	}
	public String getWorkOrderObjectName() {
		return workOrderObjectName;
	}
	public void setWorkOrderObjectName(String workOrderObjectName) {
		this.workOrderObjectName = workOrderObjectName;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
