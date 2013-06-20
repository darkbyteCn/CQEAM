package com.sino.rds.design.report.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.report.model.ReportCategoryProcessModel;
import com.sino.rds.foundation.db.structure.Field;
import com.sino.rds.share.form.ReportCategoryFrm;

import java.util.ArrayList;
import java.util.List;

public class OracleReportCategoryProcessModel extends DefaultRDSBaseSQLModelImpl implements ReportCategoryProcessModel {

    public OracleReportCategoryProcessModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }


    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_REPORT_CATEGORY\n" +
                "  (CATEGORY_ID,\n" +
                "   REPORT_ID,\n" +
                "   FIELD_ID,\n" +
                "   FIELD_DESC,\n" +
                "   CATEGORY_LEVEL,\n" +
                "   VIEW_LOCATION,\n" +
                "   FIELD_ALIGN,\n" +
                "   FIELD_WIDTH,\n" +
                "   DISPLAY_FLAG,\n" +
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
                "   ?,\n" +
                "   ?,\n" +
                "   SYSDATE,\n" +
                "   ?)";
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;

        sqlArgs.add(frm.getCategoryId());
        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getFieldId());
        sqlArgs.add(frm.getFieldDesc());
        sqlArgs.add(frm.getCategoryLevel());
        sqlArgs.add(frm.getViewLocation());
        sqlArgs.add(frm.getFieldAlign());
        sqlArgs.add(frm.getFieldWidth());
        sqlArgs.add(frm.getDisplayFlag());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_REPORT_CATEGORY RRC\n" +
                "SET    RRC.REPORT_ID         = ?,\n" +
                "       RRC.FIELD_DESC          = ?,\n" +
                "       RRC.CATEGORY_LEVEL    = ?,\n" +
                "       RRC.VIEW_LOCATION     = ?,\n" +
                "       RRC.FIELD_ALIGN       = ?,\n" +
                "       RRC.FIELD_WIDTH       = ?,\n" +
                "       RRC.DISPLAY_FLAG      = ?,\n" +
                "       RRC.ENABLED           = ?,\n" +
                "       RRC.LAST_UPDATE_DATE  = SYSDATE,\n" +
                "       RRC.LAST_UPDATE_BY    = ?\n" +
                "WHERE  RRC.CATEGORY_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getFieldDesc());
        sqlArgs.add(frm.getCategoryLevel());
        sqlArgs.add(frm.getViewLocation());
        sqlArgs.add(frm.getFieldAlign());
        sqlArgs.add(frm.getFieldWidth());
        sqlArgs.add(frm.getDisplayFlag());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getCategoryId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getReportCategoryDeleteByIdsModel() {
        SQLModel sqlModel = new SQLModel();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;
        String sqlStr = "DELETE FROM RDS_REPORT_CATEGORY RRC WHERE RRC.CATEGORY_ID IN (" + frm.getCategoryIds() + ")";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRC.CATEGORY_ID,\n" +
                "       RRC.REPORT_ID,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME,\n" +
                "       RRC.FIELD_ID,\n" +
                "       RRC.FIELD_DESC,\n" +
                "       RRC.FIELD_WIDTH,\n" +
                "       RRC.FIELD_ALIGN,\n" +
                "       RMF.FIELD_NAME,\n" +
                "       RMF.FIELD_DESC,\n" +
                "       RRC.DISPLAY_FLAG,\n" +
                "       RRC.VIEW_LOCATION,\n" +
                "       RRC.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RRC.ENABLED) ENABLED_NAME,\n" +
                "       RRC.CREATION_DATE,\n" +
                "       RRC.CREATED_BY,\n" +
                "       RRC.LAST_UPDATE_DATE,\n" +
                "       RRC.LAST_UPDATE_BY\n" +
                "FROM   RDS_REPORT_CATEGORY RRC,\n" +
                "       RDS_REPORT_DEFINE   RRD,\n" +
                "       RDS_MODEL_FIELD     RMF\n" +
                "WHERE  RRC.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RRC.FIELD_ID = RMF.FIELD_ID\n" +
                "       AND RRC.CATEGORY_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;

        sqlArgs.add(frm.getCategoryId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        if (foreignKey.equals("reportId")) {
            sqlModel = getDataByReportIdModel();
        }
        return sqlModel;
    }

    private SQLModel getDataByReportIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRC.CATEGORY_ID,\n" +
                "       RRC.REPORT_ID,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME,\n" +
                "       RRC.FIELD_ID,\n" +
                "       RRC.FIELD_DESC,\n" +
                "       RRC.FIELD_WIDTH,\n" +
                "       RRC.FIELD_ALIGN,\n" +
                "       RMF.FIELD_NAME,\n" +
                "       RRC.DISPLAY_FLAG,\n" +
                "       RRC.CATEGORY_LEVEL,\n" +
                "       RRC.ENABLED,\n" +
                "       RRC.VIEW_LOCATION,\n" +
                "       GET_RDS_FLEX_VALUE('VIEW_LOCATION', RRC.VIEW_LOCATION) VIEW_LOCATION_NAME,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RRC.ENABLED) ENABLED_NAME,\n" +
                "       RRC.CREATION_DATE,\n" +
                "       RRC.CREATED_BY,\n" +
                "       RRC.LAST_UPDATE_DATE,\n" +
                "       RRC.LAST_UPDATE_BY,\n" +
                "       RMF.FIELD_TYPE\n" +
                "FROM   RDS_REPORT_CATEGORY RRC,\n" +
                "       RDS_MODEL_FIELD     RMF,\n" +
                "       RDS_REPORT_DEFINE   RRD\n" +
                "WHERE  RRC.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RRC.FIELD_ID = RMF.FIELD_ID\n" +
                "       AND RRD.REPORT_ID = ?\n" +
                "ORDER BY RRC.VIEW_LOCATION,\n" +
                "       RRC.CATEGORY_LEVEL";
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getAvailableFieldsByReportIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRV.FIELD_ID,\n" +
                "       RRV.FIELD_NAME||'('||RRV.FIELD_DESC||')' FIELD_NAME\n" +
                "FROM   RDS_REPORT_VIEW   RRV,\n" +
                "       RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RRV.COMPUTE_EXPRESSION IS NULL\n" +
                "       AND RRV.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RRD.REPORT_TYPE <> '1'\n" +
                "       AND RRD.REPORT_ID = ?\n" +
                "       AND NOT EXISTS (SELECT NULL\n" +
                "        FROM   RDS_REPORT_CATEGORY RRC\n" +
                "        WHERE  RRV.FIELD_ID = RRC.FIELD_ID\n" +
                "               AND RRV.REPORT_ID = RRC.REPORT_ID)\n" +
                " ORDER BY RRV.SORT_NUMBER\n";
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getAboveFieldsByReportIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRV.FIELD_ID,\n" +
                "       RRV.FIELD_NAME || '(' || RRV.FIELD_DESC || ')' FIELD_NAME\n" +
                "FROM   RDS_REPORT_VIEW     RRV,\n" +
                "       RDS_REPORT_CATEGORY RRC\n" +
                "WHERE  RRV.REPORT_ID = RRC.REPORT_ID\n" +
                "       AND RRV.FIELD_ID = RRC.FIELD_ID\n" +
                "       AND RRC.VIEW_LOCATION = 'ABOVE'\n" +
                "       AND RRC.REPORT_ID = ?\n" +
                "ORDER  BY RRC.CATEGORY_LEVEL";
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getLeftFieldsByReportIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRV.FIELD_ID,\n" +
                "       RRV.FIELD_NAME || '(' || RRV.FIELD_DESC || ')' FIELD_NAME\n" +
                "FROM   RDS_REPORT_VIEW     RRV,\n" +
                "       RDS_REPORT_CATEGORY RRC\n" +
                "WHERE  RRV.REPORT_ID = RRC.REPORT_ID\n" +
                "       AND RRV.FIELD_ID = RRC.FIELD_ID\n" +
                "       AND RRC.VIEW_LOCATION = 'LEFT'\n" +
                "       AND RRC.REPORT_ID = ?\n" +
                "ORDER  BY RRC.CATEGORY_LEVEL";
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getSelectedFieldsModel() {
        SQLModel sqlModel = new SQLModel();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;
        String sqlStr = "SELECT NULL CATEGORY_ID,\n" +
                "       RRV.FIELD_ID,\n" +
                "       NULL FIELD_WIDTH,\n" +
                "       (CASE\n" +
                "         WHEN RMF.FIELD_TYPE = 'NUMBER' THEN\n" +
                "          'RIGHT'\n" +
                "         WHEN RMF.FIELD_TYPE = 'DATE' THEN\n" +
                "          'CENTER'\n" +
                "         ELSE\n" +
                "          'LEFT'\n" +
                "       END) FIELD_ALIGN,\n" +
                "       RRV.FIELD_NAME,\n" +
                "       RRV.FIELD_DESC,\n" +
                "       'Y' DISPLAY_FLAG,\n" +
                "       NULL CATEGORY_LEVEL,\n" +
                "       'Y' ENABLED,\n" +
                "       NULL CREATION_DATE,\n" +
                "       NULL LAST_UPDATE_DATE,\n" +
                "       'ÓÐÐ§' ENABLED_NAME\n" +
                "FROM   RDS_MODEL_FIELD   RMF,\n" +
                "       RDS_REPORT_VIEW   RRV,\n" +
                "       RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RMF.FIELD_ID = RRV.FIELD_ID\n" +
                "       AND RRV.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RRD.ENABLED = 'Y'\n" +
                "       AND RMF.ENABLED = 'Y'\n" +
                "       AND RRV.COMPUTE_EXPRESSION IS NULL\n" +
                "       AND NOT EXISTS (SELECT NULL\n" +
                "        FROM   RDS_REPORT_CATEGORY RRC\n" +
                "        WHERE  RRC.REPORT_ID = RRD.REPORT_ID\n" +
                "               AND RRC.FIELD_ID = RRV.FIELD_ID)\n" +
                "       AND RRV.FIELD_ID IN (" + frm.getFieldIds() + ")\n" +
                "       AND RRD.REPORT_ID = ?\n" +
                " ORDER BY RRV.SORT_NUMBER";
        List<String> sqlArgs = new ArrayList<String>();

        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        if (foreignKey.equals("reportId")) {
            sqlModel = getDeleteByReportIdModel();
        }
        return sqlModel;
    }

    private SQLModel getDeleteByReportIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE FROM RDS_REPORT_CATEGORY RRC WHERE RRC.REPORT_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;
        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDeleteByViewIdsModel() {
        SQLModel sqlModel = new SQLModel();
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;
        String sqlStr = "DELETE FROM RDS_REPORT_CATEGORY RRC\n" +
                "WHERE  RRC.REPORT_ID = ?\n" +
                "       AND EXISTS (SELECT NULL\n" +
                "        FROM   RDS_REPORT_VIEW RRV\n" +
                "        WHERE  RRC.REPORT_ID = RRV.REPORT_ID\n" +
                "               AND RRC.FIELD_ID = RRV.FIELD_ID\n" +
                "               AND RRV.VIEW_ID IN (" + frm.getViewIds() + "))";
        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getHasFieldByReportId() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT 1\n" +
                "FROM   RDS_REPORT_CATEGORY   RRC,\n" +
                "       RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RRC.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RRD.REPORT_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;
        sqlArgs.add(frm.getReportId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getFieldDeleteModelByFields(List<Field> fields) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE FROM RDS_REPORT_CATEGORY RRC\n" +
                "WHERE  RRC.FIELD_ID NOT IN (SELECT RRV.FIELD_ID\n" +
                "                            FROM   RDS_REPORT_VIEW   RRV,\n" +
                "                                   RDS_REPORT_DEFINE RRD\n" +
                "                            WHERE  RRV.REPORT_ID = RRD.REPORT_ID\n" +
                "                                   AND RRV.FIELD_NAME IN ({FIELDS})\n" +
                "                                   AND RRD.MODEL_ID = ?)\n" +
                "       AND EXISTS (SELECT NULL\n" +
                "        FROM   RDS_REPORT_DEFINE RRD\n" +
                "        WHERE  RRC.REPORT_ID = RRD.REPORT_ID\n" +
                "               AND RRD.MODEL_ID = ?)";
        String fieldNames = "";
        int fieldCount = fields.size();
        for (int i = 0; i < fieldCount; i++) {
            Field field = fields.get(i);
            fieldNames += field.getName();
            if (i < fieldCount - 1) {
                fieldNames += "', '";
            }
        }
        fieldNames = "'" + fieldNames + "'";
        sqlStr = StrUtil.replaceStr(sqlStr, "{FIELDS}", fieldNames);
        List<String> sqlArgs = new ArrayList<String>();
        ReportCategoryFrm frm = (ReportCategoryFrm) dtoParameter;
        sqlArgs.add(frm.getModelId());
        sqlArgs.add(frm.getModelId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}