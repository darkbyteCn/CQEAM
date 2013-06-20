package com.sino.ams.system.manufacturer;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-2-4
 * Time: 16:30:05
 * To change this template use File | Settings | File Templates.
 */
public class EtsManufacturerDTO extends CheckBoxDTO {
    private String  manufacturerId ="" ;
    private String manufacturerCode = "";
    private String manufacturerName = "";
    private String enable = "Y";
    private int createBy ;
    private String createDate = null;
    private int lastUpdateBy ;
    private String lastUadateDate = null;

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getLastUadateDate() {
        return lastUadateDate;
    }

    public void setLastUadateDate(String lastUadateDate) {
        this.lastUadateDate = lastUadateDate;
    }
}
