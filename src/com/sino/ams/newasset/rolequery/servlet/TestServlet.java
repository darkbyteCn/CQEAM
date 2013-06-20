package com.sino.ams.newasset.rolequery.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.rolequery.dto.SfRoleQueryDTO;
import com.sino.ams.newasset.rolequery.model.SfRoleQueryResModel;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

public class TestServlet extends HttpServlet {

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
		Message message = SessionUtil.getMessage(request);
//		RowSet sets = (RowSet) request.getSession().getAttribute(QueryConstant.SPLIT_DATA_VIEW);
//		request.setAttribute(QueryConstant.SPLIT_DATA_VIEW,sets);
//		request.setAttribute(QueryConstant.SPLIT_PAGE_HTML, request.getSession().getAttribute(QueryConstant.SPLIT_PAGE_HTML));
		Connection conn = null;
		BaseSQLProducer sqlModel = null;
		String type="";
		String forwardURL="";
		type=request.getParameter("act").toString();
		forwardURL="/newasset/rolequery/lanmu.jsp";
		try {
			SfRoleQueryDTO dto;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfRoleQueryDTO.class.getName());
			dto = (SfRoleQueryDTO) req2DTO.getDTO(request);
			Object obj=request.getSession().getAttribute("DTO");
			if(obj!=null)
			{
				dto=(SfRoleQueryDTO) obj;
			}
			conn = DBManager.getDBConnection();
			SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(request);// 从session中获取数据，根据实际情况自行修改。
			sqlModel=new SfRoleQueryResModel(user,dto,"lanmu");
			pageQuery(sqlModel, conn, request);
		} catch (PoolException e) {
			
			e.printStackTrace();
		} catch (DTOException e) {
			
			e.printStackTrace();
		} catch (QueryException e) {
			
			e.printStackTrace();
		}finally{
			DBManager.closeDBConnection(conn);
			ServletForwarder forwarder = new ServletForwarder(request, response);
			forwarder.forwardView(forwardURL);
		}
	}
	private void pageQuery(BaseSQLProducer sqlProducer, Connection conn,
			HttpServletRequest req) throws QueryException {
		PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
		pageDAO.produceWebData();
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
		doGet(request, response);
	}

}
