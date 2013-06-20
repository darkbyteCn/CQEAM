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
 * Function:取地市公司所有执行人信息
 */
public class GetImplementUser extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        String org_id = StrUtil.nullToString(req.getParameter("org_id"));
        String dept_id = StrUtil.nullToString(req.getParameter("dept_id"));

        int orgId = -1;
        if (StrUtil.isNotEmpty(org_id)) {
            orgId = Integer.valueOf(org_id);
        }

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }


        Logger.logInfo("PDA run GetArchiveUser servlet begin....");

        out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");


        out.println("<users>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            String returnStr = getImplementUsers(conn, orgId, dept_id);
            out.println(returnStr);
        } catch (PoolException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</users>");

        out.close();
    }

    /**
     * 根据ou取所有工单执行人信息
     * @param conn
     * @param org_id
     * @return
     */
    private String getImplementUsers(Connection conn, int org_id, String dept_id) {
        StringBuffer strBuffer = new StringBuffer("");

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();

       String sqlStr = "SELECT DISTINCT SG.GROUP_ID, SG.GROUP_NAME, SUV.USER_ID, SUV.USERNAME,SMD.DEPT_ID,SMD.DEPT_NAME\n" +
                "  FROM SF_USER_RIGHT    SUR,\n" +
                "       SF_USER_V        SUV,\n" +
                "       SF_GROUP         SG,\n" +
                "       SF_ROLE          SR,\n" +
                "       SINO_GROUP_MATCH SGM,\n" +
                "       SINO_MIS_DEPT    SMD\n" +
                " WHERE SUR.USER_ID = SUV.USER_ID\n" +
                "   AND SUR.GROUP_ID = SG.GROUP_ID\n" +
                "   AND SUR.ROLE_ID = SR.ROLE_ID\n" +
                "   AND SGM.GROUP_ID = SG.GROUP_ID\n" +
                "   AND SGM.DEPT_ID = SMD.DEPT_ID\n" +
                "   AND SUV.ORGANIZATION_ID = ?\n" +
                "   AND (?='' OR SMD.DEPT_ID = ?)\n" +
                "   AND SR.ROLE_NAME LIKE '%工单执行人'\n" +
                " ORDER BY SG.GROUP_ID";
        sqlArgs.add(org_id);
        sqlArgs.add(dept_id);
        sqlArgs.add(dept_id);

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
                    strBuffer.append("<user group_id=\"").append(row.getStrValue("GROUP_ID")).append("\"");
                    strBuffer.append(" group_name=\"").append(row.getStrValue("GROUP_NAME")).append("\"");
                    strBuffer.append(" user_id=\"").append(row.getStrValue("USER_ID")).append("\"");
                    strBuffer.append(" user_name=\"").append(row.getStrValue("USERNAME")).append("\"");
                    strBuffer.append(" dept_code=\"").append(row.getStrValue("DEPT_ID")).append("\"");
                    strBuffer.append(" dept_name=\"").append(row.getStrValue("DEPT_NAME")).append("\"");
                    strBuffer.append(" />");
                }
            }

        } catch (QueryException e) {
            e.printLog();
        } catch (PoolException e) {
            e.printLog();
        } catch (ContainerException e) {
            e.printLog();
        }
        return strBuffer.toString();
    }
}