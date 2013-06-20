package com.sino.rds.execute.model.sybase;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.appbase.model.RDSBaseSQLModel;
import com.sino.rds.share.form.ReportParameterFrm;

import java.util.ArrayList;
import java.util.List;

public class SybaseParameterSearchModel extends DefaultRDSBaseSQLModelImpl implements RDSBaseSQLModel {

    public SybaseParameterSearchModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getDataByForeignKeyModel(String foreignKey){
        SQLModel sqlModel = null;
        if(foreignKey.equals("reportId")){
            sqlModel = getDataByReportIdModel();
        }
        return sqlModel;
    }

    private SQLModel getDataByReportIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRP.PARAMETER_ID,\n" +
                "       RRP.FIELD_ID,\n" +
                "       RRP.FIELD_NAME,\n" +
                "       RMF.FIELD_TYPE,\n" +
                "       RRP.FIELD_DESC,\n" +
                "       RRP.FIELD_WIDTH,\n" +
                "       RRP.FIELD_ALIGN,\n" +
                "       RRP.INPUT_TYPE,\n" +
                "       RRP.LOV_ID,\n" +
                "       RRP.LOOK_UP_ID,\n" +
                "       RRP.PARAMETER_URL,\n" +
                "       RRP.CALENDAR_TYPE,\n" +
                "       RRP.REPORT_ID,\n" +
                "       RRP.SORT_NUMBER,\n" +
                "       RRP.NULL_ABLE,\n" +
                "       RRP.ENABLED\n" +
                "FROM   RDS_REPORT_PARAMETER RRP,\n" +
                "       RDS_REPORT_DEFINE    RRD,\n" +
                "       RDS_MODEL_FIELD      RMF\n" +
                "WHERE  RRP.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RRD.MODEL_ID = RMF.MODEL_ID\n" +
                "       AND RRP.FIELD_ID = RMF.FIELD_ID\n" +
                "       AND RRP.ENABLED = 'Y'\n" +
                "       AND RRP.REPORT_ID = ?\n" +
                "ORDER  BY RRP.SORT_NUMBER";
        List<String> sqlArgs = new ArrayList<String>();
        ReportParameterFrm frm = (ReportParameterFrm) dtoParameter;
        sqlArgs.add(frm.getReportId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}