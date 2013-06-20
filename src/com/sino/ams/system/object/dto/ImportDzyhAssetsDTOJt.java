package com.sino.ams.system.object.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-5-27
 * Time: 10:27:39
 * To change this template use File | Settings | File Templates.
 */
public class ImportDzyhAssetsDTOJt extends CommonRecordDTO {
    private String companyCode = "";
    private String barcode = "";
    private String itemName = "";
    private String itemSpec = "";
    private String itemUnit = "";
    private String specialityDept = "";
    private String specialityUser = "";
    private String address = "";
    private String responsibilityDept = "";
    private String responsibilityUser = "";
    private String maintainUser = "";
    private String price = "";
    private String isTd = "";
    private String dzyhStartDate = "";
    private String remark = "";
    private String itemCode = "";
    private String employeeId = "";
    private int userId;
    private String systemid = "";
    private String specialEmployeeId = "";
    private String responsEmployeeId = "";

    private String contentCode = "";
    private String contentName = "";
    private String manufacturerId = "";
    private String itemStatus = "";
    private String workorderObjectCode = "";
    private String addressId = "";
    private String workorderObjectName = "";
    private String employeeName = "";

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public String getWorkorderObjectCode() {
        return workorderObjectCode;
    }

    public void setWorkorderObjectCode(String workorderObjectCode) {
        this.workorderObjectCode = workorderObjectCode;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getSpecialEmployeeId() {
        return specialEmployeeId;
    }

    public void setSpecialEmployeeId(String specialEmployeeId) {
        this.specialEmployeeId = specialEmployeeId;
    }

    public String getResponsEmployeeId() {
        return responsEmployeeId;
    }

    public void setResponsEmployeeId(String responsEmployeeId) {
        this.responsEmployeeId = responsEmployeeId;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getDzyhStartDate() {
        return dzyhStartDate;
    }

    public void setDzyhStartDate(String dzyhStartDate) {
        this.dzyhStartDate = dzyhStartDate;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getSpecialityUser() {
        return specialityUser;
    }

    public void setSpecialityUser(String specialityUser) {
        this.specialityUser = specialityUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public String getResponsibilityUser() {
        return responsibilityUser;
    }

    public void setResponsibilityUser(String responsibilityUser) {
        this.responsibilityUser = responsibilityUser;
    }

    public String getTd() {
        return isTd;
    }

    public void setTd(String td) {
        isTd = td;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSpecialityDept() {
        return specialityDept;
    }

    public void setSpecialityDept(String specialityDept) {
        this.specialityDept = specialityDept;
    }

    public String getMaintainUser() {
        return maintainUser;
    }

    public void setMaintainUser(String maintainUser) {
        this.maintainUser = maintainUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}