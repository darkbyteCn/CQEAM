package com.sino.ams.newasset.dao;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.dto.*;
import com.sino.ams.newasset.model.AmsItemAllocationApproveModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-7-8
 * Time: 上午6:48
 * To change this template use File | Settings | File Templates.
 */
public class AmsItemAllocationApproveDAO extends AMSProcedureBaseDAO {

    public AmsItemAllocationApproveDAO(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        super.sqlProducer = new AmsItemAllocationApproveModel((SfUserDTO) userAccount, dto);
    }

    public boolean approveOrder(DTOSet orderLines) {
        boolean operateResult = false;
        boolean autoCommit = true;
        boolean needMsg = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
            boolean needConfirm = dto.getSf_task_attribute1().equals("CONFIRM");
            AmsItemAllocationApproveModel modelProducer = (AmsItemAllocationApproveModel) sqlProducer;
            if (dto.isFlowOver()) {
                dto.setTransStatus(AssetsDictConstant.APPROVED);
                setDTOParameter(dto);
                SQLModel sqlModel = modelProducer.getOrderCompleteModel();
                DBOperator.updateRecord(sqlModel, conn);

                sqlModel = modelProducer.getLineStatusUpdateModel(); //更新资产单据行数据状态为已审批
                DBOperator.updateRecord(sqlModel, conn);
            } else {
                dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
                setDTOParameter(dto);
                SQLModel sqlModel = modelProducer.getOrderApproveModel();
                DBOperator.updateRecord(sqlModel, conn);
            }
            if (dto.getSf_task_attribute1().equals("DEPT")) {
                AmsItemAllocationLineDAO lineDAO = new AmsItemAllocationLineDAO(userAccount, null, conn);
                lineDAO.updateTransLine(orderLines);
            }
            if (needConfirm) {
                confirmAssets();
            }
            operateResult = processProcedure(true);
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (SQLException ex) {
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
                    processMessage(operateResult);
                }
            } catch (SQLException ex1) {
                Logger.logError(ex1);
            }
        }
        return operateResult;
    }

    private void confirmAssets() throws DataHandleException {
        AssetsOrderConfirmDAO orderConfirmDAO = new AssetsOrderConfirmDAO(userAccount, dtoParameter, conn);
        orderConfirmDAO.setAssetsConfirm(false);
        orderConfirmDAO.confirmOrderAssets();
    }

    protected void prepareProcedureData() {
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        dto.setPrimaryKey(dto.getTransId());
        dto.setOrderNo(dto.getTransNo());
        if (dto.getTransferType().equals(AssetsDictConstant.TRANS_INN_DEPT)) {
            dto.setOrderName(AssetsDictConstant.PROCEDURE_ITEM_TRANS_INN_DEPT);
        } else if (dto.getTransferType().equals(AssetsDictConstant.TRANS_BTW_DEPT)) {
            dto.setOrderName(AssetsDictConstant.PROCEDURE_ITEM_TRANS_BTW_DEPT);
        } else {
            dto.setOrderName(AssetsDictConstant.PROCEDURE_ITEM_TRANS_BTW_COMP);
        }
    }

    /**
     * 功能：构造单据审批消息提示
     *
     * @param operateResult boolean
     */
    private void processMessage(boolean operateResult) {
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        if (operateResult) {
            prodMessage(AssetsMessageKeys.PASS_ORDER_SUCCESS);
        } else {
            prodMessage(AssetsMessageKeys.PASS_ORDER_FAILURE);
        }
        String orderType = dto.getTransTypeValue();
        if (orderType.indexOf("单") > -1) {
            orderType = orderType.substring(0, orderType.length() - 1);
        }
        message.addParameterValue(orderType);
        message.addParameterValue(dto.getTransNo());
        message.setIsError(!operateResult);
    }

    /**
     * 功能：根据主键获取资产调拨单据，并补充当前审批角色
     *
     * @return Object
     * @throws QueryException
     */
    public Object getDataByPrimaryKey() throws QueryException {
        Object primaryKeyData = super.getDataByPrimaryKey();
        AmsAssetsTransHeaderDTO data = (AmsAssetsTransHeaderDTO) primaryKeyData;
        data.setServletConfig(servletConfig);
        primaryKeyData = data;
        return primaryKeyData;
    }

    /**
     * 功能：获取当前的审批角色名称
     *
     * @return String
     * @throws QueryException
     */
    public String getCurrRoleName() throws QueryException {
        String currRoleName = "";
        try {
            AmsItemAllocationApproveModel modelProducer = (AmsItemAllocationApproveModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getCurrApproveRoleModel();
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                currRoleName = simp.getFirstRow().getStrValue("ROLENAME");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return currRoleName;
    }

    /**
     * 功能：获取附件张数
     *
     * @return String
     * @throws QueryException
     */
    public String getAccessSheet() throws QueryException {
        String accessSheet = "";
        try {
            AmsItemAllocationApproveModel modelProducer = (AmsItemAllocationApproveModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getAccessSheet();
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                accessSheet = simp.getFirstRow().getStrValue("ACCESS_SHEET");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return accessSheet;
    }

    /**
     * 功能：获取并更新不能报废的资产，并更新行表REMARK为“不允许报废”；
     */
    private void updateTransLineRemark(String[] barcodes) throws DataHandleException {
        AmsItemAllocationApproveModel modelProducer = (AmsItemAllocationApproveModel) sqlProducer;
        String aggBarcodes = initBarcodes(barcodes);
        SQLModel sqlModel = modelProducer.updateTransLineRemark();
        DBOperator.updateRecord(sqlModel, conn);
    }

    //初始化获取的BARCODE
    private String initBarcodes(String[] barcodes) {
        String aggBarcodes = "(";
        for (int i = 0; i < barcodes.length; i++) {
            String barcode = barcodes[i];
            aggBarcodes += "'" + barcode + "', ";
        }
        aggBarcodes += "'aa')";
        return aggBarcodes;
    }

    /**
     * 功能：特殊处理，先清除选中资产REMARK
     */
    private void deleteTransLineRemark(String[] barcodes) throws DataHandleException {
        AmsItemAllocationApproveModel modelProducer = (AmsItemAllocationApproveModel) sqlProducer;
        for (int i = 0; i < barcodes.length; i++) {
            String barcode = barcodes[i];
            SQLModel sqlModel = modelProducer.deleteTransLineRemark(barcode);
            DBOperator.updateRecord(sqlModel, conn);
        }
    }

    public boolean isFinanceGroup() throws QueryException {
        AmsItemAllocationApproveModel modelProducer = (AmsItemAllocationApproveModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getIsFinanceGroupModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

    //判断是否实物部门
    public boolean isSpecialGroup(int fromGroup) throws QueryException {
        AmsItemAllocationApproveModel modelProducer = (AmsItemAllocationApproveModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getIsSpecialGroupModel(fromGroup);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

    public void rejectOrder() {
        boolean operateResult = rejectProcedure();
        if (operateResult) {
            prodMessage(AssetsMessageKeys.REJECT_ORDER_SUCCESS);
        } else {
            prodMessage(AssetsMessageKeys.REJECT_ORDER_FAILURE);
            message.setIsError(true);
        }
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        message.addParameterValue(dto.getTransTypeValue());
        message.addParameterValue(dto.getTransNo());
    }
}
