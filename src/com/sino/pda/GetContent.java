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
import com.sino.base.util.StrUtil;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2008-1-15
 * Time: 11:58:01
 * Function:读取资产目录对照表，根据最后更新日期增量更新
 */
public class GetContent extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        String lastUpdateDate = StrUtil.nullToString(req.getParameter("last_update_date"));

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        Logger.logInfo("PDA run GetContent servlet begin....");

        out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");

        out.println("<contents>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getContent(conn, out, lastUpdateDate);
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</contents>");

        out.close();
    }


    private void getContent(Connection conn, PrintWriter out, String lastUpdateDate) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT" +
                "       OLD_CONTENT_CODE,\n" +
                "       OLD_CONTENT_NAME,\n" +
                "       NEW_CONTENT_CODE,\n" +
                "       NEW_CONTENT_NAME\n" +
                "  FROM AMS_CONTENT\n" +
                "  WHERE ( " + SyBaseSQLUtil.isNull() + "  OR (LAST_UPDATE_DATE>? OR " +
                " (CREATE_DATE=''  AND CREATE_DATE>?)))";

        sqlArgs.add(lastUpdateDate);
        sqlArgs.add(lastUpdateDate);
        sqlArgs.add(lastUpdateDate);

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

                    stringBuffer.append("<content ");
                    stringBuffer.append(" oldContentCode=\"").append(PDAUtil.xmlFormat(row.getStrValue("OLD_CONTENT_CODE"))).append("\"");
                    stringBuffer.append(" oldContentName=\"").append(PDAUtil.xmlFormat(row.getStrValue("OLD_CONTENT_NAME"))).append("\"");
                    stringBuffer.append(" newContentCode=\"").append(PDAUtil.xmlFormat(row.getStrValue("NEW_CONTENT_CODE"))).append("\"");
                    stringBuffer.append(" newContentName=\"").append(PDAUtil.xmlFormat(row.getStrValue("NEW_CONTENT_NAME"))).append("\"");
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