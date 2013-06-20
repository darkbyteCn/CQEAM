package com.sino.ams.adjunct.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.sino.ams.adjunct.business.FileMaintenanceDAO;
import com.sino.ams.adjunct.dto.FileDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.PubServlet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ConfigException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-29
 * Time: 10:03:27
 * To change this template use File | Settings | File Templates.
 */
public class FileMaintenanceServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Connection conn = null;
        String nextPage = "";
        String msg = "";
        String forward = "";
        boolean operateResult = false;
        try {
            conn = DBManager.getDBConnection();
            forward = StrUtil.nullToString(req.getParameter("forward"));
            String[] orderPkValues = req.getParameterValues("subBox");
            if (forward.equals("")) {
                forward = "QUERY_ACTION";
            }
            //UserAccountDTO userDTO = SessionUtil.getUserAccount(req);
            SfUserDTO userDTO = (SfUserDTO)SessionUtil.getUserAccount(req);
            Request2DTO req2 = new Request2DTO();
            req2.setDTOClassName(FileDTO.class.getName());
            FileDTO dtoParameter = (FileDTO) req2.getDTO(req);
            FileMaintenanceDAO fileMaintenanceDAO = new FileMaintenanceDAO(conn, req, dtoParameter);
            String orderPkName = dtoParameter.getOrderPkName();
            String orderPkValue = dtoParameter.getOrderPkValue();
            if (forward.equals("QUERY_ACTION")) {
                fileMaintenanceDAO.getAttaches();
                nextPage = "/system/adjunct/adjunctList.jsp";
            } else if (forward.equals("DELETE_ACTION")) {
                fileMaintenanceDAO.deleteAttaches(orderPkValues);
                nextPage = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet?forward=EDIT_ACTION&orderPkName=" + orderPkName;
            } else if (forward.equals("UPLOAD_ACTION")) {
                fileMaintenanceDAO.uploadAttaches(userDTO.getUserId());
                nextPage = "/servlet/com.sino.ams.adjunct.servlet.FileMaintenanceServlet?forward=EDIT_ACTION&orderPkName=" + orderPkName;
            } else if (forward.equals("DOWNLOAD_ACTION")) {
                fileMaintenanceDAO.downloadAttach(orderPkValue, res);
            } else if (forward.equals("EDIT_ACTION")) {
                fileMaintenanceDAO.getAttaches();
                nextPage = "/system/adjunct/adjunctMaintenance.jsp";
            } else if (forward.equals("GET_ATTACH_COUNT")) {
                int attachCount = fileMaintenanceDAO.getAttacheCount();
                PrintWriter out = res.getWriter();
                out.print(attachCount);
                out.close();
            }
            operateResult = true;
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            try {
                if (operateResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
            } catch (SQLException e) {
                Logger.logError(e);
            }
            DBManager.closeDBConnection(conn);
            if(nextPage.length() > 0){
                ServletForwarder dis = new ServletForwarder(req, res);
                dis.forwardView(nextPage);
            }
        }
    }
}
