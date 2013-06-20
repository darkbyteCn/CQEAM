package com.sino.ams.system.user.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dao.ChangeUserPasswordDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: 登录时修改密码并登入系统</p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-7-23
 */
public class FirstLoginServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "/firstLogin.jsp";
        Connection conn = null;
        Message message = new Message();
        String loginName = req.getParameter("loginName");
        String password = req.getParameter("password");
        SfUserDTO dtoParameter = new SfUserDTO();
        dtoParameter.setLoginName(loginName);
        dtoParameter.setPassword(password);

        try {
            conn = DBManager.getDBConnection();
            ChangeUserPasswordDAO dao = new ChangeUserPasswordDAO(null, dtoParameter, conn);
            dao.firstChangeUserPassword();
            message = dao.getMessage();
            forwardURL = "/servlet/com.sino.framework.security.servlet.UserLoginServlet";

        } catch (PoolException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
        } catch (DataHandleException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder sf = new ServletForwarder(req, res);
            sf.forwardView(forwardURL);
        }
    }
}
