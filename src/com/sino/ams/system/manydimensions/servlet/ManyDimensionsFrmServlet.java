package com.sino.ams.system.manydimensions.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.web.ServletForwarder;

import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.newasset.constant.AssetsURLList;

/**
 * <p>Description:  陕西移动资产管理系统</p>
 * <p>Company:      北京思诺博信息技术有限公司</p>
 * @author          李轶
 * @date            2009-08-05
 */
public class ManyDimensionsFrmServlet extends BaseServlet {

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
//        boolean hasError = false;
		try {
			forwardURL = AssetsURLList.MANY_DIMENSION_FRM_PAGE;
		} finally {
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
//            if (hasError) {
//                forwarder.forwardView(forwardURL);
//            } else {
//                forwarder.forwardView(AssetsURLList.ASSETS_ASSIGN_FRM);
//            }
		}
	}
}