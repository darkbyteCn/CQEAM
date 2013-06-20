package com.sino.ams.instrument.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.instrument.dao.AmsInstrumentEamYbMaintainReturnDAO;
import com.sino.ams.instrument.dto.AmsInstrumentEamYbMaintainDTO;
import com.sino.ams.instrument.model.AmsInstrumentEamYbMaintainReturnModel;
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
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
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
 * Date: 2009-1-15
 * Time: 9:53:53
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentEamYbMaintainReturnServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String showMsg = "";
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsInstrumentEamYbMaintainDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsInstrumentEamYbMaintainDTO.class.getName());
			dtoParameter = (AmsInstrumentEamYbMaintainDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AmsInstrumentEamYbMaintainReturnDAO dao = new AmsInstrumentEamYbMaintainReturnDAO(user, dtoParameter, conn);
			OptionProducer op = new OptionProducer(user, conn);
//			String transSta = dtoParameter.getTransStatus();
//			String transStaSelect = op.getDictOption("ORDER_STATUS", transSta);  //--单据状态（新增，暂存，完成，撤销）
//			req.setAttribute(WebAttrConstant.TRANS_STATUS, transStaSelect); //产生单据状态的下拉框
			String instrumentMame = op.getInstrument("") ;     //产生仪器仪表的地市选项
			req.setAttribute(WebAttrConstant.BORROW_OPTION, instrumentMame);
			if (action.equals("")) {
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_REPAIR_RETURN, dtoParameter);
				forwardURL = "/instrument/instrumentRepairReturn.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsInstrumentEamYbMaintainReturnModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("BARCODE");
				pageDAO.setWebCheckProp(checkProp);
				
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_REPAIR_RETURN, dtoParameter);
				forwardURL = "/instrument/instrumentRepairReturn.jsp";
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
				AmsInstrumentEamYbMaintainDTO amsInstrumentInfo = (AmsInstrumentEamYbMaintainDTO) req.getAttribute(WebAttrConstant.AMS_INSTRUMENT_INV_OUT);
                if (amsInstrumentInfo == null) {
                    amsInstrumentInfo = dtoParameter;
                }
                RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				
				String[] barcodes = parser.getParameterValues("barcode");
				dao.updateBorrowLogData(barcodes);
				
				message = dao.getMessage();
                req.setAttribute(WebAttrConstant.AMS_INSTRUMENT_REPAIR_RETURN, amsInstrumentInfo);
                //showMsg = "仪器仪表登记卡批量删除成功!";
//              forwardURL = URLDefineList.INSTRUMENT_REGISTRATION;
                forwardURL = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentEamYbMaintainReturnServlet?act=" + WebActionConstant.QUERY_ACTION;
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
		} catch (StrException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException e) {
			e.printLog();
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
