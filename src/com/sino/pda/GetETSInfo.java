package com.sino.pda;//package pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.GridQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2008-1-11
 * Time: 15:12:52
 * Function:
 */
public class GetETSInfo extends BaseServlet {
	private static final String CONTENT_TYPE = "application/xml; charset=GBK";
	private static final String DOC_TYPE = null;
	private String provinceCode = "";

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		provinceCode = getServletConfig(req).getProvinceCode();

		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();

		if (DOC_TYPE != null) {
			out.println(DOC_TYPE);
		}
		String test = req.getParameter("test");
		String organizationId = StrUtil.nullToString(req.getParameter("org_id"));
		if (test == null) test = "";

		test = test.trim();

		Logger.logInfo("PDA run GetItemCategory servlet begin....");

		if (test.compareTo("Y") == 0) {
			out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
		} else {
			out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		}

		out.println("<items>");
		Connection conn = null;
		try {
			conn = DBManager.getDBConnection();
			getAllItemInfo(conn, out, organizationId);
		} catch (PoolException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
		}

		out.println("</items>");

		out.close();
	}

	/**
	 * 取对应ou下的设备信息
	 * @param conn Connection
	 * @param out PrintWriter
	 * @param organizationId String
	 */
	private void getAllItemInfo(Connection conn, PrintWriter out, String organizationId) {
		try {
			SQLModel sqlModel = new SQLModel();
			String sqlStr = "SELECT"
							+ " EII.BARCODE,"
							+ " EII.ITEM_CODE,"
							+ " ESI.ITEM_NAME,"
							+ " ESI.ITEM_SPEC,"
							+ " ESI.ITEM_CATEGORY2,"
							+ " EII.PARENT_BARCODE,"
							+ " AOA.OBJECT_NO,"
							+ " dbo.APP_GET_OBJECT_NAME(AOA.OBJECT_NO) LOC,"
							+ " EII.RESPONSIBILITY_USER,"
							+ " EII.RESPONSIBILITY_DEPT,"
							+ " EII.ATTRIBUTE3,"
							+ " EII.PRICE,"
							+ " EII.MAINTAIN_USER"
							+ " FROM"
							+ " ETS_ITEM_INFO EII,"
							+ " ETS_SYSTEM_ITEM ESI,"
							+ " AMS_OBJECT_ADDRESS AOA"
							+ " WHERE"
							+ " EII.ITEM_CODE = ESI.ITEM_CODE"
							+ " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
							+ " AND EII.ORGANIZATION_ID = ?";
			List sqlArgs = new ArrayList();
			sqlArgs.add(organizationId);
			if (provinceCode.equals(DictConstant.PROVINCE_CODE_JIN)) {
				String itemCategorys = "('BSC', 'BTS', 'DATA', 'ELEC', 'EXCHG', 'NETOPT', 'TRANS', 'OTHERS')";
				sqlStr = sqlStr
						 + " AND ESI.ITEM_CATEGORY IN" + itemCategorys
						 + " AND EII.FINANCE_PROP <> 'DZYH'";
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
			GridQuery simpleQuery = new GridQuery(sqlModel, conn);
			simpleQuery.setPageSize(2000);
			simpleQuery.executeQuery();
			while (simpleQuery.nextPage()) {
				RowSet rs = simpleQuery.getSearchResult();
				Row row = null;
				for (int i = 0; i < rs.getSize(); i++) {
					row = rs.getRow(i);
					StringBuffer m_sb = new StringBuffer("");
					m_sb.append("<item  ");
					m_sb.append(" barcode=\"").append(row.getStrValue("BARCODE")).append("\"");
					m_sb.append(" item_code=\"").append(row.getStrValue("ITEM_CODE")).append("\"");
					m_sb.append(" name=\"").append(row.getStrValue("ITEM_NAME")).append("\"");
					m_sb.append(" spec=\"").append(StrUtil.xmlFormat(row.getStrValue("ITEM_SPEC"))).append("\"");
					m_sb.append(" parent_barcode=\"").append(row.getStrValue("PARENT_BARCODE")).append("\"");
					m_sb.append(" workorder_object_no=\"").append(row.getStrValue("OBJECT_NO")).append("\"");
					m_sb.append(" currentLocation=\"").append(row.getStrValue("LOC")).append("\"");
					m_sb.append(" assign_groupid=\"").append(PDAUtil.xmlFormat(row.getStrValue("RESPONSIBILITY_DEPT"))).append("\"");
					m_sb.append(" assign_userid=\"").append(PDAUtil.xmlFormat(row.getStrValue("RESPONSIBILITY_USER"))).append("\"");
					m_sb.append(" username=\"").append(row.getStrValue("MAINTAIN_USER")).append("\"");
					m_sb.append(" itemCategory2=\"").append(row.getStrValue("ITEM_CATEGORY2")).append("\"");
					m_sb.append(" attribute3=\"").append(row.getStrValue("ATTRIBUTE3")).append("\"");
					m_sb.append(" price=\"").append(row.getStrValue("PRICE")).append("\"");
					m_sb.append(" />");
					out.println(m_sb.toString());
				}
			}
		} catch (QueryException e) {
			out.println("<error>" + e.toString() + "</error>");
			e.printLog();
		} catch (ContainerException e) {
			out.println("<error>" + e.toString() + "</error>");
			e.printLog();
		}
	}
}
