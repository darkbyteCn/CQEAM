package com.sino.nm.spare2.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.nm.spare2.dao.AmsItemTransHDAO;
import com.sino.nm.spare2.dao.AmsItemTransLDAO;
import com.sino.nm.spare2.dao.BjslApproveDAO;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.bean.OptionProducer;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2007-10-23
 */
public class BjslApproveServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String showMsg = "";
        String action = req.getParameter("act");
        String sectionRight = req.getParameter("sectionRight");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsItemTransHDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
            dtoParameter = (AmsItemTransHDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
             String headerId = req.getParameter("sf_appDataID");
           dtoParameter.setTransId(headerId);
            AmsItemTransHDAO itemTransHDAO = new AmsItemTransHDAO(user, dtoParameter, conn);
            BjslApproveDAO approveDAO = new BjslApproveDAO(user, dtoParameter, conn);
            OptionProducer op=new OptionProducer(user,conn);
            String spareFac="";
            String feedbackInfo="";

            FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
            flowDTO.setSessionUserId(user.getUserId());
            flowDTO.setSessionUserName(user.getUsername());
            if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (amsItemTransH == null) {
                    amsItemTransH = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                 spareFac=op.getDictOption3("SERVICE_SPARE",amsItemTransH.getSpareManufacturer(),true) ;
                    amsItemTransH.setSpareManufacturerOpt(spareFac);
                   feedbackInfo=op.getDictOption3("FEEDBACK_INFO",amsItemTransH.getFeedbackType(),true) ;
                    amsItemTransH.setFeedbackTypeOpt(feedbackInfo);
                req.setAttribute("BJSL_HEADER", amsItemTransH);
                //查询行信息
                AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                req.setAttribute("BJSL_LINES", ldao.getLines(amsItemTransH.getTransId()));
//                if (sectionRight.equals("ALLOCATE")) {
//
//                    forwardURL = "/nm/spare2/bjslfp.jsp";
//                }else if(sectionRight.equals("FEEDBACK")){
//
//                  forwardURL = "/nm/spare2/bjslfk.jsp";
//                } else {
//                    forwardURL = "/nm/spare2/bjslApprove.jsp";
//                }
                 forwardURL = "/nm/spare2/bjslfp.jsp";
            } else if (action.equals(WebActionConstant.APPROVE_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransLDTO.class.getName());
                List ignorList = new ArrayList();
                ignorList.add("transId");
                ignorList.add("quantity");
                r2.setIgnoreFields(ignorList);
                DTOSet lineSet = r2.getDTOSet(req);

//                flowDTO.setApplyId(dtoParameter.getTransId());
//                flowDTO.setApplyNo(dtoParameter.getTransNo());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_AGREE);
                boolean operateResult = approveDAO.approveOrder(lineSet, flowDTO);
                message = approveDAO.getMessage();
                message.setIsError(!operateResult);
                if (!operateResult) {
                    forwardURL = "/servlet/com.sino.ams.spare2.servlet.BjslApproveServlet?act=" + WebActionConstant.DETAIL_ACTION
                            + "&transId=" + dtoParameter.getTransId();
                } else {
                    showMsg = "备件申领单已通过审批!";
                }
            } else if (action.equals(WebActionConstant.REJECT_ACTION)) {
                flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
                String content = StrUtil.nullToString(req.getParameter("approveOpinion"));
                flowDTO.setApproveContent(content);
                approveDAO.reject(flowDTO);
                showMsg = "备件申领单已退回!";
            } else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
                //将单据状态改为已完成,并生成调拨单和分配单
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                boolean operateResult = approveDAO.submitOrder(flowDTO);
                message = approveDAO.getMessage();
                message.setIsError(!operateResult);
                if (!operateResult) {
                    forwardURL = "/servlet/com.sino.ams.spare2.servlet.BjslApproveServlet?act=" + WebActionConstant.DETAIL_ACTION
                            + "&transId=" + dtoParameter.getTransId();
                } else {
                    showMsg = "备件调拨单已生成!";
                }
                /*boolean operateResult = itemTransHDAO.deleteData();
                message = itemTransHDAO.getMessage();
                message.setIsError(!operateResult);
                forwardURL = "可再次执行该Servlet的QUERY_ACTION，请根据实际情况确定";*/
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
