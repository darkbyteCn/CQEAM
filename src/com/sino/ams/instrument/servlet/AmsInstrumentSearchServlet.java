package com.sino.ams.instrument.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.instrument.dao.AmsInstrumentInfoDAO;
import com.sino.ams.instrument.dto.AmsInstrumentInfoDTO;
import com.sino.ams.instrument.model.AmsInstrumentInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
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
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-10-30
 * Time: 16:46:20
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentSearchServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
	   try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsInstrumentInfoDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsInstrumentInfoDTO.class.getName());
			dtoParameter = (AmsInstrumentInfoDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AmsInstrumentInfoDAO amsInstrumentInfoDAO = new AmsInstrumentInfoDAO(user, dtoParameter, conn);
			if (action.equals("")) {
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO, dtoParameter);
				forwardURL = "/instrument/instrumentSearch.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsInstrumentInfoModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = "/instrument/instrumentSearch.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				AmsInstrumentInfoDTO amsInstrumentInfo = (AmsInstrumentInfoDTO) req.getAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO);
				if (amsInstrumentInfo == null) {
					amsInstrumentInfo = dtoParameter;
				}
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO, amsInstrumentInfo);
				forwardURL = URLDefineList.INSTRUMENT_DETAIL;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				amsInstrumentInfoDAO.setDTOClassName(AmsInstrumentInfoDTO.class.getName());
				AmsInstrumentInfoDTO amsInstrumentInfo = (AmsInstrumentInfoDTO) amsInstrumentInfoDAO.getDataByPrimaryKey();
				if (amsInstrumentInfo == null) {
					amsInstrumentInfo = new AmsInstrumentInfoDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO, amsInstrumentInfo);
				forwardURL = "/instrument/instrumentSearchDetail.jsp";
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				/*boolean operateResult = amsInstrumentInfoDAO.createData();
					message = amsInstrumentInfoDAO.getMessage();
					message.setIsError(!operateResult);
					if(operateResult){
						forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
					} else {
						req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_DTO, dtoParameter);
						forwardURL = URLDefineList.INSTRUMENT_DETAIL;
					} */
				String itemcode1=req.getParameter("itemcode");
//                amsInstrumentInfoDAO.insertData(conn,itemcode1);
				forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				amsInstrumentInfoDAO.updateData();
				message = amsInstrumentInfoDAO.getMessage();
				forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
				File file = amsInstrumentInfoDAO.exportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			   } else if (action.equals(AMSActionConstant.STATISTICS_ACTION)) {   //仪器仪表统计操作
				  AmsInstrumentInfoModel sqlModel = new AmsInstrumentInfoModel(user, dtoParameter);
				  SQLModel tmodel = sqlModel.getSQueryModel();
				  WebPageView wpv = new WebPageView(req, conn);
//                wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
				  wpv.produceWebData(tmodel);
//                  forwardURL = URLDefineList.INSTRUMENT_QUERY;
				   forwardURL = "/instrument/instrumentSearch.jsp";
			   } else if (action.equals("EXPORT_ACTION2")) {
				 File file = amsInstrumentInfoDAO.exportFile2();
				 WebFileDownload fileDown = new WebFileDownload(req, res);
				 fileDown.setFilePath(file.getAbsolutePath());
				 fileDown.download();
				 file.delete();
			}
			else {
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
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}
		catch (DataHandleException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}  finally {
		   DBManager.closeDBConnection(conn);
		   setHandleMessage(req, message);
		   if (!StrUtil.isEmpty(forwardURL)) {
			   ServletForwarder forwarder = new ServletForwarder(req, res);
			   forwarder.forwardView(forwardURL);
		   }
	   }
	}
}
