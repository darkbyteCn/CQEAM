package com.sino.ams.spare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.*;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.spare.dao.AmsItemTransLDAO;
import com.sino.ams.spare.dao.BjfxsqDAO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.model.BjfxsqModel;
import com.sino.ams.spare.constant.SpareWebAction;
import com.sino.ams.spare.constant.SpareURLDefine;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * Created by IntelliJ IDEA.
 * User: srf
 * Date: 2008-3-18
 * Time: 21:20:32
 * To change this template use File | Settings | File Templates.
 */
public class BjfxsqServlet extends BaseServlet {

/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String showMsg = "";
		String action = req.getParameter("act");
		String transType = StrUtil.nullToString(req.getParameter("transType"));
		action = StrUtil.nullToString(action);
		Connection conn = null;
		try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsItemTransHDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
			dtoParameter = (AmsItemTransHDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			BjfxsqDAO itemTransHDAO = new BjfxsqDAO(user, dtoParameter, conn);
			OptionProducer optProducer = new OptionProducer(user, conn);
            String vendorOption = optProducer.getSpareVendorOption(dtoParameter.getVendorId());
            req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
            if (action.equals("")) {
				String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
				req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
				String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
				req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
				forwardURL = "/spare/bjOrderQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new BjfxsqModel(user, dtoParameter);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.produceWebData();
				String orderStatus = optProducer.getDictOption("ORDER_STATUS", dtoParameter.getTransStatus());
				req.setAttribute(WebAttrConstant.TRANS_STATUS, orderStatus);
				String invOption = optProducer.getInvOption(dtoParameter.getFromObjectNo());
				req.setAttribute(WebAttrConstant.INV_OPTION, invOption);
				forwardURL = "/spare/bjOrderQuery.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) req.getAttribute("AIT_HEADER");
				if (orderHeader == null) {
					orderHeader = dtoParameter;
					orderHeader.setTransNo(WebAttrConstant.ORDER_NO_AUTO_PRODUCE);
					orderHeader.setCreatedBy(user.getUserId());
					orderHeader.setCreatedUser(user.getUsername());
					orderHeader.setCreationDate(CalendarUtil.getCurrDate());
					orderHeader.setTransStatusName("未完成");
					orderHeader.setTransType(DictConstant.FXSQ);
					orderHeader.setFromOrganizationName(user.getCompany());
					orderHeader.setFromOrganizationId(user.getOrganizationId());
				}
				req.setAttribute("AIT_HEADER", orderHeader);
				forwardURL = "/spare/bjfxsqCreate.jsp";
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (orderHeader == null) {
					orderHeader = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
                vendorOption = optProducer.getSpareVendorOption(orderHeader.getVendorId());
                req.setAttribute(WebAttrConstant.SPARE_VENDOR_OPTION, vendorOption);
                req.setAttribute("AIT_HEADER", orderHeader);
				//查询行信息
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
				req.setAttribute("AIT_LINES", ldao.getLines2(orderHeader.getTransId()));
//				forwardURL = "/spare/bjfxsqOrderInfo.jsp";
				forwardURL = "/spare/bjfxsqCreate.jsp";
			} else if (action.equals(WebActionConstant.SAVE_ACTION)) {
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsItemTransLDTO.class.getName());
				r2.setIgnoreFields(AmsItemTransHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
				flowDTO.setSessionUserId(user.getUserId());
				flowDTO.setSessionUserName(user.getUsername());
				boolean operateResult = itemTransHDAO.saveOrder(lineSet, flowDTO);
				message = itemTransHDAO.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					forwardURL = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" +
								 WebActionConstant.DETAIL_ACTION
								 + "&transId=" + dtoParameter.getTransId();
				} else {
					req.setAttribute("AIT_HEADER", dtoParameter);
					forwardURL = "/spare/bjfxsqCreate.jsp";
				}
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsItemTransLDTO.class.getName());
				r2.setIgnoreFields(AmsItemTransHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
				FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
				flowDTO.setSessionUserId(user.getUserId());
				flowDTO.setSessionUserName(user.getUsername());
				boolean operateResult = itemTransHDAO.submitOrder(lineSet, flowDTO);
				message = itemTransHDAO.getMessage();
				message.setIsError(!operateResult);
				if (operateResult) {
					forwardURL = "/servlet/com.sino.flow.servlet.InboxServlet";
					showMsg = "单据" + dtoParameter.getTransNo() + "已提交!";
				}
			} else if (action.equals("show")) {

			} else if (action.equals("approve")) { //收件箱行明细
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO orderHeader = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (orderHeader == null) {
					orderHeader = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", orderHeader);
				//查询行信息
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
				req.setAttribute("AIT_LINES", ldao.getLines2(orderHeader.getTransId()));
				forwardURL = "/spare/bjfxsqApprove.jsp";
			} else if (action.equals(WebActionConstant.APPROVE_ACTION)) {
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsItemTransLDTO.class.getName());
				List ignorList = new ArrayList();
				ignorList.add("transId");
				ignorList.add("act");
				r2.setIgnoreFields(ignorList);
				DTOSet lineSet = r2.getDTOSet(req);
				FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
				flowDTO.setSessionUserId(user.getUserId());
				flowDTO.setSessionUserName(user.getUsername());
				flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
				flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_AGREE);
				String sectionRight = req.getParameter("sectionRight");
				String operateResult = itemTransHDAO.approveOrder(lineSet, flowDTO, sectionRight);
				message = itemTransHDAO.getMessage();
				if (operateResult.equals("end")) {
					showMsg = "调拨单已生成!";
				} else {
					showMsg = "单据已通过审批!";
				}
			} else if (action.equals("SCANED")) { //提交扫描
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsItemTransLDTO.class.getName());
				List ignorList = new ArrayList();
				ignorList.add("transId");
//                ignorList.add("quantity");
				ignorList.add("act");
				r2.setIgnoreFields(ignorList);
				DTOSet lineSet = r2.getDTOSet(req);
				FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
				flowDTO.setSessionUserId(user.getUserId());
				flowDTO.setSessionUserName(user.getUsername());
				flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
				flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_AGREE);
				String sectionRight = req.getParameter("sectionRight");
				boolean operateResult = itemTransHDAO.approveScaning(lineSet, flowDTO, sectionRight);
				message = itemTransHDAO.getMessage();
				showMsg = "单据已通过扫描!";
			} else if (action.equals("ALLOT")) { //审批行明细
				String barcode1 = req.getParameter("barcode1");
				String transId = req.getParameter("transId");
				String lineId1 = req.getParameter("lineId1");
				String qty = req.getParameter("sqty");
				String org = req.getParameter("orgvalue");
				dtoParameter.setTransId(transId);
				AmsItemTransLDTO dto = new AmsItemTransLDTO();
				dto.setQuantity(Integer.parseInt(qty));
				dto.setBarcode(barcode1);
				dto.setLineId(lineId1);
				req.setAttribute("LDTO", dto);
				req.setAttribute("trId", dtoParameter);
                String objectNo = itemTransHDAO.hasObjectNo(transId, barcode1);
                RowSet row = itemTransHDAO.produceWebData(barcode1, transId, lineId1, org, objectNo);
				req.setAttribute("ALLOTBARCODE", row);
				forwardURL = "/spare/bjfxsqfp.jsp";
			} else if (action.equals("SEARCH")) {
				String transId = req.getParameter("transId");
				RowSet row = itemTransHDAO.getBanchNo(transId);
				req.setAttribute("SERIAL_NO", row);
				forwardURL = "/spare/bjfxsqSerialNo.jsp";
			} else if (action.equals("write")) {
				String lineId = req.getParameter("lineId1");
				String transId = req.getParameter("transId");
				String barcode = req.getParameter("barcode1");
				String orgId[] = req.getParameterValues("organizationId");
				String holdQty[] = req.getParameterValues("holdCount");
				String detailId[] = req.getParameterValues("detailId");
                String objectNo[] = req.getParameterValues("objectNo");
                itemTransHDAO.writeDetails(lineId, barcode, transId, orgId, holdQty, detailId, objectNo);
                showMsg = "分配成功!";
				forwardURL = "/servlet/com.sino.ams.spare.servlet.BjfxsqServlet?act=" + "approve"
							 + "&transId=" + transId;
			} else if (action.equals(WebActionConstant.REJECT_ACTION)) {
                Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsItemTransLDTO.class.getName());
				r2.setIgnoreFields(AmsItemTransHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
				flowDTO.setSessionUserId(user.getUserId());
				flowDTO.setSessionUserName(user.getUsername());
				flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
				String content = StrUtil.nullToString(req.getParameter("approveContent"));
				flowDTO.setApproveContent(content);
				itemTransHDAO.reject(dtoParameter, flowDTO, lineSet);
				showMsg = "备件返修申请单已退回!";
			} else if (action.equals(WebActionConstant.CANCEL_ACTION)) {
                req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
                req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
                DTOSet lineSet = req2DTO.getDTOSet(req);
                FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
                flowDTO.setSessionUserId(user.getUserId());
                flowDTO.setSessionUserName(user.getUsername());
                boolean operateResult = itemTransHDAO.cancelslOrder(lineSet, flowDTO);
                showMsg = "单据已撤销!";
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				itemTransHDAO.deleteData();
				message = itemTransHDAO.getMessage();
				forwardURL = "可再次执行该Servlet的QUERY_ACTION，请根据实际情况确定";
			} else if (action.equals("DO_EQUALS")) { //判断扫描数量和申请数量是否相等
				boolean equals = itemTransHDAO.do_checkQty(dtoParameter.getTransId());
				message = itemTransHDAO.getMessage();
				PrintWriter out = res.getWriter();
				if (equals) {
					out.print("Y");
				}
				out.flush();
				out.close();
			} else if (action.equals("firstN")) { //第一个节点提交
				Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsItemTransLDTO.class.getName());
				List ignorList = new ArrayList();
				ignorList.add("transId");
				ignorList.add("act");
				r2.setIgnoreFields(ignorList);
				DTOSet lineSet = r2.getDTOSet(req);
				FlowDTO flowDTO = FlowAction.getDTOFromReq(req);
				flowDTO.setSessionUserId(user.getUserId());
				flowDTO.setSessionUserName(user.getUsername());
				flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
				flowDTO.setApproveContent(FlowConstant.APPROVE_CONTENT_AGREE);
				String sectionRight = req.getParameter("sectionRight");
				String operateResult = itemTransHDAO.firstN(lineSet, flowDTO, sectionRight);
				message = itemTransHDAO.getMessage();
				if (operateResult.equals("end")) {
					showMsg = "调拨单已生成!";
				} else {
					showMsg = "单据已通过审批!";
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
		} catch (CalendarException e) {
			e.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLException e) {
			Logger.logError(e);
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
		} catch (ContainerException e) {
			Logger.logError(e);
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if (!action.equals("DO_EQUALS")) {
				if (showMsg.equals("")) {
					forwarder.forwardView(forwardURL);
				} else {
					forwarder.forwardOpenerView(forwardURL, showMsg);
				}
			}
			//根据实际情况修改页面跳转代码。
		}
	}
}
