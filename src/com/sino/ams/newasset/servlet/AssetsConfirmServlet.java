package com.sino.ams.newasset.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AssetsConfirmDAO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsConfirmServlet extends BaseServlet {
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
			req2DTO.setDTOClassName(AmsAssetsTransLineDTO.class.getName());
			AmsAssetsTransLineDTO dto = (AmsAssetsTransLineDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			dto.setCalPattern(LINE_PATTERN);
			conn = getDBConnection(req);
			AssetsConfirmDAO assignDAO = new AssetsConfirmDAO(user, dto, conn);
			if (action.equals(AssetsActionConstant.CONFIRM_ACTION)) {
				DTOSet assignDatas = getConfirmData(req);
				boolean operateResult = assignDAO.confirmAssets(assignDatas);
				message = assignDAO.getMessage();
				message.setIsError(!operateResult);
				
				if( StrUtil.isEmpty( message.getMessageValue() )){
					if( operateResult ){
						message.setMessageValue( "确认资产成功" );
					}else{
						message.setMessageValue( "确认资产失败" );
					}
				}
				
                forwardURL="/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet";
//				forwardURL = AssetsURLList.ASSETS_FRM_SERVLET;
				forwardURL += "?act=" + AssetsActionConstant.QUERY_ACTION;
				forwardURL += "&treeCategory=" + AssetsWebAttributes.ASSETS_TREE_CONFIRM;
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
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
//			forwarder.forwardParentView(forwardURL, message);
			forwarder.forwardView(forwardURL);
		}
	}

	private DTOSet getConfirmData(HttpServletRequest req) throws
		ServletException {
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
				AmsAssetsTransLineDTO item = new AmsAssetsTransLineDTO();
				item.setTransId(transIds[i]);
				item.setTransNo(transNos[i]);
				item.setTransferType(transferTypes[i]);
				item.setLineStatus(AssetsDictConstant.ORDER_STS_CONFIRMD);

				item.setBarcode(barcodes[i]);
				item.setOldResponsibilityDept(oldResponsibilityDepts[i]);
				item.setOldResponsibilityUser(oldResponsibilityUsers[i]);
				item.setOldLocation( oldLocations[i] );
				item.setOldAddressId(oldAddressIds[i]);
				item.setFromOrganizationId(StrUtil.strToInt(fromOrganizationIds[i]));

				item.setNewBarcode(newBarcodes[i]);
				item.setResponsibilityDept(responsibilityDepts[i]);
				item.setResponsibilityUser(responsibilityUsers[i]);
				item.setAssignedToLocation(assignedToLocations[i]);
				item.setAddressId( addressIds[i] );
				item.setToOrganizationId(StrUtil.strToInt(toOrganizationIds[i]));
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
