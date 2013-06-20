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
 * Function:读取共建字典数据
 */
public class GetConstructDic extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        Logger.logInfo("PDA run GetConstructDic servlet begin....");

        out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");

        out.println("<constructs>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getShareDic(conn, out);
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</constructs>");
        Logger.logInfo("PDA run GetConstructDic servlet over....");
        out.close();
    }


    private void getShareDic(Connection conn, PrintWriter out) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT EFV.CODE, EFV.VALUE\n" +
                        "  FROM ETS_FLEX_VALUES EFV, ETS_FLEX_VALUE_SET EFVS\n" +
                        " WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                        "   AND EFVS.CODE = 'CONSTRUCT_STATUS'";



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

                    stringBuffer.append("<construct  ");
                    stringBuffer.append(" code=\"").append(PDAUtil.xmlFormat(row.getStrValue("CODE"))).append("\"");
                    stringBuffer.append(" name=\"").append(PDAUtil.xmlFormat(row.getStrValue("VALUE"))).append("\"");
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