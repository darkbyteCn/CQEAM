package com.sino.ams.newasset.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.model.CheckApproveModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsAssetsTransHeaderDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsTransHeaderDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class CheckApproveDAO extends AMSProcedureBaseDAO {

    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsCheckBatchDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public CheckApproveDAO(SfUserDTO userAccount, AmsAssetsCheckBatchDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
        super.sqlProducer = new CheckApproveModel((SfUserDTO) userAccount, dto);
    }

    /**
     * 功能：审批单据，含调拨单，报废单，处置单
     *
     * @return boolean
     */
    public boolean approveOrder() {
        boolean operateResult = false;
        boolean autoCommit = true;
        boolean needMsg = true;
        String flowCode = "";
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
            flowCode = dto.getFlowCode();
            if (dto.isFlowOver()) {
                dto.setBatchStatus(AssetsDictConstant.APPROVED);
            } else {
                dto.setBatchStatus(AssetsDictConstant.IN_PROCESS);
            }
            setDTOParameter(dto);
            CheckApproveModel modelProducer = (CheckApproveModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getBatchApproveModel(); //审批工单批
            DBOperator.updateRecord(sqlModel, conn);

            sqlModel = modelProducer.getHeadersApproveModel(); //审批工单
            DBOperator.updateRecord(sqlModel, conn);
            operateResult = processProcedure(true);
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                } else {
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
                if (needMsg) {
                    processMessage(operateResult, flowCode);
                }
            } catch (SQLException ex1) {
                Logger.logError(ex1);
            }
        }
        return operateResult;
    }

    public boolean newApproveOrder() {
        boolean operateResult = false;
        boolean autoCommit = true;
        boolean needMsg = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
            dto.setBatchStatus(AssetsDictConstant.IN_PROCESS);
            if (dto.isFlowOver()) {
                dto.setBatchStatus(AssetsDictConstant.CHK_STATUS_DISTRUIBUTED);
            }
            setDTOParameter(dto);
            CheckApproveModel modelProducer = (CheckApproveModel) sqlProducer;

            SQLModel sqlModel = modelProducer.getBatchApproveModel(); //审批工单批
            DBOperator.updateRecord(sqlModel, conn);

            sqlModel = modelProducer.getHeadersApproveModel(); //审批工单
            DBOperator.updateRecord(sqlModel, conn);
            operateResult = processProcedure(true);
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                } else {
                    conn.commit();
                }
                conn.setAutoCommit(autoCommit);
                if (needMsg) {
                    processMessage(operateResult, FlowConstant.FLOW_CODE_NEXT);
                }
            } catch (SQLException ex1) {
                Logger.logError(ex1);
            }
        }
        return operateResult;
    }

    public void rejectOrder() {
        boolean operateResult = rejectProcedure();
        if (operateResult) {
            prodMessage(AssetsMessageKeys.REJECT_ORDER_SUCCESS);
        } else {
            prodMessage(AssetsMessageKeys.REJECT_ORDER_FAILURE);
            message.setIsError(true);
        }
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
        message.addParameterValue("盘点工单任务批");
        message.addParameterValue(dto.getBatchNo());
    }


    /**
     * 功能：构造单据审批消息提示
     *
     * @param operateResult boolean
     * @param flowCode      String
     */
    private void processMessage(boolean operateResult, String flowCode) {
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
        if (flowCode.equals(FlowConstant.FLOW_CODE_NEXT)) {
            if (operateResult) {
                prodMessage(AssetsMessageKeys.PASS_ORDER_SUCCESS);
            } else {
                prodMessage(AssetsMessageKeys.PASS_ORDER_FAILURE);
            }
        } else {
            if (operateResult) {
                prodMessage(AssetsMessageKeys.REJECT_ORDER_SUCCESS);
            } else {
                prodMessage(AssetsMessageKeys.REJECT_ORDER_FAILURE);
            }
        }
        message.addParameterValue("盘点");
        message.addParameterValue(dto.getBatchNo());
        message.setIsError(!operateResult);
    }

    /**
     * 功能：设置流程数据。覆盖父类方法。
      */
    protected void prepareProcedureData(){
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
        dto.setPrimaryKey(dto.getBatchId());
        dto.setOrderNo(dto.getBatchNo());
        dto.setOrderName(dto.getOrderTypeName());
    }
}
