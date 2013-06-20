package com.sino.rds.design.datamodel.model.sybase;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.design.datamodel.model.SystemTableSearchModel;
import com.sino.rds.share.constant.RDSDictionaryList;
import com.sino.rds.share.form.SystemAllTableFrm;

import java.util.ArrayList;
import java.util.List;

public class SybaseSystemTableSearchModel extends DefaultRDSBaseSQLModelImpl implements SystemTableSearchModel {

    public SybaseSystemTableSearchModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getUserTableOptionsModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT SO.name AS TABLE_NAME,\n" +
                "       SO.name AS OPTION_COMMENTS\n" +
                "FROM   sysobjects SO\n" +
                "WHERE  SO.type = ?\n" +
                "ORDER BY  SO.name\n";
        List<String> sqlArgs = new ArrayList<String>();
        SystemAllTableFrm frm = (SystemAllTableFrm) dtoParameter;
        String tableType = frm.getTableType();
        if(tableType.equals(RDSDictionaryList.DATA_SRC_TYPE_TABLE)){
            sqlArgs.add("U");
        } else {
            sqlArgs.add("V");
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}

