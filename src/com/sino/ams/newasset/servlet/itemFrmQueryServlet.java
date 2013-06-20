package com.sino.ams.newasset.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.util.ResUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-3-14
 * Time: 11:40:23
 * To change this template use File | Settings | File Templates.
 */
public class itemFrmQueryServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Connection conn = null;
        try {
        	conn = DBManager.getDBConnection();
			ResUtil.setAllResName(conn, req, ResNameConstant.ITEM_QUERY );
            forwardURL = AssetsURLList.ITEM_FRM_QRY;
        } catch (PoolException e) {
			e.printLog(); 
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException e) {
			e.printLog(); 
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException e) {
			e.printLog(); 
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection( conn );
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
