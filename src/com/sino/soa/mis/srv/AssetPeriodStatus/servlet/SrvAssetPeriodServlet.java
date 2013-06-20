package com.sino.soa.mis.srv.AssetPeriodStatus.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.db.QueryConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.bean.OrgOptionProducer;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.mis.srv.AssetPeriodStatus.dao.SrvAssetPeriodDAO;
import com.sino.soa.mis.srv.AssetPeriodStatus.dto.SrvAssetPeriodStatusDTO;
import com.sino.soa.mis.srv.AssetPeriodStatus.srv.InquiryAssetPeriodStatusSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;


/**
 * <p>Title: SrvAssetBookServlet</p>
 * <p>Description:程序自动生成服务程序“SrvAssetBookServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author wangzhipeng
 * @version 1.0
 * DES: 资产会计期间状态servlet层
 */


public class SrvAssetPeriodServlet extends BaseServlet {

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
            req2DTO.setDTOClassName(SrvAssetPeriodStatusDTO.class.getName());
            SrvAssetPeriodStatusDTO dtoParameter = (SrvAssetPeriodStatusDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String assetsType = StrUtil.nullToString(req.getParameter("assetsType"));      //非TD  assetsType参数为null
            dtoParameter.setAsstesType(assetsType);
            conn = getDBConnection(req);
            OrgOptionProducer orgOptionProducer = new OrgOptionProducer(user, conn);
            String range = assetsType.equals("TD") ? "Y" : "N";      //非TD: N
            dtoParameter.setBookOption(orgOptionProducer.getBookTypeCodeOption(dtoParameter.getBookTypeCode(),range));
            SrvAssetPeriodDAO srvAssetBookDAO = new SrvAssetPeriodDAO(user, dtoParameter, conn);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.SRV_ASSET_PERIOD_PAGE;
            } else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
                InquiryAssetPeriodStatusSrv service = new InquiryAssetPeriodStatusSrv();
                service.setBookTypeCode(dtoParameter.getBookTypeCode());  
                //service.setBookTypeCode("SXMC_FA_4110");                //测试使用 山西省公司
                //service.setPeriodName(dtoParameter.getPeriodName());
                service.execute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag() != null && srm.getErrorFlag().equals("Y")) {
                    DTOSet ds = service.getDs();  
                    req.setAttribute(SrvWebActionConstant.ASSETBOOKTRANSOU, ds);
                    forwardURL = SrvURLDefineList.SRV_ASSET_PERIOD_PAGE;
                } else {
                    message.setMessageValue("调用WebService服务失败：" + srm.getErrorMessage());
                    forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
                }
            } else if (action.equals(SrvWebActionConstant.INFORSYN) && !AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) {   //非TD
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_FA_PERIOD);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步MIS资产会计期间开始");
                logUtil.synLog(logDTO, conn);
                InquiryAssetPeriodStatusSrv service = new InquiryAssetPeriodStatusSrv();
                service.setBookTypeCode(dtoParameter.getBookTypeCode());        
                service.setPeriodName(dtoParameter.getPeriodName());
                service.execute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag().equals("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_FA_PERIOD, conn);
                    DTOSet ds = service.getDs();
                    if (ds.getSize() > 0) {
                        count = srvAssetBookDAO.synAssetPeriod(ds);
                    }
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_PERIOD);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS资产会计期间结束, 同步" + (ds.getSize()) + "条记录，成功" + count + "，失败" + (ds.getSize() - count) + "，耗时" + resumeTime + "毫秒, 资产账簿："+dtoParameter.getBookTypeCode());
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_FA_PERIOD, conn);
                    message = new Message();
                    message.setMessageValue("同步MIS资产会计期间" + (ds.getSize()) + "条记录，成功" + count + "，失败" + (ds.getSize() - count) + "，耗时" + resumeTime + "毫秒");
                } else {
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_PERIOD);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS资产会计期间失败, 耗时" + resumeTime + "毫秒, 资产账簿："+dtoParameter.getBookTypeCode()+"。错误信息:"+srm.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message = new Message();
                    message.setMessageValue("同步MIS资产会计期间失败, 耗时" + resumeTime + "毫秒, 资产账簿："+dtoParameter.getBookTypeCode()+"。错误信息:"+srm.getErrorMessage());
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.SRV_ASSET_PERIOD_PAGE;
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