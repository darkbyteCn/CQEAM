package com.sino.ams.expand.servlet;


import java.sql.Connection;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.message.*;
import com.sino.base.db.conn.DBManager;
import com.sino.base.message.Message;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.base.constant.message.MsgKeyConstant;

import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.expand.dto.EtsItemInfoExDTO;
import com.sino.ams.expand.model.EtsItemInfoExModel;
import com.sino.ams.expand.dao.EtsItemInfoExDAO;


/**
 * <p>Title: EtsItemInfoExServlet</p>
 * <p>Description:程序自动生成服务程序“EtsItemInfoExServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Administrator
 * @version 1.0
 */


public class EtsItemInfoExServlet extends BaseServlet {

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
			req2DTO.setDTOClassName(EtsItemInfoExDTO.class.getName());
			EtsItemInfoExDTO dtoParameter = (EtsItemInfoExDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			EtsItemInfoExDAO etsItemInfoExDAO = new EtsItemInfoExDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				forwardURL = URLDefineList.ETS_ITEM_INFO_EX_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsItemInfoExModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.ETS_ITEM_INFO_EX_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EtsItemInfoExDTO etsItemInfoEx = (EtsItemInfoExDTO)req.getAttribute("获取因为失败而保持的数据，请根据实际情况修改");
				if(etsItemInfoEx == null){
					etsItemInfoEx= dtoParameter;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.dzyh.dto.EtsItemInfoExDTO的构造函数确定
				}
				req.setAttribute("详细数据属性，请根据实际情况修改", etsItemInfoEx);
				forwardURL = URLDefineList.ETS_ITEM_INFO_EX_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				etsItemInfoExDAO.setDTOClassName(EtsItemInfoExDTO.class.getName());
				EtsItemInfoExDTO etsItemInfoEx = (EtsItemInfoExDTO)etsItemInfoExDAO.getDataByPrimaryKey();
				if(etsItemInfoEx == null){
					etsItemInfoEx = new EtsItemInfoExDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.DZYH_DATA, etsItemInfoEx);
				forwardURL = URLDefineList.ETS_ITEM_INFO_EX_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				etsItemInfoExDAO.createData();
				forwardURL = URLDefineList.ETS_ITEM_INFO_EX_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				etsItemInfoExDAO.updateData();
				forwardURL = URLDefineList.ETS_ITEM_INFO_EX_QUERY_PAGE;
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
			forwardURL = "保持界面录入的数据，返回到原页面，并显示上面给出的消息";
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}