package com.sino.ams.instrument.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.instrument.dao.AmsInstrumentBorrowDAO;
import com.sino.ams.instrument.dto.AmsInstrumentHDTO;
import com.sino.ams.instrument.model.AmsInstrumentBorrowModel;
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
 * Time: 17:55:53
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentBorrowServlet extends BaseServlet {
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
			AmsInstrumentBorrowDAO dao = new AmsInstrumentBorrowDAO(user, dtoParameter, conn);
			OptionProducer op = new OptionProducer(user, conn);
			String transSta = dtoParameter.getTransStatus();
			String transStaSelect = op.getDictOption("ORDER_STATUS", transSta);  //--单据状态（新增，暂存，完成，撤销）
			req.setAttribute(WebAttrConstant.TRANS_STATUS, transStaSelect); //产生单据状态的下拉框
			String instrumentMame = op.getInstrument("") ;     //产生仪器仪表的地市选项
			req.setAttribute(WebAttrConstant.BORROW_OPTION, instrumentMame);
			if (action.equals("")) {
				forwardURL = "/instrument/instrumentBorrow.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsInstrumentBorrowModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = "/instrument/instrumentBorrow.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				AmsInstrumentHDTO amsInstrumentInfo = (AmsInstrumentHDTO) req.getAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO);
				if (amsInstrumentInfo == null) {
					amsInstrumentInfo = dtoParameter;
				}
				amsInstrumentInfo.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
//                amsInstrumentInfo.setBorrowUser(user.getUserId());
//                amsInstrumentInfo.setBorrowName(user.getUsername());
				amsInstrumentInfo.setTransStatusName("新增");
				amsInstrumentInfo.setBorrowDate(CalendarUtil.getCurrDate());
				amsInstrumentInfo.setTransType(DictConstant.INS_BRW);  //交易类型:借用单据
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO, amsInstrumentInfo);
//                if((!user.isProvinceUser()) && (!user.isSysAdmin())){
					forwardURL = "/instrument/instrumentBorrowDetail.jsp";  //地市公司内部借用
//                }else{
//                    forwardURL = "/instrument/instruProvBorrow.jsp";    //地市向省公司借用
//                }
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsInstrumentHDTO.class.getName());
				r2.setIgnoreFields(AmsInstrumentHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				String[] barcode = req.getParameterValues("barcode");
				boolean operateResult = dao.submitData(lineSet, barcode);
				message = dao.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					showMsg = "仪器借用单" + dtoParameter.getTransNo() + "已生成!";
					forwardURL = "/instrument/instrumentBorrow.jsp";
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
				boolean operateResult = dao.saveData(lineSet, barcode);        //暂存
				message = dao.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
				} else {
					forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getTransId();
				}
			} else if (action.equals("borrow")) {
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsInstrumentHDTO.class.getName());
				r2.setIgnoreFields(AmsInstrumentHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				String[] barcode = req.getParameterValues("barcode");
				String transId = req.getParameter("transId");
				String borrowName = req.getParameter("borrowUser");
				String transStatus = req.getParameter("transStatus");
				boolean operateResult = dao.borrowData(lineSet, barcode, borrowName, transId, transStatus);
				message = dao.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					showMsg="仪器已借出!";
				   forwardURL = "/instrument/instrumentBorrow.jsp";
				} else {
					forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getTransId();
				}


			} else if (action.equals("repal")) {
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsInstrumentHDTO.class.getName());
				r2.setIgnoreFields(AmsInstrumentHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				String transId = req.getParameter("transId");
				String transStatus = req.getParameter("transStatus");
				String[] barcode = req.getParameterValues("barcode");
				boolean operateResult = dao.repalData(lineSet, transId, transStatus, barcode);
				message = dao.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
                    showMsg = "仪器仪表撤销成功!";
                    forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=" + WebActionConstant.QUERY_ACTION;
//                    forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
				} else {
					forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getTransId();
				}

			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				dao.setDTOClassName(AmsInstrumentHDTO.class.getName());
				AmsInstrumentHDTO dto = (AmsInstrumentHDTO) dao.getDataByPrimaryKey();
				dto.setCalPattern(CalendarConstant.LINE_PATTERN);
				if (dto == null) {
					dto = new AmsInstrumentHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				} else {
					req.setAttribute(WebAttrConstant.AMS_INSTRUMENTL_DTO, dao.getLines());
				}
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO, dto);


				forwardURL = "/instrument/instrumentBorrowDetail.jsp";
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
				File file = dao.exportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			 } else if (action.equals(AMSActionConstant.BORROW_ACTION)) {   //借用操作
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsInstrumentHDTO.class.getName());
				r2.setIgnoreFields(AmsInstrumentHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				String[] barcode = req.getParameterValues("barcode");
				boolean operateResult = dao.borrowData(lineSet, barcode);
				message = dao.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					showMsg = "仪器借用单" + dtoParameter.getTransNo() + "已生成!";
//                    forwardURL = "/instrument/instrumentBorrow.jsp";
                    forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=" + WebActionConstant.QUERY_ACTION;
                } else {
					forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getTransId();
				}
			} else if (action.equals(AMSActionConstant.TEMP_SAVE)) {   //暂存操作
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsInstrumentHDTO.class.getName());
				r2.setIgnoreFields(AmsInstrumentHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				String[] barcode = req.getParameterValues("barcode");
				boolean operateResult = dao.tempSaveData(lineSet, barcode);
				message = dao.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
//					 forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
                    showMsg = "仪器仪表暂存成功!";
                    forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=" + WebActionConstant.QUERY_ACTION;
                } else {
					forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getTransId();
				}
			  } else if (action.equals("borrowProv")) {   //地市向省公司借用操作
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsInstrumentHDTO.class.getName());
				r2.setIgnoreFields(AmsInstrumentHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				String[] barcode = req.getParameterValues("barcode");
				boolean operateResult = dao.borrowProData(lineSet, barcode);
				message = dao.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					showMsg = "仪器借用单" + dtoParameter.getTransNo() + "已生成!";
					forwardURL = "/instrument/instrumentBorrow.jsp";
				} else {
					forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowServlet?act=" + WebActionConstant.DETAIL_ACTION
							+ "&transId=" + dtoParameter.getTransId();
				}
			} else if (action.equals("newProv")) {              //地市向省公司借用页面的新增操作
				AmsInstrumentHDTO amsInstrumentInfo = (AmsInstrumentHDTO) req.getAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO);
				if (amsInstrumentInfo == null) {
					amsInstrumentInfo = dtoParameter;
				}
				amsInstrumentInfo.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
				amsInstrumentInfo.setTransStatusName("新增");
				amsInstrumentInfo.setBorrowDate(CalendarUtil.getCurrDate());
				amsInstrumentInfo.setTransType(DictConstant.INS_BRW);  //交易类型:借用单据
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENTH_DTO, amsInstrumentInfo);
					forwardURL = "/instrument/instruProvBorrow.jsp";    //地市向省公司借用
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
