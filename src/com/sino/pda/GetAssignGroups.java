package com.sino.pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.db.conn.DBManager;
import com.sino.base.log.Logger;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.ams.newasset.dao.AmsMisEmployeeDAO;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class GetAssignGroups extends BaseServlet {
	private static final String CONTENT_TYPE = "application/xml; charset=GBK";

	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Connection conn = null;
		try {
            res.setContentType(CONTENT_TYPE);
            PrintWriter writer = res.getWriter();
            
            String str_orgId = StrUtil.nullToString(req.getParameter("org_id"));//组织名
            int orgId=-1;
            if (StrUtil.isNotEmpty(str_orgId)) {
                orgId=Integer.valueOf(str_orgId);
            }
            String lastUpdateDate = StrUtil.nullToString(req.getParameter("last_update_date"));//上次更新日期
			StringBuffer responeXML = new StringBuffer();
			conn = getDBConnection(req);
			AmsMisEmployeeDAO dao = new AmsMisEmployeeDAO(orgId, conn);
			if(dao.isValudOrgId()){
				if(!StrUtil.isEmpty(lastUpdateDate)){
					if(CalendarUtil.isValidCalendar(lastUpdateDate) || CalendarUtil.isValidDate(lastUpdateDate)){
						responeXML = dao.getPersonDeptXML(lastUpdateDate);
					} else {
						responeXML.append("<?xml version=\"1.0\" encoding=\"GB2312\" ?>");
						responeXML.append("<result message=\"传入日历值非法，系统无法处理\">");
						responeXML.append(false);
						responeXML.append("</result>");
					}
				} else {
					responeXML = dao.getPersonDeptXML(lastUpdateDate);
				}
			} else {
				responeXML.append("<?xml version=\"1.0\" encoding=\"GB2312\" ?>");
				responeXML.append("<result message=\"传入组织非法，不能下载数据\">");
				responeXML.append(false);
				responeXML.append("</result>");
			}
			writer.write(responeXML.toString());
			writer.close();
		} catch (Exception ex) {
			Logger.logError(ex);
		} finally {
			DBManager.closeDBConnection(conn);
		}
	}
}
