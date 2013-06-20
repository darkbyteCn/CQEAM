package com.sino.nm.ams.others.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.util.CalendarUtil;
import com.sino.base.dto.Request2DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.db.conn.DBManager;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.nm.spare2.dto.AmsItemTransHDTO;
import com.sino.nm.spare2.dto.AmsItemTransLDTO;
import com.sino.nm.spare2.dao.AmsItemTransHDAO;
import com.sino.nm.spare2.dao.AmsItemTransLDAO;
import com.sino.nm.spare2.model.AmsItemTransHModel;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.nm.ams.others.dao.DeptItemBarcodeDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2009-3-10
 * Time: 15:01:29
 * To change this template use File | Settings | File Templates.
 */
public class DeptItemBarcodeInServlet extends BaseServlet {
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
            conn = getDBConnection(req);
            DeptItemBarcodeDAO itemTransHDAO = new DeptItemBarcodeDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            if (action.equals("")) {
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = "/nm/others/deptBarcodeQuery.jsp?transType="+ DictConstant.TMRK;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsItemTransHModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = "/nm/others/deptBarcodeQuery.jsp?transType="+ DictConstant.TMRK;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) req.getAttribute("AIT_HEADER");
                if (amsItemTransH == null) {
                    amsItemTransH = dtoParameter;
                }
                amsItemTransH.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
//                amsItemTransH.setTransType(DictConstant.TMRK);
                amsItemTransH.setCreatedBy(user.getUserId());
                amsItemTransH.setCreatedUser(user.getUsername());
                amsItemTransH.setCreationDate(CalendarUtil.getCurrDate());
                amsItemTransH.setTransStatus(DictConstant.SAVE_TEMP);
                amsItemTransH.setTransStatusName("未完成");
                amsItemTransH.setFromOrganizationId(user.getOrganizationId());
                amsItemTransH.setToOrganizationId(user.getOrganizationId());
                req.setAttribute("AIT_HEADER", amsItemTransH);
                forwardURL = "/nm/others/deptBarcodeIn.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
                AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (amsItemTransH == null) {
                    amsItemTransH = new AmsItemTransHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AIT_HEADER", amsItemTransH);
                //查询行信息
                AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
                req.setAttribute("AIT_LINES", ldao.getLines(amsItemTransH.getTransId()));
                forwardURL = "/nm/others/deptBarcodeIn.jsp";
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransLDTO.class.getName());
                r2.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);
                boolean operateResult = itemTransHDAO.saveOrder(lineSet,null);
                message = itemTransHDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    forwardURL = "/servlet/com.sino.nm.ams.others.servlet.DeptItemBarcodeInServlet?act=" + WebActionConstant.DETAIL_ACTION
                            + "&transId=" + dtoParameter.getTransId();
                } else {
                    req.setAttribute("AIT_HEADER", dtoParameter);
                    forwardURL = "/nm/others/deptBarcodeIn.jsp";
                }
            } else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransLDTO.class.getName());
                r2.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);

                boolean operateResult = itemTransHDAO.submitOrder(lineSet, null);
                message = itemTransHDAO.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    forwardURL = "/servlet/com.sino.nm.ams.others.servlet.DeptItemBarcodeInServlet?act=" + WebActionConstant.QUERY_ACTION;
                    showMsg = "单据" + dtoParameter.getTransNo() + "已生成!";
                } else {
                    forwardURL = "/servlet/com.sino.nm.ams.others.servlet.DeptItemBarcodeInServlet?act=" + WebActionConstant.DETAIL_ACTION
                            + "&transId=" + dtoParameter.getTransId();
                }
            } else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                itemTransHDAO.deleteData();
                message = itemTransHDAO.getMessage();
                forwardURL = "可再次执行该Servlet的QUERY_ACTION，请根据实际情况确定";
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
            /** @todo Handle this exception */
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