package com.sino.ams.workorder.dto;

import com.sino.base.dto.DTO;

/**
 * 
 * @系统名称: 预转资条码打印功能字段
 * @功能描述:
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Sep 14, 2011
 */
public class EtsItemDTO implements DTO {
	private String companyName = "";
	private String barcode2 = ""; // 标签号图片
	private String barcodeImg = ""; // 标签号图片
	private String itemName = "";
	private String itemSpec = "";
	private String startDate2 = "";

	public String getBarcode2() {
		return barcode2;
	}

	public void setBarcode2(String barcode2) {
		this.barcode2 = barcode2;
	}

	public String getBarcodeImg() {
		return barcodeImg;
	}

	public void setBarcodeImg(String barcodeImg) {
		this.barcodeImg = barcodeImg;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStartDate2() {
		if( startDate2.length() > 10 ){
			startDate2 = startDate2.substring( 0 , 10 );
		}
		return startDate2;
	}

	public void setStartDate2(String startDate2) {
		this.startDate2 = startDate2;
	}
}
