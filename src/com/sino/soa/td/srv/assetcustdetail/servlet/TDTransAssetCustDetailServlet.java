package com.sino.soa.td.srv.assetcustdetail.servlet;

import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.bean.OrgOptionProducer;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.mis.srv.assetcustdetail.dto.ODIAssetCustDetailDTO;      
//import com.sino.soa.mis.srv.assetcustdetail.srv.TransAssetCustDetailSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;
import com.sino.soa.td.srv.assetcustdetail.srv.TDTransAssetCustDetailSrv;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * <p>Title: SrvAssetBookServlet</p>
 * <p>Description:程序自动生成服务程序“SrvAssetBookServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author wangzp
 * function:查询转资物料清单_TD(ODI) 
 */

public class TDTransAssetCustDetailServlet extends BaseServlet {

    /**
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        int totalCount = 0;
        long resumeTime = 0;
        try {
            conn = getDBConnection(req);
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(ODIAssetCustDetailDTO.class.getName());
            ODIAssetCustDetailDTO dtoParameter = (ODIAssetCustDetailDTO) req2DTO.getDTO(req);
            String action = StrUtil.nullToString(req.getParameter("act"));
            String assetsType = StrUtil.nullToString(req.getParameter("assetsType"));
            OrgOptionProducer optProducer = new OrgOptionProducer(user, conn);
            String range = assetsType.equals("TD") ? "Y" : "N";
            dtoParameter.setAssetsType(assetsType);
            String opt = optProducer.getBookTypeCodeOption(dtoParameter.getBookTypeCode(),range);
            dtoParameter.setOrgOption(opt);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.TD_TRANS_ASSET_CUST_DETAIL_PAGE;
            } else if (action.equals(SrvWebActionConstant.INFORSYN) && AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) { //TD
                long start = System.currentTimeMillis();
                String envCode = SynUpdateDateUtils.getEnvCode("TDTransMetaCustDetailSrv", conn);
                dtoParameter.setEnvCode(envCode);         //SX_ZC_TransMetaCustDetailSrv
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_TD_PA_ASSETDETAIL);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步TD转资清单开始");
                logUtil.synLog(logDTO, conn);

                TDTransAssetCustDetailSrv srv = new TDTransAssetCustDetailSrv();
                srv.setEnvCode(dtoParameter.getEnvCode());
                srv.setBookTypeCode(dtoParameter.getBookTypeCode());
                srv.setProjectNumber(dtoParameter.getProjectNumber());
                srv.setTaskNum(dtoParameter.getTaskNum());
                srv.setCapitalizedDateFrom(dtoParameter.getCapitalizedDateFrom());
                srv.setCapitalizedDateTo(dtoParameter.getCapitalizedDateTo());

                srv.excute();
                SrvReturnMessage srvMessage = srv.getSrvMessage();

                if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_PA_ASSETDETAIL, conn);

                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_PA_ASSETDETAIL);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD转资清单结束！");
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_PA_ASSETDETAIL, conn);
                    message = new Message();
                    message.setMessageValue("同步TD转资清单成功，耗时" + resumeTime + "毫秒");
                } else {
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_PA_ASSETDETAIL);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD转资清单失败！");
                    logUtil.synLog(logDTO, conn);
                    message = new Message();
                    message.setMessageValue("同步TD转资清单失败，耗时" + resumeTime + "毫秒");
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.TD_TRANS_ASSET_CUST_DETAIL_PAGE;

            } /*else if (action.equals(SrvWebActionConstant.INFORSYN) && !AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) {   //非TD
                long start = System.currentTimeMillis();
                String envCode = SynUpdateDateUtils.getEnvCode("TransAssetCustDetailSrv", conn);
                dtoParameter.setEnvCode(envCode);
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_PA_ASSETDETAIL);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步转资清单开始");
                logUtil.synLog(logDTO, conn);

                TransAssetCustDetailSrv srv = new TransAssetCustDetailSrv();

                srv.setEnvCode(dtoParameter.getEnvCode());
                srv.setBookTypeCode(dtoParameter.getBookTypeCode());
                srv.setProjectNumber(dtoParameter.getProjectNumber());
                srv.setTaskNum(dtoParameter.getTaskNum());
                srv.setCapitalizedDateFrom(dtoParameter.getCapitalizedDateFrom());
                srv.setCapitalizedDateTo(dtoParameter.getCapitalizedDateTo());

                srv.excute();

                SrvReturnMessage srvMessage = srv.getSrvMessage();
                if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_PA_ASSETDETAIL, conn);

                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_PA_ASSETDETAIL);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步转资清单成功！");
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_PA_ASSETDETAIL, conn);
                    message = new Message();
                    message.setMessageValue("同步转资清单成功，耗时" + resumeTime + "毫秒");
                } else {
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_PA_ASSETDETAIL);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步转资清单失败！");
                    logUtil.synLog(logDTO, conn);
                    message = new Message();
                    message.setMessageValue("同步转资清单失败，耗时" + resumeTime + "毫秒");
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.TRANS_ASSET_CUST_DETAIL_PAGE;
            }*/
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