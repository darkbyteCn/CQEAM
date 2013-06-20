package com.sino.rds.design.report.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.design.report.model.ReportParameterProcessModel;
import com.sino.rds.foundation.db.structure.Field;
import com.sino.rds.share.form.ReportParameterFrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReportParameterProcessDAO extends RDSBaseDAO {

    public ReportParameterProcessDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public String getNextPrimaryKey() throws DataHandleException {
        String dataModelId = "";
        try {
            dataModelId = getStrNextSeq("RDS_REPORT_PARAMETER_S");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return dataModelId;
    }

    public List<ReportParameterFrm> getAvailableFieldsByReportId() throws QueryException {
        ReportParameterProcessModel modelProducer = (ReportParameterProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAvailableFieldsByReportIdModel();
        return searchListByModel(sqlModel, ReportParameterFrm.class);
    }

    public List<ReportParameterFrm> getAlreadyFieldsByReportId() throws QueryException {
        ReportParameterProcessModel modelProducer = (ReportParameterProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAlreadyFieldsByReportIdModel();
        return searchListByModel(sqlModel, ReportParameterFrm.class);
    }

    public List<ReportParameterFrm> getSelectedFields() throws QueryException {
        ReportParameterProcessModel modelProducer = (ReportParameterProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getSelectedFieldsModel();
        return searchListByModel(sqlModel, ReportParameterFrm.class);
    }

    public void deleteReportParameterByIds()throws DataHandleException{
        ReportParameterProcessModel modelProducer = (ReportParameterProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getReportParameterDeleteByIdsModel();
        executeUpdate(sqlModel);
    }

    public boolean hasReportParameterField() throws QueryException {
        ReportParameterProcessModel modelProducer = (ReportParameterProcessModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getHasFieldByReportId();
        return hasSearchResult(sqlModel);
    }
    
    public void deleteFieldsByModelFields(List<Field> fields) throws DataHandleException{
        ReportParameterProcessModel modelProducer = (ReportParameterProcessModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getFieldDeleteModelByFields(fields);
        executeUpdate(sqlModel);
    }
}