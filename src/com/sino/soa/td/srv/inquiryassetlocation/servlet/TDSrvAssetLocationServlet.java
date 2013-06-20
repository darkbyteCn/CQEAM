package com.sino.soa.td.srv.inquiryassetlocation.servlet;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
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
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.common.SrvWebActionConstant;
import com.sino.soa.td.srv.inquiryassetlocation.dao.TdSrvAssetLocationDAO;
import com.sino.soa.td.srv.inquiryassetlocation.dto.TdSrvAssetLocationDTO;
import com.sino.soa.td.srv.inquiryassetlocation.srv.TDSrvAssetLocationSrv;
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;

/**
 * date：Sep 15, 2011 9:39:57 PM
 * author：wzp
 * function：同步MIS资产地点_TD
 */
public class TDSrvAssetLocationServlet extends BaseServlet {
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
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(TdSrvAssetLocationDTO.class.getName());
			TdSrvAssetLocationDTO dtoParameter = (TdSrvAssetLocationDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			String assetsType = req.getParameter("assetsType");   //URL传参:TD
			dtoParameter.setAssetsType(assetsType);
			conn = getDBConnection(req);
			TdSrvAssetLocationDAO srvAssetCategoryDAO = new TdSrvAssetLocationDAO(user, dtoParameter, conn);
			req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = SrvURLDefineList.TD_SRV_ASSET_LOCATION_PAGE;
			} else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
				TDSrvAssetLocationSrv service = new TDSrvAssetLocationSrv();
				String lastUpdateDate=dtoParameter.getLastUpdateDate();
				String endLastUpdateDate = dtoParameter.getEndLastUpDate();
				if(lastUpdateDate!=null&&!lastUpdateDate.equals(""))
					service.setLastUpDate(lastUpdateDate);
				if(endLastUpdateDate!=null&&!endLastUpdateDate.equals(""))
					service.setEndLastUpDate(endLastUpdateDate);
				service.setSegment1(dtoParameter.getSegment1());
				service.execute();
				SrvReturnMessage srm=service.getReturnMessage();
				if(srm.getErrorFlag()!=null&&srm.getErrorFlag().equals("Y")){
					DTOSet ds=service.getDs();
					req.setAttribute(SrvWebActionConstant.ASSETBOOKTRANSOU, ds);
					forwardURL = SrvURLDefineList.TD_SRV_ASSET_LOCATION_PAGE;
				}else{
					message.setMessageValue("调用WebService服务失败："+srm.getErrorMessage());
					forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
				}
			} else if(action.equals(SrvWebActionConstant.INFORSYN)&& AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)){ //TD
				long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_TD_FA_LOCATION);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步TD资产地址开始");
                logUtil.synLog(logDTO, conn);	
                TDSrvAssetLocationSrv service = new TDSrvAssetLocationSrv();
				String lastUpdateDate=dtoParameter.getLastUpdateDate();
				String endLastUpdateDate = dtoParameter.getEndLastUpDate();
				if(lastUpdateDate!=null&&!lastUpdateDate.equals(""))
					service.setLastUpDate(lastUpdateDate);
				if(endLastUpdateDate!=null&&!endLastUpdateDate.equals(""))
					service.setEndLastUpDate(endLastUpdateDate);
				service.setSegment1(dtoParameter.getSegment1());  
				service.execute();
				SrvReturnMessage srm=service.getReturnMessage();
				if(srm.getErrorFlag().equals("Y")){
					SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_FA_LOCATION, conn);
					DTOSet ds=service.getDs();
					if(ds.getSize()>0){
						count = srvAssetCategoryDAO.synTdAssetLocation(ds,dtoParameter.getSegment1());
					}
					resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_FA_LOCATION);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD资产地点结束。同步"+(ds.getSize())+"条记录，成功"+count+"，失败"+(ds.getSize()-count)+"，耗时"+resumeTime+"毫秒，所属部门: "+dtoParameter.getSegment1());
                    logUtil.synLog(logDTO, conn);
            		SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_TD_FA_LOCATION, conn);
                    message=new Message();
                    message.setMessageValue("同步TD资产地点 "+(ds.getSize())+"条记录，成功"+count+"，失败"+(ds.getSize()-count)+"，耗时"+resumeTime+"毫秒，所属部门: "+dtoParameter.getSegment1());
				} else{
					SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_TD_FA_LOCATION, conn);
					resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_TD_FA_LOCATION);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步TD资产地点失败。耗时" + resumeTime + "毫秒，所属部门: "+dtoParameter.getSegment1()+"，错误信息：" + srm.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message=new Message();
                    message.setMessageValue("同步TD资产地点失败。耗时" + resumeTime + "毫秒，所属部门: "+dtoParameter.getSegment1()+"，错误信息：" + srm.getErrorMessage());	
				}
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = SrvURLDefineList.TD_SRV_ASSET_LOCATION_PAGE;
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
