package com.sino.td.synAssets.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.synchronize.dto.EamSyschronizeDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
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
import com.sino.td.synAssets.dao.TdAssetsTransDAO;
import com.sino.td.synAssets.model.TdAssetsTransModel;

/**
 * Created by   李轶
 * Date:        2009-7-22
 * Time:        18:50:55
 * Function     资产调拨结果同步
 */
public class TdAssetsTransServlet extends BaseServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EamSyschronizeDTO.class.getName());
			EamSyschronizeDTO dto = (EamSyschronizeDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			TdAssetsTransDAO commitDAO = new TdAssetsTransDAO(user, dto, conn);
			ServletConfigDTO servletConfig = getServletConfig(req);
			AssetsOptProducer optProducer = new AssetsOptProducer(user, conn);
			String option = "";
			if (!dto.getTransferType().equals("BTW_COMP")){
				option = optProducer.getFilterTransferOption(dto.getTransferType());//获取非盟市间的调拔项
			} else {
				option = optProducer.getBtwTransferOption(dto.getTransferType());   //获取盟市间的调拔下拉项；
			}
			dto.setTransferTypeOption(option);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = TdURLDefineList.TD_ASSETS_TRANSFER_SYN_PAGE;
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) { //查询操作
				BaseSQLProducer sqlProducer = new TdAssetsTransModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setServletConfig(servletConfig);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("SYSTEMID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = TdURLDefineList.TD_ASSETS_TRANSFER_SYN_PAGE;
			} else if (action.equals(WebActionConstant.EXPORT_ACTION)) { //导出操作
				File file = commitDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(WebActionConstant.SYSCHRONIZE_ACTION)) { //同步操作
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.setIgnoreOtherField(true);
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] systemIds = parser.getParameterValues("systemid");
				if(systemIds != null){
					commitDAO.syschronizeAssets(systemIds);
					message = commitDAO.getMessage();
				}
				forwardURL = "/servlet/com.sino.td.synAssets.servlet.TdAssetsTransServlet?act=QUERY_ACTION";
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
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!StrUtil.isEmpty(forwardURL)){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}