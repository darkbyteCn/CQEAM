package com.sino.ams.spare.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.spare.model.SpareDBCancelModel;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.dao.SpareDBCancelDAO;
import com.sino.ams.spare.dto.AmsItemAllocateHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareDBCancel extends BaseServlet {
     public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String showMsg = "";
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsItemAllocateHDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
            dtoParameter = (AmsItemAllocateHDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            SpareDBCancelDAO itemTransHDAO = new SpareDBCancelDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            if (action.equals("")) {
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = "/spare/spareDBCancel.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new SpareDBCancelModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = "/spare/spareDBCancel.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                itemTransHDAO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
                AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (amsItemTransH == null) {
                    amsItemTransH = new AmsItemAllocateHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AIT_HEADER", amsItemTransH);
                //查询行信息
                req.setAttribute("AIT_LINES", itemTransHDAO.getLines2(amsItemTransH.getTransId()));
                forwardURL = "/spare/spareDBCancelDetail.jsp";
            } else if (action.equals("OUT")) {     //备件调出确认
                String transId=req.getParameter("transId");
                  itemTransHDAO.updateData(transId);
                itemTransHDAO.saveMessage();
                showMsg="调出确认成功!" ;
                forwardURL="/servlet/com.sino.ams.spare.servlet.SpareDBCancel?act="+WebActionConstant.QUERY_ACTION ;
              } else if (action.equals("CANCEL")) {     //备件调出撤销
                String transId=req.getParameter("transId");
                itemTransHDAO.cancelOrder(transId);
                showMsg="调出撤销成功!" ;
                forwardURL="/servlet/com.sino.ams.spare.servlet.SpareDBCancel?act="+WebActionConstant.QUERY_ACTION ;
             } else if (action.equals("print")) {//调出调入打印
                itemTransHDAO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
                AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (amsItemTransH == null) {
                    amsItemTransH = new AmsItemAllocateHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AIT_HEADER", amsItemTransH);
                //查询行信息
                req.setAttribute("AIT_LINES", itemTransHDAO.getLines2(amsItemTransH.getTransId()));
                forwardURL = "/spare/print/spareOutConfirmPrint.jsp";
            }
            else if (action.equals(WebActionConstant.CANCEL_ACTION)) {
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
        } catch (DataHandleException ex) {
            ex.printLog();
        } catch (SQLException e) {
           Logger.logError(e);
        } catch (ContainerException e) {
           Logger.logError(e);
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
