package com.sino.ams.system.resource.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.resource.dao.SfResDefineDAO;
import com.sino.ams.system.resource.dto.SfResDefineDTO;
import com.sino.ams.system.resource.model.SfResDefineModel;
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
 * <p>Title: SfResDefineServlet</p>
 * <p>Description:程序自动生成服务程序“SfResDefineServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) {year}</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfResDefineServlet extends BaseServlet {

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
			SfResDefineDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfResDefineDTO.class.getName());
			dtoParameter = (SfResDefineDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			SfResDefineDAO sfResDefineDAO = new SfResDefineDAO(userAccount, dtoParameter, conn);
			OptionProducer optProducer = new OptionProducer(userAccount, conn);
			if (action.equals("")) {
				String enabledOpt = optProducer.getBooleanOption(dtoParameter.getEnabled());
				req.setAttribute(WebAttrConstant.ENABLED_OPTION, enabledOpt);
				forwardURL = URLDefineList.RES_QRY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				String enabledOpt = optProducer.getBooleanOption(dtoParameter.getEnabled());
				req.setAttribute(WebAttrConstant.ENABLED_OPTION, enabledOpt);
				BaseSQLProducer sqlProducer = new SfResDefineModel(userAccount, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.RES_QRY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				SfResDefineDTO sfResource = (SfResDefineDTO)req.getAttribute(WebAttrConstant.RES_DATA);
				if(sfResource == null){
					sfResource = dtoParameter;
				}
				req.setAttribute(WebAttrConstant.RES_DATA, sfResource);
				String resourceOpt = sfResDefineDAO.getResourceOption(sfResource);
				req.setAttribute(WebAttrConstant.RESOURCE_OPTION, resourceOpt);
				forwardURL = URLDefineList.RES_DTL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				sfResDefineDAO.setDTOClassName(SfResDefineDTO.class.getName());
				SfResDefineDTO sfResource = (SfResDefineDTO)sfResDefineDAO.getDataByPrimaryKey();
				if(sfResource != null){
					String resourceOpt = sfResDefineDAO.getResourceOption(sfResource);
					req.setAttribute(WebAttrConstant.RESOURCE_OPTION, resourceOpt);
					if(sfResource.getIsInner().equals(WebAttrConstant.TRUE_VALUE)){
						message = getMessage(CustMessageKey.INNER_RES);
						message.addParameterValue(sfResource.getResName());
						message.setNeedBack(true);
					}
				} else {
					sfResource = new SfResDefineDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.RES_DATA, sfResource);
				forwardURL = URLDefineList.RES_DTL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				sfResDefineDAO.createData();
				message = sfResDefineDAO.getMessage();
				forwardURL = URLDefineList.RES_QRY_SERVLET;
				forwardURL += "&resId=" + dtoParameter.getResParId();
//				} else {
//					String resourceOpt = sfResDefineDAO.getResourceOption(dtoParameter);
//					req.setAttribute(WebAttrConstant.RESOURCE_OPTION, resourceOpt);
//					req.setAttribute(WebAttrConstant.RES_DATA, dtoParameter);
//					forwardURL = URLDefineList.RES_DTL_PAGE;
//				}
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				sfResDefineDAO.updateData();
				message = sfResDefineDAO.getMessage();
//				if(operateResult){
					forwardURL = URLDefineList.RES_QRY_SERVLET;
					forwardURL += "&resId=" + dtoParameter.getResParId();
//				} else {
//					String resourceOpt = sfResDefineDAO.getResourceOption(dtoParameter);
//					req.setAttribute(WebAttrConstant.RESOURCE_OPTION, resourceOpt);
//					req.setAttribute(WebAttrConstant.RES_DATA, dtoParameter);
//					forwardURL = URLDefineList.RES_DTL_SERVLET;
//				}
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				sfResDefineDAO.deleteData();
				message = sfResDefineDAO.getMessage();
				forwardURL = URLDefineList.RES_QRY_SERVLET;
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
			message.setNeedBack(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			message.setNeedBack(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			message.setNeedBack(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally{
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
