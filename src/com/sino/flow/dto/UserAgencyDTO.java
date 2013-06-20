package com.sino.flow.dto;

import com.sino.base.SinoBaseObject;
import com.sino.base.dto.DTO;

/**
 * Created by IntelliJ IDEA.
 * User: Lijun
 * Date: 2007-1-23
 * Time: 11:36:35
 * To change this template use File | Settings | File Templates.
 */
public class UserAgencyDTO extends SinoBaseObject implements DTO {
    private String userId = "";
    private String agentUserId = "";
    private String activeStartDate = "";
    private String activeEndDate = "";
    private String procId = "";
    private String deptId = "";
    private String note = "";
    private String disableDate = "";
    private String userName = "";
    private String deptName = "";
    private String agentUserName = "";
    private String id = "";

//    private String
//    private String
//    private String
//    private String
//    private String
//    private String
//    private String

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentUserName() {
        return agentUserName;
    }

    public void setAgentUserName(String agentUserName) {
        this.agentUserName = agentUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAgentUserId() {
        return agentUserId;
    }

    public void setAgentUserId(String agentUserId) {
        this.agentUserId = agentUserId;
    }

    public String getActiveStartDate() {
        return activeStartDate;
    }

    public void setActiveStartDate(String activeStartDate) {
        this.activeStartDate = activeStartDate;
    }

    public String getActiveEndDate() {
        return activeEndDate;
    }

    public void setActiveEndDate(String activeEndDate) {
        this.activeEndDate = activeEndDate;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDisableDate() {
        return disableDate;
    }

    public void setDisableDate(String disableDate) {
        this.disableDate = disableDate;
    }
}
