package com.sino.ams.spare.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.calen.DateConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.dao.BjDbDAO;
import com.sino.ams.spare.dao.SpareMoveTimeOutDAO;
import com.sino.ams.spare.dto.AmsItemAllocateHDTO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.model.SpareMoveTimeOutModel;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class SpareMoveTimeOutServlet extends BaseServlet {
	  public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String act = req.getParameter("act");
		act = StrUtil.nullToString(act);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsItemTransHDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
			dtoParameter = (AmsItemTransHDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			SpareMoveTimeOutDAO situsDAO = new SpareMoveTimeOutDAO(user, dtoParameter, conn);
			OptionProducer prd = new OptionProducer(user, conn);
			String ouOption = prd.getAllOU(dtoParameter.getOrganizationId(), true);
			req.setAttribute(WebAttrConstant.OU_OPTION, ouOption);
            String ouOption2 = prd.getAllOrganization(dtoParameter.getToOrganizationId(), true);
			req.setAttribute(WebAttrConstant.OU_OPTION2, ouOption2);
            String transStaSelect = prd.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
			req.setAttribute(WebAttrConstant.TRANS_STATUS, transStaSelect);
			String transTypeSelect = prd.getDictOption2("ORDER_TYPE_SPARE", dtoParameter.getTransType());
			req.setAttribute(WebAttrConstant.TRANS_TYPE, transTypeSelect);
			if (act.equals("")) {
				req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareTimeOutQuery.jsp";
			} else if (act.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new SpareMoveTimeOutModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				req.setAttribute(WebAttrConstant.AMS_SPARE_DTO, dtoParameter);
				forwardURL = "/spare/spareTimeOutQuery.jsp";
			} else if (act.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
				File file = situsDAO.exportFile();
				situsDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (act.equals("approve")) { //备件调拨明细
				forwardURL = "/spare/spareTimeInfo.jsp";
			}  else if (act.equals(WebActionConstant.DETAIL_ACTION)) { //备件调拨单
				AmsItemAllocateHDTO dtoParameter3 = new AmsItemAllocateHDTO();
				   dtoParameter3.setTransId(req.getParameter("transId"));
				BjDbDAO itemTransHDAO = new BjDbDAO(user, dtoParameter3, conn);

				itemTransHDAO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
				itemTransHDAO.setCalPattern(DateConstant.LINE_PATTERN);
				AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemAllocateHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", amsItemTransH);
				//查询行信息
				req.setAttribute("AIT_LINES", itemTransHDAO.getLines(amsItemTransH.getTransId()));
				forwardURL = "/spare/spareTimeInfo.jsp";
			 }  else if (act.equals("print")) { //备件调拨单打印
				AmsItemAllocateHDTO dtoParameter3 = new AmsItemAllocateHDTO();
				   dtoParameter3.setTransId(req.getParameter("transId"));
				BjDbDAO itemTransHDAO = new BjDbDAO(user, dtoParameter3, conn);

				itemTransHDAO.setDTOClassName(AmsItemAllocateHDTO.class.getName());
				itemTransHDAO.setCalPattern(DateConstant.LINE_PATTERN);
				AmsItemAllocateHDTO amsItemTransH = (AmsItemAllocateHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemAllocateHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", amsItemTransH);
				//查询行信息
				req.setAttribute("AIT_LINES", itemTransHDAO.getLines(amsItemTransH.getTransId()));
				forwardURL = "/spare/print/spareDBPrint.jsp";
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
