package com.sino.nm.spare2.dto;

import com.sino.base.SinoBaseObject;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.calen.SimpleDate;
import com.sino.base.calen.SimpleTime;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.TimeException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 备件业务头表(AMS) AmsItemAllocateH</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺博信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 何睿
 * @version 1.0
 */

public class AmsItemAllocateHDTO extends SinoBaseObject implements DTO {
    public AmsItemAllocateHDTO() {
        super();
        this.transDate = new SimpleCalendar();
        this.creationDate = new SimpleCalendar();
        this.lastUpdateDate = new SimpleCalendar();
        this.canceledDate = new SimpleCalendar();
        this.confirmedDate = new SimpleCalendar();
        this.respectReturnDate = new SimpleCalendar();
        this.fromDate = new SimpleCalendar();
        this.toDate = new SimpleCalendar();
    }

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
    private String fromOrganizationId = "";
    private String toOrganizationId = "";
    private String fromObjectName = "";
    private String toObjectName = "";
    private String fromOrganizationName = "";
    private String toOrganizationName = "";

    private SimpleCalendar transDate = null;
    private String rcvUser = "";
    private SimpleCalendar creationDate = null;
    private String createdBy = "";
    private String createdUser = "";
    private SimpleCalendar lastUpdateDate = null;
    private String lastUpdateBy = "";
    private SimpleCalendar canceledDate = null;
    private String canceledReason = "";
    private SimpleCalendar confirmedDate = null;
    private String confirmedBy = "";
    private String sourceId = "";
    private String remark = "";
    private SimpleCalendar respectReturnDate = null;
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;

    private int freightUserId = -1;  //Number		N		出库部门实物仓管员ID
    private String freightUserName = "";//VARCHAR2	32	N		出库部门实物仓管员
    private String freightDeptCode = "";//Number		N		出库部门
    private String freightDeptName = "";//      		N		出库部门
    private int freightMisUser = -1; //Number		N		出库部门备件管理员
    private String freightMisUserName = ""; //		N		出库部门备件管理员
    private String receiveUserName = ""; //VARCHAR2	32	N		接收备板人员
    private String receiveUserTel = "";  //VARCHAR2	64	N		接收备板人员电话


    /**
     * 功能：设置备件业务头表(AMS)属性 序列号
     * @param transId String
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 单据号
     * @param transNo String
     */
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 事务类型
     * @param transType String
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 单据状态
     * @param transStatus String
     */
    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 源保管人
     * @param fromUser String
     */
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 目标保管人
     * @param toUser String
     */
    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 源保管部门
     * @param fromDept String
     */
    public void setFromDept(String fromDept) {
        this.fromDept = fromDept;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 目标保管部门
     * @param toDept String
     */
    public void setToDept(String toDept) {
        this.toDept = toDept;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 源仓库
     * @param fromObjectNo String
     */
    public void setFromObjectNo(String fromObjectNo) {
        this.fromObjectNo = fromObjectNo;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 目标仓库
     * @param toObjectNo String
     */
    public void setToObjectNo(String toObjectNo) {
        this.toObjectNo = toObjectNo;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 源OU组织
     * @param fromOrganizationId String
     */
    public void setFromOrganizationId(String fromOrganizationId) {
        this.fromOrganizationId = fromOrganizationId;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 目标OU组织
     * @param toOrganizationId String
     */
    public void setToOrganizationId(String toOrganizationId) {
        this.toOrganizationId = toOrganizationId;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 事务日期
     * @param transDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setTransDate(String transDate) throws CalendarException {
        this.transDate.setCalendarValue(transDate);
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 接收人
     * @param rcvUser String
     */
    public void setRcvUser(String rcvUser) {
        this.rcvUser = rcvUser;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 创建日期
     * @param creationDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        this.creationDate.setCalendarValue(creationDate);
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 创建人
     * @param createdBy String
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 上次更新日期
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 上次更新人
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 取消日期
     * @param canceledDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCanceledDate(String canceledDate) throws CalendarException {
        this.canceledDate.setCalendarValue(canceledDate);
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 取消原因
     * @param canceledReason String
     */
    public void setCanceledReason(String canceledReason) {
        this.canceledReason = canceledReason;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 确认日期
     * @param confirmedDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setConfirmedDate(String confirmedDate) throws CalendarException {
        this.confirmedDate.setCalendarValue(confirmedDate);
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 确认人
     * @param confirmedBy String
     */
    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    /**
     * 功能：设置备件业务头表(AMS)属性 调拨单/分配单对应的申领单号
     * @param sourceId String
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }


    /**
     * 功能：获取备件业务头表(AMS)属性 序列号
     * @return String
     */
    public String getTransId() {
        return this.transId;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 单据号
     * @return String
     */
    public String getTransNo() {
        return this.transNo;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 事务类型
     * @return String
     */
    public String getTransType() {
        return this.transType;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 单据状态
     * @return String
     */
    public String getTransStatus() {
        return this.transStatus;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 源保管人
     * @return String
     */
    public String getFromUser() {
        return this.fromUser;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 目标保管人
     * @return String
     */
    public String getToUser() {
        return this.toUser;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 源保管部门
     * @return String
     */
    public String getFromDept() {
        return this.fromDept;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 目标保管部门
     * @return String
     */
    public String getToDept() {
        return this.toDept;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 源仓库
     * @return String
     */
    public String getFromObjectNo() {
        return this.fromObjectNo;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 目标仓库
     * @return String
     */
    public String getToObjectNo() {
        return this.toObjectNo;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 源OU组织
     * @return String
     */
    public String getFromOrganizationId() {
        return this.fromOrganizationId;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 目标OU组织
     * @return String
     */
    public String getToOrganizationId() {
        return this.toOrganizationId;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 事务日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getTransDate() {
        return this.transDate;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 接收人
     * @return String
     */
    public String getRcvUser() {
        return this.rcvUser;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 创建日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getCreationDate() {
        return this.creationDate;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 创建人
     * @return String
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 上次更新日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 上次更新人
     * @return String
     */
    public String getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 取消日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getCanceledDate() {
        return this.canceledDate;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 取消原因
     * @return String
     */
    public String getCanceledReason() {
        return this.canceledReason;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 确认日期
     * @return SimpleCalendar
     */
    public SimpleCalendar getConfirmedDate() {
        return this.confirmedDate;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 确认人
     * @return String
     */
    public String getConfirmedBy() {
        return this.confirmedBy;
    }

    /**
     * 功能：获取备件业务头表(AMS)属性 调拨单/分配单对应的申领单号
     * @return String
     */
    public String getSourceId() {
        return this.sourceId;
    }

    public String getFromObjectName() {
        return fromObjectName;
    }

    public void setFromObjectName(String fromObjectName) {
        this.fromObjectName = fromObjectName;
    }

    public String getToObjectName() {
        return toObjectName;
    }

    public void setToObjectName(String toObjectName) {
        this.toObjectName = toObjectName;
    }

    public String getFromOrganizationName() {
        return fromOrganizationName;
    }

    public void setFromOrganizationName(String fromOrganizationName) {
        this.fromOrganizationName = fromOrganizationName;
    }

    public String getToOrganizationName() {
        return toOrganizationName;
    }

    public void setToOrganizationName(String toOrganizationName) {
        this.toOrganizationName = toOrganizationName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SimpleCalendar getRespectReturnDate() {
        return respectReturnDate;
    }

    public void setRespectReturnDate(String respectReturnDate) throws CalendarException {
        this.respectReturnDate.setCalendarValue(respectReturnDate);
    }


    public SimpleCalendar getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) throws CalendarException {
        this.fromDate.setCalendarValue(fromDate);
    }

    public SimpleCalendar getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) throws CalendarException {
        this.toDate.setCalendarValue(toDate);
    }

    /**
	 * 功能：构造查询条件截至日期的最后一秒所的日历对象。免去应用中每个查询SQL自己构造截至日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getSQLToDate(){
		SimpleCalendar sqlEndCal = new SimpleCalendar();
		if (!StrUtil.isEmpty(toDate.toString())) {
			try {
				SimpleDate date = toDate.getSimpleDate();
				SimpleTime time = SimpleTime.getEndTime();
				sqlEndCal = new SimpleCalendar(date, time);
			} catch (TimeException ex) {
				ex.printLog();
			}
		}
		return sqlEndCal;
	}

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public int getFreightUserId() {
        return freightUserId;
    }

    public void setFreightUserId(int freightUserId) {
        this.freightUserId = freightUserId;
    }

    public String getFreightUserName() {
        return freightUserName;
    }

    public void setFreightUserName(String freightUserName) {
        this.freightUserName = freightUserName;
    }

    public String getFreightDeptCode() {
        return freightDeptCode;
    }

    public void setFreightDeptCode(String freightDeptCode) {
        this.freightDeptCode = freightDeptCode;
    }

    public int getFreightMisUser() {
        return freightMisUser;
    }

    public void setFreightMisUser(int freightMisUser) {
        this.freightMisUser = freightMisUser;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getReceiveUserTel() {
        return receiveUserTel;
    }

    public void setReceiveUserTel(String receiveUserTel) {
        this.receiveUserTel = receiveUserTel;
    }

    public String getFreightDeptName() {
        return freightDeptName;
    }

    public void setFreightDeptName(String freightDeptName) {
        this.freightDeptName = freightDeptName;
    }

    public String getFreightMisUserName() {
        return freightMisUserName;
    }

    public void setFreightMisUserName(String freightMisUserName) {
        this.freightMisUserName = freightMisUserName;
    }
}