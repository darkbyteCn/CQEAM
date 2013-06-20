package com.sino.ams.newasset.dto;


import com.sino.ams.bean.CommonRecordDTO;

/**
 * <p>Title: 部门表(EAM) AmsMisDept</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsMisDeptDTO extends CommonRecordDTO {

    private String companyCode = "";
    private String deptCode = "";
    private String deptName = "";
    private String enabled = "";
    private int organizationId;
    private int displayOrder;

    public AmsMisDeptDTO() {
        super();
    }

    /**
     * 功能：设置部门表(EAM)属性 公司代码
     * @param companyCode String
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * 功能：设置部门表(EAM)属性 部门代码
     * @param deptCode String
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * 功能：设置部门表(EAM)属性 部门名称
     * @param deptName String
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：获取部门表(EAM)属性 公司代码
     * @return String
     */
    public String getCompanyCode() {
        return this.companyCode;
    }

    /**
     * 功能：获取部门表(EAM)属性 部门代码
     * @return String
     */
    public String getDeptCode() {
        return this.deptCode;
    }

    /**
     * 功能：获取部门表(EAM)属性 部门名称
     * @return String
     */
    public String getDeptName() {
        return this.deptName;
    }

    public String getEnabled() {
        return enabled;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
