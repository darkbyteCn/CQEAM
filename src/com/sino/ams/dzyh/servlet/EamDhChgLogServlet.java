package com.sino.ams.dzyh.servlet;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.constant.LvecMessageKeys;
import com.sino.ams.dzyh.constant.LvecURLs;
import com.sino.ams.dzyh.constant.LvecWebAttributes;
import com.sino.ams.dzyh.dao.EamDhChgLogDAO;
import com.sino.ams.dzyh.dto.EamDhChgLogDTO;
import com.sino.ams.dzyh.model.EamDhChgLogModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EamDhChgLogServlet</p>
 * <p>Description:程序自动生成服务程序“EamDhChgLogServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */


public class EamDhChgLogServlet extends BaseServlet {

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
			req2DTO.setDTOClassName(EamDhChgLogDTO.class.getName());
			EamDhChgLogDTO dto = (EamDhChgLogDTO)req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			EamDhChgLogDAO logDAO = new EamDhChgLogDAO(user, dto, conn);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = LvecURLs.LOG_LIST_PAGE;
			} else if (action.equals(DzyhActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EamDhChgLogModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();

				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = LvecURLs.LOG_LIST_PAGE;
			} else if (action.equals(DzyhActionConstant.EXPORT_ACTION)) {
				File file = logDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(DzyhActionConstant.DETAIL_ACTION)) {
				logDAO.setDTOClassName(EamDhChgLogDTO.class.getName());
				EamDhChgLogDTO logDTO = (EamDhChgLogDTO)logDAO.getDataByPrimaryKey();
				if(logDTO == null){
					logDTO = dto;
					message = getMessage(LvecMessageKeys.BARCIODE_NO_CHG_LOG);
					message.addParameterValue(logDTO.getRefNo());
					message.addParameterValue(logDTO.getBarcode());
					message.setNeedClose(true);
				}
				req.setAttribute(LvecWebAttributes.LOG_DTO, logDTO);
				forwardURL = LvecURLs.LOG_DATA_PAGE;
			} else if (action.equals(DzyhActionConstant.LOG_HISTORY)) {
				DTOSet logHistories = logDAO.getChgLogHistory();
				req.setAttribute(LvecWebAttributes.LOG_HISTORY, logHistories);
				forwardURL = LvecURLs.LOG_HIS_PAGE;
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
		} catch (WebFileDownException ex) {
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
