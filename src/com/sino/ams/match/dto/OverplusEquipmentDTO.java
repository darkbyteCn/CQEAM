package com.sino.ams.match.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Suhp
 * Date: 2007-11-23
 * Time: 16:53:20
 * To change this template use File | Settings | File Templates.
 */
public class OverplusEquipmentDTO extends CheckBoxDTO {
	private String barcode="";
	private String itemName="";
	private String itemSpec="";
	private String itemCategory="";
	private String workorderObjectName="";
	private String workorderObjectNo="";
	private String responsibilityDept="";
	private String costCenterCode = "";
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

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getWorkorderObjectName() {
		return workorderObjectName;
	}

	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}

	public String getWorkorderObjectNo() {
		return workorderObjectNo;
	}

	public void setWorkorderObjectNo(String workorderObjectNo) {
		this.workorderObjectNo = workorderObjectNo;
	}

	public String getResponsibilityDept() {
		return responsibilityDept;
	}

	public String getCostCenterCode() {
		return costCenterCode;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setResponsibilityDept(String responsibilityDept) {
		this.responsibilityDept = responsibilityDept;
	}

	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
}

