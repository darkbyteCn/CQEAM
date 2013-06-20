package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 盘点任务表 EamCheckTask</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EamCheckTaskDTO extends CommonRecordDTO{

	private String checkTaskId = "";
	private String taskName = "";
	private String checkType = "";
	private String checkTypeValue = "";
	private String checkTypeOpt = "";
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String remark = "";
	private String taskStatus = "";
	private String taskStatusValue = "";
	private String taskStatusOpt = "";
	private String organizationName = "";
	private String createdUser = "";
	private String updateUser = "";
	private String orgOption = "";

	private SimpleCalendar fromDate = null;
	private SimpleCalendar toDate = null;
	private String objectCategory = "";

	public EamCheckTaskDTO() {
		super();
		this.fromDate = new SimpleCalendar();
		this.toDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置盘点任务表属性 盘点任务ID
	 * @param checkTaskId String
	 */
	public void setCheckTaskId(String checkTaskId){
		this.checkTaskId = checkTaskId;
	}

	/**
	 * 功能：设置盘点任务表属性 盘点任务名称
	 * @param taskName String
	 */
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

	/**
	 * 功能：设置盘点任务表属性 盘点类型（DZYH/ASSETS/YQYB）
	 * @param checkType String
	 */
	public void setCheckType(String checkType){
		this.checkType = checkType;
	}

	/**
	 * 功能：设置盘点任务表属性 OU组织ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置盘点任务表属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public void setTaskStatusValue(String taskStatusValue) {

		this.taskStatusValue = taskStatusValue;
	}

	public void setTaskStatusOpt(String taskStatusOpt) {
		this.taskStatusOpt = taskStatusOpt;
	}

	public void setCheckTypeValue(String checkTypeValue) {
		this.checkTypeValue = checkTypeValue;
	}

	public void setOrgOption(String orgOption) {
		this.orgOption = orgOption;
	}

	public void setCheckTypeOpt(String checkTypeOpt) {
		this.checkTypeOpt = checkTypeOpt;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	/**
	 * 功能：设置开始日期
	 * @param fromDate String
	 * @throws CalendarException
	 */
	public void setFromDate(String fromDate) throws CalendarException {
		this.fromDate.setCalendarValue(fromDate);
	}


	/**
	 * 功能：设置截至日期
	 * @param toDate String
	 * @throws CalendarException
	 */
	public void setToDate(String toDate) throws CalendarException {
		this.toDate.setCalendarValue(toDate);
	}


	/**
	 * 功能：获取盘点任务表属性 盘点任务ID
	 * @return String
	 */
	public String getCheckTaskId() {
		return this.checkTaskId;
	}

	/**
	 * 功能：获取盘点任务表属性 盘点任务名称
	 * @return String
	 */
	public String getTaskName() {
		return this.taskName;
	}

	/**
	 * 功能：获取盘点任务表属性 盘点类型（DZYH/ASSETS/YQYB）
	 * @return String
	 */
	public String getCheckType() {
		return this.checkType;
	}

	/**
	 * 功能：获取盘点任务表属性 OU组织ID
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取盘点任务表属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public String getTaskStatusValue() {
		return taskStatusValue;
	}

	public String getTaskStatusOpt() {
		return taskStatusOpt;
	}

	public String getCheckTypeValue() {
		if(checkTypeValue.equals("")){
			String[] list1 = LvecDicts.TASK_TYPE1_LIST;
			String[] list2 = LvecDicts.TASK_TYPE2_LIST;
			for(int i = 0; i < list1.length; i++){
				if(checkType.equals(list1[i])){
					checkTypeValue = list2[i];
					break;
				}
			}
		}
		return checkTypeValue;
	}

	public String getOrgOption() {
		return orgOption;
	}

	public String getCheckTypeOpt() {
		return checkTypeOpt;
	}

	/**
	 * 功能：获取开始日期
	 * @return SimpleCalendar
	 * @throws CalendarException
	 */
	public SimpleCalendar getFromDate() throws CalendarException {
		fromDate.setCalPattern(getCalPattern());
		return fromDate;
	}

	/**
	 * 功能：获取截至日期
	 * @return SimpleCalendar
	 * @throws CalendarException
	 */
	public SimpleCalendar getToDate() throws CalendarException {
		toDate.setCalPattern(getCalPattern());
		return toDate;
	}

	public String getObjectCategory() {
		return objectCategory;
	}

	public SimpleCalendar getSQLToDate(){
		return getLastValueOfDate("toDate");
	}
}
