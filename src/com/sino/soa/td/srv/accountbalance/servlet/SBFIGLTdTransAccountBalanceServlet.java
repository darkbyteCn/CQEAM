package com.sino.soa.td.srv.accountbalance.servlet;

import com.sino.ams.bean.OrgOptionProducer;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
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
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.td.srv.accountbalance.dto.SBFIGLTdTransAccountBalanceDTO;
import com.sino.soa.td.srv.accountbalance.srv.SBFIGLTdTransAccountBalanceSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-13
 * Time: 11:32:33
 * To change this template use File | Settings | File Templates.
 */
public class SBFIGLTdTransAccountBalanceServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = SrvURLDefineList.TD_SRV_ACCOUNT_BALANCE_PAGE;
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SBFIGLTdTransAccountBalanceDTO.class.getName());
            SBFIGLTdTransAccountBalanceDTO dtoParameter = (SBFIGLTdTransAccountBalanceDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String range= "Y";
            message=new Message();
            conn = getDBConnection(req);
            long resumeTime = 0;
            OrgOptionProducer oop = new OrgOptionProducer(user, conn);
            String companyCode = oop.getCompanyOption("",range);
            dtoParameter.setCompanyCode(companyCode);
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.TD_SRV_ACCOUNT_BALANCE_PAGE;
            } else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION)) {
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_TD_ACCOUNT_BALANCE);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步TD科目余额(ODI)开始");
                logUtil.synLog(logDTO, conn);
                String envCode = SynUpdateDateUtils.getEnvCode("TDTransAccountBalanceSrv", conn);
                SBFIGLTdTransAccountBalanceSrv srv = new SBFIGLTdTransAccountBalanceSrv();
                srv.setEnvCode(envCode);
                srv.setSetOfBooks(dtoParameter.getSetOfBooks());
                srv.setPeriodName(dtoParameter.getPeriodName());
                srv.excute();
                SrvReturnMessage srvMessage = srv.getReturnMessage();
                if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    resumeTime = System.currentTimeMillis() - start;
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_ACCOUNT_BALANCE, conn);
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_ACCOUNT_BALANCE);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步'TD科目余额信息(ODI)'成功, 耗时" + resumeTime + "毫秒, 帐套及期间："+dtoParameter.getSetOfBooks()+","+dtoParameter.getPeriodName());
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_ACCOUNT_BALANCE, conn);
                    message.setMessageValue("同步'TD科目余额信息(ODI)'成功！耗时" + resumeTime + "毫秒。");
                } else {
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_ACCOUNT_BALANCE);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步'TD科目余额信息(ODI)'失败, 耗时" + resumeTime + "毫秒, 帐套及期间："+dtoParameter.getSetOfBooks()+","+dtoParameter.getPeriodName()+", 错误信息："+srvMessage.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message.setMessageValue("同步'TD科目余额信息(ODI)'失败, 耗时" + resumeTime + "毫秒, 错误信息："+srvMessage.getErrorMessage());
                }
                forwardURL = SrvURLDefineList.TD_SRV_ACCOUNT_BALANCE_PAGE;
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
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}