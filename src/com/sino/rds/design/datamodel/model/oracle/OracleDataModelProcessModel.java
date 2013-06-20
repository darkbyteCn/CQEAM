package com.sino.rds.design.datamodel.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.datamodel.model.DataModelProcessModel;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.DataModelFrm;

import java.util.ArrayList;
import java.util.List;

public class OracleDataModelProcessModel extends DefaultRDSBaseSQLModelImpl implements DataModelProcessModel {

    public OracleDataModelProcessModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_DATA_MODEL\n" +
                "  (MODEL_ID,\n" +
                "   MODEL_NAME,\n" +
                "   CONNECTION_ID,\n" +
                "   MODEL_DESC,\n" +
                "   DATA_SRC_TYPE,\n" +
                "   MODEL_SQL,\n" +
                "   OWNER,\n" +
                "   ENABLED,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY)\n" +
                "VALUES\n" +
                "  (?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   SYSDATE,\n" +
                "   ?)";
        List<String> sqlArgs = new ArrayList<String>();
        DataModelFrm frm = (DataModelFrm) dtoParameter;

        sqlArgs.add(frm.getModelId());
        sqlArgs.add(frm.getModelName());
        sqlArgs.add(frm.getConnectionId());
        sqlArgs.add(frm.getModelDesc());
        sqlArgs.add(frm.getDataSrcType());
        sqlArgs.add(frm.getModelSql());
        sqlArgs.add(frm.getOwner());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_DATA_MODEL RDM\n" +
                "SET    RDM.MODEL_NAME       = ?,\n" +
                "       RDM.CONNECTION_ID    = ?,\n" +
                "       RDM.MODEL_DESC       = ?,\n" +
                "       RDM.DATA_SRC_TYPE    = ?,\n" +
                "       RDM.MODEL_SQL        = ?,\n" +
                "       RDM.OWNER            = ?,\n" +
                "       RDM.ENABLED          = ?,\n" +
                "       RDM.LAST_UPDATE_DATE = SYSDATE,\n" +
                "       RDM.LAST_UPDATE_BY   = ?\n" +
                "WHERE  RDM.MODEL_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        DataModelFrm frm = (DataModelFrm) dtoParameter;

        sqlArgs.add(frm.getModelName());
        sqlArgs.add(frm.getConnectionId());
        sqlArgs.add(frm.getModelDesc());
        sqlArgs.add(frm.getDataSrcType());
        sqlArgs.add(frm.getModelSql());
        sqlArgs.add(frm.getOwner());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getModelId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getDataModelStatusUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        DataModelFrm frm = (DataModelFrm) dtoParameter;
        String sqlStr = "UPDATE RDS_DATA_MODEL RDM\n" +
                "SET    RDM.ENABLED          = ?,\n" +
                "       RDM.LAST_UPDATE_DATE = SYSDATE,\n" +
                "       RDM.LAST_UPDATE_BY   = ?\n" +
                "WHERE  RDM.MODEL_ID IN (" + frm.getModelIds() + ")";
        List<String> sqlArgs = new ArrayList<String>();

        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RDM.MODEL_ID,\n" +
                "       RDM.MODEL_NAME,\n" +
                "       RDM.CONNECTION_ID,\n" +
                "       RDM.MODEL_DESC,\n" +
                "       RDM.DATA_SRC_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('DATA_SRC_TYPE', RDM.DATA_SRC_TYPE) DATA_SRC_TYPE_NAME,\n" +
                "       RDM.MODEL_SQL,\n" +
                "       RDM.OWNER,\n" +
                "       RDM.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RDM.ENABLED) ENABLED_NAME,\n" +
                "       RDM.CONNECTION_ID,\n" +
                "       RDM.CREATION_DATE,\n" +
                "       RDM.CREATED_BY,\n" +
                "       RDM.LAST_UPDATE_DATE,\n" +
                "       RDM.LAST_UPDATE_BY\n" +
                "FROM   RDS_DATA_MODEL RDM\n" +
                "WHERE  RDM.MODEL_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        DataModelFrm frm = (DataModelFrm) dtoParameter;

        sqlArgs.add(frm.getModelId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RDM.MODEL_ID,\n" +
                "       RDM.MODEL_NAME,\n" +
                "       RDM.MODEL_DESC,\n" +
                "       RDM.DATA_SRC_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('DATA_SRC_TYPE', RDM.DATA_SRC_TYPE) DATA_SRC_TYPE_NAME,\n" +
                "       RDM.MODEL_SQL,\n" +
                "       RDM.OWNER,\n" +
                "       RDM.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RDM.ENABLED) ENABLED_NAME,\n" +
                "       RDM.CREATION_DATE,\n" +
                "       RDM.CREATED_BY,\n" +
                "       RDM.LAST_UPDATE_DATE,\n" +
                "       RDM.LAST_UPDATE_BY,\n" +
                "       RDC.CONNECTION_NAME\n" +
                "FROM   RDS_DATA_MODEL    RDM,\n" +
                "       RDS_DB_CONNECTION RDC\n" +
                "WHERE  RDM.CONNECTION_ID = RDC.CONNECTION_ID\n" +
                "       AND RDM.MODEL_NAME LIKE NVL(?, RDM.MODEL_NAME)\n" +
                "       AND RDM.DATA_SRC_TYPE = NVL(?, RDM.DATA_SRC_TYPE)\n" +
                "       AND RDM.ENABLED = NVL(?, RDM.ENABLED)\n" +
                "ORDER BY RDM.MODEL_ID";
        List<String> sqlArgs = new ArrayList<String>();
        DataModelFrm frm = (DataModelFrm) dtoParameter;

        sqlArgs.add(frm.getModelName());
        sqlArgs.add(frm.getDataSrcType());
        sqlArgs.add(frm.getEnabled());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getAllDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RDM.MODEL_ID,\n" +
                "       RDM.MODEL_NAME\n" +
                "FROM   RDS_DATA_MODEL RDM\n" +
                "ORDER  BY RDM.MODEL_ID";

        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel getEnabledDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RDM.MODEL_ID,\n" +
                "       RDM.MODEL_NAME\n" +
                "FROM   RDS_DATA_MODEL RDM\n" +
                "WHERE  RDM.ENABLED = ?\n" +
                "ORDER  BY RDM.MODEL_ID";
        List<String> sqlArgs = new ArrayList<String>();
        sqlArgs.add(RDSDictionaryList.TRUE_VALUE);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}
