package com.sino.ams.instrument.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.instrument.dao.AmsInstrumentBorrowInvOutDAO;
import com.sino.ams.instrument.dto.AmsInstrumentRegistrationDTO;
import com.sino.ams.instrument.model.AmsInstrumentBorrowInvOutModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by MyEclipse.
 * User: yushibo
 * Date: 2009-1-11
 * Time: 15:35:53
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentBorrowInvOutServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String showMsg = "";
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsInstrumentRegistrationDTO dto = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsInstrumentRegistrationDTO.class.getName());
			dto = (AmsInstrumentRegistrationDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AmsInstrumentBorrowInvOutDAO dao = new AmsInstrumentBorrowInvOutDAO(user, dto, conn);
			OptionProducer op = new OptionProducer(user, conn);
			String action = dto.getAct();
			
			String instrumentMame = op.getInstrument("") ;     //产生仪器仪表的地市选项
			req.setAttribute(WebAttrConstant.BORROW_OPTION, instrumentMame);
			if (action.equals("")) {
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_INV_OUT, dto);
				forwardURL = "/instrument/instrumentBorrowInvOut.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				
				
				BaseSQLProducer sqlProducer = new AmsInstrumentBorrowInvOutModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("BARCODE");
				checkProp.addDbField("WORKORDER_OBJECT_NO");
				pageDAO.setWebCheckProp(checkProp);
				
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_INV_OUT, dto);
				forwardURL = "/instrument/instrumentBorrowInvOut.jsp";
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {		
				AmsInstrumentRegistrationDTO amsInstrumentInfo = (AmsInstrumentRegistrationDTO) req.getAttribute(WebAttrConstant.AMS_INSTRUMENT_INV_OUT);
                if (amsInstrumentInfo == null) {
                    amsInstrumentInfo = dto;
                }
                RequestParser parser = new RequestParser();
//				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
//				checkProp.addDbField("BARCODE");
//				checkProp.addDbField("WORKORDER_OBJECT_NO");
//				checkProp.addDbField("HANDLE_DATE");
//				checkProp.addDbField("HANDLE_USER_ID");
//				checkProp.addDbField("RESPONSIBILITY_USER");
//				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] barcodes = parser.getParameterValues("barcode");
				String[] workorderObjectNos = parser.getParameterValues("workorderObjectNo");
				String[] handleDates = parser.getParameterValues("handleDate");
				String[] handleUserIds = parser.getParameterValues("handleUserId");
				String[] responsibilityUsers = parser.getParameterValues("responsibilityUser");
				String[] borrowDates = parser.getParameterValues("borrowDate");
                
//				List exeFields = new ArrayList();
//				exeFields.add("userName");
//				req2DTO.setIgnoreFields(AmsInstrumentRegistrationDTO.class, exeFields);
//                DTOSet dtoSet = req2DTO.getDTOSet(req);
//                System.out.println(dtoSet.getSize());
//                for(int i=0; i<dtoSet.getSize(); i++){
//                	System.out.println(dtoSet.getDTO(i));
//                }
				
				for(int i=0; i<barcodes.length; i++){
					dao.updateBorrowLogData(conn, barcodes[i], handleUserIds[i], handleDates[i], responsibilityUsers[i], borrowDates[i], workorderObjectNos[i]);
				}
                
				message = dao.getMessage();
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_INV_OUT, amsInstrumentInfo);
//                showMsg = "仪器仪表借用批量实物出库成功!";
//              forwardURL = URLDefineList.INSTRUMENT_REGISTRATION;
                forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentBorrowInvOutServlet?act=" + WebActionConstant.QUERY_ACTION;
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
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (CalendarException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
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
