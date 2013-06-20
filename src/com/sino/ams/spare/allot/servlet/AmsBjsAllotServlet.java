package com.sino.ams.spare.allot.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.allot.dao.AmsBjsAllotDAO;
import com.sino.ams.spare.allot.dto.AmsBjsAllotDDto;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-6
 * Time: 11:29:13
 */
public class AmsBjsAllotServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        String showMsg = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        int toOrgId = Integer.parseInt(req.getParameter("toOrganizationId"));
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsBjsAllotHDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsBjsAllotHDTO.class.getName());
            dtoParameter = (AmsBjsAllotHDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);

            OptionProducer op = new OptionProducer(user, conn);

            AmsBjsAllotDAO dao = new AmsBjsAllotDAO(user, dtoParameter, conn);
            if (action.equals("")) {
                AmsBjsAllotHDTO amsInstrumentInfo = (AmsBjsAllotHDTO) req.getAttribute(WebAttrConstant.ALLOT_H_DTO);
                if (amsInstrumentInfo == null) {
                    amsInstrumentInfo = dtoParameter;
                }
                amsInstrumentInfo.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
                amsInstrumentInfo.setCreatedBy(user.getUserId());
                amsInstrumentInfo.setCreatedUser(user.getUsername());
                amsInstrumentInfo.setTransStatusName("未完成");
                amsInstrumentInfo.setFromOrganizationId(user.getOrganizationId());
                amsInstrumentInfo.setCreationDate(CalendarUtil.getCurrDate());
                amsInstrumentInfo.setTransType(DictConstant.BJFP);
                req.setAttribute(WebAttrConstant.ALLOT_H_DTO, amsInstrumentInfo);
                req.setAttribute("OU_OPTIONS", op.getAllOrganization(toOrgId));
                forwardURL = "/spare/allot/bjfpCreateNew.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                if (dto == null) {
                    dto = new AmsBjsAllotHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                } else {
                    toOrgId = dto.getToOrganizationId();
                    req.setAttribute("OU_OPTIONS", op.getAllOrganization(toOrgId));
                }
                req.setAttribute(WebAttrConstant.ALLOT_H_DTO, dto);
                req.setAttribute(WebAttrConstant.ALLOT_D_DTO, dao.getLines(dto.getTransId()));
                forwardURL = "/spare/allot/bjfpCreateNew.jsp";
            } else if (action.equals("approve")) {
                dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                if (dto == null) {
                    dto = new AmsBjsAllotHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute(WebAttrConstant.ALLOT_H_DTO, dto);
                req.setAttribute(WebAttrConstant.ALLOT_D_DTO, dao.getLines(dto.getTransId()));
                forwardURL = "/spare/allot/bjfpApproveNew.jsp";
            } else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsBjsAllotDDto.class.getName());
                r2.setIgnoreFields(AmsBjsAllotHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                String detailIds = StrUtil.nullToString(req.getParameter("detailIds"));
                FlowDTO flowDto = FlowAction.getDTOFromReq(req);
                flowDto.setSessionUserId(user.getUserId());
                flowDto.setSessionUserName(user.getUsername());
                boolean operateResult = dao.submitData(lineSet, detailIds, flowDto);

                message = dao.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    showMsg = "单据" + dtoParameter.getTransNo() + "已提交！";
                } else {
                    if (dtoParameter.getTransId().equals("")) {
                        forwardURL = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotServlet?act=";
                    } else {
                        forwardURL = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotServlet?act=" + WebActionConstant.DETAIL_ACTION
                                + "&transId=" + dtoParameter.getTransId();
                    }
                }
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsBjsAllotDDto.class.getName());
                r2.setIgnoreFields(AmsBjsAllotHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                String detailIds = StrUtil.nullToString(req.getParameter("detailIds"));
                FlowDTO flowDto = FlowAction.getDTOFromReq(req);
                flowDto.setSessionUserId(user.getUserId());
                flowDto.setSessionUserName(user.getUsername());
                boolean operateResult = dao.saveData(lineSet, detailIds, flowDto);
                message = dao.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    showMsg = "单据" + dtoParameter.getTransNo() + "已保存！";
                    forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
                } else {
                    if (dtoParameter.getTransId().equals("")) {
                        forwardURL = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotServlet?act=";
                    } else {
                        forwardURL = "/servlet/com.sino.ams.spare.allot.servlet.AmsBjsAllotServlet?act=" + WebActionConstant.DETAIL_ACTION
                                + "&transId=" + dtoParameter.getTransId();
                    }
                }
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException e) {
            Logger.logError(e);
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
