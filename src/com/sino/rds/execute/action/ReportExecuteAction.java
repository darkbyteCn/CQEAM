package com.sino.rds.execute.action;

import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.action.RDSBaseAction;
import com.sino.rds.execute.service.ReportExecuteService;
import com.sino.rds.share.constant.RDSActionConstant;
import com.sino.rds.share.form.ExecuteResultFrm;
import com.sino.rds.share.form.SearchParameterFrm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;

public class ReportExecuteAction extends RDSBaseAction {

    /**
     * 所有Action必须实现的方法
     *
     * @param mapping ActionMapping
     * @param form    ActionForm
     * @param req     HttpServletRequest
     * @param res     HttpServletResponse
     * @return ActionForward
     * @throws javax.servlet.ServletException
     */
    public ActionForward performTask(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ServletException {
        ActionForward forward = null;
        String forwardName = "";
        SearchParameterFrm frm = (SearchParameterFrm) form;
        String act = frm.getAct();
        BaseUserDTO userAccount = getUserAccount(req);
        Connection conn = null;
        Message message = getPrevMessage(req);
        try {
            conn = getDBConnection();
            parseRequest2Frm(req, frm);
            ReportExecuteService service = new ReportExecuteService(userAccount, frm, conn);
            if (act.equals("") || act.equals(RDSActionConstant.QUERY_ACTION)) { 
                service.setRequest(req);
                ExecuteResultFrm executeResult = service.getReportExecuteResult();
                message = service.getMessage();
    			req.setAttribute("REPORT_RESULT", executeResult);
    			forwardName = "reportPage";
            } else if (act.equals(RDSActionConstant.EXPORT_ACTION)) {
                File file = service.getExportReport();
                downloadFile(req, res, file);
                file.delete();
            } else {
                forwardName = "inValidReq";
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            message = getMessage(ex);
            message.setNeedBack(true);
            forwardName = "messageProcess";
        } finally {
            closeDBConnection(conn);
            forward = mapping.findForward(forwardName);
            setHandleMessage(req, message);
        }
        return forward;
    }

    private void parseRequest2Frm(HttpServletRequest req, SearchParameterFrm defaultFrm) {
        Map paraMap = req.getParameterMap();
        Iterator paraNames = paraMap.keySet().iterator();
        while (paraNames.hasNext()) {
            String paraName = (String) paraNames.next();
            String paraValue = req.getParameter(paraName);
            defaultFrm.addParameter(paraName, paraValue);
        }
    }
}
