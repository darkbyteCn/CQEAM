package com.sino.ams.sampling.servlet;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.sampling.bean.SamplingOptProducer;
import com.sino.ams.sampling.constant.SamplingActions;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.constant.SamplingURLs;
import com.sino.ams.sampling.constant.SamplingWebAttributes;
import com.sino.ams.sampling.dao.AmsAssetsSamplingTaskDAO;
import com.sino.ams.sampling.dto.AmsAssetsSamplingTaskDTO;
import com.sino.ams.sampling.model.AmsAssetsSamplingTaskModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsAssetsSamplingTaskServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsSamplingTaskServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsAssetsSamplingTaskServlet extends BaseServlet {

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
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsSamplingTaskDTO.class.getName());
			AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO)req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			
			AmsAssetsSamplingTaskDAO taskDAO = new AmsAssetsSamplingTaskDAO(user, dto, conn);
			taskDAO.setServletConfig(getServletConfig(req));
			boolean operateResult = false;
			if (action.equals("")) {
				appendOption(user, dto, conn);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = SamplingURLs.TASK_LIST_PAGE;
			} else if (action.equals(SamplingActions.QUERY_ACTION)) {
				appendOption(user, dto, conn);
				BaseSQLProducer sqlProducer = new AmsAssetsSamplingTaskModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(AmsAssetsSamplingTaskDTO.class.getName());
				pageDAO.setCalPattern(LINE_PATTERN);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("TASK_ID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = SamplingURLs.TASK_LIST_PAGE;
			} else if (action.equals(SamplingActions.NEW_ACTION)) {
				AmsAssetsSamplingTaskDTO samplingTask = (AmsAssetsSamplingTaskDTO)req.getAttribute(QueryConstant.QUERY_DTO);
				if(samplingTask == null){
					samplingTask= dto;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.sampling.dto.AmsAssetsSamplingTaskDTO的构造函数确定
				} else {
					samplingTask.setAct(dto.getAct());
				}
				appendOption(user, samplingTask, conn);
				appendOtherData(user, samplingTask);
				samplingTask.setCalPattern(LINE_PATTERN);
				req.setAttribute(QueryConstant.QUERY_DTO, samplingTask);
				forwardURL = SamplingURLs.TASK_DETAIL_PAGE;
			} else if (action.equals(SamplingActions.DETAIL_ACTION)) {
				AmsAssetsSamplingTaskDTO samplingTask = (AmsAssetsSamplingTaskDTO)req.getAttribute(QueryConstant.QUERY_DTO);
				if(samplingTask == null){
					taskDAO.setDTOClassName(AmsAssetsSamplingTaskDTO.class.getName());
					samplingTask = (AmsAssetsSamplingTaskDTO)taskDAO.getDataByPrimaryKey();
				}
				if(samplingTask == null){
					samplingTask = dto;
					appendOtherData(user, samplingTask);
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				} else {
					samplingTask.setAct(dto.getAct());
				}
                samplingTask.setSeachType(dto.getSeachType());
                appendOption(user, samplingTask, conn);
				samplingTask.setCalPattern(LINE_PATTERN);
				req.setAttribute(QueryConstant.QUERY_DTO, samplingTask);
				forwardURL = SamplingURLs.TASK_DETAIL_PAGE;
			} else if (action.equals(SamplingActions.EXPORT_ACTION)) {
				File file = taskDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(SamplingActions.SAVE_ACTION)) {//暂存任务(可修改)
				AmsAssetsSamplingTaskDTO samplingTask = (AmsAssetsSamplingTaskDTO)req.getAttribute(QueryConstant.QUERY_DTO);
				if(samplingTask == null){
					taskDAO.setDTOClassName(AmsAssetsSamplingTaskDTO.class.getName());
					samplingTask = (AmsAssetsSamplingTaskDTO)taskDAO.getDataByTaskName();
				}
				if(samplingTask != null){
					message = getMessage(MsgKeyConstant.MSG_PARA_MULTIPLE);
					message.setMessageValue("任务名称不能重复");
					message.setIsError(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				}else{
					String[] sampledOu = req.getParameterValues("sampledOu");
					operateResult = taskDAO.saveTask(sampledOu);
					message = taskDAO.getMessage();
					if(operateResult){
						dto = (AmsAssetsSamplingTaskDTO) taskDAO.getDTOParameter();
						forwardURL = SamplingURLs.TASK_SERVLET;
						forwardURL += "?act=" + SamplingActions.DETAIL_ACTION;
						forwardURL += "&taskId=" + dto.getTaskId();
					} else {
						req.setAttribute(QueryConstant.QUERY_DTO, dto);
						forwardURL = SamplingURLs.TASK_SERVLET;
						forwardURL += "?act=" + SamplingActions.NEW_ACTION;
					}
				}
			} else if (action.equals(SamplingActions.PUBLISH_TASK)) {//发布任务(不可修改)
				String[] sampledOu = req.getParameterValues("sampledOu");
				operateResult = taskDAO.publishTask(sampledOu);
				message = taskDAO.getMessage();
				if(operateResult){
					dto = (AmsAssetsSamplingTaskDTO) taskDAO.getDTOParameter();
					forwardURL = SamplingURLs.TASK_SERVLET;
					forwardURL += "?act=" + SamplingActions.DETAIL_ACTION;
					forwardURL += "&taskId=" + dto.getTaskId();
				} else {
					req.setAttribute(QueryConstant.QUERY_DTO, dto);
					forwardURL = SamplingURLs.TASK_SERVLET;
					forwardURL += "?act=" + SamplingActions.NEW_ACTION;
				}
			} else if (action.equals(SamplingActions.CANCEL_ACTION)) {//取消任务(不可修改)
				operateResult = taskDAO.cancelTask();
				message = taskDAO.getMessage();
				forwardURL = SamplingURLs.TASK_SERVLET;
				forwardURL += "?act=" + SamplingActions.DETAIL_ACTION;
			} else if (action.equals(SamplingActions.CLOSE_TASK)) {//关闭任务(不可修改，不可继续创建工单)
				operateResult = taskDAO.closeTask();
				message = taskDAO.getMessage();
				forwardURL = SamplingURLs.TASK_SERVLET;
				forwardURL += "?act=" + SamplingActions.DETAIL_ACTION;
			} else if (action.equals(SamplingActions.OPEN_TASK)) {//打开任务(可修改，可继续创建工单)
				operateResult = taskDAO.openTask();
				message = taskDAO.getMessage();
				forwardURL = SamplingURLs.TASK_SERVLET;
				forwardURL += "?act=" + SamplingActions.DETAIL_ACTION;
			} else if (action.equals(SamplingActions.CLOSE_SELECTED_TASK)) {//关闭选择的多个任务中的开放中的任务(不可修改，不可继续创建工单)
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				RequestParser parser = new RequestParser();
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				taskDAO.closeMultipleTask(parser.getParameterValues("taskId"));
				message = taskDAO.getMessage();
				forwardURL = SamplingURLs.TASK_SERVLET;
				forwardURL += "?act=" + SamplingActions.QUERY_ACTION;
			} else if (action.equals(SamplingActions.OPEN_SELECTED_TASK)) {//打开选择的多个任务中的已关闭中的任务(可修改，可继续创建工单)
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				RequestParser parser = new RequestParser();
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				taskDAO.openMultipleTask(parser.getParameterValues("taskId"));
				message = taskDAO.getMessage();
				forwardURL = SamplingURLs.TASK_SERVLET;
				forwardURL += "?act=" + SamplingActions.QUERY_ACTION;
			} else if (action.equals(SamplingActions.CANCEL_SELECTED_TASK)) {//关闭选择的多个任务中的开放中的任务(不可修改，不可继续创建工单)
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				RequestParser parser = new RequestParser();
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				taskDAO.cancelMultipleTask(parser.getParameterValues("taskId"));
				message = taskDAO.getMessage();
				forwardURL = SamplingURLs.TASK_SERVLET;
				forwardURL += "?act=" + SamplingActions.QUERY_ACTION;
			} else if (action.equals(SamplingActions.PUBLISH_SELECTED_TASK)) {//打开选择的多个任务中的已关闭中的任务(可修改，可继续创建工单)
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				RequestParser parser = new RequestParser();
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				taskDAO.publishMultipleTask(parser.getParameterValues("taskId"));
				message = taskDAO.getMessage();
				forwardURL = SamplingURLs.TASK_SERVLET;
				forwardURL += "?act=" + SamplingActions.QUERY_ACTION;
			} else if (action.equals("VERIFY")) {//验证发布任务时是否有抽查任务没有选抽查公司
				String[] selectTaskIds = req.getParameterValues("selectTaskId");
                Map map = taskDAO.verify(selectTaskIds);
//                boolean success = map.isEmpty();
                boolean success = map.containsValue("EMPTY");
                PrintWriter out = res.getWriter();
                if (!success) {
                    out.print("Y");
                }
                out.flush();
                out.close();
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
		} catch (Exception ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!forwardURL.equals("")){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}

	/**
	 * 功能：补充抽查任务DTO的各种下拉框数据(用于界面展示时)
	 * @param user SfUserDTO
	 * @param dto AmsAssetsSamplingTaskDTO
	 * @param conn Connection
	 * @throws QueryException
	 */
	private void appendOption(SfUserDTO user, AmsAssetsSamplingTaskDTO dto, Connection conn) throws QueryException {
		String act = dto.getAct();
		
		AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
        if (StrUtil.isEmpty(dto.getSampledOu())) {
            dto.setSampledOu(user.getOrganizationId());
        }
        String opt = optProducer.getOrganization(user.getOrganizationId());
		dto.setSampledOuOpt(opt);
		
//		SamplingOptProducer optProducer = new SamplingOptProducer(user, conn);
//		if(act.equals("") || act.equals(SamplingActions.QUERY_ACTION)){
//			if(user.isProvinceUser()){
//				String sampledOuOpt = optProducer.getAllOrganization(dto.getSampledOu());
//				dto.setSampledOuOpt(sampledOuOpt);
//			} else {
//				String sampledOuOpt = optProducer.getOrganizationOption(dto.getSampledOu());
//				dto.setSampledOuOpt(sampledOuOpt);
//			}
//		} else if(act.equals(SamplingActions.NEW_ACTION) || act.equals(SamplingActions.DETAIL_ACTION)){
//			if (user.isProvinceUser()) {
//				String leftOuOpt = optProducer.getTaskLeftOuOPt(dto.getTaskId());
//				dto.setLeftOuOpt(leftOuOpt);
//				String samplingOuOpt = optProducer.getTaskSampledOuOpt(dto.getTaskId());
//				dto.setSampledOuOpt(samplingOuOpt);
//			} else {
//				String samplingOuOpt = optProducer.getOrganizationOption(dto.getSampledOu());
//				dto.setSampledOuOpt(samplingOuOpt);
//			}
//		}
		String taskStatusOpt = optProducer.getDictOption("TASK_STATUS", dto.getTaskStatus());
		dto.setTaskStatusOpt(taskStatusOpt);
		String samplingTypeOpt = optProducer.getDictOption("SAMPLING_TYPE", dto.getSamplingType());
		dto.setSamplingTypeOpt(samplingTypeOpt);
	}

	/**
	 * 功能：抽查任务DTO的各种非下拉框数据(用于界面展示时)
	 * @param user SfUserDTO
	 * @param dto AmsAssetsSamplingTaskDTO
	 */
	private void appendOtherData(SfUserDTO user, AmsAssetsSamplingTaskDTO dto){
		String act = dto.getAct();
		if(act.equals(SamplingActions.NEW_ACTION)){
			dto.setCreatedBy(user.getUserId());
			dto.setCreatedUser(user.getUsername());
			dto.setCreatedOu(user.getOrganizationId());
			dto.setCreatedOuName(user.getCompany());
			dto.setTaskStatus(SamplingDicts.TSK_STATUS1_NEW);
			dto.setTaskStatusValue(SamplingDicts.TSK_STATUS2_NEW);
			dto.setTaskNo(SamplingWebAttributes.ORDER_AUTO_PROD);
			dto.setCurrCalendar("creationDate");
			if(!user.isProvinceUser()){
				dto.setSampledOu(user.getOrganizationId());
				dto.setSampledOuName(user.getCompany());
			}
			dto.setCalPattern(LINE_PATTERN);
		}
	}
}
