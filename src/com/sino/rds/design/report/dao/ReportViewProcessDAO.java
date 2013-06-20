package com.sino.rds.design.report.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.design.report.model.ReportViewProcessModel;
import com.sino.rds.foundation.db.structure.Field;
import com.sino.rds.share.form.ReportViewFrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReportViewProcessDAO extends RDSBaseDAO {

    public ReportViewProcessDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public String getNextPrimaryKey() throws DataHandleException {
        String primaryKey = "";
        try {
            primaryKey = getStrNextSeq("RDS_REPORT_VIEW_S");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return primaryKey;
    }

    public List<ReportViewFrm> getAvailableFieldsByReportId() throws QueryException {
        ReportViewProcessModel modelProducer = (ReportViewProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAvailableFieldsByReportIdModel();
        return searchListByModel(sqlModel, ReportViewFrm.class);
    }

    public List<ReportViewFrm> getAlreadyFieldsByReportId() throws QueryException {
        ReportViewProcessModel modelProducer = (ReportViewProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAlreadyFieldsByReportIdModel();
        return searchListByModel(sqlModel, ReportViewFrm.class);
    }

    public List<ReportViewFrm> getSelectedFields() throws QueryException {
        ReportViewProcessModel modelProducer = (ReportViewProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getSelectedFieldsModel();
        return searchListByModel(sqlModel, ReportViewFrm.class);
    }

    public void deleteReportViewByIds()throws DataHandleException{
        ReportViewProcessModel modelProducer = (ReportViewProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getReportViewDeleteByIdsModel();
        executeUpdate(sqlModel);
    }

    public boolean hasReportViewField() throws QueryException {
        ReportViewProcessModel modelProducer = (ReportViewProcessModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getHasFieldByReportId();
        return hasSearchResult(sqlModel);
    }

    public boolean hasExpressionField() throws QueryException {
        ReportViewProcessModel modelProducer = (ReportViewProcessModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getHasExpressionFieldByReportId();
        return hasSearchResult(sqlModel);
    }

    public void deleteFieldsByModelFields(List<Field> fields) throws DataHandleException{
        ReportViewProcessModel modelProducer = (ReportViewProcessModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getFieldDeleteModelByFields(fields);
        executeUpdate(sqlModel);
    }
}
