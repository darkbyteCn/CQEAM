package com.sino.sso.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sso.dao.SSOUserLoginDAO;

/**
 * User: Spencer
 * Date: 2010-7-19 9:00:19
 * Function:陕西移动财务集中管理平台 单点登录servlet
 */

public class SSOLoginByFCMPServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Connection conn = null;
        String forwardURL = "";
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO sfUserDTO = (SfUserDTO) SessionUtil.getUserAccount(req);
            ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
            FilterConfigDTO filterConfigDTO = SessionUtil.getFilterConfigDTO(req);
            SSOUserLoginDAO ssoUserLoginDAO = new SSOUserLoginDAO(servletConfig);
            forwardURL = filterConfigDTO.getLoginUrl();

            String ci=req.getParameter("CredibleInfo");
//            String endpoint = "http://10.131.93.246:8080/tunnel-web/axis/CreditService";
            String endpoint = "http://10.131.161.131:8900/tunnel-web/axis/CreditService";
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(endpoint));
            call.setOperationName("parseCredibleInfo");
            String employeeNumber = (String) call.invoke(new Object[] {ci});

            if (StrUtil.isNotEmpty(employeeNumber)) {
                sfUserDTO = ssoUserLoginDAO.validateUserByEmployeeNumber(employeeNumber);
                if (sfUserDTO != null && StrUtil.isNotEmpty(sfUserDTO.getUserId())) {
                    SessionUtil.saveUserSession(req, sfUserDTO);
                    forwardURL = filterConfigDTO.getLoginSuccessURL();
                }
            }
        } catch (PoolException e) {
            Logger.logError(e);
            e.printStackTrace();
        } catch (ServiceException e) {
            Logger.logError(e);
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}