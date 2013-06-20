package com.sino.ams.synchronize.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.synchronize.model.SynAssetsTurnModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-11-18
 * Time: 15:24:33
 * To change this template use File | Settings | File Templates.
 */
public class SynAssetsTurnDAO extends AMSBaseDAO {
    private SfUserDTO sfUser = null;

	public SynAssetsTurnDAO(SfUserDTO userAccount, AmsAssetsAddressVDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
		sfUser = userAccount;
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		AmsAssetsAddressVDTO dtoPara = (AmsAssetsAddressVDTO)dtoParameter;
        sqlProducer = new SynAssetsTurnModel((SfUserDTO)userAccount, dtoPara);
    }

    public boolean synTurnInfo(String orgId, String projectNum) throws SQLException {
		boolean success = false;
		boolean autoCommit = false;
		CallableStatement cStmt = null;
        String sqlStr = "{call IMPORT_TURN_PKG.SYN_DATA(?,?,?)}";
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, orgId);
            cStmt.setString(2, projectNum);
            cStmt.registerOutParameter(3, Types.VARCHAR);
            cStmt.execute();
            conn.commit();
            success = true;
        } catch (SQLException e) {
            Logger.logError(e);
            conn.rollback();
        } finally {
            DBManager.closeDBStatement(cStmt);
            conn.setAutoCommit(autoCommit);
        }
		return success;
	}

    public String getDataCount(String orgId, String projectNum) throws SQLModelException, QueryException, ContainerException {
        String totalCount = "";
        SynAssetsTurnModel model = (SynAssetsTurnModel) sqlProducer;
        SQLModel sqlModel = model.getDataCountModel(orgId, projectNum);
        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();
        RowSet rs = simpleQuery.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            totalCount = StrUtil.nullToString(row.getValue("TAOTAL_COUNT"));
        }
        return totalCount;
    }
}
