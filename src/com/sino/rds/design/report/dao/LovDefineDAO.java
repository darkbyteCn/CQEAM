package com.sino.rds.design.report.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.design.report.model.LovDefineModel;

import java.sql.Connection;
import java.sql.SQLException;

public class LovDefineDAO extends RDSBaseDAO {

    public LovDefineDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public String getNextPrimaryKey() throws DataHandleException {
        String primaryKey = "";
        try {
            primaryKey = getStrNextSeq("RDS_LOV_DEFINE_S");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return primaryKey;
    }

    /**
     * 功能：更新值列表的有效状态
     *
     * @throws com.sino.base.exception.DataHandleException
     *          更新状态出错时抛出该异常
     */
    public void updateLOVStatus() throws DataHandleException {
        LovDefineModel modelProducer = (LovDefineModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getLOVStatusUpdateModel();
        executeUpdate(sqlModel);
    }
}
