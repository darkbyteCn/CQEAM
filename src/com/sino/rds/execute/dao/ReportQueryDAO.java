package com.sino.rds.execute.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.execute.model.ReportQueryModel;
import com.sino.rds.share.form.FavoriteHeaderFrm;

import java.sql.Connection;
import java.util.List;

public class ReportQueryDAO extends RDSBaseDAO {

    public ReportQueryDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public List<FavoriteHeaderFrm> searchMyFavoriteReports() throws QueryException {
        ReportQueryModel modelProducer = (ReportQueryModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getMyFavoriteReportModel();
        return searchListByModel(sqlModel, FavoriteHeaderFrm.class);
    }
}