package com.sino.ams.system.project.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.project.dao.EtsPaProjectsAllDAO;
import com.sino.ams.system.project.dto.EtsPaProjectsAllDTO;
import com.sino.ams.system.project.model.EtsPaProjectsAllModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
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
 * <p>Title: EtsPaProjectsAllServlet</p>
 * <p>Description:程序自动生成服务程序“EtsPaProjectsAllServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class EtsPaProjectsAllServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			EtsPaProjectsAllDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsPaProjectsAllDTO.class.getName());
			dtoParameter = (EtsPaProjectsAllDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EtsPaProjectsAllDAO etsPaProjectsAllDAO = new EtsPaProjectsAllDAO(user, dtoParameter, conn);
			OptionProducer prd = new OptionProducer(user, conn);
			if (action.equals("")) {
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, dtoParameter.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, dtoParameter.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				forwardURL = URLDefineList.PROJECT_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsPaProjectsAllModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, dtoParameter.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, dtoParameter.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				forwardURL = URLDefineList.PROJECT_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
//				EtsPaProjectsAllDTO etsPaProjectsAll = (EtsPaProjectsAllDTO)req.getAttribute("详细数据属性，请根据实际情况修改");
//				if(etsPaProjectsAll == null){
//					etsPaProjectsAll= dtoParameter;
//				}
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, dtoParameter.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, dtoParameter.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				req.setAttribute(WebAttrConstant.ETS_PA_PROJECTS_ALL_DTO, dtoParameter);
				forwardURL = URLDefineList.PROJECT_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				etsPaProjectsAllDAO.setDTOClassName(EtsPaProjectsAllDTO.class.getName());
				etsPaProjectsAllDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				EtsPaProjectsAllDTO etsPaProjectsAll = (EtsPaProjectsAllDTO)etsPaProjectsAllDAO.getDataByPrimaryKey();
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, etsPaProjectsAll.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, etsPaProjectsAll.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				etsPaProjectsAllDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				if(etsPaProjectsAll == null){
					etsPaProjectsAll = new EtsPaProjectsAllDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.ETS_PA_PROJECTS_ALL_DTO, etsPaProjectsAll);
				forwardURL = URLDefineList.PROJECT_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {           //do_save操作
				etsPaProjectsAllDAO.setServletConfig(getServletConfig(req));
				etsPaProjectsAllDAO.createData();
				message = etsPaProjectsAllDAO.getMessage();
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, dtoParameter.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, dtoParameter.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				forwardURL = URLDefineList.PROJECT_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				etsPaProjectsAllDAO.setServletConfig(getServletConfig(req));
				etsPaProjectsAllDAO.updateData();
				message = etsPaProjectsAllDAO.getMessage();
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, dtoParameter.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, dtoParameter.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				forwardURL = URLDefineList.PROJECT_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				etsPaProjectsAllDAO.deleteData();
				message = etsPaProjectsAllDAO.getMessage();
				String proTypeOption = prd.getDictOption(DictConstant.PROJECT_TYPE, dtoParameter.getProjectType());
				req.setAttribute(WebAttrConstant.PROJECT_TYPE_OPTION, proTypeOption);
				String proStatOption = prd.getDictOption(DictConstant.PROJECT_STATUS, dtoParameter.getProjectStatusCode());
				req.setAttribute(WebAttrConstant.PROJECT_STATUS_OPTION, proStatOption);
				forwardURL = URLDefineList.PROJECT_QUERY_SERVLET;
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
