package com.sino.ams.spare.ruturn.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.dao.AmsItemTransHDAO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.model.AmsItemTransHModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * User: zhoujs
 * Date: 2008-3-23
 * Time: 17:15:29
 * Function:备件送修返还
 */
public class SpareReturnServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		String action = StrUtil.nullToString(req.getParameter("act"));

		try {
			SfUserDTO userAccout = (SfUserDTO) SessionUtil.getUserAccount(req);
			conn = DBManager.getDBConnection();
			Request2DTO req2DTO = new Request2DTO();
			AmsItemTransHDTO dtoParameter = null;
			req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
			dtoParameter = (AmsItemTransHDTO) req2DTO.getDTO(req);
			AmsItemTransHDAO itemTransHDAO = new AmsItemTransHDAO(userAccout, dtoParameter, conn);
			OptionProducer optProducer = new OptionProducer(userAccout, conn);
			RowSet rowSet = new RowSet();
			if (action.equals("")) {

				String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
				req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
				String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
				req.setAttribute(WebAttrConstant.INV_OPTION, invOption);

				forwardURL = "/spare/return/spareReturnQuery.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				AmsItemTransHDTO amsItemTransHDTO = new AmsItemTransHDTO();
				amsItemTransHDTO.setTransStatusName("未完成");
				amsItemTransHDTO.setTransType(DictConstant.BJFH);
				amsItemTransHDTO.setTransStatus(DictConstant.SAVE_TEMP);
				amsItemTransHDTO.setCreatedBy(userAccout.getUserId());
				amsItemTransHDTO.setCreatedUser(userAccout.getUsername());
				amsItemTransHDTO.setFromOrganizationId(userAccout.getOrganizationId());
				req.setAttribute("AIT_HEADER", amsItemTransHDTO);
				forwardURL = "/spare/return/spareReturn.jsp";
			} else if (action.equals(WebActionConstant.SAVE_ACTION)) {

			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				dtoParameter.setTransType("BJFH");
				BaseSQLProducer sqlProducer = new AmsItemTransHModel(userAccout, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
				req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
				String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
				req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
				forwardURL = "/spare/SpareCKQuery.jsp";
			}

		} catch (PoolException e) {
			e.printStackTrace();
		} catch (DTOException e) {
			e.printStackTrace();
		} catch (QueryException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
