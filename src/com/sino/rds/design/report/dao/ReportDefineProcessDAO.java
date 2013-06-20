package com.sino.rds.design.report.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.design.report.model.ReportDefineProcessModel;
import com.sino.rds.share.form.ReportDefineFrm;

import java.sql.Connection;
import java.util.List;

public class ReportDefineProcessDAO extends ReportDefineBaseDAO {

    public ReportDefineProcessDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：更新数据模型的有效状态
     *
     * @throws com.sino.base.exception.DataHandleException
     *          更新状态出错时抛出该异常
     */
    public void updateReportStatus() throws DataHandleException {
        ReportDefineProcessModel modelProducer = (ReportDefineProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getReportStatusUpdateModel();
        executeUpdate(sqlModel);
    }

    public List<ReportDefineFrm> getTargetReports() throws QueryException {
        ReportDefineProcessModel modelProducer = (ReportDefineProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getTargetReportModel();
        return searchListByModel(sqlModel, ReportDefineFrm.class);
    }

    public void confirmActualSQL() throws DataHandleException{
        ReportDefineProcessModel modelProducer = (ReportDefineProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getActualSQLConfirmModel();
        executeUpdate(sqlModel);
    }

    public void updateSQLModifiedFlag() throws DataHandleException{
        ReportDefineProcessModel modelProducer = (ReportDefineProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getActualSQLModifiedFlagModel();
        executeUpdate(sqlModel);
    }

    public void updateModifiedFlagByDataModelId() throws DataHandleException{
        ReportDefineProcessModel modelProducer = (ReportDefineProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getModifiedFlagByDataModelIdModel();
        executeUpdate(sqlModel);
    }

    public boolean hasSQLUpdated() throws QueryException{
        ReportDefineProcessModel modelProducer = (ReportDefineProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getActualSQLHasUpdatedModel();
        return hasSearchResult(sqlModel);
    }
}
