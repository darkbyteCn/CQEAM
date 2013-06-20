package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsCJYCDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-4-9
 * Time: 17:17:57
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsChangeZJQueryModel  extends AMSSQLProducer {
    private AmsAssetsCJYCDTO dto = null;


    public AmsAssetsChangeZJQueryModel(SfUserDTO userAccount, AmsAssetsCJYCDTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto = (AmsAssetsCJYCDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT /*+rule*/\n" +
                " AMS_PUB_PKG.GET_ORGNIZATION_NAME(EFA.ORGANIZATION_ID) ORGNIZATION_NAME,\n" +
                " EFA.ASSET_NUMBER,\n" +
                " EFA.FA_CATEGORY1,\n" +
                " EFA.ASSETS_DESCRIPTION,\n" +
                " EFA.MODEL_NUMBER,\n" +
                " AACY.NEW_YEARS,\n" +
                " ROUND(AACA.CHANGE_AMOUNT, 2) CHANGE_AMOUNT,\n" +
                " EFA.DATE_PLACED_IN_SERVICE,\n" +
                " EFA.SEGMENT1,\n" +
                " AACA.SEGMENT2\n" +
                "FROM   AMS_ASSETS_CHANGE_AMOUNT AACA,\n" +
                "       AMS_ASSETS_CHANGE_YEARS  AACY,\n" +
                "       ETS_FA_ASSETS            EFA\n" +
                "WHERE  EFA.ASSET_ID= AACA.ASSET_ID\n" +
                "       AND EFA.ORGANIZATION_ID = AACA.ORGANIZATION_ID\n" +
                "       AND AACY.ORGANIZATION_ID = AACA.ORGANIZATION_ID\n" +
                "       AND (AACA.SEGMENT2 || '.' || AACA.SEGMENT3) = AACY.FA_CATEGORY2\n" +
                "       AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)\n" +
                "       AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE ?)\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.SEGMENT1 = ? )\n" +
                "       AND ( " + SyBaseSQLUtil.isNull() + "  OR AACA.SEGMENT2 LIKE ?)";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getAssetsDescription());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getSegment1());
        sqlArgs.add(dto.getSegment1());
        sqlArgs.add(dto.getSegment2());
        sqlArgs.add(dto.getSegment2());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
