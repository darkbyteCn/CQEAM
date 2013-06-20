package com.sino.rds.execute.model.oracle;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.model.DefaultRDSBaseSQLModelImpl;
import com.sino.rds.execute.model.FavoriteHeaderModel;
import com.sino.rds.share.form.FavoriteHeaderFrm;

import java.util.ArrayList;
import java.util.List;


public class OracleFavoriteHeaderModel extends DefaultRDSBaseSQLModelImpl implements FavoriteHeaderModel {

    public OracleFavoriteHeaderModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "INSERT INTO RDS_FAVORITE_HEADER\n" +
                "  (HEADER_ID,\n" +
                "   FAVORITE_NAME,\n" +
                "   FAVORITE_REMARK,\n" +
                "   CREATION_DATE,\n" +
                "   CREATED_BY)\n" +
                "VALUES\n" +
                "  (?,\n" +
                "   ?,\n" +
                "   ?,\n" +
                "   SYSDATE,\n" +
                "   ?)";
        List<String> sqlArgs = new ArrayList<String>();
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) dtoParameter;

        sqlArgs.add(frm.getHeaderId());
        sqlArgs.add(frm.getFavoriteName());
        sqlArgs.add(frm.getFavoriteRemark());
        sqlArgs.add(getUserId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDataUpdateModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE RDS_FAVORITE_HEADER RFH\n" +
                "SET    RFH.FAVORITE_NAME    = ?,\n" +
                "       RFH.FAVORITE_REMARK  = ?,\n" +
                "       RFH.LAST_UPDATE_DATE = SYSDATE,\n" +
                "       RFH.LAST_UPDATE_BY   = ?\n" +
                "WHERE  RFH.HEADER_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) dtoParameter;

        sqlArgs.add(frm.getFavoriteName());
        sqlArgs.add(frm.getFavoriteRemark());
        sqlArgs.add(getUserId());
        sqlArgs.add(frm.getHeaderId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getDeleteByPrimaryKeyModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "DELETE FROM RDS_FAVORITE_HEADER RFH WHERE RFH.HEADER_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) dtoParameter;

        sqlArgs.add(frm.getHeaderId());

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPrimaryKeyDataModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RFH.HEADER_ID,\n" +
                "       RFH.FAVORITE_NAME,\n" +
                "       RFH.FAVORITE_REMARK,\n" +
                "       RFH.CREATION_DATE,\n" +
                "       RFH.CREATED_BY,\n" +
                "       RFH.LAST_UPDATE_DATE,\n" +
                "       RFH.LAST_UPDATE_BY\n" +
                "FROM   RDS_FAVORITE_HEADER RFH\n" +
                "WHERE  RFH.HEADER_ID = ?";
        List<String> sqlArgs = new ArrayList<String>();
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) dtoParameter;
        sqlArgs.add(frm.getHeaderId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RFH.HEADER_ID,\n" +
                "       RFH.FAVORITE_NAME,\n" +
                "       RFH.FAVORITE_REMARK,\n" +
                "       RFH.CREATION_DATE,\n" +
                "       RFH.CREATED_BY,\n" +
                "       RFH.LAST_UPDATE_DATE,\n" +
                "       RFH.LAST_UPDATE_BY\n" +
                "FROM   RDS_FAVORITE_HEADER RFH\n" +
                "WHERE  RFH.CREATED_BY = ?";
        List<String> sqlArgs = new ArrayList<String>();
        sqlArgs.add(getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    public SQLModel getMyFavoriteReportModel(){
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT RFH.HEADER_ID,\n" +
                "       RFH.FAVORITE_NAME\n" +
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
