package com.sino.ams.apd.dto;

import com.sino.ams.bean.CommonRecordDTO;

public class EtsItemCheckDTO extends CommonRecordDTO {

	private static final long serialVersionUID = 4657008147526342542L;
	
	private String barcode = ""; //标签号
	
	private String itemName = ""; //设备名称
	
	private String itemSpec = ""; //设备型号
	
	private String itemStatus = ""; //设备状态
	
	private String isNorMal="";

	
    private String itemLevelOption = ""; //紧急成都下拉框
    
	public String getIsNorMal() {
		return isNorMal;
	}

	public void setIsNorMal(String isNorMal) {
		this.isNorMal = isNorMal;
	}

	public String getItemLevelOption() {
		return itemLevelOption;
	}

	public void setItemLevelOption(String itemLevelOption) {
		this.itemLevelOption = itemLevelOption;
	}

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

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	
}
