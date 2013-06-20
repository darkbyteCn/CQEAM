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
 * Function:读取资产目录网络层次对照关系字典数据
 */
public class GetContentNLE extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        Logger.logInfo("PDA run GetContentLNE servlet begin....");

        out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");

        out.println("<nle_contents>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getContentNle(conn, out);
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</nle_contents>");
        Logger.logInfo("PDA run GetContentLNE servlet over....");
        out.close();
    }


    private void getContentNle(Connection conn, PrintWriter out) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT IN_RANGE,\n" +
                        "       CORRESPONDENCE,\n" +
                        "       CONTENT_CODE,\n" +
                        "       CONTENT_NAME,\n" +
                        "       NLE_NAME,\n" +
                        "       NLE_CODE,\n" +
                        "       NLE_ID\n" +
                        "  FROM AMS_NLE_CONTENT";


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

                    stringBuffer.append("<lne_content  ");
                    stringBuffer.append(" in_range=\"").append(PDAUtil.xmlFormat(row.getStrValue("IN_RANGE"))).append("\"");
                    stringBuffer.append(" correspondence=\"").append(PDAUtil.xmlFormat(row.getStrValue("CORRESPONDENCE"))).append("\"");
                    stringBuffer.append(" content_code=\"").append(PDAUtil.xmlFormat(row.getStrValue("CONTENT_CODE"))).append("\"");
                    stringBuffer.append(" content_name=\"").append(PDAUtil.xmlFormat(row.getStrValue("CONTENT_NAME"))).append("\"");
                    stringBuffer.append(" nle_name=\"").append(PDAUtil.xmlFormat(row.getStrValue("NLE_NAME"))).append("\"");
                    stringBuffer.append(" nle_code=\"").append(PDAUtil.xmlFormat(row.getStrValue("NLE_CODE"))).append("\"");
                    stringBuffer.append(" nle_id=\"").append(PDAUtil.xmlFormat(row.getStrValue("NLE_ID"))).append("\"");
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