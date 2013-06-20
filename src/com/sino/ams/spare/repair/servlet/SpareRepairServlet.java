package com.sino.ams.spare.repair.servlet;

import com.sino.ams.bean.ProcedureUtil;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.spare.dto.AmsItemTransDDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.repair.dao.SpareRepairDAO;
import com.sino.ams.spare.repair.dto.AmsCustomerInfoDTO;
import com.sino.ams.spare.constant.SpareURLDefine;
import com.sino.ams.spare.constant.SparePROCConstant;
import com.sino.ams.spare.constant.SpareWebAction;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.log.Logger;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.flow.constant.FlowConstant;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

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
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-12
 * Time: 9:26:46
 * Function:备件送修（新）
 */
public class SpareRepairServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = StrUtil.nullToString(req.getParameter("act"));
        String flowAction = StrUtil.nullToString(req.getParameter("flowAct"));
        List ignorFields = new ArrayList();
        String procName = StrUtil.nullToString(req.getParameter("procName"));
        String taskProp = StrUtil.nullToString(req.getParameter("taskProp"));
        Connection conn = null;
        try {
            conn = getDBConnection(req);
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);

            FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
            String sectionRight = StrUtil.nullToString(req.getParameter("sectionRight"));
            boolean isFirstNode = sectionRight.equals("NEW") || sectionRight.equals("");
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
            AmsItemTransHDTO transHDTO = (AmsItemTransHDTO) req2DTO.getDTO(req);
            flowDTO.setApplyId(transHDTO.getTransId());
            boolean canApprove = ProcedureUtil.canProcess(userAccount, flowDTO, conn);
            req.setAttribute("CAN_APPROVE", String.valueOf(canApprove));

            boolean isNew = StrUtil.isEmpty(transHDTO.getTransId());
            SpareRepairDAO spareRepairDAO = new SpareRepairDAO(userAccount, transHDTO, conn);

            if (action.equals("") || action.equals(SpareWebAction.NEW_ACTION)) {
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) req.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
                if (orderHeader == null) {
                    orderHeader = transHDTO;
                    orderHeader.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
                    orderHeader.setCreatedBy(userAccount.getUserId());
                    orderHeader.setCreatedUser(userAccount.getUsername());
                    orderHeader.setTransStatus(DictConstant.IN_PROCESS);
                    orderHeader.setTransStatusName("未完成");
                    orderHeader.setCreationDate(CalendarUtil.getCurrDate());
                    orderHeader.setTransType(DictConstant.BJSX);
                    orderHeader.setFromObjectNo(spareRepairDAO.getObjectN0());
                }
                req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, orderHeader);
                forwardURL = SpareURLDefine.REPAIR_PAGE;
            } else if (action.equals(SpareWebAction.DETAIL_ACTION)) {
                spareRepairDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) spareRepairDAO.getDataByPrimaryKey();
                if (orderHeader == null) {
                    orderHeader = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, orderHeader);
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, spareRepairDAO.getDetails(orderHeader.getTransId()));
                forwardURL = SpareURLDefine.REPAIR_PAGE;
            } else if (action.equals(SpareWebAction.SAVE_ACTION)) {
                req2DTO.setDTOClassName(AmsItemTransDDTO.class.getName());
                ignorFields.add("transId");
                ignorFields.add("act");
                ignorFields.add("itemSpec");
                req2DTO.setIgnoreFields(ignorFields);
                DTOSet ds = req2DTO.getDTOSet(req);
                flowDTO.setProcName(SparePROCConstant.REPAIRE_PROC);
                spareRepairDAO.saveData(ds, flowDTO, flowAction, isFirstNode);
                forwardURL = "/servlet/com.sino.ams.spare.repair.servlet.SpareRepairServlet";
                if (flowAction.equals(DictConstant.FLOW_SAVE)) {
                    forwardURL = "/servlet/com.sino.ams.spare.repair.servlet.SpareRepairServlet" + "?act=" + SpareWebAction.DETAIL_ACTION + "&transId=" + transHDTO.getTransId() + "&taskProp=" + taskProp;
                } else if (flowAction.equals(DictConstant.FLOW_BACK)) {
                } else if (flowAction.equals(DictConstant.FLOW_COMPLETE)) {
                    forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
                }

            } else if (action.equals("print")) {
                spareRepairDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) spareRepairDAO.getPrintH(transHDTO.getTransId());
                if (orderHeader == null) {
                    orderHeader = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, orderHeader);
                req.setAttribute(WebAttrConstant.BARCODE_PRINT_DTO, spareRepairDAO.getVendorInfo(orderHeader.getCompany()));
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, spareRepairDAO.getDetails(orderHeader.getTransId()));
                AmsCustomerInfoDTO dto1 = (AmsCustomerInfoDTO) spareRepairDAO.getByOU();
                req.setAttribute("CUSTOMER_INFO", dto1);
                forwardURL = SpareURLDefine.REPAIR_PRINT_PAGE;
            } else if (action.equals("LOOK")) {
                spareRepairDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) spareRepairDAO.getDataByPrimaryKey();
                if (orderHeader == null) {
                    orderHeader = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, orderHeader);
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, spareRepairDAO.getLines(orderHeader.getTransId()));
                forwardURL = SpareURLDefine.REPAIR_LOOK_PAGE;
            } else if (action.equals(SpareWebAction.REJECT_ACTION)) {//退回
                flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(userAccount.getUserId());
                flowDTO.setSessionUserName(userAccount.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
                flowDTO.setApproveContent("退回");
                spareRepairDAO.excuteFlow(flowDTO, DictConstant.FLOW_BACK, false);
                forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
            } else if (action.equals("verify")) {//验证部件号是否存在
                String[] barcodes = req.getParameterValues("barcode");
                String codes = spareRepairDAO.verifyBarcodes(barcodes);
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();

                out.print(codes);
                out.flush();
                out.close();
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
        } catch (ContainerException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.CONTAINER_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException e) {
            e.printLog();
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException e) {
            Logger.logError(e);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!forwardURL.equals("")) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
        }
    }
}