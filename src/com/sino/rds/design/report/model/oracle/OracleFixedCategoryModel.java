package com.sino.rds.design.report.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.report.model.FixedCategoryModel;
import com.sino.rds.share.form.FixedCategoryFrm;

import java.util.ArrayList;
import java.util.List;

public class OracleFixedCategoryModel extends DefaultRDSBaseSQLModelImpl implements FixedCategoryModel {

    public OracleFixedCategoryModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_FIXED_CATEGORY\n" +
                "  (DEFINE_ID,\n" +
                "   CATEGORY_ID,\n" +
                "   DEFINE_VALUE,\n" +
                "   SORT_NUMBER,\n" +
                "   PARENT_ID,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY)\n" +
                "VALUES\n" +
                "  (?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   SYSDATE,\n" +
                "   ?)";
        List<String> sqlArgs = new ArrayList<String>();
        FixedCategoryFrm frm = (FixedCategoryFrm) dtoParameter;

        sqlArgs.add(frm.getDefineId());
        sqlArgs.add(frm.getCategoryId());
        sqlArgs.add(frm.getDefineValue());
        sqlArgs.add(frm.getSortNumber());
        sqlArgs.add(frm.getParentId());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_FIXED_CATEGORY RFC\n" +
                "SET    RFC.DEFINE_VALUE     = ?,\n" +
                "       RFC.SORT_NUMBER      = ?,\n" +
                "       RFC.PARENT_ID      = ?,\n" +
                "       RFC.LAST_UPDATE_DATE = SYSDATE,\n" +
                "       RFC.LAST_UPDATE_BY   = ?\n" +
                "WHERE  RFC.DEFINE_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        FixedCategoryFrm frm = (FixedCategoryFrm) dtoParameter;

        sqlArgs.add(frm.getDefineValue());
        sqlArgs.add(frm.getSortNumber());
        sqlArgs.add(frm.getParentId());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getDefineId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        if(foreignKey.equals("categoryId")){
            sqlModel = getDataByCategoryIdModel();
        }
        return sqlModel;
    }

    private SQLModel getDataByCategoryIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RFC.DEFINE_ID,\n" +
                "       RFC.CATEGORY_ID,\n" +
                "       RRV.FIELD_NAME,\n" +
                "       RRV.FIELD_DESC,\n" +
                "       RRC.VIEW_LOCATION,\n" +
                "       GET_RDS_FLEX_VALUE('VIEW_LOCATION', RRC.VIEW_LOCATION) VIEW_LOCATION_NAME,\n" +
                "       RRC.CATEGORY_LEVEL,\n" +
                "       RFC.DEFINE_VALUE,\n" +
                "       RFC.SORT_NUMBER,\n" +
                "       RFC.PARENT_ID,\n" +
                "       RFP.DEFINE_VALUE PARENT_VALUE\n" +
                "FROM   RDS_FIXED_CATEGORY  RFC,\n" +
                "       RDS_REPORT_CATEGORY RRC,\n" +
                "       RDS_REPORT_VIEW     RRV,\n" +
                "       RDS_FIXED_CATEGORY rfp\n" +
                "WHERE  RFC.CATEGORY_ID = RRC.CATEGORY_ID\n" +
                "       AND RRC.FIELD_ID = RRV.FIELD_ID\n" +
                "       AND RFC.PARENT_ID = RFP.DEFINE_ID(+)\n" +
                "       AND RRC.CATEGORY_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        FixedCategoryFrm frm = (FixedCategoryFrm) dtoParameter;

        sqlArgs.add(frm.getCategoryId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getParentValuesModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RFC.DEFINE_ID,\n" +
                "       RFC.DEFINE_VALUE\n" +
                "FROM   RDS_FIXED_CATEGORY  RFC,\n" +
                "       RDS_REPORT_CATEGORY RRC,\n" +
                "       RDS_REPORT_CATEGORY RRP\n" +
                "WHERE  RFC.CATEGORY_ID = RRP.CATEGORY_ID\n" +
                "       AND RRP.CATEGORY_LEVEL 1 = RRC.CATEGORY_LEVEL\n" +
                "       AND RRP.VIEW_LOCATION = RRC.VIEW_LOCATION\n" +
                "       AND RRP.REPORT_ID = RRC.REPORT_ID\n" +
                "       AND RRC.CATEGORY_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        FixedCategoryFrm frm = (FixedCategoryFrm) dtoParameter;

        sqlArgs.add(frm.getCategoryId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}