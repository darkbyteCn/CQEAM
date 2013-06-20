package com.sino.ams.newasset.servlet;

import com.sino.base.web.ServletForwarder;
import com.sino.framework.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-6-10
 * Time: 22:31:02
 * To change this template use File | Settings | File Templates.
 */
public class ItemSpareFrmServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
		String forwardURL = "";
		try {
			forwardURL = "/newasset/itemSpareFrm.jsp";
		} finally {
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}