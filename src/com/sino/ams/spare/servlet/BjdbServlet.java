package com.sino.ams.spare.servlet;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.bean.ProcedureUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.constant.SpareURLDefine;
import com.sino.ams.spare.constant.SpareWebAction;
import com.sino.ams.spare.dao.BjDbDAO;
import com.sino.ams.spare.dto.AmsItemAllocateDDTO;
import com.sino.ams.spare.dto.AmsItemAllocateHDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.model.BjDboModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.DateConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
public class BjdbServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        boolean operateResu = false;
        String showMsg = "";
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
            AmsItemAllocateHDTO dto = (AmsItemAllocateHDTO) req2DTO.getDTO(req);
            String action = dto.getAct();
            conn = getDBConnection(req);
            BjDbDAO orderDAO = new BjDbDAO(user, dto, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            if (!dto.getApp_dataID().equals("")) {
                dto.setTransId(dto.getApp_dataID());
            }
            if (action.equals("")) {
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dto.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dto.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = SpareURLDefine.DB_QUERY_PAGE;
            } else if (action.equals(SpareWebAction.QUERY_ACTION)) {    //备件调拨查询
                BjDboModel sqlProducer = new BjDboModel(user, dto);
                SQLModel dbModel = sqlProducer.getbjdbQueryModel();
                WebPageView wpv = new WebPageView(req, conn);
                wpv.produceWebData(dbModel);
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dto.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dto.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = SpareURLDefine.DB_QUERY_PAGE;
            } else if (action.equals(SpareWebAction.NEW_ACTION)) {
                AmsItemAllocateHDTO headerDTO = (AmsItemAllocateHDTO) req.getAttribute("AIT_HEADER");
                if (headerDTO == null) {
                    headerDTO = dto;
                    headerDTO.setFromOrganizationId(user.getOrganizationId());
                    headerDTO.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
                    headerDTO.setTransType(DictConstant.BJDB);
                    headerDTO.setCreatedBy(user.getUserId());
                    headerDTO.setCreatedUser(user.getUsername());
                    headerDTO.setCreationDate(CalendarUtil.getCurrDate());
                    headerDTO.setTransStatusName("新增");
                }
                String fromOrg = optProducer.getAllOU(headerDTO.getFromOrganizationId(), false);
                req.setAttribute(WebAttrConstant.CITY_OPTION, fromOrg);
                String toOrg = optProducer.getAllOU(headerDTO.getToOrganizationId(), false);
                req.setAttribute(WebAttrConstant.OU_OPTION, toOrg);
                req.setAttribute("AIT_HEADER", headerDTO);
                forwardURL = SpareURLDefine.DB_CREATE_PAGE;
            } else if (action.equals(SpareWebAction.DETAIL_ACTION)) {
                orderDAO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
                AmsItemAllocateHDTO headerDTO = (AmsItemAllocateHDTO) orderDAO.getDataByPrimaryKey();
                if (headerDTO == null) {
                    headerDTO = new AmsItemAllocateHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String fromOrg = optProducer.getAllOrganization(headerDTO.getFromOrganizationId());
                req.setAttribute(WebAttrConstant.CITY_OPTION, fromOrg);
                String toOrg = optProducer.getAllOrganization(headerDTO.getToOrganizationId());
                req.setAttribute(WebAttrConstant.OU_OPTION, toOrg);
                req.setAttribute("AIT_HEADER", headerDTO);
                req.setAttribute("AIT_LINES", orderDAO.getLines(headerDTO.getTransId()));
                forwardURL = SpareURLDefine.DB_CREATE_PAGE;
            } else if (action.equals(SpareWebAction.VIEW_ACTION)) {//单据查看页面
                orderDAO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
                AmsItemAllocateHDTO headerDTO = (AmsItemAllocateHDTO) orderDAO.getDataByPrimaryKey();
                if (headerDTO == null) {
                    headerDTO = new AmsItemAllocateHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String fromOrg = optProducer.getAllOrganization(headerDTO.getFromOrganizationId());
                req.setAttribute(WebAttrConstant.CITY_OPTION, fromOrg);
                String toOrg = optProducer.getAllOrganization(headerDTO.getToOrganizationId());
                req.setAttribute(WebAttrConstant.OU_OPTION, toOrg);
                req.setAttribute("AIT_HEADER", headerDTO);
                req.setAttribute("AIT_LINES", orderDAO.getLines(headerDTO.getTransId()));
                forwardURL = SpareURLDefine.DB_VIEW_PAGE;
            } else if (action.equals(SpareWebAction.SAVE_ACTION)) {   //暂存操作(保存操作)
                req2DTO.setDTOClassName(AmsItemAllocateDDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemAllocateHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                boolean operateResult = orderDAO.saveOrder(lineSet, flowDTO);
                message = orderDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    forwardURL = "/servlet/com.sino.ams.spare.servlet.BjdbServlet?act=" + SpareWebAction.DETAIL_ACTION + "&transId=" + dto.getTransId();
                } else {
                    req.setAttribute("AIT_HEADER", dto);
                    forwardURL = SpareURLDefine.DB_CREATE_PAGE;
                }
            } else if (action.equals(SpareWebAction.SUBMIT_ACTION)) {
                req2DTO.setDTOClassName(AmsItemAllocateDDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                operateResu = orderDAO.submitOrder(lineSet, dto);
                if (operateResu) {
                    showMsg = "保存数据成功";
                } else {
                    showMsg = "保存数据失败";
                }
            } else if (action.equals("approve")) {//审批打开页面
                orderDAO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
                orderDAO.setCalPattern(DateConstant.LINE_PATTERN);
                AmsItemAllocateHDTO headerDTO = (AmsItemAllocateHDTO) orderDAO.getDataByPrimaryKey();
                if (dto == null) {
                    headerDTO = new AmsItemAllocateHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                String fromOrg = optProducer.getAllOU(headerDTO.getFromOrganizationId(), false);
                req.setAttribute(WebAttrConstant.CITY_OPTION, fromOrg);
                String toOrg = optProducer.getAllOU(headerDTO.getToOrganizationId(), false);
                req.setAttribute(WebAttrConstant.OU_OPTION, toOrg);
                req.setAttribute("AIT_HEADER", headerDTO);
                req.setAttribute("AIT_LINES", orderDAO.getLines(headerDTO.getTransId()));
                headerDTO.setAttribute1(dto.getSf_task_attribute1());
                forwardURL = SpareURLDefine.DB_APPROVE_PAGE;
            } else if (action.equals(SpareWebAction.APPROVE_ACTION)) {
                req2DTO.setDTOClassName(AmsItemAllocateDDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemAllocateHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                operateResu = orderDAO.approveOrder(lineSet, dto);
                if (operateResu) {
                    showMsg = "保存数据成功";
                } else {
                    showMsg = "保存数据失败";
                }
            } else if (action.equals(SpareWebAction.REJECT_ACTION)) {
                req2DTO.setDTOClassName(AmsItemAllocateDDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
                operateResu = orderDAO.reject(dto, flowDTO, lineSet);
                message = orderDAO.getMessage();
                forwardURL = "/servlet/com.sino.ams.spare.servlet.BjdbServlet?act=" + SpareWebAction.VIEW_ACTION + "&transId=" + dto.getTransId();
            } else if (action.equals(SpareWebAction.CANCEL_ACTION)) {    //调拨暂存撤销
                req2DTO.setDTOClassName(AmsItemAllocateDDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemAllocateHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                boolean operateResult = orderDAO.cancelOrder(lineSet, flowDTO);
                message = orderDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    forwardURL = "/servlet/com.sino.ams.spare.servlet.BjdbServlet?act=" + SpareWebAction.VIEW_ACTION + "&transId=" + dto.getTransId();
                } else {
                    req.setAttribute("AIT_HEADER", dto);
                    forwardURL = SpareURLDefine.DB_CREATE_PAGE;
                }

            } else if (action.equals("print")) {
//                orderDAO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
//                AmsItemAllocateHDTO headerDTO = (AmsItemAllocateHDTO) orderDAO.getDataByPrimaryKey();
//                if (headerDTO == null) {
//                    headerDTO = new AmsItemAllocateHDTO();
//                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
//                    message.setIsError(true);
//                }
//                req.setAttribute("AIT_HEADER", headerDTO);
//                orderDAO.setCalPattern(DateConstant.LINE_PATTERN);
//                req.setAttribute("AIT_LINES", orderDAO.getPrintLines(headerDTO.getTransId()));
//                forwardURL = SpareURLDefine.DB_PRINT_PAGE;

                AmsItemAllocateHDTO dtoParameter3 = new AmsItemAllocateHDTO();
				dtoParameter3.setTransId(req.getParameter("transId"));
				BjDbDAO itemTransHDAO = new BjDbDAO(user, dtoParameter3, conn);

				itemTransHDAO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
				itemTransHDAO.setCalPattern(DateConstant.LINE_PATTERN);
				AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemAllocateHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", amsItemTransH);
				//查询行信息
				req.setAttribute("AIT_LINES", itemTransHDAO.getLines(amsItemTransH.getTransId()));
				forwardURL = "/spare/print/spareDBPrint.jsp";
            } else if (action.equals(SpareWebAction.EXPORT_ACTION)) {
                String transId = req.getParameter("transId");
                File file = orderDAO.exportFile(transId);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if (action.equals("CHECK_KYL")) {
                String barcode = StrUtil.nullToString(req.getParameter("barcode"));
                String fromObjectNo = StrUtil.nullToString(req.getParameter("fromObjectNo"));
                String qtyObj = StrUtil.nullToString(req.getParameter("qtyObj"));
                String onhandQty = orderDAO.checkKYL(barcode, fromObjectNo);
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
        } catch (ContainerException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataTransException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
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
        } catch (SQLModelException e) {
            e.printStackTrace();
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
