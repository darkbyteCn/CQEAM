package com.sino.sinoflow.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.FlowTaskTool;
import com.sino.base.util.SelectEmpty;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.dao.SfAutoValueDAO;
import com.sino.sinoflow.dto.SfAutoValueDTO;
import com.sino.sinoflow.model.SfAutoValueModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfAutoValueServlet</p>
 * <p>Description:程序自动生成服务程序“SfAutoValueServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfAutoValueServlet extends BaseServlet {

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
			req2DTO.setDTOClassName(SfAutoValueDTO.class.getName());
			SfAutoValueDTO dtoParameter = (SfAutoValueDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			SfAutoValueDAO sfAutoValueDAO = new SfAutoValueDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				BaseSQLProducer sqlProducer = new SfAutoValueModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(SfAutoValueDTO.class.getName());
				pageDAO.produceWebData();
				sfAutoValueDAO.setAssignOn((DTOSet)req.getAttribute(QueryConstant.SPLIT_DATA_VIEW));
				forwardURL = URLDefineList.AUTO_VALUE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				forwardURL = URLDefineList.AUTO_VALUE_SERLVET;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				/* 页面上工程的下拉列表 */
				OptionProducer op = new OptionProducer(user, conn);
				String projectOptionStr = op.getProjectOption2(null,
						WebAttrConstant.PROJECT_OPTION_STR_ALL);
				req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR,
						projectOptionStr);
				forwardURL = URLDefineList.AUTO_VALUE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
 				sfAutoValueDAO.setDTOClassName(SfAutoValueDTO.class.getName());
				SfAutoValueDTO sfAutoValue = (SfAutoValueDTO)sfAutoValueDAO.getDataByPrimaryKey();
				if(sfAutoValue == null){
					sfAutoValue = new SfAutoValueDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				OptionProducer op = new OptionProducer(user, conn);
				setVal(req,sfAutoValue,op);
				req.setAttribute(WebAttrConstant.AUTOVALUE_ATTR, sfAutoValue);
				forwardURL = URLDefineList.AUTO_VALUE_DETAIL_PAGE;
				
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				
				List list = new ArrayList();
				list.add(new SelectEmpty("FIELD_NAME", dtoParameter.getFieldName(),false));
				list.add(new SelectEmpty("PROJECT_NAME", dtoParameter.getProjectName(),false));
				list.add(new SelectEmpty("PROCEDURE_NAME", dtoParameter.getProcedureName(),false));
				list.add(new SelectEmpty("TASK_TID", String.valueOf(dtoParameter.getTaskTid()),true));
				if(!FlowTaskTool.isExist("SF_AUTO_VALUE", list, conn)){
					sfAutoValueDAO.createData();
					message = this.getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
					message.addParameterValue("自动赋值");
					forwardURL = URLDefineList.AUTO_VALUE_SERLVET;
				}else{
					message = new Message();
					message.setMessageValue("该信息已存在");
					req.setAttribute(WebAttrConstant.AUTOVALUE_ATTR, dtoParameter);
					forwardURL = URLDefineList.AUTO_VALUE_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				sfAutoValueDAO.updateData();
				forwardURL = URLDefineList.AUTO_VALUE_SERLVET;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				String ids[] = req.getParameterValues("mdc");
				if(ids != null){
					sfAutoValueDAO.del(ids);
					message = this.getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
					message.addParameterValue("自动赋值");
				}
				forwardURL = URLDefineList.AUTO_VALUE_SERLVET;
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
			message = getMessage(MsgKeyConstant.CREATE_DATA_FAILURE);
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
	
	private void setVal(HttpServletRequest req,SfAutoValueDTO sav,OptionProducer op) throws QueryException{
		
		/* 页面上工程的下拉列表 */
		String projectOptionStr = op.getProjectOption2(String.valueOf(sav.getProjectId()),"");
		req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR,
				projectOptionStr);
		/* 页面上过程的下拉列表 */
		String prodecureOptionStr = op.getProcedureOption(sav.getProjectId(), String.valueOf(sav.getProcedureId()),
				WebAttrConstant.ROLE_OPTION_STR_SELECT);
		req.setAttribute(WebAttrConstant.PROJECT_PROCEDURE_OPTION,
				prodecureOptionStr);
		
		/* 页面上任务下拉列表 */
		String taskOptionStr = op.getTask(sav.getProcedureId(), String.valueOf(sav.getTaskTid()),
				WebAttrConstant.ROLE_OPTION_STR_SELECT);
		req.setAttribute(WebAttrConstant.PROCEDURE_TASK_OPTION_STR,
				taskOptionStr);
		
		List list = new ArrayList();
//		if(sav.getAssignOn()!=null && !sav.getAssignOn().trim().equals("")){
        if(sav.getAssignOn() >= 0) {
            list = FlowTaskTool.findNum(sav.getAssignOn());
		}
		req.setAttribute("nl", list);
	}
}