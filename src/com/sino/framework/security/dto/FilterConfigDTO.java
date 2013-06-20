package com.sino.framework.security.dto;

import com.sino.framework.dto.BaseLocaleDTO;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class FilterConfigDTO extends BaseLocaleDTO{
	private String authenticator = "";//权限验证器
	private String loginUrl = "";//登录页面
	private String loginServlet = "";//登录页面提交的Servlet
	private String logOutServlet = "";//退出登录用的Servlet
	private String sessionServlet = "";//会话有效性管理Servlet
	private String timeOutUrl = "";//会话过期页面
	private String noPriviledgeURL = "";//无权限时的页面
	private String exceptServlets = "";//不必验证的servlet
	private String exceptJSPs = "";//不必验证的JSP
	private String exceptions = "";//不必验证的其他类文件
	private String userDTO = "";//代表用户的完整类名
	private String loginDAO = "";//用户登录时使用的有效性验证类
	private String loginSuccessURL = "";//登陆成功后导向的页面
	private String loginName = "";//登录帐号表单域名
	private String loginPwd = "";//登录密码表单域名

	public void setAuthenticator(String authenticator) {
		this.authenticator = authenticator;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setTimeOutUrl(String timeOutUrl) {
		this.timeOutUrl = timeOutUrl;
	}

	public void setNoPriviledgeURL(String noPriviledgeURL) {
		this.noPriviledgeURL = noPriviledgeURL;
	}

	public void setExceptServlets(String exceptServlets) {
		this.exceptServlets = exceptServlets;
	}

	public void setExceptJSPs(String exceptJSPs) {
		this.exceptJSPs = exceptJSPs;
	}

	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}

	public void setUserDTO(String userDTO) {
		this.userDTO = userDTO;
	}

	public void setLoginSuccessURL(String loginSuccessURL) {
		this.loginSuccessURL = loginSuccessURL;
	}

	public void setLoginDAO(String loginDAO) {
		this.loginDAO = loginDAO;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public void setLoginServlet(String loginServlet) {
		this.loginServlet = loginServlet;
	}

	public void setLogOutServlet(String logOutServlet) {
		this.logOutServlet = logOutServlet;
	}

	public void setSessionServlet(String sessionServlet) {
		this.sessionServlet = sessionServlet;
	}

	public String getAuthenticator() {
		return authenticator;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public String getTimeOutUrl() {
		return timeOutUrl;
	}

	public String getNoPriviledgeURL() {
		return noPriviledgeURL;
	}

	public String getExceptServlets() {
		return exceptServlets;
	}

	public String getExceptJSPs() {
		return exceptJSPs;
	}

	public String getExceptions() {
		return exceptions;
	}

	public String getUserDTO() {
		return userDTO;
	}

	public String getLoginSuccessURL() {
		return loginSuccessURL;
	}

	public String getLoginDAO() {
		return loginDAO;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public String getLoginServlet() {
		return loginServlet;
	}

	public String getLogOutServlet() {
		return logOutServlet;
	}

	public String getSessionServlet() {
		return sessionServlet;
	}
}
