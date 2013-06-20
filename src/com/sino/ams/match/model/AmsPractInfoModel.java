package com.sino.ams.match.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.match.dto.AmsAssetsInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2008-7-21
 * Time: 15:16:42
 * To change this template use File | Settings | File Templates.
 */

public class AmsPractInfoModel extends BaseSQLProducer {
    private AmsAssetsInfoDTO dtoParameter = null;
    private SfUserDTO sfUser = null;


    public AmsPractInfoModel(SfUserDTO userAccount, DTO dtoParameter) {
        super(userAccount, dtoParameter);
        sfUser = userAccount;
        this.dtoParameter = (AmsAssetsInfoDTO) dtoParameter;
    }

    public SQLModel getPageQueryModel() throws SQLModelException {
        SQLModel sqlModel = new SQLModel();
        AmsAssetsInfoDTO assetsInfo = (AmsAssetsInfoDTO)dtoParameter;
        try {
            List sqlArgs = new ArrayList();
            String sqlStr ="SELECT \n" +
                    " EII.BARCODE,\n" +
                    " EFA.TAG_NUMBER,\n" +
                    " EFA.ASSET_NUMBER,\n" +
                    " ESI.ITEM_NAME,\n" +
                    " ESI.ITEM_SPEC,\n" +
                    " EFA.MODEL_NUMBER,\n" +
                    " EFA.ASSETS_DESCRIPTION,\n" +
                    " EO.WORKORDER_OBJECT_CODE,\n" +
                    " EFA.ASSETS_LOCATION_CODE,\n" +
                    " EO.WORKORDER_OBJECT_LOCATION,\n" +
                    " EFA.ASSETS_LOCATION\n" +
                    " FROM\n" +
                    " ETS_ITEM_MATCH     EIM,\n" +
                    " ETS_FA_ASSETS      EFA,\n" +
                    " ETS_ITEM_INFO      EII,\n" +
                    " ETS_SYSTEM_ITEM    ESI,\n" +
                    " AMS_OBJECT_ADDRESS AOA,\n" +
                    " ETS_OBJECT         EO\n" +
                    " WHERE\n" +
                    " EIM.ASSET_ID=EFA.ASSET_ID\n" +
                    " AND\n" +
                    " EIM.SYSTEMID=EII.SYSTEMID\n" +
                    " AND\n" +
                    " EII.ITEM_CODE=ESI.ITEM_CODE\n" +
                    " AND\n" +
                    " EII.ADDRESS_ID=AOA.ADDRESS_ID\n" +
                    " AND\n" +
                    " AOA.OBJECT_NO=EO.WORKORDER_OBJECT_NO "+
                    " AND (EO.OBJECT_CATEGORY < = 70 OR EO.OBJECT_CATEGORY = 80)" +   //备件仓库不参加匹配
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSET_NUMBER LIKE ?)"+
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.TAG_NUMBER LIKE ?)"+
                    " AND ( " + SyBaseSQLUtil.isNull() + "  OR EFA.ASSETS_DESCRIPTION LIKE ?)"+
                    " AND EO.ORGANIZATION_ID=?" +
                    " ORDER BY EII.BARCODE";

            sqlArgs.add(assetsInfo.getAssetsNumber());
            sqlArgs.add(assetsInfo.getAssetsNumber());
            sqlArgs.add(assetsInfo.getTagNumber());
            sqlArgs.add(assetsInfo.getTagNumber());
            sqlArgs.add(assetsInfo.getAssetsDescription());
            sqlArgs.add(assetsInfo.getAssetsDescription());
            sqlArgs.add(sfUser.getOrganizationId());
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sqlModel;
    }
}
