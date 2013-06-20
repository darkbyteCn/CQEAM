package com.sino.ams.net.reportforms.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-11-12
 * Time: 10:30:09
 * To change this template use File | Settings | File Templates.
 */
public class SitusStatisticsDTO extends CheckBoxDTO {
    private String workorderObjectName = "";
     private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
    private int organizationId;
    private String objectCategory = "";

    public String getObjectCategory() {
        return objectCategory;
    }

    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }
//    private String fromDate = "";
//    private String toDate = "";

//    private Timestamp fromDate = null;
//    private Timestamp toDate = null;

      public SitusStatisticsDTO() {
		this.fromDate = new SimpleCalendar();
		this.toDate = new SimpleCalendar();
	}
//
	public SimpleCalendar getToDate() throws CalendarException {
		toDate.setCalPattern(getCalPattern());
        return this.toDate;
    }

    public void setToDate(String toDate)throws CalendarException{
		this.toDate.setCalendarValue(toDate);
	}

    public SimpleCalendar getFromDate() throws CalendarException {
		fromDate.setCalPattern(getCalPattern());
        return this.fromDate;
    }

    public void setFromDate(String fromDate)throws CalendarException{
		this.fromDate.setCalendarValue(fromDate);
	}

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public int getOrganizationId(){
            return this.organizationId;
        }


       public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

//       public String getFromDate() {
//        return fromDate;
//    }
//
//    public void setCreationDate(String fromDate) {
//        if (!fromDate.equals("")) {
//            fromDate += " 00:00:00";
//        }
//        this.fromDate = fromDate;
//    }
//
//    public String getToDate() {
//        return toDate;
//    }
//
//    public void setLastUpdateDate(String lastUpdateDate) {
//        if (!toDate.equals("")) {
//            toDate += " 23:59:59";
//        }
//        this.toDate = lastUpdateDate;
//    }
//     public Timestamp getFromDate() {
//        return fromDate;
//    }
//
//    public void setFromDate(String fromDate) throws CalendarException {
//        if (!StrUtil.isEmpty(fromDate)) {
//            SimpleDate startDate = new SimpleDate(fromDate);
//            SimpleTime startTime = null;
//            try {
//                startTime = SimpleTime.getStartTime();
//            } catch (TimeException e) {
//                throw new CalendarException(e.getMessage());
//            }
//            SimpleCalendar cal = new SimpleCalendar(startDate, startTime);
//            this.fromDate = cal.getSQLTimestamp();
//        }
//    }
//
//    public Timestamp getToDate() {
//        return toDate;
//    }
//
//    public void setToDate(String toDate) throws CalendarException {
//        if (!StrUtil.isEmpty(toDate)) {
//            SimpleDate startDate = new SimpleDate(toDate);
//            SimpleTime startTime = null;
//            try {
//                startTime = SimpleTime.getEndTime();
//            } catch (TimeException e) {
//                throw new CalendarException(e.getMessage());
//            }
//            SimpleCalendar cal = new SimpleCalendar(startDate, startTime);
//            this.toDate = cal.getSQLTimestamp();
//        }
//    }
}
