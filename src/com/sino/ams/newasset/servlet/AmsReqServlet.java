package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sino.ams.newasset.dao.AmsReqDAO;
import com.sino.ams.newasset.dao.UploadFileDAO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.log.Logger;
import com.sino.flow.servlet.ClientReader;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-13
 * Time: 10:34:18
 * To change this template use File | Settings | File Templates.
 */
public class AmsReqServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        Connection conn = null;
        JSONObject jsonObject; //从客户端读过来的对象
        JSONArray retArray; //返回给客户端的对象
        res.setContentType("text/xml;charset=GBK");
        req.setCharacterEncoding("GBK");
        SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
        int orgId = user.getOrganizationId();
        PrintWriter pw = res.getWriter();
        try {
            conn = DBManager.getDBConnection();
            ClientReader cr = new ClientReader();
            String json = cr.readToJSON(req);
            AmsReqDAO dao = new AmsReqDAO(conn);
            String reqType = req.getParameter("reqType");
            if (reqType.equals("GET_HEADER_ID")) {
                String headerId = dao.getHeaderId();
                pw.write(headerId);
            } else if (reqType.equals("UPDATE_FILE_DESC")) {
                UploadFileDAO fdao = new UploadFileDAO(conn, req, null, null);
                String fileId = req.getParameter("fileId");
                String desc = req.getParameter("description");
                fdao.updateDesc(fileId, desc);
            } else if (reqType.equals("GET_ITEM_CODE")) {
                String iName = req.getParameter("iName");
//                String orgId=user.getOrganizationId();
                String iSpec = req.getParameter("iSpec");
                String iCode = dao.getItemCode(iName, iSpec);
                pw.write(iCode);
            } else if (reqType.equals("GET_ADRESS")) {
                String adressName = req.getParameter("adressName");
                String adressId = dao.getAddreId(adressName, orgId);
                if (adressId.equals("")) {
                	pw.write(adressId);
                } else {
                	pw.write("" +adressId);
                }
            } else if (reqType.equals("GET_DEPT")) {
                String deptName = req.getParameter("deptName");
                String deptId = dao.getDepId(deptName, orgId);
                if (deptId.equals("")) {
                	pw.write(deptId);
                } else {
                	pw.write("" + deptId);
                }
            } else if (reqType.equals("GET_USE")) {
                String userName = req.getParameter("userName");
                String userId = dao.getUseId(userName, orgId);
                if (userId .equals("")) {
                	pw.write(userId);
                } else {
                	pw.write("" +userId);
                }
            } else if (reqType.equals("GET_CARD_OPER_INFO")) {
                int userId = user.getUserId();
                String materialFrom = req.getParameter("materialFrom");
                String orderListOption = dao.getCardDefineListOption(materialFrom,userId);
                pw.write(orderListOption);
            }
        } catch (PoolException e) {
            Logger.logError(e);
            pw.write("ERROR$##$" + "获取数据库连接失败！");
        } catch (SQLException e) {
            Logger.logError(e);
            pw.write("ERROR$##$" + "执行数据库操作失败！");
        } catch (Exception e) {
            Logger.logError(e);
            pw.write("ERROR$##$" + e.getMessage());
        } finally {
            pw.flush();
            pw.close();
            closeDBConnection(conn);
        }
    }
}
