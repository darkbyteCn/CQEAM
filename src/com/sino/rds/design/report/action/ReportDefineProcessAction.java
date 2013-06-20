package com.sino.rds.design.report.action;

import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.rds.appbase.action.RDSBaseAction;
import com.sino.rds.design.report.service.ReportCopyService;
import com.sino.rds.design.report.service.ReportDefineProcessService;
import com.sino.rds.share.constant.RDSActionConstant;
import com.sino.rds.share.form.ReportDefineFrm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class ReportDefineProcessAction extends RDSBaseAction {

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

        ReportDefineFrm frm = (ReportDefineFrm) form;
        String act = frm.getAct();
        BaseUserDTO userAccount = getUserAccount(req);
        Connection conn = null;
        Message message = getPrevMessage(req);
        try {

            conn = getDBConnection();
            ReportDefineProcessService service = new ReportDefineProcessService(userAccount, frm, conn);
            if (act.equals("")) {
                service.produceWebComponent();
                req.setAttribute(mapping.getName(), frm);
                forwardName = "listPage";
            } else if (act.equals(RDSActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = service.getSQLProducer();
                PageQueryDAO searchDAO = new PageQueryDAO(req, conn, sqlProducer);
                searchDAO.setDTOClassName(ReportDefineFrm.class.getName());
                searchDAO.setCalPattern(LINE_PATTERN);
                searchDAO.setPageSize(22);
                searchDAO.produceWebData();
                service.produceWebComponent();
                req.setAttribute(mapping.getName(), frm);
                forwardName = "listPage";
            } else if (act.equals(RDSActionConstant.DETAIL_ACTION)) {
                frm = service.searchDataByPrimaryKey();
                req.setAttribute(mapping.getName(), frm);
                forwardName = "detailPage";
            } else if (act.equals(RDSActionConstant.SAVE_ACTION)) {
                service.saveReport();
                message = service.getMessage();
                forwardName = "detailAct";
                forward = new ActionForward();
                String path = mapping.findForward(forwardName).getPath();
                path += "&reportId=" + frm.getReportId();
                forward.setPath(path);
            } else if (act.equals(RDSActionConstant.DISABLED_ACTION)) {
                service.disableReport();
                forwardName = "searchAct";
            } else if (act.equals(RDSActionConstant.ENABLE_ACTION)) {
                service.enableReport();
                forwardName = "searchAct";
            } else if (act.equals(RDSActionConstant.COPY_ACTION)) {
                ReportCopyService copyProcessor = new ReportCopyService(userAccount, frm, conn);
                copyProcessor.copyReport();
                message = copyProcessor.getMessage();
                forwardName = "searchAct";
            } else {
                forwardName = "inValidReq";
                message = getUndefinedRequestMessage();
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
            message = getMessage(ex);
            message.setNeedBack(true);
            forwardName = "messageProcess";
        } finally {
            closeDBConnection(conn);
            if (forward == null && !StrUtil.isEmpty(forwardName)) {
                forward = mapping.findForward(forwardName);
            }
            setHandleMessage(req, message);
        }
        return forward;
    }
}
