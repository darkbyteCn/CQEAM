package com.sino.ams.spare.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.data.RowSet;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.dto.AmsItemAllocateHDTO;
import com.sino.ams.spare.model.AmsItemTransHModel;
import com.sino.ams.spare.model.AmsItemTransLModel;
import com.sino.ams.spare.model.SpareModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.appbase.dao.AMSBaseDAO;
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

public class AmsItemTransHDAO extends AMSProcedureBaseDAO {

    public AmsItemTransHDAO(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        super.sqlProducer = new AmsItemTransHModel((SfUserDTO) userAccount, dto);
    }

    /**
     * 功能：插入备件事务头表(AMS)表“AMS_ITEM_TRANS_H”数据。
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("备件事务头表(AMS)");
    }

    /**
     * 功能：更新备件事务头表(AMS)表“AMS_ITEM_TRANS_H”数据。
     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("备件事务头表(AMS)");
    }

    /**
     * 功能：删除备件事务头表(AMS)表“AMS_ITEM_TRANS_H”数据。
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("备件事务头表(AMS)");
    }


    /**
     * 保存单据
     * @param lineSet 行数据
     * @param flowDTO FlowDTO
     * @return operateResult
     * @throws java.sql.SQLException
     */
    public boolean saveOrder(DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean operateResult = false;
        boolean autoCommit = false;
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            if (transId.equals("")) {
                dto.setTransStatus(DictConstant.SAVE_TEMP);
                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), dto.getTransType());
                dto.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
                transId = seq.getGUID();
                dto.setTransId(transId);
                createData();
                if (dto.getTransType().equals(DictConstant.BJSL)) {
                    flowDTO.setApplyId(transId);
                    flowDTO.setApplyNo(dto.getTransNo());
                    flowDTO.setActivity("9");
                    FlowAction fa = new FlowAction(conn);
                    fa.setDto(flowDTO);
                    fa.add2Flow("备件申领流程");
                }
            } else {
                updateData();
                //有数据，删行信息
                deleteLines(transId);
            }
            setDTOParameter(dto);
            saveLines(lineSet);
            operateResult = true;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
        } finally {
            if (operateResult) {
                conn.commit();
                prodMessage(SpareMessageKeys.SAVE_SUCCESS);
            } else {
                conn.rollback();
                prodMessage(SpareMessageKeys.SAVE_FAILURE);
                message.setNeedClose(true);
            }
            message.setIsError(!operateResult);
            conn.setAutoCommit(autoCommit);
        }
        return operateResult;
    }

    /**
     * 功能: 备件申领暂存单据
     * @param lineSet 行数据
     * @param flowDTO FlowDTO
     * @return operateResult
     * @throws java.sql.SQLException
     */
    public boolean saveCKOrder(DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean operateResult = false;
        boolean autoCommit = false;
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            if (transId.equals("")) {
                dto.setTransStatus(DictConstant.SAVE_TEMP);
                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), dto.getTransType());
                dto.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
                transId = seq.getGUID();
                dto.setTransId(transId);
                createData();
                flowDTO.setApplyId(transId);
                flowDTO.setApplyNo(dto.getTransNo());
                FlowAction fa = new FlowAction(conn);
                fa.setDto(flowDTO);
                fa.add2Flow(SparePROCConstant.SPARE_OUT_PROC);
            } else {
                updateData();
                deleteLines(transId);
            }
            saveLines(lineSet);
            conn.commit();
            prodMessage("SAVE_SUCCESS");
            operateResult = true;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
        } finally {
            if (operateResult) {
                conn.commit();
                prodMessage(SpareMessageKeys.SAVE_SUCCESS);
            } else {
                conn.rollback();
                prodMessage(SpareMessageKeys.SAVE_FAILURE);
                message.setNeedClose(true);
            }
            message.setIsError(!operateResult);
            conn.setAutoCommit(autoCommit);
        }
        return operateResult;
    }

    /**
     * 功能: 备件单据撤销
     * @param flowDTO FlowDTO
     * @return operateResult
     * @throws java.sql.SQLException
     */
    public boolean cancelItemOrder(FlowDTO flowDTO) throws SQLException {
        boolean operateResult = false;
        boolean autoCommit = false;
        try {
            AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            FlowAction fa = new FlowAction(conn);

            BillUtil.updateStatus("AMS_ITEM_TRANS_H", "TRANS_ID", transId, "TRANS_STATUS", DictConstant.CANCELED, conn); // 撤销单据
            flowDTO.setApplyId(transId); //删除在办箱数据
            fa.setDto(flowDTO);
            fa.cancel();
            operateResult = true;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
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
                message.addParameterValue(flowDTO.getProcName());
                message.setIsError(!operateResult);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    public boolean cancelOrder(DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean operateResult = false;
        boolean autoCommit = false;
        try {
            AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            FlowAction fa = new FlowAction(conn);
            flowDTO.setProcName(SparePROCConstant.SPARE_OUT_PROC);
            BillUtil.updateStatus("AMS_ITEM_TRANS_H", "TRANS_ID", transId, "TRANS_STATUS", DictConstant.CANCELED, conn); // 撤销单据
            flowDTO.setApplyId(transId); //删除在办箱数据
            fa.setDto(flowDTO);
            fa.cancel();
            operateResult = true;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
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
                message.addParameterValue("备件出库");
                message.setIsError(!operateResult);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }

    /**
     * 功能: 备件申领撤销
     * @param lineSet 行数据
     * @param flowDTO FlowDTO
     * @return operateResult
     * @throws java.sql.SQLException
     */
    public boolean cancelslOrder(DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean operateResult = false;
        boolean autoCommit = false;
        try {
            AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            FlowAction fa = new FlowAction(conn);
            flowDTO.setProcName(SparePROCConstant.SPARE_APPLY_PROC);
            BillUtil.updateStatus("AMS_ITEM_TRANS_H", "TRANS_ID", transId, "TRANS_STATUS", DictConstant.CANCELED, conn); // 撤销单据
            flowDTO.setApplyId(transId); //删除在办箱数据
            fa.setDto(flowDTO);
            fa.cancel();
            operateResult = true;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
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
                message.addParameterValue("备件申领");
                message.setIsError(!operateResult);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
            }
        }
        return operateResult;
    }


    /**
     * 提交单据（备件入库）
     * @param lineSet 行数据
     * @return operateResult
     * @throws java.sql.SQLException
     */
    public boolean submitOrder(DTOSet lineSet, AmsItemTransHDTO transDTO) throws SQLException {
        boolean operateResult = false;
        boolean autoCommit = false;
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setTransDate(CalendarUtil.getCurrDate());
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), dto.getTransType());
                dto.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
                transId = seq.getGUID();
                dto.setTransId(transId);
                createData();
            } else {
                updateData();
                deleteLines(transId);
            }
            saveLines(lineSet);
            if (dto.getTransType().equals(DictConstant.BJRK)) {     //备件入库
                addSpareInfo(lineSet);
            } else if (dto.getTransType().equals(DictConstant.TMRK)) { //条码设备入库
                addToItemInfo(lineSet, "UNKNOW");
            } else if (dto.getTransType().equals(DictConstant.TMCK)) { //条码设备出库
            } else if (dto.getTransType().equals(DictConstant.FTMRK)) {//非条码设备入库
                InvStorageIn(lineSet);
            } else if (dto.getTransType().equals(DictConstant.FTMCK)) {//非条码设备出库
                InvStorageOut(lineSet);
            } else if (dto.getTransType().equals(DictConstant.BJFK)) {
                //判断条码是否存在,有则修改地点,没有则新增加
                addSpareInfo(lineSet);
            } else if (dto.getTransType().equals(DictConstant.BJSL)) {
                transDTO.setPrimaryKey(transId);
                transDTO.setOrderNo(dto.getTransNo());
                transDTO.setOrderName("备件申领");
                processProcedure(true);
            }
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
                prodMessage(SpareMessageKeys.SAVE_SUCCESS);
                message.addParameterValue(dto.getTransNo());
            } else {
                conn.rollback();
                prodMessage(SpareMessageKeys.SAVE_FAILURE);
                message.setNeedClose(true);
            }
            message.setIsError(!operateResult);
            conn.setAutoCommit(autoCommit);
        }
        return operateResult;
    }

    /**
     * 保存行信息至单据表
     * @param lineSet 行数据
     */
    public void saveLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
            AmsItemTransLDAO lineDAO = new AmsItemTransLDAO(userAccount, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                lineData.setTransId(dto.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    public void deleteLines(String transId) throws DataHandleException {
        AmsItemTransLModel model = new AmsItemTransLModel(userAccount, null);
        DBOperator.updateRecord(model.getDeleteByTransIdModel(transId), conn);
    }

    /**
     * 写ETS_ITEM_INFO表
     * @param lineSet     行数据
     * @param financeProp 类型
     * @throws com.sino.base.exception.DataHandleException
     *
     */
    private void addToItemInfo(DTOSet lineSet, String financeProp) throws DataHandleException, SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ADD_ITEM_INFO(?,?,?,?,?,?)}";
            AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setString(2, lineData.getItemCode());
                    cStmt.setInt(3, userAccount.getOrganizationId());
                    cStmt.setInt(4, userAccount.getUserId());
                    cStmt.setString(5, dto.getToObjectNo());
                    cStmt.setString(6, financeProp);
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
     * @throws com.sino.base.exception.DataHandleException
     */
    private void addSpareInfo(DTOSet lineSet) throws DataHandleException, SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call dbo.AIT_ADD_SPARE_INFO(?,?,?,?,?,?)}";
            AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setString(2, lineData.getItemCode());
                    cStmt.setInt(3, lineData.getQuantity());
                    cStmt.setInt(4, userAccount.getOrganizationId());
                    cStmt.setInt(5, userAccount.getUserId());
                    cStmt.setString(6, dto.getToObjectNo());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    /**
     * 更新条码所在仓库为在途库
     * @param dtoSet DTOSet
     * @throws java.sql.SQLException
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

    public void InvStorageIn(DTOSet lineSet) throws SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.INV_STORAGE_IN(?,?,?,?,?,?)}";
            AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBatchNo());
                    cStmt.setString(2, dto.getToObjectNo());
                    cStmt.setInt(3, dto.getToOrganizationId());
                    cStmt.setString(4, lineData.getItemCode());
                    cStmt.setInt(5, lineData.getQuantity());
                    cStmt.setInt(6, userAccount.getUserId());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public void InvStorageOut(DTOSet lineSet) throws SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.INV_STORAGE_OUT(?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getStorageId());
                    cStmt.setInt(2, lineData.getOutQuantity());
                    cStmt.setInt(3, userAccount.getUserId());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public boolean submitOrderFk(DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean operateResult = false;
        boolean autoCommit = false;
        AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = dto.getTransId();
            dto.setTransDate(CalendarUtil.getCurrDate());
            FlowAction fa = null;
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), dto.getTransType());
                dto.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
                transId = seq.getGUID();
                dto.setTransId(transId);
                createData();
            } else {
                updateData();
                //有数据，删行信息
                deleteLines(transId);
            }
            saveLines(lineSet);
            if (dto.getTransType().equals(DictConstant.BJRK)) {
                addSpareInfo(lineSet);
            } else if (dto.getTransType().equals(DictConstant.TMRK)) { //条码设备入库
                addToItemInfo(lineSet, "UNKNOW");
            } else if (dto.getTransType().equals(DictConstant.TMCK)) { //条码设备出库
            } else if (dto.getTransType().equals(DictConstant.FTMRK)) {//非条码设备入库
                InvStorageIn(lineSet);
            } else if (dto.getTransType().equals(DictConstant.FTMCK)) {//非条码设备出库
                InvStorageOut(lineSet);
            } else if (dto.getTransType().equals(DictConstant.BJFK)) {
                //判断条码是否存在,有则修改地点,没有则新增加
                addSpareInfo(lineSet);
            } else if (dto.getTransType().equals(DictConstant.BJSL)) {
                flowDTO.setApplyId(transId);
                flowDTO.setApplyNo(dto.getTransNo());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                fa = new FlowAction(conn, flowDTO);
                fa.flow();
            } else if (dto.getTransType().equals(DictConstant.JCHG)) {
                InvJchgOut(lineSet);
                InvJchgIn(lineSet);
            } else if (dto.getTransType().equals(DictConstant.HJGH)) {
                InvHjghOut(lineSet);
                InvHjghIn(lineSet);
            }
            operateResult = true;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
        } catch (CalendarException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
        } finally {
            if (operateResult) {
                conn.commit();
                prodMessage(SpareMessageKeys.WAREIN_SUCCESS);
                message.addParameterValue(dto.getTransNo());
            } else {
                conn.rollback();
                prodMessage(SpareMessageKeys.WAREIN_FAILURE);
                message.setNeedClose(true);
            }
            message.setIsError(!operateResult);
            conn.setAutoCommit(autoCommit);
        }
        return operateResult;
    }


    public String getObjectN0() throws QueryException, ContainerException {
        String objectN0 = "";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT EO.WORKORDER_OBJECT_NO, EO.WORKORDER_OBJECT_LOCATION||EO.WORKORDER_OBJECT_NAME\n" +
                        "  FROM ETS_OBJECT EO\n" +
                        " WHERE (EO.OBJECT_CATEGORY = '71' )\n" +
                        "   AND EO.ORGANIZATION_ID = ?";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            objectN0 = simpleQuery.getFirstRow().getStrValue("WORKORDER_OBJECT_NO");
        } else {
            objectN0 = "";
        }
        return objectN0;
    }

    /**
     * 取该单据下的行信息
     * @param transId 单据号
     * @return RowSet
     * @throws QueryException
     */
    public RowSet getLineData(String transId) throws QueryException {
        SQLModel sqlModel = SpareModel.getLineByTransIdModel(transId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        return simpleQuery.getSearchResult();
    }

    /**
     * 取该单据下的详细信息
     * @param transId 单据号
     * @return RowSet
     * @throws QueryException
     */
    public RowSet getDetailData(String transId) throws QueryException {
        SQLModel sqlModel = SpareModel.getDtlByTransIdModel(transId);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        return simpleQuery.getSearchResult();
    }

    public void InvJchgOut(DTOSet lineSet) throws SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS_SX.INV_JCHG_OUT(?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setInt(2, lineData.getQuantity());
                    cStmt.setInt(3, userAccount.getUserId());
                    cStmt.setString(4, dto.getFromObjectNo());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public void InvJchgIn(DTOSet lineSet) throws SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS_SX.INV_JCHG_IN(?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setInt(2, lineData.getQuantity());
                    cStmt.setInt(3, userAccount.getUserId());
                    cStmt.setString(4, dto.getFromObjectNo());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public String getBjghToObjectNo() throws QueryException, ContainerException {
        String objectN0 = "";
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EO.WORKORDER_OBJECT_NO\n" +
                "  FROM ETS_OBJECT EO\n" +
                " WHERE EO.OBJECT_CATEGORY = '72'\n" +
                "       AND EO.WORKORDER_OBJECT_NAME = '省公司待修库'";
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        if (simpleQuery.hasResult()) {
            objectN0 = simpleQuery.getFirstRow().getStrValue("WORKORDER_OBJECT_NO");
        } else {
            objectN0 = "";
        }
        return objectN0;
    }

    public void InvHjghOut(DTOSet lineSet) throws SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call dbo.AITS_INV_HJGH_OUT(?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setInt(2, lineData.getQuantity());
                    cStmt.setInt(3, userAccount.getUserId());
                    cStmt.setString(4, dto.getFromObjectNo());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public void InvHjghIn(DTOSet lineSet) throws SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call dbo.AITS_INV_HJGH_IN(?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                AmsItemTransHDTO dto = (AmsItemTransHDTO) dtoParameter;
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setInt(2, lineData.getQuantity());
                    cStmt.setInt(3, userAccount.getUserId());
                    cStmt.setString(4, dto.getToObjectNo());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    public String checkKYL(String barcode, String fromObjectNo) throws QueryException, ContainerException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ASI.QUANTITY - ASI.RESERVE_QUANTITY ONHAND_QTY\n" +
                "  FROM AMS_SPARE_CATEGORY AC,\n" +
                "       AMS_SPARE_VENDORS  ASV,\n" +
                "       AMS_SPARE_INFO     ASI\n" +
                " WHERE AC.VENDOR_ID = ASV.VENDOR_ID\n" +
                "       AND ASI.BARCODE = AC.BARCODE\n" +
                "       AND AC.ENABLED = 'Y'\n" +
                "       AND ASI.QUANTITY - ASI.RESERVE_QUANTITY > 0\n" +
                "       AND ASI.BARCODE = ?\n" +
                "       AND ASI.OBJECT_NO = ?";
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
