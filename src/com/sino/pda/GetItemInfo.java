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
import com.sino.base.util.StrUtil;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2008-1-11
 * Time: 16:11:01
 * Function:读取该组织下所有设备列表
 */
public class GetItemInfo extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        //参数处理
        String str_orgId = StrUtil.nullToString(req.getParameter("org_id"));
        String category = StrUtil.nullToString(req.getParameter("category"));
        String barcode = StrUtil.nullToString(req.getParameter("barcode"));
        String lastUpdateDate = StrUtil.nullToString(req.getParameter("last_update_date"));
        int orgId = -1;
        if (StrUtil.isNotEmpty(str_orgId)) {
            orgId = Integer.valueOf(str_orgId);
        }
        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        com.sino.base.log.Logger.logInfo("PDA run GetItemInfo servlet begin....");

        
        out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");

        out.println("<items>");
        Connection conn = null;
        try {
            conn = getDBConnection(req);

            if (StrUtil.isEmpty(barcode)) {
                getAllItemInfo(conn, out, orgId, category, lastUpdateDate);
            } else {
                getItemInfo(conn, out, barcode);
            }
        } catch (PoolException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</items>");

        out.close();
    }

    /**
     * 查找条码信息（包括责任人、责任部门、使用人、启用日期）
     * @param conn    数据库连接
     * @param out
     * @param barcode 条码
     */
    private void getItemInfo(Connection conn, PrintWriter out, String barcode) {
        StringBuffer sb = new StringBuffer();
        SQLModel sqlModel = new SQLModel();
        String sqlStr = "SELECT"
                + " EII.ITEM_CODE,"
                + " ESI.ITEM_NAME,"
                + " ESI.ITEM_CATEGORY,"
                + " ESI.ITEM_SPEC,"
                + " RTRIM(STR_REPLACE(convert(char,EII.START_DATE,102),'.','-')) START_DATE,"
                + " dbo.APP_CHECK_STATUS(EII.DISABLE_DATE) ENABLED,"
                + " EII.RESPONSIBILITY_DEPT,"
                + " EII.RESPONSIBILITY_USER,"
                + " EII.MAINTAIN_USER,"
                + " ESI.ITEM_CATEGORY2,"
                + " EII.PRICE,"
                + " EII.ATTRIBUTE3,"
                + " EII.MANUFACTURER_ID,"
                + " EII.IS_SHARE,"
                + " EII.CONTENT_CODE,"
                + " EII.CONTENT_NAME,"
                + " EII.POWER,"
                + " EO.WORKORDER_OBJECT_LOCATION"
                + " FROM"
                + " ETS_ITEM_INFO      EII,"
                + " ETS_SYSTEM_ITEM    ESI,"
                + " ETS_OBJECT         EO,"
                + " AMS_OBJECT_ADDRESS AOA"
                + " WHERE"
                + " EII.ITEM_CODE = ESI.ITEM_CODE"
                + " AND EO.WORKORDER_OBJECT_NO = AOA.OBJECT_NO"
                + " AND EII.ADDRESS_ID = AOA.ADDRESS_ID"
                + " AND EII.BARCODE = ?";

        List sqlArgs = new ArrayList();
        sqlArgs.add(barcode);
        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        try {
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                Row row = simpleQuery.getFirstRow();
                sb.append("<item ");
                sb.append(" item_code=\"").append(row.getStrValue("ITEM_CODE")).append("\"");
                sb.append(" item_category=\"").append(row.getStrValue("ITEM_CATEGORY")).append("\"");
                sb.append(" item_name=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_NAME"))).append("\"");
                sb.append(" item_spec=\"").append(PDAUtil.xmlFormat(row.getStrValue("ITEM_SPEC"))).append("\"");
                sb.append(" start_date=\"").append(PDAUtil.xmlFormat(row.getStrValue("START_DATE"))).append("\"");
                sb.append(" assign_groupid=\"").append(PDAUtil.xmlFormat(row.getStrValue("RESPONSIBILITY_DEPT"))).append("\"");
                sb.append(" assign_userid=\"").append(PDAUtil.xmlFormat(row.getStrValue("RESPONSIBILITY_USER"))).append("\"");
                sb.append(" username=\"").append(row.getStrValue("MAINTAIN_USER")).append("\"");
                sb.append(" currentLocation=\"").append(row.getStrValue("WORKORDER_OBJECT_LOCATION")).append("\"");
                sb.append(" item_category2=\"").append(row.getStrValue("ITEM_CATEGORY2")).append("\"");
                sb.append(" price=\"").append(row.getStrValue("PRICE")).append("\"");
                sb.append(" attribute3=\"").append(row.getStrValue("ATTRIBUTE3")).append("\"");
                sb.append(" manufacturerId=\"").append(row.getStrValue("MANUFACTURER_ID")).append("\"");
                sb.append(" contentCode=\"").append(row.getStrValue("CONTENT_CODE")).append("\"");
                sb.append(" contentName=\"").append(row.getStrValue("CONTENT_NAME")).append("\"");
                sb.append(" power=\"").append(row.getStrValue("POWER")).append("\"");
                sb.append(" isShare=\"").append(row.getStrValue("IS_SHARE")).append("\"");
                sb.append(" />");
                out.println(sb.toString());
            }
        } catch (QueryException e) {
            e.printLog();
        } catch (ContainerException e) {
            e.printLog();
        }
    }

    private void getAllItemInfo(Connection conn, PrintWriter out, int orgId, String category, String last_update_date) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "";

        sqlStr = "SELECT"
                + " ESI.ITEM_CODE,"
                + " ESI.ITEM_NAME,"
                + " ESI.ITEM_CATEGORY,"
                + " ESI.ITEM_SPEC,"
                + " ESD.ORGANIZATION_ID,"
                + " ESI.ITEM_CATEGORY2,"
                + " ESI.ENABLED"
                + " FROM"
                + " ETS_SYSTEM_ITEM ESI,"
                + " ETS_SYSITEM_DISTRIBUTE ESD"
                + " WHERE"
                + " ESI.ITEM_CODE = ESD.ITEM_CODE"
                + " AND ( ?=-1  OR ESD.ORGANIZATION_ID = ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR ESI.ITEM_CATEGORY = ?)"
                + " AND ( " + SyBaseSQLUtil.isNull() + "  OR (ESI.LAST_UPDATE_DATE > ?  OR ESI.CREATION_DATE > ?))"
                + " ORDER BY"
                + " ESI.ITEM_CATEGORY,"
                + " ESI.ITEM_NAME";
        sqlArgs.add(orgId);
        sqlArgs.add(orgId);
        sqlArgs.add(category);
        sqlArgs.add(category);
        sqlArgs.add(last_update_date);
        sqlArgs.add(last_update_date);
        sqlArgs.add(last_update_date);

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);


        try {
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();


            String m_oldItemNameAndCategory = "";
            String m_newItemName = "";
            String m_newItemCategory = "";
            boolean b_isSame = false;
            int n = 0;
            if (simpleQuery.hasResult()) {
                RowSet rs = simpleQuery.getSearchResult();
                Row row = null;
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);

                    StringBuffer m_sb = new StringBuffer("");
                    m_newItemName = StrUtil.replaceStr(row.getStrValue("ITEM_NAME"), "\"", "''");
                    m_newItemCategory = StrUtil.replaceStr(row.getStrValue("ITEM_CATEGORY"), "\"", "''");

                    if (m_oldItemNameAndCategory.compareTo(m_newItemName + '-' + m_newItemCategory) == 0) {
                        b_isSame = true;
                    } else {
                        b_isSame = false;
                        m_oldItemNameAndCategory = m_newItemName + '-' + m_newItemCategory;
                        if (i > 0) {
                            m_sb.append("</item>");
                        }
                    }

                    if (!b_isSame) {
                        m_sb.append("<item ");
                        m_sb.append(" item_name=\"").append(PDAUtil.xmlFormat(m_newItemName)).append("\"");
                        m_sb.append(" item_category=\"").append(PDAUtil.xmlFormat(m_newItemCategory)).append("\"");
                        m_sb.append(" >");
                    }
                    //<item_type name="TSGA" />
                    m_sb.append("<item_type ");
                    m_sb.append(" item_code=\"").append(row.getStrValue("ITEM_CODE")).append("\"");
                    m_sb.append(" name=\"").append(PDAUtil.xmlFormat(StrUtil.replaceStr(row.getStrValue("ITEM_SPEC"), "\"", "''"))).append("\"");
                    m_sb.append(" Enabled=\"").append(row.getStrValue("ENABLED")).append("\"");
                    m_sb.append(" item_category2=\"").append(row.getStrValue("ITEM_CATEGORY2")).append("\"");
                    m_sb.append(" />");

                    out.println(m_sb.toString());
                }
                out.println("</item>");
            }


        } catch (ContainerException e) {
            e.printLog();
        } catch (QueryException e) {
            e.printLog();
        }
    }
}
