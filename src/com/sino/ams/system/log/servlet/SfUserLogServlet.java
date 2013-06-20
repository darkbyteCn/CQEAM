package com.sino.ams.system.log.servlet;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.constant.ResNameConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.log.dao.SfUserLogDAO;
import com.sino.ams.system.log.dto.SfUserLogDTO;
import com.sino.ams.system.log.model.SfUserLogModel;
import com.sino.ams.util.ResUtil;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfUserLogServlet</p>
 * <p>Description:程序自动生成服务程序“SfUserLogServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class SfUserLogServlet extends BaseServlet {

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
		SfUserBaseDTO user = (SfUserBaseDTO)SessionUtil.getUserAccount(req);
		try {
			
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SfUserLogDTO.class.getName());
			SfUserLogDTO dto = (SfUserLogDTO)req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			
			String columeType=req.getParameter("columeType");
			
			//columeType="SYSTEM";
			//columeType="PERSONAL";
			dto.setColumeType(columeType);
			//if("PERSONAL".equals(columeType)){
			//	dto.setUserAccount(user.getUsername());
	        //}
			req.setAttribute("columeType",columeType);
			if (action.equals("")) {
				ResUtil.setAllResName(conn, req, ResNameConstant.USER_LOG_QUERY );
				
				forwardURL = URLDefineList.USER_LOG_PAGE;
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				
				//TODO 2011-10-10
				ResUtil.setAllResName(conn, req, ResNameConstant.USER_LOG_QUERY );
            	
				BaseSQLProducer sqlProducer = new SfUserLogModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("LOG_ID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setCalPattern(CAL_PATT_50);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.USER_LOG_PAGE;
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
				SfUserLogDAO logDAO = new SfUserLogDAO(user, dto, conn);
				String excelType = req.getParameter("excelType");
				File file = logDAO.getExportFile(excelType);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
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
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(CustMessageKey.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(CustMessageKey.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(CustMessageKey.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (ContainerException ex) {
			ex.printLog();
			message = getMessage(CustMessageKey.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!StrUtil.isEmpty(forwardURL)){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
