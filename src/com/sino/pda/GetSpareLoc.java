package com.sino.pda;//package pda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.constant.calen.CalendarConstant;
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
 * Function:获取备件仓库地点
 */
public class GetSpareLoc extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        String obj_category = StrUtil.nullToString(req.getParameter("OBJECT_Category"));
        String org_id = StrUtil.nullToString(req.getParameter("org_id"));
        String b_lastupdate = StrUtil.nullToString(req.getParameter("last_update_date"));

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        //out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
        String test = StrUtil.nullToString(req.getParameter("test"));

        Logger.logInfo("PDA run GetSpareLoc servlet begin....");

        if (test.compareTo("Y") == 0) {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
        } else {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        }

        out.println("<locations>");
          Connection conn=null;
        try {
            conn=DBManager.getDBConnection();
            getAllLocations(conn,out, obj_category, org_id, b_lastupdate);
        } catch (PoolException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</locations>");

        out.close();
    }

    private void getAllLocations(Connection conn,PrintWriter out, String obj_category, String org_id, String b_lastupdate) {
        SQLModel sqlModel=new SQLModel();
        List sqlArgs=new ArrayList();
        String sqlStr= "SELECT EO.WORKORDER_OBJECT_NO,\n" +
                "       EO.WORKORDER_OBJECT_CODE,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION,\n" +
                "       AMS_PUB_PKG.CHECK_STATUS( EO.DISABLE_DATE) ENABLED\n" +
                "  FROM ETS_OBJECT EO\n" +
                "  WHERE (EO.OBJECT_CATEGORY>70 AND EO.OBJECT_CATEGORY < 80)\n" +
                "  AND EO.OBJECT_CATEGORY<>74" +
                "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.ORGANIZATION_ID=?)\n" +
                "  AND ( " + SyBaseSQLUtil.isNull() + "  OR (EO.LAST_UPDATE_DATE>TO_DATE(?,'"+ CalendarConstant.CAL_PATT_14+"') OR (EO.LAST_UPDATE_DATE " + SyBaseSQLUtil.isNullNoParam() + "   AND EO.CREATION_DATE>TO_DATE(?,'"+CalendarConstant.CAL_PATT_14+"'))))\n" +
                "  ORDER BY EO.OBJECT_CATEGORY," +
                " CASE WHEN EO.OBJECT_CATEGORY=10 THEN EO.WORKORDER_OBJECT_LOCATION ELSE EO.WORKORDER_OBJECT_NAME END" +
                "";
        sqlArgs.add(org_id);
        sqlArgs.add(org_id);
        sqlArgs.add(b_lastupdate);
        sqlArgs.add(b_lastupdate);
        sqlArgs.add(b_lastupdate);

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

                    m_sb.append("<location object_no=\"").append(PDAUtil.xmlFormat(row.getStrValue("WORKORDER_OBJECT_NO"))).append("\"");
                    m_sb.append(" object_name=\"").append(PDAUtil.xmlFormat(row.getStrValue("WORKORDER_OBJECT_NAME"))).append("\"");
                    m_sb.append(" Enabled=\"").append(row.getStrValue("ENABLED")).append("\"");
                    m_sb.append(" />");
                    out.println(m_sb.toString());
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