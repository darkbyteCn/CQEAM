package com.sino.ams.system.dict.servlet;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.dict.dao.EtsFlexAnalyseValuesDAO;
import com.sino.ams.system.dict.dto.EtsFlexValuesDTO;
import com.sino.ams.system.dict.model.EtsFlexAnalyseValuesModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.WebRadio;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EtsFlexAnalyseValuesServlet</p>
 * <p>Description:程序自动生成服务程序“EtsFlexAnalyseValuesServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author kouzh
 * @version 1.0
 */


public class EtsFlexAnalyseValuesServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if(req.getParameter("flexValueSetName") != null){
			String opartor = System.getProperty("file.separator");
			String flexValueSetName = "";
			if(opartor.equals("\\")){
			    flexValueSetName = new String(req.getParameter("flexValueSetName").getBytes("ISO-8859-1"), "GBK");
			}else{
				flexValueSetName = req.getParameter("flexValueSetName");
			}
			req.setAttribute("flexValueSetName", flexValueSetName);
		}
		
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			EtsFlexValuesDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsFlexValuesDTO.class.getName());
			dtoParameter = (EtsFlexValuesDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EtsFlexAnalyseValuesDAO flexAnalyseValuesDAO = new EtsFlexAnalyseValuesDAO(user, dtoParameter, conn);
			req.setAttribute("remark", dtoParameter.getRemark());
			OptionProducer optProducer = new OptionProducer(user, conn);
			if (action.equals("")) {
				forwardURL = URLDefineList.DICT_ANALYSE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				if(dtoParameter.getIsInner().equals(""))dtoParameter.setIsInner("Y");
				if(dtoParameter.getEnabled().equals(""))dtoParameter.setEnabled("Y");
				BaseSQLProducer sqlProducer = new EtsFlexAnalyseValuesModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				WebRadio webRadio = new WebRadio("isInner");
				webRadio.addValueCaption("Y", "是");
				webRadio.addValueCaption("N", "否");
				
				webRadio.setCheckedValue(dtoParameter.getIsInner());
				req.setAttribute(WebAttrConstant.IS_INNER_RADIO, webRadio.toString());
				webRadio.setRadioName("enabled");
				
				webRadio.setCheckedValue(dtoParameter.getEnabled());
				req.setAttribute(WebAttrConstant.ENABLED_RADIO, webRadio.toString());
				forwardURL = URLDefineList.DICT_ANALYSE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				EtsFlexValuesDTO flexValues = (EtsFlexValuesDTO)req.getAttribute(WebAttrConstant.DICT_DATA);
				if(flexValues == null){
					flexValues= dtoParameter;
				}
				req.setAttribute(WebAttrConstant.DICT_DATA, flexValues);

				dtoParameter.setEnabled(WebAttrConstant.TRUE_VALUE);
				WebRadio webRadio = new WebRadio("enabled");
				webRadio.addValueCaption("Y", "是");
				webRadio.addValueCaption("N", "否");
				webRadio.setCheckedValue(dtoParameter.getEnabled());
				req.setAttribute(WebAttrConstant.ENABLED_RADIO, webRadio.toString());
				webRadio.setRadioName("isInner");
				webRadio.setCheckedValue(dtoParameter.getIsInner());
				req.setAttribute(WebAttrConstant.IS_INNER_RADIO, webRadio.toString());
				String dictParent = optProducer.getDictParentOption(StrUtil.nullToString(dtoParameter.getFlexValueSetId()));
				req.setAttribute(WebAttrConstant.DICT_PARENT_OPT, dictParent);
				forwardURL = URLDefineList.DICT_ANALYSE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				flexAnalyseValuesDAO.setDTOClassName(EtsFlexValuesDTO.class.getName());
				EtsFlexValuesDTO flexValues = (EtsFlexValuesDTO)flexAnalyseValuesDAO.getDataByPrimaryKey();
				if(flexValues == null){
					flexValues = new EtsFlexValuesDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				} else {
					req.setAttribute(WebAttrConstant.DICT_DATA, flexValues);
					//资产分析的字典可修改
//					if(flexValues.getIsInner().equals(WebAttrConstant.TRUE_VALUE)){
//						message = getMessage(CustMessageKey.INNER_DICT);
//						message.addParameterValue(flexValues.getFlexValueSetName());
//						message.setNeedBack(true);
//					}
					WebRadio webRadio = new WebRadio("enabled");
					webRadio.addValueCaption("Y", "是");
					webRadio.addValueCaption("N", "否");
					webRadio.setCheckedValue(flexValues.getEnabled());
					req.setAttribute(WebAttrConstant.ENABLED_RADIO, webRadio.toString());
					webRadio.setRadioName("isInner");
					webRadio.setCheckedValue(flexValues.getIsInner());
					req.setAttribute(WebAttrConstant.IS_INNER_RADIO, webRadio.toString());
//					String dictParent = optProducer.getDictParentOption(flexValues.getFlexValueSetId());
					String dictParent = optProducer.getAnalyseDictParentOption(StrUtil.nullToString(flexValues.getFlexValueSetId()));
					req.setAttribute(WebAttrConstant.DICT_PARENT_OPT, dictParent);
					forwardURL = URLDefineList.DICT_ANALYSE_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				ServletConfigDTO servletConfig = getServletConfig(req);
				flexAnalyseValuesDAO.setServletConfig(servletConfig);
				flexAnalyseValuesDAO.createData();
				message = flexAnalyseValuesDAO.getMessage();
				forwardURL = URLDefineList.DICT_ANALYSE_QUERYRY_SERVLET;

			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				ServletConfigDTO servletConfig = getServletConfig(req);
				flexAnalyseValuesDAO.setServletConfig(servletConfig);
				flexAnalyseValuesDAO.updateData();
				message = flexAnalyseValuesDAO.getMessage();
				forwardURL = URLDefineList.DICT_ANALYSE_QUERYRY_SERVLET;
			}  else if (action.equals(WebActionConstant.EXPORT_ACTION)) {
				File file = flexAnalyseValuesDAO.exportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			}else {
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
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
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
