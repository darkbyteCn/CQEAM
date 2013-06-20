package com.sino.ams.match.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.match.dto.HideMisAssetDTO;
import com.sino.ams.match.model.HideMisAssetModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-12-5
 * Time: 10:55:09
 * To change this template use File | Settings | File Templates.
 */
public class HideMisAssetDAO extends AMSBaseDAO {
    private SfUserDTO sfUser = null;

    /**
     * 功能：资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public HideMisAssetDAO(SfUserDTO userAccount, HideMisAssetDTO dtoParameter, Connection conn) {
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
        HideMisAssetDTO dtoPara = (HideMisAssetDTO) dtoParameter;
        super.sqlProducer = new HideMisAssetModel((SfUserDTO) userAccount, dtoPara);
    }

    public void deleteData() throws DataHandleException {
        super.deleteData();
    }


    public String doHide(String[] assetIds, String reMark) {
        String ret = "Y";
        boolean hasError = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            HideMisAssetModel modelProducer = (HideMisAssetModel) sqlProducer;
            SQLModel sqlModel;
            sqlModel = modelProducer.getHasBeenModel(assetIds);
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
            if (!sq.hasResult()) { // 判断 表 ets_item_match_assist_mis
                for (int i = 0; i < assetIds.length; i++) {     //进行屏蔽操作
                    HideMisAssetDTO hudedto = (HideMisAssetDTO) dtoParameter;
                    hudedto.setAssetId(StrUtil.strToInt(assetIds[i]));
                    hudedto.setReMark(reMark);
                    super.createData();
                }
            } else {               //所选设备中存在已经被其它用户屏蔽的设备
                ret = "N";
            }
            conn.commit();
            hasError = false;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            e.printStackTrace();
        } catch (QueryException e) {
            e.printStackTrace();
        } finally {
            try {
                if (hasError) {
                    ret = "N";
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            }
            catch (SQLException e) {
                Logger.logError(e);
            }
        }
//        }
        return ret;
    }

    //插入ETS_ITEM_MATCH_REC
    public void insertIntoEIMR() throws DataHandleException {
        HideMisAssetModel modelProducer = (HideMisAssetModel) sqlProducer;
        SQLModel sqlModel = modelProducer.insertIntoEIMRModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    //更新ETS_ITEM_MATCH_REC
    public void updateEIMR() throws DataHandleException {
        HideMisAssetModel modelProducer = (HideMisAssetModel) sqlProducer;
        SQLModel sqlModel = modelProducer.updateEIMRModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    //插入ETS_ITEM_MATCH_REC_LOG
    public void insertIntoEIMRL() throws DataHandleException {
        HideMisAssetModel modelProducer = (HideMisAssetModel) sqlProducer;
        SQLModel sqlModel = modelProducer.insertIntoEIMRLModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    //更新ETS_ITEM_MATCH_COMPLET
    public void updateEIMC() throws DataHandleException {
        HideMisAssetModel modelProducer = (HideMisAssetModel) sqlProducer;
        SQLModel sqlModel = modelProducer.updateEIMCModel();
        DBOperator.updateRecord(sqlModel, conn);
    }
}
