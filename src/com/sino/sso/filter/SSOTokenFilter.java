package com.sino.sso.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.sso.util.SSOUserLoginUtil;
import com.sxmcc.portal.SSOAgent;


public final class SSOTokenFilter implements Filter {

    private FilterConfig filterConfig;
    private String authenticateURL;
    private String userLoginPage;
    private List lst;
    private final String eamLogPortal = "http://ams.sxmcc.com.cn:7838/";
    private final String myWork = "com.sino.sso.servlet.OAInboxServlet";
    private final String myBox = "com.sino.sso.servlet.OABoxServlet";
    private final String portalLogin = "com.sino.sso.PortalLoginServlet";
    private final String eamLogin = "com.sino.ams.log.servlet.LoginFrmServlet";
    private boolean isValidateUser;


    public void destroy() {
        filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String requestStr = getRequestString(req);
        boolean haveSSOToken = false;

        if (needFileter(requestStr)) {
            boolean isTDUser = requestStr.indexOf("TD") > -1;
            String employeeNumber = "";
            String uid = "";
            SfUserDTO sfUserDTO = (SfUserDTO) SessionUtil.getUserAccount(req);
            SfUserDTO userAccount = new SfUserDTO();//默认用户信息
            userAccount.setUserId(0);

            ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
            SSOUserLoginUtil ssoUserLoginUtil = new SSOUserLoginUtil(servletConfig);
            try {
                if (req.getSession(false) == null || req.getSession().getAttribute("uid") == null) {
                    haveSSOToken = checkSSOToken(req);
//                    System.out.println("haveSSOToken = " + haveSSOToken);
                    if (haveSSOToken) {
                        SSOAgent sso = SSOAgent.getInstance(req, true);
                        if (sso != null) {
                            uid = sso.getUid();
                            employeeNumber = sso.getVisitorInfo("employeeNumber");
                            req.getSession().setAttribute("uid", uid);
                            req.getSession().setAttribute("employeeNumber", employeeNumber);
                            if (isTDUser) {
                                employeeNumber = "8" + employeeNumber.substring(1, employeeNumber.length());
                            }
                        }
                    }
                } else {
                    SSOAgent sso = SSOAgent.getInstance(req, true);
                    uid = sso.getUid();
                    req.getSession().setAttribute("uid", uid);
                    employeeNumber = sso.getVisitorInfo("employeeNumber");
                    if (isTDUser) {
                        employeeNumber = "8" + employeeNumber.substring(1, employeeNumber.length());
                    }
                }
            } catch (Exception e) {
                Logger.logInfo("SSO ERROR:" + e.toString());
                Logger.logInfo("URL=" + requestStr + "\r\nhaveSSOToken=" + haveSSOToken + "||userInfo:" + (sfUserDTO != null && sfUserDTO.getUserId() > 0));
            } finally {
                if (StrUtil.isEmpty(employeeNumber)) {
                    SessionUtil.saveUserSession(req, userAccount);
                } else {
                    sfUserDTO = ssoUserLoginUtil.validateUser(employeeNumber);
                    SessionUtil.saveUserSession(req, sfUserDTO);
                }
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        authenticateURL = this.filterConfig.getInitParameter("authenticateURL");
    }

    public String toString() {
        if (filterConfig == null) {
            return "RequestDumperFilter()";
        } else {
            StringBuffer sb = new StringBuffer("RequestDumperFilter(");
            sb.append(filterConfig);
            sb.append(")");
            return sb.toString();
        }
    }

    public SSOTokenFilter() {
        isValidateUser = false;
        authenticateURL = "http://portal.sxmcc.com.cn/amserver/UI/Login";
        userLoginPage = " /sso/oaNoLog.html";
        filterConfig = null;
        authenticateURL = "";
        initial();
    }

    private void initial() {
        lst = new ArrayList();
        lst.add("login.jsp");
        lst.add("UserLoginServlet");
        lst.add("UserLogOutServlet");
        lst.add("ResourceAuthServlet");
        lst.add("EtsFavoritesServlet");
    }

    /**
     * SSO验证令牌
     * @param request
     * @return
     */
    private boolean checkSSOToken(HttpServletRequest request) {
        boolean flag = false;
        Cookie cookies[] = request.getCookies();
        Cookie sCookie = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                sCookie = cookies[i];
                if (sCookie.getName().equals("iPlanetDirectoryPro")) {
                    flag = true;
                }
            }
        }

        return flag;
    }

    private String getRequestString(HttpServletRequest req) {
        String requestURL = req.getRequestURL().toString();
        String queryString = req.getQueryString();
        if (queryString != null) {
            requestURL = requestURL + "?" + queryString;
        }
        return requestURL;
    }

    /**
     * 根据请求URL确认是否需要验证过滤，目前只有Portal接口相关需要进行验证
     * @param requestStr
     * @return
     */
    private boolean needFileter(String requestStr) {
        boolean logFromPortal = requestStr.indexOf("com.sino.sso.servlet") > -1;
        boolean needLogOnEAM = requestStr.indexOf("PortalLoginServlet") > -1;
        return logFromPortal || needLogOnEAM;
    }

    private String getNextPage(String requestStr) {
        String nextPage = "";
        if (requestStr.indexOf("com.sino.sso.servlet.OABoxServlet") > -1) {
            nextPage = "/sso/oaNoBox.html";
        } else if (requestStr.indexOf("com.sino.sso.servlet.OAInboxServlet") > -1) {
            nextPage = "/sso/oaNoInBox.html";
        } else {
            nextPage = "/sso/oaNoLog.html";
        }
        Logger.logError("nextPage=" + nextPage);
        return nextPage;
    }
}
