package com.sino.ams.system.trust.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.system.trust.dao.AmsMaintainFilesDAO;
import com.sino.ams.system.trust.dto.AmsMaintainFilesDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;


/**
 * <p>Title: AmsMaintainFilesServlet</p>
 * <p>Description:程序自动生成服务程序“AmsMaintainFilesServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsMaintainFilesServlet extends BaseServlet {

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
			AmsMaintainFilesDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsMaintainFilesDTO.class.getName());
			RequestParser parser = new RequestParser();
			parser.transData(req);
			dtoParameter = (AmsMaintainFilesDTO) req2DTO.getDTO(parser);
			conn = getDBConnection(req);
			String action = parser.getParameter("act");
			AmsMaintainFilesDAO amsMaintainFilesDAO = new AmsMaintainFilesDAO(user, dtoParameter, conn);
			if (action.equals(WebActionConstant.NEW_ACTION)) {
				forwardURL = URLDefineList.TRUST_ATTACH_FILE_PAGE;
			} else if (action.equals(WebActionConstant.DOWNLOAD_ACTION)) {
				amsMaintainFilesDAO.setDTOClassName(AmsMaintainFilesDTO.class.getName());
				AmsMaintainFilesDTO file = (AmsMaintainFilesDTO) amsMaintainFilesDAO.getDataByPrimaryKey();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getFilePath());
				fileDown.download();
			} else if (action.equals(WebActionConstant.UPLOAD_ACTION)) {

				UploadFileSaver saver = parser.getFileSaver();
				saver.saveFiles("/" + parser.getParameter("companyId"));
				UploadRow row = saver.getRow();
				UploadFile uploadFile = row.getFiles()[0];

				dtoParameter.setFilePath(uploadFile.getAbsolutePath());
				String fileName = uploadFile.getFileName();
				dtoParameter.setFileName(fileName);
				dtoParameter.setCreatedBy(user.getUserId());
				if (dtoParameter.getFileDescription().equals("")) {
					int index = fileName.lastIndexOf(".");
					if (index != -1) {
						fileName = fileName.substring(0, index);
					}
					dtoParameter.setFileDescription(fileName);
				}

				amsMaintainFilesDAO.createData();


				message = amsMaintainFilesDAO.getMessage();

				forwardURL = URLDefineList.TRUST_ATTACH_FILE_PAGE;
				forwardURL += "?companyId=" + dtoParameter.getCompanyId();

			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}

		} catch (DataHandleException ex) {
			 ex.printLog();
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
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (FileSizeException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
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
			if (!StrUtil.isEmpty(forwardURL)) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
			//根据实际情况修改页面跳转代码。
		}
	}
}
