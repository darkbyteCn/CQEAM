package com.sino.ams.oa;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.exception.PoolPassivateException;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.servlet.BaseServlet;

public class OAPendServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Connection con = null;
		String pendIngCount="";
		PrintWriter out= res.getWriter();
		try {
			con = getDBConnection(req);
			String oaName = req.getParameter("userId");
			if (oaName != null) {
				SSOLoginDAO ssoLoginDao = new SSOLoginDAO(con);
				pendIngCount=ssoLoginDao.getPendingCount(oaName);
			}
		} catch (PoolPassivateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("pendingCount", pendIngCount);
		ServletForwarder forwarder = new ServletForwarder(req, res);
		forwarder.forwardView("/pendCount.jsp");
	}
}
