package com.sino.ams.spare.reject.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.reject.dao.AmsBjRejectDAO;
import com.sino.ams.spare.reject.model.AmsBjRejectModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-13
 * Time: 22:58:54
 */
public class AmsBjRejectServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String showMsg = "";
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsItemTransHDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
            dtoParameter = (AmsItemTransHDTO) req2DTO.getDTO(req);
            String transId = dtoParameter.getTransId();
            conn = getDBConnection(req);
            AmsBjRejectDAO dao = new AmsBjRejectDAO(user, dtoParameter, conn);
            if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsItemTransHDTO headerDto = (AmsItemTransHDTO) req.getAttribute("AIT_HEADER");
                if (headerDto == null) {
                    headerDto = dtoParameter;
                }
                headerDto.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
                headerDto.setCreatedBy(user.getUserId());
                headerDto.setCreatedUser(user.getUsername());
                headerDto.setTransStatusName("未完成");
                headerDto.setCreationDate(CalendarUtil.getCurrDate());
                headerDto.setTransType(DictConstant.BJBF);
                req.setAttribute("AIT_HEADER", headerDto);
                //req.setAttribute("ALL_ITEM", dao.produceWebData());
                forwardURL = "/spare/reject/bjReject.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsBjRejectModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                forwardURL = "/instrument/instrumentBorrow.jsp";

            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransLDTO.class.getName());
                r2.setIgnoreFields(AmsBjsAllotHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());

                boolean operateResult = dao.saveData(lineSet, flowDTO);
                message = dao.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    showMsg = "单据保存成功，单据号为：" + dtoParameter.getTransNo();
                } else {
                    forwardURL = "/servlet/com.sino.ams.spare.reject.servlet.AmsBjRejectServlet?act=";
                    if (transId.equals("")) {
                        forwardURL += WebActionConstant.NEW_ACTION;
                    } else {
                        forwardURL += WebActionConstant.DETAIL_ACTION + "&transId=" + dtoParameter.getTransId();
                    }
                }

            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                dao.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO dto = (AmsItemTransHDTO) dao.getDataByPrimaryKey();
                if (dto == null) {
                    dto = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AIT_HEADER", dto);
                DTOSet ds = dao.getLines(dto.getTransId());
                req.setAttribute("AIT_LINES", ds);
                forwardURL = "/spare/reject/bjReject.jsp";
            } else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransLDTO.class.getName());
                r2.setIgnoreFields(AmsBjsAllotHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity("9");

                boolean operateResult = dao.submitData(lineSet, flowDTO);
                message = dao.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    showMsg = "单据提交成功，单据号为：" + dtoParameter.getTransNo();
                } else {
                    forwardURL = "/servlet/com.sino.ams.spare.reject.servlet.AmsBjRejectServlet?act=";
                    if (transId.equals("")) {
                        forwardURL += WebActionConstant.NEW_ACTION;
                    } else {
                        forwardURL += WebActionConstant.DETAIL_ACTION + "&transId=" + dtoParameter.getTransId();
                    }
                }
            }  //审批流程
            else if (action.equals("approve")) {
                dao.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO dto = (AmsItemTransHDTO) dao.getDataByPrimaryKey();
                if (dto == null) {
                    dto = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AIT_HEADER", dto);
                req.setAttribute("AIT_LINES", dao.getLines(dto.getTransId()));
                forwardURL = "/spare/reject/bjRejectApprove.jsp";

            } else if (action.equals(WebActionConstant.APPROVE_ACTION)) {

                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                boolean success = dao.passApprove(flowDTO);
                if (success) {
                    showMsg = "单据" + dtoParameter.getTransNo() + "已通过审批!";
                } else {
                    forwardURL = "/servlet/com.sino.ams.spare.reject.servlet.AmsBjRejectServlet?act=approve&transId="
                            + dtoParameter.getTransId();
                }
            } else if (action.equals(WebActionConstant.REJECT_ACTION)) {
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
                String content = StrUtil.nullToString(req.getParameter("approveOpinion"));
                flowDTO.setApproveContent(content);
                dao.reject(flowDTO);
                showMsg = "备件报废单已退回!";
//                forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
            } else if (action.equals(WebActionConstant.CANCEL_ACTION)) {
                boolean operateResult = dao.cancelData(transId);
                if (operateResult) {
                    showMsg = "撤销成功！";
                } else {
                    forwardURL = "/servlet/com.sino.ams.spare.reject.servlet.AmsBjRejectServlet?act=" + WebActionConstant.DETAIL_ACTION + "&transId=" + dtoParameter.getTransId();

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
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            if (showMsg.equals("")) {
                forwarder.forwardView(forwardURL);
            } else {
                forwarder.forwardOpenerView(forwardURL, showMsg);
            }
        }
    }
}