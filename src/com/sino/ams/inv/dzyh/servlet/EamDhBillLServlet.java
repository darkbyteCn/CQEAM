package com.sino.ams.inv.dzyh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.inv.common.bean.InvOptionProducer;
import com.sino.ams.inv.dzyh.dao.EamDhBillLDAO;
import com.sino.ams.inv.dzyh.dao.EamDhChgLogDAO;
import com.sino.ams.inv.dzyh.dto.EamDhBillLDTO;
import com.sino.ams.inv.dzyh.model.EamDhBillLModel;
import com.sino.ams.inv.storeman.base.constant.web.WebInvActionConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: EamDhBillLServlet</p>
 * <p>Description:程序自动生成服务程序“EamDhBillLServlet”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 于士博
 * @version 1.0
 */
public class EamDhBillLServlet extends BaseServlet {

	/**
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		boolean isOpener = false;
		String showMsg = "";
		try{
			SfUserDTO user = (SfUserDTO)SessionUtil.getUserAccount(req);
			Request2DTO req2dto = new Request2DTO();
			req2dto.setDTOClassName(EamDhBillLDTO.class.getName());
			EamDhBillLDTO dto = (EamDhBillLDTO)req2dto.getDTO(req);
			
			String action = dto.getAct(); //保证action不为空的写法

			conn = getDBConnection(req);

			EamDhBillLDAO eamDhBillLDAO = new EamDhBillLDAO(user, dto, conn);
			EamDhChgLogDAO eamDhChgLogDAO = new EamDhChgLogDAO(user, dto, conn);
			InvOptionProducer optProducer = new InvOptionProducer(user, conn);

			if(action.equals("")){
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/inv/dzyh/dzyhBillLQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				String workorderObjectCode = req.getParameter("workorderObjectCode");
				String workorderObjectNo = req.getParameter("workorderObjectNo");
				BaseSQLProducer sqlProducer = new EamDhBillLModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				req.setAttribute("workorderObjectCode", workorderObjectCode);
				req.setAttribute("workorderObjectNo", workorderObjectNo);
				forwardURL = "/inv/dzyh/dzyhBillLQuery.jsp";
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {	
				eamDhBillLDAO.createData();
				eamDhChgLogDAO.createData();
				forwardURL = "/servlet/" + getClass().getName();
				forwardURL += "?act=" + WebActionConstant.QUERY_ACTION;
				isOpener = true;
				//forwardURL = "/inv/storeman/storemanQuery.jsp";
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				String[] systemids = req.getParameterValues("systemid");
				String[] catalogValueId2s = req.getParameterValues("catalogValueId2");
				String[] barcodes = req.getParameterValues("barcode");
				String[] deptCodes = req.getParameterValues("deptCode");
				String[] employeeIds = req.getParameterValues("employeeId");
				String[] addressIds = req.getParameterValues("addressId");
				String[] maintainUsers = req.getParameterValues("maintainUser");
				String[] itemCodes = req.getParameterValues("itemCode");
				String[] itemCategorys = req.getParameterValues("itemCategory");
				String[] itemCategory2s = req.getParameterValues("itemCategory2");
				String workorderObjectCode = req.getParameter("workorderObjectCode");
				
				//生成单据号
				String transactionNo = eamDhBillLDAO.createBillNo();
				//插入头表信息并返回trans_id值
				int trans_id = eamDhBillLDAO.createTransId(transactionNo, workorderObjectCode);
				
				if(trans_id > 0) {
					for(int i=0; i<systemids.length; i++){
						eamDhBillLDAO
								.updateDatas(systemids[i], catalogValueId2s[i],
										barcodes[i], deptCodes[i],
										employeeIds[i], addressIds[i],
										maintainUsers[i],trans_id,
										itemCodes[i], itemCategorys[i], 
										itemCategory2s[i]);
					}
				}
				
				forwardURL = "/servlet/" + getClass().getName();
				forwardURL += "?act=" + WebActionConstant.QUERY_ACTION;
//				isOpener = true;
			} else if (action.equals("UPDATEDEPT_ACTION")) {
				PrintWriter out = res.getWriter();
				
				String catalogValueId = req.getParameter("catalogValueId");
				String barcode = req.getParameter("barcode");
				String oldDept = req.getParameter("oldDept");
				String newDept = req.getParameter("newDept");
						
				eamDhBillLDAO.updateResponsibilityDeptData(barcode, newDept);
				eamDhChgLogDAO.createData(barcode, catalogValueId, oldDept, newDept, "", "", "", "", "", "");
				
				res.setContentType("text/html;charset=GBK");
				out.print("正在处理中...");
				out.flush();
				out.close();
				
				//forwardURL = "/servlet/" + getClass().getName();
				//forwardURL += "?act=" + WebActionConstant.QUERY_ACTION;
				isOpener = true;
			} else if (action.equals(WebInvActionConstant.NEW_ACTION_USERS)) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				String[] gets = req.getParameterValues("storemanIds");
				req.setAttribute("gets", gets);
				forwardURL = "/inv/storeman/updateStoremans.jsp";
			} else if (action.equals(WebInvActionConstant.CHECK_ACTION)) {
				String workorderObjectNo = req.getParameter("workorderObjectNo");
				PrintWriter out = res.getWriter();
//				boolean codeExist = eamDhBillLDAO.getUniqueKeyDataDAO(workorderObjectNo);
//				if(codeExist){
//					//res.getWriter().println("CODE_EXIST");
//					out.print(WebAttrConstant.CODE_EXIST);
//				} else {
//					//res.getWriter().println("CODE_NOT_EXIST");
//					out.print(WebAttrConstant.CODE_NOT_EXIST);
//				}
//				//req.setAttribute(QueryConstant.QUERY_DTO, dto);
//				//forwardURL = "/inv/storeman/addStoreman.jsp";
//				out.flush();
//				out.close();
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
		} catch (ContainerException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException ex) {
			ex.printLog();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			if(isOpener){
				forwarder.forwardOpenerView(forwardURL, "");
			} else {
				forwarder.forwardView(forwardURL);
			}
		}

	}

}
