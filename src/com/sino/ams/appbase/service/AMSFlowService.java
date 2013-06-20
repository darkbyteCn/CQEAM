package com.sino.ams.appbase.service;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.utilities.CaseRoutine;

import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: mshtang
 * Date: 2011-7-6
 * Time: 15:31:56
 * To change this template use File | Settings | File Templates.
 */
public abstract class AMSFlowService extends AMSBaseService {
    private AMSFlowDTO flowDTO = null;

    public AMSFlowService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.flowDTO = (AMSFlowDTO) dtoParameter;
    }


    public void setDTOParameter(DTO dtoParameter) {
        super.setDTOParameter(dtoParameter);
        this.flowDTO = (AMSFlowDTO) dtoParameter;
    }

    /**
     * 功能：处理流程信息
     *
     * @param isSubmit boolean
     * @return boolean
     */
    protected boolean processProcedure(boolean isSubmit) {
        boolean operateResult = false;
        CaseRoutine cr = new CaseRoutine();
        String appFieldValue = flowDTO.getSf_appFieldValue();
        String primaryKey = flowDTO.getPrimaryKey();
        String orderNo = flowDTO.getOrderNo();
        String orderName = flowDTO.getOrderName();
        if (isSubmit) {
            operateResult = cr.caseComplete(appFieldValue, primaryKey, orderNo, orderName, conn);
        } else {
            operateResult = cr.caseSave(appFieldValue, primaryKey, orderNo, orderName, conn);
        }
        return operateResult;
    }
}
