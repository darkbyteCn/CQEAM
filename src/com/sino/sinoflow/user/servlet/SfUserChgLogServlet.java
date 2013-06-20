package com.sino.sinoflow.user.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dao.SfUserChgLogDAO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.sinoflow.user.dto.SfUserChgLogDTO;
import com.sino.sinoflow.user.model.SfUserChgLogModel;

/**
 * Title: SfGroupServlet
 * Description:程序自动生成服务程序“SfGroupServlet”，请根据需要自行修改
 * Copyright: Copyright (c) 2007
 * Company: 北京思诺博信息技术有限公司
 * @author mshtang
 * @version 1.0
 */

public class SfUserChgLogServlet extends BaseServlet {


    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(req);
            if (!(user.isSysAdmin())) {
                message = getMessage("USER.NO_AUTHORITY");
                ServletConfigDTO servletConfig = getServletConfig(req);
                message.addParameterValue(servletConfig.getSysAdminRole());
                message.addParameterValue(servletConfig.getCityAdminRole());
                forwardURL = getFilterConfig(req).getNoPriviledgeURL();
            } else {
                Request2DTO req2DTO = new Request2DTO();
                req2DTO.setDTOClassName(SfUserChgLogDTO.class.getName());
                SfUserChgLogDTO dtoParameter = (SfUserChgLogDTO) req2DTO.getDTO(req);
                conn = DBManager.getDBConnection();

                if (action.equals("")) {
                    forwardURL = "/system/userChglog.jsp";
                    req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                    BaseSQLProducer sqlProducer = new SfUserChgLogModel(user, dtoParameter);
                    PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                    pageDAO.produceWebData();
                    req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                    forwardURL = "/system/userChglog.jsp";
                } else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
                    SfUserChgLogDAO chgLogDAO = new SfUserChgLogDAO(user, dtoParameter, conn);
                    chgLogDAO.setCalPattern(LINE_PATTERN);
                    File file = chgLogDAO.getExportFile();
                    WebFileDownload fileDown = new WebFileDownload(req, res);
                    fileDown.setFilePath(file.getAbsolutePath());
                    fileDown.download();
                    file.delete();
                } else {
                    message = getMessage(MsgKeyConstant.INVALID_REQ);
                    message.setIsError(true);
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                }
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
        } catch (DataTransException e) {
            Logger.logError(e);
             message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
