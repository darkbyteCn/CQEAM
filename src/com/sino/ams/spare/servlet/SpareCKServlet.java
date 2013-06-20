package com.sino.ams.spare.servlet;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.constant.SpareURLDefine;
import com.sino.ams.spare.constant.SpareWebAction;
import com.sino.ams.spare.dao.AmsItemTransHDAO;
import com.sino.ams.spare.dao.SpareCKDAO;
import com.sino.ams.spare.dto.AmsItemAllocateDDTO;
import com.sino.ams.spare.dto.AmsItemAllocateHDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.AmsItemTransHModel;
import com.sino.ams.spare.model.AmsItemTransLModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareCKServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String showMsg = "";
        Connection conn = null;
        boolean operateResult = false;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
            AmsItemTransHDTO dto = (AmsItemTransHDTO) req2DTO.getDTO(req);
            String action = dto.getAct();
            conn = getDBConnection(req);
            AmsItemTransHDAO itemTransHDAO = new AmsItemTransHDAO(user, dto, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            RowSet rowSet = new RowSet();
            if (!dto.getApp_dataID().equals("")) {
                dto.setTransId(dto.getApp_dataID());
            }
            if (action.equals("")) {
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dto.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dto.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = SpareURLDefine.CK_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsItemTransHModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dto.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dto.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = SpareURLDefine.CK_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) req.getAttribute("AIT_HEADER");
                if (orderHeader == null) {
                    orderHeader = dto;
                    orderHeader.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
                    orderHeader.setTransType(DictConstant.BJCK);
                    orderHeader.setCreatedBy(user.getUserId());
                    orderHeader.setCreatedUser(user.getUsername());
                    orderHeader.setCreationDate(CalendarUtil.getCurrDate());
                    orderHeader.setTransStatusName("未完成");
                    orderHeader.setToOrganizationId(user.getOrganizationId());
                }
                orderHeader.setAttribute1(dto.getSf_task_attribute1());
                String orderReason = optProducer.getDictOption2("SPARE_REASON", "");
                req.setAttribute("ORDER_REASON", orderReason);
                req.setAttribute("AIT_HEADER", orderHeader);
                forwardURL = SpareURLDefine.CK_CREATE_PAGE;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (orderHeader == null) {
                    orderHeader = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String orderReason = optProducer.getDictOption2("SPARE_REASON", orderHeader.getSpareReason());
                req.setAttribute("ORDER_REASON", orderReason);
                req.setAttribute("AIT_HEADER", orderHeader);
                AmsItemTransLModel amsItemTransLModel = new AmsItemTransLModel(user, null);
                SQLModel sqlModel = amsItemTransLModel.getByHIdModel(orderHeader.getTransId());
                SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
                simpleQuery.executeQuery();
                if (simpleQuery.hasResult()) {
                    rowSet = simpleQuery.getSearchResult();
                }
                req.setAttribute("AIT_LINES", rowSet);
                orderHeader.setAttribute1(dto.getSf_task_attribute1());
                forwardURL = SpareURLDefine.CK_CREATE_PAGE;
            }  else if (action.equals(SpareWebAction.VIEW_ACTION)) { //单据查看页面
                itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (orderHeader == null) {
                    orderHeader = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String orderReason = optProducer.getDictOption2("SPARE_REASON", orderHeader.getSpareReason());
                req.setAttribute("ORDER_REASON", orderReason);
                req.setAttribute("AIT_HEADER", orderHeader);
                AmsItemTransLModel amsItemTransLModel = new AmsItemTransLModel(user, null);
                SQLModel sqlModel = amsItemTransLModel.getByHIdModel(orderHeader.getTransId());
                SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
                simpleQuery.executeQuery();
                if (simpleQuery.hasResult()) {
                    rowSet = simpleQuery.getSearchResult();
                }
                req.setAttribute("AIT_LINES", rowSet);
                forwardURL = SpareURLDefine.CK_VIEW_PAGE;
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {  //备件出库的暂存操作
                req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                dto.setFromOrganizationId(user.getOrganizationId());
//                flowDTO.setSessionUserId(user.getUserId());
//                flowDTO.setSessionUserName(user.getUsername());
//                operateResult = itemTransHDAO.saveCKOrder(lineSet, flowDTO);
//                message = itemTransHDAO.getMessage();
//                message.setIsError(!operateResult);
//                if (operateResult) {
//                    forwardURL = "/servlet/com.sino.ams.spare.servlet.SpareCKServlet?act=" + WebActionConstant.DETAIL_ACTION + "&transId=" + dto.getTransId();
//                } else {
//                    req.setAttribute("AIT_HEADER", dto);
//                    forwardURL = SpareURLDefine.CK_CREATE_PAGE;
//                }
            } else if (action.equals(WebActionConstant.CANCEL_ACTION)) {    //备件出库撤销
                req2DTO.setDTOClassName(AmsItemAllocateDDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemAllocateHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
//                flowDTO.setSessionUserId(user.getUserId());
//                flowDTO.setSessionUserName(user.getUsername());
//                operateResult = itemTransHDAO.cancelOrder(lineSet, flowDTO);
//                message = itemTransHDAO.getMessage();
//                message.setIsError(!operateResult);
//                if (operateResult) {
//                    forwardURL = "/servlet/com.sino.ams.spare.servlet.SpareCKServlet?act=" + SpareWebAction.VIEW_ACTION + "&transId=" + dto.getTransId();
//                } else {
//                    req.setAttribute("AIT_HEADER", dto);
//                    forwardURL = SpareURLDefine.CK_CREATE_PAGE;
//                }
            } else if (action.equals(SpareWebAction.SUBMIT_ACTION)) {
                req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                dto.setTransStatus(DictConstant.IN_PROCESS);//状态为处理中，而非完成
                SpareCKDAO spareCKDAO = new SpareCKDAO(user, dto, conn);
                operateResult = spareCKDAO.submitOrder(lineSet, dto);
                if (operateResult) {
                    showMsg = "保存数据成功";
                } else {
                    showMsg = "保存数据失败";
                }
            } else if (action.equals(SpareWebAction.APPROVE_ACTION)) {
                req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                SpareCKDAO spareCKDAO = new SpareCKDAO(user, dto, conn);
                operateResult = spareCKDAO.approve(lineSet, dto);
                message = spareCKDAO.getMessage();
                dto = (AmsItemTransHDTO) spareCKDAO.getDTOParameter();
                forwardURL = "/servlet/com.sino.ams.spare.servlet.SpareCKServlet?act=look&transId=" + dto.getTransId();
            } else if (action.equals("print")) { //备件出库打印
                itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (orderHeader == null) {
                    orderHeader = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String orderReason = optProducer.getDictOption2("SPARE_REASON", orderHeader.getSpareReason());
                req.setAttribute("ORDER_REASON", orderReason);
                req.setAttribute("AIT_HEADER", orderHeader);
                AmsItemTransLModel amsItemTransLModel = new AmsItemTransLModel(user, null);
                SQLModel sqlModel = amsItemTransLModel.getByHIdModel(orderHeader.getTransId());
                SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
                simpleQuery.executeQuery();
                if (simpleQuery.hasResult()) {
                    rowSet = simpleQuery.getSearchResult();
                }
                req.setAttribute("AIT_LINES", rowSet);
                forwardURL = SpareURLDefine.CK_PRINT_PAGE;
            } else if (action.equals(SpareWebAction.REJECT_ACTION)) {  //备件出库单据退回操作
                req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);

//                flowDTO.setSessionUserId(user.getUserId());
//                flowDTO.setSessionUserName(user.getUsername());
//                flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
//                String content = StrUtil.nullToString(req.getParameter("approveOpinion"));
//                flowDTO.setApproveContent(content);
//                SpareCKDAO spareCKDAO = new SpareCKDAO(user, dto, conn);
//
//                operateResult = spareCKDAO.reject(dto, lineSet,flowDTO);
//                message = spareCKDAO.getMessage();
//                forwardURL = "/servlet/com.sino.ams.spare.servlet.SpareCKServlet?act=look&transId=" + dto.getTransId();
            } else if (action.equals("look")) {
                itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (orderHeader == null) {
                    orderHeader = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String orderReason = optProducer.getDictOption2("SPARE_REASON", orderHeader.getSpareReason());
                req.setAttribute("ORDER_REASON", orderReason);
                req.setAttribute("AIT_HEADER", orderHeader);
                AmsItemTransLModel amsItemTransLModel = new AmsItemTransLModel(user, null);
                SQLModel sqlModel = amsItemTransLModel.getByHIdModel(orderHeader.getTransId());
                SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
                simpleQuery.executeQuery();
                if (simpleQuery.hasResult()) {
                    rowSet = simpleQuery.getSearchResult();
                }
                req.setAttribute("AIT_LINES", rowSet);
                forwardURL = SpareURLDefine.CK_LOOK_PAGE;
            } else if (action.equals("CHECK_KYL")) {
                String barcode = StrUtil.nullToString(req.getParameter("barcode"));
                String fromObjectNo = StrUtil.nullToString(req.getParameter("fromObjectNo"));
                String orgId = StrUtil.nullToString(req.getParameter("orgId"));
                String qtyObj = StrUtil.nullToString(req.getParameter("qtyObj"));
                SpareCKDAO spareCKDAO = new SpareCKDAO(user, dto, conn);
                String onhandQty = spareCKDAO.checkKYL(barcode, fromObjectNo, orgId);
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                if (Integer.parseInt(qtyObj) > Integer.parseInt(onhandQty)) {
                    out.print("Y");
                } else {
                    out.print("N");
                }
                out.flush();
                out.close();
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
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (ContainerException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
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
