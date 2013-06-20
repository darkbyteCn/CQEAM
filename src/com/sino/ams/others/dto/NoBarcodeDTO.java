package com.sino.ams.others.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-7-9
 * Time: 16:19:07
 * To change this template use File | Settings | File Templates.
 */
public class NoBarcodeDTO extends CheckBoxDTO {
      private String batchNo = "";
    private String itemName = "";
    private String itemSpec = "";
    private String itemUnit = "";
    private String toObjectNo = "";
    private int toOrganizationId;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getToObjectNo() {
        return toObjectNo;
    }

    public void setToObjectNo(String toObjectNo) {
        this.toObjectNo = toObjectNo;
    }

    public int getToOrganizationId() {
        return toOrganizationId;
    }

    public void setToOrganizationId(int toOrganizationId) {
        this.toOrganizationId = toOrganizationId;
    }
}
