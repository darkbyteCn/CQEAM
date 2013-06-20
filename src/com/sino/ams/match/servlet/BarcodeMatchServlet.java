package com.sino.ams.match.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.AMSActionConstant;
import com.sino.ams.constant.CustMessageKey;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.match.dao.BarcodeMatchDAO;
import com.sino.ams.match.dto.BarcodeMatchDTO;
import com.sino.ams.match.model.BarcodeMatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.security.dto.ServletConfigDTO;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-11-29
 * Time: 9:35:52
 * To change this template use File | Settings | File Templates.
 */
public class BarcodeMatchServlet extends BaseServlet {

/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(BarcodeMatchDTO.class.getName());
			BarcodeMatchDTO dto = (BarcodeMatchDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			BarcodeMatchDAO barcodeMatchDAO = new BarcodeMatchDAO(user, dto, conn);
			OptionProducer prd = new OptionProducer(user, conn);
			String dept = prd.getDeptOption(dto.getResponsibilityDept());
			req.setAttribute(WebAttrConstant.RESOURCE_OPTION, dept);
			ServletConfigDTO configDTO = getServletConfig(req);
			if (action.equals("")) {
				dto.setMatch("N");
				req.setAttribute(WebAttrConstant.IS_MATCH_OPTION, getIsMatchOption(dto.getMatch()));
				req.setAttribute(WebAttrConstant.BARCODE_MATCH_DTO, dto);
				forwardURL = URLDefineList.BARCODE_MATCH;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new BarcodeMatchModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setServletConfig(configDTO);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("SYSTEMID");
				checkProp.addDbField("ASSET_ID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.produceWebData();
				req.setAttribute(WebAttrConstant.IS_MATCH_OPTION, getIsMatchOption(dto.getMatch()));
				req.setAttribute(WebAttrConstant.BARCODE_MATCH_DTO, dto);
				forwardURL = URLDefineList.BARCODE_MATCH;
			} else if (action.equals(AMSActionConstant.MATCH_ACTION)) { //进行匹配操作
				DTOSet checkedAssets = getCheckedAssets(req);
				String ret = barcodeMatchDAO.matchCheckedAssets(checkedAssets);
				req.setAttribute(WebAttrConstant.IS_MATCH_OPTION, getIsMatchOption(dto.getMatch()));
				req.setAttribute(WebAttrConstant.BARCODE_MATCH_DTO, dto);
				forwardURL = URLDefineList.BARCODE_MATCH_SERVLET;
				forwardURL += "?act=" + WebActionConstant.QUERY_ACTION;
				if (ret.equalsIgnoreCase("Y")) {
                    message = getMessage(CustMessageKey.MATCH_SUCCESS);
                    message.setIsError(false);
                } else {
                    message = getMessage(CustMessageKey.MATCH_FAILURE);
                    message.setIsError(true);
                }                
//				forwardURL += "&ret=" + ret;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				barcodeMatchDAO.updateData();
				forwardURL = URLDefineList.BARCODE_MATCH;
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) { //进行批量匹配操作
				barcodeMatchDAO.deleteData();
				forwardURL = URLDefineList.BARCODE_MATCH;
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) { //导出
				barcodeMatchDAO.setServletConfig(configDTO);
				File file = barcodeMatchDAO.exportFile();
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
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (StrException ex) {
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
				//根据实际情况修改页面跳转代码。
			}
		}
	}


	/**
	 * 功能：构造是否匹配下拉框
	 * @param selectedValue String
	 * @return String
	 */
	private String getIsMatchOption(String selectedValue) {
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
		strOpt.append(">已匹配</option>");
		strOpt.append("<option value=\"");
		strOpt.append(WebAttrConstant.FALSE_VALUE);
		strOpt.append("\"");
		if (selectedValue.equals(WebAttrConstant.FALSE_VALUE)) {
			strOpt.append(" selected");
		}
		strOpt.append(">未匹配</option>");
		return strOpt.toString();
	}

	private DTOSet getCheckedAssets(HttpServletRequest req) throws ServletException {
		DTOSet checkedAssets = new DTOSet();
		try {
			RequestParser parser = new RequestParser();
			CheckBoxProp checkProp = new CheckBoxProp("subCheck");
			checkProp.setIgnoreOtherField(true);
			parser.setCheckBoxProp(checkProp);
			parser.transData(req);
			String[] systemids = parser.getParameterValues("systemid");
			String[] assetIds = parser.getParameterValues("assetId");
			if(systemids != null){
				int checkedCount = systemids.length;
				for(int i = 0; i < checkedCount; i++){
					BarcodeMatchDTO matchDTO = new BarcodeMatchDTO();
					matchDTO.setSystemid(systemids[i]);
					matchDTO.setAssetId(StrUtil.strToInt(assetIds[i]));
					checkedAssets.addDTO(matchDTO);
				}
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
		return checkedAssets;
	}
}
