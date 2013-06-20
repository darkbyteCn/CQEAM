package com.sino.ams.system.procedure.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.procedure.dao.SfProcedureDefDAO;
import com.sino.ams.system.procedure.dto.SfProcedureDefDTO;
import com.sino.ams.system.procedure.model.SfProcedureDefModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SfProcedureDefServlet</p>
 * <p>Description:程序自动生成服务程序“SfProcedureDefServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class SfProcedureDefServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			SfProcedureDefDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfProcedureDefDTO.class.getName());
			dtoParameter = (SfProcedureDefDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			SfProcedureDefDAO sfProcedureDefDAO = new SfProcedureDefDAO(user, dtoParameter, conn);


			if (action.equals("")) {
				forwardURL = URLDefineList.PROCEDURE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new SfProcedureDefModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.PROCEDURE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				SfProcedureDefDTO sfProcedureDef = (SfProcedureDefDTO) req.getAttribute(WebAttrConstant.PORCDURE_ATTR);
				if (sfProcedureDef == null) {
					sfProcedureDef = dtoParameter;
				}

				OptionProducer optionProducer = new OptionProducer(user, conn);
				String disableOption = optionProducer.getDisabled("N");
				req.setAttribute("IsDisabledOption", disableOption);

				req.setAttribute(WebAttrConstant.PORCDURE_ATTR, sfProcedureDef);
				forwardURL = URLDefineList.PROCEDURE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				sfProcedureDefDAO.setDTOClassName(SfProcedureDefDTO.class.getName());
				SfProcedureDefDTO sfProcedureDef = (SfProcedureDefDTO) sfProcedureDefDAO.getDataByPrimaryKey();
				if (sfProcedureDef == null) {
					sfProcedureDef = new SfProcedureDefDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}

				OptionProducer optionProducer = new OptionProducer(user, conn);
				String disableOption = optionProducer.getDisabled(sfProcedureDef.getDisabled());
				req.setAttribute("IsDisabledOption", disableOption);

				req.setAttribute(WebAttrConstant.PORCDURE_ATTR, sfProcedureDef);
				forwardURL = URLDefineList.PROCEDURE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				sfProcedureDefDAO.createData();
				message = sfProcedureDefDAO.getMessage();
				forwardURL = URLDefineList.PROCEDURE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				sfProcedureDefDAO.updateData();
				message = sfProcedureDefDAO.getMessage();
				forwardURL = URLDefineList.PROCEDURE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				sfProcedureDefDAO.deleteData();
				message = sfProcedureDefDAO.getMessage();
				forwardURL = URLDefineList.PROCEDURE_QUERY_PAGE;
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
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

		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
