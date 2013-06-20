package com.sino.ams.instrument.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-10-24
 * Time: 22:08:48
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentHDTO extends CheckBoxDTO {

    private String barcodeNo = "";
    private String itemCode = "";
    private String instruUsage = "";
    private SimpleCalendar creationDate = null;
    private int createdBy = SyBaseSQLUtil.NULL_INT_VALUE;
    private SimpleCalendar lastUpdateDate = null;
    private int lastUpdateBy = SyBaseSQLUtil.NULL_INT_VALUE;
    private String currKeepUser = "";
    private String itemName = "";
    private String barcodeNo1 = "";
    private String itemCategory = "";
    private String itemSpec = "";
    private String vendorName = "";
    private int vendorId = SyBaseSQLUtil.NULL_INT_VALUE;
    private int borrowUser = SyBaseSQLUtil.NULL_INT_VALUE;
    private SimpleCalendar borrowDate = null;
    private int confirmUser = SyBaseSQLUtil.NULL_INT_VALUE;
    private SimpleCalendar confirmDate = null;
    private String transNo = "";
    private String transId = "";
    private String transType = "";
    private String transStatus = "";
    private SimpleCalendar cancelDate = null;
    private String borrowName = "";
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
    private int returnUser = SyBaseSQLUtil.NULL_INT_VALUE;
    private SimpleCalendar returnDate = null;
    private String returnName = "";
    private int checkUser = SyBaseSQLUtil.NULL_INT_VALUE;
    private String checkName = "";
    private SimpleCalendar checkDate = null;
    private String transStatusName = "";
    private SimpleCalendar preReturnDate = null;
    private String borrowReason = "";
    private String repaireReason = "";
    private int repaireUser = SyBaseSQLUtil.NULL_INT_VALUE;
    private int userId = SyBaseSQLUtil.NULL_INT_VALUE;
    private String repaireName = "";
    private String borrCityUser = "";
    private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
    private String addressId = "";
    private String segment1 = "";


    public String getSegment1() {
        return segment1;
    }

    public void setSegment1(String segment1) {
        this.segment1 = segment1;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getBorrCityUser() {
        return borrCityUser;
    }

    public void setBorrCityUser(String borrCityUser) {
        this.borrCityUser = borrCityUser;
    }

    public String getRepaireName() {
        return repaireName;
    }

    public void setRepaireName(String repaireName) {
        this.repaireName = repaireName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBorrowReason() {
        return borrowReason;
    }

    public void setBorrowReason(String borrowReason) {
        this.borrowReason = borrowReason;
    }

    public String getRepaireReason() {
        return repaireReason;
    }

    public void setRepaireReason(String repaireReason) {
        this.repaireReason = repaireReason;
    }

    public int getRepaireUser() {
        return repaireUser;
    }

    public void setRepaireUser(int repaireUser) {
        this.repaireUser = repaireUser;
    }

    public String getRepariFactory() {
        return repariFactory;
    }

    public void setRepariFactory(String repariFactory) {
        this.repariFactory = repariFactory;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private SimpleCalendar repaireDate = null;
    private String repariFactory = "";
    private String remark = "";

    public AmsInstrumentHDTO() {
        this.cancelDate = new SimpleCalendar();
        this.borrowDate = new SimpleCalendar();
        this.confirmDate = new SimpleCalendar();
        this.fromDate = new SimpleCalendar();
        this.toDate = new SimpleCalendar();
        this.returnDate = new SimpleCalendar();
        this.checkDate = new SimpleCalendar();
        this.preReturnDate = new SimpleCalendar();
        this.repaireDate = new SimpleCalendar();
    }


    public SimpleCalendar getPreReturnDate() throws CalendarException {
        preReturnDate.setCalPattern(getCalPattern());
        return this.preReturnDate;
    }

    public void setPreReturnDate(String preReturnDate) throws CalendarException {
        this.preReturnDate.setCalendarValue(preReturnDate);
    }

    public SimpleCalendar getRepaireDate() throws CalendarException {
        repaireDate.setCalPattern(getCalPattern());
        return this.repaireDate;
    }

    public void setRepaireDate(String repaireDate) throws CalendarException {
        this.repaireDate.setCalendarValue(repaireDate);
    }

    public String getTransStatusName() {
        return transStatusName;
    }

    public void setTransStatusName(String transStatusName) {
        this.transStatusName = transStatusName;
    }

    public SimpleCalendar getCheckDate() throws CalendarException {
        checkDate.setCalPattern(getCalPattern());
        return this.checkDate;
    }

    public void setCheckDate(String checkDate) throws CalendarException {
        this.checkDate.setCalendarValue(checkDate);
    }

    public int getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(int checkUser) {
        this.checkUser = checkUser;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public int getReturnUser() {
        return returnUser;
    }

    public void setReturnUser(int returnUser) {
        this.returnUser = returnUser;
    }

    public SimpleCalendar getReturnDate() throws CalendarException {
        returnDate.setCalPattern(getCalPattern());
        return this.returnDate;
    }

    public void setReturnDate(String returnDate) throws CalendarException {
        this.returnDate.setCalendarValue(returnDate);
    }

    public SimpleCalendar getToDate() throws CalendarException {
        toDate.setCalPattern(getCalPattern());
        return this.toDate;
    }

    public void setToDate(String toDate) throws CalendarException {
        this.toDate.setCalendarValue(toDate);
    }

    public SimpleCalendar getFromDate() throws CalendarException {
        fromDate.setCalPattern(getCalPattern());
        return this.fromDate;
    }

    public void setFromDate(String fromDate) throws CalendarException {
        this.fromDate.setCalendarValue(fromDate);
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public SimpleCalendar getConfirmDate() throws CalendarException {
        confirmDate.setCalPattern(getCalPattern());
        return this.confirmDate;
    }

    public void setConfirmDate(String confirmDate) throws CalendarException {
        this.confirmDate.setCalendarValue(confirmDate);
    }


    public SimpleCalendar getBorrowDate() throws CalendarException {
        cancelDate.setCalPattern(getCalPattern());
        return this.borrowDate;
    }

    public void setBorrowDate(String borrowDate) throws CalendarException {
        this.borrowDate.setCalendarValue(borrowDate);
    }

    public SimpleCalendar getCancelDate() throws CalendarException {
        cancelDate.setCalPattern(getCalPattern());
        return this.cancelDate;
    }

    public void setCancelDate(String cancelDate) throws CalendarException {
        this.cancelDate.setCalendarValue(cancelDate);
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }


    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public int getBorrowUser() {
        return borrowUser;
    }

    public void setBorrowUser(int borrowUser) {
        this.borrowUser = borrowUser;
    }


    public int getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(int confirmUser) {
        this.confirmUser = confirmUser;
    }


    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
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

    public String getBarcodeNo1() {
        return barcodeNo1;
    }

    public void setBarcodeNo1(String barcodeNo1) {
        this.barcodeNo1 = barcodeNo1;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 功能：设置仪器仪表管理(EAM)属性 仪具条码
     *
     * @param barcodeNo String
     */
    public void setBarcodeNo(String barcodeNo) {
        this.barcodeNo = barcodeNo;
    }

    /**
     * 功能：设置仪器仪表管理(EAM)属性 分类代码
     *
     * @param itemCode String
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 功能：设置仪器仪表管理(EAM)属性 仪具用途
     *
     * @param instruUsage String
     */
    public void setInstruUsage(String instruUsage) {
        this.instruUsage = instruUsage;
    }

    /**
     * 功能：设置仪器仪表管理(EAM)属性 创建日期
     *
     * @param creationDate SimpleCalendar
     * @throws com.sino.base.exception.CalendarException
     *          传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        if (!StrUtil.isEmpty(creationDate)) {
            this.creationDate = new SimpleCalendar(creationDate);
        }
    }

    /**
     * 功能：设置仪器仪表管理(EAM)属性 责任人
     *
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置仪器仪表管理(EAM)属性 上次修改日期
     *
     * @param lastUpdateDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        if (!StrUtil.isEmpty(lastUpdateDate)) {
            this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
        }
    }

    /**
     * 功能：设置仪器仪表管理(EAM)属性 上次修改人
     *
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * 功能：设置仪器仪表管理(EAM)属性 当前使用人
     *
     * @param currKeepUser String
     */
    public void setCurrKeepUser(String currKeepUser) {
        this.currKeepUser = currKeepUser;
    }


    /**
     * 功能：获取仪器仪表管理(EAM)属性 仪具条码
     *
     * @return String
     */
    public String getBarcodeNo() {
        return this.barcodeNo;
    }

    /**
     * 功能：获取仪器仪表管理(EAM)属性 分类代码
     *
     * @return String
     */
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * 功能：获取仪器仪表管理(EAM)属性 仪具用途
     *
     * @return String
     */
    public String getInstruUsage() {
        return this.instruUsage;
    }

    /**
     * 功能：获取仪器仪表管理(EAM)属性 创建日期
     *
     * @return SimpleCalendar
     */
    public SimpleCalendar getCreationDate() {
        return this.creationDate;
    }

    /**
     * 功能：获取仪器仪表管理(EAM)属性 责任人
     *
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取仪器仪表管理(EAM)属性 上次修改日期
     *
     * @return SimpleCalendar
     */
    public SimpleCalendar getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取仪器仪表管理(EAM)属性 上次修改人
     *
     * @return String
     */
    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    /**
     * 功能：获取仪器仪表管理(EAM)属性 当前使用人
     *
     * @return String
     */
    public String getCurrKeepUser() {
        return this.currKeepUser;
    }

}
