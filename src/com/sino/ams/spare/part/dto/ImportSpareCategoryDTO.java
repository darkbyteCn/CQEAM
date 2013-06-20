package com.sino.ams.spare.part.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: wangzp
 * Date: 2011-12-12
 * Function: 备件设备分类导入
 * To change this template use File | Settings | File Templates.
 */
public class ImportSpareCategoryDTO extends CommonRecordDTO {
    private String id = "";
    private String barcode = "";
    private String itemName = "";
    private String itemSpec = "";
    private String itemCategory = "";
    private String spareUsage = "";
    private String vendorId = "";
    private String vendorName = "";
    private String itemUnit = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public String getSpareUsage() {
        return spareUsage;
    }

    public void setSpareUsage(String spareUsage) {
        this.spareUsage = spareUsage;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }
}
