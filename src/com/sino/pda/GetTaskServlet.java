package com.sino.pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.util.StrUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.dao.TaskDownloadDAO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class GetTaskServlet extends BaseServlet {
	private static final String CONTENT_TYPE = "application/xml; charset=GBK";

	/**
	 * 所有的Servlet都必须实现的方法。
	 *
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();

		String organizationId = StrUtil.nullToString(req.getParameter("org_id"));
        int orgId = -1;
        if(StrUtil.isInteger(organizationId)){
            orgId = Integer.parseInt(organizationId);
        }
		Connection conn = null;
		try {
			conn = getDBConnection(req);
			TaskDownloadDAO taskDownDAO = new TaskDownloadDAO(orgId, conn);
			out.print(taskDownDAO.getTaskXML());
		} catch (PoolException e) {
			e.printLog();
		} finally {
			DBManager.closeDBConnection(conn);
			out.close();
		}
	}
}
