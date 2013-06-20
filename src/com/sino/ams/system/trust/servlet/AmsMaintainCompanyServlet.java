package com.sino.ams.system.trust.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.trust.dao.AmsMaintainCompanyDAO;
import com.sino.ams.system.trust.dao.AmsMaintainFilesDAO;
import com.sino.ams.system.trust.dto.AmsMaintainCompanyDTO;
import com.sino.ams.system.trust.dto.AmsMaintainFilesDTO;
import com.sino.ams.system.trust.model.AmsMaintainCompanyModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
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
 * <p>Title: AmsMaintainCompanyServlet</p>
 * <p>Description:程序自动生成服务程序“AmsMaintainCompanyServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-yuanshuai
 * @version 1.0
 */


public class AmsMaintainCompanyServlet extends BaseServlet {

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
			AmsMaintainCompanyDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsMaintainCompanyDTO.class.getName());
			dtoParameter = (AmsMaintainCompanyDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			AmsMaintainCompanyDAO amsMaintainCompanyDAO = new AmsMaintainCompanyDAO(user, dtoParameter, conn);
			OptionProducer optProducer = new OptionProducer(user, conn);

			if (action.equals("")) {
				//获取区县信息
				setCountOption( optProducer , dtoParameter , req  );
				

				forwardURL = URLDefineList.TRUSTCOR_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				//获取区县信息
				setCountOption( optProducer , dtoParameter , req  );
				//分页查询
				BaseSQLProducer sqlProducer = new AmsMaintainCompanyModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();

				forwardURL = URLDefineList.TRUSTCOR_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				dtoParameter.setAct( WebActionConstant.CREATE_ACTION );
				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_ATTR, dtoParameter);
				//获取区县信息
				setCountOption( optProducer , dtoParameter , req  );
				
				forwardURL = URLDefineList.TRUSTCOR_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				dtoParameter.setAct( WebActionConstant.UPDATE_ACTION );
				//根据主键获取DTO
				amsMaintainCompanyDAO.setDTOClassName(AmsMaintainCompanyDTO.class.getName());
				AmsMaintainCompanyDTO amsMaintainCompany = (AmsMaintainCompanyDTO) amsMaintainCompanyDAO.getDataByPrimaryKey();
				if (amsMaintainCompany == null) {
					amsMaintainCompany = new AmsMaintainCompanyDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.MAINTAIN_CORP_ATTR, amsMaintainCompany);
				//获取区县信息
				setCountOption( optProducer , amsMaintainCompany , req  );
				
				AmsMaintainFilesDTO fileDTO = new AmsMaintainFilesDTO();
				fileDTO.setCompanyId(dtoParameter.getCompanyId());
				AmsMaintainFilesDAO amsMaintainFilesDAO = new AmsMaintainFilesDAO(user, fileDTO, conn);
				amsMaintainFilesDAO.setDTOClassName(AmsMaintainFilesDTO.class.getName());
				DTOSet files = (DTOSet) amsMaintainFilesDAO.getDataByForeignKey("companyId");
				req.setAttribute(WebAttrConstant.ATTACH_FILES, files);
				forwardURL = URLDefineList.TRUSTCOR_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				amsMaintainCompanyDAO.setServletConfig(getServletConfig(req));
				amsMaintainCompanyDAO.createData();
				message = amsMaintainCompanyDAO.getMessage();

				dtoParameter = (AmsMaintainCompanyDTO) amsMaintainCompanyDAO.getDTOParameter();
				forwardURL = URLDefineList.TRUSTCOR_QUERY_SERVLET;
				forwardURL += "&companyId=" + dtoParameter.getCompanyId();
				String isAttachFile = req.getParameter("isAttachFile");
				req.setAttribute(WebAttrConstant.ATTACH_FILE_ATTR, isAttachFile);

			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				amsMaintainCompanyDAO.setServletConfig(getServletConfig(req));
				amsMaintainCompanyDAO.updateData();
				message = amsMaintainCompanyDAO.getMessage();

				dtoParameter = (AmsMaintainCompanyDTO) amsMaintainCompanyDAO.getDTOParameter();
				forwardURL = URLDefineList.TRUSTCOR_QUERY_SERVLET;
				forwardURL += "&companyId=" + dtoParameter.getCompanyId();
				String isAttachFile = req.getParameter("isAttachFile");
				req.setAttribute(WebAttrConstant.ATTACH_FILE_ATTR, isAttachFile);

			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				//删除DTO
				amsMaintainCompanyDAO.deleteData();
				message = amsMaintainCompanyDAO.getMessage();

				forwardURL = URLDefineList.TRUSTCOR_QUERY_SERVLET;
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
		} catch (ContainerException ex) {
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
	
	private void setCountOption( OptionProducer optProducer , AmsMaintainCompanyDTO dtoParameter , HttpServletRequest req ) throws QueryException{
		 String countyOption = optProducer.getCountyOption(dtoParameter.getCountyCode() );
		 req.setAttribute(WebAttrConstant.COUNTY_OPTION, countyOption);
	}
}
