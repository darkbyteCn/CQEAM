package com.sino.ams.match.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: jiangtao
 * Date: 2007-11-21
 * Time: 11:53:58
 * To change this template use File | Settings | File Templates.
 */
public class AmsNoMactingAssetDTO extends CheckBoxDTO {
	private String tagNumber = ""; //标签号
	private String assetsDescription = ""; //资产描述
	private String modelumber = ""; //型号
	private String assetsLocation = ""; //资产地点
	private SimpleCalendar dateService = null; //启用日期
	private String lifeIn = ""; //使用年限
	private String cost = ""; //原值
	private String assetNumber = ""; //资产编号
	private String bookTypeCode = ""; //资产账簿
	private String workorderObjectName = ""; //资产地点
	private String costCenterCode = "";
	private String costCenterName = "";


	public String getBookTypeCode() {
		return bookTypeCode;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
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

	public String getAssetsLocation() {
		return assetsLocation;
	}

	public void setAssetsLocation(String assetsLocation) {
		this.assetsLocation = assetsLocation;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public SimpleCalendar getDateService() {
		return dateService;
	}

	public void setDateService(SimpleCalendar dateService) {
		this.dateService = dateService;
	}

	public String getLifeIn() {
		return lifeIn;
	}

	public void setLifeIn(String lifeIn) {
		this.lifeIn = lifeIn;
	}

	public String getModelumber() {
		return modelumber;
	}

	public void setModelumber(String modelumber) {
		this.modelumber = modelumber;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getWorkorderObjectName() {
		return workorderObjectName;
	}

	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}

	public String getCostCenterCode() {
		return costCenterCode;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
}
