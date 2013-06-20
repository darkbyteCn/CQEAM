package com.sino.ams.system.user.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dao.SfRoleDAO;
import com.sino.ams.system.user.dto.SfRoleDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.model.SfRoleModel;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SfRoleServlet</p>
 * <p>Description:程序自动生成服务程序“SfRoleServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfRoleServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user =  (SfUserDTO)  SessionUtil.getUserAccount(req);//从session中获取数据，根据实际情况自行修改。
			SfRoleDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfRoleDTO.class.getName());
			dtoParameter = (SfRoleDTO)req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			SfRoleDAO sfRoleDAO = new SfRoleDAO(user, dtoParameter, conn);

			if (action.equals("")) {
				forwardURL=URLDefineList.ROLE_QUERY_PAGE;
			}else   if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new SfRoleModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.ROLE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				SfRoleDTO sfRole = null;
				if(sfRole == null){
					sfRole= new SfRoleDTO();
					sfRole.setIsInner("N");
				}
				req.setAttribute(WebAttrConstant.ROLE_ATTR, sfRole);
				forwardURL = URLDefineList.ROLE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				sfRoleDAO.setDTOClassName(SfRoleDTO.class.getName());
				SfRoleDTO sfRole = (SfRoleDTO)sfRoleDAO.getDataByPrimaryKey();
				if(sfRole == null){
					sfRole = new SfRoleDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.ROLE_ATTR, sfRole);
				forwardURL =URLDefineList.ROLE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				sfRoleDAO.createData();
				message = sfRoleDAO.getMessage();
				forwardURL = URLDefineList.ROLE_QUERY_SERVLET;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				sfRoleDAO.updateData();
				message = sfRoleDAO.getMessage();
				forwardURL = URLDefineList.ROLE_QUERY_SERVLET;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				sfRoleDAO.deleteData();
				message = sfRoleDAO.getMessage();
				message = getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
				message.addParameterValue("角色");
				forwardURL = URLDefineList.ROLE_QUERY_PAGE;
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.CONN_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			/** @todo Handle this exception */
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
