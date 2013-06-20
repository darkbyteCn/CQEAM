package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-17
 * Time: 16:42:06
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsCJYCDTO extends CommonRecordDTO {
    private String bookTypeCode = "";
    private String catSegment2 = "";
    private String catSegment1 = "";
    private String fromMonth = "";
    private String toMonth = "";
    private String bookTypeName = "";
    private String faCategory2 = "";
    private String faCategory1 = "";
    private String faCategory3 = "";
    private String standardsYears = "";
    private String newYears = "";
    private String faCatName2 = "";
    private int organizationId;
    private String assetsDescription = "";
    private String modelNumber = "";
    private String segment1 = "";
    private String segment2 = "";
    private String deptCode = "";
    private String deptName = "";
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;

    public AmsAssetsCJYCDTO() {
        super();
        this.fromDate = new SimpleCalendar();
        this.toDate = new SimpleCalendar();
    }

    public void setFromDate(String fromDate) throws CalendarException {
        this.fromDate.setCalendarValue(fromDate);
    }

    public SimpleCalendar getFromDate() throws CalendarException {
        fromDate.setCalPattern(getCalPattern());
        return this.fromDate;
    }

    public void setToDate(String toDate) throws CalendarException {
        this.toDate.setCalendarValue(toDate);
    }

    public SimpleCalendar getToDate() throws CalendarException {
        toDate.setCalPattern(getCalPattern());
        return this.toDate;
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

    public String getSegment2() {
        return segment2;
    }

    public void setSegment2(String segment2) {
        this.segment2 = segment2;
    }

    public String getFaCategory1() {
        return faCategory1;
    }

    public void setFaCategory1(String faCategory1) {
        this.faCategory1 = faCategory1;
    }

    public String getFaCategory3() {
        return faCategory3;
    }

    public void setFaCategory3(String faCategory3) {
        this.faCategory3 = faCategory3;
    }

    public String getAssetsDescription() {
        return assetsDescription;
    }

    public void setAssetsDescription(String assetsDescription) {
        this.assetsDescription = assetsDescription;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSegment1() {
        return segment1;
    }

    public void setSegment1(String segment1) {
        this.segment1 = segment1;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getFaCategory2() {
        return faCategory2;
    }

    public void setFaCategory2(String faCategory2) {
        this.faCategory2 = faCategory2;
    }

    public String getStandardsYears() {
        return standardsYears;
    }

    public void setStandardsYears(String standardsYears) {
        this.standardsYears = standardsYears;
    }

    public String getNewYears() {
        return newYears;
    }

    public void setNewYears(String newYears) {
        this.newYears = newYears;
    }

    public String getFaCatName2() {
        return faCatName2;
    }

    public void setFaCatName2(String faCatName2) {
        this.faCatName2 = faCatName2;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public String getFromMonth() {
        return fromMonth;
    }

    public void setFromMonth(String fromMonth) {
        this.fromMonth = fromMonth;
    }

    public String getToMonth() {
        return toMonth;
    }

    public void setToMonth(String toMonth) {
        this.toMonth = toMonth;
    }

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public String getCatSegment2() {
        return catSegment2;
    }

    public void setCatSegment2(String catSegment2) {
        this.catSegment2 = catSegment2;
    }

    public String getCatSegment1() {
        return catSegment1;
    }

    public void setCatSegment1(String catSegment1) {
        this.catSegment1 = catSegment1;
    }
}
