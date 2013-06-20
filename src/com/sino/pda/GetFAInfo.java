package com.sino.pda;

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
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2008-1-11 15:12:25
 * Function:读取当前地市所有资产信息(MIS)，在PDA离线操作时可以查看资产信息
 * Date: 2009-10-20 增加读取TD-ERP资产信息
 */
public class GetFAInfo extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }
        String test = req.getParameter("test");
        String organizationId = StrUtil.nullToString(req.getParameter("org_id"));
        if (test == null) test = "";

        test = test.trim();

        Logger.logInfo("PDA run GetFAInfo servlet begin....");

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
            getAllTDAssetsInfo(conn,out,organizationId);
        } catch (PoolException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }
        out.println("</items>");

        out.close();
    }

    /**
     * 取改地市下所有资产信息
     * @param conn           Connection 数据库连接
     * @param out            PrintWriter 输出流
     * @param organizationId String 组织ID
     */
    private void getAllItemInfo(Connection conn, PrintWriter out, String organizationId) {
        try {
            SQLModel sqlModel = new SQLModel();
            String sqlStr =
                    "SELECT EFA.TAG_NUMBER BARCODE,\n" +
                            "       EFA.ASSETS_DESCRIPTION DESCRIPTION,\n" +
                            "       EFA.MODEL_NUMBER MODEL,\n" +
                            "       EFA.DATE_PLACED_IN_SERVICE DEPRN_START_DATE,\n" +
                            "       EFA.COST ORIGIN_COST,\n" +
                            "       EFA.LIFE_IN_YEARS LIFE_IN_YEAR,\n" +
                            "       EFA.ASSETS_LOCATION LOCATION,\n" +
                            "       EFA.ASSIGNED_TO_NAME ASSIGN_TO_NAME,\n" +
                            "       EFA.ASSET_ID,\n" +
                            "       EFA.FA_CATEGORY2 LEVEL2\n" +
                            "  FROM ETS_FA_ASSETS EFA\n" +
                            " WHERE EFA.ORGANIZATION_ID = ?";


            List sqlArgs = new ArrayList();
            sqlArgs.add(organizationId);
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
                    m_sb.append("<item ");
                    m_sb.append(" barcode=\"").append(row.getStrValue("BARCODE")).append("\"");
                    m_sb.append(" description=\"").append(StrUtil.xmlFormat(row.getStrValue("DESCRIPTION"))).append("\"");
                    m_sb.append(" model=\"").append(StrUtil.xmlFormat(row.getStrValue("MODEL"))).append("\"");
                    m_sb.append(" deprn_start_date=\"").append(row.getStrValue("DEPRN_START_DATE")).append("\"");
                    m_sb.append(" origin_cost=\"").append(row.getStrValue("ORIGIN_COST")).append("\"");
                    m_sb.append(" life_in_year=\"").append(row.getStrValue("LIFE_IN_YEAR")).append("\"");
                    m_sb.append(" location=\"").append(StrUtil.xmlFormat(row.getStrValue("LOCATION"))).append("\"");
                    m_sb.append(" assign_to_name=\"").append(row.getStrValue("ASSIGN_TO_NAME")).append("\"");
                    m_sb.append(" asset_id=\"").append(row.getStrValue("ASSET_ID")).append("\"");
                    m_sb.append(" level2=\"").append(row.getStrValue("LEVEL2")).append("\"");
                    m_sb.append(" />");
                    out.println(m_sb.toString());
                }
            }
        } catch (QueryException e) {
            out.println("<error>" + e.toString() + "</error>");
        } catch (ContainerException e) {
            out.println("<error>" + e.toString() + "</error>");
        }
    }

    /**
     * 取该地市下所有资产信息
     * @param conn           Connection 数据库连接
     * @param out            PrintWriter 输出流
     * @param organizationId String 组织ID
     */
    private void getAllTDAssetsInfo(Connection conn, PrintWriter out, String organizationId) {
        try {
            SQLModel sqlModel = new SQLModel();
            String sqlStr =
                    "SELECT EFA.TAG_NUMBER BARCODE,\n" +
                            "       EFA.ASSETS_DESCRIPTION DESCRIPTION,\n" +
                            "       EFA.MODEL_NUMBER MODEL,\n" +
                            "       EFA.DATE_PLACED_IN_SERVICE DEPRN_START_DATE,\n" +
                            "       EFA.COST ORIGIN_COST,\n" +
                            "       EFA.LIFE_IN_YEARS LIFE_IN_YEAR,\n" +
                            "       EFA.ASSETS_LOCATION LOCATION,\n" +
                            "       EFA.ASSIGNED_TO_NAME ASSIGN_TO_NAME,\n" +
                            "       EFA.ASSET_ID,\n" +
                            "       EFA.FA_CATEGORY2 LEVEL2\n" +
                            "  FROM ETS_FA_ASSETS_TD EFA\n" +
                            " WHERE EFA.ORGANIZATION_ID = ?";


            List sqlArgs = new ArrayList();
            sqlArgs.add(organizationId);
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
                    m_sb.append("<item ");
                    m_sb.append(" barcode=\"").append(row.getStrValue("BARCODE")).append("\"");
                    m_sb.append(" description=\"").append(StrUtil.xmlFormat(row.getStrValue("DESCRIPTION"))).append("\"");
                    m_sb.append(" model=\"").append(StrUtil.xmlFormat(row.getStrValue("MODEL"))).append("\"");
                    m_sb.append(" deprn_start_date=\"").append(row.getStrValue("DEPRN_START_DATE")).append("\"");
                    m_sb.append(" origin_cost=\"").append(row.getStrValue("ORIGIN_COST")).append("\"");
                    m_sb.append(" life_in_year=\"").append(row.getStrValue("LIFE_IN_YEAR")).append("\"");
                    m_sb.append(" location=\"").append(StrUtil.xmlFormat(row.getStrValue("LOCATION"))).append("\"");
                    m_sb.append(" assign_to_name=\"").append(row.getStrValue("ASSIGN_TO_NAME")).append("\"");
                    m_sb.append(" asset_id=\"").append(row.getStrValue("ASSET_ID")).append("\"");
                    m_sb.append(" level2=\"").append(row.getStrValue("LEVEL2")).append("\"");
                    m_sb.append(" />");
                    out.println(m_sb.toString());
                }
            }
        } catch (QueryException e) {
            out.println("<error>" + e.toString() + "</error>");
        } catch (ContainerException e) {
            out.println("<error>" + e.toString() + "</error>");
		}
	}
}
