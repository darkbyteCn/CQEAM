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
import com.sino.base.util.StrUtil;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: zy
 * Date: 2008-6-25
 * Time: 17:54:03
 * Function:
 */
public class BJGroupChooseServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        String action = StrUtil.nullToString(req.getParameter("act"));
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            OptionProducer optProducer = new OptionProducer(user, conn);
            String groupOptions = optProducer.getBJGroup2();
            req.setAttribute(AssetsWebAttributes.GROUP_OPTIONS, groupOptions);
            if (action.equals("")) {
                forwardURL = "/spare/bjChooseGroup.jsp";
            } else if (action.equals("1")) {
                forwardURL = "/spare/util/spareChooseGroup.jsp";
            } else if (action.equals("2")) {
                groupOptions = optProducer.getBJGroup("地市备品备件管理员");
                req.setAttribute(AssetsWebAttributes.GROUP_OPTIONS, groupOptions);
                forwardURL = "/spare/util/spareGroup2.jsp";
            }
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
