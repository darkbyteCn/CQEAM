package com.sino.sso.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.PubServlet;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.flow.constant.FlowURLDefineList;
import com.sino.flow.constant.ReqAttributeList;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.sso.constant.SSOWebAttributes;
import com.sino.sso.dao.OADAO;
import com.sino.sso.dao.OAUtil;

/**
 * OA收件箱SERVLET
 * Created by wwb.
 * User: demo
 * Date: 2006-12-19
 * Time: 20:57:12
 */
public class OABoxServlet extends PubServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nextPage ="/sx/oa/oaBox.jsp";
        Connection conn = null;
        try {
            conn = DBManager.getDBConnection();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            String type = StrUtil.nullToString(req.getParameter("type"));
            if (userAccount.getUserId() > 0) {
                String loginName = StrUtil.nullToString(userAccount.getLoginName());

                OADAO oaDAO = new OADAO();
                if (type.equals("0")) {
                    int count = oaDAO.getInboxCount(loginName, conn);
                    nextPage = "/sx/oa/oaCount.jsp";
                    req.setAttribute(ReqAttributeList.INBOX_COUNT, count);
                } else {
                    RowSet rs = oaDAO.getInbox(loginName, conn);
                    req.setAttribute(ReqAttributeList.INBOX_DATA, rs);

                    DTOSet dtoSet = userAccount.getUserRight();
                    if (dtoSet != null && dtoSet.getSize() > 0) {
                        OAUtil oaUtil = new OAUtil();
                        if (oaUtil.isSyncUser(userAccount.getUserRight())) {
                            req.setAttribute(SSOWebAttributes.SYNC_CHANGES, String.valueOf(oaDAO.hasAssetsUpdate(conn, userAccount)));
                            req.setAttribute(SSOWebAttributes.SYNC_TRANS_IN_COMP, String.valueOf(oaDAO.hasAssetsCommit(conn, userAccount, "BTW_COMP")));
                            req.setAttribute(SSOWebAttributes.SYNC_TRANS_RESULT, String.valueOf(oaDAO.hasAssetsCommit(conn, userAccount, "")));
                        } else {
                            req.setAttribute(SSOWebAttributes.SYNC_CHANGES, "FALSE");
                            req.setAttribute(SSOWebAttributes.SYNC_TRANS_IN_COMP, "FALSE");
                            req.setAttribute(SSOWebAttributes.SYNC_TRANS_RESULT, "FALSE");
                        }
                    }
                }
            } else {
                if (type.equals("0")) {
                    nextPage = "/sx/oa/oaCount.jsp";
                    req.setAttribute(ReqAttributeList.INBOX_COUNT, 0);
                } else {
                    nextPage="/sso/oaNoBox.html";
                }
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
        } catch (SQLModelException e) {
            req.setAttribute(ReqAttributeList.ERROR_MSG, "查询数据库失败！");
            Logger.logError(e);
            nextPage = FlowURLDefineList.ERROR_PAGE;
        } finally {
            DBManager.closeDBConnection(conn);
            req.getRequestDispatcher(nextPage).forward(req, res);
        }
    }
}