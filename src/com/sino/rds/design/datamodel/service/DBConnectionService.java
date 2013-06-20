package com.sino.rds.design.datamodel.service;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.datamodel.dao.DBConnectionDAO;
import com.sino.rds.share.form.DBConnectionFrm;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;


public class DBConnectionService extends RDSBaseService {

    private DBConnectionDAO connectionDAO = null;

    public DBConnectionService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        connectionDAO = new DBConnectionDAO(userAccount, dtoParameter, conn);
        setPrimaryDAO(connectionDAO);
    }

    /**
     * 功能：保存数据模型
     *
     * @throws com.sino.base.exception.DataHandleException
     *          保存数据模型出错时抛出该异常
     */
    public void saveDataSource() throws DataHandleException {
        DBConnectionFrm frm = (DBConnectionFrm) dtoParameter;
        String primaryKey = frm.getPrimaryKey();
        boolean isNew = StrUtil.isEmpty(primaryKey);
        boolean operateResult = false;
        boolean autoCommit = true;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            if (isNew) {
                primaryKey = connectionDAO.getNextPrimaryKey();
                frm.setPrimaryKey(primaryKey);
                connectionDAO.setDTOParameter(frm);
                connectionDAO.createData();
            } else {
                connectionDAO.updateData();
            }
            operateResult = true;
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        } finally {
            try {
                if (!operateResult && isNew) {
                    frm.clearPrimaryKey();
                }
                if (operateResult) {
                    conn.commit();
                    prodMessage("SAVE_CONNECTION_SUCCESS");
                } else {
                    conn.rollback();
                    prodMessage("SAVE_CONNECTION_FAILURE");
                    message.setIsError(true);
                }
                conn.setAutoCommit(autoCommit);
            } catch (Throwable ex) {
                Logger.logError(ex);
                prodMessage(ex);
                message.setIsError(true);
            }
        }
    }

    public DBConnectionFrm searchDataByPrimaryKey() throws QueryException {
        DBConnectionFrm frm = null;
        DBConnectionFrm dto = (DBConnectionFrm) dtoParameter;
        if (dto.isPrimaryKeyEmpty()) {
            frm = dto;
        } else {
            frm = connectionDAO.searchDTOByPrimaryKey();
        }
        return frm;
    }

    /**
     * 功能：检查数据库联接是否正确
     *
     * @return 返回错误信息，如果返回为空，则校验合法，否则不合法。
     */
    public String checkDBConnection() {
        String errorMessage = "";
        Connection conn = null;
        try {
            DBConnectionFrm frm = (DBConnectionFrm) dtoParameter;
            conn = frm.getDBConnection();
        } catch (Throwable ex) {
            errorMessage += getExMessage(ex);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                errorMessage += getExMessage(ex);
            }
        }
        return errorMessage;
    }
    
    private String getExMessage(Throwable ex){
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintWriter(buf, true));
        return buf.toString();
    }
}
