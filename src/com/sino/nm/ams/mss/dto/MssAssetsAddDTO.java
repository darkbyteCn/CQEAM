package com.sino.nm.ams.mss.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;
import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-4-26
 * Time: 11:44:04
 * To change this template use File | Settings | File Templates.
 */
public class MssAssetsAddDTO extends CommonRecordDTO {

    private String headId = "";
    private String billNo = "";
    private int status;
    private String createUser = "";
    private SimpleCalendar createdDate = null;
    private String remark = "";
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
    private String itemName = "";
    private String itemSpec = "";
    private String barcod = "";
    private String itemUsage1 = "";
    private String itemUsageOp = "";
    private String itemUsageStatus1 = "";
    private String itemUsageStatusOp = "";
    private String barcodeNo = "";
    private String printItemName = "";
    private String printItemSpec = "";
    private String printUserName = "";
    private String printDeptName = "";
    private String printMaintainUser = "";
    private String printOrgName = "";
    private String printItemQty = "";
    private String printItemUsageName = "";
    private String printItemUsageStatusName = "";
    private String printItemUnit = "";
    private String printCost = "";


    public String getPrintCost() {
        return printCost;
    }

    public void setPrintCost(String printCost) {
        this.printCost = printCost;
    }

    public String getPrintItemUnit() {
        return printItemUnit;
    }

    public void setPrintItemUnit(String printItemUnit) {
        this.printItemUnit = printItemUnit;
    }

    public String getPrintItemSpec() {
        return printItemSpec;
    }

    public void setPrintItemSpec(String printItemSpec) {
        this.printItemSpec = printItemSpec;
    }

    public String getPrintUserName() {
        return printUserName;
    }

    public void setPrintUserName(String printUserName) {
        this.printUserName = printUserName;
    }

    public String getPrintDeptName() {
        return printDeptName;
    }

    public void setPrintDeptName(String printDeptName) {
        this.printDeptName = printDeptName;
    }

    public String getPrintMaintainUser() {
        return printMaintainUser;
    }

    public void setPrintMaintainUser(String printMaintainUser) {
        this.printMaintainUser = printMaintainUser;
    }

    public String getPrintOrgName() {
        return printOrgName;
    }

    public void setPrintOrgName(String printOrgName) {
        this.printOrgName = printOrgName;
    }

    public String getPrintItemQty() {
        return printItemQty;
    }

    public void setPrintItemQty(String printItemQty) {
        this.printItemQty = printItemQty;
    }

    public String getPrintItemUsageName() {
        return printItemUsageName;
    }

    public void setPrintItemUsageName(String printItemUsageName) {
        this.printItemUsageName = printItemUsageName;
    }

    public String getPrintItemUsageStatusName() {
        return printItemUsageStatusName;
    }

    public void setPrintItemUsageStatusName(String printItemUsageStatusName) {
        this.printItemUsageStatusName = printItemUsageStatusName;
    }

    public String getPrintItemName() {
        return printItemName;
    }

    public void setPrintItemName(String printItemName) {
        this.printItemName = printItemName;
    }

    public String getBarcodeNo() {
        return barcodeNo;
    }

    public void setBarcodeNo(String barcodeNo) {
        this.barcodeNo = barcodeNo;
    }

    public String getItemUsageOp() {
        return itemUsageOp;
    }

    public void setItemUsageOp(String itemUsageOp) {
        this.itemUsageOp = itemUsageOp;
    }

    public String getItemUsageStatusOp() {
        return itemUsageStatusOp;
    }

    public void setItemUsageStatusOp(String itemUsageStatusOp) {
        this.itemUsageStatusOp = itemUsageStatusOp;
    }


    public String getItemUsage1() {
        return itemUsage1;
    }

    public void setItemUsage1(String itemUsage1) {
        this.itemUsage1 = itemUsage1;
    }

    public String getItemUsageStatus1() {
        return itemUsageStatus1;
    }

    public void setItemUsageStatus1(String itemUsageStatus1) {
        this.itemUsageStatus1 = itemUsageStatus1;
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

    public String getBarcod() {
        return barcod;
    }

    public void setBarcod(String barcod) {
        this.barcod = barcod;
    }

    public MssAssetsAddDTO() {
        createdDate = new SimpleCalendar();
        fromDate = new SimpleCalendar();
        toDate = new SimpleCalendar();
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHeadId() {
        return headId;
    }

    public void setHeadId(String headId) {
        this.headId = headId;
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

    public SimpleCalendar getCreatedDate() throws CalendarException {
        createdDate.setCalPattern(getCalPattern());
        return createdDate;
    }

    public void setCreatedDate(String createdDate) throws CalendarException {
        this.createdDate.setCalendarValue(createdDate);
    }

}
