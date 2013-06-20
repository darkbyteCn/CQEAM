package com.sino.ams.match.dao;


import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.match.dto.EtsItemMatchDTO;
import com.sino.ams.match.model.EtsItemMatchModel;
import com.sino.ams.match.model.UnusedAssetsModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;


/**
 * <p>Title: EtsItemMatchDAO</p>
 * <p>Description:程序自动生成服务程序“EtsItemMatchDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-jiachuanchuan
 * @version 1.0
 */


public class EtsItemMatchDAO extends BaseDAO {

    private SfUserDTO sfUser = null;

    /**
     * 功能：资产匹配-匹配数据存储(EAM) ETS_ITEM_MATCH 数据访问层构造函数
     *
     * @param userAccount  SfUserDTO 代表本系统的最终操作用户对象
     * @param dtoParameter EtsItemMatchDTO 本次操作的数据
     * @param conn         Connection 数据库连接，由调用者传入。
     */
    public EtsItemMatchDAO(SfUserDTO userAccount, EtsItemMatchDTO dtoParameter, Connection conn) {
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
        EtsItemMatchDTO dtoPara = (EtsItemMatchDTO) dtoParameter;
        super.sqlProducer = new EtsItemMatchModel((SfUserDTO) userAccount, dtoPara);
    }

    public void deleteData() throws DataHandleException {
        super.deleteData();
    }


    public String doMatch() {
        String ret = "Y";
        boolean hasError = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            super.createData();
            super.updateData();

            EtsItemMatchModel   modelProducer =  (EtsItemMatchModel) sqlProducer;
            SQLModel sqlModel ;
            sqlModel =modelProducer.getHasBeenModel();
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.executeQuery();
             if (!sq.hasResult()) {
                insertIntoEIMR();
            } else {
                updateEIMR();
            }
//              insertIntoEIMR();
              insertIntoEIMRL();
              updateEIMC();

            conn.commit();
            hasError = false;
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
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
       return ret;
    }

    //插入ETS_ITEM_MATCH_REC
    public void insertIntoEIMR() throws DataHandleException {
        EtsItemMatchModel modelProducer = (EtsItemMatchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.insertIntoEIMRModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    //更新ETS_ITEM_MATCH_REC
    public void updateEIMR() throws DataHandleException {
        EtsItemMatchModel modelProducer = (EtsItemMatchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.updateEIMRModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    //插入ETS_ITEM_MATCH_REC_LOG
    public void insertIntoEIMRL() throws DataHandleException {
        EtsItemMatchModel modelProducer = (EtsItemMatchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.insertIntoEIMRLModel();
        DBOperator.updateRecord(sqlModel, conn);
    }

    //更新ETS_ITEM_MATCH_COMPLET
    public void updateEIMC() throws DataHandleException {
        EtsItemMatchModel modelProducer = (EtsItemMatchModel) sqlProducer;
        SQLModel sqlModel = modelProducer.updateEIMCModel();
        DBOperator.updateRecord(sqlModel,conn)  ;
    }

//    public void deletExist() throws SQLModelException, DataHandleException {
//      UnusedAssetsModel modelProducer = (UnusedAssetsModel) sqlProducer;
//      SQLModel sqlModel = modelProducer.getDataDeleteModel();
//      DBOperator.updateRecord(sqlModel,conn)  ;
//      prodMessage(CustMessageKey.DISABLE_SUCCESS);
//   	 getMessage().addParameterValue("设备");
//    }


    public boolean unUserdisplay() {
        boolean right;
        right = false;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            UnusedAssetsModel  unusedAssetsModel = new UnusedAssetsModel(userAccount,dtoParameter);
            unusedAssetsModel.do_deleteMatched();
            unusedAssetsModel.getDataDeleteModel();

            conn.commit();
            right = true;
        } catch (SQLException e) {
            Logger.logInfo(e);
        } catch (SQLModelException e) {
             Logger.logInfo(e);
        } finally {
            try {
                if (!right) {

                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                Logger.logError(e);
            }
        }
        return right;
    }

}

