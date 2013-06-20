package com.sino.ams.newasset.dto;

import com.sino.base.dto.CheckBoxDTO;

public class AmsMisCostDTO extends CheckBoxDTO {
	private String costCode = "";
	private String costName = "";
    private String companyCode="";
    private int organizationId = -1 ;

    public AmsMisCostDTO() {
		super();
	}

	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}

	public void setCostName(String costName) {
		this.costName = costName;
	}

	public String getCostCode() {
		return costCode;
	}

	public String getCostName() {
		return costName;
	}

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }
}
