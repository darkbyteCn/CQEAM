package com.sino.soa.mis.srv.transassetdistribution.servlet;

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
import com.sino.soa.mis.srv.transassetdistribution.dto.SrvAssetDistributionDTO;
import com.sino.soa.mis.srv.transassetdistribution.srv.TransAssetDistributionSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;
//import com.sino.soa.td.srv.transassetdistribution.srv.TDTransAssetDistributionSrv;   //TD
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.sql.Connection;

/**
 * <p>Title: TransAssetDistributionSevlet</p>
 * <p>Description:资产分配行读取服务类“TransAssetDistributionSevlet”</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author wangzp
 * @version 1.0
 * function:查询资产分配行信息_非TD(ODI)
 */

public class TransAssetDistributionSevlet extends BaseServlet {

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
        boolean autoCommit = true;
        long resumeTime = 0;
        try {
            conn = getDBConnection(req);
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SrvAssetDistributionDTO.class.getName());
            SrvAssetDistributionDTO dtoParameter = (SrvAssetDistributionDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String assetsType = StrUtil.nullToString(req.getParameter("assetsType"));
            OrgOptionProducer optionProducer = new OrgOptionProducer(user, conn);
            String range = assetsType.equals("TD") ? "Y" : "N";
            dtoParameter.setAssetsType(assetsType);
            String opt = optionProducer.getBookTypeCodeOption(dtoParameter.getBookTypeCode(),range);
            dtoParameter.setOrgOption(opt);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.TRANS_ASSET_DISTRIBUTION_PAGE;
            } else if (action.equals(SrvWebActionConstant.INFORSYN) && !AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) { //非TD
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                DTOSet ds = new DTOSet();
                ds.addDTO(dtoParameter);

                String envCode = SynUpdateDateUtils.getEnvCode("TransAssetDistributionSrv", conn);
                TransAssetDistributionSrv service = new TransAssetDistributionSrv();
                service.setEnvCode(envCode);
                service.setBookTypeCode(dtoParameter.getBookTypeCode());
                if (StrUtil.isNotEmpty(dtoParameter.getEndLastUpdateDate())) {
                    service.setStratLastUpdateDate(dtoParameter.getStratLastUpdateDate());
                }
                if (StrUtil.isNotEmpty(dtoParameter.getEndLastUpdateDate())) {
                    service.setEndLastUpdateDate(dtoParameter.getEndLastUpdateDate());
                }
                service.excute();
                SrvReturnMessage srvMessage = service.getReturnMessage();
                resumeTime = System.currentTimeMillis() - start;
                message = new Message();
                if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_FA_DISTRIBUTION, conn);
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_DISTRIBUTION);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS资产分配行信息(ODI)成功。耗时" + resumeTime + "毫秒，资产账簿：" + dtoParameter.getBookTypeCode());
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_FA_DISTRIBUTION, conn);
                    message.setMessageValue("同步MIS资产分配行信息(ODI)成功，" + "耗时" + resumeTime + "毫秒");
                } else {
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_DISTRIBUTION);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS资产分配行信息(ODI)失败。耗时" + resumeTime + "毫秒，资产账簿：" + dtoParameter.getBookTypeCode()+"，错误信息: "+srvMessage.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message.setMessageValue("同步MIS资产分配行信息(ODI)失败，" + "耗时" + resumeTime + "毫秒，资产账簿：" + dtoParameter.getBookTypeCode()+"，错误信息: "+srvMessage.getErrorMessage());
                }
                forwardURL = SrvURLDefineList.TRANS_ASSET_DISTRIBUTION_PAGE;
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