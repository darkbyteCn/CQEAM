package com.sino.ams.print.dto;

import com.sino.base.dto.CheckBoxDTO;

public class BarcodeMaxMaintainDTO extends CheckBoxDTO  {

	//公司编号
	private String companyId = "";
	
	//公司名称
	private String companyName = "" ;
	
	//标签号
	private String tagSeq = "";
	
	//修改后的标签号
	private String updateTagSeq = "";
	
	//标签类型
	private String assetsType = "";
	
	//OU编号
	private int organizationId;
	
		
	//经过处理后,完整的条码,如:4010-TD6000000001....
	private String completeBarcode = "";

	public String getAssetsType() {
		return assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTagSeq() {
		return tagSeq;
	}

	public void setTagSeq(String tagSeq) {
		this.tagSeq = tagSeq;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}


	public String getCompleteBarcode() {
		return completeBarcode;
	}

	public void setCompleteBarcode(String completeBarcode) {
		this.completeBarcode = completeBarcode;
	}

	public String getUpdateTagSeq() {
		return updateTagSeq;
	}

	public void setUpdateTagSeq(String updateTagSeq) {
		this.updateTagSeq = updateTagSeq;
	}
	
	
	
}
