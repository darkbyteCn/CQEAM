package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.data.RowSet;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.bean.AssetsRadioProducer;
import com.sino.ams.newasset.constant.*;
import com.sino.ams.newasset.dao.ApproveContentDAO;
import com.sino.ams.newasset.dao.ItemOrderHeaderPrintDAO;
import com.sino.ams.newasset.dao.ItemOrderLinePrintDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.model.ItemOrderHeaderPrintModel;
import com.sino.ams.system.user.dto.SfUserDTO;
public class ItemOrderPrintServlet extends BaseServlet {

	/**
	 * 所有的Servlet都必须实现的方法。
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
			ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
			AmsAssetsTransHeaderDTO dto = (AmsAssetsTransHeaderDTO) req2DTO.getDTO(req);
			dto.setServletConfig(getServletConfig(req));
			String action = dto.getAct();
			dto.setCalPattern(LINE_PATTERN);
			conn = getDBConnection(req);
			String transType = dto.getTransType();
			if (transType.equals(AssetsDictConstant.ASS_RED)) {
				AssetsOptProducer optProducer = new AssetsOptProducer(user,conn);
				String option = optProducer.getTransferOption(dto.getTransferType());
				dto.setTransferTypeOption(option);
				AssetsRadioProducer radioPrd = new AssetsRadioProducer(user);
				req.setAttribute(AssetsWebAttributes.PRINT_RADIO,radioPrd.getOrderPrintRadio(dto.getPrintType()));
			}
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL ="/newasset/itemPrintQuery.jsp";
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new ItemOrderHeaderPrintModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/newasset/itemPrintQuery.jsp";
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) { //进入详细信息页面，页面提供打印功能
				ItemOrderHeaderPrintDAO printDAO = new ItemOrderHeaderPrintDAO(user, dto, conn);
				printDAO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
				AmsAssetsTransHeaderDTO headerDTO = (AmsAssetsTransHeaderDTO)printDAO.getDataByPrimaryKey();
				if (headerDTO == null) {
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				} else {
					headerDTO.setPrintType(dto.getPrintType());
					AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
					lineDTO.setTransId(headerDTO.getTransId());
					lineDTO.setTransType(headerDTO.getTransType());
					ItemOrderLinePrintDAO lineDAO = new ItemOrderLinePrintDAO(user, lineDTO, conn);
					lineDAO.setCalPattern(LINE_PATTERN);
					lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
//					lineDAO.setTransType(headerDTO.getTransType());
					lineDAO.setPrintType(headerDTO.getPrintType());
					DTOSet ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
					req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);

					String transId = headerDTO.getTransId();
					String tableName = "AMS_ASSETS_TRANS_HEADER";
					RowSet rows = ApproveContentDAO.getApproveContent(conn, transId, tableName);
					req.setAttribute(AssetsWebAttributes.APPROVE_CONTENT, rows);
				}
				headerDTO.setCalPattern(LINE_PATTERN);
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
				forwardURL = "/newasset/itemOrderPrintDetail.jsp";
			} else {
				message = getMessage(AssetsMessageKeys.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}