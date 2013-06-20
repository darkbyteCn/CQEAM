package com.sino.flow.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.SinoBaseObject;
import com.sino.base.dto.DTO;
import com.sino.flow.constant.FlowConstant;

/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-11-9
 * Time: 14:48:00
 * FlowAction类参数DTO
 */
public class FlowDTO extends SinoBaseObject implements DTO {
    private String applyId = "";//应用ID   不能为空
    private String applyNo = "";//应用编号  不能为空
    private String actId = "";//流程记录ID   第一次可以为空，
    private String activity = "";//流程的流向，正向（同意）为9，负向（拒绝）为10； 不能为空
    private String sessionCurTaskId = "";//客户端保存的当前节点，流程流转时，不能为空
    private int sessionUserId = SyBaseSQLUtil.NULL_INT_VALUE; //当前用户，但不一定是责任人，有可能是代理人 不能为空
    private String sessionUserName = "";//当前用户姓名      不能为空，主要用于短信通知
    private String approveContent = "";//审批意见， 最好不为空
    private String toTaskId = "";//目的节点    如果正向，不能为空
    private String toUserIds = "";//目的人，可能为多数 多个用分号分隔   如果正向，不能为空
    private String procId = "";//可以为空
    private String prevTaskId = "";//如果一步一步退回，应该退到的节点，可以为空
    private String prevUserId = "";//如果一步一步退回，应该退到的用户  可以为空，如果为空，系统会自动找
    private String curTaskId = "";//当前节点，可以为空
    private String beginTaskId = "";//一个流程的开始节点，可以为空
    private String beginUserId = "";//一个流程的启动人，可以为空
    private String procName = "";
    private String sectionRight = "";
    
    protected String primaryKey = "";
    protected String orderNo = "";
    protected String orderName = "";

    public FlowDTO() {
    }

    public FlowDTO(String applyId, String applyNo, String actId, String activity, String sessionCurTaskId, int sessionUserId, String sessionUserName, String approveContent, String toTaskId, String toUserIds, String procId) {
        this.applyId = applyId;
        this.applyNo = applyNo;
        this.actId = actId;
        this.activity = activity;
        this.sessionUserId = sessionUserId;
        this.approveContent = approveContent;
        this.toTaskId = toTaskId;
        this.toUserIds = toUserIds;
        this.sessionUserName = sessionUserName;
        this.procId = procId;
        this.sessionCurTaskId = sessionCurTaskId;
    }

    public String getApplyId() {
        return applyId;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getSessionUserId() {
        return sessionUserId;
    }

    public void setSessionUserId(int sessionUserId) {
        this.sessionUserId = sessionUserId;
    }

    public String getApproveContent() {
        return approveContent;
    }

    public void setApproveContent(String approveContent) {
        this.approveContent = approveContent;
    }

    public String getToTaskId() {
        return toTaskId;
    }

    public void setToTaskId(String toTaskId) {
        this.toTaskId = toTaskId;
    }

    public String getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(String toUserIds) {
        this.toUserIds = toUserIds;
    }

    public String getSessionUserName() {
        return sessionUserName;
    }

    public void setSessionUserName(String sessionUserName) {
        this.sessionUserName = sessionUserName;
    }

    public String getPrevTaskId() {
        return prevTaskId;
    }

    public void setPrevTaskId(String prevTaskId) {
        this.prevTaskId = prevTaskId;
    }

    public String getPrevUserId() {
        return prevUserId;
    }

    public void setPrevUserId(String prevUserId) {
        this.prevUserId = prevUserId;
    }

    public String getCurTaskId() {
        return curTaskId;
    }

    public void setCurTaskId(String curTaskId) {
        this.curTaskId = curTaskId;
    }

    public String getBeginTaskId() {
        return beginTaskId;
    }

    public void setBeginTaskId(String beginTaskId) {
        this.beginTaskId = beginTaskId;
    }

    public String getBeginUserId() {
        return beginUserId;
    }

    public void setBeginUserId(String beginUserId) {
        this.beginUserId = beginUserId;
    }

    public String getSessionCurTaskId() {
        return sessionCurTaskId;
    }

    public void setSessionCurTaskId(String sessionCurTaskId) {
        this.sessionCurTaskId = sessionCurTaskId;
    }

    //本DTO是参数DTO，DTO中有些参数是不能为空的，如果为空，流程没办法流转，
    public String validate() {
        String retValue = "";
        if (this.activity == null || this.activity.equals("")) {
            retValue = "activity";
        } else if (this.activity.equals(FlowConstant.FLOW_CODE_NEXT)) {
            //下一个节点ID不能为空，如果为空，无法扭转
            if (this.toTaskId == null || this.toTaskId.equals("")) {
                retValue = "toTaskId";
            }

        } else if ( this.sessionUserId > 0 ) {
            retValue = "sessionUserId";
        } else if (this.sessionCurTaskId == null || this.sessionCurTaskId.equals("")) {
            retValue = "sessionCurTaskId";// 这个数据是要确保流程表单没有重复提交
        }
        return retValue;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getSectionRight() {
        return sectionRight;
    }

    public void setSectionRight(String sectionRight) {
        this.sectionRight = sectionRight;
    }

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
}
