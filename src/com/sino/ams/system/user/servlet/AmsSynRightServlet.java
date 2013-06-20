package com.sino.ams.system.user.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.base.message.Message;
import com.sino.base.dto.Request2DTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.AmsSynRightDTO;
import com.sino.ams.system.user.dao.AmsSynRightDAO;
import com.sino.ams.system.user.model.AmsSynRightModel;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2009-6-12
 * Time: 11:20:01
 * To change this template use File | Settings | File Templates.
 */
public class AmsSynRightServlet extends BaseServlet {

/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
 * @throws java.io.IOException
 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsSynRightDTO.class.getName());
			AmsSynRightDTO dto = (AmsSynRightDTO) req2DTO.getDTO(req);
			String action = dto.getAct();

			conn = DBManager.getDBConnection();
			OptionProducer op = new OptionProducer(user, conn);
            AmsSynRightDAO sfGroupDAO = new AmsSynRightDAO(user, dto, conn);
			String opt = "";
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);

				forwardURL = "/system/user/synRightQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsSynRightModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);

				forwardURL = "/system/user/synRightQuery.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				AmsSynRightDTO sfGroup = null;
				if (sfGroup == null) {
					sfGroup = new AmsSynRightDTO();
					sfGroup.setOrganizationId(user.getOrganizationId());
				}
                req.setAttribute(WebAttrConstant.GROUP_ATTR, sfGroup);
                 	String allRoleOption = op.getViewAllOrganization(sfGroup.getUserId(),false);
				req.setAttribute(WebAttrConstant.ALL_ROLE_OPTION, allRoleOption);
				String viewRoleOption = op.getViewOrganization(sfGroup.getUserId(),false);
				req.setAttribute(WebAttrConstant.VIEW_ROLE_OPTION, viewRoleOption);
				forwardURL = "/system/user/synRightDetail.jsp";
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				sfGroupDAO.setDTOClassName(AmsSynRightDTO.class.getName());
				AmsSynRightDTO sfGroup = (AmsSynRightDTO) sfGroupDAO.getDataByPrimaryKey();
				if (sfGroup == null) {
					sfGroup = new AmsSynRightDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
                req.setAttribute(WebAttrConstant.GROUP_ATTR, sfGroup);
                 	String allRoleOption = op.getViewAllOrganization(sfGroup.getUserId(),false);
				req.setAttribute(WebAttrConstant.ALL_ROLE_OPTION, allRoleOption);
				String viewRoleOption = op.getViewOrganization(sfGroup.getUserId(),false);
				req.setAttribute(WebAttrConstant.VIEW_ROLE_OPTION, viewRoleOption);
                forwardURL = "/system/user/synRightDetail.jsp";
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				sfGroupDAO.createData();
				message = sfGroupDAO.getMessage();
				forwardURL = URLDefineList.GROUP_SERVLET_PAGE;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
                String organizationIds[]=req.getParameterValues("organizationId");
                sfGroupDAO.updateByUser(organizationIds);
				message = sfGroupDAO.getMessage();
				forwardURL = "/servlet/com.sino.ams.system.user.servlet.AmsSynRightServlet?act="+WebActionConstant.QUERY_ACTION;
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.CONN_ERROR);
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