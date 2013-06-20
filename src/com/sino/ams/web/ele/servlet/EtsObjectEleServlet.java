package com.sino.ams.web.ele.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.web.ele.dao.EtsObjectEleDAO;
import com.sino.ams.web.ele.dto.EtsObjectEleDTO;
import com.sino.ams.web.ele.model.EtsObjectEleModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsObjectEleServlet</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsObjectEleServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EtsObjectEleDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EtsObjectEleDTO.class.getName());
            dtoParameter = (EtsObjectEleDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            EtsObjectEleDAO etsObjectEleDAO = new EtsObjectEleDAO(user, dtoParameter, conn);
            OptionProducer op = new OptionProducer(user, conn);
            int company = StrUtil.strToInt(dtoParameter.getCompany());
            String companySelect = op.getAllOrganization(company, true);
            req.setAttribute(WebAttrConstant.OU_OPTION, companySelect);
            if (action.equals("")) {
                forwardURL = URLDefineList.OBJECT_ELE;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new EtsObjectEleModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
                forwardURL = URLDefineList.OBJECT_ELE;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                EtsObjectEleDTO etsObjectEle = (EtsObjectEleDTO) req.getAttribute(WebAttrConstant.OBJECT_ELE_DTO);
                if (etsObjectEle == null) {
                    etsObjectEle = dtoParameter;
                }
                req.setAttribute(WebAttrConstant.OBJECT_ELE_DTO, etsObjectEle);
                forwardURL = URLDefineList.OBJECT_ELE_DETAIL;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                etsObjectEleDAO.setDTOClassName(EtsObjectEleDTO.class.getName());
                EtsObjectEleDTO etsObjectEle = (EtsObjectEleDTO) etsObjectEleDAO.getDataByPrimaryKey();
                if (etsObjectEle == null) {
                    etsObjectEle = new EtsObjectEleDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                req.setAttribute(WebAttrConstant.OBJECT_ELE_DTO, etsObjectEle);
                forwardURL = URLDefineList.OBJECT_ELE_DETAIL;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {
                etsObjectEleDAO.createData();
                message = etsObjectEleDAO.getMessage();
                forwardURL = URLDefineList.OBJECT_ELE_QUERY;
            } else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
                etsObjectEleDAO.updateData();
                message = etsObjectEleDAO.getMessage();
                forwardURL = URLDefineList.OBJECT_ELE_QUERY;
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
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
