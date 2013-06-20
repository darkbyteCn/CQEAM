package com.sino.ams.newasset.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.base.web.ServletForwarder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2009-3-14
 * Time: 11:28:31
 * To change this template use File | Settings | File Templates.
 */
public class ItemCostEasyFrmServlet extends BaseServlet {

	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
			ServletException, IOException {
		String forwardURL = "";
		try {
			forwardURL = "/newasset/itemCostEasyFrm.jsp";
		} finally {
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
