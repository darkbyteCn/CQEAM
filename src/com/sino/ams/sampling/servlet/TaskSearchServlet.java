package com.sino.ams.sampling.servlet;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.sampling.bean.SamplingOptProducer;
import com.sino.ams.sampling.constant.SamplingActions;
import com.sino.ams.sampling.constant.SamplingURLs;
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
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: TaskSearchServlet</p>
 * <p>Description:程序自动生成服务程序“TaskSearchServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class TaskSearchServlet extends BaseServlet {

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
			if (action.equals("")) {
				appendOption(user, dto, conn);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = SamplingURLs.TASK_SEARCH_PAGE;
			} else if (action.equals(SamplingActions.QUERY_ACTION)) {
				appendOption(user, dto, conn);
				BaseSQLProducer sqlProducer = new AmsAssetsSamplingTaskModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(AmsAssetsSamplingTaskDTO.class.getName());
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = SamplingURLs.TASK_SEARCH_PAGE;
			} else if (action.equals(SamplingActions.EXPORT_ACTION)) {
				File file = taskDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
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
		if(dto == null){
			return;
		}
		AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
        if (StrUtil.isEmpty(dto.getSampledOu())) {
            dto.setSampledOu(user.getOrganizationId());
        }
        String opt = optProducer.getOrganization(user.getOrganizationId());
		dto.setSampledOuOpt(opt);
//		SamplingOptProducer optProducer = new SamplingOptProducer(user, conn);
//		if(user.isProvinceUser()){
//			String samplingOuOpt = optProducer.getAllOrganization(dto.getSampledOu());
//			dto.setSampledOuOpt(samplingOuOpt);
//		} else {
//			String samplingOuOpt = optProducer.getOrganizationOption(dto.getSampledOu());
//			dto.setSampledOuOpt(samplingOuOpt);
//		}
		String taskStatusOpt = optProducer.getDictOption("TASK_STATUS", dto.getTaskStatus());
		dto.setTaskStatusOpt(taskStatusOpt);
		String samplingTypeOpt = optProducer.getDictOption("SAMPLING_TYPE", dto.getSamplingType());
		dto.setSamplingTypeOpt(samplingTypeOpt);
	}
}
