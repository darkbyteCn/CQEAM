package com.sino.ams.system.house.servlet;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.*;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.house.dao.AmsHouseInfoDAO;
import com.sino.ams.system.house.dto.AmsHouseInfoDTO;
import com.sino.ams.system.house.dto.AmsHouseUsesDTO;
import com.sino.ams.system.house.dto.AmsItemFilesDTO;
import com.sino.ams.system.house.model.AmsHouseInfoModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.util.GenBarcode;


/**
 * <p>Title: AmsHouseInfoServlet</p>
 * <p>Description:程序自动生成服务程序“AmsHouseInfoServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Zyun
 * @version 1.0
 */


public class AmsHouseInfoServlet extends BaseServlet {

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
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsHouseInfoDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsHouseInfoDTO.class.getName());
			dtoParameter = (AmsHouseInfoDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AmsHouseInfoDAO amsHouseInfoDAO = new AmsHouseInfoDAO(user, dtoParameter, conn);
			OptionProducer prd = new OptionProducer(user, conn);
			String status = prd.getDictOption(DictConstant.HOUSE_STATUS, dtoParameter.getHouseStatus());
			String use = prd.getDictOption(DictConstant.HOUSE_USAGE, dtoParameter.getHouseUsage());
			req.setAttribute(WebAttrConstant.HOUSE_STATUS_OPTION, status);
			req.setAttribute(WebAttrConstant.HOUSE_USAGE_OPTION, use);
			String isCertificate = prd.getBooleanOption(dtoParameter.getCertificate());
			req.setAttribute(WebAttrConstant.IS_CERTIFICATE_OPTION, isCertificate);
			if (action.equals("")) {
				String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
				req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
				forwardURL = URLDefineList.HOUSE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsHouseInfoModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);           //
				pageDAO.produceWebData();
				String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
				req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
				forwardURL = URLDefineList.HOUSE_QUERY_PAGE;
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				AmsHouseUsesDTO  dto=new AmsHouseUsesDTO();

				String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
				req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
				String areaOption = prd.getDictOption2(DictConstant.AREA_UNIT, dtoParameter.getAreaUnit());
				req.setAttribute(WebAttrConstant.AREA_UNIT_OPTION, areaOption);

				dtoParameter = new AmsHouseInfoDTO();

				dtoParameter.setCalPattern(CalendarConstant.LINE_PATTERN);       //
				dtoParameter.setRentDate(CalendarUtil.getCurrDate());
				req.setAttribute("AMSHOUSEUSESDTO",dto);
				req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
				forwardURL = URLDefineList.HOUSE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
//                    DTOSet  dto=amsHouseInfoDAO.getUses();

				amsHouseInfoDAO.setDTOClassName(AmsHouseInfoDTO.class.getName());
				AmsHouseInfoDTO amsHouseInfo = (AmsHouseInfoDTO) amsHouseInfoDAO.getDataByPrimaryKey();
				amsHouseInfo.setCalPattern(CalendarConstant.LINE_PATTERN);   //
				if (amsHouseInfo == null) {
					amsHouseInfo = new AmsHouseInfoDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}

				String files = prd.getAttachFile(amsHouseInfo.getBarcode());
				req.setAttribute(WebAttrConstant.ATTACH_FILES, files);

				String payOption = prd.getDictOption(DictConstant.PAY_TYPE, amsHouseInfo.getPayType());
				req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
				String areaOption = prd.getDictOption2(DictConstant.AREA_UNIT, amsHouseInfo.getAreaUnit());
				req.setAttribute(WebAttrConstant.AREA_UNIT_OPTION, areaOption);

				status = prd.getDictOption(DictConstant.HOUSE_STATUS, amsHouseInfo.getHouseStatus());
				use = prd.getDictOption(DictConstant.HOUSE_USAGE, amsHouseInfo.getHouseUsage());
				req.setAttribute(WebAttrConstant.HOUSE_STATUS_OPTION, status);
				req.setAttribute(WebAttrConstant.HOUSE_USAGE_OPTION, use);
				 req.setAttribute("AMSHOUSEUSESDTODETAIL",amsHouseInfoDAO.getUses());
				req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, amsHouseInfo);
				forwardURL = URLDefineList.HOUSE_DETAIL_PAGE;
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {   //do_save 操作
				String systemId = StrUtil.nullToString(req.getParameter("systemId"));
				req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
				EtsItemInfoDTO itemInfoDTO = (EtsItemInfoDTO) req2DTO.getDTO(req);
				String[] filePaths = req.getParameterValues("affix");

				 Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsHouseUsesDTO.class.getName());
				List ignoreList = new ArrayList();
				ignoreList.add("barcode");
				ignoreList.add("act");
				r2.setIgnoreFields(ignoreList);
				DTOSet lineSet = r2.getDTOSet(req);

				boolean operateResult = amsHouseInfoDAO.createData(itemInfoDTO, systemId, filePaths,lineSet);
				message = amsHouseInfoDAO.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
					req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
					forwardURL = URLDefineList.HOUSE_QUERY_PAGE;
				} else {
					String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
					req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
					String areaOption = prd.getDictOption2(DictConstant.AREA_UNIT, dtoParameter.getAreaUnit());
					req.setAttribute(WebAttrConstant.AREA_UNIT_OPTION, areaOption);
					req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
					forwardURL = URLDefineList.HOUSE_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {       //修改操作

				req2DTO.setDTOClassName(EtsItemInfoDTO.class.getName());
				EtsItemInfoDTO itemInfoDTO = (EtsItemInfoDTO) req2DTO.getDTO(req);

				AmsItemFilesDTO fileDTO = new AmsItemFilesDTO();
				String[] filePaths = req.getParameterValues("affix");

				 Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsHouseUsesDTO.class.getName());
				List ignoreList = new ArrayList();
				ignoreList.add("barcode");
				ignoreList.add("act");
				r2.setIgnoreFields(ignoreList);
				DTOSet lineSet = r2.getDTOSet(req);

				boolean operateResult = amsHouseInfoDAO.updateData(itemInfoDTO, fileDTO, filePaths,lineSet);
				message = amsHouseInfoDAO.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					String isRent = prd.getBooleanOption(dtoParameter.getIsRent());
					req.setAttribute(WebAttrConstant.IS_RENT_OPTION, isRent);
					forwardURL = URLDefineList.HOUSE_QUERY_PAGE;
				} else {
					String payOption = prd.getDictOption(DictConstant.PAY_TYPE, dtoParameter.getPayType());
					req.setAttribute(WebAttrConstant.PAY_TYPE_OPTION, payOption);
					String areaOption = prd.getDictOption2(DictConstant.AREA_UNIT, dtoParameter.getAreaUnit());
					req.setAttribute(WebAttrConstant.AREA_UNIT_OPTION, areaOption);
					req.setAttribute(WebAttrConstant.AMS_HOUSE_INFO_DTO, dtoParameter);
					forwardURL = URLDefineList.HOUSE_DETAIL_PAGE;
				}
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				amsHouseInfoDAO.deleteData();
				message = amsHouseInfoDAO.getMessage();
				forwardURL = URLDefineList.HOUSE_SERVLET;
			} else if (action.equals(AMSActionConstant.INURE_ACTION)) {   //   批量生效
				String systemIds[] = req.getParameterValues("systemIds");
				amsHouseInfoDAO.efficientData(systemIds);
				forwardURL = URLDefineList.ITEM_FIXING_QUERY;
				message = amsHouseInfoDAO.getMessage();
			} else if (action.equals(AMSActionConstant.DISABLED_ACTION)) {   //批量失效
				String systemIds[] = req.getParameterValues("systemIds");
				amsHouseInfoDAO.disabledData(systemIds);
				message = amsHouseInfoDAO.getMessage();
				forwardURL = URLDefineList.ITEM_FIXING_QUERY;
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) {      //导出
				File file = amsHouseInfoDAO.exportFile();
				amsHouseInfoDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(AMSActionConstant.BUILD_NO_ACTION)) {      //创建单barcode单句号

			} else if (action.equals("verifyBarcode")) {                                          //验证barcode是否存在
				String barcode = StrUtil.nullToString(req.getParameter("barcode"));
				boolean success = amsHouseInfoDAO.verifyBarcode(barcode);
				PrintWriter out = res.getWriter();
				if (success) {
					out.print("Y");
				}
				out.flush();
				out.close();
			} else if (action.equals("creatBarcode")) {                                          //创建单barcode单句号
				String barcode = GenBarcode.getAssetBarcode(conn,user.getCompanyCode());
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				out.print(barcode);
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
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (CalendarException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLModelException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (!forwardURL.equals("")) {
				forwarder.forwardView(forwardURL);
			}
			//根据实际情况修改页面跳转代码。
		}
	}
}
