package com.sino.rds.design.report.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.design.report.model.ReportCategoryProcessModel;
import com.sino.rds.foundation.db.structure.Field;
import com.sino.rds.share.form.ReportCategoryFrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReportCategoryProcessDAO extends RDSBaseDAO {

    public ReportCategoryProcessDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public String getNextPrimaryKey() throws DataHandleException {
        String primaryKey = "";
        try {
            primaryKey = getStrNextSeq("RDS_REPORT_CATEGORY_S");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return primaryKey;
    }
    

    public List<ReportCategoryFrm> getAvailableFieldsByReportId() throws QueryException {
        ReportCategoryProcessModel modelProducer = (ReportCategoryProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAvailableFieldsByReportIdModel();
        return searchListByModel(sqlModel, ReportCategoryFrm.class);
    }

    public List<ReportCategoryFrm> getAboveFieldsByReportId() throws QueryException {
        ReportCategoryProcessModel modelProducer = (ReportCategoryProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAboveFieldsByReportIdModel();
        return searchListByModel(sqlModel, ReportCategoryFrm.class);
    }

    public List<ReportCategoryFrm> getLeftFieldsByReportId() throws QueryException {
        ReportCategoryProcessModel modelProducer = (ReportCategoryProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getLeftFieldsByReportIdModel();
        return searchListByModel(sqlModel, ReportCategoryFrm.class);
    }

    public List<ReportCategoryFrm> getSelectedFields() throws QueryException {
        ReportCategoryProcessModel modelProducer = (ReportCategoryProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getSelectedFieldsModel();
        return searchListByModel(sqlModel, ReportCategoryFrm.class);
    }

    public void deleteReportCategoryByIds()throws DataHandleException{
        ReportCategoryProcessModel modelProducer = (ReportCategoryProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getReportCategoryDeleteByIdsModel();
        executeUpdate(sqlModel);
    }

    public void deleteReportCategoryByViewIds()throws DataHandleException{
        ReportCategoryProcessModel modelProducer = (ReportCategoryProcessModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getDeleteByViewIdsModel();
        executeUpdate(sqlModel);
    }

    public boolean hasReportCategoryField() throws QueryException {
        ReportCategoryProcessModel modelProducer = (ReportCategoryProcessModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getHasFieldByReportId();
        return hasSearchResult(sqlModel);
    }

    public void deleteFieldsByModelFields(List<Field> fields) throws DataHandleException{
        ReportCategoryProcessModel modelProducer = (ReportCategoryProcessModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getFieldDeleteModelByFields(fields);
        executeUpdate(sqlModel);
    }
}
