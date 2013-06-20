package com.sino.rds.design.datamodel.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.datamodel.model.SystemTableSearchModel;
import com.sino.rds.share.form.SystemAllTableFrm;

import java.util.ArrayList;
import java.util.List;

public class OracleSystemTableSearchModel extends DefaultRDSBaseSQLModelImpl implements SystemTableSearchModel {

    public OracleSystemTableSearchModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getUserTableOptionsModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT\n" +
                "       DO.OBJECT_NAME TABLE_NAME,\n" +
                "       DO.OBJECT_NAME||'('||(CASE\n" +
                "         WHEN DO.OBJECT_TYPE = 'TABLE' THEN\n" +
                "          NVL(DTC.COMMENTS, '未指定表描述')\n" +
                "         ELSE\n" +
                "          '视图无描述'\n" +
                "       END)||')' OPTION_COMMENTS\n" +
                "FROM   DBA_OBJECTS      DO,\n" +
                "       DBA_TAB_COMMENTS DTC\n" +
                "WHERE  DO.OWNER = DTC.OWNER\n" +
                "       AND DO.OBJECT_NAME = DTC.TABLE_NAME\n" +
                "       AND DO.OWNER = SYS_CONTEXT('USERENV', 'CURRENT_USER')\n" +
                "       AND DO.OBJECT_TYPE = ?\n" +
                "ORDER  BY DO.OWNER,\n" +
                "          DO.OBJECT_NAME";
        List<String> sqlArgs = new ArrayList<String>();
        SystemAllTableFrm frm = (SystemAllTableFrm) dtoParameter;
        sqlArgs.add(frm.getTableType());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}