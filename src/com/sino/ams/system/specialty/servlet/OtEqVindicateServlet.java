package com.sino.ams.system.specialty.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.specialty.dao.OtEqVindicateDAO;
import com.sino.ams.system.specialty.dto.OtherDTO;
import com.sino.ams.system.specialty.model.OtEqVindicateModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.util.GenBarcode;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-12-20
 * Time: 11:48:45
 * To change this template use File | Settings | File Templates.
 */
public class OtEqVindicateServlet extends BaseServlet {
	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		String itemCategory = StrUtil.nullToString(req.getParameter("itemCategory"));
		String itemCategor = StrUtil.nullToString(req.getParameter("type"));
		Connection conn = null;
		String showMsg = "";
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			OtherDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(OtherDTO.class.getName());
			dtoParameter = (OtherDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			OtEqVindicateDAO equipDAO = new OtEqVindicateDAO(user, dtoParameter, conn);
			OptionProducer op = new OptionProducer(user, conn);
			int company = StrUtil.strToInt(dtoParameter.getCompany());
			String companySelect = op.getAllOrganization(company, true);
			req.setAttribute(WebAttrConstant.OU_OPTION, companySelect);

			ServletConfigDTO servletConfig = getServletConfig(req);

			dtoParameter.setItemCategory(itemCategory);
			dtoParameter.setItemCategoryDesc(equipDAO.simpleQueryData(itemCategory));
			//dtoParameter.setCompanyCode(etsItemInfoDAO.getCode());
			equipDAO.setServletConfig(servletConfig);

			req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, dtoParameter);

			if (action.equals("")) {
				req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, dtoParameter);
				forwardURL = URLDefineList.OTHER_EQUIPMENTS_QUERY;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new OtEqVindicateModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.OTHER_EQUIPMENTS_QUERY;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				dtoParameter.setStartDate(CalendarUtil.getCurrDate());
				req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, dtoParameter);
				OtherDTO etsItemInfo = (OtherDTO) req.getAttribute(WebAttrConstant.ETS_ITEM_DTO);
				if (etsItemInfo == null) {
					etsItemInfo = dtoParameter;
				}
				if (!servletConfig.getProvinceCode().equals("40")) {
					etsItemInfo.setBarcode1(GenBarcode.getAssetBarcode(conn,user.getCompanyCode()));
				}
                req.setAttribute(WebAttrConstant.IS_SHARE_OPTION, getIsSHAREOption(etsItemInfo.getShare()));
                req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, etsItemInfo);
				forwardURL = URLDefineList.OTHER_EQUIPMENTS_INFO;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
//                req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, dtoParameter);
				equipDAO.setDTOClassName(OtherDTO.class.getName());
				OtherDTO etsItemInfo = (OtherDTO) equipDAO.getDataByPrimaryKey();
				etsItemInfo.setItemCategoryDesc("其它设备");
				if (etsItemInfo == null) {
					etsItemInfo = new OtherDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
//                etsItemInfo.setItemCategory(itemCategory);
//                etsItemInfo.setItemCategoryDesc(etsItemInfoDAO.simpleQueryData(itemCategory));
                req.setAttribute(WebAttrConstant.IS_SHARE_OPTION, getIsSHAREOption(etsItemInfo.getShare()));
                req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, etsItemInfo);
				forwardURL = URLDefineList.OTHER_EQUIPMENTS_INFO;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {   //保存
				equipDAO.insertData();
				message = equipDAO.getMessage();
				showMsg = "设备信息保存成功";
				forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;

			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				equipDAO.updateData();
				message = equipDAO.getMessage();
				forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
			} else if (action.equals(AMSActionConstant.INURE_ACTION)) {
				String systemIds[] = req.getParameterValues("systemid");
				for (int i = 0; i < systemIds.length; i++) {
					equipDAO.enableItem(systemIds[i]);
				}

//				forwardURL = URLDefineList.ITEM_FIXING_QUERY;
                forwardURL = URLDefineList.OTHER_EQUIPMENTS_QUERY;
                message = equipDAO.getMessage();
			} else if (action.equals(AMSActionConstant.DISABLED_ACTION)) {
				String systemIds[] = req.getParameterValues("systemid");
				for (int i = 0; i < systemIds.length; i++) {
					equipDAO.disableItem(systemIds[i]);
				}
				message = equipDAO.getMessage();
				forwardURL = URLDefineList.OTHER_EQUIPMENTS_QUERY;
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
				File file = equipDAO.exportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals("verifyBarcode")) {                                          //验证barcode是否存在
				String barcode = StrUtil.nullToString(req.getParameter("barcode"));
				boolean success = equipDAO.verifyBarcode(barcode);
				PrintWriter out = res.getWriter();
				if (success) {
					out.print("Y");
				}
				out.flush();
				out.close();
			} else {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
		} catch (CalendarException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLException e) {
			e.printStackTrace();
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!StrUtil.isEmpty(forwardURL)) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				if (showMsg.equals("")) {
					forwarder.forwardView(forwardURL);
				} else {
					forwarder.forwardOpenerView(forwardURL, showMsg);
				}
			}
		}
	}

     /**
	 * 功能：构造是否共享下拉框
	 * @param selectedValue String
	 * @return String
	 */
	private String getIsSHAREOption(String selectedValue) {
		StringBuffer strOpt = new StringBuffer();
		if (selectedValue == null) {
			selectedValue = "";
		}
		strOpt.append("<option value=\"");
		strOpt.append(WebAttrConstant.TRUE_VALUE);
		strOpt.append("\"");
		if (selectedValue.equals(WebAttrConstant.TRUE_VALUE)) {
			strOpt.append(" selected");
		}
		strOpt.append(">是</option>");
		strOpt.append("<option value=\"");
		strOpt.append(WebAttrConstant.FALSE_VALUE);
		strOpt.append("\"");
		if (selectedValue.equals(WebAttrConstant.FALSE_VALUE)) {
			strOpt.append(" selected");
		}
		strOpt.append(">否</option>");
		return strOpt.toString();
	}
}
