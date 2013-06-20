package com.sino.sinoflow.user.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.user.dao.SfProjectDAO;
import com.sino.sinoflow.user.dto.SfProjectDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.user.model.SfProjectModel;


/**
 * <p>Title: SfProjectServlet</p>
 * <p>Description:程序自动生成服务程序“SfProjectServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfProjectServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserBaseDTO user = (SfUserBaseDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfProjectDTO.class.getName());
			SfProjectDTO dtoParameter = (SfProjectDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			SfProjectDAO sfProjectDAO = new SfProjectDAO(user, dtoParameter, conn);
			if (action.equals("")) {//工程查询首页面
				BaseSQLProducer sqlProducer = new SfProjectModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(SfProjectDTO.class.getName());
				pageDAO.produceWebData();
				forwardURL = URLDefineList.PROJECT_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				forwardURL = "";
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				forwardURL = URLDefineList.PROJECT_SERVLET;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				sfProjectDAO.createData();
				forwardURL = URLDefineList.PROJECT_SERVLET;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				sfProjectDAO.updateData();
				forwardURL = URLDefineList.PROJECT_SERVLET;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				sfProjectDAO.deleteData();
				forwardURL = URLDefineList.PROJECT_SERVLET;
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolPassivateException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}  finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}