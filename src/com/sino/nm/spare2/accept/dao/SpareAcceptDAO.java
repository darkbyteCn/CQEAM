package com.sino.nm.spare2.accept.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.ConvertUtil;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.nm.spare2.accept.model.SpareAcceptModel;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 *
 * @author 何睿
 * @version 0.1
 *          Date: 2008-2-18
 */
public class SpareAcceptDAO extends BaseDAO {
    private AmsItemTransHDTO headerDto = null;
    private SfUserDTO sfUser = null;

    /**
     * 功能：构造函数。
     *
     * @param userAccount  UserAccountDTO 用户信息
     * @param dtoParameter DTO 其他与数据库交互时需要的参数。
     * @param conn         Connection 数据库连接
     */
    public SpareAcceptDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.headerDto = (AmsItemTransHDTO) dtoParameter;
        this.sfUser = (SfUserDTO) userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        sqlProducer = new SpareAcceptModel(userAccount, dtoParameter);
    }

    public RowSet getDetails(String transId) throws QueryException {
        SpareAcceptModel model = (SpareAcceptModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdModel(), conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public boolean noAccept(String freightId, String itemCode, String detailId, String qty, String transId) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            createNoAccData(freightId);
          //  addNoAccInfo(transId);
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

    public boolean accept(String freightId, String itemCode, String detailId, String qty, String transId) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            createAccData(freightId, qty);
            addAccInfo(itemCode, detailId, qty, transId);

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

    private void addNoAccInfo(String transId) throws DataHandleException, SQLException {

        CallableStatement cStmt = null;
        String sqlStr = "{call AMS_DEPRECIATION_PKG.UPDATE_NO_ACP_INFO(?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, transId);
            cStmt.setString(2, ConvertUtil.int2String(sfUser.getUserId()));
            cStmt.execute();

        } finally {
            DBManager.closeDBStatement(cStmt);
        }

    }

    private void addAccInfo(String itemCode, String detailId, String qty, String transId) throws DataHandleException, SQLException {

        CallableStatement cStmt = null;
        conn.setAutoCommit(true);
        String sqlStr = "{call dbo.AMS_DEPRECIATION_PKG_UPDATE_ACP_INFO(?,?,?,?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);

            cStmt.setString(1, transId);
            cStmt.setString(2, detailId);
            cStmt.setString(3, itemCode);
            cStmt.setInt(4, ConvertUtil.String2Int(qty));
            cStmt.setInt(5, sfUser.getUserId());
            cStmt.execute();

        } finally {
            DBManager.closeDBStatement(cStmt);
        }

    }

    private void updateHeader() throws DataHandleException, SQLModelException {
        DBOperator.updateRecord(sqlProducer.getDataUpdateModel(), conn);
    }

    private void createNoAccData(String freightId) throws DataHandleException, QueryException {
//        for (int i = 0; i < lineSet.getSize(); i++) {
//            AmsItemAllocateDDTO lineData = (AmsItemAllocateDDTO) lineSet.getDTO(i);
//            lineData.setTransId(headerDto.getTransId());

            SpareAcceptModel model = new SpareAcceptModel(sfUser, headerDto);

//            SimpleQuery sq = new SimpleQuery(model.searchModel(lineData), conn);
//            sq.executeQuery();
//            if (sq.getSearchResult().getSize() > 0) {
                DBOperator.updateRecord(model.updateNoAcceptModel(freightId), conn);
//            } else {
//                DBOperator.updateRecord(model.getInsertNoAccModel(lineData), conn);
//            }


//        }
    }

    private void createAccData(String freightId, String qty) throws DataHandleException, QueryException {

        SpareAcceptModel model = new SpareAcceptModel(sfUser, headerDto);

        DBOperator.updateRecord(model.updateAcceptModel(freightId, qty), conn);
    }

    private void writeAcceptQty(DTOSet lineSet) throws DataHandleException {
        if (lineSet != null && lineSet.getSize() > 0) {
            AmsItemTransLDTO detailDto = null;
            for (int i = 0; i < lineSet.getSize(); i++) {
                detailDto = (AmsItemTransLDTO) lineSet.getDTO(i);
                //接收数量等于调拨数量
                DBOperator.updateRecord(((SpareAcceptModel) sqlProducer).getUpdateAcceptQtyModel(detailDto.getLineId(), detailDto.getQuantity()), conn);
            }
        }
    }

    /**
     * 写AMS_SPARE_INFO表
     *
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
                    cStmt.setString(3, lineData.getAcceptQty());
                    cStmt.setString(4, ConvertUtil.int2String(sfUser.getOrganizationId()));
                    cStmt.setString(5, ConvertUtil.int2String(sfUser.getUserId()));
                    cStmt.setString(6, headerDto.getToObjectNo());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }
}
