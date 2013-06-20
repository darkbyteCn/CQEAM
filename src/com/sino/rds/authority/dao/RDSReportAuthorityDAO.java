package com.sino.rds.authority.dao;

import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class RDSReportAuthorityDAO extends RDSBaseDAO {

    public RDSReportAuthorityDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }


    public String getNextPrimaryKey() throws DataHandleException {
        String dataModelId = "";
        try {
            dataModelId = getStrNextSeq("RDS_REPORT_AUTHORITY_S");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return dataModelId;
    }

}
