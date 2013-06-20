package com.sino.rds.execute.action;

import com.sino.base.log.Logger;
import com.sino.rds.appbase.action.RDSBaseAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FavoriteFrameAction extends RDSBaseAction {

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
        try {
            forward = mapping.findForward("favoriteFrame");
        } catch (Throwable ex) {
            Logger.logError(ex);
            throw new ServletException(ex);
        }
        return forward;
    }
}
