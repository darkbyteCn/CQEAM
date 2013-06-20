package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: SinoEAM</p>
 *
 * <p>Description: 山西移动实物资产管理系统</p>
 *
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 *
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class OrderPrintPriviDAO extends AMSBaseDAO {

    public OrderPrintPriviDAO(SfUserDTO userAccount,
                              AmsAssetsTransHeaderDTO dtoParameter,
                              Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器baseSQLProducer的初始化。具体的DAO继承时初始化具体的SQL生成器。
     *
     * @param userAccount BaseUserDTO
     * @param dtoParameter DTO
     * @todo Implement this com.sino.framework.dao.BaseDAO method
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
    }
}
