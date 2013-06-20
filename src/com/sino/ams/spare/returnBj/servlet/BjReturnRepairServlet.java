package com.sino.ams.spare.returnBj.servlet;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.allot.dto.AmsBjsAllotHDTO;
import com.sino.ams.spare.constant.SpareURLDefine;
import com.sino.ams.spare.constant.SpareWebAction;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.returnBj.dao.BjReturnRepairDAO;
import com.sino.ams.spare.returnBj.model.BjReturnRepairModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-11-12
 * Time: 9:33:54
 * Function:备件送修返还
 */
public class BjReturnRepairServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String showMsg = "";
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsBjsAllotHDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsBjsAllotHDTO.class.getName());
            dtoParameter = (AmsBjsAllotHDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            BjReturnRepairDAO dao = new BjReturnRepairDAO(user, dtoParameter, conn);
            OptionProducer optProducer = new OptionProducer(user, conn);
            String vendorOption = optProducer.getSpareVendorOption(dtoParameter.getVendorId());
            req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
//            action = action.equals("") ? WebActionConstant.QUERY_ACTION : action;
            if (action.equals("")) {
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);

                forwardURL = "/spare/return/spareReturnQuery.jsp";
            }
            if (action.equals(SpareWebAction.NEW_ACTION)) {
                AmsBjsAllotHDTO amsInstrumentInfo = (AmsBjsAllotHDTO) req.getAttribute(WebAttrConstant.AMS_ITEMH_REPAIR);
                if (amsInstrumentInfo == null) {
                    amsInstrumentInfo = dtoParameter;
                }
                amsInstrumentInfo.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
                amsInstrumentInfo.setCreatedBy(user.getUserId());
                amsInstrumentInfo.setCreatedUser(user.getUsername());
                amsInstrumentInfo.setTransStatusName("未完成");
                amsInstrumentInfo.setCreationDate(CalendarUtil.getCurrDate());
                amsInstrumentInfo.setTransType(DictConstant.BJFH);   //送修返还
                amsInstrumentInfo.setReason("送修返还");
                req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, amsInstrumentInfo);
                //req.setAttribute("ALL_ITEM", dao.produceWebData());
                forwardURL = "/spare/return/spareReturn.jsp";
            } else if (action.equals(SpareWebAction.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new BjReturnRepairModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
//                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = "/spare/return/spareReturnQuery.jsp";

            } else if (action.equals(SpareWebAction.SAVE_ACTION)) {
                Request2DTO r2 = new Request2DTO();
                r2.setDTOClassName(AmsItemTransLDTO.class.getName());
                r2.setIgnoreFields(AmsBjsAllotHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);

                boolean operateResult = dao.saveData(lineSet);
                message = dao.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
                    showMsg = "单据保存成功，单据号为：" + dtoParameter.getTransNo();
                } else {
                    forwardURL = "/servlet/com.sino.ams.spare.returnBj.servlet.BjReturnRepairServlet?act=" + SpareWebAction.DETAIL_ACTION + "&transId=" + dtoParameter.getTransId();
                }

            } else if (action.equals(SpareWebAction.DETAIL_ACTION)) {
                dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                if (dto == null) {
                    dto = new AmsBjsAllotHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }

//                if (dto.getFromObjectName().indexOf("工程") > -1) {
//                    dto.setToObjectName(dao.getToObjectName(dto.getTransId()));
//                }
                vendorOption = optProducer.getSpareVendorOption(dto.getVendorId());
                req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
                req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                DTOSet ds = dao.getLines(dto.getTransId());
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, ds);
                forwardURL = "/spare/return/spareReturn.jsp";
            } else if (action.equals(SpareWebAction.SUBMIT_ACTION)) {
                Request2DTO r2 = new Request2DTO();

                r2.setDTOClassName(AmsItemTransLDTO.class.getName());

                r2.setIgnoreFields(AmsBjsAllotHDTO.class);
                DTOSet lineSet = r2.getDTOSet(req);

                boolean operateResult = dao.submitData(lineSet);
                message = dao.getMessage();
                message.setIsError(!operateResult);
                if (operateResult) {
//                    showMsg = "单据提交成功，单据号为：" + dtoParameter.getTransNo();
                    forwardURL = "/servlet/com.sino.ams.spare.returnBj.servlet.BjReturnRepairServlet?act=print&transId=" + dtoParameter.getTransId();
                } else {
                    forwardURL = "/servlet/com.sino.ams.spare.returnBj.servlet.BjReturnRepairServlet?act=" + SpareWebAction.DETAIL_ACTION + "&transId=" + dtoParameter.getTransId();
                }
            } else if (action.equals("print")) {   //备件送修返还
                dao.setDTOClassName(AmsBjsAllotHDTO.class.getName());
                AmsBjsAllotHDTO dto = (AmsBjsAllotHDTO) dao.getDataByPrimaryKey();
                if (dto == null) {
                    dto = new AmsBjsAllotHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
//                if (dto.getFromObjectName().indexOf("工程") > -1) {
//                    dto.setToObjectName(dao.getToObjectName(dto.getTransId()));
//                }
                vendorOption = optProducer.getSpareVendorOption(dto.getVendorId());
                req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
                req.setAttribute(WebAttrConstant.AMS_ITEMH_REPAIR, dto);
                DTOSet ds = dao.getLines(dto.getTransId());
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, ds);

                forwardURL = SpareURLDefine.SXFH_PRINT_PAGE;
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
        } catch (ContainerException e) {
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
