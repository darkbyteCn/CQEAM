package com.sino.ams.spare.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class AmsSpareCategoryDTO extends CheckBoxDTO {

    private String barcode = "";
    private String itemCode = "";
    private SimpleCalendar creationDate = null;
    private String createdBy = "";
    private SimpleCalendar lastUpdateDate = null;
    private String lastUpdateBy = "";
    private String spareUsage = "";
    private String itemName = "";
    private String itemSpec = "";
    private String itemCategory = "";
    private String vendorId = "";
    private String itemUnit = "";
    private String remark = "";
    private String enabled = "";
    private int organizationId = -1;
    private String vendorName = "";
    private String objectNo = "";
    private String batchNo = "";
    private String barcode1 = "";
    private String spareId = "";

    private String storeType = "";

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getSpareId() {
        return spareId;
    }

    public void setSpareId(String spareId) {
        this.spareId = spareId;
    }

    public String getBarcode1() {
        return barcode1;
    }

    public void setBarcode1(String barcode1) {
        this.barcode1 = barcode1;
    }

    private String objectCategory="";

    public String getObjectNo() {
        return objectNo;
    }

    public void setObjectNo(String objectNo) {
        this.objectNo = objectNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public AmsSpareCategoryDTO() {
        super();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();

    }

    /**
     * 功能：设置备件设备分类表属性 部件号
     * @param barcode String
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 功能：设置备件设备分类表属性 ETS_SYSTEM_ITEM设备分类
     * @param itemCode String
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 功能：设置备件设备分类表属性 CREATION_DATE
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        this.creationDate.setCalendarValue(creationDate);
    }

    /**
     * 功能：设置备件设备分类表属性 CREATED_BY
     * @param createdBy String
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置备件设备分类表属性 LAST_UPDATE_DATE
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }

    /**
     * 功能：设置备件设备分类表属性 LAST_UPDATE_BY
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * 功能：设置备件设备分类表属性 规格用途
     * @param spareUsage String
     */
    public void setSpareUsage(String spareUsage) {
        this.spareUsage = spareUsage;
    }

    /**
     * 功能：设置备件设备分类表属性 设备名称
     * @param itemName String
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 功能：设置备件设备分类表属性 规格型号
     * @param itemSpec String
     */
    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    /**
     * 功能：设置备件设备分类表属性 设备分类
     * @param itemCategory String
     */
    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    /**
     * 功能：设置备件设备分类表属性 生产厂家
     * @param vendorId String
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * 功能：设置备件设备分类表属性 计量单位
     * @param itemUnit String
     */
    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    /**
     * 功能：设置备件设备分类表属性 备注
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 功能：设置备件设备分类表属性 是否有效
     * @param enabled String
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }


    /**
     * 功能：获取备件设备分类表属性 部件号
     * @return String
     */
    public String getBarcode() {
        return this.barcode;
    }

    /**
     * 功能：获取备件设备分类表属性 ETS_SYSTEM_ITEM设备分类
     * @return String
     */
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * 功能：获取备件设备分类表属性 CREATION_DATE
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getCreationDate() throws CalendarException {
        creationDate.setCalPattern(getCalPattern());
        return this.creationDate;
    }

    /**
     * 功能：获取备件设备分类表属性 CREATED_BY
     * @return String
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取备件设备分类表属性 LAST_UPDATE_DATE
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        lastUpdateDate.setCalPattern(getCalPattern());
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取备件设备分类表属性 LAST_UPDATE_BY
     * @return String
     */
    public String getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    /**
     * 功能：获取备件设备分类表属性 规格用途
     * @return String
     */
    public String getSpareUsage() {
        return this.spareUsage;
    }

    
    /**
     * 功能：获取备件设备分类表属性 设备名称
     * @return String
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 功能：获取备件设备分类表属性 规格型号
     * @return String
     */
    public String getItemSpec() {
        return this.itemSpec;
    }

    /**
     * 功能：获取备件设备分类表属性 设备分类
     * @return String
     */
    public String getItemCategory() {
        return this.itemCategory;
    }

    /**
     * 功能：获取备件设备分类表属性 生产厂家
     * @return String
     */
    public String getVendorId() {
        return this.vendorId;
    }

    /**
     * 功能：获取备件设备分类表属性 计量单位
     * @return String
     */
    public String getItemUnit() {
        return this.itemUnit;
    }

    /**
     * 功能：获取备件设备分类表属性 备注
     * @return String
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 功能：获取备件设备分类表属性 是否有效
     * @return String
     */
    public String getEnabled() {
        return this.enabled;
    }


    public String getObjectCategory() {
        return objectCategory;
    }

    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }
}