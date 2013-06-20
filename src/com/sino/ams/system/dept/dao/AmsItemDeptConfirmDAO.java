package com.sino.ams.system.dept.dao;

import com.sino.framework.dao.BaseDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.dept.model.AmsItemDeptConfirmModel;
import com.sino.base.dto.DTO;
import com.sino.base.exception.*;
import com.sino.base.util.ArrUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.conn.DBManager;
import com.sino.base.data.RowSet;
import com.sino.base.data.Row;
import com.sino.base.log.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2009-7-9
 * Time: 10:18:50
 * To change this template use File | Settings | File Templates.
 */
public class AmsItemDeptConfirmDAO extends BaseDAO {

    private SfUserDTO sfUser = null;


    public AmsItemDeptConfirmDAO(SfUserDTO userAccount, EtsItemInfoDTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
        sfUser = userAccount;
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        EtsItemInfoDTO dtoPara = (EtsItemInfoDTO) dtoParameter;
        super.sqlProducer = new AmsItemDeptConfirmModel((SfUserDTO) userAccount, dtoPara);
    }
      public String  confirmRentAssets(String[] systemIds, String[] compyCode, String[] newDept, String[] deptCode) throws DataHandleException, SQLModelException {
        boolean autoCommit = false;
        boolean hasError = true;
        String msg = "";
          boolean ret=false;

        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            if ((systemIds != null) && (systemIds.length > 0)) {

                    for (int i = 0; i < systemIds.length; i++) {
                        String systemId[] = systemIds[i].split(";");
                        int j = Integer.parseInt(systemId[1]);
                        AmsItemDeptConfirmModel modelProducer = (AmsItemDeptConfirmModel) sqlProducer;
                        SQLModel sm1 = modelProducer.getHasUnknowModel(systemId[0]);
                        SimpleQuery sq = new SimpleQuery(sm1, conn);
                        sq.executeQuery();
                        if (sq.hasResult()) {
                          SQLModel sqlModel = modelProducer.updateLogModel(systemId[0], newDept[j]);
                        DBOperator.updateRecord(sqlModel, conn);
                        }else{
                            SQLModel sqlModel = modelProducer.insertLogModel(systemId[0], newDept[j]);
                        DBOperator.updateRecord(sqlModel, conn);
                        }
                           SQLModel sd = modelProducer.insertLogInfoModel(systemId[0], newDept[j]);
                        DBOperator.updateRecord(sd, conn);
                      ret=saveItemDept(systemId[0], newDept[j]);
                        if(!ret){
                         msg="请确认部门！";
                        }
                    }

             
            }
            conn.commit();
             hasError = !msg.equals("");
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
        } finally {
            try {
                if (hasError) {
                    conn.rollback();
                }
                conn.setAutoCommit(autoCommit);
            }
            catch (SQLException e) {
                Logger.logError(e);
            }
        }
        return  msg;
    }
    public boolean saveItemDept(String oldDept, String newDept) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
        CallableStatement cStmt = null;
        String sqlStr = "{call dbo.APP_UPDATE_ITEM_DEPT(?,?,?)}";
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            cStmt = conn.prepareCall(sqlStr);
                cStmt.setString(1, newDept);
                cStmt.setString(2, oldDept);
               // cStmt.setString(3, sfUser.getUserId());
                cStmt.setInt(3, sfUser.getUserId());
                cStmt.execute();
                conn.commit();

                success=true;



        } catch (SQLException e) {
            Logger.logError(e);
            conn.rollback();
            message.setIsError(true);
        } finally {
            DBManager.closeDBStatement(cStmt);
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }
}
