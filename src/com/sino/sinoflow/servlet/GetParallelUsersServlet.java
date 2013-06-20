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
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.util;
import com.sino.sinoflow.dto.SfActInfoDTO;
import com.sino.sinoflow.model.SfActInfoModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: CaseList</p>
 * <p>Description:显示任务列表供用户新建任务</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class GetParallelUsersServlet extends BaseServlet {

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

        String actId = util.getReqPara(req, "sf_actId");

        Connection conn = null;
        boolean autocommit = true;
        boolean commitFlag = false;
        try {
            SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(req);

            SfActInfoDTO act = new SfActInfoDTO();
            act.setSfactActId(actId);

            conn = getDBConnection(req);
            autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            SQLModel sqlModel;
            sqlModel = (new SfActInfoModel(user, null)).getParallelUsersModel(actId);

            SimpleQuery simpleQuery=new SimpleQuery(sqlModel,conn);
            simpleQuery.executeQuery();
            RowSet rs;

            String realnames = "";
            String usernames = "";
            if (simpleQuery.hasResult()) {
//                taskDTOSet=simpleQuery.getDTOSet();
                rs = simpleQuery.getSearchResult();
                for(int i = 0; i < rs.getSize(); i++) {
                    Row row = rs.getRow(i);
                    if(!realnames.equals(""))
                        realnames += ";";
                    realnames += row.getStrValue("USERNAME") + "/" + row.getStrValue("GROUP_NAME");
                    if(!usernames.equals("")) {
                        usernames += ";";
                    }
                    usernames += row.getStrValue("LOGIN_NAME");
                }
            }
            resout.write("[{usernames:'" + usernames + "',realnames:'" + realnames + "'}]");
            commitFlag = true;
        } catch (PoolPassivateException ex) {
			Logger.logError(ex);
            resout.write("取用户名出错");
        } catch (ContainerException ex) {
			Logger.logError(ex);
            resout.write("取用户名出错");
        } catch (QueryException ex) {
			Logger.logError(ex);
            resout.write("取用户名出错");
        } catch (SQLException ex) {
            Logger.logError(ex);
            resout.write("取用户名出错");
        } catch (SQLModelException ex) {
            Logger.logError(ex);
            resout.write("取用户名出错");
        } finally {
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
		}
    }
}