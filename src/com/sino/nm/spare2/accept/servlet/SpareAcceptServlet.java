package com.sino.nm.spare2.accept.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.dto.AmsItemAllocateDDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.ConvertUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.nm.spare2.accept.dao.SpareAcceptDAO;
import com.sino.nm.spare2.accept.model.SpareAcceptModel;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-2-18
 */
public class SpareAcceptServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String showMsg = "";
        String action = StrUtil.nullToString(req.getParameter("act"));
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsItemTransHDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
            dtoParameter = (AmsItemTransHDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            SpareAcceptDAO srDAO = new SpareAcceptDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            if (action.equals("")) {
                String ouOptions = optProducer.getAllOU(dtoParameter.getFromOrganizationId(), true);
                req.setAttribute(WebAttrConstant.OU_OPTION, ouOptions);
                forwardURL = "/nm/spare2/spareAcceptQuery.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new SpareAcceptModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                String ouOptions = optProducer.getAllOU(dtoParameter.getFromOrganizationId(), true);
                req.setAttribute(WebAttrConstant.OU_OPTION, ouOptions);
                forwardURL = "/nm/spare2/spareAcceptQuery.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                srDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) srDAO.getDataByPrimaryKey();
                if (amsItemTransH == null) {
                    amsItemTransH = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AIT_HEADER", amsItemTransH);
                //查询行信息
                req.setAttribute("AIT_LINES", srDAO.getDetails(amsItemTransH.getTransId()));
                forwardURL = "/nm/spare2/spareAcceptDetail.jsp";
            } else if (action.equals(WebActionConstant.RECEIVE_ACTION)) {
                boolean success = true;
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemAllocateDDTO.class.getName());
                r2.setIgnoreFields(AmsItemTransHDTO.class);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                String[] freightId = req.getParameterValues("subCheck");
                for (int i = 0; i < freightId.length; i++) {
                    String[] freightIds = freightId[i].split(";");
                    success = srDAO.accept(freightIds[0], freightIds[1], freightIds[2], freightIds[3], freightIds[4]);
                }

                message = srDAO.getMessage();
                message.setIsError(!success);
                if (success) {
                    showMsg = "已接收确认！";
                    forwardURL = "/servlet/com.sino.nm.spare2.accept.servlet.SpareAcceptServlet?act=" + WebActionConstant.QUERY_ACTION;
                } else {
                    forwardURL = "/servlet/com.sino.nm.spare2.accept.servlet.SpareAcceptServlet?act=" + WebActionConstant.QUERY_ACTION;
                }
            } else if (action.equals("NO_RECEIVE_ACTION")) {
                boolean success = true;
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemAllocateDDTO.class.getName());
                r2.setIgnoreFields(AmsItemTransHDTO.class);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                String[] freightId = req.getParameterValues("subCheck");
                for (int i = 0; i < freightId.length; i++) {
                    String[] freightIds = freightId[i].split(";");
                    success = srDAO.noAccept(freightIds[0], freightIds[1], freightIds[2], freightIds[3], freightIds[4]);
                }
//                boolean success = srDAO.noAccept(lineSet);
                message = srDAO.getMessage();
                message.setIsError(!success);
                if (success) {
                    showMsg = "未接收确认！";
                    forwardURL = "/servlet/com.sino.nm.spare2.accept.servlet.SpareAcceptServlet?act=" + WebActionConstant.QUERY_ACTION;
                } else {
                    forwardURL = "/servlet/com.sino.nm.spare2.accept.servlet.SpareAcceptServlet?act=" + WebActionConstant.QUERY_ACTION;
                }
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } /*catch (DataHandleException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }*/ finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (showMsg.equals("")) {
                forwarder.forwardView(forwardURL);
            } else {
                forwarder.forwardView(forwardURL, showMsg);
            }
            //根据实际情况修改页面跳转代码。
        }
    }
}
