package com.sino.ams.system.object.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-5-23
 * Time: 19:43:58
 * To change this template use File | Settings | File Templates.
 */
public class ImportVillageAssetsDTO extends CommonRecordDTO {
    private String companyCode = "";
    private String barcode = "";
    private String itemName = "";
    private String itemSpec = "";
    private String employeeNumber = "";
    private String employeeName = "";
    private String workorderObjectCode = "";
    private String equipmentPerformance = "";
    private String contentCode = "";
    private String contentName = "";
    private String workorderObjectName = "";
    private String specialityDept = "";
    private String maintainUser = "";
    private String maintainDept = "";
    private String price = "";
    private String deprnCost = "";//净值
    private String scrapValue = "";//残值
    private String useYear = "";//使用年限
    private String remainMonth = "";//剩余月数
    private String villageStartDate = "";
    private String remark = "";
    private String itemCode = "";
    private String addressId = "";
    private String employeeId = "";
    private int userId ;
    private String rentId = "";
    private String systemid = "";
    private String responsibilityDept = "";



    private String power = "";
    private String rentPerson = "";
    private String tenancy = "";
    private String yearRental = "";
    private String monthReantal = "";
    private String rentStartDate = "";//获取EXCEL日期特殊处理
    private String rentEndDate = "";//获取EXCEL日期特殊处理

    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDeprnCost() {
        return deprnCost;
    }

    public void setDeprnCost(String deprnCost) {
        this.deprnCost = deprnCost;
    }

    public String getScrapValue() {
        return scrapValue;
    }

    public void setScrapValue(String scrapValue) {
        this.scrapValue = scrapValue;
    }

    public String getUseYear() {
        return useYear;
    }

    public void setUseYear(String useYear) {
        this.useYear = useYear;
    }

    public String getRemainMonth() {
        return remainMonth;
    }

    public void setRemainMonth(String remainMonth) {
        this.remainMonth = remainMonth;
    }

    public String getVillageStartDate() {
        return villageStartDate;
    }

    public void setVillageStartDate(String villageStartDate) {
        this.villageStartDate = villageStartDate;
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

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
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
