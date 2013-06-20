package com.sino.ams.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.sino.base.db.conn.DBManager;
import com.sino.base.log.Logger;

/**
 * User: zhoujs
 * Date: 2009-5-24 18:32:30
 * Function:
 */
public class GenBarcode {
    /**
     * 根据公司代码取标签号
     * @param conn        数据库连接
     * @param companyCode 公司代码
     * @return String
     */
    public static String getAssetBarcode(Connection conn, String companyCode) {
        String tagNumber = "";
        CallableStatement cst = null;
        String sqlStr = "begin ? := AMS_BARCODE_PKG.GETASSETBARCODE(?); end;";
        try {
            cst = conn.prepareCall(sqlStr);
            cst.registerOutParameter(1, Types.VARCHAR);
            cst.setString(2, companyCode);
            cst.execute();
            tagNumber = cst.getString(1);
        } catch (SQLException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBStatement(cst);
        }
        return tagNumber;
    }

    /**
     * 功能:调取生成条码.
     * @param conn
     * @param companyCode
     * @param assetsType
     * @param quantity
     * @return
     * @throws SQLException
     */
    public static int getAssetNumber(Connection conn, String companyCode, String assetsType, int quantity) throws SQLException {
        int firstAssetNumber = 0;
        String sqlStr = "";
        if (assetsType.equals("")) {
            sqlStr = "BEGIN ? := AMS_BARCODE_PKG.UPDATEASSETNUMBER(?,?); END;";
        } else {
            sqlStr = "BEGIN ? := AMS_BARCODE_PKG.UPDATEASSETNUMBER(?,?,?); END;";
        }
        CallableStatement cStmt = null;
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.registerOutParameter(1, Types.NUMERIC);
            cStmt.setString(2, companyCode);
            if (assetsType.equals("")) {
                cStmt.setInt(3, quantity);
            } else {
                cStmt.setString(3, assetsType);
                cStmt.setInt(4, quantity);
            }
            cStmt.execute();
            firstAssetNumber = cStmt.getInt(1);
        } finally {
            DBManager.closeDBStatement(cStmt);
        }

        return firstAssetNumber;
    }

    /**
     * 取当前类型的初始标签号
     * @param conn
     * @param companyCode
     * @param assetsType
     * @param quantity
     * @return
     * @throws SQLException
     */
    public static int getAssetTagNumber(Connection conn, String companyCode, String assetsType, int quantity) throws SQLException {
        int firstAssetNumber = 0;
        String sqlStr = "";

        if (assetsType.equals("MIS") || assetsType.equals("TD")) {
            sqlStr = "{ ? = CALL dbo.ABP_UPDATE_MIS_ASSET_NUMBER ?,?,@N_STARTNUM}";
        } else {
            sqlStr = "{ ? = CALL dbo.ABP_UPDATE_ASSET_NUMBER ?,?,?,@N_STARTNUM}";
        }
        CallableStatement cStmt = null;
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.registerOutParameter(1, Types.INTEGER);
            if (assetsType.equals("MIS") || assetsType.equals("TD")) {
                cStmt.setString(2, companyCode);
                cStmt.setInt(3, quantity);
            } else {
                cStmt.setString(2, companyCode);
                cStmt.setString(3, assetsType);
                cStmt.setInt(4, quantity);

            }
            cStmt.execute();
            firstAssetNumber = cStmt.getInt(1);
        } finally {
            DBManager.closeDBStatement(cStmt);
        }

        return firstAssetNumber;
    }
}
