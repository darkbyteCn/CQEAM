package com.sino.ams.spare.servlet;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.constant.SparePROCConstant;
import com.sino.ams.spare.constant.SpareURLDefine;
import com.sino.ams.spare.constant.SpareWebAction;
import com.sino.ams.spare.dao.AmsItemTransHDAO;
import com.sino.ams.spare.dao.AmsItemTransLDAO;
import com.sino.ams.spare.dao.RepairBackDAO;
import com.sino.ams.spare.dto.AmsItemTransDDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.RepairBackModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
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
import java.util.ArrayList;
import java.util.List;

/**
 * User: srf
 * Date: 2008-3-18
 * Time: 21:20:32
 * Function:返修申请（新）
 */
public class RepairBackServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String showMsg = "";
        String action = req.getParameter("act");
        String transType = StrUtil.nullToString(req.getParameter("transType"));
        String taskProp = StrUtil.nullToString(req.getParameter("taskProp"));
        String flowAction = StrUtil.nullToString(req.getParameter("flowAct"));
        boolean isFirstNode = taskProp.equals("") || taskProp.equals(FlowConstant.TASK_PROP_START);
        String sectionRight = StrUtil.nullToString(req.getParameter("sectionRight"));
        String hiddenRight = StrUtil.nullToString(req.getParameter("hiddenRight"));

        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsItemTransHDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
            dtoParameter = (AmsItemTransHDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            RepairBackDAO repairBackDAO = new RepairBackDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            if (action.equals("")) {
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = "/spare/bjOrderQuery.jsp";
            } else if (action.equals(SpareWebAction.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new RepairBackModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = "/spare/bjOrderQuery.jsp";
            } else if (action.equals(SpareWebAction.NEW_ACTION)) {  //新增单据
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) req.getAttribute("AIT_HEADER");
                if (orderHeader == null) {
                    orderHeader = dtoParameter;
                    orderHeader.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
                    orderHeader.setCreatedBy(user.getUserId());
                    orderHeader.setCreatedUser(user.getUsername());
                    orderHeader.setCreationDate(CalendarUtil.getCurrDate());
                    orderHeader.setTransStatus("SAVE_TEMP");
                    orderHeader.setTransStatusName("未完成");
                    orderHeader.setTransType(DictConstant.FXSQ);
                    orderHeader.setFromOrganizationName(user.getCompany());
                    orderHeader.setFromOrganizationId(user.getOrganizationId());
                }
                req.setAttribute("AIT_HEADER", orderHeader);
                forwardURL = SpareURLDefine.BACK_CREATE_PAGE;
            } else if (action.equals(SpareWebAction.DETAIL_ACTION)) {
                repairBackDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) repairBackDAO.getDataByPrimaryKey();
                if (orderHeader == null) {
                    orderHeader = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AIT_HEADER", orderHeader);
                //查询行信息
                AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                req.setAttribute("AIT_LINES", ldao.getLines2(orderHeader.getTransId()));
                forwardURL = SpareURLDefine.BACK_INFO_PAGE;
            } else if (action.equals(SpareWebAction.VIEW_ACTION)) {//单据查看页面
                repairBackDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) repairBackDAO.getDataByPrimaryKey();
                if (orderHeader == null) {
                    orderHeader = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AIT_HEADER", orderHeader);
                //查询行信息
                AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                req.setAttribute("AIT_LINES", ldao.getLines2(orderHeader.getTransId()));
                forwardURL = SpareURLDefine.BACK_INFO_PAGE;
            } else if (action.equals(SpareWebAction.SAVE_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransDDTO.class.getName());
                r2.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet ds = r2.getDTOSet(req);

                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setProcName(SparePROCConstant.REPAIRE_BACK_PROC);

                boolean operateResult = repairBackDAO.saveOrder(ds, flowDTO, flowAction, isFirstNode);
                message = repairBackDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    if (isFirstNode && flowAction.equals(DictConstant.FLOW_SAVE)) {
                        forwardURL = SpareURLDefine.BACK_SERVLET_PAGE + "?act=approve&transId=" + dtoParameter.getTransId();
                    } else {
                        forwardURL = SpareURLDefine.BACK_SERVLET_PAGE + "?act=" + SpareWebAction.DETAIL_ACTION + "&transId=" + dtoParameter.getTransId();
                    }
                } else {
                    req.setAttribute("AIT_HEADER", dtoParameter);
                    forwardURL = SpareURLDefine.BACK_CREATE_PAGE;
                }
            } else if (action.equals("原先的保存操作")) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransLDTO.class.getName());
                r2.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                boolean operateResult = true;
//                        operateResult = repairBackDAO.saveOrder(lineSet, flowDTO);
                message = repairBackDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    forwardURL = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" +
                            SpareWebAction.DETAIL_ACTION
                            + "&transId=" + dtoParameter.getTransId();
                } else {
                    req.setAttribute("AIT_HEADER", dtoParameter);
                    forwardURL = SpareURLDefine.BACK_CREATE_PAGE;
                }
            } else if (action.equals(SpareWebAction.SUBMIT_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransLDTO.class.getName());
                r2.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
//                FlowAction.
                boolean operateResult = repairBackDAO.submitOrder(lineSet, flowDTO);
                message = repairBackDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    forwardURL = "/servlet/com.sino.ams.spare.servlet.AmsItemTransHServlet?act=" +
                            SpareWebAction.QUERY_ACTION;
                    showMsg = "单据" + dtoParameter.getTransNo() + "已提交!";
                } else {
                    forwardURL = "/servlet/com.sino.ams.spare.servlet.AmsItemTransHServlet?act=" +
                            SpareWebAction.DETAIL_ACTION
                            + "&transId=" + dtoParameter.getTransId() + "&transType=" + transType;
                }
            } else if (action.equals("approve")) { //审批
                repairBackDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO transHDTO = (AmsItemTransHDTO) repairBackDAO.getDataByPrimaryKey();
                if (transHDTO == null) {
                    transHDTO = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AIT_HEADER", transHDTO);
                //查询行信息
                AmsItemTransHDAO transHDAO = new AmsItemTransHDAO(user, transHDTO, conn);
                if (hiddenRight.equals("OUT")) {
                    req.setAttribute("AIT_LINES", transHDAO.getLineData(transHDTO.getTransId()));
                    forwardURL = SpareURLDefine.BACK_APPROVE_PAGE;
                } else {
                    req.setAttribute("AIT_LINES", transHDAO.getDetailData(transHDTO.getTransId()));
                    forwardURL = SpareURLDefine.BACK_CREATE_PAGE;
                }
            } else if (action.equals(SpareWebAction.APPROVE_ACTION)) { //省公司审批提交
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransLDTO.class.getName());
                List ignorList = new ArrayList();
                ignorList.add("transId");
//                ignorList.add("quantity");
                ignorList.add("act");
                r2.setIgnoreFields(ignorList);
                DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_AGREE);
//                boolean isFlowToEnd

                boolean isOut = sectionRight.equals("OUT") && hiddenRight.equals("OUT");
                String operateResult = repairBackDAO.approveOrder(lineSet, flowDTO, isOut);
                message = repairBackDAO.getMessage();
//                message.setIsError(!operateResult);
//                if (operateResult.equals("")) {
//                    forwardURL = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" + "approve"
//                            + "&transId=" + dtoParameter.getTransId();
//                } else {
                if (operateResult.equals("end")) {
                    showMsg = "调拨单已生成!";
                } else {
                    showMsg = "单据已通过审批!";
                }

//                }
            } else if (action.equals("ALLOT")) { //审批行明细
                String barcode1 = req.getParameter("barcode1");
                String transId = req.getParameter("transId");
                String lineId1 = req.getParameter("lineId1");
                String qty = req.getParameter("sqty");
                String org = req.getParameter("orgvalue");
                dtoParameter.setTransId(transId);
                AmsItemTransLDTO dto = new AmsItemTransLDTO();
                dto.setQuantity(Integer.parseInt(qty));
                dto.setBarcode(barcode1);
                dto.setLineId(lineId1);
                req.setAttribute("LDTO", dto);
                req.setAttribute("trId", dtoParameter);
                String objectNo = repairBackDAO.hasObjectNo(transId, barcode1);
                RowSet row = repairBackDAO.produceWebData(barcode1, transId, lineId1, org, objectNo);
                req.setAttribute("ALLOTBARCODE", row);
                forwardURL = "/spare/bjfxsqfp.jsp";

            } else if (action.equals("SEARCH")) {
                String transId = req.getParameter("transId");
                RowSet row = repairBackDAO.getBanchNo(transId);
                req.setAttribute("SERIAL_NO", row);
                forwardURL = "/spare/bjfxsqSerialNo.jsp";
            } else if (action.equals("write")) {
                String lineId = req.getParameter("lineId1");
                String transId = req.getParameter("transId");
                String barcode = req.getParameter("barcode1");
                String orgId[] = req.getParameterValues("organizationId");
                String holdQty[] = req.getParameterValues("holdCount");
                String detailId[] = req.getParameterValues("detailId");
                repairBackDAO.writeDetails(lineId, barcode, transId, orgId, holdQty, detailId);
                showMsg = "分配成功!";
                forwardURL = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" + "approve" + "&transId=" + transId;
            } else if (action.equals(SpareWebAction.REJECT_ACTION)) {//退回
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
//                String content = StrUtil.nullToString(req.getParameter("approveOpinion"));
//                flowDTO.setApproveContent(content);
                repairBackDAO.reject(dtoParameter, flowDTO);
                showMsg = "备件返修申请单已退回!";
            } else if (action.equals(SpareWebAction.CANCEL_ACTION)) { //单据撤销
                repairBackDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) repairBackDAO.getDataByPrimaryKey();
                AmsItemTransHDAO transHDAO = new AmsItemTransHDAO(user, orderHeader, conn);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setProcName(SparePROCConstant.REPAIRE_BACK_PROC);
                boolean operateResult = transHDAO.cancelItemOrder(flowDTO);
                message = transHDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    forwardURL = SpareURLDefine.BACK_SERVLET_PAGE + "?act=" + SpareWebAction.VIEW_ACTION + "&transId=" + dtoParameter.getTransId();
                } else {
                    if (orderHeader == null) {
                        orderHeader = new AmsItemTransHDTO();
                        message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                        message.setIsError(true);
                    }
                    req.setAttribute("AIT_HEADER", orderHeader);
                    AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                    req.setAttribute("AIT_LINES", ldao.getLines2(orderHeader.getTransId()));
                    forwardURL = SpareURLDefine.BACK_INFO_PAGE;
                }

            } else if (action.equals("DO_EQUALS")) { //判断扫描数量和申请数量是否相等
                boolean equals = repairBackDAO.do_checkQty(dtoParameter.getTransId());
                message = repairBackDAO.getMessage();
                PrintWriter out = res.getWriter();
                if (equals) {
                    out.print("Y");
                }
                out.flush();
                out.close();
            } else if (action.equals("firstN")) { //第一个节点提交
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransLDTO.class.getName());
                List ignorList = new ArrayList();
                ignorList.add("transId");
//                ignorList.add("quantity");
                ignorList.add("act");
                r2.setIgnoreFields(ignorList);
                DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_AGREE);
//                boolean isFlowToEnd

                String operateResult = repairBackDAO.firstN(lineSet, flowDTO, sectionRight);
                message = repairBackDAO.getMessage();
//                message.setIsError(!operateResult);
//                if (operateResult.equals("")) {
//                    forwardURL = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" + "approve"
//                            + "&transId=" + dtoParameter.getTransId();
//                } else {
                if (operateResult.equals("end")) {
                    showMsg = "调拨单已生成!";
                } else {
                    showMsg = "单据已通过审批!";
                }

//                }
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
        } catch (DataHandleException ex) {
            ex.printLog();
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (ContainerException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (!action.equals("DO_EQUALS")) {
                if (showMsg.equals("")) {
                    forwarder.forwardView(forwardURL);
                } else {
                    forwarder.forwardOpenerView(forwardURL, showMsg);
                }
            }
        }
    }
}