package com.sino.soa.td.srv.valueinfo.servlet;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.MIS_CONSTANT;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.td.srv.valueinfo.dao.SBSYSYTdInquiryVSetValueInfoDAO;
import com.sino.soa.td.srv.valueinfo.dto.SBSYSYTdInquiryVSetValueInfoDTO;
import com.sino.soa.td.srv.valueinfo.srv.SBSYSYTdInquiryVSetValueInfoSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-14
 * Time: 14:30:08
 * To change this template use File | Settings | File Templates.
 */
public class SBSYSYTdInquiryVSetValueInfoServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = SrvURLDefineList.TD_SRV_SETVALUE_PAGE;
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        int errorCount = 0;
        int totalCount = 0;
        long resumeTime = 0;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SBSYSYTdInquiryVSetValueInfoDTO.class.getName());
            SBSYSYTdInquiryVSetValueInfoDTO dtoParameter = (SBSYSYTdInquiryVSetValueInfoDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String assetType = StrUtil.nullToString(req.getParameter("type"));
            conn = getDBConnection(req);
            SBSYSYTdInquiryVSetValueInfoDAO mFndFlexValuesDAO = new SBSYSYTdInquiryVSetValueInfoDAO(user, dtoParameter, conn);
            OptionProducer optionProducer = new OptionProducer(user, conn);
            String range = MIS_CONSTANT.SOURCE_TD;//TD
            String opt = optionProducer.getFlexValueSetOption(dtoParameter.getFlexValueName(), range);
            dtoParameter.setFlexValetSetOption(opt);
            if (action.equals("")) {
                dtoParameter.setType(assetType);
                String lastUpdateDate = SynUpdateDateUtils.getLastUpdateDate(SrvType.SRV_TD_SET_VALUESET, conn);
                dtoParameter.setLastUpdateDate(lastUpdateDate);
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.TD_SRV_SETVALUE_PAGE;
            } else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION)) {
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_TD_SET_VALUESET);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步TD值集值信息服务开始");
                logUtil.synLog(logDTO, conn);
                SBSYSYTdInquiryVSetValueInfoSrv setValueInfoSrv = new SBSYSYTdInquiryVSetValueInfoSrv();
                setValueInfoSrv.setFlexValueName(dtoParameter.getFlexValueName());
                setValueInfoSrv.setStartLastUpdateDate(dtoParameter.getStartLastUpdateDate());
                setValueInfoSrv.setEndLastUpdateDate(dtoParameter.getEndLastUpdateDate());
                setValueInfoSrv.execute();
                SrvReturnMessage srvMessage = setValueInfoSrv.getReturnMessage();
                if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_SET_VALUESET, conn);
                    DTOSet ds = setValueInfoSrv.getDs();
                    for (int i = 0; i < ds.getSize(); i++) {
                        SBSYSYTdInquiryVSetValueInfoDTO dto = (SBSYSYTdInquiryVSetValueInfoDTO) ds.getDTO(i);
                        mFndFlexValuesDAO = new SBSYSYTdInquiryVSetValueInfoDAO(user, dto, conn);
                        try {
                            if (mFndFlexValuesDAO.isexistsSetValueModel(dto.getFlexValue(), dto.getFlexValueSetId())) {
                                mFndFlexValuesDAO.updateData();
                            } else {
                                mFndFlexValuesDAO.createData();
                            }
                            totalCount++;
                        } catch (DataHandleException e) {
                            Logger.logError(e);
                            logDTO = new SynLogDTO();
                            logDTO.setSynType(SrvType.SRV_TD_SET_VALUESET);
                            logDTO.setCreatedBy(user.getUserId());
                            logDTO.setSynMsg(e.toString());
                            logUtil.synLog(logDTO, conn);
                            errorCount++;
                        }
                    }
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_SET_VALUESET, conn);
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_SET_VALUESET);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD值集值信息服务结束,同步" + (totalCount + errorCount) + "条记录，成功" + totalCount + "，失败" + errorCount + "，耗时" + resumeTime + "毫秒");
                    logUtil.synLog(logDTO, conn);
                    message = new Message();
                    message.setMessageValue("同步TD值集值信息服务 " + (totalCount + errorCount) + "条记录，成功" + totalCount + "，失败" + errorCount + "，耗时" + resumeTime + "毫秒");
                } else {
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_SET_VALUESET);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD值集值信息服务失败, 耗时" + resumeTime + "毫秒。错误信息:"+srvMessage.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message = new Message();
                    message.setMessageValue("同步TD值集值信息服务失败, 耗时" + resumeTime + "毫秒。错误信息:"+srvMessage.getErrorMessage());
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.TD_SRV_SETVALUE_PAGE;
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
        } catch (CalendarException e) {
            e.printLog();
        } catch (DatatypeConfigurationException e) {
            Logger.logError(e);
        } catch (ContainerException e) {
            e.printLog();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}