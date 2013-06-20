package com.sino.appbase.help.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.appbase.help.dto.HelpDTO;
import com.sino.appbase.help.business.HelpProcessDAO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

public class HelpTreeServlet extends BaseServlet {

    /**
     * 所有的Servlet都必须实现的方法。
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = null;
        String action = req.getParameter("action");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserBaseDTO userAccount = (SfUserBaseDTO)SessionUtil.getUserAccount(req);
            ServletConfigDTO servletConfig = SessionUtil.getServletConfigDTO(req);
            conn = DBManager.getDBConnection();
            String systemName = servletConfig.getSystemName();
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(HelpDTO.class.getName());
            HelpDTO resourceDTO = (HelpDTO) req2DTO.getDTO(req);
            HelpProcessDAO resourceDAO = new HelpProcessDAO(userAccount, resourceDTO, conn);
            if (action.equals(WebActionConstant.TREE_ACTION)) {
                String webTree = resourceDAO.getTreeHtml(systemName);
                req.setAttribute(WebAttrConstant.MENU_TREE, webTree);
                forwardURL = URLDefineList.HLP_RES_TREE_PAGE;
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.CONN_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder serForwarder = new ServletForwarder(req, res);
            serForwarder.forwardView(forwardURL);
        }
    }
}
