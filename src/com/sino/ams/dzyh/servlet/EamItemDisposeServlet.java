package com.sino.ams.dzyh.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.dzyh.dao.EamItemDisposeDAO;
import com.sino.ams.dzyh.dto.EamItemDisposeDTO;
import com.sino.ams.dzyh.model.EamItemDisposeModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
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


/**
 * <p>Title: EamItemDisposeServlet</p>
 * <p>Description:程序自动生成服务程序“EamItemDisposeServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EamItemDisposeServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action=req.getParameter("act");
		action=StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EamItemDisposeDTO.class.getName());
			EamItemDisposeDTO dtoParameter = (EamItemDisposeDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EamItemDisposeDAO eamItemDisposeDAO = new EamItemDisposeDAO(user, dtoParameter, conn);
			OptionProducer optPro=new OptionProducer(user,conn);
			String disposeReason=optPro.getDictOption(WebAttrConstant.DZYH_DISPOSE_REASON, dtoParameter.getDisposeReason());
			String disposeType=optPro.getDictOption(WebAttrConstant.DZYH_DISPOSE_TYPE, dtoParameter.getDisposeType());
			req.setAttribute(WebAttrConstant.DZYH_DISPOSE_REASON_OPT, disposeReason);
			req.setAttribute(WebAttrConstant.DZYH_DISPOSE_TYPE_OPT, disposeType);
			if (action.equals("")) {
				forwardURL = URLDefineList.DZYH_ITEMDISPOSE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EamItemDisposeModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.DZYH_ITEMDISPOSE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				eamItemDisposeDAO.setDTOClassName(EamItemDisposeDTO.class.getName());
				EamItemDisposeDTO eamItemDispose = (EamItemDisposeDTO)eamItemDisposeDAO.getDataByPrimaryKey();
				if(eamItemDispose == null){
					eamItemDispose = new EamItemDisposeDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.DZYH_DATA, eamItemDispose);
				forwardURL = URLDefineList.DZYH_ITEMDISPOSE_DETAIL_PAGE;
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
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}