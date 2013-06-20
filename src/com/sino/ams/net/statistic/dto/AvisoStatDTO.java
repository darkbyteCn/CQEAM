package com.sino.ams.net.statistic.dto;

import java.util.Date;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: V-yuanshuai
 * Date: 2007-11-14
 * Time: 15:30:26
 * To change this template use File | Settings | File Templates.
 */
public class AvisoStatDTO extends CheckBoxDTO {
    private String qryType = "";
    private String year = "";
    private String month = "";
    private int weekNo = 1;
    private Date firDayOfWeek[] = new Date[6];
    private Date lasDayOfWeek[] = new Date[6];
    private int weekCount = 0;
    private SimpleCalendar startDate = null;
    private SimpleCalendar endDate = null;

    public AvisoStatDTO() {
        this.endDate = new SimpleCalendar();
        this.startDate = new SimpleCalendar();
    }

    public SimpleCalendar getStartDate() throws CalendarException {
        startDate.setCalPattern(getCalPattern());
        return this.startDate;
    }

    public void setStartDate(String startDate) throws CalendarException {
        this.startDate.setCalendarValue(startDate);
    }

    public SimpleCalendar getEndDate() throws CalendarException {
        endDate.setCalPattern(getCalPattern());
        return this.endDate;
    }

    public void setEndDate(String endDate) throws CalendarException {
        this.endDate.setCalendarValue(endDate);
    }

    public int getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(int weekCount) {
        this.weekCount = weekCount;
    }

    public Date[] getFirDayOfWeek() {
        return firDayOfWeek;
    }

    public void setFirDayOfWeek(Date[] firDayOfWeek) {
        this.firDayOfWeek = firDayOfWeek;
    }

    public Date[] getLasDayOfWeek() {
        return lasDayOfWeek;
    }

    public void setLasDayOfWeek(Date[] lasDayOfWeek) {
        this.lasDayOfWeek = lasDayOfWeek;
    }

    public int getWeekNo() {
        return weekNo++;
    }

    public void setWeekNo(int weekNo) {
        this.weekNo = weekNo;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getQryType() {
        return qryType;
    }

    public void setQryType(String qryType) {
        this.qryType = qryType;
    }

}
