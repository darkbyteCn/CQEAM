package com.sino.flow.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.PubServlet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.flow.constant.FlowURLDefineList;
import com.sino.flow.constant.MessageDefineList;
import com.sino.flow.constant.ReqAttributeList;
import com.sino.flow.dao.FlowTraceDAO;

/**
 * Created by wwb.
 * User: demo
 * Date: 2006-12-28
 * Time: 14:47:20
 * 申请取消,只有在申请的当前办理人是创建人才可以取消
 */
public class CancelServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nextPage = FlowURLDefineList.CANCEL_PAGE;
        Connection conn = null;
        try {
//            UserInfoDTO userInfo=(UserInfoDTO) req.getSession().getAttribute(SessionAttributeList.USER_INFO);
//            String userId = userInfo.getUserId();
            String userId = "";  //tmp
            conn = DBManager.getDBConnection();
            FlowTraceDAO dao = new FlowTraceDAO(req, conn);
            dao.getCancel(userId);
        } catch (PoolException e) {
            e.printLog();
            req.setAttribute(ReqAttributeList.ERROR_MSG, MessageDefineList.GET_CONN_ERROR);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } catch (QueryException e) {
            e.printLog();
            req.setAttribute(ReqAttributeList.ERROR_MSG, MessageDefineList.QUERY_ERROR);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } finally {
            DBManager.closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }
}
