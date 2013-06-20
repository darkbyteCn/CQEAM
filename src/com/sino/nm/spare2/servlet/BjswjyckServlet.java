package com.sino.nm.spare2.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.nm.spare2.dao.BjswjyckDAO;
import com.sino.nm.spare2.dto.AmsItemAllocateDDTO;
import com.sino.nm.spare2.dto.AmsItemAllocateHDTO;
import com.sino.nm.spare2.model.BjswjyckModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description:备件实物借用出库 </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-6-20
 */
public class BjswjyckServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String showMsg = "";
        String action = req.getParameter("act");
        String transType = StrUtil.nullToString(req.getParameter("transType"));
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsItemAllocateHDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
            dtoParameter = (AmsItemAllocateHDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            BjswjyckDAO itemTransHDAO = new BjswjyckDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            if (action.equals("")) {
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = "/nm/spare2/bjswjyck.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new BjswjyckModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = "/nm/spare2/bjswjyck.jsp";
            } /*else if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) req.getAttribute("AIT_HEADER");
                if (amsItemTransH == null) {
                    amsItemTransH = dtoParameter;
                }
                amsItemTransH.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
                amsItemTransH.setCreatedBy(user.getUserId());
                amsItemTransH.setCreatedUser(user.getUsername());
                amsItemTransH.setCreationDate(CalendarUtil.getCurrDate());
                amsItemTransH.setTransStatus(DictConstant.SAVE_TEMP);
                amsItemTransH.setTransStatusName("未完成");
                amsItemTransH.setToOrganizationId(user.getOrganizationId());
                req.setAttribute("AIT_HEADER", amsItemTransH);
                forwardURL = "/spare2/xgrkOrder.jsp";
            }*/ else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                itemTransHDAO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
                AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (amsItemTransH == null) {
                    amsItemTransH = new AmsItemAllocateHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AIT_HEADER", amsItemTransH);
                //查询行信息
                req.setAttribute("AIT_LINES", itemTransHDAO.getLines(amsItemTransH.getTransId()));
                forwardURL = "/nm/spare2/bjswjyckCreate.jsp";
            } /*else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransLDTO.class.getName());
                r2.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                boolean operateResult = itemTransHDAO.saveOrder(lineSet, flowDTO);
                message = itemTransHDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    forwardURL = "/servlet/com.sino.ams.spare2.servlet.AmsItemTransHServlet?act=" + WebActionConstant.DETAIL_ACTION
                            + "&transId=" + dtoParameter.getTransId();
                } else {
                    req.setAttribute("AIT_HEADER", dtoParameter);
                    forwardURL = "/spare2/xgrkOrder.jsp";
                }
            }*/ else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemAllocateDDTO.class.getName());
                r2.setIgnoreFields(AmsItemAllocateHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                boolean operateResult = itemTransHDAO.submitOrder(lineSet);
                message = itemTransHDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    forwardURL = "/servlet/com.sino.nm.spare2.servlet.BjswjyckServlet?act=" + WebActionConstant.QUERY_ACTION;
                    showMsg = "发运信息已生成!";
                } else {
                    forwardURL = "/servlet/com.sino.nm.spare2.servlet.BjswjyckServlet?act=" + WebActionConstant.DETAIL_ACTION
                            + "&transId=" + dtoParameter.getTransId() + "&transType=" + transType;
                }
            } else if (action.equals("RESEND")) {
                String resendDetailId = StrUtil.nullToString(req.getParameter("resendDetailId"));
                boolean operateResult =itemTransHDAO.do_resend(resendDetailId);
                message = itemTransHDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    forwardURL = "/servlet/com.sino.nm.spare2.servlet.BjswjyckServlet?act=" + WebActionConstant.QUERY_ACTION;
                    showMsg = "备板已重新寄送!";
                } else {
                    forwardURL = "/servlet/com.sino.nm.spare2.servlet.BjswjyckServlet?act=" + WebActionConstant.DETAIL_ACTION
                            + "&transId=" + dtoParameter.getTransId() + "&transType=" + transType;
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
        } catch (SQLException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException e) {
            Logger.logError(e);
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (showMsg.equals("")) {
                forwarder.forwardView(forwardURL);
            } else {
                forwarder.forwardOpenerView(forwardURL, showMsg);
            }
            //根据实际情况修改页面跳转代码。
        }
    }
}

