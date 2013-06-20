package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Title: 工作时间总表 SfWorkSchedule
 * Description: 程序自动生成DTO数据传输对象
 * Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006
 * Company: 北京思诺博信息科技有限公司
 * @author 唐明胜
 * @version 1.0
 */

public class SfWorkScheduleDTO extends CheckBoxDTO {
	private int workScheduleId = 0;
	private String workScheduleName = "";
	private int workHourId;
	private String holidayName = "";
	private String memo = "";
	private int createdBy = -1;
	private String creationDate = "";
	private int lastUpdatedBy = -1;
	private String lastUpdateDate = "";
	private String workStr = "";
	private String workingDate = "";
	private String workBegin1 = "";
	private String workEnd1 = "";
	private String workBegin2 = "";
	private String workEnd2 = "";
	private String holidays = "";

	public String getWorkStr() {
		return workStr;
	}

	public void setWorkStr(String workStr) {
		this.workStr = workStr;
	}

	public int getWorkScheduleId() {
		return workScheduleId;
	}

	public void setWorkScheduleId(int workScheduleId) {
		this.workScheduleId = workScheduleId;
	}

	public String getWorkScheduleName() {
		return workScheduleName;
	}

	public void setWorkScheduleName(String workScheduleName) {
		this.workScheduleName = workScheduleName;
	}

	public int getWorkHourId() {
		return workHourId;
	}

	public void setWorkHourId(int workHourId) {
		this.workHourId = workHourId;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public int getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getWorkingDate() {
		return workingDate;
	}

    public String getWorkDataStr() {
        return "";
    }

    public void setWorkingDate(String workingDate) {
		this.workingDate = workingDate;
	}

	public String getWorkBegin1() {
		return workBegin1;
	}

	public void setWorkBegin1(String workBegin1) {
		this.workBegin1 = workBegin1;
	}

	public String getWorkEnd1() {
		return workEnd1;
	}

	public void setWorkEnd1(String workEnd1) {
		this.workEnd1 = workEnd1;
	}

	public String getWorkBegin2() {
		return workBegin2;
	}

	public void setWorkBegin2(String workBegin2) {
		this.workBegin2 = workBegin2;
	}

	public String getWorkEnd2() {
		return workEnd2;
	}

	public void setWorkEnd2(String workEnd2) {
		this.workEnd2 = workEnd2;
	}

	public String getHolidays() {
		return holidays;
	}

	public void setHolidays(String holidays) {
		this.holidays = holidays;
	}

}