package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.log.dao.UserLoginDAO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dao.ChkOrderDownLoadDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ChkOrderDownLoadServlet extends BaseServlet {
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
		try {
			String loginName = StrUtil.nullToString(req.getParameter("username")); //登录名
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
			res.setContentType(CONTENT_TYPE);
			PrintWriter writer = res.getWriter();
			StringBuffer responeXML = new StringBuffer();
			if (user != null) {
				AmsAssetsCheckHeaderDTO dtoParameter = new
						AmsAssetsCheckHeaderDTO();
				dtoParameter.setOrderStatus(AssetsDictConstant.
											CHK_STATUS_DISTRUIBUTED);
				ChkOrderDownLoadDAO dao = new ChkOrderDownLoadDAO(user,
						dtoParameter, conn);
				responeXML = dao.getOrderXml();
			} else {
				responeXML.append(
						"<?xml version=\"1.0\" encoding=\"GB2312\" ?>");
				responeXML.append("<result message=\"用户非法，不能下载数据\">");
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
