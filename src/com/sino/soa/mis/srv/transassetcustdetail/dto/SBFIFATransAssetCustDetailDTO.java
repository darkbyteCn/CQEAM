package com.sino.soa.mis.srv.transassetcustdetail.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-13
 * Time: 14:18:55
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFATransAssetCustDetailDTO extends CheckBoxDTO {
    private String envCode = "";
    private String bookTypeCode = "";
    private String projectNumber = "";
    private String taskNum = "";
//    private SimpleCalendar capitalizedDateFrom = null;
//    private SimpleCalendar capitalizedDateTo = null;
    private String capitalizedDateFrom = "";
    private String capitalizedDateTo = "";
    private String orgOption = "";

    public SBFIFATransAssetCustDetailDTO(){
		super();
//		capitalizedDateFrom = new SimpleCalendar();
//		capitalizedDateTo = new SimpleCalendar();
	}

    public String getOrgOption() {
        return orgOption;
    }

    public void setOrgOption(String orgOption) {
        this.orgOption = orgOption;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public String getCapitalizedDateFrom() {
        return capitalizedDateFrom;
    }

    public void setCapitalizedDateFrom(String capitalizedDateFrom) {
        this.capitalizedDateFrom = capitalizedDateFrom;
    }

    public String getCapitalizedDateTo() {
        return capitalizedDateTo;
    }

    public void setCapitalizedDateTo(String capitalizedDateTo) {
        this.capitalizedDateTo = capitalizedDateTo;
    }

    //    public SimpleCalendar getCapitalizedDateFrom()throws CalendarException {
//		capitalizedDateFrom.setCalPattern(getCalPattern());
//		return this.capitalizedDateFrom;
//	}
//
//	public void setCapitalizedDateFrom(String capitalizedDateFrom) throws CalendarException {
//		this.capitalizedDateFrom.setCalendarValue(capitalizedDateFrom);
//	}
//
//    public SimpleCalendar getCapitalizedDateTo()throws CalendarException {
//		capitalizedDateTo.setCalPattern(getCalPattern());
//		return this.capitalizedDateTo;
//	}
//
//	public void setCapitalizedDateTo(String  capitalizedDateTo) throws CalendarException {
//		this.capitalizedDateTo.setCalendarValue(capitalizedDateTo);
//	}
}
