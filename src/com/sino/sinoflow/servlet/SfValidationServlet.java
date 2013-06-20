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
import com.sino.sinoflow.dao.SfValidationDAO;
import com.sino.sinoflow.dto.SfValidationDTO;
import com.sino.sinoflow.model.SfValidationModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfValidationServlet</p>
 * <p>Description:程序自动生成服务程序“SfValidationServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfValidationServlet extends BaseServlet {

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
			req2DTO.setDTOClassName(SfValidationDTO.class.getName());
			SfValidationDTO dtoParameter = (SfValidationDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			SfValidationDAO sfValidationDAO = new SfValidationDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				BaseSQLProducer sqlProducer = new SfValidationModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(SfValidationDTO.class.getName());
				pageDAO.produceWebData();
				DTOSet ds = (DTOSet)req.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
				sfValidationDAO.setEmpty(ds);
				forwardURL = URLDefineList.VALIDATION_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				forwardURL = URLDefineList.VALIDATION_SERVLET;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				
				/* 页面上工程的下拉列表 */
				OptionProducer op = new OptionProducer(user, conn);
				String projectOptionStr = op.getProjectOption2(null,
						WebAttrConstant.PROJECT_OPTION_STR_ALL);
				req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR,
						projectOptionStr);
				
				forwardURL = URLDefineList.VALIDATION_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				sfValidationDAO.setDTOClassName(SfValidationDTO.class.getName());
				SfValidationDTO sfValidation = (SfValidationDTO)sfValidationDAO.getDataByPrimaryKey();
				if(sfValidation == null){
					sfValidation = new SfValidationDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.VALIDATION_ATTR, sfValidation);
				OptionProducer op = new OptionProducer(user, conn);
				setVal(req,sfValidation,op);
				forwardURL = URLDefineList.VALIDATION_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				List list = new ArrayList();
				list.add(new SelectEmpty("FIELD_NAME", dtoParameter.getFieldName(),false));
				list.add(new SelectEmpty("PROJECT_NAME", dtoParameter.getProjectName(),false));
				list.add(new SelectEmpty("PROCEDURE_NAME", dtoParameter.getProcedureName(),false));
				list.add(new SelectEmpty("TASK_TID", String.valueOf(dtoParameter.getTaskTid()),true));
				
				if(!FlowTaskTool.isExist("SF_VALIDATION", list, conn)){
					sfValidationDAO.createData();
					message = this.getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
					message.addParameterValue("合法性检查");
					forwardURL = URLDefineList.VALIDATION_SERVLET;
				}else{
					message = new Message();
					message.setMessageValue("该域的合法性检查已存在");
					OptionProducer op = new OptionProducer(user, conn);
					setVal(req,dtoParameter,op);
					req.setAttribute(WebAttrConstant.VALIDATION_ATTR,dtoParameter);
					forwardURL = URLDefineList.VALIDATION_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				sfValidationDAO.updateData();
				message = this.getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
				message.addParameterValue("合法性检查");
				forwardURL = URLDefineList.VALIDATION_SERVLET;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				String[] ids = req.getParameterValues("mdc");
				if(ids != null){
					sfValidationDAO.del(ids);
					message = this.getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
					message.addParameterValue("合法性检查");
				}
				forwardURL = URLDefineList.VALIDATION_SERVLET;
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
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
	
	private void setVal(HttpServletRequest req,SfValidationDTO sfv,OptionProducer op) throws QueryException{
		
		/* 页面上工程的下拉列表 */
		String projectOptionStr = op.getProjectOption2(String.valueOf(sfv.getProjectId()),"");
		req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR,
				projectOptionStr);
		/* 页面上过程的下拉列表 */
		String prodecureOptionStr = op.getProcedureOption(sfv.getProjectId(), String.valueOf(sfv.getProcedureId()),
				WebAttrConstant.ROLE_OPTION_STR_SELECT);
		req.setAttribute(WebAttrConstant.PROJECT_PROCEDURE_OPTION,
				prodecureOptionStr);
		
		/* 页面上任务下拉列表 */
		String taskOptionStr = op.getTask(sfv.getProcedureId(), String.valueOf(sfv.getTaskTid()),
				WebAttrConstant.ROLE_OPTION_STR_SELECT);
		req.setAttribute(WebAttrConstant.PROCEDURE_TASK_OPTION_STR,
				taskOptionStr);
		List list = new ArrayList();
		if(sfv.getValidationType() >= 0){
			list = FlowTaskTool.findNum(sfv.getValidationType());
		}
		req.setAttribute("nl", list);
	}
}