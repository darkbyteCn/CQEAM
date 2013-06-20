package com.sino.rds.design.report.dao;

import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.design.report.model.ReportDefineBaseModel;
import com.sino.rds.design.report.model.ReportDefineProcessModel;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ReportDefineBaseDAO extends RDSBaseDAO {

    public ReportDefineBaseDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public String getNextPrimaryKey() throws DataHandleException {
        String primaryKey = "";
        try {
            primaryKey = getStrNextSeq("RDS_REPORT_DEFINE_S");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return primaryKey;
    }

    public String getNextReportCode() throws QueryException {
        String reportCode = "";
        try {
            ReportDefineBaseModel modelProducer = (ReportDefineBaseModel)sqlProducer;
            SQLModel sqlModel = modelProducer.getNextReportCodeModel();
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            if(sq.hasResult()){
                reportCode = sq.getFirstRow().getStrValue("RPT_CODE");
            }
        } catch (ContainerException ex) {
            Logger.logError(ex);
            throw new QueryException(ex);
        }
        return reportCode;
    }
}
