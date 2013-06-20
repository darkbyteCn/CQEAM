package com.sino.ams.spare.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-6-25
 * Time: 20:17:28
 * To change this template use File | Settings | File Templates.
 */
public class BJ4GroupChooseServlet extends BaseServlet {


    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
//            Request2DTO req2DTO = new Request2DTO();
            OptionProducer optProducer = new OptionProducer(user, conn);
            String groupOptions = optProducer.getBJGroup("地市备品备件管理员");
            req.setAttribute(AssetsWebAttributes.GROUP_OPTIONS, groupOptions);
            forwardURL = "/spare/bjChooseGroup4.jsp";
        } catch (QueryException e) {
            e.printLog();
        } catch (PoolException e) {
             e.printLog();
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }
}
