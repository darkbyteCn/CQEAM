package com.sino.ams.newasset.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsFaChgHistoryDTO;
import com.sino.ams.newasset.model.AmsFaChgHistoryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsFaChgHistoryDAO</p>
 * <p>Description:程序自动生成服务程序“AmsFaChgHistoryDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AssetsChgHistoryDAO extends AMSBaseDAO {

    /**
     * 功能：固定资产变更表(EAM) AMS_FA_CHG_HISTORY 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsFaChgHistoryDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public AssetsChgHistoryDAO(SfUserDTO userAccount,
                               AmsFaChgHistoryDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsFaChgHistoryDTO dtoPara = (AmsFaChgHistoryDTO) dtoParameter;
        super.sqlProducer = new AmsFaChgHistoryModel((SfUserDTO) userAccount,
                dtoPara);
    }

    /**
     * 功能：创建资产调拨日志记录
     * @throws DataHandleException
     */
    public void createAssetsTransferLog() throws DataHandleException {
        AmsFaChgHistoryModel modelProducer = (AmsFaChgHistoryModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAssetsTransferLogModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：创建资产报废日志记录
     * @throws DataHandleException
     */
    public void createAssetsDiscardLog() throws DataHandleException {
        AmsFaChgHistoryModel modelProducer = (AmsFaChgHistoryModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAssetsDiscardLogModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    /**
     * 功能：创建资产清理日志记录
     * @throws DataHandleException
     */
    public void createAssetsClearLog() throws DataHandleException {
        AmsFaChgHistoryModel modelProducer = (AmsFaChgHistoryModel) sqlProducer;
        SQLModel sqlModel = modelProducer.getAssetsClearLogModel();
        DBOperator.updateRecord(sqlModel, conn);
    }
}
