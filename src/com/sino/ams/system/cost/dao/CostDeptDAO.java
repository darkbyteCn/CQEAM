package com.sino.ams.system.cost.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.system.cost.model.CostCenterModel;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.DTO;
import com.sino.base.exception.DataHandleException;
import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-31
 * Time: 17:17:18
 * To change this template use File | Settings | File Templates.
 */
public class CostDeptDAO extends AMSBaseDAO {
     private int matchedItemCount = 0;   //本次匹配成功的数量

    public CostDeptDAO(BaseUserDTO userAccount, DTO dtoParameter, Connection conn) {
        super(userAccount, dtoParameter, conn);
    }

    protected void initSQLProducer(BaseUserDTO userAccount, DTO dtoParameter) {
        super.sqlProducer = new CostCenterModel(userAccount, dtoParameter);
    }


    public boolean saveItemMatch(String[] depts, String cost) throws SQLException {
        boolean success = false;
        boolean autoCommit = false;
//        CallableStatement cStmt = null;
//        String sqlStr = "{call (?,?,?,?,?)}";
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
//            cStmt = conn.prepareCall(sqlStr);
            for (int i = 0; i < depts.length; i++) {
//                cStmt.setString(1, depts[i]);
//                cStmt.setString(2, cost);
//                cStmt.setString(3, "");
//                cStmt.setString(4, "9");
//                cStmt.setString(5, userAccount.getUserId());
//                cStmt.execute();
                conn.commit();
                success = true;
            }
        } catch (SQLException e) {
            Logger.logError(e);
            conn.rollback();
            prodMessage("SAVE_FAILURE");
        } finally {
//            DBManager.closeDBStatement(cStmt);
            conn.setAutoCommit(autoCommit);
        }
        return success;
    }

    /**
     * 功能:进行匹配。
     * @param depts
     * @param cost
     * @return
     */
    public boolean doMatch(String[] depts, String cost,String companyCode) {
//        String ret = "Y";
        boolean ret = false;
        boolean hasError = true;
        boolean autoCommit = false;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            for(int i = 0; i < depts.length;i++) {
              insertIntoCOST(depts[i],cost,companyCode);
            }
            hasError = false;
            ret = true;
        } catch (SQLException e) {
            Logger.logError(e);
            e.printStackTrace();
        } catch (DataHandleException e) {
           Logger.logError(e);
           e.printStackTrace();
//        } catch (QueryException e) {
//            e.printStackTrace();
        } finally {
            try {
                if (hasError) {
//                    ret = "N";
                    ret = false;
                    conn.rollback();
                }else{
                	conn.commit();
                }
                conn.setAutoCommit(autoCommit);
            }
            catch (SQLException e) {
                Logger.logError(e);
            }
        }
       return ret;
    }

    /**
     * 功能:AMS_MIS_COST_MATCH；
     * @throws DataHandleException
     */
    public void insertIntoCOST(String dept, String cost , String companyCode) throws DataHandleException {
        CostCenterModel modelProducer = (CostCenterModel) sqlProducer;
        SQLModel sqlModel = modelProducer.insertIntoCOSTModel(dept,cost,companyCode);
        DBOperator.updateRecord(sqlModel, conn);
    }
}
