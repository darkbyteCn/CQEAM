package com.sino.sinoflow.framework.security.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.hn.constant.PortalConstant;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.framework.resource.dto.SfResDefineDTO;
import com.sino.sinoflow.framework.security.dao.PrivilegeAuthorizer;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public class LoginFrmServlet extends BaseServlet {

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
        FilterConfigDTO filterDTO = getFilterConfig(req);
        
        String source = StrUtil.nullToString( req.getParameter( PortalConstant.PARAM_SOURCE_NAME ) );
        try {
            String method = req.getMethod();
            if (!method.equalsIgnoreCase("post") && !source.equalsIgnoreCase( "oa" )) {
                req.getSession().removeAttribute(WebConstant.USER_INFO);
                message = getMessage("NOT_ALLOWED_METHOD");
                message.setIsError(true);
                forwardURL = filterDTO.getLoginUrl();
            } else {
                BaseUserDTO userAccount = SessionUtil.getUserAccount(req);
                conn = DBManager.getDBConnection();
                PrivilegeAuthorizer authorizer = new PrivilegeAuthorizer(userAccount, conn);
                SfResDefineDTO firstResource = authorizer.getFirstAuthorizedResource();
                if (firstResource == null || firstResource.getResId().equals("")) {
                    forwardURL = filterDTO.getLoginUrl();
                    message = getMessage(MsgKeyConstant.LOGIN_NO_PRIVI);
                    message.setNeedBack(true);
                    message.setIsError(true);
                    SessionUtil.invalidateSession(req);
                } else {
                    req.setAttribute(WebAttrConstant.FIRST_RESOURCE, firstResource);
                    forwardURL = URLDefineList.HOME_PAGE;
                }
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
