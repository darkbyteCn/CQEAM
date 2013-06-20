package com.sino.ams.system.assetcatelogMaintenanceLNE.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.framework.security.bean.SessionUtil;

public class AssetAjaxServlet extends HttpServlet{
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		try {
			String matchCode="";
			String nleCode="";
			String lneCode="";
			String count = request.getParameter("count");
			Connection conn = DBManager.getDBConnection();
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(request);
			AssetsOptProducer aop=new AssetsOptProducer(userAccount,conn);
			if(count.equals("1")){
			String options=aop.getValue(matchCode);
			out.print(options);
			}else if(count.equals("2")){
				String options=aop.getNleValue(nleCode);
				out.print(options);
			}else if(count.equals("3")){
				String options=aop.getLneValue(lneCode);
				out.print(options);
			}
		} catch (PoolException e) {
			
			e.printStackTrace();
		} catch (QueryException e) {
			
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
	}

}
