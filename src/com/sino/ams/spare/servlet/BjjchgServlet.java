package com.sino.ams.spare.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.base.message.Message;
import com.sino.base.dto.Request2DTO;
import com.sino.base.dto.DTOSet;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.util.CalendarUtil;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.db.conn.DBManager;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.dao.AmsItemTransHDAO;
import com.sino.ams.spare.dao.AmsItemTransLDAO;
import com.sino.ams.spare.model.AmsItemTransHModel;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.constant.DictConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2010-2-1
 * Time: 16:34:47
 * To change this template use File | Settings | File Templates.
 */
public class BjjchgServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
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
			if (action.equals("")) {//没用到
				String orderStatus = optProducer.getDictOption("ORDER_STATUS", dto.getTransStatus());
				req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
				String invOption = optProducer.getInvOption(dto.getFromObjectNo());
				req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
				forwardURL = "/spare/bjOrderQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {//没用到
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
					orderHeader.setTransType(DictConstant.BJFK);
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
				forwardURL = "/spare/bjjchgCreate.jsp";
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (orderHeader == null) {
					orderHeader = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", orderHeader);
				//查询行信息
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
				req.setAttribute("AIT_LINES", ldao.getLines(orderHeader.getTransId()));
				forwardURL = "/spare/bjjchgCreate.jsp";
			} else if (action.equals(WebActionConstant.SAVE_ACTION)) {
				req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
				req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
				DTOSet lineSet = req2DTO.getDTOSet(req);
				boolean operateResult = itemTransHDAO.saveOrder(lineSet, null);
				message = itemTransHDAO.getMessage();
				if (operateResult) {
					forwardURL = "/servlet/com.sino.ams.spare.servlet.BjjchgServlet" + "?act=" + WebActionConstant.DETAIL_ACTION + "&transId=" + dto.getTransId();
				} else {
					req.setAttribute("AIT_HEADER", dto);
					forwardURL = "/spare/bjjchgCreate.jsp";
				}
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
				req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
				req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
				DTOSet lineSet = req2DTO.getDTOSet(req);
				itemTransHDAO.submitOrderFk(lineSet, null);
				message = itemTransHDAO.getMessage();
				forwardURL = "/servlet/com.sino.ams.spare.servlet.BjjchgServlet" + "?act=" + WebActionConstant.DETAIL_ACTION + "&transId=" + dto.getTransId();
			} else if (action.equals("print")) {
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (orderHeader == null) {
					orderHeader = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", orderHeader);
				//查询行信息
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
				req.setAttribute("AIT_LINES", ldao.getLines(orderHeader.getTransId()));
				forwardURL = "/spare/print/spareJCHGPrint.jsp";
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
		} catch (DataHandleException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!forwardURL.equals("")) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
