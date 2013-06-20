package com.sino.ams.prematch.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: MIS转资准备清单 AmsPaAssets</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsPaAssetsDTO extends CommonRecordDTO{

	private String tagNumber = "";
	private String assetsDescription = "";
	private String modelNumber = "";
	private int projectId;
	private String projectNumber = "";
	private String projectName = "";
	private String assetsLocationCode = "";
	private String assetsLocation = "";
	private int assignedToPersonId;
	private String assignedToNumber = "";
	private String assignedToName = "";
	private SimpleCalendar datePlacedInService = null;
	private String bookTypeCode = "";
	private int assetUnits;
	private int organizationId;
	private int faAssetId ;
	private int taskId;
	private String taskName = "";
	private String taskNumber = "";
	private int paAssetId;
	private String transferFlag = "";
	private String reverseFlag = "";
	private String faCategoryCode = "";
	private String segment2 = "";

	public AmsPaAssetsDTO() {
		super();
		this.datePlacedInService = new SimpleCalendar();
	}

	/**
	 * 功能：设置MIS转资准备清单属性 标签号
	 * @param tagNumber String
	 */
	public void setTagNumber(String tagNumber){
		this.tagNumber = tagNumber;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 资产描述
	 * @param assetsDescription String
	 */
	public void setAssetsDescription(String assetsDescription){
		this.assetsDescription = assetsDescription;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 型号
	 * @param modelNumber String
	 */
	public void setModelNumber(String modelNumber){
		this.modelNumber = modelNumber;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 项目编号
	 * @param projectId String
	 */
	public void setProjectId(int projectId){
		this.projectId = projectId;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 MIS总账项目段编号
	 * @param projectNumber String
	 */
	public void setProjectNumber(String projectNumber){
		this.projectNumber = projectNumber;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 项目ID
	 * @param projectName String
	 */
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 资产地点代码组合
	 * @param assetsLocationCode String
	 */
	public void setAssetsLocationCode(String assetsLocationCode){
		this.assetsLocationCode = assetsLocationCode;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 资产地点
	 * @param assetsLocation String
	 */
	public void setAssetsLocation(String assetsLocation){
		this.assetsLocation = assetsLocation;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 责任人ID
	 * @param assignedToPersonId String
	 */
	public void setAssignedToPersonId(int assignedToPersonId){
		this.assignedToPersonId = assignedToPersonId;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 责任人员工号
	 * @param assignedToNumber String
	 */
	public void setAssignedToNumber(String assignedToNumber){
		this.assignedToNumber = assignedToNumber;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 责任人姓名
	 * @param assignedToName String
	 */
	public void setAssignedToName(String assignedToName){
		this.assignedToName = assignedToName;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 启用日期
	 * @param datePlacedInService SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDatePlacedInService(String datePlacedInService) throws CalendarException{
		this.datePlacedInService.setCalendarValue(datePlacedInService);
	}

	/**
	 * 功能：设置MIS转资准备清单属性 资产账簿代码
	 * @param bookTypeCode String
	 */
	public void setBookTypeCode(String bookTypeCode){
		this.bookTypeCode = bookTypeCode;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 资产数量
	 * @param assetUnits String
	 */
	public void setAssetUnits(int assetUnits){
		this.assetUnits = assetUnits;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 OU组织ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 固定资产ID(转资后回写)
	 * @param faAssetId String
	 */
	public void setFaAssetId(int faAssetId){
		this.faAssetId = faAssetId;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 任务ID
	 * @param taskId String
	 */
	public void setTaskId(int taskId){
		this.taskId = taskId;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 任务名称
	 * @param taskName String
	 */
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 PA资产ID
	 * @param paAssetId String
	 */
	public void setPaAssetId(int paAssetId){
		this.paAssetId = paAssetId;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 转资标志
	 * @param transferFlag String
	 */
	public void setTransferFlag(String transferFlag){
		this.transferFlag = transferFlag;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 冲销标志
	 * @param reverseFlag String
	 */
	public void setReverseFlag(String reverseFlag){
		this.reverseFlag = reverseFlag;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 资产类别
	 * @param faCategoryCode String
	 */
	public void setFaCategoryCode(String faCategoryCode){
		this.faCategoryCode = faCategoryCode;
	}

	/**
	 * 功能：设置MIS转资准备清单属性 资产类别2代码
	 * @param segment2 String
	 */
	public void setSegment2(String segment2){
		this.segment2 = segment2;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}


	/**
	 * 功能：获取MIS转资准备清单属性 标签号
	 * @return String
	 */
	public String getTagNumber() {
		return this.tagNumber;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 资产描述
	 * @return String
	 */
	public String getAssetsDescription() {
		return this.assetsDescription;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 型号
	 * @return String
	 */
	public String getModelNumber() {
		return this.modelNumber;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 项目编号
	 * @return String
	 */
	public int getProjectId() {
		return this.projectId;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 MIS总账项目段编号
	 * @return String
	 */
	public String getProjectNumber() {
		return this.projectNumber;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 项目ID
	 * @return String
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 资产地点代码组合
	 * @return String
	 */
	public String getAssetsLocationCode() {
		return this.assetsLocationCode;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 资产地点
	 * @return String
	 */
	public String getAssetsLocation() {
		return this.assetsLocation;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 责任人ID
	 * @return String
	 */
	public int getAssignedToPersonId() {
		return this.assignedToPersonId;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 责任人员工号
	 * @return String
	 */
	public String getAssignedToNumber() {
		return this.assignedToNumber;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 责任人姓名
	 * @return String
	 */
	public String getAssignedToName() {
		return this.assignedToName;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDatePlacedInService() throws CalendarException {
		datePlacedInService.setCalPattern(getCalPattern());
		return this.datePlacedInService;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 资产账簿代码
	 * @return String
	 */
	public String getBookTypeCode() {
		return this.bookTypeCode;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 资产数量
	 * @return String
	 */
	public int getAssetUnits() {
		return this.assetUnits;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 OU组织ID
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 固定资产ID(转资后回写)
	 * @return String
	 */
	public int getFaAssetId() {
		return this.faAssetId;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 任务ID
	 * @return String
	 */
	public int getTaskId() {
		return this.taskId;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 任务名称
	 * @return String
	 */
	public String getTaskName() {
		return this.taskName;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 PA资产ID
	 * @return String
	 */
	public int getPaAssetId() {
		return this.paAssetId;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 转资标志
	 * @return String
	 */
	public String getTransferFlag() {
		return this.transferFlag;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 冲销标志
	 * @return String
	 */
	public String getReverseFlag() {
		return this.reverseFlag;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 资产类别
	 * @return String
	 */
	public String getFaCategoryCode() {
		return this.faCategoryCode;
	}

	/**
	 * 功能：获取MIS转资准备清单属性 资产类别2代码
	 * @return String
	 */
	public String getSegment2() {
		return this.segment2;
	}

	public String getTaskNumber() {
		return taskNumber;
	}
}
