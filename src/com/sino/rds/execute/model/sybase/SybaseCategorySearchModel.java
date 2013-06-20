package com.sino.rds.execute.model.sybase;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.appbase.model.RDSBaseSQLModel;
import com.sino.rds.share.form.ReportCategoryFrm;

import java.util.ArrayList;
import java.util.List;

public class SybaseCategorySearchModel extends DefaultRDSBaseSQLModelImpl implements RDSBaseSQLModel {

    public SybaseCategorySearchModel(BaseUserDTO userAccount, DTO dtoParameter) {
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
        String sqlStr = "SELECT RRC.CATEGORY_ID,\n" +
                "       RRC.FIELD_ID,\n" +
                "       RRC.FIELD_WIDTH,\n" +
                "       RMF.FIELD_NAME,\n" +
                "       RMF.FIELD_DESC,\n" +
                "       RRC.VIEW_LOCATION,\n" +
                "       RRC.DISPLAY_FLAG,\n" +
                "       RRC.CATEGORY_LEVEL\n" +
                "FROM   RDS_REPORT_CATEGORY RRC,\n" +
                "       RDS_MODEL_FIELD     RMF\n" +
                "WHERE  RRC.FIELD_ID = RMF.FIELD_ID\n" +
                "       AND RRC.REPORT_ID = ?\n" +
                "ORDER  BY RRC.VIEW_LOCATION,\n" +
                "          RRC.CATEGORY_LEVEL";
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;
        sqlArgs.add(frm.getReportId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}