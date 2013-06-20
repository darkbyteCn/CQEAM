package com.sino.ams.newasset.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.URLDefineList;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsCheckLineDAO;
import com.sino.ams.newasset.dao.ArchiveHeaderDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.newasset.model.ArchiveHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.DataTransException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.message.Message;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class ChkOrderArchiveServlet extends BaseServlet {
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
			req2DTO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
			AmsAssetsCheckHeaderDTO dtoParameter = (AmsAssetsCheckHeaderDTO)req2DTO.getDTO(req);
			String action = dtoParameter.getAct();
			conn = getDBConnection(req);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = AssetsURLList.ARCHIVE_ORDER_QRY;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				dtoParameter.setOrderStatus(AssetsDictConstant. CHK_STATUS_UPLOADED);
				BaseSQLProducer sqlProducer = new ArchiveHeaderModel(user, dtoParameter);
				sqlProducer.setServletConfig(getServletConfig(req));
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("HEADER_ID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dtoParameter);
				forwardURL = AssetsURLList.ARCHIVE_ORDER_QRY;
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
				AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) req.getAttribute(AssetsWebAttributes.CHECK_HEADER_DATA);
				DTOSet chkLines = (DTOSet) req.getAttribute(AssetsWebAttributes.CHECK_LINE_DATAS);
				if (dto == null) {
					ArchiveHeaderDAO archiveDAO = new ArchiveHeaderDAO(user, dtoParameter, conn);
					archiveDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
					archiveDAO.setCalPattern(LINE_PATTERN);
					dto = (AmsAssetsCheckHeaderDTO) archiveDAO.getDataByPrimaryKey();
					AmsAssetsCheckLineDTO checkLine = new AmsAssetsCheckLineDTO();
					checkLine.setHeaderId(dto.getHeaderId());
					AmsAssetsCheckLineDAO lineDAO = new AmsAssetsCheckLineDAO(user, checkLine, conn);
					lineDAO.setCalPattern(LINE_PATTERN);
					lineDAO.setDTOClassName(AmsAssetsCheckLineDTO.class.getName());
					chkLines = (DTOSet) lineDAO.getDataByForeignKey("headerId");
				}
				if (dto == null) {
					dto = dtoParameter;
					message = getMessage(AssetsMessageKeys.DATA_NOT_EXIST);
					message.setIsError(true);
					message.setNeedClose(true);
				}
				req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATA, dto);
				req.setAttribute(AssetsWebAttributes.CHECK_LINE_DATAS, chkLines);
				forwardURL = AssetsURLList.ARCHIVE_ORDER_DTL;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) { //导出待归档工单
				ArchiveHeaderDAO archiveDAO = new ArchiveHeaderDAO(user,dtoParameter, conn);
				File file = archiveDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(AssetsActionConstant.EPT_DTL_ACTION)) { //导出工单详细信息
				ArchiveHeaderDAO archiveDAO = new ArchiveHeaderDAO(user,dtoParameter, conn);
				File file = archiveDAO.exportArchiveData();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(AssetsActionConstant.ARCHIVE_ACTION)) {
				dtoParameter.setOrderStatus(AssetsDictConstant.CHK_STATUS_ARCHIEVED);
				dtoParameter.setStatusName(AssetsDictConstant.CHKORDER_STATUS_ACHIEVED);
				req2DTO.setIgnoreFields(AmsAssetsCheckHeaderDTO.class);
				req2DTO.setDTOClassName(AmsAssetsCheckLineDTO.class.getName());
				DTOSet chkLines = req2DTO.getDTOSet(req);
				ArchiveHeaderDAO archiveDAO = new ArchiveHeaderDAO(user, dtoParameter, conn);
				archiveDAO.setServletConfig(getServletConfig(req));
				boolean operateResult = archiveDAO.archiveChkOrder(chkLines);
				message = archiveDAO.getMessage();
				forwardURL = AssetsURLList.ARCHIVE_ORDER_SERVLET;
				forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
				forwardURL += "&headerId=" + dtoParameter.getHeaderId();
				if (!operateResult) {
					dtoParameter.setOrderStatus(AssetsDictConstant.CHK_STATUS_UPLOADED);
					dtoParameter.setStatusName(AssetsDictConstant.CHKORDER_STATUS_UPLOADED);
					req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATA,dtoParameter);
					req.setAttribute(AssetsWebAttributes.CHECK_LINE_DATAS,chkLines);
				}
			} else if(action.equals(AssetsActionConstant.BACK_ACTION)) {
				ArchiveHeaderDAO archiveDAO = new ArchiveHeaderDAO(user,dtoParameter, conn);
				archiveDAO.getBackOrder();
				forwardURL = URLDefineList.WINDOW_CLOSE_PAGE;
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
		} catch (DataHandleException e) {
			e.printStackTrace();
		} finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!forwardURL.equals("")) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
