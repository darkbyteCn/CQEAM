package com.sino.ams.spare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

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
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;

import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

import com.sino.ams.spare.dao.AmsItemTransHDAO;
import com.sino.ams.spare.dao.AmsItemTransLDAO;
import com.sino.ams.spare.dao.BjslApproveDAO;
import com.sino.ams.spare.dto.AmsItemTransHDTO;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.constant.SpareURLDefine;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.flow.bean.FlowAction;
import com.sino.flow.constant.FlowConstant;
import com.sino.flow.dto.FlowDTO;
import com.sino.ams.bean.ProcedureUtil;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */
public class BjslApproveServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String showMsg = "";
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;
		boolean forwardOpener = false;
        boolean operateResu = false;
        try {
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount(req);
			AmsItemTransHDTO dtoParameter = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(AmsItemTransHDTO.class.getName());
			dtoParameter = (AmsItemTransHDTO) req2DTO.getDTO(req);
			conn = getDBConnection(req);
			AmsItemTransHDAO itemTransHDAO = new AmsItemTransHDAO(user, dtoParameter, conn);
			BjslApproveDAO approveDAO = new BjslApproveDAO(user, dtoParameter, conn);
            if (!dtoParameter.getApp_dataID().equals("")) {
                dtoParameter.setTransId(dtoParameter.getApp_dataID());
            }
			if (action.equals(WebActionConstant.DETAIL_ACTION)) { //申领审批行明细
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", amsItemTransH);
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
				req.setAttribute("AIT_LINES", ldao.getLines4(amsItemTransH.getTransId()));
                amsItemTransH.setAttribute1(dtoParameter.getSf_task_attribute1());
				forwardURL = SpareURLDefine.SL_APPROVE_PAGE;
            } else if (action.equals(WebActionConstant.APPROVE_ACTION)) {
				req2DTO.setDTOClassName(AmsItemTransLDTO.class.getName());
				req2DTO.setIgnoreFields(AmsItemTransHDTO.class);
				DTOSet lineSet = req2DTO.getDTOSet(req);
				operateResu = approveDAO.approveOrder(lineSet, dtoParameter);
				message = approveDAO.getMessage();
                if (operateResu) {
                    showMsg = "保存数据成功";
                } else {
                    showMsg = "保存数据失败";
                }
			} else if (action.equals("ALLOT")) {
				String barcode1 = req.getParameter("barcode1");
				String transId = req.getParameter("transId");
				String lineId1 = req.getParameter("lineId1");
				int qty = Integer.parseInt(req.getParameter("sqty"));
				String org = req.getParameter("orgvalue");
				dtoParameter.setTransId(transId);
				AmsItemTransLDTO dto = new AmsItemTransLDTO();
				dto.setBarcode(barcode1);
				dto.setQuantity(qty);
				dto.setLineId(lineId1);
				req.setAttribute("LDTO", dto);
				req.setAttribute("trId", dtoParameter);
                String objectNo = approveDAO.hasObjectNo(transId, barcode1);
                RowSet row = approveDAO.produceWebData(barcode1, transId, lineId1, org, objectNo);
				req.setAttribute("ALLOTBARCODE", row);
				forwardURL = SpareURLDefine.SL_FP2_PAGE;
            } else if (action.equals("write")) {
				String lineId = req.getParameter("lineId1");
				String transId = req.getParameter("transId");
				String barcode = req.getParameter("barcode1");
				String orgId[] = req.getParameterValues("organizationId");
				String holdQty[] = req.getParameterValues("holdCount");
				String detailId[] = req.getParameterValues("detailId");
				String objectNo[] = req.getParameterValues("objectNo");
				approveDAO.writeDetails(lineId, barcode, transId, orgId, holdQty, detailId, objectNo);
                forwardURL = "/servlet/com.sino.ams.spare.servlet.BjslApproveServlet?act=" + WebActionConstant.DETAIL_ACTION + "&transId=" + transId;
				showMsg = "分配成功!";
                forwardOpener = true;
			} else if (action.equals(WebActionConstant.REJECT_ACTION)) {
                Request2DTO r2 = new Request2DTO();
				r2.setDTOClassName(AmsItemTransLDTO.class.getName());
				r2.setIgnoreFields(AmsItemTransHDTO.class);
				DTOSet lineSet = r2.getDTOSet(req);
//                flowDTO.setActivity(FlowConstant.FLOW_CODE_PREV);
//				operateResu = approveDAO.reject(flowDTO, lineSet);
//				message = approveDAO.getMessage();
//                forwardURL = "/servlet/com.sino.ams.spare.servlet.BjslServlet" + "?act=VIEW_ACTION&transId=" + dtoParameter.getTransId();
			} else if (action.equals("print")) {
				itemTransHDAO.setDTOClassName(AmsItemTransHDTO.class.getName());
				AmsItemTransHDTO amsItemTransH = (AmsItemTransHDTO) itemTransHDAO.getDataByPrimaryKey();
				if (amsItemTransH == null) {
					amsItemTransH = new AmsItemTransHDTO();
					message = getMessage(MsgKeyConstant.DATA_NOT_EXIST);
					message.setIsError(true);
				}
				req.setAttribute("AIT_HEADER", amsItemTransH);
				AmsItemTransLDAO ldao = new AmsItemTransLDAO(user, null, conn);
				req.setAttribute("AIT_LINES", ldao.getLines3(amsItemTransH.getTransId()));
				forwardURL = SpareURLDefine.SL_APPROVE_PAGE;
			} else if (action.equals(WebActionConstant.SUBMIT_ACTION)) {
				//将单据状态改为已完成,并生成调拨单和分配单
//				flowDTO.setActivity(FlowConstant.FLOW_CODE_NEXT);
//				boolean operateResult = approveDAO.submitOrder(flowDTO);
//				message = approveDAO.getMessage();
//				message.setIsError(!operateResult);
//				if (!operateResult) {
//                    forwardURL = "/servlet/com.sino.ams.spare.servlet.BjslApproveServlet?act=" + WebActionConstant.DETAIL_ACTION + "&transId=" + dtoParameter.getTransId();
//				} else {
//					showMsg = "备件调拨单已生成!";
//				}
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
		} catch (ContainerException e) {
			e.printLog();
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
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
		} catch (SQLException e) {
			Logger.logError(e);
			message = getMessage(MsgKeyConstant.SQL_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
            ServletForwarder forwarder = new ServletForwarder(req, res);
			if (!forwardURL.equals("")) {
                if(forwardOpener){
                    forwarder.forwardOpenerView(forwardURL, showMsg);
                } else {
                    forwarder.forwardView(forwardURL);
                }
            } else {
                forwarder.forwardOpenerView(forwardURL, showMsg);
            }
        }
	}
}
