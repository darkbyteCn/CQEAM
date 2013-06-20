package com.sino.ams.others.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.base.db.conn.DBManager;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.others.model.NobarcodeLModel;
import com.sino.ams.others.model.NobarcodeModel;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2008-7-4
 * Time: 10:05:59
 * To change this template use File | Settings | File Templates.
 */

public class NobarcodeDAO extends BaseDAO {

    private SfUserDTO sfUser = null;
    private AmsItemTransHDTO amsItemTransH = null;

    /**
     * 功能：备件事务头表(EAM) AMS_ITEM_TRANS_H 数据访问层构造函数
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsItemTransHDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public NobarcodeDAO(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.amsItemTransH = (AmsItemTransHDTO) super.dtoParameter;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsItemTransHDTO dtoPara = (AmsItemTransHDTO) dtoParameter;
        super.sqlProducer = new NobarcodeModel((SfUserDTO) userAccount, dtoPara);
    }

    /**
     * 功能：插入备件事务头表(EAM)表“AMS_ITEM_TRANS_H”数据。
     */
    public void createData() throws DataHandleException {
        super.createData();
        getMessage().addParameterValue("备件事务头表(EAM)");
    }

    /**
     * 功能：更新备件事务头表(EAM)表“AMS_ITEM_TRANS_H”数据。
     */
    public void updateData() throws DataHandleException {
        super.updateData();
        getMessage().addParameterValue("备件事务头表(EAM)");
    }

    /**
     * 功能：删除备件事务头表(EAM)表“AMS_ITEM_TRANS_H”数据。
     */
    public void deleteData() throws DataHandleException {
        super.deleteData();
        getMessage().addParameterValue("备件事务头表(EAM)");
    }

    /**
     * 保存单据
     * @param lineSet 行数据
     * @param flowDTO FlowDTO
     * @return success
     * @throws java.sql.SQLException
     */
    public boolean saveOrder(DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean success = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = amsItemTransH.getTransId();
            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), amsItemTransH.getTransType());
                amsItemTransH.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
                transId = StrUtil.nullToString(seq.getStrNextSeq("AMS_ITEM_TRANS_H_S"));
                amsItemTransH.setTransId(transId);
                createData();
                if (amsItemTransH.getTransType().equals(DictConstant.BJSL)) {
                    flowDTO.setApplyId(transId);
                    flowDTO.setApplyNo(amsItemTransH.getTransNo());
                    flowDTO.setActivity("9");
                    FlowAction fa = new FlowAction(conn);
                    fa.setDto(flowDTO);
                    fa.add2Flow(flowDTO.getProcName());
                }
            } else {
                updateData();
                //有数据，删行信息
                deleteLines(transId);
            }
            saveLines(lineSet);
            conn.commit();
            prodMessage("SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (QueryException e) {
            conn.rollback();
            success = false;
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }

        return success;
    }

    /**
     * 提交
     * @param lineSet 行数据
     * @param flowDTO FlowDTO
     * @return success
     * @throws SQLException
     */
    public boolean submitOrder(DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            String transId = amsItemTransH.getTransId();
//            amsItemTransH.setTransStatus(DictConstant.COMPLETED);
            amsItemTransH.setTransDate(CalendarUtil.getCurrDate());
            if (flowDTO == null) {
                flowDTO = new FlowDTO();
            }
//            if (flowDTO.getActId().equals("")) {
            flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_SUBMIT);
//            } else {
//                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_RESUBMIT);
//            }
            FlowAction fa = null;

            if (transId.equals("")) {
                OrderNumGenerator ong = new OrderNumGenerator(conn, sfUser.getCompanyCode(), amsItemTransH.getTransType());
                amsItemTransH.setTransNo(ong.getOrderNum());
                SeqProducer seq = new SeqProducer(conn);
                transId = StrUtil.nullToString(seq.getStrNextSeq("AMS_NOBARCODE_TRANS_H_S"));
                amsItemTransH.setTransId(transId);
                createData();

                /*if (amsItemTransH.getTransType().equals(DictConstant.BJSL)){
                    fa.add2Flow(flowDTO.getProcName());
                }*/

            } else {
                updateData();
                //有数据，删行信息
                deleteLines(transId);
            }
            saveLines(lineSet);
//            if (amsItemTransH.getTransType().equals(DictConstant.BJRK)) {
////                addToItemInfo(lineSet, "SPARE");     //V1.0 备件做为条码管理时调用，写ETS_ITEM_INFO
//                addSpareInfo(lineSet);
//
//            } else if (amsItemTransH.getTransType().equals(DictConstant.TMRK)) { //条码设备入库
//                addToItemInfo(lineSet, "UNKNOW");
//            } else if (amsItemTransH.getTransType().equals(DictConstant.TMCK)) { //条码设备出库
//                updateAddressIdOnWay(lineSet);
//            } else if (amsItemTransH.getTransType().equals(DictConstant.FTMRK)) {//非条码设备入库
////                addToItemInfo(lineSet, "OTHERS");
//                InvStorageIn(lineSet);
//            } else if (amsItemTransH.getTransType().equals(DictConstant.FTMCK)) {//非条码设备出库
//                InvStorageOut(lineSet);
//            } else if (amsItemTransH.getTransType().equals(DictConstant.BJFK)) {
//                //判断条码是否存在,有则修改地点,没有则新增加
////                addToItemInfo(lineSet, "SPARE");
//                addSpareInfo(lineSet);
//            } else if (amsItemTransH.getTransType().equals(DictConstant.BJSL)) {
//                flowDTO.setApplyId(transId);
//                flowDTO.setApplyNo(amsItemTransH.getTransNo());
//                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
//                fa = new FlowAction(conn, flowDTO);
//                fa.flow();
//            }
            conn.commit();
            prodMessage("SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (CalendarException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        }  finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    /**
     * 保存行信息至单据表
     * @param lineSet 行数据
     */
    public void saveLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            NoBarcodeLDAO lineDAO = new NoBarcodeLDAO(sfUser, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                lineData.setTransId(amsItemTransH.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    public void deleteLines(String transId) throws DataHandleException {
        NobarcodeLModel model = new NobarcodeLModel(sfUser, null);
        DBOperator.updateRecord(model.getDeleteByTransIdModel(transId), conn);
    }

    /**
     * 写ETS_ITEM_INFO表
     * @param lineSet     行数据
     * @param financeProp 类型
     * @throws DataHandleException
     */
    private void addToItemInfo(DTOSet lineSet, String financeProp) throws DataHandleException, SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ADD_ITEM_INFO(?,?,?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setString(2, lineData.getItemCode());
                    cStmt.setInt(3, sfUser.getOrganizationId());
                    cStmt.setInt(4, sfUser.getUserId());
                    cStmt.setString(5, amsItemTransH.getToObjectNo());
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
     * @throws DataHandleException
     */
    private void addSpareInfo(DTOSet lineSet) throws DataHandleException, SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_INV_TRANS2.ADD_SPARE_INFO(?,?,?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setString(2, lineData.getItemCode());
                    cStmt.setInt(3, lineData.getQuantity());
                    cStmt.setInt(4, sfUser.getOrganizationId());
                    cStmt.setInt(5, sfUser.getUserId());
                    cStmt.setString(6, amsItemTransH.getToObjectNo());
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
     * @throws SQLException
     */
    private void updateAddressIdOnWay(DTOSet dtoSet) throws SQLException {
        if (dtoSet != null && !dtoSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ITEM_ON_THE_WAY(?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < dtoSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) dtoSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setInt(2, amsItemTransH.getToOrganizationId());
                    cStmt.setInt(3, sfUser.getUserId());
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
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
//                    String batchNo =  lineData.getBatchNo().equals("")?
                    cStmt.setString(1, lineData.getBatchNo());
                    cStmt.setString(2, amsItemTransH.getToObjectNo());
                    cStmt.setInt(3, amsItemTransH.getToOrganizationId());
                    cStmt.setString(4, lineData.getItemCode());
                    cStmt.setInt(5, lineData.getQuantity());
                    cStmt.setInt(6, sfUser.getUserId());
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
                    cStmt.setInt(2, lineData.getQuantity());
                    cStmt.setInt(3, sfUser.getUserId());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }
}