package com.sino.rds.execute.model.sybase;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.appbase.model.RDSBaseSQLModel;
import com.sino.rds.share.form.ReportViewFrm;

import java.util.ArrayList;
import java.util.List;

public class SybaseViewSearchModel extends DefaultRDSBaseSQLModelImpl implements RDSBaseSQLModel {

    public SybaseViewSearchModel(BaseUserDTO userAccount, DTO dtoParameter) {
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
        String sqlStr = "SELECT RRV.VIEW_ID,\n" +
                "       RRV.REPORT_ID,\n" +
                "       RRV.FIELD_ID,\n" +
                "       RRV.DATA_PATTERN,\n" +
                "       RRV.DOT_NUMBER,\n" +
                "       RRV.FIELD_WIDTH,\n" +
                "       RRV.FIELD_ALIGN,\n" +
                "       RRV.DATA_SRC_TYPE,\n" +
                "       RRV.COMPUTE_EXPRESSION,\n" +
                "       RRV.FIELD_NAME,\n" +
                "       RRV.FIELD_DESC,\n" +
                "       RRV.SORT_NUMBER,\n" +
                "       RRV.ENABLED,\n" +
                "       RMF.FIELD_TYPE\n" +
                "FROM   RDS_REPORT_VIEW RRV,\n" +
                "       RDS_MODEL_FIELD RMF\n" +
                "WHERE  RRV.FIELD_ID = RMF.FIELD_ID\n" +
                "       AND RRV.ENABLED = 'Y'\n" +
                "       AND RRV.REPORT_ID = ?\n" +
                "ORDER  BY RRV.SORT_NUMBER";
        List<String> sqlArgs = new ArrayList<String>();
        ReportViewFrm frm = (ReportViewFrm) dtoParameter;
        sqlArgs.add(frm.getReportId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}