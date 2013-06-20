package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 组别属性 SfGroup</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SinoMisDeptDTO extends CheckBoxDTO{

    private String deptId = "";
    private String deptName = "";
    private int orgId = 0;
    private String enabled = "";
    private String companyCode = "";
    private String deptProperty = "";
    private String deptCode = "";
    private String parentDeptId = "";
    private String displayOrder = "";
    private String secondDept = "";
    private String specialityDept = "";

    public SinoMisDeptDTO() {
		super();

	}

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setOrgId(int orgId){
		this.orgId = orgId;
	}

	public int getOrgId() {
		return this.orgId;
	}

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setDeptProperty(String deptProperty) {
        this.deptProperty = deptProperty;
    }

    public String getDeptProperty() {
        return this.deptProperty;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public void setParentDeptId(String parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    public String getParentDeptId() {
        return this.parentDeptId;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDisplayOrder() {
        return this.displayOrder;
    }

    public void setSecondDept(String secondDept) {
        this.secondDept = secondDept;
    }

    public String getSecondDept() {
        return this.secondDept;
    }

    public void setSpecialityDept(String specialityDept) {
        this.specialityDept = specialityDept;
    }

    public String getSpeicalityDept() {
        return this.specialityDept;
    }
}