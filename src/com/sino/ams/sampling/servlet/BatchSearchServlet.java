package com.sino.ams.sampling.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.sampling.constant.SamplingActions;
import com.sino.ams.sampling.constant.SamplingURLs;
import com.sino.ams.sampling.constant.SamplingWebAttributes;
import com.sino.ams.sampling.dao.AmsAssetsSamplingBatchDAO;
import com.sino.ams.sampling.dao.AmsAssetsSamplingHeaderDAO;
import com.sino.ams.sampling.dto.AmsAssetsSamplingBatchDTO;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.sampling.model.AmsAssetsSamplingBatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class BatchSearchServlet extends BaseServlet {
	/**
	 * 所有的Servlet都必须实现的方法。
	 *
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsSamplingBatchDTO.class.getName());
			AmsAssetsSamplingBatchDTO dto = (AmsAssetsSamplingBatchDTO)req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AmsAssetsSamplingBatchDAO batchDAO = new AmsAssetsSamplingBatchDAO(user, dto, conn);
			batchDAO.setServletConfig(getServletConfig(req));
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = SamplingURLs.BATCH_LIST_PAGE;
			} else if (action.equals(SamplingActions.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsAssetsSamplingBatchModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setDTOClassName(AmsAssetsSamplingBatchDTO.class.getName());
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = SamplingURLs.BATCH_LIST_PAGE;
			} else if (action.equals(SamplingActions.EXPORT_ACTION)) {
				File file = batchDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(SamplingActions.DETAIL_ACTION)) {
				batchDAO.setDTOClassName(AmsAssetsSamplingBatchDTO.class.getName());
				dto = (AmsAssetsSamplingBatchDTO)batchDAO.getDataByPrimaryKey();
				AmsAssetsSamplingHeaderDTO orderHeader = new AmsAssetsSamplingHeaderDTO();
				orderHeader.setBatchId(dto.getBatchId());
				AmsAssetsSamplingHeaderDAO headerDAO = new AmsAssetsSamplingHeaderDAO(user, orderHeader, conn);
				headerDAO.setDTOClassName(AmsAssetsSamplingHeaderDTO.class.getName());
				DTOSet orderHeaders = (DTOSet)headerDAO.getDataByForeignKey("batchId");

				req.setAttribute(SamplingWebAttributes.BATCH_DTO, dto);
				req.setAttribute(SamplingWebAttributes.ORDER_HEADERS, orderHeaders);
				forwardURL = SamplingURLs.BATCH_DETAIL_PAGE;
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
		} catch (Exception ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!forwardURL.equals("")){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
