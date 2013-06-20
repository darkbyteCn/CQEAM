package com.sino.flow.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.PubServlet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.flow.constant.FlowURLDefineList;
import com.sino.flow.constant.MessageDefineList;
import com.sino.flow.constant.ReqAttributeList;
import com.sino.flow.dao.FlowTraceDAO;
import com.sino.framework.security.bean.SessionUtil;

/**
 * Created by wwb.
 * User: demo
 * Date: 2006-12-25
 * Time: 20:58:02
 * 流程发件箱servlet
 */
public class OutboxServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nextPage = FlowURLDefineList.OUT_BOX_PAGE;
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            //取userId,不同的应用session中存的userInfo可能不同
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            String userId = StrUtil.nullToString(userAccount.getUserId());
            FlowTraceDAO dao = new FlowTraceDAO(req, conn);
            String process = StrUtil.nullToString(req.getParameter("process"));
            if (process.equals("")) {
                dao.getOutbox(userId);
            } else if (process.equals("GETBACK")) {
                dao.getBack(userId);
                req.setAttribute("GET_BACK_MSG", "单据取回成功！");
                dao.getOutbox(userId);
            }
        } catch (PoolException e) {
            req.setAttribute(ReqAttributeList.ERROR_MSG, MessageDefineList.GET_CONN_ERROR);
            Logger.logError(e);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } catch (QueryException e) {
            req.setAttribute(ReqAttributeList.ERROR_MSG, MessageDefineList.QUERY_ERROR);
            Logger.logError(e);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } catch (SQLException e) {
            req.setAttribute(ReqAttributeList.ERROR_MSG, "单据取回失败！详细原因：" + e.getMessage());
            Logger.logError(e);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } finally {
            DBManager.closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }
}
