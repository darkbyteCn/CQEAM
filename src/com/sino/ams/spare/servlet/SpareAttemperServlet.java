package com.sino.ams.spare.servlet;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.constant.SpareURLDefine;
import com.sino.ams.spare.dao.AmsItemTransHDAO;
import com.sino.ams.spare.dao.AmsItemTransLDAO;
import com.sino.ams.spare.dao.SpareAttemperDAO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.SpareAttemperModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareAttemperServlet extends BaseServlet {
	   public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String act = req.getParameter("act");
		act = StrUtil.nullToString(act);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsItemTransLDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
			dtoParameter = (AmsItemTransLDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			SpareAttemperDAO situsDAO = new SpareAttemperDAO(user, dtoParameter, conn);
			dtoParameter.setBatchNo(situsDAO.getPDNo());
			if (act.equals("")) {
				req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareAttemperQuery.jsp";
			} else if (act.equals(WebActionConstant.QUERY_ACTION)) {                             //查询出所有
				BaseSQLProducer sqlProducer = new SpareAttemperModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareAttemperQuery.jsp";
			} else if (act.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
				File file = situsDAO.exportFile();
				situsDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (act.equals(WebActionConstant.DETAIL_ACTION)) {    //申领单明细
				AmsItemTransHDTO dtoParameter3 = new AmsItemTransHDTO();
				dtoParameter3.setTransId(req.getParameter("transId"));
				AmsItemTransHDAO itemTransHDAO = new AmsItemTransHDAO(user, dtoParameter3, conn);
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", amsItemTransH);
				//查询行信息
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
				req.setAttribute("AIT_LINES", ldao.getLines4(amsItemTransH.getTransId()));
				forwardURL = SpareURLDefine.SL_ORDER_INFO_PAGE;
			  } else if (act.equals("print")) {    //申领单明细打印
				AmsItemTransHDTO dtoParameter3 = new AmsItemTransHDTO();
				dtoParameter3.setTransId(req.getParameter("transId"));
				AmsItemTransHDAO itemTransHDAO = new AmsItemTransHDAO(user, dtoParameter3, conn);
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", amsItemTransH);
				//查询行信息
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
				req.setAttribute("AIT_LINES", ldao.getLines4(amsItemTransH.getTransId()));
				forwardURL = SpareURLDefine.SL_PRINT_PAGE;
			}
		} catch (PoolException e) {
            e.printLog();
		} catch (DTOException e) {
            e.printLog();
		} catch (QueryException e) {
            e.printLog();
		} catch (DataTransException e) {
            e.printLog();
		} catch (WebFileDownException e) {
            e.printLog();
		} catch (ContainerException e) {
            e.printLog();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
