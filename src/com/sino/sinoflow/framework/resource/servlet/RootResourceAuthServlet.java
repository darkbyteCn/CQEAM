package com.sino.sinoflow.framework.resource.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.constant.WebAttrConstant;
import com.sino.sinoflow.framework.security.dao.PrivilegeAuthorizer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class RootResourceAuthServlet extends BaseServlet {

	/**
	 * 所有的Servlet都必须实现的方法。
	 *
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
			BaseUserDTO userAccount = SessionUtil.getUserAccount(req);
			conn = getDBConnection(req);
			PrivilegeAuthorizer authorizer = new PrivilegeAuthorizer(userAccount, conn);
			DTOSet rootResources = authorizer.getAuthorizedRootResource();
			if(rootResources != null && !rootResources.isEmpty()){
				req.setAttribute(WebAttrConstant.ROOT_RESOURCE, rootResources);
				forwardURL = URLDefineList.TOP_PAGE;
			} else {
				FilterConfigDTO filterDTO = getFilterConfig(req);
				forwardURL = filterDTO.getNoPriviledgeURL();
				message = getMessage(MsgKeyConstant.LOGIN_NO_PRIVI);
				message.setNeedBack(true);
				message.setIsError(true);
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally{
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
