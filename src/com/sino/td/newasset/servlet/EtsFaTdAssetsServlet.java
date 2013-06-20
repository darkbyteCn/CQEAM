package com.sino.td.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.bean.FlexValueUtil;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsFaDictDAO;
import com.sino.ams.newasset.dao.AmsMisDeptDAO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsFaDictDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
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
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.td.commom.TdURLDefineList;
import com.sino.td.newasset.dao.EtsFaTdAssetsDAO;
import com.sino.td.newasset.dto.TdAssetsTransHeaderDTO;
import com.sino.td.newasset.dto.TdAssetsTransLineDTO;
import com.sino.td.newasset.model.EtsFaTdAssetsModel;

/**
 * <p>Title: EtsFaAssetsServlet</p>
 * <p>Description:程序自动生成服务程序“EtsFaAssetsServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class EtsFaTdAssetsServlet extends BaseServlet {

	/**
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
			req2DTO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
			AmsAssetsAddressVDTO dto = (AmsAssetsAddressVDTO) req2DTO.getDTO(req);
			String treeCategory = dto.getTreeCategory();
			String action = dto.getAct();
			conn = getDBConnection(req);
			EtsFaTdAssetsDAO assetsDAO = new EtsFaTdAssetsDAO(user, dto, conn);
			assetsDAO.setServletConfig(getServletConfig(req));
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			String itemStatusOption = optProducer.getAssetsStatusOption(dto.getItemStatus());
			dto.setItemStatusOption(itemStatusOption);
			if (action.equals("")) {
				String option = optProducer.getTransferOption("");
				dto.setTransferTypeOption(option);
				option = optProducer.getFAContentOption(dto.getFaContentCode());
				dto.setFaContentOption(option);
				dto.setCalPattern(LINE_PATTERN);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.ASSETS_QRY_PAGE;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				String option = optProducer.getTransferOption("");
				dto.setTransferTypeOption(option);
				option = optProducer.getFAContentOption(dto.getFaContentCode());
				dto.setFaContentOption(option);
				BaseSQLProducer sqlProducer = new EtsFaTdAssetsModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setWebCheckProp(initCheckBoxProp(treeCategory));
				pageDAO.setPageSize(21);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.setCountPages(getCountPages(dto));
				pageDAO.produceWebData();
				dto.setCalPattern(LINE_PATTERN);
				option = optProducer.getTransferFaOption("",AssetsDictConstant.TRANS_INN_DEPT);
				dto.setInnDeptOpt(option);
				option = optProducer.getTransferFaOption("",AssetsDictConstant.TRANS_BTW_DEPT);
				dto.setBtwDeptOpt(option);
				option = optProducer.getTransferFaOption("",AssetsDictConstant.TRANS_BTW_COMP);
				dto.setBtwCompOpt(option);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = AssetsURLList.ASSETS_QRY_PAGE;
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
				assetsDAO.setDTOClassName(AmsAssetsAddressVDTO.class.getName());
				assetsDAO.setCalPattern(LINE_PATTERN);
				dto = (AmsAssetsAddressVDTO) assetsDAO.getDataByPrimaryKey();
				if (dto == null) {
					dto = new AmsAssetsAddressVDTO();
					message = getMessage(AssetsMessageKeys.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute(AssetsWebAttributes.ASSETS_DATA, dto);
				forwardURL = TdURLDefineList.ASSETS_DTL_PAGE_TD;
			} else if (action.equals(AssetsActionConstant.TRANSFER_ACTION)) {
				dto.setServletConfig(getServletConfig(req));
				TdAssetsTransHeaderDTO headerDTO = getAssetsOrder(user, dto, AssetsDictConstant.ASS_RED, conn);
				DTOSet orderLines = getProcessItems(req, action, user, conn);
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
				req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, orderLines);
				forwardURL = TdURLDefineList.ASSETS_TRANS_SERVLET_TD + "?act=" + AssetsActionConstant.NEW_ACTION;
			} else if (action.equals(AssetsActionConstant.DISCARD_ACTION)) {
				TdAssetsTransHeaderDTO headerDTO = getAssetsOrder(user, dto, AssetsDictConstant.ASS_DIS, conn);
				DTOSet orderLines = getProcessItems(req, action, user, conn);
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
				req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, orderLines);
				forwardURL = TdURLDefineList.ASSETS_TRANS_SERVLET_TD + "?act=" + AssetsActionConstant.NEW_ACTION;
			} else if (action.equals(AssetsActionConstant.CLEAR_ACTION)) {
				TdAssetsTransHeaderDTO headerDTO = getAssetsOrder(user, dto, AssetsDictConstant.ASS_CLR, conn);
				DTOSet orderLines = getProcessItems(req, action, user, conn);
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
				req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, orderLines);
				forwardURL = TdURLDefineList.ASSETS_TRANS_SERVLET_TD + "?act=" + AssetsActionConstant.NEW_ACTION;
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
					file = assetsDAO.exportCheckedAssets(barcodes);
				} else {
					file = assetsDAO.exportQueryAssets();
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
		} catch (QueryException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
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
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (CalendarException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!StrUtil.isEmpty(forwardURL)) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}

	/**
	 * 功能：根据展示的资产树设置是否计算总记录数
	 * @param dto AmsAssetsAddressVDTO
	 * @return boolean
	 */
	private boolean getCountPages(AmsAssetsAddressVDTO dto) {
		boolean countPages = true;
		String treeCategory = dto.getTreeCategory();
		if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_COMPAN)) {
			countPages = false;
		} else if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_PROVIN)) {
			countPages = false;
		}
		return countPages;
	}

	/**
	 * 功能：构造资产调拨等单据数据
	 * @param user SfUserDTO
	 * @param dto AmsAssetsAddressVDTO
	 * @param transType String
	 * @param conn Connection
	 * @return TdAssetsTransHeaderDTO
	 * @throws CalendarException
	 * @throws QueryException
	 */
	private TdAssetsTransHeaderDTO getAssetsOrder(SfUserDTO user,
			AmsAssetsAddressVDTO dto, String transType,
			Connection conn) throws CalendarException, QueryException {
		TdAssetsTransHeaderDTO order = new TdAssetsTransHeaderDTO();
		order.setTransNo(AssetsWebAttributes.ORDER_NO_AUTO_PRODUCE);
		order.setFromOrganizationId(StrUtil.nullToString(user.getOrganizationId()));
		order.setTransType(transType);
		FlexValueUtil flexUtil = new FlexValueUtil(user, conn);
		order.setTransTypeValue(flexUtil.getFlexValue(AssetsDictConstant.
				ORDER_TYPE_ASSETS, order.getTransType()));

		order.setCreatedBy(user.getUserId());
		order.setCreated(user.getUsername());

		order.setCurrCreationDate();
		order.setFromDept(dto.getDeptCode());
		order.setTransferType(dto.getTransferType());
		order.setTransStatus(AssetsDictConstant.SAVE_TEMP);

		AmsMisDeptDTO dept = new AmsMisDeptDTO();
		dept.setDeptName(dto.getDeptName());
		AmsMisDeptDAO deptDAO = new AmsMisDeptDAO(user, dept, conn);
		dept = deptDAO.getDeptByName();
		order.setFromDeptName(dept.getDeptName());
		order.setFromDept(dept.getDeptCode());

		AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
		String deptCode = order.getFromDept();
		if (deptCode.equals("")) {
			deptCode = user.getDeptCode();
		}
		String deptOptions = optProducer.getUserAsssetsDeptOption(deptCode);
		order.setFromDeptOption(deptOptions);

		if (transType.equals(AssetsDictConstant.ASS_RED)) {
			if (dto.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP)) {
				String opt = optProducer.getAllOrganization(StrUtil.strToInt(order.
						getToOrganizationId()));
				order.setToCompanyOption(opt);
				ServletConfigDTO config = dto.getServletConfig();
				String provinceCode = config.getProvinceCode();
				if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_NM)) {
					int fromOrgId = dto.getFromOrganizationId();
					if (fromOrgId ==0) {
						fromOrgId = user.getOrganizationId();
					}
					opt = optProducer.getAllOrganization(fromOrgId);
					order.setFromCompanyOption(opt);
					opt = optProducer.getBookTypeOption2(StrUtil.nullToString(fromOrgId));
					order.setBookTypeOption(opt);
				}
			} else {
				if (order.getTransId().equals("")) {
					order.setToOrganizationId(StrUtil.nullToString(user.getOrganizationId()));
				}
			}
		}

		order.setFromCompanyName(user.getCompany());
		order.setBookTypeName(user.getBookTypeCode() + "--" +
							  user.getBookTypeName());
		order.setEmail(user.getEmail());
		order.setPhoneNumber(user.getMobilePhone());
		order.setUserDeptName(user.getDeptName());
		order.setFaContentCode(dto.getFaContentCode());
		order.setFaContentName(dto.getFaContentName());
		DTOSet assetsGroups = user.getUserGroups();
		if (assetsGroups.getSize() == 1) {
			SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
			order.setFromGroup(sfGRoup.getGroupId());
			order.setFromGroupName(sfGRoup.getGroupname());
		}

		String faContentOption = "";
		if (transType.equals(AssetsDictConstant.ASS_RED)) {
			faContentOption = optProducer.getTransferFaOption(dto.
					getFaContentCode(), dto.getTransferType());
		} else {
			faContentOption = optProducer.getFAContentOption(dto.
					getFaContentCode());
		}
		order.setFaContentOption(faContentOption);
		return order;
	}

	/**
	 * 功能：获取操作中选取的数据
	 * @param req HttpServletRequest
	 * @param action String 本次执行的操作
	 * @param user SfUserDTO
	 * @param conn Connection
	 * @return DTOSet
	 * @throws DTOException
	 * @throws QueryException
	 */
	private DTOSet getProcessItems(HttpServletRequest req, String action,
								   SfUserDTO user, Connection conn) throws
			DTOException, QueryException {
		DTOSet orderLines = new DTOSet();
		try {
			RequestParser parser = new RequestParser();
			CheckBoxProp checkProp = new CheckBoxProp("subCheck");
			checkProp.setIgnoreOtherField(true);
			parser.setCheckBoxProp(checkProp);
			parser.transData(req);
			String faContentCode = parser.getParameter("faContentCode"); //根据该数据对选中的barcode进行过滤
			AmsFaDictDTO faDict = new AmsFaDictDTO();
			faDict.setFaContentCode(faContentCode);
			AmsFaDictDAO dictDAO = new AmsFaDictDAO(user, faDict, conn);
			dictDAO.setDTOClassName(AmsFaDictDTO.class.getName());
			DTOSet dtos = (DTOSet) dictDAO.getMuxData();
			String transferType = parser.getParameter("transferType");
			String[] barcodes = parser.getParameterValues("barcode");
			String[] assetNumbers = parser.getParameterValues("assetNumber");
			String[] assetsDescriptions = parser.getParameterValues(
					"assetsDescription");
			String[] modelNumbers = parser.getParameterValues("modelNumber");
			String[] costs = parser.getParameterValues("cost");
			String[] deprnCosts = parser.getParameterValues("deprnCost");
			String[] deptCodes = parser.getParameterValues("deptCode");
			String[] deptNames = parser.getParameterValues("deptName");
			String[] datePlacedInServices = parser.getParameterValues(
					"datePlacedInService");
			String[] responsibilityUserNames = parser.getParameterValues(
					"responsibilityUserName");
			String[] responsibilityUsers = parser.getParameterValues(
					"responsibilityUser");
			String[] numbers = parser.getParameterValues("currentUnits");
			String[] workorderObjectNames = parser.getParameterValues(
					"workorderObjectName");
			String[] workorderObjectNos = parser.getParameterValues(
					"workorderObjectNo");
			String[] faCategoryCodes = parser.getParameterValues(
					"faCategoryCode");
			String tmpCategorCode = "";
			for (int i = 0; i < barcodes.length; i++) {
				tmpCategorCode = faCategoryCodes[i];
				tmpCategorCode = tmpCategorCode.substring(1, 3);
				if (!dtos.contains("faCode", tmpCategorCode)) {
					continue;
				}
				TdAssetsTransLineDTO item = new TdAssetsTransLineDTO();
				item.setBarcode(barcodes[i]);
				item.setAssetNumber(assetNumbers[i]);
				item.setAssetsDescription(assetsDescriptions[i]);
				item.setModelNumber(modelNumbers[i]);
				item.setCost(Float.valueOf(costs[i]));
				item.setDeprnCost(Float.valueOf(deprnCosts[i]));
				item.setOldResponsibilityDept(deptCodes[i]);
				item.setOldResponsibilityDeptName(deptNames[i]);
				item.setDatePlacedInService(datePlacedInServices[i]);
				item.setOldResponsibilityUserName(responsibilityUserNames[i]);
				item.setOldResponsibilityUser(responsibilityUsers[i]);
				item.setCurrentUnits(StrUtil.strToInt(numbers[i]));
				item.setOldLocationName(workorderObjectNames[i]);
				item.setOldLocation(workorderObjectNos[i]);
				if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) {
					item.setResponsibilityUserName(responsibilityUserNames[i]);
					item.setResponsibilityUser(responsibilityUsers[i]);
					item.setResponsibilityDept(deptCodes[i]);
					item.setResponsibilityDeptName(deptNames[i]);
				}
				orderLines.addDTO(item);
			}
		} catch (StrException ex) {
			ex.printLog();
			throw new DTOException(ex);
		} catch (UploadException ex) {
			ex.printLog();
			throw new DTOException(ex);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new DTOException(ex);
		}
		return orderLines;
	}

	/**
	 * 功能：获取CheckBoxProp对象
	 * @param treeCategory 展示数据的类型
	 * @return CheckBoxProp
	 * @throws StrException
	 */
	private static CheckBoxProp initCheckBoxProp(String treeCategory) throws
			StrException {
		CheckBoxProp checkProp = new CheckBoxProp("subCheck");
		if (treeCategory.equals(AssetsWebAttributes.ASSETS_TREE_CONFIRM)) { //待确认资产

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

		} else { //调拨
			checkProp.addDbField("BARCODE"); //加入的顺序不可变
			checkProp.addDbField("ASSET_NUMBER");
			checkProp.addDbField("ASSETS_DESCRIPTION");
			checkProp.addDbField("MODEL_NUMBER");
			checkProp.addDbField("COST");
			checkProp.addDbField("DEPRN_COST");
			checkProp.addDbField("DATE_PLACED_IN_SERVICE");
			checkProp.addDbField("RESPONSIBILITY_USER_NAME");
			checkProp.addDbField("RESPONSIBILITY_USER");
			checkProp.addDbField("DEPT_NAME");
			checkProp.addDbField("DEPT_CODE");
			checkProp.addDbField("CURRENT_UNITS");
			checkProp.addDbField("WORKORDER_OBJECT_NAME");
			checkProp.addDbField("WORKORDER_OBJECT_NO");
			checkProp.addDbField("FA_CATEGORY_CODE");
		}
		checkProp.setIgnoreOtherField(true);
		return checkProp;
	}
}
