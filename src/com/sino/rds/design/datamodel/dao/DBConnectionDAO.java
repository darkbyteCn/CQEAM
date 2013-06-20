package com.sino.rds.design.datamodel.dao;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionDAO extends RDSBaseDAO {

    public DBConnectionDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public String getNextPrimaryKey() throws DataHandleException {
        String dataModelId = "";
        try {
            dataModelId = getStrNextSeq("RDS_DB_CONNECTION_S");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return dataModelId;
    }
}
