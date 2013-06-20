package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-4-7
 * Time: 17:19:03
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsChangeZJYearsModel extends AMSSQLProducer {
    private AmsAssetsCJYCDTO dto = null;


    public AmsAssetsChangeZJYearsModel(SfUserDTO userAccount, AmsAssetsCJYCDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto = (AmsAssetsCJYCDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT AMS_PUB_PKG.GET_ORGNIZATION_NAME(AACY.ORGANIZATION_ID) ORGNIZATION_NAME,\n" +
                "       MFCV.FA_CAT_NAME_2,\n" +
                "       AACY.STANDARDS_YEARS,\n" +
                "       AACY.NEW_YEARS,\n" +
                "       AACY.ORGANIZATION_ID,\n" +
                "       AACY.FA_CATEGORY2\n" +
                "FROM   AMS_ASSETS_CHANGE_YEARS AACY,\n" +
                "       AMS_FA_CATEGORY_V       MFCV\n" +
                "WHERE  AACY.FA_CATEGORY2 =\n" +
                "       (MFCV.FA_CAT_CODE_2 || '.' || MFCV.FA_CAT_CODE_3)\n" +
                "       AND AACY.ORGANIZATION_ID =ISNULL(?, AACY.ORGANIZATION_ID)\n" +
                "       AND AACY.FA_CATEGORY2 LIKE dbo.NVL(?, AACY.FA_CATEGORY2)";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getFaCatName2());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel UpdateYears(){
           SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ASSETS_CHANGE_YEARS AACY\n" +
                "SET    AACY.NEW_YEARS = ?\n" +
                "WHERE  AACY.ORGANIZATION_ID = ISNULL(?, AACY.ORGANIZATION_ID)\n" +
                "       AND AACY.FA_CATEGORY2 LIKE dbo.NVL(?, AACY.FA_CATEGORY2)";
        sqlArgs.add(dto.getNewYears());
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getFaCatName2());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
      public SQLModel updateYear(String faCategory2,String year) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "UPDATE AMS_ASSETS_CHANGE_YEARS AACY\n" +
                "SET    AACY.NEW_YEARS = ?\n" +
                "WHERE  AACY.FA_CATEGORY2 = ?";
        sqlArgs.add(year);
        sqlArgs.add(faCategory2);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
        public SQLModel insertAmount() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ASSETS_CHANGE_AMOUNT\n" +
                "  (ASSET_ID,\n" +
                "   DATE_PLACED_IN_SERVICE,\n" +
                "   SEGMENT2,\n" +
                "   SEGMENT3,\n" +
                "   ORGANIZATION_ID,\n" +
                "   CREATE_DATE,\n" +
                "   DEPRN_COST,\n" +
                "   SCRAP_VALUE)\n" +
                "  (SELECT EFA.ASSET_ID,\n" +
                "          EFA.DATE_PLACED_IN_SERVICE,\n" +
                "          EFA.SEGMENT2,\n" +
                "          EFA.SEGMENT3,\n" +
                "          EFA.ORGANIZATION_ID,\n" +
                "          GETDATE(),\n" +
                "          EFA.DEPRN_COST,\n" +
                "          EFA.SCRAP_VALUE\n" +
                "   FROM   ETS_FA_ASSETS EFA\n" +
                "   WHERE  EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)";
        sqlArgs.add(dto.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    public SQLModel deleteAmount() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM AMS_ASSETS_CHANGE_AMOUNT AACA\n" +
                "WHERE  AACA.ORGANIZATION_ID = ISNULL(?, AACA.ORGANIZATION_ID)";
        sqlArgs.add(dto.getOrganizationId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
      public SQLModel insertAmount1(String faCategory2) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "INSERT INTO AMS_ASSETS_CHANGE_AMOUNT\n" +
                "  (ASSET_ID,\n" +
                "   DATE_PLACED_IN_SERVICE,\n" +
                "   SEGMENT2,\n" +
                "   SEGMENT3,\n" +
                "   ORGANIZATION_ID,\n" +
                "   CREATE_DATE,\n" +
                "   DEPRN_COST,\n" +
                "   SCRAP_VALUE)\n" +
                "  (SELECT EFA.ASSET_ID,\n" +
                "          EFA.DATE_PLACED_IN_SERVICE,\n" +
                "          EFA.SEGMENT2,\n" +
                "          EFA.SEGMENT3,\n" +
                "          EFA.ORGANIZATION_ID,\n" +
                "          GETDATE(),\n" +
                "          EFA.DEPRN_COST,\n" +
                "          EFA.SCRAP_VALUE\n" +
                "   FROM   ETS_FA_ASSETS EFA\n" +
                "   WHERE  EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)\n" +
                "          AND EFA.SEGMENT2 || '.' || EFA.SEGMENT3 = ?)";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(faCategory2);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
     public SQLModel deleteAmount1(String faCategory2) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM AMS_ASSETS_CHANGE_AMOUNT AACA\n" +
                "WHERE  AACA.ORGANIZATION_ID = ISNULL(?, AACA.ORGANIZATION_ID)\n" +
                "       AND AACA.SEGMENT2 || '.' || AACA.SEGMENT3 = ?";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(faCategory2);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
