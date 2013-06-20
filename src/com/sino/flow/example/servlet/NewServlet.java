/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-9-20
 * Time: 16:58:20
 */
package com.sino.flow.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.PubServlet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.flow.example.dao.NewDAO;
import com.sino.flow.exception.FlowException;

//新增测试servlet

public class NewServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nextPage = "/example/newTest.jsp";
        Connection conn = null;
        RequestParser rp = new RequestParser();
        try {
            conn = DBManager.getDBConnection();
            rp.transData(req);
            String act = rp.getParameter("act");
            NewDAO dao = new NewDAO(req, conn);
            //暂存，不走流程
            if (act.equals("save")) {
                dao.save();
            } else if (act.equals("complete")) {//完成，走流程
                dao.complete();
            } else if (act.equals("cancel")) {//撤消，也要在流程在撤消
                dao.cancel();
            }
            if (act.equals("")) {
                String appId = StrUtil.nullToString(req.getParameter("appId"));
                if (!appId.equals("")) {
                    dao.find(appId);
                }
            }
        } catch (PoolException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } catch (UploadException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } catch (SQLException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } catch (DataHandleException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } catch (FlowException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } catch (QueryException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } finally {
            DBManager.closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }
}
