package com.sino.sinoflow.user.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: 用戶信息 SfUser</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class SfUserBaseDTO extends BaseUserDTO {

    private int userId = 0;
    private String username = "";
    private String loginName = "";
    private String password = "";
    private String employeeId = "";
    private String employeeNumber = "";
    private int displayOrder = 0;
    private String officeTel = "";
    private String fax = "";
    private String mobilePhone = "";
    private String email = "";
    private String organization = "";
    private int organizationId = 0;
    private int orgId = 0;
    private boolean sysAdmin = false;
    private boolean provinceUser = false;
    private int workScheduleId = 0;

    private String groupName = "";
    private String projectName = "";
    private String roleName = "";
    private String categoryCode = "";
    private DTOSet authoritys = null;

    private String enabled = "";
    private SimpleCalendar startDate;
    private SimpleCalendar endDate;
    private SimpleCalendar disableDate;

    public SfUserBaseDTO() {
        super();
        authoritys = new DTOSet();
        startDate=new SimpleCalendar();
        endDate=new SimpleCalendar();
        disableDate=new SimpleCalendar();
    }
    	public SimpleCalendar getDisableDate() {
		return disableDate;
	}

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public void setDisableDate(String disableDate) throws CalendarException {
		this.disableDate.setCalendarValue(disableDate);
	}
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
 
    public SimpleCalendar getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) throws CalendarException {
        if (!StrUtil.isEmpty(startDate)) {
            this.startDate.setCalendarValue(startDate);
        }
    }

    public SimpleCalendar getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String disableDate) throws CalendarException {
        if (!StrUtil.isEmpty(disableDate)) {
            this.endDate.setCalendarValue(disableDate);
        }
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * 功能：设置用戶信息属性 用户 ID
     * @param userId String
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 功能：设置用戶信息属性 登陆帐号
     * @param username String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 功能：设置用戶信息属性 用户真实姓名
     * @param loginName String
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 功能：设置用戶信息属性 登陆密码
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 功能：设置用戶信息属性 员工编号
     * @param employeeId String
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 功能：设置用戶信息属性 办公电话
     * @param officeTel String
     */
    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    /**
     * 功能：设置用戶信息属性 传真号码
     * @param fax String
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 功能：设置用戶信息属性 移动电话
     * @param mobilePhone String
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * 功能：设置用戶信息属性 E-Mail 地址
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 功能：设置用戶信息属性 公司
     * @param organization String
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public void setSysAdmin(boolean sysAdmin) {
        this.sysAdmin = sysAdmin;
    }

    public void setProvinceUser(boolean provinceUser) {
        this.provinceUser = provinceUser;
    }

    public void setWorkScheduleId(int workScheduleId) {
        this.workScheduleId = workScheduleId;
    }
/*
	public void setUserGroups(DTOSet userGroups) {
		this.userGroups = userGroups;
	}
*/

    /**
     * 功能：获取用戶信息属性 用户 ID
     * @return String
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * 功能：获取用戶信息属性 登陆帐号
     * @return String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 功能：获取用戶信息属性 用户真实姓名
     * @return String
     */
    public String getLoginName() {
        return this.loginName;
    }

    /**
     * 功能：获取用戶信息属性 登陆密码
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 功能：获取用戶信息属性 员工编号
     * @return String
     */
    public String getEmployeeId() {
        return this.employeeId;
    }

    /**
     * 功能：获取用戶信息属性 办公电话
     * @return String
     */
    public String getOfficeTel() {
        return this.officeTel;
    }

    /**
     * 功能：获取用戶信息属性 传真号码
     * @return String
     */
    public String getFax() {
        return this.fax;
    }

    /**
     * 功能：获取用戶信息属性 移动电话
     * @return String
     */
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    /**
     * 功能：获取用戶信息属性 E-Mail 地址
     * @return String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * 功能：获取用戶信息属性 公司
     * @return String
     */
    public String getOrganization() {
        return this.organization;
    }

    public int getOrganizationId() {
        return this.organizationId;
    }

    public boolean isSysAdmin() {
        return sysAdmin;
    }

    public boolean isProvinceUser() {
        return provinceUser;
    }

    public int getWorkScheduleId() {
        return workScheduleId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public String getProjectName() {
        return projectName;
    }

    public DTOSet getAuthoritys() {
        return authoritys;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setAuthoritys(DTOSet authoritys) {
        this.authoritys = authoritys;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId; 
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    /*
	public DTOSet getUserGroups() {
		return userGroups;
	}
*/
}
