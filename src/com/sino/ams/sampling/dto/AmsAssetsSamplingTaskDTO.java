package com.sino.ams.sampling.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 资产抽查任务表 AmsAssetsSamplingTask</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsAssetsSamplingTaskDTO extends CommonRecordDTO{

	private String taskId = "";
	private String taskNo = "";
	private String taskName = "";
	private String taskDesc = "";
	private int sampledOu = SyBaseSQLUtil.NULL_INT_VALUE;
	private String sampledOuName = "";
	private String samplingType = "";
	private String samplingTypeValue = "";
    private int samplingRatio;
	private int createdOu = SyBaseSQLUtil.NULL_INT_VALUE;
	private String createdOuName = "";
	private String taskStatus = "";
	private String taskStatusValue = "";
	private SimpleCalendar closedDate = null;
	private int closedBy;
	private SimpleCalendar openedDate = null;
	private int openedBy;
	private SimpleCalendar canceledDate = null;
	private int canceledBy;
	private String createdUser = "";
	private String closedUser = "";
	private String openedUser = "";
	private String canceledUser = "";
	private String samplingTypeOpt = "";
	private String taskStatusOpt = "";
	private String sampledOuOpt = "";

	private SimpleCalendar fromDate = null;
	private SimpleCalendar toDate = null;
	private String leftOuOpt = "";//可选分配OU选项
	private String taskTreeName = "";//树形构造产生的参数，构成方式为TASK_NO[TASK_NAME]

    private String seachType = "";//判断是查询还是维护

    public AmsAssetsSamplingTaskDTO() {
		super();
		this.closedDate = new SimpleCalendar();
		this.openedDate = new SimpleCalendar();
		this.canceledDate = new SimpleCalendar();

		this.fromDate = new SimpleCalendar();
		this.toDate = new SimpleCalendar();
	}

    public String getSeachType() {
        return seachType;
    }

    public void setSeachType(String seachType) {
        this.seachType = seachType;
    }

    public int getSamplingRatio() {
        return samplingRatio;
    }

    public void setSamplingRatio(int samplingRatio) {
        this.samplingRatio = samplingRatio;
    }

	public void setTaskId(String taskId){
		this.taskId = taskId;
	}

	public void setTaskNo(String taskNo){
		this.taskNo = taskNo;
	}

	public void setTaskDesc(String taskDesc){
		this.taskDesc = taskDesc;
	}

	public void setSampledOu(int sampledOu){
		this.sampledOu = sampledOu;
	}

	public void setSamplingType(String samplingType){
		this.samplingType = samplingType;
	}

	public void setCreatedOu(int createdOu){
		this.createdOu = createdOu;
	}

	public void setTaskStatus(String taskStatus){
		this.taskStatus = taskStatus;
	}

	public void setClosedDate(String closedDate) throws CalendarException{
		this.closedDate.setCalendarValue(closedDate);
	}

	public void setClosedBy(int closedBy){
		this.closedBy = closedBy;
	}

	public void setOpenedDate(String openedDate) throws CalendarException{
		this.openedDate.setCalendarValue(openedDate);
	}

	public void setCanceledDate(String canceledDate) throws CalendarException{
		this.canceledDate.setCalendarValue(canceledDate);
	}

	public void setOpenedBy(int openedBy){
		this.openedBy = openedBy;
	}

	public void setCanceledBy(int canceledBy) {
		this.canceledBy = canceledBy;
	}

	public void setCanceledUser(String canceledUser) {
		this.canceledUser = canceledUser;
	}

	public void setClosedUser(String closedUser) {
		this.closedUser = closedUser;
	}

	public void setCreatedOuName(String createdOuName) {
		this.createdOuName = createdOuName;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setOpenedUser(String openedUser) {
		this.openedUser = openedUser;
	}

	public void setSampledOuName(String sampledOuName) {
		this.sampledOuName = sampledOuName;
	}

	public void setSamplingTypeOpt(String samplingTypeOpt) {
		this.samplingTypeOpt = samplingTypeOpt;
	}

	public void setSamplingTypeValue(String samplingTypeValue) {
		this.samplingTypeValue = samplingTypeValue;
	}

	public void setTaskStatusOpt(String taskStatusOpt) {
		this.taskStatusOpt = taskStatusOpt;
	}

	public void setTaskStatusValue(String taskStatusValue) {
		this.taskStatusValue = taskStatusValue;
	}

	public void setSampledOuOpt(String sampledOuOpt) {
		this.sampledOuOpt = sampledOuOpt;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setLeftOuOpt(String leftOuOpt) {
		this.leftOuOpt = leftOuOpt;
	}

	public void setTaskTreeName(String taskTreeName) {
		this.taskTreeName = taskTreeName;
	}

	public void setFromDate(String fromDate) throws CalendarException {
		this.fromDate.setCalendarValue(fromDate);
	}

	public void setToDate(String toDate) throws CalendarException {
		this.toDate.setCalendarValue(toDate);
	}

	public String getTaskId() {
		return this.taskId;
	}

	public String getTaskNo() {
		if(taskNo.equals("")){
			int index = taskTreeName.indexOf("[");
			if (index > -1) {
				setTaskNo(taskTreeName.substring(0, index));
			}
		}
		return this.taskNo;
	}

	public String getTaskDesc() {
		return this.taskDesc;
	}

	public int getSampledOu() {
		return this.sampledOu;
	}

	public String getSamplingType() {
		return this.samplingType;
	}

	public int getCreatedOu() {
		return this.createdOu;
	}

	public String getTaskStatus() {
		return this.taskStatus;
	}

	public SimpleCalendar getClosedDate() throws CalendarException {
		closedDate.setCalPattern(getCalPattern());
		return this.closedDate;
	}

	public int getClosedBy() {
		return this.closedBy;
	}

	public SimpleCalendar getOpenedDate() throws CalendarException {
		openedDate.setCalPattern(getCalPattern());
		return this.openedDate;
	}

	public SimpleCalendar getCanceledDate() throws CalendarException {
		canceledDate.setCalPattern(getCalPattern());
		return this.canceledDate;
	}

	public int getOpenedBy() {
		return this.openedBy;
	}

	public int getCanceledBy() {
		return canceledBy;
	}

	public String getCanceledUser() {
		return canceledUser;
	}

	public String getClosedUser() {
		return closedUser;
	}

	public String getCreatedOuName() {
		return createdOuName;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public String getOpenedUser() {
		return openedUser;
	}

	public String getSampledOuName() {
		return sampledOuName;
	}

	public String getSamplingTypeOpt() {
		return samplingTypeOpt;
	}

	public String getSamplingTypeValue() {
		return samplingTypeValue;
	}

	public String getTaskStatusOpt() {
		return taskStatusOpt;
	}

	public String getTaskStatusValue() {
		return taskStatusValue;
	}

	public SimpleCalendar getFromDate() throws CalendarException {
		fromDate.setCalPattern(getCalPattern());
		return fromDate;
	}

	public SimpleCalendar getToDate() throws CalendarException {
		toDate.setCalPattern(getCalPattern());
		return toDate;
	}

	public String getSampledOuOpt() {
		return sampledOuOpt;
	}

	public String getTaskName() {
		if(taskName.equals("")){
			int startIndex = taskTreeName.indexOf("[");
			int endIndex = taskTreeName.indexOf("]");
			if (endIndex > startIndex && startIndex > -1) {
				setTaskName(taskTreeName.substring(startIndex + 1, endIndex));
			}
		}
		return taskName;
	}

	public String getLeftOuOpt() {
		return leftOuOpt;
	}

	public String getTaskTreeName() {
		return taskTreeName;
	}

	public static void main(String[] args) throws Exception{
		SimpleCalendar c = new SimpleCalendar();
		c.setCalendarValue(System.currentTimeMillis());
		c.setCalPattern(LINE_PATTERN);
		System.out.println(c.getCalendarValue());
	}
}
