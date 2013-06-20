package com.sino.rds.design.datamodel.model.sybase;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.appbase.model.RDSBaseSQLModel;
import com.sino.rds.share.form.DBConnectionFrm;

import java.util.ArrayList;
import java.util.List;

public class SybaseDBConnectionModel extends DefaultRDSBaseSQLModelImpl implements RDSBaseSQLModel {

    public SybaseDBConnectionModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_DB_CONNECTION\n" +
                "  (CONNECTION_ID,\n" +
                "   CONNECTION_NAME,\n" +
                "   DB_DRIVER,\n" +
                "   DB_URL,\n" +
                "   DB_USER,\n" +
                "   DB_PWD,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY)\n" +
                "VALUES\n" +
                "  (?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   GETDATE(),\n" +
                "   CONVERT(INT, ?))";
        List<String> sqlArgs = new ArrayList<String>();
        DBConnectionFrm frm = (DBConnectionFrm) dtoParameter;

        sqlArgs.add(frm.getConnectionId());
        sqlArgs.add(frm.getConnectionName());
        sqlArgs.add(frm.getDbDriver());
        sqlArgs.add(frm.getDbUrl());
        sqlArgs.add(frm.getDbUser());
        sqlArgs.add(frm.getDbPwd());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_DB_CONNECTION\n" +
                "SET    DB_DRIVER        = ?,\n" +
                "       CONNECTION_NAME  = ?,\n" +
                "       DB_URL           = ?,\n" +
                "       DB_USER          = ?,\n" +
                "       DB_PWD           = ?,\n" +
                "       LAST_UPDATE_DATE = GETDATE(),\n" +
                "       LAST_UPDATE_BY   = CONVERT(INT, ?)\n" +
                "WHERE  CONNECTION_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        DBConnectionFrm frm = (DBConnectionFrm) dtoParameter;

        sqlArgs.add(frm.getDbDriver());
        sqlArgs.add(frm.getConnectionName());
        sqlArgs.add(frm.getDbUrl());
        sqlArgs.add(frm.getDbUser());
        sqlArgs.add(frm.getDbPwd());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getConnectionId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RDC.CONNECTION_ID,\n" +
                "       RDC.CONNECTION_NAME,\n" +
                "       RDC.DB_DRIVER,\n" +
                "       RDC.DB_URL,\n" +
                "       RDC.DB_USER,\n" +
                "       RDC.DB_PWD,\n" +
                "       RDC.CREATION_DATE,\n" +
                "       RDC.CREATED_BY,\n" +
                "       RDC.LAST_UPDATE_DATE,\n" +
                "       RDC.LAST_UPDATE_BY\n" +
                "FROM   RDS_DB_CONNECTION RDC\n" +
                "WHERE  RDC.CONNECTION_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        DBConnectionFrm frm = (DBConnectionFrm) dtoParameter;
        sqlArgs.add(frm.getConnectionId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RDC.CONNECTION_ID,\n" +
                "       RDC.CONNECTION_NAME,\n" +
                "       RDC.DB_DRIVER,\n" +
                "       RDC.DB_URL,\n" +
                "       RDC.DB_USER,\n" +
                "       RDC.DB_PWD,\n" +
                "       RDC.CREATION_DATE,\n" +
                "       RDC.CREATED_BY,\n" +
                "       RDC.LAST_UPDATE_DATE,\n" +
                "       RDC.LAST_UPDATE_BY\n" +
                "FROM   RDS_DB_CONNECTION RDC";
        List<String> sqlArgs = new ArrayList<String>();
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
