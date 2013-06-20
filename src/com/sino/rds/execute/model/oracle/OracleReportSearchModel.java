package com.sino.rds.execute.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.appbase.model.RDSBaseSQLModel;
import com.sino.rds.share.form.ReportDefineFrm;

import java.util.ArrayList;
import java.util.List;

public class OracleReportSearchModel extends DefaultRDSBaseSQLModelImpl implements RDSBaseSQLModel {

    public OracleReportSearchModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRD.REPORT_ID,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME,\n" +
                "       RRD.REPORT_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('REPORT_TYPE', RRD.REPORT_TYPE) REPORT_TYPE_NAME,\n" +
                "       RRD.MODEL_ID,\n" +
                "       RRD.REPORT_WIDTH,\n" +
                "       RRD.DISPLAY_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('DISPLAY_TYPE', RRD.DISPLAY_TYPE) DISPLAY_TYPE_NAME,\n" +
                "       RRD.PAGE_SIZE,\n" +
                "       RRD.COUNT_PAGES,\n" +
                "       GET_RDS_FLEX_VALUE('COUNT_PAGES', RRD.COUNT_PAGES) COUNT_PAGES_NAME,\n" +
                "       RRD.SUPPORT_DRILL_DOWN,\n" +
                "       GET_RDS_FLEX_VALUE('SUPPORT_DRILL_DOWN', RRD.SUPPORT_DRILL_DOWN) SUPPORT_DRILL_DOWN_NAME,\n" +
                "       RRD.DRILL_DOWN_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('DRILL_DOWN_TYPE', RRD.DRILL_DOWN_TYPE) DRILL_DOWN_TYPE_NAME,\n" +
                "       RRD.DRILL_DOWN_URL,\n" +
                "       RRD.DRILL_DOWN_PARAMETERS,\n" +
                "       RRD.DRILL_DOWN_REPORT,\n" +
                "       RRD2.REPORT_NAME DRILL_DOWN_REPORT_NAME,\n" +
                "       RRD.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RRD.ENABLED) ENABLED_NAME,\n" +
                "       RRD.CREATION_DATE,\n" +
                "       RRD.CREATED_BY,\n" +
                "       RDM.DATA_SRC_TYPE,\n" +
                "       RDM.MODEL_NAME\n" +
                "FROM   RDS_REPORT_DEFINE RRD,\n" +
                "       RDS_REPORT_DEFINE RRD2,\n" +
                "       RDS_DATA_MODEL    RDM\n" +
                "WHERE  RRD.MODEL_ID = RDM.MODEL_ID\n" +
                "       AND RRD.DRILL_DOWN_REPORT = RRD2.REPORT_ID(+)\n" +
                "       AND RRD.REPORT_TYPE = NVL(?, RRD.REPORT_TYPE)\n" +
                "       AND RRD.MODEL_ID = NVL(?, RRD.MODEL_ID)\n" +
                "       AND RRD.REPORT_CODE LIKE NVL(?, RRD.REPORT_CODE)\n" +
                "       AND RRD.REPORT_NAME LIKE NVL(?, RRD.REPORT_NAME)\n" +
                "       AND RRD.ENABLED = 'Y'\n" +
                "ORDER BY RRD.REPORT_CODE"
                ;
        List<String> sqlArgs = new ArrayList<String>();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;

        sqlArgs.add(frm.getReportType());
        sqlArgs.add(frm.getModelId());
        sqlArgs.add(frm.getReportCode());
        sqlArgs.add(frm.getReportName());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRD.REPORT_ID,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME,\n" +
                "       RRD.REPORT_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('REPORT_TYPE', RRD.REPORT_TYPE) REPORT_TYPE_NAME,\n" +
                "       RRD.MODEL_ID,\n" +
                "       RRD.REPORT_WIDTH,\n" +
                "       RRD.DISPLAY_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('DISPLAY_TYPE', RRD.DISPLAY_TYPE) DISPLAY_TYPE_NAME,\n" +
                "       RRD.PAGE_SIZE,\n" +
                "       RRD.COUNT_PAGES,\n" +
                "       GET_RDS_FLEX_VALUE('COUNT_PAGES', RRD.COUNT_PAGES) COUNT_PAGES_NAME,\n" +
                "       RRD.SUPPORT_DRILL_DOWN,\n" +
                "       GET_RDS_FLEX_VALUE('SUPPORT_DRILL_DOWN', RRD.SUPPORT_DRILL_DOWN) SUPPORT_DRILL_DOWN_NAME,\n" +
                "       RRD.DRILL_DOWN_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('DRILL_DOWN_TYPE', RRD.DRILL_DOWN_TYPE) DRILL_DOWN_TYPE_NAME,\n" +
                "       RRD.DRILL_DOWN_URL,\n" +
                "       RRD.DRILL_DOWN_PARAMETERS,\n" +
                "       RRD.DRILL_DOWN_REPORT,\n" +
                "       RRD2.REPORT_NAME DRILL_DOWN_REPORT_NAME,\n" +
                "       RRD.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RRD.ENABLED) ENABLED_NAME,\n" +
                "       RRD.CREATION_DATE,\n" +
                "       RRD.CREATED_BY,\n" +
                "       RRD.SQL_MODIFIED,\n" +
                "       RRD.MODIFIED_TIME,\n" +
                "       RRD.ACTUAL_SQL,\n" +
                "       RDM.MODEL_NAME,\n" +
                "       RDM.DATA_SRC_TYPE\n" +
                "FROM   RDS_REPORT_DEFINE RRD,\n" +
                "       RDS_REPORT_DEFINE RRD2,\n" +
                "       RDS_DATA_MODEL    RDM\n" +
                "WHERE  RRD.MODEL_ID = RDM.MODEL_ID\n" +
                "       AND RRD.DRILL_DOWN_REPORT = RRD2.REPORT_ID(+)\n" +
                "       AND RRD.REPORT_ID = NVL(?, RRD.REPORT_ID)\n" +
                "       AND RRD.REPORT_CODE = NVL(?, RRD.REPORT_CODE)\n" +
                "       AND RRD.ENABLED = 'Y'";
        List<String> sqlArgs = new ArrayList<String>();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getReportCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getDataByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        if(foreignKey.equals("lookUpId")){
            sqlModel = getDataByLookUpIdModel();
        }
        return sqlModel;
    }

    private SQLModel getDataByLookUpIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRD.REPORT_ID,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME,\n" +
                "       RRD.REPORT_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('REPORT_TYPE', RRD.REPORT_TYPE) REPORT_TYPE_NAME,\n" +
                "       RRD.MODEL_ID,\n" +
                "       RRD.REPORT_WIDTH,\n" +
                "       RRD.DISPLAY_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('DISPLAY_TYPE', RRD.DISPLAY_TYPE) DISPLAY_TYPE_NAME,\n" +
                "       RRD.PAGE_SIZE,\n" +
                "       RRD.COUNT_PAGES,\n" +
                "       GET_RDS_FLEX_VALUE('COUNT_PAGES', RRD.COUNT_PAGES) COUNT_PAGES_NAME,\n" +
                "       RRD.SUPPORT_DRILL_DOWN,\n" +
                "       GET_RDS_FLEX_VALUE('SUPPORT_DRILL_DOWN',\n" +
                "                                     RRD.SUPPORT_DRILL_DOWN) SUPPORT_DRILL_DOWN_NAME,\n" +
                "       RRD.DRILL_DOWN_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('DRILL_DOWN_TYPE', RRD.DRILL_DOWN_TYPE) DRILL_DOWN_TYPE_NAME,\n" +
                "       RRD.DRILL_DOWN_URL,\n" +
                "       RRD.DRILL_DOWN_PARAMETERS,\n" +
                "       RRD.DRILL_DOWN_REPORT,\n" +
                "       RRD2.REPORT_NAME DRILL_DOWN_REPORT_NAME,\n" +
                "       RRD.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RRD.ENABLED) ENABLED_NAME,\n" +
                "       RRD.CREATION_DATE,\n" +
                "       RRD.CREATED_BY,\n" +
                "       RRD.SQL_MODIFIED,\n" +
                "       RRD.MODIFIED_TIME,\n" +
                "       RRD.ACTUAL_SQL,\n" +
                "       RDM.MODEL_NAME,\n" +
                "       RDM.DATA_SRC_TYPE\n" +
                "FROM   RDS_REPORT_DEFINE RRD,\n" +
                "       RDS_REPORT_DEFINE RRD2,\n" +
                "       RDS_DATA_MODEL    RDM,\n" +
                "       RDS_LOOKUP_DEFINE RLD\n" +
                "WHERE  RRD.MODEL_ID = RDM.MODEL_ID\n" +
                "       AND RRD.DRILL_DOWN_REPORT = RRD2.REPORT_ID(+)\n" +
                "       AND RRD.REPORT_ID = RLD.REPORT_ID\n" +
                "       AND RLD.LOOK_UP_ID = NVL(?, RLD.LOOK_UP_ID)\n" +
                "       AND RLD.LOOK_UP_CODE = NVL(?, RLD.LOOK_UP_CODE)\n" +
                "       AND RRD.ENABLED = 'Y'";
        List<String> sqlArgs = new ArrayList<String>();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;

        sqlArgs.add(frm.getLookUpId());
        sqlArgs.add(frm.getLookUpCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
