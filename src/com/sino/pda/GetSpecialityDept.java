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
 * Date: 2008-1-11
 * Time: 17:48:34
 * Function:读取OU下实物部门信息
 */
public class GetSpecialityDept extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }
        int orgId = StrUtil.strToInt(req.getParameter("org_id"));


        Logger.logInfo("PDA run GetSpecialityDept servlet begin....");

        out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");

        out.println("<depts>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getSpecialityDept(conn, out, orgId);
        } catch (PoolException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</depts>");

        out.close();

    }

    private void getSpecialityDept(Connection conn, PrintWriter out, int orgId) {
        try {
            SQLModel sqlModel = new SQLModel();
            String sqlStr =
                    "SELECT DEPT_CODE GROUP_CODE, DEPT_NAME GROUPNAME, ORG_ID ORGANIZATION_ID\n" +
                            "  FROM SINO_MIS_DEPT\n" +
                            " WHERE SPECIALITY_DEPT = 'Y'\n" +
                            "   AND ORG_ID = ?";


            List sqlArgs = new ArrayList();
            sqlArgs.add(orgId);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

            out.println("<dept group_code=\"\" groupname=\"\" organizationId=\"" + orgId + "\"/>  ");
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                RowSet rs = simpleQuery.getSearchResult();
                Row row = null;
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    StringBuffer m_sb = new StringBuffer("");
                    m_sb.append("<dept  ");
                    m_sb.append(" group_code=\"").append(row.getStrValue("GROUP_CODE")).append("\"");
                    m_sb.append(" groupname=\"").append(row.getStrValue("GROUPNAME")).append("\"");
                    m_sb.append(" organizationId=\"").append(row.getStrValue("ORGANIZATION_ID")).append("\"");
                    m_sb.append(" />");
                    out.println(m_sb.toString());
                }
            }

        } catch (QueryException e) {
            out.println("<error>" + e.toString() + "</error>");
        } catch (ContainerException e) {
            out.println("<error>" + e.toString() + "</error>");
        }
    }
}