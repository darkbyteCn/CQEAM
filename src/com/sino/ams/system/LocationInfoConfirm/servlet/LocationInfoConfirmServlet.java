package com.sino.ams.system.LocationInfoConfirm.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.system.LocationInfoConfirm.dao.LocationInfoConfirmDAO;
import com.sino.ams.system.LocationInfoConfirm.dto.LocationInfoConfirmDTO;
import com.sino.ams.system.LocationInfoConfirm.model.LocationInfoConfirmModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * @author Administrator
 *
 */
public class LocationInfoConfirmServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(LocationInfoConfirmDTO.class.getName());
			LocationInfoConfirmDTO dto = (LocationInfoConfirmDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			LocationInfoConfirmDAO localtionInfoConfirmDAO = new LocationInfoConfirmDAO(user, dto, conn);
			localtionInfoConfirmDAO.setServletConfig(getServletConfig(req));
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/system/locationInfoConfirm/locationInfoConfirm.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new LocationInfoConfirmModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/system/locationInfoConfirm/locationInfoConfirm.jsp";
			} else if (action.equals(AMSActionConstant.CONFIRM_ACTION)) { //确认操作
				String[] workorderObjectNos = req.getParameterValues("workorderObjectNos");
				String[] transNos = req.getParameterValues("transNos");
				if (workorderObjectNos != null) {
					try {
						localtionInfoConfirmDAO.confirm(workorderObjectNos,transNos);
					} catch (ContainerException e) {
						e.printStackTrace();
					}
				}
				BaseSQLProducer sqlProducer = new LocationInfoConfirmModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/system/locationInfoConfirm/locationInfoConfirm.jsp";
			} else if (action.equals(AMSActionConstant.CANCEL_ACTION)) { //取消操作
				String[] workorderObjectNos = req.getParameterValues("workorderObjectNos");
				String[] transNos = req.getParameterValues("transNos");
				
				if (workorderObjectNos != null) {
					try {
						localtionInfoConfirmDAO.cancel(workorderObjectNos,transNos);
					} catch (ContainerException e) {
						e.printStackTrace();
					}
				}
				BaseSQLProducer sqlProducer = new LocationInfoConfirmModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/system/locationInfoConfirm/locationInfoConfirm.jsp";
			} else if (action.equals("QueryPage")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/system/locationInfoConfirm/locationInfoQuery.jsp";
			} else if (action.equals("QueryResult")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				RowSet rows = localtionInfoConfirmDAO.getQueryPage();
				req.setAttribute(QueryConstant.SPLIT_DATA_VIEW, rows);
				forwardURL = "/system/locationInfoConfirm/locationInfoQuery.jsp";
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = "/system/locationInfoConfirm/locationInfoConfirm.jsp";
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
		} catch (SQLModelException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
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
