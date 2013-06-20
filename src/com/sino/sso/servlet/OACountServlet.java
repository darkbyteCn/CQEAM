package com.sino.sso.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.PubServlet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.util.StrUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.flow.constant.ReqAttributeList;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.sso.dao.OADAO;
import com.sino.sso.util.SSOUserLoginUtil;
import com.sino.sso.util.SunJceEncrypt;

/**
 * OAÊÕ¼þÏäSERVLET
 * Created by wwb.
 * User: demo
 * Date: 2006-12-19
 * Time: 20:57:12
 */
public class OACountServlet extends PubServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nextPage = "/sx/oa/oaBox.jsp";
        Connection conn = null;
        try {
            SunJceEncrypt encrypt = new SunJceEncrypt();
            conn = DBManager.getDBConnection();
            String employeeNumberStr = StrUtil.nullToString(req.getParameter("uid"));
            String employeeNumber = encrypt.decryptMode(URLDecoder.decode(employeeNumberStr));
            ServletConfigDTO servletConfigDTO = new ServletConfigDTO();
            SSOUserLoginUtil loginUtil = new SSOUserLoginUtil(servletConfigDTO);
            SfUserDTO userAccount = loginUtil.validateUser(employeeNumber);

            if (userAccount != null && userAccount.getUserId() > 0) {
                String loginName = StrUtil.nullToString(userAccount.getLoginName());
                OADAO oaDAO = new OADAO();
                int count = oaDAO.getInboxCount(loginName, conn);
                nextPage = "/sx/oa/oaCount.jsp";
                req.setAttribute(ReqAttributeList.INBOX_COUNT, count);
            } else {
                nextPage = "/sx/oa/oaCount.jsp";
                req.setAttribute(ReqAttributeList.INBOX_COUNT, 0);
            }
        } catch (Exception e) {
            nextPage = "/sx/oa/oaCount.jsp";
            req.setAttribute(ReqAttributeList.INBOX_COUNT, 0);
        } finally {
            DBManager.closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }
}