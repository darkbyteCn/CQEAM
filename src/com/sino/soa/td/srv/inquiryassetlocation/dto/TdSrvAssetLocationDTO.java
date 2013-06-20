package com.sino.soa.td.srv.inquiryassetlocation.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * <p>Title: 资产类别服务 SrvAssetCategory</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class TdSrvAssetLocationDTO extends CheckBoxDTO {
	private String locationSegment1="";
	private String locationSegment2="";
	private String locationSegment3="";
	private String locationSegment1Name="";
	private String locationSegment2Name="";
	private String locationSegment3Name="";
	private String enabledFlag="";
	private String segment1="";
	private String segment2="";
	private String segment3="";
	private String locationCombinationCode =""; 
	private String locationCombinationName ="";
	private String bookTypeCode ="";
	private String orgId ="";
	private String costCenterCode="";
	private String lastUpdateDate="";
	private String endLastUpDate ="";
	private int organizationId=-1;
	private String orgOption="";
	private String assetsType ="";
	
	private String locationId="";            //资产地点ID
	private String startDateActive = "";     //启用日期自
	private String endDateActive = "" ;       //启用日期至
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
	/**
	 * @return the endLastUpDate
	 */
	public String getEndLastUpDate() {
		return endLastUpDate;
	}
	/**
	 * @param endLastUpDate the endLastUpDate to set
	 */
	public void setEndLastUpDate(String endLastUpDate) {
		this.endLastUpDate = endLastUpDate;
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
	/**
	 * @return the costCenterCode
	 */
	public String getCostCenterCode() {
		return costCenterCode;
	}
	/**
	 * @param costCenterCode the costCenterCode to set
	 */
	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}
	/**
	 * @return the locationSegment1
	 */
	public String getLocationSegment1() {
		return locationSegment1;
	}
	/**
	 * @param locationSegment1 the locationSegment1 to set
	 */
	public void setLocationSegment1(String locationSegment1) {
		this.locationSegment1 = locationSegment1;
	}
	/**
	 * @return the locationSegment2
	 */
	public String getLocationSegment2() {
		return locationSegment2;
	}
	/**
	 * @param locationSegment2 the locationSegment2 to set
	 */
	public void setLocationSegment2(String locationSegment2) {
		this.locationSegment2 = locationSegment2;
	}
	/**
	 * @return the locationSegment3
	 */
	public String getLocationSegment3() {
		return locationSegment3;
	}
	/**
	 * @param locationSegment3 the locationSegment3 to set
	 */
	public void setLocationSegment3(String locationSegment3) {
		this.locationSegment3 = locationSegment3;
	}
	/**
	 * @return the locationSegment1Name
	 */
	public String getLocationSegment1Name() {
		return locationSegment1Name;
	}
	/**
	 * @param locationSegment1Name the locationSegment1Name to set
	 */
	public void setLocationSegment1Name(String locationSegment1Name) {
		this.locationSegment1Name = locationSegment1Name;
	}
	/**
	 * @return the locationSegment2Name
	 */
	public String getLocationSegment2Name() {
		return locationSegment2Name;
	}
	/**
	 * @param locationSegment2Name the locationSegment2Name to set
	 */
	public void setLocationSegment2Name(String locationSegment2Name) {
		this.locationSegment2Name = locationSegment2Name;
	}
	/**
	 * @return the locationSegment3Name
	 */
	public String getLocationSegment3Name() {
		return locationSegment3Name;
	}
	/**
	 * @param locationSegment3Name the locationSegment3Name to set
	 */
	public void setLocationSegment3Name(String locationSegment3Name) {
		this.locationSegment3Name = locationSegment3Name;
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
	 * @return the segment1
	 */
	public String getSegment1() {
		return segment1;
	}
	/**
	 * @param segment1 the segment1 to set
	 */
	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}
	/**
	 * @return the segment2
	 */
	public String getSegment2() {
		return segment2;
	}
	/**
	 * @param segment2 the segment2 to set
	 */
	public void setSegment2(String segment2) {
		this.segment2 = segment2;
	}
	/**
	 * @return the segment3
	 */
	public String getSegment3() {
		return segment3;
	}
	/**
	 * @param segment3 the segment3 to set
	 */
	public void setSegment3(String segment3) {
		this.segment3 = segment3;
	}
	/**
	 * @return the locationCombinationCode
	 */
	public String getLocationCombinationCode() {
		return locationCombinationCode;
	}
	/**
	 * @param locationCombinationCode the locationCombinationCode to set
	 */
	public void setLocationCombinationCode(String locationCombinationCode) {
		this.locationCombinationCode = locationCombinationCode;
	}
	/**
	 * @return the locationCombinationName
	 */
	public String getLocationCombinationName() {
		return locationCombinationName;
	}
	/**
	 * @param locationCombinationName the locationCombinationName to set
	 */
	public void setLocationCombinationName(String locationCombinationName) {
		this.locationCombinationName = locationCombinationName;
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
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the organizationId
	 */
	public int getOrganizationId() {
		return organizationId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	
	public String getAssetsType() {
		return assetsType;
	}
	
	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getStartDateActive() {
		return startDateActive;
	}
	public void setStartDateActive(String startDateActive) {
		this.startDateActive = startDateActive;
	}
	public String getEndDateActive() {
		return endDateActive;
	}
	public void setEndDateActive(String endDateActive) {
		this.endDateActive = endDateActive;
	} 
}