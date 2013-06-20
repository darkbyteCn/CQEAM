package com.sino.ams.newasset.servlet;

import com.sino.ams.newasset.dto.ItemFinanceReportDTO;
import com.sino.ams.newasset.service.ItemFinanceReportService;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;


public class ItemFinanceReportServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Connection conn = null;
        Message message = SessionUtil.getMessage(req);
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(ItemFinanceReportDTO.class.getName());
			ItemFinanceReportDTO dto = (ItemFinanceReportDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            ItemFinanceReportService reportService = new ItemFinanceReportService(user, dto, conn);
            DTOSet financeReport = reportService.getItemFinanceReport();
            forwardURL = "/newasset/financeReport.jsp";
            req.setAttribute("financeReport", financeReport);
		} catch (Throwable ex) {
            Logger.logError(ex);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!forwardURL.equals("")){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}

