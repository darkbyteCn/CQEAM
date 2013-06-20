package com.sino.ams.dzyh.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.dzyh.bean.LvecOptProducer;
import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.constant.LvecURLs;
import com.sino.ams.dzyh.dao.EamCheckTaskDAO;
import com.sino.ams.dzyh.dto.EamCheckTaskDTO;
import com.sino.ams.dzyh.model.EamCheckTaskModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EamCheckTaskServlet</p>
 * <p>Description:程序自动生成服务程序“EamCheckTaskServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class EamCheckTaskServlet extends BaseServlet {

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
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EamCheckTaskDTO.class.getName());
			EamCheckTaskDTO dto = (EamCheckTaskDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			EamCheckTaskDAO taskDAO = new EamCheckTaskDAO(user, dto, conn);
			taskDAO.setServletConfig(getServletConfig(req));
			if (action.equals("")) {
				appendData(user, dto, conn);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = LvecURLs.TASK_LIST_PAGE;
			} else if (action.equals(DzyhActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EamCheckTaskModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("CHECK_TASK_ID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setDTOClassName(EamCheckTaskDTO.class.getName());
				pageDAO.produceWebData();

				appendData(user, dto, conn);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = LvecURLs.TASK_LIST_PAGE;
			} else if (action.equals(DzyhActionConstant.EXPORT_ACTION)) {
				File file = taskDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(DzyhActionConstant.NEW_ACTION)) {
				EamCheckTaskDTO checkTask = (EamCheckTaskDTO) req.getAttribute(QueryConstant.QUERY_DTO);
				if (checkTask == null) {
					checkTask = dto; //表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.lvec.dto.EamCheckTaskDTO的构造函数确定
				}
				checkTask.setAct(dto.getAct());
				appendData(user, checkTask, conn);
				req.setAttribute(QueryConstant.QUERY_DTO, checkTask);
				forwardURL = LvecURLs.TASK_DATA_PAGE;
			} else if (action.equals(DzyhActionConstant.DETAIL_ACTION)) {
				EamCheckTaskDTO checkTask = (EamCheckTaskDTO) req.getAttribute(QueryConstant.QUERY_DTO);
				if (checkTask == null) {
					taskDAO.setDTOClassName(EamCheckTaskDTO.class.getName());
					checkTask = (EamCheckTaskDTO) taskDAO.getDataByPrimaryKey();
				}
				if (checkTask == null) {
					checkTask = dto;
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				checkTask.setAct(dto.getAct());
				appendData(user, checkTask, conn);
				req.setAttribute(QueryConstant.QUERY_DTO, checkTask);
				forwardURL = LvecURLs.TASK_DATA_PAGE;
			} else if (action.equals(DzyhActionConstant.SAVE_ACTION)) {
				dto.setTaskStatus(LvecDicts.TSK_STATUS1_SAVE_TEMP);
				boolean operateResult = taskDAO.saveTask();
				message = taskDAO.getMessage();
				dto = (EamCheckTaskDTO) taskDAO.getDTOParameter();
				String taskId = dto.getCheckTaskId();
				if (taskId.equals("")) {
					req.setAttribute(QueryConstant.QUERY_DTO, dto);
					forwardURL = LvecURLs.TASK_SERVLET;
					forwardURL += "?act=" + DzyhActionConstant.NEW_ACTION;
				} else {
					if (!operateResult) {
						req.setAttribute(QueryConstant.QUERY_DTO, dto);
					}
					forwardURL = LvecURLs.TASK_SERVLET;
					forwardURL += "?act=" + DzyhActionConstant.DETAIL_ACTION;
					forwardURL += "&checkTaskId=" + dto.getCheckTaskId();
				}
			} else if (action.equals(DzyhActionConstant.SUBMIT_ACTION)) {
				dto.setTaskStatus(LvecDicts.TSK_STATUS1_OPENING);
				boolean operateResult = taskDAO.saveTask();
				message = taskDAO.getMessage();
				dto = (EamCheckTaskDTO) taskDAO.getDTOParameter();
				String taskId = dto.getCheckTaskId();
				if (taskId.equals("")) {
					req.setAttribute(QueryConstant.QUERY_DTO, dto);
					forwardURL = LvecURLs.TASK_SERVLET;
					forwardURL += "?act=" + DzyhActionConstant.NEW_ACTION;
				} else {
					if (!operateResult) {
						req.setAttribute(QueryConstant.QUERY_DTO, dto);
					}
					forwardURL = LvecURLs.TASK_SERVLET;
					forwardURL += "?act=" + DzyhActionConstant.DETAIL_ACTION;
					forwardURL += "&checkTaskId=" + dto.getCheckTaskId();
				}
			} else if (action.equals(DzyhActionConstant.CAL_MUL_TASK)) { //批量取消
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] taskIds = parser.getParameterValues("checkTaskId");
				boolean singleCancel = parser.contains("remark");
				taskDAO.cancelTasks(taskIds, singleCancel);
				message = taskDAO.getMessage();
				if(singleCancel){
					forwardURL = LvecURLs.TASK_SERVLET;
					forwardURL += "?act=" + DzyhActionConstant.DETAIL_ACTION;
					forwardURL += "&checkTaskId=" + dto.getCheckTaskId();
				} else {
					forwardURL = LvecURLs.TASK_SERVLET;
					forwardURL += "?act=" + DzyhActionConstant.QUERY_ACTION;
				}
			} else if (action.equals(DzyhActionConstant.SUB_MUL_TASK)) { //批量确定
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] taskIds = parser.getParameterValues("checkTaskId");
				taskDAO.submitTasks(taskIds);
				message = taskDAO.getMessage();
				forwardURL = LvecURLs.TASK_SERVLET;
				forwardURL += "?act=" + DzyhActionConstant.QUERY_ACTION;
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
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
			if (!forwardURL.equals("")) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}

/**
	 * 功能：补充下拉列表框
	 * @param user SfUserDTO
	 * @param dto EamCheckTaskDTO
	 * @param conn Connection
	 * @throws QueryException
	 */
	private void appendData(SfUserDTO user, EamCheckTaskDTO dto, Connection conn) throws QueryException {
		LvecOptProducer optPrd = new LvecOptProducer(user, conn);
		String opt = "";
		String act = dto.getAct();
		opt = optPrd.getDictOption("CHECK_TYPE", dto.getCheckType());
		dto.setCheckTypeOpt(opt);
		if(act.equals("") || act.equals(DzyhActionConstant.QUERY_ACTION)){
			if (user.isProvinceUser()) {
				opt = optPrd.getAllOrganization(dto.getOrganizationId());
			} else {
				opt = optPrd.getOrganizationOption(dto.getOrganizationId());
			}
			dto.setOrgOption(opt);
			opt = optPrd.getDictOption("TASK_STATUS", dto.getTaskStatus());
			dto.setTaskStatusOpt(opt);
		} else if(act.equals(DzyhActionConstant.NEW_ACTION)){
			dto.setOrganizationId(user.getOrganizationId());
			dto.setOrganizationName(user.getCompany());
			dto.setTaskStatus(LvecDicts.ORDER_STS1_NEW);
			dto.setTaskStatusValue(LvecDicts.ORDER_STS2_NEW);
			dto.setCreatedUser(user.getUsername());
			dto.setCreatedBy(user.getUserId());
			dto.setCurrCalendar("creationDate");
		}
		dto.setCalPattern(LINE_PATTERN);
	}
}
