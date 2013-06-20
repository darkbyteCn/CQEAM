package com.sino.flow.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;

/**
 * Created by wwb.
 * User: demo
 * Date: 2007-4-12
 * Time: 18:02:42
 */
public class SignApplyDAO {
     // //falg:0表示有人已经签收，1表示签收成功
     public String sign(String actId, String userId, Connection conn) throws SQLException, DataHandleException, QueryException, ContainerException {
        String flag = "";
        try {
            conn.setAutoCommit(false);
            //step1:删除actInfo表中的记录
            String delSql =
                    "DELETE FROM SF_ACT_INFO SAI\n" +
                    " WHERE SAI.ACT_ID = ?\n" +
                    "   AND ((SAI.AGENT_USER_ID " + SyBaseSQLUtil.isNullNoParam() + "  AND SAI.USER_ID <> ?) OR\n" +
                    "       ( " + SyBaseSQLUtil.isNotNull("SAI.AGENT_USER_ID") + "  AND SAI.AGENT_USER_ID <> ?))";
            ArrayList delAl = new ArrayList();
            delAl.add(actId);
            delAl.add(userId);
            delAl.add(userId);
            SQLModel delSm = new SQLModel();
            delSm.setSqlStr(delSql);
            delSm.setArgs(delAl);
            DBOperator.updateRecord(delSm, conn);
            //step2:计算表中还有多少条记录
            String selSql = "SELECT COUNT(*) TOTAL FROM SF_ACT_INFO SAI WHERE SAI.ACT_ID = ?";
            ArrayList selAl = new ArrayList();
            selAl.add(actId);
            SQLModel selSm = new SQLModel();
            selSm.setSqlStr(selSql);
            selSm.setArgs(selAl);
            SimpleQuery sq = new SimpleQuery(selSm, conn);
            sq.executeQuery();
            RowSet rs = sq.getSearchResult();
            int total = 0;
            if (rs != null && !rs.isEmpty()) {
                Row row = rs.getRow(0);
                total = Integer.parseInt((String) row.getValue("TOTAL"));
            }
            //step3:如果只有1条记录，commit.0条记录rollback
            if (total == 1) {
                //如果此人已经签收，那么别人都应该不再短信通知
                String msgSQL =
                        "UPDATE SF_MSG_SEND_INFO SMS\n" +
                                "   SET HAS_PROCESSED = 'Y'\n" +
                                " WHERE ACT_ID = ?\n" +
                                "   AND MSG_CELL_PHONE = (SELECT SUP.MSG_CELL_PHONE\n" +
                                "                           FROM SF_USER_PHONE SUP\n" +
                                "                          WHERE SUP.USER_ID = ?)";
                ArrayList msgAl = new ArrayList();
                msgAl.add(actId);
                msgAl.add(userId);
                SQLModel msgSm = new SQLModel();
                msgSm.setSqlStr(msgSQL);
                msgSm.setArgs(msgAl);
                DBOperator.updateRecord(msgSm, conn);
                //将本流程记录更新为已签收
                String signSQL="UPDATE SF_ACT_INFO SAI SET SAI.SIGN_FLAG = 'Y' WHERE SAI.ACT_ID = ?";
                ArrayList signAl=new ArrayList();
                signAl.add(actId);
                SQLModel signModel=new SQLModel();
                signModel.setArgs(signAl);
                signModel.setSqlStr(signSQL);
                DBOperator.updateRecord(signModel,conn);
                //签收成功，返回1
                flag = "1";
                conn.commit();
            } else { //如果是0条记录，那么有可能有人正在同时签收这条申请，而且已经签收了。
                flag = "0";
                conn.rollback();
            }
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } catch (DataHandleException e) {
            conn.rollback();
            throw e;
        } catch (QueryException e) {
            conn.rollback();
            throw e;
        } catch (ContainerException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
        return flag;
    }
}
