package com.sino.ams.newasset.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;
import com.sino.ams.newasset.model.ContentPriviModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: AmsAssetsChkLogDAO</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsChkLogDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class ContentPriviDAO extends AMSBaseDAO {

    /**
     * 功能：资产盘点记录 AMS_ASSETS_CHK_LOG 数据访问层构造函数
     * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsChkLogDTO 本次操作的数据
     * @param conn Connection 数据库连接，由调用者传入。
     */
    public ContentPriviDAO(SfUserDTO userAccount,
                           AmsAssetsPriviDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     * @param userAccount BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        AmsAssetsPriviDTO dtoPara = (AmsAssetsPriviDTO) dtoParameter;
        super.sqlProducer = new ContentPriviModel((SfUserDTO) userAccount,
                                                  dtoPara);
    }


    /**
     * 功能：保存专业资产权限
     * @param priviDTOs DTOSet
     * @return boolean
     */
    public boolean savePrivi(DTOSet priviDTOs) {
        boolean operateResult = false;
        boolean autoCommit = false;
        try {
            int dataCount = priviDTOs.getSize();
            AmsAssetsPriviDTO dto = null;
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            ContentPriviModel modelProducer = (ContentPriviModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getDataDeleteModel();
            DBOperator.updateRecord(sqlModel, conn);
            for (int i = 0; i < dataCount; i++) {
                dto = (AmsAssetsPriviDTO) priviDTOs.getDTO(i);
                setDTOParameter(dto);
                sqlModel = modelProducer.getDataCreateModel();
                DBOperator.updateRecord(sqlModel, conn);
            }
            operateResult = true;
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (SQLException ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (!operateResult) {
                    conn.rollback();
                    prodMessage(AssetsMessageKeys.ASS_PRIVI_SAVE_FAILURE);
                } else {
                    conn.commit();
                    prodMessage(AssetsMessageKeys.ASS_PRIVI_SAVE_SUCCESS);
                }
                message.setIsError(!operateResult);
                conn.setAutoCommit(autoCommit);
            } catch (SQLException ex1) {
                Logger.logError(ex1);
                prodMessage(AssetsMessageKeys.ROLL_BACK_ERROR);
                message.setIsError(true);
            }
        }
        return operateResult;
    }
}
