package com.sino.ams.instrument.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.instrument.dao.AmsInstrumentReturnDAO;
import com.sino.ams.instrument.dto.AmsInstrumentHDTO;
import com.sino.ams.instrument.model.AmsInstrumentReturnModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
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
 * Date: 2007-10-22
 * Time: 17:56:54
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentReturnServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String showMsg = "";
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsInstrumentHDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsInstrumentHDTO.class.getName());
			dtoParameter = (AmsInstrumentHDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AmsInstrumentReturnDAO dao = new AmsInstrumentReturnDAO(user, dtoParameter, conn);
			OptionProducer op = new OptionProducer(user, conn);
			String transSta = dtoParameter.getTransStatus();
			String transStaSelect = op.getDictOption("ORDER_STATUS", transSta);  //头表的单据状态
			req.setAttribute(WebAttrConstant.TRANS_STATUS, transStaSelect);

			if (action.equals("")) {
				forwardURL = "/instrument/instrumentReturn.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsInstrumentReturnModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = "/instrument/instrumentReturn.jsp";

			} else if (action.equals(WebActionConstant.NEW_ACTION)) {  //新增操作
				AmsInstrumentHDTO amsInstrumentInfo = (AmsInstrumentHDTO) req.getAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO);
				if (amsInstrumentInfo == null) {
					amsInstrumentInfo = dtoParameter;
				}
				amsInstrumentInfo.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
				amsInstrumentInfo.setReturnUser(user.getUserId());
//                amsInstrumentInfo.setReturnName(user.getUsername());
				amsInstrumentInfo.setTransStatusName("未完成");
				amsInstrumentInfo.setReturnDate(CalendarUtil.getCurrDate());
				amsInstrumentInfo.setTransType(DictConstant.INS_RET);  //仪器仪表借用单据ams_instrument_info。TRANS_TYPE事物类型
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO, amsInstrumentInfo);
				forwardURL = "/instrument/instrumentReturnDetail.jsp";
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {  //生成归还单（执行归还操作）
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsInstrumentHDTO.class.getName());
				r2.setIgnoreFields(AmsInstrumentHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				String[] barcode = req.getParameterValues("barcode");
				boolean operateResult = dao.submitData(lineSet, barcode);


				message = dao.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					showMsg = "仪器返还单" + dtoParameter.getTransNo() + "已生成!";
				   forwardURL = "/instrument/instrumentReturn.jsp";
				} else {
					forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getTransId();
				}

			} else if (action.equals(WebActionConstant.SAVE_ACTION)) {
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsInstrumentHDTO.class.getName());
				r2.setIgnoreFields(AmsInstrumentHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				String[] barcode = req.getParameterValues("barcode");
				boolean operateResult = dao.saveData(lineSet, barcode);
				message = dao.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
				} else {
					forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getTransId();
				}
			} else if (action.equals("return")) {  //返还操作
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsInstrumentHDTO.class.getName());
				r2.setIgnoreFields(AmsInstrumentHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				String[] barcode = req.getParameterValues("barcode");
				String transId = req.getParameter("transId");
				String transStatus = req.getParameter("transStatus");
				boolean operateResult = dao.returnData(lineSet, barcode, transId, transStatus);
				message = dao.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					showMsg="仪器已归还!";
				   forwardURL = "/instrument/instrumentReturn.jsp";
				} else {
					forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentReturnServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getTransId();
				}


			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				dao.setDTOClassName(AmsInstrumentHDTO.class.getName());
				AmsInstrumentHDTO dto = (AmsInstrumentHDTO) dao.getDataByPrimaryKey();
				if (dto == null) {
					dto = new AmsInstrumentHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				} else {
					req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, dao.getLines());
				}
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO, dto);


				forwardURL = "/instrument/instrumentReturnDetail.jsp";
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
				File file = dao.exportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}

		}
		catch (PoolPassivateException ex) {
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
		} catch (SQLException e) {
			Logger.logError(e);
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (CalendarException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}

		finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (showMsg.equals("")) {
				forwarder.forwardView(forwardURL);
			} else {
				forwarder.forwardOpenerView(forwardURL, showMsg);
			}
		}
	}
}
