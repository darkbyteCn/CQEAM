package com.sino.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.PubServlet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.flow.constant.FlowURLDefineList;
import com.sino.flow.dao.UserAgencyDAO;
import com.sino.flow.dto.UserAgencyDTO;

/**
 * 流程代理人设置
 */
public class UserAgencyServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //To change body of implemented methods use File | Settings | File Templates.
        Connection conn = null;
        String forward = "";
        String message = "";
        String nextPage = FlowURLDefineList.ERROR_PAGE;
        RequestParser parser = new RequestParser();
        try {
            parser.transData(req);
            conn = DBManager.getDBConnection();
            UserAgencyDAO userAgencyDAO = new UserAgencyDAO(conn, req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(UserAgencyDTO.class.getName());
            UserAgencyDTO userAgencyDTO = (UserAgencyDTO) req2DTO.getDTO(req);
            userAgencyDAO.setParameter(userAgencyDTO);
            forward = StrUtil.nullToString(req.getParameter("forward"));
            if (forward.equals("")) {
                forward = "search";
            }
            if (forward.equals("search")) {
                userAgencyDAO.prodUserAgency();
                nextPage = FlowURLDefineList.USER_AGENCY_MANAGE_PAGE;
            } else if (forward.equals("addNew")) {
                nextPage = FlowURLDefineList.USER_AGENCY_DETAIL_PAGE;
            } else if (forward.equals("detail")) {
                userAgencyDAO.prodUserAgencyDetail();
                nextPage = FlowURLDefineList.USER_AGENCY_DETAIL_PAGE;
            } else if (forward.equals("Insert_New")) {
                userAgencyDAO.prodInsertNewUserAgency();
                message = "代办人信息已保存！";
                nextPage = FlowURLDefineList.USER_AGENCY_SERVLET;
            } else if (forward.equals("save")) {
                userAgencyDAO.prodUpdateUserAgency();
                message = "代办人信息已修改！";
                nextPage = FlowURLDefineList.USER_AGENCY_SERVLET;
            } else if (forward.equals("disable")) {
                userAgencyDAO.prodDisableUserAgency();
                message = "代办人信息已失效！";
                nextPage = FlowURLDefineList.USER_AGENCY_SERVLET;
            } else if (forward.equals("back")) {
                nextPage = FlowURLDefineList.USER_AGENCY_SERVLET;
            }
        } catch (UploadException e) {
            Logger.logError(e);
        } catch (PoolException e) {
            Logger.logError(e);
        } catch (DTOException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            Logger.logError(e);
        } catch (SQLException e) {
            Logger.logError(e);
        } catch (DataHandleException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
            ServletForwarder sforwarder = new ServletForwarder(req, res);
            if (!message.equals("")) {
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                out.print("<script language=\"javascript\">");
                out.println("alert(\"" + message + "\");");
                out.println("window.returnValue = \"Y\";");
                out.println("window.close();");
                out.print("</script>");
            } else {
                sforwarder.forwardView(nextPage);
            }
        }
    }
}
