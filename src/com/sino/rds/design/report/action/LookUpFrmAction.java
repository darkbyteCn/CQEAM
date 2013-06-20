package com.sino.rds.design.report.action;

import com.sino.base.log.Logger;
import com.sino.rds.appbase.action.RDSBaseAction;
import com.sino.rds.share.form.LookUpDefineFrm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LookUpFrmAction extends RDSBaseAction {//报表定义的Frame框架Action

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
        LookUpDefineFrm frm = (LookUpDefineFrm) form;
        try {
            req.setAttribute(mapping.getName(), frm);
            forwardName = "frmPage";
        } catch (Throwable ex) {
            Logger.logError(ex);
        } finally {
            forward = mapping.findForward(forwardName);
        }
        return forward;
    }
}
