package com.sino.sinoflow.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.util;


/**
 * <p>Title: CaseList</p>
 * <p>Description:显示任务列表供用户新建任务</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class GetDeptUsers extends BaseServlet {
    private static final String m_sContentType = "text/html; charset=GBK";

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(m_sContentType);
        PrintWriter resout = res.getWriter();

        String dept = util.getReqPara(req, "dept");
        Connection conn = null;
        boolean autocommit = true;
        boolean commitFlag = false;
        try {
//            SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(req);
            conn = getDBConnection(req);
            autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            SQLModel sqlModel = new SQLModel();
            String sqlStr = "SELECT SU.LOGIN_NAME, SU.USERNAME FROM SF_USER SU, SINO_MIS_DEPT SMD,"
                    + " SINO_MIS_EMPLOYEE SME"
                    + " WHERE SMD.DEPT_NAME = '" + dept + "'"
                    + " AND SMD.DEPT_ID = SME.DEPT_ID AND SME.EMPLOYEE_ID = SU.EMPLOYEE_ID";
            sqlModel.setSqlStr(sqlStr);
            String realnames = "";
            String usernames = "";
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            RowSet rs;
            if(simpleQuery.hasResult()) {
                    rs = simpleQuery.getSearchResult();
                    Row row;
                    if(rs.getSize() < 1) {
                        resout.write("[{status:'ERROR',message:'没找到组别对应的用户!'}]");
                        return;
                    } else {
                        for(int i = 0; i < rs.getSize(); i++) {
                            row = rs.getRow(i);
                            if(realnames.equals(""))
                                realnames = row.getStrValue("USERNAME");
                            else
                                realnames += ";" + row.getStrValue("USERNAME");
                            if(usernames.equals(""))
                                usernames = row.getStrValue("LOGIN_NAME");
                            else
                                usernames += ";" + row.getStrValue("LOGIN_NAME");
                        }
                    }
            }
            resout.write("[{realnames:'" + realnames
                    + "', usernames:'" + usernames + "'}]");
            commitFlag = true;
        } catch (PoolPassivateException ex) {
			Logger.logError(ex);
            resout.write("[{status:'ERROR',message:'查询数据库组别对应用户出錯!'}]");
        } catch (ContainerException ex) {
			Logger.logError(ex);
            resout.write("[{status:'ERROR',message:'查询数据库组别对应用户出錯!'}]");
        } catch (QueryException ex) {
			Logger.logError(ex);
            resout.write("[{status:'ERROR',message:'查询数据库组别对应用户出錯!'}]");
        } catch (SQLException ex) {
            Logger.logError(ex);
            resout.write("[{status:'ERROR',message:'查询数据库组别对应用户出错!'}]");
        } finally {
            resout.flush();
            resout.close();
            if(conn != null) {
                try {
                    if(commitFlag)
                        conn.commit();
                    else
                        conn.rollback();
                    conn.setAutoCommit(autocommit);
                } catch(SQLException ex) {
                    Logger.logError(ex);
                }
	    		DBManager.closeDBConnection(conn);
            }
            //根据实际情况修改页面跳转代码。
		}
    }
}