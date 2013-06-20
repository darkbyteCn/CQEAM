package com.sino.soa.td.srv.vendor.servlet;

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
import com.sino.soa.td.srv.vendor.dao.TdSrvVendorInfoDAO;
import com.sino.soa.td.srv.vendor.dto.TdSrvVendorInfoDTO;
import com.sino.soa.td.srv.vendor.srv.TDInquiryVendorInfoSrv;
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
import com.sino.base.util.StrUtil;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * DATE：2011-09-15
 * user：wangzhipeng
 * function： 供应商同步_TD
 */
public class TDSrvVendorInfoSrvServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
	    Connection conn = null;
	    int count = 0;
	    long resumeTime = 0;
	    int errorCount = 0;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(TdSrvVendorInfoDTO.class.getName());
			TdSrvVendorInfoDTO dtoParameter = (TdSrvVendorInfoDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			String assetsType = StrUtil.nullToString(req.getParameter("assetsType"));
			dtoParameter.setAssetsType(assetsType);
			conn = getDBConnection(req);
			TdSrvVendorInfoDAO srvAssetBookDAO = new TdSrvVendorInfoDAO(user, dtoParameter, conn);
			req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = SrvURLDefineList.TD_SRV_VENDOR_INFO_PAGE;
			} else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
				TDInquiryVendorInfoSrv service=new TDInquiryVendorInfoSrv();
				service.setVendorName(dtoParameter.getVendorName());
				service.setVendorNumber(dtoParameter.getVendorNumber());
				service.setVatFlag(dtoParameter.getVatFlag());
				service.setVendorTypeDisp(dtoParameter.getVendorTypeDisp());
				if(dtoParameter.getLastUpdateDate()!=null){
					service.setLastUpdateDate(dtoParameter.getLastUpdateDate().toString());
				}
				service.execute();
				SrvReturnMessage srm=service.getReturnMessage();
				if(srm.getErrorFlag()!=null&&srm.getErrorFlag().equals("Y")){
					DTOSet ds=service.getDs();	
					req.setAttribute(SrvWebActionConstant.ASSETBOOKTRANSOU, ds);
					forwardURL = SrvURLDefineList.TD_SRV_VENDOR_INFO_PAGE;
				}else{
					message.setMessageValue("调用WebService服务失败："+srm.getErrorMessage());
					forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
				}
			} else if(action.equals(SrvWebActionConstant.INFORSYN)&& AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)){ //TD
				long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_TD_VENDOR);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步TD供应商信息开始");
                logUtil.synLog(logDTO, conn);

                TDInquiryVendorInfoSrv service=new TDInquiryVendorInfoSrv();
				service.setVendorName(dtoParameter.getVendorName());
				service.setVendorNumber(dtoParameter.getVendorNumber());
				service.setVatFlag(dtoParameter.getVatFlag());
				service.setVendorTypeDisp(dtoParameter.getVendorTypeDisp());
				service.setLastUpdateDate(dtoParameter.getLastUpdateDate().getCalendarValue());
				service.execute();
				SrvReturnMessage srm=service.getReturnMessage();
				if(srm.getErrorFlag().equals("Y")){
					SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_VENDOR, conn);
					DTOSet ds=service.getDs();
					if(ds.getSize()>0){
						count=srvAssetBookDAO.synVendorInfo(ds);
					}
					errorCount = srvAssetBookDAO.getErrorCount();
					resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_VENDOR);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD供应商信息结束,同步"+(count+errorCount)+"条记录，成功"+count+"，失败"+errorCount+"，耗时"+resumeTime+"毫秒");
                    logUtil.synLog(logDTO, conn);
                	SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_VENDOR, conn);
                    message=new Message();
                    message.setMessageValue("同步TD供应商信息 "+(count+errorCount)+"条记录，成功"+count+"，失败"+errorCount+"，耗时"+resumeTime+"毫秒");
				} else {
					resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_VENDOR);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD供应商信息失败, 耗时"+resumeTime+"毫秒,出错信息:"+srm.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message=new Message();
                    message.setMessageValue("同步TD供应商信息失败, 耗时"+resumeTime+"毫秒,出错信息:"+srm.getErrorMessage());
				}
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = SrvURLDefineList.TD_SRV_VENDOR_INFO_PAGE;
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
