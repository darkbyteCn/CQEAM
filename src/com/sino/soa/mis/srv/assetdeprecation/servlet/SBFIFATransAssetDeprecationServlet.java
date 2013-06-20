package com.sino.soa.mis.srv.assetdeprecation.servlet;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.mis.srv.assetdeprecation.dto.SBFIFATransAssetDeprecationDTO;
import com.sino.soa.mis.srv.assetdeprecation.srv.SBFIFATransAssetDeprecationSrv;
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
 * Date: 2011-10-12 
 * Time: 15:01:45
 * To change this template use File | Settings | File Templates.
 */
public class SBFIFATransAssetDeprecationServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = SrvURLDefineList.TRANS_ASSET_DEPRECATION_PAGE;
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        try {
            String periodName = "";
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SBFIFATransAssetDeprecationDTO.class.getName());
            SBFIFATransAssetDeprecationDTO dtoParameter = (SBFIFATransAssetDeprecationDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            conn = getDBConnection(req);
            periodName = req.getParameter("periodName");
            long resumeTime = 0;
            message = new Message();
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.TRANS_ASSET_DEPRECATION_PAGE;
            } else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION)) {
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                String envCode = SynUpdateDateUtils.getEnvCode("TransAssetDeprecationSrv", conn);
                SBFIFATransAssetDeprecationSrv srv = new SBFIFATransAssetDeprecationSrv();
                srv.setPeriodName(periodName);
                srv.setEnvCode(envCode);
                srv.excute();
                SrvReturnMessage srvMessage = srv.getSrvMessage();
                resumeTime = System.currentTimeMillis() - start;
                if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                	SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_FA_DEPRECATION, conn);
					logDTO = new SynLogDTO();
					logDTO.setSynType(SrvType.SRV_FA_DEPRECATION);
					logDTO.setCreatedBy(user.getUserId());
					logDTO.setSynMsg("同步'MIS资产折旧信息(ODI)'成功, 耗时" + resumeTime + "毫秒, 期间名称:"+periodName);
					logUtil.synLog(logDTO, conn);
					SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_FA_DEPRECATION, conn);
                    message.setMessageValue("同步'MIS资产折旧信息(ODI)'成功, 耗时" + resumeTime + "毫秒。");
                } else {
                	logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_DEPRECATION);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步'MIS资产折旧信息(ODI)'失败, 耗时" + resumeTime + "毫秒, 期间名称:"+periodName+", 错误信息: "+srvMessage.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message.setMessageValue("同步'MIS资产折旧信息(ODI)'失败, 耗时" + resumeTime + "毫秒, 错误信息：" + srvMessage.getErrorMessage());
                }
                forwardURL = SrvURLDefineList.TRANS_ASSET_DEPRECATION_PAGE;

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
        }catch (QueryException e) {
			e.printStackTrace();
		} catch (ContainerException e) {
			e.printStackTrace();
		} catch (DataHandleException e) {
			e.printLog();
		} finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
        }
    }
}