package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * <p>Title: 资产管理权限表(EAM) AmsAssetsPrivi</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsPriviDTO extends CommonRecordDTO {

    private String userId = "";
    private String deptCode = "";
    private String roleId = "";
    private String companyCode = "";
    private int priviId;
    private String userName = "";
    private String roleName = "";
    private String deptName = "";
    private String companyName = "";
    private String provinceCode = "";
    private String loginName = "";
    private String srcPage = "";
    private boolean saved = true;

    private String faCategoryCode = "";
    private String faCategoryName = "";
    private String employeeNumber = "";

    private String existPriviOption = "";
    private String noPriviOption = "";
    private int groupId;
    
    private int organizationId;
    
	private String  record = "";
    /**
     * 功能：设置资产管理权限表(EAM)属性 用户ID
     * @param userId String
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 功能：设置资产管理权限表(EAM)属性 部门ID
     * @param deptCode String
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * 功能：设置资产管理权限表(EAM)属性 角色ID
     * @param roleId String
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 功能：设置资产管理权限表(EAM)属性 公司代码
     * @param companyCode String
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * 功能：设置资产管理权限表(EAM)属性 序列号
     * @param priviId String
     */
    public void setPriviId(int priviId) {
        this.priviId = priviId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setSrcPage(String srcPage) {
        this.srcPage = srcPage;
    }

    public void setFaCategoryCode(String faCategoryCode) {
        this.faCategoryCode = faCategoryCode;
    }

    public void setFaCategoryName(String faCategoryName) {
        this.faCategoryName = faCategoryName;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setExistPriviOption(String existPriviOption) {
        this.existPriviOption = existPriviOption;
    }

    public void setNoPriviOption(String noPriviOption) {
        this.noPriviOption = noPriviOption;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * 功能：获取资产管理权限表(EAM)属性 用户ID
     * @return String
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 功能：获取资产管理权限表(EAM)属性 部门ID
     * @return String
     */
    public String getDeptCode() {
        return this.deptCode;
    }

    /**
     * 功能：获取资产管理权限表(EAM)属性 角色ID
     * @return String
     */
    public String getRoleId() {
        return this.roleId;
    }

    /**
     * 功能：获取资产管理权限表(EAM)属性 公司代码
     * @return String
     */
    public String getCompanyCode() {
        return this.companyCode;
    }

    /**
     * 功能：获取资产管理权限表(EAM)属性 序列号
     * @return String
     */
    public int getPriviId() {
        return this.priviId;
    }

    public String getUserName() {
        return userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public boolean isSaved() {
        return saved;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getSrcPage() {
        return srcPage;
    }

    public String getFaCategoryCode() {
        return faCategoryCode;
    }

    public String getFaCategoryName() {
        return faCategoryName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getExistPriviOption() {
        return existPriviOption;
    }

    public String getNoPriviOption() {
        return noPriviOption;
    }

    public int getGroupId() {
        return groupId;
    }

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

}
