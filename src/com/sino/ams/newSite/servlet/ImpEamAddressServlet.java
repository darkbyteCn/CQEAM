package com.sino.ams.newSite.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.FileSizeException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.PDAUtil;

/** 
 * @author 作者 :wangzhipeng
 * @version 创建时间：Apr 21, 2011 8:25:11 PM 
 * 类说明 : 导入文件excel
 *
 */
public class ImpEamAddressServlet extends BaseServlet {

	private static final String sContentType = "text/html;charset=GBK";	

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = null;
		Connection conn = null;
		res.setContentType(sContentType);
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
			Logger.logInfo("Excel submit servlet begin....");
			RequestParser reqPar = new RequestParser();
			 reqPar.transData(req);
			UploadFile[] upFiles = null;
			UploadRow uploadRow;
			String conFilePath = PDAUtil.getCurUploadFilePath(conn);
			UploadFileSaver uploadFileSaver = reqPar.getFileSaver();
			uploadFileSaver.saveFiles(conFilePath);
			uploadRow = uploadFileSaver.getRow();
			upFiles = uploadRow.getFiles();
			if (upFiles == null) {
				return;
			} else if (upFiles.length != 1 || upFiles[0].getFileName().equals("")) {
				return;
			}
			UploadFile uploadFile = upFiles[0];
			String fileName = uploadFile.getAbsolutePath();
			fileName = fileName.replaceAll("\\\\", "/");
			forwardURL="/public/windowClose.jsp?retValue="+fileName;
		}catch (PoolException e) {
			e.printStackTrace();
		}catch (UploadException e) {
			e.printStackTrace();
		}catch (FileSizeException e) {
			e.printStackTrace();
		}catch (ContainerException e) {
			e.printStackTrace();
		}finally{
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
