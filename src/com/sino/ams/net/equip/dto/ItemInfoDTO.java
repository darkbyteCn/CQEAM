package com.sino.ams.net.equip.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: ITEM_INFO ItemInfo</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class ItemInfoDTO extends CheckBoxDTO {

    private int systemid;
    private String barcode = "";
    private String faBarcode = "";
    private SimpleCalendar startDate = null;
    private SimpleCalendar disableDate = null;
    private int projectId;
    private String sendtomisFlag = "";
    private String financeProp = "";
    private int organizationId;
    private String itemCode = "";
    private String itemName = "";
    private String itemSpec = "";
    private String itemCategory = "";
    private String workorderObjectName = "";
    private int addressId;
    private String boxNo = "";
    private String netUnit = "";
    private String countyCode = null;
    private String countyName = "";
    private String qryType = "";
    private String projectName = "";
    private String objectCategory = "";
    private String workorderObjectCode = "";
    private SimpleCalendar minTime = null;
    private SimpleCalendar maxTime = null;
    private String invType = "";
    private String daiwei = "";

    public ItemInfoDTO() {
        super();
        this.startDate = new SimpleCalendar();
        this.disableDate = new SimpleCalendar();
//        this.systemid = new AdvancedNumber();
//        this.projectId = new AdvancedNumber();
//        this.addressId = new AdvancedNumber();
//        this.countyCode = new AdvancedNumber();
        this.minTime = new SimpleCalendar();
        this.maxTime = new SimpleCalendar();
    }

    public String getDaiwei() {
        return daiwei;
    }

    public void setDaiwei(String daiwei) {
        this.daiwei = daiwei;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getObjectCategory() {
        return objectCategory;
    }

    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }

    public String getWorkorderObjectCode() {
        return workorderObjectCode;
    }

    public void setWorkorderObjectCode(String workorderObjectCode) {
        this.workorderObjectCode = workorderObjectCode;
    }

    public SimpleCalendar getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = new SimpleCalendar(minTime);
    }

    public SimpleCalendar getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = new SimpleCalendar(maxTime);
    }

    /**
     * 功能：设置ITEM_INFO属性 SYSTEMID
     *
     * @param systemid String
     */
    public void setSystemid(int systemid) {
        this.systemid = systemid;
    }

    /**
     * 功能：设置ITEM_INFO属性 BARCODE
     *
     * @param barcode String
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 功能：设置ITEM_INFO属性 FA_BARCODE
     *
     * @param faBarcode String
     */
    public void setFaBarcode(String faBarcode) {
        this.faBarcode = faBarcode;
    }

    /**
     * 功能：设置ITEM_INFO属性 START_DATE
     *
     * @param startDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setStartDate(String startDate) throws CalendarException {
        this.startDate.setCalendarValue(startDate);
    }

    /**
     * 功能：设置ITEM_INFO属性 DISABLE_DATE
     *
     * @param disableDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setDisableDate(String disableDate) throws CalendarException {
        this.disableDate.setCalendarValue(disableDate);
    }

    /**
     * 功能：设置ITEM_INFO属性 PROJECTID
     *
     * @param projectid String
     */
    public void setProjectid(int projectid) {
        this.projectId = projectid;
    }

    /**
     * 功能：设置ITEM_INFO属性 SENDTOMIS_FLAG
     *
     * @param sendtomisFlag String
     */
    public void setSendtomisFlag(String sendtomisFlag) {
        this.sendtomisFlag = sendtomisFlag;
    }

    /**
     * 功能：设置ITEM_INFO属性 FINANCE_PROP
     *
     * @param financeProp String
     */
    public void setFinanceProp(String financeProp) {
        this.financeProp = financeProp;
    }

    /**
     * 功能：设置ITEM_INFO属性 ORGANIZATION_ID
     *
     * @param organizationId String
     */
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置ITEM_INFO属性 ITEM_CODE
     *
     * @param itemCode String
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 功能：设置ITEM_INFO属性 ITEM_NAME
     *
     * @param itemName String
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 功能：设置ITEM_INFO属性 ITEM_SPEC
     *
     * @param itemSpec String
     */
    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    /**
     * 功能：设置ITEM_INFO属性 ITEM_CATEGORY
     *
     * @param itemCategory String
     */
    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    /**
     * 功能：设置ITEM_INFO属性 WORKORDER_OBJECT_NAME
     *
     * @param workorderObjectName String
     */
    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    /**
     * 功能：设置ITEM_INFO属性 ADDRESS_ID
     *
     * @param addressId String
     */
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    /**
     * 功能：设置ITEM_INFO属性 BOX_NO
     *
     * @param boxNo String
     */
    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    /**
     * 功能：设置ITEM_INFO属性 NET_UNIT
     *
     * @param netUnit String
     */
    public void setNetUnit(String netUnit) {
        this.netUnit = netUnit;
    }

    /**
     * 功能：设置ITEM_INFO属性 COUNTY_CODE
     *
     * @param countyCode String
     */
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    /**
     * 功能：设置ITEM_INFO属性 COUNTY_NAME
     *
     * @param countyName String
     */
    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }


    /**
     * 功能：获取ITEM_INFO属性 SYSTEMID
     *
     * @return String
     */
    public int getSystemid() {
        return this.systemid;
    }

    /**
     * 功能：获取ITEM_INFO属性 BARCODE
     *
     * @return String
     */
    public String getBarcode() {
        return this.barcode;
    }

    /**
     * 功能：获取ITEM_INFO属性 FA_BARCODE
     *
     * @return String
     */
    public String getFaBarcode() {
        return this.faBarcode;
    }

    /**
     * 功能：获取ITEM_INFO属性 START_DATE
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getStartDate() throws CalendarException {
        startDate.setCalPattern(getCalPattern());
        return this.startDate;
    }

    /**
     * 功能：获取ITEM_INFO属性 DISABLE_DATE
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getDisableDate() throws CalendarException {
        disableDate.setCalPattern(getCalPattern());
        return this.disableDate;
    }

    /**
     * 功能：获取ITEM_INFO属性 PROJECTID
     *
     * @return String
     */
    public int getProjectid() {
        return this.projectId;
    }

    /**
     * 功能：获取ITEM_INFO属性 SENDTOMIS_FLAG
     *
     * @return String
     */
    public String getSendtomisFlag() {
        return this.sendtomisFlag;
    }

    /**
     * 功能：获取ITEM_INFO属性 FINANCE_PROP
     *
     * @return String
     */
    public String getFinanceProp() {
        return this.financeProp;
    }

    /**
     * 功能：获取ITEM_INFO属性 ORGANIZATION_ID
     *
     * @return String
     */
    public int getOrganizationId() {
        return this.organizationId;
    }

    /**
     * 功能：获取ITEM_INFO属性 ITEM_CODE
     *
     * @return String
     */
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * 功能：获取ITEM_INFO属性 ITEM_NAME
     *
     * @return String
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 功能：获取ITEM_INFO属性 ITEM_SPEC
     *
     * @return String
     */
    public String getItemSpec() {
        return this.itemSpec;
    }

    /**
     * 功能：获取ITEM_INFO属性 ITEM_CATEGORY
     *
     * @return String
     */
    public String getItemCategory() {
        return this.itemCategory;
    }

    /**
     * 功能：获取ITEM_INFO属性 WORKORDER_OBJECT_NAME
     *
     * @return String
     */
    public String getWorkorderObjectName() {
        return this.workorderObjectName;
    }

    /**
     * 功能：获取ITEM_INFO属性 ADDRESS_ID
     *
     * @return String
     */
    public int getAddressId() {
        return this.addressId;
    }

    /**
     * 功能：获取ITEM_INFO属性 BOX_NO
     *
     * @return String
     */
    public String getBoxNo() {
        return this.boxNo;
    }

    /**
     * 功能：获取ITEM_INFO属性 NET_UNIT
     *
     * @return String
     */
    public String getNetUnit() {
        return this.netUnit;
    }

    /**
     * 功能：获取ITEM_INFO属性 COUNTY_CODE
     *
     * @return String
     */
    public String getCountyCode() {
        return this.countyCode;
    }

    /**
     * 功能：获取ITEM_INFO属性 COUNTY_NAME
     *
     * @return String
     */
    public String getCountyName() {
        return this.countyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getQryType() {
        return qryType;
    }

    public void setQryType(String qryType) {
        this.qryType = qryType;
    }
}