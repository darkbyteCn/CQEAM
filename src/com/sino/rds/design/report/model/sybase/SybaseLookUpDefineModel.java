package com.sino.rds.design.report.model.sybase;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.report.model.LookUpDefineModel;
import com.sino.rds.share.form.LookUpDefineFrm;

import java.util.ArrayList;
import java.util.List;

public class SybaseLookUpDefineModel extends DefaultRDSBaseSQLModelImpl implements LookUpDefineModel {

    public SybaseLookUpDefineModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }


    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_LOOKUP_DEFINE\n" +
                "  (LOOK_UP_ID,\n" +
                "   LOOK_UP_CODE,\n" +
                "   LOOK_UP_NAME,\n" +
                "   LOOK_UP_TITLE,\n" +
                "   REPORT_ID,\n" +
                "   RETURN_FIELD,\n" +
                "   OTHER_RETURN_VALUE,\n" +
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
                "   GETDATE(),\n" +
                "   CONVERT(INT, ?))";
        List<String> sqlArgs = new ArrayList<String>();
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;

        sqlArgs.add(frm.getLookUpId());
        sqlArgs.add(frm.getLookUpCode());
        sqlArgs.add(frm.getLookUpName());
        sqlArgs.add(frm.getLookUpTitle());
        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getReturnField());
        sqlArgs.add(frm.getOtherReturnValue());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_LOOKUP_DEFINE \n" +
                "SET    LOOK_UP_CODE       = ?,\n" +
                "       LOOK_UP_NAME       = ?,\n" +
                "       LOOK_UP_TITLE      = ?,\n" +
                "       REPORT_ID          = ?,\n" +
                "       RETURN_FIELD       = ?,\n" +
                "       OTHER_RETURN_VALUE = ?,\n" +
                "       ENABLED            = ?,\n" +
                "       LAST_UPDATE_DATE   = GETDATE(),\n" +
                "       LAST_UPDATE_BY     = CONVERT(INT, ?)\n" +
                "WHERE  LOOK_UP_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;

        sqlArgs.add(frm.getLookUpCode());
        sqlArgs.add(frm.getLookUpName());
        sqlArgs.add(frm.getLookUpTitle());
        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getReturnField());
        sqlArgs.add(frm.getOtherReturnValue());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getLookUpId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getLookUpStatusUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;
        String sqlStr = "UPDATE RDS_LOOKUP_DEFINE RLD\n" +
                "SET    RLD.ENABLED             = ?,\n" +
                "       RLD.LAST_UPDATE_DATE    = GETDATE(),\n" +
                "       RLD.LAST_UPDATE_BY      = ?\n" +
                "WHERE  RLD.LOOK_UP_ID IN ("+frm.getLookUpIds()+")";
        List<String> sqlArgs = new ArrayList<String>();

        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RLD.LOOK_UP_ID,\n" +
                "       RLD.LOOK_UP_CODE,\n" +
                "       RLD.LOOK_UP_NAME,\n" +
                "       RLD.LOOK_UP_TITLE,\n" +
                "       RLD.REPORT_ID,\n" +
                "       RLD.RETURN_FIELD,\n" +
                "       RMF.FIELD_NAME RETURN_FIELD_NAME,\n" +
                "       RLD.OTHER_RETURN_VALUE,\n" +
                "       RLD.ENABLED,\n" +
                "       RLD.CREATION_DATE,\n" +
                "       RLD.CREATED_BY,\n" +
                "       RLD.LAST_UPDATE_DATE,\n" +
                "       RLD.LAST_UPDATE_BY,\n" +
                "       dbo.GET_RDS_FLEX_VALUE('ENABLED_FLAG', RLD.ENABLED) ENABLED_NAME,\n" +
                "       RRD.REPORT_NAME\n" +
                "FROM   RDS_LOOKUP_DEFINE RLD,\n" +
                "       RDS_REPORT_DEFINE RRD,\n" +
                "       RDS_MODEL_FIELD   RMF\n" +
                "WHERE  RLD.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RLD.RETURN_FIELD = RMF.FIELD_ID\n" +
                "       AND (? = '' OR RLD.ENABLED = ?)\n" +
                "       AND (? = '' OR RLD.LOOK_UP_CODE = ?)\n" +
                "       AND (? = '' OR RLD.LOOK_UP_NAME = ?)";
        List<String> sqlArgs = new ArrayList<String>();
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;

        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(frm.getLookUpCode());
        sqlArgs.add(frm.getLookUpCode());
        sqlArgs.add(frm.getLookUpName());
        sqlArgs.add(frm.getLookUpName());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RLD.LOOK_UP_ID,\n" +
                "       RLD.LOOK_UP_CODE,\n" +
                "       RLD.LOOK_UP_NAME,\n" +
                "       RLD.LOOK_UP_TITLE,\n" +
                "       RLD.REPORT_ID,\n" +
                "       RLD.RETURN_FIELD,\n" +
                "       RLD.OTHER_RETURN_VALUE,\n" +
                "       RLD.ENABLED,\n" +
                "       RLD.CREATION_DATE,\n" +
                "       RLD.CREATED_BY,\n" +
                "       RLD.LAST_UPDATE_DATE,\n" +
                "       RLD.LAST_UPDATE_BY,\n" +
                "       dbo.GET_RDS_FLEX_VALUE('ENABLED_FLAG', RLD.ENABLED) ENABLED_NAME,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME,\n" +
                "       RMF.FIELD_NAME RETURN_FIELD_NAME\n" +
                "FROM   RDS_LOOKUP_DEFINE RLD,\n" +
                "       RDS_REPORT_DEFINE RRD,\n" +
                "       RDS_MODEL_FIELD   RMF\n" +
                "WHERE  RLD.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RRD.MODEL_ID = RMF.MODEL_ID\n" +
                "       AND RLD.RETURN_FIELD = RMF.FIELD_ID\n" +
                "       AND (RLD.LOOK_UP_ID = ? OR RLD.LOOK_UP_CODE = ?)";

        List<String> sqlArgs = new ArrayList<String>();
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;

        sqlArgs.add(frm.getLookUpId());
        sqlArgs.add(frm.getLookUpCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getFieldByReportIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RMF.FIELD_ID,\n" +
                "       RMF.FIELD_NAME\n" +
                "FROM   RDS_MODEL_FIELD   RMF,\n" +
                "       RDS_DATA_MODEL    RDM,\n" +
                "       RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RMF.MODEL_ID = RDM.MODEL_ID\n" +
                "       AND RDM.MODEL_ID = RRD.MODEL_ID\n" +
                "       AND RRD.REPORT_ID = ?\n" +
                "ORDER  BY RMF.FIELD_ID";

        List<String> sqlArgs = new ArrayList<String>();
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getField4OptionsByReportIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRV.FIELD_ID,\n" +
                "       RRV.FIELD_NAME\n" +
                "FROM   RDS_REPORT_VIEW   RRV\n" +
                "WHERE  RRV.REPORT_ID = ?\n" +
                "ORDER  BY RRV.SORT_NUMBER";

        List<String> sqlArgs = new ArrayList<String>();
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getReportIdByPrimaryKeyModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RLD.REPORT_ID\n" +
                "FROM   RDS_LOOKUP_DEFINE RLD\n" +
                "WHERE  (? = '' OR RLD.LOOK_UP_ID = ?)\n" +
                "       AND (? = '' OR RLD.LOOK_UP_CODE = ?)\n";

        List<String> sqlArgs = new ArrayList<String>();
        LookUpDefineFrm frm = (LookUpDefineFrm) dtoParameter;

        sqlArgs.add(frm.getLookUpId());
        sqlArgs.add(frm.getLookUpId());
        sqlArgs.add(frm.getLookUpCode());
        sqlArgs.add(frm.getLookUpCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
