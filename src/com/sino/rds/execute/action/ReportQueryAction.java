package com.sino.rds.execute.action;

import com.sino.base.exception.HandlerException;
import com.sino.base.exception.StrException;
import com.sino.base.log.Logger;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.EventHandler;
import com.sino.base.web.EventHandlers;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.rds.appbase.action.RDSBaseAction;
import com.sino.rds.execute.service.ReportQueryService;
import com.sino.rds.share.constant.RDSActionConstant;
import com.sino.rds.share.form.FavoriteHeaderFrm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class ReportQueryAction extends RDSBaseAction {

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
        FavoriteHeaderFrm frm = (FavoriteHeaderFrm) form;
        String act = frm.getAct();
        BaseUserDTO userAccount = getUserAccount(req);
        Connection conn = null;
        try {
            conn = getDBConnection();
            ReportQueryService service = new ReportQueryService(userAccount, frm, conn);
            if (act.equals("")) {
                service.produceWebComponent();
                req.setAttribute(mapping.getName(), frm);
                forwardName = "listPage";
            } else if (act.equals(RDSActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = service.getSQLProducer();
                PageQueryDAO searchDAO = new PageQueryDAO(req, conn, sqlProducer);
                searchDAO.setCalPattern(LINE_PATTERN);
                searchDAO.setPageSize(22);

                CheckBoxProp checkProp = prodCheckBoxProp();
                searchDAO.setWebCheckProp(checkProp);

                searchDAO.produceWebData();

                service.produceWebComponent();
                req.setAttribute(mapping.getName(), frm);
                forwardName = "listPage";
            } else {
                forwardName = "inValidReq";
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            closeDBConnection(conn);
            forward = mapping.findForward(forwardName);
        }
        return forward;
    }

    private CheckBoxProp prodCheckBoxProp() throws StrException, HandlerException {
        CheckBoxProp checkProp = new CheckBoxProp("subCheck");

        checkProp.addDbField("REPORT_ID");

        checkProp.addTransField("REPORT_ID");
        checkProp.addTransField("REPORT_NAME");
        checkProp.addTransField("REPORT_CODE");

        EventHandlers handlers = new EventHandlers();
        EventHandler eventHandler = new EventHandler();
        eventHandler.setEventName("onPropertyChange");
        eventHandler.setFunName("do_SetCheckedReport");

        handlers.addHandler(eventHandler);
        checkProp.setHandlers(handlers);
        return checkProp;
    }
}
