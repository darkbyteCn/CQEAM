package com.sino.ams.system.assetcatalog.dto;

import com.sino.ams.bean.CommonRecordDTO;

public class AssetCatalogDTO extends CommonRecordDTO {
    
	private String assetName = "";
	
	private String enable = "";
	
	private String importantFlag = "";

	public String getImportantFlag() {
		return importantFlag;
	}

	public void setImportantFlag(String importantFlag) {
		this.importantFlag = importantFlag;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}
	
}