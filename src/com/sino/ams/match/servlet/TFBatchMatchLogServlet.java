package com.sino.ams.match.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.match.dao.TFManualMatchDAO;
import com.sino.ams.match.model.TFBatchMatchLogModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

public class TFBatchMatchLogServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Connection conn = null;
        Message message = SessionUtil.getMessage(req);
        SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
        String act = StrUtil.nullToString(req.getParameter("act"));
        String batchId = StrUtil.nullToString(req.getParameter("batchId"));
        TFManualMatchDAO matchDAO = null;
        try {
            conn = getDBConnection(req);
            matchDAO = new TFManualMatchDAO(user, null, conn);
            if (act.equals("")) {
                AMSSQLProducer sqlProducer = new TFBatchMatchLogModel(user, null);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                forwardURL = "/match/tf/tfBatchMatchLog.jsp";
            } else if (act.equals("UN_MATCH")) {
                int count = matchDAO.unMatchBatch(batchId);
                if (count >= 0) {
                    req.setAttribute("showMsg", "一共解除匹配<font color=red>" + count + "</font>条记录!");
                }
                forwardURL = "/servlet/com.sino.ams.match.servlet.TFBatchMatchLogServlet?act=";
            }else if(act.equals("matchByLocation")||act.equals("matchByCounty")||act.equals("matchByCity")){
                matchDAO.batchMatch(batchId, act);
                forwardURL = "/servlet/com.sino.ams.match.servlet.TFBatchMatchLogServlet?act=";
                req.setAttribute("showMsg", "一共匹配了<font color=red>" + matchDAO.getMatchedItemCount() + "</font>条记录!");
            }
        } catch (QueryException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
        } catch (PoolPassivateException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
        } catch (SQLException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder sf = new ServletForwarder(req, res);
            sf.forwardView(forwardURL);
        }
    }
}
