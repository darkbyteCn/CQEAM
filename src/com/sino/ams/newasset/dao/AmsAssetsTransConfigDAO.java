package com.sino.ams.newasset.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransConfigDTO;
import com.sino.ams.newasset.model.AmsAssetsTransConfigModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.QueryException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsAssetsTransConfigDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsTransConfigDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class AmsAssetsTransConfigDAO extends AMSBaseDAO {

    /**
     * 功能：固定资产调拨启用配置 AMS_ASSETS_TRANS_CONFIG 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransConfigDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public AmsAssetsTransConfigDAO(SfUserDTO userAccount,
                                   AmsAssetsTransConfigDTO dtoParameter,
                                   Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsTransConfigDTO dtoPara = (AmsAssetsTransConfigDTO)
                                          dtoParameter;
        super.sqlProducer = new AmsAssetsTransConfigModel((SfUserDTO)
                userAccount, dtoPara);
    }

    /**
     * 功能：获取资产调拨配置信息。用户登录时即获取，放入session中
     * @return DTOSet
     * @throws QueryException
     */
    public DTOSet getTransConfigs() throws QueryException {
        AmsAssetsTransConfigModel modelProducer = (AmsAssetsTransConfigModel)
                                                  sqlProducer;
        SQLModel sqlModel = modelProducer.getTransConfigModel();
        SimpleQuery simp = new SimpleQuery(sqlModel, conn);
        simp.setDTOClassName(AmsAssetsTransConfigDTO.class.getName());
        simp.executeQuery();
        return simp.getDTOSet();
    }
}
