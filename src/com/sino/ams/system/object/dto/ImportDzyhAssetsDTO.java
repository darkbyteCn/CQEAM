package com.sino.ams.system.object.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-27
 * Time: 10:09:15
 * To change this template use File | Settings | File Templates.
 */
public class ImportDzyhAssetsDTO extends CommonRecordDTO {
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
    private int userId ;
    private String systemid = "";
    private String specialEmployeeId = "";
    private String responsEmployeeId = "";

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
