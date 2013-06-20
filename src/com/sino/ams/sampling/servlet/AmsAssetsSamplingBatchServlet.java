package com.sino.ams.sampling.servlet;


import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.sampling.constant.SamplingActions;
import com.sino.ams.sampling.constant.SamplingURLs;
import com.sino.ams.sampling.constant.SamplingWebAttributes;
import com.sino.ams.sampling.dao.AmsAssetsSamplingBatchDAO;
import com.sino.ams.sampling.dao.AmsAssetsSamplingTaskDAO;
import com.sino.ams.sampling.dto.AmsAssetsSamplingBatchDTO;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
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
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;


/**
 * <p>Title: AmsAssetsSamplingBatchServlet</p>
 * <p>Description:程序自动生成服务程序“AmsAssetsSamplingBatchServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsAssetsSamplingBatchServlet extends BaseServlet {

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
        String msg="";
		boolean forwardOpener = false;
		try {
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsAssetsSamplingBatchDTO.class.getName());
			AmsAssetsSamplingBatchDTO dto = (AmsAssetsSamplingBatchDTO)req2DTO.getDTO(req);
			String action = dto.getAct();
			conn = getDBConnection(req);
			AmsAssetsSamplingBatchDAO batchDAO = new AmsAssetsSamplingBatchDAO(user, dto, conn);
			if (action.equals(SamplingActions.NEW_ACTION)) {//头数据为工单批加任务信息
				AmsAssetsSamplingBatchDTO batch = (AmsAssetsSamplingBatchDTO)req.getAttribute(SamplingWebAttributes.TASK_DTO);
				DTOSet orders = (DTOSet)req.getAttribute(SamplingWebAttributes.ORDER_HEADERS);
				if(batch == null){
					if(dto.getBatchNo().equals("")){
						AmsAssetsSamplingTaskDAO taskDAO = new AmsAssetsSamplingTaskDAO(user, dto, conn);
						batch = taskDAO.getDataByTaskNo();
						batch.setBatchNo(SamplingWebAttributes.ORDER_AUTO_PROD);
					} else {
						batch = batchDAO.getDataByBatchNo();
						batchDAO.setDTOParameter(batch);
						orders = batchDAO.getTempSavedOrders();
					}
					batch.setCreatedOu(dto.getCreatedOu());
					batch.setCreatedOuName(dto.getCreatedOuName());
					batch.setSampledOu(dto.getSampledOu());
					batch.setSampledOuName(dto.getSampledOuName());
					appendBatchRemark(user, batch);
				}
				req.setAttribute(SamplingWebAttributes.BATCH_DTO, batch);
				req.setAttribute(SamplingWebAttributes.ORDER_HEADERS, orders);
				forwardURL = SamplingURLs.ORDER_CREATE_PAGE;
			} else if (action.equals(SamplingActions.DETAIL_ACTION)) {//头数据为工单批加任务信息
				batchDAO.setDTOClassName(AmsAssetsSamplingBatchDTO.class.getName());
				AmsAssetsSamplingBatchDTO batch = (AmsAssetsSamplingBatchDTO)batchDAO.getDataByPrimaryKey();
				DTOSet orders = batchDAO.getTempSavedOrders();
				req.setAttribute(SamplingWebAttributes.BATCH_DTO, batch);
				req.setAttribute(SamplingWebAttributes.ORDER_HEADERS, orders);
				forwardURL = SamplingURLs.ORDER_DETAIL_PAGE;
			} else if (action.equals(SamplingActions.SAVE_ACTION)) {
				req2DTO.setDTOClassName(AmsAssetsSamplingHeaderDTO.class.getName());
				req2DTO.setIgnoreFields(AmsAssetsSamplingBatchDTO.class);
				DTOSet orderHeaders = req2DTO.getDTOSet(req);
				batchDAO.saveOrders(orderHeaders);
				dto = (AmsAssetsSamplingBatchDTO)batchDAO.getDTOParameter();
				message = batchDAO.getMessage();
				if(!dto.getBatchId().equals("")){
					forwardURL = SamplingURLs.BATCH_SERVLET;
					forwardURL += "?act=" + SamplingActions.DETAIL_ACTION;
					forwardURL += "&batchId=" + dto.getBatchId();
				} else {
					req.setAttribute(SamplingWebAttributes.TASK_DTO, dto);
					req.setAttribute(SamplingWebAttributes.ORDER_HEADERS, orderHeaders);
					forwardURL = SamplingURLs.BATCH_SERVLET;
					forwardURL += "?act=" + SamplingActions.NEW_ACTION;
				}
				msg = "暂存成功";
			} else if (action.equals(SamplingActions.DISTRIBUTE_ORDER)) {//下发工单
				req2DTO.setDTOClassName(AmsAssetsSamplingHeaderDTO.class.getName());
				req2DTO.setIgnoreFields(AmsAssetsSamplingBatchDTO.class);
				DTOSet orderHeaders = req2DTO.getDTOSet(req);
				batchDAO.saveOrders(orderHeaders);
				dto = (AmsAssetsSamplingBatchDTO)batchDAO.getDTOParameter();
				message = batchDAO.getMessage();
                msg="下发成功";
//				forwardURL = SamplingURLs.BATCH_SERVLET;
//				forwardURL += "?act=" + SamplingActions.DETAIL_ACTION;
//				forwardURL += "&batchId=" + dto.getBatchId();
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
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if(forwardOpener){
				forwarder.forwardOpenerView(forwardURL, message.getMessageValue());
			} else {

                if(msg.equals("")){
                	forwarder.forwardView(forwardURL);
                }else{
                	forwarder.forwardOpenerView(forwardURL, msg);
                }

			}
		}
	}

	/**
	 * 功能：为工单批生成备注信息
	 * @param user SfUserDTO
	 * @param batch AmsAssetsSamplingBatchDTO
	 */
	private void appendBatchRemark(SfUserDTO user, AmsAssetsSamplingBatchDTO batch) {
		String createdOuName = batch.getCreatedOuName();
		String sampledOuName = batch.getSampledOuName();
		String batchRemark = "";
		if (batch.getBatchRemark().equals("")) {
			if (user.isProvinceUser()) {
				if (sampledOuName.equals(createdOuName)) {
					batchRemark = createdOuName
								  + " 在抽查任务 “"
								  + batch.getTaskName()
								  + "” 下创建自抽查工单";
				} else {
					batchRemark = createdOuName
								  + " 为 "
								  + sampledOuName
								  + " 在抽查任务 “"
								  + batch.getTaskName()
								  + "” 下创建抽查工单";
				}
			} else {
				if (sampledOuName.equals(createdOuName)) {
					batchRemark = sampledOuName
								  + " 在本公司抽查任务 “"
								  + batch.getTaskName()
								  + "” 下创建自抽查工单";
				} else {
					batchRemark = sampledOuName
								  + " 在 "
								  + createdOuName
								  + " 的抽查任务 “"
								  + batch.getTaskName()
								  + "” 下创建自抽查工单";
				}
			}
			batch.setBatchRemark(batchRemark);
		}
	}
}
