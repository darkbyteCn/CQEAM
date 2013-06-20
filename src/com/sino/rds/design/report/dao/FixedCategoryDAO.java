package com.sino.rds.design.report.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.design.report.model.FixedCategoryModel;
import com.sino.rds.share.form.FixedCategoryFrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FixedCategoryDAO extends RDSBaseDAO {

    public FixedCategoryDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public String getNextPrimaryKey() throws DataHandleException {
        String primaryKey = "";
        try {
            primaryKey = getStrNextSeq("RDS_FIXED_CATEGORY_S");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return primaryKey;
    }

    public List<FixedCategoryFrm> searchParentValueFrms() throws QueryException {
        FixedCategoryModel modelProducer = (FixedCategoryModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getParentValuesModel();
        return searchListByModel(sqlModel, FixedCategoryFrm.class);
    }
}
