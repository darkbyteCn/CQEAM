package com.sino.ams.system.cost.dto;

import com.sino.ams.newasset.dto.AmsMisDeptDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-31
 * Time: 10:44:08
 * To change this template use File | Settings | File Templates.
 */
public class AmsMisCostMatchDTO extends AmsMisDeptDTO {
   private String  costCenterCode  = "";
   private String  costCenterName  = "";
   private String  costCode  = "";
   private String  costName  = "";
   private String  countyCodeMis  = "";
   private String  deptId  = "";

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getCountyCodeMis() {
        return countyCodeMis;
    }

    public void setCountyCodeMis(String countyCodeMis) {
        this.countyCodeMis = countyCodeMis;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}
