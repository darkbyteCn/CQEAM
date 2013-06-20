package com.sino.rds.execute.action;

import com.sino.base.log.Logger;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.rds.appbase.action.RDSBaseAction;
import com.sino.rds.execute.service.FavoriteLeftService;
import com.sino.rds.share.constant.RDSActionConstant;
import com.sino.rds.share.form.FavoriteHeaderFrm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class FavoriteLeftAction extends RDSBaseAction {

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
            FavoriteLeftService service = new FavoriteLeftService(userAccount, frm, conn);
            if (act.equals("") || act.equals(RDSActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = service.getSQLProducer();
                PageQueryDAO searchDAO = new PageQueryDAO(req, conn, sqlProducer);
                searchDAO.setCalPattern(LINE_PATTERN);
                searchDAO.setDTOClassName(FavoriteHeaderFrm.class.getName());
                
                searchDAO.setPageSize(22);
                searchDAO.produceWebData();
                req.setAttribute(mapping.getName(), frm);
                forwardName = "listPage";
            } else if(act.equals(RDSActionConstant.DELETE_ACTION)){
                service.deleteByPrimaryKey();
                forwardName = "listSearch";
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
}
