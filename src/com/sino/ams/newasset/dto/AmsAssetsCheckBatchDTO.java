package com.sino.ams.newasset.dto;


import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: 年度资产盘点批表(AMS) AmsAssetsCheckBatch</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author jeffery
 * @version 1.0
 */

public class AmsAssetsCheckBatchDTO extends AMSFlowDTO {
	
	//
	  private String taskNumber ="";//盘点任务编码
	  private String taskName = ""; //盘点任务名称
	  private String taskType = "";//盘点类型
	  private String taskTypeName ="";//盘点类型名称<!-- 2013-07-04 Jeffery-->
	//
	
	  
	private String batchId = "";
	public String getTaskTypeName() {
		return taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	private String batchNo = "";
	private String checkDept="";
	private String batchCheckLocation = "";
	private int batchImplementBy;
	private SimpleCalendar batchStartTime = null;
	private int batchImplementDays;
	private String taskDesc = "";
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE ;

	private String checkDeptName = "";
	private String checkLocationName = "";
	private String companyName = "";
	private String batchImplementUser = "";
	private String createdUser = "";
	private String createdLoginUser = "";

	private String batchStatus = "";
	private String batchStatusName = "";
	private SimpleCalendar distributeDate = null;
	private int distributeBy = SyBaseSQLUtil.NULL_INT_VALUE ;
	private String distributeUser = "";
	private String checkDeptOption = "";

	private int groupId;
	private String groupName = "";
	private String groupOption = "";
	private String transType = "";
	private SimpleCalendar approvedDate = null;
	private int approvedBy;

	private String procdureName = "";
	private String flowCode = "";
	private String srcPage = "";
	private String batchStatusOption = "";

	private boolean flow2End = false;
	private String orderType = ""; //盘点类单据类型代码：目前支持资产盘点，仪器仪表盘点，租赁盘点
	private String orderTypeName = ""; //盘点类单据类型描述：目前支持资产盘点，仪器仪表盘点，租赁盘点

	private String checkCategory = "";
	private String checkCategoryName = "";
	private String checkCategoryOpt = "";
	private String costCenterCode="";
	private String costCenterName="";
	private String orgOpt = "";
	private String analyseType = "";
    private String fromBarcode = "";
    private String toBarcode = "";
    
    private String proviceCode = "";
    private String tdProviceCode = "";
    
    private String checkTpye = "";
    private String deptCode = "";
    private SimpleCalendar misEndDate=null ;

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getCheckTpye() {
        return checkTpye;
    }

    public void setCheckTpye(String checkTpye) {
        this.checkTpye = checkTpye;
    }


    public String getFromBarcode() {
        return fromBarcode;
    }

    public void setFromBarcode(String fromBarcode) {
        this.fromBarcode = fromBarcode;
    }

    public String getToBarcode() {
        return toBarcode;
    }

    public void setToBarcode(String toBarcode) {
        this.toBarcode = toBarcode;
    }

    public AmsAssetsCheckBatchDTO() {
		super();
		this.transType = AssetsDictConstant.ASS_CHK;
		this.procdureName = AssetsDictConstant.PROCEDURE_NAME_CHECK;
		this.batchStartTime = new SimpleCalendar();
		this.distributeDate = new SimpleCalendar();
		this.approvedDate = new SimpleCalendar();
		this.misEndDate=new SimpleCalendar();
	}


	public boolean isFlow2End() {
		return flow2End;
	}

	public void setFlow2End(boolean flow2End) {
		this.flow2End = flow2End;
	}

	/**
	 * 功能：设置资产盘点头表(AMS)属性 下发日期
	 * @param distributeDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDistributeDate(String distributeDate) throws
			CalendarException {
		this.distributeDate.setCalendarValue(distributeDate);
	}

	/**
	 * 功能：设置资产盘点头表(AMS)属性 下发人
	 * @param distributeBy String
	 */
	public void setDistributeBy(int distributeBy) {
		this.distributeBy = distributeBy;
	}

	public void setDistributeUser(String distributeUser) {
		this.distributeUser = distributeUser;
	}

	/**
	 * 功能：设置资产盘点批表(AMS)属性 盘点批序列号
	 * @param batchId String
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * 功能：设置资产盘点批表(AMS)属性 盘点部门
	 * @param checkDept String
	 */
	public void setCheckDept(String checkDept) {
		this.checkDept = checkDept;
	}

	/**
	 * 功能：设置资产盘点批表(AMS)属性 盘点地点
	 * @param batchCheckLocation String
	 */
	public void setBatchCheckLocation(String batchCheckLocation) {
		this.batchCheckLocation = batchCheckLocation;
	}

	/**
	 * 功能：设置资产盘点批表(AMS)属性 执行人
	 * @param batchImplementBy String
	 */
	public void setBatchImplementBy(int batchImplementBy) {
		this.batchImplementBy = batchImplementBy;
	}

	/**
	 * 功能：设置资产盘点批表(AMS)属性 开始时间
	 * @param batchStartTime SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setBatchStartTime(String batchStartTime) throws
			CalendarException {
		this.batchStartTime.setCalendarValue(batchStartTime);
	}

	/**
	 * 功能：设置资产盘点批表(AMS)属性 执行周期
	 * @param batchImplementDays String
	 */
	public void setBatchImplementDays(int batchImplementDays) {
		this.batchImplementDays = batchImplementDays;
	}

	/**
	 * 功能：设置资产盘点批表(AMS)属性 盘点任务描述
	 * @param taskDesc String
	 */
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	/**
	 * 功能：设置资产盘点批表(AMS)属性 OU组织ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public void setCheckDeptName(String checkDeptName) {
		this.checkDeptName = checkDeptName;
	}

	public void setCheckLocationName(String checkLocationName) {
		this.checkLocationName = checkLocationName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setBatchImplementUser(String batchImplementUser) {

		this.batchImplementUser = batchImplementUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	public void setBatchStatusName(String batchStatusName) {
		this.batchStatusName = batchStatusName;
	}

	public void setCheckDeptOption(String checkDeptOption) {
		this.checkDeptOption = checkDeptOption;
	}

	/**
	 * 功能：获取资产盘点头表(AMS)属性 下发日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDistributeDate() throws CalendarException {
		distributeDate.setCalPattern(getCalPattern());
		return this.distributeDate;
	}

	/**
	 * 功能：获取资产盘点头表(AMS)属性 下发人
	 * @return String
	 */
	public int getDistributeBy() {
		return this.distributeBy;
	}

	public String getDistributeUser() {
		return distributeUser;
	}

	/**
	 * 功能：获取资产盘点批表(AMS)属性 盘点批序列号
	 * @return String
	 */
	public String getBatchId() {
		return this.batchId;
	}

	/**
	 * 功能：获取资产盘点批表(AMS)属性 盘点部门
	 * @return String
	 */
	public String getCheckDept() {
		return this.checkDept;
	}

	/**
	 * 功能：获取资产盘点批表(AMS)属性 盘点地点
	 * @return String
	 */
	public String getBatchCheckLocation() {
		return batchCheckLocation;
	}

	/**
	 * 功能：获取资产盘点批表(AMS)属性 执行人
	 * @return String
	 */
	public int getBatchImplementBy() {
		return batchImplementBy;
	}

	/**
	 * 功能：获取资产盘点批表(AMS)属性 开始时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getBatchStartTime() throws CalendarException {
		batchStartTime.setCalPattern(getCalPattern());
		return batchStartTime;
	}

	/**
	 * 功能：获取资产盘点批表(AMS)属性 执行周期
	 * @return String
	 */
	public int getBatchImplementDays() {
		return batchImplementDays;
	}

	/**
	 * 功能：获取资产盘点批表(AMS)属性 盘点任务描述
	 * @return String
	 */
	public String getTaskDesc() {
		return this.taskDesc;
	}

	/**
	 * 功能：获取资产盘点批表(AMS)属性 OU组织ID
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	public String getCheckDeptName() {
		return checkDeptName;
	}

	public String getCheckLocationName() {
		return checkLocationName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getBatchImplementUser() {
		return batchImplementUser;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public String getBatchStatusName() {
		return batchStatusName;
	}

	public String getCheckDeptOption() {
		return checkDeptOption;
	}


	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setGroupOption(String groupOption) {
		this.groupOption = groupOption;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public void setCreatedLoginUser(String createdLoginUser) {
		this.createdLoginUser = createdLoginUser;
	}

	public void setProcdureName(String procdureName) {
		this.procdureName = procdureName;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}

	public void setSrcPage(String srcPage) {
		this.srcPage = srcPage;
	}

	public void setBatchStatusOption(String batchStatusOption) {
		this.batchStatusOption = batchStatusOption;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public void setApprovedDate(String approvedDate) throws CalendarException {
		this.approvedDate.setCalendarValue(approvedDate);
	}

	public int getGroupId() {
		return groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getGroupOption() {
		return groupOption;
	}

	public String getTransType() {
		return transType;
	}

	public String getCreatedLoginUser() {
		return createdLoginUser;
	}

	public String getProcdureName() {
		return procdureName;
	}

	public String getFlowCode() {
		return flowCode;
	}

	public int getApprovedBy() {
		return approvedBy;
	}

	public SimpleCalendar getApprovedDate() throws CalendarException {
		approvedDate.setCalPattern(getCalPattern());
		return approvedDate;
	}

	public String getSrcPage() {
		return srcPage;
	}

	public String getBatchStatusOption() {
		return batchStatusOption;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public void setCheckCategory(String checkCategory) {
		this.checkCategory = checkCategory;
	}

	public void setCheckCategoryOpt(String checkCategoryOpt) {
		this.checkCategoryOpt = checkCategoryOpt;
	}

	public String getOrderType() {
		return orderType;
	}

	public String getOrderTypeName() {
		orderTypeName = "资产盘点";
		return orderTypeName;
	}

	public String getCheckCategory() {
		return checkCategory;
	}

	public String getCheckCategoryOpt() {
		return checkCategoryOpt;
	}

	public String getCheckCategoryName() {
		return checkCategoryName;
	}

	public void setCheckCategoryName(String checkCategoryName) {
		this.checkCategoryName = checkCategoryName;
	}

	public String getCostCenterCode() {
		return costCenterCode;
	}

	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public String getOrgOpt() {
		return orgOpt;
	}

	public String getAnalyseType() {
		return analyseType;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public void setOrgOpt(String orgOpt) {
		this.orgOpt = orgOpt;
	}

	public void setAnalyseType(String analyseType) {
		this.analyseType = analyseType;
	}

	public String getProviceCode() {
		return proviceCode;
	}

	public void setProviceCode(String proviceCode) {
		this.proviceCode = proviceCode;
	}

	public String getTdProviceCode() {
		return tdProviceCode;
	}

	public void setTdProviceCode(String tdProviceCode) {
		this.tdProviceCode = tdProviceCode;
	}

	public SimpleCalendar getMisEndDate() {
		return misEndDate;
	}

	public void setMisEndDate(SimpleCalendar misEndDate) {
		this.misEndDate = misEndDate;
	}
}
