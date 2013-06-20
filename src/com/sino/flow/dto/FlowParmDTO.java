package com.sino.flow.dto;

import com.sino.base.dto.DTO;

/**
 * Created by wwb.
 * User: demo
 * Date: 2006-12-18
 * Time: 15:42:25
 */
public class FlowParmDTO implements DTO {
    private String procId = "";//流程ID
    private String procName = "";//流程名
    private String applyId = "";//应用ID
    private String currTaskId = "";//当前结点ID
    private String currDeptId = "";//当前部门ID
    private String currDeptName = "";//当前部门名称
    private String nextTaskId = "";//下一结点ID
    private String nextDeptId = "";//下一部门ID
    private String nextDeptName = "";//下一部门名称;
    private String orgId = "";//OU
    private String userId = "";//
    /**
     * 以下属性为流程查找下一用户扩展字段
     */
    private String appId = "";//流程查找下一用户扩展字段
    private String extendType = "";
    private String attr1 = "";
    private String attr2 = "";
    private String attr3 = "";
    private String ou = "";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getExtendType() {
        return extendType;
    }

    public void setExtendType(String extendType) {
        this.extendType = extendType;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getCurrTaskId() {
        return currTaskId;
    }

    public void setCurrTaskId(String currTaskId) {
        this.currTaskId = currTaskId;
    }

    public String getCurrDeptId() {
        return currDeptId;
    }

    public void setCurrDeptId(String currDeptId) {
        this.currDeptId = currDeptId;
    }

    public String getCurrDeptName() {
        return currDeptName;
    }

    public void setCurrDeptName(String currDeptName) {
        this.currDeptName = currDeptName;
    }

    public String getNextTaskId() {
        return nextTaskId;
    }

    public void setNextTaskId(String nextTaskId) {
        this.nextTaskId = nextTaskId;
    }

    public String getNextDeptId() {
        return nextDeptId;
    }

    public void setNextDeptId(String nextDeptId) {
        this.nextDeptId = nextDeptId;
    }

    public String getNextDeptName() {
        return nextDeptName;
    }

    public void setNextDeptName(String nextDeptName) {
        this.nextDeptName = nextDeptName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
