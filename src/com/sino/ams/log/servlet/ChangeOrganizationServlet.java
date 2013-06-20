package com.sino.ams.log.servlet;

import com.sino.ams.log.service.ChangeOrganizationService;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTO;
import com.sino.base.dto.Request2DTO;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2012</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ChangeOrganizationServlet extends BaseServlet {

	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = new Message();
        FilterConfigDTO filterDTO = SessionUtil.getFilterConfigDTO(req);
		Connection conn = null;
		try {
			BaseUserDTO userAccount = SessionUtil.getUserAccount(req);
			conn = DBManager.getDBConnection();
            Request2DTO request2DTO = new Request2DTO();
            request2DTO.setDTOClassName(userAccount.getClass().getName());
            DTO dto = request2DTO.getDTO(req);
            ChangeOrganizationService service = new ChangeOrganizationService(userAccount, dto, conn);
            String act = req.getParameter("act");
            if(StrUtil.isEmpty(act)){
                List<EtsOuCityMapDTO> otherOrgList = service.getUserOrgListExceptCurrent();
                if(otherOrgList != null && !otherOrgList.isEmpty()){
                    req.setAttribute("ORG_LIST", otherOrgList);
                } else {
                    message.setMessageValue("对不起，你没有对应的OU组织可供切换");
                    message.setIsError(true);
                    message.setNeedClose(true);
                }
                forwardURL = "/changeOU.jsp";
            } else if(act.equals("CHANGE_OU")){
                BaseUserDTO anotherAccount = service.getChangedAccount();
                req.setAttribute("DATA_PROCESSED", "Y");
                req.setAttribute("ANOTHER_ACCOUNT", anotherAccount);
                forwardURL = "/changeOU.jsp";
            } else {
                forwardURL = filterDTO.getLoginUrl();
                SessionUtil.invalidateSession(req);
                message.setMessageKey("由于你的请求非法，系统已经将你注销...");
                message.setIsError(true);
            }
		} catch (Throwable ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
