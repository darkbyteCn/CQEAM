package com.sino.sinoflow.bean.impl;

import java.sql.Connection;

import org.json.JSONObject;

import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.sinoflow.bean.FlowCommon;
import com.sino.sinoflow.flowinterface.AppFlowBaseDTO;
import com.sino.sinoflow.utilities.CaseRoutine;
import com.sino.sinoflow.utilities.FlowUtil;

/**
 * 
 * @系统名称: 
 * @功能描述: 默认流程提交等功能
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Dec 15, 2011
 */
public class DefaultFlowCommon implements FlowCommon{
	private AppFlowBaseDTO bForm = null;
    private Connection conn = null;

    public DefaultFlowCommon(AppFlowBaseDTO bForm, Connection conn) { 
        this.bForm = bForm;
        this.conn = conn;
    }
     

    /**
     * 功能：处理流程信息
     * @param isSubmit boolean
     * @return boolean
     */
    public boolean processProcedure(boolean isSubmit) {
        boolean operateResult = false;
        CaseRoutine cr = new CaseRoutine();
        String appFieldValue = bForm.getSf_appFieldValue();
        String[] valueList = appFieldValue.split("&;&");
        String keyword = bForm.getOrderNo();
        if (keyword.equals("")) {
            keyword = valueList[3];
        }
        String subject = bForm.getOrderName();
        if (subject.equals("")) {
            subject = valueList[4];
        }

        if (isSubmit) {
            operateResult = cr.caseComplete(appFieldValue, getPrimaryKey(), keyword, subject, conn);
        } else {
            operateResult = cr.caseSave(appFieldValue, getPrimaryKey(), bForm.getOrderNo(), bForm.getOrderName(),
                    conn);
        } 

        return operateResult;
    }

    /**
     * 撤销整个流程
     * @return
     */
    public boolean processDel()  {
        boolean operateResult = false;
        try {
            if (!StrUtil.isEmpty(bForm.getSfOpinion())) {
                operateResult = FlowUtil.removeCase(bForm.getSf_caseID(), bForm.getSfOpinion(), conn);
            } else if (!StrUtil.isEmpty(bForm.getSfAppName())) {
                operateResult = FlowUtil.removeCase(bForm.getSfAppName(), StrUtil.nullToString(getPrimaryKey()), "撤销申请单据", conn);
            } else {
                operateResult = FlowUtil.removeCase(bForm.getSf_caseID(), conn);
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return operateResult;
    }

    /**
     * 取消流程信息， 比如放锁
     * @return
     */
    public boolean processCancel() {
    	return processDel();
//        CaseRoutine cr = new CaseRoutine();
//        String appFieldValue = bForm.getSf_appFieldValue();
//        return cr.caseCancel(appFieldValue, conn);
    }

    /**
     * 退回时调用的方法
     *
     * @return boolean
     */
    public boolean reject() {
        CaseRoutine cr = new CaseRoutine();
        return cr.caseBack(bForm.getSf_appFieldValue(), conn);
    }


    public String getFlowCode() throws Exception {
        String flowCode = "";
        String fieldValue = "";

        if (bForm != null) {
            fieldValue = bForm.getSf_appFieldValue();
        } else {
            throw new DataHandleException("调用流程操作类时,未设置DTO对象");
        }

        if (!fieldValue.equals("")) {
            JSONObject jObj = new JSONObject(fieldValue);
            String nextTaskDate = jObj.getString("sf_nextTaskData");
            JSONObject nextTaskObj = new JSONObject(nextTaskDate);
            flowCode = nextTaskObj.getString("flowCode");
        }
        return flowCode;
    }
 
 

    /**
     * 查找主键
     *
     * @return
     */
    private String getPrimaryKey() {
        String primaryKey = bForm.getPrimaryKey();
        if (StrUtil.isEmpty(primaryKey)) {
            primaryKey = bForm.getApp_dataID();
        }
        return primaryKey;
    }


	public AppFlowBaseDTO getBForm() {
		return bForm;
	}


	public void setBForm(AppFlowBaseDTO form) {
		bForm = form;
	}


	public Connection getConn() {
		return conn;
	}


	public void setConn(Connection conn) {
		this.conn = conn;
	}
 
}
