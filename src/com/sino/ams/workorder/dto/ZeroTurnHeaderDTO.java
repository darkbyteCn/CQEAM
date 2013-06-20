package com.sino.ams.workorder.dto;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.base.calen.SimpleCalendar;
import com.sino.framework.security.dto.ServletConfigDTO;

public class ZeroTurnHeaderDTO extends AMSFlowDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7061078340898781512L;

	// 属性
	//private ServletConfigDTO servletConfig = null;
    private String excel="";
    
	//表头信息
    private String transId = "";
	private String transNo = "";          //单据编号
	private String transType = "";        //单据类型
	private String transStatus = "";      //单据状态
	private String statusType = "";//     //单据状态名称
	private String createdReason = "";    //单据创建原因
	//private String isCreated = "";        //是否已经提交创建了单据
	private int  createdBy;               //创建人;
	private String createdByName = "";    //创建人姓名
	private int organizationId =-1;       //公司编码
	private String organizationName = ""; //公司名称       
	private String deptCode = "";         //部门
	private String deptName = "";         //部门名称
	private int groupId ;
	private String groupName = "";//组别
	
	
	private String computeTims = "";// 执行周期
	private String orderExecuter = "";//工单执行人
	private String orderExecuterName = "";//
	private String orderFiler = "";//工单归档人
	private String orderFilerName = "";
	private String orderNo = "";//采购单号
	private String remark1 = "";//备注
	private String ccenter ="";//成本中心
	private String copye = "";//公司编码
	
    private String phoneNumber = ""; // 创建人手机号码
    private String email = ""; // 创建人电子邮件
	//表头信息
	
	// 流程相关信息
	private String attribute1 = "";   // 加入流程相关信息
	private String attribute2 = "";   // 加入流程相关信息
	private String attribute3 = "";   // 加入流程相关信息
	private String attribute4 = "";   // 加入流程相关信息
	private String attribute5 = "";   // 加入流程相关信息
    private String sectionRight = ""; // 加入流程相关信息
    private String hiddenRight = "";  // 加入流程相关信息
    
    
    //流程相关信息
    
    //用户相关信息[暂存]
    private int userId = 0;
    
    
    private String emergentLevel = ""; //紧急程度
    private String emergentLevelOption = ""; //紧急成度下拉框  
	private String centerCode = "";// 成本中心（必输）
	private String centerName = "";// 成本中心
    
    private String costCenterName = "";
    private String costCenterCode = "";
    private String barcode ="";
    
	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return the costCenterName
	 */
	public String getCostCenterName() {
		return costCenterName;
	}

	/**
	 * @param costCenterName the costCenterName to set
	 */
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	/**
	 * @return the costCenterCode
	 */
	public String getCostCenterCode() {
		return costCenterCode;
	}

	/**
	 * @param costCenterCode the costCenterCode to set
	 */
	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	public String getEmergentLevel() {
		return emergentLevel;
	}

	public void setEmergentLevel(String emergentLevel) {
		this.emergentLevel = emergentLevel;
	}

	public String getEmergentLevelOption() {
		return emergentLevelOption;
	}

	public void setEmergentLevelOption(String emergentLevelOption) {
		this.emergentLevelOption = emergentLevelOption;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAttribute1() {
		return attribute1;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSectionRight() {
		return sectionRight;
	}

	public void setSectionRight(String sectionRight) {
		this.sectionRight = sectionRight;
	}

	public String getHiddenRight() {
		return hiddenRight;
	}

	public void setHiddenRight(String hiddenRight) {
		this.hiddenRight = hiddenRight;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}


	public String getExcel() {
		return excel;
	}

	public void setExcel(String excel) {
		this.excel = excel;
	}

	public String getCreatedReason() {
		return createdReason;
	}

	public void setCreatedReason(String createdReason) {
		this.createdReason = createdReason;
	}

//	public String getIsCreated() {
//		return isCreated;
//	}
//
//	public void setIsCreated(String isCreated) {
//		this.isCreated = isCreated;
//	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getOrderExecuter() {
		return orderExecuter;
	}

	public void setOrderExecuter(String orderExecuter) {
		this.orderExecuter = orderExecuter;
	}

	public String getOrderFiler() {
		return orderFiler;
	}

	public void setOrderFiler(String orderFiler) {
		this.orderFiler = orderFiler;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getComputeTims() {
		return computeTims;
	}

	public void setComputeTims(String computeTims) {
		this.computeTims = computeTims;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getOrderExecuterName() {
		return orderExecuterName;
	}

	public void setOrderExecuterName(String orderExecuterName) {
		this.orderExecuterName = orderExecuterName;
	}

	public String getOrderFilerName() {
		return orderFilerName;
	}

	public void setOrderFilerName(String orderFilerName) {
		this.orderFilerName = orderFilerName;
	}

	public String getCcenter() {
		return ccenter;
	}

	public void setCcenter(String ccenter) {
		this.ccenter = ccenter;
	}

	public String getCopye() {
		return copye;
	}

	public void setCopye(String copye) {
		this.copye = copye;
	}
	
	
}
