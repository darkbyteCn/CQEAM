package com.sino.soa.mis.srv.InquiryRetiredAssetDetail.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 资产会计期状态 SrvAssetPeriodStatus</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* user:wangzp
* function:查询报废资产基本信息（分页）
*/

public class PageRetiredAssetDTO extends CheckBoxDTO{
	
	private String retirementId = "";         //报废流水号
	private String bookTypeCode="";
	private String assetId=""; 
	private String datePlacedInService="";    //资产投入使用日期
	private String dateRettred ="";           //报废日期
	private String dateEffective ="";         //生效日期
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
	public PageRetiredAssetDTO(){
		super();
		
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
	public String getDatePlacedInService() {
		return datePlacedInService;
	}
	public void setDatePlacedInService(String datePlacedInService) {
		this.datePlacedInService = datePlacedInService;
	}
	public String getDateRettred() {
		return dateRettred;
	}
	public void setDateRettred(String dateRettred) {
		this.dateRettred = dateRettred;
	}
	public String getDateEffective() {
		return dateEffective;
	}
	public void setDateEffective(String dateEffective) {
		this.dateEffective = dateEffective;
	}
	public String getRetirementId() {
		return retirementId;
	}
	public void setRetirementId(String retirementId) {
		this.retirementId = retirementId;
	}
	
}   

