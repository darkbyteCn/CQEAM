package com.sino.ams.spare.allot.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-6
 * Time: 15:39:28
 */
public class AmsBjsAllotHDTO extends CheckBoxDTO {

    private String actId = "";
    private String transId = "";
    private String transNo = "";
    private String transType = "";
    private String transStatus = "";
    private String fromUser = "";
    private String toUser = "";
    private String fromDept = "";
    private String toDept = "";
    private String fromObjectNo = "";
    private String toObjectNo = "";
    private int fromOrganizationId = -1;
    private String fromOrganizationName = "";
    private int toOrganizationId = -1;
    private String toOrganizationName = "";
    private SimpleCalendar transDate = null;
    private String rcvUser = "";
    private SimpleCalendar creationDate = null;
    private int createdBy = -1;
    private SimpleCalendar lastUpdateDate = null;
    private String lastUpdateBy = "";
    private SimpleCalendar canceledDate = null;
    private String canceledReason = "";
    private String transStatusName = "";
    private String createdUser = "";
    private String toObjectName = "";
    private String toObjectLocation = "";
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
    private String itemName = "";
    private String itemSpec = "";
    private String workorderObjectName = "";
    private String addressId = "";
    private SimpleCalendar startDate = null;
    private String implementDays = "";
    private String transTypeOption = "";
    private String transStatusOption = "";
    private String fromObjectName = "";
    private String remark = "";
    private String reason = "";
    private String attribute4 = "";
    private String vendorId = "";
    private String vendorName = "";

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFromObjectName() {
        return fromObjectName;
    }

    public void setFromObjectName(String fromObjectName) {
        this.fromObjectName = fromObjectName;
    }

    public String getImplementDays() {
        return implementDays;
    }

    public void setImplementDays(String implementDays) {
        this.implementDays = implementDays;
    }

    public String getTransTypeOption() {
        return transTypeOption;
    }

    public void setTransTypeOption(String transTypeOption) {
        this.transTypeOption = transTypeOption;
    }

    public String getTransStatusOption() {
        return transStatusOption;
    }

    public void setTransStatusOption(String transStatusOption) {
        this.transStatusOption = transStatusOption;
    }

    public AmsBjsAllotHDTO() {
        transDate = new SimpleCalendar();
        creationDate = new SimpleCalendar();
        lastUpdateDate = new SimpleCalendar();
        canceledDate = new SimpleCalendar();
        fromDate = new SimpleCalendar();
        toDate = new SimpleCalendar();
        startDate = new SimpleCalendar();
    }


    public SimpleCalendar getStartDate() throws CalendarException {
        startDate.setCalPattern(getCalPattern());
        return this.startDate;
    }

    public void setStartDate(String startDate) throws CalendarException {
        this.startDate.setCalendarValue(creationDate);
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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
     * 功能：设置备件事务头表(AMS)属性 序列号
     * @param transId String
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 单据号
     * @param transNo String
     */
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 事务类型
     * @param transType String
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 单据状态
     * @param transStatus String
     */
    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 源保管人
     * @param fromUser String
     */
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 目标保管人
     * @param toUser String
     */
    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 源保管部门
     * @param fromDept String
     */
    public void setFromDept(String fromDept) {
        this.fromDept = fromDept;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 目标保管部门
     * @param toDept String
     */
    public void setToDept(String toDept) {
        this.toDept = toDept;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 源仓库
     * @param fromObjectNo String
     */
    public void setFromObjectNo(String fromObjectNo) {
        this.fromObjectNo = fromObjectNo;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 目标仓库
     * @param toObjectNo String
     */
    public void setToObjectNo(String toObjectNo) {
        this.toObjectNo = toObjectNo;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 源OU组织
     * @param fromOrganizationId String
     */
    public void setFromOrganizationId(int fromOrganizationId) {
        this.fromOrganizationId = fromOrganizationId;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 目标OU组织
     * @param toOrganizationId String
     */
    public void setToOrganizationId(int toOrganizationId) {
        this.toOrganizationId = toOrganizationId;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 事务日期
     * @param transDate SimpleCalendar
     * @throws com.sino.base.exception.CalendarException
     *          传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setTransDate(String transDate) throws CalendarException {
        this.transDate.setCalendarValue(transDate);
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 接收人
     * @param rcvUser String
     */
    public void setRcvUser(String rcvUser) {
        this.rcvUser = rcvUser;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 创建日期
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        this.creationDate.setCalendarValue(creationDate);
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 创建人
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 上次更新日期
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 上次更新人
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 取消日期
     * @param canceledDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCanceledDate(String canceledDate) throws CalendarException {
        this.canceledDate.setCalendarValue(canceledDate);
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 取消原因
     * @param canceledReason String
     */
    public void setCanceledReason(String canceledReason) {
        this.canceledReason = canceledReason;
    }


    /**
     * 功能：获取备件事务头表(AMS)属性 序列号
     * @return String
     */
    public String getTransId() {
        return this.transId;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 单据号
     * @return String
     */
    public String getTransNo() {
        return this.transNo;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 事务类型
     * @return String
     */
    public String getTransType() {
        return this.transType;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 单据状态
     * @return String
     */
    public String getTransStatus() {
        return this.transStatus;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 源保管人
     * @return String
     */
    public String getFromUser() {
        return this.fromUser;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 目标保管人
     * @return String
     */
    public String getToUser() {
        return this.toUser;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 源保管部门
     * @return String
     */
    public String getFromDept() {
        return this.fromDept;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 目标保管部门
     * @return String
     */
    public String getToDept() {
        return this.toDept;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 源仓库
     * @return String
     */
    public String getFromObjectNo() {
        return this.fromObjectNo;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 目标仓库
     * @return String
     */
    public String getToObjectNo() {
        return this.toObjectNo;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 源OU组织
     * @return String
     */
    public int getFromOrganizationId() {
        return this.fromOrganizationId;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 目标OU组织
     * @return String
     */
    public int getToOrganizationId() {
        return this.toOrganizationId;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 事务日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getTransDate() throws CalendarException {
        transDate.setCalPattern(getCalPattern());
        return this.transDate;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 接收人
     * @return String
     */
    public String getRcvUser() {
        return this.rcvUser;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 创建日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getCreationDate() throws CalendarException {
        creationDate.setCalPattern(getCalPattern());
        return this.creationDate;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 创建人
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 上次更新日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        lastUpdateDate.setCalPattern(getCalPattern());
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 上次更新人
     * @return String
     */
    public String getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 取消日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getCanceledDate() throws CalendarException {
        canceledDate.setCalPattern(getCalPattern());
        return this.canceledDate;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 取消原因
     * @return String
     */
    public String getCanceledReason() {
        return this.canceledReason;
    }

    public String getTransStatusName() {
        return transStatusName;
    }

    public void setTransStatusName(String transStatusName) {
        this.transStatusName = transStatusName;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getToObjectName() {
        return toObjectName;
    }

    public void setToObjectName(String toObjectName) {
        this.toObjectName = toObjectName;
    }

    public String getToObjectLocation() {
        return toObjectLocation;
    }

    public void setToObjectLocation(String toObjectLocation) {
        this.toObjectLocation = toObjectLocation;
    }

    public SimpleCalendar getFromDate() throws CalendarException {
        fromDate.setCalPattern(getCalPattern());
        return fromDate;
    }

    public void setFromDate(String fromDate) throws CalendarException {
        this.fromDate.setCalendarValue(fromDate);
    }

    public SimpleCalendar getToDate() throws CalendarException {
        toDate.setCalPattern(getCalPattern());
        return toDate;
    }

    public void setToDate(String toDate) throws CalendarException {
        this.toDate.setCalendarValue(toDate);
    }

    public String getToOrganizationName() {
        return toOrganizationName;
    }

    public void setToOrganizationName(String toOrganizationName) {
        this.toOrganizationName = toOrganizationName;
    }

    public String getFromOrganizationName() {
        return fromOrganizationName;
    }

    public void setFromOrganizationName(String fromOrganizationName) {
        this.fromOrganizationName = fromOrganizationName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }
}

