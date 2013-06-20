package com.sino.ams.spare.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.dao.AmsItemTransHDAO;
import com.sino.ams.spare.dao.AmsItemTransLDAO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.AmsItemTransHModel;
import com.sino.ams.spare.constant.SpareURLDefine;
import com.sino.ams.spare.constant.SpareWebAttributes;
import com.sino.ams.spare.constant.SpareWebAction;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class BjslServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        String showMsg = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        boolean operateResu = false;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
            AmsItemTransHDTO dto = (AmsItemTransHDTO) req2DTO.getDTO(req);
            String action = dto.getAct();
            conn = getDBConnection(req);
            AmsItemTransHDAO itemTransHDAO = new AmsItemTransHDAO(user, dto, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            String vendorOption = optProducer.getSpareVendorOption(dto.getVendorId());
            req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
            if (!dto.getApp_dataID().equals("")) {
                dto.setTransId(dto.getApp_dataID());
            }
            if (action.equals("")) {
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dto.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);                                
                String invOption = optProducer.getInvOption(dto.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = SpareURLDefine.SL_QUERY_PAGE;
            } else if (action.equals(SpareWebAction.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsItemTransHModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dto.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dto.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = SpareURLDefine.SL_QUERY_PAGE;
            } else if (action.equals(SpareWebAction.NEW_ACTION)) {
                if (dto.getTransId().equals("")) {
                    AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) req.getAttribute(SpareWebAttributes.BJSL_HEADER);
                    if (orderHeader == null) {
                        orderHeader = dto;
                        orderHeader.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
                        orderHeader.setTransType(DictConstant.BJSL);
                        orderHeader.setCreatedBy(user.getUserId());
                        orderHeader.setCreatedUser(user.getUsername());
                        orderHeader.setToOrganizationId(user.getOrganizationId());
                        orderHeader.setFromOrganizationId(user.getOrganizationId());
                        orderHeader.setToOrganizationName(user.getCompany());
                        orderHeader.setCreationDate(CalendarUtil.getCurrDate());
                        orderHeader.setTransStatusName("未完成");
                        req.setAttribute(SpareWebAttributes.BJSL_HEADER, orderHeader);
                        forwardURL= SpareURLDefine.SL_CREATE_PAGE;
                    }
                } else {
                    itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                    AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
                    if (orderHeader == null) {
                        orderHeader = new AmsItemTransHDTO();
                        message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                        message.setIsError(true);
                    }
                    vendorOption = optProducer.getSpareVendorOption(orderHeader.getVendorId());
                    req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
                    req.setAttribute(SpareWebAttributes.BJSL_HEADER, orderHeader);
                    AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                    req.setAttribute(SpareWebAttributes.BJSL_LINES, ldao.getLines(orderHeader.getTransId()));
                    forwardURL = SpareURLDefine.SL_CREATE_PAGE;
                }
            } else if (action.equals(SpareWebAction.DETAIL_ACTION)) {
                itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (orderHeader == null) {
                    orderHeader = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                vendorOption = optProducer.getSpareVendorOption(orderHeader.getVendorId());
                req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
                req.setAttribute(SpareWebAttributes.BJSL_HEADER, orderHeader);
                AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                req.setAttribute(SpareWebAttributes.BJSL_LINES, ldao.getLines(orderHeader.getTransId()));
                forwardURL = SpareURLDefine.SL_CREATE_PAGE;
            }  else if (action.equals(SpareWebAction.VIEW_ACTION)) { //单据查看页面
                itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (orderHeader == null) {
                    orderHeader = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                vendorOption = optProducer.getSpareVendorOption(orderHeader.getVendorId());
                req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
                req.setAttribute(SpareWebAttributes.BJSL_HEADER, orderHeader);
                AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                req.setAttribute(SpareWebAttributes.BJSL_LINES, ldao.getLines4(orderHeader.getTransId()));
                forwardURL = SpareURLDefine.SL_VIEW_PAGE;
            } else if (action.equals(SpareWebAction.SAVE_ACTION)) {  //备件申领暂存操作
                req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
//                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
//                flowDTO.setSessionUserId(user.getUserId());
//                flowDTO.setSessionUserName(user.getUsername());
//                boolean operateResult = itemTransHDAO.saveOrder(lineSet, flowDTO);
//                message = itemTransHDAO.getMessage();
//                message.setIsError(!operateResult);
//                if (operateResult) {
//                    forwardURL = "/servlet/com.sino.ams.spare.servlet.BjslServlet?act=" + SpareWebAction.DETAIL_ACTION + "&transId=" + dto.getTransId();
//                } else {
//                    req.setAttribute("BJSL_HEADER", dto);
//                    forwardURL = SpareURLDefine.SL_CREATE_PAGE;
//                }
            } else if (action.equals(SpareWebAction.CANCEL_ACTION)) {    //调拨暂存撤销
                req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
//                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
//                flowDTO.setSessionUserId(user.getUserId());
//                flowDTO.setSessionUserName(user.getUsername());
//                boolean operateResult = itemTransHDAO.cancelslOrder(lineSet, flowDTO);
//                message = itemTransHDAO.getMessage();
//                message.setIsError(!operateResult);
//                if (operateResult) {
//                    forwardURL = "/servlet/com.sino.ams.spare.servlet.BjslServlet?act=" + SpareWebAction.VIEW_ACTION + "&transId=" + dto.getTransId();
//                } else {
//                    req.setAttribute("BJSL_HEADER", dto);
//                    forwardURL = SpareURLDefine.SL_CREATE_PAGE;
//                }
            } else if (action.equals(SpareWebAction.SUBMIT_ACTION)) { //备件申领提交流程
                req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                operateResu = itemTransHDAO.submitOrder(lineSet, dto);
                message = itemTransHDAO.getMessage();
                if (operateResu) {
                    showMsg = "保存数据成功";
                } else {
                    showMsg = "保存数据失败";
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
        } catch (CalendarException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException e) {
            e.printStackTrace();
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }
        finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (!forwardURL.equals("")) {
                forwarder.forwardView(forwardURL);
            } else {
                if (!showMsg.equals("")) {
                    forwarder.forwardOpenerView(forwardURL, showMsg);
                }
            }
        }
    }
}
