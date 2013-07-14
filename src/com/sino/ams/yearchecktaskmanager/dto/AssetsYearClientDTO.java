package com.sino.ams.yearchecktaskmanager.dto;

import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;

public class AssetsYearClientDTO extends AmsAssetsAddressVDTO {

	private String excel="";
	private String ExcelLineId = "";//Excel––∫≈
	private String isImport = "";
	
	public String getIsImport() {
		return isImport;
	}
	public void setIsImport(String isImport) {
		this.isImport = isImport;
	}
	public String getExcel() {
		return excel;
	}
	public void setExcel(String excel) {
		this.excel = excel;
	}
	public String getExcelLineId() {
		return ExcelLineId;
	}
	public void setExcelLineId(String excelLineId) {
		ExcelLineId = excelLineId;
	}
	
	
}
