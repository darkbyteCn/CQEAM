package com.sino.rds.design.report.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.design.report.model.ReportDefineProcessModel;
import com.sino.rds.share.form.ReportDefineFrm;

import java.util.ArrayList;
import java.util.List;

public class OracleReportDefineProcessModel extends OracleReportDefineBaseModel implements ReportDefineProcessModel {

    public OracleReportDefineProcessModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_REPORT_DEFINE RRD\n" +
                "SET    RRD.REPORT_CODE           = ?,\n" +
                "       RRD.REPORT_NAME           = ?,\n" +
                "       RRD.REPORT_DESC           = ?,\n" +
                "       RRD.REPORT_TYPE           = ?,\n" +
                "       RRD.MODEL_ID              = ?,\n" +
                "       RRD.REPORT_WIDTH          = NVL(?, '100%'),\n" +
                "       RRD.SUPPORT_DRILL_DOWN    = ?,\n" +
                "       RRD.DRILL_DOWN_TYPE       = ?,\n" +
                "       RRD.DRILL_DOWN_URL        = ?,\n" +
                "       RRD.DRILL_DOWN_PARAMETERS = ?,\n" +
                "       RRD.DRILL_DOWN_REPORT     = ?,\n" +
                "       RRD.DISPLAY_TYPE          = ?,\n" +
                "       RRD.PAGE_SIZE             = ?,\n" +
                "       RRD.COUNT_PAGES           = ?,\n" +
                "       RRD.SUM_POSITION          = ?,\n" +
                "       RRD.SQL_MODIFIED          = 'Y',\n" +
                "       RRD.MODIFIED_TIME         = SYSDATE,\n" +
                "       RRD.SUPPORT_SUB_SUMMARY   = ?,\n" +
                "       RRD.ENABLED               = ?,\n" +
                "       RRD.LAST_UPDATE_DATE      = SYSDATE,\n" +
                "       RRD.LAST_UPDATE_BY        = ?\n" +
                "WHERE  RRD.REPORT_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;

        sqlArgs.add(frm.getReportCode());
        sqlArgs.add(frm.getReportName());
        sqlArgs.add(frm.getReportDesc());
        sqlArgs.add(frm.getReportType());
        sqlArgs.add(frm.getModelId());
        sqlArgs.add(frm.getReportWidth());
        sqlArgs.add(frm.getSupportDrillDown());
        sqlArgs.add(frm.getDrillDownType());
        sqlArgs.add(frm.getDrillDownUrl());
        sqlArgs.add(frm.getDrillDownParameters());
        sqlArgs.add(frm.getDrillDownReport());
        sqlArgs.add(frm.getDisplayType());
        sqlArgs.add(frm.getPageSize());
        sqlArgs.add(frm.getCountPages());
        sqlArgs.add(frm.getSumPosition());
        sqlArgs.add(frm.getSupportSubSummary());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getReportStatusUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;
        String sqlStr = "UPDATE RDS_REPORT_DEFINE RRD\n" +
                "SET    RRD.ENABLED          = ?,\n" +
                "       RRD.LAST_UPDATE_DATE = SYSDATE,\n" +
                "       RRD.LAST_UPDATE_BY   = ?\n" +
                "WHERE  RRD.REPORT_ID IN (" + frm.getReportIds() + ")";
        List<String> sqlArgs = new ArrayList<String>();

        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
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
                "       RRD.SUPPORT_DRILL_DOWN,\n" +
                "       GET_RDS_FLEX_VALUE('SUPPORT_DRILL_DOWN', RRD.SUPPORT_DRILL_DOWN) SUPPORT_DRILL_DOWN_NAME,\n" +
                "       RRD.DRILL_DOWN_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('DRILL_DOWN_TYPE', RRD.DRILL_DOWN_TYPE) DRILL_DOWN_TYPE_NAME,\n" +
                "       RRD.DRILL_DOWN_URL,\n" +
                "       RRD.DRILL_DOWN_PARAMETERS,\n" +
                "       RRD.DRILL_DOWN_REPORT,\n" +
                "       RRD2.REPORT_NAME DRILL_DOWN_REPORT_NAME,\n" +
                "       RRD.DISPLAY_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('DISPLAY_TYPE', RRD.DISPLAY_TYPE) DISPLAY_TYPE_NAME,\n" +
                "       RRD.PAGE_SIZE,\n" +
                "       RRD.COUNT_PAGES,\n" +
                "       GET_RDS_FLEX_VALUE('COUNT_PAGES', RRD.COUNT_PAGES) COUNT_PAGES_NAME,\n" +
                "       RRD.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RRD.ENABLED) ENABLED_NAME,\n" +
                "       GET_RDS_FLEX_VALUE('SUPPORT_SUB_SUMMARY', RRD.SUPPORT_SUB_SUMMARY) SUPPORT_SUB_SUMMARY_NAME,\n" +
                "       RRD.CREATION_DATE,\n" +
                "       RRD.CREATED_BY,\n" +
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
                "       AND RRD.ENABLED = NVL(?, RRD.ENABLED)\n" +
                "ORDER BY RRD.REPORT_ID";
        List<String> sqlArgs = new ArrayList<String>();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;

        sqlArgs.add(frm.getReportType());
        sqlArgs.add(frm.getModelId());
        sqlArgs.add(frm.getReportCode());
        sqlArgs.add(frm.getReportName());
        sqlArgs.add(frm.getEnabled());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRD.REPORT_ID,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME,\n" +
                "       RRD.REPORT_DESC,\n" +
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
                "       RRD.SUM_POSITION,\n" +
                "       RRD.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RRD.ENABLED) ENABLED_NAME,\n" +
                "       GET_RDS_FLEX_VALUE('SUPPORT_SUB_SUMMARY', RRD.SUPPORT_SUB_SUMMARY) SUPPORT_SUB_SUMMARY_NAME,\n" +
                "       RRD.ACTUAL_SQL,\n" +
                "       RRD.ABOVE_DIMENSION_SQL,\n" +
                "       RRD.LEFT_DIMENSION_SQL,\n" +
                "       RRD.BOTTOM_EXPRESSION_SQL,\n" +
                "       RRD.VERTICAL_EXPRESSION_SQL,\n" +
                "       RRD.CREATION_DATE,\n" +
                "       RRD.CREATED_BY,\n" +
                "       RDM.MODEL_NAME,\n" +
                "       RDM.CONNECTION_ID,\n" +
                "       (CASE RDM.DATA_SRC_TYPE\n" +
                "         WHEN 'SQL' THEN\n" +
                "          RDM.MODEL_SQL\n" +
                "         ELSE\n" +
                "          'SELECT * FROM ' || RDM.MODEL_SQL\n" +
                "       END) MODEL_SQL,\n" +
                "       RDM.DATA_SRC_TYPE\n" +
                "FROM   RDS_REPORT_DEFINE RRD,\n" +
                "       RDS_REPORT_DEFINE RRD2,\n" +
                "       RDS_DATA_MODEL    RDM\n" +
                "WHERE  RRD.MODEL_ID = RDM.MODEL_ID\n" +
                "       AND RRD.DRILL_DOWN_REPORT = RRD2.REPORT_ID(+)\n" +
                "       AND (RRD.REPORT_ID = ? OR RRD.REPORT_CODE = ?)";
        List<String> sqlArgs = new ArrayList<String>();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getReportCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getTargetReportModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRD.REPORT_ID,\n" +
                "       RRD.REPORT_NAME,\n" +
                "       RRD.DRILL_DOWN_REPORT\n" +
                "FROM   RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RRD.REPORT_ID <> NVL(?, -1)\n" +
                "       AND RRD.ENABLED = 'Y'\n" +
                "       AND NOT EXISTS (SELECT NULL\n" +
                "        FROM   RDS_REPORT_DEFINE RRD2\n" +
                "        WHERE  RRD2.REPORT_ID = RRD.REPORT_ID\n" +
                "               AND RRD2.DRILL_DOWN_REPORT = NVL(?, -1))\n" +
                "ORDER  BY RRD.REPORT_ID";
        List<String> sqlArgs = new ArrayList<String>();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getActualSQLConfirmModel() {
        SQLModel sqlModel = new SQLModel();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;
        String sqlStr = "UPDATE RDS_REPORT_DEFINE RRD\n" +
                "SET    RRD.ACTUAL_SQL                = ?,\n" +
                "       RRD.ABOVE_DIMENSION_SQL       = ?,\n" +
                "       RRD.LEFT_DIMENSION_SQL        = ?,\n" +
                "       RRD.BOTTOM_EXPRESSION_SQL     = ?,\n" +
                "       RRD.VERTICAL_EXPRESSION_SQL      = ?,\n" +
                "       RRD.SQL_MODIFIED              = 'N',\n" +
                "       RRD.MODIFIED_TIME             = NULL,\n" +
                "       RRD.LAST_UPDATE_DATE          = SYSDATE,\n" +
                "       RRD.LAST_UPDATE_BY            = ?\n" +
                "WHERE  RRD.REPORT_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();

        sqlArgs.add(frm.getActualSql());
        sqlArgs.add(frm.getAboveDimensionSql());
        sqlArgs.add(frm.getLeftDimensionSql());
        sqlArgs.add(frm.getBottomExpressionSql());
        sqlArgs.add(frm.getVerticalExpressionSql());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getActualSQLHasUpdatedModel() {
        SQLModel sqlModel = new SQLModel();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;
        String sqlStr = "SELECT 1\n" +
                "FROM   RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RRD.REPORT_ID = NVL(?, RRD.REPORT_ID)\n" +
                "       AND RRD.REPORT_CODE = NVL(?, RRD.REPORT_CODE)\n" +
                "       AND RRD.SQL_MODIFIED = 'Y'";
        List<String> sqlArgs = new ArrayList<String>();

        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getReportCode());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getActualSQLModifiedFlagModel() {
        SQLModel sqlModel = new SQLModel();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;
        String sqlStr = "UPDATE RDS_REPORT_DEFINE RRD\n" +
                "SET    RRD.SQL_MODIFIED     = 'Y',\n" +
                "       RRD.MODIFIED_TIME    = SYSDATE,\n" +
                "       RRD.LAST_UPDATE_DATE = SYSDATE,\n" +
                "       RRD.LAST_UPDATE_BY   = ?\n" +
                "WHERE  RRD.REPORT_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();

        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getModifiedFlagByDataModelIdModel() {
        SQLModel sqlModel = new SQLModel();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;
        String sqlStr = "UPDATE RDS_REPORT_DEFINE RRD\n" +
                "SET    RRD.SQL_MODIFIED     = 'Y',\n" +
                "       RRD.MODIFIED_TIME    = SYSDATE,\n" +
                "       RRD.LAST_UPDATE_DATE = SYSDATE,\n" +
                "       RRD.LAST_UPDATE_BY   = ?\n" +
                "WHERE  RRD.MODEL_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();

        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getModelId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
