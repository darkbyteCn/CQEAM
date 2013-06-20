package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsRcvLineDAO;
import com.sino.ams.newasset.dao.RcvOrderPrintDAO;
import com.sino.ams.newasset.dto.AmsAssetsRcvHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsRcvLineDTO;
import com.sino.ams.newasset.model.AmsAssetsRcvHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

public class RcvOrderPrintServlet extends BaseServlet {
	/**
	 * 所有的Servlet都必须实现的方法。
	 *
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
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsRcvHeaderDTO.class.getName());
			AmsAssetsRcvHeaderDTO dto = (AmsAssetsRcvHeaderDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.RCV_PRINT_QRY;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsAssetsRcvHeaderModel(user,dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.RCV_PRINT_QRY;
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
				RcvOrderPrintDAO printDAO = new RcvOrderPrintDAO(user, dto, conn);
				printDAO.setDTOClassName(AmsAssetsRcvHeaderDTO.class.getName());
				dto = (AmsAssetsRcvHeaderDTO) printDAO.getDataByPrimaryKey();
				dto.setCalPattern(LINE_PATTERN);
				AmsAssetsRcvLineDTO lineDTO = new AmsAssetsRcvLineDTO();
				lineDTO.setReceiveHeaderId(StrUtil.strToInt(dto.getReceiveHeaderId()));
				AmsAssetsRcvLineDAO lineDAO = new AmsAssetsRcvLineDAO(user,lineDTO, conn);
				lineDAO.setDTOClassName(AmsAssetsRcvLineDTO.class.getName());
				lineDAO.setCalPattern(LINE_PATTERN);
				DTOSet orderLines = (DTOSet) lineDAO.getDataByForeignKey("receiveHeaderId");
				RowSet approveContents = printDAO.getApproveContent();
				req.setAttribute(AssetsWebAttributes.RCV_ORDER_HEAD, dto);
				req.setAttribute(AssetsWebAttributes.RCV_ORDER_LINE, orderLines);
				req.setAttribute(AssetsWebAttributes.APPROVE_CONTENT, approveContents);
				forwardURL = AssetsURLList.RCV_ORDER_PRINT;
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
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
