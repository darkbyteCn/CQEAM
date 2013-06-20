package com.sino.ams.newasset.scrap.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.scrap.constant.ScrapURLListConstant;
import com.sino.ams.newasset.scrap.dto.TransDTO;
import com.sino.ams.newasset.scrap.service.TransService;
import com.sino.ams.newasset.servlet.AssetsTransFlowBaseServlet;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;

/**
 * 
 * @系统名称: 续租
 * @功能描述:
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 14, 2011
 */
public class TransFlowServlet extends AssetsTransFlowBaseServlet {

	@Override
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		String msg = "";
		boolean isClose = false;
		String action = null;
		Connection conn = null;
		TransService service = null;
		boolean isSuccess = false;
		TransDTO transDTO = null;
		SfUserDTO user = null;
		Message message = SessionUtil.getMessage(req);
		try {
			user = (SfUserDTO) getUserAccount(req);
			// // 处理头参数
			transDTO = this.getDTOFromReq(req);
			conn = getDBConnection(req);
			action = transDTO.getAct();
			service = new TransService(user, transDTO, conn);
			if (action.equals(WebActionConstant.SAVE_ACTION)) {
				isSuccess = service.doSave();
				service.prodData();
				transDTO = service.getForm();
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, transDTO);
				// forwardURL = TransURLListConstant.LEASE_SERVELT + "?act=EDIT_ACTION";
				forwardURL = ScrapURLListConstant.TRANS_EDIT_PAGE;
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
				isSuccess = service.doSubmit();
				transDTO = service.getForm();
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, transDTO);

				// 意见展现
				super.doApproveContent(req, conn, transDTO.getHeaderDTO()
						.getTransId());

				forwardURL = ScrapURLListConstant.TRANS_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.EDIT_ACTION)) {
				service.prodData();
				transDTO = service.getForm();
				// transDTO = setOption(conn, req, userInfo, applyDTO);
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, transDTO);
				forwardURL = ScrapURLListConstant.TRANS_EDIT_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				service.prodData();
				transDTO = service.getForm();
				// transDTO = setOption(conn, req, userInfo, applyDTO);
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, transDTO);
				forwardURL = ScrapURLListConstant.TRANS_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CANCEL_ACTION)) {
				service.doCancel();
				transDTO = service.getForm();
				// transDTO = setOption(conn, req, userInfo, applyDTO);
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, transDTO);
				// 意见展现
				super.doApproveContent(req, conn, transDTO.getHeaderDTO()
						.getTransId());
				forwardURL = ScrapURLListConstant.TRANS_DETAIL_PAGE;
			}

			msg = service.getMsg();
			if (!StrUtil.isEmpty(msg)) {
				message = new Message();
				message.setMessageValue(msg);
				req.setAttribute(MessageConstant.MESSAGE_DATA, message);
				// message.setIsError( !isSuccess );
			}

		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			// DBManager.closeDBConnection( conn );
			closeDBConnection(conn);
			// setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);

			if (!StrUtil.isEmpty(forwardURL)) {
				forwarder.forwardView(forwardURL);
			} else {
				forwarder.forwardOpenerView(forwardURL, msg);
			}
		}
	}

	/**
	 * 取参数
	 * 
	 * @param req
	 * @return
	 * @throws DTOException
	 */
	public TransDTO getDTOFromReq(HttpServletRequest req) throws DTOException {
		TransDTO dto = new TransDTO();
		AmsAssetsTransHeaderDTO headerDTO = null;
		DTOSet lines = null;

		Request2DTO req2DTO = new Request2DTO();
		req2DTO.setDTOClassName(TransDTO.class.getName());
		dto = (TransDTO) req2DTO.getDTO(req);

		// 处理头参数
		req2DTO.setDTOClassName(AmsAssetsTransHeaderDTO.class.getName());
		headerDTO = (AmsAssetsTransHeaderDTO) req2DTO.getDTO(req);
		dto.setHeaderDTO(headerDTO);

		// 处理行参数
		req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
		req2DTO.setIgnoreFields(AmsAssetsTransHeaderDTO.class);
		lines = req2DTO.getDTOSet(req);
		dto.setLines(lines);

		return dto;
	}

}
