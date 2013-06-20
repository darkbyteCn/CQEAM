package com.sino.ams.others.cabel.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.others.cabel.dao.AmsCabelInfoDAO;
import com.sino.ams.others.cabel.dto.AmsCabelInfoDTO;
import com.sino.ams.others.cabel.model.AmsCabelInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
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
 * <p>Title: AmsCabelInfoServlet</p>
 * <p>Description:程序自动生成服务程序“AmsCabelInfoServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsCabelInfoServlet extends BaseServlet {

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
            AmsCabelInfoDTO dtoParameter = null;
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsCabelInfoDTO.class.getName());
            dtoParameter = (AmsCabelInfoDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            AmsCabelInfoDAO amsCabelInfoDAO = new AmsCabelInfoDAO(user, dtoParameter, conn);

            OptionProducer optProducer = new OptionProducer(user, conn);

            if (action.equals("")) {
                //电缆铺设方式
                String spreadTypeOption = optProducer.getDictOption(DictConstant.SPREAD_TYPE, "");
                req.setAttribute(WebAttrConstant.SPREAD_TYPE_OPTION, spreadTypeOption);
                //电缆用途
                String cabelUsageOption = optProducer.getDictOption(DictConstant.CABEL_USAGE, "");
                req.setAttribute(WebAttrConstant.CABEL_USAGE_OPTION, cabelUsageOption);

                forwardURL = URLDefineList.CABEL_INFO_QUERY;
            } else if (action.equals(WebActionConstant.QUERY_ACTION)) {
                BaseSQLProducer sqlProducer = new AmsCabelInfoModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.produceWebData();
                //电缆铺设方式
                String spreadTypeOption = optProducer.getDictOption(DictConstant.SPREAD_TYPE, "");
                req.setAttribute(WebAttrConstant.SPREAD_TYPE_OPTION, spreadTypeOption);
                //电缆用途
                String cabelUsageOption = optProducer.getDictOption(DictConstant.CABEL_USAGE, "");
                req.setAttribute(WebAttrConstant.CABEL_USAGE_OPTION, cabelUsageOption);

                forwardURL = URLDefineList.CABEL_INFO_QUERY;
            } else if (action.equals(WebActionConstant.NEW_ACTION)) {
                AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) req.getAttribute(WebAttrConstant.CABEL_INFO_DTO);
                if (amsCabelInfo == null) {
                    amsCabelInfo = dtoParameter;
                }
                //电缆铺设方式
                String spreadTypeOption = optProducer.getDictOption(DictConstant.SPREAD_TYPE, StrUtil.nullToString(amsCabelInfo.getSpreadType()));
                req.setAttribute(WebAttrConstant.SPREAD_TYPE_OPTION, spreadTypeOption);
                //电缆用途
                String cabelUsageOption = optProducer.getDictOption(DictConstant.CABEL_USAGE, StrUtil.nullToString(amsCabelInfo.getCabelUsage()));
                req.setAttribute(WebAttrConstant.CABEL_USAGE_OPTION, cabelUsageOption);
                req.setAttribute(WebAttrConstant.CABEL_INFO_DTO, amsCabelInfo);
                forwardURL = URLDefineList.CABEL_INFO_DETAIL;
            } else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
                amsCabelInfoDAO.setDTOClassName(AmsCabelInfoDTO.class.getName());
                AmsCabelInfoDTO amsCabelInfo = (AmsCabelInfoDTO) amsCabelInfoDAO.getDataByPrimaryKey();
                if (amsCabelInfo == null) {
                    amsCabelInfo = new AmsCabelInfoDTO();
                    message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
                    message.setIsError(true);
                }
                //电缆铺设方式
                String spreadTypeOption = optProducer.getDictOption(DictConstant.SPREAD_TYPE, StrUtil.nullToString(amsCabelInfo.getSpreadType()));
                req.setAttribute(WebAttrConstant.SPREAD_TYPE_OPTION, spreadTypeOption);
                //电缆用途
                String cabelUsageOption = optProducer.getDictOption(DictConstant.CABEL_USAGE, StrUtil.nullToString(amsCabelInfo.getCabelUsage()));
                req.setAttribute(WebAttrConstant.CABEL_USAGE_OPTION, cabelUsageOption);
                req.setAttribute(WebAttrConstant.CABEL_INFO_DTO, amsCabelInfo);
                forwardURL = URLDefineList.CABEL_INFO_DETAIL;
            } else if (action.equals(WebActionConstant.CREATE_ACTION)) {  //如果存在UPDATE不存在CREATE
                amsCabelInfoDAO.submit();
                req.setAttribute(WebAttrConstant.CABEL_INFO_DTO, dtoParameter);
                forwardURL = URLDefineList.CABEL_INFO_QUERY;
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
        } catch (ContainerException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.CONTAINER_ERROR);
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
            message = getMessage(MsgKeyConstant.CONN_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
    }
}