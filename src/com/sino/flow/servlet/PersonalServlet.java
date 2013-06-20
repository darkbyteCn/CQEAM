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
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.flow.constant.FlowURLDefineList;
import com.sino.flow.constant.MessageDefineList;
import com.sino.flow.constant.ReqAttributeList;
import com.sino.flow.dao.FlowTraceDAO;

/**
 * Created by wwb.
 * User: demo
 * Date: 2006-12-26
 * Time: 11:00:11
 */
public class PersonalServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nextPage = FlowURLDefineList.PERSONAL_PAGE;
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
//            UserInfoDTO userInfo = (UserInfoDTO) req.getSession().getAttribute(SessionAttributeList.USER_INFO);
//            String userId = userInfo.getUserId();
            String userId="";
            String fromDate = StrUtil.nullToString(req.getParameter("fromDate"));
            if (!fromDate.equals("")) {
                fromDate += " 00:00:00";
            }
            String toDate = StrUtil.nullToString(req.getParameter("toDate"));
            if (!toDate.equals("")) {
                toDate += " 23:59:59";
            }
            String expenseType = StrUtil.nullToString(req.getParameter("expenseType"));
            String headerStatus = StrUtil.nullToString(req.getParameter("headerStatus"));
            String reportNumber=StrUtil.nullToString(req.getParameter("reportNumber"));
            //String process = req.getParameter("process");
//            OptionProducer op = new OptionProducer(conn, req);
//            String expenseTypeOption = op.ExpenseType(expenseType);
//            req.setAttribute("EXPENSE_TYPE_OPTION", expenseTypeOption);
//            req.setAttribute("HEADER_STATUS_OPTION", op.headerStatus(headerStatus));
            FlowTraceDAO dao = new FlowTraceDAO(req, conn);
            dao.getPersonal(userId, fromDate, toDate,expenseType, headerStatus,reportNumber);
        } catch (PoolException e) {
            req.setAttribute(ReqAttributeList.ERROR_MSG, MessageDefineList.GET_CONN_ERROR);
            Logger.logError(e);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } catch (QueryException e) {
            req.setAttribute(ReqAttributeList.ERROR_MSG, MessageDefineList.QUERY_ERROR);
            Logger.logError(e);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } finally {
            DBManager.closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }
}
