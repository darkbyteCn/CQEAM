package com.sino.ams.system.place.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 *
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class PlaceInfoDTO extends CommonRecordDTO{
	private int flexValueId ;
	private String flexValueSetId = "";
	private String flexValue = "";
    private String flexValueMeaning = "";
    private int description;
    private String parentFlexValueLow = "";
    private String parentFlexValueHigh = "";
    private String enabledFlag = "";
    private String summaryFlag = "";
    private String startDateActive = "";
    private String endDateActive = "";
    private String companyCode = "";
    private String companyName = "";
    private String countyCode = "";
    private String countyName = "";
    private String loc1SetName= "";
    
	public String getLoc1SetName() {
		return loc1SetName;
	}

	public void setLoc1SetName(String loc1SetName) {
		this.loc1SetName = loc1SetName;
	}

	public int getFlexValueId() {
		return flexValueId;
	}

	public void setFlexValueId(int flexValueId) {
		this.flexValueId = flexValueId;
	}

	/**
	 * @return the flexValueSetId
	 */
	public String getFlexValueSetId() {
		return flexValueSetId;
	}

	/**
	 * @param flexValueSetId the flexValueSetId to set
	 */
	public void setFlexValueSetId(String flexValueSetId) {
		this.flexValueSetId = flexValueSetId;
	}

	/**
	 * @return the flexValue
	 */
	public String getFlexValue() {
		return flexValue;
	}

	/**
	 * @param flexValue the flexValue to set
	 */
	public void setFlexValue(String flexValue) {
		this.flexValue = flexValue;
	}

	/**
	 * @return the flexValueMeaning
	 */
	public String getFlexValueMeaning() {
		return flexValueMeaning;
	}

	/**
	 * @param flexValueMeaning the flexValueMeaning to set
	 */
	public void setFlexValueMeaning(String flexValueMeaning) {
		this.flexValueMeaning = flexValueMeaning;
	}

	/**
	 * @return the description
	 */
	public int getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(int description) {
		this.description = description;
	}

	/**
	 * @return the parentFlexValueLow
	 */
	public String getParentFlexValueLow() {
		return parentFlexValueLow;
	}

	/**
	 * @param parentFlexValueLow the parentFlexValueLow to set
	 */
	public void setParentFlexValueLow(String parentFlexValueLow) {
		this.parentFlexValueLow = parentFlexValueLow;
	}

	/**
	 * @return the parentFlexValueHigh
	 */
	public String getParentFlexValueHigh() {
		return parentFlexValueHigh;
	}

	/**
	 * @param parentFlexValueHigh the parentFlexValueHigh to set
	 */
	public void setParentFlexValueHigh(String parentFlexValueHigh) {
		this.parentFlexValueHigh = parentFlexValueHigh;
	}

	/**
	 * @return the enabledFlag
	 */
	public String getEnabledFlag() {
		return enabledFlag;
	}

	/**
	 * @param enabledFlag the enabledFlag to set
	 */
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	/**
	 * @return the summaryFlag
	 */
	public String getSummaryFlag() {
		return summaryFlag;
	}

	/**
	 * @param summaryFlag the summaryFlag to set
	 */
	public void setSummaryFlag(String summaryFlag) {
		this.summaryFlag = summaryFlag;
	}

	/**
	 * @return the startDateActive
	 */
	public String getStartDateActive() {
		return startDateActive;
	}

	/**
	 * @param startDateActive the startDateActive to set
	 */
	public void setStartDateActive(String startDateActive) {
		this.startDateActive = startDateActive;
	}

	/**
	 * @return the endDateActive
	 */
	public String getEndDateActive() {
		return endDateActive;
	}

	/**
	 * @param endDateActive the endDateActive to set
	 */
	public void setEndDateActive(String endDateActive) {
		this.endDateActive = endDateActive;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the countyCode
	 */
	public String getCountyCode() {
		return countyCode;
	}

	/**
	 * @param countyCode the countyCode to set
	 */
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	/**
	 * @return the countyName
	 */
	public String getCountyName() {
		return countyName;
	}

	/**
	 * @param countyName the countyName to set
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
 
	/**
	* 功能：自动生成数据传输对象构造函数：组合地点表(EAM)。
	* <B>有日期字段时同时需要完成其初始化，如有其他需要初始化的字段请在此完成。
	* 尤其是生成代码后数据库表又增加了日期字段，必须在此增加其初始化</B>
	*/
	public PlaceInfoDTO() {
		super();
	}

	
}
