package com.sino.ams.dzyh.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.constant.LvecMessageKeys;
import com.sino.ams.dzyh.constant.LvecURLs;
import com.sino.ams.dzyh.dao.InstruConfirmDAO;
import com.sino.ams.dzyh.dto.EamCheckTaskDTO;
import com.sino.ams.dzyh.dto.EamDhCheckLineDTO;
import com.sino.ams.dzyh.model.OrderLineConfirmModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: InstruConfirmServlet</p>
 * <p>Description:程序自动生成服务程序“InstruConfirmServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class InstruConfirmServlet extends BaseServlet {

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
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EamDhCheckLineDTO.class.getName());
			EamDhCheckLineDTO dto = (EamDhCheckLineDTO)req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = LvecURLs.INS_CONFIRM_PAGE;
			} else if (action.equals(DzyhActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new OrderLineConfirmModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(EamDhCheckLineDTO.class.getName());
				pageDAO.setCalPattern(LINE_PATTERN);

				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("HEADER_ID");
				checkProp.addDbField("BARCODE");
				checkProp.addDbField("CATALOG_VALUE_ID");
				checkProp.addDbField("RESPONSIBILITY_USER");
				checkProp.addDbField("RESPONSIBILITY_DEPT");
				checkProp.addDbField("ORDER_NO");

				pageDAO.setWebCheckProp(checkProp);
				pageDAO.produceWebData();

				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = LvecURLs.INS_CONFIRM_PAGE;
			} else if (action.equals(DzyhActionConstant.CONFIRM_ACTION)) {//确认
				if(user.getEmployeeNumber().equals("")){
					message = getMessage(LvecMessageKeys.NO_EMPLOYEE_NUMBER);
					message.setIsError(true);
					message.setNeedBack(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				} else {
					RequestParser parser = new RequestParser();
					CheckBoxProp checkProp = new CheckBoxProp("subCheck");
					checkProp.setIgnoreOtherField(true);
					parser.setCheckBoxProp(checkProp);
					parser.transData(req);

					req2DTO.setIgnoreFields(EamCheckTaskDTO.class);
					req2DTO.addIgnoreField("itemName");
//					req2DTO.addIgnoreField("orderNo");

					DTOSet orderLines = req2DTO.getDTOSet(parser);
					InstruConfirmDAO confirmDAO = new InstruConfirmDAO(user, dto, conn);

					confirmDAO.ConfirmInstrument(orderLines);
					message = confirmDAO.getMessage();
					forwardURL = LvecURLs.INS_CONFIRM_SERVLET;
					forwardURL += "?act=" + DzyhActionConstant.QUERY_ACTION;
				}
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
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!forwardURL.equals("")){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
