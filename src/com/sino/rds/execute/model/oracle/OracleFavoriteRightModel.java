package com.sino.rds.execute.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.appbase.model.RDSBaseSQLModel;
import com.sino.rds.share.form.FavoriteLineFrm;

import java.util.ArrayList;
import java.util.List;


public class OracleFavoriteRightModel extends DefaultRDSBaseSQLModelImpl implements RDSBaseSQLModel {

    public OracleFavoriteRightModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RFL.LINE_ID,\n" +
                "       RFL.HEADER_ID,\n" +
                "       RFL.REPORT_ID,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME\n" +
                "FROM   RDS_FAVORITE_LINE RFL,\n" +
                "       RDS_REPORT_DEFINE RRD,\n" +
                "       RDS_FAVORITE_HEADER RFH\n" +
                "WHERE  RFL.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RFH.HEADER_ID = RFL.HEADER_ID\n" +
                "       AND RFH.CREATED_BY = ?\n" +
                "       AND RFL.HEADER_ID = NVL(?, RFL.HEADER_ID)";
        List<String> sqlArgs = new ArrayList<String>();
        FavoriteLineFrm frm = (FavoriteLineFrm) dtoParameter;

        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getHeaderId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
