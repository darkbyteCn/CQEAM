package com.sino.ams.newasset.print.dto;

import com.sino.base.dto.DTO;

/**
 * 
 * @系统名称: 标签打印DTO
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Nov 22, 2011
 */
public class BarCodePrintDTO implements DTO {
	private String companyNamePrint = "";
	private String barcodePrint = ""; // 标签号图片
	private String barcodeImg = ""; // 标签号图片
	private String itemNamePrint = "";
	private String itemSpecPrint = "";
	private String startDatePrint = "";

	public String getCompanyNamePrint() {
		return companyNamePrint;
	}

	public void setCompanyNamePrint(String companyNamePrint) {
		this.companyNamePrint = companyNamePrint;
	}

	public String getBarcodePrint() {
		return barcodePrint;
	}

	public void setBarcodePrint(String barcodePrint) {
		this.barcodePrint = barcodePrint;
	}

	public String getBarcodeImg() {
		return barcodeImg;
	}

	public void setBarcodeImg(String barcodeImg) {
		this.barcodeImg = barcodeImg;
	}

	public String getItemNamePrint() {
		return itemNamePrint;
	}

	public void setItemNamePrint(String itemNamePrint) {
		this.itemNamePrint = itemNamePrint;
	}

	public String getItemSpecPrint() {
		return itemSpecPrint;
	}

	public void setItemSpecPrint(String itemSpecPrint) {
		this.itemSpecPrint = itemSpecPrint;
	}

	public String getStartDatePrint() {
		return startDatePrint;
	}

	public void setStartDatePrint(String startDatePrint) {
		this.startDatePrint = startDatePrint;
	}

}
