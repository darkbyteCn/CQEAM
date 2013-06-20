package com.sino.ams.spare.allot.servlet;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.allot.dao.AmsBjsAllotouDAO;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-7
 * Time: 23:27:45
 */
public class AmsBjsAllotouServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "/spare/allot/bjAllotou.jsp";
        Message message = null;
        String showMsg = "";
        boolean firstSave = false;
        String action = req.getParameter("act");
        String itemCode = req.getParameter("itemCode");
        String detailId = req.getParameter("detailId");
        String transId = req.getParameter("transId");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        AmsBjsAllotHDTO dtoParameter = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);

            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsBjsAllotHDTO.class.getName());
            dtoParameter = (AmsBjsAllotHDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            AmsBjsAllotouDAO dao = new AmsBjsAllotouDAO(user, dtoParameter, conn);
            dao.setServletConfig(getServletConfig(req));
            /* if(action.equals("")){
               forwardURL="/spare/allot/bjAllotou.jsp" ;
             }
           else*/
            if (action.equals(WebActionConstant.QUERY_ACTION)) {
                dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());

                AmsBjsAllotHDTO amsInstrumentInfo = (AmsBjsAllotHDTO) req.getAttribute(WebAttrConstant.ALLOT_H_DTO);
                if (amsInstrumentInfo == null) {
                    amsInstrumentInfo = dtoParameter;
                }
                RowSet row = dao.produceWebData(itemCode, transId);
                req.setAttribute("OU_ITEM_COUNT", row);
                req.setAttribute(WebAttrConstant.ALLOT_H_DTO, amsInstrumentInfo);
                forwardURL = "/spare/allot/bjAllotou.jsp";
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                r2.setIgnoreFields(AmsBjsAllotHDTO.class);

                DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                String[] holdCount = req.getParameterValues("holdCount");
                String[] organizationId = req.getParameterValues("organizationId");
                boolean operateResult = dao.saveData(lineSet, organizationId, holdCount, itemCode, flowDTO);
                message = dao.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    if (transId.equals("")) {
                        firstSave = true;
//                        forwardURL = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotServlet?act=" + WebActionConstant.DETAIL_ACTION + "&transId=" + dtoParameter.getTransId();
                    }
                    forwardURL = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotouServlet?act=" + WebActionConstant.QUERY_ACTION + "&transId=" + dtoParameter.getTransId() + "&itemCode=" + itemCode;
                    showMsg = "保存成功!";
                } else {
//                    forwardURL = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotServlet?act=" + "";
                }
            } else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                r2.setIgnoreFields(AmsBjsAllotHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity("9");
                String[] holdCount = req.getParameterValues("holdCount");
                String[] organizationId = req.getParameterValues("organizationId");
                String[] barcode = req.getParameterValues("barcode");
                String sectionRight = req.getParameter("sectionRight");
                boolean operateResult = dao.submitData(dtoParameter, lineSet, organizationId, holdCount, itemCode, flowDTO);
                message = dao.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    showMsg = "单据提交成功，单据号为：" + dtoParameter.getTransNo();
                } else {
//                    forwardURL = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotServlet?act=" + "";
                }
            }else if (action.equals(WebActionConstant.APPROVE_ACTION)) {   //审批流程 
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                r2.setIgnoreFields(AmsBjsAllotHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
                String[] holdCount = req.getParameterValues("holdCount");
                String[] organizationId = req.getParameterValues("organizationId");
                String[] barcode = req.getParameterValues("barcode");
                String sectionRight = req.getParameter("sectionRight");
                boolean operateResult = dao.submit(dtoParameter, lineSet, organizationId, holdCount, itemCode, req, barcode, sectionRight, detailId, flowDTO);
                message = dao.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    req.setAttribute("SUCCESS_MSG", "单据提交成功，单据号为：" + dtoParameter.getTransNo());
                    showMsg = "单据已通过审批!";
                    forwardURL = "";

                } else {
//                    forwardURL = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotServlet?act=" + "";
                }
            } else if (action.equals(WebActionConstant.REJECT_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                r2.setIgnoreFields(AmsBjsAllotHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
                dao.reject(dtoParameter, flowDTO);
                showMsg = "备件分配单已退回!";

            } else if (action.equals(WebActionConstant.CANCEL_ACTION)) {
                boolean operateResult = dao.cancelData(transId);
                if (operateResult) {
                    showMsg = "撤销成功！";
                } else {
                    firstSave = true;
                    forwardURL = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotServlet?act=" + WebActionConstant.DETAIL_ACTION + "&transId=" + dtoParameter.getTransId();

                }

            } else if (action.equals("barcode")) {
                String itemCode1 = req.getParameter("itemCode");
                RowSet row = dao.produceBarcode(itemCode1);
                req.setAttribute("ALLOTBARCODE", row);
                forwardURL = "/spare/allot/bjAllotBarcode.jsp";
            } else if (action.equals("allot")) {
                String[] barcode = req.getParameterValues("subCheck");
                String itemCode1 = req.getParameter("itemCode");
                String detailIds = req.getParameter("detailId");
                String transIds = req.getParameter("transId");
                for (int i = 0; i < barcode.length; i++) {
                    dao.insertDetail(transIds, detailIds, itemCode1, barcode[i]);
                }
                showMsg = "条码分配成功!";
                forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
            }
        } catch (PoolPassivateException e) {
            Logger.logError(e);
        } catch (QueryException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException e) {
            e.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder sf = new ServletForwarder(req, res);

            if (showMsg.equals("")) {
                sf.forwardView(forwardURL);
            } else {
                sf.forwardOpenerView(forwardURL, showMsg);
//                    sf.closeParent(showMsg);
            }
        }
    }
}