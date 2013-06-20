package com.sino.ams.system.dict.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.dict.dao.EtsFlexValueSetDAO;
import com.sino.ams.system.dict.dto.EtsFlexValueSetDTO;
import com.sino.ams.system.dict.model.EtsFlexValueSetModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.DateConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.WebRadio;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsFlexValueSetServlet</p>
 * <p>Description:程序自动生成服务程序“EtsFlexValueSetServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class EtsFlexValueSetServlet extends BaseServlet {

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
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			EtsFlexValueSetDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsFlexValueSetDTO.class.getName());
			dtoParameter = (EtsFlexValueSetDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EtsFlexValueSetDAO flexValueSetDAO = new EtsFlexValueSetDAO(user, dtoParameter, conn);
			if (action.equals("")) {

				setRadioProp(req, dtoParameter);
				forwardURL = URLDefineList.DICT_TYPE_QRY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				setRadioProp(req, dtoParameter);
				BaseSQLProducer sqlProducer = new EtsFlexValueSetModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(DateConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.DICT_TYPE_QRY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				
				dtoParameter.setEnabled(WebAttrConstant.TRUE_VALUE);
				dtoParameter.setIsInner(WebAttrConstant.TRUE_VALUE);
				dtoParameter.setMaintainFlag(WebAttrConstant.TRUE_VALUE);
				
				setRadioProp(req, dtoParameter);
				EtsFlexValueSetDTO etsFlexValueSet = (EtsFlexValueSetDTO)req.getAttribute(WebAttrConstant.DICT_TYPE_DATA);
				if(etsFlexValueSet == null){
					etsFlexValueSet= dtoParameter;
				}
				req.setAttribute(WebAttrConstant.DICT_TYPE_DATA, etsFlexValueSet);
				forwardURL = URLDefineList.DICT_TYPE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				flexValueSetDAO.setDTOClassName(EtsFlexValueSetDTO.class.getName());
				EtsFlexValueSetDTO etsFlexValueSet = (EtsFlexValueSetDTO)flexValueSetDAO.getDataByPrimaryKey();
				if(etsFlexValueSet == null){
					etsFlexValueSet = new EtsFlexValueSetDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				} else {
					setRadioProp(req, dtoParameter);
					req.setAttribute(WebAttrConstant.DICT_TYPE_DATA, etsFlexValueSet);
					forwardURL = URLDefineList.DICT_TYPE_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				ServletConfigDTO servletConfig = getServletConfig(req);
				flexValueSetDAO.setServletConfig(servletConfig);
				flexValueSetDAO.createData();
				message = flexValueSetDAO.getMessage();
				forwardURL = URLDefineList.DICT_TYPE_QRY_SERVLET;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				ServletConfigDTO servletConfig = getServletConfig(req);
				flexValueSetDAO.setServletConfig(servletConfig);
				flexValueSetDAO.updateData();
				message = flexValueSetDAO.getMessage();
				forwardURL = URLDefineList.DICT_TYPE_QRY_SERVLET;
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
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			/** @todo Handle this exception */
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}

	/**
	 * 功能：设置Web页面的Radio域
	 * @param req HttpServletRequest
	 * @param dto EtsFlexValueSetDTO
	 * @throws StrException
	 */
	private void setRadioProp(HttpServletRequest req, EtsFlexValueSetDTO dto) throws StrException {
		WebRadio webRadio = new WebRadio("enabled");
		webRadio.addValueCaption("Y", "是");
		webRadio.addValueCaption("N", "否");
		webRadio.setCheckedValue(dto.getEnabled());
		req.setAttribute(WebAttrConstant.ENABLED_RADIO, webRadio.toString());
		webRadio.setRadioName("isInner");
		webRadio.setCheckedValue(dto.getIsInner());
		req.setAttribute(WebAttrConstant.IS_INNER_RADIO, webRadio.toString());
		webRadio.setRadioName("maintainFlag");
		webRadio.setCheckedValue(dto.getMaintainFlag());
		req.setAttribute(WebAttrConstant.MAINTAIN_RADIO, webRadio.toString());
	}
}
