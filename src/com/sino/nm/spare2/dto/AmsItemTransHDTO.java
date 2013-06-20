package com.sino.nm.spare2.dto;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.calen.SimpleDate;
import com.sino.base.calen.SimpleTime;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.TimeException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 备件事务头表(AMS) AmsItemTransH</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsItemTransHDTO extends AMSFlowDTO {

    private String transId = "";
    private String transNo = "";
    private String transType = "";
    private String transStatus = "";
    private int fromUser=-1 ;
    private int toUser=-1 ;
    private String fromDept = "";
    private String toDept = "";
    private String fromObjectNo = "";
    private String fromObjectName = "";
    private String fromObjectLocation = "";
    private String toObjectNo = "";
    private String toObjectName = "";
    private String toObjectLocation = "";
    private int fromOrganizationId=-1 ;
    private String fromOrganizationName = "";
    private int toOrganizationId=-1 ;
    private String toOrganizationName = "";
    private SimpleCalendar transDate = null;
    private int rcvUser =-1;
    private SimpleCalendar creationDate = null;
    private int createdBy =-1;
    private SimpleCalendar lastUpdateDate = null;
    private int lastUpdateBy=-1;
    private SimpleCalendar canceledDate = null;
    private String canceledReason = "";
    private String transStatusName = "";
    private String createdUser = "";
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
    private String flag = "";
    private String remark = "";
    private String company  ="";        //公司,服务方
    private String address  ="";        //地址和邮编
    private String contact  ="";        //联系人
    private String tel  ="";            //联系电话
    private String fax  ="";            //传真
    private String attribute1  ="";     //委托书编号
    private String attribute2  ="";     //承运人
    private String attribute3 ="";      //保值金额
    private SimpleCalendar respectReturnDate = null;       //预计归还日期
    private String deptCode = "";           //领用部门（室），选择项，从HR部门中选择
    private String deptName = "";           //领用部门（室），选择项，从HR部门中选择
    private String authorizationUser = "";  //授权人：手工填写项，申领方负责人
    private String invManager = "";         //仓管员：手工填写项
    private String reason = "";             //用途
    private String spareManufacturer = "";             
    private String spareManufacturerOpt = "";
    private String spareType = "";
    private String fromGroup = "";
    private String spareManufacturerName = "";
    private String feedbackInfo = "";
    private String feedbackType = "";
    private String feedbackTypeOpt = "";

    public AmsItemTransHDTO() {
        transDate = new SimpleCalendar();
        creationDate = new SimpleCalendar();
        lastUpdateDate = new SimpleCalendar();
        canceledDate = new SimpleCalendar();
        fromDate = new SimpleCalendar();
        toDate = new SimpleCalendar();
        respectReturnDate = new SimpleCalendar();
    }


    public String getFeedbackInfo() {
        return feedbackInfo;
    }

    public void setFeedbackInfo(String feedbackInfo) {
        this.feedbackInfo = feedbackInfo;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackTypeOpt() {
        return feedbackTypeOpt;
    }

    public void setFeedbackTypeOpt(String feedbackTypeOpt) {
        this.feedbackTypeOpt = feedbackTypeOpt;
    }

    public String getSpareManufacturerName() {
        return spareManufacturerName;
    }

    public void setSpareManufacturerName(String spareManufacturerName) {
        this.spareManufacturerName = spareManufacturerName;
    }

    public String getSpareManufacturerOpt() {
        return spareManufacturerOpt;
    }

    public void setSpareManufacturerOpt(String spareManufacturerOpt) {
        this.spareManufacturerOpt = spareManufacturerOpt;
    }

    public String getSpareManufacturer() {
        return spareManufacturer;
    }

    public void setSpareManufacturer(String spareManufacturer) {
        this.spareManufacturer = spareManufacturer;
    }

    public String getSpareType() {
        return spareType;
    }

    public void setSpareType(String spareType) {
        this.spareType = spareType;
    }

    public String getFromGroup() {
        return fromGroup;
    }

    public void setFromGroup(String fromGroup) {
        this.fromGroup = fromGroup;
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
    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 目标保管人
     * @param toUser String
     */
    public void setToUser(int toUser) {
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
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setTransDate(String transDate) throws CalendarException {
        this.transDate.setCalendarValue(transDate);
    }

    /**
     * 功能：设置备件事务头表(AMS)属性 接收人
     * @param rcvUser String
     */
    public void setRcvUser(int rcvUser) {
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
    public void setLastUpdateBy(int lastUpdateBy) {
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
    public int getFromUser() {
        return this.fromUser;
    }

    /**
     * 功能：获取备件事务头表(AMS)属性 目标保管人
     * @return String
     */
    public int getToUser() {
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
    public int getRcvUser() {
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
    public int getLastUpdateBy() {
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

    public SimpleCalendar getRespectReturnDate() {
        return respectReturnDate;
    }

    public void setRespectReturnDate(String  respectReturnDate) throws CalendarException {
        this.respectReturnDate.setCalendarValue(respectReturnDate);
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getAuthorizationUser() {
        return authorizationUser;
    }

    public void setAuthorizationUser(String authorizationUser) {
        this.authorizationUser = authorizationUser;
    }

    public String getInvManager() {
        return invManager;
    }

    public void setInvManager(String invManager) {
        this.invManager = invManager;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
