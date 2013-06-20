package com.sino.ams.system.fixing.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.fixing.dao.EtsItemInfoDAO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.fixing.model.EtsItemInfoModel;
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
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: EtsItemInfoServlet</p>
 * <p>Description:程序自动生成服务程序“EtsItemInfoServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author yuyao
 * @version 1.0
 */


public class EtsItemInfoServlet extends BaseServlet {

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
		String showMsg="";
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			EtsItemInfoDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
			dtoParameter = (EtsItemInfoDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EtsItemInfoDAO etsItemInfoDAO = new EtsItemInfoDAO(user, dtoParameter, conn);
			OptionProducer op = new OptionProducer(user, conn);
			int company = StrUtil.strToInt(dtoParameter.getCompany());
			String companySelect = op.getAllOrganization(company, true);
			req.setAttribute(WebAttrConstant.OU_OPTION, companySelect);

			dtoParameter.setItemCategory(itemCategory);
			dtoParameter.setItemCategoryDesc(etsItemInfoDAO.simpleQueryData(itemCategory));
			//dtoParameter.setCompanyCode(etsItemInfoDAO.getCode());
			req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, dtoParameter);
			if (action.equals("")) {
				req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, dtoParameter);
				forwardURL = URLDefineList.ITEM_FIXING_QUERY;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EtsItemInfoModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				forwardURL = URLDefineList.ITEM_FIXING_QUERY;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				dtoParameter.setStartDate(CalendarUtil.getCurrDate());
				req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, dtoParameter);
				EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) req.getAttribute(WebAttrConstant.ETS_ITEM_DTO);
				if (etsItemInfo == null) {
					etsItemInfo = dtoParameter;
				}
				etsItemInfo.setBarcode1(GenBarcode.getAssetBarcode(conn,user.getCompanyCode()));
				req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, etsItemInfo);
				forwardURL = URLDefineList.ITEM_FIXING_DETAIL;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
//                req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, dtoParameter);
				etsItemInfoDAO.setDTOClassName(EtsItemInfoDTO.class.getName());
				EtsItemInfoDTO etsItemInfo = (EtsItemInfoDTO) etsItemInfoDAO.getDataByPrimaryKey();
				if (etsItemInfo == null) {
					etsItemInfo = new EtsItemInfoDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				etsItemInfo.setItemCategory(itemCategory);
				etsItemInfo.setItemCategoryDesc(etsItemInfoDAO.simpleQueryData(itemCategory));
				req.setAttribute(WebAttrConstant.ETS_ITEM_DTO, etsItemInfo);
				forwardURL = URLDefineList.ITEM_FIXING_DETAIL;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				etsItemInfoDAO.insertData();
				message = etsItemInfoDAO.getMessage();
				 showMsg="设备信息保存成功";
				forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;

			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				etsItemInfoDAO.updateData();
				message = etsItemInfoDAO.getMessage();
				forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
			} else if (action.equals(AMSActionConstant.INURE_ACTION)) {
				String systemIds[] = req.getParameterValues("systemid");
				for(int i=0;i<systemIds.length;i++){
				etsItemInfoDAO.enableItem(systemIds[i]);
				}

				forwardURL = URLDefineList.ITEM_FIXING_QUERY;
				message = etsItemInfoDAO.getMessage();
			} else if (action.equals(AMSActionConstant.DISABLED_ACTION)) {
				String systemIds[] = req.getParameterValues("systemid");
				 for(int i=0;i<systemIds.length;i++){
				etsItemInfoDAO.disableItem(systemIds[i]);
				 }
				message = etsItemInfoDAO.getMessage();
				forwardURL = URLDefineList.ITEM_FIXING_QUERY;
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
				File file = etsItemInfoDAO.exportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
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
		}catch (SQLException e) {
			e.printStackTrace();
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!StrUtil.isEmpty(forwardURL)) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				if(showMsg.equals("")){
				forwarder.forwardView(forwardURL);       }
				else{
					 forwarder.forwardOpenerView(forwardURL, showMsg);
				}
			}
		}
	}
}
