package com.sino.ams.match.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.match.dao.LastChangeViewDAO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class LastChangeViewServlet extends BaseServlet {

	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		boolean hasError = true;
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			conn = getDBConnection(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
			EtsItemInfoDTO dto = (EtsItemInfoDTO)req2DTO.getDTO(req);
			LastChangeViewDAO changeViewer = new LastChangeViewDAO(user, dto, conn);
			res.setContentType("text/html;charset=GBK");
			PrintWriter out = res.getWriter();
			out.println(changeViewer.getLastChangeInfo());
			hasError = false;
		} catch (PoolPassivateException ex) {
			ex.printLog();
		} catch (DTOException ex) {
			ex.printLog();
		} catch (QueryException ex) {
			ex.printLog();
		} finally {
			closeDBConnection(conn);
			if (hasError) {
				message = getMessage(AssetsMessageKeys.COMMON_ERROR);
				message.setIsError(true);
				setHandleMessage(req, message);
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(MessageConstant.MSG_PRC_SERVLET);
			}
		}
	}
}
