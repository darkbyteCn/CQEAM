package com.sino.ams.system.resource.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.resource.dao.SfResPrivsDAO;
import com.sino.ams.system.resource.dto.SfResDefineDTO;
import com.sino.ams.system.resource.dto.SfResPrivsDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SfResPrivsServlet</p>
 * <p>Description:程序自动生成服务程序“SfResPrivsServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) {year}</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfResPrivsServlet extends BaseServlet {

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
			SfUserDTO userAccount = (SfUserDTO)SessionUtil.getUserAccount(req);
			SfResPrivsDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfResPrivsDTO.class.getName());
			dtoParameter = (SfResPrivsDTO)req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			SfResPrivsDAO sfResPrivsDAO = new SfResPrivsDAO(userAccount, dtoParameter, conn);
			OptionProducer optProducer = new OptionProducer(userAccount, conn);
			if(action.equals("")){
				action = WebActionConstant.QUERY_ACTION;
			}
			if (action.equals(WebActionConstant.QUERY_ACTION)) {
				SfResDefineDTO resource = sfResPrivsDAO.getResourceById();
				req.setAttribute(WebAttrConstant.RES_DATA, resource);
				String allRoleOption = optProducer.getAllRoleOption(resource.getSystemId());
				req.setAttribute(WebAttrConstant.ALL_ROLE_OPTION, allRoleOption);
				String viewRoleOption = optProducer.getViewRoleOption(dtoParameter.getResId());
				req.setAttribute(WebAttrConstant.VIEW_ROLE_OPTION, viewRoleOption);
				forwardURL = URLDefineList.RES_PRIVI_QUERY;
			} else if (action.equals(WebActionConstant.SAVE_ACTION)) {
				DTOSet dtos = getResPrivis(req);
				sfResPrivsDAO.saveResPrivis(dtos);
//				forwardURL = URLDefineList.PRIVI_QUERY_SERVLET;
//				forwardURL += "&resId=" + req.getParameter("resId");
				message = sfResPrivsDAO.getMessage();

				SfResDefineDTO resource = sfResPrivsDAO.getResourceById();
				req.setAttribute(WebAttrConstant.RES_DATA, resource);
				String allRoleOption = optProducer.getAllRoleOption(resource.getSystemId());
				req.setAttribute(WebAttrConstant.ALL_ROLE_OPTION, allRoleOption);
				String viewRoleOption = optProducer.getViewRoleOption(dtoParameter.getResId());
				req.setAttribute(WebAttrConstant.VIEW_ROLE_OPTION, viewRoleOption);
				forwardURL = URLDefineList.RES_PRIVI_QUERY;

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
			message.setNeedBack(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			message.setNeedBack(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}

	private DTOSet getResPrivis(HttpServletRequest req) throws DTOException {
		DTOSet priviDTOs = new DTOSet();
		String resId = req.getParameter("systemId");
		String[] roleIds = req.getParameterValues("roleId");
		for(int i = 0; i < roleIds.length; i++){
			SfResPrivsDTO dto = new SfResPrivsDTO();
			dto.setRoleId(roleIds[i]);
			dto.setResId(StrUtil.strToInt(resId));
			priviDTOs.addDTO(dto);
		}
		return priviDTOs;
	}
}
