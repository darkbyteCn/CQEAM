package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-6-5
 * Time: 19:16:01
 * To change this template use File | Settings | File Templates.
 */
public class ItemVillageBottomServlet extends BaseServlet {

    /**
     * 所有的Servlet都必须实现的方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            conn = getDBConnection(req);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String deptOpt = optProducer.getAllDeptOption("");
            req.setAttribute(AssetsWebAttributes.DEPT_OPTIONS, deptOpt);
            String itemStatus = optProducer.getDictOption(AssetsDictConstant.ITEM_STATUS, "");
		    req.setAttribute(AssetsWebAttributes.ITEM_STATUS_OPTIONS, itemStatus);
            String specialDepOpt = optProducer.getSpecialAsssetsDeptOption("");
		    req.setAttribute("DEPT_OPTIONS2", specialDepOpt);
            String shareOption = optProducer.getDictOption("SHARE_STATUS", "");
            req.setAttribute("SHARE_OPTION", shareOption);
            String constructStatusOption = optProducer.getDictOption("CONSTRUCT_STATUS", "");
            req.setAttribute("CONSTRUCT_OPTION", constructStatusOption);
            forwardURL = "/newasset/itemVillageBottom.jsp";
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
