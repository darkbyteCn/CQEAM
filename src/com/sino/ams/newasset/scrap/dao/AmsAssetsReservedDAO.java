package com.sino.ams.newasset.scrap.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsReservedDTO;
import com.sino.ams.newasset.model.AmsAssetsReservedModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsAssetsReservedDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsReservedDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */


public class AmsAssetsReservedDAO extends AMSBaseDAO {

    /**
     * 功能：资产事务保留表 AMS_ASSETS_RESERVED 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsReservedDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public AmsAssetsReservedDAO(SfUserDTO userAccount,
                                AmsAssetsReservedDTO dtoParameter,
                                Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsReservedDTO dtoPara = (AmsAssetsReservedDTO) dtoParameter;
        super.sqlProducer = new AmsAssetsReservedModel((SfUserDTO) userAccount,
                dtoPara);
    }
}
