package com.sino.ams.dzyh.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.dzyh.bean.LvecOptProducer;
import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.constant.LvecURLs;
import com.sino.ams.dzyh.constant.LvecWebAttributes;
import com.sino.ams.dzyh.dao.EamDhCheckBatchDAO;
import com.sino.ams.dzyh.dao.EamDhCheckHeaderDAO;
import com.sino.ams.dzyh.dto.EamDhCheckBatchDTO;
import com.sino.ams.dzyh.dto.EamDhCheckHeaderDTO;
import com.sino.ams.dzyh.model.EamDhCheckBatchModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
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


/**
 * <p>Title: EamDhCheckBatchServlet</p>
 * <p>Description:程序自动生成服务程序“EamDhCheckBatchServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class EamDhCheckBatchServlet extends BaseServlet {

/**
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
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EamDhCheckBatchDTO.class.getName());
			EamDhCheckBatchDTO dto = (EamDhCheckBatchDTO) req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			EamDhCheckBatchDAO batchDAO = new EamDhCheckBatchDAO(user, dto, conn);
			if (action.equals("")) {
				appendData(user, dto, conn);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = LvecURLs.BATCH_LIST_PAGE;
			} else if (action.equals(DzyhActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EamDhCheckBatchModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("BATCH_ID");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setDTOClassName(EamDhCheckBatchDTO.class.getName());
				pageDAO.setCalPattern(LINE_PATTERN);
				pageDAO.produceWebData();

				appendData(user, dto, conn);
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = LvecURLs.BATCH_LIST_PAGE;
			} else if (action.equals(DzyhActionConstant.EXPORT_ACTION)) {
				File file = batchDAO.getExportFile();
				WebFileDownload fileDown = new WebFileDownload(req, res);
				fileDown.setFilePath(file.getAbsolutePath());
				fileDown.download();
				file.delete();
			} else if (action.equals(DzyhActionConstant.NEW_ACTION)) {
				EamDhCheckBatchDTO orderBatch = (EamDhCheckBatchDTO) req.getAttribute(LvecWebAttributes.BATCH_DTO);
				DTOSet orders = (DTOSet)req.getAttribute(LvecWebAttributes.ORDER_HEADERS);
				if (orderBatch == null) {
					orderBatch = dto; //表示没有因失败而保持的数据，则产生默认的对象数据，数据由com.sino.ams.lvec.dto.EamDhCheckBatchDTO的构造函数确定
					orders = new DTOSet();
				}
				orderBatch.setAct(action);
				appendData(user, orderBatch, conn);
				req.setAttribute(LvecWebAttributes.BATCH_DTO, orderBatch);
				req.setAttribute(LvecWebAttributes.ORDER_HEADERS, orders);

				forwardURL = LvecURLs.BATCH_DATA_PAGE;
			} else if (action.equals(DzyhActionConstant.DETAIL_ACTION)) {
				EamDhCheckBatchDTO orderBatch = (EamDhCheckBatchDTO) req.getAttribute(LvecWebAttributes.BATCH_DTO);
				DTOSet orders = (DTOSet)req.getAttribute(LvecWebAttributes.ORDER_HEADERS);
				if (orderBatch == null) {
					batchDAO.setDTOClassName(EamDhCheckBatchDTO.class.getName());
					orderBatch = (EamDhCheckBatchDTO) batchDAO.getDataByPrimaryKey();
				}
				if (orderBatch == null) {
					orderBatch = dto;
					orders = new DTOSet();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				} else {
					orderBatch.setAct(action);
					orderBatch.setOrderType(dto.getOrderType());
					EamDhCheckHeaderDTO orderHeader = new EamDhCheckHeaderDTO();
					orderHeader.setBatchId(orderBatch.getBatchId());
					orderHeader.setOrderType(dto.getOrderType());
					EamDhCheckHeaderDAO headerDAO = new EamDhCheckHeaderDAO(user, orderHeader, conn);
					headerDAO.setDTOClassName(EamDhCheckHeaderDTO.class.getName());
					headerDAO.setCalPattern(LINE_PATTERN);
					orders = (DTOSet)headerDAO.getDataByForeignKey("batchId");
				}
				appendData(user, orderBatch, conn);
				req.setAttribute(LvecWebAttributes.BATCH_DTO, orderBatch);
				req.setAttribute(LvecWebAttributes.ORDER_HEADERS, orders);
				forwardURL = LvecURLs.BATCH_DATA_PAGE;
			} else if (action.equals(DzyhActionConstant.SAVE_ACTION)) {
				req2DTO.setDTOClassName(EamDhCheckHeaderDTO.class.getName());
				List exeFields = new ArrayList();
				exeFields.add("orderType");
				exeFields.add("checkTools");
				req2DTO.setIgnoreFields(EamDhCheckBatchDTO.class, exeFields);
				DTOSet orders = req2DTO.getDTOSet(req);
				boolean operateResult = batchDAO.saveOrders(orders);
				message = batchDAO.getMessage();
				dto = (EamDhCheckBatchDTO) batchDAO.getDTOParameter();
				String batchId = dto.getBatchId();
				if (operateResult) {
					forwardURL = LvecURLs.BATCH_SERVLET;
					forwardURL += "?act=" + DzyhActionConstant.DETAIL_ACTION;
					forwardURL += "&orderType=" + dto.getOrderType();
					forwardURL += "&batchId=" + batchId;
				} else {
					req.setAttribute(LvecWebAttributes.BATCH_DTO, dto);
					req.setAttribute(LvecWebAttributes.ORDER_HEADERS, orders);
					if (batchId.equals("")) {
						forwardURL = LvecURLs.BATCH_SERVLET;
						forwardURL += "?act=" + DzyhActionConstant.NEW_ACTION;
						forwardURL += "&orderType=" + dto.getOrderType();
					} else {
						forwardURL = LvecURLs.BATCH_SERVLET;
						forwardURL += "?act=" + DzyhActionConstant.DETAIL_ACTION;
						forwardURL += "&orderType=" + dto.getOrderType();
						forwardURL += "&batchId=" + batchId;
					}
				}
			} else if (action.equals(DzyhActionConstant.DISTRI_ORDER)) {
				req2DTO.setDTOClassName(EamDhCheckHeaderDTO.class.getName());
				List exeFields = new ArrayList();
				exeFields.add("orderType");
				exeFields.add("checkTools");
				req2DTO.setIgnoreFields(EamDhCheckBatchDTO.class, exeFields);
				DTOSet orders = req2DTO.getDTOSet(req);
				dto.setBatchStatus(LvecDicts.ORDER_STS1_DISTRIBUTED);
				boolean operateResult = batchDAO.saveOrders(orders);
				message = batchDAO.getMessage();
				dto = (EamDhCheckBatchDTO) batchDAO.getDTOParameter();
				String batchId = dto.getBatchId();
				if (operateResult) {
					forwardURL = LvecURLs.BATCH_SERVLET;
					forwardURL += "?act=" + DzyhActionConstant.DETAIL_ACTION;
					forwardURL += "&orderType=" + dto.getOrderType();
					forwardURL += "&batchId=" + batchId;
				} else {
					req.setAttribute(QueryConstant.QUERY_DTO, dto);
					req.setAttribute(LvecWebAttributes.ORDER_HEADERS, orders);
					if (batchId.equals("")) {
						forwardURL = LvecURLs.BATCH_SERVLET;
						forwardURL += "?act=" + DzyhActionConstant.NEW_ACTION;
						forwardURL += "&orderType=" + dto.getOrderType();
					} else {
						forwardURL = LvecURLs.BATCH_SERVLET;
						forwardURL += "?act=" + DzyhActionConstant.DETAIL_ACTION;
						forwardURL += "&orderType=" + dto.getOrderType();
						forwardURL += "&batchId=" + batchId;
					}
				}
			} else if (action.equals(DzyhActionConstant.CANCEL_ACTION)) {
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String[] batchIds = parser.getParameterValues("batchId");
				boolean singleCancel = parser.contains("barcode");
				batchDAO.cancelOrderBatchs(batchIds, singleCancel);
				message = batchDAO.getMessage();
				if (singleCancel) {
					forwardURL = LvecURLs.BATCH_SERVLET;
					forwardURL += "?act=" + DzyhActionConstant.DETAIL_ACTION;
					forwardURL += "&batchId=" + dto.getBatchId();
				} else {
					forwardURL = LvecURLs.BATCH_SERVLET;
					forwardURL += "?act=" + DzyhActionConstant.QUERY_ACTION;
				}
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
		} catch (StrException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataTransException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (WebFileDownException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			if (!forwardURL.equals("")) {
				ServletForwarder forwarder = new ServletForwarder(req, res);
				forwarder.forwardView(forwardURL);
			}
		}
	}

	/**
	 * 功能：补充下拉列表框
	 * @param user SfUserDTO
	 * @param dto EamCheckTaskDTO
	 * @param conn Connection
	 * @throws QueryException
	 */
	private void appendData(SfUserDTO user, EamDhCheckBatchDTO dto, Connection conn) throws QueryException {
		LvecOptProducer optPrd = new LvecOptProducer(user, conn);
		String opt = "";
		String act = dto.getAct();
		String orderType = dto.getOrderType();
		if (act.equals("") || act.equals(DzyhActionConstant.QUERY_ACTION)) {
			if(!orderType.equals(LvecDicts.ORD_TYPE1_YQYB)){
				dto.setCheckType(LvecDicts.CHK_TYPE1_DZYH);
			} else {
				dto.setCheckType(LvecDicts.CHK_TYPE1_YQYB);
			}
			opt = optPrd.getDictOption("CHECK_TYPE", dto.getCheckType());
			dto.setCheckTypeOpt(opt);
			opt = optPrd.getBatchStatusOpt(dto.getTaskStatus());
			dto.setBatchStatusOpt(opt);
		} else if (act.equals(DzyhActionConstant.NEW_ACTION)) {
			if(!orderType.equals(LvecDicts.ORD_TYPE1_YQYB)){
				dto.setCheckType(LvecDicts.CHK_TYPE1_DZYH);
				dto.setCheckTypeValue(LvecDicts.CHK_TYPE2_DZYH);
				dto.setObjectCategory(LvecDicts.LOCATION_CATEGORY_DZYH);
			} else {
				dto.setCheckType(LvecDicts.CHK_TYPE1_YQYB);
				dto.setCheckTypeValue(LvecDicts.CHK_TYPE2_YQYB);
				dto.setObjectCategory(LvecDicts.LOCATION_CATEGORY_YQYB);
			}
			dto.setOrganizationId(user.getOrganizationId());
			dto.setOrganizationName(user.getCompany());
			dto.setBatchStatus(LvecDicts.ORDER_STS1_NEW);
			dto.setBatchStatusValue(LvecDicts.ORDER_STS2_NEW);
			dto.setBatchNo(LvecWebAttributes.ORDER_NO_AUTO_PRODUCE);
			dto.setCheckDeptName(user.getDeptName());
			dto.setCheckDept(StrUtil.strToInt(user.getDeptCode()));
			dto.setCreatedUser(user.getUsername());
			dto.setCreatedBy(user.getUserId());
			dto.setCurrCalendar("creationDate");

			opt = optPrd.getDictOption("CHECK_TOOLS", dto.getCheckTools());
			dto.setCheckToolsOpt(opt);
		} else if (act.equals(DzyhActionConstant.DETAIL_ACTION)) {
			opt = optPrd.getDictOption("CHECK_TOOLS", dto.getCheckTools());
			dto.setCheckToolsOpt(opt);
		}
		dto.setCalPattern(LINE_PATTERN);
	}
}
