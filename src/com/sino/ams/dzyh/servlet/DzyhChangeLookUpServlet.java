package com.sino.ams.dzyh.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.dzyh.constant.DzyhLookUpConstant;
import com.sino.ams.dzyh.dto.EamCheckImplementDTO;
import com.sino.ams.dzyh.dto.EamDhBillLDTO;
import com.sino.ams.dzyh.model.DzyhChangeLookUpModel;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.log.Logger;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>
 * Title: EamDhChgLogServlet
 * </p>
 * <p>
 * Description:程序自动生成服务程序“EamDhChgLogServlet”，请根据需要自行修改
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 * 
 * @author 张星
 * @version 1.0
 */

public class DzyhChangeLookUpServlet extends BaseServlet {

	/**
	 * @param req
	 *            HttpServletRequest
	 * @param res
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String forwardURL = "";
		String lookUpName = req.getParameter("lookUpName");
		lookUpName = StrUtil.nullToString(lookUpName);
		Message message = SessionUtil.getMessage(req);
		try {
			if (lookUpName.equals("")) {
				message = getMessage(MsgKeyConstant.INVALID_REQ);
				message.setIsError(true);
				message.setNeedClose(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} else {
				LookUpProp lookProp = new LookUpProp(lookUpName);
				String[] dispNames = null;
				String[] dispLabels = null;
				String[] retFields = null;
				String[] viewPercent = null;
				String[] qryNames = null;
				String[] qryLabels = null;
				String[] primaryKeys = null;
				if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_CHANGE_DZYH)) { // 低值易耗品的变动
					dispNames = new String[] { "EDCL_FROM_DEPT_NAME",
							"EDCL_TO_DEPT_NAME", "EDCL_FROM_WORKORDER_NAME",
							"EDCL_TO_WORKORDER_NAME", "EDCL_FROM_USER_NAME",
							"EDCL_TO_USER_NAME", "FROM_MAINTAIN_USER",
							"TO_MAINTAIN_USER", "FROM_STATUS", "TO_STATUS",
							"CREATION_DATE", "USERNAME" };
					dispLabels = new String[] { "原使用部门", "新使用部门", "原地点", "新地点",
							"原领用人", "新领用人", "原保管人", "新保管人", "原使用状态", "新使用状态",
							"变动日期", "执行人" };
					viewPercent = new String[] { "5%", "5%", "5%", "5%", "5%",
							"5%", "5%", "5%", "5%", "5%", "5%", "5%" };
					retFields = new String[] { "DH_CHG_LOG_ID", "BARCODE",
							"CATALOG_VALUE_ID", "CHG_TYPE", "FROM_DEPT",
							"EDCL_FROM_DEPT_NAME", "TO_DEPT",
							"EDCL_TO_DEPT_NAME", "FROM_ADDRESS_ID",
							"EDCL_FROM_WORKORDER_NAME", "TO_ADDRESS_ID",
							"EDCL_TO_WORKORDER_NAME",
							"FROM_RESPONSIBILITY_USE", "EDCL_FROM_USER_NAME",
							"TO_RESPONSIBILITY_USER", "EDCL_TO_USER_NAME",
							"FROM_MAINTAIN_USER", "TO_MAINTAIN_USER",
							"FROM_STATUS", "TO_STATUS", "CREATE_BY",
							"USERNAME", "CREATION_DATE" };
					qryNames = new String[] { "BARCODE", "CATALOG_VALUE_ID" };
					qryLabels = new String[] { "条码标签", "品 名" };
					primaryKeys = new String[] { "BILL_LINE_ID" };

					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsOuCityMapDTO.class);
				} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_CATEGORY2_DZYH)) { // 低值易耗目录编号
					dispNames = new String[] { "CATALOG_CODE", "ENABLED" };
					dispLabels = new String[] { "目录编号", "是否有效" };
					viewPercent = new String[] { "40%", "30%" };
					retFields = new String[] { "CATALOG_VALUE_ID",
							"CATALOG_CODE", "ENABLED" };
					qryNames = new String[] { "CATALOG_CODE", "ENABLED" };
					qryLabels = new String[] { "目录编号", "是否有效" };
					primaryKeys = new String[] { "CATALOG_VALUE_ID" };

					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EamDhBillLDTO.class);

				} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_ITEMNAME_DZYH)) { // 低值易耗品名

					dispNames = new String[] { "ITEM_CODE", "ITEM_NAME",
							"ENABLED" };
					dispLabels = new String[] { "设备代码", "品名", "是否有效" };
					viewPercent = new String[] { "30%", "30%", "20%" };
					retFields = new String[] { "ITEM_CODE", "ITEM_NAME",
							"ENABLED" };
					qryNames = new String[] { "ITEM_CODE", "ITEM_NAME" };
					qryLabels = new String[] { "设备代码", "品名" };
					primaryKeys = new String[] { "ITEM_CODE" };

					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_ITEMSPEC_DZYH)) { // 低值易耗规格型号

					dispNames = new String[] { "ITEM_CODE", "ITEM_NAME",
							"ITEM_SPEC", "ENABLED" };
					dispLabels = new String[] { "设备代码", "品名", "规格型号", "是否有效" };
					viewPercent = new String[] { "20%", "20%", "20%", "10%" };
					retFields = new String[] { "ITEM_CODE", "ITEM_NAME",
							"ITEM_SPEC", "ENABLED" };
					qryNames = new String[] { "ITEM_NAME", "ITEM_SPEC" };
					qryLabels = new String[] { "品名", "规格型号" };
					primaryKeys = new String[] { "ITEM_CODE" };

					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(DzyhLookUpConstant.LOOK_UP_TASK_DZYH)) {	// 任务名称
					dispNames = new String[] { "CHECK_TASK_ID", "TASK_NAME", "CHECK_TYPE"  };
					dispLabels = new String[] { "任务编号", "任务名称", "任务类型" };
					viewPercent = new String[] { "20%", "30%", "30%" };
					retFields = new String[] { "CHECK_TASK_ID", "TASK_NAME", "CHECK_TYPE" };
					qryNames = new String[] { "TASK_NAME", "CHECK_TYPE" };
					qryLabels = new String[] { "任务名称", "任务类型" };
					primaryKeys = new String[] { "CHECK_TASK_ID" };

					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EamCheckImplementDTO.class);
				}
				lookProp.setCalPattern(LINE_PATTERN);
				lookProp.setDisFieldNames(dispNames);
				lookProp.setDisFieldLabels(dispLabels);
				lookProp.setRetFields(retFields);
				lookProp.setViewPercent(viewPercent);
				lookProp.setQryFieldLabels(qryLabels);
				lookProp.setQryFieldNames(qryNames);
				lookProp.setPrimaryKeys(primaryKeys);
				lookProp.setModelClass(DzyhChangeLookUpModel.class);
				SessionUtil.saveLoopUpProp(req, lookProp);

				forwardURL = WebConstant.LOOK_UP_SERVLET;
			}
		} catch (Exception ex) {
			Logger.logError(ex);
			throw new ServletException(ex);
		} finally {
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
