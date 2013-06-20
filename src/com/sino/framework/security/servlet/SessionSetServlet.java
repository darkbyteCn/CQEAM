package com.sino.framework.security.servlet;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.ReflectionUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.bean.FormDataProducer;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dao.BaseLoginDAO;
import com.sino.framework.security.dao.PrivilegeAuthenticator;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 0.1
 */
public class SessionSetServlet extends BaseServlet {

    /**
     * 所有的Servlet都必须实现的方法。
     *
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = null;
        Connection conn = null;
        try {
            FilterConfigDTO filterDTO = SessionUtil.getFilterConfigDTO(req);
            String method = req.getMethod();
            if (!method.equalsIgnoreCase("post")) {
                req.getSession().removeAttribute(WebConstant.USER_INFO);
                message = getMessage("NOT_ALLOWED_METHOD");
                message.setIsError(true);
                forwardURL = filterDTO.getLoginUrl();
            } else {
                Request2DTO req2DTO = new Request2DTO();
                req2DTO.setDTOClassName(filterDTO.getUserDTO());
                BaseUserDTO loginUser = (BaseUserDTO) req2DTO.getDTO(req);
                String reDirectURL = req.getParameter(WebConstant.REDIRECT_URL);
                String loginDAO = filterDTO.getLoginDAO();
                conn = DBManager.getDBConnection();
                Object[] authenParas = new Object[2];
                authenParas[0] = loginUser;
                authenParas[1] = conn;
                BaseLoginDAO userLoginDAO = (BaseLoginDAO) ReflectionUtil.getInstance(loginDAO, authenParas);
                ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
                userLoginDAO.setServletConfig(servletConfig);
                userLoginDAO.setUserDtoName(filterDTO.getUserDTO()); //把web.xml里配置的userDto设进去
                boolean validUser = userLoginDAO.isValidUser();
                if (validUser) {
                    BaseUserDTO userAccount = userLoginDAO.getUserAccount();//合法用户需要继续检查资源权限
                    authenParas[0] = userAccount;
                    String authenCls = filterDTO.getAuthenticator();
                    PrivilegeAuthenticator authenticator = (PrivilegeAuthenticator) ReflectionUtil.getInstance(authenCls, authenParas);
                    boolean hasPrivilege = authenticator.hasPrivilege(reDirectURL);
                    String resoureName = authenticator.getUrlName();
                    if (!hasPrivilege) {
                        message = getMessage(MsgKeyConstant.NO_RES_PRIVI);
                        if (StrUtil.isEmpty(resoureName)) {
                            resoureName = reDirectURL;
                        }
                        message.addParameterValue(resoureName);
                        forwardURL = filterDTO.getNoPriviledgeURL();
                    } else {
                        forwardURL = reDirectURL;
                    }
                    SessionUtil.saveUserSession(req, userAccount);//无论对该自愿有无访问权限，都需要设置会话信息，方便用户请求其他资源而不必再次输入密码
                } else {//非法用户一律以账号和密码不符对待
                    FormDataProducer dataProducer = new FormDataProducer(req, reDirectURL);
                    dataProducer.produceData();
                    forwardURL = filterDTO.getTimeOutUrl();
                    message = userLoginDAO.getMessage();
                    message.setIsError(true);
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            setHandleMessage(req, message);
            DBManager.closeDBConnection(conn);
            ServletForwarder serForwarder = new ServletForwarder(req, res);
            serForwarder.forwardView(forwardURL);
        }
    }
}
