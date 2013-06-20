package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.dto.EtsFaAssetsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2008-6-17
 * Time: 10:38:56
 * To change this template use File | Settings | File Templates.
 */
public class MisEquipmentScreenModel extends BaseSQLProducer {
    private SQLModel sqlModel = null;
    private EtsFaAssetsDTO dtoParameter = null;
    private SfUserDTO sfUser = null;


    public MisEquipmentScreenModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        this.dtoParameter = (EtsFaAssetsDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        EtsFaAssetsDTO assetsdto = (EtsFaAssetsDTO) dtoParameter;
        String matchType = assetsdto.getKey();
        String sqlStr = "";
        if (matchType.equals(WebAttrConstant.MATCH_MODE_0THER_RET) || matchType.equals(WebAttrConstant.SCREEN_EXPROT)) {
            sqlStr = " SELECT" +
                             " EFA.ASSET_ID," +
                             " EFA.TAG_NUMBER BARCODE," +
                             " EFA.ASSETS_DESCRIPTION ITEM_NAME," +
                             " EFA.MODEL_NUMBER ITEM_SPEC,\n" +
                             " EFA.ASSETS_LOCATION WORKORDER_OBJECT_NAME\n" +
                     " FROM ETS_ITEM_MATCH_ASSIST_MIS EIMAM," +
                          " ETS_FA_ASSETS EFA\n" +
                     " WHERE EIMAM.ASSET_ID = EFA.ASSET_ID" +
                        " AND  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.TAG_NUMBER LIKE ? )\n" +
                        " AND  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_DESCRIPTION LIKE ?)\n" +
                        " AND  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE ? )\n" +
                        " AND  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_LOCATION LIKE ?)   \n" +
                        " AND   EFA.ORGANIZATION_ID = ?";
        } else {
            sqlStr = " SELECT" +
                             " EFA.ASSET_ID," +
                             " EFA.TAG_NUMBER BARCODE," +
                             " EFA.ASSETS_DESCRIPTION ITEM_NAME," +
                             " EFA.MODEL_NUMBER ITEM_SPEC," +
                             " EFA.ASSETS_LOCATION WORKORDER_OBJECT_NAME\n" +
                     " FROM ETS_FA_ASSETS EFA\n" +
                     " WHERE NOT EXISTS" +
                       " (SELECT 1 FROM ETS_ITEM_MATCH_ASSIST_MIS EIMA WHERE EIMA.ASSET_ID=EFA.ASSET_ID)\n" +
                       " AND  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.TAG_NUMBER LIKE ? )\n" +
                       " AND  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_DESCRIPTION LIKE ?)\n" +
                       " AND  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.MODEL_NUMBER LIKE ? )\n" +
                       " AND  ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_LOCATION LIKE ?)   \n" +
                       " AND   EFA.ORGANIZATION_ID = ?";
        }
            sqlStr += " ORDER BY EFA.TAG_NUMBER";
            sqlArgs.add(assetsdto.getBarcode());
            sqlArgs.add(assetsdto.getBarcode());
            sqlArgs.add(assetsdto.getItemName());
            sqlArgs.add(assetsdto.getItemName());
            sqlArgs.add(assetsdto.getItemSpec());
            sqlArgs.add(assetsdto.getItemSpec());
            sqlArgs.add(assetsdto.getAssetsLocation());
            sqlArgs.add(assetsdto.getAssetsLocation());
            sqlArgs.add(sfUser.getOrganizationId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel insertAssistMis(EtsFaAssetsDTO assistdto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = " INSERT INTO" +
                " ETS_ITEM_MATCH_ASSIST_MIS" +
                " (ASSET_ID," +
                " CREATION_DATE," +
                " CREATED_BY," +
                " LAST_UPDATE_DATE," +
                " LAST_UPDATE_BY)" +
                " VALUES (?, GETDATE(), ?, GETDATE() ,?)";
        sqlArgs.add(assistdto.getAssetId());
        sqlArgs.add(sfUser.getUserId());
        sqlArgs.add(sfUser.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    public SQLModel deleteAssistMis(EtsFaAssetsDTO assistdto) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "DELETE FROM ETS_ITEM_MATCH_ASSIST_MIS WHERE ASSET_ID = ?";
        sqlArgs.add(assistdto.getAssetId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
