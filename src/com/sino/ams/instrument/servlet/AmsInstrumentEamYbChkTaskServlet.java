package com.sino.ams.instrument.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.instrument.dao.AmsInstrumentEamYbChkTaskDAO;
import com.sino.ams.instrument.dto.AmsInstrumentEamYbChkTaskDTO;
import com.sino.ams.instrument.model.AmsInstrumentEamYbChkTaskModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
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
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: AmsInstrumentEamYbChkTaskServlet</p>
 * <p>Description:仪器仪表任务批号设置</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-Yushibo
 * @version 1.0
 */
public class AmsInstrumentEamYbChkTaskServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		boolean isOpener = false;
		try{
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2dto = new Request2DTO();
			req2dto.setDTOClassName(AmsInstrumentEamYbChkTaskDTO.class.getName());
			AmsInstrumentEamYbChkTaskDTO dto = (AmsInstrumentEamYbChkTaskDTO)req2dto.getDTO(req);
			String action = dto.getAct(); //保证action不为空的写法
			conn = getDBConnection(req);
			AmsInstrumentEamYbChkTaskDAO taskDAO = new AmsInstrumentEamYbChkTaskDAO(user, dto, conn);

			if(action.equals("")){
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/instrument/instrumentTaskQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsInstrumentEamYbChkTaskModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/instrument/instrumentTaskQuery.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				
				OptionProducer optProducer = new OptionProducer(user, conn); //获取公司信息
	            String ouOption = optProducer.getAllOrganization(dto.getOrganizationId(), true);
	            req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
	            
				String taskId = req.getParameter("taskId");
				forwardURL = "/instrument/instrumentTaskAdd.jsp?taskId=" + taskId;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				String taskId = req.getParameter("taskId");
				taskDAO.setDTOClassName(AmsInstrumentEamYbChkTaskDTO.class.getName());
				taskDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				dto = (AmsInstrumentEamYbChkTaskDTO) taskDAO.getDataByPrimaryKey();
				req.setAttribute("taskId", taskId);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/instrument/instrumentTaskDetail.jsp";
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {	
				taskDAO.createData();
				forwardURL = "/servlet/" + getClass().getName();
				forwardURL += "?act=" + WebActionConstant.QUERY_ACTION;
				forwardURL += "&taskId=";
				isOpener = true;
				//forwardURL = "/instrument/instrumentTaskQuery.jsp";
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				taskDAO.updateData();
				message = taskDAO.getMessage();
				forwardURL = "/servlet/" + getClass().getName();
				forwardURL += "?act=" + WebActionConstant.QUERY_ACTION;
				
//				forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentEamYbChkTaskServlet?act=" + WebActionConstant.QUERY_ACTION;
				isOpener = true;
			} else if (action.equals("CHECK_NAME_ACTION")) {     //任务名称不允许重复
				String objName = req.getParameter("taskName");
				PrintWriter out = res.getWriter();
				boolean hasBeen = taskDAO.checkName(objName);
				if (hasBeen) out.print(WebAttrConstant.TASK_NAME_EXIST);
				else out.print(WebAttrConstant.TASK_NAME_NOT_EXIST);
				out.flush();
				out.close();
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
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if(isOpener){
				forwarder.forwardOpenerView(forwardURL, "");
			} else {
				forwarder.forwardView(forwardURL);
			}
		}
	}

}
