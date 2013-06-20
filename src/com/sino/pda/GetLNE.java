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
 * Function:¶ÁÈ¡Âß¼­ÍøÂçÔªËØ×ÖµäÊý¾Ý
 */
public class GetLNE extends BaseServlet {
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

        out.println("<lnes>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getlne(conn, out);
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</lnes>");
        Logger.logInfo("PDA run GetLNE servlet over....");
        out.close();
    }


    private void getlne(Connection conn, PrintWriter out) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT AMS_LNE_ID,\n" +
                        "       NET_CATEGORY1,\n" +
                        "       NET_CATEGORY2,\n" +
                        "       NET_UNIT_CODE,\n" +
                        "       LOG_NET_ELE,\n" +
                        "       ENG_AB\n" +
                        "  FROM AMS_LNE";


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

                    stringBuffer.append("<lne  ");
                    stringBuffer.append(" lne_id=\"").append(PDAUtil.xmlFormat(row.getStrValue("AMS_LNE_ID"))).append("\"");
                    stringBuffer.append(" net_category1=\"").append(PDAUtil.xmlFormat(row.getStrValue("NET_CATEGORY1"))).append("\"");
                    stringBuffer.append(" net_category2=\"").append(PDAUtil.xmlFormat(row.getStrValue("NET_CATEGORY2"))).append("\"");
                    stringBuffer.append(" net_unit_code=\"").append(PDAUtil.xmlFormat(row.getStrValue("NET_UNIT_CODE"))).append("\"");
                    stringBuffer.append(" log_net_ele=\"").append(PDAUtil.xmlFormat(row.getStrValue("LOG_NET_ELE"))).append("\"");
                    stringBuffer.append(" eng_ab=\"").append(PDAUtil.xmlFormat(row.getStrValue("ENG_AB"))).append("\"");
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