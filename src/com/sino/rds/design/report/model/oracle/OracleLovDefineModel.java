package com.sino.rds.design.report.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.report.model.LovDefineModel;
import com.sino.rds.share.form.LovDefineFrm;

import java.util.ArrayList;
import java.util.List;

public class OracleLovDefineModel extends DefaultRDSBaseSQLModelImpl implements LovDefineModel {

    public OracleLovDefineModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }


    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_LOV_DEFINE\n" +
                "  (LOV_ID,\n" +
                "   LOV_NAME,\n" +
                "   CONNECTION_ID,\n" +
                "   ADD_BLANK,\n" +
                "   LOV_TYPE,\n" +
                "   LOV_SQL,\n" +
                "   LOV_VALUE,\n" +
                "   JAVASCRIPT_FUNCTION,\n" +
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
                "   ?,\n" +
                "   SYSDATE,\n" +
                "   ?)";
        List<String> sqlArgs = new ArrayList<String>();
        LovDefineFrm frm = (LovDefineFrm) dtoParameter;

        sqlArgs.add(frm.getLovId());
        sqlArgs.add(frm.getLovName());
        sqlArgs.add(frm.getConnectionId());
        sqlArgs.add(frm.getAddBlank());
        sqlArgs.add(frm.getLovType());
        sqlArgs.add(frm.getLovSql());
        sqlArgs.add(frm.getLovValue());
        sqlArgs.add(frm.getJavascriptFunction());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_LOV_DEFINE RLD\n" +
                "SET    RLD.LOV_NAME            = ?,\n" +
                "       RLD.CONNECTION_ID       = ?,\n" +
                "       RLD.ADD_BLANK           = ?,\n" +
                "       RLD.LOV_TYPE            = ?,\n" +
                "       RLD.LOV_SQL             = ?,\n" +
                "       RLD.LOV_VALUE           = ?,\n" +
                "       RLD.JAVASCRIPT_FUNCTION = ?,\n" +
                "       RLD.ENABLED             = ?,\n" +
                "       RLD.LAST_UPDATE_DATE    = SYSDATE,\n" +
                "       RLD.LAST_UPDATE_BY      = ?\n" +
                "WHERE  RLD.LOV_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        LovDefineFrm frm = (LovDefineFrm) dtoParameter;

        sqlArgs.add(frm.getLovName());
        sqlArgs.add(frm.getConnectionId());
        sqlArgs.add(frm.getAddBlank());
        sqlArgs.add(frm.getLovType());
        sqlArgs.add(frm.getLovSql());
        sqlArgs.add(frm.getLovValue());
        sqlArgs.add(frm.getJavascriptFunction());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getLovId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getLOVStatusUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        LovDefineFrm frm = (LovDefineFrm) dtoParameter;
        String sqlStr = "UPDATE RDS_LOV_DEFINE RLD\n" +
                "SET    RLD.ENABLED             = ?,\n" +
                "       RLD.LAST_UPDATE_DATE    = SYSDATE,\n" +
                "       RLD.LAST_UPDATE_BY      = ?\n" +
                "WHERE  RLD.LOV_ID IN ("+frm.getLovIds()+")";
        List<String> sqlArgs = new ArrayList<String>();

        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RLD.LOV_ID,\n" +
                "       RLD.LOV_NAME,\n" +
                "       RLD.LOV_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('LOV_TYPE', RLD.LOV_TYPE) LOV_TYPE_NAME,\n" +
                "       RLD.LOV_SQL,\n" +
                "       RLD.LOV_VALUE,\n" +
                "       RLD.JAVASCRIPT_FUNCTION,\n" +
                "       RLD.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RLD.ENABLED) ENABLED_NAME,\n" +
                "       RLD.ADD_BLANK,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RLD.ADD_BLANK) ADD_BLANK_NAME,\n" +
                "       RLD.CREATION_DATE,\n" +
                "       RLD.CREATED_BY,\n" +
                "       RLD.LAST_UPDATE_DATE,\n" +
                "       RLD.LAST_UPDATE_BY,\n" +
                "       RDC.CONNECTION_NAME\n" +
                "FROM   RDS_LOV_DEFINE    RLD,\n" +
                "       RDS_DB_CONNECTION RDC\n" +
                "WHERE  RLD.CONNECTION_ID = RDC.CONNECTION_ID(+)\n" +
                "       AND RLD.LOV_NAME LIKE NVL(?, RLD.LOV_NAME)\n" +
                "       AND RLD.LOV_TYPE = NVL(?, RLD.LOV_TYPE)\n" +
                "       AND RLD.ENABLED = NVL(?, RLD.ENABLED)";
        List<String> sqlArgs = new ArrayList<String>();
        LovDefineFrm frm = (LovDefineFrm) dtoParameter;

        sqlArgs.add(frm.getLovName());
        sqlArgs.add(frm.getLovType());
        sqlArgs.add(frm.getEnabled());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RLD.LOV_ID,\n" +
                "       RLD.LOV_NAME,\n" +
                "       RLD.CONNECTION_ID,\n" +
                "       RLD.ADD_BLANK,\n" +
                "       RLD.LOV_TYPE,\n" +
                "       RLD.LOV_SQL,\n" +
                "       RLD.LOV_VALUE,\n" +
                "       RLD.JAVASCRIPT_FUNCTION,\n" +
                "       RLD.ENABLED,\n" +
                "       RLD.CREATION_DATE,\n" +
                "       RLD.CREATED_BY,\n" +
                "       RLD.LAST_UPDATE_DATE,\n" +
                "       RLD.LAST_UPDATE_BY\n" +
                "FROM   RDS_LOV_DEFINE RLD\n" +
                "WHERE  RLD.LOV_ID = ?";

        List<String> sqlArgs = new ArrayList<String>();
        LovDefineFrm frm = (LovDefineFrm) dtoParameter;

        sqlArgs.add(frm.getLovId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
