package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-17
 * Time: 22:22:51
 * To change this template use File | Settings | File Templates.
 */
public class AmsAssetsChangeModel extends AMSSQLProducer {
    public AmsAssetsChangeModel(BaseUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsFaAssetsDTO dto = (EtsFaAssetsDTO) dtoParameter;
        String sqlStr = "SELECT EFA.TAG_NUMBER,\n" +
                "         EFA.ASSET_ID," +
                "       EFA.ASSETS_DESCRIPTION,\n" +
                "       EFA.MODEL_NUMBER,\n" +
                "       EFA.ASSETS_LOCATION,\n" +
                "       EFA.DATE_PLACED_IN_SERVICE,\n" +
                "       EFA.LIFE_IN_YEARS,\n" +
                "       EFA.COST,\n" +
                "       EFA.ASSIGNED_TO_NAME,\n" +
                "       EFA.ASSET_NUMBER,\n" +
                "       EFA.BOOK_TYPE_CODE\n" +
                "  FROM ETS_FA_ASSETS EFA\n" +
                " WHERE (EFA.IS_RETIREMENTS = 0 OR EFA.IS_RETIREMENTS = 2)\n" +
                "   AND NOT EXISTS\n" +
                " (SELECT 1 FROM ETS_ITEM_MATCH EIM WHERE EIM.ASSET_ID = EFA.ASSET_ID)\n" +
                "   AND NOT EXISTS\n" +
                " (SELECT 1\n" +
                "          FROM ETS_ITEM_MATCH_ASSIST_MIS EIMAM\n" +
                "         WHERE EIMAM.ASSET_ID = EFA.ASSET_ID)\n" +
                "   AND ORGANIZATION_ID = ?\n" +
                "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR EFA.ASSETS_DESCRIPTION LIKE ? )\n" +
                "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR EFA.MODEL_NUMBER LIKE ? )\n" +
                "   AND (" + SyBaseSQLUtil.nullStringParam() + " OR EFA.TAG_NUMBER LIKE ? )\n" ;
                
//                "   AND EFA.ASSETS_DESCRIPTION LIKE dbo.NVL(?, EFA.ASSETS_DESCRIPTION)\n" +
//                "   AND EFA.MODEL_NUMBER LIKE dbo.NVL(?, EFA.MODEL_NUMBER)\n" +
//                "   AND EFA.TAG_NUMBER LIKE dbo.NVL(?, EFA.TAG_NUMBER)";
        sqlArgs.add(userAccount.getOrganizationId());
        
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getAssetsDescription() );
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getModelNumber() );
        SyBaseSQLUtil.nullStringParamArgs(sqlArgs, dto.getTagNumber() );
        
//        sqlArgs.add(dto.getAssetsDescription());
//        sqlArgs.add(dto.getModelNumber());
//        sqlArgs.add(dto.getTagNumber());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}

