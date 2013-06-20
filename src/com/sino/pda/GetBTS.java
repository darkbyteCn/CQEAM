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
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2008-1-15
 * Time: 11:58:01
 * Function:读取基站、营业厅数据
 */
public class GetBTS extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        String companyCode = StrUtil.nullToString(req.getParameter("companyCode"));

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        Logger.logInfo("PDA run GetBTS servlet begin....");

        out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");

        out.println("<objects>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getBTS(conn, out, companyCode);
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</objects>");
        Logger.logInfo("PDA run GetBTS servlet over....");
        out.close();
    }


    private void getBTS(Connection conn, PrintWriter out, String companyCode) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EB.CODE, EB.NAME, EB.CATEGORY, EB.COMPANY_CODE FROM EAM_BTS EB WHERE EB.COMPANY_CODE LIKE NVL(?,EB.COMPANY_CODE)";


        sqlArgs.add(companyCode);

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

                    stringBuffer.append("<object ");
                    stringBuffer.append(" code=\"").append(PDAUtil.xmlFormat(row.getStrValue("CODE"))).append("\"");
                    stringBuffer.append(" name=\"").append(PDAUtil.xmlFormat(row.getStrValue("NAME"))).append("\"");
                    stringBuffer.append(" category=\"").append(PDAUtil.xmlFormat(row.getStrValue("CATEGORY"))).append("\"");
                    stringBuffer.append(" company_code=\"").append(PDAUtil.xmlFormat(row.getStrValue("COMPANY_CODE"))).append("\"");
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