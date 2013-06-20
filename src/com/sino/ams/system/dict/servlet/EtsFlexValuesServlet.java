package com.sino.ams.system.dict.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.dict.dao.EtsFlexValuesDAO;
import com.sino.ams.system.dict.dto.EtsFlexValuesDTO;
import com.sino.ams.system.dict.model.EtsFlexValuesModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
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
 * <p>Title: EtsFlexValuesServlet</p>
 * <p>Description:程序自动生成服务程序“EtsFlexValuesServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class EtsFlexValuesServlet extends BaseServlet {

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
			EtsFlexValuesDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsFlexValuesDTO.class.getName());
			dtoParameter = (EtsFlexValuesDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EtsFlexValuesDAO flexValuesDAO = new EtsFlexValuesDAO(user, dtoParameter, conn);
			OptionProducer optProducer = new OptionProducer(user, conn);
			if (action.equals("")) {
				WebRadio webRadio = new WebRadio("isInner");
				webRadio.addValueCaption("Y", "是");
				webRadio.addValueCaption("N", "否");
				webRadio.setCheckedValue(dtoParameter.getIsInner());
				req.setAttribute(WebAttrConstant.IS_INNER_RADIO, webRadio.toString());
				webRadio.setRadioName("enabled");
				webRadio.setCheckedValue(dtoParameter.getEnabled());
				req.setAttribute(WebAttrConstant.ENABLED_RADIO, webRadio.toString());
				forwardURL = URLDefineList.DICT_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsFlexValuesModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				WebRadio webRadio = new WebRadio("isInner");
				webRadio.addValueCaption("Y", "是");
				webRadio.addValueCaption("N", "否");
				webRadio.setCheckedValue(dtoParameter.getIsInner());
				req.setAttribute(WebAttrConstant.IS_INNER_RADIO, webRadio.toString());
				webRadio.setRadioName("enabled");
				webRadio.setCheckedValue(dtoParameter.getEnabled());
				req.setAttribute(WebAttrConstant.ENABLED_RADIO, webRadio.toString());
				forwardURL = URLDefineList.DICT_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EtsFlexValuesDTO flexValues = (EtsFlexValuesDTO)req.getAttribute(WebAttrConstant.DICT_DATA);
				if(flexValues == null){
					flexValues= dtoParameter;
				}
				req.setAttribute(WebAttrConstant.DICT_DATA, flexValues);

				dtoParameter.setEnabled(WebAttrConstant.TRUE_VALUE);
				WebRadio webRadio = new WebRadio("enabled");
				webRadio.addValueCaption("Y", "是");
				webRadio.addValueCaption("N", "否");
				webRadio.setCheckedValue(dtoParameter.getEnabled());
				req.setAttribute(WebAttrConstant.ENABLED_RADIO, webRadio.toString());
				webRadio.setRadioName("isInner");
				webRadio.setCheckedValue(dtoParameter.getIsInner());
				req.setAttribute(WebAttrConstant.IS_INNER_RADIO, webRadio.toString());				
				String dictParent = optProducer.getDictParentOption(String.valueOf(dtoParameter.getFlexValueSetId()));
				req.setAttribute(WebAttrConstant.DICT_PARENT_OPT, dictParent);
				forwardURL = URLDefineList.DICT_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				flexValuesDAO.setDTOClassName(EtsFlexValuesDTO.class.getName());
				EtsFlexValuesDTO flexValues = (EtsFlexValuesDTO)flexValuesDAO.getDataByPrimaryKey();
				if(flexValues == null){
					flexValues = new EtsFlexValuesDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				} else {
					req.setAttribute(WebAttrConstant.DICT_DATA, flexValues);
					if(flexValues.getIsInner().equals(WebAttrConstant.TRUE_VALUE)){
						message = getMessage(CustMessageKey.INNER_DICT);
						message.addParameterValue(flexValues.getFlexValueSetName());
						message.setNeedBack(true);
					}
					WebRadio webRadio = new WebRadio("enabled");
					webRadio.addValueCaption("Y", "是");
					webRadio.addValueCaption("N", "否");
					webRadio.setCheckedValue(flexValues.getEnabled());
					req.setAttribute(WebAttrConstant.ENABLED_RADIO, webRadio.toString());
					webRadio.setRadioName("isInner");
					webRadio.setCheckedValue(flexValues.getIsInner());
					req.setAttribute(WebAttrConstant.IS_INNER_RADIO, webRadio.toString());
					String dictParent = optProducer.getDictParentOption(String.valueOf(flexValues.getFlexValueSetId()));
					req.setAttribute(WebAttrConstant.DICT_PARENT_OPT, dictParent);
					forwardURL = URLDefineList.DICT_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				ServletConfigDTO servletConfig = getServletConfig(req);
				flexValuesDAO.setServletConfig(servletConfig);
				flexValuesDAO.createData();
				message = flexValuesDAO.getMessage();
				forwardURL = URLDefineList.DICT_QUERYRY_SERVLET;

			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				ServletConfigDTO servletConfig = getServletConfig(req);
				flexValuesDAO.setServletConfig(servletConfig);
				flexValuesDAO.updateData();
				message = flexValuesDAO.getMessage();
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
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
