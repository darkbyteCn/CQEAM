package com.sino.rds.design.datamodel.model.sybase;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.datamodel.model.DataModelCheckModel;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.DataModelFrm;

public class SybaseDataModelCheckModel extends DefaultRDSBaseSQLModelImpl implements DataModelCheckModel {

    public SybaseDataModelCheckModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：获取SQL查询语创建视图的SQLModel对象。
     *
     * @return 获取SQL查询语创建视图的SQLModel对象
     */
    public SQLModel getModelCheckModel() {
        SQLModel sqlModel = new SQLModel();
        DataModelFrm frm = (DataModelFrm) dtoParameter;
        String dataSrcType = frm.getDataSrcType();
        String sqlStr = "";
        if(!dataSrcType.equals(RDSDictionaryList.DATA_SRC_TYPE_SQL)){
            sqlStr = "SELECT TOP 1 * FROM ( SELECT * FROM " + frm.getModelSql() + ") T";
        } else {
            sqlStr = "SELECT TOP 1 * FROM (" + frm.getModelSql() + ") T";
        }
        sqlModel.setSqlStr(sqlStr);
        return sqlModel;
    }
}
