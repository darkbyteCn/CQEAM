package com.sino.ams.system.item.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: 设备分类表(EAM) EtsSystemItem</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class EtsSystemItemDTO extends CommonRecordDTO {

	private String itemCode = "";
	private String itemName = "";
	private String itemSpec = "";
	private String itemCategory = "";
	private String misItemCode = "";
	private String enabled = "";
	private String memo = "";
	private int years  ;
	private String itemUnit = "";
	private String vendorId  ;
	private String isFixedAssets = "";
	private int masterOrganizationId ;
	private String vendorName = "";
	private String vendorCode = "";
	private String isParentItem = "";      //	是否父设备
	private String barcodeNullable = "";   //	是否可当作非条码类设备
	private String isTmpCode = "";         //	是否临时设备
	private SimpleCalendar fromDate = null;
	private SimpleCalendar toDate = null;
	private String repairQuery = "";   //设备返修查询方式
	private int orgId = 0;
	private String lastUpdateByName = ""; //上次修改人
	
	private String itemCategory2 = ""; //目录编号

	public EtsSystemItemDTO() {
		super();
		this.toDate = new SimpleCalendar();
		this.fromDate = new SimpleCalendar();
	}

	public SimpleCalendar getToDate() throws CalendarException {
		toDate.setCalPattern(getCalPattern());
		return this.toDate;
	}

	public void setToDate(String toDate) throws CalendarException {
		this.toDate.setCalendarValue(toDate);
	}

	public SimpleCalendar getFromDate() throws CalendarException {
		fromDate.setCalPattern(getCalPattern());
		return this.fromDate;
	}

	public void setFromDate(String fromDate) throws CalendarException {
		this.fromDate.setCalendarValue(fromDate);
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getRepairQuery() {
		return repairQuery;
	}

	public void setRepairQuery(String repairQuery) {
		this.repairQuery = repairQuery;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * 功能：设置设备分类表(EAM)属性 设备代码
	 *
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置设备分类表(EAM)属性 设备名称
	 *
	 * @param itemName String
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 功能：设置设备分类表(EAM)属性 规格型号
	 *
	 * @param itemSpec String
	 */
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	/**
	 * 功能：设置设备分类表(EAM)属性 设备分类
	 *
	 * @param itemCategory String
	 */
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	/**
	 * 功能：设置设备分类表(EAM)属性 MIS物料编码
	 *
	 * @param misItemCode String
	 */
	public void setMisItemCode(String misItemCode) {
		this.misItemCode = misItemCode;
	}

	/**
	 * 功能：设置设备分类表(EAM)属性 是否有效
	 *
	 * @param enabled String
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	/**
	 * 功能：设置设备分类表(EAM)属性 备注
	 *
	 * @param memo String
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 功能：设置设备分类表(EAM)属性 使用年限
	 *
	 * @param years String
	 */
	public void setYears(int years) {
		this.years = years;
	}

	/**
	 * 功能：设置设备分类表(EAM)属性 计量单位
	 *
	 * @param itemUnit String
	 */
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	/**
	 * 功能：设置设备分类表(EAM)属性 生产厂家
	 *
	 * @param vendorId String
	 */
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * 功能：设置设备分类表(EAM)属性 是否为固定资产设备
	 *
	 * @param isFixedAssets String
	 */
	public void setIsFixedAssets(String isFixedAssets) {
		this.isFixedAssets = isFixedAssets;
	}


	/**
	 * 功能：设置设备分类表(EAM)属性 主组织ID
	 *
	 * @param masterOrganizationId String
	 */
	public void setMasterOrganizationId(int masterOrganizationId) {
		this.masterOrganizationId = masterOrganizationId;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 设备代码
	 *
	 * @return String
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 设备名称
	 *
	 * @return String
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 规格型号
	 *
	 * @return String
	 */
	public String getItemSpec() {
		return this.itemSpec;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 设备分类
	 *
	 * @return String
	 */
	public String getItemCategory() {
		return this.itemCategory;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 MIS物料编码
	 *
	 * @return String
	 */
	public String getMisItemCode() {
		return this.misItemCode;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 是否有效
	 *
	 * @return String
	 */
	public String getEnabled() {
		return this.enabled;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 备注
	 *
	 * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 使用年限
	 *
	 * @return String
	 */
	public int getYears() {
		return this.years;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 计量单位
	 *
	 * @return String
	 */
	public String getItemUnit() {
		return this.itemUnit;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 生产厂家
	 *
	 * @return String
	 */
	public String getVendorId() {
		return this.vendorId;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 是否为固定资产设备
	 *
	 * @return String
	 */
	public String getIsFixedAssets() {
		return this.isFixedAssets;
	}

	/**
	 * 功能：获取设备分类表(EAM)属性 主组织ID
	 *
	 * @return String
	 */
	public int getMasterOrganizationId() {
		return this.masterOrganizationId;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public String getFixedAssets() {
		return isFixedAssets;
	}

	public void setFixedAssets(String fixedAssets) {
		isFixedAssets = fixedAssets;
	}

	public String getParentItem() {
		return isParentItem;
	}

	public void setParentItem(String parentItem) {
		isParentItem = parentItem;
	}

	public String getBarcodeNullable() {
		return barcodeNullable;
	}

	public String getIsTmpCode() {
		return isTmpCode;
	}

	public void setBarcodeNullable(String barcodeNullable) {
		this.barcodeNullable = barcodeNullable;
	}

	public void setIsTmpCode(String isTmpCode) {
		this.isTmpCode = isTmpCode;
	}

	public String getItemCategory2() {
		return itemCategory2;
	}

	public void setItemCategory2(String itemCategory2) {
		this.itemCategory2 = itemCategory2;
	}

	public String getLastUpdateByName() {
		return lastUpdateByName;
	}

	public void setLastUpdateByName(String lastUpdateByName) {
		this.lastUpdateByName = lastUpdateByName;
	}
}
