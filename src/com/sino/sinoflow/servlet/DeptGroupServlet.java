package com.sino.sinoflow.servlet;

import com.sino.base.constant.calen.CalendarConstant;
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
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.constant.URLDefineList;
import com.sino.sinoflow.dao.DeptGroupDAO;
import com.sino.sinoflow.dto.DeptGroupDTO;
import com.sino.sinoflow.dto.DeptGroupLineDTO;
import com.sino.sinoflow.model.DeptGroupModel;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

/**
 * Title: SfGroupServlet
 * Description:程序自动生成服务程序“SfGroupServlet”，请根据需要自行修改
 * Copyright: Copyright (c) 2007
 * Company: 北京思诺博信息技术有限公司
 * @author mshtang
 * @version 1.0
 */

public class DeptGroupServlet extends BaseServlet {

	/**
	 * @param req
	 *            HttpServletRequest
	 * @param res
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */


	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
//        String preAction = req.getParameter("preAct");
        action = StrUtil.nullToString(action);
		Connection conn = null;

        try {
			SfUserBaseDTO user = (SfUserBaseDTO) SessionUtil.getUserAccount(req);// 从session中获取数据，根据实际情况自行修改。
			DeptGroupDTO dtoParameter;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(DeptGroupDTO.class.getName());
			dtoParameter = (DeptGroupDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();

            if(conn == null)
                return;

            if (action.equals("")) {
				forwardURL = URLDefineList.DEPT_GROUP_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new DeptGroupModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(20);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.DEPT_GROUP_QUERY_PAGE;
            } else if(action.equals("MATCH_ACTION")) {
                DeptGroupDAO deptDAO = new DeptGroupDAO(user, dtoParameter, conn);
                deptDAO.produceMatchData(req);
                forwardURL = URLDefineList.DEPT_GROUP_QUERY_PAGE;
            } else if (action.equals(WebActionConstant.SAVE_ACTION)) {
                req2DTO.setDTOClassName(DeptGroupLineDTO.class.getName());
                req2DTO.setIgnoreFields(DeptGroupDTO.class);
                DTOSet deptLines = req2DTO.getDTOSet(req);
                DeptGroupDAO deptDAO = new DeptGroupDAO(user, dtoParameter, conn);
                deptDAO.updateGroup(deptLines);
                BaseSQLProducer sqlProducer = new DeptGroupModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setPageSize(20);
                pageDAO.produceWebData();
                forwardURL = URLDefineList.DEPT_GROUP_QUERY_PAGE;
			} else if(action.equals(WebActionConstant.CREATE_ACTION)) {
                req2DTO.setDTOClassName(DeptGroupLineDTO.class.getName());
                req2DTO.setIgnoreFields(DeptGroupDTO.class);
                DTOSet deptLines = req2DTO.getDTOSet(req);
                DeptGroupDAO deptDAO = new DeptGroupDAO(user, dtoParameter, conn);
                deptDAO.createGroup(deptLines);
                deptDAO.produceMatchData(req);
                forwardURL = URLDefineList.DEPT_GROUP_QUERY_PAGE;
            } else if(action.equals(WebActionConstant.EXPORT_ACTION)) {
                DeptGroupDAO deptGroupDAO = new DeptGroupDAO(user, dtoParameter, conn);
                File file = deptGroupDAO.exportFile();
                deptGroupDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            } else if(action.equals("MATCH_EXPORT_ACTION")) {
                DeptGroupDAO deptGroupDAO = new DeptGroupDAO(user, dtoParameter, conn);
                File file = deptGroupDAO.exportMatchFile();
                deptGroupDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                WebFileDownload fileDown = new WebFileDownload(req, res);
                fileDown.setFilePath(file.getAbsolutePath());
                fileDown.download();
                file.delete();
            }
		} catch (PoolException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.CONN_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (DataHandleException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.CREATE_DATA_FAILURE);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (QueryException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (SQLModelException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (WebFileDownException ex) {
            Logger.logError(ex);
            message = getMessage(MsgKeyConstant.CONN_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        }  finally {
            closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
