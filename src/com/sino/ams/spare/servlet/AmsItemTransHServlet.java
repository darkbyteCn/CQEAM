package com.sino.ams.spare.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.dao.AmsItemTransHDAO;
import com.sino.ams.spare.dao.AmsItemTransLDAO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.AmsItemTransHModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class AmsItemTransHServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String transType = StrUtil.nullToString(req.getParameter("transType"));
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
			AmsItemTransHDTO dto = (AmsItemTransHDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AmsItemTransHDAO itemTransHDAO = new AmsItemTransHDAO(user, dto, conn);
			OptionProducer optProducer = new OptionProducer(user, conn);
			if (action.equals("")) {
				String orderStatus = optProducer.getDictOption("ORDER_STATUS", dto.getTransStatus());
				req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
				String invOption = optProducer.getInvOption(dto.getFromObjectNo());
				req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
				forwardURL = "/spare/bjOrderQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsItemTransHModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				String orderStatus = optProducer.getDictOption("ORDER_STATUS", dto.getTransStatus());
				req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
				String invOption = optProducer.getInvOption(dto.getFromObjectNo());
				req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
				forwardURL = "/spare/bjOrderQuery.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) req.getAttribute("AIT_HEADER");
				if (orderHeader == null) {
					orderHeader = dto;
					orderHeader.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
					orderHeader.setCreatedBy(user.getUserId());
					orderHeader.setCreatedUser(user.getUsername());
					orderHeader.setCreationDate(CalendarUtil.getCurrDate());
					orderHeader.setTransStatus(DictConstant.SAVE_TEMP);
					orderHeader.setTransStatusName("未完成");
					orderHeader.setToOrganizationId(user.getOrganizationId());
				}
				String spareReason = optProducer.getDictOption2("SPARE_REASON", "");
				req.setAttribute(WebAttrConstant.SPARE_REASON, spareReason);
				req.setAttribute("AIT_HEADER", orderHeader);
				forwardURL = "/spare/xgrkOrder.jsp";
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {//工单明细
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (orderHeader == null) {
					orderHeader = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", orderHeader);
				String spareReason = optProducer.getDictOption2("SPARE_REASON", orderHeader.getSpareReason());
				req.setAttribute(WebAttrConstant.SPARE_REASON, spareReason);
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
				req.setAttribute("AIT_LINES", ldao.getLines(orderHeader.getTransId()));
				forwardURL = "/spare/xgrkOrder.jsp";
			} else if (action.equals(WebActionConstant.SAVE_ACTION)) {//工单提交
				req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
				req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
				DTOSet lineSet = req2DTO.getDTOSet(req);
				FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
				flowDTO.setSessionUserId(user.getUserId());
				flowDTO.setSessionUserName(user.getUsername());
				boolean operateResult = itemTransHDAO.saveOrder(lineSet, flowDTO);
				message = itemTransHDAO.getMessage();
				dto = (AmsItemTransHDTO) itemTransHDAO.getDTOParameter();
				if (operateResult) {
					forwardURL = "/servlet/com.sino.ams.spare.servlet.AmsItemTransHServlet" + "?act=" + WebActionConstant.DETAIL_ACTION + "&transId=" + dto.getTransId();
				} else {
					req.setAttribute("AIT_HEADER", dto);
					forwardURL = "/spare/xgrkOrder.jsp";
				}
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {//入库
				req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
				req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
				DTOSet lineSet = req2DTO.getDTOSet(req);
				FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
				flowDTO.setSessionUserId(user.getUserId());
				flowDTO.setSessionUserName(user.getUsername());
				boolean operateResult = itemTransHDAO.submitOrder(lineSet, dto);
				message = itemTransHDAO.getMessage();
				dto = (AmsItemTransHDTO) itemTransHDAO.getDTOParameter();
				if (operateResult) {
					forwardURL = "/servlet/com.sino.ams.spare.servlet.AmsItemTransHServlet" + "?act=" + WebActionConstant.DETAIL_ACTION + "&transId=" + dto.getTransId();
				} else {
					req.setAttribute("AIT_HEADER", dto);
					forwardURL = "/spare/xgrkOrder.jsp";
				}
			} else if (action.equals("print")) {//入库单打印
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (orderHeader == null) {
					orderHeader = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", orderHeader);
				String spareReason = optProducer.getDictOption2("SPARE_REASON", orderHeader.getSpareReason());
				req.setAttribute(WebAttrConstant.SPARE_REASON, spareReason);
				//查询行信息
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
				req.setAttribute("AIT_LINES", ldao.getLines(orderHeader.getTransId()));
				forwardURL = "/spare/print/spareRKPrint.jsp";
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				itemTransHDAO.deleteData();
				message = itemTransHDAO.getMessage();
				forwardURL = "可再次执行该Servlet的QUERY_ACTION，请根据实际情况确定";
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
		} catch (CalendarException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLException e) {
			Logger.logError(e);
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			Logger.logError(ex);
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
