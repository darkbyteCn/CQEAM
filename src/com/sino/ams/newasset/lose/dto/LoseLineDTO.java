package com.sino.ams.newasset.lose.dto;

import com.sino.ams.appbase.dto.AMSBaseDTO;

/**
 * 
 * @系统名称: 
 * @功能描述: 挂失
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本： 1.0
 * @开发作者: sj
 * @创建时间: Oct 31, 2011
 */
public class LoseLineDTO extends AMSBaseDTO {

	private String assetNumber = "";
	private String assetsDescription = "";
	private String modelNumber = "";
	private String vendorName = "";
	private String datePlacedInService = "";
	private float currentUnits ;
	private String unitOfMeasure = "";
	private String oldLocationName = "";
	private String lineId = "";
	private String transId = "";
	private String barcode = "";

	private String oldResponsibilityDept = "";
	private String oldLocation = "";
	private String oldResponsibilityUser = "";

	private String lineReason = "";
	
	public LoseLineDTO() {

	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getAssetsDescription() {
		return assetsDescription;
	}

	public void setAssetsDescription(String assetsDescription) {
		this.assetsDescription = assetsDescription;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getDatePlacedInService() {
		return datePlacedInService;
	}

	public void setDatePlacedInService(String datePlacedInService) {
		this.datePlacedInService = datePlacedInService;
	}

	public float getCurrentUnits() {
		return currentUnits;
	}

	public void setCurrentUnits(float currentUnits) {
		this.currentUnits = currentUnits;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getOldLocationName() {
		return oldLocationName;
	}

	public void setOldLocationName(String oldLocationName) {
		this.oldLocationName = oldLocationName;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getOldResponsibilityDept() {
		return oldResponsibilityDept;
	}

	public void setOldResponsibilityDept(String oldResponsibilityDept) {
		this.oldResponsibilityDept = oldResponsibilityDept;
	}

	public String getOldLocation() {
		return oldLocation;
	}

	public void setOldLocation(String oldLocation) {
		this.oldLocation = oldLocation;
	}

	public String getOldResponsibilityUser() {
		return oldResponsibilityUser;
	}

	public void setOldResponsibilityUser(String oldResponsibilityUser) {
		this.oldResponsibilityUser = oldResponsibilityUser;
	}

	public String getLineReason() {
		return lineReason;
	}

	public void setLineReason(String lineReason) {
		this.lineReason = lineReason;
	}

}
