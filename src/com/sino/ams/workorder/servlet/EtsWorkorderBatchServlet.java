package com.sino.ams.workorder.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.EtsWorkorderBatchDAO;
import com.sino.ams.workorder.dto.EtsWorkorderBatchDTO;
import com.sino.ams.workorder.util.WorkOrderUtil;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.utilities.CaseRoutine;
import com.sino.sinoflow.utilities.FlowUtil;


/**
 * <p>Title: EtsWorkorderBatchServlet</p>
 * <p>Description:工单创建建及流转是的处理</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author zhoujs
 * @version 1.0
 */


public class EtsWorkorderBatchServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        boolean operatorResult = false;
        boolean isNew = true;
        boolean isTempSave = true;
        try {
            conn = DBManager.getDBConnection();
            boolean autoCommit = conn.getAutoCommit();
            SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);

            FlowDTO flowDTO = new FlowDTO();
            flowDTO = FlowAction.getDTOFromReq(req);

            WorkOrderUtil workOrderUtil = new WorkOrderUtil();

            boolean isFlowOver = false;
            String sectionRight = req.getParameter("sectionRight");
            String firstTask = StrUtil.nullToString(req.getParameter("sf_task_attribute1"));
//            String workorderNode = getOrderNode(sectionRight);
            String workorderNode = firstTask;

            String flowSaveType = StrUtil.nullToString(req.getParameter("flowSaveType"));
            String sf_appFieldValue = StrUtil.nullToString(req
                    .getParameter("sf_appFieldValue"));
            String to_end = StrUtil.nullToString(req
                    .getParameter("sf_end"));
            String systemid = req.getParameter("sf_appDataID");
            EtsWorkorderBatchDTO workorderBatch = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsWorkorderBatchDTO.class.getName());
            workorderBatch = (EtsWorkorderBatchDTO) req2DTO.getDTO(req);
            if (systemid != null) {
                workorderBatch.setSystemid(systemid);
            }

            isNew = StrUtil.isEmpty(workorderBatch.getSystemid());
            isTempSave = flowSaveType.equals(DictConstant.FLOW_SAVE);
            boolean isFirstNode = false;
            if (isNew) {
                SeqProducer sp = new SeqProducer(conn);
                workorderBatch.setSystemid(sp.getGUID());
                isFirstNode = true;
            } else {
                isFirstNode = workorderNode.equals("from");
            }
            EtsWorkorderBatchDAO etsWorkorderBatchDAO = new EtsWorkorderBatchDAO(userAccount, workorderBatch, conn);


            forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
            if (flowSaveType.equals(DictConstant.FLOW_SAVE)) {//暂存
                conn.setAutoCommit(false);
                if (isFirstNode) {
                    boolean operateResult;
                    flowDTO.setApplyId(workorderBatch.getSystemid());
                    flowDTO.setApplyNo(workorderBatch.getWorkorderBatch());
                    flowDTO.setSessionUserId(userAccount.getUserId());
                    flowDTO.setSessionUserName(userAccount.getUsername());
                    if (isNew) {
                        etsWorkorderBatchDAO.createData();
                    } else {
                        etsWorkorderBatchDAO.updateData();
                    }
                    workorderNode = "PROCESS1";
                    workOrderUtil.saveBatchExtends(conn, workorderBatch);
                    workOrderUtil.saveWorkorderPorcess(conn, workorderBatch.getWorkorderBatch(), workorderNode, isNew, true, false);
//                   流向控制
                    if (isNew && !workorderBatch.getWorkorderType().equals("18")) {
                        etsWorkorderBatchDAO.processFlow(false, sf_appFieldValue, workorderBatch);
                    }
                }
                conn.commit();
                conn.setAutoCommit(autoCommit);
                forwardURL = "/servlet/com.sino.ams.workorder.servlet.OrderEntryServlet";
                forwardURL += "?workorderType=" + workorderBatch.getWorkorderType();
                forwardURL += "&category=BTS";
                forwardURL += "&systemid=" + workorderBatch.getSystemid();
                message = new Message();
                message.setMessageValue("工单保存成功");
            } else if (flowSaveType.equals(DictConstant.FLOW_COMPLETE) || flowSaveType.equals(DictConstant.FLOW_TO)) {//完成、特送

                conn.setAutoCommit(false);
                if (isFirstNode) {
                    if (isNew) {
                        etsWorkorderBatchDAO.createData();
                        flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                    } else {
                        etsWorkorderBatchDAO.updateData();
                    }
                    workOrderUtil.saveBatchExtends(conn, workorderBatch);
                }
                //流程信息
                if (to_end.equals("1")) {
                    isFlowOver = true;
                }
                workorderNode = "PROCESS2";
                if (workorderBatch.getWorkorderType().equals("18")) {
                    isFlowOver = true;
                    workorderNode = "PROCESS3";
                }

                if (isFlowOver) {
                    workorderNode = "PROCESS3";
                    workOrderUtil.saveOrderStatus(conn, workorderBatch.getWorkorderBatch(), workorderNode);
                }

                workOrderUtil.saveWorkorderPorcess(conn, workorderBatch.getWorkorderBatch(), workorderNode, isNew, false, isFlowOver);

                //流向控制
                if (!workorderBatch.getWorkorderType().equals("18")) {
//                    fa.flow();
                    etsWorkorderBatchDAO.processFlow(true, sf_appFieldValue, workorderBatch);
                }
                //  fb.flow2Next("", true);
                conn.commit();
                conn.setAutoCommit(autoCommit);
                operatorResult = true;
                if (workorderBatch.getWorkorderType().equals("18")) {
                    res.setContentType("text/html;charset=GBK");
                    res.getWriter().println("<script language=javascript>window.top.close();</script>");
                    forwardURL = "";
                } else {
                    forwardURL = "/servlet/com.sino.ams.workorder.servlet.OrderEntryServlet";
                    forwardURL += "?workorderType=" + workorderBatch.getWorkorderType();
                    forwardURL += "&category=BTS";
                    forwardURL += "&fromPage=DETAIL_PAGE";
                    forwardURL += "&systemid=" + workorderBatch.getSystemid();
                }
                message=new Message();
                message.setMessageValue("工单提交成功");
            } else if (flowSaveType.equals(DictConstant.FLOW_CANCEL)) {//撤销
                workOrderUtil.saveOrderStatus(conn, workorderBatch.getWorkorderBatch(), DictConstant.WORKORDER_NODE_CANCEL);
                FlowUtil.removeCase(req.getParameter("sf_caseID"),conn);
                operatorResult = true;
            } else if (flowSaveType.equals(DictConstant.FLOW_BACK)) {//退回
                CaseRoutine cr = new CaseRoutine();
                cr.caseBack(req, conn);
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.CONN_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException ex) {
            forwardURL = URLDefineList.ERROR_PAGE;
            ex.printStackTrace();
        } catch (DataHandleException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (ContainerException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();
        } catch (SQLModelException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParseException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (QueryException e) {
            forwardURL = URLDefineList.ERROR_PAGE;
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);

            //根据实际情况修改页面跳转代码。
        }
    }
}
