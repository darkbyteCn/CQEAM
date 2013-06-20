package com.sino.ams.expand.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.*;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.expand.dao.AmsItemFilesDAO;
import com.sino.ams.expand.dto.AmsItemFilesDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.util.StrUtil;
import com.sino.base.log.Logger;


/**
 * <p>Title: AmsItemFilesServlet</p>
 * <p>Description:程序自动生成服务程序“AmsItemFilesServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class AmsItemFilesServlet extends BaseServlet {

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
			AmsItemFilesDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsItemFilesDTO.class.getName());

			RequestParser parser = new RequestParser();
			parser.transData(req);
			dtoParameter = (AmsItemFilesDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			 String action = parser.getParameter("act");
			AmsItemFilesDAO amsItemFilesDAO = new AmsItemFilesDAO(user, dtoParameter, conn);
			if (action.equals(WebActionConstant.NEW_ACTION)) {
				forwardURL = URLDefineList.ETS_EX_LAND_FILE_PAGE;
			} else if (action.equals(WebActionConstant.DOWNLOAD_ACTION)) {
				amsItemFilesDAO.setDTOClassName(AmsItemFilesDTO.class.getName());
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(dtoParameter.getFilePath());
				fileDown.download();
			} else if (action.equals(WebActionConstant.UPLOAD_ACTION)) {

				UploadFileSaver saver = parser.getFileSaver();
				System.out.println("\n=========barcode============>>>======"+parser.getParameter("barcode"));
				saver.saveFiles("/" + parser.getParameter("barcode"));
				UploadRow row = saver.getRow();
				UploadFile uploadFile = row.getFiles()[0];
				dtoParameter.setFilePath(uploadFile.getAbsolutePath());
				String fileName = uploadFile.getFileName();
				if (dtoParameter.getFileDesc().equals("")) {
					int index = fileName.lastIndexOf(".");
					if (index != -1) {
						fileName = fileName.substring(0, index);
					}
					dtoParameter.setFileDesc(fileName);
				}
				String retValue=dtoParameter.getFileDesc()+"$"+uploadFile.getAbsolutePath();
				message = amsItemFilesDAO.getMessage();
				forwardURL="/public/windowClose.jsp?retValue="+retValue;
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
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!StrUtil.isEmpty(forwardURL)){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}

}
