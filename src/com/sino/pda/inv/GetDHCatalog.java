package com.sino.pda.inv;

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
import com.sino.pda.PDAUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-1-9
 * Time: 14:10:47
 * To change this template use File | Settings | File Templates.
 */
public class GetDHCatalog extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

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

        out.println("<catalogs>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getAllCatalog(conn, out, b_lastupdate);
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</catalogs>");

        out.close();
    }

    /**
     * 根据专业、ou、最后更新时间取地点信息（有效地点）
     * @param conn
     * @param out
     * @param b_lastupdate
     */
    private void getAllCatalog(Connection conn, PrintWriter out, String b_lastupdate) {

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT EDCS.SET_CODE,\n" +
                "       EDCS.SET_NAME,\n" +
                "       EDCV.CATALOG_CODE,\n" +
                "       EDCV.CATALOG_NAME,\n" +
                "       (SELECT V.VALUE\n" +
                "          FROM ETS_FLEX_VALUES V, ETS_FLEX_VALUE_SET S\n" +
                "         WHERE S.FLEX_VALUE_SET_ID = V.FLEX_VALUE_SET_ID\n" +
                "           AND S.CODE = 'UNIT_OF_MEASURE'\n" +
                "           AND V.FLEX_VALUE_ID = EDCV.UNIT) UNIT_DESC,\n" +
                "       EDCV.ENABLED S_ENABLED,EDCS.ENABLED V_ENABLED\n" +
                "  FROM EAM_DH_CATALOG_SET EDCS, EAM_DH_CATALOG_VALUES EDCV\n" +
                " WHERE EDCS.CATLOG_SET_ID = EDCV.CATALOG_SET_ID\n" +
                " ORDER BY EDCS.SET_CODE, EDCV.CATALOG_CODE";

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);


        try {
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();

            if (simpleQuery.hasResult()) {
                RowSet rs = simpleQuery.getSearchResult();
                Row row = null;
                /*
<item item_name=”载频板” item_category=”222”>
		 < item_type item_code =”111” name =”TSGA” Enabled=”Y” />
		 < item_type item_code =”112” name =”TSGB” Enabled=”N”  />
				…
		</item>

                */
                String tmpSetCode=null,newSetCode=null;
                StringBuffer m_sb = new StringBuffer("");

                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    newSetCode= PDAUtil.xmlFormat(row.getStrValue("SET_CODE"));
                    if(!newSetCode.equalsIgnoreCase(tmpSetCode))
                    {
                          if(tmpSetCode!=null)
                          {
                             m_sb.append("</catalog_set>");
                             out.println(m_sb.toString());
                             m_sb = new StringBuffer("");
                          }
                          tmpSetCode= newSetCode;
                          m_sb.append("<catalog_set set_code=\"").append(newSetCode).append("\"");
                          m_sb.append(" set_name=\"").append(PDAUtil.xmlFormat(row.getStrValue("SET_NAME"))).append("\"");
                          m_sb.append(" Enabled=\"").append(row.getStrValue("S_ENABLED")).append("\" >");
                    }
                    m_sb.append("<catalog catalog_code=\"").append(PDAUtil.xmlFormat(row.getStrValue("CATALOG_CODE"))).append("\"");
                    m_sb.append(" catalog_name=\"").append(PDAUtil.xmlFormat(row.getStrValue("CATALOG_NAME"))).append("\"");
                    m_sb.append(" unit_desc=\"").append(PDAUtil.xmlFormat(row.getStrValue("UNIT_DESC"))).append("\"");
                    m_sb.append(" Enabled=\"").append(row.getStrValue("V_ENABLED")).append("\"");
                    m_sb.append(" />");
                    //out.println(m_sb.toString());
                }
                if(m_sb.length()>0)
                {
                     m_sb.append("</catalog_set>");
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
