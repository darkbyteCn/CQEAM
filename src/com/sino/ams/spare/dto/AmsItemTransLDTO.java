package com.sino.ams.spare.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class AmsItemTransLDTO extends CheckBoxDTO {

    private String transId = "";
    private String lineId = "";
    private String barcode = "";
    private String itemName = "";
    private String itemSpec = "";
    private String itemCode = "-1";
    private int quantity = 0;
    private String itemCodes = "";
    private int organizationId = -1;
    private String batchNo = "";            //非条码设备入库使用
    private String storageId = "";          //非条码设备入库使用
    private int outQuantity = 0;          //非条码设备入库使用
    private String objectNo = "";
    private int acceptQty = 0;
    private int onhandQty = 0;
    private String serialNo = "";
    private String spareUsage = "";
    private String remark = "";
    private String spareId= "";
    private String vendorName = "";
    private String reasons = "";
    private String objectCategory = "";
    private String vendorId = "";
    private String remarkl = "";
    private String dxkQty = "";
    private String searchYear = "";
    private String searchMonth = "";
    private int actualQty = 0;
    private String workorderObjectName = "";
    private String itemCategory = "";
    private int proQty = 0;
    private int ordQty = 0;

    private String normalQuantity = "";
    private String badQuantity = "";
    private String itemUnit = "";

    public String getNormalQuantity() {
        return normalQuantity;
    }

    public void setNormalQuantity(String normalQuantity) {
        this.normalQuantity = normalQuantity;
    }

    public String getBadQuantity() {
        return badQuantity;
    }

    public void setBadQuantity(String badQuantity) {
        this.badQuantity = badQuantity;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public int getProQty() {
        return proQty;
    }

    public void setProQty(int proQty) {
        this.proQty = proQty;
    }

    public int getOrdQty() {
        return ordQty;
    }

    public void setOrdQty(int ordQty) {
        this.ordQty = ordQty;
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

    public String getDxkQty() {
        return dxkQty;
    }

    public void setDxkQty(String dxkQty) {
        this.dxkQty = dxkQty;
    }


    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getObjectCategory() {
        return objectCategory;
    }

    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }

    public String getRemarkl() {
        return remarkl;
    }

    public void setRemarkl(String remarkl) {
        this.remarkl = remarkl;
    }



    public String getBfje() {
        return bfje;
    }

    public void setBfje(String bfje) {
        this.bfje = bfje;
    }

    private String bfje = "" ;  //报废金额

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getSpareId() {
        return spareId;
    }

    public void setSpareId(String spareId) {
        this.spareId = spareId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getSpareUsage() {
        return spareUsage;
    }

    public void setSpareUsage(String spareUsage) {
        this.spareUsage = spareUsage;
    }

    /**
     * 功能：设置备件事务行表(AMS)属性 事务交易ID
     *
     * @param transId String
     */
    public void setTransId(String transId) {
        this.transId = transId;
    }

    /**
     * 功能：设置备件事务行表(AMS)属性 行ID
     *
     * @param lineId String
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * 功能：设置备件事务行表(AMS)属性 设备条码
     *
     * @param barcode String
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }


    /**
     * 功能：获取备件事务行表(AMS)属性 事务交易ID
     *
     * @return String
     */
    public String getTransId() {
        return this.transId;
    }

    /**
     * 功能：获取备件事务行表(AMS)属性 行ID
     *
     * @return String
     */
    public String getLineId() {
        return this.lineId;
    }

    /**
     * 功能：获取备件事务行表(AMS)属性 设备条码
     *
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

    public int getAcceptQty() {
        return acceptQty;
    }

    public void setAcceptQty(int acceptQty) {
        this.acceptQty = acceptQty;
    }

    public int getOnhandQty() {
        return onhandQty;
    }

    public void setOnhandQty(int onhandQty) {
        this.onhandQty = onhandQty;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSearchYear() {
        return searchYear;
    }

    public void setSearchYear(String searchYear) {
        this.searchYear = searchYear;
    }

    public String getSearchMonth() {
        return searchMonth;
    }

    public void setSearchMonth(String searchMonth) {
        this.searchMonth = searchMonth;
    }

    public int getActualQty() {
        return actualQty;
    }

    public void setActualQty(int actualQty) {
        this.actualQty = actualQty;
    }
}
