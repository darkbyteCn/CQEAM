package com.sino.soa.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.constant.calen.DateConstant;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * User: zhoujs
 * Date: 2009-4-22 21:34:46
 * Function:
 */
public class XMLGregorianCalendarUtil {
    public static String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov","Dec"};

    public static String getSimpleCalendarString(XMLGregorianCalendar calendar) {
        String calendarString = "";
        if (calendar != null) {
            calendarString = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay();
        }
        return calendarString;
    }

    public static XMLGregorianCalendar getXMLGregorianCalendar(SimpleCalendar simpleCalendar) throws DatatypeConfigurationException, CalendarException {
        XMLGregorianCalendar calendar = null;
        if (simpleCalendar != null) {
            DatatypeFactory factory = DatatypeFactory.newInstance();
            calendar = factory.newXMLGregorianCalendar(simpleCalendar.get(DateConstant.YEAR), simpleCalendar.get(DateConstant.MONTH), simpleCalendar.get(DateConstant.DATE), 0, 0, 0, 0, 480);
        }
        return calendar;
    }

    public static XMLGregorianCalendar getXMLGregorianCalendar(String strDate) throws DatatypeConfigurationException, CalendarException {
        XMLGregorianCalendar calendar = null;
        if (StrUtil.isNotEmpty(strDate)) {
            SimpleCalendar simpleCalendar = new SimpleCalendar(strDate);
            DatatypeFactory factory = DatatypeFactory.newInstance();
            calendar = factory.newXMLGregorianCalendar(simpleCalendar.get(DateConstant.YEAR), simpleCalendar.get(DateConstant.MONTH), simpleCalendar.get(DateConstant.DATE), 0, 0, 0, 0, 480);
        }
        return calendar;
    }

    public static String getPeriodName(String misPeriodName) {
        String periodName = "";
        String year = "";
        String month = "";
        int idx = misPeriodName.indexOf("-");
        if (idx > -1) {
            month = misPeriodName.substring(0, idx);
            year = misPeriodName.substring(idx + 1, misPeriodName.length());
            periodName = "20" + year;
            for (int i = 0; i < months.length; i++) {
                if (month.equalsIgnoreCase(months[i])) {
                    if (i < 9) {
                        periodName += "-0" + (i+1);
                    } else {
                        periodName += "-" + (i+1);
                    }
                    break;
                }
            }
        }

        return periodName;
    }

}
