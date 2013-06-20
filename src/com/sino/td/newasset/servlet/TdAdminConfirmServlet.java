package com.sino.td.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.td.commom.TdURLDefineList;
import com.sino.td.newasset.dao.TdAdminConfirmDAO;
import com.sino.td.newasset.dto.TdAssetsTransLineDTO;
import com.sino.td.newasset.model.TdAdminConfirmModel;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class TdAdminConfirmServlet extends BaseServlet {
	/**
	 * 所有的Servlet都必须实现的方法。
	 *
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
			ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(TdAssetsTransLineDTO.class.getName());
			TdAssetsTransLineDTO dto = (TdAssetsTransLineDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			dto.setCalPattern(LINE_PATTERN);
			conn = getDBConnection(req);
			TdAdminConfirmDAO confirmDAO = new TdAdminConfirmDAO(user, dto, conn);
			if (action.equals("")) {
				dto.setCalPattern(LINE_PATTERN);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = TdURLDefineList.ADMIN_CONFIRM_PAGE_TD;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new TdAdminConfirmModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setWebCheckProp(initCheckBoxProp());
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				dto.setCalPattern(LINE_PATTERN);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = TdURLDefineList.ADMIN_CONFIRM_PAGE_TD;
			} else if (action.equals(AssetsActionConstant.CONFIRM_ACTION)) {
				DTOSet confirmAssets = getConfirmData(req);
				boolean operateResult = confirmDAO.confirmAssets(confirmAssets);
				message = confirmDAO.getMessage();
				message.setIsError(!operateResult);
				forwardURL = TdURLDefineList.ADMIN_CONFIRM_SERVLET_TD;
				forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
				String exportType = dto.getExportType();
				File file = null;
				if (exportType.equals(AssetsWebAttributes.EXPORT_SELECTED_ASSETS)) {
					RequestParser parser = new RequestParser();
					CheckBoxProp checkProp = new CheckBoxProp("subCheck");
					checkProp.setIgnoreOtherField(true);
					parser.setCheckBoxProp(checkProp);
					parser.transData(req);
					String[] barcodes = parser.getParameterValues("barcode");
					file = confirmDAO.exportCheckedAssets(barcodes);
				} else {
					file = confirmDAO.exportQueryAssets();
				}
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else {
				message = getMessage(AssetsMessageKeys.INVALID_REQ);
				message.setIsError(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			}
		} catch (PoolPassivateException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.POOL_PASSIVATE_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!forwardURL.equals("")) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}

	/**
	 * 功能：获取CheckBoxProp对象
	 * @return CheckBoxProp
	 * @throws StrException
	 */
	private static CheckBoxProp initCheckBoxProp() throws StrException {
		CheckBoxProp checkProp = new CheckBoxProp("subCheck");
		checkProp.addDbField("TRANS_ID");
		checkProp.addDbField("TRANS_NO");
		checkProp.addDbField("TRANSFER_TYPE");

		checkProp.addDbField("BARCODE");
		checkProp.addDbField("OLD_LOCATION");
		checkProp.addDbField("OLD_RESPONSIBILITY_USER");
		checkProp.addDbField("OLD_RESPONSIBILITY_DEPT");
		checkProp.addDbField("OLD_ADDRESS_ID");
		checkProp.addDbField("FROM_ORGANIZATION_ID");

		checkProp.addDbField("NEW_BARCODE");
		checkProp.addDbField("ASSIGNED_TO_LOCATION");
		checkProp.addDbField("RESPONSIBILITY_USER");
		checkProp.addDbField("RESPONSIBILITY_DEPT");
		checkProp.addDbField("ADDRESS_ID");
		checkProp.addDbField("TO_ORGANIZATION_ID");
		checkProp.setIgnoreOtherField(true);
		return checkProp;
	}


	private DTOSet getConfirmData(HttpServletRequest req) throws ServletException {
		DTOSet confirmDatas = new DTOSet();
		try {
			RequestParser parser = new RequestParser();
			CheckBoxProp checkProp = new CheckBoxProp("subCheck");
			checkProp.setIgnoreOtherField(true);
			parser.setCheckBoxProp(checkProp);
			parser.transData(req);
			String[] barcodes = parser.getParameterValues("barcode");
			String[] newBarcodes = parser.getParameterValues("newBarcode");
			String[] transIds = parser.getParameterValues("transId");
			String[] transNos = parser.getParameterValues("transNo");
			String[] transferTypes = parser.getParameterValues("transferType");

			String[] oldLocations = parser.getParameterValues("oldLocation");
			String[] oldResponsibilityUsers = parser.getParameterValues("oldResponsibilityUser");
			String[] oldResponsibilityDepts = parser.getParameterValues("oldResponsibilityDept");
			String[] oldAddressIds = parser.getParameterValues("oldAddressId");
			String[] fromOrganizationIds = parser.getParameterValues("fromOrganizationId");

			String[] assignedToLocations = parser.getParameterValues("assignedToLocation");
			String[] responsibilityUsers = parser.getParameterValues("responsibilityUser");
			String[] responsibilityDepts = parser.getParameterValues("responsibilityDept");
			String[] addressIds = parser.getParameterValues("addressId");
			String[] toOrganizationIds = parser.getParameterValues("toOrganizationId");
			for (int i = 0; i < barcodes.length; i++) {
				TdAssetsTransLineDTO item = new TdAssetsTransLineDTO();
				item.setTransId(transIds[i]);
				item.setTransNo(transNos[i]);
				item.setTransferType(transferTypes[i]);
				item.setLineStatus(AssetsDictConstant.ORDER_STS_CONFIRMD);

				item.setBarcode(barcodes[i]);
				item.setOldResponsibilityDept(oldResponsibilityDepts[i]);
				item.setOldResponsibilityUser(oldResponsibilityUsers[i]);
				item.setOldLocation(oldLocations[i]);
				item.setOldAddressId(oldAddressIds[i]);
				item.setFromOrganizationId(StrUtil.strToInt(fromOrganizationIds[i]));

				item.setNewBarcode(newBarcodes[i]);
				item.setResponsibilityDept(responsibilityDepts[i]);
				item.setResponsibilityUser(responsibilityUsers[i]);
				item.setAssignedToLocation(assignedToLocations[i]);
				item.setAddressId( addressIds[i] );
				item.setToOrganizationId(toOrganizationIds[i]);
				confirmDatas.addDTO(item);
			}
		} catch (UploadException ex) {
			ex.printLog();
			throw new ServletException(ex);
		} catch (StrException ex) {
			ex.printLog();
			throw new ServletException(ex);
		} catch (DTOException ex) {
			ex.printLog();
			throw new ServletException(ex);
		}
		return confirmDatas;
	}
}
