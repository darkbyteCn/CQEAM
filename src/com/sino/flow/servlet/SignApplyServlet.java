/**
 * Created by wwb.
 * User: demo
 * Date: 2007-4-11
 * Time: 11:32:22
 */
package com.sino.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.PubServlet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.flow.dao.SignApplyDAO;
import com.sino.framework.security.bean.SessionUtil;

public class SignApplyServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nextPage = "";
        Connection conn = null;
        RequestParser rp = new RequestParser();
        res.setContentType("text/xml;charset=GBK");
        PrintWriter pw = res.getWriter();
        try {
            conn = DBManager.getDBConnection();
            rp.transData(req);
            String actId = rp.getParameter("actId");
            SignApplyDAO dao = new SignApplyDAO();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            String userId = StrUtil.nullToString(userAccount.getUserId());
            String flag = dao.sign(actId, userId, conn);
            //falg:0表示有人已经签收，1表示签收成功
            pw.write(flag);
        } catch (PoolException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } catch (UploadException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } catch (SQLException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } catch (DataHandleException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } catch (QueryException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } catch (ContainerException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } finally {
            pw.flush();
            pw.close();
            DBManager.closeDBConnection(conn);
        }
    }


}
