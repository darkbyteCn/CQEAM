package com.sino.ams.newasset.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsFaCategoryVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsFaCategoryVModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsFaCategoryVModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsFaCategoryVModel extends BaseSQLProducer {

    /**
     * 功能：AMS_FA_CATEGORY_V 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsFaCategoryVDTO 本次操作的数据
     */
    public AmsFaCategoryVModel(SfUserDTO userAccount,
                               AmsFaCategoryVDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成AMS_FA_CATEGORY_V页面翻页查询SQLModel，请根据实际需要修改。
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getPageQueryModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsFaCategoryVDTO dto = (AmsFaCategoryVDTO) dtoParameter;
        String mtlPrivi = dto.getMtlPrivi();
        String tmpVariable = "";
        if (mtlPrivi.equals(AssetsWebAttributes.MTL_PRIVI_N)) {
            tmpVariable = " NOT";
        }
        String sqlStr = "SELECT "
                        + " AFCV.FA_CAT_CODE_1,"
                        + " AFCV.FA_CAT_NAME_1,"
                        + " AFCV.FA_CAT_CODE_2,"
                        + " AFCV.FA_CAT_NAME_2,"
                        + " AFCV.FA_CAT_CODE_3,"
                        + " AFCV.FA_CAT_NAME_3,"
                        + " AFCV.FA_CATEGORY_CODE,"
                        + " AFCV.FA_CATEGORY_NAME"
                        + " FROM"
                        + " AMS_FA_CATEGORY_V AFCV"
                        + " WHERE"
                        +
                " CHARINDEX(FA_CATEGORY_NAME, dbo.NVL(?, FA_CATEGORY_NAME)) > 0"
                        + " AND"
                        + tmpVariable
                        + " EXISTS("
                        + " SELECT"
                        + " NULL"
                        + " FROM"
                        + " AMS_ASSETS_PRIVI  AAP"
                        + " WHERE"
                        + " AFCV.FA_CATEGORY_CODE = AAP.FA_CATEGORY_CODE"
                        + " AND AAP.USER_ID = ?)"
                        + " ORDER BY"
                        + " AFCV.FA_CAT_CODE_1,"
                        + " AFCV.FA_CAT_CODE_2,"
                        + " AFCV.FA_CAT_CODE_3";
        sqlArgs.add(dto.getFaCategoryName());
        sqlArgs.add(dto.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
