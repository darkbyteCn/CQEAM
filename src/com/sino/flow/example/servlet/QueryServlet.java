package com.sino.flow.example.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.PubServlet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.flow.example.dao.NewDAO;

/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-9-24
 * Time: 10:57:04
 * 流程测试
 * 应用查询servlet
 */
public class QueryServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nextPage = "/example/queryTest.jsp";
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            NewDAO dao = new NewDAO(req, conn);
            String appId = req.getParameter("appId");
            dao.find(appId);
        } catch (PoolException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } catch (QueryException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } finally{
            DBManager.closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }
}
