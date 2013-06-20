package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: SinoIES</p>
 * <p>Description: 河南移动IES系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author ai
 * @date: 2008-3-14
 * 新增管理资产
 */
public class AssetsAddLDTO extends CommonRecordDTO {

    private String headId = "";
    private String lineId = "";
    private String barcode = "";
    private String barcodeImg = "";
    private String itemCode = "";
    private String itemName = "";
    private String itemSpec = "";
    private int respUser;
    private String employeeId = "";
    private String respDept = "";
    private String deptCode = "";
    private String remark1 = "";
    private int createUser;
    private String status = "";
    private String addressId = "";
    private String maintainUser = "";
    private SimpleCalendar createdDate = null;
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
    private String specialityDept = ""; //实物管理部门

    public AssetsAddLDTO() {
        createdDate = new SimpleCalendar();
        fromDate = new SimpleCalendar();
        toDate = new SimpleCalendar();
    }

    public String getBarcodeImg() {
        return barcodeImg;
    }

    public void setBarcodeImg(String barcodeImg) {
        this.barcodeImg = barcodeImg;
    }

    public String getMaintainUser() {
        return maintainUser;
    }

    public void setMaintainUser(String maintainUser) {
        this.maintainUser = maintainUser;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getRespUser() {
        return respUser;
    }

    public void setRespUser(int respUser) {
        this.respUser = respUser;
    }

    public String getRespDept() {
        return respDept;
    }

    public void setRespDept(String respDept) {
        this.respDept = respDept;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getHeadId() {
        return headId;
    }

    public void setHeadId(String headId) {
        this.headId = headId;
    }

    public SimpleCalendar getCreatedDate() throws CalendarException {
        createdDate.setCalPattern(getCalPattern());
        return createdDate;
    }

    public void setCreatedDate(String createdDate) throws CalendarException {
        this.createdDate.setCalendarValue(createdDate);
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

	public String getSpecialityDept() {
		return specialityDept;
	}

	public void setSpecialityDept(String specialityDept) {
		this.specialityDept = specialityDept;
	}

}
