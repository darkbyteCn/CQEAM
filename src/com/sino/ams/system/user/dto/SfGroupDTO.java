package com.sino.ams.system.user.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: SfGroup</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class SfGroupDTO extends CheckBoxDTO {
    private int groupId ;
    private String groupCode = "";
    private String groupname = "";
    private String groupName = "";
    private String groupPid = "";
    private int organizationId;
    private String sortno = "";
    private String isroot = "";
    private String category = "";
    private String enabled = "";
    private String isInner = "";
    private Timestamp creationDate = null;
    private int createdBy;
    private Timestamp lastUpdateDate = null;
    private int lastUpdateBy;
    private String isDesigner = "";
    private int pFlowId;
    private String categoryDesc = "";
    private String groupProp = "";
    private String isDesignerOption = "";
    private String flowGroupOption = "";
    private String enableOption = "";
    private String groupThred = "";
    private String deptId = "";
    private String deptName = "";

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    /**
     * 功能：获取DTO属性 pFlowId
     * @return String
     */
    public int getpFlowId() {
        return this.pFlowId;
    }

    /**
     * 功能：设置DTO属性 pFlowId
     * @param pFlowId String
     */
    public void setpFlowId(int pFlowId) {
        this.pFlowId = pFlowId;
    }

    /**
     * 功能：获取DTO属性 isDesigner
     * @return String
     */
    public String getIsDesigner() {
        return this.isDesigner;
    }

    /**
     * 功能：设置DTO属性 isDesigner
     * @param isDesigner String
     */
    public void setIsDesigner(String isDesigner) {
        this.isDesigner = isDesigner;
    }

    /**
     * 功能：设置DTO属性 groupId
     * @param groupId String
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * 功能：设置DTO属性 groupCode
     * @param groupCode String
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    /**
     * 功能：设置DTO属性 groupname
     * @param groupname String
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    /**
     * 功能：设置DTO属性 groupPid
     * @param groupPid String
     */
    public void setGroupPid(String groupPid) {
        this.groupPid = groupPid;
    }

    /**
     * 功能：设置DTO属性 organizationId
     * @param organizationId String
     */
    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 功能：设置DTO属性 sortno
     * @param sortno String
     */
    public void setSortno(String sortno) {
        this.sortno = sortno;
    }

    /**
     * 功能：设置DTO属性 isroot
     * @param isroot String
     */
    public void setIsroot(String isroot) {
        this.isroot = isroot;
    }

    /**
     * 功能：设置DTO属性 category
     * @param category String
     */
    public void setCategory(String category) {
        this.category = category;
    }


    /**
     * 功能：设置DTO属性 enabled
     * @param enabled String
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * 功能：设置DTO属性 isInner
     * @param isInner String
     */
    public void setIsInner(String isInner) {
        this.isInner = isInner;
    }

    /**
     * 功能：设置DTO属性 creationDate
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
     * 功能：设置DTO属性 createdBy
     * @param createdBy String
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 功能：设置DTO属性 lastUpdateDate
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
     * 功能：设置DTO属性 lastUpdateBy
     * @param lastUpdateBy String
     */
    public void setLastUpdateBy(int lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setGroupProp(String groupProp) {
        this.groupProp = groupProp;
    }

    public void setFlowGroupOption(String flowGroupOption) {
        this.flowGroupOption = flowGroupOption;
    }

    public void setIsDesignerOption(String isDesignerOption) {
        this.isDesignerOption = isDesignerOption;
    }

    public void setEnableOption(String enableOption) {
        this.enableOption = enableOption;
    }

    /**
     * 功能：获取DTO属性 groupId
     * @return String
     */
    public int getGroupId() {
        return this.groupId;
    }

    /**
     * 功能：获取DTO属性 groupCode
     * @return String
     */
    public String getGroupCode() {
        return this.groupCode;
    }

    /**
     * 功能：获取DTO属性 groupname
     * @return String
     */
    public String getGroupname() {
        return this.groupname;
    }

    /**
     * 功能：获取DTO属性 groupPid
     * @return String
     */
    public String getGroupPid() {
        return this.groupPid;
    }

    /**
     * 功能：获取DTO属性 organizationId
     * @return String
     */
    public int getOrganizationId() {
        return this.organizationId;
    }

    /**
     * 功能：获取DTO属性 sortno
     * @return String
     */
    public String getSortno() {
        return this.sortno;
    }

    /**
     * 功能：获取DTO属性 isroot
     * @return String
     */
    public String getIsroot() {
        return this.isroot;
    }

    /**
     * 功能：获取DTO属性 category
     * @return String
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * 功能：获取DTO属性 enabled
     * @return String
     */
    public String getEnabled() {
        return this.enabled;
    }

    /**
     * 功能：获取DTO属性 isInner
     * @return String
     */
    public String getIsInner() {
        return this.isInner;
    }

    /**
     * 功能：获取DTO属性 creationDate
     * @return Timestamp
     */
    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    /**
     * 功能：获取DTO属性 createdBy
     * @return String
     */
    public int getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 功能：获取DTO属性 lastUpdateDate
     * @return Timestamp
     */
    public Timestamp getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
     * 功能：获取DTO属性 lastUpdateBy
     * @return String
     */
    public int getLastUpdateBy() {
        return this.lastUpdateBy;
    }

    public String getGroupProp() {
        return groupProp;
    }

    public String getFlowGroupOption() {
        return flowGroupOption;
    }

    public String getIsDesignerOption() {
        return isDesignerOption;
    }

    public String getEnableOption() {
        return enableOption;
    }

    public String getGroupThred() {
        return groupThred;
    }

    public void setGroupThred(String groupThred) {
        this.groupThred = groupThred;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
