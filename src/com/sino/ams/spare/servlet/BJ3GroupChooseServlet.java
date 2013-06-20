package com.sino.ams.spare.servlet;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.constant.SpareURLDefine;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * User: Administrator
 * Date: 2008-6-25
 * Time: 20:17:28
 * Function:
 */
public class BJ3GroupChooseServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            OptionProducer optProducer = new OptionProducer(user, conn);
            String groupOptions = optProducer.getBJGroup("地市备品备件管理员");
            req.setAttribute(AssetsWebAttributes.GROUP_OPTIONS, groupOptions);
            forwardURL = SpareURLDefine.CHOOSE_GROUP3;
        } catch (QueryException e) {
            e.printLog();
        } catch (PoolException e) {
            e.printLog();
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
