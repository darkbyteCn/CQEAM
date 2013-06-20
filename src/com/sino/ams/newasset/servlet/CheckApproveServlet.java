package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsCheckHeaderDAO;
import com.sino.ams.newasset.dao.CheckApproveDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;


/**
 * <p>Title: CheckBatchApproveServlet</p>
 * <p>Description:程序自动生成服务程序“CheckBatchApproveServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class CheckApproveServlet extends BaseServlet {

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
            req2DTO.setDTOClassName(AmsAssetsCheckBatchDTO.class.getName());
            AmsAssetsCheckBatchDTO dtoParameter = (AmsAssetsCheckBatchDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            CheckApproveDAO approveDAO = new CheckApproveDAO(user, dtoParameter, conn);
            if (action.equals(AssetsActionConstant.EDIT_ACTION)) { //进入审批前页面
                if(dtoParameter.getBatchId().equals("")) {
                    dtoParameter.setBatchId(dtoParameter.getApp_dataID());
                }
                approveDAO.setDTOClassName(AmsAssetsCheckBatchDTO.class.getName());
                approveDAO.setCalPattern(LINE_PATTERN);
                AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) approveDAO.getDataByPrimaryKey();
                if (dto == null) {
                    dto = dtoParameter;
                    message.setIsError(true);
                    message = getMessage(AssetsMessageKeys.DATA_NOT_EXIST);
                } else {
                    AmsAssetsCheckHeaderDTO orderDTO = new AmsAssetsCheckHeaderDTO();
                    orderDTO.setBatchId(dtoParameter.getBatchId());
                    AmsAssetsCheckHeaderDAO orderDAO = new AmsAssetsCheckHeaderDAO(user, orderDTO, conn);
                    orderDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
                    orderDAO.setCalPattern(LINE_PATTERN);
                    DTOSet orders = (DTOSet) orderDAO.getDataByForeignKey("batchId");
                    req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS, orders);
                }
                req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dto);
                forwardURL = AssetsURLList.BATCH_APPROVE_EDIT;
            } else if (action.equals(AssetsActionConstant.APPROVE_ACTION)) { //审批流程
                if(dtoParameter.getBatchId().equals("")) {
                    dtoParameter.setBatchId(dtoParameter.getApp_dataID());
                }
                approveDAO.setDTOParameter(dtoParameter);
                boolean operateResult = approveDAO.newApproveOrder();
                message = approveDAO.getMessage();
                forwardURL = AssetsURLList.CHECK_APPR_SERVLET;
                if (operateResult) {
                    forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                } else {
                    forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
                }
                forwardURL += "&batchId=" + dtoParameter.getBatchId();
            } else if (action.equals("REJECT_ACTION")) { //审批流程
                approveDAO.rejectOrder();
                message = approveDAO.getMessage();
                forwardURL = AssetsURLList.CHECK_APPR_SERVLET;
                forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
                forwardURL += "&batchId=" + dtoParameter.getBatchId();
            } else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) { //进入审批后页面
                approveDAO.setDTOClassName(AmsAssetsCheckBatchDTO.class.getName());
                approveDAO.setCalPattern(LINE_PATTERN);
                AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) approveDAO.getDataByPrimaryKey();
                if (dto == null) {
                    dto = dtoParameter;
                    message.setIsError(true);
                    message = getMessage(AssetsMessageKeys.DATA_NOT_EXIST);
                } else {
                    AmsAssetsCheckHeaderDTO orderDTO = new AmsAssetsCheckHeaderDTO();
                    orderDTO.setBatchId(dtoParameter.getBatchId());
                    AmsAssetsCheckHeaderDAO orderDAO = new AmsAssetsCheckHeaderDAO(user, orderDTO, conn);
                    orderDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
                    orderDAO.setCalPattern(LINE_PATTERN);
                    DTOSet orders = (DTOSet) orderDAO.getDataByForeignKey("batchId");
                    req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATAS, orders);
                }
                req.setAttribute(AssetsWebAttributes.CHECK_BATCH_DATA, dto);
                forwardURL = AssetsURLList.BATCH_DETAIL_PAGE;
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
        } finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}
