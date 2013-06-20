package com.sino.ams.system.user.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.system.user.dao.EtsOuCityMapDAO;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.model.EtsOuCityMapModel;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: EtsOuCityMapServlet</p>
 * <p>Description:程序自动生成服务程序“EtsOuCityMapServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author mshtang
 * @version 1.0
 */


public class EtsOuCityMapServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = null;//从session中获取数据，根据实际情况自行修改。
			EtsOuCityMapDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsOuCityMapDTO.class.getName());
			dtoParameter = (EtsOuCityMapDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			EtsOuCityMapDAO etsOuCityMapDAO = new EtsOuCityMapDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				forwardURL = "翻页查询页面，请根据实际情况修改";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsOuCityMapModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = "翻页查询页面，请根据实际情况修改";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EtsOuCityMapDTO etsOuCityMap = (EtsOuCityMapDTO) req.getAttribute("详细数据属性，请根据实际情况修改");
				if (etsOuCityMap == null) {
					etsOuCityMap = new EtsOuCityMapDTO();
				}
				req.setAttribute("详细数据属性，请根据实际情况修改", etsOuCityMap);
				forwardURL = "详细数据页面，此时数据为空，请根据实际情况修改";
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				etsOuCityMapDAO.setDTOClassName(EtsOuCityMapDTO.class.getName());
				EtsOuCityMapDTO etsOuCityMap = (EtsOuCityMapDTO) etsOuCityMapDAO.getDataByPrimaryKey();
				if (etsOuCityMap == null) {
					etsOuCityMap = new EtsOuCityMapDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("详细数据属性，请根据实际情况修改", etsOuCityMap);
				forwardURL = "详细数据页面，此时有数据，请根据实际情况修改";
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				etsOuCityMapDAO.createData();
				message = etsOuCityMapDAO.getMessage();
				forwardURL = "可再次执行该Servlet的QUERY_ACTION，请根据实际情况确定";
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				etsOuCityMapDAO.updateData();
				message = etsOuCityMapDAO.getMessage();
				forwardURL = "可再次执行该Servlet的QUERY_ACTION，请根据实际情况确定";
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				etsOuCityMapDAO.deleteData();
				message = etsOuCityMapDAO.getMessage();
				forwardURL = "可再次执行该Servlet的QUERY_ACTION，请根据实际情况确定";
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.CONN_ERROR);
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
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
