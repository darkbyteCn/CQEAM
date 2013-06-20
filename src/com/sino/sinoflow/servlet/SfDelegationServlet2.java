package com.sino.sinoflow.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.util.ResUtil;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
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
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.dao.SfDelegationDAO;
import com.sino.sinoflow.dto.SfDelegationDTO;
import com.sino.sinoflow.model.SfDelegationModel2;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfDelegationServlet</p>
 * <p>Description:程序自动生成服务程序“SfDelegationServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfDelegationServlet2 extends BaseServlet {

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
			req2DTO.setDTOClassName(SfDelegationDTO.class.getName());
			SfDelegationDTO dtoParameter = (SfDelegationDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			SfDelegationDAO sfDelegationDAO = new SfDelegationDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				ResUtil.setAllResName(conn, req, ResNameConstant.SF_DELEGATION );
				
				BaseSQLProducer sqlProducer = new SfDelegationModel2(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(SfDelegationDTO.class.getName());
				pageDAO.produceWebData();
				forwardURL = URLDefineList.DELEGATION_QUERY_PAGE2;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				//TODO 2011-10-10
				ResUtil.setAllResName(conn, req, ResNameConstant.SF_DELEGATION );
            	
				forwardURL = "com.sino.sinoflow.servlet.SfDelegationServlet的翻页查询JSP页面，一般在类似于URLDefineList的常量接口中定义";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				
				/* 用户下拉列表 */
				OptionProducer op = new OptionProducer(user,conn);
				String projectOptionStr = op.getDelegateUserOption(null,-1);
				req.setAttribute(WebAttrConstant.USER_OPTION_STR, projectOptionStr);
				forwardURL = URLDefineList.DELEGATION_DETAIL_PAGE2;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				sfDelegationDAO.setDTOClassName(SfDelegationDTO.class.getName());
				SfDelegationDTO sfDelegation = (SfDelegationDTO)sfDelegationDAO.getDataByPrimaryKey();
				if(sfDelegation == null){
					sfDelegation = new SfDelegationDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				/* 用户下拉列表 */
				OptionProducer op = new OptionProducer(user,conn);
				String projectOptionStr = op.getDelegateUserOption("",sfDelegation.getDelegateTo());
				req.setAttribute(WebAttrConstant.USER_OPTION_STR, projectOptionStr);
				
				req.setAttribute(WebAttrConstant.DELEGATION_ATT, sfDelegation);
				forwardURL = URLDefineList.DELEGATION_DETAIL_PAGE2;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				sfDelegationDAO.createData();
				message = getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
				message.addParameterValue("委托定义");
				forwardURL = URLDefineList.DELEGATION_SERLVET2;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				sfDelegationDAO.updateData();
				message = getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
				message.addParameterValue("委托定义");
				forwardURL = URLDefineList.DELEGATION_SERLVET2;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				String[] ids = req.getParameterValues("mdc");
				if(ids != null){
					sfDelegationDAO.del(ids);
					message = getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
					message.addParameterValue("委托定义");
				}
				forwardURL = URLDefineList.DELEGATION_SERLVET2;
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
		} catch (ContainerException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}