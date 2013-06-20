package com.sino.sinoflow.bean.impl;

import java.sql.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.config.SinoConfig;
import com.sino.hn.todo.job.JobControl;
import com.sino.sinoflow.bean.FlowCommon;
import com.sino.sinoflow.flowinterface.AppFlowBaseDTO;
import com.sino.sinoflow.todo.constant.HNOAConstant;
import com.sino.sinoflow.todo.dao.OaTodoDAO;
import com.sino.sinoflow.todo.dto.OaTodoDTO;
import com.sino.sinoflow.utilities.CaseRoutine;
import com.sino.sinoflow.utilities.FlowUtil;

/**
 * 
 * @系统名称: 
 * @功能描述: 河南定制化功能
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Dec 15, 2011
 */
public class HNFlowCommon implements FlowCommon{
	private AppFlowBaseDTO bForm = null;
    private Connection conn = null;
    private OaTodoDAO oaTodoDAO = null;

    public HNFlowCommon(AppFlowBaseDTO bForm, Connection conn) {
        this.bForm = bForm;
        this.conn = conn;
    }


    /**
     * 功能：处理流程信息
     *
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
            operateResult = insertOaTodoDele();
            if (operateResult) {
                operateResult = cr.caseComplete(appFieldValue, getPrimaryKey(), keyword, subject, conn);
            }
        } else {
            operateResult = cr.caseSave(appFieldValue, getPrimaryKey(), bForm.getOrderNo(), bForm.getOrderName(),
                    conn);
        }


        if (operateResult) {
            operateResult = insertOaTodo();

            //Job控制
            activeTodoJob(operateResult);
            activeTodoDeleJob(operateResult);
        }

        return operateResult;
    }

    /**
     * 激活待办JOB SJ add
     *
     * @param operateResult
     */
    public void activeTodoJob(boolean operateResult) {
        if (isOpenOaTodo() && operateResult) {
            JobControl.setTodoStart(true);
        }
    }

    /**
     * 激活已办JOB SJ add
     *
     * @param operateResult
     */
    public void activeTodoDeleJob(boolean operateResult) {
        if (isOpenOaTodo() && operateResult) {
            JobControl.setTodoDeleteStart(true);
        }
    }


    /**
     * 撤销整个流程
     *
     * @return
     */
    public boolean processDel()  {
        boolean operateResult = false;
        try {
            operateResult = insertOaTodoDele();
            if (operateResult) {
                if (!StrUtil.isEmpty(bForm.getSfOpinion())) {
                    operateResult = FlowUtil.removeCase(bForm.getSf_caseID(), bForm.getSfOpinion(), conn);
                } else if (!StrUtil.isEmpty(bForm.getSfAppName())) {
                    operateResult = FlowUtil.removeCase(bForm.getSfAppName(), StrUtil.nullToString(getPrimaryKey()), "撤销申请单据", conn);
                } else {
                    operateResult = FlowUtil.removeCase(bForm.getSf_caseID(), conn);
                }
                if(operateResult){
                    activeTodoDeleJob(operateResult);
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return operateResult;
    }

    /**
     * 取消流程
     *
     * @return
     */
    public boolean processCancel() {
    	return processDel();
    }

    /**
     * 退回时调用的方法
     *
     * @return boolean
     */
    public boolean reject() {
        boolean operateResult = false;
        CaseRoutine cr = new CaseRoutine();

        operateResult = insertOaTodoDele();

        if (operateResult) {
            operateResult = cr.caseBack(bForm.getSf_appFieldValue(), conn);
        }

        if (operateResult) {
            operateResult = insertOaTodo();

            activeTodoJob(operateResult);
            activeTodoDeleJob(operateResult);
        }

        return operateResult;
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
    
    public String getAppId() throws DataHandleException, JSONException {
        String appId = "";
        String fieldValue = "";

        if (bForm != null) {
            fieldValue = bForm.getSf_appFieldValue();
        } else {
            throw new DataHandleException("调用流程操作类时,未设置DTO对象");
        }

        if (!fieldValue.equals("")) {
            JSONObject jObj = new JSONObject(fieldValue);
//            String nextTaskDate = jObj.getString("sf_nextTaskData");
//            JSONObject nextTaskObj = new JSONObject(nextTaskDate);
            appId = jObj.getString("sf_appID");
        }
        return appId;
    }


    /**
     * 插入待办
     *
     * @return
     */
    private boolean insertOaTodo() {
        if (isOpenOaTodo()) {
            boolean operateResult = false;
            initOaTodoDAO();
            operateResult = oaTodoDAO.insertOaTODOFromEAM(getPrimaryKey());
            return operateResult;
        } else {
            return true;
        }
    }


    /**
     * 插入已办
     *
     * @return
     */
    private boolean insertOaTodoDele() {
        if (isOpenOaTodo()) {
            boolean operateResult = false;
            initOaTodoDAO();
            operateResult = oaTodoDAO.insertOaTodoDeleFromEAM(getPrimaryKey());
            return operateResult;
        } else {
            return true;
        }
    }

    /**
     * 查找主键
     *
     * @return
     * @throws Exception 
     */
    private String getPrimaryKey() {
        String primaryKey = bForm.getPrimaryKey();
        if (StrUtil.isEmpty(primaryKey)) {
            primaryKey = bForm.getApp_dataID();
        }
        if( StrUtil.isEmpty(primaryKey) ){
        	try {
				primaryKey = getAppId();
			} catch (DataHandleException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
        }
        return primaryKey;
    }

    /**
     * 是否插入OA_TODO表
     *
     * @return
     */
    private boolean isOpenOaTodo() {
    	return true;
//        return SinoConfig.getProvinceCode().equals("25");
    }

    /**
     * 初始化TodoDAO
     */
    private void initOaTodoDAO() {
        if (null == oaTodoDAO) {
            OaTodoDTO dto = new OaTodoDTO();
            dto.setPri(HNOAConstant.OA_TODO_PRI_DEFAULT);
            dto.setDocType(HNOAConstant.OA_TODO_DOC_TYPE);
            dto.setSourceId(HNOAConstant.OA_TODO_SOURCE_ID);
            dto.setSysId(HNOAConstant.OA_TODO_SYSID);
            oaTodoDAO = new OaTodoDAO(null, dto, conn);
        }
    }
}
