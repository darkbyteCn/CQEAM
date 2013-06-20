package com.sino.ams.synchronize.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.bean.FlexValueUtil;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsFaDictDAO;
import com.sino.ams.newasset.dao.AmsMisDeptDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.dto.AmsFaDictDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.synchronize.dao.NetAssetsUpdateDAO;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.synchronize.model.NetAssetsUpdateModel;
import com.sino.ams.system.user.dto.SfGroupDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-3-13
 * Time: 2:17:59
 * To 网络资产变动处理
 */
public class NetAssetsUpdateServlet extends BaseServlet {
     public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String forwardURL = "";
        Message message = SessionUtil.getMessage(req);
        String action = req.getParameter("act");
        action = StrUtil.nullToString(action);
        Connection conn = null;
        try {
            SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
            EamSyschronizeDTO dtoParameter = null;  //声明DTO
            Request2DTO req2DTO = new Request2DTO();
            req2DTO.setDTOClassName(EamSyschronizeDTO.class.getName());
            dtoParameter = (EamSyschronizeDTO) req2DTO.getDTO(req);
            conn = getDBConnection(req);
            NetAssetsUpdateDAO netAssetsUpdate = new NetAssetsUpdateDAO(user,dtoParameter,conn);
            AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);

            if(action.equals("")) {
				String deptOptions = optProducer.getUserAsssetsDeptOption(dtoParameter.getNewDeptName());
			    dtoParameter.setNewDeptNameOption(deptOptions);
              	dtoParameter.setCalPattern(LINE_PATTERN);
                req.setAttribute(WebAttrConstant.SYSCHRONIZE_DTO, dtoParameter);
                forwardURL = URLDefineList.NET_ASSETS_UPDATE;
            } else if(action.equals(WebActionConstant.QUERY_ACTION)) {  //查询操作
                BaseSQLProducer sqlProducer = new NetAssetsUpdateModel(user, dtoParameter);
                PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
                String deptOptions = optProducer.getUserAsssetsDeptOption(dtoParameter.getNewDeptName());
			    dtoParameter.setNewDeptNameOption(deptOptions);
                pageDAO.setWebCheckProp(initCheckBoxProp());
                pageDAO.setCalPattern(LINE_PATTERN);
                pageDAO.produceWebData();
              	req.setAttribute(WebAttrConstant.SYSCHRONIZE_DTO, dtoParameter);
                forwardURL = URLDefineList.NET_ASSETS_UPDATE;
            }  else if (action.equals(AssetsActionConstant.TRANSFER_ACTION)) {  //调拨操作
              	AmsAssetsTransHeaderDTO headerDTO = getAssetsOrder(user, dtoParameter, AssetsDictConstant.ASS_RED, conn);
				DTOSet orderLines = getProcessItems(req, action, user, conn);
				req.setAttribute(AssetsWebAttributes.ORDER_HEAD_DATA, headerDTO);
				req.setAttribute(AssetsWebAttributes.ORDER_LINE_DATA, orderLines);
				forwardURL = AssetsURLList.ASSETS_TRANS_SERVLET + "?act=" + AssetsActionConstant.NEW_ACTION;
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
        }  catch (QueryException ex) {
            ex.printLog();
            message = getMessage(MsgKeyConstant.QUERY_ERROR);
            message.setIsError(true);
            forwardURL = MessageConstant.MSG_PRC_SERVLET;
        } catch (CalendarException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (StrException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            DBManager.closeDBConnection(conn);
            setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
            forwarder.forwardView(forwardURL);
            //根据实际情况修改页面跳转代码。
        }
     }
    /**
	 * 功能：构造资产调拨等单据数据
	 * @param user SfUserDTO
	 * @param dto AmsAssetsAddressVDTO
	 * @param transType String
	 * @param conn Connection
	 * @return AmsAssetsTransHeaderDTO
	 * @throws CalendarException
	 * @throws QueryException
	 */
	private AmsAssetsTransHeaderDTO getAssetsOrder(SfUserDTO user, EamSyschronizeDTO dto, String transType,
		Connection conn) throws CalendarException, QueryException {
		AmsAssetsTransHeaderDTO order = new AmsAssetsTransHeaderDTO();
		order.setTransNo(AssetsWebAttributes.ORDER_NO_AUTO_PRODUCE);
		order.setFromOrganizationId(user.getOrganizationId());
		order.setTransType(transType);
		FlexValueUtil flexUtil = new FlexValueUtil(user, conn);
		order.setTransTypeValue(flexUtil.getFlexValue(AssetsDictConstant.ORDER_TYPE_ASSETS, order.getTransType()));

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
		String deptCode = StrUtil.nullToString(order.getFromDept());
		if (deptCode.equals("")) {
			deptCode = user.getDeptCode();
		}
		String deptOptions = optProducer.getUserAsssetsDeptOption(deptCode);
		order.setFromDeptOption(deptOptions);

		if (transType.equals(AssetsDictConstant.ASS_RED)) {
			if (dto.getTransferType().equals(AssetsDictConstant.TRANS_BTW_COMP)) {
				String opt = optProducer.getAllOrganization(order.getToOrganizationId());
				order.setToCompanyOption(opt);
			} else {
				if (order.getTransId().equals("")) {
					order.setToOrganizationId(user.getOrganizationId());
				}
			}
		}

		order.setFromCompanyName(user.getCompany());
		order.setBookTypeName(user.getBookTypeName());
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
	private DTOSet getProcessItems(HttpServletRequest req, String action, SfUserDTO user, Connection conn) throws
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
			String[] barcodes = parser.getParameterValues("newBarcode");
			String[] assetNumbers = parser.getParameterValues("assetNumber");
			String[] assetsDescriptions = parser.getParameterValues("oldAssetsDescription");
			String[] modelNumbers = parser.getParameterValues("oldModelNumber");
			String[] oldAssetsDescription = parser.getParameterValues("oldAssetsDescription");
			String[] oldModelNumber = parser.getParameterValues("oldModelNumber");
			String[] newAssetsLocation = parser.getParameterValues("newAssetsLocation");
			String[] oldAssetsLocation = parser.getParameterValues("oldAssetsLocation");
            String[] deptCodes = parser.getParameterValues("deptCode");
			String[] deptNames = parser.getParameterValues("newdeptName");
            String[] oldDeptName = parser.getParameterValues("oldDeptName");
			String[] oldUserName = parser.getParameterValues("oldUserName");
			String[] newUserName = parser.getParameterValues("newUserName");
			for (int i = 0; i < barcodes.length; i++) {

				AmsAssetsTransLineDTO item = new AmsAssetsTransLineDTO();
				item.setBarcode(barcodes[i]);
				item.setAssetNumber(assetNumbers[i]);
				item.setAssetsDescription(assetsDescriptions[i]);
				item.setModelNumber(modelNumbers[i]);
				item.setOldResponsibilityDept(deptCodes[i]);
				item.setOldResponsibilityDeptName(deptNames[i]);
			//	item.setDatePlacedInService(datePlacedInServices[i]);
				item.setOldResponsibilityUserName(oldUserName[i]);
			//	item.setOldResponsibilityUser(oldUserName[i]);
				item.setOldLocationName(oldAssetsLocation[i]);
			//	item.setOldLocation(workorderObjectNos[i]);
				if (transferType.equals(AssetsDictConstant.TRANS_INN_DEPT)) {
					item.setResponsibilityUserName(oldUserName[i]);
					item.setResponsibilityUser(newUserName[i]);
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
		} /*catch (CalendarException ex) {
			ex.printLog();
			throw new DTOException(ex);
		}*/
		return orderLines;
	}

    	/**
	 * 功能：获取CheckBoxProp对象
	 * @return CheckBoxProp
	 * @throws StrException
	 */
	private static CheckBoxProp initCheckBoxProp() throws StrException {
		CheckBoxProp checkProp = new CheckBoxProp("subCheck");

			checkProp.addDbField("OLD_BARDOE"); //加入的顺序不可变
			checkProp.addDbField("NEW_BARCODE");
			checkProp.addDbField("ASSETS_DESCRIPTION");
			checkProp.addDbField("ASSET_NUMBER");
			checkProp.addDbField("OLD_ASSETS_DESCRIPTION");
			checkProp.addDbField("OLD_MODEL_NUMBER");
			checkProp.addDbField("OLD_ASSETS_LOCATION");
			checkProp.addDbField("NEW_ASSETS_LOCATION");
			checkProp.addDbField("OLD_DEPT_NAME");
			checkProp.addDbField("OLD_USER_NAME");
			checkProp.addDbField("NEW_USER_NAME");
            checkProp.addDbField("NEW_DEPT_NAME");
            checkProp.addDbField("DEPT_CODE");
        checkProp.setIgnoreOtherField(true);
		return checkProp;
	}
}
