package com.sino.ams.workorder.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * User: zhoujs
 * Date: 2007-11-10
 * Time: 20:18:54
 * Function:工单差异DTO
 */
public class OrderDiffDTO extends CheckBoxDTO {
    private String barcode=""; //标签号
    private String confirmResult=""; //核实结果
    private String scanStatus ="";//设备扫描状态
    private String scanStatusDesc="";//设备扫描状态描述
    private String itemName="";//设备名称
    private String itemSpec="";//设备规格型号
    private String itemCategory="";//设备专业
    private String itemCategoryDesc="";//设备专业说明
    private String vendorName="";//供应商
    private String itemStatus=""; //设备当系统状态
    private String itemStatusDesc="";//设备系统状态描述
    private String verifyResult="";//核实结果
    private String differenceReason="";//核实备注(0:以扫描结果为准；1:以设备目前状态为准；2:不需要处理；null:未处理)

    private String parentBarcode="";//父标签号

    private String preBoxNo="";//设备当前机柜
    private String preNetUnit="";// 设备当前网元
    private String boxNo="";//设备扫描机柜
    private String netUnit="";//设备扫描网元
    private String responsibilityUserName="";//责任人
    private String responsibilityDept="";//责任部门
    private String systemResponsibilityUserName="";//系统责任人
    private String systemResponsibilityDept="";//系统责任部门
    private String systemItemName="";//系统资产名称
    private String systemItemSpec="";//系统资产规格型号


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getConfirmResult() {
        return confirmResult;
    }

    public void setConfirmResult(String confirmResult) {
        this.confirmResult = confirmResult;
    }

    public String getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getPreBoxNo() {
        return preBoxNo;
    }

    public void setPreBoxNo(String preBoxNo) {
        this.preBoxNo = preBoxNo;
    }

    public String getPreNetUnit() {
        return preNetUnit;
    }

    public void setPreNetUnit(String preNetUnit) {
        this.preNetUnit = preNetUnit;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String getNetUnit() {
        return netUnit;
    }

    public void setNetUnit(String netUnit) {
        this.netUnit = netUnit;
    }

    public String getScanStatusDesc() {
        return scanStatusDesc;
    }

    public void setScanStatusDesc(String scanStatusDesc) {
        this.scanStatusDesc = scanStatusDesc;
    }

    public String getItemStatusDesc() {
        return itemStatusDesc;
    }

    public void setItemStatusDesc(String itemStatusDesc) {
        this.itemStatusDesc = itemStatusDesc;
    }


    public String getItemCategoryDesc() {
        return itemCategoryDesc;
    }

    public void setItemCategoryDesc(String itemCategoryDesc) {
        this.itemCategoryDesc = itemCategoryDesc;
    }

    public String getParentBarcode() {
        return parentBarcode;
    }

    public void setParentBarcode(String parentBarcode) {
        this.parentBarcode = parentBarcode;
    }


    public String getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(String verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getDifferenceReason() {
        return differenceReason;
    }

    public void setDifferenceReason(String differenceReason) {
        this.differenceReason = differenceReason;
    }

    public String getResponsibilityUserName() {
        return responsibilityUserName;
    }

    public void setResponsibilityUserName(String responsibilityUserName) {
        this.responsibilityUserName = responsibilityUserName;
    }

    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public String getSystemResponsibilityUserName() {
        return systemResponsibilityUserName;
    }

    public void setSystemResponsibilityUserName(String systemResponsibilityUserName) {
        this.systemResponsibilityUserName = systemResponsibilityUserName;
    }

    public String getSystemResponsibilityDept() {
        return systemResponsibilityDept;
    }

    public void setSystemResponsibilityDept(String systemResponsibilityDept) {
        this.systemResponsibilityDept = systemResponsibilityDept;
    }

    public String getSystemItemName() {
        return systemItemName;
    }

    public void setSystemItemName(String systemItemName) {
        this.systemItemName = systemItemName;
    }

    public String getSystemItemSpec() {
        return systemItemSpec;
    }

    public void setSystemItemSpec(String systemItemSpec) {
        this.systemItemSpec = systemItemSpec;
    }
}
