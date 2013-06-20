package com.sino.soa.mis.srv.PageInquiryAssetHeaderInfo.servlet;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;
import com.sino.ams.newasset.bean.AssetsOptProducer;
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
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.mis.srv.PageInquiryAssetHeaderInfo.dao.PageInquiryAssetHeaderInfoDAO;   
import com.sino.soa.mis.srv.PageInquiryAssetHeaderInfo.dto.InquiryAssetHeaderInfoSrvDTO;    
import com.sino.soa.mis.srv.PageInquiryAssetHeaderInfo.srv.PageInquiryAssetHeaderInfoSrv;   

/**
 * <p>Title: SrvRetiredAssetDetailServlet</p>
 * <p>Description:程序自动生成服务程序“SrvRetiredAssetDetailServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * user:wangzp
 * function:查询资产头基本信息（分页）
 */

public class PageInquiryAssetHeaderInfoServlet extends BaseServlet {

    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        Connection conn = null;
        int count = 0;
        int errerCount = 0;
        long resumeTime = 0;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(InquiryAssetHeaderInfoSrvDTO.class.getName());
            InquiryAssetHeaderInfoSrvDTO dtoParameter = (InquiryAssetHeaderInfoSrvDTO) req2DTO.getDTO(req);
            String action = dtoParameter.getAct();
            String assetsType = req.getParameter("assetsType");
            conn = getDBConnection(req);
            dtoParameter.setAssetsType(assetsType);
            PageInquiryAssetHeaderInfoDAO retiredAssetDetailDAO = new PageInquiryAssetHeaderInfoDAO(user, dtoParameter, conn);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
            String opt = optProducer.getBookTypeOption(dtoParameter.getBookTypeCode());
            dtoParameter.setOrgOption(opt);
            if (action.equals("")) {
            	req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.SRV_ASSET_HEADER_PAGE;
            } else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
            	PageInquiryAssetHeaderInfoSrv service = new PageInquiryAssetHeaderInfoSrv();
                service.setBookTypeCode(dtoParameter.getBookTypeCode());   //资产账簿
                service.setTagNumber(dtoParameter.getTagNumber());
                service.setStartLastUpdateDate(dtoParameter.getStartLastUpdateDate());
                service.setEndLastUpdateDate(dtoParameter.getEndLastUpdateDate());
                service.excute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag() != null && srm.getErrorFlag().equals("Y")) {
                    DTOSet ds = service.getDs();
                    req.setAttribute(SrvWebActionConstant.ASSETBOOKTRANSOU, ds);
                    forwardURL = SrvURLDefineList.SRV_ASSET_HEADER_PAGE;
                } else {
                    message.setMessageValue("调用WebService服务失败：" + srm.getErrorMessage());
                    req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                    forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
                }
            } else if (action.equals(SrvWebActionConstant.INFORSYN)&& !AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) { //非TD
                long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_FA_HEADER_ASSET);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步资产头信息开始");
                logUtil.synLog(logDTO, conn);
                PageInquiryAssetHeaderInfoSrv service = new PageInquiryAssetHeaderInfoSrv();
                service.setBookTypeCode(dtoParameter.getBookTypeCode());
                service.setTagNumber(dtoParameter.getTagNumber());    //4187-00000002 
                service.setStartLastUpdateDate(dtoParameter.getStartLastUpdateDate());
                service.setEndLastUpdateDate(dtoParameter.getEndLastUpdateDate());
                service.excute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag().equals("Y")) {
                	SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_FA_HEADER_ASSET, conn);
                    DTOSet ds = service.getDs();
                    if (ds.getSize() > 0) {
                        count = retiredAssetDetailDAO.saveRetireAssets(ds);
                    } 
                    errerCount = retiredAssetDetailDAO.getErrorCount();
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_HEADER_ASSET);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步资产头信息结束！");
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_FA_HEADER_ASSET, conn);
                    message=new Message();
                    message.setMessageValue("同步"+(errerCount+count)+"条记录，成功"+count+"，失败"+(errerCount)+"，耗时"+resumeTime+"毫秒");
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.SRV_ASSET_HEADER_PAGE;
                
            } else if(action.equals(SrvWebActionConstant.INFORSYN_EAM)){  //同步到EAM表
                 boolean isSyn=retiredAssetDetailDAO.assetHeaderODI();
         	     message=new Message();
               if(isSyn){
                   message.setMessageValue("调用'资产头信息'存储过程成功!"); 
               }else{
            	   message.setMessageValue("调用'资产头信息'存储过程失败！请检查!"); 
               }
               	req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.SRV_ASSET_HEADER_PAGE;
            	
            }/*else if (action.equals(SrvWebActionConstant.INFORSYN)&& AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) {  //TD
            	long start = System.currentTimeMillis();
                
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_TD_FA_RETIRE);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步TD报废资产开始");
                logUtil.synLog(logDTO, conn);
                
                PageInquiryRetiredAssetDetailSrv service = new PageInquiryRetiredAssetDetailSrv();
                
                service.setBookTypeCode(dtoParameter.getBookTypeCode());
                service.setAssetNumber(dtoParameter.getAssetNumber());
                service.setLocalionDep(dtoParameter.getLocalionDep());
                service.setTagNumber(dtoParameter.getTagNumber());
                service.setRetirementTypeCode(dtoParameter.getRetirementTypeCode());
                
                service.setDateEffectiveFrom(dtoParameter.getDateEffectiveFrom());
                service.setDateEffectiveTo(dtoParameter.getDateEffectiveTo());
                service.setDateRettredFrom(dtoParameter.getDateRettredFrom());
                service.setDateRettredTo(dtoParameter.getDateRettredTo());
                service.excute();
                SrvReturnMessage srm = service.getReturnMessage();
                if (srm.getErrorFlag().equals("Y")) {
                	SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_FA_RETIRE, conn);
                    DTOSet ds = service.getDs();
                    if (ds.getSize() > 0) {
                        count = retiredAssetDetailDAO.saveRetireAssets(ds);
                    } 
                    errerCount = retiredAssetDetailDAO.getErrorCount();
                    resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_FA_RETIRE);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD报废资产结束！");
                    logUtil.synLog(logDTO, conn);
                    SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_FA_RETIRE, conn);
                    message=new Message();
                    message.setMessageValue("同步"+(errerCount+count)+"条记录，成功"+count+"，失败"+(errerCount)+"，耗时"+resumeTime+"毫秒");
                }
                req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
                forwardURL = SrvURLDefineList.SRV_Retired_Asset_Detail_PAGE;
            }*/
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