package com.sino.ams.net.equip.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.net.equip.dao.IntegratedDAO;
import com.sino.ams.net.equip.dto.IntegratedDTO;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * Created by IntelliJ IDEA.
 * User: Owner
 * Date: 2008-1-28
 * Time: 11:46:44
 * To change this template use File | Settings | File Templates.
 */
public class IntegratedServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String act = req.getParameter("act");
        act = StrUtil.nullToString(act);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            IntegratedDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(IntegratedDTO.class.getName());
            dtoParameter = (IntegratedDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            IntegratedDAO IntegratedDAO = new IntegratedDAO(user, dtoParameter, conn);
            if (act.equals("")) {
                String allQueryFields = IntegratedDAO.getAllQueryFields();
                req.setAttribute(AssetsWebAttributes.ALL_QRY_FIELDS, allQueryFields);
                String chkQueryFields = IntegratedDAO.getChkQueryFields();
                req.setAttribute(AssetsWebAttributes.CHK_QRY_FIELDS, chkQueryFields);
                String allDisplayFields = IntegratedDAO.getAllDisplayFields();
                req.setAttribute(AssetsWebAttributes.ALL_DIS_FIELDS, allDisplayFields);
                String chkDisplayFields = IntegratedDAO.getChkDisplayFields();
                req.setAttribute(AssetsWebAttributes.CHK_DIS_FIELDS, chkDisplayFields);
                forwardURL = URLDefineList.QRY_BY_INTEGRATED;
            } else if (act.equals(WebActionConstant.SAVE_ACTION)) {
                String[] chkQueryFields = req.getParameterValues("chkQueryField");
                String[] chkDisplayFields = req.getParameterValues("chkDisplayField");
                IntegratedDAO.saveCustomizeFields(chkQueryFields, chkDisplayFields);
                message = IntegratedDAO.getMessage();
                forwardURL = URLDefineList.QRY_BY_INTEGRATEDSERVLET;
                forwardURL += "?act=";

            } else if (act.equals(WebActionConstant.QUERY_ACTION)) {

                forwardURL = URLDefineList.QRY_BY_INTAGRATEDQUERY_SERVLET;
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        }
        catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }


    }
}
