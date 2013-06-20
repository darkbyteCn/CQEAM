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
import com.sino.ams.constant.AmsOrderConstant;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2008-1-15
 * Time: 11:58:01
 * Function:取地市公司所有归档人信息
 */
public class GetArchiveUser extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        String org_id = StrUtil.nullToString(req.getParameter("org_id"));

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
            String returnStr = getArchieveUsers(conn, orgId);
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
     * 根据ou取所有归档人信息
     * @param conn
     * @param org_id
     * @return
     */
    private String getArchieveUsers(Connection conn, int org_id) {
        StringBuffer strBuffer = new StringBuffer("");

        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT DISTINCT SG.GROUP_ID, SG.GROUP_NAME, SU.USER_ID, SU.USERNAME\n" +
                "  FROM SF_USER SU, SF_GROUP SG, SF_ROLE SR, SF_USER_RIGHT SUR\n" +
                " WHERE SUR.USER_ID = SU.USER_ID\n" +
                "   AND SUR.GROUP_ID = SG.GROUP_ID\n" +
                "   AND SUR.ROLE_ID = SR.ROLE_ID\n" +
                "   AND SU.ORGANIZATION_ID = ?\n" +
                "   AND (SU.DISABLE_DATE " + SyBaseSQLUtil.isNullNoParam() + "  OR SU.DISABLE_DATE>GETDATE())" +
                "   AND ( SR.ROLE_NAME = ? OR SR.ROLE_NAME='盘点工单归档人')";

        sqlArgs.add(org_id);
        sqlArgs.add(AmsOrderConstant.ACHIEVE_ROLE);

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