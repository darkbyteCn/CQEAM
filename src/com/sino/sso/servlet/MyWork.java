package com.sino.sso.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.pda.PDAUtil;
import com.sino.sso.dao.SSODAO;
import com.sino.sso.dao.SSOUserLoginDAO;

/**
 * User: zhoujs
 * Date: 2008-4-24
 * Time: 11:58:01
 * Function:读取EAM在办箱信息，
 */
public class MyWork extends BaseServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=GBK";
    private static final String DOC_TYPE = null;

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType(CONTENT_TYPE);
        PrintWriter out = res.getWriter();
        String userId = StrUtil.nullToString(req.getParameter("user_id"));
        String wid = StrUtil.nullToString(req.getParameter("wid"));
        String rid = StrUtil.nullToString(req.getParameter("rid"));
        SfUserDTO user = (SfUserDTO) getUserAccount(req);
        ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
        FilterConfigDTO filterConfigDTO = SessionUtil.getFilterConfigDTO(req);
        SSOUserLoginDAO ssoUserLoginDAO = new SSOUserLoginDAO(servletConfig);
//        if (userId.equals("")) {
//            userId = "caobo";
//        }
        user = ssoUserLoginDAO.validateUser(userId);
//        String uid = ssoUserLoginDAO.validateSSOUser(req);
        Connection conn = null;

        try {
            conn = DBManager.getDBConnection();

            if (StrUtil.isEmpty(wid) && StrUtil.isEmpty(rid)) {
                if (DOC_TYPE != null) {
                    out.println(DOC_TYPE);
                }
                out.println("<?xml version=\"1.0\" encoding=\"GBK\"?>");
                out.println("<apps id=\"54\" name=\"资产管理\">");
                out.println("<open>");
                String inboxInfo = "";
                String outboxInfo = "";
                SSODAO dao = new SSODAO(req, conn);
                RowSet rs = dao.getMyWork(user.getUserId(), "");
                inboxInfo = getInboxInfo(rs, user);
                RowSet outRs = dao.getMyWorked(user.getUserId());
                outboxInfo = getOutboxInfo(outRs, userId, user);


                out.println(inboxInfo);
                out.println("</open>");
                out.println("<close>");
                out.println(outboxInfo);
                out.println("</close>");
                out.println("<cancle>");
                out.println("</cancle>");
                out.println("</apps>");
                out.close();
            } else {
                String[] wids = StrUtil.splitStr(wid, ",");
                String status = getStatus(conn, wids, user);
                out.println(status);
                out.close();
            }
        } catch (PoolException e) {
            e.printLog();
        } catch (QueryException e) {
            e.printLog();
        }finally {
            DBManager.closeDBConnection(conn);
        }
    }

    private String getInboxInfo(RowSet rs, SfUserDTO userDTO) {

        String inboxInfo = "";
        String userId = "";
        try {
            if (rs != null) {
                Row row = null;
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    StringBuffer stringBuffer = new StringBuffer("");

                    String preTaskUser = row.getStrValue("FROM_TASK_USER");
                    String preUserId = "";
                    String preTaskId = "";
                    if (preTaskUser.indexOf("$$") > -1) {
                        preUserId = preTaskUser.substring(0, preTaskUser.indexOf("$$"));
                        preTaskId = preTaskUser.substring(preTaskUser.indexOf("$$") + 2, preTaskUser.length());
                    }
                    String url = "/servlet/com.sino.sso.servlet.MyOAWorkServlet";
                    url += "?user_id=" + row.getStrValue("OA_NAME").toLowerCase() + "&actId=" + row.getStrValue("ACTID");
//                    url += row.getStrValue("FORWARD_PATH") + row.getStrValue("APP_ID") + "&actId=" + row.getStrValue("ACTID")
//                            + "&prevUserId=" + preUserId + "&prevTaskId=" + preTaskId + "&procId=" + row.getStrValue("PROC_ID")
//                            + "&currTaskId=" + row.getStrValue("CUR_TASK_ID") + "&taskProp=" + row.getStrValue("TASK_PROP")
//                            + "&sectionRight=" + row.getStrValue("SECTION_RIGHT") + "&hiddenRight=" + row.getStrValue("HIDDEN_RIGHT")
////                            + "&currTaskName=" + row.getStrValue("TASK_NAME") + "&signFlag=" + row.getStrValue("SIGN_FLAG")
//                            + "&currTaskName=填写单据&signFlag=" + row.getStrValue("SIGN_FLAG")
//                            + "&attribute1=" + row.getStrValue("ATTRIBUTE1") + "&attribute2=" + row.getStrValue("ATTRIBUTE2")
//                            + "&attribute3=" + row.getStrValue("ATTRIBUTE3") + "&attribute4=" + row.getStrValue("ATTRIBUTE4")
//                            + "&attribute5=" + row.getStrValue("ATTRIBUTE5");
                    stringBuffer.append("<work>");
                    stringBuffer.append("<doc-id>").append(PDAUtil.xmlFormat(row.getStrValue("ACTID"))).append("</doc-id>");
                    stringBuffer.append("<work-id>").append(PDAUtil.xmlFormat(row.getStrValue("ACTID"))).append("</work-id>");
                    stringBuffer.append("<user-id>").append(row.getStrValue("OA_NAME").toLowerCase()).append("</user-id>");
                    stringBuffer.append("<title>").append(PDAUtil.xmlFormat(row.getStrValue("PROC_NAME"))).append("[").append(row.getStrValue("APPLY_NUMBER")).append("]").append("</title>");
                    stringBuffer.append("<start-time>").append(PDAUtil.xmlFormat(row.getStrValue("CREATION_DATE"))).append("</start-time>");
                    stringBuffer.append("<url>").append(PDAUtil.xmlFormat(url)).append("</url>");
                    stringBuffer.append("<pri>").append(1).append("</pri>");
                    stringBuffer.append("<type>").append(PDAUtil.xmlFormat(row.getStrValue("TYPE"))).append("</type>");
                    stringBuffer.append("<doc-type>").append("资产管理").append("</doc-type>");
                    stringBuffer.append("<sender>").append(StrUtil.isEmpty(row.getStrValue("FROM_USER")) ? userDTO.getUsername() : row.getStrValue("FROM_USER")).append("</sender>");
                    stringBuffer.append("<current-activity>").append("").append("</current-activity>");
                    stringBuffer.append("<origin-org>").append("").append("</origin-org>");
                    stringBuffer.append("</work>");
                    inboxInfo += stringBuffer.toString();
                }
            }
        } catch (PoolException e) {
            e.printLog();
        } catch (ContainerException e) {
            e.printLog();
        }
        return inboxInfo;
    }

    private String getOutboxInfo(RowSet rs, String userId, SfUserDTO userDTO) {

        String inboxInfo = "";
        try {
            if (rs != null) {
                Row row = null;
                for (int i = 0; i < rs.getSize(); i++) {
                    row = rs.getRow(i);
                    StringBuffer stringBuffer = new StringBuffer("");

                    String preTaskUser = "";
                    String preUserId = "";
                    String preTaskId = "";
                    if (preTaskUser.indexOf("$$") > -1) {
                        preUserId = preTaskUser.substring(0, preTaskUser.indexOf("$$"));
                        preTaskId = preTaskUser.substring(preTaskUser.indexOf("$$") + 2, preTaskUser.length());
                    }
                    String url = "/servlet/com.sino.sso.servlet.MyOAWorkServlet";
                    url += "?user_id=" + userId + "&actId=" + row.getStrValue("ACTID");
//                    url += row.getStrValue("FORWARD_PATH") + row.getStrValue("APP_ID") + "&actId=" + row.getStrValue("ACTID")
//                            + "&prevUserId=" + preUserId + "&prevTaskId=" + preTaskId + "&procId=" + row.getStrValue("PROC_ID")
//                            + "&currTaskId=" + row.getStrValue("CUR_TASK_ID") + "&taskProp=" + row.getStrValue("TASK_PROP")
//                            + "&sectionRight=" + row.getStrValue("SECTION_RIGHT") + "&hiddenRight=" + row.getStrValue("HIDDEN_RIGHT")
////                            + "&currTaskName=" + row.getStrValue("TASK_NAME") + "&signFlag=" + row.getStrValue("SIGN_FLAG")
//                            + "&currTaskName=填写单据&signFlag=" + row.getStrValue("SIGN_FLAG")
//                            + "&attribute1=" + row.getStrValue("ATTRIBUTE1") + "&attribute2=" + row.getStrValue("ATTRIBUTE2")
//                            + "&attribute3=" + row.getStrValue("ATTRIBUTE3") + "&attribute4=" + row.getStrValue("ATTRIBUTE4")
//                            + "&attribute5=" + row.getStrValue("ATTRIBUTE5");
                    stringBuffer.append("<work>");
                    stringBuffer.append("<doc-id>").append(PDAUtil.xmlFormat(row.getStrValue("ACTID"))).append("</doc-id>");
                    stringBuffer.append("<work-id>").append(PDAUtil.xmlFormat(row.getStrValue("ACTID"))).append("</work-id>");
                    stringBuffer.append("<user-id>").append(userId).append("</user-id>");
                    stringBuffer.append("<title>").append(PDAUtil.xmlFormat(row.getStrValue("PROC_NAME"))).append("</title>");
                    stringBuffer.append("<start-time>").append(PDAUtil.xmlFormat(row.getStrValue("CREATION_DATE"))).append("</start-time>");
                    stringBuffer.append("<url>").append(PDAUtil.xmlFormat(url)).append("</url>");
                    stringBuffer.append("<pri>").append(1).append("</pri>");
                    stringBuffer.append("<type>").append(1).append("</type>");
                    stringBuffer.append("<doc-type>").append("资产管理").append("</doc-type>");
                    stringBuffer.append("<sender>").append(StrUtil.isEmpty(row.getStrValue("CUR_USER")) ? userDTO.getUsername() : row.getStrValue("CUR_USER")).append("</sender>");
                    stringBuffer.append("<current-activity>").append("").append("</current-activity>");
                    stringBuffer.append("<origin-org>").append("").append("</origin-org>");
                    stringBuffer.append("</work>");
                    inboxInfo += stringBuffer.toString();
                }
            }
        } catch (PoolException e) {
            e.printLog();
        } catch (ContainerException e) {
            e.printLog();
        }
        return inboxInfo;
    }

    public String getStatus(Connection conn, String[] idArr, SfUserDTO user) throws QueryException {
        String status = "";
        SimpleQuery sq;
        if (idArr != null) {
            for (int i = 0; i < idArr.length; i++) {
                String id = idArr[i];
                sq = new SimpleQuery(getMyWorkModel(user.getUserId(), id), conn);
                sq.setCalPattern(CalendarConstant.LINE_PATTERN);
                sq.executeQuery();
                if (sq.hasResult()) {
                    status += "1";
                } else {
                    status += "0";
                }
            }
        }

        return status;
    }

    public SQLModel getMyWorkModel(int currUserId, String actId) {
        SQLModel sm = new SQLModel();
        ArrayList al = new ArrayList();
        String sql = "SELECT SP.USERNAME,\n" +
                "       SP.USER_ID,\n" +
                "       SP2.USERNAME FROM_USER,\n" +
                "       SA.FROM_DT FROM_TIME,\n" +
                "       SA.ACTID,\n" +
                "       SA.CUR_TASK_ID,\n" +
                "       SA.PROC_ID,\n" +
                "       SA.APP_ID,\n" +
                "       SA.APPLY_NUMBER,\n" +
                "       SA.CREATION_DATE,\n" +
                "       SF_FLOW_PKG.GET_FROM_TASK_USER(SA.CUR_TASK_ID, SA.ACTID) FROM_TASK_USER,\n" +
                "       SF_FLOW_PKG.GET_CUR_USERS_TOTAL(SA.ACTID) TOTAL_USERS,\n" +
                "       STD.TASK_PROP,\n" +
                "       NVL(STD.FORWARD_PATH,\n" +
                " CASE WHEN STD.TASK_PROP='1' THEN SPD.EDIT_PATH ELSE SPD.APPROVE_PATH END FORWARD_PATH,"+
                "       STD.SECTION_RIGHT,\n" +
                "       STD.HIDDEN_RIGHT,\n" +
                "       STD.ATTRIBUTE1,\n" +
                "       STD.ATTRIBUTE2,\n" +
                "       STD.ATTRIBUTE3,\n" +
                "       STD.ATTRIBUTE4,\n" +
                "       STD.ATTRIBUTE5,\n" +
                "       STD.SECTION_RIGHT,\n" +
                "       STD.HIDDEN_RIGHT,\n" +
                "       STD.TASK_NAME,\n" +
                "       SPD.PROC_NAME,\n" +
                "       SAI.SIGN_FLAG,\n" +
                " CASE WHEN SAI.SIGN_FLAG='Y' THEN 0 ELSE 4 END TYPE "+
                "  FROM SF_USER          SP,\n" +
                "       SF_USER          SP2,\n" +
                "       SF_ACT           SA,\n" +
                "       SF_ACT_INFO      SAI,\n" +
                "       SF_TASK_DEFINE   STD,\n" +
                "       SF_PROCEDURE_DEF SPD\n" +
                " WHERE SA.FROM_USER_ID = SP2.USER_ID(+)\n" +
                "   AND SA.CREATED_BY = SP.USER_ID\n" +
                "   AND SA.ACTID = SAI.ACT_ID\n" +
                "   AND SA.CUR_TASK_ID = STD.TASK_ID\n" +
                "   AND SA.PROC_ID = SPD.PROC_ID\n" +
                "   AND ((SAI.USER_ID = ? AND SAI.AGENT_USER_ID IS NULL) OR\n" +
                "       (SAI.AGENT_USER_ID = ?))\n" +
                "   AND (SA.ACTID = ?)\n" +
                " ORDER BY SA.CREATION_DATE DESC";
        al.add(currUserId);
        al.add(currUserId);
        al.add(actId);

        sm.setArgs(al);
        sm.setSqlStr(sql);
        return sm;
    }


}