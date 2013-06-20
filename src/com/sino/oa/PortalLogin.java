package com.sino.oa;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dao.BaseLoginDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.base.util.StrUtil;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.message.Message;
import com.sino.base.dto.Request2DTO;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.db.conn.DBManager;
import com.sino.base.web.ServletForwarder;
//import com.sino.common.user.dto.SfUserDTO;
import com.sino.sinoflow.framework.security.dao.UserLoginDAO;
import com.branchitech.sso.client.SsoClientHelper;
import com.branchitech.sso.SsoCheckResult;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
/**
 * Created by IntelliJ IDEA.

 * To change this template use File | Settings | File Templates.
 */
public class PortalLogin extends BaseServlet{
     public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String uid = StrUtil.nullToString(req.getParameter("uid"));
        String dynpswd = StrUtil.nullToString(req.getParameter("dynpswd"));
        HttpConnection th = new HttpConnection();
        String retVal = th.validate(uid, dynpswd);
        String na=req.getHeader("iv-user");

//            uid= na;

        System.out.println("tttt = " + na);
        System.out.println("uid = " + uid);
        System.out.println("dynpswd = " + dynpswd);
        System.out.println("retVal = " + retVal);
        Message message = new Message();
        Connection conn = null;
        String forwardURL = "";
        FilterConfigDTO filterDTO = null;
        try {
             SsoCheckResult result = SsoClientHelper.getInstance().doCheck(req);
             String ia=result.getUsername();
            System.out.println("rrrrr = " + ia);
//            uid=ia;   //开放时候获取用户名
            Request2DTO req2DTO = new Request2DTO();
            filterDTO = SessionUtil.getFilterConfigDTO(req);
            forwardURL = filterDTO.getLoginUrl();
            req2DTO.setDTOClassName(filterDTO.getUserDTO());
            BaseUserDTO loginUser = (BaseUserDTO) req2DTO.getDTO(req);
            SfUserBaseDTO userAcc = (SfUserBaseDTO) loginUser;
            userAcc.setLoginName(uid);
            conn = DBManager.getDBConnection();


            PortalLoginDAO ald = new PortalLoginDAO(userAcc, userAcc, conn);
//            if (StrUtil.startsWith(retVal, "Y")) {
            if (true) {
                String loginDAO = filterDTO.getLoginDAO();
                Object[] consParas = new Object[2];
                consParas[0] = userAcc;
                consParas[1] = conn;
                UserLoginDAO userLoginDAO = null;
                ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
//                userLoginDAO = (UserLoginDAO) ReflectionUtil.getInstance(loginDAO, consParas);
                userLoginDAO.setServletConfig(servletConfig);
                 userLoginDAO.setUserDtoName(filterDTO.getUserDTO());
                UserLoginDAO ul = (UserLoginDAO) userLoginDAO;
                ul.setLoginType("SSO");

                if (ul.isValidUser()) {
                    if (ald.isDefaultPassword()) {    //密码是初始密码或者密码已过期
                        forwardURL = "/firstLogin.jsp?loginName="+uid;
                    } else {
                        BaseUserDTO userAccount = ul.getUserAccount();
                        SessionUtil.saveUserSession(req, userAccount);
                        // forward to login servlet
                        forwardURL = filterDTO.getLoginSuccessURL();
                    }
                } else {  // user not exist
                    message = ul.getMessage();
//                    message = getMessage(MsgKeyConstant.USER_NOT_EXIST);
                    message.setIsError(true);
                }
            } else {
                // OA 验证未通过
                message.setMessageValue("OA 验证未通过:" + retVal);
                message.setIsError(true);
            }
        } catch (DTOException e) {
            message.setMessageValue("登录时出错!");
            message.setIsError(true);
            Logger.logError(e);
        } catch (PoolPassivateException e) {
            message.setMessageValue("登录时出错!");
            message.setIsError(true);
            Logger.logError(e);
        } catch (ReflectException e) {
            message.setMessageValue("登录时出错!");
            message.setIsError(true);
            Logger.logError(e);
        } catch (QueryException e) {
            message.setMessageValue("登录时出错!");
            message.setIsError(true);
            Logger.logError(e);
        } catch (ContainerException e) {
            message.setMessageValue("登录时出错!");
            message.setIsError(true);
            Logger.logError(e);
        }catch (Exception e) {
            message.setMessageValue("登录时出错!");
            message.setIsError(true);
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
//            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
