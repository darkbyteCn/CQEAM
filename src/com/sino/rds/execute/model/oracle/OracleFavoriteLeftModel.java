package com.sino.rds.execute.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.appbase.model.RDSBaseSQLModel;

import java.util.ArrayList;
import java.util.List;


public class OracleFavoriteLeftModel extends DefaultRDSBaseSQLModelImpl implements RDSBaseSQLModel {

    public OracleFavoriteLeftModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel(){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RFH.HEADER_ID,\n" +
                "       RFH.FAVORITE_NAME,\n" +
                "       RFH.CREATION_DATE\n" +
                "FROM   RDS_FAVORITE_HEADER RFH\n" +
                "WHERE RFH.CREATED_BY = ?\n" +
                "ORDER BY RFH.CREATION_DATE";

        List<String> sqlArgs = new ArrayList<String>();
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }
}
