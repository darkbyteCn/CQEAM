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
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2008-1-15
 * Time: 11:58:01
 * Function:读取投资分类字典数据
 */
public class GetCEX extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        Logger.logInfo("PDA run GetLNE servlet begin....");

        out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");

        out.println("<cexs>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getCex(conn, out);
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</cexs>");
        Logger.logInfo("PDA run GetLNE servlet over....");
        out.close();
    }


    private void getCex(Connection conn, PrintWriter out) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT AMS_CEX_ID,\n" +
                        "       INVEST_CATEGORY1,\n" +
                        "       INVEST_CATEGORY2,\n" +
                        "       INVEST_CAT_CODE,\n" +
                        "       INVEST_CAT_NAME\n" +
                        "  FROM AMS_CEX";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        try {
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();

            if (simpleQuery.hasResult()) {
                RowSet rs = simpleQuery.getSearchResult();
                Row row = null;
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    StringBuffer stringBuffer = new StringBuffer("");

                    stringBuffer.append("<cex  ");
                    stringBuffer.append(" cex_id=\"").append(PDAUtil.xmlFormat(row.getStrValue("AMS_CEX_ID"))).append("\"");
                    stringBuffer.append(" invest_category1=\"").append(PDAUtil.xmlFormat(row.getStrValue("INVEST_CATEGORY1"))).append("\"");
                    stringBuffer.append(" invest_category2=\"").append(PDAUtil.xmlFormat(row.getStrValue("INVEST_CATEGORY2"))).append("\"");
                    stringBuffer.append(" invest_cat_code=\"").append(PDAUtil.xmlFormat(row.getStrValue("INVEST_CAT_CODE"))).append("\"");
                    stringBuffer.append(" invest_cat_name=\"").append(PDAUtil.xmlFormat(row.getStrValue("INVEST_CAT_NAME"))).append("\"");
                    stringBuffer.append(" />");
                    out.println(stringBuffer.toString());
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