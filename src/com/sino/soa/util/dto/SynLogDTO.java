package com.sino.soa.util.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 自动同步日志表(EAM) EtsAutoSynLog</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SynLogDTO extends CheckBoxDTO{

	private String synId = null;
	private String synType = "";
	private SimpleCalendar synDate = null;
	private String synMsg = "";
	private SimpleCalendar creationDate = null;
	private int createdBy = -1;
	private SimpleCalendar lastUpdateDate = null;
	private int lastUpdateBy = -1;

	public SynLogDTO() {
		super();
		this.synDate = new SimpleCalendar();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();
	}

    /**
	 * 功能：设置自动同步日志表(EAM)属性 SYN_ID
	 * @param synId String
	 */
	public void setSynId(String synId){
		this.synId = synId;
	}

	/**
	 * 功能：设置自动同步日志表(EAM)属性 SYN_TYPE
	 * @param synType String
	 */
	public void setSynType(String synType){
		this.synType = synType;
	}

	/**
	 * 功能：设置自动同步日志表(EAM)属性 SYN_DATE
	 * @param synDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setSynDate(String synDate) throws CalendarException{
		this.synDate.setCalendarValue(synDate);
	}

	/**
	 * 功能：设置自动同步日志表(EAM)属性 SYN_MSG
	 * @param synMsg String
	 */
	public void setSynMsg(String synMsg){
		this.synMsg = synMsg;
	}

	/**
	 * 功能：设置自动同步日志表(EAM)属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置自动同步日志表(EAM)属性 创建人
	 * @param createdBy int
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置自动同步日志表(EAM)属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置自动同步日志表(EAM)属性 上次修改人
	 * @param lastUpdateBy int
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}


	/**
	 * 功能：获取自动同步日志表(EAM)属性 SYN_ID
	 * @return String
	 */
	public String getSynId() {
		return this.synId;
	}

	/**
	 * 功能：获取自动同步日志表(EAM)属性 SYN_TYPE
	 * @return String
	 */
	public String getSynType() {
		return this.synType;
	}

	/**
	 * 功能：获取自动同步日志表(EAM)属性 SYN_DATE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getSynDate() throws CalendarException {
		synDate.setCalPattern(getCalPattern());
		return this.synDate;
	}

	/**
	 * 功能：获取自动同步日志表(EAM)属性 SYN_MSG
	 * @return String
	 */
	public String getSynMsg() {
		return this.synMsg;
	}

	/**
	 * 功能：获取自动同步日志表(EAM)属性 创建日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * 功能：获取自动同步日志表(EAM)属性 创建人
	 * @return int
	 */
	public int getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 功能：获取自动同步日志表(EAM)属性 上次修改日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取自动同步日志表(EAM)属性 上次修改人
	 * @return String
	 */
	public int getLastUpdateBy() {
		return this.lastUpdateBy;
	}
}