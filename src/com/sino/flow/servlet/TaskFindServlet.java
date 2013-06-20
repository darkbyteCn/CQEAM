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
import com.sino.flow.dao.TaskFindDAO;
import com.sino.flow.dto.FlowParmDTO;

/**
 * <p>Title: SinoCPS</p>
 * <p>Description: 河南移动集中核算系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 */
public class TaskFindServlet extends PubServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Connection conn = null;
        TaskFindDAO taskFindDAO = null;
        JSONObject jsonObject;//从客户端读过来的对象
        JSONArray retArray;//返回给客户端的对象
        res.setContentType("text/xml;charset=GBK");
        req.setCharacterEncoding("GBK");
        PrintWriter pw = res.getWriter();
        try {
            //step1:read data from request
            ClientReader cr = new ClientReader();
            String json = cr.readToJSON(req);
            jsonObject = new JSONObject(json);
            //step2:fetch next task information by current parameter
            conn = DBManager.getDBConnection();
            FlowParmDTO dto = new FlowParmDTO();//MODEL用到的参数，构建成一个DTO
            dto.setCurrDeptId(jsonObject.getString("deptId"));
            dto.setProcName(jsonObject.getString("procdureName"));
            dto.setOrgId(jsonObject.getString("orgId"));
            String actId = jsonObject.getString("actId");
            taskFindDAO = new TaskFindDAO(conn, req, dto);
            if (actId != null && !actId.equals("")) {  //已经有流程记录
                taskFindDAO.setCurrAct2DTO(actId);//将当前ACT必要的信息存进去
            } else { //第一次提交审批时 ，没有流程记录
                taskFindDAO.setProcId2DTO();  //通过流程类型和OU找到流程ID
            }
            taskFindDAO = new TaskFindDAO(conn, req, dto);
            //step3:判断下个流向有几条
            int nextTaskCount = taskFindDAO.getNextTaskCount();
            String flowCode = "";
            if (nextTaskCount > 1) {//如果流向大于一个，就由应用提供流程控制码，否则多条流向就会显示全部让用户去选
                flowCode = jsonObject.getString("flowCode");
            }
            retArray = taskFindDAO.getNextTasks(flowCode);
            pw.print(retArray.toString());
        } catch (PoolException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } catch (QueryException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } catch (ContainerException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } catch (JSONException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } catch (UploadException e) {
            Logger.logError(e);
            pw.write("ERROR");
        } finally {
            pw.flush();
            pw.close();
            DBManager.closeDBConnection(conn);
        }
    }
}
