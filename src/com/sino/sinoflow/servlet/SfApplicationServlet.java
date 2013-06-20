package com.sino.sinoflow.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.Row;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.FlowTaskTool;
import com.sino.base.util.SelectEmpty;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.dao.SfApplicationDAO;
import com.sino.sinoflow.dao.SfTaskDAO;
import com.sino.sinoflow.dto.SfApplicationDTO;
import com.sino.sinoflow.model.SfApplicationModel;
import com.sino.sinoflow.model.SfApplicationPageQueryModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * Title: SinoApplication
 * Description: Java Enterprise Edition 平台应用开发基础框架
 * Copyright: 唐明胜版权所有Copyright (c) 2003~2007。
 * Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。
 * Copyright: 作者授权北京思诺博信息技术有限公司在一定范围内使用
 * Company: 北京思诺博信息技术有限公司
 * @author 白嘉
 * @version 0.1
 * @time 2008.9.8
 */
public class SfApplicationServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(req);
			SfApplicationDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfApplicationDTO.class.getName());
			dtoParameter = (SfApplicationDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			SfApplicationDAO sfAppDAO = new SfApplicationDAO(user,
					dtoParameter, conn);
			OptionProducer op = new OptionProducer(user, conn);
			if (action.equals("")) {
				SfApplicationModel sfm = new SfApplicationModel(user, dtoParameter);
				
//				WebPageView pageView = new WebPageView(req, conn);
//				pageView.setDTOClassName(SfApplicationDTO.class.getName());
//				pageView.produceWebData(sfm.getPageModel());
				
				
				BaseSQLProducer sqlProducer = new SfApplicationPageQueryModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(SfApplicationDTO.class.getName());
				pageDAO.produceWebData();
				
				forwardURL = URLDefineList.APPLICATION_QUERY_PAGE;
				
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				/* 页面上工程的下拉列表 */
				String projectOptionStr = op.getProjectOption2(null,
						WebAttrConstant.PROJECT_OPTION_STR_ALL);
				req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR,
						projectOptionStr);
				forwardURL = URLDefineList.APPLICATION_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				dtoParameter.setAppId(Integer.parseInt(req.getParameter("appId")));
				sfAppDAO.setDTOClassName(SfApplicationDTO.class.getName());
				SfApplicationDTO sfa = (SfApplicationDTO)sfAppDAO.getDataByPrimaryKey();
				if(sfa == null){
					sfa = new SfApplicationDTO();
				}
				List list = new ArrayList();
				if(sfa.getAllowOperation() > 0){
					list = FlowTaskTool.findNum(sfa.getAllowOperation());
				}
				
				sfa.setCategoryName(FlowTaskTool.escapeHTML(sfa.getCategoryName()));
				sfa.setAppName(FlowTaskTool.escapeHTML(sfa.getAppName()));
				/* 页面上工程的下拉列表 */
				String projectOptionStr = op.getProjectOption2(String.valueOf(sfa.getProjectId()),"");
				req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR,
						projectOptionStr);
				
				/* 工程下所对应的过程 */
				String projectProcedure = op.getProcedureOption2(
						sfa.getProjectId(),sfa.getProcedureName(),WebAttrConstant.ROLE_OPTION_STR_SELECT);
				req.setAttribute(WebAttrConstant.PROJECT_PROCEDURE_OPTION,projectProcedure );
				
				req.setAttribute("nl", list);
				req.setAttribute(WebAttrConstant.APP_ATTR,sfa);
				forwardURL = URLDefineList.APPLICATION_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				
					if(dtoParameter.getIsFlowProcess().equals("Y")){
						Row row = new SfTaskDAO(user,null,conn).
							getFirstTask(dtoParameter.getProjectName(),
								dtoParameter.getProcedureName());
						dtoParameter.setUrl(row.isEmpty() ? "" : row.getStrValue("URL"));
						dtoParameter.setGroupName(row.isEmpty() ? "" :row.getStrValue("GROUP_NAME"));
						dtoParameter.setRoleName(row.isEmpty() ? "" :row.getStrValue("ROLE_NAME"));
					}else if(dtoParameter.getIsFlowProcess().equals("N")){
						dtoParameter.setWindowType(Integer.parseInt(StrUtil.nullToString(req.getParameter("window_Type"))));
						dtoParameter.setViewInList(StrUtil.nullToString(req.getParameter("view_In_List")));
						dtoParameter.setIsFlowProcess(StrUtil.nullToString(req.getParameter("is_Flow_Process")));
						dtoParameter.setAppName(StrUtil.nullToString(req.getParameter("app_Name")));
						dtoParameter.setProjectName(StrUtil.nullToString(req.getParameter("hiProjectName")));
						dtoParameter.setGroupName(StrUtil.nullToString(req.getParameter("hiGroupName")));
						dtoParameter.setRoleName(StrUtil.nullToString(req.getParameter("hiRoleName")));
					}
					
					List list = new ArrayList();
					list.add(new SelectEmpty("APP_NAME",dtoParameter.getAppName(),false));
					list.add(new SelectEmpty("PROJECT_NAME",dtoParameter.getProjectName(),false));
					list.add(new SelectEmpty("PROCEDURE_NAME",dtoParameter.getProcedureName(),false));
					if(!FlowTaskTool.isExist("SF_APPLICATION", list, conn)){
						sfAppDAO.createData();				
						message = this.getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
						message.addParameterValue("应用定义");
						forwardURL = URLDefineList.APPLICATION_SERVLET;
					}else{
						message = new Message();
						message.setMessageValue("应用定义已存在");
						req.setAttribute(WebAttrConstant.APP_ATTR,dtoParameter);
						
						/* 页面上工程的下拉列表 */
						String projectOptionStr = op.getProjectOption2(String.valueOf(dtoParameter.getProjectId()),"");	
						req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR,
								projectOptionStr);
						
						forwardURL = URLDefineList.APPLICATION_DETAIL_PAGE;
					}
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				if(dtoParameter.getIsFlowProcess().equals("Y")){
					Row row = new SfTaskDAO(user,null,conn).
						getFirstTask(dtoParameter.getProjectName(),
							dtoParameter.getProcedureName());
                    if(dtoParameter.getUrl().equals("")) {
                        dtoParameter.setUrl(row.isEmpty() ? "" : row.getStrValue("URL"));
                    }
                    dtoParameter.setGroupName(row.isEmpty() ? "" :row.getStrValue("GROUP_NAME"));
					dtoParameter.setRoleName(row.isEmpty() ? "" :row.getStrValue("ROLE_NAME"));
				}else if(dtoParameter.getIsFlowProcess().equals("N")){
					dtoParameter.setWindowType(Integer.parseInt(StrUtil.nullToString(req.getParameter("window_Type"))));
					dtoParameter.setViewInList(StrUtil.nullToString(req.getParameter("view_In_List")));
					dtoParameter.setIsFlowProcess(StrUtil.nullToString(req.getParameter("is_Flow_Process")));
					dtoParameter.setAppName(StrUtil.nullToString(req.getParameter("app_Name")));
					dtoParameter.setProjectName(StrUtil.nullToString(req.getParameter("hiProjectName")));
					dtoParameter.setGroupName(StrUtil.nullToString(req.getParameter("hiGroupName")));
					dtoParameter.setRoleName(StrUtil.nullToString(req.getParameter("hiRoleName")));
				}
				sfAppDAO.updateData();
				message = this.getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
				message.addParameterValue("应用定义");
                forwardURL = URLDefineList.APPLICATION_SERVLET;
//                res.sendRedirect(URLDefineList.APPLICATION_SERVLET+"&appId="+dtoParameter.getAppId());
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				String[] ids = req.getParameterValues("mdc");
				if(ids != null){
					sfAppDAO.delApp(ids);
					message = this.getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
					message.addParameterValue("应用定义");
				}
				forwardURL = URLDefineList.APPLICATION_SERVLET;
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
		}
	}

}
