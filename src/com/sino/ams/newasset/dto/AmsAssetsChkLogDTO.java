package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: 资产盘点记录 AmsAssetsChkLog</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsChkLogDTO extends CommonRecordDTO {

    private String barcode = "";
    private int chkTimes = 1;
    private SimpleCalendar lastChkDate = null;
    private String lastChkNo = "";

    private String itemCode = "";
    private String itemName = "";
    private String itemSpec = "";
    private String itemCategory = "";
    private String responsibilityUser =  "";
    private String responsibilityDept = "";
    private String addressId;
    private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
    private String headerId;
    private String batchId;
    private String orderDtlUrl = "";
    private int synStatus;
    private String orderType = "";
    private String isExist = "Y";

    public AmsAssetsChkLogDTO() {
        super();
    }

    /**
     * 功能：设置盘点资产最新状态属性 标签号
     * @param barcode String
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 功能：设置盘点资产最新状态属性 设备代码
     * @param itemCode String
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 功能：设置盘点资产最新状态属性 设备名称
     * @param itemName String
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 功能：设置盘点资产最新状态属性 规格型号
     * @param itemSpec String
     */
    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    /**
     * 功能：设置盘点资产最新状态属性 设备分类
     * @param itemCategory String
     */
    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    /**
     * 功能：设置盘点资产最新状态属性 责任人
     * @param responsibilityUser String
     */
    public void setResponsibilityUser(String responsibilityUser) {
        this.responsibilityUser = responsibilityUser;
    }

    /**
     * 功能：设置盘点资产最新状态属性 责任部门

     * @param responsibilityDept String
     */
    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    /**
     * 功能：设置盘点资产最新状态属性 资产地点ID
     * @param addressId String
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
     * 功能：设置盘点资产最新状态属性 公司OU组织ID
     * @param organizationId String
     */
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置盘点资产最新状态属性 盘点单序列号
     * @param headerId String
     */
    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }

    /**
     * 功能：设置盘点资产最新状态属性 盘点批序列号
     * @param batchId String
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    /**
     * 功能：设置盘点资产最新状态属性 盘点单详细信息URL
     * @param orderDtlUrl String
     */
    public void setOrderDtlUrl(String orderDtlUrl) {
        this.orderDtlUrl = orderDtlUrl;
    }


    /**
     * 功能：设置盘点资产最新状态属性 同步状态：0表示未同步，1表示已同步
     * @param synStatus String
     */
    public void setSynStatus(int synStatus) {
        this.synStatus = synStatus;
    }

    /**
     * 功能：获取盘点资产最新状态属性 标签号
     * @return String
     */
    public String getBarcode() {
        return this.barcode;
    }

    /**
     * 功能：获取盘点资产最新状态属性 设备代码
     * @return String
     */
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * 功能：获取盘点资产最新状态属性 设备名称
     * @return String
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 功能：获取盘点资产最新状态属性 规格型号
     * @return String
     */
    public String getItemSpec() {
        return this.itemSpec;
    }

    /**
     * 功能：获取盘点资产最新状态属性 设备分类
     * @return String
     */
    public String getItemCategory() {
        return this.itemCategory;
    }

    /**
     * 功能：获取盘点资产最新状态属性 责任人
     * @return String
     */
    public String getResponsibilityUser() {
        return this.responsibilityUser;
    }

    /**
     * 功能：获取盘点资产最新状态属性 责任部门

     * @return String
     */
    public String getResponsibilityDept() {
        return this.responsibilityDept;
    }

    /**
     * 功能：获取盘点资产最新状态属性 资产地点ID
     * @return String
     */
    public String getAddressId() {
        return this.addressId;
    }

    /**
     * 功能：获取盘点资产最新状态属性 公司OU组织ID
     * @return String
     */
    public int getOrganizationId() {
        return this.organizationId;
    }

    /**
     * 功能：获取盘点资产最新状态属性 盘点单序列号
     * @return String
     */
    public String getHeaderId() {
        return this.headerId;
    }

    /**
     * 功能：获取盘点资产最新状态属性 盘点批序列号
     * @return String
     */
    public String getBatchId() {
        return this.batchId;
    }

    /**
     * 功能：获取盘点资产最新状态属性 盘点单详细信息URL
     * @return String
     */
    public String getOrderDtlUrl() {
        return this.orderDtlUrl;
    }

    /**
     * 功能：获取盘点资产最新状态属性 同步状态：0表示未同步，1表示已同步
     * @return String
     */
    public int getSynStatus() {
        return this.synStatus;
    }


    /**
     * 功能：设置资产盘点记录属性 盘点次数
     * @param chkTimes String
     */
    public void setChkTimes(int chkTimes) {
        this.chkTimes = chkTimes;
    }

    /**
     * 功能：设置资产盘点记录属性 最后盘点日期
     * @param lastChkDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastChkDate(String lastChkDate) throws CalendarException {
        this.lastChkDate.setCalendarValue(lastChkDate);
    }

    /**
     * 功能：设置资产盘点记录属性 最后盘点工单号
     * @param lastChkNo String
     */
    public void setLastChkNo(String lastChkNo) {
        this.lastChkNo = lastChkNo;
    }

    public void setIsExist(String isExist) {
        this.isExist = isExist;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 功能：获取资产盘点记录属性 盘点次数
     * @return String
     */
    public int getChkTimes() {
        return this.chkTimes;
    }

    /**
     * 功能：获取资产盘点记录属性 最后盘点日期
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getLastChkDate() throws CalendarException {
        lastChkDate.setCalPattern(getCalPattern());
        return this.lastChkDate;
    }

    /**
     * 功能：获取资产盘点记录属性 最后盘点工单号
     * @return String
     */
    public String getLastChkNo() {
        return this.lastChkNo;
    }

    public String getIsExist() {
        return isExist;
    }

    public String getOrderType() {
        return orderType;
    }
}
