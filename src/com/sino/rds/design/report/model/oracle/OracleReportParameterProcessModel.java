package com.sino.rds.design.report.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.report.model.ReportParameterProcessModel;
import com.sino.rds.foundation.db.structure.Field;
import com.sino.rds.share.form.ModelFieldFrm;
import com.sino.rds.share.form.ReportParameterFrm;

import java.util.ArrayList;
import java.util.List;

public class OracleReportParameterProcessModel extends DefaultRDSBaseSQLModelImpl implements ReportParameterProcessModel {

    public OracleReportParameterProcessModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_REPORT_PARAMETER\n" +
                "  (PARAMETER_ID,\n" +
                "   FIELD_ID,\n" +
                "   FIELD_NAME,\n" +
                "   FIELD_DESC,\n" +
                "   FIELD_WIDTH,\n" +
                "   FIELD_ALIGN,\n" +
                "   INPUT_TYPE,\n" +
                "   LOV_ID,\n" +
                "   LOOK_UP_ID,\n" +
                "   PARAMETER_URL,\n" +
                "   CALENDAR_TYPE,\n" +
                "   REPORT_ID,\n" +
                "   SORT_NUMBER,\n" +
                "   NULL_ABLE,\n" +
                "   DEFAULT_VALUE,\n" +
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
                "   ?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   SYSDATE,\n" +
                "   ?)";
        List<String> sqlArgs = new ArrayList<String>();
        ReportParameterFrm frm = (ReportParameterFrm) dtoParameter;

        sqlArgs.add(frm.getParameterId());
        sqlArgs.add(frm.getFieldId());
        sqlArgs.add(frm.getFieldName());
        sqlArgs.add(frm.getFieldDesc());
        sqlArgs.add(frm.getFieldWidth());
        sqlArgs.add(frm.getFieldAlign());
        sqlArgs.add(frm.getInputType());
        sqlArgs.add(frm.getLovId());
        sqlArgs.add(frm.getLookUpId());
        sqlArgs.add(frm.getParameterUrl());
        sqlArgs.add(frm.getCalendarType());
        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getSortNumber());
        sqlArgs.add(frm.getNullAble());
        sqlArgs.add(frm.getDefaultValue());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_REPORT_PARAMETER RRP\n" +
                "SET    \n" +
                "       RRP.FIELD_NAME        = ?,\n" +
                "       RRP.FIELD_DESC        = ?,\n" +
                "       RRP.FIELD_WIDTH       = ?,\n" +
                "       RRP.FIELD_ALIGN       = ?,\n" +
                "       RRP.INPUT_TYPE        = ?,\n" +
                "       RRP.LOV_ID            = ?,\n" +
                "       RRP.LOOK_UP_ID        = ?,\n" +
                "       RRP.PARAMETER_URL     = ?,\n" +
                "       RRP.CALENDAR_TYPE     = ?,\n" +
                "       RRP.REPORT_ID         = ?,\n" +
                "       RRP.SORT_NUMBER       = ?,\n" +
                "       RRP.NULL_ABLE         = ?,\n" +
                "       RRP.DEFAULT_VALUE     = ?,\n" +
                "       RRP.ENABLED           = ?,\n" +
                "       RRP.LAST_UPDATE_DATE  = SYSDATE,\n" +
                "       RRP.LAST_UPDATE_BY    = ?\n" +
                "WHERE  RRP.PARAMETER_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportParameterFrm frm = (ReportParameterFrm) dtoParameter;

        sqlArgs.add(frm.getFieldName());
        sqlArgs.add(frm.getFieldDesc());
        sqlArgs.add(frm.getFieldWidth());
        sqlArgs.add(frm.getFieldAlign());
        sqlArgs.add(frm.getInputType());
        sqlArgs.add(frm.getLovId());
        sqlArgs.add(frm.getLookUpId());
        sqlArgs.add(frm.getParameterUrl());
        sqlArgs.add(frm.getCalendarType());
        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getSortNumber());
        sqlArgs.add(frm.getNullAble());
        sqlArgs.add(frm.getDefaultValue());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getParameterId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getReportParameterDeleteByIdsModel() {
        SQLModel sqlModel = new SQLModel();
        ReportParameterFrm frm = (ReportParameterFrm) dtoParameter;
        String sqlStr = "DELETE FROM RDS_REPORT_PARAMETER RRP WHERE RRP.PARAMETER_ID IN (" + frm.getParameterIds() + ")";
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRP.PARAMETER_ID,\n" +
                "       RRP.FIELD_ID,\n" +
                "       RRP.FIELD_NAME,\n" +
                "       RRP.FIELD_DESC,\n" +
                "       RRP.INPUT_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('INPUT_TYPE', RRP.INPUT_TYPE) INPUT_TYPE_NAME,\n" +
                "       RRP.LOV_ID,\n" +
                "       RLD.LOV_NAME,\n" +
                "       RRP.LOOK_UP_ID,\n" +
                "       RLD2.LOOK_UP_NAME,\n" +
                "       RRP.PARAMETER_URL,\n" +
                "       RRP.FIELD_WIDTH,\n" +
                "       RRP.FIELD_ALIGN,\n" +
                "       RRP.NULL_ABLE,\n" +
                "       GET_RDS_FLEX_VALUE('NULL_ABLE', RRP.NULL_ABLE) NULL_ABLE_NAME,\n" +
                "       RRP.CALENDAR_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('CALENDAR_TYPE', RRP.CALENDAR_TYPE) CALENDAR_TYPE_NAME,\n" +
                "       RRP.DEFAULT_VALUE,\n" +
                "       RRP.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RRP.NULL_ABLE) ENABLED_NAME,\n" +
                "FROM   RDS_REPORT_PARAMETER RRP,\n" +
                "       RDS_LOV_DEFINE       RLD,\n" +
                "       RDS_LOOKUP_DEFINE    RLD2\n" +
                "WHERE  RRP.LOV_ID = RLD.LOV_ID(+)\n" +
                "       AND RRP.LOOK_UP_ID = RLD2.LOOK_UP_ID(+)\n" +
                "       AND RRP.PARAMETER_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportParameterFrm frm = (ReportParameterFrm) dtoParameter;

        sqlArgs.add(frm.getParameterId());

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
        String sqlStr = "SELECT RRP.PARAMETER_ID,\n" +
                "       RRP.FIELD_ID,\n" +
                "       RRP.FIELD_NAME,\n" +
                "       RRP.FIELD_DESC,\n" +
                "       RRP.INPUT_TYPE,\n" +
                "       RRP.LOV_ID,\n" +
                "       RRP.LOOK_UP_ID,\n" +
                "       RRP.PARAMETER_URL,\n" +
                "       RRP.CALENDAR_TYPE,\n" +
                "       RRP.FIELD_WIDTH,\n" +
                "       RRP.FIELD_ALIGN,\n" +
                "       RRP.NULL_ABLE,\n" +
                "       RRP.ENABLED,\n" +
                "       RRP.DEFAULT_VALUE,\n" +
                "       RMF.FIELD_TYPE\n" +
                "FROM   RDS_REPORT_PARAMETER RRP,\n" +
                "       RDS_MODEL_FIELD      RMF\n" +
                "WHERE  RRP.FIELD_ID = RMF.FIELD_ID\n" +
                "       AND RRP.REPORT_ID = ?\n" +
                "ORDER  BY RRP.SORT_NUMBER";
        List<String> sqlArgs = new ArrayList<String>();
        ReportParameterFrm frm = (ReportParameterFrm) dtoParameter;

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
                "        FROM   RDS_REPORT_PARAMETER RRP\n" +
                "        WHERE  RMF.FIELD_ID = RRP.FIELD_ID\n" +
                "               AND RRP.REPORT_ID = ?)\n" +
                "ORDER  BY RMF.FIELD_ID";
        List<String> sqlArgs = new ArrayList<String>();
        ReportParameterFrm frm = (ReportParameterFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());
        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getAlreadyFieldsByReportIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RRP.FIELD_ID,\n" +
                "       RRP.FIELD_NAME||'('||RRP.FIELD_DESC||')' FIELD_NAME\n" +
                "FROM   RDS_REPORT_PARAMETER RRP\n" +
                "WHERE  RRP.REPORT_ID = ?\n" +
                "ORDER BY RRP.SORT_NUMBER";
        List<String> sqlArgs = new ArrayList<String>();
        ReportParameterFrm frm = (ReportParameterFrm) dtoParameter;

        sqlArgs.add(frm.getReportId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getSelectedFieldsModel() {
        SQLModel sqlModel = new SQLModel();
        ReportParameterFrm frm = (ReportParameterFrm) dtoParameter;
        String sqlStr = "SELECT RRP.PARAMETER_ID,\n" +
                "       RRP.FIELD_ID,\n" +
                "       RRP.FIELD_NAME,\n" +
                "       RRP.FIELD_DESC,\n" +
                "       RRP.INPUT_TYPE,\n" +
                "       RRP.LOV_ID,\n" +
                "       RRP.LOOK_UP_ID,\n" +
                "       RRP.PARAMETER_URL,\n" +
                "       RRP.CALENDAR_TYPE,\n" +
                "       RRP.FIELD_WIDTH,\n" +
                "       RRP.FIELD_ALIGN,\n" +
                "       RRP.NULL_ABLE,\n" +
                "       RRP.DEFAULT_VALUE,\n" +
                "       RRP.ENABLED\n" +
                "FROM   RDS_REPORT_PARAMETER RRP\n" +
                "WHERE  RRP.FIELD_ID IN (" + frm.getFieldIds() + ")\n" +
                "       AND RRP.REPORT_ID = ?\n" +
                "UNION ALL\n" +
                "SELECT NULL PARAMETER_ID,\n" +
                "       RMF.FIELD_ID,\n" +
                "       RMF.FIELD_NAME,\n" +
                "       RMF.FIELD_DESC,\n" +
                "       NULL INPUT_TYPE,\n" +
                "       NULL LOV_ID,\n" +
                "       NULL LOOK_UP_ID,\n" +
                "       NULL PARAMETER_URL,\n" +
                "       NULL CALENDAR_TYPE,\n" +
                "       NULL FIELD_WIDTH,\n" +
                "       (CASE\n" +
                "         WHEN RMF.FIELD_TYPE = 'NUMBER' THEN\n" +
                "          'RIGHT'\n" +
                "         WHEN RMF.FIELD_TYPE = 'DATE' THEN\n" +
                "          'CENTER'\n" +
                "         ELSE\n" +
                "          'LEFT'\n" +
                "       END) FIELD_ALIGN,\n" +
                "       NULL NULL_ABLE,\n" +
                "       NULL DEFAULT_VALUE,\n" +
                "       'Y' ENABLED\n" +
                "FROM   RDS_DATA_MODEL    RDM,\n" +
                "       RDS_MODEL_FIELD   RMF,\n" +
                "       RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RDM.MODEL_ID = RRD.MODEL_ID\n" +
                "       AND RDM.MODEL_ID = RMF.MODEL_ID\n" +
                "       AND NOT EXISTS (SELECT NULL\n" +
                "        FROM   RDS_REPORT_PARAMETER RRP\n" +
                "        WHERE  RRP.FIELD_ID = RMF.FIELD_ID\n" +
                "               AND RRP.REPORT_ID = RRD.REPORT_ID)\n" +
                "       AND RMF.FIELD_ID IN (" + frm.getFieldIds() + ")\n" +
                "       AND RRD.REPORT_ID = ?";
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
                "FROM   RDS_REPORT_PARAMETER   RRP,\n" +
                "       RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RRP.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RRD.REPORT_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ReportParameterFrm frm = (ReportParameterFrm) dtoParameter;
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