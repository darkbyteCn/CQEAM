package com.sino.ams.spare.repair.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: V-jiachuanchuan
 * Date: 2007-11-29
 * Time: 14:52:45
 * To change this template use File | Settings | File Templates.
 */
public class RepairStatisticDTO extends CheckBoxDTO {
    private int orgId = -1;
    private String itemName;
    private String itemSpec;
    private String transDate;
    private String month;
    private String year;
    private String statisticType;
    private String vendorName = "";
    private String vendorCode = "";
    private String repairQuery = "";   //设备返修查询方式


    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getRepairQuery() {
        return repairQuery;
    }

    public void setRepairQuery(String repairQuery) {
        this.repairQuery = repairQuery;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getStatisticType() {
        return statisticType;
    }

    public void setStatisticType(String statisticType) {
        this.statisticType = statisticType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
}
