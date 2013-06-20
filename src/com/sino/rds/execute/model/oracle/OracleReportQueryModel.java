package com.sino.rds.execute.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.execute.model.ReportQueryModel;
import com.sino.rds.share.form.FavoriteHeaderFrm;

import java.util.ArrayList;
import java.util.List;

public class OracleReportQueryModel extends DefaultRDSBaseSQLModelImpl implements ReportQueryModel {

    public OracleReportQueryModel(BaseUserDTO userAccount, DTO dtoParameter) {
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
                "       AND ((RDM.MODEL_NAME LIKE NVL(?, RDM.MODEL_NAME))\n" +
                "       OR (RRD.REPORT_CODE LIKE NVL(?, RRD.REPORT_CODE))\n" +
                "       OR (RRD.REPORT_NAME LIKE NVL(?, RRD.REPORT_NAME)))\n" +
                "       AND RRD.ENABLED = 'Y'\n" +
                "ORDER BY RRD.REPORT_CODE"
                ;
        List<String> sqlArgs = new ArrayList<String>();
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) dtoParameter;

        sqlArgs.add(frm.getReportName());
        sqlArgs.add(frm.getReportName());
        sqlArgs.add(frm.getReportName());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getMyFavoriteReportModel(){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RFH.HEADER_ID,\n" +
                "       RFH.FAVORITE_NAME\n" +
                "FROM   RDS_FAVORITE_HEADER RFH\n" +
                "WHERE RFH.CREATED_BY = ?\n" +
                "ORDER BY RFH.CREATION_DATE";

        List<String> sqlArgs = new ArrayList<String>();
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}
