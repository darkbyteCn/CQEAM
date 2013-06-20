package com.sino.ams.system.procedure.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class MisDeptDTO extends CheckBoxDTO {
	private int organizationId;
	private String companyCode = "";
	private String companyName = "";
	private String deptCode = "";
	private String deptName = "";
	private String transferType = "";
    private String provinceCode = "";

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public MisDeptDTO() {
		super();
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
}
