package com.sino.sinoflow.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.util;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.model.TransferTrayModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * <p>Title: CaseList</p>
 * <p>Description:显示任务列表供用户新建任务</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class TransferTray extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String sortTypeStr = req.getSession().getServletContext().getInitParameter("sortType");
        int sortType;
        if(sortTypeStr == null || sortTypeStr.equals(""))
            sortType = 0;
        else
            sortType = Integer.parseInt(sortTypeStr);
        String keyword = util.getReqPara(req, "keyword");
        String subject = util.getReqPara(req, "subject");
        String others = util.getReqPara(req, "others");
        String createby = util.getReqPara(req, "createby");
        String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
        boolean autocommit = true;
        boolean commitFlag = false;
        try {
			SfUserBaseDTO user = (SfUserBaseDTO)SessionUtil.getUserAccount(req);
            conn = getDBConnection(req);

            autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            SQLModel sqlModel = (new TransferTrayModel(user, null)).getPageQueryModel(keyword, subject, others, createby, sortType);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            RowSet rs = new RowSet();
            if(simpleQuery.hasResult()) {
                rs = simpleQuery.getSearchResult();
            }
            req.setAttribute(WebAttrConstant.SF_KEYWORD, keyword);
            req.setAttribute(WebAttrConstant.SF_SUBJECT, subject);
            req.setAttribute(WebAttrConstant.SF_CREATEBY, createby);
            req.setAttribute(WebAttrConstant.SF_ACT_INFO_DTO, rs);

            forwardURL = "/flow/transferTray.jsp";
            commitFlag = true;
        } catch (PoolPassivateException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
            setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}