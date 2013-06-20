package com.sino.ams.spare.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.spare.dto.AmsItemAllocateDDTO;
import com.sino.ams.spare.dto.AmsItemAllocateHDTO;
import com.sino.ams.spare.model.BjInConfirmedModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class BjInConfirmedDAO extends BaseDAO {

    private SfUserDTO sfUser = null;
    private AmsItemAllocateHDTO amsItemTransH = null;

    public BjInConfirmedDAO(SfUserDTO userAccount, AmsItemAllocateHDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = userAccount;
        this.amsItemTransH = (AmsItemAllocateHDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsItemAllocateHDTO dtoPara = (AmsItemAllocateHDTO) dtoParameter;
        super.sqlProducer = new BjInConfirmedModel((SfUserDTO) userAccount, dtoPara);
    }

    public RowSet getLines2(String transId) throws QueryException {
        BjInConfirmedModel model = (BjInConfirmedModel) sqlProducer;
        SimpleQuery sq = new SimpleQuery(model.getDataByTransIdMode2(transId), conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public void updateOut(DTOSet lineSet, AmsItemAllocateHDTO hdto) throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call dbo.AITS_INV_NORMAL_OUT(?,?,?,?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemAllocateDDTO dto = (AmsItemAllocateDDTO) lineSet.getDTO(i);
                cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, dto.getBarcode());
                cStmt.setInt(2, amsItemTransH.getFromOrganizationId());
                cStmt.setInt(3, dto.getQuantity());
                cStmt.setInt(4, sfUser.getUserId());
                cStmt.setString(5, hdto.getFromObjectNo());
                cStmt.execute();
            }
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    public void updateIn(DTOSet lineSet, AmsItemAllocateHDTO hdto) throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call dbo.AITS_INV_NORMAL_IN(?,?,?,?,?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            for (int i = 0; i < lineSet.getSize(); i++) {
                AmsItemAllocateDDTO dto = (AmsItemAllocateDDTO) lineSet.getDTO(i);
                cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, dto.getBarcode());
                cStmt.setInt(2, amsItemTransH.getToOrganizationId());
                cStmt.setInt(3, dto.getQuantity());
                cStmt.setInt(4, sfUser.getUserId());
                cStmt.setString(5, hdto.getToObjectNo());
                cStmt.execute();
            }
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    public boolean updateData(String transId,DTOSet lineSet, AmsItemAllocateHDTO hdto) throws DataHandleException, SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            updateIn(lineSet, hdto);
            updateOut(lineSet, hdto);

            BjInConfirmedModel model = new BjInConfirmedModel(sfUser, null);
            DBOperator.updateRecord(model.updateData(transId), conn);
            conn.commit();
            success = true;
        } catch (SQLException e) {
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

}

