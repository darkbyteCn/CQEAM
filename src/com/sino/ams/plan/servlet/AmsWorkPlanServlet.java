package com.sino.ams.plan.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.plan.dao.AmsWorkPlanDAO;
import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.plan.model.AmsWorkPlanModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: AmsWorkPlanServlet</p>
 * <p>Description:程序自动生成服务程序“AmsWorkPlanServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class AmsWorkPlanServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsWorkPlanDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsWorkPlanDTO.class.getName());
			dtoParameter = (AmsWorkPlanDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			AmsWorkPlanDAO amsWorkPlanDAO = new AmsWorkPlanDAO(user, dtoParameter, conn);
			amsWorkPlanDAO.setCalPattern(CalendarConstant.CHINESE_PATTERN);
			OptionProducer op = new OptionProducer(user, conn);
			String planStatus = dtoParameter.getPlanStatus();
			String planStausSelect = op.getDictOption(DictConstant.PLAN_STATUS, planStatus);
			req.setAttribute(WebAttrConstant.PLAN_STATUS_OPTION, planStausSelect);
			if (action.equals("")) {
				forwardURL = URLDefineList.WORK_PLAN_QUERY;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsWorkPlanModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.WORK_PLAN_QUERY;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				AmsWorkPlanDTO workPlan = (AmsWorkPlanDTO) req.getAttribute(WebAttrConstant.WORK_PLAN_DTO);
				if (workPlan == null) {
					workPlan = dtoParameter;
				}
				req.setAttribute(WebAttrConstant.WORK_PLAN_DTO, workPlan);
				forwardURL = URLDefineList.WORK_PLAN_DETAIL;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				amsWorkPlanDAO.createData();
				message = amsWorkPlanDAO.getMessage();
				forwardURL =URLDefineList.WINDOW_CLOSE_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				amsWorkPlanDAO.setDTOClassName(AmsWorkPlanDTO.class.getName());
				AmsWorkPlanDTO dto = (AmsWorkPlanDTO) amsWorkPlanDAO.getDataByPrimaryKey();
				if (dto == null) {
					dto = new AmsWorkPlanDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.WORK_PLAN_DTO, dto);
				forwardURL = URLDefineList.WORK_PLAN_DETAIL;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				amsWorkPlanDAO.updateData();
				message = amsWorkPlanDAO.getMessage();
				forwardURL = URLDefineList.WORK_PLAN_QUERY;
			} else if (action.equals("repeal")) {
				String planId[] = req.getParameterValues("planId1");
				for (int i = 0; i < planId.length; i++) {
					amsWorkPlanDAO.repealData(planId[i]);
				}
				forwardURL = URLDefineList.WORK_PLAN_QUERY;
			} else if (action.equals("ok")) {
			   String plandId=req.getParameter("planId");
			   amsWorkPlanDAO.compData(plandId);
				forwardURL = URLDefineList.WORK_PLAN_QUERY;
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
		} catch (DataHandleException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
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
