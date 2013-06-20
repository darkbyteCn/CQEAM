package com.sino.soa.mis.srv.assetsinfoupdate.servlet;

import com.sino.ams.bean.OrgOptionProducer;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
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
import com.sino.base.util.StrUtil;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.mis.srv.assetsinfoupdate.dto.SrvAccountBalanceDTO;
import com.sino.soa.mis.srv.assetsinfoupdate.srv.TransAssetHeaderInfoSrv;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;
//import com.sino.soa.td.srv.assetsinfoupdate.srv.TDTransAssetHeaderInfoSrv;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>Title: TransAssetHeaderInfoServlet</p>
 * <p>Description:资产头基本信息同步读取类ODI“TransAssetHeaderInfoServlet”</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author wangzp
 * @version 1.0
 * 
 */
public class TransAssetHeaderInfoServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = SrvURLDefineList.SRV_TRANSASSETHEADERINFO;
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        boolean autoCommit = true;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(SrvAccountBalanceDTO.class.getName());
            SrvAccountBalanceDTO dtoParameter = (SrvAccountBalanceDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String assetsType = StrUtil.nullToString(req.getParameter("assetsType"));
            String range = assetsType.equals("TD") ? "Y" : "N";
            conn = getDBConnection(req);
            autoCommit = conn.getAutoCommit();
            long resumeTime = 0;
            OrgOptionProducer optionProducer = new OrgOptionProducer(user, conn);
            String bookType = optionProducer.getBookTypeCodeOption(dtoParameter.getBookTypeCode(),range);
            dtoParameter.setBookTypeCode(bookType);
            dtoParameter.setAssetsType(assetsType);
            req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
            if (action.equals("")) {
                forwardURL = SrvURLDefineList.SRV_TRANSASSETHEADERINFO;
            } else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION) && !AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) { //非TD
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();

                String envCode = SynUpdateDateUtils.getEnvCode("TransAssetHeaderInfoSrv", conn);
                String bookTypeCode = req.getParameter("bookTypeCode");
                String startLastUpdateDate = req.getParameter("startLastUpdateDate");
                String endLastUpdateDate = req.getParameter("endLastUpdateDate");

                TransAssetHeaderInfoSrv transAssetSrv = new TransAssetHeaderInfoSrv();
                transAssetSrv.setBookTypeCode(bookTypeCode);
                transAssetSrv.setStartLastUpdateDate(startLastUpdateDate);
                transAssetSrv.setEndLastUpdateDate(endLastUpdateDate);
                transAssetSrv.setEnvCode(envCode);
                transAssetSrv.excute();
                SrvReturnMessage srvMessage = transAssetSrv.getReturnMessage();

                resumeTime = System.currentTimeMillis() - start;
                message = new Message();
                if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
                    SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_FA_HEADERINFO, conn);
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_HEADERINFO);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS资产头信息(ODI)成功,耗时" + resumeTime + "毫秒,资产账簿:"+bookTypeCode);
                    logUtil.synLog(logDTO, conn);
                    message.setMessageValue("同步MIS资产头信息(ODI)成功,耗时" + resumeTime + "毫秒");
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_FA_HEADERINFO, conn);
                } else {
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_HEADERINFO);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS资产头信息(ODI)失败,耗时" + resumeTime + "毫秒,资产账簿:"+bookTypeCode+" ,错误信息："+srvMessage.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message.setMessageValue("同步MIS资产头信息(ODI)失败，" + "耗时" + resumeTime + "毫秒, 错误信息："+srvMessage.getErrorMessage());
                }
//                String bookType = optionProducer.getBookTypeCodeOption(bookTypeCode);
//                dtoParameter.setBookTypeCode(bookType);
//            	req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.SRV_TRANSASSETHEADERINFO;
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