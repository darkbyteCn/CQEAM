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
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.FlowTaskTool;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.user.dao.SfRoleDAO;
import com.sino.sinoflow.user.dto.SfRoleDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.user.model.SfRoleModel;

/**
 * Title: SfRoleServlet
 * Description:程序自动生成服务程序“SfRoleServlet”，请根据需要自行修改
 * Copyright: Copyright (c) 2007
 * Company: 北京思诺博信息技术有限公司
 * @author mshtang
 * @version 1.0
 */

public class SfRoleServlet extends BaseServlet {

	/**
	 * @param req
	 *            HttpServletRequest
	 * @param res
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(req);// 从session中获取数据，根据实际情况自行修改。
			if(!(user.isSysAdmin())){
				message = getMessage("USER.NO_AUTHORITY");
				ServletConfigDTO servletConfig = getServletConfig(req);
				message.addParameterValue(servletConfig.getSysAdminRole());
				message.addParameterValue(servletConfig.getCityAdminRole());
				forwardURL = getFilterConfig(req).getNoPriviledgeURL();
			} else {
				SfRoleDTO dtoParameter;
				Request2DTO req2DTO = new Request2DTO();
				req2DTO.setDTOClassName(SfRoleDTO.class.getName());
				dtoParameter = (SfRoleDTO) req2DTO.getDTO(req);
				conn = DBManager.getDBConnection();
				SfRoleDAO sfRoleDAO = new SfRoleDAO(user, dtoParameter, conn);
				OptionProducer op = new OptionProducer(user, conn);
				if (action.equals("")) {
					forwardURL = URLDefineList.ROLE_QUERY_PAGE;
				} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
					BaseSQLProducer sqlProducer = new SfRoleModel(user,
						dtoParameter);
					PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
					pageDAO.produceWebData();
					forwardURL = URLDefineList.ROLE_QUERY_PAGE;
				} else if (action.equals(WebActionConstant.NEW_ACTION)) { // 添加
					/* 工程下拉列表 */
					String projectOptionStr = op.getProjectOption(null, WebAttrConstant.PROJECT_OPTION_STR_ALL);
					req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR, projectOptionStr);
					forwardURL = URLDefineList.ROLE_DETAIL_PAGE;
				} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
					sfRoleDAO.setDTOClassName(SfRoleDTO.class.getName());
					SfRoleDTO sfRole = (SfRoleDTO) sfRoleDAO.getDataByPrimaryKey();
					if (sfRole == null) {
						sfRole = new SfRoleDTO();
						message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
						message.setIsError(true);
					}
					/* 工程下拉列表 */
					String projectOptionStr = op.getProjectOption(sfRole.getProjectName(), "");
					req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR, projectOptionStr);
					req.setAttribute(WebAttrConstant.ROLE_ATTR, sfRole);
					forwardURL = URLDefineList.ROLE_DETAIL_PAGE;
				} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
					if (!FlowTaskTool.isExist("SF_ROLE", "ROLE_NAME", dtoParameter.getRoleName(), conn)) {
						sfRoleDAO.createData();
						message = getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
						message.addParameterValue("角色");
						forwardURL = URLDefineList.ROLE_QUERY_SERVLET;
					} else {
						req.setAttribute(WebAttrConstant.ROLE_ATTR, dtoParameter);
						String projectOptionStr = op.getProjectOption(dtoParameter.getProjectName(), "");
						req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR, projectOptionStr);
						message = new Message();
						message.setMessageValue("角色已存在");
						forwardURL = URLDefineList.ROLE_DETAIL_PAGE;
					}

				} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
					sfRoleDAO.updateData();
					message = sfRoleDAO.getMessage();
					forwardURL = URLDefineList.ROLE_QUERY_SERVLET;
				} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
					sfRoleDAO.deleteData();
					message = getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
					message.addParameterValue("角色");
					forwardURL = URLDefineList.ROLE_QUERY_PAGE;
                } else if(action.equals("selOpt")){
                    req.setAttribute("selOpt", op.getAllRoleOption(0));
                    forwardURL = "/system/resource/selOption.jsp";
				} else {
					message = getMessage(MsgKeyConstant.INVALID_REQ);
					message.setIsError(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				}
			}
		} catch (PoolException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.CONN_ERROR);
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
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			// 根据实际情况修改页面跳转代码。
		}
	}
}
