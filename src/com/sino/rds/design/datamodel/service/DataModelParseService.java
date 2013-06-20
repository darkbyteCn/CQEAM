package com.sino.rds.design.datamodel.service;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DatabaseException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.service.RDSBaseService;
import com.sino.rds.design.datamodel.dao.DBConnectionDAO;
import com.sino.rds.foundation.db.structure.Field;
import com.sino.rds.foundation.db.structure.SQLObject;
import com.sino.rds.foundation.db.structure.StructureParser;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.DBConnectionFrm;
import com.sino.rds.share.form.DataModelFrm;
import com.sino.rds.share.form.ModelFieldFrm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DataModelParseService extends RDSBaseService {

    public DataModelParseService(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    public List<ModelFieldFrm> getModelFields() throws QueryException {
        List<ModelFieldFrm> modelFields = new ArrayList<ModelFieldFrm>();
        try {
            DataModelFrm frm = (DataModelFrm) dtoParameter;
            List<Field> fields = parseSQLObject();
            for (Field field : fields) {
                ModelFieldFrm fieldFrm = new ModelFieldFrm();
                fieldFrm.setFieldName(field.getName());
                if (field.getComment().equals("")) {
                    fieldFrm.setFieldDesc(field.getName());
                } else {
                    fieldFrm.setFieldDesc(field.getComment());
                }
                fieldFrm.setFieldType(field.getType());
                fieldFrm.setEnabled(RDSDictionaryList.TRUE_VALUE);
                fieldFrm.setCurrCreationDate();
                fieldFrm.setCreatedBy(getUserId());
                fieldFrm.setModelId(frm.getModelId());
                modelFields.add(fieldFrm);
            }
        } catch (DatabaseException ex) {
            Logger.logError(ex);
            throw new QueryException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new QueryException(ex.getMessage());
        }
        return modelFields;
    }

    public List<Field> parseSQLObject() throws DatabaseException {
        List<Field> fields = null;
        Connection dataSource = null;
        try {
            DataModelFrm frm = (DataModelFrm) dtoParameter;
            DBConnectionFrm dbcFrm = new DBConnectionFrm();
            dbcFrm.setConnectionId(frm.getConnectionId());
            DBConnectionDAO connectionDAO = new DBConnectionDAO(userAccount, dbcFrm, conn);
            dbcFrm = connectionDAO.searchDTOByPrimaryKey();
            if (dbcFrm != null) {
                SQLModel sqlModel = new SQLModel();
                String dataSrcType = frm.getDataSrcType();
                if(dataSrcType.equals(RDSDictionaryList.DATA_SRC_TYPE_SQL)){
                    sqlModel.setSqlStr(frm.getModelSql());
                } else {
                    sqlModel.setSqlStr("SELECT * FROM " + frm.getModelSql());
                }
                dataSource = dbcFrm.getDBConnection();
                SQLObject sqlObject = StructureParser.getSQLObject(sqlModel, dataSource);
                fields = sqlObject.getFields();
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            try {
                if (dataSource != null) {
                    dataSource.close();
                }
            } catch (SQLException ex) {
                Logger.logError(ex);
            }
        }
        return fields;
    }
}
