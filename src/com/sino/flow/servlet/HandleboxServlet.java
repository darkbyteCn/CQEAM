package com.sino.flow.servlet;

import java.io.IOException;
import java.sql.Connection;

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
import com.sino.flow.constant.ReqAttributeList;
import com.sino.flow.dao.FlowTraceDAO;
import com.sino.framework.security.bean.SessionUtil;

/**
 * Created by wwb.
 * User: V-wangwenbin
 * Date: 2007-9-22
 * Time: 14:18:15
 * 在办箱servlet
 */
public class HandleboxServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Connection conn = null;
        String nextPage = "/flow/handlebox.jsp";
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            int userId = userAccount.getUserId();
            FlowTraceDAO dao = new FlowTraceDAO(req, conn);
            dao.getHandle(StrUtil.nullToString(userId));
        } catch (PoolException e) {
            req.setAttribute(ReqAttributeList.ERROR_MSG, "取数据库连接失败！");
            Logger.logError(e);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } catch (QueryException e) {
            req.setAttribute(ReqAttributeList.ERROR_MSG, "取数据库连接失败！");
            Logger.logError(e);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } finally {
            DBManager.closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }
}
