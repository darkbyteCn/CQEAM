package com.sino.rds.execute.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.execute.model.FavoriteHeaderModel;
import com.sino.rds.share.form.FavoriteHeaderFrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FavoriteHeaderDAO extends RDSBaseDAO {

    public FavoriteHeaderDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }


    public String getNextPrimaryKey() throws DataHandleException {
        String primaryKey = "";
        try {
            primaryKey = getStrNextSeq("RDS_FAVORITE_HEADER_S");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return primaryKey;
    }

    public List<FavoriteHeaderFrm> searchMyFavoriteReports() throws QueryException {
        FavoriteHeaderModel modelProducer = (FavoriteHeaderModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getMyFavoriteReportModel();
        return searchListByModel(sqlModel, FavoriteHeaderFrm.class);
    }
}
