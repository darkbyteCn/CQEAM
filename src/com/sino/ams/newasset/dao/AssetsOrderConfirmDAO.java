package com.sino.ams.newasset.dao;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.AssetsOrderConfirmModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

import java.sql.Connection;

/**
 * <p>Title: EtsFaAssetsDAO</p>
 * <p>Description:程序自动生成服务程序“EtsFaAssetsDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class AssetsOrderConfirmDAO extends AMSBaseDAO {

    private boolean assetsConfirm = true;

    /**
     * 功能：固定资产当前信息(EAM) ETS_FA_ASSETS 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter AmsAssetsTransLineDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public AssetsOrderConfirmDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }


    public void setAssetsConfirm(boolean assetsConfirm){
        this.assetsConfirm = assetsConfirm;
    }


    /**
     * 功能：SQL生成器BaseSQLProducer的初始化。
     *
     * @param userAccount  BaseUserDTO 本系统最终操作用户类
     * @param dtoParameter DTO 本次操作的数据
     */
    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new AssetsOrderConfirmModel(userAccount, dtoParameter);
    }

    public void confirmOrderAssets() throws DataHandleException {
        try {
            AssetsOrderConfirmModel modelProducer = (AssetsOrderConfirmModel) sqlProducer;
            SQLModel sqlModel = modelProducer.getOrderAssetsModel();
            SimpleQuery searcher = new SimpleQuery(sqlModel, conn);
            searcher.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
            searcher.executeQuery();
            DTOSet orderAssets = searcher.getDTOSet();
            if(orderAssets != null && !orderAssets.isEmpty()){
                AssetsConfirmDAO assignDAO = new AssetsConfirmDAO(userAccount, null, conn);
                assignDAO.setAssetsConfirm(assetsConfirm);
                assignDAO.confirmAssets(orderAssets);
            }
        } catch (QueryException ex) {
            ex.printLog();
            throw new DataHandleException(ex);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new DataHandleException(ex.getMessage());
        }
    }

}
