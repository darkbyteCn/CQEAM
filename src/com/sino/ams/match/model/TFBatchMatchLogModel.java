package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.framework.dto.BaseUserDTO;

public class TFBatchMatchLogModel extends AMSSQLProducer {
    public TFBatchMatchLogModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT * FROM ETS_FA_MATCH_LOG EFML\n" +
                "   WHERE EFML.ORGID = ?" +
                "     AND EFML.CANCELED_BY IS NULL" +
                "      ORDER BY EFML.CREATION_DATE DESC ";
        sqlArgs.add(userAccount.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
