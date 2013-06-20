package com.sino.pda;//package pda;

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
 * User: Yung Kam Hing
 * Date: 2008-6-30
 * Time: 15:58:01
 * Function:
 */
public class GetPDAConfig extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        //out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
        String test = req.getParameter("test");

        if (test == null) test = "";

        test = test.trim();

        Logger.logInfo("PDA run GetPDAConfig servlet begin....");

        if (test.compareTo("Y") == 0) {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
        } else {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        }

        out.println("<Config>");
          Connection conn=null;
        try {
            conn=DBManager.getDBConnection();
            getAllConfigs(conn,out);
        } catch (PoolException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</Config>");

        out.close();
    }

    private void getAllConfigs(Connection conn,PrintWriter out) {

        SQLModel sqlModel=new SQLModel();
        List sqlArgs=new ArrayList();
        String sqlStr= "SELECT DISTINCT APPNAME FROM ETS_PDA_CONFIG";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        try {
            SimpleQuery simpleQuery=new SimpleQuery(sqlModel,conn);
            simpleQuery.executeQuery();

            if (simpleQuery.hasResult()) {
                RowSet rs=simpleQuery.getSearchResult();
                Row row=null;
                for (int i = 0; i < rs.getSize(); i++) {
                    row=rs.getRow(i);
                    StringBuffer m_sb = new StringBuffer("");
                    String appName = PDAUtil.xmlFormat(row.getStrValue("APPNAME"));
                    
                    sqlStr = "SELECT * FROM ETS_PDA_CONFIG WHERE APPNAME='" + appName + "'";
                    sqlModel.setSqlStr(sqlStr);
                    sqlModel.setArgs(sqlArgs);
		                       
                    SimpleQuery simpleQuery2=new SimpleQuery(sqlModel,conn);
                    simpleQuery2.executeQuery();
                    if(simpleQuery2.hasResult()) {
	                    m_sb.append("<").append(appName);

                    	RowSet rs2=simpleQuery2.getSearchResult();
                    	Row row2=null;
                    	for(int j = 0; j < rs2.getSize(); j++) {
                    		row2=rs2.getRow(j);
                    
                    		m_sb.append(" ").append(PDAUtil.xmlFormat(row2.getStrValue("KEYNAME")));
                    		m_sb.append("=\"").append(PDAUtil.xmlFormat(row2.getStrValue("KEYVALUE")));
                    		m_sb.append("\"");
                    	}
                        m_sb.append(" />");
                    	out.println(m_sb.toString());
                    }
                }
            }
        } catch (QueryException e) {
            e.printStackTrace();
        } catch (PoolException e) {
            e.printStackTrace();
        } catch (ContainerException e) {
            e.printStackTrace();
        }
    }
}
