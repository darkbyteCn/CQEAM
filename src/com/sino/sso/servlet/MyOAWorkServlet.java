package com.sino.sso.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.PubServlet;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.flow.constant.FlowURLDefineList;
import com.sino.flow.constant.ReqAttributeList;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.sso.dao.SSODAO;
import com.sino.sso.dao.SSOUserLoginDAO;

/**
 * OA收件箱SERVLET
 * User: zhoujs
 * Date: 2006-12-19
 * Time: 20:57:12
 */
public class MyOAWorkServlet extends PubServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nextPage = FlowURLDefineList.INBOX_PAGE;
        nextPage = "/flow/oaBox.jsp";
        Connection conn = null;
        SfUserDTO user;
        String forwordURL = "";
        try {
            conn = DBManager.getDBConnection();
            String userId = StrUtil.nullToString(req.getParameter("user_id"));
            String actId = StrUtil.nullToString(req.getParameter("actId"));
            ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
            FilterConfigDTO filterConfigDTO = SessionUtil.getFilterConfigDTO(req);
            SSOUserLoginDAO ssoUserLoginDAO = new SSOUserLoginDAO(servletConfig);
            user = ssoUserLoginDAO.validateUser(userId);
            SessionUtil.saveUserSession(req, user);

            SSODAO dao = new SSODAO(req, conn);
            RowSet rs = dao.getMyWork(user.getUserId(), actId);
            if (rs != null) {
                Row row = rs.getRow(0);
                String preTaskUser = row.getStrValue("FROM_TASK_USER");
                String preUserId = "";
                String preTaskId = "";
                if (preTaskUser.indexOf("$$") > -1) {
                    preUserId = preTaskUser.substring(0, preTaskUser.indexOf("$$"));
                    preTaskId = preTaskUser.substring(preTaskUser.indexOf("$$") + 2, preTaskUser.length());
                }
                forwordURL += row.getStrValue("FORWARD_PATH") + row.getStrValue("APP_ID") + "&actId=" + row.getStrValue("ACTID")
                        + "&prevUserId=" + preUserId + "&prevTaskId=" + preTaskId + "&procId=" + row.getStrValue("PROC_ID")
                        + "&currTaskId=" + row.getStrValue("CUR_TASK_ID") + "&taskProp=" + row.getStrValue("TASK_PROP")
                        + "&sectionRight=" + row.getStrValue("SECTION_RIGHT") + "&hiddenRight=" + row.getStrValue("HIDDEN_RIGHT")
                        + "&currTaskName=" + row.getStrValue("TASK_NAME") + "&signFlag=" + row.getStrValue("SIGN_FLAG")
                        + "&attribute1=" + row.getStrValue("ATTRIBUTE1") + "&attribute2=" + row.getStrValue("ATTRIBUTE2")
                        + "&attribute3=" + row.getStrValue("ATTRIBUTE3") + "&attribute4=" + row.getStrValue("ATTRIBUTE4")
                        + "&attribute5=" + row.getStrValue("ATTRIBUTE5");
            }
        } catch (PoolException e) {
            req.setAttribute(ReqAttributeList.ERROR_MSG, "取数据库连接失败！");
            Logger.logError(e);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } catch (QueryException e) {
            req.setAttribute(ReqAttributeList.ERROR_MSG, "查询数据库失败！");
            Logger.logError(e);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } catch (ContainerException e) {
            req.setAttribute(ReqAttributeList.ERROR_MSG, "查询数据库失败！");
            Logger.logError(e);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } finally {
            DBManager.closeDBConnection(conn);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwordURL);
        }
    }
}