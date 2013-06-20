package com.sino.ams.spare.servlet;

import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.dto.Request2DTO;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.ServletForwarder;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.db.conn.DBManager;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.spare.dto.SpareHistoryDTO;
import com.sino.ams.spare.dao.SpareMonthlyReportDAO;
import com.sino.ams.spare.model.SpareMonthlyReportModel;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.File;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2010-2-4
 * Time: 10:24:18
 * To change this template use File | Settings | File Templates.
 */
public class SpareMonthlyReportServlet extends BaseServlet {
	 public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String act = req.getParameter("act");
		act = StrUtil.nullToString(act);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			SpareHistoryDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(SpareHistoryDTO.class.getName());
			dtoParameter = (SpareHistoryDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			SpareMonthlyReportDAO situsDAO = new SpareMonthlyReportDAO(user, dtoParameter, conn);
			OptionProducer op = new OptionProducer(user, conn);
            String vendorOption = op.getSpareVendorOption(dtoParameter.getVendorId());
            req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
            if (act.equals("")) {
				req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareMonthlyReport.jsp";
			} else if (act.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new SpareMonthlyReportModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
				req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareMonthlyReport.jsp";
			} else if (act.equals(WebActionConstant.EXPORT_ACTION)) {
				File file = situsDAO.exportFile();
				situsDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}
		} catch (PoolException e) {
			e.printStackTrace();
			Logger.logError(e.toString());
		} catch (DTOException e) {
			e.printStackTrace();
			Logger.logError(e.toString());

		} catch (QueryException e) {
			e.printStackTrace();
		} catch (DataTransException e) {
			e.printStackTrace();
		} catch (WebFileDownException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}