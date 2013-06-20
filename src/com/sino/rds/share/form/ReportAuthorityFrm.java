package com.sino.rds.share.form;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;
import com.sino.rds.appbase.form.RDSBaseFrm;


/**
 * <p>Title: 报表查询权限 RDS_REPORT_AUTHORITY</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class ReportAuthorityFrm extends RDSBaseFrm {

    private String authorityId = "";
    private String reportId = "";
    private String roleId = "";
    private String userId = "";
    private SimpleCalendar effectiveStartDate = null;
    private SimpleCalendar effectiveEndDate = null;

    private String reportName = "";
    private String roleName = "";
    private String userName = "";

    public ReportAuthorityFrm() {
        super();
        primaryKeyName = "authorityId";
        this.effectiveStartDate = new SimpleCalendar();
        this.effectiveEndDate = new SimpleCalendar();
    }


    /**
     * 功能：设置报表查询权限属性 权限ID
     *
     * @param authorityId String
     */
    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    /**
     * 功能：设置报表查询权限属性 报表ID
     *
     * @param reportId String
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * 功能：设置报表查询权限属性 角色ID
     *
     * @param roleId String
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 功能：设置报表查询权限属性 用户ID
     *
     * @param userId String
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 功能：设置报表查询权限属性 生效日期
     *
     * @param effectiveStartDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setEffectiveStartDate(String effectiveStartDate) throws CalendarException {
        this.effectiveStartDate.setCalendarValue(effectiveStartDate);
    }

    /**
     * 功能：设置报表查询权限属性 失效日期
     *
     * @param effectiveEndDate SimpleCalendar
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setEffectiveEndDate(String effectiveEndDate) throws CalendarException {
        this.effectiveEndDate.setCalendarValue(effectiveEndDate);
    }


    /**
     * 功能：获取报表查询权限属性 权限ID
     *
     * @return String
     */
    public String getAuthorityId() {
        return this.authorityId;
    }

    /**
     * 功能：获取报表查询权限属性 报表ID
     *
     * @return String
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * 功能：获取报表查询权限属性 角色ID
     *
     * @return String
     */
    public String getRoleId() {
        return this.roleId;
    }

    /**
     * 功能：获取报表查询权限属性 用户ID
     *
     * @return String
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 功能：获取报表查询权限属性 生效日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getEffectiveStartDate() throws CalendarException {
        effectiveStartDate.setCalPattern(getCalPattern());
        return this.effectiveStartDate;
    }

    /**
     * 功能：获取报表查询权限属性 失效日期
     *
     * @return SimpleCalendar
     * @throws CalendarException 设置的日历格式不合法时抛出该异常
     */
    public SimpleCalendar getEffectiveEndDate() throws CalendarException {
        effectiveEndDate.setCalPattern(getCalPattern());
        return this.effectiveEndDate;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}