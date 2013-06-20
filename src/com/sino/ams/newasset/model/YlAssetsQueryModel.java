package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 预龄资产查询</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-7-2
 */
public class YlAssetsQueryModel extends AMSSQLProducer {
    private EtsFaAssetsDTO dto = null;

    public YlAssetsQueryModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        this.dto = (EtsFaAssetsDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EFA.ORGANIZATION_ID,\n" +
                "       EFA.TAG_NUMBER,\n" +
                "       EFA.ASSET_NUMBER,\n" +
                "       EFA.ASSETS_DESCRIPTION,\n" +
                "       EFA.MODEL_NUMBER,\n" +
                "       EFA.LIFE_IN_YEARS,\n" +
                "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                "       EFA.ASSIGNED_TO_NAME,\n" +
                "       EFA.ASSIGNED_TO_NUMBER,\n" +
                "       AMS_PUB_PKG.GET_ORGNIZATION_NAME(EFA.ORGANIZATION_ID) ORGANIZATION_NAME,\n" +
                "       EFA.FA_CATEGORY_CODE\n" +
                "  FROM ETS_FA_ASSETS EFA\n" +
                " WHERE (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                "   AND NOT EXISTS\n" +
                " (SELECT 1 FROM ETS_ITEM_MATCH EIM WHERE EIM.ASSET_ID = EFA.ASSET_ID)\n" +
                "   AND NOT EXISTS (SELECT 1\n" +
                "          FROM ETS_ITEM_MATCH_ASSIST_MIS EIMAM\n" +
                "         WHERE EIMAM.ASSET_ID = EFA.ASSET_ID)\n" +
                "   AND EFA.ORGANIZATION_ID = ISNULL(?, EFA.ORGANIZATION_ID)\n" +
                "   AND EFA.ASSET_NUMBER LIKE dbo.NVL(?, EFA.ASSET_NUMBER)" +
                "   AND EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER)" +
                "   AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)" +
                "   AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE ?)" +
                "   AND EFA.FA_CATEGORY_CODE LIKE dbo.NVL(?, EFA.FA_CATEGORY_CODE)";
        sqlArgs.add(dto.getOrganizationId());
        sqlArgs.add(dto.getAssetNumber());
        sqlArgs.add(dto.getTagNumber());
        sqlArgs.add(dto.getAssetsDescription());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getModelNumber());
        sqlArgs.add(dto.getFaCategoryCode());
        try {
            if (dto.getEndDate().toString().equals("")) {
                sqlStr += "   AND GETDATE() - EFA.DATE_PLACED_IN_SERVICE >=\n" +
                        "       EFA.LIFE_IN_YEARS * 365";
            } else {
                sqlStr += "   AND TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') - EFA.DATE_PLACED_IN_SERVICE >=\n" +
                        "       EFA.LIFE_IN_YEARS * 365";
                sqlArgs.add(dto.getSQLEndDate().toString());
            }
        } catch (CalendarException e) {
            throw new SQLModelException(e);
        }
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
