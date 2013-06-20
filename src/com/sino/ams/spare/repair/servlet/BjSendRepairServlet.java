package com.sino.ams.spare.repair.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

import com.sino.ams.bean.OrderNumGenerator;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.repair.dao.BjSendRepairDAO;
import com.sino.ams.spare.repair.dto.AmsCustomerInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.bean.ProcedureUtil;
import com.sino.ams.bean.OptionProducer;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-12
 * Time: 9:26:46
 * Function:备件送修（）
 */
public class BjSendRepairServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = StrUtil.nullToString(req.getParameter("act"));
        String flowSaveType = StrUtil.nullToString(req.getParameter("flowSaveType"));
        String procName = StrUtil.nullToString(req.getParameter("procName"));
        procName = "备件送修流程";
        Connection conn = null;
        try {
            conn = getDBConnection(req);
            FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
            String sectionRight = StrUtil.nullToString(req.getParameter("sectionRight"));
            boolean isFirstNode = sectionRight.equals("NEW") || sectionRight.equals("");
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
            AmsItemTransHDTO dto = (AmsItemTransHDTO) req2DTO.getDTO(req);
            flowDTO.setApplyId(dto.getTransId());
            boolean canApprove = ProcedureUtil.canProcess(userAccount, flowDTO, conn);
            req.setAttribute("CAN_APPROVE", String.valueOf(canApprove));

            boolean isNew = StrUtil.isEmpty(dto.getTransId());
            BjSendRepairDAO dao = new BjSendRepairDAO(userAccount, dto, conn);
            if (flowSaveType.equals("")) {//非流程提交
                if (action.equals("print")) {
                    dao.setDTOClassName(AmsItemTransHDTO.class.getName());
                    AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) dao.getPrintH(dto.getTransId());
                    if (orderHeader == null) {
                        orderHeader = new AmsItemTransHDTO();
                        message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                        message.setIsError(true);
                    }
                    req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, orderHeader);
                    req.setAttribute(WebAttrConstant.BARCODE_PRINT_DTO,dao.getVendorInfo(orderHeader.getCompany()));
                    req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, dao.getLines(orderHeader.getTransId()));//原来是：dao.getDetails(orderHeader.getTransId())
                    AmsCustomerInfoDTO dto1 = (AmsCustomerInfoDTO) dao.getByOU();
                    req.setAttribute("CUSTOMER_INFO", dto1);
                    forwardURL = "/spare/repair/bjSendRepairPrint.jsp";
                } else if (action.equals("LOOK")) {
                        dao.setDTOClassName(AmsItemTransHDTO.class.getName());
                        AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) dao.getDataByPrimaryKey();
                        if (orderHeader == null) {
                            orderHeader = new AmsItemTransHDTO();
                            message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                            message.setIsError(true);
                        }
                        req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, orderHeader);
                        req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, dao.getLines(orderHeader.getTransId()));
                        forwardURL = "/spare/bjSendRepairLook.jsp";
                } else {
                    action = isNew ? WebActionConstant.NEW_ACTION : WebActionConstant.DETAIL_ACTION;
                    if (action.equals(WebActionConstant.NEW_ACTION)) {
                        OptionProducer  optProducer = new OptionProducer(userAccount,conn);
                        String vendorOption = optProducer.getSpareVendorOption(dto.getVendorId());
                        req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
                        String fromObjectOption = optProducer.getSpareFromObjectOption(dto.getVendorId());
                        req.setAttribute(WebAttrConstant.SPARE_FROM_OBJECT_OPTION, fromObjectOption);
                        AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) req.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
                        if (orderHeader == null) {
                            orderHeader = dto;
                            orderHeader.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
                            orderHeader.setCreatedBy(userAccount.getUserId());
                            orderHeader.setCreatedUser(userAccount.getUsername());
                            orderHeader.setTransStatus(DictConstant.IN_PROCESS);
                            orderHeader.setTransStatusName("未完成");
                            orderHeader.setCreationDate(CalendarUtil.getCurrDate());
                            orderHeader.setTransType(DictConstant.BJSX);
//                            orderHeader.setFromObjectNo(dao.getObjectN0());
//                            orderHeader.setFromPRJObjectNo(dao.getPRJObjectN0());
                        }
                        req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, orderHeader);
                        forwardURL = "/spare/repair/bjSendRepair.jsp?flowSaveType=" + DictConstant.FLOW_COMPLETE;
                    } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                        dao.setDTOClassName(AmsItemTransHDTO.class.getName());
                        AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) dao.getDataByPrimaryKey();
                        if (orderHeader == null) {
                            orderHeader = new AmsItemTransHDTO();
                            message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                            message.setIsError(true);
                        }
                        OptionProducer  optProducer = new OptionProducer(userAccount,conn);
                        String vendorOption = optProducer.getSpareVendorOption(orderHeader.getVendorId());
                        req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
                        String fromObjectOption = optProducer.getSpareFromObjectOption(orderHeader.getFromObjectNo());
                        req.setAttribute(WebAttrConstant.SPARE_FROM_OBJECT_OPTION, fromObjectOption);
                        req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, orderHeader);
                        req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, dao.getLines(orderHeader.getTransId()));
                        forwardURL = "/spare/repair/bjSendRepair.jsp";
                    }
                }
            } else {//流程提交
                if (flowSaveType.equals(DictConstant.FLOW_SAVE)) {//流程暂存，放入在办箱
                    if (isNew) {
                        isFirstNode = true;
                    }
                    if (isFirstNode) {
                        if (isNew) {
                            OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), DictConstant.BJSX);
                            dto.setTransNo(ong.getOrderNum());
                            SeqProducer seq = new SeqProducer(conn);
//                            String transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                            String transId = seq.getGUID();
                            dto.setTransId(transId);
                            dao.setDTOParameter(dto);
                        }
                        req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
                        req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
                        DTOSet lineSet = req2DTO.getDTOSet(req);
                        boolean operateResult = dao.saveBillData(lineSet, isNew);
                        message = dao.getMessage();
                        message.setIsError(!operateResult);

                        flowDTO.setApplyId(dto.getTransId());
                        flowDTO.setApplyNo(dto.getTransNo());
                        flowDTO.setSessionUserId(userAccount.getUserId());
                        flowDTO.setSessionUserName(userAccount.getUsername());
                        FlowAction fa = new FlowAction(conn);
                        fa.setDto(flowDTO);

                        if (isNew) {
                            fa.add2Flow(procName);
                        }
                        forwardURL = "/servlet/com.sino.ams.spare.repair.servlet.BjSendRepairServlet"
                                     + "?act=" + WebActionConstant.DETAIL_ACTION
                                     + "&transId=" + dto.getTransId();
                    }
                } else if (flowSaveType.equals(DictConstant.FLOW_COMPLETE)) {//流程完成
                    if (isNew) {
                        OrderNumGenerator ong = new OrderNumGenerator(conn, userAccount.getCompanyCode(), DictConstant.BJSX);
                        dto.setTransNo(ong.getOrderNum());
                        SeqProducer seq = new SeqProducer(conn);
//                        String transId = seq.getStrNextSeq("AMS_ITEM_TRANS_H_S");
                        String transId = seq.getGUID();
                        dto.setTransId(transId);
                        dao.setDTOParameter(dto);
                    }
                    req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
                    req2DTO.setIgnoreFields(AmsItemTransHDTO.class);

                    DTOSet lineSet = req2DTO.getDTOSet(req);
                    dao.saveBillData(lineSet, isNew);
                    
                    if(sectionRight.equals("NEW")||sectionRight.equals("")){
                        dao.updateResQty(dto.getFromObjectNo(),lineSet,true);
                    }
                    message = dao.getMessage();

                    flowDTO.setApplyId(dto.getTransId());
                    flowDTO.setApplyNo(dto.getTransNo());
                    flowDTO.setSessionUserId(userAccount.getUserId());
                    flowDTO.setSessionUserName(userAccount.getUsername());
                    FlowAction fa = new FlowAction(conn);
                    fa.setDto(flowDTO);
                    boolean isFlowToEnd = fa.isFlowToEnd();
                    String flowCode = fa.getFlowCode();
                    if (flowCode.equals("4")) {
                        dao.completeTrans(dto.getTransId(), DictConstant.SCANING);
                    } else if (flowCode.equals("6")) {
                        dao.completeTrans(dto.getTransId(), DictConstant.IN_PROCESS);
                    } else if (flowCode.equals("3")) {
                        dao.updateResQty2(dto.getFromObjectNo(),lineSet,false);//退回保留量减少
                    }
                    if (isFlowToEnd) {//流程结束，但不是撤销
                        if (flowCode.equals("7")) {
//                            dao.updateStorage(dto.getTransId(), dto.getStoreType());
                            dao.completeTrans(dto.getTransId(), DictConstant.CANCELED);
                        } else {
                            dao.updateResQty(dto.getFromObjectNo(),lineSet,false);
//                            dao.updateSXQty(dto.getStoreType(),lineSet,false);
                            dao.updateSX(dto.getFromObjectNo(), lineSet);
                            dao.completeTrans(dto.getTransId(), DictConstant.COMPLETED);
                        }
                    }
                    fa.flow();
//                    forwardURL = "/servlet/com.sino.ams.spare.repair.servlet.BjSendRepairServlet"
//                                 + "?flowSaveType="
//                                 + "&act=" + WebActionConstant.DETAIL_ACTION
//                                 + "&transId=" + dto.getTransId();
                    forwardURL = "/servlet/com.sino.ams.spare.repair.servlet.BjSendRepairServlet"
                                 + "?flowSaveType="
                                 + "&act=LOOK"
                                 + "&transId=" + dto.getTransId();
                }
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
        } catch (SQLException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (CalendarException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException e) {
            e.printStackTrace();
        } catch (ContainerException e) {
            e.printStackTrace();
        } catch (SQLModelException ex) {
			ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
//            if(!forwardURL.equals("")){
//                ServletForwarder forwarder = new ServletForwarder(req, res);
//                forwarder.forwardView(forwardURL);
//            }
            if (flowSaveType.equals(DictConstant.FLOW_COMPLETE)) {
                res.setContentType("text/html;charset=GBK");
                PrintWriter out = res.getWriter();
                out.print("<script language=\"javascript\">\n");
                out.println("window.close();");
                out.println("window.top.opener.location.reload();");
                out.print("</script>");
            } else {
                if (!forwardURL.equals("")) {
                    ServletForwarder forwarder = new ServletForwarder(req, res);
                    forwarder.forwardView(forwardURL);
                }
            }
        }
    }
}
