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
 * Date: 2008-3-26
 * Time: 17:54:58
 * Function:备件部件号查询，传入部件号
 */
public class GetSpareInfo extends BaseServlet {

    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }
        String test = StrUtil.nullToString(req.getParameter("test"));
        String barcode = StrUtil.nullToString(req.getParameter("barcode"));


        Logger.logInfo("PDA run GetSpareInfo servlet begin....");

        if (test.compareTo("Y") == 0) {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
        } else {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        }

        out.println("<categorys>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            String outStr = getSpareInfo(conn, barcode);
            out.println(outStr);

        } catch (PoolException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</categorys>");

        out.close();

    }

    /**
     * 取备件信息，如果传入barcode（部件号），则返回当前部件号信息、否则返回所有部件信息。
     * @param conn
     * @param barcode
     * @return
     */
    private String  getSpareInfo(Connection conn, String barcode) {
        StringBuffer returnStr=new StringBuffer();
        try {
            SQLModel sqlModel = new SQLModel();
            String sqlStr = "SELECT BARCODE, ITEM_CODE, ITEM_NAME, ITEM_SPEC\n" +
                    "  FROM AMS_SPARE_CATEGORY";
            List sqlArgs = new ArrayList();

            if(StrUtil.isNotEmpty(barcode)){
                sqlStr+=" WHERE BARCODE =?";
                sqlArgs.add(barcode);
            }

            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                RowSet rs = simpleQuery.getSearchResult();
                Row row = null;
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    returnStr.append("<category  ");
                    returnStr.append(" barcode=\"").append(row.getStrValue("BARCODE")).append("\"");
                    returnStr.append(" itemCode=\"").append(row.getStrValue("ITEM_CODE")).append("\"");
                    returnStr.append(" itemName=\"").append(row.getStrValue("ITEM_NAME")).append("\"");
                    returnStr.append(" itemSpec=\"").append(row.getStrValue("ITEM_SPEC")).append("\"");
                    returnStr.append(" />");
                }
            }

        } catch (QueryException e) {
            returnStr.append("<error>" + e.toString() + "</error>");
        } catch (ContainerException e) {
            returnStr.append("<error>" + e.toString() + "</error>");
        }

        return  returnStr.toString();

    }
}