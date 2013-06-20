package com.sino.rds.design.report.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.report.model.ReportViewProcessModel;
import com.sino.rds.foundation.db.structure.Field;
import com.sino.rds.share.form.ModelFieldFrm;
import com.sino.rds.share.form.ReportViewFrm;

import java.util.ArrayList;
import java.util.List;

public class OracleReportViewProcessModel extends DefaultRDSBaseSQLModelImpl implements ReportViewProcessModel {

    public OracleReportViewProcessModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_REPORT_VIEW\n" +
                "  (VIEW_ID,\n" +
                "   REPORT_ID,\n" +
                "   FIELD_ID,\n" +
                "   FIELD_WIDTH,\n" +
                "   FIELD_ALIGN,\n" +
                "   DATA_PATTERN,\n" +
                "   DOT_NUMBER,\n" +
                "   DATA_SRC_TYPE,\n" +
                "   COMPUTE_EXPRESSION,\n" +
                "   FIELD_NAME,\n" +
                "   FIELD_DESC,\n" +
                "   SORT_NUMBER,\n" +
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
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   SYSDATE,\n" +
                "   ?)";
        List<String> sqlArgs = new ArrayList<String>();
        ReportViewFrm frm = (ReportViewFrm) dtoParameter;

        sqlArgs.add(frm.getViewId());
        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getFieldId());
        sqlArgs.add(frm.getFieldWidth());
        sqlArgs.add(frm.getFieldAlign());
        sqlArgs.add(frm.getDataPattern());
        sqlArgs.add(frm.getDotNumber());
        sqlArgs.add(frm.getDataSrcType());
        sqlArgs.add(frm.getComputeExpression());
        sqlArgs.add(frm.getFieldName());
        sqlArgs.add(frm.getFieldDesc());
        sqlArgs.add(frm.getSortNumber());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_REPORT_VIEW RRV\n" +
                "SET    RRV.REPORT_ID          = ?,\n" +
                "       RRV.FIELD_WIDTH        = ?,\n" +
                "       RRV.FIELD_ALIGN        = ?,\n" +
                "       RRV.DATA_PATTERN       = ?,\n" +
                "       RRV.DOT_NUMBER       = ?,\n" +
                "       RRV.DATA_SRC_TYPE      = ?,\n" +
                "       RRV.COMPUTE_EXPRESSION = ?,\n" +
                "       RRV.FIELD_NAME         = ?,\n" +
                "       RRV.FIELD_DESC         = ?,\n" +
                "       RRV.SORT_NUMBER        = ?,\n" +
                "       RRV.ENABLED            = ?,\n" +
                "       RRV.LAST_UPDATE_DATE   = SYSDATE,\n" +
                "       RRV.LAST_UPDATE_BY     = ?\n" +
                "WHERE  RRV.VIEW_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportViewFrm frm = (ReportViewFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getFieldWidth());
        sqlArgs.add(frm.getFieldAlign());
        sqlArgs.add(frm.getDataPattern());
        sqlArgs.add(frm.getDotNumber());
        sqlArgs.add(frm.getDataSrcType());
        sqlArgs.add(frm.getComputeExpression());
        sqlArgs.add(frm.getFieldName());
        sqlArgs.add(frm.getFieldDesc());
        sqlArgs.add(frm.getSortNumber());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getViewId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getReportViewDeleteByIdsModel() {
        SQLModel sqlModel = new SQLModel();
        ReportViewFrm frm = (ReportViewFrm) dtoParameter;
        String sqlStr = "DELETE FROM RDS_REPORT_VIEW RRV WHERE RRV.VIEW_ID IN ("+frm.getViewIds()+")";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRV.VIEW_ID,\n" +
                "       RRV.REPORT_ID,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME,\n" +
                "       RRV.FIELD_ID,\n" +
                "       RRV.FIELD_WIDTH,\n" +
                "       RRV.FIELD_ALIGN,\n" +
                "       RRV.DATA_PATTERN,\n" +
                "       RRV.DATA_SRC_TYPE,\n" +
                "       RRV.COMPUTE_EXPRESSION,\n" +
                "       RRV.FIELD_NAME,\n" +
                "       RRV.FIELD_DESC,\n" +
                "       RRV.SORT_NUMBER,\n" +
                "       RRV.DOT_NUMBER,\n" +
                "       RRV.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RRV.ENABLED) ENABLED_NAME,\n" +
                "       RRV.CREATION_DATE,\n" +
                "       RRV.CREATED_BY,\n" +
                "       RRV.LAST_UPDATE_DATE,\n" +
                "       RRV.LAST_UPDATE_BY\n" +
                "FROM   RDS_REPORT_VIEW   RRV,\n" +
                "       RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RRV.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RRD.VIEW_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportViewFrm frm = (ReportViewFrm) dtoParameter;

        sqlArgs.add(frm.getViewId());

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
        String sqlStr = "SELECT RRV.VIEW_ID,\n" +
                "       RRV.REPORT_ID,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME,\n" +
                "       RRV.FIELD_ID,\n" +
                "       RRV.FIELD_WIDTH,\n" +
                "       RRV.FIELD_ALIGN,\n" +
                "       RRV.DATA_PATTERN,\n" +
                "       RRV.DATA_SRC_TYPE,\n" +
                "       RRV.COMPUTE_EXPRESSION,\n" +
                "       RRV.FIELD_NAME,\n" +
                "       RRV.FIELD_DESC,\n" +
                "       RRV.SORT_NUMBER,\n" +
                "       RRV.DOT_NUMBER,\n" +
                "       RRV.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RRV.ENABLED) ENABLED_NAME,\n" +
                "       RRV.CREATION_DATE,\n" +
                "       RRV.CREATED_BY,\n" +
                "       RRV.LAST_UPDATE_DATE,\n" +
                "       RRV.LAST_UPDATE_BY,\n" +
                "       RMF.FIELD_TYPE\n" +
                "FROM   RDS_REPORT_VIEW   RRV,\n" +
                "       RDS_MODEL_FIELD   RMF,\n" +
                "       RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RRV.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RRV.FIELD_ID = RMF.FIELD_ID\n" +
                "       AND RRD.REPORT_ID = ?\n" +
                "ORDER BY RRV.SORT_NUMBER";
        List<String> sqlArgs = new ArrayList<String>();
        ReportViewFrm frm = (ReportViewFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getAvailableFieldsByReportIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RMF.FIELD_ID,\n" +
                "       RMF.FIELD_NAME||'('||RMF.FIELD_DESC||')' FIELD_NAME\n" +
                "FROM   RDS_MODEL_FIELD   RMF,\n" +
                "       RDS_DATA_MODEL    RDM,\n" +
                "       RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RMF.MODEL_ID = RDM.MODEL_ID\n" +
                "       AND RDM.MODEL_ID = RRD.MODEL_ID\n" +
                "       AND RRD.REPORT_ID = ?\n" +
                "       AND NOT EXISTS (SELECT NULL\n" +
                "        FROM   RDS_REPORT_VIEW RRV\n" +
                "        WHERE  RMF.FIELD_ID = RRV.FIELD_ID\n" +
                "               AND RRV.REPORT_ID = ?)\n" +
                "ORDER  BY RMF.FIELD_ID";
        List<String> sqlArgs = new ArrayList<String>();
        ReportViewFrm frm = (ReportViewFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getAlreadyFieldsByReportIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRV.FIELD_ID,\n" +
                "       RRV.FIELD_NAME||'('||RRV.FIELD_DESC||')' FIELD_NAME\n" +
                "FROM   RDS_REPORT_VIEW RRV\n" +
                "WHERE  RRV.REPORT_ID = ?\n" +
                "ORDER  BY RRV.SORT_NUMBER";
        List<String> sqlArgs = new ArrayList<String>();
        ReportViewFrm frm = (ReportViewFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getSelectedFieldsModel() {
        SQLModel sqlModel = new SQLModel();
        ReportViewFrm frm = (ReportViewFrm) dtoParameter;
        String sqlStr = "SELECT NULL VIEW_ID,\n" +
                "       RMF.FIELD_ID,\n" +
                "       (CASE\n" +
                "         WHEN RMF.FIELD_TYPE = 'NUMBER' THEN\n" +
                "          'RIGHT'\n" +
                "         WHEN RMF.FIELD_TYPE = 'DATE' THEN\n" +
                "          'CENTER'\n" +
                "         ELSE\n" +
                "          'LEFT'\n" +
                "       END) FIELD_ALIGN,\n" +
                "       NULL DATA_PATTERN,\n" +
                "       RMF.FIELD_NAME,\n" +
                "       RMF.FIELD_DESC,\n" +
                "       NULL SORT_NUMBER,\n" +
                "       'Y' ENABLED,\n" +
                "       'ÓÐÐ§' ENABLED_NAME,\n" +
                "       RMF.FIELD_TYPE\n" +
                "FROM   RDS_DATA_MODEL    RDM,\n" +
                "       RDS_REPORT_DEFINE RRD,\n" +
                "       RDS_MODEL_FIELD   RMF\n" +
                "WHERE  RDM.MODEL_ID = RRD.MODEL_ID\n" +
                "       AND RDM.MODEL_ID = RMF.MODEL_ID\n" +
                "       AND RDM.ENABLED = 'Y'\n" +
                "       AND RRD.ENABLED = 'Y'\n" +
                "       AND RMF.ENABLED = 'Y'\n" +
                "       AND NOT EXISTS (SELECT NULL\n" +
                "        FROM   RDS_REPORT_VIEW RRV\n" +
                "        WHERE  RRV.REPORT_ID = RRD.REPORT_ID\n" +
                "               AND RRV.FIELD_ID = RMF.FIELD_ID\n" +
                "               AND RRD.REPORT_ID = ?)\n" +
                "       AND RMF.FIELD_ID IN (" + frm.getFieldIds() + ")\n" +
                "       AND RRD.REPORT_ID = ?\n" +
                "ORDER  BY RMF.FIELD_ID";
        List<String> sqlArgs = new ArrayList<String>();

        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getHasFieldByReportId() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT 1\n" +
                "FROM   RDS_REPORT_VIEW   RRV,\n" +
                "       RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RRV.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RRD.REPORT_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportViewFrm frm = (ReportViewFrm) dtoParameter;
        sqlArgs.add(frm.getReportId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getHasExpressionFieldByReportId() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT 1\n" +
                "FROM   RDS_REPORT_VIEW RRV\n" +
                "WHERE  RRV.COMPUTE_EXPRESSION IS NOT NULL\n" +
                "       AND RRV.REPORT_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportViewFrm frm = (ReportViewFrm) dtoParameter;
        sqlArgs.add(frm.getReportId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getFieldDeleteModelByFields(List<Field> fields){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE FROM RDS_REPORT_VIEW RRV\n" +
                "WHERE  RRV.FIELD_NAME NOT IN ({FIELDS})\n" +
                "       AND EXISTS (SELECT NULL\n" +
                "        FROM   RDS_REPORT_DEFINE RRD\n" +
                "        WHERE  RRV.REPORT_ID = RRD.REPORT_ID\n" +
                "               AND RRD.MODEL_ID = ?)";
        String fieldNames = "";
        int fieldCount = fields.size();
        for(int i = 0; i < fieldCount; i++){
            Field field = fields.get(i);
            fieldNames += field.getName();
            if( i < fieldCount - 1){
                fieldNames += "', '";
            }
        }
        fieldNames = "'" + fieldNames + "'";
        sqlStr = StrUtil.replaceStr(sqlStr, "{FIELDS}", fieldNames);
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;
        sqlArgs.add(frm.getModelId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}