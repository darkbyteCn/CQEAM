package com.sino.rds.design.report.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.design.report.model.ReportDefineCopyModel;

import java.sql.Connection;

public class ReportDefineCopyDAO extends ReportDefineBaseDAO {

    public ReportDefineCopyDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public void copyReport() throws DataHandleException {
        ReportDefineCopyModel modelProducer = (ReportDefineCopyModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getReportDefineCopyModel();
        executeUpdate(sqlModel);
    }
}
