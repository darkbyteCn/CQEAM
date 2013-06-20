package com.sino.sinoflow.servlet;


import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.util.ResUtil;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.model.PendingTrayModel2;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.util;
import com.sino.sinoflow.utilities.TrayUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>Title: CaseList</p>
 * <p>Description:显示任务列表供用户新建任务</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class PendingTray2 extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String sortTypeStr = util.getReqPara(req, "sortTypeStr");
        if(sortTypeStr == null || sortTypeStr.equals("")) {
            sortTypeStr = "SFACT_PROC_NAME, SFACT_APPL_MSG, SFACT_FROM_DATE DESC";
        }
        String sortType = util.getReqPara(req, "sortType");
        String keyword = util.getReqPara(req, "keyword");
        String subject = util.getReqPara(req, "subject");
        String others = util.getReqPara(req, "others");
        String createby = util.getReqPara(req, "createby");
        String fromDate = util.getReqPara(req, "fromDate");
        String toDate = util.getReqPara(req, "toDate");
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
            
            //栏目定义标头
            ResUtil.setAllResName(conn, req, ResNameConstant.PENDING_TRAY2 );
            
            SQLModel sqlModel = (new PendingTrayModel2(user, null)).getPageQueryModel(keyword, subject, others, createby, fromDate, toDate, sortTypeStr);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            RowSet rs = new RowSet();
            if(simpleQuery.hasResult()) {
                rs = simpleQuery.getSearchResult();
            }
            req.setAttribute(WebAttrConstant.SF_KEYWORD, keyword);
            req.setAttribute(WebAttrConstant.SF_SUBJECT, subject);
            req.setAttribute(WebAttrConstant.SF_CREATEBY, createby);
            req.setAttribute(WebAttrConstant.FROM_DATE, fromDate);
            req.setAttribute(WebAttrConstant.TO_DATE, toDate);
            req.setAttribute(WebAttrConstant.SORT_TYPE, sortType);
            req.setAttribute(WebAttrConstant.SORT_TYPE_STR, sortTypeStr);
            req.setAttribute(WebAttrConstant.SF_ACT_INFO_DTO, rs);
            TrayUtil tu = new TrayUtil();
            req.setAttribute(WebAttrConstant.SF_PENDINGTRAY_TOP_LIST,
                    tu.getPendingTrayTop(user.getLoginName(), keyword, subject, createby, conn));
            req.setAttribute(WebAttrConstant.SF_PENDINGTRAY_BOTTOM_LIST,
                    tu.getPendingTrayBottom(user.getLoginName(), keyword, subject, createby, conn));

            forwardURL = "/flow/pendingTray2.jsp";
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
		} catch (ContainerException ex) {
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