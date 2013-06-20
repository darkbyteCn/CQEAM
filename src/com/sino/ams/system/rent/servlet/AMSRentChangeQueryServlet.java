package com.sino.ams.system.rent.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.system.rent.dao.AMSRentChangeLDAO;
import com.sino.ams.system.rent.dao.AMSRentChangeQueryDAO;
import com.sino.ams.system.rent.model.AMSRentChangeQueryModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.ConvertUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2010-2-5
 * Time: 10:19:40
 * To change this template use File | Settings | File Templates.
 */
public class AMSRentChangeQueryServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        String msg = "";
        try {
            SfUserDTO user = (SfUserDTO) getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
            AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) req2DTO.getDTO(req);
            ServletConfigDTO servletConfig = getServletConfig(req);
            dto.setServletConfig(servletConfig);
            FlowDTO flowDTO = this.getDTOFromReq(req);
            String action = dto.getAct();
            conn = getDBConnection(req);
            AMSRentChangeQueryDAO headerDAO = new AMSRentChangeQueryDAO(user, dto, conn);
            headerDAO.setServletConfig(servletConfig);
            String transType = dto.getTransType();
            String transferype = dto.getTransferType();
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            OptionProducer op = new OptionProducer(user, conn);
            String option = "";
            if (transType.equals(AssetsDictConstant.ASS_RED)) {
                option = optProducer.getTransferOption(dto.getTransferType());
                dto.setTransferTypeOption(option);
            }
            option = optProducer.getFAContentOption(dto.getFaContentCode());
            dto.setFaContentOption(option);
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/system/rent/amsRentTransQuery.jsp";
            } else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
                dto.setTransStatus(AssetsDictConstant.SAVE_TEMP);
                BaseSQLProducer sqlProducer = new AMSRentChangeQueryModel(user, dto);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                CheckBoxProp checkProp = new CheckBoxProp("subCheck");
                checkProp.addDbField("TRANS_ID");
                pageDAO.setWebCheckProp(checkProp);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = "/system/rent/amsRentTransQuery.jsp";
            }  else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
                headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
                AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
                if (headerDTO == null) {
                    headerDTO = new AmsAssetsTransHeaderDTO();
                }
                headerDTO.setCalPattern(LINE_PATTERN);
                AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
                lineDTO.setTransId(headerDTO.getTransId());
                AMSRentChangeLDAO lineDAO = new AMSRentChangeLDAO(user, lineDTO, conn);
                lineDAO.setCalPattern(LINE_PATTERN);
                lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                DTOSet ds = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
                if (ds == null) {
                    ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
                }
                String deptOptions = optProducer.getAllDeptOption( headerDTO.getToDept()   );
                String group = dto.getToGroupOption();
                String groupOp = op.getAllGroup3( StrUtil.nullToString( headerDTO.getToGroup() ), user.getOrganizationId(), false, true);
                headerDTO.setToGroupOption(groupOp);
                headerDTO.setFromDeptOption(deptOptions);
                headerDTO.setToDeptOption(deptOptions);

                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
                forwardURL = "/system/rent/amsRentTransDetail.jsp";
            } else if (action.equals(AssetsActionConstant.EDIT_ACTION)) {
                headerDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
                AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO) headerDAO.getDataByPrimaryKey();
                if (headerDTO == null) {
                    headerDTO = new AmsAssetsTransHeaderDTO();
                }
                headerDTO.setCalPattern(LINE_PATTERN);
                AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
                lineDTO.setTransId(headerDTO.getTransId());
                AMSRentChangeLDAO lineDAO = new AMSRentChangeLDAO(user, lineDTO, conn);
                lineDAO.setCalPattern(LINE_PATTERN);
                lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                DTOSet ds = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
                if (ds == null) {
                    ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
                }
                String deptOptions = optProducer.getAllDeptOption(   headerDTO.getToDept()    );
                String group = dto.getToGroupOption();
                String groupOp = op.getAllGroup3(StrUtil.nullToString( headerDTO.getToGroup() ), user.getOrganizationId(), false, true);
                headerDTO.setToGroupOption(groupOp);
                headerDTO.setFromDeptOption(deptOptions);
                headerDTO.setToDeptOption(deptOptions);
                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
                forwardURL = "/system/rent/amsRentChangeApprove.jsp";
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
        } catch (StrException ex) {
            ex.printLog();
            message = getMessage(AssetsMessageKeys.COMMON_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (ContainerException e) {
            Logger.logError(e);
            message = getMessage(MsgKeyConstant.SQL_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }  finally {
            closeDBConnection(conn);
            setHandleMessage(req, message);
            if (!StrUtil.isEmpty(forwardURL)) {
                ServletForwarder forwarder = new ServletForwarder(req, res);
                if (msg.equals("")) {
                    forwarder.forwardView(forwardURL);
                } else {
                    forwarder.forwardParentView(forwardURL, msg);
                }

            }
        }
    }
    
    
    public static FlowDTO getDTOFromReq(HttpServletRequest req) {
        FlowDTO flowDTO = new FlowDTO();
        flowDTO.setActId(StrUtil.nullToString(req.getParameter("actId")));
        flowDTO.setSessionCurTaskId(StrUtil.nullToString(req.getParameter("currTaskId")));
        flowDTO.setToTaskId(StrUtil.nullToString(req.getParameter("nextTaskId")));
        flowDTO.setToUserIds(StrUtil.nullToString(req.getParameter("nextUserId")));
        flowDTO.setProcId(StrUtil.nullToString(req.getParameter("procId")));
        flowDTO.setPrevTaskId(StrUtil.nullToString(req.getParameter("prevTaskId")));
        flowDTO.setPrevUserId(StrUtil.nullToString(req.getParameter("prevUserId")));
        flowDTO.setCurTaskId(StrUtil.nullToString(req.getParameter("currTaskId")));
        flowDTO.setApproveContent(StrUtil.nullToString(req.getParameter("approveContent")));
        flowDTO.setSectionRight(StrUtil.nullToString(req.getParameter("sectionRight")));
        flowDTO.setProcName(StrUtil.nullToString(req.getParameter("procName")));
        return flowDTO;
    }
}
