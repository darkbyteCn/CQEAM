package com.sino.sinoflow.flowinterface;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-3-21
 * Time: 14:38:34
 * To change this template use File | Settings | File Templates.
 */
public abstract class AppFlowBaseDTO extends CommonRecordDTO {
    protected String primaryKey = "";
    protected String orderNo = "";
    protected String orderName = "";
    protected String status = "";

    private String sf_appFieldValue = "";//流程接收的所有参数
    private String app_dataID = "";//用于流程中
    private String sf_taskname = "";//用于流程中
    private String sf_end = "0";//0表示当前任务不结束流程，1表示结束流程
    private String signGroups = "";//会签部门(指流程中的组别)

    private String sf_task_attribute1 = "";
    private String sf_task_attribute2 = "";
    private String sf_task_attribute3 = "";
    private String sf_task_attribute4 = "";
    private String sf_task_attribute5 = "";
    private String sf_actID = "";
    private String sf_caseID = "";

    private String sf_appName = "";
    private String sfAppName = "";
    private String actID = "";
    private String nextGroup = "";
    private String nextUser = "";
    private String flowCode = "";
    private String comment = "";

    private String hasAuthority = "true";
    private String cancelApply = "";
    private String transType = "";

    private boolean isSubmit = false;

    private String sfOpinion = "";
    private String sf_opinion = "";

    private String act = "";

    public String getSf_actID() {
        return sf_actID;
    }


    public void setSf_actID(String sf_actID) {
        this.sf_actID = sf_actID;
    }

    public boolean isSubmit() {
        return isSubmit;
    }

    public void setSubmit(boolean isSubmit) {
        this.isSubmit = isSubmit;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSf_appFieldValue() {
        return sf_appFieldValue;
    }

    public void setSf_appFieldValue(String sf_appFieldValue) {
        this.sf_appFieldValue = sf_appFieldValue;
    }

    public String getApp_dataID() {
        return app_dataID;
    }

    public void setApp_dataID(String app_dataID) {
        this.app_dataID = app_dataID;
    }

    public String getSf_taskname() {
        return sf_taskname;
    }

    public void setSf_taskname(String sf_taskname) {
        this.sf_taskname = sf_taskname;
    }

    public String getSf_end() {
        return sf_end;
    }

    public void setSf_end(String sf_end) {
        this.sf_end = sf_end;
    }

    public String getSignGroups() {
        return signGroups;
    }

    public void setSignGroups(String signGroups) {
        this.signGroups = signGroups;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getSf_task_attribute1() {
        return sf_task_attribute1;
    }

    public void setSf_task_attribute1(String sf_task_attribute1) {
        this.sf_task_attribute1 = sf_task_attribute1;
    }

    public String getSf_task_attribute2() {
        return sf_task_attribute2;
    }

    public void setSf_task_attribute2(String sf_task_attribute2) {
        this.sf_task_attribute2 = sf_task_attribute2;
    }

    public String getSf_task_attribute3() {
        return sf_task_attribute3;
    }

    public void setSf_task_attribute3(String sf_task_attribute3) {
        this.sf_task_attribute3 = sf_task_attribute3;
    }

    public String getSf_task_attribute4() {
        return sf_task_attribute4;
    }

    public void setSf_task_attribute4(String sf_task_attribute4) {
        this.sf_task_attribute4 = sf_task_attribute4;
    }

    public String getSf_task_attribute5() {
        return sf_task_attribute5;
    }

    public void setSf_task_attribute5(String sf_task_attribute5) {
        this.sf_task_attribute5 = sf_task_attribute5;
    }

    public String getSf_appName() {
        return sf_appName;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public String getNextGroup() {
        return nextGroup;
    }

    public String getNextUser() {
        return nextUser;
    }

    public String getComment() {
        return comment;
    }

    public String getActID() {
        return actID;
    }

    public String getSfAppName() {
        return sfAppName;
    }

    public String getHasAuthority() {
        return hasAuthority;
    }

    public String getCancelApply() {
        return cancelApply;
    }

    public void setSf_appName(String sf_appName) {
        this.sf_appName = sf_appName;
        if (sfAppName.equals("") && !sf_appName.equals("")) {
            setSfAppName(sf_appName);
        }
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public void setNextGroup(String nextGroup) {
        this.nextGroup = nextGroup;
    }

    public void setNextUser(String nextUser) {
        this.nextUser = nextUser;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setActID(String actID) {
        this.actID = actID;
    }

    public void setSfAppName(String sfAppName) {
        this.sfAppName = sfAppName;
        if (sf_appName.equals("") && !sfAppName.equals("")) {
            setSf_appName(sfAppName);
        }
    }

    public void setHasAuthority(String hasAuthority) {
        this.hasAuthority = hasAuthority;
    }

    public void setCancelApply(String cancelApply) {
        this.cancelApply = cancelApply;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getSfOpinion() {
        return sfOpinion;
    }

    public void setSfOpinion(String sfOpinion) {
        this.sfOpinion = sfOpinion;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }


	public String getAct() {
		return act;
	}


	public void setAct(String act) {
		this.act = act;
	}


	public String getSf_caseID() {
		return sf_caseID;
	}


	public void setSf_caseID(String sf_caseID) {
		this.sf_caseID = sf_caseID;
	}

    public String getSf_opinion() {
        return sf_opinion;
    }

    public void setSf_opinion(String sf_opinion) {
        this.sf_opinion = sf_opinion;
    }
}