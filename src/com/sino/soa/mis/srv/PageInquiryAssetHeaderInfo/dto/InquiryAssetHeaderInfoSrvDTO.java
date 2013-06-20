package com.sino.soa.mis.srv.PageInquiryAssetHeaderInfo.dto;

import java.util.Date;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 资产会计期状态 SrvAssetPeriodStatus</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* user:wangzp
* function:查询资产头基本信息（分页）
*/

public class InquiryAssetHeaderInfoSrvDTO extends CheckBoxDTO{
	
	private String orgOption = "";
	private String assetsType = "";
	private String assetId = "";
	private String bookTypeCode = "";
	private String assetNumber = "";
	private String tagNumber = "";
	private String datePlacedInService = "";
	private String lifeInMonth = "";                //1 liveInMonth
	private String deprnMethod = "";
	private String depreciateFlag = "";
	private String fixedAssetsCost= "";   //Double
	private String deprnReserve= "";      //
	private String salvageValue= "";      //
	private String impairmentReserve= ""; //
	private String description= "";
	private String manufacturerName= "";
	private String modelNumber= "";
	private String serialNumber= "";
	private String inUseFlag= "";
	private String inventorial= "";
	private String assetSourceId= "";
	private String projectNumber= "";
	private String constructionStatus= "";
	private String categoryConSeg= "";
	private String categoryDescription= "";
	private String assetKey= "";
	private String assetKeyDesc= "";
	private String creationDate = "";        //Date
	private String lastUpdateDate= "";   //Date        // fabLastUpdateDate
	private String fixedAssetsUnit = "";      //Long      //currentUnits
	//private String fbLastUpdateDate = "";    //Date
	//private String lastUpdateDate = "";
	private String retirementFlag = "";                   //add
	
    private String startLastUpdateDate = ""; //最后更新开始时间
    private String endLastUpdateDate = "";   //最后更新结束时间
	
	
	public InquiryAssetHeaderInfoSrvDTO(){
		super();
		
	}

	public String getOrgOption() {
		return orgOption;
	}
	public void setOrgOption(String orgOption) {
		this.orgOption = orgOption;
	}
	public String getAssetsType() {
		return assetsType;
	}
	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getBookTypeCode() {
		return bookTypeCode;
	}
	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}
	public String getAssetNumber() {
		return assetNumber;
	}
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}
	public String getTagNumber() {
		return tagNumber;
	}
	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}
	public String getDatePlacedInService() {
		return datePlacedInService;
	}
	public void setDatePlacedInService(String datePlacedInService) {
		this.datePlacedInService = datePlacedInService;
	}
	public String getLifeInMonth() {
		return lifeInMonth;
	}
	public void setLifeInMonth(String lifeInMonth) {
		this.lifeInMonth = lifeInMonth;
	}
	public String getDeprnMethod() {
		return deprnMethod;
	}
	public void setDeprnMethod(String deprnMethod) {
		this.deprnMethod = deprnMethod;
	}
	public String getDepreciateFlag() {
		return depreciateFlag;
	}
	public void setDepreciateFlag(String depreciateFlag) {
		this.depreciateFlag = depreciateFlag;
	}
	public String getFixedAssetsCost() {
		return fixedAssetsCost;
	}
	public void setFixedAssetsCost(String fixedAssetsCost) {
		this.fixedAssetsCost = fixedAssetsCost;
	}
	public String getDeprnReserve() {
		return deprnReserve;
	}
	public void setDeprnReserve(String deprnReserve) {
		this.deprnReserve = deprnReserve;
	}
	public String getSalvageValue() {
		return salvageValue;
	}
	public void setSalvageValue(String salvageValue) {
		this.salvageValue = salvageValue;
	}
	public String getImpairmentReserve() {
		return impairmentReserve;
	}
	public void setImpairmentReserve(String impairmentReserve) {
		this.impairmentReserve = impairmentReserve;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getInUseFlag() {
		return inUseFlag;
	}
	public void setInUseFlag(String inUseFlag) {
		this.inUseFlag = inUseFlag;
	}
	public String getInventorial() {
		return inventorial;
	}
	public void setInventorial(String inventorial) {
		this.inventorial = inventorial;
	}
	public String getAssetSourceId() {
		return assetSourceId;
	}
	public void setAssetSourceId(String assetSourceId) {
		this.assetSourceId = assetSourceId;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public String getConstructionStatus() {
		return constructionStatus;
	}
	public void setConstructionStatus(String constructionStatus) {
		this.constructionStatus = constructionStatus;
	}
	public String getCategoryConSeg() {
		return categoryConSeg;
	}
	public void setCategoryConSeg(String categoryConSeg) {
		this.categoryConSeg = categoryConSeg;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public String getAssetKey() {
		return assetKey;
	}
	public void setAssetKey(String assetKey) {
		this.assetKey = assetKey;
	}
	public String getAssetKeyDesc() {
		return assetKeyDesc;
	}
	public void setAssetKeyDesc(String assetKeyDesc) {
		this.assetKeyDesc = assetKeyDesc;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
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

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getRetirementFlag() {
		return retirementFlag;
	}

	public void setRetirementFlag(String retirementFlag) {
		this.retirementFlag = retirementFlag;
	}

	public String getFixedAssetsUnit() {
		return fixedAssetsUnit;
	}

	public void setFixedAssetsUnit(String fixedAssetsUnit) {
		this.fixedAssetsUnit = fixedAssetsUnit;
	}
	
	

}   

