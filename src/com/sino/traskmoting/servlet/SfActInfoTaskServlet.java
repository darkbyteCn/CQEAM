package com.sino.traskmoting.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;
import com.sino.traskmoting.dto.SfActInfoDTO;
import com.sino.traskmoting.model.SfActInfoModel;


/**
 * <p>Title: SfActInfoServlet</p>
 * <p>Description:程序自动生成服务程序“SfActInfoServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */


public class SfActInfoTaskServlet extends BaseServlet {

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
		
		try {
			SfUserBaseDTO user = (SfUserBaseDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfActInfoDTO.class.getName());
			SfActInfoDTO dtoParameter = (SfActInfoDTO)req2DTO.getDTO(req);
			SfActInfoModel sfActInfoModel=new SfActInfoModel(user,dtoParameter);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			
			WebPageView pageView = new WebPageView(req, conn);
			pageView.setDTOClassName(SfActInfoDTO.class.getName());
			pageView.setPageSize(100);
			if (action.equals("")) {
				forwardURL =URLDefineList.TRASK_MOTING_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
		        String checkName=req.getParameter("checkValue");
		         if(checkName.equals("users"))
		         {   
		        	
//		        	 dtoSet=sfActInfoDAO.getTraskUserMotingModel(conn);
//		        	 req.setAttribute("dtoParameter", dtoParameter);
		        	 pageView.produceWebData(sfActInfoModel.getTraskUserMotingModel());
		        	 forwardURL =URLDefineList.TRASK_USER_MONITING_PAGE;
		         } if(checkName.equals("role"))
		         {
		        	 
		        	 pageView.produceWebData(sfActInfoModel.getTraskRoleMotingModel());
		        	 forwardURL =URLDefineList.TRASK_ROLE_MONITING_PAGE; 
		         } if(checkName.equals("traskName"))
		         {
		        	 pageView.produceWebData(sfActInfoModel.getTraskNameMotingModel());
		        	 forwardURL =URLDefineList.TRASK_NAME_MONITING_PAGE;
		         } 
		         if(checkName.equals("Otusers"))
		         {
		        	 pageView.produceWebData(sfActInfoModel.getTraskOtUserMotingModel());
		        	 forwardURL =URLDefineList.TRASK_OT_USER_MONITING_PAGE;
		         } if(checkName.equals("Otrole"))
		         {
		        	 pageView.produceWebData(sfActInfoModel.getTraskOtRoleMotingModel());
		        	 forwardURL =URLDefineList.TRASK_OT_ROLE_MONITING_PAGE;
		         }if(checkName.equals("traskOtName"))
		         {
		        	 pageView.produceWebData(sfActInfoModel.getTraskOtNameMotingModel());
		        	 forwardURL =URLDefineList.TRASK_OT_NAME_MONITING_PAGE;
		         }
                 if(checkName.equals("Enote"))
                 {
                     pageView.produceWebData(sfActInfoModel.getTraskEnoteMotingModel());
                     forwardURL =URLDefineList.TRASK_ENOTE_MONITING_PAGE;                     
                 }
//		        	 if(checkName.equals("chuban"))
//		         {
////		        	 pageView.produceWebData(sfActInfoModel.getTraskUserMotingModel());
//		        	 forwardURL =URLDefineList.TRASK_CHU_BAN_PAGE;
//		         }
			
			}else if(action.equals(WebActionConstant.DETAIL_ACTION))
			{
				
				pageView.produceWebData(sfActInfoModel.getPrimaryKeyMointingModel(req));
//				DTOSet ds = (DTOSet) req.getAttribute(QueryConstant.SPLIT_DATA_VIEW);
				forwardURL =URLDefineList.TRASK_MOINTING_DETAIL_PAGE;
			}else {
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
		}   finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}