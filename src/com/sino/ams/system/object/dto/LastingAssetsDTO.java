package com.sino.ams.system.object.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-21
 * Time: 16:04:19
 * To change this template use File | Settings | File Templates.
 */
public class LastingAssetsDTO extends CommonRecordDTO {
    private String companyCode = "";
    private String barcode = "";
    private String itemName = "";
    private String itemSpec = "";
    private String employeeNumber = "";
    private String employeeName = "";
    private String workorderObjectCode = "";
    private String power = "";
    private String equipmentPerformance = "";
    private String contentCode = "";
    private String contentName = "";
    private String workorderObjectName = "";
    private String specialityDept = "";
    private String maintainUser = "";
    private String maintainDept = "";
    private String rentPerson = "";
    private String tenancy = "";
    private String yearRental = "";
    private String monthReantal = "";
    private String remark = "";

    private String itemCode = "";
    private int addressId = SyBaseSQLUtil.NULL_INT_VALUE  ;
    private int employeeId = SyBaseSQLUtil.NULL_INT_VALUE  ;
    private int userId =  SyBaseSQLUtil.NULL_INT_VALUE  ;
    private int rentId = SyBaseSQLUtil.NULL_INT_VALUE  ;
    private int systemid = SyBaseSQLUtil.NULL_INT_VALUE  ;

    private String responsibilityDept = "";

    private String rentStartDate = "";//获取EXCEL日期特殊处理
    private String rentEndDate = "";//获取EXCEL日期特殊处理

    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public String getRentStartDate() {
        return rentStartDate;
    }

    public void setRentStartDate(String rentStartDate) {
        this.rentStartDate = rentStartDate;
    }

    public String getRentEndDate() {
        return rentEndDate;
    }

    public void setRentEndDate(String rentEndDate) {
        this.rentEndDate = rentEndDate;
    }

    public int getSystemid() {
        return systemid;
    }

    public void setSystemid(int systemid) {
        this.systemid = systemid;
    }

    public int getRentId() {
        return rentId;
    }

    public void setRentId(int rentId) {
        this.rentId = rentId;
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

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getWorkorderObjectCode() {
        return workorderObjectCode;
    }

    public void setWorkorderObjectCode(String workorderObjectCode) {
        this.workorderObjectCode = workorderObjectCode;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getEquipmentPerformance() {
        return equipmentPerformance;
    }

    public void setEquipmentPerformance(String equipmentPerformance) {
        this.equipmentPerformance = equipmentPerformance;
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

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getSpecialityDept() {
        return specialityDept;
    }

    public void setSpecialityDept(String specialityDept) {
        this.specialityDept = specialityDept;
    }

    public String getMaintainDept() {
        return maintainDept;
    }

    public void setMaintainDept(String maintainDept) {
        this.maintainDept = maintainDept;
    }

    public String getMaintainUser() {
        return maintainUser;
    }

    public void setMaintainUser(String maintainUser) {
        this.maintainUser = maintainUser;
    }

    public String getRentPerson() {
        return rentPerson;
    }

    public void setRentPerson(String rentPerson) {
        this.rentPerson = rentPerson;
    }

    public String getTenancy() {
        return tenancy;
    }

    public void setTenancy(String tenancy) {
        this.tenancy = tenancy;
    }

    public String getYearRental() {
        return yearRental;
    }

    public void setYearRental(String yearRental) {
        this.yearRental = yearRental;
    }

    public String getMonthReantal() {
        return monthReantal;
    }

    public void setMonthReantal(String monthReantal) {
        this.monthReantal = monthReantal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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
