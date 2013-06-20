package com.sino.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sino.base.PubServlet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.UploadException;
import com.sino.base.log.Logger;
import com.sino.flow.dao.ApproveUserFindDAO;
import com.sino.flow.dto.FlowParmDTO;

/**
 * <p>Title: SinoCPS</p>
 * <p>Description: 河南移动集中核算系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 */
public class ApproveUserFindServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Connection conn = null;
        ApproveUserFindDAO userFindDAO = null;
        JSONObject jsonObject;//从客户端读过来的对象
        JSONArray retArray;//返回给客户端的对象
        res.setContentType("text/xml;charset=GBK");
        req.setCharacterEncoding("GBK");
        PrintWriter pw = res.getWriter();
        try {
            conn = DBManager.getDBConnection();
            ClientReader cr = new ClientReader();
            jsonObject = new JSONObject(cr.readToJSON(req));
            FlowParmDTO dto = new FlowParmDTO();
            dto.setNextDeptId(jsonObject.getString("nextDeptId"));
            dto.setNextTaskId(jsonObject.getString("nextTaskId"));
            dto.setProcId(jsonObject.getString("procId"));
            dto.setAppId(jsonObject.getString("appId"));
            dto.setExtendType(jsonObject.getString("extendType"));
            dto.setAttr1(jsonObject.getString("attr1"));
            dto.setAttr2(jsonObject.getString("attr2"));
            dto.setAttr3(jsonObject.getString("attr3"));
            dto.setOu(jsonObject.getString("ou"));
            userFindDAO = new ApproveUserFindDAO(conn, req, dto);
            retArray = userFindDAO.getPositionUsers();
            pw.write(retArray.toString());
        } catch (PoolException ex) {
            Logger.logError(ex);
            pw.write("ERROR");
        } catch (QueryException ex) {
            Logger.logError(ex);
            pw.write("ERROR");
        } catch (UploadException ex) {
            Logger.logError(ex);
            pw.write("ERROR");
        } catch (ContainerException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } catch (JSONException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } finally {
            pw.flush();
            pw.close();
            DBManager.closeDBConnection(conn);
        }
    }
}
