package com.sino.rds.execute.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.execute.model.FavoriteLineModel;

import java.sql.Connection;

public class FavoriteLineDAO extends RDSBaseDAO {

    public FavoriteLineDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public void saveFavoriteLines(String[] reportIds) throws DataHandleException {
        FavoriteLineModel modelProducer = (FavoriteLineModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getFavoriteLineCreateModel(reportIds);
        executeUpdate(sqlModel);
    }
}
