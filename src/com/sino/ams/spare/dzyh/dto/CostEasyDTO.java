package com.sino.ams.spare.dzyh.dto;

import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.dto.CalendarUtililyDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2008-4-7
 * Time: 16:32:44
 * Function；低值易耗维护。
 */
public class CostEasyDTO extends CalendarUtililyDTO {
    private String systemid = "";
    private String assetNumber = "";
    private String barcode = "";
    private String itemCode = "";
    private String itemSpec = "";
    private String cost = "";
    private int organizationId = -1;
    private String itemQty = "";     //数量
    private String itemName = "";
    private String vendorId = "";
    private String vendorName = "";
    private String remark = "";
    private String workorderObjectName = "";
    private String addressId = "";
    private String itemCategoryDesc = "";
    private String prjId = "";
    private String itemCategory = "";

    private String responsibilityDept = "";
    private String responsibilityUser = "";
    private String responsibilityUserName = "";
    private String specialityDept = "";
    private String specialityUser = "";
    private String specialityUserName = "";
    private String price = "";
    private String isTD = "";
    private String manufacturerId = "";
    private String manufacturerName = "";
    private String maintainUser = "";

    public String getResponsibilityUserName() {
        return responsibilityUserName;
    }

    public void setResponsibilityUserName(String responsibilityUserName) {
        this.responsibilityUserName = responsibilityUserName;
    }

    public String getSpecialityUserName() {
        return specialityUserName;
    }

    public void setSpecialityUserName(String specialityUserName) {
        this.specialityUserName = specialityUserName;
    }

    public String getResponsibilityUser() {
        return responsibilityUser;
    }

    public void setResponsibilityUser(String responsibilityUser) {
        this.responsibilityUser = responsibilityUser;
    }

    public String getSpecialityUser() {
        return specialityUser;
    }

    public void setSpecialityUser(String specialityUser) {
        this.specialityUser = specialityUser;
    }

    public String getMaintainUser() {
        return maintainUser;
    }

    public void setMaintainUser(String maintainUser) {
        this.maintainUser = maintainUser;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public String getSpecialityDept() {
        return specialityDept;
    }

    public void setSpecialityDept(String specialityDept) {
        this.specialityDept = specialityDept;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsTD() {
        return isTD;
    }

    public void setIsTD(String TD) {
        isTD = TD;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getItemCategoryDesc() {
        return itemCategoryDesc;
    }

    public void setItemCategoryDesc(String itemCategoryDesc) {
        this.itemCategoryDesc = itemCategoryDesc;
    }

    public String getPrjId() {
        return prjId;
    }

    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
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

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
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

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }
}
