package com.sino.sso.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sso.dao.SSOUserLoginDAO;

/**
 * User: zhoujs
 * Date: 2009-5-6 10:29:19
 * Function:µ¥µãµÇÂ¼servlet
 */

public class SSOLoginServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Connection conn = null;
        String forwardURL = "";
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO sfUserDTO = (SfUserDTO) SessionUtil.getUserAccount(req);
            ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
            FilterConfigDTO filterConfigDTO = SessionUtil.getFilterConfigDTO(req);
            SSOUserLoginDAO ssoUserLoginDAO = new SSOUserLoginDAO(servletConfig);
            String uid = ssoUserLoginDAO.validateSSOUser(req);
            forwardURL = filterConfigDTO.getLoginUrl();
//            uid = "CAOBO";
            if (StrUtil.isNotEmpty(uid)) {
                sfUserDTO = ssoUserLoginDAO.validateUser(uid);
                if (sfUserDTO != null && StrUtil.isNotEmpty(sfUserDTO.getUserId())) {
                    SessionUtil.saveUserSession(req, sfUserDTO);
                    forwardURL = filterConfigDTO.getLoginSuccessURL();
                }
            }
        } catch (Exception e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
