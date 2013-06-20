package com.sino.rds.authority.model;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.share.form.ModelFieldFrm;

import java.util.ArrayList;
import java.util.List;

public class RDSReportAuthorityModel extends DefaultRDSBaseSQLModelImpl {

    public RDSReportAuthorityModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }


    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_DATA_MODEL\n" +
                "  (MODEL_ID,\n" +
                "   MODEL_NAME,\n" +
                "   MODEL_DESC,\n" +
                "   DATA_SRC_TYPE,\n" +
                "   MODEL_SQL,\n" +
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
                "   GETDATE(),\n" +
                "   ?)";
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;


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
                "       RDM.MODEL_DESC       = ?,\n" +
                "       RDM.DATA_SRC_TYPE    = ?,\n" +
                "       RDM.MODEL_SQL        = ?,\n" +
                "       RDM.ENABLED          = ?,\n" +
                "       RDM.LAST_UPDATE_DATE = GETDATE(),\n" +
                "       RDM.LAST_UPDATE_BY   = ?\n" +
                "WHERE  RDM.MODEL_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;


        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getModelId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_DATA_MODEL RDM\n" +
                "SET    RDM.ENABLED          = ?,\n" +
                "       RDM.LAST_UPDATE_DATE = GETDATE(),\n" +
                "       RDM.LAST_UPDATE_BY   = ?\n" +
                "WHERE  RDM.MODEL_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;

        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());
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
                "       RDM.MODEL_SQL,\n" +
                "       RDM.ENABLED,\n" +
                "       RDM.CREATION_DATE,\n" +
                "       RDM.CREATED_BY,\n" +
                "       RDM.LAST_UPDATE_DATE,\n" +
                "       RDM.LAST_UPDATE_BY\n" +
                "FROM   RDS_DATA_MODEL RDM\n" +
                "WHERE  RDM.MODEL_NAME LIKE NVL(?, RDM.MODEL_NAME)\n" +
                "       AND RDM.DATA_SRC_TYPE = NVL(?, RDM.DATA_SRC_TYPE)\n" +
                "       AND RDM.ENABLED = NVL(?, RDM.ENABLED)";
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;

        sqlArgs.add(frm.getEnabled());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
