package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 * <p>Title: AmsAssetsChkLogModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsChkLogModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */
public class ContentPriviModel extends AMSSQLProducer {

    /**
     * 功能：资产盘点记录 AMS_ASSETS_CHK_LOG 数据库SQL构造层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsPriviDTO 本次操作的数据
     */
    public ContentPriviModel(SfUserDTO userAccount,
                             AmsAssetsPriviDTO dtoParameter) {
        super(userAccount, dtoParameter);
    }

    /**
     * 功能：框架自动生成资产盘点记录 AMS_ASSETS_CHK_LOG数据插入SQLModel，请根据实际需要修改。
     * @return SQLModel 返回数据插入用SQLModel
     */
    public SQLModel getDataCreateModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
        String sqlStr = "INSERT INTO "
                        + " AMS_ASSETS_PRIVI("
                        + " PRIVI_ID,"
                        + " USER_ID,"
                        + " ROLE_ID,"
                        + " COMPANY_CODE,"
                        + " FA_CATEGORY_CODE,"
                        + " CREATION_DATE,"
                        + " PROVINCE_CODE,"
                        + " CREATED_BY"
                        + ") VALUES ("
                        +
                        "  NEWID() , ?, ?, ?, ?, GETDATE(), ?,?)";
        sqlArgs.add(dto.getUserId());
        sqlArgs.add(dto.getRoleId());
        sqlArgs.add(userAccount.getCompanyCode());
        sqlArgs.add(dto.getFaCategoryCode());
        sqlArgs.add(servletConfig.getProvinceCode());
        sqlArgs.add(userAccount.getUserId());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }


    /**
     * 功能：返回数据删除的SQLModel
     * <B>默认为空实现。可由具体应用选择是否需要实现。继承类需要用dtoParameter构造SQLModel。</B>
     * @return SQLModel
     */
    public SQLModel getDataDeleteModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        AmsAssetsPriviDTO dto = (AmsAssetsPriviDTO) dtoParameter;
        String sqlStr = "DELETE FROM "
                        + " AMS_ASSETS_PRIVI AAP"
                        + " WHERE"
                        + " AAP.USER_ID = ?"
                        + " AND  " + SyBaseSQLUtil.isNotNull("AAP.FA_CATEGORY_CODE") + " "
                        + " AND AAP.ROLE_ID = ("
                        + " SELECT"
                        + " ROLE_ID"
                        + " FROM"
                        + " SF_ROLE SR"
                        + " WHERE"
                        + " SR.ROLE_NAME = ?)";
        sqlArgs.add(dto.getUserId());
        sqlArgs.add(servletConfig.getMtlAssetsMgr());
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}
