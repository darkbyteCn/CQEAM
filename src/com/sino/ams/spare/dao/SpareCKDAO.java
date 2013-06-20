package com.sino.ams.spare.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;

import com.sino.framework.dto.BaseUserDTO;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.dto.AmsItemAllocateHDTO;
import com.sino.ams.spare.model.AmsItemTransHModel;
import com.sino.ams.spare.model.BjfxsqModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.spare.assistant.SpareMessageKeys;
import com.sino.ams.spare.constant.SparePROCConstant;
import com.sino.ams.util.BillUtil;
import com.sino.ams.newasset.constant.AssetsMessageKeys;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareCKDAO extends AMSProcedureBaseDAO {

    public SpareCKDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        sqlProducer = new AmsItemTransHModel((SfUserDTO) userAccount, dto);
    }

    public boolean submitOrder(DTOSet lineSet, AmsItemTransHDTO transDTO) throws SQLException {
        boolean operateResult = false;
        boolean autoCommit = false;
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsItemTransHDAO aitDAO = new AmsItemTransHDAO(userAccount, dto, conn);
            String transId = dto.getTransId();
            dto.setTransDate(CalendarUtil.getCurrDate());
            if (transDTO.getSf_task_attribute1().equals("FIRST")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), dto.getTransType());
                dto.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
                transId = seq.getGUID();
                dto.setTransId(transId);
                createData();
            } else {
                updateData();
                //有数据，删行信息
                aitDAO.deleteLines(transId);
            }
            setDTOParameter(dto);
            aitDAO.saveLines(lineSet);
            if(transDTO.getSf_task_attribute1().equals("FIRST")){//起始节点
                updateResQty(lineSet,true);
            }
            if (transDTO.getSf_task_attribute1().equals("PRINT")) {//流程结束
                String status = DictConstant.COMPLETED;
                BjfxsqModel model = new BjfxsqModel(userAccount, dto);
                DBOperator.updateRecord(model.updateStatusModel(status), conn);
                updateQty(lineSet);
            }
            transDTO.setPrimaryKey(transId);
            transDTO.setOrderNo(dto.getTransNo());
            transDTO.setOrderName("备件出库");
            processProcedure(true);
            operateResult = true;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
        } catch (CalendarException e) {
            Logger.logError(e);
        } finally {
            if (operateResult) {
                conn.commit();
                prodMessage(SpareMessageKeys.WAREOUT_SUBMIT_SUCCESS);
                message.addParameterValue(dto.getTransNo());
            } else {
                conn.rollback();
                prodMessage(SpareMessageKeys.WAREOUT_SUBMIT_FAILURE);
                message.setNeedClose(true);
            }
            message.setIsError(!operateResult);
            conn.setAutoCommit(autoCommit);
        }
        return operateResult;
    }


    /**
     * 更新条码所在仓库为在途库
     * @param dtoSet DTOSet
     * @throws SQLException
     */
    private void updateAddressIdOnWay(DTOSet dtoSet) throws SQLException {
        if (dtoSet != null && !dtoSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ITEM_ON_THE_WAY(?,?,?)}";
            AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) dtoSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setInt(2, dto.getToOrganizationId());
                    cStmt.setInt(3, userAccount.getUserId());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    /**
     * 写AMS_SPARE_INFO表
     * @param lineSet 行数据
     * @throws DataHandleException
     */
    private void addSpareInfo(DTOSet lineSet) throws DataHandleException, SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_INV_TRANS.ADD_SPARE_INFO(?,?,?,?,?,?)}";
            AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setString(2, lineData.getItemCode());
                    cStmt.setString(3, "-" + lineData.getQuantity());
                    cStmt.setInt(4, userAccount.getOrganizationId());
                    cStmt.setInt(5, userAccount.getUserId());
                    cStmt.setString(6, dto.getFromObjectNo());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    /**
     * 功能: 退回备件出库单
     * @param dto
     * @param flowDTO
     * @throws SQLException
     */
    public boolean reject(AmsItemTransHDTO dto,DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean operateResult = false;
        try {
            conn.setAutoCommit(false);
            //业务处理
            AmsItemTransHModel model = new AmsItemTransHModel(userAccount, dto);
            String status = DictConstant.REJECTED;
            DBOperator.updateRecord(model.updateStatusModel(status), conn);
            updateResQty(lineSet,false);
            flowDTO.setProcName(SparePROCConstant.SPARE_OUT_PROC);
            FlowAction fb = new FlowAction(conn, flowDTO);
            fb.reject2Begin();
            operateResult = true;
        } catch (DataHandleException ex) {
            ex.printLog();
        } finally {
            if (operateResult) {
                conn.commit();
                prodMessage(SpareMessageKeys.TRANS_REJECT_SUCCESS);
            } else {
                conn.rollback();
                prodMessage(SpareMessageKeys.TRANS_REJECT_FAILURE);
            }
            message.addParameterValue(dto.getTransNo());
            message.setIsError(!operateResult);
            conn.setAutoCommit(true);
        }
        return operateResult;
    }


    /**
     * 出库时更新现有量、保留量
     * @param lineSet
     * @throws DataHandleException
     */
    private void updateQty(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            List sqModels = new ArrayList();
            AmsItemTransLDTO lineData = null;
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                SQLModel sqlModel = new SQLModel();
                List sqlArgs = new ArrayList();
                String sqlStr = " UPDATE AMS_SPARE_INFO\n" +
                        "       SET QUANTITY         = QUANTITY - ?,\n" +
                        "           RESERVE_QUANTITY = RESERVE_QUANTITY - ?,\n" +
                        "           LAST_UPDATE_DATE = GETDATE(),\n" +
                        "           LAST_UPDATE_BY   = ?\n" +
                        "     WHERE SPARE_ID = ?";
                sqlArgs.add(lineData.getQuantity());
                sqlArgs.add(lineData.getQuantity());
                sqlArgs.add(userAccount.getUserId());
                sqlArgs.add(lineData.getSpareId());
                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
                sqModels.add(sqlModel);
            }
            DBOperator.updateBatchRecords(sqModels, conn);
        }
    }

    /**
     * 更新保留量--提交 退
     * @param lineSet
     * @throws DataHandleException
     */
    private void updateResQty(DTOSet lineSet,boolean submit) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            List sqModels = new ArrayList();
            AmsItemTransLDTO lineData = null;
            for (int i = 0; i < lineSet.getSize(); i++) {
                lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                SQLModel sqlModel = new SQLModel();
                List sqlArgs = new ArrayList();
                String sqlStr ="";
                if (submit) {
                    sqlStr=" UPDATE AMS_SPARE_INFO\n" +
                            "       SET RESERVE_QUANTITY = RESERVE_QUANTITY + ?,\n" +
                            "           LAST_UPDATE_DATE = GETDATE(),\n" +
                            "           LAST_UPDATE_BY   = ?\n" +
                            "     WHERE SPARE_ID = ?";
                }else{
                    sqlStr=" UPDATE AMS_SPARE_INFO\n" +
                            "       SET RESERVE_QUANTITY = RESERVE_QUANTITY - ?,\n" +
                            "           LAST_UPDATE_DATE = GETDATE(),\n" +
                            "           LAST_UPDATE_BY   = ?\n" +
                            "     WHERE SPARE_ID = ?";

                }
                sqlArgs.add(lineData.getQuantity());
                sqlArgs.add(userAccount.getUserId());
                sqlArgs.add(lineData.getSpareId());
                sqlModel.setSqlStr(sqlStr);
                sqlModel.setArgs(sqlArgs);
                sqModels.add(sqlModel);
            }
            DBOperator.updateBatchRecords(sqModels, conn);
        }
    }

    public boolean approve(DTOSet lineSet, AmsItemTransHDTO transDTO) throws SQLException {//没有用到,原程序审批时调用此方法，现改为SUBMIT方法（2011-12-30 SUHP）
        boolean operateResult = false;
        boolean autoCommit = false;
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            AmsItemTransHDAO aitDAO = new AmsItemTransHDAO(userAccount, dto, conn);
            String transId = dto.getTransId();
            dto.setTransDate(CalendarUtil.getCurrDate());
            if (transDTO.getSf_task_attribute1().equals("PRINT")) { //流程结束
                dto.setTransStatus(DictConstant.COMPLETED);
            } else {
                dto.setTransStatus(DictConstant.IN_PROCESS);
            }
            BjfxsqModel model = new BjfxsqModel(userAccount, dto);
            DBOperator.updateRecord(model.updateStatusModel(dto.getTransStatus()), conn); //修改状态   //审批后不能修改数量由页面控制

            if (transDTO.getSf_task_attribute1().equals("PRINT")) { //流程结束
                updateData(); //修改数量
                //有数据，删行信息
                aitDAO.deleteLines(transId);
                aitDAO.saveLines(lineSet); //插入行信息
                updateQty(lineSet); //修改仓库数量
            }
            transDTO.setPrimaryKey(transId);
            transDTO.setOrderNo(dto.getTransNo());
            transDTO.setOrderName("备件出库");
            processProcedure(true);
            operateResult = true;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
        } catch (CalendarException e) {
            Logger.logError(e);
        } finally {
            if (operateResult) {
                conn.commit();
                prodMessage(SpareMessageKeys.WAREOUT_APPROVE_SUCCESS);
            } else {
                conn.rollback();
                prodMessage(SpareMessageKeys.WAREOUT_APPROVE_FAILURE);
                message.setNeedClose(true);
            }
            message.addParameterValue(dto.getTransNo());
            message.setIsError(!operateResult);
            conn.setAutoCommit(autoCommit);
        }
        return operateResult;
    }

    public String checkKYL(String barcode, String fromObjectNo, String orgId) throws QueryException, ContainerException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASI.QUANTITY - ASI.RESERVE_QUANTITY ONHAND_QTY\n" +
                "  FROM AMS_SPARE_INFO     ASI,\n" +
                "       AMS_SPARE_CATEGORY AMSC,\n" +
                "       AMS_SPARE_VENDORS  ASV\n" +
                " WHERE ASI.BARCODE = AMSC.BARCODE\n" +
                "       AND AMSC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND ASI.QUANTITY - ASI.RESERVE_QUANTITY > 0\n" +
                "       AND ASI.BARCODE = ?\n" +
                "       AND ASI.OBJECT_NO = ?\n";
        sqlArgs.add(barcode);
        sqlArgs.add(fromObjectNo);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.executeQuery();
        String onhandQty = "";
        if (sq.hasResult()) {
            onhandQty = sq.getFirstRow().getStrValue("ONHAND_QTY");
        } else {
            onhandQty = "0";
        }
        return onhandQty;
    }
}
