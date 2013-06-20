package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.servlet.BaseServlet;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.util.ResUtil;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ItemMaintainFrmServlet extends BaseServlet {

	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
			ServletException, IOException {
		String forwardURL = "";
//        boolean hasError = false;
		Connection conn = null;
		try {
			conn = DBManager.getDBConnection();
			Object obj=req.getParameter("type");
			if(obj!=null){
				if(obj.toString().equals("dwj")){
					req.getSession().setAttribute("DWZCGLY", "dwzcgly");
				}
			}
			ResUtil.setAllResName(conn, req, ResNameConstant.ITEM_MAINTAIN );
			forwardURL = AssetsURLList.ITEM_FRM_PAGE;
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
//            if (hasError) {
//                forwarder.forwardView(forwardURL);
//            } else {
//                forwarder.forwardView(AssetsURLList.ASSETS_ASSIGN_FRM);
//            }
		}
	}
}
