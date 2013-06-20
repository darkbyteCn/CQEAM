package com.sino.nm.spare2.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.ConvertUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.nm.spare2.dto.AmsItemAllocateDDTO;
import com.sino.nm.spare2.dto.AmsItemAllocateHDTO;
import com.sino.nm.spare2.model.BjswjyckModel;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-6-20
 */
public class BjswjyckDAO extends AMSBaseDAO {
    private AmsItemAllocateHDTO headerDto = null;

    /**
     * 功能：构造函数。
     * @param userAccount  UserAccountDTO 用户信息
     * @param dtoParameter DTO 其他与数据库交互时需要的参数。
     * @param conn         Connection 数据库连接
     */
    public BjswjyckDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        headerDto = (AmsItemAllocateHDTO) dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new BjswjyckModel(userAccount, dtoParameter);
    }

    public RowSet getLines(String transId) throws QueryException, SQLModelException {
        SimpleQuery sq = new SimpleQuery(sqlProducer.getDataByForeignKeyModel(transId), conn);
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public boolean submitOrder(DTOSet lines) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            updateData();
            do_freight(lines);
            conn.commit();
            success = true;
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    /**
     * 发运
     * @param lines DTOSet
     * @throws DataHandleException
     * @throws SQLException
     */
    private void do_freight(DTOSet lines) throws DataHandleException, SQLException {
        if (lines != null && !lines.isEmpty()) {
            AmsItemAllocateDDTO lineDto = null;
            CallableStatement cStmt = null;
            try {
                for (int i = 0; i < lines.getSize(); i++) {
                    lineDto = (AmsItemAllocateDDTO) lines.getDTO(i);
                    if (lineDto.getFreightQty()>0 && lineDto.getFreightQty()>0) {
                        String sqlStr = "{call dbo.AIT_DO_FREIGHT(?,?,?,?,?,?)}";
                        cStmt = conn.prepareCall(sqlStr);
                        cStmt.setString(1, headerDto.getTransId());
                        cStmt.setString(2, lineDto.getDetailId());
                        cStmt.setString(3, lineDto.getItemCode());
                        cStmt.setInt(4, lineDto.getFreightQty());
                        cStmt.setString(5, lineDto.getBarcode());
                        cStmt.setInt(6, userAccount.getUserId());
                        cStmt.execute();
                    }
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    /**
     * 重新寄送
     * @param resendDetailId 需重新寄送的行
     * @return boolean
     * @throws SQLException
     */
    public boolean do_resend(String resendDetailId) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        CallableStatement cStmt = null;
        String sqlStr = "{call AMS_INV_TRANS2.DO_RESEND(?,?)}";
        try {
            conn.setAutoCommit(false);
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, headerDto.getTransId());
            cStmt.setString(2, resendDetailId);
            cStmt.execute();
            conn.commit();
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SAVE_FAILURE");
        } finally {
            DBManager.closeDBStatement(cStmt);
            conn.setAutoCommit(autoCommit);
        }

        return success;
    }
}
