package com.sino.ams.system.house.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-4-10
 * Time: 16:26:19
 */
public class AmsHouseUsesDTO extends CheckBoxDTO {
    private String barcode = "";
    private String area = "";
    private String remark = "";
    private String usesId = "";
     private String usage = "";

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUsesId() {
        return usesId;
    }

    public void setUsesId(String usesId) {
        this.usesId = usesId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
