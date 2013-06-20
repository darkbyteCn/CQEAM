package com.sino.ams.newasset.service;

import com.sino.ams.appbase.dto.AMSFlowDTO;
import com.sino.ams.appbase.service.AMSBaseService;
import com.sino.ams.newasset.dao.AmsAssetsReservedDAO;
import com.sino.ams.newasset.dao.AmsItemInfoHistoryDAO;
import com.sino.ams.newasset.dto.AmsItemInfoHistoryDTO;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.sinoflow.bean.FlowCommonUtil;
import com.sino.sinoflow.flowinterface.AppFlowBaseDTO;

import java.sql.Connection;

/**
 * @系统名称: 资产业务基础类
 * @功能描述: 此基类现包含了资产保留表操作，资产变更历史操作
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本： 1.0
 * @开发作者: sj
 * @创建时间: Sep 3, 2011
 */
public abstract class AssetsBaseService extends AMSBaseService {
    protected AMSFlowDTO flowDTO = null;

    public AssetsBaseService(BaseUserDTO user, AMSFlowDTO dto, Connection conn) {
        super(user, dto, conn);
        setDTOParameter(dto);
    }

    public void setDTOParameter(DTO dtoParameter){
        super.setDTOParameter(dtoParameter);
        if(dtoParameter instanceof AMSFlowDTO){
            this.flowDTO = (AMSFlowDTO)dtoParameter;
        }
    }


    /**
     * 暂存
     *
     * @return
     */
    public abstract boolean doSave();

    /**
     * 流程提交
     *
     * @return
     */
    public abstract boolean doSubmit();

    /**
     * 查询明细
     *
     * @throws QueryException
     */
    public abstract void prodData() throws QueryException;

    /**
     * 功能：保存行 --
     *
     * @throws DataHandleException
     */
    public void createReserved(String transId, String barcode) throws DataHandleException {
        AmsAssetsReservedDAO reservedDAO = new AmsAssetsReservedDAO(userAccount, null, conn);
        reservedDAO.createReserved(transId, barcode);
    }

    /**
     * 功能：
     *
     * @throws DataHandleException
     */
    public void deleteReserved(String transId) throws DataHandleException {
        AmsAssetsReservedDAO reservedDAO = new AmsAssetsReservedDAO(userAccount, null, conn);
        reservedDAO.deleteReserved(transId);
    }


    /**
     * 变更历史记录
     *
     * @param dto
     * @throws DataHandleException
     */
    public void saveItemInfoHistory(AmsItemInfoHistoryDTO dto) throws DataHandleException {
        AmsItemInfoHistoryDAO amsItemInfoHistoryDAO = new AmsItemInfoHistoryDAO(userAccount, dto, conn);
        amsItemInfoHistoryDAO.recordHistory();
    }

    /**
     * 流程提交
     *
     * @return
     */
    public boolean cancelProcedure() {
        FlowCommonUtil flowCommonUtil = new FlowCommonUtil(flowDTO, conn); // 流程工具类
        return flowCommonUtil.processDel();
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
     * 功能：准备流程数据,由应用实现
     */
    protected abstract void prepareProcedureData();
}
