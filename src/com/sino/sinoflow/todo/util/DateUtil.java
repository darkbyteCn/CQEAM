package com.sino.sinoflow.todo.util; 

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;

/**
 * 
 * @系统名称:  
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: wangwenbiaogy
 * @创建时间: Oct 24, 2011
 */ 
public class DateUtil {
	public static java.sql.Timestamp getSqlCommonDate(String date) throws ParseException {
		if (null == date || date.equals("")) {
			return null;
		} else {
			return string2Time(date);
		}
	}

	public static java.sql.Date getSqlDate(String date) {
		if (null == date || date.equals("")) {
			return null;
		} else {
			return java.sql.Date.valueOf(date);
		}
	}

	public static java.sql.Timestamp getSqlFromDate(String date) {
		if (null == date || date.equals("")) {
			return null;
		} else {
			try {
				return string2Time(date + " 00:00:00");
			} catch (ParseException e) {
				return null;
			}
		}
	}

	public static java.sql.Timestamp getSqlToDate(String date) {
		if (null == date || date.equals("")) {
			return null;
		} else {
			try {
				return string2Time(date + " 23:59:59");
			} catch (ParseException e) {
				return null;
			}
		}
	}

	/**
	 * method 将字符串类型的日期转换为一个timestamp（时间戳记java.sql.Timestamp）
	 * 
	 * @param dateString 需要转换为timestamp的字符串
	 * @return dataTime timestamp
	 * @throws ParseException
	 */
	public static java.sql.Timestamp string2Time(String dateString) throws ParseException {
		if (StrUtil.isEmpty(dateString)) {
			return new java.sql.Timestamp((new Date()).getTime());
		} else {
			DateFormat dateFormat;
			dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);// 设定格式
			// dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
			dateFormat.setLenient(false);
			java.util.Date timeDate = new Date();// util类型
			try {
				timeDate = dateFormat.parse(dateString);
			} catch (ParseException e) {
				Logger.logError(e);
				throw e;
			}
			return new java.sql.Timestamp(timeDate.getTime());
		}
	}
	
	public static java.sql.Timestamp string2Date(String dateString) throws ParseException {
		if (StrUtil.isEmpty(dateString)) {
			return new java.sql.Timestamp((new Date()).getTime());
		} else {
			DateFormat dateFormat;
			dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);// 设定格式
			// dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
			dateFormat.setLenient(false);
			java.util.Date timeDate = new Date();// util类型
			try {
				timeDate = dateFormat.parse(dateString);
			} catch (ParseException e) {
				Logger.logError(e);
				throw e;
			}
			return new java.sql.Timestamp(timeDate.getTime());
		}
	}

	public static String getCurDateStr() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	public static String getCurDateTimeStr() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return df.format(date);
	}

	/**
	 * 功能：对传入对象按照指定格式格式化指定属性字段。
	 * 
	 * @param obj Object 符合JavaBean规范的对象
	 * @param dateFields String 对象的属性字段，多个字段则用分号连接
	 * @param datePattern String 日历格式
	 * @see com.sino.base.constant.calen.CalendarConstant
	 * @return Object 返回格式化之后的对象
	 */
	public static Object formatCalendar(Object obj, String dateFields, String datePattern) {
		boolean isValidCalendar = CalendarUtil.isValidCalendar(datePattern);
		boolean isValidDatePattern = CalendarUtil.isValidDatePattern(datePattern);
		boolean isValidPattern = isValidCalendar || isValidDatePattern;
		if (obj != null && !StrUtil.isEmpty(dateFields) && isValidPattern) {
			String[] fieldArr = StrUtil.splitStr(dateFields);
			int fieldCount = fieldArr.length;
			String fieldName = "";
			Object fieldValue = null;
			Class cls = obj.getClass();
			SimpleCalendar cal = new SimpleCalendar();
			for (int i = 0; i < fieldCount; i++) {
				try {
					fieldName = fieldArr[i];
					if (ReflectionUtil.hasProperty(cls, fieldName)) {
						fieldValue = ReflectionUtil.getProperty(obj, fieldName);
					}
					if (StrUtil.isEmpty(fieldValue)) {
						continue;
					}
					cal.setCalendarValue(String.valueOf(fieldValue));
					cal.setCalPattern(datePattern);
					if (isValidCalendar) {
						fieldValue = cal.getCalendarValue();
					} else {
						fieldValue = cal.getSimpleDate().getDateValue();
					}
					ReflectionUtil.setProperty(obj, fieldName, fieldValue);
				} catch (Exception ex) {
					// Logger.logError(ex);//本异常不做记录
				}
			}
		}
		return obj;
	}
}
