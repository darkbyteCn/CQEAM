package com.sino.rds.design.report.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.design.report.model.LookUpDefineModel;
import com.sino.rds.share.form.LookUpDefineFrm;
import com.sino.rds.share.form.ModelFieldFrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LookUpDefineDAO extends RDSBaseDAO {

    public LookUpDefineDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public String getNextPrimaryKey() throws DataHandleException {
        String primaryKey = "";
        try {
            primaryKey = getStrNextSeq("RDS_LOOKUP_DEFINE_S");
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
    public void updateLookUpStatus() throws DataHandleException {
        LookUpDefineModel modelProducer = (LookUpDefineModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getLookUpStatusUpdateModel();
        executeUpdate(sqlModel);
    }

    public List<ModelFieldFrm> getFieldByReportId() throws QueryException {
        LookUpDefineModel modelProducer = (LookUpDefineModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getFieldByReportIdModel();
        return searchListByModel(sqlModel, ModelFieldFrm.class);
    }

    public List<ModelFieldFrm> getField4OptionsByReportId() throws QueryException {
        LookUpDefineModel modelProducer = (LookUpDefineModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getField4OptionsByReportIdModel();
        return searchListByModel(sqlModel, ModelFieldFrm.class);
    }

    public String getReportIdByPrimaryKey()throws QueryException{
        String reportId = "";
        LookUpDefineModel modelProducer = (LookUpDefineModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getReportIdByPrimaryKeyModel();
        LookUpDefineFrm lookUpFrm = searchDTOByModel(sqlModel, LookUpDefineFrm.class);
        if(lookUpFrm != null){
            reportId = lookUpFrm.getReportId();
        }
        return reportId;
    }
}
