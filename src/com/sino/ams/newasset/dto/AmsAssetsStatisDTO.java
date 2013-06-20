package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

public class AmsAssetsStatisDTO extends CommonRecordDTO {
   private String company;
   private String count;
   private SimpleCalendar  fromDate;
   private SimpleCalendar  toDate;
   private int organizationId;
   private String assetsType = "";
   public AmsAssetsStatisDTO(){
	   super();
	   this.fromDate=new SimpleCalendar();
	   this.toDate=new SimpleCalendar();
   }
   public String getCompany() {
	  return company;
   }
   public void setCompany(String company) {
	  this.company = company;
   }
   public String getCount() {
	  return count;
   }
   public void setCount(String count) {
	  this.count = count;
   }
   public SimpleCalendar getFromDate() throws CalendarException {
	  fromDate.setCalPattern(getCalPattern());
	  return fromDate;
   }
   public void setFromDate(String fromDate) throws CalendarException {
	  this.fromDate.setCalendarValue(fromDate);
   }
   public int getOrganizationId() {
	  return organizationId;
   }
   public void setOrganizationId(int organizationId) {
	  this.organizationId = organizationId;
   }
   public SimpleCalendar getToDate() throws CalendarException {
	  toDate.setCalPattern(getCalPattern());
	  return toDate;
}
   public void setToDate(String toDate) throws CalendarException {
	  this.toDate.setCalendarValue(toDate);
}
public String getAssetsType() {
	return assetsType;
}
public void setAssetsType(String assetsType) {
	this.assetsType = assetsType;
}

}
