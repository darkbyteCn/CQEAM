package com.sino.pda;//package pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.data.Row;
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
 * Date: 2008-1-10
 * Time: 10:34:55
 * Function:读取服务器时间
 */
public class GetServerTime extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        String test = req.getParameter("test");

        if (test == null) test = "";

        test = test.trim();
        Logger.logInfo("PDA run GetServerTime servlet begin....");
        if (test.compareTo("Y") == 0) {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
        } else {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        }

        out.println("<ServerTime>");
        Connection conn=null;
        try {
            conn=DBManager.getDBConnection();
            getCurDateTime(out,conn);
        } catch (PoolException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</ServerTime>");

        out.close();
    }

    private void getCurDateTime(PrintWriter out,Connection conn) {
        try {
            SQLModel sqlModel=new SQLModel();
            String sqlStr="SELECT rtrim(STR_REPLACE(convert(char,getdate(),102),'.','-')) DD,(convert(char,getdate(),108))  TT ";
            sqlModel.setSqlStr(sqlStr);

            SimpleQuery simpleQuery=new SimpleQuery(sqlModel, conn);

            simpleQuery.executeQuery();

            if (simpleQuery.hasResult()) {
                Row row=simpleQuery.getFirstRow();
                StringBuffer m_sb = new StringBuffer("");

                m_sb.append("<date>").append(row.getStrValue("DD")).append("</date>");
                m_sb.append("<time>").append(row.getStrValue("TT")).append("</time>");
                out.println(m_sb.toString());
            }

        } catch (QueryException e) {
            e.printStackTrace();
        } catch (ContainerException e) {
            e.printStackTrace();
        }
    }
}