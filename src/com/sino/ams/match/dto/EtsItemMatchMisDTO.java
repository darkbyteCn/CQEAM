package com.sino.ams.match.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Suhp
 * Date: 2007-11-26
 * Time: 20:22:25
 * To change this template use File | Settings | File Templates.
 */
public class EtsItemMatchMisDTO extends CheckBoxDTO {
	private String barcode="";
	private String itemName="";
	private String itemSpec="";
	private String itemCategory="";
	private String systemid="";

	private String tagNumber="";
	private String assetsDescription="";
	private String modelNumber="";
	private String faCategory1="";
	private String faCategory2="";
	private String remark="";
	private int assetId;
	private String responsibilityDept = "";
	private String costCenterName = "";

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
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

	public String getFaCategory1() {
		return faCategory1;
	}

	public void setFaCategory1(String faCategory1) {
		this.faCategory1 = faCategory1;
	}

	public String getFaCategory2() {
		return faCategory2;
	}

	public void setFaCategory2(String faCategory2) {
		this.faCategory2 = faCategory2;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public String getResponsibilityDept() {
		return responsibilityDept;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setResponsibilityDept(String responsibilityDept) {
		this.responsibilityDept = responsibilityDept;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
}


