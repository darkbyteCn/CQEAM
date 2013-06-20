package com.sino.rds.execute.action;

import com.sino.base.log.Logger;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.rds.appbase.action.RDSBaseAction;
import com.sino.rds.execute.service.ReportQueryService;
import com.sino.rds.share.form.FavoriteHeaderFrm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.List;

public class ReportOrderAction extends RDSBaseAction {

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
        BaseUserDTO userAccount = getUserAccount(req);
        Connection conn = null;
        try {
            conn = getDBConnection();
            ReportQueryService service = new ReportQueryService(userAccount, frm, conn);
            List<FavoriteHeaderFrm> favorites = service.searchMyFavoriteReports();
            if(favorites == null || favorites.isEmpty()){
                forwardName = "reportList";
            } else {
                forwardName = "favoriteFrame";
            }
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            closeDBConnection(conn);
            forward = mapping.findForward(forwardName);
        }
        return forward;
    }
}
