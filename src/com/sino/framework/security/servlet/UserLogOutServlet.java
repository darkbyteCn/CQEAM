package com.sino.framework.security.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.FilterConfigDTO;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoApplication</p>
 * <p>Description: Java Enterprise Edition 平台应用开发基础框架</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2003~2008。
 * <p>Copyright: 其中使用到的第三方组件，根据中华人民共和国相关法律以及中华人民共和国加入的相关国际公约，版权属原作者所有。</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 0.1
 */
public class UserLogOutServlet extends BaseServlet {

	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		FilterConfigDTO filterDTO = SessionUtil.getFilterConfigDTO(req);
		String loginURL = filterDTO.getLoginUrl();
		String method = req.getMethod();
		SessionUtil.invalidateSession(req);
		Message message = null;
		ServletForwarder forwarder = new ServletForwarder(req, res);
		if (!method.equalsIgnoreCase("post")) {
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			loginURL = MessageConstant.MSG_PRC_SERVLET;
			forwarder.forwardView(loginURL);
		}
		else
		{		
		forwarder.forwardView(loginURL);
		}
	}
}
