package com.sino.ams.system.cost.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * User: zhoujs
 * Date: 2008-7-31
 * Time: 14:50:45
 * Function: 成本中心DTO
 */
public class CostCenterDTO extends CommonRecordDTO {
    private String costCenterCode = "";
    private String costCenterName = "";
    private String companyCode = "";
    private int organizationId;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

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

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }
}
