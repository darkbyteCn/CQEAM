package com.sino.ams.dzyh.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.dzyh.dao.DzyhDisposeDAO;
import com.sino.ams.dzyh.dto.EamItemDisposeDTO;
import com.sino.ams.dzyh.model.DzyhDisposeModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
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
 * <p>Title: DzyhDisposeServlet</p>
 * <p>Description:程序自动生成服务程序“DzyhDisposeServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class DzyhDisposeServlet extends BaseServlet {

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
			OptionProducer optPro=new OptionProducer(user,conn);
			DzyhDisposeDAO disposeDAO = new DzyhDisposeDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				forwardURL = URLDefineList.DZYH_DISPOSE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new DzyhDisposeModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.DZYH_DISPOSE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EamItemDisposeDTO eamItemDispose = (EamItemDisposeDTO)req.getAttribute(WebAttrConstant.DZYH_DATA);
				if(eamItemDispose == null){
					eamItemDispose= dtoParameter;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.dzyh.dto.EamItemDisposeDTO的构造函数确定
				}
				req.setAttribute(WebAttrConstant.DZYH_DATA, eamItemDispose);
				forwardURL = URLDefineList.DZYH_DISPOSE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				disposeDAO.setDTOClassName(EamItemDisposeDTO.class.getName());
				EamItemDisposeDTO disposeDto = (EamItemDisposeDTO)disposeDAO.getDataByPrimaryKey();
				if(disposeDto == null){
					disposeDto = new EamItemDisposeDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}

				String disposeReason=optPro.getDictOption(WebAttrConstant.DZYH_DISPOSE_REASON, dtoParameter.getDisposeReason());
				String disposeType=optPro.getDictOption(WebAttrConstant.DZYH_DISPOSE_TYPE, dtoParameter.getDisposeType());
				req.setAttribute(WebAttrConstant.DZYH_DISPOSE_REASON_OPT, disposeReason);
				req.setAttribute(WebAttrConstant.DZYH_DISPOSE_TYPE_OPT, disposeType);
				
				req.setAttribute(WebAttrConstant.DZYH_DATA, disposeDto);
				forwardURL = URLDefineList.DZYH_DISPOSE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				disposeDAO.createDisposeUpdate(dtoParameter);
				forwardURL = URLDefineList.DZYH_ITEMDISPOSE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				disposeDAO.updateData();
				forwardURL = URLDefineList.DZYH_ITEMDISPOSE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				disposeDAO.deleteData();
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
		} catch (DataHandleException ex) {
			ex.printLog();
			//请根据实际情况处理消息
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