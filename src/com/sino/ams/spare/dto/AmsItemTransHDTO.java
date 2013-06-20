package com.sino.ams.spare.dto;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.calen.SimpleDate;
import com.sino.base.calen.SimpleTime;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.TimeException;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class AmsItemTransHDTO extends AMSFlowDTO {

    private String transId = "";
    private String transNo = "";
    private String transType = "";
    private String transStatus = "";
    private int fromUser = -1;
    private int toUser = -1;
    private String fromDept = "";
    private String toDept = "";
    private String fromObjectNo = "";
    private String fromObjectName = "";
    private String fromObjectLocation = "";
    private String toObjectNo = "";
    private String toObjectName = "";
    private String toObjectLocation = "";
    private int fromOrganizationId = -1;
    private String fromOrganizationName = "";
    private int toOrganizationId = -1;
    private String toOrganizationName = "";
    private SimpleCalendar transDate = null;
    private int rcvUser = -1;
    private SimpleCalendar creationDate = null;
    private int createdBy = -1;
    private SimpleCalendar lastUpdateDate = null;
    private int lastUpdateBy = -1;
    private SimpleCalendar canceledDate = null;
    private String canceledReason = "";
    private String transStatusName = "";
    private String createdUser = "";
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
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
    private String vendorCode = "";
    private String att1 = "";
    private String att2 = "";
    private String att3 = "";
    private String spareReasonD ="";
    private String vendorName = "";
    private String vendorId = "";
    private String storeType = "";
    private String fromPRJObjectNo = "";

    private SimpleCalendar respectReturnDate = null;//预计归还日期
    private String deptCode = "";                   //领用部门（室），选择项，从HR部门中选择
    private String deptName = "";                   //领用部门（室），选择项，从HR部门中选择
    private String authorizationUser = "";          //授权人：手工填写项，申领方负责人
    private String invManager = "";                 //仓管员：手工填写项
    private String reason = "";                     //用途
    private ServletConfigDTO servletConfig = null;


    public SimpleCalendar getRespectReturnDate() {
        return respectReturnDate;
    }

    public void setRespectReturnDate(SimpleCalendar respectReturnDate) {
        this.respectReturnDate = respectReturnDate;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ServletConfigDTO getServletConfig() {
        return servletConfig;
    }

    public void setServletConfig(ServletConfigDTO servletConfig) {
        this.servletConfig = servletConfig;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getSpareReasonD() {
        return spareReasonD;
    }

    public void setSpareReasonD(String spareReasonD) {
        this.spareReasonD = spareReasonD;
    }

    public String getAtt1() {
        return att1;
    }

    public void setAtt1(String att1) {
        this.att1 = att1;
    }

    public String getAtt2() {
        return att2;
    }

    public void setAtt2(String att2) {
        this.att2 = att2;
    }

    public String getAtt3() {
        return att3;
    }

    public void setAtt3(String att3) {
        this.att3 = att3;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
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

    public AmsItemTransHDTO() {
        transDate = new SimpleCalendar();
        creationDate = new SimpleCalendar();
        lastUpdateDate = new SimpleCalendar();
        canceledDate = new SimpleCalendar();
        fromDate = new SimpleCalendar();
        toDate = new SimpleCalendar();
    }

    public SimpleCalendar getSQLEndDate(){
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

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public void setFromDept(String fromDept) {
        this.fromDept = fromDept;
    }

    public void setToDept(String toDept) {
        this.toDept = toDept;
    }

    public void setFromObjectNo(String fromObjectNo) {
        this.fromObjectNo = fromObjectNo;
    }

    public void setToObjectNo(String toObjectNo) {
        this.toObjectNo = toObjectNo;
    }

    public void setFromOrganizationId(int fromOrganizationId) {
        this.fromOrganizationId = fromOrganizationId;
    }

    public void setToOrganizationId(int toOrganizationId) {
        this.toOrganizationId = toOrganizationId;
    }

    public void setTransDate(String transDate) throws CalendarException {
        this.transDate.setCalendarValue(transDate);
    }

    public void setRcvUser(int rcvUser) {
        this.rcvUser = rcvUser;
    }

    public void setCreationDate(String creationDate) throws CalendarException {
        this.creationDate.setCalendarValue(creationDate);
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        this.lastUpdateDate.setCalendarValue(lastUpdateDate);
    }

    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setCanceledDate(String canceledDate) throws CalendarException {
        this.canceledDate.setCalendarValue(canceledDate);
    }

    public void setCanceledReason(String canceledReason) {
        this.canceledReason = canceledReason;
    }

    public String getTransId() {
        return this.transId;
    }

    public String getTransNo() {
        return this.transNo;
    }

    public String getTransType() {
        return this.transType;
    }

    public String getTransStatus() {
        return this.transStatus;
    }

    public int getFromUser() {
        return this.fromUser;
    }

    public int getToUser() {
        return this.toUser;
    }

    public String getFromDept() {
        return this.fromDept;
    }

    public String getToDept() {
        return this.toDept;
    }

    public String getFromObjectNo() {
        return this.fromObjectNo;
    }

    public String getToObjectNo() {
        return this.toObjectNo;
    }

    public int getFromOrganizationId() {
        return this.fromOrganizationId;
    }

    public int getToOrganizationId() {
        return this.toOrganizationId;
    }

    public SimpleCalendar getTransDate() throws CalendarException {
        transDate.setCalPattern(getCalPattern());
        return this.transDate;
    }

    public int getRcvUser() {
        return this.rcvUser;
    }

    public SimpleCalendar getCreationDate() throws CalendarException {
        creationDate.setCalPattern(getCalPattern());
        return this.creationDate;
    }

    public int getCreatedBy() {
        return this.createdBy;
    }

    public SimpleCalendar getLastUpdateDate() throws CalendarException {
        lastUpdateDate.setCalPattern(getCalPattern());
        return this.lastUpdateDate;
    }

    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    public SimpleCalendar getCanceledDate() throws CalendarException {
        canceledDate.setCalPattern(getCalPattern());
        return this.canceledDate;
    }

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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getFromPRJObjectNo() {
        return fromPRJObjectNo;
    }

    public void setFromPRJObjectNo(String fromPRJObjectNo) {
        this.fromPRJObjectNo = fromPRJObjectNo;
    }
}
