package com.sino.sinoflow.servlet;


import java.io.IOException;
import java.sql.Connection;

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
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.FlowTaskTool;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.bean.optionProducer.OptionProducer;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.dao.SfWorkScheduleDAO;
import com.sino.sinoflow.dto.SfWorkScheduleDTO;
import com.sino.sinoflow.model.SfWorkScheduleModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

/**
 * <p>Title: SfWorkScheduleServlet</p>
 * <p>Description:程序自动生成服务程序“SfWorkScheduleServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */
 

public class SfWorkScheduleServlet extends BaseServlet {

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
			req2DTO.setDTOClassName(SfWorkScheduleDTO.class.getName());
			SfWorkScheduleDTO dtoParameter = (SfWorkScheduleDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			SfWorkScheduleDAO sfWorkScheduleDAO = new SfWorkScheduleDAO(user, dtoParameter, conn);
			
			if (action.equals("")) {
				BaseSQLProducer sqlProducer = new SfWorkScheduleModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(SfWorkScheduleDTO.class.getName());
				pageDAO.produceWebData();
				sfWorkScheduleDAO.setWorkingDateStr((DTOSet)req.getAttribute(QueryConstant.SPLIT_DATA_VIEW));
				forwardURL = URLDefineList.WORK_SCHEDULE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				forwardURL = URLDefineList.WORK_SCHEDULE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				setOption(req,conn);//设置下拉列表
				forwardURL = URLDefineList.WORK_SCHEDULE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				sfWorkScheduleDAO.setDTOClassName(SfWorkScheduleDTO.class.getName());
				SfWorkScheduleDTO sfWorkSchedule = (SfWorkScheduleDTO)sfWorkScheduleDAO.getDataByPrimaryKey();
				if(sfWorkSchedule == null){
					sfWorkSchedule = new SfWorkScheduleDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				setOption2(req,conn,sfWorkSchedule);//设置下拉列表
				req.setAttribute(WebAttrConstant.WORKSCHEDULE_ATTR, sfWorkSchedule);
				forwardURL = URLDefineList.WORK_SCHEDULE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				
				if(!FlowTaskTool.isExist("SF_WORK_SCHEDULE","WORK_SCHEDULE_NAME",
							dtoParameter.getWorkScheduleName(), conn)){
					sfWorkScheduleDAO.createData();
					message = this.getMessage(MsgKeyConstant.CREATE_DATA_SUCCESS);
					message.addParameterValue("工作时间");
					forwardURL = URLDefineList.WORK_SCHEDULE_SERLVET;
				}else{
					setOption(req,conn);//设置下拉列表
					message = new Message();
					message.setMessageValue("工作时间已存在");
					forwardURL = URLDefineList.WORK_SCHEDULE_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				sfWorkScheduleDAO.updateData();
				message = this.getMessage(MsgKeyConstant.UPDATE_DATA_SUCCESS);
				message.addParameterValue("工作时间");
				forwardURL = URLDefineList.WORK_SCHEDULE_SERLVET;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				String ids[] = req.getParameterValues("mdc");
				if(ids != null){
					sfWorkScheduleDAO.del(ids);
					message = this.getMessage(MsgKeyConstant.DELETE_DATA_SUCCESS);
					message.addParameterValue("工作时间");
				}
				forwardURL = URLDefineList.WORK_SCHEDULE_SERLVET;
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
		} catch (NumberFormatException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
	
	private void setOption(HttpServletRequest req,Connection conn) throws QueryException{
		
		OptionProducer op = new OptionProducer(null,conn);
		//节假日下拉列表
		req.setAttribute(WebAttrConstant.HOLIDAYS_OPTION_STR, op.getHolidays(null));
		//工作时间下拉列表
		req.setAttribute(WebAttrConstant.WORKHOUR_OPTION_STR, op.getWorkHour(null));
	}
	
	private void setOption2(HttpServletRequest req,Connection conn,SfWorkScheduleDTO sd) throws QueryException{
		
		OptionProducer op = new OptionProducer(null,conn);
		//节假日下拉列表
		req.setAttribute(WebAttrConstant.HOLIDAYS_OPTION_STR, op.getHolidays(sd.getHolidayName()));
		Logger.logInfo(op.getHolidays(sd.getHolidayName()));
		//工作时间下拉列表
		req.setAttribute(WebAttrConstant.WORKHOUR_OPTION_STR, op.getWorkHour(String.valueOf(sd.getWorkHourId())));
	}
}