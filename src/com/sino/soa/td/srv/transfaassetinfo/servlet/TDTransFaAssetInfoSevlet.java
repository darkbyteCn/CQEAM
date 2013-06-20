package com.sino.soa.td.srv.transfaassetinfo.servlet;

import com.sino.ams.bean.OrgOptionProducer;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.TimeException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.mis.srv.transfaassetinfo.dto.TransFaAssetInfoDTO;  
import com.sino.soa.td.srv.transfaassetinfo.srv.TDTransFaAssetInfoSrv;    
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
 * <p>Title: TransAssetDistributionSevlet</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author wangzp
 * @version 1.0
 * 查询资产财务信息服务(ODI)
 */

public class TDTransFaAssetInfoSevlet extends BaseServlet {

	
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        boolean autoCommit = true;
        long resumeTime = 0;
        try {
            conn = getDBConnection(req);
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(TransFaAssetInfoDTO.class.getName());
            TransFaAssetInfoDTO dtoParameter = (TransFaAssetInfoDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String assetsType = StrUtil.nullToString(req.getParameter("assetsType"));
            OrgOptionProducer optionProducer = new OrgOptionProducer(user, conn);
            String range = assetsType.equals("TD") ? "Y" : "N";
            String opt = optionProducer.getBookTypeCodeOption(dtoParameter.getBookTypeCode(),range);  //资产账簿(2012-02-17新加)
            dtoParameter.setOrgOption(opt);
            dtoParameter.setAssetsType(assetsType);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.TD_TRANS_FAASSET_PAGE;
            } else if (action.equals(SrvWebActionConstant.INFORSYN) && AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) { //TD
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                DTOSet ds = new DTOSet();
                ds.addDTO(dtoParameter);
                String envCode = SynUpdateDateUtils.getEnvCode("TDTransFaAssetInfoSrv", conn);   //
                TDTransFaAssetInfoSrv service = new TDTransFaAssetInfoSrv();
                service.setEnvCode(envCode);
                service.setPeriodName(dtoParameter.getPeriodName());
                //此处需要增加资产账簿0217
                service.setBookTypeCode(dtoParameter.getBookTypeCode());
                service.excute();
                SrvReturnMessage srvMessage = service.getReturnMessage();
                resumeTime = System.currentTimeMillis() - start;
                message = new Message();
                if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_FA_TRANS_ASSET, conn);          
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_FA_TRANS_ASSET);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步'TD资产财务信息(ODI)'成功, 耗时" + resumeTime + "毫秒, 会计期间:"+dtoParameter.getPeriodName());
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_FA_TRANS_ASSET, conn);
                    message.setMessageValue("同步'TD资产财务信息(ODI)'成功, 耗时" + resumeTime + "毫秒");
                } else {
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_TRANS_ASSET);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步'TD资产财务信息(ODI)'失败, 耗时" + resumeTime + "毫秒, 会计期间:"+dtoParameter.getPeriodName()+", 错误信息："+srvMessage.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message.setMessageValue("同步'TD资产财务信息(ODI)'失败, 耗时" + resumeTime + "毫秒, 错误信息："+srvMessage.getErrorMessage());
                }
                forwardURL = SrvURLDefineList.TD_TRANS_FAASSET_PAGE;
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