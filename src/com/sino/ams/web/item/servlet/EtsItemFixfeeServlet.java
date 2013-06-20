package com.sino.ams.web.item.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.web.item.dao.EtsItemFixfeeDAO;
import com.sino.ams.web.item.dto.EtsItemFixfeeDTO;
import com.sino.ams.web.item.model.EtsItemFixfeeModel;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: EtsItemFixfeeServlet</p>
 * <p>Description:程序自动生成服务程序“EtsItemFixfeeServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsItemFixfeeServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String showMsg = "";
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			EtsItemFixfeeDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsItemFixfeeDTO.class.getName());
			dtoParameter = (EtsItemFixfeeDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EtsItemFixfeeDAO etsItemFixfeeDAO = new EtsItemFixfeeDAO(user, dtoParameter, conn);
			OptionProducer op = new OptionProducer(user, conn);
			String company = dtoParameter.getCompany();
			String companySelect = op.getAllOrganization(StrUtil.strToInt(company), true);
			req.setAttribute(WebAttrConstant.OU_OPTION, companySelect);
			if (action.equals("")) {
				forwardURL = URLDefineList.OBJECT_SPARE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsItemFixfeeModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.OBJECT_SPARE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EtsItemFixfeeDTO etsItemFixfee = (EtsItemFixfeeDTO) req.getAttribute(WebAttrConstant.ETS_WORKORDER_ITEM_DTO);
				if (etsItemFixfee == null) {
					etsItemFixfee = dtoParameter;
				}
				etsItemFixfee.setItemName("");
				req.setAttribute(WebAttrConstant.ETS_WORKORDER_ITEM_DTO, etsItemFixfee);
				forwardURL = URLDefineList.OBJECT_SPARE_DETAIL;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				etsItemFixfeeDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				etsItemFixfeeDAO.setDTOClassName(EtsItemFixfeeDTO.class.getName());
				EtsItemFixfeeDTO etsItemFixfee = (EtsItemFixfeeDTO) etsItemFixfeeDAO.getDataByPrimaryKey();
				if (etsItemFixfee == null) {
					etsItemFixfee = new EtsItemFixfeeDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.ETS_WORKORDER_ITEM_DTO, etsItemFixfee);
				forwardURL = URLDefineList.OBJECT_SPARE_DETAIL;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				etsItemFixfeeDAO.createData();
				message = etsItemFixfeeDAO.getMessage();
				showMsg = "新增成功!";
				forwardURL = URLDefineList.OBJECT_SPARE;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				etsItemFixfeeDAO.updateData();
				message = etsItemFixfeeDAO.getMessage();
				showMsg = "保存成功!";
				forwardURL = URLDefineList.OBJECT_ITEM_QUERY;
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
			/** @todo Handle this exception */
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (showMsg.equals("")) {
				forwarder.forwardView(forwardURL);
			} else {
				forwarder.forwardView(forwardURL, showMsg);
			}

			//根据实际情况修改页面跳转代码。
		}
	}
}
