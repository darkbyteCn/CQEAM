package com.sino.soa.mis.srv.transTaskInfo.servlet;

import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.soa.common.SrvReturnMessage;
import com.sino.soa.common.SrvType;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.mis.srv.transTaskInfo.dao.PageInquiryTaskinfoDAO;    
import com.sino.soa.mis.srv.transTaskInfo.dto.PageInquiryTaskinfoDTO;    
import com.sino.soa.mis.srv.transTaskInfo.srv.PageInquiryTaskInfoSrv;    
import com.sino.soa.util.SynLogUtil;
import com.sino.soa.util.SynUpdateDateUtils;
import com.sino.soa.util.dto.SynLogDTO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>Title: PageInquiryTaskinfoServlet</p>
 * <p>Description:程序自动生成服务程序“PageInquiryTaskinfoServlet”</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author wangzp
 * 查询项目任务信息服务(分页) 
 */

public class PageInquiryTaskinfoServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = SrvURLDefineList.SRV_PROJECTTASK1_PAGE;
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		boolean autoCommit = true;
		int errorCount = 0;
		int totalCount = 0;
		long resumeTime = 0;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(PageInquiryTaskinfoDTO.class.getName());
			PageInquiryTaskinfoDTO dtoParameter = (PageInquiryTaskinfoDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			String assetsType = StrUtil.nullToString(req.getParameter("assetsType"));
			dtoParameter.setAssetsType(assetsType);
			conn = getDBConnection(req);
			autoCommit = conn.getAutoCommit();
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = SrvURLDefineList.SRV_PROJECTTASK1_PAGE;
			} else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION)&& !AssetsWebAttributes.TD_ASSETS_TYPE.equals(assetsType)) {  //非TD
				long start = System.currentTimeMillis();
				SynLogDTO logDTO = null;
				SynLogUtil logUtil = new SynLogUtil();
				logDTO = new SynLogDTO();
				logDTO.setSynType(SrvType.SRV_PA_TASK);
				logDTO.setCreatedBy(user.getUserId());
				logDTO.setSynMsg("同步项目任务信息(分页)开始");
				logUtil.synLog(logDTO, conn);
				
				PageInquiryTaskInfoSrv projectTaskInfoSrv = new PageInquiryTaskInfoSrv();
				projectTaskInfoSrv.setProjectNum(dtoParameter.getSegment1());
				projectTaskInfoSrv.setTaskNum(dtoParameter.getTaskNumber());
				projectTaskInfoSrv.excute();
				SrvReturnMessage srvMessage = projectTaskInfoSrv.getReturnMessage();
				if (srvMessage.getErrorFlag().equalsIgnoreCase("Y")) {
					SynUpdateDateUtils.createLastUpdateDate(SrvType.SRV_PA_TASK, conn);
					DTOSet ds = projectTaskInfoSrv.getDs();
					for (int i = 0; i < ds.getSize(); i++) {
						PageInquiryTaskinfoDTO dto = (PageInquiryTaskinfoDTO) ds.getDTO(i);
						PageInquiryTaskinfoDAO srvTaskinfoDAO = new PageInquiryTaskinfoDAO(user, dto, conn);
					    try {
							//if(SynUpdateDateUtils.getBetweenDays(SynUpdateDateUtils.getLastUpdateDate(SrvType.SRV_PA_TASK, conn),(dto.getLastUpdateDate().toString()))>0){
								if (srvTaskinfoDAO.isProjectTaskExists(dto.getTaskNumber(),dto.getSegment1())) {
									srvTaskinfoDAO.updateData();
								} else{
									srvTaskinfoDAO.createData();
								}
								totalCount++;
							//}
					    } catch (DataHandleException e) {
					        Logger.logError(e);
					        logDTO = new SynLogDTO();
					        logDTO.setSynType(SrvType.SRV_PA_TASK);
					        logDTO.setCreatedBy(user.getUserId());
					        logDTO.setSynMsg(e.toString());
					        logUtil.synLog(logDTO, conn);
					        errorCount++;
					    }/*catch (ContainerException e) {
					    	Logger.logError(e);
					    	logDTO.setSynMsg(e.toString());
						} catch (ParseException e) {
							Logger.logError(e);
							logDTO.setSynMsg(e.toString());
						}*/
					}
				}
				SynUpdateDateUtils.updateLastUpdateDate(SrvType.SRV_PA_TASK, conn);
				resumeTime = System.currentTimeMillis() - start;
				logDTO = new SynLogDTO();
				logDTO.setSynType(SrvType.SRV_PA_TASK);
				logDTO.setCreatedBy(user.getUserId());
				logDTO.setSynMsg("同步项目任务信息(分页)结束！");
				logUtil.synLog(logDTO, conn);
				message=new Message();
				message.setMessageValue("同步"+(totalCount+errorCount)+"条记录，成功"+totalCount+"，失败"+errorCount+"，耗时"+resumeTime+"毫秒");
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = SrvURLDefineList.SRV_PROJECTTASK1_PAGE;
	                
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
		}catch (SQLException e) {
            e.printStackTrace();  
        } catch (CalendarException e) {
            e.printStackTrace();
        } finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}