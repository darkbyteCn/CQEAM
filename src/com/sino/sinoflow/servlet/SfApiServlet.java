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
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
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
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.dao.SfApiDAO;
import com.sino.sinoflow.dto.SfApiDTO;
import com.sino.sinoflow.model.SfApiModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfApiServlet</p>
 * <p>Description:程序自动生成服务程序“SfApiServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 */


public class SfApiServlet extends BaseServlet {

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
			req2DTO.setDTOClassName(SfApiDTO.class.getName());
			SfApiDTO dtoParameter = (SfApiDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			SfApiDAO sfApiDAO = new SfApiDAO(user, dtoParameter, conn);
			if (action.equals("")) {
/*
                BaseSQLProducer sqlProducer = new SfApiModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(SfApiDTO.class.getName());
				pageDAO.produceWebData();
				sfApiDAO.operation((DTOSet)req.getAttribute(QueryConstant.SPLIT_DATA_VIEW));
*/

                SQLModel sqlModel = new SfApiModel(user, dtoParameter).getPageQueryModel();
                SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
                DTOSet dtoSet;
                simpleQuery.setDTOClassName(SfApiDTO.class.getName());
                simpleQuery.executeQuery();
                dtoSet = simpleQuery.getDTOSet();
                req.setAttribute(QueryConstant.SPLIT_DATA_VIEW, dtoSet);
                sfApiDAO.operation((DTOSet)req.getAttribute(QueryConstant.SPLIT_DATA_VIEW));

                forwardURL = URLDefineList.API_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				forwardURL = URLDefineList.API_SERVLET;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				/* 页面上工程的下拉列表 */
				OptionProducer op = new OptionProducer(user, conn);
				String projectOptionStr = op.getProjectOption2(null,
						WebAttrConstant.PROJECT_OPTION_STR_ALL);
				req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR,
						projectOptionStr);
				forwardURL = URLDefineList.API_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				sfApiDAO.setDTOClassName(SfApiDTO.class.getName());
				SfApiDTO sfApi = (SfApiDTO)sfApiDAO.getDataByPrimaryKey();
				if(sfApi == null){
					sfApi = new SfApiDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.API_ATTR, sfApi);
				OptionProducer op = new OptionProducer(user, conn);
				setVal(req,sfApi,op);
				forwardURL = URLDefineList.API_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				
				List list = new ArrayList();
				list.add(new SelectEmpty("API_NAME", dtoParameter.getApiName(),false));
				list.add(new SelectEmpty("PROJECT_NAME", dtoParameter.getProjectName(),false));
				list.add(new SelectEmpty("PROCEDURE_NAME", dtoParameter.getProcedureName(),false));
				list.add(new SelectEmpty("TASK_TID", String.valueOf(dtoParameter.getTaskTid()),true));
				
				if(!FlowTaskTool.isExist("SF_API", list, conn)){
					message = this.getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
					message.addParameterValue("接口程序");
					sfApiDAO.createData();
					forwardURL = URLDefineList.API_SERVLET;
				}else{
					message = new Message();
					message.setMessageValue("接口程序已存在");
					req.setAttribute(WebAttrConstant.API_ATTR, dtoParameter);
					forwardURL = URLDefineList.API_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				sfApiDAO.updateData();
				message = this.getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
				message.addParameterValue("接口程序");
				forwardURL = URLDefineList.API_SERVLET;
            } else if (action.equals(WebActionConstant.CHECK_ACTION)) {
                String ids[] = req.getParameterValues("mdc");
                if(ids != null){
                    sfApiDAO.enabled(ids);
                    message = new Message();
                    message.setMessageValue("接口程序已恢复");
                    message.addParameterValue("接口程序");
                }
                forwardURL = URLDefineList.API_SERVLET;
            } else if (action.equals(WebActionConstant.DISABLED_ACTION)) {
                String ids[] = req.getParameterValues("mdc");
                if(ids != null){
                    sfApiDAO.disabled(ids);
                    message = new Message();
                    message.setMessageValue("接口程序已暫停");
                    message.addParameterValue("接口程序");
                }
                forwardURL = URLDefineList.API_SERVLET;
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				String ids[] = req.getParameterValues("mdc");
				if(ids != null){
					sfApiDAO.del(ids);
					message = this.getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
					message.addParameterValue("接口程序");
				}
				forwardURL = URLDefineList.API_SERVLET;
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
	
	private void setVal(HttpServletRequest req,SfApiDTO sfApi,OptionProducer op) throws QueryException{
		
		/* 页面上工程的下拉列表 */
		String projectOptionStr = op.getProjectOption2(String.valueOf(sfApi.getProjectId()),"");
		req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR,
				projectOptionStr);
		/* 页面上过程的下拉列表 */
/*
        String prodecureOptionStr = op.getProcedureOption(sfApi.getProjectId(), sfApi.getProcedureId(),
//                WebAttrConstant.PROCEDURE_OPTION_STR_SELECT);
                WebAttrConstant.ROLE_OPTION_STR_SELECT);
        req.setAttribute(WebAttrConstant.PROJECT_PROCEDURE_OPTION,
				prodecureOptionStr);
*/
		/* 页面上任务下拉列表 */
/*		String taskOptionStr = op.getTask(sfApi.getProcedureId(), sfApi.getTaskTid(),
				WebAttrConstant.ROLE_OPTION_STR_SELECT);
		req.setAttribute(WebAttrConstant.PROCEDURE_TASK_OPTION_STR,
				taskOptionStr);
*/
        String apiOptionStr = op.getApi(sfApi.getApiId(), sfApi.getApiName(), WebAttrConstant.API_OPTION_STR);
        req.setAttribute(WebAttrConstant.API_OPTION_STR, apiOptionStr);

        List list = new ArrayList();
		if(sfApi.getApiControl() >= 0){
			list = FlowTaskTool.findNum(sfApi.getApiControl());
		}
		req.setAttribute("nl", list);
	}
	
}
