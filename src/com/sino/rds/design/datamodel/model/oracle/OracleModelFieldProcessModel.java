package com.sino.rds.design.datamodel.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.datamodel.model.ModelFieldProcessModel;
import com.sino.rds.foundation.db.structure.Field;
import com.sino.rds.share.form.ModelFieldFrm;

import java.util.ArrayList;
import java.util.List;

public class OracleModelFieldProcessModel extends DefaultRDSBaseSQLModelImpl implements ModelFieldProcessModel {

    public OracleModelFieldProcessModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_MODEL_FIELD\n" +
                "  (FIELD_ID,\n" +
                "   MODEL_ID,\n" +
                "   FIELD_NAME,\n" +
                "   FIELD_DESC,\n" +
                "   FIELD_TYPE,\n" +
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
                "   SYSDATE,\n" +
                "   ?)";
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;

        sqlArgs.add(frm.getFieldId());
        sqlArgs.add(frm.getModelId());
        sqlArgs.add(frm.getFieldName());
        sqlArgs.add(frm.getFieldDesc());
        sqlArgs.add(frm.getFieldType());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_MODEL_FIELD RMF\n" +
                "SET    RMF.FIELD_DESC       = ?,\n" +
                "       RMF.ENABLED          = ?,\n" +
                "       RMF.LAST_UPDATE_DATE = SYSDATE,\n" +
                "       RMF.LAST_UPDATE_BY   = ?\n" +
                "WHERE  RMF.FIELD_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;

        sqlArgs.add(frm.getFieldDesc());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getFieldId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RMF.FIELD_ID,\n" +
                "       RDM.MODEL_NAME,\n" +
                "       RMF.MODEL_ID,\n" +
                "       RMF.FIELD_NAME,\n" +
                "       RMF.FIELD_DESC,\n" +
                "       RDM.DATA_SRC_TYPE,\n" +
                "       GET_RDS_FLEX_VALUE('DATA_SRC_TYPE', RDM.DATA_SRC_TYPE) DATA_SRC_TYPE_NAME,\n" +
                "       RDM.OWNER,\n" +
                "       RMF.CREATION_DATE,\n" +
                "       RMF.ENABLED,\n" +
                "       GET_RDS_FLEX_VALUE('ENABLED_FLAG', RMF.ENABLED) ENABLED_NAME\n" +
                "FROM   RDS_MODEL_FIELD RMF,\n" +
                "       RDS_DATA_MODEL  RDM\n" +
                "WHERE  RMF.MODEL_ID = RDM.MODEL_ID\n" +
                "       AND RMF.FIELD_NAME LIKE NVL(?, RMF.FIELD_NAME)\n" +
                "       AND RMF.ENABLED = NVL(?, RMF.ENABLED)\n" +
                "       AND RDM.MODEL_ID = NVL(?, RDM.MODEL_ID)";
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;

        sqlArgs.add(frm.getFieldName());
        sqlArgs.add(frm.getEnabled());
        sqlArgs.add(frm.getModelId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RMF.FIELD_ID,\n" +
                "       RMF.MODEL_ID,\n" +
                "       RMF.FIELD_NAME,\n" +
                "       RMF.FIELD_DESC,\n" +
                "       RMF.FIELD_TYPE,\n" +
                "       RDM.ENABLED,\n" +
                "       RMF.CREATION_DATE,\n" +
                "       RMF.CREATED_BY,\n" +
                "       RDM.MODEL_NAME,\n" +
                "       RDM.MODEL_SQL\n" +
                "FROM   RDS_MODEL_FIELD RMF,\n" +
                "       RDS_DATA_MODEL  RDM\n" +
                "WHERE  RMF.MODEL_ID = RDM.MODEL_ID\n" +
                "       AND RMF.FIELD_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;
        sqlArgs.add(frm.getFieldId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        if (foreignKey.equals("modelId")) {
            sqlModel = getDataByModelId();
        }
        return sqlModel;
    }

    public SQLModel getDataByModelId() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RMF.FIELD_ID,\n" +
                "       RMF.MODEL_ID,\n" +
                "       RMF.FIELD_NAME,\n" +
                "       RMF.FIELD_DESC,\n" +
                "       RMF.FIELD_TYPE,\n" +
                "       RMF.ENABLED,\n" +
                "       RMF.CREATION_DATE,\n" +
                "       RMF.CREATED_BY,\n" +
                "       RDM.MODEL_NAME,\n" +
                "       RDM.MODEL_SQL\n" +
                "FROM   RDS_MODEL_FIELD RMF,\n" +
                "       RDS_DATA_MODEL  RDM\n" +
                "WHERE  RMF.MODEL_ID = RDM.MODEL_ID\n" +
                "       AND RDM.MODEL_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;
        sqlArgs.add(frm.getModelId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getHasFieldByModelId() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT 1\n" +
                "FROM   RDS_MODEL_FIELD RMF,\n" +
                "       RDS_DATA_MODEL  RDM\n" +
                "WHERE  RMF.MODEL_ID = RDM.MODEL_ID\n" +
                "       AND RDM.MODEL_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;
        sqlArgs.add(frm.getModelId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        if (foreignKey.equals("modelId")) {
            sqlModel = getDataDeleteByModelId();
        }
        return sqlModel;
    }

    private SQLModel getDataDeleteByModelId() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE FROM RDS_MODEL_FIELD RMF WHERE RMF.MODEL_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        ModelFieldFrm frm = (ModelFieldFrm) dtoParameter;
        sqlArgs.add(frm.getModelId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getFieldDeleteModelByFields(List<Field> fields){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE FROM RDS_MODEL_FIELD RMF\n" +
                "WHERE  RMF.FIELD_NAME NOT IN ({FIELDS})\n" +
                "       AND RMF.MODEL_ID = ?";
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
