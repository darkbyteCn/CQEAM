package com.sino.soa.td.srv.valueinfo.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-14
 * Time: 14:31:58
 * To change this template use File | Settings | File Templates.
 */
public class SBSYSYTdInquiryVSetValueInfoDTO extends CheckBoxDTO {

	private int flexValueId = SyBaseSQLUtil.NULL_INT_VALUE;
	private int flexValueSetId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String flexValue = "";
	private String flexValueMeaning = "";
	private String description = "";
	private String parentFlexValueLow = "";
	private String parentFlexValueHigh = "";
	private String enabledFlag = "";
	private String summaryFlag = "";
	private SimpleCalendar startDateActive = null;
	private SimpleCalendar endDateActive = null;
	private String flexValueName = "";
	private String lastUpdateDate = "";
	private String type ="";
    private String startLastUpdateDate = "";
    private String endLastUpdateDate = "";
    private String flexValetSetOption = "";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public SBSYSYTdInquiryVSetValueInfoDTO() {
		super();
		this.startDateActive = new SimpleCalendar();
		this.endDateActive = new SimpleCalendar();

	}


	/**
	 * 功能：设置M_FND_FLEX_VALUES属性 代码
	 * @param flexValue String
	 */
	public void setFlexValue(String flexValue){
		this.flexValue = flexValue;
	}

	/**
	 * 功能：设置M_FND_FLEX_VALUES属性 含义
	 * @param flexValueMeaning String
	 */
	public void setFlexValueMeaning(String flexValueMeaning){
		this.flexValueMeaning = flexValueMeaning;
	}

	/**
	 * 功能：设置M_FND_FLEX_VALUES属性 描述
	 * @param description String
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * 功能：设置M_FND_FLEX_VALUES属性 父（最小值）
	 * @param parentFlexValueLow String
	 */
	public void setParentFlexValueLow(String parentFlexValueLow){
		this.parentFlexValueLow = parentFlexValueLow;
	}

	/**
	 * 功能：设置M_FND_FLEX_VALUES属性 父（最大值）
	 * @param parentFlexValueHigh String
	 */
	public void setParentFlexValueHigh(String parentFlexValueHigh){
		this.parentFlexValueHigh = parentFlexValueHigh;
	}

	/**
	 * 功能：设置M_FND_FLEX_VALUES属性 是否可用
	 * @param enabledFlag String
	 */
	public void setEnabledFlag(String enabledFlag){
		this.enabledFlag = enabledFlag;
	}

	/**
	 * 功能：设置M_FND_FLEX_VALUES属性 是否汇总科目
	 * @param summaryFlag String
	 */
	public void setSummaryFlag(String summaryFlag){
		this.summaryFlag = summaryFlag;
	}

	/**
	 * 功能：设置M_FND_FLEX_VALUES属性 START_DATE_ACTIVE
	 * @param startDateActive SimpleCalendar
	 * @throws com.sino.base.exception.CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartDateActive(String startDateActive) throws CalendarException {
		this.startDateActive.setCalendarValue(startDateActive);
	}

	/**
	 * 功能：设置M_FND_FLEX_VALUES属性 END_DATE_ACTIVE
	 * @param endDateActive SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setEndDateActive(String endDateActive) throws CalendarException{
		this.endDateActive.setCalendarValue(endDateActive);
	}

	/**
	 * @return the flexValueId
	 */
	public int getFlexValueId() {
		return flexValueId;
	}


	/**
	 * @param flexValueId the flexValueId to set
	 */
	public void setFlexValueId(int flexValueId) {
		this.flexValueId = flexValueId;
	}


	/**
	 * @return the flexValueSetId
	 */
	public int getFlexValueSetId() {
		return flexValueSetId;
	}


	/**
	 * @param flexValueSetId the flexValueSetId to set
	 */
	public void setFlexValueSetId(int flexValueSetId) {
		this.flexValueSetId = flexValueSetId;
	}


	/**
	 * 功能：获取M_FND_FLEX_VALUES属性 代码
	 * @return String
	 */
	public String getFlexValue() {
		return this.flexValue;
	}

	/**
	 * 功能：获取M_FND_FLEX_VALUES属性 含义
	 * @return String
	 */
	public String getFlexValueMeaning() {
		return this.flexValueMeaning;
	}

	/**
	 * 功能：获取M_FND_FLEX_VALUES属性 描述
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 功能：获取M_FND_FLEX_VALUES属性 父（最小值）
	 * @return String
	 */
	public String getParentFlexValueLow() {
		return this.parentFlexValueLow;
	}

	/**
	 * 功能：获取M_FND_FLEX_VALUES属性 父（最大值）
	 * @return String
	 */
	public String getParentFlexValueHigh() {
		return this.parentFlexValueHigh;
	}

	/**
	 * 功能：获取M_FND_FLEX_VALUES属性 是否可用
	 * @return String
	 */
	public String getEnabledFlag() {
		return this.enabledFlag;
	}

	/**
	 * 功能：获取M_FND_FLEX_VALUES属性 是否汇总科目
	 * @return String
	 */
	public String getSummaryFlag() {
		return this.summaryFlag;
	}

	/**
	 * 功能：获取M_FND_FLEX_VALUES属性 START_DATE_ACTIVE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getStartDateActive() throws CalendarException {
		startDateActive.setCalPattern(getCalPattern());
		return this.startDateActive;
	}

	/**
	 * 功能：获取M_FND_FLEX_VALUES属性 END_DATE_ACTIVE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getEndDateActive() throws CalendarException {
		endDateActive.setCalPattern(getCalPattern());
		return this.endDateActive;
	}


	/**
	 * @return the flexValueName
	 */
	public String getFlexValueName() {
		return flexValueName;
	}


	/**
	 * @param flexValueName the flexValueName to set
	 */
	public void setFlexValueName(String flexValueName) {
		this.flexValueName = flexValueName;
	}


	/**
	 * @return the lastUpdateDate
	 */
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}


	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

    public String getStartLastUpdateDate() {
        return startLastUpdateDate;
    }

    public void setStartLastUpdateDate(String startLastUpdateDate) {
        this.startLastUpdateDate = startLastUpdateDate;
    }

    public String getEndLastUpdateDate() {
        return endLastUpdateDate;
    }

    public void setEndLastUpdateDate(String endLastUpdateDate) {
        this.endLastUpdateDate = endLastUpdateDate;
    }

    public String getFlexValetSetOption() {
        return flexValetSetOption;
    }

    public void setFlexValetSetOption(String flexValetSetOption) {
        this.flexValetSetOption = flexValetSetOption;
    }
}