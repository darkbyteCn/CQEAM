package com.sino.rds.design.report.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.report.model.ReportDefineBaseModel;
import com.sino.rds.share.form.ReportDefineFrm;

import java.util.ArrayList;
import java.util.List;

public abstract class OracleReportDefineBaseModel extends DefaultRDSBaseSQLModelImpl implements ReportDefineBaseModel {

    public OracleReportDefineBaseModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getNextReportCodeModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT GET_RDS_NEXT_RPT_CODE RPT_CODE FROM DUAL";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRD.REPORT_ID,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME,\n" +
                "       RRD.REPORT_DESC,\n" +
                "       RRD.REPORT_TYPE,\n" +
                "       RRD.MODEL_ID,\n" +
                "       RRD.REPORT_WIDTH,\n" +
                "       RRD.DISPLAY_TYPE,\n" +
                "       RRD.PAGE_SIZE,\n" +
                "       RRD.COUNT_PAGES,\n" +
                "       RRD.SUPPORT_DRILL_DOWN,\n" +
                "       RRD.DRILL_DOWN_TYPE,\n" +
                "       RRD.DRILL_DOWN_URL,\n" +
                "       RRD.DRILL_DOWN_PARAMETERS,\n" +
                "       RRD.DRILL_DOWN_REPORT,\n" +
                "       RRD.SUM_POSITION,\n" +
                "       RRD.ENABLED,\n" +
                "       RRD.SUPPORT_SUB_SUMMARY,\n" +
                "       RRD.CREATED_BY\n" +
                "FROM   RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RRD.REPORT_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_REPORT_DEFINE\n" +
                "  (REPORT_ID,\n" +
                "   REPORT_CODE,\n" +
                "   REPORT_NAME,\n" +
                "   REPORT_DESC,\n" +
                "   REPORT_TYPE,\n" +
                "   MODEL_ID,\n" +
                "   REPORT_WIDTH,\n" +
                "   SUPPORT_DRILL_DOWN,\n" +
                "   DRILL_DOWN_TYPE,\n" +
                "   DRILL_DOWN_URL,\n" +
                "   DRILL_DOWN_PARAMETERS,\n" +
                "   DRILL_DOWN_REPORT,\n" +
                "   DISPLAY_TYPE,\n" +
                "   PAGE_SIZE,\n" +
                "   COUNT_PAGES,\n" +
                "   SQL_MODIFIED,\n" +
                "   MODIFIED_TIME,\n" +
                "   SUM_POSITION,\n" +
                "   SUPPORT_SUB_SUMMARY,\n" +
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
                "   NVL(?, '100%'),\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   'Y',\n" +
                "   SYSDATE,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   SYSDATE,\n" +
                "   ?)";
        List<String> sqlArgs = new ArrayList<String>();
        ReportDefineFrm frm = (ReportDefineFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());
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

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

}
