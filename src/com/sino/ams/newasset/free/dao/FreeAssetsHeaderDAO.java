package com.sino.ams.newasset.free.dao;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.sino.base.constant.WorldConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.Row;
import com.sino.base.db.datatrans.*;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsReservedDAO;
import com.sino.ams.newasset.dao.AmsAssetsTransLineDAO;
import com.sino.ams.newasset.dao.AmsItemInfoHistoryDAO;
import com.sino.ams.newasset.dto.AmsAssetsReservedDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.dto.AmsItemInfoHistoryDTO;
import com.sino.ams.newasset.free.model.FreeAssetsHeaderModel;
import com.sino.ams.newasset.model.AmsAssetsTransHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsAssetsTransHeaderDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsTransHeaderDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class FreeAssetsHeaderDAO extends AMSProcedureBaseDAO {

    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public FreeAssetsHeaderDAO(SfUserDTO userAccount, AmsAssetsTransHeaderDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        sqlProducer = new FreeAssetsHeaderModel((SfUserDTO) userAccount, dtoPara);
    }


    /**
     * 功能：保存资产单据：含调拨，报废，处置
     * @param lineSet DTOSet 资产数据
     * @return boolean
     */
    public boolean saveOrder(DTOSet lineSet) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        String transType = dtoPara.getTransType();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            saveOrderHeader();
            saveOrderLines(lineSet);
            operateResult = processProcedure(false);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_FAILURE);
                    }
                } else {
                    conn.commit();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_SUCCESS);
                    }
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("保存");
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    public boolean newSaveOrder(DTOSet lineSet) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        String transType = dtoPara.getTransType();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            saveNewOrderHeader();
            saveOrderLines(lineSet);
            operateResult = processProcedure(false);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_FAILURE);
                    }
                } else {
                    conn.commit();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_SUCCESS);
                    }
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("保存");
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    /**
     * 功能：提交资产单据：含调拨，报废，处置
     * @param lineSet DTOSet 资产数据
     * @return boolean
     */
    public boolean submitOrder(DTOSet lineSet) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        String transType = dtoPara.getTransType();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            saveOrderHeader();
            saveOrderLines(lineSet);
            if (dtoPara.getSf_end().equals("1")) {
                freeAssets();
                deleteReserveAssets();
                logItemChgHistory(lineSet);
            }
            operateResult = processProcedure(true);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_FAILURE);
                    }
                } else {
                    conn.commit();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_SUCCESS);
                    }
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("提交");
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    public boolean newSubmitOrder(DTOSet lineSet) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        String transType = dtoPara.getTransType();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            saveNewOrderHeader();
            saveOrderLines(lineSet);
            operateResult = processProcedure(true);
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_FAILURE);
                    }
                } else {
                    conn.commit();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_SUCCESS);
                    }
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("提交");
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    public boolean doDelete(DTOSet lineSet) {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO) dtoParameter;
        String transType = dtoPara.getTransType();
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            deleteLines();
            deleteReserveAssets();
            saveOrderLines(lineSet);
            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
        } catch (DataHandleException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_FAILURE);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_FAILURE);
                    }
                } else {
                    conn.commit();
                    if (transType.equals(AssetsDictConstant.ASS_RED)) {
                        prodMessage(AssetsMessageKeys.ASSETS_TRANSFER_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_DIS)) {
                        prodMessage(AssetsMessageKeys.ASSETS_DISCARD_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_CLR)) {
                        prodMessage(AssetsMessageKeys.ASSETS_CLEAR_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_FREE)) {
                        prodMessage(AssetsMessageKeys.ASSETS_FREE_SUCCESS);
                    } else if (transType.equals(AssetsDictConstant.ASS_SUB)) {
                        prodMessage(AssetsMessageKeys.ASSETS_SUB_SUCCESS);
                    }
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue("删除");
                message.setIsError(!operateResult);
            } catch (SQLException ex) {
                Logger.logError(ex);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    /**
     * 功能：保存单据头信息
     * @return String
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void saveOrderHeader() throws DataHandleException {
        try {
            AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
            String transNo = headerDTO.getTransNo();
            headerDTO.setFromPerson(userAccount.getEmployeeNumber());
            if (transNo.equals(AssetsWebAttributes.ORDER_AUTO_PROD)) {
                String transId = headerDTO.getTransId();
                if (StrUtil.isEmpty(transId)) {
                    SeqProducer seq = new SeqProducer(conn);
                    transId = seq.getGUID();
                    headerDTO.setTransId(transId);
                }
                String companyCode = userAccount.getCompanyCode(); //还是采用该方法，以下考虑周子君认为没必要


                String transType = headerDTO.getTransType();
                OrderNumGenerator numberProducer = new OrderNumGenerator(conn, companyCode, transType);
                headerDTO.setTransNo(numberProducer.getOrderNum());
                setDTOParameter(headerDTO);
                createData();
            } else {
                if (headerDTO.getSf_end().equals("1")) {
                    headerDTO.setTransStatus(AssetsDictConstant.APPROVED);
                }
                updateData();
                deleteLines();
                deleteReserveAssets();
            }
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
    }

    protected void prepareProcedureData(){
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        headerDTO.setPrimaryKey(headerDTO.getTransId());
        headerDTO.setOrderNo(headerDTO.getTransNo());
        headerDTO.setOrderName(AssetsDictConstant.PROCEDURE_NAME_FREE);
    }

    private String saveNewOrderHeader() throws DataHandleException {
        String transId = "";
        try {
            AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
            transId = headerDTO.getTransId();
            String transNo = headerDTO.getTransNo();
            headerDTO.setFromPerson(userAccount.getEmployeeNumber());
            String act = headerDTO.getAct();
            if (transNo.equals(AssetsWebAttributes.ORDER_AUTO_PROD)) {
                if (StrUtil.isEmpty(transId)) {
                    SeqProducer seq = new SeqProducer(conn);
                    transId = seq.getGUID();
                    headerDTO.setTransId(transId);
                }
                String companyCode = userAccount.getCompanyCode(); //还是采用该方法，以下考虑周子君认为没必要
                String transType = headerDTO.getTransType();
                OrderNumGenerator numberProducer = new OrderNumGenerator(conn, companyCode, transType);
                headerDTO.setTransNo(numberProducer.getOrderNum());
                setDTOParameter(headerDTO);
                createData();
            } else {
                updateData();
                deleteLines();
                deleteReserveAssets();
            }
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return transId;
    }

    public boolean rejectOrder() {
        boolean operateResult = rejectProcedure();
        if(operateResult){
            prodMessage(AssetsMessageKeys.REJECT_ORDER_SUCCESS);
        } else {
            prodMessage(AssetsMessageKeys.REJECT_ORDER_FAILURE);
            message.setIsError(true);
        }
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        message.addParameterValue(dto.getTransTypeValue());
        message.addParameterValue(dto.getTransNo());
        return operateResult;
    }
    /**
     * 功能：保存单据行信息
     * @param lineSet DTOSet
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void saveOrderLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsAssetsTransHeaderDTO orderDTO = (AmsAssetsTransHeaderDTO)dtoParameter;
            AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO( userAccount, null, conn);
            String transferType = orderDTO.getTransferType();
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsAssetsTransLineDTO lineData = (AmsAssetsTransLineDTO)lineSet.getDTO(i);
                if (lineData.getBarcode().equals("")) {
                    continue;
                }
                lineData.setTransId(orderDTO.getTransId());
                lineData.setLineStatus(orderDTO.getTransStatus());
                if (lineData.getOldResponsibilityDept().equals("")) {
                    lineData.setOldResponsibilityDept(StrUtil.nullToString(orderDTO.getFromDept()));
                }
                if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { //部门内调拨
                    lineData.setResponsibilityDept(StrUtil.nullToString(orderDTO.getFromDept()));
                }
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
                createReserveAssets(lineData.getBarcode()); //保留资产
            }
        }
    }

    /**
     * 功能：删除单据的行信息
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void deleteLines() throws DataHandleException {
        AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        lineDTO.setTransId(headerDTO.getTransId());
        AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, lineDTO, conn);
        lineDAO.DeleteByForeignKey("transId");
    }

    /**
     * 功能：删除本单据保留的资产
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void deleteReserveAssets() throws DataHandleException {
        AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        reserveDTO.setTransId(headerDTO.getTransId());
        AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount,reserveDTO, conn);
        reserveDAO.DeleteByForeignKey("transId");
    }

    /**
     * 更改减值资产状态
     * @throws DataHandleException
     */
    private void freeAssets() throws DataHandleException {
        FreeAssetsHeaderModel headerModel = (FreeAssetsHeaderModel) sqlProducer;
        AmsAssetsTransHeaderDTO batchDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        SQLModel sqlModel = headerModel.getFreeAssetsModel(batchDTO.getTransId());
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：保留资产
     * @param batrcode String
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void createReserveAssets(String batrcode) throws DataHandleException {
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
        reserveDTO.setTransId(headerDTO.getTransId());
        reserveDTO.setBarcode(batrcode);
        reserveDTO.setCurrCalendar("reservedDate");
        AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount, reserveDTO, conn);
        reserveDAO.createData();
    }


    /**
     * 功能：撤销暂存的单据
     *
     * @return boolean
     */
    public boolean cancelOrders() {
        boolean operateResult = false;
        boolean autoCommit = true;
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            cancelOrderHeader();
            cancelOrderLines();
            deleteReservedAssets();
            operateResult = cancelProcedure();
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                    prodMessage(AssetsMessageKeys.ORDER_CANCEL_SUCCESS);
                } else {
                    conn.rollback();
                    prodMessage(AssetsMessageKeys.ORDER_CANCEL_FAILURE);
                }
                conn.setAutoCommit(autoCommit);
                message.addParameterValue(headerDTO.getTransTypeValue());
                message.setIsError(!operateResult);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }
    

    /**
     * 功能：撤销单据头
     * @throws DataHandleException
     */
    private void cancelOrderHeader() throws DataHandleException{
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        FreeAssetsHeaderModel modelProducer = (FreeAssetsHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getOrderCancelModel(headerDTO.getTransId()); //撤销单据
        DBOperator.updateRecord(sqlModel, conn);
    }
    /**
     * 功能：撤销单据行
     * @throws DataHandleException
     */
    private void cancelOrderLines() throws DataHandleException{
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
        lineDTO.setTransId(headerDTO.getTransId());
        AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(userAccount, lineDTO, conn);
        lineDAO.cancelLinesByHeader();

    }

    /**
     * 功能：撤销保留资产
     * @throws DataHandleException
     */
    private void deleteReservedAssets() throws DataHandleException{
        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) dtoParameter;
        AmsAssetsReservedDTO reserveDTO = new AmsAssetsReservedDTO();
        reserveDTO.setTransId(headerDTO.getTransId()); //删除保留数据
        AmsAssetsReservedDAO reserveDAO = new AmsAssetsReservedDAO(userAccount, reserveDTO, conn);
        reserveDAO.DeleteByForeignKey("transId");
    }

    public File exportFile() throws DataTransException { //导出模板
        File file = null;
        DataTransfer transfer = null;
        AmsAssetsTransHeaderModel modelProducer = (AmsAssetsTransHeaderModel)
                sqlProducer;
        SQLModel sqlModel = modelProducer.getOrderModel();
        TransRule rule = new TransRule();
        rule.setDataSource(sqlModel);
        rule.setCalPattern(CalendarConstant.LINE_PATTERN);
        rule.setSourceConn(conn);

        AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)
                dtoParameter;
        String transferType = headerDTO.getTransferType();
        String fileName = "";
        if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) { //部门内调拨
            fileName = "部门内调拨.xls";
        } else if (transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)) { //部门内调拨
            fileName = "部门间调拨.xls";
        } else if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) { //地市间调拨
            fileName = "地市间调拨.xls";
        }

        String filePath = WorldConstant.USER_HOME;
        filePath += WorldConstant.FILE_SEPARATOR;
        filePath += fileName;
        rule.setTarFile(filePath);
        DataRange range = new DataRange();
        rule.setDataRange(range);

        Map fieldMap = new HashMap();
        fieldMap.put("MB1", "资产标签");
        fieldMap.put("MB2", "资产编号");
        fieldMap.put("MB3", "资产名称");
        fieldMap.put("MB4", "资产型号");
        fieldMap.put("MB5", "数量");
        if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) {
            fieldMap.put("MB6", "调出地点NO");
            fieldMap.put("MB7", "调出地点");
            fieldMap.put("MB8", "原责任人员工ID");
            fieldMap.put("MB9", "原责任人");
            fieldMap.put("MB10", "调入地点NO");
            fieldMap.put("MB11", "调入地点");
            fieldMap.put("MB12", "新责任人员工ID");
            fieldMap.put("MB13", "新责任人");
            fieldMap.put("MB14", "调动日期");
            fieldMap.put("MB15", "摘要");
        } else if (transferType.equals(AssetsDictConstant.TRANS_BTW_DEPT)) {
            fieldMap.put("MB6", "调出地点NO");
            fieldMap.put("MB7", "调出地点");
            fieldMap.put("MB8", "原责任人员工ID");
            fieldMap.put("MB9", "原责任人");
            fieldMap.put("MB10", "调入部门代码");
            fieldMap.put("MB11", "调入部门");
            fieldMap.put("MB12", "调入地点N0");
            fieldMap.put("MB13", "调入地点");
            fieldMap.put("MB14", "新责任人员工ID");
            fieldMap.put("MB15", "新责任人");
            fieldMap.put("MB16", "调动日期");
            fieldMap.put("MB17", "备注");
        } else if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) {
            fieldMap.put("MB6", "原值");
            fieldMap.put("MB7", "累计折旧");
            fieldMap.put("MB8", "残值");
            fieldMap.put("MB9", "启用日期");
            fieldMap.put("MB10", "调出部门代码");
            fieldMap.put("MB11", "调出部门");
            fieldMap.put("MB12", "调出地点NO");
            fieldMap.put("MB13", "调出地点");
            fieldMap.put("MB14", "原责任人员工ID");
            fieldMap.put("MB15", "原责任人");
            fieldMap.put("MB16", "原折旧账户");
            fieldMap.put("MB17", "原类别");
            fieldMap.put("MB18", "调入部门代码");
            fieldMap.put("MB19", "调入部门");
            fieldMap.put("MB20", "调入地点NO");
            fieldMap.put("MB21", "调入地点");
            fieldMap.put("MB22", "新责任人员工ID");
            fieldMap.put("MB23", "新责任人");
            fieldMap.put("MB24", "新折旧账户");
            fieldMap.put("MB25", "新类别");
            fieldMap.put("MB26", "调动日期");
            fieldMap.put("MB27", "备注");
        }

        rule.setFieldMap(fieldMap);
        CustomTransData custData = new CustomTransData();
//            custData.setReportTitle(fileName);
//            custData.setReportPerson(sfUser.getUsername());
        custData.setNeedReportDate(false);
        rule.setCustData(custData);

        TransferFactory factory = new TransferFactory();
        transfer = factory.getTransfer(rule);
        transfer.transData();
        file = (File) transfer.getTransResult();
        return file;
    }

    /**
     * 功能：查找用户所对应的PID，流程用
     */
    public boolean isGroupFlowId() throws QueryException {
        FreeAssetsHeaderModel modelProducer = (FreeAssetsHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getGroupPidModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

    //判断是否实物部门
    public boolean isSpecialGroup(int fromGroup) throws QueryException {
        FreeAssetsHeaderModel modelProducer = (FreeAssetsHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getIsSpecialGroupModel(fromGroup);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }


    //判断是否财务部
    public boolean isFinanceGroup() throws QueryException {
        FreeAssetsHeaderModel modelProducer = (FreeAssetsHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getIsFinanceGroupModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

    /**
     * Function:        判断用户当前建单组别是否专业组别
     * author:         李轶
     * date            2009-08-25
     * param           //fromGroupName
     * @return boolean类型
     * @throws com.sino.base.exception.QueryException
     *
     */
    public boolean isProfessionalGroup(String fromGroup) throws QueryException {
        FreeAssetsHeaderModel modelProducer = (FreeAssetsHeaderModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getIsGroupFlowIdModel(fromGroup);
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.executeQuery();
        return simp.hasResult();
    }

    public String findGroupFlowId() throws QueryException {
        String GroupPid = "";
        try {
            FreeAssetsHeaderModel modelProducer = (FreeAssetsHeaderModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getGroupPidModel();
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                Row row = simp.getFirstRow();
                GroupPid = row.getStrValue("P_FLOW_ID");
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return GroupPid;
    }

    public boolean findThredDept(String fDept, String tDept) throws QueryException {
        boolean isThredDept = false;
        try {
            FreeAssetsHeaderModel modelProducer = (FreeAssetsHeaderModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getThredDeptModel(fDept, tDept);
            SimpleQuery simp = new SimpleQuery(sqlModel, conn);
            simp.executeQuery();
            if (simp.hasResult()) {
                Row row = simp.getFirstRow();
                String thredDept = row.getStrValue("IS_THRED");
//                if (thredDept.equals("2")) {
//                    isThredDept = true;
//                }
                if (!thredDept.equals("0")) {
                    isThredDept = true;
                } else {
                    isThredDept = false;
                }
            }
        } catch (ContainerException ex) {
            ex.printLog();
            throw new QueryException(ex);
        }
        return isThredDept;
    }

    public void logItemChgHistory(DTOSet lineSet) {
        AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) dtoParameter;
        AmsItemInfoHistoryDAO historyDAO = new AmsItemInfoHistoryDAO(userAccount, null, conn);
        String orderURL = "/servlet/com.sino.ams.newasset.free.servlet.FreeAssetsHeaderServlet";
        orderURL += "?act=DETAIL_ACTION";
        orderURL += "&transId=" + dto.getTransId();
        for (int i = 0; i < lineSet.getSize(); i++) {
            AmsAssetsTransLineDTO lineData = (AmsAssetsTransLineDTO) lineSet.getDTO(i);
            String barcode = lineData.getBarcode();

            AmsItemInfoHistoryDTO historyDTO = new AmsItemInfoHistoryDTO();
            historyDTO.setOrderCategory("3");
            historyDTO.setOrderNo(dto.getTransNo());
            historyDTO.setCreatedBy(userAccount.getUserId());
            historyDTO.setOrderDtlUrl(orderURL);
            historyDTO.setBarcode(barcode);

            historyDAO.setDTOParameter(historyDTO);
            historyDAO.recordHistory();
        }
    }
}
