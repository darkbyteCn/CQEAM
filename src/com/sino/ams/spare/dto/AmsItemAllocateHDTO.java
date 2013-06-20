package com.sino.ams.spare.dto;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class AmsItemAllocateHDTO extends AMSFlowDTO {
	private SimpleCalendar fromDate = null;
	private SimpleCalendar toDate = null;
	private String transId = "";
	private String transNo = "";
	private String transType = "";
	private String transStatus = "";
	private int fromUser = -1;
	private int toUser = -1;
	private String fromDept = "";
	private String toDept = "";
	private String fromObjectNo = "";
	private String toObjectNo = "";
	private int fromOrganizationId = -1;
	private int toOrganizationId = -1;
	private SimpleCalendar transDate = null;
	private String rcvUser = "";
	private SimpleCalendar canceledDate = null;
	private String canceledReason = "";
	private SimpleCalendar confirmedDate = null;
	private String confirmedBy = "";
	private String fromOrganizationName = "";
	private String createdUser = "";
	private String sourceId = "";
	private String transStatusName = "";
	private String remark = "";
	private String toOrganizationName = "";
    private String fromObjectName = "";
    private String toObjectName = "";
    private String attribute1 = "";

    public AmsItemAllocateHDTO() {
		super();
		this.transDate = new SimpleCalendar();
		this.canceledDate = new SimpleCalendar();
		this.confirmedDate = new SimpleCalendar();
		this.fromDate = new SimpleCalendar();
		this.toDate = new SimpleCalendar();
	}

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
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

	public String getFromOrganizationName() {
		return fromOrganizationName;
	}

	public void setFromOrganizationName(String fromOrganizationName) {
		this.fromOrganizationName = fromOrganizationName;
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

	public void setFromDate(String fromDate) throws CalendarException {
		this.fromDate.setCalendarValue(fromDate);
	}

	public SimpleCalendar getFromDate() {
		return this.fromDate;
	}

	public void setToDate(String toDate) throws CalendarException {
		this.toDate.setCalendarValue(toDate);
	}

	public SimpleCalendar getToDate() {
		return this.toDate;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 序列号
	 *
	 * @param transId String
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 单据号
	 *
	 * @param transNo String
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 事务类型
	 *
	 * @param transType String
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 单据状态
	 *
	 * @param transStatus String
	 */
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 源保管人
	 *
	 * @param fromUser String
	 */
	public void setFromUser(int fromUser) {
		this.fromUser = fromUser;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 目标保管人
	 *
	 * @param toUser String
	 */
	public void setToUser(int toUser) {
		this.toUser = toUser;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 源保管部门
	 *
	 * @param fromDept String
	 */
	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 目标保管部门
	 *
	 * @param toDept String
	 */
	public void setToDept(String toDept) {
		this.toDept = toDept;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 源仓库
	 *
	 * @param fromObjectNo String
	 */
	public void setFromObjectNo(String fromObjectNo) {
		this.fromObjectNo = fromObjectNo;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 目标仓库
	 *
	 * @param toObjectNo String
	 */
	public void setToObjectNo(String toObjectNo) {
		this.toObjectNo = toObjectNo;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 源OU组织
	 *
	 * @param fromOrganizationId String
	 */
	public void setFromOrganizationId(int fromOrganizationId) {
		this.fromOrganizationId = fromOrganizationId;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 目标OU组织
	 *
	 * @param toOrganizationId String
	 */
	public void setToOrganizationId(int toOrganizationId) {
		this.toOrganizationId = toOrganizationId;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 事务日期
	 *
	 * @param transDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setTransDate(String transDate) throws CalendarException {
		this.transDate.setCalendarValue(transDate);
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 接收人
	 *
	 * @param rcvUser String
	 */
	public void setRcvUser(String rcvUser) {
		this.rcvUser = rcvUser;
	}


	/**
	 * 功能：设置备件业务头表(AMS)属性 取消日期
	 *
	 * @param canceledDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCanceledDate(String canceledDate) throws CalendarException {
		this.canceledDate.setCalendarValue(canceledDate);
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 取消原因
	 *
	 * @param canceledReason String
	 */
	public void setCanceledReason(String canceledReason) {
		this.canceledReason = canceledReason;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 确认日期
	 *
	 * @param confirmedDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setConfirmedDate(String confirmedDate) throws CalendarException {
		this.confirmedDate.setCalendarValue(confirmedDate);
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 确认人
	 *
	 * @param confirmedBy String
	 */
	public void setConfirmedBy(String confirmedBy) {
		this.confirmedBy = confirmedBy;
	}

	/**
	 * 功能：设置备件业务头表(AMS)属性 调拨单/分配单对应的申领单号
	 *
	 * @param sourceId String
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}


	/**
	 * 功能：获取备件业务头表(AMS)属性 序列号
	 *
	 * @return String
	 */
	public String getTransId() {
		return this.transId;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 单据号
	 *
	 * @return String
	 */
	public String getTransNo() {
		return this.transNo;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 事务类型
	 *
	 * @return String
	 */
	public String getTransType() {
		return this.transType;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 单据状态
	 *
	 * @return String
	 */
	public String getTransStatus() {
		return this.transStatus;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 源保管人
	 *
	 * @return String
	 */
	public int getFromUser() {
		return this.fromUser;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 目标保管人
	 *
	 * @return String
	 */
	public int getToUser() {
		return this.toUser;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 源保管部门
	 *
	 * @return String
	 */
	public String getFromDept() {
		return this.fromDept;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 目标保管部门
	 *
	 * @return String
	 */
	public String getToDept() {
		return this.toDept;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 源仓库
	 *
	 * @return String
	 */
	public String getFromObjectNo() {
		return this.fromObjectNo;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 目标仓库
	 *
	 * @return String
	 */
	public String getToObjectNo() {
		return this.toObjectNo;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 源OU组织
	 *
	 * @return String
	 */
	public int getFromOrganizationId() {
		return this.fromOrganizationId;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 目标OU组织
	 *
	 * @return String
	 */
	public int getToOrganizationId() {
		return this.toOrganizationId;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 事务日期
	 *
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getTransDate() {
		return this.transDate;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 接收人
	 *
	 * @return String
	 */
	public String getRcvUser() {
		return this.rcvUser;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 取消日期
	 *
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getCanceledDate() {
		return this.canceledDate;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 取消原因
	 *
	 * @return String
	 */
	public String getCanceledReason() {
		return this.canceledReason;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 确认日期
	 *
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getConfirmedDate() {
		return this.confirmedDate;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 确认人
	 *
	 * @return String
	 */
	public String getConfirmedBy() {
		return this.confirmedBy;
	}

	/**
	 * 功能：获取备件业务头表(AMS)属性 调拨单/分配单对应的申领单号
	 *
	 * @return String
	 */
	public String getSourceId() {
		return this.sourceId;
	}

}
