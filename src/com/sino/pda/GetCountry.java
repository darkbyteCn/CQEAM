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
 * Function:读取区县字典数据
 */
public class GetCountry extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        Logger.logInfo("PDA run GetCounty servlet begin....");

        out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");

        out.println("<cities>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getCounty(conn, out);
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</cities>");
        Logger.logInfo("PDA run GetCounty servlet over....");
        out.close();
    }


    private void getCounty(Connection conn, PrintWriter out) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr =
                "SELECT AC.COUNTY_CODE, AC.COUNTY_NAME, AC.PARENT_CODE\n" +
                        "  FROM AMS_COUNTY AC";
                     


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

                    if (StrUtil.isEmpty(row.getStrValue("PARENT_CODE"))) {
                        if(i>0){
                            stringBuffer.append("</city>");
                        }
                        stringBuffer.append("<city  ");
                        stringBuffer.append(" county_code=\"").append(PDAUtil.xmlFormat(row.getStrValue("COUNTY_CODE"))).append("\"");
                        stringBuffer.append(" county_name=\"").append(PDAUtil.xmlFormat(row.getStrValue("COUNTY_NAME"))).append("\"");
                        stringBuffer.append(" >");
                        out.println(stringBuffer.toString());
                    }else{
                        stringBuffer.append("<county  ");
                        stringBuffer.append(" county_code=\"").append(PDAUtil.xmlFormat(row.getStrValue("COUNTY_CODE"))).append("\"");
                        stringBuffer.append(" county_name=\"").append(PDAUtil.xmlFormat(row.getStrValue("COUNTY_NAME"))).append("\"");
                        stringBuffer.append(" parent_code=\"").append(PDAUtil.xmlFormat(row.getStrValue("PARENT_CODE"))).append("\"");
                        stringBuffer.append(" />");
                        out.println(stringBuffer.toString());
                    }
                    if(i==rs.getSize()-1){
                        out.println("</city>"); 
                    }
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