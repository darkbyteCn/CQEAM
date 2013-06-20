package com.sino.rds.execute.action;

import com.sino.base.log.Logger;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.rds.appbase.action.RDSBaseAction;
import com.sino.rds.execute.service.FavoriteReportService;
import com.sino.rds.share.constant.RDSActionConstant;
import com.sino.rds.share.form.FavoriteHeaderFrm;
import com.sino.rds.share.form.FavoriteLineFrm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class FavoriteReportAction extends RDSBaseAction {

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
            FavoriteReportService service = new FavoriteReportService(userAccount, frm, conn);
            if (act.equals("") || act.equals(RDSActionConstant.QUERY_ACTION)) {
                service.produceWebComponent();

                BaseSQLProducer sqlProducer = service.getSQLProducer();
                PageQueryDAO searchDAO = new PageQueryDAO(req, conn, sqlProducer);
                searchDAO.setCalPattern(LINE_PATTERN);
                searchDAO.setDTOClassName(FavoriteLineFrm.class.getName());
                searchDAO.produceWebData();
                req.setAttribute(mapping.getName(), frm);
                forwardName = "listPage";
            } else if (act.equals(RDSActionConstant.SAVE_ACTION)) {
                preProcessData(frm, req);
                service.saveFavoriteReports();
                forwardName = "saveResult";
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


    private void preProcessData(FavoriteHeaderFrm frm, HttpServletRequest req) throws ServletException {
        try {
            RequestParser parser = new RequestParser();
            CheckBoxProp checkProp = new CheckBoxProp("subCheck");
            checkProp.setIgnoreOtherField(true);
            parser.setCheckBoxProp(checkProp);
            parser.transData(req);
            String[] reportIdArr = parser.getParameterValues("reportId");
            frm.setReportIds(reportIdArr);
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ServletException(ex);
        }
    }
}