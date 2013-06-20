package com.sino.flow.designer;

/**
 * Title:        ASBuilder Java Interface
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Tuofang network Ltd.
 *
 * @author Chenjian
 * @version 1.0
 */

public class TaskInfo {

    private String taskID;
    private String taskName;
    private String taskDesc;
    private int duration;
    private String roleID;
    private String groupID = "";
    private String readOnlyFlag;
    private String hiddenFlag;
    private String forms;
    private String nextID;
    private boolean isFinishTask;
    private boolean neglectable = false;
    private String taskControl;

    //以下属性于2007-11-07增加，added by wwb
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;

    public TaskInfo() {

    }

    public TaskInfo(String tID, String tName, String tDesc, int dura, String rID, String gID,
                    String rFlag, String hFlag, String fs, String nextID) {
        this.taskID = tID;
        this.taskName = tName;
        this.taskDesc = tDesc;
        this.duration = dura;
        this.roleID = rID;
        this.groupID = gID;
        this.readOnlyFlag = rFlag;
        this.hiddenFlag = hFlag;
        this.forms = fs;
        this.nextID = nextID;

        if ((this.roleID == null) & (this.groupID == null)) {
            this.isFinishTask = true;
        } else {
            this.isFinishTask = false;
        }

    }

    public boolean IsFinishTask() {
        return isFinishTask;
    }

    public boolean isNeglectable() {
        return neglectable;
    }

    public String getNextID() {
        return nextID;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public int getDuration() {
        return duration;
    }

    public String getRoleID() {
        return roleID;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getReadOnlyFlag() {
        return readOnlyFlag;
    }

    public String getHiddenFlag() {
        return hiddenFlag;
    }

    public String getForms() {
        return forms;
    }

    public String getTaskControl() {
        return taskControl;
    }

    public void setTaskControl(String taskControl) {
        this.taskControl = taskControl;
    }

    public boolean isFinishTask() {
        return isFinishTask;
    }

    public void setFinishTask(boolean finishTask) {
        isFinishTask = finishTask;
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

    /**
     * 此处插入方法说明。
     * 创建日期：(2002-3-19 11:21:46)
     *
     * @param str java.lang.String
     */
    public void setReadOnlyFlag(String str) {
        readOnlyFlag = str;
}}