package com.sino.ams.spare.allot.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.spare.dao.AmsItemTransHDAO;
import com.sino.ams.spare.dao.BjdbApproveDAO;
import com.sino.ams.spare.dto.AmsItemAllocateDDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: </p>
 * <p>Copyright: 北京思诺搏信息技术有限公司 Copyright (c) 2007</p>
 * <p>Company: 北京思诺搏信息技术有限公司</p>
 *
 * @author 何睿
 * @version 0.1
 *          Date: 2008-2-18
 */
public class BjfpApproveServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String showMsg = "";
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsItemTransHDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
			dtoParameter = (AmsItemTransHDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AmsItemTransHDAO itemTransHDAO = new AmsItemTransHDAO(user, dtoParameter, conn);
			BjdbApproveDAO approveDAO = new BjdbApproveDAO(user, dtoParameter, conn);
			OptionProducer optProducer = new OptionProducer(user, conn);
			if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				approveDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) approveDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", amsItemTransH);
				//查询行信息
				req.setAttribute("AIT_LINES", approveDAO.getLines());
				forwardURL = "/spare/bjdbApprove.jsp";
			} else if (action.equals(WebActionConstant.APPROVE_ACTION)) {
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsItemAllocateDDTO.class.getName());
				List ignorList = new ArrayList();
				ignorList.add("transId");
				ignorList.add("quantity");
				r2.setIgnoreFields(ignorList);
				DTOSet detailSet = r2.getDTOSet(req);
				boolean operateResult = approveDAO.approveOrder(detailSet, req);
				message = approveDAO.getMessage();
				message.setIsError(!operateResult);
				if (!operateResult) {
					forwardURL = "com.sino.ams.spare.allot.servlet.AmsBjsAllotServlet?act=approve&transId=" + dtoParameter.getTransId();
				} else {
					showMsg = "备件分配单已通过!";
				}
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				/*boolean operateResult = itemTransHDAO.deleteData();
			  message = itemTransHDAO.getMessage();
			  message.setIsError(!operateResult);
			  forwardURL = "可再次执行该Servlet的QUERY_ACTION，请根据实际情况确定";*/
			} else if (action.equals(WebActionConstant.REJECT_ACTION)) {
				FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
				 flowDTO.setSessionUserId(user.getUserId());
			flowDTO.setSessionUserName(user.getUsername());
				flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
				String content = StrUtil.nullToString(req.getParameter("approveOpinion"));
				flowDTO.setApproveContent(content);
				approveDAO.reject(flowDTO);
				showMsg = "备件分配单已退回!";
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
		} catch (SQLModelException e) {
			Logger.logError(e);
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLException e) {
			Logger.logError(e);
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (showMsg.equals("")) {
				forwarder.forwardView(forwardURL);
			} else {
				forwarder.forwardOpenerView(forwardURL, showMsg);
			}
			//根据实际情况修改页面跳转代码。
		}
	}

}
