package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: AmsAssetsTransHeaderModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsTransHeaderModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class OrderPrintPriviModel extends AMSSQLProducer {

    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
     */
    public OrderPrintPriviModel(SfUserDTO userAccount,
                                AmsAssetsTransHeaderDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：撤销单据
     * @return SQLModel
     */
    public SQLModel getHasPrintPriviModel() {
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "UPDATE"
                        + " AMS_ASSETS_TRANS_HEADER AATH"
                        + " SET"
                        + " AATH.TRANS_STATUS = ?,"
                        + " AATH.LAST_UPDATE_DATE = GETDATE(),"
                        + " AATH.LAST_UPDATE_BY = ?"
                        + " WHERE"
                        + " AATH.TRANS_ID = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(AssetsDictConstant.CANCELED);
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
