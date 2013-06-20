package com.sino.ams.appbase.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.DTO;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.bean.FlowCommonUtil;
import com.sino.sinoflow.flowinterface.AppFlowBaseDTO;
import com.sino.sinoflow.utilities.CaseRoutine;
import com.sino.sinoflow.utilities.FlowUtil;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public abstract class AMSProcedureBaseDAO extends AMSBaseDAO {//该类的存在是为了免去其他DAO类处处定义用户对象

    public AMSProcedureBaseDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }


    /**
     * 功能：处理流程单据
     *
     * @param isSubmit true表示提交，false表示暂存
     * @return true表示成功，false表示失败
     */
    protected boolean processProcedure(boolean isSubmit) {
        boolean operateResult = false;
        if (dtoParameter != null && dtoParameter instanceof AppFlowBaseDTO) {
            prepareProcedureData();
            AppFlowBaseDTO frm = (AppFlowBaseDTO) dtoParameter;
            FlowCommonUtil fcu = new FlowCommonUtil(frm, conn);
            operateResult = fcu.processProcedure(isSubmit);
        }
        return operateResult;
    }

    /**
     * <p>注意：本方法请慎重使用，除非你确认你的流程提交采用的是SUBMIT_ACTION</p>
     * <p>注意：本方法之所以是protected，是确保你在子类中调用，而不能在servlet中调用</p>
     * 功能：处理流程单据
     *
     * @return true表示成功，false表示失败
     */
    protected boolean processProcedure() {
        boolean operateResult = false;
        if (dtoParameter != null && dtoParameter instanceof AppFlowBaseDTO) {
            prepareProcedureData();
            AppFlowBaseDTO frm = (AppFlowBaseDTO) dtoParameter;
            FlowCommonUtil fcu = new FlowCommonUtil(frm, conn);
            String act = frm.getAct();
            boolean isSubmit = act.equals(WebActionConstant.SUBMIT_ACTION);
            operateResult = fcu.processProcedure(isSubmit);
        }
        return operateResult;
    }

    /**
     * 功能：退回流程单据
     *
     * @return true表示成功，false表示失败
     */
    public boolean rejectProcedure() {
        if (dtoParameter != null && dtoParameter instanceof AppFlowBaseDTO) {
            AppFlowBaseDTO frm = (AppFlowBaseDTO) dtoParameter;
            FlowCommonUtil fcu = new FlowCommonUtil(frm, conn);
            return fcu.reject();
        }
        return false;
    }

    /**
     * 功能：撤销流程单据
     *
     * @return true表示成功，false表示失败
     */
    public boolean cancelProcedure() {
        boolean operateResult = false;
        try {
            if (dtoParameter != null && dtoParameter instanceof AMSFlowDTO) {
                AMSFlowDTO frm = (AMSFlowDTO) dtoParameter;
                FlowCommonUtil fcu = new FlowCommonUtil(frm, conn);
                operateResult = fcu.processDel();
//                operateResult = FlowUtil.removeCase(frm.getSf_caseID(), conn);
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return operateResult;
    }


    /**
     * 功能：准备流程数据,由应用实现
     */
    protected void prepareProcedureData(){

    }

}
