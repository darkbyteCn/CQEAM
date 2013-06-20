package com.sino.soa.td.srv.AssetPeriodStatus.servlet;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sino.soa.td.srv.AssetPeriodStatus.dao.TdSrvAssetPeriodDAO;
import com.sino.soa.td.srv.AssetPeriodStatus.dto.TdSrvAssetPeriodStatusDTO;
import com.sino.soa.td.srv.AssetPeriodStatus.srv.TDInquiryAssetPeriodStatusSrv;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;
import com.sino.ams.bean.OrgOptionProducer;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.TimeException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.base.util.StrUtil;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
/**
 * DATE：Sep 15, 2011 4:30:07 PM
 * AUTHOR：wangzhipeng
 * FUNCTION：资产会计状态_TD(servlet)
 */
public class TDSrvAssetPeriodServlet extends BaseServlet{
	  /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        int count = 0;
        long resumeTime = 0;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(TdSrvAssetPeriodStatusDTO.class.getName());
            TdSrvAssetPeriodStatusDTO dtoParameter = (TdSrvAssetPeriodStatusDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String assetsType = StrUtil.nullToString(req.getParameter("assetsType"));
            dtoParameter.setAsstesType(assetsType);
            conn = getDBConnection(req);
            OrgOptionProducer orgOptionProducer = new OrgOptionProducer(user, conn);
            String range = assetsType.equals("TD") ? "Y" : "N";
            dtoParameter.setBookOption(orgOptionProducer.getBookTypeCodeOption(dtoParameter.getBookTypeCode(),range));
            TdSrvAssetPeriodDAO srvAssetBookDAO = new TdSrvAssetPeriodDAO(user, dtoParameter, conn);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.TD_SRV_ASSET_PERIOD_PAGE;
            }  else if (action.equals(SrvWebActionConstant.INFORSYN) && AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) {//TD
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_TD_FA_PERIOD);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步TD资产会计期间开始");
                logUtil.synLog(logDTO, conn);
                TDInquiryAssetPeriodStatusSrv service = new TDInquiryAssetPeriodStatusSrv();
                service.setBookTypeCode(dtoParameter.getBookTypeCode());    //
               // service.setBookTypeCode("SXMC_FA_8110");       //模拟数据
               // service.setPeriodName(dtoParameter.getPeriodName());
                service.execute();
                SrvReturnMessage srvMessage = service.getReturnMessage();
                if (srvMessage.getErrorFlag().equals("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_FA_PERIOD, conn);
                    DTOSet ds = service.getDs();
                    if (ds.getSize() > 0) {
                        count = srvAssetBookDAO.synAssetPeriod(ds);
                    }
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_FA_PERIOD);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD资产会计期间结束, 同步" + (ds.getSize()) + "条记录，成功" + count + "，失败" + (ds.getSize() - count) + "，耗时" + resumeTime + "毫秒, 资产账簿："+dtoParameter.getBookTypeCode());
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_FA_PERIOD, conn);
                    message = new Message();
                    message.setMessageValue("同步TD资产会计期间 " + (ds.getSize()) + "条记录，成功" + count + "，失败" + (ds.getSize() - count) + "，耗时" + resumeTime + "毫秒");
                } else {
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_FA_PERIOD);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD资产会计期间失败，耗时" + resumeTime + "毫秒, 资产账簿："+dtoParameter.getBookTypeCode()+"。错误信息:"+srvMessage.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message = new Message();
                    message.setMessageValue("同步TD资产会计期间失败，耗时" + resumeTime + "毫秒, 资产账簿："+dtoParameter.getBookTypeCode()+"。错误信息:"+srvMessage.getErrorMessage());
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.TD_SRV_ASSET_PERIOD_PAGE;
            }
        } catch (PoolPassivateException ex) {
            ex.printLog();
            message.setMessageValue("同步失败");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
        } catch (DTOException ex) {
            ex.printLog();
            message.setMessageValue("同步失败:DTOException");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
        } catch (TimeException e) {
            message.setMessageValue("同步失败:TimeException");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
            e.printLog();
        } catch (QueryException e) {
            message.setMessageValue("同步失败:QueryException");
            forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
            e.printLog();
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
