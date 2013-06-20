package com.sino.nm.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;

public class InformationMaterialManageDTO extends CommonRecordDTO {

	// primary key
	private String itemId = "";

	// 资产标签
	private String barcode = "";

	// 资产名称
	private String itemName = "";

	// 资产型号
	private String itemSpec = "";

	// 资产状态
	private String itemStatus = "";

	// 资产品牌
	private String itemBrand = "";

	// 资产序列号
	private String itemSerial = "";

	// 资产创建日期
	// private String creationDate;

	// 创建人
	private int createdBy;

	// 上次修改日期
	// private String lastUpdateDate;

	// 上次修改人
	private int lastUpdateBy;

	// 承载系统
	private String useBySystem = "";

	// 产品号
	private String productId = "";

	// 责任人
	private String responsibilityUser = "";

	// 责任部门
	private String responsibilityDept = "";

	// 内存
	private String memory = "";

	// CPU 处理器
	private String cpu = "";

	// IP
	private String ipAddress = "";

	// 硬盘信息
	private String diskInformation = "";

	// 操作系统
	private String systemName = "";

	// 托管类型
	private String trusteeshipType = "";

	// 重要程度
	private String importantLevel = "";

	// 物资大类别
	private String itemCategory1 = "";

	// 物资二级类别
	private String itemCategory2 = "";

	// 是否有效
	private String enabled = "";

	// 失效日期
	private String disableDate = "";

	// 物资三级类别
	private String itemCategory3 = "";

	// 扩展属性1
	private String attribute1 = "";

	// 扩展属性2
	private String attribute2 = "";

	// 扩展属性3
	private String attribute3 = "";

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

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public String getItemSerial() {
		return itemSerial;
	}

	public void setItemSerial(String itemSerial) {
		this.itemSerial = itemSerial;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(int lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getUseBySystem() {
		return useBySystem;
	}

	public void setUseBySystem(String useBySystem) {
		this.useBySystem = useBySystem;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getResponsibilityUser() {
		return responsibilityUser;
	}

	public void setResponsibilityUser(String responsibilityUser) {
		this.responsibilityUser = responsibilityUser;
	}

	public String getResponsibilityDept() {
		return responsibilityDept;
	}

	public void setResponsibilityDept(String responsibilityDept) {
		this.responsibilityDept = responsibilityDept;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDiskInformation() {
		return diskInformation;
	}

	public void setDiskInformation(String diskInformation) {
		this.diskInformation = diskInformation;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getTrusteeshipType() {
		return trusteeshipType;
	}

	public void setTrusteeshipType(String trusteeshipType) {
		this.trusteeshipType = trusteeshipType;
	}

	public String getImportantLevel() {
		return importantLevel;
	}

	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}

	public String getItemCategory1() {
		return itemCategory1;
	}

	public void setItemCategory1(String itemCategory1) {
		this.itemCategory1 = itemCategory1;
	}

	public String getItemCategory2() {
		return itemCategory2;
	}

	public void setItemCategory2(String itemCategory2) {
		this.itemCategory2 = itemCategory2;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getDisableDate() {
		return disableDate;
	}

	public void setDisableDate(String disableDate) {
		this.disableDate = disableDate;
	}

	public String getItemCategory3() {
		return itemCategory3;
	}

	public void setItemCategory3(String itemCategory3) {
		this.itemCategory3 = itemCategory3;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
