package com.sino.nm.spare2.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * <p>Title: 备件事务行表(AMS) AmsItemTransL</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsItemTransLDTO extends CheckBoxDTO {

    private String transId = "";
    private String lineId = "";
    private String barcode = "";
    private String itemName = "";
    private String itemSpec = "";
    private String itemCode = "";
    private int quantity;
    private String itemCodes = "";
    private int organizationId ;
    private String batchNo = "";            //非条码设备入库使用      备件归还单用来记录调拨单号
    private String storageId = "";          //非条码设备入库使用      备件归还单用来记录AMS_ITEM_ALLOCATE_H.DETAIL_ID
    private int outQuantity;        //非条码设备入库使用
    private String objectNo = "";
    private String acceptQty = "";
    private String onhandQty = "";
    private String serialNo = "";
    private int normalQuantity;
    private int badQuantity;
    private String returnedQuantity = "";
    private String repairQuantity = "";
    private String sourceId = "";
    private String itemUnit = "";
    private String workorderObjectName = "";
    private String addressId = "";
    private String employeeId = "";
    private String responsibilityDept = "";


    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    /**
     * 功能：设置备件事务行表(AMS)属性 事务交易ID
     * @param transId String
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }

    /**
     * 功能：设置备件事务行表(AMS)属性 行ID
     * @param lineId String
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * 功能：设置备件事务行表(AMS)属性 设备条码
     * @param barcode String
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }


    /**
     * 功能：获取备件事务行表(AMS)属性 事务交易ID
     * @return String
     */
    public String getTransId() {
        return this.transId;
    }

    /**
     * 功能：获取备件事务行表(AMS)属性 行ID
     * @return String
     */
    public String getLineId() {
        return this.lineId;
    }

    /**
     * 功能：获取备件事务行表(AMS)属性 设备条码
     * @return String
     */
    public String getBarcode() {
        return this.barcode;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemCodes() {
        return itemCodes;
    }

    public void setItemCodes(String itemCodes) {
        this.itemCodes = itemCodes;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public int getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(int outQuantity) {
        this.outQuantity = outQuantity;
    }

    public String getObjectNo() {
        return objectNo;
    }

    public void setObjectNo(String objectNo) {
        this.objectNo = objectNo;
    }

    public String getAcceptQty() {
        return acceptQty;
    }

    public void setAcceptQty(String acceptQty) {
        this.acceptQty = acceptQty;
    }

    public String getOnhandQty() {
        return onhandQty;
    }

    public void setOnhandQty(String onhandQty) {
        this.onhandQty = onhandQty;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public int getNormalQuantity() {
        return normalQuantity;
    }

    public void setNormalQuantity(int normalQuantity) {
        this.normalQuantity = normalQuantity;
    }

    public int getBadQuantity() {
        return badQuantity;
    }

    public void setBadQuantity(int badQuantity) {
        this.badQuantity = badQuantity;
    }

    public String getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setReturnedQuantity(String returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    public String getRepairQuantity() {
        return repairQuantity;
    }

    public void setRepairQuantity(String repairQuantity) {
        this.repairQuantity = repairQuantity;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }
}