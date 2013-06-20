package com.sino.soa.mis.srv.transTaskInfo.dto;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: SRV_TASKINFO SrvTaskinfo</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class PageInquiryTaskinfoDTO extends CheckBoxDTO{

	private String orgId = "";
	private String orgName = "";
	private String projectId ="";
	private String segment1 = "";
	private String taskId = "";
	private String taskNumber = "";
	private String taskName = "";
	private String description = "";
	private String wbsLevel = "";
	private String taskManager = "";
	private String parentTaskId = "";
	private String parentTaskNum = "";
	private String startDate = "";
	private String completionDate = "";
	private String serviceTypeCode = "";
	private String chargeableFlag = "";
	private String billableFlag = "";
	private String costFlag = "";
	private String attribute1 = "";
	private String attribute2 = "";
	private String attribute3 = "";
	private String attribute4 = "";
	private String attribute5 = "";
	private String attribute6 = "";
	private String pmProductCode = "";
	private String pmTaskReference = "";
	private String creationDate = "";
	private String lastUpdateDate = "";
	
	private String startLastUpdatDate = "";
	private String endLastUpdatDate = "";
	private String ouOption ="";
	private String assetsType ="";

	public PageInquiryTaskinfoDTO() {
		super();

	}

	
	/**
	 * 功能：设置SRV_TASKINFO属性 任务所属组织 名称
	 * @param orgName String
	 */
	public void setOrgName(String orgName){
		this.orgName = orgName;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 项目编号
	 * @param segment1 String
	 */
	public void setSegment1(String segment1){
		this.segment1 = segment1;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 任务编号
	 * @param taskNumber String
	 */
	public void setTaskNumber(String taskNumber){
		this.taskNumber = taskNumber;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 任务名称
	 * @param taskName String
	 */
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 描述
	 * @param description String
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 任务经理
	 * @param taskManager String
	 */
	public void setTaskManager(String taskManager){
		this.taskManager = taskManager;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 父级任务编号
	 * @param parentTaskNum String
	 */
	public void setParentTaskNum(String parentTaskNum){
		this.parentTaskNum = parentTaskNum;
	}



	/**
	 * 功能：设置SRV_TASKINFO属性 服务类型
	 * @param serviceTypeCode String
	 */
	public void setServiceTypeCode(String serviceTypeCode){
		this.serviceTypeCode = serviceTypeCode;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 可计费
	 * @param chargeableFlag String
	 */
	public void setChargeableFlag(String chargeableFlag){
		this.chargeableFlag = chargeableFlag;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 可资本化
	 * @param billableFlag String
	 */
	public void setBillableFlag(String billableFlag){
		this.billableFlag = billableFlag;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 是否为共同任务
	 * @param costFlag String
	 */
	public void setCostFlag(String costFlag){
		this.costFlag = costFlag;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 部门
	 * @param attribute1 String
	 */
	public void setAttribute1(String attribute1){
		this.attribute1 = attribute1;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 地点
	 * @param attribute2 String
	 */
	public void setAttribute2(String attribute2){
		this.attribute2 = attribute2;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 备注
	 * @param attribute3 String
	 */
	public void setAttribute3(String attribute3){
		this.attribute3 = attribute3;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 其他信息
	 * @param attribute4 String
	 */
	public void setAttribute4(String attribute4){
		this.attribute4 = attribute4;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 其他信息
	 * @param attribute5 String
	 */
	public void setAttribute5(String attribute5){
		this.attribute5 = attribute5;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 其他信息
	 * @param attribute6 String
	 */
	public void setAttribute6(String attribute6){
		this.attribute6 = attribute6;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 PM产品来源
	 * @param pmProductCode String
	 */
	public void setPmProductCode(String pmProductCode){
		this.pmProductCode = pmProductCode;
	}

	/**
	 * 功能：设置SRV_TASKINFO属性 PM来源参考
	 * @param pmTaskReference String
	 */
	public void setPmTaskReference(String pmTaskReference){
		this.pmTaskReference = pmTaskReference;
	}


	/**
	 * 功能：获取SRV_TASKINFO属性 任务所属组织 名称
	 * @return String
	 */
	public String getOrgName() {
		return this.orgName;
	}


	/**
	 * 功能：获取SRV_TASKINFO属性 项目编号
	 * @return String
	 */
	public String getSegment1() {
		return this.segment1;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 任务编号
	 * @return String
	 */
	public String getTaskNumber() {
		return this.taskNumber;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 任务名称
	 * @return String
	 */
	public String getTaskName() {
		return this.taskName;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 描述
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 任务经理
	 * @return String
	 */
	public String getTaskManager() {
		return this.taskManager;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 父级任务编号
	 * @return String
	 */
	public String getParentTaskNum() {
		return this.parentTaskNum;
	}



	/**
	 * 功能：获取SRV_TASKINFO属性 服务类型
	 * @return String
	 */
	public String getServiceTypeCode() {
		return this.serviceTypeCode;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 可计费
	 * @return String
	 */
	public String getChargeableFlag() {
		return this.chargeableFlag;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 可资本化
	 * @return String
	 */
	public String getBillableFlag() {
		return this.billableFlag;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 是否为共同任务
	 * @return String
	 */
	public String getCostFlag() {
		return this.costFlag;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 部门
	 * @return String
	 */
	public String getAttribute1() {
		return this.attribute1;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 地点
	 * @return String
	 */
	public String getAttribute2() {
		return this.attribute2;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 备注
	 * @return String
	 */
	public String getAttribute3() {
		return this.attribute3;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 其他信息
	 * @return String
	 */
	public String getAttribute4() {
		return this.attribute4;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 其他信息
	 * @return String
	 */
	public String getAttribute5() {
		return this.attribute5;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 其他信息
	 * @return String
	 */
	public String getAttribute6() {
		return this.attribute6;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 PM产品来源
	 * @return String
	 */
	public String getPmProductCode() {
		return this.pmProductCode;
	}

	/**
	 * 功能：获取SRV_TASKINFO属性 PM来源参考
	 * @return String
	 */
	public String getPmTaskReference() {
		return this.pmTaskReference;
	}



	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the parentTaskId
	 */
	public String getParentTaskId() {
		return parentTaskId;
	}

	/**
	 * @param parentTaskId the parentTaskId to set
	 */
	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the wbsLevel
	 */
	public String getWbsLevel() {
		return wbsLevel;
	}

	/**
	 * @param wbsLevel the wbsLevel to set
	 */
	public void setWbsLevel(String wbsLevel) {
		this.wbsLevel = wbsLevel;
	}

	/**
	 * @return the ouOption
	 */
	public String getOuOption() {
		return ouOption;
	}

	/**
	 * @param ouOption the ouOption to set
	 */
	public void setOuOption(String ouOption) {
		this.ouOption = ouOption;
	}

	public String getAssetsType() {
		return assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getCompletionDate() {
		return completionDate;
	}


	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}


	public String getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}


	public String getLastUpdateDate() {
		return lastUpdateDate;
	}


	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}


	public String getStartLastUpdatDate() {
		return startLastUpdatDate;
	}


	public void setStartLastUpdatDate(String startLastUpdatDate) {
		this.startLastUpdatDate = startLastUpdatDate;
	}


	public String getEndLastUpdatDate() {
		return endLastUpdatDate;
	}


	public void setEndLastUpdatDate(String endLastUpdatDate) {
		this.endLastUpdatDate = endLastUpdatDate;
	}

}