package com.sino.ams.inv.storeman.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.inv.common.bean.InvOptionProducer;
import com.sino.ams.inv.storeman.base.constant.web.WebInvActionConstant;
import com.sino.ams.inv.storeman.dao.AmsInvPriviDAO;
import com.sino.ams.inv.storeman.dao.EamInvStoremanDAO;
import com.sino.ams.inv.storeman.dto.EamInvStoremanDTO;
import com.sino.ams.inv.storeman.model.EamInvStoremanModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.db.QueryConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolPassivateException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.StrException;
import com.sino.base.exception.UploadException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.CheckBoxProp;
import com.sino.base.web.ServletForwarder;
import com.sino.base.web.request.upload.RequestParser;
import com.sino.framework.dao.PageQueryDAO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;
import com.sino.framework.sql.BaseSQLProducer;

/**
 * <p>Title: EamInvStoremanServlet</p>
 * <p>Description:仓库地点设置</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author V-Yushibo
 * @version 1.0
 */
public class EamInvStoremanServlet extends BaseServlet {

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
			req2dto.setDTOClassName(EamInvStoremanDTO.class.getName());
			EamInvStoremanDTO dto = (EamInvStoremanDTO)req2dto.getDTO(req);
			
			String action = dto.getAct(); //保证action不为空的写法

			conn = getDBConnection(req);

			EamInvStoremanDAO storemanDAO = new EamInvStoremanDAO(user, dto, conn);
			AmsInvPriviDAO priviDAO = new AmsInvPriviDAO(user, dto, conn);
			InvOptionProducer optProducer = new InvOptionProducer(user, conn);


			String ouOption = optProducer.getAllOrganization(dto.getOrganizationId(), true);//公司信息
			dto.setOuOption(ouOption); //在前台用DTO就能得到相应的值

			String invCategoryOpt = optProducer.getDictOption("INV_TYPE", StrUtil.nullToString(dto.getObjectCategory()));//仓库类型列表
			dto.setInvCategoryOpt(invCategoryOpt);//在前台用DTO就能得到相应的值
			String bizCategoryOpt = optProducer.getDictOption("INV_BIZ_CATEGORY", StrUtil.nullToString(dto.getBusinessCategory()));//业务类型列表
			dto.setBizCategoryOpt(bizCategoryOpt);//在前台用DTO就能得到相应的值

			if(action.equals("")){
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/inv/storeman/storemanQuery.jsp";
			} else if (action.equals(WebActionConstant.QUERY_ACTION)) {
				BaseSQLProducer sqlProducer = new EamInvStoremanModel(user, dto);
				PageQueryDAO pageDAO = new PageQueryDAO(req, conn, sqlProducer);
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				checkProp.addDbField("STOREMAN_ID");
				checkProp.addDbField("WORKORDER_OBJECT_NO");
				pageDAO.setWebCheckProp(checkProp);
				pageDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				pageDAO.produceWebData();
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				forwardURL = "/inv/storeman/storemanQuery.jsp";
			} else if (action.equals(WebActionConstant.NEW_ACTION)) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				String workorderObjectNo = req.getParameter("workorderObjectNo");
				String storemanId = req.getParameter("storemanId");
				forwardURL = "/inv/storeman/addStoreman.jsp?workorderObjectNo=" + workorderObjectNo + "&storemanId=" + storemanId;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				req.setAttribute(QueryConstant.QUERY_DTO, dto);
				
				String workorderObjectNo = req.getParameter("workorderObjectNo");
				String storemanId = req.getParameter("storemanId");
				storemanDAO.setDTOClassName(EamInvStoremanDTO.class.getName());
				storemanDAO.setCalPattern(CalendarConstant.LINE_PATTERN);
				dto = (EamInvStoremanDTO) storemanDAO.getDataUniqueKeyDAO(workorderObjectNo);
				req.setAttribute("storemanId", storemanId);
				req.setAttribute("workorderObjectNo", workorderObjectNo);
				forwardURL = "/inv/storeman/storemanDetail.jsp";
			} else if (action.equals(WebActionConstant.CREATE_ACTION)) {	
				storemanDAO.createData();
				priviDAO.createDatas("INV_IN");
				priviDAO.createDatas("INV_OUT");
				priviDAO.createDatas("INV_QUERY");
				forwardURL = "/servlet/" + getClass().getName();
				forwardURL += "?act=" + WebActionConstant.QUERY_ACTION;
				forwardURL += "&workorderObjectNo=";
				//isOpener = true;
				//forwardURL = "/inv/storeman/storemanQuery.jsp";
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				String storemanId = req.getParameter("storemanId");
				//storemanDAO.updateData();
				storemanDAO.updateData(storemanId);
				//priviDAO.createData();
				priviDAO.createDatas("INV_IN");
				priviDAO.createDatas("INV_OUT");
				priviDAO.createDatas("INV_QUERY");
				
				message = storemanDAO.getMessage();
				dto.setWorkorderObjectNo("");
				forwardURL = "/servlet/" + getClass().getName();
				forwardURL += "?act=" + WebActionConstant.QUERY_ACTION;
//				forwardURL = "/servlet/com.sino.ams.inv.storeman.servlet.EamInvStoremanServlet?act=" + WebActionConstant.QUERY_ACTION;
				//isOpener = true;
			} else if (action.equals(WebInvActionConstant.UPDATE_ACTION_USERS)) {
				RequestParser parser = new RequestParser();
				CheckBoxProp checkProp = new CheckBoxProp("subCheck");
				parser.setCheckBoxProp(checkProp);
				parser.transData(req);
				String userId = parser.getParameter("userId");
				String[] workorderObjectNos = parser.getParameterValues("workorderObjectNo");
				String[] storemanIds = parser.getParameterValues("storemanId");
				storemanDAO.saveDatas(storemanIds, workorderObjectNos, userId);
				message = storemanDAO.getMessage();
				forwardURL = "/servlet/com.sino.ams.inv.storeman.servlet.EamInvStoremanServlet?act=" + WebActionConstant.QUERY_ACTION;
			} else if (action.equals(WebInvActionConstant.CHECK_ACTION)) {
				String workorderObjectNo = req.getParameter("workorderObjectNo");
				PrintWriter out = res.getWriter();
				boolean codeExist = storemanDAO.getUniqueKeyDataDAO(workorderObjectNo);
				if(codeExist){
					//res.getWriter().println("CODE_EXIST");
					out.print(WebAttrConstant.CODE_EXIST);
				} else {
					//res.getWriter().println("CODE_NOT_EXIST");
					out.print(WebAttrConstant.CODE_NOT_EXIST);
				}
				//req.setAttribute(QueryConstant.QUERY_DTO, dto);
				//forwardURL = "/inv/storeman/addStoreman.jsp";
				out.flush();
				out.close();
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
		} catch (UploadException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.COMMON_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
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
