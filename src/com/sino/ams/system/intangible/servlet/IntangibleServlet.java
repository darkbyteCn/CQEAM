package com.sino.ams.system.intangible.servlet;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.intangible.dao.IntangibleDAO;
import com.sino.ams.system.intangible.dto.IntangibleDTO;
import com.sino.ams.system.intangible.model.IntangibleModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsObjectServlet</p>
 * <p>Description:程序自动生成服务程序“EtsObjectServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class IntangibleServlet extends BaseServlet {

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
			IntangibleDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(IntangibleDTO.class.getName());
			dtoParameter = (IntangibleDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			IntangibleDAO intangDAO = new IntangibleDAO(user, dtoParameter, conn);
			OptionProducer optProducer = new OptionProducer(user, conn); //获取区县信息
			String cityOption = optProducer.getAllOrganization(dtoParameter.getOrganizationId(),true);
			req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);

			if (action.equals("")) {
//                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
				forwardURL = URLDefineList.INTANGIBLE_QUERY;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {       //查询
				BaseSQLProducer sqlProducer = new IntangibleModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
//                req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
				forwardURL = URLDefineList.INTANGIBLE_QUERY;
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
					File file = intangDAO.exportFile();
					intangDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
					WebFileDownload fileDown = new WebFileDownload(req, res);
					fileDown.setFilePath(file.getAbsolutePath());
					fileDown.download();
					file.delete();
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {      //点新增操作-未使用
				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
				forwardURL = URLDefineList.INTANGIBLE_QUERY;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {        //点明细操作-未使用
				intangDAO.setDTOClassName(IntangibleDTO.class.getName());
				IntangibleDTO etsObject = (IntangibleDTO) intangDAO.getDataByPrimaryKey();
				if (etsObject == null) {
					etsObject = new IntangibleDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, etsObject);
				forwardURL = URLDefineList.INTANGIBLE_QUERY;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {  //点save操作-未使用
				intangDAO.createData();
				message = intangDAO.getMessage();
				req.setAttribute(WebAttrConstant.ETS_OBJECT_DTO, dtoParameter);
				forwardURL = URLDefineList.INTANGIBLE_QUERY;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {    //修改操作-未使用
				intangDAO.updateData();
				message = intangDAO.getMessage();
				forwardURL = URLDefineList.INTANGIBLE_QUERY;
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
		} catch (WebFileDownException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
