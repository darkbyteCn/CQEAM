package com.sino.ams.match.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.match.model.ManualCtMatchModel;
import com.sino.base.data.Row;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 于士博
 * @version 0.1
 *          Date: 2008-12-08
 */
public class ManualCtMatchDAO extends AMSBaseDAO {
	
	public ManualCtMatchDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	private int matchedItemCount = 0;   //本次匹配成功的数量

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new ManualCtMatchModel(userAccount, dtoParameter);
    }


    public boolean saveItemMatch(String[] systemIds, String assetId) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        CallableStatement cStmt = null;
        String sqlStr = "{call ETS_ITEM_CT_MATCH_PKG.SAVE_ITEM_CT_MATCH(?,?,?,?,?)}";
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            cStmt = conn.prepareCall(sqlStr);
            for (int i = 0; i < systemIds.length; i++) {
                cStmt.setString(1, systemIds[i].trim());
                cStmt.setString(2, assetId.trim());
                cStmt.setString(3, "".trim());
                cStmt.setString(4, "9".trim());
                cStmt.setString(5, StrUtil.nullToString(userAccount.getUserId()));
                cStmt.execute();
                conn.commit();
                success = true;
            }
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

    /**
     * 批量匹配
     * @param systemid 设备ID
     * @param assetId  资产ID
     * @param type     匹配方式
     * @return success
     * @throws SQLException
     */
    public synchronized boolean matchByType(String systemid, String assetId, int type) throws SQLException {
        boolean success = false;
        String sqlStr = "";
        switch (type) {
            case 1:
                sqlStr = "{call ETS_ITEM_CT_MATCH_PKG.MATCH_BY_LOCATION(?,?,?,?,?,?)}";
                break;
            case 2:
                sqlStr = "{call ETS_ITEM_CT_MATCH_PKG.MATCH_BY_COUNTY(?,?,?,?,?,?)}";
                break;
            case 3:
                sqlStr = "{call ETS_ITEM_CT_MATCH_PKG.MATCH_BY_CITY(?,?,?,?,?,?)}";
                break;
            default:
                Logger.logError("Manual match type Error !");
        }
        if (!sqlStr.equals("")) {
            boolean autoCommit = false;
            CallableStatement cStmt = null;
            try {
                autoCommit = conn.getAutoCommit();
                conn.setAutoCommit(false);
                cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, systemid);
                cStmt.setString(2, assetId);
                cStmt.setString(3, null);        //BATCH_ID  如果不传则自动生成一个序列号
                cStmt.setInt(4, userAccount.getUserId());
                cStmt.setInt(5, userAccount.getOrganizationId());
                cStmt.registerOutParameter(6, Types.NUMERIC);
                cStmt.execute();
                this.matchedItemCount = cStmt.getInt(6);
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
        }
        return success;
    }

    public int getMatchedItemCount() {
        return matchedItemCount;
    }

    /**
     * 解除某次批量匹配的??
     * @param batchId 批次
     * @return success
     * @throws SQLException
     */
    public int unMatchBatch(String batchId) throws SQLException {
        int count = 0;
        boolean autoCommit = false;
        CallableStatement cStmt = null;
        String sqlStr = "{call ETS_ITEM_CT_MATCH_PKG.UNMATCH_BATCH(?,?,?)}";
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, batchId);
            cStmt.setInt(2, userAccount.getUserId());
            cStmt.registerOutParameter(3, Types.NUMERIC);
            cStmt.execute();
            count = cStmt.getInt(3);   //这里表示解除匹配的数量
            conn.commit();
        } catch (SQLException e) {
            Logger.logError(e);
            conn.rollback();
            prodMessage("SAVE_FAILURE");
            count = -1;
        } finally {
            DBManager.closeDBStatement(cStmt);
            conn.setAutoCommit(autoCommit);
        }
        return count;
    }

    /**
     * 用于批量匹配历史记录查看页面中的批量匹配
     * @param batchId   批次
     * @param matchType 匹配方式
     * @return success
     * @throws SQLException
     */
    public synchronized boolean batchMatch(String batchId, String matchType) throws SQLException {
        boolean success = false;
        String systemId = "";
        String assetId = "";
        //根据batchId找到任意一个与本次记录相匹配的systemId和assetId
        String sqlStr = "SELECT EIM.SYSTEMID, EIM.ASSET_ID\n" +
                "  FROM ETS_FA_MATCH_LOG EFML, ETS_ITEM_MATCH EIM\n" +
                " WHERE EFML.ID = EIM.BATCHID\n" +
                "   AND EIM.BATCHID =?" +
                "   AND ROWNUM = 1";
        SQLModel sqlModel = new SQLModel();
        List list = new ArrayList();
        list.add(batchId);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(list);

        try {
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            Row row = sq.getFirstRow();
            systemId = String.valueOf(row.getValue("SYSTEMID"));
            assetId = String.valueOf(row.getValue("ASSET_ID"));

            if (matchType.equals("matchByLocation")) {
                success = matchByType(systemId, assetId, 0);
            } else if (matchType.equals("matchByCounty")) {
                success = matchByType(systemId, assetId, 1);
            } else if (matchType.equals("matchByCity")) {
                success = matchByType(systemId, assetId, 2);
            }
        } catch (QueryException e) {
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } catch (ContainerException e) {
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        }
        return success;
    }
}
