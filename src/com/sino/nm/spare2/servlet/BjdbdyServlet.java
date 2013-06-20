package com.sino.nm.spare2.servlet;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.nm.spare2.dao.BjswjyckDAO;
import com.sino.nm.spare2.dto.AmsItemAllocateHDTO;
import com.sino.nm.spare2.model.BjdbdyModel;
import com.sino.nm.spare2.model.BjswjyckModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.message.Message;
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

/**
 * <p>Title: SinoEAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007 - 2008</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 * @author 何睿
 * @version 0.1
 *          Date: 2008-7-30
 */
public class BjdbdyServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = StrUtil.nullToString(req.getParameter("act"));
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            AmsItemAllocateHDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
            dtoParameter = (AmsItemAllocateHDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            OptionProducer optProducer = new OptionProducer(user, conn);
            if (action.equals("")) {
                String orderStatus = optProducer.getDictOption("SPARE_ORDER_STATUS", dtoParameter.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = "/nm/spare2/bjdbdy.jsp";
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new BjdbdyModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                String orderStatus = optProducer.getDictOption("SPARE_ORDER_STATUS", dtoParameter.getTransStatus());
                req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
                String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
                req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
                forwardURL = "/nm/spare2/bjdbdy.jsp";
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                BjswjyckDAO itemTransHDAO = new BjswjyckDAO(user, dtoParameter, conn);
                itemTransHDAO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
                AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) itemTransHDAO.getDataByPrimaryKey();
                if (amsItemTransH == null) {
                    amsItemTransH = new AmsItemAllocateHDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute("AIT_HEADER", amsItemTransH);
                String freightDate = req.getParameter("freightDate");
                WebPageView wpv = new WebPageView(req, conn);
                wpv.setPrintProp(true);
                BaseSQLProducer sqlProducer = new BjswjyckModel(user, dtoParameter);
                wpv.produceWebData(sqlProducer.getDataByForeignKeyModel(dtoParameter.getTransId()));

                forwardURL = "/nm/spare2/bjdbdyDetail.jsp";
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                forwardURL = MessageConstant.MSG_PRC_SERVLET;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DTOException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.DTO_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            message.setNeedBack(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}


        