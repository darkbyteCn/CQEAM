package com.sino.nm.spare2.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.system.user.dto.SfUserDTO;
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
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.nm.spare2.model.BjghModel;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-3-13
 */
public class BjghDAO extends BaseDAO {

    private SfUserDTO sfUser = null;
    private AmsItemTransHDTO amsItemTransH = null;

    /**
     * 功能：构造函数。
     * @param userAccount  UserAccountDTO 用户信息
     * @param dtoParameter DTO 其他与数据库交互时需要的参数。
     * @param conn         Connection 数据库连接
     */
    public BjghDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        this.sfUser = (SfUserDTO) userAccount;
        this.amsItemTransH = (AmsItemTransHDTO) super.dtoParameter;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new BjghModel(userAccount, dtoParameter);
    }

    /**
     * 备件归还接收
     * @return success
     * @throws SQLException
     */
    public boolean accept() throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            //将单所状态修改为"已接收",并写入接收人
            updateData();
            //将归还的设备入库,并更新调拨单明细信息里的已归还数量
            spareReturnAccept();
            conn.commit();
            prodMessage("SPARE_SAVE_SUCCESS");
            success = true;
        } catch (SQLException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SQL_ERROR");
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SQL_ERROR");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    public boolean reject() throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        //更新单据状态为"已退回",并将归还数量减少
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            updateData();
            spareReturnReject();
            conn.commit();
            success = true;
        } catch (DataHandleException e) {
            conn.rollback();
            Logger.logError(e);
            prodMessage("SQL_ERROR");
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    /**
     * 将归还的设备入库
     * @throws SQLException
     */
    private void spareReturnAccept() throws SQLException {
        CallableStatement cStmt = null;
        String sqlStr = "{call dbo.AMS_INV_TRANS2_SPARE_RETURN_ACCEPT(?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, this.amsItemTransH.getTransId());
            cStmt.execute();
        } finally {
            DBManager.closeDBStatement(cStmt);
        }
    }

    /**
     * 更新调拨单明细信息里的已归还数量
     * @throws SQLException
     */
    private void spareReturnReject() throws SQLException {
        CallableStatement cStmt = null;
        conn.setAutoCommit(true);
        String sqlStr = "{call dbo.AMS_INV_TRANS2_SPARE_RETURN_REJECT(?)}";
        try {
            cStmt = conn.prepareCall(sqlStr);
            cStmt.setString(1, this.amsItemTransH.getTransId());
            cStmt.execute();
        } finally {
            DBManager.closeDBStatement(cStmt);
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
                    cStmt.setString(2, ConvertUtil.int2String(this.amsItemTransH.getToOrganizationId()));
                    cStmt.setString(3, ConvertUtil.int2String(this.sfUser.getUserId()));
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
            String sqlStr = "{call AMS_INV_TRANS.ADD_SPARE_INFO(?,?,?,?,?,?)}";
            try {
                cStmt = conn.prepareCall(sqlStr);
                for (int i = 0; i < lineSet.getSize(); i++) {
                    AmsItemTransLDTO lineData = (AmsItemTransLDTO) lineSet.getDTO(i);
                    cStmt.setString(1, lineData.getBarcode());
                    cStmt.setString(2, lineData.getItemCode());
                    cStmt.setString(3, "-" + lineData.getQuantity());
                    cStmt.setString(4, ConvertUtil.int2String(sfUser.getOrganizationId()));
                    cStmt.setString(5, ConvertUtil.int2String(sfUser.getUserId()));
                    cStmt.setString(6, amsItemTransH.getFromObjectNo());
                    cStmt.execute();
                }
            } finally {
                DBManager.closeDBStatement(cStmt);
            }
        }
    }

    /**
     * 获取行信息
     * @return RowSet
     * @throws QueryException
     */
    public RowSet getLines() throws QueryException {
        SimpleQuery sq = null;
        try {
            sq = new SimpleQuery(sqlProducer.getDataByForeignKeyModel(amsItemTransH.getTransId()), conn);
        } catch (SQLModelException e) {
            throw new QueryException(e);
        }
        sq.executeQuery();
        return sq.getSearchResult();
    }

    public RowSet getQueryLines(String transId) throws QueryException {
        SimpleQuery sq = null;
        try {
            sq = new SimpleQuery(sqlProducer.getDataByForeignKeyModel(transId), conn);
        } catch (SQLModelException e) {
            throw new QueryException(e);
        }
        sq.executeQuery();
        return sq.getSearchResult();
    }

}
