package com.sino.soa.mis.srv.inquiryassetlocation.servlet;

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
import com.sino.soa.mis.srv.inquiryassetlocation.dao.SrvAssetLocationDAO;
import com.sino.soa.mis.srv.inquiryassetlocation.dto.SrvAssetLocationDTO;
import com.sino.soa.mis.srv.inquiryassetlocation.srv.SrvAssetLocationSrv;
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
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.TimeException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SrvAssetCategoryServlet</p>
 * <p>Description:程序自动生成服务程序“SrvAssetCategoryServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * date:2011-09-08
 * DSC:同步MIS资产地点
 */

public class SrvAssetLocationServlet extends BaseServlet {

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
			req2DTO.setDTOClassName(SrvAssetLocationDTO.class.getName());
			SrvAssetLocationDTO dtoParameter = (SrvAssetLocationDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			String assetsType = req.getParameter("assetsType");                  //URL传参
			dtoParameter.setAssetsType(assetsType);
			conn = getDBConnection(req);
			SrvAssetLocationDAO srvAssetCategoryDAO = new SrvAssetLocationDAO(user, dtoParameter, conn);
			req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = SrvURLDefineList.SRV_ASSET_LOCATION_PAGE;
			} else if (action.equals(SrvWebActionConstant.QUERY_ACTION)) {
				SrvAssetLocationSrv service = new SrvAssetLocationSrv();
				String lastUpdateDate=dtoParameter.getLastUpdateDate();
				String endLastUpdateDate = dtoParameter.getEndLastUpDate();
				if(lastUpdateDate!=null&&!lastUpdateDate.equals(""))
					service.setLastUpDate(lastUpdateDate);
				if(endLastUpdateDate!=null&&!endLastUpdateDate.equals(""))
					service.setEndLastUpDate(endLastUpdateDate);
				service.setSegment1(dtoParameter.getSegment1());
				service.setSegment2(dtoParameter.getSegment2());
				service.setSegment3(dtoParameter.getSegment3());
				service.setLocationCombinationCode(dtoParameter.getLocationCombinationCode());
				service.setLocationCombinationName(dtoParameter.getLocationCombinationName());
				service.setEnabledFlag(dtoParameter.getEnabledFlag());
				service.excute();
				SrvReturnMessage srm=service.getReturnMessage();
				if(srm.getErrorFlag()!=null&&srm.getErrorFlag().equals("Y")){
					DTOSet ds=service.getDs();
					req.setAttribute(SrvWebActionConstant.ASSETBOOKTRANSOU, ds);
					forwardURL = SrvURLDefineList.SRV_ASSET_LOCATION_PAGE;
				}else{
					message.setMessageValue("调用WebService服务失败："+srm.getErrorMessage());
					forwardURL = SrvURLDefineList.MESSAGE_PRINT_PUB;
				}
			}else if (action.equals(SrvWebActionConstant.INFORSYN)&& !AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) { //非TD
				long start = System.currentTimeMillis();
                SynLogDTO logDTO = null;
                SynLogUtil logUtil = new SynLogUtil();
                logDTO = new SynLogDTO();
                logDTO.setSynType(SrvType.SRV_FA_LOCATION);
                logDTO.setCreatedBy(user.getUserId());
                logDTO.setSynMsg("同步MIS资产地址开始");
                logUtil.synLog(logDTO, conn);
				SrvAssetLocationSrv service = new SrvAssetLocationSrv();
				String lastUpdateDate=dtoParameter.getLastUpdateDate();
				String endLastUpdateDate = dtoParameter.getEndLastUpDate();
				if(lastUpdateDate!=null&&!lastUpdateDate.equals(""))
				{
				  service.setLastUpDate(lastUpdateDate);          //最后更新开始日期 
				}
				if(endLastUpdateDate!=null&&!endLastUpdateDate.equals(""))
				{
				  service.setEndLastUpDate(endLastUpdateDate);    //最后更新结束日期
				}
				service.setSegment1(dtoParameter.getSegment1()); //所属部门
				service.excute();
				SrvReturnMessage srm=service.getReturnMessage();
				if(srm.getErrorFlag().equals("Y")){
					SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_FA_LOCATION, conn);
					DTOSet ds=service.getDs();
					if(ds.getSize()>0){
						count = srvAssetCategoryDAO.synAssetLocation(ds,dtoParameter.getSegment1());
					}
					resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_LOCATION);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS资产地点结束。本次同步"+(ds.getSize())+"条记录，成功"+count+"，失败"+(ds.getSize()-count)+"，耗时"+resumeTime+"毫秒，所属部门: "+dtoParameter.getSegment1());
                    logUtil.synLog(logDTO, conn);
            		SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_FA_LOCATION, conn);
                    message=new Message();
                    message.setMessageValue("同步MIS资产地点 "+(ds.getSize())+"条记录，成功"+count+"，失败"+(ds.getSize()-count)+"，耗时"+resumeTime+"毫秒，所属部门: "+dtoParameter.getSegment1());
				}else{
					SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_FA_LOCATION, conn);
					resumeTime = System.currentTimeMillis() - start;
                    logDTO = new SynLogDTO();
                    logDTO.setSynType(SrvType.SRV_FA_LOCATION);
                    logDTO.setCreatedBy(user.getUserId());
                    logDTO.setSynMsg("同步MIS资产地点失败。耗时" + resumeTime + "毫秒, 所属部门:"+dtoParameter.getSegment1()+", 错误信息：" + srm.getErrorMessage());
                    logUtil.synLog(logDTO, conn);
                    message=new Message();
                    message.setMessageValue("同步MIS资产地址失败。耗时" + resumeTime + "毫秒, 所属部门:"+dtoParameter.getSegment1()+", 错误信息：" + srm.getErrorMessage());
				}
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = SrvURLDefineList.SRV_ASSET_LOCATION_PAGE;
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