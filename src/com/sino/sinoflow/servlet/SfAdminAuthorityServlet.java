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
import com.sino.base.db.conn.DBManager;
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
import com.sino.sinoflow.dao.SfAdminAuthorityDAO;
import com.sino.sinoflow.dto.SfAdminAuthorityDTO;
import com.sino.sinoflow.model.SfAdminAuthorityModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfAdminAuthorityServlet</p>
 * <p>Description:程序自动生成服务程序“SfAdminAuthorityServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfAdminAuthorityServlet extends BaseServlet {

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
			req2DTO.setDTOClassName(SfAdminAuthorityDTO.class.getName());
			SfAdminAuthorityDTO dtoParameter = (SfAdminAuthorityDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			SfAdminAuthorityDAO sfAdminAuthorityDAO = new SfAdminAuthorityDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				BaseSQLProducer sqlProducer = new SfAdminAuthorityModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(SfAdminAuthorityDTO.class.getName());
				pageDAO.produceWebData();
				forwardURL = URLDefineList.ADMIN_AUTHORITY_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				forwardURL = "";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				/*工程下拉列表*/
				OptionProducer op = new OptionProducer(user,conn);
				String projectOptionStr = op.getProjectOption(null,WebAttrConstant.PROJECT_OPTION_STR_ALL);
				req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR, projectOptionStr);
				forwardURL = URLDefineList.ADMIN_AUTHORITY_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				sfAdminAuthorityDAO.setDTOClassName(SfAdminAuthorityDTO.class.getName());
				SfAdminAuthorityDTO dto = (SfAdminAuthorityDTO)sfAdminAuthorityDAO.getDataByPrimaryKey();
				if(dto == null){
					dto = new SfAdminAuthorityDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.ADMINAUTHORITY_ATTR, dto);

				/*工程下拉列表*/
				OptionProducer op = new OptionProducer(user,conn);
				String projectOptionStr = op.getProjectOption(dto.getProjectName(),"");
				req.setAttribute(WebAttrConstant.PROJECT_OPTION_STR, projectOptionStr);

				/* 组别下拉列表 */
				String groupOptionStr = op.getGroupOption(dto.getGroupName(),
						dto.getProjectName()
						,WebAttrConstant.GROUP_OPTION_STR_SELECT, dto.getOrgId());
				req.setAttribute(WebAttrConstant.PROJECT_GROUP_OPTION_STR, groupOptionStr);

				forwardURL = URLDefineList.ADMIN_AUTHORITY_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {//保存
				String uid = req.getParameter("uId");
				String[] str = sfAdminAuthorityDAO.spliptStr(uid);
				add(str,dtoParameter,sfAdminAuthorityDAO,conn);
				message = this.getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
				message.addParameterValue("工程 管理员");
				forwardURL = URLDefineList.ADMIN_AUTHORITY_SERVLET;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				String uid = req.getParameter("uId");
				String[] str = sfAdminAuthorityDAO.spliptStr(uid);

				sfAdminAuthorityDAO.del(new String[]{String.valueOf(dtoParameter.getAdminId())});
				add(str,dtoParameter,sfAdminAuthorityDAO,conn);

				message = this.getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
				message.addParameterValue("工程 管理员");
				res.sendRedirect(URLDefineList.ADMIN_AUTHORITY_SERVLET);
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				String[] ids = req.getParameterValues("mdc");
				if(ids != null){
					sfAdminAuthorityDAO.del(ids);
					message = this.getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
					message.addParameterValue("工程 管理员");
				}
				forwardURL = URLDefineList.ADMIN_AUTHORITY_SERVLET;
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

	private void add(String[] str,SfAdminAuthorityDTO dtoParameter,
			SfAdminAuthorityDAO saad,Connection conn) throws QueryException, DataHandleException{
		if(str != null){
			for(int i=0;i<str.length;i++){
				dtoParameter.setLoginName(str[i]);
				List list = new ArrayList();
/*
				list.add(new SelectEmpty("USER_ID",str[i],true));
				list.add(new SelectEmpty("PROJECT_NAME",dtoParameter.getProjectName(),false));
				list.add(new SelectEmpty("GROUP_NAME",dtoParameter.getGroupName(),false));
*/

				list.add(new SelectEmpty("SU.LOGIN_NAME",str[i],false));
				list.add(new SelectEmpty("SAU.PROJECT_NAME",dtoParameter.getProjectName(),false));
				list.add(new SelectEmpty("SAU.GROUP_NAME",dtoParameter.getGroupName(),false));
				list.add(new SelectEmpty("SAU.USER_ID","SU.USER_ID",true));
				if(!FlowTaskTool.isExist("SF_ADMIN_AUTHORITY SAU, SF_USER SU",list,conn)){

//                if(!FlowTaskTool.isExist("SF_ADMIN_AUTHORITY",list,conn)) {
//                    saad.createData();
					saad.add(dtoParameter.getLoginName());
				}
			}
		}
	}
}
