package com.sino.rds.design.datamodel.dao;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.dao.RDSBaseDAO;
import com.sino.rds.design.datamodel.model.ModelFieldProcessModel;
import com.sino.rds.foundation.db.structure.Field;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ModelFieldProcessDAO extends RDSBaseDAO {

    public ModelFieldProcessDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public String getNextPrimaryKey() throws DataHandleException {
        String dataModelId = "";
        try {
            dataModelId = getStrNextSeq("RDS_MODEL_FIELD_S");
        } catch (SQLException ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex);
        }
        return dataModelId;
    }

    public boolean hasModelField() throws QueryException {
        ModelFieldProcessModel modelProducer = (ModelFieldProcessModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getHasFieldByModelId();
        return hasSearchResult(sqlModel);
    }

    public void deleteFieldsByModelFields(List<Field> fields) throws DataHandleException{
        ModelFieldProcessModel modelProducer = (ModelFieldProcessModel)sqlProducer;
        SQLModel sqlModel = modelProducer.getFieldDeleteModelByFields(fields);
        executeUpdate(sqlModel);
    }
}
