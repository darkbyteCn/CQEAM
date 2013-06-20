package com.sino.ams.system.trust.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.trust.dao.AmsMaintainPeopleDAO;
import com.sino.ams.system.trust.dto.AmsMaintainPeopleDTO;
import com.sino.ams.system.trust.model.AmsMaintainPeopleModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsMaintainPeopleServlet</p>
 * <p>Description:程序自动生成服务程序“AmsMaintainPeopleServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsMaintainPeopleServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsMaintainPeopleDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsMaintainPeopleDTO.class.getName());
			dtoParameter = (AmsMaintainPeopleDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			AmsMaintainPeopleDAO amsMaintainPeopleDAO = new AmsMaintainPeopleDAO(user, dtoParameter, conn);
			OptionProducer optProducer = new OptionProducer(user, conn);

			if (action.equals("")) {
				//公司下拉列表
//				String corpOption = optProducer.getMainCorpOption(dtoParameter.getCompanyId());
//				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, corpOption);
				this.setCorpOption(req, optProducer, dtoParameter);
				forwardURL = URLDefineList.TRUSTCORUSR_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				//公司下拉列表
//				String corpOption = optProducer.getMainCorpOption(dtoParameter.getCompanyId());
//				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, corpOption);
				this.setCorpOption(req, optProducer, dtoParameter);
				
				BaseSQLProducer sqlProducer = new AmsMaintainPeopleModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.TRUSTCORUSR_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {

				AmsMaintainPeopleDTO amsMaintainPeople = null /*(AmsMaintainPeopleDTO)req.getAttribute(WebAttrConstant.MAINTAIN_CORP_USR_ATTR)*/;
				if (amsMaintainPeople == null) {
					amsMaintainPeople = dtoParameter;
				}
				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_USR_ATTR, amsMaintainPeople);
				//公司下拉列表
//				String corpOption = optProducer.getMainCorpOption(dtoParameter.getCompanyId());
//				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, corpOption);
				this.setCorpOption(req, optProducer, dtoParameter);
				
				forwardURL = URLDefineList.TRUSTCORUSR_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				amsMaintainPeopleDAO.setDTOClassName(AmsMaintainPeopleDTO.class.getName());
				AmsMaintainPeopleDTO amsMaintainPeople = (AmsMaintainPeopleDTO) amsMaintainPeopleDAO.getDataByPrimaryKey();
				if (amsMaintainPeople == null) {
					amsMaintainPeople = new AmsMaintainPeopleDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_USR_ATTR, amsMaintainPeople);

				//公司下拉列表
//				String corpOption = optProducer.getMainCorpOption(amsMaintainPeople.getCompanyId());
//				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, corpOption);
				this.setCorpOption(req, optProducer, dtoParameter);
				
				forwardURL = URLDefineList.TRUSTCORUSR_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				amsMaintainPeopleDAO.createData();
				message = amsMaintainPeopleDAO.getMessage();

				forwardURL = URLDefineList.TRUSTCORUSR_QUERY_SERVLET;

			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				amsMaintainPeopleDAO.updateData();
				message = amsMaintainPeopleDAO.getMessage();

				forwardURL = URLDefineList.TRUSTCORUSR_QUERY_SERVLET;

			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				amsMaintainPeopleDAO.deleteData();
				message = amsMaintainPeopleDAO.getMessage();

				forwardURL = URLDefineList.TRUSTCORUSR_QUERY_SERVLET;
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (DataHandleException ex) {
			ex.printLog();
		} catch (PoolException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.CONN_ERROR);
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
	
	private void setCorpOption(  HttpServletRequest req  , OptionProducer optProducer , AmsMaintainPeopleDTO dtoParameter ) throws QueryException{
		String corpOption = optProducer.getMainCorpOption( String.valueOf( dtoParameter.getCompanyId() ) );
		req.setAttribute(WebAttrConstant.MAINTAIN_CORP_OPTION, corpOption);

	}
}
