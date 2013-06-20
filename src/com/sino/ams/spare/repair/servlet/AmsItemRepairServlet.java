package com.sino.ams.spare.repair.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
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
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.repair.dao.AmsItemRepairDAO;
import com.sino.ams.spare.repair.model.AmsItemRepairModel;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: Sunny song
 * Date: 2007-11-16
 * Time: 6:02:24
 * Edited:jiachuanchuan
 * To change this template use File | Settings | File Templates.
 */

public class AmsItemRepairServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);//获得帐户信息
			EtsSystemItemDTO dtoParameter = null;//bean类，存储信息
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsSystemItemDTO.class.getName()); //设置欲构造的DTOClass的名称
			dtoParameter = (EtsSystemItemDTO) req2DTO.getDTO(req);   //从表单里取数据来构造单个DTO
			dtoParameter.setCalPattern(CalendarConstant.LINE_PATTERN);//设置日历格式
			conn = DBManager.getDBConnection();
			OptionProducer optProducer = new OptionProducer(user, conn);
			//组织
			String ouoption = optProducer.getAllOrganization(dtoParameter.getOrgId(),true);
			req.setAttribute(WebAttrConstant.OU_OPTION, ouoption);

			AmsItemRepairDAO amsItemRepairDAO = new AmsItemRepairDAO(user, dtoParameter, conn);
			amsItemRepairDAO.setCalPattern(CalendarConstant.LINE_PATTERN);

//            String repairQuery = dtoParameter.getRepairQuery();
			String repairQuery = req.getParameter("repairQuery") ;

			if (action.equals("")) {
				if (repairQuery.equals(DictConstant.ITEM_REPAIR_QUERY)) {
					forwardURL = URLDefineList.QRY_ITEM_REPAIR;
				} else if (repairQuery.equals(DictConstant.VENTOR_REPAIR_QUERY)) {
					forwardURL = URLDefineList.QRY_VENDOR_REPAIR;
				}
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {

				BaseSQLProducer sqlProducer = new AmsItemRepairModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				if (repairQuery.equals(DictConstant.ITEM_REPAIR_QUERY)) {
					forwardURL = URLDefineList.QRY_ITEM_REPAIR;
				} else if (repairQuery.equals(DictConstant.VENTOR_REPAIR_QUERY)) {
					forwardURL = URLDefineList.QRY_VENDOR_REPAIR;
				}
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
				if (repairQuery.equals(DictConstant.ITEM_REPAIR_QUERY)) {
					File file = amsItemRepairDAO.exportFile();
					amsItemRepairDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
					WebFileDownload fileDown = new WebFileDownload(req, res);
					fileDown.setFilePath(file.getAbsolutePath());
					fileDown.download();
					file.delete();
				} else if (repairQuery.equals(DictConstant.VENTOR_REPAIR_QUERY)) {
					File file = amsItemRepairDAO.vendorFile();
					amsItemRepairDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
					WebFileDownload fileDown = new WebFileDownload(req, res);
					fileDown.setFilePath(file.getAbsolutePath());
					fileDown.download();
					file.delete();
				}
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
		} catch (WebFileDownException e) {
			e.printStackTrace();
		} catch (DataTransException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}

}
