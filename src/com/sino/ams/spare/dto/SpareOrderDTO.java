package com.sino.ams.spare.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-4-10
 * Time: 12:19:45
 * To change this template use File | Settings | File Templates.
 */
public class SpareOrderDTO extends CommonRecordDTO {
	private String transId = "";
	private String transNo = "";
	private String transType = "";
	private String transStatus = "";
	private String fromUser = "";
	private String toUser = "";
	private String fromDept = "";
	private String toDept = "";
	private String fromObjectNo = "";
	private String fromObjectName = "";
	private String fromObjectLocation = "";
	private String toObjectNo = "";
	private String toObjectName = "";
	private String toObjectLocation = "";
	private String fromOrganizationId = "";
	private String fromOrganizationName = "";
	private String toOrganizationId = "";
	private String toOrganizationName = "";
	private SimpleCalendar transDate = null;
	private String rcvUser = "";
	private SimpleCalendar canceledDate = null;
	private String canceledReason = "";
	private String transStatusName = "";
	private String createdUser = "";
	private String flag = "";
	private String remark = "";
	private String company = "";  //公司,服务方
	private String address = "";      //地址和邮编
	private String contact = "";      //联系人
	private String tel = "";           //联系电话
	private String attribute1 = "";      //委托书编号
	private String attribute3 = "";       //保值金额
	private String fax = "";       //传真
	private String attribute2 = "";       //承运人
	private String spareReason = ""; //出入库原因
	private String attribute4 = "";  //用于总计金额
	private String applyGroup = "";
	private int organizationId  = -1;
	private String spareUsage = "";
	private String itemName = "";
	private String itemSpec = "";
	private String itemCategory = "";
	private String vendorId = "";
	private String vendorName = "";

    public SpareOrderDTO() {
		transDate = new SimpleCalendar();
		canceledDate = new SimpleCalendar();
	}

	public String getSpareUsage() {
		return spareUsage;
	}

	public void setSpareUsage(String spareUsage) {
		this.spareUsage = spareUsage;
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

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getApplyGroup() {
		return applyGroup;
	}

	public void setApplyGroup(String applyGroup) {
		this.applyGroup = applyGroup;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getSpareReason() {
		return spareReason;
	}

	public void setSpareReason(String spareReason) {
		this.spareReason = spareReason;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 序列号
	 *
	 * @param transId String
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 单据号
	 *
	 * @param transNo String
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 事务类型
	 *
	 * @param transType String
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 单据状态
	 *
	 * @param transStatus String
	 */
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 源保管人
	 *
	 * @param fromUser String
	 */
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 目标保管人
	 *
	 * @param toUser String
	 */
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 源保管部门
	 *
	 * @param fromDept String
	 */
	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 目标保管部门
	 *
	 * @param toDept String
	 */
	public void setToDept(String toDept) {
		this.toDept = toDept;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 源仓库
	 *
	 * @param fromObjectNo String
	 */
	public void setFromObjectNo(String fromObjectNo) {
		this.fromObjectNo = fromObjectNo;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 目标仓库
	 *
	 * @param toObjectNo String
	 */
	public void setToObjectNo(String toObjectNo) {
		this.toObjectNo = toObjectNo;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 源OU组织
	 *
	 * @param fromOrganizationId String
	 */
	public void setFromOrganizationId(String fromOrganizationId) {
		this.fromOrganizationId = fromOrganizationId;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 目标OU组织
	 *
	 * @param toOrganizationId String
	 */
	public void setToOrganizationId(String toOrganizationId) {
		this.toOrganizationId = toOrganizationId;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 事务日期
	 *
	 * @param transDate SimpleCalendar
	 * @throws com.sino.base.exception.CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setTransDate(String transDate) throws CalendarException {
		this.transDate.setCalendarValue(transDate);
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 接收人
	 *
	 * @param rcvUser String
	 */
	public void setRcvUser(String rcvUser) {
		this.rcvUser = rcvUser;
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 取消日期
	 *
	 * @param canceledDate SimpleCalendar
	 * @throws com.sino.base.exception.CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCanceledDate(String canceledDate) throws CalendarException {
		this.canceledDate.setCalendarValue(canceledDate);
	}

	/**
	 * 功能：设置备件事务头表(AMS)属性 取消原因
	 *
	 * @param canceledReason String
	 */
	public void setCanceledReason(String canceledReason) {
		this.canceledReason = canceledReason;
	}


	/**
	 * 功能：获取备件事务头表(AMS)属性 序列号
	 *
	 * @return String
	 */
	public String getTransId() {
		return this.transId;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 单据号
	 *
	 * @return String
	 */
	public String getTransNo() {
		return this.transNo;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 事务类型
	 *
	 * @return String
	 */
	public String getTransType() {
		return this.transType;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 单据状态
	 *
	 * @return String
	 */
	public String getTransStatus() {
		return this.transStatus;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 源保管人
	 *
	 * @return String
	 */
	public String getFromUser() {
		return this.fromUser;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 目标保管人
	 *
	 * @return String
	 */
	public String getToUser() {
		return this.toUser;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 源保管部门
	 *
	 * @return String
	 */
	public String getFromDept() {
		return this.fromDept;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 目标保管部门
	 *
	 * @return String
	 */
	public String getToDept() {
		return this.toDept;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 源仓库
	 *
	 * @return String
	 */
	public String getFromObjectNo() {
		return this.fromObjectNo;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 目标仓库
	 *
	 * @return String
	 */
	public String getToObjectNo() {
		return this.toObjectNo;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 源OU组织
	 *
	 * @return String
	 */
	public String getFromOrganizationId() {
		return this.fromOrganizationId;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 目标OU组织
	 *
	 * @return String
	 */
	public String getToOrganizationId() {
		return this.toOrganizationId;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 事务日期
	 * @return SimpleCalendar
	 * @throws CalendarException
	 */
	public SimpleCalendar getTransDate() throws CalendarException {
		transDate.setCalPattern(getCalPattern());
		return this.transDate;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 接收人
	 *
	 * @return String
	 */
	public String getRcvUser() {
		return this.rcvUser;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 取消日期
	 * @return SimpleCalendar
	 * @throws CalendarException
	 */
	public SimpleCalendar getCanceledDate() throws CalendarException {
		canceledDate.setCalPattern(getCalPattern());
		return this.canceledDate;
	}

	/**
	 * 功能：获取备件事务头表(AMS)属性 取消原因
	 *
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFromObjectName() {
		return fromObjectName;
	}

	public void setFromObjectName(String fromObjectName) {
		this.fromObjectName = fromObjectName;
	}

	public String getFromObjectLocation() {
		return fromObjectLocation;
	}

	public void setFromObjectLocation(String fromObjectLocation) {
		this.fromObjectLocation = fromObjectLocation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
