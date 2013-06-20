package com.sino.hn.portal.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.config.PoolConfig;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.hn.constant.PortalConstant;
import com.sino.hn.portal.dao.UserPortalDAO;

/**********************************************
 * 
 * @系统名称:
 * @功能描述: 单点登录
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Oct 28, 2011
 */
public final class PortalLoginServlet extends BaseServlet {
 
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Connection conn = null;
		Request2DTO req2DTO = new Request2DTO();
		String forwardURL = "";
		
		String is_td = "";
		boolean todo = false;

		String portalUserId = req.getParameter( PortalConstant.PARAM_UID_NAME );
		String srcPage = req.getParameter( PortalConstant.PARAM_SOURCE_NAME );
//		String url = req.getParameter( PortalConstant.PARAM_TODO_URL_NAME );
		
		String uid = req.getParameter( PortalConstant.TODO_UID );
		is_td = StrUtil.nullToString( req.getParameter( PortalConstant.TODO_IS_TD ) );
		
		StringBuilder todoUrl = new StringBuilder();
		//加入是待办/已办过来的
		if( !StrUtil.isEmpty( uid ) ){
			todo = true ;
			String sf_actID = req.getParameter( PortalConstant.TODO_SF_ACT_ID );
			String sf_appMask = req.getParameter( PortalConstant.TODO_SF_APP_MASK );
			String sf_caseID = req.getParameter( PortalConstant.TODO_CASE_ID );
			is_td = StrUtil.nullToString( req.getParameter( PortalConstant.TODO_IS_TD ) );
			
			todoUrl.append( "/servlet/com.sino.sinoflow.servlet.ProcessCase?" );
			todoUrl.append( "sf_actID=" + sf_actID  );
			todoUrl.append( "&sf_appMask=" + sf_appMask );
			todoUrl.append( "&sf_caseID=" + sf_caseID );
			
			portalUserId = uid;
		}
		
		BaseUserDTO userAccount = null;
		Message message = null;
		
		FilterConfigDTO filterDTO;
		try {
			filterDTO = SessionUtil.getFilterConfigDTO(req);
			forwardURL = filterDTO.getLoginUrl();

			conn = getDBConnection(req);
			if (conn == null) {
				message = getMessage(MsgKeyConstant.INVALID_CONN_POOL);
				PoolConfig poolConfig = getPoolConfig();
				if (poolConfig != null) {
					message.addParameterValue(poolConfig.toString());
				}
				message.setIsError(true);
				message.setNeedBack(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} else {
				if (!StrUtil.isEmpty(portalUserId) && !StrUtil.isEmpty(srcPage)
						&& srcPage.equals("oa")) {
					Logger.logInfo( "OA LOGIN ..........." );
					userAccount = SessionUtil.getUserAccount( req );
					if( null != userAccount ){
						Logger.logInfo( "OA has already LOGIN ..........." );
						if( todo ){
							forwardURL = todoUrl.toString();
						}else{
							forwardURL = filterDTO.getLoginSuccessURL();
						}
					}else{
						Logger.logInfo( "OA start LOGIN ..........." );
						forwardURL = filterDTO.getLoginUrl();
						UserPortalDAO userPortalDAO = new UserPortalDAO(
								portalUserId, conn);
						userPortalDAO.setTodo( todo );
						userPortalDAO.setIsTd( is_td );
						if (userPortalDAO.isValidUser()) {
							 userAccount = userPortalDAO
									.getUserAccount();
							SessionUtil.saveUserSession(req, userAccount);
							if( todo ){
								forwardURL = todoUrl.toString();
							}else{
								forwardURL = filterDTO.getLoginSuccessURL();
							} 
							Logger.logInfo( "OA LOGIN success..........." );
						} else {
							Logger.logInfo( "OA LOGIN error..........." );
							message = userPortalDAO.getMessage();
							message.setIsError(true);
							forwardURL = filterDTO.getLoginUrl();
						}
					}
				}
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setMessageValue( ex.getMessage() );
			message.setIsError(true);
			//forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setMessageValue( ex.getMessage() );
			message.setIsError(true);
			//forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}  catch (Throwable ex) {
			Logger.logError( ex );
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setMessageValue( ex.getMessage() );
			message.setIsError(true);
			//forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	} 
}
