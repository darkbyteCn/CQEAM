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
import com.sino.base.exception.QueryException;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2008-1-10
 * Time: 9:49:01
 * Function:验证用户、读取用户信息（权限、组别等）
 */
public class ValidateUser extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();

        String m_userName = req.getParameter("username");
        String m_password = req.getParameter("password");
        String test = req.getParameter("test");

        if (test == null)
            test = "";

        if (DOC_TYPE != null) {
            out.println(DOC_TYPE);
        }

        if (test.compareTo("Y") == 0) {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        } else {
            out.println("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
        }

        out.println("<validateuser>");


        Connection con = null;
        try {
            con = DBManager.getDBConnection();

            String errMsg = checkUser(con, m_userName, m_password);
            boolean bSuccess = false;

            if (errMsg == null) {
                errMsg = "";
                bSuccess = true;
            }

            out.println(" <result message=\"" + errMsg + "\">" + String.valueOf(bSuccess) + "</result>");

            if (bSuccess) {
                StringBuffer m_sb = getUserRight(con, m_userName);
                out.println(m_sb.toString());
                SfUserDTO sfUser = PDAUtil.getUserInfo(con, m_userName);
                String companyCode=sfUser.getCompanyCode();
                if(sfUser.getBookTypeCode().length()>4){
                    companyCode=sfUser.getBookTypeCode().substring(sfUser.getBookTypeCode().length()-4);
                }
                out.println("<user_id id=\"" + sfUser.getUserId() + "\" name=\"" + sfUser.getUsername() + "\"  />");
                out.println("<org_id id=\"" + sfUser.getOrganizationId() + "\" />");
                out.println("<company_code code=\"" + companyCode + "\" />");
            }
        } catch (Exception e) {
            com.sino.base.log.Logger.logError("error:" + e.toString());
            out.println(" <result message=\"+e.toString()+\">false</result>");
        } finally {
            DBManager.closeDBConnection(con);
        }
        out.println("</validateuser>");

        out.close();
    }

    private StringBuffer getUserRight(Connection conn, String loginName) {
        StringBuffer m_sb = new StringBuffer("");

        try {
            SQLModel sqlModel = new SQLModel();
            List sqlArgs = new ArrayList();
            String sqlStr =
                    "SELECT SG.GROUP_ID,\n" +
                            "       SG.GROUP_NAME GROUPNAME,\n" +
                            "       SR.ROLE_ID,\n" +
                            "       SR.ROLE_NAME  ROLENAME,\n" +
                            "       SMD.DEPT_ID,\n" +
                            "       SMD.DEPT_NAME\n" +
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
                            "   AND UPPER(SUV.LOGIN_NAME) = UPPER(?)\n" +
                            " ORDER BY SG.GROUP_ID";

            sqlArgs.add(loginName);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);

            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);

            simpleQuery.executeQuery();

            m_sb.append("<groups>");

            if (simpleQuery.hasResult()) {
                String oldGroupId = "", newGroupID = "";
                RowSet rs = simpleQuery.getSearchResult();
                Row row = null;

                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    newGroupID = row.getStrValue("GROUP_ID");

                    if (oldGroupId.compareTo(newGroupID) != 0) {
                        if (i > 0) {
                            m_sb.append("</roles>");
                            m_sb.append("</group>");
                        }

                        m_sb.append("<group id=\"").append(newGroupID).append("\"");
                        m_sb.append(" name=\"").append(row.getStrValue("GROUPNAME")).append("\"");
                        m_sb.append(" dept_code=\"").append(row.getStrValue("DEPT_ID")).append("\"");
                        m_sb.append(" dept_name=\"").append(row.getStrValue("DEPT_NAME")).append("\"");
                        m_sb.append(" >");
                        m_sb.append("<roles>");
                        oldGroupId = newGroupID;
                    }


                    m_sb.append("<role id=\"").append(row.getStrValue("ROLE_ID")).append("\"");
                    m_sb.append(" name=\"").append(row.getStrValue("ROLENAME")).append("\"");
                    m_sb.append(" />");


                }

                m_sb.append("</roles>");
                m_sb.append("</group>");
            }

            m_sb.append("</groups>");
        } catch (Exception e) {
            com.sino.base.log.Logger.logError("error:" + e.toString());
        }

        return m_sb;

    }

    private String checkUser(Connection conn, String loginName, String password) {
        String retStr = null;

        try {
            SQLModel sqlModel = new SQLModel();
            String sqlStr = "SELECT *\n" +
                    "  FROM SF_USER_V SUV\n" +
                    " WHERE UPPER(SUV.LOGIN_NAME) = UPPER(?)\n" +
                    "   AND ( " + SyBaseSQLUtil.isNull() + "  OR SUV.PASSWORD = dbo.SFK_ENCODE(?))";
            List sqlArgs = new ArrayList();
            sqlArgs.add(loginName);
            sqlArgs.add(password);
            sqlArgs.add(password);
            sqlModel.setSqlStr(sqlStr);
            sqlModel.setArgs(sqlArgs);
            SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
            simpleQuery.executeQuery();
            boolean hasRecord = simpleQuery.hasResult();
            if (!hasRecord) {
                retStr = "用户名口令不符";
            }
        } catch (QueryException e) {
            e.printStackTrace();
        }

        return retStr;
    }
}