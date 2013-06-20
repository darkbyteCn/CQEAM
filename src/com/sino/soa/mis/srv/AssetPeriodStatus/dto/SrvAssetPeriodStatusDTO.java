package com.sino.soa.mis.srv.AssetPeriodStatus.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 资产会计期状态 SrvAssetPeriodStatus</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SrvAssetPeriodStatusDTO extends CheckBoxDTO{

	private String bookTypeCode = "";
	private String periodName = "";
	private SimpleCalendar startDate = null;
	private SimpleCalendar endDate = null;
	private SimpleCalendar periodOpenDate = null;
	private SimpleCalendar periodCloseDate = null;
	private String glTransferFlag = "";
	private String periodStatus = "";
    private String bookOption="";
    private String asstesType ="";
    private String misPeriodName ="";
    private int organizationId =0;

	public SrvAssetPeriodStatusDTO() {
		super();
		this.startDate = new SimpleCalendar();
		this.endDate = new SimpleCalendar();
		this.periodOpenDate = new SimpleCalendar();
		this.periodCloseDate = new SimpleCalendar();

	}

	/**
	 * 功能：设置资产会计期状态属性 资产账簿
	 * @param bookTypeCode String
	 */
	public void setBookTypeCode(String bookTypeCode){
		this.bookTypeCode = bookTypeCode;
	}

	/**
	 * 功能：设置资产会计期状态属性 期间名称
	 * @param periodName String
	 */
	public void setPeriodName(String periodName){
		this.periodName = periodName;
	}

	/**
	 * 功能：设置资产会计期状态属性 启始日期
	 * @param startDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartDate(String startDate) throws CalendarException{
		this.startDate.setCalendarValue(startDate);
	}

	/**
	 * 功能：设置资产会计期状态属性 截止日期
	 * @param endDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setEndDate(String endDate) throws CalendarException{
		this.endDate.setCalendarValue(endDate);
	}

	/**
	 * 功能：设置资产会计期状态属性 期间打开日期
	 * @param periodOpenDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setPeriodOpenDate(String periodOpenDate) throws CalendarException{
		this.periodOpenDate.setCalendarValue(periodOpenDate);
	}

	/**
	 * 功能：设置资产会计期状态属性 期间关闭日期
	 * @param periodCloseDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setPeriodCloseDate(String periodCloseDate) throws CalendarException{
		this.periodCloseDate.setCalendarValue(periodCloseDate);
	}

	/**
	 * 功能：设置资产会计期状态属性 是否传送总账 Y/N
	 * @param glTransferFlag String
	 */
	public void setGlTransferFlag(String glTransferFlag){
		this.glTransferFlag = glTransferFlag;
	}

	/**
	 * 功能：设置资产会计期状态属性 会计期间状态 CLOSE/OPEN
	 * @param periodStatus String
	 */
	public void setPeriodStatus(String periodStatus){
		this.periodStatus = periodStatus;
	}


	/**
	 * 功能：获取资产会计期状态属性 资产账簿
	 * @return String
	 */
	public String getBookTypeCode() {
		return this.bookTypeCode;
	}

	/**
	 * 功能：获取资产会计期状态属性 期间名称
	 * @return String
	 */
	public String getPeriodName() {
		return this.periodName;
	}

	/**
	 * 功能：获取资产会计期状态属性 启始日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getStartDate() throws CalendarException {
		startDate.setCalPattern(getCalPattern());
		return this.startDate;
	}

	/**
	 * 功能：获取资产会计期状态属性 截止日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getEndDate() throws CalendarException {
		endDate.setCalPattern(getCalPattern());
		return this.endDate;
	}

	/**
	 * 功能：获取资产会计期状态属性 期间打开日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getPeriodOpenDate() throws CalendarException {
		periodOpenDate.setCalPattern(getCalPattern());
		return this.periodOpenDate;
	}

	/**
	 * 功能：获取资产会计期状态属性 期间关闭日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getPeriodCloseDate() throws CalendarException {
		periodCloseDate.setCalPattern(getCalPattern());
		return this.periodCloseDate;
	}

	/**
	 * 功能：获取资产会计期状态属性 是否传送总账 Y/N
	 * @return String
	 */
	public String getGlTransferFlag() {
		return this.glTransferFlag;
	}

	/**
	 * 功能：获取资产会计期状态属性 会计期间状态 CLOSE/OPEN
	 * @return String
	 */
	public String getPeriodStatus() {
		return this.periodStatus;
	}

    public String getBookOption() {
        return bookOption;
    }

    public void setBookOption(String bookOption) {
        this.bookOption = bookOption;
    }

	public String getAsstesType() {
		return asstesType;
	}

	public void setAsstesType(String asstesType) {
		this.asstesType = asstesType;
	}

    public String getMisPeriodName() {
        return misPeriodName;
    }

    public void setMisPeriodName(String misPeriodName) {
        this.misPeriodName = misPeriodName;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }
}