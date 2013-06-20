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
import com.sino.base.util.StrUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2008-1-11
 * Time: 17:48:34
 * Function:读取指定组别下所有用户信息
 */
public class GetGroupUsers extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }
        String test = req.getParameter("test");
        String str_groupId = StrUtil.nullToString(req.getParameter("GroupID"));
        int groupId = -1;
        if (StrUtil.isNotEmpty(str_groupId)) {
            groupId = Integer.valueOf(str_groupId);
        }

        if (test == null) test = "";

        test = test.trim();

        Logger.logInfo("PDA run GetGroupUsers servlet begin....");

        if (test.compareTo("Y") == 0) {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?> ");
        } else {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        }

        out.println("<users>");
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            getUsersOfGroup(conn, out, groupId);
        } catch (PoolException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
        }

        out.println("</users>");

        out.close();

    }

    private void getUsersOfGroup(Connection conn, PrintWriter out, int groupId) {
        try {
            SQLModel sqlModel = new SQLModel();
            String sqlStr = "SELECT DISTINCT SU.LOGIN_NAME, SU.USER_ID, SU.USERNAME\n" +
                    "  FROM SF_USER SU, SF_USER_RIGHT SUR\n" +
                    " WHERE SU.USER_ID = SUR.USER_ID\n" +
                    "   AND SUR.GROUP_ID = ?";

            List sqlArgs = new ArrayList();
            sqlArgs.add(groupId);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            if (simpleQuery.hasResult()) {
                RowSet rs = simpleQuery.getSearchResult();
                Row row = null;
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    StringBuffer m_sb = new StringBuffer("");
                    m_sb.append("<user  ");
                    m_sb.append(" loginname=\"").append(row.getStrValue("LOGIN_NAME")).append("\"");
                    m_sb.append(" userid=\"").append(row.getStrValue("USER_ID")).append("\"");
                    m_sb.append(" username=\"").append(row.getStrValue("USERNAME")).append("\"");
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