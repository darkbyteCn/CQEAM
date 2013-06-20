package com.sino.ams.spare.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.dto.Request2DTO;
import com.sino.base.db.conn.DBManager;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.web.ServletForwarder;
import com.sino.base.exception.*;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.*;
import com.sino.ams.spare.dto.AmsSpareCategoryDTO;
import com.sino.ams.spare.dao.SpareConfirmDAO;
import com.sino.ams.spare.model.SpareConfirmModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareConfirmServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsSpareCategoryDTO.class.getName());
			AmsSpareCategoryDTO dto = (AmsSpareCategoryDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			SpareConfirmDAO amsSpareCategoryDAO = new SpareConfirmDAO(user, dto, conn);
			amsSpareCategoryDAO.setServletConfig(getServletConfig(req));
			OptionProducer prd = new OptionProducer(user, conn);
            String vendorOption = prd.getSpareVendorOption(dto.getVendorId());
            req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
            if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.SPARE_CATEORY_CONFIRM;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {   //查询
				BaseSQLProducer sqlProducer = new SpareConfirmModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.SPARE_CATEORY_CONFIRM;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {  //显示明细
                amsSpareCategoryDAO.setDTOClassName(AmsSpareCategoryDTO.class.getName());
                AmsSpareCategoryDTO amsSpareCategory = (AmsSpareCategoryDTO)amsSpareCategoryDAO.getDataByPrimaryKey();
                req.setAttribute(WebAttrConstant.SPARE_CATEGORY_DTO, amsSpareCategory);
				forwardURL = URLDefineList.SPARE_CATEORY_INFO;
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {  //备件确认
                String fromBarcode = dto.getBarcode1();
                String toBarcode = dto.getBarcode();
                String ret = amsSpareCategoryDAO.getReplaceCategory(fromBarcode, toBarcode);
                req.setAttribute(QueryConstant.QUERY_DTO, dto);
                forwardURL = URLDefineList.SPARE_CATEORY_CONFIRM;
                if (ret.equalsIgnoreCase("Y")) {
                    message = getMessage(CustMessageKey.SPARE_REPLACE_SUCCESS);
                    message.setIsError(false);
                } else {
                    message = getMessage(CustMessageKey.SPARE_REPLACE_FAILURE);
                    message.setIsError(true);
                }
            } else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
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
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
