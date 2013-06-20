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
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.framework.servlet.BaseServlet;


public class QueryUpdate extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    /**
     * 所有的Servlet都必须实现的方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        //参数处理
        String module = StrUtil.nullToString(req.getParameter("module"));

        module = module.trim();

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }
        Logger.logInfo("PDA run QueryUpdate servlet begin....");
        out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");

        out.print("<result>");
        Connection conn = null;

        try {
            conn = DBManager.getDBConnection();
            String outStr=getUpdateInfo(out, conn, module);
            out.println(outStr);
        } catch (Exception e) {
            out.println("<message >false</message>");
        } finally {
            DBManager.closeDBConnection(conn);
        }
        out.print("</result>");

        out.close();
    }

    private String getUpdateInfo(PrintWriter out, Connection conn, String module) throws QueryException, ContainerException {
        StringBuffer bufStr = new StringBuffer("");

        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT MODULE,\n" +
                "       VERSION,\n" +
                "       rtrim(STR_REPLACE(convert(char,getdate(),102),'.','-'))||' '||convert(char,getdate(),108) LAST_UPDATE_DATE,\n" +
                "       DESCRIPTION,\n" +
                "       FILESIZE\n" +
                "  FROM ETS_AUTOUPDATE\n" +
                " WHERE MODULE = ?";
        List sqlArgs = new ArrayList();
        sqlArgs.add(module);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
        simpleQuery.executeQuery();

        if (simpleQuery.hasResult()) {
            Row row = simpleQuery.getFirstRow();

            bufStr.append("<last_update ");
            bufStr.append(" version=\"").append(row.getStrValue("VERSION")).append("\" ");
            bufStr.append(" last_update_date=\"").append(row.getStrValue("LAST_UPDATE_DATE")).append("\" ");
            bufStr.append(" description=\"").append(row.getStrValue("DESCRIPTION")).append("\" ");
            bufStr.append(" FileSize=\"").append(row.getStrValue("FILESIZE")).append("\" ");
            bufStr.append(" />");
        }

      return bufStr.toString();
    }
}
