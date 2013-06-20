package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransConfigDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;


/**
 * <p>Title: AmsAssetsTransConfigModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsTransConfigModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class AmsAssetsTransConfigModel extends AMSSQLProducer {


    /**
     * 功能：固定资产调拨启用配置 AMS_ASSETS_TRANS_CONFIG 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransConfigDTO 本次操作的数据
     */
    public AmsAssetsTransConfigModel(SfUserDTO userAccount,
                                     AmsAssetsTransConfigDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：获取资产调拨配置信息的SQL
     * @return SQLModel
     */
    public SQLModel getTransConfigModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                        + " AATC.*,"
                        + " EFV.VALUE FA_CONTENT_NAME"
                        + " FROM"
                        + " AMS_ASSETS_TRANS_CONFIG AATC,"
                        + " ETS_FLEX_VALUES         EFV,"
                        + " ETS_FLEX_VALUE_SET      EFVS"
                        + " WHERE"
                        + " AATC.FA_CONTENT_CODE = EFV.CODE"
                        + " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
                        + " AND EFVS.CODE = ?"
                        + " AND AATC.ORGANIZATION_ID = ?"
                        + " AND AATC.ENABLED = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(AssetsDictConstant.FA_CONTENT_CODE);
        sqlArgs.add(userAccount.getOrganizationId());
        sqlArgs.add(AssetsDictConstant.STATUS_YES);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
