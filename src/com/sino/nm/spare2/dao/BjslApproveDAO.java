package com.sino.nm.spare2.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import com.sino.ams.appbase.dao.AMSProcedureBaseDAO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.OracleAppErrorCode;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.nm.spare2.model.AmsItemTransHModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-10-24
 */
public class BjslApproveDAO extends AMSProcedureBaseDAO {
    private SfUserDTO sfUser = null;
    private AmsItemTransHDTO amsItemTransH = null;

    /**
     * 功能：构造函数。
     * @param userAccount  UserAccountDTO 用户信息
     * @param dtoParameter DTO 其他与数据库交互时需要的参数。
     * @param conn         Connection 数据库连接
     */
    public BjslApproveDAO(SfUserDTO userAccount, AmsItemTransHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.amsItemTransH = dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new AmsItemTransHModel((SfUserDTO) userAccount, (AmsItemTransHDTO) dtoParameter);
    }


    /**
     * 审批通过
     * @param lineSet DTOSet
     * @param flowDTO FlowDTO
     * @return success
     * @throws SQLException
     */
    public boolean approveOrder(DTOSet lineSet, FlowDTO flowDTO) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            // 出库
            /*else if(sectionRight.equals("RECEIVE")){   //接收 作为一个单独的Act
                //,并将条码所在仓库改为在正常库

                updateAddressIdOnNormal();
                updateStatus(DictConstant.COMPLETED); //暂不改变单据状态,接收后才改变状态
            }*/

            //流程处理
            FlowAction fa = new FlowAction(conn, flowDTO);
            fa.flow();
            conn.commit();
            success = true;
        } catch (SQLException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (QueryException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } catch (DataHandleException e) {
            prodMessage("SQL_ERROR");
            Logger.logError(e);
            conn.rollback();
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    /**
     * 保存条码信息
     * @param lineSet DTOSet
     * @throws DataHandleException
     */
    public void saveLines(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            AmsItemTransLDAO lineDAO = new AmsItemTransLDAO(sfUser, null, conn);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                lineData.setTransId(amsItemTransH.getTransId());
                lineDAO.setDTOParameter(lineData);
                lineDAO.createData();
            }
        }
    }

    /**
     * 更新单据状态
     * @param status 状态
     * @throws DataHandleException
     */
    public void updateStatus(String status) throws DataHandleException {
        String sqlStr = " UPDATE AMS_ITEM_TRANS_H\n" +
                " SET TRANS_STATUS = ?," +
                "     SPARE_TYPE=?," +
                "      SPARE_MANUFACTURER=?," +
                "      FEEDBACK_TYPE=?," +
                "      FEEDBACK_INFO=?" +
                " WHERE TRANS_ID = ?";
        List list = new ArrayList();
        list.add(status);
        list.add(amsItemTransH.getSpareType());
        list.add(amsItemTransH.getSpareManufacturer());
        list.add(amsItemTransH.getFeedbackType());
        list.add(amsItemTransH.getFeedbackInfo());
        list.add(amsItemTransH.getTransId());
        SQLModel sqlModel = new SQLModel();
        sqlModel.setArgs(list);
        sqlModel.setSqlStr(sqlStr);
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 写保留量
     * @param lineSet DTOSet
     * @throws DataHandleException
     */
    private void writeReservQty(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && !lineSet.isEmpty()) {
            Map rservedItem = new HashMap();

            String itemCode = "";
            for (int i = 0; i < lineSet.getSize(); i++) {
                int count = 0;
                AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                itemCode = lineData.getItemCode();
                if (rservedItem.get(itemCode) == null) {
                    count++;
                } else {
                    count = Integer.parseInt(rservedItem.get(itemCode).toString()) + 1;
                }
                rservedItem.put(itemCode, String.valueOf(count));
            }
            Iterator keys = rservedItem.keySet().iterator();
            Object key = null;
            String resItem = "";
            String resCount = "";
            String sqlStr = " INSERT INTO AMS_ITEM_RESERVED VALUES(?,SYSDATE,?,?)";
            SQLModel sqlModel = new SQLModel();
            sqlModel.setSqlStr(sqlStr);
            while (keys.hasNext()) {
                key = keys.next();
                resItem = (String) key;
                resCount = (String) rservedItem.get(key);
                List args = new ArrayList();
                args.add(amsItemTransH.getTransId());
                args.add(resItem);
                args.add(resCount);
                sqlModel.setArgs(args);
                DBOperator.updateRecord(sqlModel, conn);
            }

            //==========================================================
            /*DTOSet dtos = new DTOSet();
            String itemCode = "";
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                itemCode = lineData.getItemCode();

                AmsItemReservedDTO reservedDTO = (AmsItemReservedDTO) dtos.getDTO("itemCode", itemCode);
                if(reservedDTO == null){
                    reservedDTO = new AmsItemReservedDTO();
                }
                reservedDTO.setReservedCount(String.valueOf(Integer.parseInt(reservedDTO.getReservedCount()) + 1));
                dtos.addDTO(reservedDTO);
            }*/
        }
    }

    /**
     * 更新条码所在仓库为在途库
     * @param lineSet DTOSet
     * @throws SQLException
     */
    private void updateAddressIdOnWay(DTOSet lineSet) throws SQLException {
        if (lineSet != null && !lineSet.isEmpty()) {
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ITEM_ON_THE_WAY(?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setInt(2, this.amsItemTransH.getToOrganizationId());
                    cStmt.setInt(3, this.sfUser.getUserId());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    private void updateAddressIdOnNormal() throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call AMS_ITEM_TRANS.ITEM_ON_NORMAL(?,?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, amsItemTransH.getTransId());
            cStmt.setString(2, this.amsItemTransH.getToObjectNo());
            cStmt.setInt(3, this.sfUser.getUserId());
            cStmt.execute();
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    /**
     * 接收
     * @param req HttpServletRequest
     * @return success
     * @throws SQLException
     */
    /* public boolean accept(HttpServletRequest req) throws SQLException {
            boolean success = false;
            //改变条码所在地点为正常库,并修改单据状态为已完成,删除保留量
            CallableStatement cStmt = null;
            String sqlStr = "{call AMS_ITEM_TRANS.ITEM_RECEIVE(?,?,?)}";
            boolean autoCommit = false;
            try {
                autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(autoCommit);
                cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, amsItemTransH.getTransId());
                cStmt.setString(2, amsItemTransH.getToObjectNo());
                cStmt.setString(3, sfUser.getUserId());
                cStmt.execute();
                //流程处理
                FlowBusiness fb = new FlowBusiness(conn, req, amsItemTransH.getTransId(), amsItemTransH.getTransNo(), sfUser.getUserId(), sfUser.getUsername());
                fb.flow2Next("", true);
                conn.commit();
                success = true;
            } catch (SQLException e) {
                Logger.logError(e);
                conn.rollback();
                prodMessage("SQL_ERROR");
            } catch (FlowException e) {
                Logger.logError(e);
                conn.rollback();
                prodMessage("FLOW_ERROR");
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
            return success;
        }

    */
    /**
     * 退回
     * @param flowDTO FlowDTO
     * @throws SQLException
     */
    public void reject(FlowDTO flowDTO) throws SQLException {
        try {
            conn.setAutoCommit(false);
            //业务处理
            updateStatus(DictConstant.REJECTED);
            //流程处理
            /*  if(flowDTO.getApproveContent().equals("")){
                    flowDTO.setApproveContent("不同意");
            }*/
            FlowAction fb = new FlowAction(conn, flowDTO);
            fb.reject2Begin();
//            fb.reject();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } catch (DataHandleException e) {
            conn.rollback();
            throw new SQLException(e.getMessage());
        } finally {
            conn.setAutoCommit(true);
        }
    }

    /**
     * 生成调拨单和分配单
     * @param flowDTO FlowDTO
     * @return success
     * @throws SQLException
     */
    public boolean submitOrder(FlowDTO flowDTO) throws SQLException {
        boolean success = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            updateStatus(amsItemTransH.getTransStatus());
            if(amsItemTransH.getSpareType().equals("practicality")){
              buildOrders();
            }

            //流程处理
//            FlowAction fb = new FlowAction(conn, flowDTO);
//            fb.flow();
               success = processProcedure(true);
            conn.commit();
            success = true;
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SQL_ERROR");
        } catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            if (e.getErrorCode() == OracleAppErrorCode.APPROVE_USER_NOT_FOUND) {
                prodMessage("APPROVE_USER_NOT_FOUND");
            } else {
                prodMessage("SQL_ERROR");
            }
        } /*catch (QueryException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SQL_ERROR");
        }*/ finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    public void buildOrders() throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call dbo.AIT_BUILD_ORDERS(?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, amsItemTransH.getTransId());
            cStmt.setInt(2, sfUser.getUserId());
            cStmt.execute();
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }
}
