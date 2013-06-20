package com.sino.pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.model.PDAOrderUtilModel;

/**
 * User: zhoujs
 * Date: 2008-1-15
 * Time: 11:58:01
 * Function:取成本中心
 */
public class GetCostCenterServlet extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        String organizationId = StrUtil.nullToString(req.getParameter("org_id"));

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        String test = StrUtil.nullToString(req.getParameter("test"));

        Logger.logInfo("PDA run GetCostCenter servlet begin....");

        if (test.compareTo("Y") == 0) {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
        } else {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        }

        out.println("<costCenters>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getCostCenters(conn, out, organizationId);
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</costCenters>");

        out.close();
    }

    /**
     * 取指定ou下的成本中心清单
     * @param conn  数据库连接
     * @param out
     * @param organizationId
     */
    private void getCostCenters(Connection conn, PrintWriter out, String organizationId) {
        PDAOrderUtilModel pdaOrderUtilModel=new PDAOrderUtilModel();
        SQLModel sqlModel = pdaOrderUtilModel.getCostCenterModel(organizationId);

        try {
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();

            if (simpleQuery.hasResult()) {
                RowSet rs = simpleQuery.getSearchResult();
                Row row = null;
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    StringBuffer m_sb = new StringBuffer("");

                    m_sb.append("<costCenter  costCode=\"").append(row.getStrValue("COST_CENTER_CODE")).append("\"");
                    m_sb.append(" costName=\"").append(row.getStrValue("COST_CENTER_NAME")).append("\"");
                    m_sb.append(" />");
                    out.println(m_sb.toString());
                }
            }

        } catch (QueryException e) {
            e.printLog();
        } catch (PoolException e) {
            e.printLog();
        } catch (ContainerException e) {
            e.printLog();
        }
    }
}