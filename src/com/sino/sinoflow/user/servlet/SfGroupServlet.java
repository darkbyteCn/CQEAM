package com.sino.sinoflow.user.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.FlowTaskTool;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.user.dao.SfGroupDAO;
import com.sino.sinoflow.user.dto.SfGroupDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.user.model.SfGroupModel;

/**
 * Title: SfGroupServlet
 * Description:程序自动生成服务程序“SfGroupServlet”，请根据需要自行修改
 * Copyright: Copyright (c) 2007
 * Company: 北京思诺博信息技术有限公司
 * @author mshtang
 * @version 1.0
 */

public class SfGroupServlet extends BaseServlet {

	/**
	 * @param req
	 *            HttpServletRequest
	 * @param res
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */


	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(req);// 从session中获取数据，根据实际情况自行修改。
			if(!(user.isSysAdmin())){
				message = getMessage("USER.NO_AUTHORITY");
				ServletConfigDTO servletConfig = getServletConfig(req);
				message.addParameterValue(servletConfig.getSysAdminRole());
				message.addParameterValue(servletConfig.getCityAdminRole());
				forwardURL = getFilterConfig(req).getNoPriviledgeURL();
			} else {
				SfGroupDTO dtoParameter;
				Request2DTO req2DTO = new Request2DTO();
				req2DTO.setDTOClassName(SfGroupDTO.class.getName());
				dtoParameter = (SfGroupDTO) req2DTO.getDTO(req);
				conn = DBManager.getDBConnection();
				SfGroupDAO sfGroupDAO = new SfGroupDAO(user, dtoParameter, conn);

				OptionProducer op = new OptionProducer(user, conn);

				/* 页面上工程的下拉列表 */
				String projectOptionStr = op.getProjectOption(null, WebAttrConstant.PROJECT_OPTION_STR_ALL);
				req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR, projectOptionStr);
				if (action.equals("")) {
					forwardURL = URLDefineList.GROUP_QUERY_PAGE;
				} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
					BaseSQLProducer sqlProducer = new SfGroupModel(user,
												  dtoParameter);
					PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
					pageDAO.produceWebData();
					forwardURL = URLDefineList.GROUP_QUERY_PAGE;
				} else if (action.equals(WebActionConstant.NEW_ACTION)) {
					SfGroupDTO sfGroup = null;
					if (sfGroup == null) {
						sfGroup = new SfGroupDTO();
					}
					req.setAttribute(WebAttrConstant.GROUP_ATTR, sfGroup);
					forwardURL = URLDefineList.GROUP_DETAIL_PAGE;

				} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
					sfGroupDAO.setDTOClassName(SfGroupDTO.class.getName());
					SfGroupDTO sfGroup = (SfGroupDTO) sfGroupDAO
										 .getDataByPrimaryKey();
					if (sfGroup == null) {
						sfGroup = new SfGroupDTO();
						message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
						message.setIsError(true);
					}
					req.setAttribute(WebAttrConstant.GROUP_ATTR, sfGroup);

					/* 页面上工程的下拉列表 状态选中*/
					String projectOptionStrSelect = op.getProjectOption(sfGroup.getProjectName(), "");
					req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR, projectOptionStrSelect);

					/* 页面上工程下组的下拉列表 状态选中 */
					String groupOptionStr = op.getGroupOption2(String.valueOf(sfGroup.getParentId()), sfGroup.getProjectName(),
											WebAttrConstant.GROUP_OPTION_STR_SELECT);
					req.setAttribute(WebAttrConstant.GROUP_OPTION_STR, groupOptionStr);
					forwardURL = URLDefineList.GROUP_DETAIL_PAGE;
				} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
					if (!FlowTaskTool.isExist("SF_GROUP", "GROUP_NAME", dtoParameter.getGroupName(), conn)) {
//					if(!dtoParameter.getParentName().equals("")){
//						dtoParameter.setGroupName(dtoParameter.getParentName()+"."+dtoParameter.getGroupName());
//					}
//						sfGroupDAO.createData();
						boolean isSuccess = sfGroupDAO.saveGroup();
						if( isSuccess ){
							message = this.getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
							message.addParameterValue("组别");
							forwardURL = URLDefineList.GROUP_SERVLET_PAGE;
						}else{
							req.setAttribute(WebAttrConstant.GROUP_ATTR, dtoParameter);
							String projectOptionStrSelect = op.getProjectOption(dtoParameter.getProjectName(), "");
							req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR, projectOptionStrSelect);
							message = new Message();
							message.setMessageValue( sfGroupDAO.getMsg() );
							forwardURL = URLDefineList.GROUP_DETAIL_PAGE;
						}
					} else {
						req.setAttribute(WebAttrConstant.GROUP_ATTR, dtoParameter);
						String projectOptionStrSelect = op.getProjectOption(dtoParameter.getProjectName(), "");
						req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR, projectOptionStrSelect);
						message = new Message();
						message.setMessageValue("组别已存在");
						forwardURL = URLDefineList.GROUP_DETAIL_PAGE;
					}

				} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
					sfGroupDAO.updateData();
					message = this.getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
					message.addParameterValue("组别");
					forwardURL = URLDefineList.GROUP_SERVLET_PAGE;
				} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
					sfGroupDAO.deleteData();
					message = this.getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
					message.addParameterValue("组别");
					forwardURL = URLDefineList.GROUP_SERVLET_PAGE;
                }else if(action.equals("selOpt")){
                    req.setAttribute("selOpt", op.getAllGroupOption(0));
                    forwardURL = "/system/resource/selOption.jsp";
				} else {
					message = getMessage(MsgKeyConstant.INVALID_REQ);
					message.setIsError(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				}
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
		}  finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
