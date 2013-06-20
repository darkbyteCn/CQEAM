package com.sino.sso.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sso.util.SSOUserLoginUtil;

/**
 * User: T_zhoujinsong
 * Date: 2010-11-11 14:00:17
 * Function:
 */
public class SSOForward extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        FilterConfigDTO filterDTO = null;
        try {
            String uid = StrUtil.nullToString(req.getParameter("uid"));
            String url = StrUtil.nullToString(req.getParameter("url"));
            filterDTO = SessionUtil.getFilterConfigDTO(req);
            ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
            SSOUserLoginUtil ssoUserLoginUtil = new SSOUserLoginUtil(servletConfig);
            SfUserDTO sfUserDTO = ssoUserLoginUtil.validateUser(uid);
            SessionUtil.saveUserSession(req, sfUserDTO);
            if (sfUserDTO != null) {
                forwardURL = url;
            }
        } catch (Exception e) {
            Logger.logError(e);
            forwardURL = "/login.jsp";
        } finally {
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
