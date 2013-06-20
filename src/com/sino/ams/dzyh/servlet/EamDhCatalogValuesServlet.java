package com.sino.ams.dzyh.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.dzyh.OptionProducer.EamProducer;
import com.sino.ams.dzyh.dao.EamDhCatalogValuesDAO;
import com.sino.ams.dzyh.dto.EamDhCatalogValuesDTO;
import com.sino.ams.dzyh.model.EamDhCatalogValuesModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.WebRadio;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: EamDhCatalogValuesServlet</p>
 * <p>Description:程序自动生成服务程序“EamDhCatalogValuesServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 张星
 * @version 1.0
 */


public class EamDhCatalogValuesServlet extends BaseServlet {

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
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			EamDhCatalogValuesDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EamDhCatalogValuesDTO.class.getName());
			dtoParameter = (EamDhCatalogValuesDTO)req2DTO.getDTO(req);
			conn = getDBConnection(req);
			EamDhCatalogValuesDAO eamDhCatalogValuesDAO = new EamDhCatalogValuesDAO(user, dtoParameter, conn);
			EamProducer eamProducer = new EamProducer(user, conn);
			OptionProducer optProducer = new OptionProducer(user, conn);
			String barcodeFlagOpt=optProducer.getDictOption(WebAttrConstant.DZYH_BARCODE_FLAG, StrUtil.nullToString(dtoParameter.getBarcodeFlag()));
			String commonFlagOpt=optProducer.getDictOption(WebAttrConstant.DZYH_COMMON_FLAG, StrUtil.nullToString(dtoParameter.getCommonFlag()));
			req.setAttribute(WebAttrConstant.DZYH_BARCODE_OPT, barcodeFlagOpt);
			req.setAttribute(WebAttrConstant.DZYH_COMMON_OPT, commonFlagOpt);
			if (action.equals("")) {
				forwardURL = URLDefineList.DZYHCATALOG_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EamDhCatalogValuesModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				WebRadio webRadio = new WebRadio("enabled");
				webRadio.addValueCaption("1", "是");
				webRadio.addValueCaption("0", "否");
				webRadio.setCheckedValue(String.valueOf(dtoParameter.getEnabled()));
				req.setAttribute("ENABLED_RADIO", webRadio.toString());
				forwardURL = URLDefineList.DZYHCATALOG_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				WebRadio webRadio = new WebRadio("enabled");
				webRadio.addValueCaption("1", "是");
				webRadio.addValueCaption("0", "否");
				webRadio.setCheckedValue(String.valueOf(dtoParameter.getEnabled()));
				req.setAttribute("ENABLED_RADIO", webRadio.toString());
				
				EamDhCatalogValuesDTO eamDhCatalogValues = (EamDhCatalogValuesDTO)req.getAttribute(WebAttrConstant.DZYH_DATA);
				if(eamDhCatalogValues == null){
					eamDhCatalogValues= dtoParameter;//表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.dzyh.dto.EamDhCatalogValuesDTO的构造函数确定
				}
				req.setAttribute(WebAttrConstant.DZYH_DATA, eamDhCatalogValues);
				
				String dzyhSet=eamProducer.getDzyhParentOption(dtoParameter.getCatalogSetId());
				String dzyhUnit=eamProducer.getDictOption(WebAttrConstant.UNIT_OF_MEASURE, StrUtil.nullToString(dtoParameter.getUnit()));

				req.setAttribute(WebAttrConstant.DZYH_UNIT_OF_MEASURE_OPT, dzyhUnit);
				req.setAttribute(WebAttrConstant.DZYH_PARENT_OPT, dzyhSet);
				
				forwardURL = URLDefineList.DZYHCATALOG_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				eamDhCatalogValuesDAO.setDTOClassName(EamDhCatalogValuesDTO.class.getName());
				EamDhCatalogValuesDTO eamDhCatalogValues = (EamDhCatalogValuesDTO)eamDhCatalogValuesDAO.getDataByPrimaryKey();
				if(eamDhCatalogValues == null){
					eamDhCatalogValues = new EamDhCatalogValuesDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}else{
					req.setAttribute(WebAttrConstant.DZYH_DATA, eamDhCatalogValues);
					
					WebRadio webRadio = new WebRadio("enabled");
					webRadio.addValueCaption("1", "是");
					webRadio.addValueCaption("0", "否");
					webRadio.setCheckedValue(StrUtil.nullToString(dtoParameter.getEnabled()));
					req.setAttribute("ENABLED_RADIO", webRadio.toString());

					String dzyhSet=eamProducer.getDetailDzyhParentOption(StrUtil.nullToString(dtoParameter.getCatalogSetId()));
					String dzyhUnit=eamProducer.getDeDictOption(WebAttrConstant.UNIT_OF_MEASURE, StrUtil.nullToString(dtoParameter.getUnit()));
					String DetailBarcodeFlag=eamProducer.getDetailDictOption(WebAttrConstant.DZYH_BARCODE_FLAG, StrUtil.nullToString(dtoParameter.getBarcodeFlag()));
					String DetailCommonFlag=eamProducer.getDetailDictOption(WebAttrConstant.DZYH_COMMON_FLAG, StrUtil.nullToString(dtoParameter.getCommonFlag()));
					
					req.setAttribute(WebAttrConstant.DZYH_BARCODE_OPT, DetailBarcodeFlag);
					req.setAttribute(WebAttrConstant.DZYH_COMMON_OPT, DetailCommonFlag);
					req.setAttribute(WebAttrConstant.DZYH_UNIT_OF_MEASURE_OPT, dzyhUnit);
					req.setAttribute(WebAttrConstant.DZYH_PARENT_OPT, dzyhSet);
					forwardURL = URLDefineList.DZYHCATALOG_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {
				ServletConfigDTO servletConfig = getServletConfig(req);
				eamDhCatalogValuesDAO.setServletConfig(servletConfig);
				eamDhCatalogValuesDAO.createData();
				message = eamDhCatalogValuesDAO.getMessage();
				forwardURL = URLDefineList.DZYHCATALOG_QUERYRY_SERVLET;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				ServletConfigDTO servletConfig = getServletConfig(req);
				eamDhCatalogValuesDAO.setServletConfig(servletConfig);
				eamDhCatalogValuesDAO.updateData();
				message=eamDhCatalogValuesDAO.getMessage();
				forwardURL = URLDefineList.DZYHCATALOG_QUERYRY_SERVLET;
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
		} catch (DataHandleException ex) {
			ex.printLog();
			//请根据实际情况处理消息
		} catch (StrException e) {
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