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
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2008-1-15
 * Time: 11:58:01
 * Function:取地点信息
 */
public class GetLocationInfo extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        String obj_category = StrUtil.nullToString(req.getParameter("OBJECT_Category"));
        String str_org_id = StrUtil.nullToString(req.getParameter("org_id"));
        int org_id = -1;
        if (StrUtil.isNotEmpty(str_org_id)) {
            org_id = Integer.valueOf(str_org_id);
        }

        String b_lastupdate = StrUtil.nullToString(req.getParameter("last_update_date"));

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        String test = StrUtil.nullToString(req.getParameter("test"));

        Logger.logInfo("PDA run GetLocationInfo servlet begin....");

        if (test.compareTo("Y") == 0) {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
        } else {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        }

        out.println("<locations>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getAllLocations(conn, out, obj_category, org_id, b_lastupdate);
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</locations>");

        out.close();
    }

    /**
     * 根据专业、ou、最后更新时间取地点信息（有效地点）
     * @param conn
     * @param out
     * @param obj_category
     * @param org_id
     * @param b_lastupdate
     */
    private void getAllLocations(Connection conn, PrintWriter out, String obj_category, int org_id, String b_lastupdate) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EO.WORKORDER_OBJECT_NO,\n" +
                "       EO.WORKORDER_OBJECT_CODE,\n" +
                "       EO.WORKORDER_OBJECT_NAME,\n" +
                "       EO.WORKORDER_OBJECT_LOCATION,\n" +
                "       (SELECT EFV.VALUE\n" +
                "          FROM ETS_FLEX_VALUE_SET EFVS, ETS_FLEX_VALUES EFV\n" +
                "         WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                "           AND EO.OBJECT_CATEGORY = EFV.CODE\n" +
                "           AND (EFVS.CODE = 'OBJECT_CATEGORY' OR EFVS.CODE= 'INV_TYPE')) LOCATION_TYPE,\n" +
                "       EO.BUSINESS_CATEGORY,\n" +
                "       (SELECT EFV.VALUE\n" +
                "          FROM ETS_FLEX_VALUE_SET EFVS, ETS_FLEX_VALUES EFV\n" +
                "         WHERE EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                "           AND EO.BUSINESS_CATEGORY = EFV.CODE\n" +
                "           AND EFVS.CODE ='INV_BIZ_CATEGORY') BUSINESS_TYPE,\n" +
                "       --EFV.VALUE,\n" +
                "       dbo.APP_CHECK_STATUS(EO.DISABLE_DATE) ENABLED" +
                "  FROM ETS_OBJECT EO , ETS_FLEX_VALUE_SET EFVS, ETS_FLEX_VALUES EFV\n" +
                "  WHERE \n" +
                "  EO.OBJECT_CATEGORY = EFV.CODE\n" +
                "  AND EFVS.FLEX_VALUE_SET_ID = EFV.FLEX_VALUE_SET_ID\n" +
                "  AND (EFVS.CODE = 'OBJECT_CATEGORY' OR\n" +
                "       (EFVS.CODE = 'INV_TYPE' AND EFV.CODE = '71'))\n" +
                "  AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.OBJECT_CATEGORY=?)\n" +
                "  AND ( ?=-1 OR EO.ORGANIZATION_ID=?)\n" +
                "  AND ( " + SyBaseSQLUtil.isNull() + "  OR (EO.LAST_UPDATE_DATE " + SyBaseSQLUtil.isNullNoParam() + " OR EO.LAST_UPDATE_DATE>?) OR ( EO.CREATION_DATE ='' OR EO.CREATION_DATE>?))\n" +
                "  AND EO.WORKORDER_OBJECT_CODE NOT LIKE '%DL%' \n" +
                "  AND EO.WORKORDER_OBJECT_CODE NOT LIKE '%XL%' \n" +
                "  AND EO.WORKORDER_OBJECT_CODE NOT LIKE '%GD%' \n" +
                "  AND EO.WORKORDER_OBJECT_CODE NOT LIKE '%GL%' \n" +
//                "   AND EO.WORKORDER_OBJECT_CODE NOT LIKE '%BJ%' \n" +
                "  ORDER BY EO.OBJECT_CATEGORY,EO.WORKORDER_OBJECT_NAME";
        sqlArgs.add(obj_category);
        sqlArgs.add(obj_category);
        sqlArgs.add(org_id);
        sqlArgs.add(org_id);
        sqlArgs.add(b_lastupdate);
        sqlArgs.add(b_lastupdate);
        sqlArgs.add(b_lastupdate);

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
                    StringBuffer m_sb = new StringBuffer("");

                    m_sb.append("<location object_no=\"").append(PDAUtil.xmlFormat(row.getStrValue("WORKORDER_OBJECT_NO"))).append("\"");
                    m_sb.append(" object_code=\"").append(PDAUtil.xmlFormat(row.getStrValue("WORKORDER_OBJECT_CODE"))).append("\"");
                    m_sb.append(" object_name=\"").append(PDAUtil.xmlFormat(row.getStrValue("WORKORDER_OBJECT_NAME"))).append("\"");
                    m_sb.append(" loc_type=\"").append(PDAUtil.xmlFormat(row.getStrValue("LOCATION_TYPE"))).append("\"");
                    m_sb.append(" business_type_code=\"").append(PDAUtil.xmlFormat(row.getStrValue("BUSINESS_CATEGORY"))).append("\"");
                    m_sb.append(" business_type=\"").append(PDAUtil.xmlFormat(row.getStrValue("BUSINESS_TYPE"))).append("\"");
                    m_sb.append(" Enabled=\"").append(row.getStrValue("ENABLED")).append("\"");
                    m_sb.append(" />");
                    out.println(m_sb.toString());
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
