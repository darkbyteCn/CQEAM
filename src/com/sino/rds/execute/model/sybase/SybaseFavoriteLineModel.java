package com.sino.rds.execute.model.sybase;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.util.ArrUtil;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.execute.model.FavoriteLineModel;
import com.sino.rds.share.form.FavoriteLineFrm;

import java.util.ArrayList;
import java.util.List;

public class SybaseFavoriteLineModel extends DefaultRDSBaseSQLModelImpl implements FavoriteLineModel {

    public SybaseFavoriteLineModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getFavoriteLineCreateModel(String[] reportIdArr) {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_FAVORITE_LINE\n" +
                "  (LINE_ID,\n" +
                "   HEADER_ID,\n" +
                "   REPORT_ID)\n" +
                "  (SELECT NEWID(),\n" +
                "          ?,\n" +
                "          RRD.REPORT_ID\n" +
                "   FROM   RDS_REPORT_DEFINE RRD\n" +
                "   WHERE  RRD.REPORT_ID IN ({REPORT_ID})\n" +
                "          AND NOT EXISTS (SELECT NULL\n" +
                "           FROM   RDS_FAVORITE_LINE RFL\n" +
                "           WHERE  RFL.HEADER_ID = ?\n" +
                "                  AND RFL.REPORT_ID = RRD.REPORT_ID))";
        List<String> sqlArgs = new ArrayList<String>();
        FavoriteLineFrm frm = (FavoriteLineFrm) dtoParameter;

        sqlArgs.add(frm.getHeaderId());
        sqlArgs.add(frm.getHeaderId());
        String reportIds = ArrUtil.arrToSqlStr(reportIdArr);
        sqlStr = StrUtil.replaceStr(sqlStr, "{REPORT_ID}", reportIds);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        if (foreignKey.equals("headerId")) {
            sqlModel = getDeleteByHeaderIdModel();
        }
        return sqlModel;
    }

    private SQLModel getDeleteByHeaderIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE FROM RDS_FAVORITE_LINE WHERE HEADER_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        FavoriteLineFrm frm = (FavoriteLineFrm) dtoParameter;

        sqlArgs.add(frm.getHeaderId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataByForeignKeyModel(String foreignKey) {
        SQLModel sqlModel = null;
        if (foreignKey.equals("headerId")) {
            sqlModel = getDataByHeaderIdModel();
        }
        return sqlModel;
    }

    private SQLModel getDataByHeaderIdModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RFL.LINE_ID,\n" +
                "       RFL.HEADER_ID,\n" +
                "       RFL.REPORT_ID,\n" +
                "       RRD.REPORT_CODE,\n" +
                "       RRD.REPORT_NAME\n" +
                "FROM   RDS_FAVORITE_LINE RFL,\n" +
                "       RDS_REPORT_DEFINE RRD\n" +
                "WHERE  RFL.REPORT_ID = RRD.REPORT_ID\n" +
                "       AND RFL.HEADER_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        FavoriteLineFrm frm = (FavoriteLineFrm) dtoParameter;

        sqlArgs.add(frm.getHeaderId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDeleteByPrimaryKeyModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE FROM RDS_FAVORITE_LINE WHERE LINE_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        FavoriteLineFrm frm = (FavoriteLineFrm) dtoParameter;

        sqlArgs.add(frm.getLineId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
