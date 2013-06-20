package com.sino.ams.system.part.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.part.dao.PartConfirmDAO;
import com.sino.ams.system.part.dto.PartConfirmDTO;
import com.sino.ams.system.part.model.PartConfirmModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.WebPageView;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-12-28
 * Time: 9:33:10
 * To change this template use File | Settings | File Templates.
 */
public class PartConfirmServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		String itemCode = StrUtil.nullToString(req.getParameter("itemCode"));
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(PartConfirmDTO.class.getName());
			PartConfirmDTO dto = (PartConfirmDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			PartConfirmDAO partConfirmDAO = new PartConfirmDAO(user, dto, conn);
			partConfirmDAO.setServletConfig(getServletConfig(req));
			OptionProducer prd = new OptionProducer(user, conn);
			if (action.equals("")) {
				String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dto.getItemCategory());
				req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.SPARE_CONFIRM;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dto.getItemCategory());
				req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
				BaseSQLProducer sqlProducer = new PartConfirmModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.SPARE_CONFIRM;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) { //计入到确认详细页面
				String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dto.getItemCategory());
				req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
				forwardURL = URLDefineList.SPARE_REJIGGER;
			} else if (action.equals(WebActionConstant.EDIT_ACTION)) { //设备分配确认的页面
				String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dto.getItemCategory());
				req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
				forwardURL = URLDefineList.SPARE_QUERY;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) { //设备分配确认的详细页面
				PartConfirmModel partConfirmModel = new PartConfirmModel(user, dto);
				SQLModel sqlModel = partConfirmModel.getSpareDistriModel();
				WebPageView wpv = new WebPageView(req, conn);
				wpv.produceWebData(sqlModel);
				forwardURL = URLDefineList.SPARE_DISTRI;
			} else if (action.equals(AMSActionConstant.DISTRIBUTE_ACTION)) { //设备分配确认的查询操作
				String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dto.getItemCategory());
				req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
				PartConfirmModel partConfirmModel = new PartConfirmModel(user, dto);
				SQLModel sqlModel = partConfirmModel.getSpareQueryModel();
				WebPageView wpv = new WebPageView(req, conn);
				wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
				wpv.produceWebData(sqlModel);
				forwardURL = URLDefineList.SPARE_QUERY;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) { //设备分配确认操作 4
				String[] Ids = req.getParameterValues("Ids");
				partConfirmDAO.distriData(Ids, itemCode);
				message = partConfirmDAO.getMessage();
				forwardURL = URLDefineList.SPARE_CONFIRM;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) { //删除
				boolean operateResult = partConfirmDAO.deleteData(itemCode);
				message = partConfirmDAO.getMessage();
				message.setIsError(!operateResult);
				forwardURL = URLDefineList.SPARE_CONFIRM;
			} else if (action.equals(AMSActionConstant.CONFIRM_ACTION)) { //确认操作 1
				String[] itemCodes = req.getParameterValues("itemCodes");
				partConfirmDAO.confirm(itemCodes);
				message = partConfirmDAO.getMessage();
				forwardURL = URLDefineList.CONFIRM_QUERY_SERVLET;
			} else if (action.equals(AMSActionConstant.INSTEAD_ACTION)) { //合并操作 2
				String oldCode = req.getParameter("itemCode1");
				String newCode = req.getParameter("itemCode");
				String newCategory = req.getParameter("itemCategory");
				String newName = req.getParameter("itemName");
				String newSpec = req.getParameter("itemSpec");
				partConfirmDAO.replaceItemCode(oldCode, newCode, newCategory, newName, newSpec);
				message = partConfirmDAO.getMessage();
				forwardURL = URLDefineList.CONFIRM_QUERY_SERVLET;
//			} else if (action.equals(AMSActionConstant.INSTEAD_ACTION)) { //合并操作3
//				String itemCode1 = req.getParameter("itemCode1");
//				String ret = partConfirmDAO.do_stand3(itemCode1);
//				forwardURL = URLDefineList.SPARE_CONFIRM + "?ret=" + ret;
			} else if (action.equals(WebActionConstant.CHECK_ACTION)) { //进入到统计页面
				forwardURL = URLDefineList.SPARE_STATISTIC;
			} else if (action.equals(AMSActionConstant.STATISTICS_ACTION)) { //错误率统计操作
				String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dto.getItemCategory());
				req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
				PartConfirmModel partConfirmModel = new PartConfirmModel(user, dto);
				SQLModel sqlModel = partConfirmModel.getFalseRateModel();
				WebPageView wpv = new WebPageView(req, conn);
				wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
				wpv.produceWebData(sqlModel);
				forwardURL = URLDefineList.SPARE_STATISTIC;
			} else if (action.equals(AMSActionConstant.QUERY_DETAIL)) { //进入到原因统计页面
				OptionProducer optProducer = new OptionProducer(user, conn);
				String cityOption = optProducer.getAllOrganization( SyBaseSQLUtil.NULL_INT_VALUE  , true);
				req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
				forwardURL = URLDefineList.REASON_STATISTIC;
			} else if (action.equals(AMSActionConstant.REASON_STATISTICS)) { //错误原因统计操作
				OptionProducer optProducer = new OptionProducer(user, conn);
				String cityOption = optProducer.getAllOrganization( SyBaseSQLUtil.NULL_INT_VALUE , true);
				req.setAttribute(WebAttrConstant.CITY_OPTION, cityOption);
				String itemCatOption = prd.getDictOption(DictConstant.ITEM_TYPE, dto.getItemCategory());
				PartConfirmModel partConfirmModel = new PartConfirmModel(user, dto);
				SQLModel sqlModel = partConfirmModel.getReasonRateModel();
				WebPageView wpv = new WebPageView(req, conn);
				wpv.setCalPattern(CalendarConstant.LINE_PATTERN);
				wpv.produceWebData(sqlModel);
				req.setAttribute(WebAttrConstant.EQUIPMENT_OPTION, itemCatOption);
				forwardURL = URLDefineList.REASON_STATISTIC;
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) { //导出错误统计
				File file = partConfirmDAO.exportFile1();
				partConfirmDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) { //导出错误原因统计
				File file = partConfirmDAO.exportFile2();
				partConfirmDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
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
		} catch (WebFileDownException e) {
			e.printStackTrace();
		} catch (DataTransException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
			//根据实际情况修改页面跳转代码。
		}
	}
}
