package com.sino.ams.inv.dzyh.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.ct.dto.EtsItemInfoDTO;

/**
 * <p>Title: 低值易耗品出库-台账查询出库 EamDhBillL</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 于士博
 * @version 1.0
 */
public class EamDhBillLDTO extends EtsItemInfoDTO {

	private String billLineId = ""; //EAM_DH_BILL_L的主键
	private int catalogValueId = SyBaseSQLUtil.NULL_INT_VALUE; //目录编号
	private int catalogValueId2 = SyBaseSQLUtil.NULL_INT_VALUE; //为了区分页面的重复使用情况
	
	private String workorderObjectNoName1 = ""; //为了区分页面的重复使用情况
	
	//------------------------------以下不是EAM_DH_BILL_L表中的字段-----------------------------------------//
	private String catalogNameId = ""; //用于识别EAM_DH_CATALOG_VALUES表中的CATALOG_NAME字段
	
	//------------------------------以下是EAM_DH_CHG_LOG(变动历史)表中的字段-----------------------------------------//
	private String dhChgLogId = ""; //EAM_DH_CHG_LOG表中的主键ID
	private String chgType = ""; //变动类型
	private String fromDept = ""; //原使用部门
	private String toDept = ""; //新使用部门
	private int fromAddressId = SyBaseSQLUtil.NULL_INT_VALUE; //原地点
	private int toAddressId = SyBaseSQLUtil.NULL_INT_VALUE; //新地点
	private int fromResponsibilityUser; //原领用人
	private int toResponsibilityUser; //新领用人
	private String fromMaintainUser = ""; //原保管人
	private String toMaintainUser = ""; //新保管人
	private String fromStatus = ""; //原使用状态
	private String toStatus = ""; //新使用状态
	
	private String deptCode = ""; //AMS_MIS_DEPT表中的DEPT_CODE(部门代码)字段
	private String deptName = ""; //AMS_MIS_DEPT表中的DEPT_NAME(部门名称)字段
	private int employeeId; //AMS_MIS_EMPLOYEE表中的EMPLOYEE_ID(员工ID)字段
	private String eamployeeNumber = ""; //AMS_MIS_EMPLOYEE表中的EMPLOYEE_NUMBER(员工编号)字段
	private String userName = ""; //AMS_MIS_EMPLOYEE表中的USER_NAME(员工姓名)字段
	
	//------------------------------以下是EAM_DH_CATALOG_VALUES表中的字段--------------------------------------//
	private String barcodeFlag = ""; //标签编号
	private String catalogCode = ""; //物品编号
	private String catalogName = ""; //品名
	
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDhChgLogId() {
		return dhChgLogId;
	}

	public void setDhChgLogId(String dhChgLogId) {
		this.dhChgLogId = dhChgLogId;
	}

	public String getChgType() {
		return chgType;
	}

	public void setChgType(String chgType) {
		this.chgType = chgType;
	}

	public int getFromAddressId() {
		return fromAddressId;
	}

	public void setFromAddressId(int fromAddressId) {
		this.fromAddressId = fromAddressId;
	}

	public int getToAddressId() {
		return toAddressId;
	}

	public void setToAddressId(int toAddressId) {
		this.toAddressId = toAddressId;
	}

	public int getFromResponsibilityUser() {
		return fromResponsibilityUser;
	}

	public void setFromResponsibilityUser(int fromResponsibilityUser) {
		this.fromResponsibilityUser = fromResponsibilityUser;
	}

	public int getToResponsibilityUser() {
		return toResponsibilityUser;
	}

	public void setToResponsibilityUser(int toResponsibilityUser) {
		this.toResponsibilityUser = toResponsibilityUser;
	}

	public String getFromMaintainUser() {
		return fromMaintainUser;
	}

	public void setFromMaintainUser(String fromMaintainUser) {
		this.fromMaintainUser = fromMaintainUser;
	}

	public String getToMaintainUser() {
		return toMaintainUser;
	}

	public void setToMaintainUser(String toMaintainUser) {
		this.toMaintainUser = toMaintainUser;
	}

	public String getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}

	public String getToStatus() {
		return toStatus;
	}

	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}

	public String getCatalogNameId() {
		return catalogNameId;
	}

	public void setCatalogNameId(String catalogNameId) {
		this.catalogNameId = catalogNameId;
	}
	
	public int getCatalogValueId() {
		return catalogValueId;
	}
	public void setCatalogValueId(int catalogValueId) {
		this.catalogValueId = catalogValueId;
	}

	public String getBillLineId() {
		return billLineId;
	}

	public void setBillLineId(String billLineId) {
		this.billLineId = billLineId;
	}

	public String getFromDept() {
		return fromDept;
	}

	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}

	public String getToDept() {
		return toDept;
	}

	public void setToDept(String toDept) {
		this.toDept = toDept;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getCatalogValueId2() {
		return catalogValueId2;
	}

	public void setCatalogValueId2(int catalogValueId2) {
		this.catalogValueId2 = catalogValueId2;
	}

	public String getEamployeeNumber() {
		return eamployeeNumber;
	}

	public void setEamployeeNumber(String eamployeeNumber) {
		this.eamployeeNumber = eamployeeNumber;
	}

	public String getWorkorderObjectNoName1() {
		return workorderObjectNoName1;
	}

	public void setWorkorderObjectNoName1(String workorderObjectNoName1) {
		this.workorderObjectNoName1 = workorderObjectNoName1;
	}

	public String getBarcodeFlag() {
		return barcodeFlag;
	}

	public void setBarcodeFlag(String barcodeFlag) {
		this.barcodeFlag = barcodeFlag;
	}

	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
}
