package com.sino.ams.dzyh.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.dzyh.dao.EamDhCatalogSetDAO;
import com.sino.ams.dzyh.dto.EamDhCatalogSetDTO;
import com.sino.ams.dzyh.model.EamDhCatalogSetModel;
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
 * <p>Title: EamDhCatalogSetServlet</p>
 * <p>Description:程序自动生成服务程序“EamDhCatalogSetServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */


public class EamDhCatalogSetServlet extends BaseServlet {

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
			EamDhCatalogSetDTO dtoParameter = null ;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EamDhCatalogSetDTO.class.getName());
			dtoParameter = (EamDhCatalogSetDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EamDhCatalogSetDAO eamDhCatalogSetDAO = new EamDhCatalogSetDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				WebRadio webRadio = new WebRadio("enabled");
				webRadio.addValueCaption("1", "是");
				webRadio.addValueCaption("0", "否");
				webRadio.setCheckedValue(String.valueOf(dtoParameter.getEnabled()));
				req.setAttribute("ENABLED_RADIO", webRadio.toString());
				forwardURL = URLDefineList.DZYHCATALOG_TYPE_QRY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EamDhCatalogSetModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				WebRadio webRadio = new WebRadio("enabled");
				webRadio.addValueCaption("1", "是");
				webRadio.addValueCaption("0", "否");
				webRadio.setCheckedValue(String.valueOf(dtoParameter.getEnabled()));
				req.setAttribute("ENABLED_RADIO", webRadio.toString());
				forwardURL = URLDefineList.DZYHCATALOG_TYPE_QRY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				dtoParameter.setEnabled(WebAttrConstant.DZYH_TRUE_VALUE);
				WebRadio webRadio = new WebRadio("enabled");
				webRadio.addValueCaption("1", "是");
				webRadio.addValueCaption("0", "否");
				webRadio.setCheckedValue(String.valueOf(dtoParameter.getEnabled()));
				req.setAttribute("ENABLED_RADIO", webRadio.toString());
				
				EamDhCatalogSetDTO eamDhCatalogSet = (EamDhCatalogSetDTO)req.getAttribute(WebAttrConstant.DZYH_TYPE_DATA);
				if(eamDhCatalogSet == null){
					eamDhCatalogSet= dtoParameter;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.dzyh.dto.EamDhCatalogSetDTO的构造函数确定
				}
				req.setAttribute(WebAttrConstant.DZYH_TYPE_DATA, eamDhCatalogSet);
				forwardURL = URLDefineList.DZYHCATALOG_TYPE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				eamDhCatalogSetDAO.setDTOClassName(EamDhCatalogSetDTO.class.getName());
				EamDhCatalogSetDTO eamDhCatalogSet = (EamDhCatalogSetDTO)eamDhCatalogSetDAO.getDataByPrimaryKey();
				if(eamDhCatalogSet == null){
					eamDhCatalogSet = new EamDhCatalogSetDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}else{
					WebRadio webRadio = new WebRadio("enabled");
					webRadio.addValueCaption("1", "是");
					webRadio.addValueCaption("0", "否");
					webRadio.setCheckedValue(String.valueOf(dtoParameter.getEnabled()));
					req.setAttribute("ENABLED_RADIO", webRadio.toString());
					req.setAttribute(WebAttrConstant.DZYH_TYPE_DATA, eamDhCatalogSet);
					forwardURL = URLDefineList.DZYHCATALOG_TYPE_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				ServletConfigDTO servletConfig = getServletConfig(req);
				eamDhCatalogSetDAO.setServletConfig(servletConfig);
				eamDhCatalogSetDAO.createData();
				forwardURL = URLDefineList.DZYHCATALOG_TYPE_QRY_SERVLET;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				ServletConfigDTO servletConfig = getServletConfig(req);
				eamDhCatalogSetDAO.setServletConfig(servletConfig);
				eamDhCatalogSetDAO.updateData();
				forwardURL = URLDefineList.DZYHCATALOG_TYPE_QRY_SERVLET;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				ServletConfigDTO servletConfig = getServletConfig(req);
				eamDhCatalogSetDAO.setServletConfig(servletConfig);
				eamDhCatalogSetDAO.deleteData();
				forwardURL = URLDefineList.DZYHCATALOG_TYPE_QRY_SERVLET;
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
			ex.printLog();
		} catch (StrException e) {
			
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}