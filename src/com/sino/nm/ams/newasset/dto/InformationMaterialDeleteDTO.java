package com.sino.nm.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;

public class InformationMaterialDeleteDTO extends CommonRecordDTO {

	// primary key
	private String itemId = "";

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
