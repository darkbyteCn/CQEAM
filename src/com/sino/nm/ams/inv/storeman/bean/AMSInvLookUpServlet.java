package com.sino.nm.ams.inv.storeman.bean;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.sino.ams.bean.AMSLookUpServlet;
import com.sino.ams.constant.LookUpConstant;
import com.sino.ams.ct.bean.LookUpCtConstant;
import com.sino.ams.ct.dto.EtsItemInfoDTO;
import com.sino.ams.inv.dzyh.dto.EamDhBillLDTO;
import com.sino.nm.ams.inv.storeman.dto.EamInvStoremanDTO;
import com.sino.nm.ams.inv.storeman.base.constant.web.WebInvConstant;
import com.sino.ams.system.cost.dto.CostCenterDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.log.Logger;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

public class AMSInvLookUpServlet extends AMSLookUpServlet {

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
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} else {
				LookUpProp lookProp = new LookUpProp(lookUpName);

				String[] dispFieldNames = null;
				String[] retFields = null;
				String[] dispFieldLabels = null;
				String[] viewPercent = null;
				String[] qryFieldNames = null;
				String[] qryFieldLabels = null;
				String[] primaryKeys = null;
				if (lookUpName.equals(LookUpConstant.LOOK_UP_SYS_ITEM)) {

					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"仪器仪表名称", "规格型号"};
					retFields = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CODE"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"仪器仪表名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_SYS_ITEM_DZYH)) {

					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"设备名称", "规格型号"};
					retFields = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CODE"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpCtConstant.LOOK_UP_SYS_ITEM_NAME)) {

					dispFieldNames = new String[]{"ITEM_NAME"};
					dispFieldLabels = new String[]{"设备名称"};
					//retFields = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CODE", "VENDOR_NAME", "VENDOR_ID"};
					retFields = new String[]{"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC"};
					viewPercent = new String[]{"50%"};
					qryFieldNames = new String[]{"ITEM_NAME"};
					qryFieldLabels = new String[]{"设备名称"};
					primaryKeys = new String[]{"ITEM_CODE"};


					lookProp.setMultipleChose(false);  //设置成单选按钮吧，true是不是就是多选按钮啊！

					//lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);

				} else if (lookUpName.equals(LookUpCtConstant.LOOK_UP_SYS_ITEM_SPEC)) {

					dispFieldNames = new String[]{"ITEM_SPEC"};
					dispFieldLabels = new String[]{"规格型号"};
					retFields = new String[]{"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC"};
					viewPercent = new String[]{"50%"};
					qryFieldNames = new String[]{"ITEM_SPEC"};
					qryFieldLabels = new String[]{"规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setMultipleChose(false);
					//lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if(lookUpName.equals(LookUpInvConstant.LOOK_UP_WORKORDER_OBJECT_NO)) { //选择仓库

					dispFieldNames = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "INV_CATEGORY_NAME", "WORKORDER_OBJECT_LOCATION", "BIZ_CATEGORY_NAME"};
					dispFieldLabels = new String[]{"仓库代码", "仓库名称", "仓库类型", "仓库地点", "业务类型"};
					retFields = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "INV_CATEGORY_NAME", "WORKORDER_OBJECT_LOCATION", "BIZ_CATEGORY_NAME"};
					viewPercent = new String[]{"10%", "30%", "20%", "30%", "10%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"仓库代码", "仓库名称", "仓库地点"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamInvStoremanDTO.class);
				} else if(lookUpName.equals(LookUpInvConstant.LOOK_UP_USER_ID)) { //选择仓管员
					dispFieldNames = new String[]{"LOGIN_NAME", "USER_NAME"};
					dispFieldLabels = new String[]{"登录名", "仓管员姓名"};
					retFields = new String[]{"USER_ID", "USER_NAME", "LOGIN_NAME"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"LOGIN_NAME", "USERNAME"};
					qryFieldLabels = new String[]{"登录名", "仓管员姓名"};
					primaryKeys = new String[]{"USER_ID"};
					
					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(SfUserDTO.class);
				} else if(lookUpName.equals(LookUpInvConstant.LOOK_UP_UPDATED_USER)) { //选择修改人
					dispFieldNames = new String[]{"LAST_UPDATE_BY", "UPDATED_USER"};
					dispFieldLabels = new String[]{"修改人ID", "修改人"};
					retFields = new String[]{"LAST_UPDATE_BY", "UPDATED_USER"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"LAST_UPDATE_BY", "UPDATED_USER"};
					qryFieldLabels = new String[]{"修改人ID", "修改人"};
					primaryKeys = new String[]{"LAST_UPDATE_BY"};
					
					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(SfUserDTO.class);
				} else if(lookUpName.equals(LookUpInvConstant.LOOK_UP_CREATED_USER)) { //选择创建人
					dispFieldNames = new String[]{"CREATED_BY", "CREATED_USER"};
					dispFieldLabels = new String[]{"创建人ID", "创建人"};
					retFields = new String[]{"CREATED_BY", "CREATED_USER"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"CREATED_BY", "CREATED_USER"};
					qryFieldLabels = new String[]{"创建人ID", "创建人"};
					primaryKeys = new String[]{"CREATED_BY"};
					
					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(SfUserDTO.class);
				} else if (lookUpName.equals(LookUpConstant.COST_CENTER)) {//查找成本中心
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"COST_CENTER_CODE", "COST_CENTER_NAME"};
					dispFieldLabels = new String[]{"成本中心代码", "成本中心名称"};
					retFields = new String[]{"COST_CENTER_CODE", "COST_CENTER_NAME"};
					viewPercent = new String[]{"30%", "60%"};
					qryFieldNames = new String[]{"COST_CENTER_CODE", "COST_CENTER_NAME"};
					qryFieldLabels = new String[]{"成本中心代码", "成本中心名称"};
					primaryKeys = new String[]{"COST_CENTER_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(CostCenterDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_CATALOG_VALUE_ID)) { //查找目录编号
					dispFieldNames = new String[]{"CATALOG_CODE", "CATALOG_NAME"};
					dispFieldLabels = new String[]{"目录编号", "品名"};
					retFields = new String[]{"CATALOG_VALUE_ID", "CATALOG_CODE", "CATALOG_NAME"};
					viewPercent = new String[]{"30%", "60%"};
					qryFieldNames = new String[]{"CATALOG_CODE", "CATALOG_NAME"};
					qryFieldLabels = new String[]{"目录编号", "品名"};
					primaryKeys = new String[]{"CATALOG_VALUE_ID"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_RESPONSIBILITY_DEPT)) { //查找使用部门
					dispFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					dispFieldLabels = new String[]{"部门代码", "部门名称"};
					retFields = new String[]{"DEPT_CODE", "DEPT_NAME"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					qryFieldLabels = new String[]{"部门代码", "部门名称"};
					primaryKeys = new String[]{"DEPT_CODE"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_RESPONSIBILITY_USER)) { //查找领用人
					dispFieldNames = new String[]{"DEPT_NAME", "USER_NAME"};
					dispFieldLabels = new String[]{"领用人所属部门", "领用人姓名"};
					retFields = new String[]{"EMPLOYEE_ID", "DEPT_CODE", "USER_NAME", "DEPT_NAME"};
					viewPercent = new String[]{"40%", "40%"};
					qryFieldNames = new String[]{"DEPT_NAME", "USER_NAME"};
					qryFieldLabels = new String[]{"领用人所属部门", "领用人姓名"};
					primaryKeys = new String[]{"EMPLOYEE_ID"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJSL_ITEM_INFO)) { //备件申领查找条码号
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME"};
					dispFieldLabels = new String[]{"设备条码", "设备名称"};
					retFields = new String[]{"BARCODE", "ITEM_NAME"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME"};
					qryFieldLabels = new String[]{"设备条码", "设备名称"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_BARCODE)) { //选择标签编号
					dispFieldNames = new String[]{"BARCODE_FLAG", "CATALOG_NAME", "VALUE"};
					dispFieldLabels = new String[]{"标签编号", "品名", "计量单位"};
					retFields = new String[]{"BARCODE_FLAG", "CATALOG_NAME", "UNIT" , "VALUE"};
					viewPercent = new String[]{"30%", "40%", "20%"};
					qryFieldNames = new String[]{"BARCODE_FLAG", "CATALOG_NAME"};
					qryFieldLabels = new String[]{"标签编号", "品名"};
					primaryKeys = new String[]{"BARCODE_FLAG"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_RESPONSIBILITY_EMPLOYEE)) { //选择申请人
					dispFieldNames = new String[]{"EMPLOYEE_NUMBER", "USER_NAME"};
					dispFieldLabels = new String[]{"员工编号", "员工姓名"};
					retFields = new String[]{"EMPLOYEE_ID", "USER_NAME", "EMPLOYEE_NUMBER"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"EMPLOYEE_NUMBER", "USER_NAME"};
					qryFieldLabels = new String[]{"员工编号", "员工姓名"};
					primaryKeys = new String[]{"EMPLOYEE_ID"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_WORKORDER_OBJECT_NO_DZYH_WARE)) { //选择低值易耗品仓库
					dispFieldNames = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_CODE"};
					dispFieldLabels = new String[]{"仓库编号", "仓库名称", "仓库代码"};
					retFields = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_CODE"};
					viewPercent = new String[]{"20%", "40%", "30%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_CODE"};
					qryFieldLabels = new String[]{"仓库编号", "仓库名称", "仓库代码"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_WORKORDER_OBJECT_NO_DZYH)) { //选择低值易耗品地点
					dispFieldNames = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_CODE"};
					dispFieldLabels = new String[]{"地点编号", "地点名称", "地点代码"};
					retFields = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_CODE"};
					viewPercent = new String[]{"20%", "40%", "30%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_CODE"};
					qryFieldLabels = new String[]{"地点编号", "地点名称", "地点代码"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpInvConstant.LOOK_UP_ITEM_CATEGORY2)) { //选择ETS_SYSTEM_ITEM表中的目录编号
					dispFieldNames = new String[]{"ITEM_CATEGORY2", "ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"目录编号", "设备名称", "规格型号"};
					retFields = new String[]{"ITEM_CATEGORY2", "ITEM_NAME", "ITEM_SPEC", "ITEM_UNIT", "ITEM_CODE"};
					viewPercent = new String[]{"20%", "30%", "40%"};
					qryFieldNames = new String[]{"ITEM_CATEGORY2", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"目录编号", "设备名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CATEGORY2"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				}
				lookProp.setDisFieldNames(dispFieldNames);
				lookProp.setDisFieldLabels(dispFieldLabels);
				lookProp.setRetFields(retFields);
				lookProp.setViewPercent(viewPercent);
				lookProp.setQryFieldNames(qryFieldNames);
				lookProp.setQryFieldLabels(qryFieldLabels);
				lookProp.setPrimaryKeys(primaryKeys);
				//lookProp.setModelClass(AMSLookUpModel.class);
				lookProp.setModelClass(AMSInvLookUpModel.class);
				
				forwardURL = WebConstant.LOOK_UP_SERVLET;

				SessionUtil.saveLoopUpProp(req, lookProp);
			}

		} catch (Exception ex) {
			Logger.logError(ex);
		} finally {
			setHandleMessage(req, message);
			ServletForwarder forwarder = new ServletForwarder(req, res);
			forwarder.forwardView(forwardURL);
		}
	}
}
