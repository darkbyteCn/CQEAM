/**
 * User: zhoujs
 * Date: 2007-9-21
 * Time: 10:16:35
 * Function:查询工单批下的所有工单Servlet
 */
package com.sino.ams.workorder.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.AmsOrderConstant;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.EtsWorkorderDAO;
import com.sino.ams.workorder.dao.EtsWorkorderTmpDAO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.dto.EtsWorkorderTmpDTO;
import com.sino.ams.workorder.model.OrderExtendModel;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebActionConstant;
import com.sino.base.data.RowSet;
import com.sino.base.db.conn.DBManager;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

public class WorkOrderTmpServlet extends BaseServlet {

	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		String action = req.getParameter("act");
		action = StrUtil.nullToString(action);
		Connection conn = null;

		String workorderBatchNo = StrUtil.nullToString(req.getParameter("workorderBatchNo"));
		String workorderType = StrUtil.nullToString(req.getParameter("workorderType"));
		boolean isFirstNode = StrUtil.nullToString(req.getParameter("isFirstNode")).equalsIgnoreCase("TRUE");
		String category = StrUtil.nullToString(req.getParameter("objectCategory"));//专业
		String itemCategory = getItemCat(category);


		try {
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);
			EtsWorkorderDTO etsWorkorder = null;
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsWorkorderDTO.class.getName());
			etsWorkorder = (EtsWorkorderDTO) req2DTO.getDTO(req);
			conn = DBManager.getDBConnection();
			EtsWorkorderDAO etsWorkorderDAO = new EtsWorkorderDAO(userAccount, etsWorkorder, conn);
			action = action.equals("") ? WebActionConstant.QUERY_ACTION : action;
			OrderExtendModel orderExtend = new OrderExtendModel();
			SQLModel sqlModel = new SQLModel();
			RowSet rowSet = new RowSet();
			if (action.equals(WebActionConstant.QUERY_ACTION)) {
				sqlModel = orderExtend.getWorkorderQueryModel(workorderBatchNo, isFirstNode);
				SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				rowSet = simpleQuery.getSearchResult();
				req.setAttribute(WebAttrConstant.WORKORDER_ATTR, rowSet); //--
				if(workorderType.equals("18")){
                    forwardURL = URLDefineList.HANDOVER_DETAIL;         //交接工单
                } else if (category.equals(DictConstant.NETADDR_BTS)) {
					forwardURL = URLDefineList.WORKORDER_TMP_PAGE;
				} else if (category.equals(DictConstant.NETADDR_DATA)) { //数据专业
					forwardURL = URLDefineList.DATE_LINE;
				} else if (category.equals(DictConstant.NETADDR_NETOPT)) { //网优专业
					forwardURL = URLDefineList.NET_BETTER_LINE;
				} else if (category.equals(DictConstant.NETADDR_EXCHG)) { //交换专业
					forwardURL = URLDefineList.CHANG_LINE;
				} else if (category.equals(DictConstant.NETADDR_BSC)) { //监控专业
					forwardURL = URLDefineList.MONITOR_LINE;
				} else if (category.equals(DictConstant.NETADDR_TRANS)) { //传输专业
					forwardURL = URLDefineList.TRANSFER_LINE;
				} else if (category.equals(DictConstant.NETADDR_ELE)) { //电力专业
					forwardURL = URLDefineList.ELECTRI_LINE;
				} else {
					Logger.logError("专业信息未定义！！！");
					forwardURL = URLDefineList.ERROR_PAGE;
				}
			} else if (action.equals(WebActionConstant.DELETE_ACTION)) {
				String[] systemids = req.getParameterValues("systemids");

				if (systemids != null && systemids.length > 0) {
					sqlModel = orderExtend.getDeleteTmpDataModel(systemids);
					boolean operatorResult = DBOperator.updateRecord(sqlModel, conn);
				}
				forwardURL = URLDefineList.WORKORDER_TMP_SERVLET + "?act=" + WebActionConstant.QUERY_ACTION + "&isFirstNode=" + isFirstNode;
				forwardURL += "&workorderBatchNo=" + workorderBatchNo;
				forwardURL += "&workorderType=" + workorderType;
				forwardURL += "&objectCategory=" + category;
			} else if (action.equals(WebActionConstant.UPDATE_ACTION)) {
				String[] systemids = req.getParameterValues("systemids");
				String implementBy = StrUtil.nullToString(req.getParameter("implementBy"));
				String checkoverBy = StrUtil.nullToString(req.getParameter("arcUser"));
				String groupId = StrUtil.nullToString(req.getParameter("groupId"));
				if (systemids != null && systemids.length > 0) {
					sqlModel = orderExtend.getUpdateTmpDataModel(systemids, groupId, implementBy, checkoverBy);
					boolean operatorResult = DBOperator.updateRecord(sqlModel, conn);
				}
				forwardURL = URLDefineList.WORKORDER_TMP_SERVLET + "?act=" + WebActionConstant.QUERY_ACTION + "&isFirstNode=" + isFirstNode;
				forwardURL += "&workorderBatchNo=" + workorderBatchNo;
				forwardURL += "&workorderType=" + workorderType;
				forwardURL += "&objectCategory=" + category;
			} else if (action.equals(WebActionConstant.DETAIL_ACTION)) {
				OptionProducer optionProducer = new OptionProducer(userAccount, conn);
				String systemid = StrUtil.nullToString(req.getParameter("systemid"));
				if( StrUtil.isEmpty( systemid )){
					systemid = StrUtil.nullToString(req.getParameter("app_dataID"));
				}

				EtsWorkorderTmpDTO workorderTmpDTO = null;
				if (isFirstNode) {
					workorderTmpDTO = new EtsWorkorderTmpDTO();
					workorderTmpDTO.setSystemid(systemid);
					EtsWorkorderTmpDAO workorderTmpDAO = new EtsWorkorderTmpDAO(userAccount, workorderTmpDTO, conn);
					workorderTmpDAO.setDTOClassName(EtsWorkorderTmpDTO.class.getName());
					workorderTmpDTO = (EtsWorkorderTmpDTO) workorderTmpDAO.getDataByPrimaryKey();
				} else {
					EtsWorkorderDTO workorderDTO = new EtsWorkorderDTO();
					workorderDTO.setSystemid(systemid);
					EtsWorkorderDAO workorderDAO = new EtsWorkorderDAO(userAccount, workorderDTO, conn);
					workorderDAO.setDTOClassName(EtsWorkorderTmpDTO.class.getName());
					workorderTmpDTO = (EtsWorkorderTmpDTO) workorderDAO.getDataByPrimaryKey();
				}

				sqlModel = orderExtend.getSchemeQueryModel(isFirstNode, workorderTmpDTO.getWorkorderNo());
				SimpleQuery simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				rowSet = simpleQuery.getSearchResult();
				req.setAttribute(WebAttrConstant.ETS_WO_SCHEME_RST, rowSet);
				if (workorderTmpDTO.getAttribute7().equals("ALL") || workorderTmpDTO.getAttribute7().equals("")) {
					itemCategory = AmsOrderConstant.scanAllItemCategory;
				}
//                System.out.println("itemCategory = " + itemCategory);
				sqlModel = orderExtend.getSchemeOfObjectModel(workorderTmpDTO.getWorkorderObjectNo(), workorderTmpDTO.getOrganizationId(), itemCategory);
				simpleQuery = new SimpleQuery(sqlModel, conn);
				simpleQuery.executeQuery();
				rowSet = simpleQuery.getSearchResult();

				req.setAttribute(WebAttrConstant.USER_OPTION, optionProducer.getUsersOfGroup(workorderTmpDTO.getGroupId(), StrUtil.nullToString(workorderTmpDTO.getImplementBy())));
				req.setAttribute(WebAttrConstant.ETS_WORKORDER_TMP_DTO, workorderTmpDTO);
				req.setAttribute(WebAttrConstant.CUR_OBJ_SCHEME_RST, rowSet);

				if (category.equals(DictConstant.NETADDR_BTS)) {   //基站专业
					forwardURL = URLDefineList.TMP_WORKORDER_INFO;
				} else if (category.equals(DictConstant.NETADDR_DATA)) { //数据专业
					forwardURL = URLDefineList.DATE_DETAIL;
				} else if (category.equals(DictConstant.NETADDR_NETOPT)) { //网优专业
					forwardURL = URLDefineList.NET_BETTET_DETAIL;
				} else if (category.equals(DictConstant.NETADDR_EXCHG)) { //交换专业
					forwardURL = URLDefineList.CHANG_DETAIL;
				} else if (category.equals(DictConstant.NETADDR_BSC)) { //监控专业
					forwardURL = URLDefineList.MOINTOR_DETAIL;
				} else if (category.equals(DictConstant.NETADDR_TRANS)) { //传输专业
					forwardURL = URLDefineList.TRANSFER_DETAIL;
				} else if (category.equals(DictConstant.NETADDR_ELE)) { //电力专业
					forwardURL = URLDefineList.ELECTRI_DETAIL;
				} else {
					Logger.logError("专业信息未定义！！！");
					forwardURL = URLDefineList.ERROR_PAGE;
				}

			} else if (action.equals(WebActionConstant.SAVE_ACTION)) {
				String[] itemCodes = req.getParameterValues("itemCode");
				String[] itemQtys = req.getParameterValues("itemQty");

				boolean operatorResult = etsWorkorderDAO.updateTmpData(etsWorkorder, userAccount, itemCodes, itemQtys);

				forwardURL = URLDefineList.WINDOW_CLOSE_PAGE + "?retValue=1";
			}
		} catch (PoolException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.CONN_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DTOException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.DTO_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (QueryException ex) {
			Logger.logError(ex);
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (DataHandleException e) {
			Logger.logError(e);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}

	private String getItemCat(String objectCat) {
		String category = "";
		if (objectCat.equals(DictConstant.NETADDR_BTS)) {
			category = DictConstant.CATEGORY_BTS;
		} else if (objectCat.equals(DictConstant.NETADDR_BSC)) {
			category = DictConstant.CATEGORY_BSC;
		} else if (objectCat.equals(DictConstant.NETADDR_DATA)) {
			category = DictConstant.CATEGORY_DATA;
		} else if (objectCat.equals(DictConstant.NETADDR_ELE)) {
			category = DictConstant.CATEGORY_ELEC;
		} else if (objectCat.equals(DictConstant.NETADDR_EXCHG)) {
			category = DictConstant.CATEGORY_EXCHG;
		} else if (objectCat.equals(DictConstant.NETADDR_NETOPT)) {
			category = DictConstant.CATEGORY_NETOPT;
		} else if (objectCat.equals(DictConstant.NETADDR_TRANS)) {
			category = DictConstant.CATEGORY_TRANS;
		}

		return category;
	}
}
