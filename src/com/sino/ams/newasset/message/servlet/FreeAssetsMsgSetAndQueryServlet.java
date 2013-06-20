package com.sino.ams.newasset.message.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.newasset.message.dao.FreeAssetsMsgSetAndQueryDAO;
import com.sino.ams.newasset.message.model.FreeAssetsMsgSetAndQueryModel;
import com.sino.ams.newasset.message.dto.FreeAssetsMsgSetAndQueryDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.model.SfUserBaseModel;

public class FreeAssetsMsgSetAndQueryServlet extends BaseServlet {
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
			req2DTO.setDTOClassName(FreeAssetsMsgSetAndQueryDTO.class.getName());
			FreeAssetsMsgSetAndQueryDTO dto = (FreeAssetsMsgSetAndQueryDTO)req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			FreeAssetsMsgSetAndQueryDAO messageDAO = new FreeAssetsMsgSetAndQueryDAO(user, dto, conn);
			OptionProducer optProducer = new OptionProducer(user, conn);
			String opt = optProducer.getAllOrganization( dto.getOrganizationId() , true);
			dto.setOrganizationOption(opt);
			dto.setInitPrivi(user.isSysAdmin());
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.FA_MSG_QRY_PAGE;
			} else if (action.equals(AMSActionConstant.QUERY_ACTION)) {			
				BaseSQLProducer sqlProducer = new FreeAssetsMsgSetAndQueryModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(FreeAssetsMsgSetAndQueryDTO.class.getName());
				pageDAO.setPageSize(200);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.FA_MSG_QRY_PAGE;
			} else if (action.equals(AMSActionConstant.DETAIL_ACTION)) {
				String msgCategoryId = dto.getMsgCategoryId();
				if(!msgCategoryId.equals("")){
					messageDAO.setDTOClassName(FreeAssetsMsgSetAndQueryDTO.class.getName());
					dto = (FreeAssetsMsgSetAndQueryDTO)messageDAO.getDataByPrimaryKey();
				}
				if(dto == null){
					dto = new FreeAssetsMsgSetAndQueryDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				opt = optProducer.getAllOrganization( dto.getOrganizationId() , true);
				dto.setOrganizationOption(opt);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.FA_MSG_DTL_PAGE;
			} else if (action.equals(AMSActionConstant.SAVE_ACTION)) {
				messageDAO.saveMessageData();
				dto = (FreeAssetsMsgSetAndQueryDTO)messageDAO.getDTOParameter();
				forwardURL = URLDefineList.FA_MSG_SERVLET;
				forwardURL += "?act=" + AMSActionConstant.DETAIL_ACTION;
				forwardURL += "&msgCategoryId=" + dto.getMsgCategoryId();
				message = messageDAO.getMessage();
			} else if (action.equals(AMSActionConstant.DELETE_ACTION)) {
				messageDAO.deleteData();
				forwardURL = URLDefineList.FA_MSG_SERVLET;
				forwardURL += "?act=" + AMSActionConstant.QUERY_ACTION;
			} else if (action.equals(AMSActionConstant.INIT_ACTION)) {
				messageDAO.initMessage();
				message = messageDAO.getMessage();
				forwardURL = URLDefineList.FA_MSG_SERVLET;
				forwardURL += "?act=" + AMSActionConstant.QUERY_ACTION;
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
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
