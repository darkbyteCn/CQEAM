/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-9-20
 * Time: 16:58:36
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
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.flow.example.dao.ApproveDAO;
import com.sino.flow.example.dao.NewDAO;
import com.sino.flow.exception.FlowException;

public class ApproveServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nextPage = "/example/approveTest.jsp";
        Connection conn = null;
        RequestParser rp = new RequestParser();
        try {
            conn = DBManager.getDBConnection();
            rp.transData(req);
            String appId = req.getParameter("appId");
            String act = StrUtil.nullToString(req.getParameter("act"));
            if (act.equals("")) {
                NewDAO dao = new NewDAO(req, conn);
                dao.find(appId);
            } else if (act.equals("pass")) {//审批通过
                ApproveDAO dao = new ApproveDAO(req, conn);
                dao.pass();
                req.setAttribute("SUCCESS_MSG","通过单据申请成功！");
                nextPage="/example/success.jsp";
            } else if (act.equals("reject")) {//审批退回
                ApproveDAO dao = new ApproveDAO(req, conn);
                dao.back();
                req.setAttribute("SUCCESS_MSG","退回单据申请成功！");
                nextPage="/example/success.jsp";
            }
        } catch (PoolException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } catch (UploadException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } catch (QueryException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } catch (SQLException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } catch (FlowException e) {
            Logger.logError(e);
            req.setAttribute("FLOW_ERROR_MSG", e.getMessage());
            nextPage = "/example/error.jsp";
        } finally {
            DBManager.closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }
}
