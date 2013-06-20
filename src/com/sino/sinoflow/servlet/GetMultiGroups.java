package com.sino.sinoflow.servlet;


import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.util;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.model.SfMultiGroupModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: CaseList</p>
 * <p>Description:显示任务列表供用户新建任务</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class GetMultiGroups extends BaseServlet {

//    private static final String m_sContentType = "text/html; charset=GBK";
	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
//        res.setContentType(m_sContentType);
//        PrintWriter resout = res.getWriter();

        String prjName = util.getReqPara(req, "project");
        String matchGroups = util.getReqPara(req, "groups");
        String role = util.getReqPara(req, "role");

        Connection conn = null;
        DTOSet groupDTOSet=new DTOSet();
        boolean autocommit = true;
        boolean commitFlag = false;
        try {
            SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(req);

            conn = getDBConnection(req);
            autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            SQLModel sqlModel = (new SfMultiGroupModel(user, null)).getMatchGroupsModel(prjName, matchGroups, role);

            SimpleQuery simpleQuery=new SimpleQuery(sqlModel,conn);
            simpleQuery.executeQuery();
            RowSet rs;

            String groups = "";
            if (simpleQuery.hasResult()) {
                rs = simpleQuery.getSearchResult();
                Row row;
                for(int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    if(!groups.equals(""))
                        groups += ";";
                    groups += row.getStrValue("GROUP_NAME");
                }
            }
            req.setAttribute(WebAttrConstant.GROUP_DTOSET,groupDTOSet);
            if(req.getParameter("multiSelected").equals("0")) {
                forwardURL = "/flow/singleSelect.jsp?header=选择组别&groups='"
                        + URLEncoder.encode(groups,"gb2312")+"'";
            } else {
                forwardURL = "/flow/multipleSelect.jsp?header=选择组别&groups='"
                    + URLEncoder.encode(groups,"gb2312")
                    + "'&selectedGroups=''";
            }
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
        } catch (ContainerException ex) {
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
            //根据实际情况修改页面跳转代码。
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
		}

    }
}