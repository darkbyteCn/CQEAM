package com.sino.soa.mis.srv.assetretire.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 资产会计期状态 SrvAssetPeriodStatus</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2011</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author wangzp
* @version 1.0
*/

public class SrvRetiredAssetDTO extends CheckBoxDTO{
	private String bookTypeCode="";
	private String assetId=""; 
	private SimpleCalendar datePlacedInService=null;   
	private SimpleCalendar dateRettred =null;
	private SimpleCalendar dateEffective =null;
	private String costRetired=""; 
	private String status="";
	private String units="";
	private String localionDep ="";
	private String assetNumber=""; 
	private String tagNumber ="";
	private String retirementTypeCode="";  
	private String dateRettredFrom ="";
	private String dateRettredTo ="";
	private String dateEffectiveFrom="";
	private String dateEffectiveTo="";
	private String orgOption="";
	private String assetsType ="";
	/**
	 * @return the orgOption
	 */
	public String getOrgOption() {
		return orgOption;
	}
	/**
	 * @param orgOption the orgOption to set
	 */
	public void setOrgOption(String orgOption) {
		this.orgOption = orgOption;
	}
	public SrvRetiredAssetDTO(){
		super();
		dateRettred =  new SimpleCalendar();
		dateEffective = new SimpleCalendar();
		datePlacedInService = new SimpleCalendar();
		
	}
	/**
	 * @return the bookTypeCode
	 */
	public String getBookTypeCode() {
		return bookTypeCode;
	}
	/**
	 * @param bookTypeCode the bookTypeCode to set
	 */
	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}
	/**
	 * @return the assetId
	 */
	public String getAssetId() {
		return assetId;
	}
	/**
	 * @param assetId the assetId to set
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	/**
	 * @return the datePlacedInService
	 * @throws CalendarException 
	 */
	public SimpleCalendar getDatePlacedInService() throws CalendarException {
		datePlacedInService.setCalPattern(getCalPattern());
		return this.datePlacedInService;

	}
	/**
	 * @param datePlacedInService the datePlacedInService to set
	 * @throws CalendarException 
	 */
	public void setDatePlacedInService(String datePlacedInService) throws CalendarException {
		this.datePlacedInService.setCalendarValue(datePlacedInService);

	}
	/**
	 * @return the dateRettred
	 * @throws CalendarException 
	 */
	public SimpleCalendar getDateRettred() throws CalendarException {
		dateRettred.setCalPattern(getCalPattern());
		return this.dateRettred;

	}
	/**
	 * @param dateRettred the dateRettred to set
	 * @throws CalendarException 
	 */
	public void setDateRettred(String dateRettred) throws CalendarException {
		this.dateRettred.setCalendarValue(dateRettred);
	}
	/**
	 * @return the dateEffective
	 * @throws CalendarException 
	 */
	public SimpleCalendar getDateEffective() throws CalendarException {
		dateEffective.setCalPattern(getCalPattern());
		return this.dateEffective;

	}
	/**
	 * @param dateEffective the dateEffective to set
	 * @throws CalendarException 
	 */
	public void setDateEffective(String dateEffective) throws CalendarException {
		this.dateEffective.setCalendarValue(dateEffective);

	}
	/**
	 * @return the costRetired
	 */
	public String getCostRetired() {
		return costRetired;
	}
	/**
	 * @param costRetired the costRetired to set
	 */
	public void setCostRetired(String costRetired) {
		this.costRetired = costRetired;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the units
	 */
	public String getUnits() {
		return units;
	}
	/**
	 * @param units the units to set
	 */
	public void setUnits(String units) {
		this.units = units;
	}
	/**
	 * @return the localionDep
	 */
	public String getLocalionDep() {
		return localionDep;
	}
	/**
	 * @param localionDep the localionDep to set
	 */
	public void setLocalionDep(String localionDep) {
		this.localionDep = localionDep;
	}
	/**
	 * @return the assetNumber
	 */
	public String getAssetNumber() {
		return assetNumber;
	}
	/**
	 * @param assetNumber the assetNumber to set
	 */
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}
	/**
	 * @return the tagNumber
	 */
	public String getTagNumber() {
		return tagNumber;
	}
	/**
	 * @param tagNumber the tagNumber to set
	 */
	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}
	/**
	 * @return the retirementTypeCode
	 */
	public String getRetirementTypeCode() {
		return retirementTypeCode;
	}
	/**
	 * @param retirementTypeCode the retirementTypeCode to set
	 */
	public void setRetirementTypeCode(String retirementTypeCode) {
		this.retirementTypeCode = retirementTypeCode;
	}
	/**
	 * @return the dateRettredFrom
	 */
	public String getDateRettredFrom() {
		return dateRettredFrom;
	}
	/**
	 * @param dateRettredFrom the dateRettredFrom to set
	 */
	public void setDateRettredFrom(String dateRettredFrom) {
		this.dateRettredFrom = dateRettredFrom;
	}
	/**
	 * @return the dateRettredTo
	 */
	public String getDateRettredTo() {
		return dateRettredTo;
	}
	/**
	 * @param dateRettredTo the dateRettredTo to set
	 */
	public void setDateRettredTo(String dateRettredTo) {
		this.dateRettredTo = dateRettredTo;
	}
	/**
	 * @return the dateEffectiveFrom
	 */
	public String getDateEffectiveFrom() {
		return dateEffectiveFrom;
	}
	/**
	 * @param dateEffectiveFrom the dateEffectiveFrom to set
	 */
	public void setDateEffectiveFrom(String dateEffectiveFrom) {
		this.dateEffectiveFrom = dateEffectiveFrom;
	}
	/**
	 * @return the dateEffectiveTo
	 */
	public String getDateEffectiveTo() {
		return dateEffectiveTo;
	}
	/**
	 * @param dateEffectiveTo the dateEffectiveTo to set
	 */
	public void setDateEffectiveTo(String dateEffectiveTo) {
		this.dateEffectiveTo = dateEffectiveTo;
	}
	public String getAssetsType() {
		return assetsType;
	}
	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}
	
}   

