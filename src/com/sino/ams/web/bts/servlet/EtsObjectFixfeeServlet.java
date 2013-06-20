package com.sino.ams.web.bts.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.web.bts.dao.EtsObjectFixfeeDAO;
import com.sino.ams.web.bts.dto.EtsObjectFixfeeDTO;
import com.sino.ams.web.bts.model.EtsObjectFixfeeModel;
import com.sino.base.constant.calen.CalendarConstant;
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
 * <p>Title: EtsObjectFixfeeServlet</p>
 * <p>Description:程序自动生成服务程序“EtsObjectFixfeeServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsObjectFixfeeServlet extends BaseServlet {

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
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			EtsObjectFixfeeDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsObjectFixfeeDTO.class.getName());
			dtoParameter = (EtsObjectFixfeeDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			EtsObjectFixfeeDAO etsObjectFixfeeDAO = new EtsObjectFixfeeDAO(user, dtoParameter, conn);
			 etsObjectFixfeeDAO.setCalPattern(CalendarConstant.CHINESE_PATTERN);

			OptionProducer op = new OptionProducer(user, conn);
			int company=  StrUtil.strToInt(dtoParameter.getCompany());
			String companySelect = op.getAllOrganization(company,true);
			req.setAttribute(WebAttrConstant.OU_OPTION, companySelect);
			if (action.equals("")) {
				forwardURL = URLDefineList.OBJECT_BTS;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsObjectFixfeeModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.OBJECT_BTS;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EtsObjectFixfeeDTO etsObjectFixfee = (EtsObjectFixfeeDTO) req.getAttribute(WebAttrConstant.OBJECT_BTS_DTO);
				if (etsObjectFixfee == null) {
					etsObjectFixfee = dtoParameter;
				}
				req.setAttribute(WebAttrConstant.OBJECT_BTS_DTO, etsObjectFixfee);
				forwardURL = URLDefineList.OBJECT_BTS_DETAIL;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				etsObjectFixfeeDAO.setDTOClassName(EtsObjectFixfeeDTO.class.getName());
				EtsObjectFixfeeDTO etsObjectFixfee = (EtsObjectFixfeeDTO) etsObjectFixfeeDAO.getDataByPrimaryKey();
				if (etsObjectFixfee == null) {
					etsObjectFixfee = new EtsObjectFixfeeDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.OBJECT_BTS_DTO, etsObjectFixfee);
				forwardURL = URLDefineList.OBJECT_BTS_DETAIL;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				boolean operateResult =true;
						etsObjectFixfeeDAO.createData();
				message = etsObjectFixfeeDAO.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					forwardURL = URLDefineList.OBJECT_BTS_QUERY;
				} else {
					req.setAttribute(WebAttrConstant.OBJECT_BTS_DTO, dtoParameter);
					forwardURL = URLDefineList.OBJECT_BTS_DETAIL;
				}
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				boolean operateResult =true;
				etsObjectFixfeeDAO.updateData();
				message = etsObjectFixfeeDAO.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					forwardURL = URLDefineList.OBJECT_BTS_QUERY;
				} else {
					req.setAttribute(WebAttrConstant.OBJECT_BTS_DTO, dtoParameter);
					forwardURL = URLDefineList.OBJECT_BTS_DETAIL;
				}
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
		}
		catch (DTOException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
