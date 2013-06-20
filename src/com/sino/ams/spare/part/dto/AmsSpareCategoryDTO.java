package com.sino.ams.spare.part.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.ams.bean.CommonRecordDTO;

/**
* <p>Title: 备件设备分类表 AmsSpareCategory</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
* Function; 部件号维护 
*/

public class AmsSpareCategoryDTO extends CommonRecordDTO { //CheckBoxDTO

    private String barcode = "";
    private String itemCode = "";
    private SimpleCalendar creationDate = null;
    private SimpleCalendar lastUpdateDate = null;
    private String lastUpdateBy = "";
    private String spareUsage = "";
    private String organizationId = "";
    private String itemName = "";
    private String itemSpec = "";
    private String itemCategory = "";
    private String vendorId = "";
    private String itemUnit = "";
    private String remark = "";
    private String enabled = "";
    private String partNo = "";
    private String vendorName = "";
    private String New = "";
    private String createdByName = "";

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getNew() {
        return New;
    }

    public void setNew(String aNew) {
        New = aNew;
    }

    public AmsSpareCategoryDTO() {
        super();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();
    }

    public String getVendorName() {
           return vendorName;
       }

       public void setVendorName(String vendorName) {
           this.vendorName = vendorName;
       }


     public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    /**
     * 功能：设置备件设备分类表属性 部件号
     * @param barcode String
     */
    public void setBarcode(String barcode){
        this.barcode = barcode;
    }

    /**
     * 功能：设置备件设备分类表属性 ETS_SYSTEM_ITEM设备分类
     * @param itemCode String
     */
    public void setItemCode(String itemCode){
        this.itemCode = itemCode;
    }

    /**
     * 功能：设置备件设备分类表属性 CREATION_DATE
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException{
        this.creationDate.setCalendarValue(creationDate);
    }

    /**
     * 功能：设置备件设备分类表属性 LAST_UPDATE_DATE
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }

    /**
     * 功能：设置备件设备分类表属性 LAST_UPDATE_BY
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(String lastUpdateBy){
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * 功能：设置备件设备分类表属性 规格类型
     * @param spareUsage String
     */
    public void setSpareUsage(String spareUsage){
        this.spareUsage = spareUsage;
    }

    /**
     * 功能：设置备件设备分类表属性 OU
     * @param organizationId String
     */
    public void setOrganizationId(String organizationId){
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置备件设备分类表属性 设备名称
     * @param itemName String
     */
    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    /**
     * 功能：设置备件设备分类表属性 规格型号
     * @param itemSpec String
     */
    public void setItemSpec(String itemSpec){
        this.itemSpec = itemSpec;
    }

    /**
     * 功能：设置备件设备分类表属性 设备分类
     * @param itemCategory String
     */
    public void setItemCategory(String itemCategory){
        this.itemCategory = itemCategory;
    }

    /**
     * 功能：设置备件设备分类表属性 生产厂家
     * @param vendorId String
     */
    public void setVendorId(String vendorId){
        this.vendorId = vendorId;
    }

    /**
     * 功能：设置备件设备分类表属性 计量单位
     * @param itemUnit String
     */
    public void setItemUnit(String itemUnit){
        this.itemUnit = itemUnit;
    }

    /**
     * 功能：设置备件设备分类表属性 备注
     * @param remark String
     */
    public void setRemark(String remark){
        this.remark = remark;
    }

    /**
     * 功能：设置备件设备分类表属性 是否有效
     * @param enabled String
     */
    public void setEnabled(String enabled){
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
     * 功能：获取备件设备分类表属性 LAST_UPDATE_DATE
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        lastUpdateDate.setCalPattern(getCalPattern());
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取备件设备分类表属性 规格类型
     * @return String
     */
    public String getSpareUsage() {
        return this.spareUsage;
    }

    /**
     * 功能：获取备件设备分类表属性 OU
     * @return String
     */
    public String getOrganizationId() {
        return this.organizationId;
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

}