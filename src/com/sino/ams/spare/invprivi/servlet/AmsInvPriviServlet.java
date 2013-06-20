package com.sino.ams.spare.invprivi.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.invprivi.dao.AmsInvPriviDAO;
import com.sino.ams.spare.invprivi.dto.AmsInvPriviDTO;
import com.sino.ams.spare.invprivi.model.AmsInvPriviModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: AmsInvPriviServlet</p>
 * <p>Description:程序自动生成服务程序“AmsInvPriviServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsInvPriviServlet extends BaseServlet {

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
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsInvPriviDTO.class.getName());

            AmsInvPriviDTO DTO = new AmsInvPriviDTO();
            DTO.setExecuteUser(req.getParameter("executeUser"));
            DTO.setExecuteInv(req.getParameter("executeInv"));
            DTO.setBusinessCategory(req.getParameter("businessCategory"));

            conn = getDBConnection(req);
            AmsInvPriviDAO amsInvPriviDAO = new AmsInvPriviDAO(user, DTO, conn);

            OptionProducer optProducer = new OptionProducer(user, conn);
            String InvOption = optProducer.getInvOption(DTO.getExecuteInv());
            req.setAttribute(WebAttrConstant.INV_OPTION, InvOption);
            
            String bizCategoryOpt = optProducer.getDictOption("INV_BIZ_CATEGORY", DTO.getBusinessCategory());//业务类型列表
            DTO.setBizCategoryOpt(bizCategoryOpt);//在前台用DTO就能得到相应的值

            if (action.equals("")) {
            	req.setAttribute(QueryConstant.QUERY_DTO, DTO);
                forwardURL = URLDefineList.INV_PRIVI_QUERY;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsInvPriviModel(user, DTO);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, DTO);
                forwardURL = URLDefineList.INV_PRIVI_QUERY;
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                ArrayList ignoreFields = new ArrayList();
                ignoreFields.add("executeUser");
                ignoreFields.add("executeInv");
                ignoreFields.add("businessCategory");
                ignoreFields.add("act");
                req2DTO.setIgnoreFields(ignoreFields);
                DTOSet priviDTOS = req2DTO.getDTOSet(req);
                boolean operateResult = amsInvPriviDAO.savePrivis(priviDTOS);
                message = amsInvPriviDAO.getMessage();
                req.setAttribute(QueryConstant.QUERY_DTO, DTO);
                if (operateResult) {
                    forwardURL = URLDefineList.INV_PRIVI_SERVLET;
                } else {
                    forwardURL = MessageConstant.MSG_PRC_SERVLET;
                }
            } else {
                message = getMessage(MsgKeyConstant.INVALID_REQ);
                message.setIsError(true);
                req.setAttribute(QueryConstant.QUERY_DTO, DTO);
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
            message = getMessage(MsgKeyConstant.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                forwarder.forwardView(forwardURL);
            }
            //根据实际情况修改页面跳转代码。
        }
    }
}
