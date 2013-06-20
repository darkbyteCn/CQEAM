package com.sino.ams.instrument.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

public class AmsInstrumentEamYbChkTaskDTO extends CommonRecordDTO {

	private String taskId = ""; //任务ID
	private String taskName = ""; //任务名称
	private String remark = ""; //备注
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE; //OU组织ID
	private String company = ""; //公司名称
	private String ouOption = "";//公司信息下拉框数据列表
	
	private String nameExist = ""; //检查任务代码是否已经存在
	
	private SimpleCalendar startDate = null; //开始时间
	private SimpleCalendar endDate = null; //结束时间
	
	public AmsInstrumentEamYbChkTaskDTO() {
		this.startDate = new SimpleCalendar();
		this.endDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 启用日期
	 * @param startDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartDate(String startDate) throws CalendarException {
		this.startDate.setCalendarValue(startDate);
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getStartDate() throws CalendarException {
		startDate.setCalPattern(getCalPattern());
		return this.startDate;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 结束日期
	 * @param endDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setEndDate(String endDate) throws CalendarException {
		this.endDate.setCalendarValue(endDate);
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 结束日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getEndDate() throws CalendarException {
		endDate.setCalPattern(getCalPattern());
		return this.endDate;
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOuOption() {
		return ouOption;
	}

	public void setOuOption(String ouOption) {
		this.ouOption = ouOption;
	}

	public String getNameExist() {
		return nameExist;
	}

	public void setNameExist(String nameExist) {
		this.nameExist = nameExist;
	}
	
}
