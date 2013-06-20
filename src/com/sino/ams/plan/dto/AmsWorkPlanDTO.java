package com.sino.ams.plan.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: 工作计划管理 AmsWorkPlan</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsWorkPlanDTO extends CheckBoxDTO {

    private String planId = "";
    private String planName = "";
    private String planDesc = "";
    private int executeUser;
    private String executeUserName = "";


    private String planStatus = "";
    private Timestamp creationDate = null;
    private int createdBy;
    private Timestamp lastUpdateDate = null;
    private int lastUpdateBy;
    private String fromDate = null;
    private String toDate = null;
    private SimpleCalendar executeTime = null;
    private String strExecuteTime = "";
    private String loginName = "";
    private int organizationId;

    public AmsWorkPlanDTO() {
        this.executeTime = new SimpleCalendar();
    }

    public SimpleCalendar getExecuteTime() throws CalendarException {
        executeTime.setCalPattern(getCalPattern());
        return this.executeTime;
    }

    public void setExecuteTime(String executeTime) throws CalendarException {
        this.executeTime.setCalendarValue(executeTime);
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }
    /*  public Timestamp getExecuteTime() {
        return executeTime;
    }*/
/*	public void setExecuteTime(String executeTime) throws CalendarException{
		if(!StrUtil.isEmpty(executeTime)){
			SimpleCalendar cal = new SimpleCalendar(executeTime);
			this.executeTime = cal.getSQLTimestamp();
		}
	}*/

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * 功能：设置工作计划管理属性 上次修改人
     * @param planId String
     */
    public void setPlanId(String planId) {
        this.planId = planId;
    }

    /**
     * 功能：设置工作计划管理属性 上次修改人
     * @param planName String
     */
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    /**
     * 功能：设置工作计划管理属性 上次修改人
     * @param planDesc String
     */
    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    /**
     * 功能：设置工作计划管理属性 上次修改人
     * @param executeUser String
     */
    public void setExecuteUser(int executeUser) {
        this.executeUser = executeUser;
    }

    /**
     * 功能：设置工作计划管理属性 上次修改人
     * @param planStatus String
     */
    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    /**
     * 功能：设置工作计划管理属性 上次修改人
     * @param creationDate Timestamp
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setCreationDate(String creationDate) throws CalendarException {
        if (!StrUtil.isEmpty(creationDate)) {
            SimpleCalendar cal = new SimpleCalendar(creationDate);
            this.creationDate = cal.getSQLTimestamp();
        }
    }

    /**
     * 功能：设置工作计划管理属性 上次修改人
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置工作计划管理属性 上次修改人
     * @param lastUpdateDate Timestamp
     * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
     */
    public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
        if (!StrUtil.isEmpty(lastUpdateDate)) {
            SimpleCalendar cal = new SimpleCalendar(lastUpdateDate);
            this.lastUpdateDate = cal.getSQLTimestamp();
        }
    }

    /**
     * 功能：设置工作计划管理属性 上次修改人
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }


    /**
     * 功能：获取工作计划管理属性 上次修改人
     * @return String
     */
    public String getPlanId() {
        return this.planId;
    }

    /**
     * 功能：获取工作计划管理属性 上次修改人
     * @return String
     */
    public String getPlanName() {
        return this.planName;
    }

    /**
     * 功能：获取工作计划管理属性 上次修改人
     * @return String
     */
    public String getPlanDesc() {
        return this.planDesc;
    }

    /**
     * 功能：获取工作计划管理属性 上次修改人
     * @return String
     */
    public int getExecuteUser() {
        return this.executeUser;
    }

    /**
     * 功能：获取工作计划管理属性 上次修改人
     * @return String
     */
    public String getPlanStatus() {
        return this.planStatus;
    }

    /**
     * 功能：获取工作计划管理属性 上次修改人
     * @return Timestamp
     */
    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    /**
     * 功能：获取工作计划管理属性 上次修改人
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取工作计划管理属性 上次修改人
     * @return Timestamp
     */
    public Timestamp getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取工作计划管理属性 上次修改人
     * @return String
     */
    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    /*public String getStrExecuteTime() throws DateException {
        if(executeTime != null){
            SimpleCalendar cal = new SimpleCalendar(executeTime);
            strExecuteTime = cal.getSimpleDate().getDateValue();
        }
        return strExecuteTime;
    }*/
}