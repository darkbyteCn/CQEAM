package com.sino.ams.system.resource.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.resource.dao.ResourceAdjustDAO;
import com.sino.ams.system.resource.dto.SfResDefineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * User: zhoujs
 * Date: 2009-7-1 16:06:56
 * Function:À¸Ä¿µ÷Õû
 */

public class ResourceAdjustServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = StrUtil.nullToString(req.getParameter("act"));
        Connection conn = null;
        SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
        JSONArray retArray = new JSONArray();
        try {
            conn = getDBConnection(req);
            SfResDefineDTO dto = new SfResDefineDTO();
            ResourceAdjustDAO adjustDAO = new ResourceAdjustDAO(userAccount, dto, conn);
            if (action.equals("")) {
                String resourceOpt = adjustDAO.getResourceOption(dto);
                req.setAttribute(WebAttrConstant.RESOURCE_OPTION, resourceOpt);
                forwardURL = "/system/resource/resourceSort.jsp";
            } else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
                String[] resIds = req.getParameterValues("resIds");
                if(resIds!=null&&resIds.length>0){
                    adjustDAO.updateChildrenOrder(resIds);
                }
                 String resourceOpt = adjustDAO.getResourceOption(dto);
                req.setAttribute(WebAttrConstant.RESOURCE_OPTION, resourceOpt);
                 forwardURL = "/system/resource/resourceSort.jsp";
            } else if (action.equals("small")) {
                res.setContentType("text/xml;charset=GBK");
                PrintWriter pw = res.getWriter();
                String resParId = req.getParameter("resParId");
                ResourceAdjustDAO dao = new ResourceAdjustDAO(userAccount, dto, conn);
                RowSet rows = dao.getChildren(resParId);
                Row row = null;                                                   
                if (rows != null && rows.getSize() > 0) {
                    for (int i = 0; i < rows.getSize(); i++) {
                        row = rows.getRow(i);
                        retArray.put(i, row.getValue("RES_ID").toString() + "$" + row.getValue("RES_NAME"));
                    }
                }
                pw.print(retArray.toString());
                pw.flush();
                pw.close();
            }

        } catch (PoolPassivateException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;

        } catch (ContainerException e) {
            e.printLog();
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            retArray.put("ERROR");
        } catch (QueryException e) {
            e.printLog();
        } catch (DataHandleException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
