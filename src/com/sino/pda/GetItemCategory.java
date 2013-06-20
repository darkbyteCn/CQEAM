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
 * Date: 2008-1-11
 * Time: 14:36:41
 * Function:读取设备专业字典
 */
public class GetItemCategory extends BaseServlet {
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

       Logger.logInfo("PDA run GetItemCategory servlet begin....");

        if (test.compareTo("Y") == 0) {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
        } else {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        }

        out.println("<categorys>");
        Connection conn=null;
        try {
            conn=DBManager.getDBConnection();
            getAllItemCategory(conn,out);
        } catch (PoolException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</categorys>");

        out.close();

    }

    private void getAllItemCategory(Connection conn,PrintWriter out) {
        try {
            SQLModel sqlModel=new SQLModel();
            String sqlStr= "SELECT \n" +
                    "       EFV.CODE,\n" +
                    "       EFV.VALUE\n" +
                    "  FROM ETS_FLEX_VALUES EFV, ETS_FLEX_VALUE_SET EFVS\n" +
                    " WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                    " AND EFVS.CODE=?";
            List sqlArgs=new ArrayList();
            sqlArgs.add("ITEM_TYPE");
//            sqlArgs.add("NET");
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

            SimpleQuery simpleQuery=new SimpleQuery(sqlModel,conn);
            simpleQuery.executeQuery();
            if(simpleQuery.hasResult()){
                RowSet rs=simpleQuery.getSearchResult();
                Row row=null;
                for (int i = 0; i < rs.getSize(); i++) {
                    row=rs.getRow(i);
                    StringBuffer m_sb = new StringBuffer("");
                    m_sb.append("<category  ");
                    m_sb.append(" id=\"").append(row.getStrValue("CODE")).append("\"");
                    m_sb.append(" name=\"").append(row.getStrValue("VALUE")).append("\"");
                    m_sb.append(" />");
                    out.println(m_sb.toString());
                }
            }

        } catch (QueryException e) {
//            e.printStackTrace();
            out.println("<error>"+e.toString()+"</error>");
        } catch (ContainerException e) {
            out.println("<error>"+e.toString()+"</error>");
//            e.printStackTrace();
        }
    }
}
