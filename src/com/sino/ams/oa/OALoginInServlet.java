package com.sino.ams.oa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.ReflectException;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dao.BaseLoginDAO;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>
 * Title: SinoApplication
 * </p>
 * <p>
 * Description: Java Enterprise Edition 平台应用开发基础框架
 * </p>
 * <p>
 * Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>
 * Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 * 
 * @author 唐明胜
 * @version 0.1
 */
public class OALoginInServlet extends BaseServlet {

	/**
	 * 所有的Servlet都必须实现的方法。
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param res
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Connection con = null;
		Cookie[] cookies = req.getCookies();
		String ltpaToken = "";
		String smsession = "";
		String kmUserId = "";
		try {
			con = getDBConnection(req);
		} catch (PoolPassivateException e2) {
			openErrorPage(req,res,"系统认证失败!");
			e2.printStackTrace();
		}
		SSOLoginDAO ssoLoginDao = new SSOLoginDAO(con);
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				// 从Cookie中获取LtpaToken
				if ("LtpaToken".equals(cookies[i].getName())) {
					ltpaToken = cookies[i].getValue();
					ltpaToken += "==";
				} else if ("SMSESSION".equals(cookies[i].getName())) {
					smsession = cookies[i].getValue();
					smsession += "==";
				}
			}
		}else{
			openErrorPage(req,res,"系统认证失败!");
		}
		if(ltpaToken != null && ltpaToken.length() <= 0&&smsession != null && smsession.length() <= 0)
		{
			openErrorPage(req,res,"不允许空登录");
		}
		// 判断LTPA Token
		if (ltpaToken != null && ltpaToken.length() > 0) {
			if (ltpaToken == null || ltpaToken.trim().length() == 0)
				try {
					throw new Exception("The LtpaToken is null!");
				} catch (Exception e1) {
					openErrorPage(req,res,"不允许空登录");
					e1.printStackTrace();
				}

			try {
				// 连接超时时间
				System.setProperty("sun.net.client.defaultConnectTimeout",
						"20000");
				// 读取超时时间
				System
						.setProperty("sun.net.client.defaultReadTimeout",
								"20000");
				// 取SSO 应用保护的userToken的页面地址
				URL url = new URL(
						"http://kmpassport.bmcc.com.cn/kmsso/index.jsp");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDefaultUseCaches(false);
				conn.setUseCaches(false);
				conn.setRequestProperty("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
				// 使用LtpaToken为cookie
				conn.setRequestProperty("Cookie", "LtpaToken=" + ltpaToken
						+ "; path=/; domain=.bmcc.com.cn");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "utf-8"));
				String line = reader.readLine();
				reader.close();
				conn.disconnect();
				// 解析用户id
				if (line != null && line.length() > 0
						&& line.indexOf("SSO_KM_USERID") != -1) {
					kmUserId = line.substring("SSO_KM_USERID".length() + 1);
					if (kmUserId.toLowerCase().equals("null"))
						throw new Exception("userId return null!");
				} else {
					openErrorPage(req,res,"系统认证失败!");
					throw new Exception("wrong token format file!");
				}

			} catch (MalformedURLException e) {
				openErrorPage(req,res,"系统认证失败!");
				System.out.println("LtpaToken url error!");
			} catch (IOException e) {
				openErrorPage(req,res,"系统认证失败!");
				System.out.println("Read LTPA Token error!");
			} catch (Exception e) {
				openErrorPage(req,res,"系统认证失败!");
				e.printStackTrace();
			}

			if (kmUserId != null && kmUserId.length() > 0) {
				// 获得的SSO用户ID，进行业务处理，并返回
				// .....
				try {
					List<SfUserDTO> userList = ssoLoginDao.getUserDto(kmUserId);
					if (userList.size() == 1) {
						SfUserDTO userDto = userList.get(0);
						req.setAttribute("loginName", userDto.getLoginName());
						req.setAttribute("loginPwd", userDto.getPassword());
						req.setAttribute("userType", "sso");
						forwardURL = "/servlet/com.sino.framework.security.servlet.UserLoginServlet";
					}else if(userList.size()>1){
						req.setAttribute("listUser", userList);
						forwardURL="/chooseUerPage.jsp";
					}else {
						openErrorPage(req,res,"系统无此用户!");
					}
				} catch (QueryException e) {
					openErrorPage(req,res,"系统认证失败!");
					e.printStackTrace();
				}
			}
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			return;
		}
		// 判断Siteminder Token
		if (smsession != null && smsession.length() > 0) {
			if (smsession == null || smsession.trim().length() == 0)
				try {
					throw new Exception("The SMSESSION is null!");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			try {
				// 连接超时时间
				System.setProperty("sun.net.client.defaultConnectTimeout",
						"20000");
				// 读取超时时间
				System
						.setProperty("sun.net.client.defaultReadTimeout",
								"20000");
				// 取siteminder保护的userToken的页面地址
				URL url = new URL(
						"http://kmapp01.bmcc.com.cn/mvnforum/token.jsp");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDefaultUseCaches(false);
				conn.setUseCaches(false);
				conn.setRequestProperty("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
				// 使用SMSESSION为cookie
				conn.setRequestProperty("Cookie", "SMSESSION=" + smsession
						+ "; path=/; domain=.bmcc.com.cn");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "utf-8"));
				String line = reader.readLine();
				reader.close();
				conn.disconnect();
				// 解析用户id
				if (line != null && line.length() > 0
						&& line.indexOf("SSO_KM_USERID") != -1) {
					kmUserId = line.substring("SSO_KM_USERID".length() + 1);
					if (kmUserId.toLowerCase().equals("null")) {
						throw new Exception("userId return null!");
					}
				} else {
					openErrorPage(req,res,"系统认证失败!");
					throw new Exception("wrong token format file!");
				}

			} catch (MalformedURLException e) {
				openErrorPage(req,res,"系统认证失败!");
				System.out.println("SMSESSION Token url error!");
			} catch (IOException e) {
				openErrorPage(req,res,"系统认证失败!");
				System.out.println("Read SMSESSION Token error!");
			} catch (Exception e) {
				openErrorPage(req,res,"系统认证失败!");
				e.printStackTrace();
			}
			if (kmUserId != null && kmUserId.length() > 0) {
				// 获得的SSO用户ID，进行业务处理，并返回
				// .....
				try {
					List<SfUserDTO> userList = ssoLoginDao.getUserDto(kmUserId);
					if (userList.size() == 1) {
						SfUserDTO userDto = userList.get(0);
						req.setAttribute("loginName", userDto.getLoginName());
						req.setAttribute("loginPwd", userDto.getPassword());
						req.setAttribute("userType", "sso");
						forwardURL = "/servlet/com.sino.framework.security.servlet.UserLoginServlet";
					}else if(userList.size()>1){
						req.setAttribute("listUser", userList);
						forwardURL="/chooseUerPage.jsp";
					}else{
						openErrorPage(req,res,"系统无此用户!");
					}
				} catch (QueryException e) {
					openErrorPage(req,res,"系统认证失败!");
					e.printStackTrace();
				}
			}
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			return;
		}
	}
	
	private void openErrorPage(HttpServletRequest req,HttpServletResponse res,String errorMsg)
	{
		try {
			res.setContentType("text/html;charset=gbk");
			PrintWriter print=res.getWriter();
			print.print("<script> window.showModalDialog('/ssoLoginError.html?inputStr="+errorMsg+"','','dialogWidth:500px;dialogHeight:100px;scroll:no;status:no')</script>");
			print.close();
			print.flush();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
