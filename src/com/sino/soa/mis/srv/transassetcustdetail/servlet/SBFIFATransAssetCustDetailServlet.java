package com.sino.soa.mis.srv.transassetcustdetail.servlet;

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
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.mis.srv.transassetcustdetail.dto.SBFIFATransAssetCustDetailDTO;
import com.sino.soa.mis.srv.transassetcustdetail.srv.SBFIFATransAssetCustDetailSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-13
 * Time: 14:18:44
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFATransAssetCustDetailServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        long resumeTime = 0;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SBFIFATransAssetCustDetailDTO.class.getName());
            SBFIFATransAssetCustDetailDTO dtoParameter = (SBFIFATransAssetCustDetailDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String range="N";
            message=new Message();
            conn = getDBConnection(req);
            OrgOptionProducer optProducer = new OrgOptionProducer(user, conn);
            String opt = optProducer.getBookTypeCodeOption(dtoParameter.getBookTypeCode(),range);
            dtoParameter.setOrgOption(opt);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.SRV_ASSET_CUST_DETAIL_PAGE;
            } else if (action.equals(SrvWebActionConstant.INFORSYN)) {
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_PA_ASSETDETAIL);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步转资资产清单(ODI)开始");
                logUtil.synLog(logDTO, conn);
                String envCode = SynUpdateDateUtils.getEnvCode("TransAssetCustDetailSrv", conn);
                SBFIFATransAssetCustDetailSrv srv = new SBFIFATransAssetCustDetailSrv();
                srv.setEnvCode(envCode);
                srv.setBookTypeCode(dtoParameter.getBookTypeCode());
                srv.setProjectNumber(dtoParameter.getProjectNumber());
                srv.setTaskNum(dtoParameter.getTaskNum());
                srv.setCapitalizedDateFrom(dtoParameter.getCapitalizedDateFrom());
                srv.setCapitalizedDateTo(dtoParameter.getCapitalizedDateTo());
                srv.excute();
                SrvReturnMessage srvMessage = srv.getReturnMessage();
                if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    resumeTime = System.currentTimeMillis() - start;
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_PA_ASSETDETAIL, conn);
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_PA_ASSETDETAIL);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步转资资产清单(ODI)成功！");
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_PA_ASSETDETAIL, conn);
                    message.setMessageValue("同步转资资产清单(ODI)成功！耗时" + resumeTime + "毫秒。");
                } else {
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_PA_ASSETDETAIL);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步转资资产清单(ODI)失败！耗时" + resumeTime + "毫秒。错误信息:"+srvMessage.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_PA_ASSETDETAIL, conn);
                    message.setMessageValue("同步转资资产清单(ODI)失败！耗时" + resumeTime + "毫秒。");
                }
                forwardURL = SrvURLDefineList.SRV_ASSET_CUST_DETAIL_PAGE;
        }else if (action.equals(WebActionConstant.QUERY_ACTION)) {
			forwardURL = " ";
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