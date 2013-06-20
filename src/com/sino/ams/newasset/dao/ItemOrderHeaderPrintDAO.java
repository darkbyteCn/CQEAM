package com.sino.ams.newasset.dao;

import java.sql.Connection;

import com.sino.base.dto.DTO;

import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.model.ItemOrderHeaderPrintModel;
import com.sino.ams.system.user.dto.SfUserDTO;
public class ItemOrderHeaderPrintDAO extends AMSBaseDAO {

    /**
     * 功能：资产业务头表(EAM)--取代原表 AMS_ASSETS_TRANS_HEADER 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransHeaderDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public ItemOrderHeaderPrintDAO(SfUserDTO userAccount,
                               AmsAssetsTransHeaderDTO dtoParameter,
                               Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsTransHeaderDTO dtoPara = (AmsAssetsTransHeaderDTO)
                                          dtoParameter;
        super.sqlProducer = new ItemOrderHeaderPrintModel((SfUserDTO) userAccount,
                dtoPara);
    }
}
