package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.log.dao.UserLoginDAO;
import com.sino.ams.newasset.dao.ChkOrderUpLoadDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.base.web.request.upload.UploadFile;
import com.sino.base.web.request.upload.UploadFileSaver;
import com.sino.base.web.request.upload.UploadRow;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ChkOrderUpLoadServlet extends BaseServlet {
	private static final String CONTENT_TYPE = "application/xml; charset=GBK";

	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
			ServletException, IOException {
		Connection conn = null;
		boolean hasError = true;
		try {
			RequestParser parser = new RequestParser();
			parser.transData(req);
			String loginName = parser.getParameter("username"); //登录名
			SfUserDTO user = null;
			conn = getDBConnection(req);
			if (!StrUtil.isEmpty(loginName)) {
				user = new SfUserDTO();
				user.setLoginName(loginName);
				UserLoginDAO loginDAO = new UserLoginDAO(user, conn);
				loginDAO.setFromPDA(true);
				if (loginDAO.isValidUser()) {
					user = (SfUserDTO) loginDAO.getUserAccount();
				} else {
					user = null;
				}
			} else {
				user = (SfUserDTO) getUserAccount(req);
			}
			StringBuffer responeXML = new StringBuffer();
			res.setContentType(CONTENT_TYPE);
			PrintWriter writer = res.getWriter();

			if (user != null) {
				UploadFileSaver saver = parser.getFileSaver();
				saver.saveFiles("/assetsChk/" + user.getCompanyCode() + "/");
				UploadRow row = saver.getRow();
				UploadFile[] files = row.getFiles();
				if (files != null && files.length > 0) {
					UploadFile file = files[0];
					String filePath = file.getAbsolutePath();
					ChkOrderUpLoadDAO dao = new ChkOrderUpLoadDAO(user, conn);
					hasError = !dao.uploadOrders(filePath);
					saver.saveFiles("/assetsChk/" + user.getCompanyCode() +
									"/bak/" + CalendarUtil.getCurrDate() + "/");
				} else {
					hasError = false;
				}
				String msg = "资产盘点工单上载成功";
				if (hasError) {
					msg = "资产盘点工单上载失败";
				}
				responeXML.append(
						"<?xml version=\"1.0\" encoding=\"GB2312\" ?>");
				responeXML.append("<result message=\"" + msg + "\">");
				responeXML.append(!hasError);
				responeXML.append("</result>");
			} else {
				responeXML.append(
						"<?xml version=\"1.0\" encoding=\"GB2312\" ?>");
				responeXML.append("<result message=\"系统不接收非法用户上载的工单\">");
				responeXML.append(false);
				responeXML.append("</result>");
			}
			writer.write(responeXML.toString());
			writer.close();
		} catch (Exception ex) {
			Logger.logError(ex);
		} finally {
			closeDBConnection(conn);
		}
	}
}
