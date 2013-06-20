package com.sino.ams.system.assetcatalog.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.assetcatalog.dto.AssetCatalogDTO;
import com.sino.ams.system.assetcatalog.model.AssetCatalogModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

public class AssetCatalogDAO extends AMSBaseDAO {
    private SfUserDTO sfUser = null;
      public AssetCatalogDAO(SfUserDTO userAccount, AssetCatalogDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
    	AssetCatalogDTO dtoPara = (AssetCatalogDTO) dtoParameter;
        super.sqlProducer = new AssetCatalogModel((SfUserDTO) userAccount, dtoPara);
    }
}
