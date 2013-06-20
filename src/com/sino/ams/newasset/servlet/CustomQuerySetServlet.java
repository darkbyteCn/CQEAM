package com.sino.ams.newasset.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.CustomQuerySetDAO;
import com.sino.ams.newasset.dto.AmsAssetsCommQueryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: CustomQuerySetServlet</p>
 * <p>Description:程序自动生成服务程序“CustomQuerySetServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class CustomQuerySetServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws
            ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsCommQueryDTO.class.getName());
            AmsAssetsCommQueryDTO dtoParameter = (AmsAssetsCommQueryDTO)
                                                 req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            CustomQuerySetDAO commQueryDAO = new CustomQuerySetDAO(user,
                    dtoParameter, conn);
            if (action.equals("")) {
                String allQueryFields = commQueryDAO.getAllQueryFields();
                req.setAttribute(AssetsWebAttributes.ALL_QRY_FIELDS,
                                 allQueryFields);
                String chkQueryFields = commQueryDAO.getChkQueryFields();
                req.setAttribute(AssetsWebAttributes.CHK_QRY_FIELDS,
                                 chkQueryFields);
                String allDisplayFields = commQueryDAO.getAllDisplayFields();
                req.setAttribute(AssetsWebAttributes.ALL_DIS_FIELDS,
                                 allDisplayFields);
                String chkDisplayFields = commQueryDAO.getChkDisplayFields();
                req.setAttribute(AssetsWebAttributes.CHK_DIS_FIELDS,
                                 chkDisplayFields);
                forwardURL = AssetsURLList.CUST_QRY_SET_PAGE;
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                String[] chkQueryFields = req.getParameterValues(
                        "chkQueryField");
                String[] chkDisplayFields = req.getParameterValues(
                        "chkDisplayField");
                commQueryDAO.saveCustomizeFields(chkQueryFields,
                                                 chkDisplayFields);
                message = commQueryDAO.getMessage();
                forwardURL = AssetsURLList.CUST_QRY_SET_SERVLET;
                forwardURL += "?act=";
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
//		} catch (DataHandleException ex) {
//			ex.printLog();
//			//请根据实际情况处理消息
//			forwardURL = "保持界面录入的数据，返回到原页面，并显示上面给出的消息";
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
