/**
 * Function:创建工单统一入口
 * 需要传入参数：专业类型：objectCategory,工单类型：workorderType、主键：systemId
 * User: zhoujs
 * Date: 2007-9-20
 * Time: 13:56:00
 */
package com.sino.ams.workorder.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.FlexValue;
import com.sino.ams.constant.DictConstant;
import com.sino.ams.constant.URLDefineList;
import com.sino.ams.constant.WebAttrConstant;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dao.EtsWorkorderBatchDAO;
import com.sino.ams.workorder.dto.EtsWorkorderBatchDTO;
import com.sino.ams.workorder.util.WorkOrderUtil;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.dto.DTOSet;
import com.sino.base.dto.Request2DTO;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

public class OrderEntryServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String forwardURL = "";
		Message message = SessionUtil.getMessage(req);
		Connection conn = null;
		WorkOrderUtil orderUtil = new WorkOrderUtil();

		String workorderType = StrUtil.nullToString(req.getParameter("workorderType"));
		String categroy = StrUtil.nullToString(req.getParameter("category"));
		int groupId = StrUtil.strToInt(req.getParameter("groupId"));
		String groupName = StrUtil.nullToString(req.getParameter("groupName"));
		String pkValue = StrUtil.nullToString(req.getParameter("systemid"));
        if(pkValue.equals("")){
            pkValue = StrUtil.nullToString(req.getParameter("sf_appDataID"));
        }

		categroy = orderUtil.getObjectCategory(categroy);
		String sectionRight = req.getParameter("sectionRight");
		String hiddenRight = req.getParameter("hiddenRight");
          String systemid =  req.getParameter("sf_appDataID");
         String firstTask = StrUtil.nullToString(req.getParameter("sf_task_attribute1"));
           String   sf_isNew=(String)req.getAttribute(com.sino.sinoflow.constant.WebAttrConstant.SINOFLOW_NEW_CASE);
              if(sf_isNew != null && sf_isNew.equals("1")){
                     //action="NEW_ACTION";
                }
		boolean isFirstNode = false;         //判断是否第一个结点
//		if (StrUtil.isEmpty(sectionRight) || sectionRight.equals("PROCESS1")) {
//			isFirstNode = true;
//		}
        if (StrUtil.isEmpty(firstTask) || firstTask.equals("from")) {
			isFirstNode = true;
		}
		boolean chooseGroup = false;
		String needChooseGroup = "";

		boolean isNew;
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO userAccount = (SfUserDTO) SessionUtil.getUserAccount(req);

			DTOSet groups = userAccount.getUserGroups();
			Request2DTO req2DTO = new Request2DTO();
			req2DTO.setDTOClassName(EtsWorkorderBatchDTO.class.getName());
			EtsWorkorderBatchDTO etsWorkorderBatchDTO = (EtsWorkorderBatchDTO) req2DTO.getDTO(req);
			EtsWorkorderBatchDAO etsWorkorderBatchDAO = new EtsWorkorderBatchDAO(userAccount, etsWorkorderBatchDTO, conn);

			etsWorkorderBatchDTO.setSystemid(pkValue);
			isNew = pkValue.equals("");

			String procdureName = WorkOrderUtil.getOrderProcdureName(workorderType, categroy);

			if (isNew) {
//                chooseGroup = groups.getSize() > 1;
//                if (!chooseGroup) {
//                    SfGroupDTO sfGroup = (SfGroupDTO) groups.getDTO(0);
//                    etsWorkorderBatchDTO.setDistributeGroupId(sfGroup.getGroupId());
//                    etsWorkorderBatchDTO.setDistributeGroupName(sfGroup.getGroupname());
//                }
				etsWorkorderBatchDTO.setDistributeGroupId(groupId);
				etsWorkorderBatchDTO.setDistributeGroupName(groupName);
				etsWorkorderBatchDTO.setCreatedBy(userAccount.getUserId());
				etsWorkorderBatchDTO.setCreateUser(userAccount.getUsername());
				etsWorkorderBatchDTO.setWorkorderBatch(WorkOrderUtil.getWorkorderBatchNo(conn));
				etsWorkorderBatchDTO.setWorkorderType(workorderType);
				etsWorkorderBatchDTO.setWorkorderTypeDesc(FlexValue.getValue(conn, workorderType, DictConstant.WORKORDER_TYPE));

				etsWorkorderBatchDTO.setStatus(0);
			} else {
				chooseGroup = false;
				etsWorkorderBatchDAO.setDTOClassName(EtsWorkorderBatchDTO.class.getName());
				etsWorkorderBatchDTO = (EtsWorkorderBatchDTO) etsWorkorderBatchDAO.getDataByPrimaryKey();
				if (isFirstNode) {//处理临时表
					etsWorkorderBatchDAO.copyToTmpData(etsWorkorderBatchDTO.getWorkorderBatch());
				}
				forwardURL = URLDefineList.WORKORDER_NEW;
			}

			if (chooseGroup) {
				needChooseGroup = "Y";
			} else {
				needChooseGroup = "N";
			}
			req.setAttribute(WebAttrConstant.WORKORDER_BATCH_ATTR, etsWorkorderBatchDTO);

            if(workorderType.equals(DictConstant.ORDER_TYPE_HDV)){
                forwardURL = URLDefineList.HANDOVER_NEW + "?isFirstNode=true&procdureName=" + procdureName;
            } else if (categroy.equals(DictConstant.NETADDR_BTS)) {//基站专业
				forwardURL = URLDefineList.WORKORDER_NEW + "?chooseGroup=" + needChooseGroup + "&objectCategory=" + categroy + "&isFirstNode=" + isFirstNode + "&procdureName=" + procdureName;
			} else if (categroy.equals(DictConstant.NETADDR_DATA)) { //数据专业
				forwardURL = URLDefineList.DATA_NEW + "?chooseGroup=" + needChooseGroup + "&objectCategory=" + categroy + "&isFirstNode=" + isFirstNode + "&procdureName=" + procdureName;
			} else if (categroy.equals(DictConstant.NETADDR_NETOPT)) { //网优专业
				forwardURL = URLDefineList.NET_BETTER + "?chooseGroup=" + needChooseGroup + "&objectCategory=" + categroy + "&isFirstNode=" + isFirstNode + "&procdureName=" + procdureName;
			} else if (categroy.equals(DictConstant.NETADDR_EXCHG)) { //交换专业
				forwardURL = URLDefineList.CHANG_NEW + "?chooseGroup=" + needChooseGroup + "&objectCategory=" + categroy + "&isFirstNode=" + isFirstNode + "&procdureName=" + procdureName;
			} else if (categroy.equals(DictConstant.NETADDR_BSC)) { //监控专业
				forwardURL = URLDefineList.MONITOR_NEW + "?chooseGroup=" + needChooseGroup + "&objectCategory=" + categroy + "&isFirstNode=" + isFirstNode + "&procdureName=" + procdureName;
			} else if (categroy.equals(DictConstant.NETADDR_TRANS)) { //传输专业
				forwardURL = URLDefineList.TRANSFER_NEW + "?chooseGroup=" + needChooseGroup + "&objectCategory=" + categroy + "&isFirstNode=" + isFirstNode + "&procdureName=" + procdureName;
			} else if (categroy.equals(DictConstant.NETADDR_ELE)) { //电力专业
				forwardURL = URLDefineList.ELECTRI_NEW + "?chooseGroup=" + needChooseGroup + "&objectCategory=" + categroy + "&isFirstNode=" + isFirstNode + "&procdureName=" + procdureName;
			} else {
				forwardURL=URLDefineList.ERROR_PAGE;
//                forwardURL = URLDefineList.WORKORDER_NEW + "?chooseGroup=" + needChooseGroup + "&objectCategory=" + categroy + "&isFirstNode=" + isFirstNode;
			}
		} catch (PoolException e) {
			e.printStackTrace();
		} catch (DTOException e) {
			e.printStackTrace();
		} catch (DataHandleException e) {
			e.printLog();
		}
		catch (QueryException ex) {
			ex.printLog();
			message = getMessage(MsgKeyConstant.QUERY_ERROR);
			message.setIsError(true);
			forwardURL = MessageConstant.MSG_PRC_SERVLET;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.closeDBConnection(conn);
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}

	}


}