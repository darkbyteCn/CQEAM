package com.sino.ams.newasset.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTO;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-17
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsMatchDAO extends AMSBaseDAO {
    public AmsAssetsMatchDAO(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter,
                             Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsItemInfoDTO dtoPara = (EtsItemInfoDTO) dtoParameter;

    }

    public boolean saveItemMatch(String barcode, String assetId) throws
            SQLException {
        boolean success = false;
        boolean autoCommit = false;
        CallableStatement cStmt = null;
        //ETS_ITEM_MATCH_PKG.SAVE_ITEM_MATCH1 
        String sqlStr = "{call EIMP_SAVE_ITEM_MATCH1(?,?,?,?,?)}";
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, barcode);
            cStmt.setString(2, assetId);
            cStmt.setString(3, "");
            cStmt.setString(4, "9");
            cStmt.setInt(5, userAccount.getUserId());
            cStmt.execute();
            conn.commit();
            success = true;

        } catch (SQLException e) {
            Logger.logError(e);
            conn.rollback();
            prodMessage("SAVE_FAILURE");
        } finally {
            DBManager.closeDBStatement(cStmt);
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }
}
