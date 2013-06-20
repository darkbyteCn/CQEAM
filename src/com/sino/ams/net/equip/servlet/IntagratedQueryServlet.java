package com.sino.ams.net.equip.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.net.equip.dao.IntadgratedQueryDAO;
import com.sino.ams.net.equip.dto.IntadgratedQueryDTO;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;


public class IntagratedQueryServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(IntadgratedQueryDTO.class.getName());
			IntadgratedQueryDTO dtoParameter = (IntadgratedQueryDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			IntadgratedQueryDAO commQueryDAO = new IntadgratedQueryDAO(user, dtoParameter, conn);
			if(!commQueryDAO.hasCustomizedFields()){
				message = getMessage(AssetsMessageKeys.NOT_DEFINE_FIELDS);
				message.setIsError(true);
				forwardURL = URLDefineList.QRY_BY_INTEGRATEDSERVLET;
			} else {
				String queryParas = commQueryDAO.getQueryParas();
				DTOSet fields = commQueryDAO.getDisplayFields();
				String headerDivTopPx = commQueryDAO.getHeaderDivTopPx();
				String dataDivTopPx = commQueryDAO.getDataDivTopPx();
				String tableWidthPx = commQueryDAO.getTableWidthPx();
				String tdWidthPx = commQueryDAO.getTdWidthPx();
				String dataDivHeightPx = commQueryDAO.getDataDiveightPx();

				req.setAttribute(AssetsWebAttributes.COMM_QUERY_PARA, queryParas);
				req.setAttribute(AssetsWebAttributes.COMM_QUERY_HEAD, fields);
				req.setAttribute(AssetsWebAttributes.HEADER_DIV_TOP, headerDivTopPx);
				req.setAttribute(AssetsWebAttributes.DATA_DIV_TOP, dataDivTopPx);
				req.setAttribute(AssetsWebAttributes.TABLE_WIDTH, tableWidthPx);
				req.setAttribute(AssetsWebAttributes.TD_WIDTH, tdWidthPx);
				req.setAttribute(AssetsWebAttributes.DATA_DIV_HEIGHT, dataDivHeightPx);

				if (action.equals("")) {
					forwardURL = URLDefineList.QRY_BY_INTAGRATEDQUERY;
				} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
					WebPageView pageView = new WebPageView(req, conn);
					SQLModel sqlModel = commQueryDAO.getCommonQueryModel();
					pageView.setDTOClassName(IntadgratedQueryDTO.class.getName());
					pageView.setCalPattern(LINE_PATTERN);
					pageView.setCountPages(false);
					pageView.produceWebData(sqlModel);
					forwardURL = URLDefineList.QRY_BY_INTAGRATEDQUERY;

                } else {
					message = getMessage(MsgKeyConstant.INVALID_REQ);
					message.setIsError(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				}
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
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
