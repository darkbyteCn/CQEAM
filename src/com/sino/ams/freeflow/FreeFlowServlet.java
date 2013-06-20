package com.sino.ams.freeflow;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.bean.FlexValueUtil;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsTransLineDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.system.user.dto.SfUserRightDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
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
import com.sino.flow.bean.FlowAction;
import com.sino.flow.dto.FlowDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 2009-3-25
 * Time: 17:02:06
 * To change this template use File | Settings | File Templates.
 */
public class FreeFlowServlet extends BaseServlet {
    public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(FreeFlowDTO.class.getName());
			FreeFlowDTO dto = (FreeFlowDTO) req2DTO.getDTO(req);
			ServletConfigDTO servletConfig = getServletConfig(req);
			dto.setServletConfig(servletConfig);
			FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			FreeFlowDAO headerDAO = new FreeFlowDAO(user, dto, conn);
			headerDAO.setServletConfig(servletConfig);
			String transType = dto.getTransType();
			String transferype = dto.getTransferType();
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			String option = "";
			option = optProducer.getFAContentOption(dto.getFaContentCode());
			dto.setFaContentOption(option);
            boolean isGroupPID = headerDAO.isGroupFlowId();
            req.setAttribute(AssetsWebAttributes.IS_GROUP_PID, isGroupPID+"");
            if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.FREE_FLOW_QUERY;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				dto.setTransStatus(AssetsDictConstant.SAVE_TEMP);
				BaseSQLProducer sqlProducer = new FreeFlowModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("TRANS_ID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = URLDefineList.FREE_FLOW_QUERY;
			} else if (action.equals(AssetsActionConstant.NEW_ACTION)) {
				FreeFlowDTO headerDTO = (FreeFlowDTO) req.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
				if (headerDTO == null) {
					headerDTO = fillData(dto, user, conn);
				} else {
					option = optProducer.getFAContentOption(dto.getFaContentCode());
					headerDTO.setFaContentOption(option);
					String deptOptions = optProducer.getUserAsssetsDeptOption("");
					dto.setFromDeptOption(deptOptions);
				}
				headerDTO.setServletConfig(servletConfig);
				String provinceCode = servletConfig.getProvinceCode();
				headerDTO.setCalPattern(LINE_PATTERN);
				 if(provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SN)) {
                    req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
					forwardURL = URLDefineList.FREE_FLOW_EDIT;
				}
			} else if (action.equals(AssetsActionConstant.EDIT_ACTION)) {
				headerDAO.setDTOClassName(FreeFlowDTO.class.getName());
				FreeFlowDTO headerDTO = (FreeFlowDTO) headerDAO.getDataByPrimaryKey();
				if (headerDTO == null) {
					headerDTO = fillData(dto, user, conn);
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				} else {
					headerDTO.setServletConfig(servletConfig);
					headerDTO = fillOptions(headerDTO, user, conn);
					headerDTO.setCalPattern(LINE_PATTERN);
					AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
					lineDTO.setTransId(headerDTO.getTransId());
					lineDTO.setTransType(headerDTO.getTransType());
					AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(user, lineDTO, conn);
					lineDAO.setCalPattern(LINE_PATTERN);
					lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
					DTOSet ds = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
					if (ds == null) {
						ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
					}
					req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
					req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
					if (servletConfig.getProvinceCode().equals(AssetsDictConstant.PROVINCE_CODE_NM)) {
						forwardURL = URLDefineList.TRANS_FREE_EDIT;
					} else {
						forwardURL = URLDefineList.FREE_FLOW_EDIT;
					}
				}
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
				headerDAO.setDTOClassName(FreeFlowDTO.class.getName());
				FreeFlowDTO headerDTO = (FreeFlowDTO)headerDAO.getDataByPrimaryKey();
                OrderApproveDAO approveDAO = new OrderApproveDAO(user, dto, conn);
                String accessSheet = approveDAO.getAccessSheet();//附件张数
                headerDTO.setAccessSheet(accessSheet);
                if (headerDTO == null) {
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				} else {
					headerDTO.setServletConfig(servletConfig);
					headerDTO = fillOptions(headerDTO, user, conn);
					headerDTO.setCalPattern(LINE_PATTERN);
					AmsAssetsTransLineDTO lineDTO = new AmsAssetsTransLineDTO();
					lineDTO.setTransId(headerDTO.getTransId());
					lineDTO.setTransType(headerDTO.getTransType());
					AmsAssetsTransLineDAO lineDAO = new AmsAssetsTransLineDAO(user, lineDTO, conn);
					lineDAO.setCalPattern(LINE_PATTERN);
					lineDAO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
					DTOSet ds = (DTOSet) req.getAttribute(AssetsWebAttributes.ORDER_LINE_DATA);
					if (ds == null) {
						ds = (DTOSet) lineDAO.getDataByForeignKey("transId");
					}
					req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
					req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, ds);
					if (servletConfig.getProvinceCode().equals(AssetsDictConstant.PROVINCE_CODE_NM)) {
						forwardURL = URLDefineList.TRANS_FREE_DETAIL_NOTHER;
					} else {
						forwardURL = URLDefineList.TRANS_FREE_DETAIL;
					}
				}
			} else if (action.equals(AssetsActionConstant.SAVE_ACTION)) {
				req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
				req2DTO.setIgnoreFields(FreeFlowDTO.class);
				DTOSet orderLines = req2DTO.getDTOSet(req);
				dto.setTransStatus(AssetsDictConstant.SAVE_TEMP);
				headerDAO.setDTOParameter(dto);
				headerDAO.saveOrder(orderLines, flowDTO);
				message = headerDAO.getMessage();
				dto = (FreeFlowDTO) headerDAO.getDTOParameter();
				String transId = dto.getTransId();
				forwardURL = URLDefineList.FREE_FLOW_SERVLET;
				if (transId.equals("")) {
					forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
					req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, orderLines);
				} else {
					forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
					forwardURL += "&transId=" + dto.getTransId();
				}
			} else if (action.equals(AssetsActionConstant.SUBMIT_ACTION)) {
				req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
				req2DTO.setIgnoreFields(FreeFlowDTO.class);
				DTOSet orderLines = req2DTO.getDTOSet(req);
				dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
				headerDAO.setDTOParameter(dto);
				headerDAO.submitOrder(orderLines, flowDTO);
				message = headerDAO.getMessage();
				dto = (FreeFlowDTO) headerDAO.getDTOParameter();
				String transId = dto.getTransId();
				forwardURL = URLDefineList.FREE_FLOW_SERVLET;
				if (transId.equals("")) {
					forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
				} else {
					forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
					forwardURL += "&transId=" + dto.getTransId();
				}
			}else if(action.equals(AssetsActionConstant.DELETE_ACTION)){
				req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
				req2DTO.setIgnoreFields(FreeFlowDTO.class);
				DTOSet orderLines = req2DTO.getDTOSet(req);
				dto.setTransStatus(AssetsDictConstant.IN_PROCESS);
				headerDAO.setDTOParameter(dto);
				headerDAO.doDelete(orderLines, flowDTO);
				message = headerDAO.getMessage();
				dto = (FreeFlowDTO) headerDAO.getDTOParameter();
				String transId = dto.getTransId();
				forwardURL = "/servlet/com.sino.ams.freeflow.OrderApproveServlet";
				if (transId.equals("")) {
					forwardURL += "?act=" + AssetsActionConstant.NEW_ACTION;
				} else {
					forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
					forwardURL += "&transId=" + dto.getTransId();
				}
			} else if (action.equals(AssetsActionConstant.CANCEL_ACTION)) { //撤销暂存的单据
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("TRANS_ID");
				RequestParser parser = new RequestParser();
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] transIds = parser.getParameterValues("transId");
				boolean operateResult = headerDAO.cancelOrders(transIds);
				message = headerDAO.getMessage();
				forwardURL = URLDefineList.FREE_FLOW_SERVLET;
				if (parser.contains("fromGroup")) {
					if (operateResult) {
						forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
					} else {
						forwardURL += "?act=" + AssetsActionConstant.EDIT_ACTION;
					}
					forwardURL += "&transId=" + dto.getTransId();
				} else {
					forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
				}
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) { //导出
				File file = headerDAO.exportFile();
				headerDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(AssetsActionConstant.GET_TARGET_OU)) { //获取目标OU
				res.setContentType("text/html;charset=GBK");
				PrintWriter out = res.getWriter();
				String opt = optProducer.getTargetOrganization(dto.getFromOrganizationId(), 0);
				out.print(opt);
				out.close();
			} else if (action.equals("excel")) {
                 FreeFlowDTO headerDTO = (FreeFlowDTO) req.getAttribute(AssetsWebAttributes.ORDER_HEAD_DATA);
				if (headerDTO == null) {
					headerDTO = fillData(dto, user, conn);
				} else {
					option = optProducer.getFAContentOption(dto.getFaContentCode());
					headerDTO.setFaContentOption(option);
					String deptOptions = optProducer.getUserAsssetsDeptOption("");
					dto.setFromDeptOption(deptOptions);
				}
				headerDTO.setServletConfig(servletConfig);
				headerDTO.setCalPattern(LINE_PATTERN);

                DTOSet lineDTOSet = new DTOSet();
                FreeFlowModel freeFlowModel = new FreeFlowModel(user, dto);
                String excel= StrUtil.nullToString(req.getParameter("excel"));
                SQLModel sqlModel = new SQLModel();
                sqlModel = freeFlowModel.getQueryBarcodeExcelModel(excel, headerDTO);
                SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
//				simpleQuery.executeQuery();
//				RowSet rowSet = simpleQuery.getSearchResult();
                simpleQuery.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
                simpleQuery.executeQuery();
                lineDTOSet = simpleQuery.getDTOSet();

                req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
//                req.setAttribute(WebAttrConstant.WORKORDER_LOC_ROWSET, rowSet);

                req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA,lineDTOSet);
                forwardURL = URLDefineList.FREE_FLOW_EDIT;

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
		} catch (CalendarException e) {
			e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
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
	 * 功能:填充DTO数据
	 * @param dto  AmsAssetsTransHeaderDTO
	 * @param user SfUserDTO
	 * @param conn Connection
	 * @return AmsAssetsTransHeaderDTO
	 * @throws DTOException
	 * @throws QueryException
	 * @throws CalendarException
	 */
	private FreeFlowDTO fillData(FreeFlowDTO dto, SfUserDTO user, Connection conn) throws
			DTOException, QueryException, CalendarException {
		dto.setTransNo(AssetsWebAttributes.ORDER_AUTO_PROD); //设置单据号
		dto.setCreatedBy(user.getUserId()); //设置创建人
		dto.setCreated(user.getUsername()); //设置创建人
		dto.setFromOrganizationId(user.getOrganizationId());
		dto.setCurrCreationDate();
		dto.setFromCompanyName(user.getCompany());
		dto.setBookTypeName(user.getBookTypeCode() + "--" + user.getBookTypeName());
		dto.setEmail(user.getEmail());
		dto.setPhoneNumber(user.getMobilePhone());
		dto.setUserDeptName(user.getDeptName());
		DTOSet assetsGroups = user.getUserGroups();
		String provinceCode = dto.getServletConfig().getProvinceCode();
		if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_NM)) { //内蒙古特殊处理
			String transferType = dto.getTransferType();
			SfGroupDTO sfGRoup = null;
			if (transferType.equals(AssetsDictConstant.TRANS_BTW_COMP)) {
				DTOSet specGroups = getSpecGroups(user);
				if (specGroups.getSize() == 1) {
					sfGRoup = (SfGroupDTO) specGroups.getDTO(0);
					dto.setFromGroup(sfGRoup.getGroupId());
					dto.setFromGroupName(sfGRoup.getGroupname());
				}
			} else {
				if (assetsGroups.getSize() == 1) {
					sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
					dto.setFromGroup(sfGRoup.getGroupId());
					dto.setFromGroupName(sfGRoup.getGroupname());
				}
			}
		} else if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_SX)) {//山西特殊处理
			String transType = dto.getTransType();
			if(transType.equals(AssetsDictConstant.ASS_RED)){
				if (assetsGroups.getSize() == 1) {
					SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
					dto.setFromGroup(sfGRoup.getGroupId());
					dto.setFromGroupName(sfGRoup.getGroupname());
					dto.setGroupProp(sfGRoup.getGroupProp());
				}
			} else {
				if(!user.isProvinceUser()){
					assetsGroups = getSpecGroups(user);
				}
				if (assetsGroups.getSize() == 1) {
					SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
					dto.setFromGroup(sfGRoup.getGroupId());
					dto.setFromGroupName(sfGRoup.getGroupname());
					dto.setGroupProp(sfGRoup.getGroupProp());
				}
			}
		} else {
			if (assetsGroups.getSize() == 1) {
				SfGroupDTO sfGRoup = (SfGroupDTO) assetsGroups.getDTO(0);
				dto.setFromGroup(sfGRoup.getGroupId());
				dto.setFromGroupName(sfGRoup.getGroupname());
				dto.setGroupProp(sfGRoup.getGroupProp());
			}
		}
		dto = fillOptions(dto, user, conn);
		return dto;
	}

	/**
	 * 功能：获取用户的专业组别
	 * @param user SfUserDTO
	 * @return DTOSet
	 * @throws DTOException
	 */
	private DTOSet getSpecGroups(SfUserDTO user) throws DTOException {
		DTOSet specGroups = new DTOSet();
		DTOSet assetsGroups = user.getUserGroups();
		SfGroupDTO sfGroup = null;
		for (int i = 0; i < assetsGroups.getSize(); i++) {
			sfGroup = (SfGroupDTO) assetsGroups.getDTO(i);
			if (!sfGroup.getGroupProp().equals(AssetsDictConstant.GROUP_PROP_SPEC)) {
				continue;
			}
			specGroups.addDTO(sfGroup);
		}
		return specGroups;
	}

	/**
	 * 功能：获取用户的专业组别
	 * @param user          SfUserDTO
	 * @param servletConfig servlet配置信息
	 * @return DTOSet
	 * @throws DTOException
	 */
	private boolean hasSpecRole(SfUserDTO user, ServletConfigDTO servletConfig) throws
			DTOException {
		boolean hasSpecRole = false;
		DTOSet specGroups = getSpecGroups(user);
		if (specGroups != null && !specGroups.isEmpty()) {
			DTOSet groupRoles = user.getUserRight();
			SfGroupDTO sfGroup = null;
			SfUserRightDTO sfUserRight = null;
			for (int i = 0; i < specGroups.getSize(); i++) {
				sfGroup = (SfGroupDTO) specGroups.getDTO(i);
				for (int j = 0; j < groupRoles.getSize(); j++) {
					sfUserRight = (SfUserRightDTO) groupRoles.getDTO(j);
					if (sfUserRight.getGroupId()!=sfGroup.getGroupId()) {
						continue;
					}
					if (!sfUserRight.getRoleName().equals(servletConfig.
							getMtlAssetsMgr())) {
						continue;
					}
					hasSpecRole = true;
					break;
				}
				if (hasSpecRole) {
					break;
				}
			}
		}
		return hasSpecRole;
	}

	/**
	 * 功能：补充资产来源部门数据下拉框
	 * @param dto  AmsAssetsTransHeaderDTO
	 * @param user SfUserDTO
	 * @param conn Connection
	 * @return AmsAssetsTransHeaderDTO
	 * @throws QueryException
	 */
	private FreeFlowDTO fillOptions(FreeFlowDTO dto, SfUserDTO user, Connection conn) throws
			QueryException {
		FlexValueUtil flexUtil = new FlexValueUtil(user, conn);
		dto.setTransTypeValue(flexUtil.getFlexValue(AssetsDictConstant.ORDER_TYPE_ASSETS, dto.getTransType()));
		AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
		String fromDept = StrUtil.nullToString(dto.getFromDept());
		if (StrUtil.isEmpty(fromDept)) {
			fromDept = user.getDeptCode();
		}
		String deptOptions = optProducer.getUserAsssetsDeptOption(fromDept);
		dto.setFromDeptOption(deptOptions);
		String transType = dto.getTransType();
		if (transType.equals(AssetsDictConstant.ASS_RED)) {
			if (dto.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP)) {
				String opt = optProducer.getTargetOrganization(user.getOrganizationId(), dto.getToOrganizationId());
				dto.setToCompanyOption(opt);
				ServletConfigDTO config = dto.getServletConfig();
				String provinceCode = config.getProvinceCode();
				if (provinceCode.equals(AssetsDictConstant.PROVINCE_CODE_NM)) {
					int fromOrgId = dto.getFromOrganizationId();
					if (fromOrgId == 0) {
						fromOrgId = user.getOrganizationId();
					}
					opt = optProducer.getAllOrganization(fromOrgId);
					dto.setFromCompanyOption(opt);
					opt = optProducer.getBookTypeOption2(StrUtil.nullToString(fromOrgId));
					dto.setBookTypeOption(opt);
				}
			} else {
				if (dto.getTransId().equals("")) {
					dto.setToOrganizationId(user.getOrganizationId());
				}
			}
		}
		String transOption = "";
		if (transType.equals(AssetsDictConstant.ASS_RED)) {
			transOption = optProducer.getTransferFaOption(dto.getFaContentCode(),
					dto.getTransferType());
		} else {
			transOption = optProducer.getFAContentOption(dto.getFaContentCode());
		}
		dto.setFaContentOption(transOption);
		return dto;
	}

}
