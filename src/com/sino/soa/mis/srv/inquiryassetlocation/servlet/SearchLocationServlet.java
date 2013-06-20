/**
 * 
 */
package com.sino.soa.mis.srv.inquiryassetlocation.servlet;


import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.soa.common.SrvURLDefineList;
import com.sino.soa.mis.srv.inquiryassetlocation.dto.SrvAssetLocationDTO;
import com.sino.soa.mis.srv.inquiryassetlocation.model.SrvAssetLocationModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * @author dell
 *
 */
public class SearchLocationServlet extends BaseServlet{
	/**
	 * 所有的Servlet都必须实现的方法。
	 *
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 * @todo Implement this com.sino.base.PubServlet method
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
            ServletConfigDTO configDTO=SessionUtil.getServletConfigDTO(req);
            Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SrvAssetLocationDTO.class.getName());
			SrvAssetLocationDTO dto = (SrvAssetLocationDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			if (action.equals("")) {
				String opt = optProducer.getOrganizationOption(dto.getOrganizationId());
				dto.setOrgOption(opt);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = SrvURLDefineList.SEARCH_lOCATION_PAGE;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				String opt = optProducer.getOrganizationOption(dto.getOrganizationId());
				dto.setOrgOption(opt);
				BaseSQLProducer sqlProducer = new SrvAssetLocationModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setServletConfig(configDTO);
                pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = SrvURLDefineList.SEARCH_lOCATION_PAGE;
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!StrUtil.isEmpty(forwardURL)) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
