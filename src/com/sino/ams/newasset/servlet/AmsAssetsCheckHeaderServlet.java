package com.sino.ams.newasset.servlet;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.newasset.bean.AssetsOptProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.constant.AssetsURLList;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.ams.newasset.dao.AmsAssetsCheckHeaderDAO;
import com.sino.ams.newasset.dao.AmsAssetsCheckLineDAO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckLineDTO;
import com.sino.ams.newasset.model.AmsAssetsCheckHeaderModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;


/**
 * <p>Title: AmsAssetsCheckHeaderServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsCheckHeaderServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsCheckHeaderServlet extends BaseServlet {

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
			req2DTO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
			AmsAssetsCheckHeaderDTO dtoPara = (AmsAssetsCheckHeaderDTO) req2DTO.getDTO(req);
			String action = dtoPara.getAct();
			conn = getDBConnection(req);
			AmsAssetsCheckHeaderDAO orderDAO = new AmsAssetsCheckHeaderDAO(user,dtoPara, conn);
			AssetsOptProducer optProducer = new AssetsOptProducer(user,conn);
			String orderStatus = optProducer.getDictOption(AssetsDictConstant.CHKORDER_STATUS, dtoPara.getOrderStatus());
			req.setAttribute(AssetsWebAttributes.ORDER_STATUS_OPT, orderStatus);
			//String groupOPtion = optProducer.getUserGroups(dtoPara.getGroupId());
			//optProducer.getu
			String groupOPtion = optProducer.getAllGroup(Integer.toString(dtoPara.getGroupId()));
			dtoPara.setGroupOpt(groupOPtion);
			if (action.equals("")) {
				req.setAttribute(QueryConstant.QUERY_DTO, dtoPara);
				forwardURL = AssetsURLList.HEADER_QRY_PAGE;
			} else if (action.equals(AssetsActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new AmsAssetsCheckHeaderModel(user, dtoPara);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("HEADER_ID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dtoPara);
				forwardURL = AssetsURLList.HEADER_QRY_PAGE;
			} else if (action.equals(AssetsActionConstant.EXPORT_ACTION)) {
				File file = orderDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(AssetsActionConstant.DETAIL_ACTION)) {
				orderDAO.setDTOClassName(AmsAssetsCheckHeaderDTO.class.getName());
				orderDAO.setCalPattern(LINE_PATTERN);
				AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)orderDAO.getDataByPrimaryKey();
				if (dto == null) {
					message = getMessage(AssetsMessageKeys.DATA_NOT_EXIST);
					message.setIsError(true);
					forwardURL = MessageConstant.MSG_PRC_SERVLET;
				} else {
					orderStatus = dto.getOrderStatus();
					if (orderStatus.equals(AssetsDictConstant.CHK_STATUS_ARCHIEVED)) {
						forwardURL = AssetsURLList.ARCHIVE_ORDER_SERVLET;
						forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
						forwardURL += "&headerId=" + dto.getHeaderId();
					} else if (orderStatus.equals(AssetsDictConstant.CHK_STATUS_UPLOADED)) {
						forwardURL = AssetsURLList.ARCHIVE_ORDER_SERVLET;
						forwardURL += "?act=" + AssetsActionConstant.DETAIL_ACTION;
						forwardURL += "&headerId=" + dto.getHeaderId();
						forwardURL += "&srcPage=" + AssetsActionConstant.QUERY_ACTION;
					} else {
						AmsAssetsCheckLineDTO checkLine = new AmsAssetsCheckLineDTO();
						checkLine.setHeaderId(dto.getHeaderId());
						AmsAssetsCheckLineDAO lineDAO = new AmsAssetsCheckLineDAO(user, checkLine, conn);
						lineDAO.setCalPattern(LINE_PATTERN);
						lineDAO.setDTOClassName(AmsAssetsCheckLineDTO.class.getName());
						DTOSet checkLines = (DTOSet) lineDAO.getDataByForeignKey("headerId");
						req.setAttribute(AssetsWebAttributes.CHECK_LINE_DATAS, checkLines);
						req.setAttribute(AssetsWebAttributes.CHECK_HEADER_DATA, dto);
						forwardURL = AssetsURLList.HEADER_EDIT_PAGE;
					}
				}
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
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch(Exception e){
			Logger.logError(e);
			message = getMessage(AssetsMessageKeys.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		}finally {
			closeDBConnection(conn);
			setHandleMessage(req, message);
			if(!StrUtil.isEmpty(forwardURL)){
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}
}
