package com.sino.ams.system.note.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.note.dao.AmsRentDeadlineDAO;
import com.sino.ams.system.note.dto.AmsRentDeadlineDTO;
import com.sino.ams.system.note.model.AmsRentDeadlineModel;
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
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsRentDeadlineServlet</p>
 * <p>Description:程序自动生成服务程序“AmsRentDeadlineServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class AmsRentDeadlineServlet extends BaseServlet {

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
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			AmsRentDeadlineDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsRentDeadlineDTO.class.getName());
			dtoParameter = (AmsRentDeadlineDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AmsRentDeadlineDAO amsRentDeadlineDAO = new AmsRentDeadlineDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				forwardURL = URLDefineList.NOTE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsRentDeadlineModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
                pageDAO.produceWebData();
				forwardURL = URLDefineList.NOTE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
//				AmsRentDeadlineDTO amsRentDeadline = (AmsRentDeadlineDTO)req.getAttribute("详细数据属性，请根据实际情况修改");
//				if(amsRentDeadline == null){
//					amsRentDeadline= dtoParameter;
//				}
                 dtoParameter = new  AmsRentDeadlineDTO();
                req.setAttribute(WebAttrConstant.AMS_RENT_DEADLINE_DTO, dtoParameter);
				forwardURL = URLDefineList.NOTE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				amsRentDeadlineDAO.setDTOClassName(AmsRentDeadlineDTO.class.getName());
				AmsRentDeadlineDTO amsRentDeadline = (AmsRentDeadlineDTO)amsRentDeadlineDAO.getDataByPrimaryKey();
				amsRentDeadline.setCalPattern(CalendarConstant.LINE_PATTERN);
				if(amsRentDeadline == null){
					amsRentDeadline = new AmsRentDeadlineDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
                req.setAttribute(WebAttrConstant.AMS_RENT_DEADLINE_DTO, amsRentDeadline);
				forwardURL = URLDefineList.NOTE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {      //do_save
                amsRentDeadlineDAO.setServletConfig(getServletConfig(req));
                  boolean operateResult  = true;
//                boolean operateResult = amsRentDeadlineDAO.createData();
                amsRentDeadlineDAO.createData();
				message = amsRentDeadlineDAO.getMessage();
				message.setIsError(!operateResult);
				if(operateResult){
					forwardURL = URLDefineList.NOTE_QUERY_PAGE;
				} else {
					req.setAttribute(WebAttrConstant.AMS_RENT_DEADLINE_DTO, dtoParameter);
					forwardURL = URLDefineList.NOTE_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
//				boolean operateResult = amsRentDeadlineDAO.updateData();
                 boolean operateResult = true;
                 amsRentDeadlineDAO.updateData();
				message = amsRentDeadlineDAO.getMessage();
				message.setIsError(!operateResult);
				if(operateResult){
					forwardURL = URLDefineList.NOTE_QUERY_PAGE;
				} else {
					req.setAttribute(WebAttrConstant.AMS_RENT_DEADLINE_DTO, dtoParameter);
					forwardURL = URLDefineList.NOTE_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
                 boolean operateResult = true;
//                boolean operateResult = amsRentDeadlineDAO.deleteData();
				 amsRentDeadlineDAO.deleteData();
				message = amsRentDeadlineDAO.getMessage();
				message.setIsError(!operateResult);
				forwardURL = URLDefineList.NOTE_QUERY_PAGE;
              } else if (action.equals("verifyBarcode")) {                                          //验证barcode是否存在
               String barcode = StrUtil.nullToString(req.getParameter("barcode"));
                boolean success = amsRentDeadlineDAO.verifyBarcode(barcode);
                PrintWriter out = res.getWriter();
                if (success) {
                    out.print("Y");
                }
                out.flush();
                out.close();
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
        } finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
