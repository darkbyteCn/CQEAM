package com.sino.flow.dto;

import java.sql.Timestamp;

import com.sino.base.SinoBaseObject;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * <p>Title: SfActLog</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息技术有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class SfActLogDTO extends SinoBaseObject implements DTO {
    private String actid = "";
    private String procId = "";
    private String procName = "";
    private String curTaskId = "";
    private String curUserid = "";
    private String curStatus = "";
    private String curTaskPositionId = "";
    private String curTaskPositionName = "";
    private String curApplmsg = "";
    private Timestamp fromDt = null;
    private String fromTaskId = "";
    private String fromUserId = "";
    private Timestamp completeDt = null;
    private String completeStatus = "";
    private String completeUser = "";
    private String nextTaskId = "";
    private String nextUserId = "";
    private Timestamp creationDate = null;
    private int createdBy = -1;
    private String handleUser = "";
    private String deptId = "";
    private String deptName = "";
    public void setActid(String actid) {
        this.actid = actid;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public void setCurTaskId(String curTaskId) {
        this.curTaskId = curTaskId;
    }

    public void setCurUserid(String curUserid) {
        this.curUserid = curUserid;
    }

    public void setCurStatus(String curStatus) {
        this.curStatus = curStatus;
    }

    public void setCurTaskPositionId(String curTaskPositionId) {
        this.curTaskPositionId = curTaskPositionId;
    }

    public void setCurTaskPositionName(String curTaskPositionName) {
        this.curTaskPositionName = curTaskPositionName;
    }

    public void setCurApplmsg(String curApplmsg) {
        this.curApplmsg = curApplmsg;
    }

    public void setFromDt(String fromDt) throws CalendarException {
        if (!StrUtil.isEmpty(fromDt)) {
            SimpleCalendar cal = new SimpleCalendar(fromDt);
            this.fromDt = cal.getSQLTimestamp();
        }
    }

    public void setFromTaskId(String fromTaskId) {
        this.fromTaskId = fromTaskId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public void setCompleteDt(String completeDt) throws CalendarException {
        if (!StrUtil.isEmpty(completeDt)) {
            SimpleCalendar cal = new SimpleCalendar(completeDt);
            this.completeDt = cal.getSQLTimestamp();
        }
    }

    public void setCompleteStatus(String completeStatus) {
        this.completeStatus = completeStatus;
    }

    public void setCompleteUser(String completeUser) {
        this.completeUser = completeUser;
    }

    public void setNextTaskId(String nextTaskId) {
        this.nextTaskId = nextTaskId;
    }

    public void setNextUserId(String nextUserId) {
        this.nextUserId = nextUserId;
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

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getActid() {
        return this.actid;
    }

    public String getProcId() {
        return this.procId;
    }

    public String getProcName() {
        return this.procName;
    }

    public String getCurTaskId() {
        return this.curTaskId;
    }

    public String getCurUserid() {
        return this.curUserid;
    }

    public String getCurStatus() {
        return this.curStatus;
    }

    public String getCurTaskPositionId() {
        return this.curTaskPositionId;
    }

    public String getCurTaskPositionName() {
        return this.curTaskPositionName;
    }

    public String getCurApplmsg() {
        return this.curApplmsg;
    }

    public Timestamp getFromDt() {
        return this.fromDt;
    }

    public String getFromTaskId() {
        return this.fromTaskId;
    }

    public String getFromUserId() {
        return this.fromUserId;
    }

    public Timestamp getCompleteDt() {
        return this.completeDt;
    }

    public String getCompleteStatus() {
        return this.completeStatus;
    }

    public String getCompleteUser() {
        return this.completeUser;
    }

    public String getNextTaskId() {
        return this.nextTaskId;
    }

    public String getNextUserId() {
        return this.nextUserId;
    }

    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public int getCreatedBy() {
        return this.createdBy;
    }

    public String getHandleUser() {
        return this.handleUser;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public String getDeptName() {
        return this.deptName;
    }
}
