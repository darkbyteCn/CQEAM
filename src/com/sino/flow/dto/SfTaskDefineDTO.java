package com.sino.flow.dto;

import java.sql.Timestamp;

import com.sino.base.SinoBaseObject;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: SfTaskDefine</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息技术有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class SfTaskDefineDTO extends SinoBaseObject implements DTO {
    private String taskId = "";
    private String procId = "";
    private String taskName = "";
    private String taskMsg = "";
    private String flowDesc = "";
    private String taskPositionId = "";
    private String taskPositionName = "";
    private String sectionRight = "";
    private String hiddenRight = "";
    private String fromTaskId = "";
    private Timestamp creationDate = null;
    private int createdBy = -1;
    private String isHandUser = "";
    private String deptId = "";
    private String deptName = "";
    private String procName = "";
    private String description = "";
    private String appTableName = "";
    private String appPkName = "";
    private int organizationId = -1;
    private String taskProp = "";
    private String flowCode = "";//此字段只有流程找一个流向时调用
    private String attribute1 = "";
    private String attribute2 = "";
    private String attribute3 = "";
    private String attribute4 = "";
    private String attribute5 = "";

    public String getHandUser() {
        return isHandUser;
    }

    public void setHandUser(String handUser) {
        isHandUser = handUser;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public String getTaskProp() {
        return taskProp;
    }

    public void setTaskProp(String taskProp) {
        this.taskProp = taskProp;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskMsg(String taskMsg) {
        this.taskMsg = taskMsg;
    }

    public void setFlowDesc(String flowDesc) {
        this.flowDesc = flowDesc;
    }

    public void setTaskPositionId(String taskPositionId) {
        this.taskPositionId = taskPositionId;
    }

    public void setTaskPositionName(String taskPositionName) {
        this.taskPositionName = taskPositionName;
    }

    public void setSectionRight(String sectionRight) {
        this.sectionRight = sectionRight;
    }

    public void setHiddenRight(String hiddenRight) {
        this.hiddenRight = hiddenRight;
    }

    public void setFromTaskId(String fromTaskId) {
        this.fromTaskId = fromTaskId;
    }

    public void setCreationDate(String creationDate) throws CalendarException {
        if (!StrUtil.isEmpty(creationDate)) {
            SimpleCalendar cal = new SimpleCalendar(creationDate);
            this.creationDate = cal.getSQLTimestamp();
        }
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setIsHandUser(String isHandUser) {
        this.isHandUser = isHandUser;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAppTableName(String appTableName) {
        this.appTableName = appTableName;
    }

    public void setAppPkName(String appPkName) {
        this.appPkName = appPkName;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getProcId() {
        return this.procId;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public String getTaskMsg() {
        return this.taskMsg;
    }

    public String getFlowDesc() {
        return this.flowDesc;
    }

    public String getTaskPositionId() {
        return this.taskPositionId;
    }

    public String getTaskPositionName() {
        return this.taskPositionName;
    }

    public String getSectionRight() {
        return this.sectionRight;
    }

    public String getHiddenRight() {
        return this.hiddenRight;
    }

    public String getFromTaskId() {
        return this.fromTaskId;
    }

    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public int getCreatedBy() {
        return this.createdBy;
    }

    public String getIsHandUser() {
        return this.isHandUser;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public String getProcName() {
        return this.procName;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAppTableName() {
        return this.appTableName;
    }

    public String getAppPkName() {
        return this.appPkName;
    }

    public int getOrganizationId() {
        return this.organizationId;
    }
}
