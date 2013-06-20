package com.sino.soa.mis.srv.pagequiryaccountbalance.servlet;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.TimeException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.mis.srv.pagequiryaccountbalance.dao.SBFIGLPageQuiryAccountBalanceDAO;
import com.sino.soa.mis.srv.pagequiryaccountbalance.dto.SBFIGLPageQuiryAccountBalanceDTO;
import com.sino.soa.mis.srv.pagequiryaccountbalance.srv.SBFIGLPageQuiryAccountBalanceSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-15
 * Time: 16:51:38
 * To change this template use File | Settings | File Templates.
 */
public class SBFIGLPageQuiryAccountBalanceServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        int count = 0;
        long resumeTime = 0;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SBFIGLPageQuiryAccountBalanceDTO.class.getName());
            SBFIGLPageQuiryAccountBalanceDTO dtoParameter = (SBFIGLPageQuiryAccountBalanceDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            SBFIGLPageQuiryAccountBalanceDAO srvAssetCategoryDAO = new SBFIGLPageQuiryAccountBalanceDAO(user, dtoParameter, conn);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.PAGE_ACCOUNT_BALANCE_PAGE;
            } else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
                SBFIGLPageQuiryAccountBalanceSrv service = new SBFIGLPageQuiryAccountBalanceSrv();
                String bookTypeCode = req.getParameter("bookTypeCode");
                service.execute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag() != null && srm.getErrorFlag().equals("Y")) {
                    DTOSet ds = service.getDs();
                    req.setAttribute(SrvWebActionConstant.ASSETBOOKTRANSOU, ds);
                    forwardURL = SrvURLDefineList.PAGE_ACCOUNT_BALANCE_PAGE;
                }
            } else if (action.equals(SrvWebActionConstant.INFORSYN)) {
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_FA_PAGE_ACCOUNT_BALANCE);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步MIS科目余额开始");
                logUtil.synLog(logDTO, conn);

                SBFIGLPageQuiryAccountBalanceSrv service = new SBFIGLPageQuiryAccountBalanceSrv();
                service.setSetOfBooks(dtoParameter.getSetOfBooks());
                service.setPeriodNameFrom(dtoParameter.getPeriodNameFrom());
                service.setPeriodNameTo(dtoParameter.getPeriodNameTo());
                service.setActualFlag(dtoParameter.getActualFlag());
                service.execute();

                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_FA_PAGE_ACCOUNT_BALANCE, conn);
                    DTOSet ds = service.getDs();
                    if (ds.getSize() > 0) {
                        count = srvAssetCategoryDAO.SavaAssetCategory(ds);
                    }
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_PAGE_ACCOUNT_BALANCE);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS科目余额结束！");
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_FA_PAGE_ACCOUNT_BALANCE, conn);

                    message = new Message();
                    message.setMessageValue("同步" + (ds.getSize()) + "条记录，成功" + count + "，失败" + (ds.getSize() - count) + "，耗时" + resumeTime + "毫秒");
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.PAGE_ACCOUNT_BALANCE_PAGE;

            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
        } catch (DTOException ex) {
            ex.printLog();
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
        } catch (DatatypeConfigurationException ex1) {
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
            ex1.printStackTrace();
        } catch (TimeException e) {
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
            e.printStackTrace();
        } catch (Exception e) {
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
            e.printStackTrace();
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}