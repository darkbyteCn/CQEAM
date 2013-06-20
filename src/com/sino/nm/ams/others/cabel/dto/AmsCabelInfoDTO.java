package com.sino.nm.ams.others.cabel.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: 线缆类设备扩展信息(AMS) AmsCabelInfo</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class AmsCabelInfoDTO extends CheckBoxDTO {
    public AmsCabelInfoDTO() {
        super();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();
    }

    private String barcode = "";
    private String fromAddress = "";
    private String toAddress = "";
    private String spreadType = "";
    private String cabelUsage = "";
    private String fromTude = "";
    private String toTude = "";
    private String cabelDepth = "";
    private SimpleCalendar creationDate = null;
    private String createdBy = "";
    private SimpleCalendar lastUpdateDate = null;
    private String lastUpdateBy = "";
    //------------------------------------
    private String itemCode = "";
    private String itemCategory = "";
    private String itemName = "";
    private String itemSpec = "";


    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
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


    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 设备条码
     *
     * @param barcode String
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 始地点
     *
     * @param fromAddress String
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 止地点
     *
     * @param toAddress String
     */
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 铺设方式
     *
     * @param spreadType String
     */
    public void setSpreadType(String spreadType) {
        this.spreadType = spreadType;
    }

    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 线缆用途
     *
     * @param cabelUsage String
     */
    public void setCabelUsage(String cabelUsage) {
        this.cabelUsage = cabelUsage;
    }

    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 始地点经纬度
     *
     * @param fromTude String
     */
    public void setFromTude(String fromTude) {
        this.fromTude = fromTude;
    }

    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 止地点经纬度
     *
     * @param toTude String
     */
    public void setToTude(String toTude) {
        this.toTude = toTude;
    }

    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 光缆埋深
     *
     * @param cabelDepth String
     */
    public void setCabelDepth(String cabelDepth) {
        this.cabelDepth = cabelDepth;
    }

    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 创建日期
     *
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        this.creationDate.setCalendarValue(creationDate);
    }

    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 创建人
     *
     * @param createdBy String
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 上次修改日期
     *
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }

    /**
     * 功能：设置线缆类设备扩展信息(AMS)属性 上次修改人
     *
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }


    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 设备条码
     *
     * @return String
     */
    public String getBarcode() {
        return this.barcode;
    }

    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 始地点
     *
     * @return String
     */
    public String getFromAddress() {
        return this.fromAddress;
    }

    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 止地点
     *
     * @return String
     */
    public String getToAddress() {
        return this.toAddress;
    }

    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 铺设方式
     *
     * @return String
     */
    public String getSpreadType() {
        return this.spreadType;
    }

    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 线缆用途
     *
     * @return String
     */
    public String getCabelUsage() {
        return this.cabelUsage;
    }

    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 始地点经纬度
     *
     * @return String
     */
    public String getFromTude() {
        return this.fromTude;
    }

    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 止地点经纬度
     *
     * @return String
     */
    public String getToTude() {
        return this.toTude;
    }

    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 光缆埋深
     *
     * @return String
     */
    public String getCabelDepth() {
        return this.cabelDepth;
    }

    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 创建日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getCreationDate() throws CalendarException {
        if (creationDate != null) {
            creationDate.setCalPattern(getCalPattern());
        }
        return this.creationDate;
    }

    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 创建人
     *
     * @return String
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 上次修改日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        if (lastUpdateDate != null) {
            lastUpdateDate.setCalPattern(getCalPattern());
        }
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取线缆类设备扩展信息(AMS)属性 上次修改人
     *
     * @return String
     */
    public String getLastUpdateBy() {
        return this.lastUpdateBy;
    }

}