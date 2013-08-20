package com.sino.ams.bean;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.message.MsgKeyConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.ams.constant.LookUpConstant;
import com.sino.ams.ct.dto.EtsFaAssetsDTO;
import com.sino.ams.instrument.dto.AmsInstrumentInfoDTO;
import com.sino.ams.inv.dzyh.dto.EamDhBillLDTO;
import com.sino.ams.inv.storeman.dto.EamInvStoremanDTO;
import com.sino.ams.net.statistic.dto.EquipStatDTO;
import com.sino.ams.newasset.dto.AmsMisDeptDTO;
import com.sino.ams.newasset.dto.AmsMisEmployeeDTO;
//import com.sino.ams.newasset.dto.AmsWorkOrderDTO;
import com.sino.ams.newasset.dto.AssetsTagNumberQueryDTO;
import com.sino.ams.newasset.dto.GroupDTO;
import com.sino.nm.ams.others.cabel.dto.AmsCabelInfoDTO;
import com.sino.nm.ams.others.dto.AmsInvStorageDTO;
import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.spare.allot.dto.AmsBjsAllotDDto;
import com.sino.ams.spare.dto.AmsItemTransLDTO;
import com.sino.ams.spare.dto.AmsSpareCategoryDTO;
import com.sino.ams.spare.dto.AmsSpareInfoDTO;
import com.sino.ams.spare.dto.SpareReturnDTO;
import com.sino.ams.spare.repair.dto.AmsVendorInfoDTO;
import com.sino.ams.spare.version.dto.AmsItemVersionDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.cost.dto.CostCenterDTO;
import com.sino.ams.system.county.dto.EtsCountyDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.house.dto.AmsHouseInfoDTO;
import com.sino.ams.system.house.dto.AmsLandInfoDTO;
import com.sino.ams.system.item.dto.EtsMisPoVendorsDTO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.manufacturer.EtsManufacturerDTO;
import com.sino.ams.system.note.dto.AmsRentDeadlineDTO;
import com.sino.ams.system.project.dto.EtsPaProjectsAllDTO;
//import com.sino.ams.system.user.dto.AmsSynRightDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.web.bts.dto.EtsObjectFixfeeDTO;
import com.sino.ams.workorder.dto.EtsWorkorderDTO;
import com.sino.ams.workorder.dto.EtsWorkorderTmpDTO;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

import com.sino.ams.yj.dto.AmsYjTeamDTO;
/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-9-24
 * Time: 10:26:58
 */
public class AMSLookUpServlet extends BaseServlet {
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
				if (lookUpName.equals(LookUpConstant.LOOK_UP_USER)) {
					dispFieldNames = new String[]{"LOGIN_NAME", "EXECUTE_USER_NAME"};
					dispFieldLabels = new String[]{"登陆名", "用户名"};
					retFields = new String[]{"LOGIN_NAME", "EXECUTE_USER_NAME", "EXECUTE_USER"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"LOGIN_NAME", "EXECUTE_USER_NAME"};
					qryFieldLabels = new String[]{"登陆名", "用户名"};
					primaryKeys = new String[]{"EXECUTE_USER"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsWorkPlanDTO.class);
				}
				else if(lookUpName.equals(LookUpConstant.LOOK_UP_USER_WITH_DEPT))
				{
					dispFieldNames = new String[]{"LOGIN_NAME","EXECUTE_USER_NAME","DEPT_NAME","GROUP_NAME"};
					dispFieldLabels = new String[]{"登陆名","用户名","部门","组名"};
					retFields = new String[]{"LOGIN_NAME","EXECUTE_USER", "EXECUTE_USER_NAME", "DEPT_NAME","DEPT_CODE","GROUP_NAME","GROUP_ID"};
					viewPercent = new String[]{"20%", "20%","30%","30%"};
					qryFieldNames = new String[]{"LOGIN_NAME","EXECUTE_USER_NAME"};
					qryFieldLabels = new String[]{"登陆名", "用户名"};
					primaryKeys = new String[]{"EXECUTE_USER"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsWorkPlanDTO.class);
				}
				else if(lookUpName.equals(LookUpConstant.LOOK_UP_USER_NAME)) {
					dispFieldNames = new String[]{"LOGIN_NAME", "USER_NAME"};
					dispFieldLabels = new String[]{"登陆名", "用户名"};
					retFields = new String[]{"LOGIN_NAME", "USER_NAME", "EMPLOYEE_NUMBER"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"LOGIN_NAME", "USER_NAME"};
					qryFieldLabels = new String[]{"登陆名", "用户名"};
					primaryKeys = new String[]{"EMPLOYEE_NUMBER"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsWorkorderDTO.class);
				} else if(lookUpName.equals(LookUpConstant.LOOK_UP_USER_RIGHT)){
                   	dispFieldNames = new String[]{"LOGIN_NAME", "USER_NAME"};
					dispFieldLabels = new String[]{"登陆名", "用户名"};
					retFields = new String[]{"LOGIN_NAME", "USER_NAME", "USER_ID"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"LOGIN_NAME", "USER_NAME"};
					qryFieldLabels = new String[]{"登陆名", "用户名"};
					primaryKeys = new String[]{"USER_ID"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
//					lookProp.setDtoClass(AmsSynRightDTO.class);
                } else if (lookUpName.equals(LookUpConstant.LOOK_UP_VENDOR)) { //查找供应商
					dispFieldNames = new String[]{"SEGMENT1", "VENDOR_NAME"};
					dispFieldLabels = new String[]{"供应商代码", "供应商名称"};
					retFields = new String[]{"VENDOR_ID", "VENDOR_NAME"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"SEGMENT1", "VENDOR_NAME"};
					qryFieldLabels = new String[]{"供应商代码", "供应商名称"};
					primaryKeys = new String[]{"VENDOR_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750); //数据显示的总体宽度，单位为像素
					lookProp.setDtoClass(EtsMisPoVendorsDTO.class); //查询结果是以DTO对象显示
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_BTS)) {
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"地点编号", "地点简称", "所在地点"};
					retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION",
							"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_CODE"};
					viewPercent = new String[]{"20%", "40%", "40%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"地点编号", "地点简称", "所在地点"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsObjectFixfeeDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_CT_BTS)) {
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"地点编号", "地点简称", "所在地点"};
					retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION",
							"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_CODE"};
					viewPercent = new String[]{"20%", "40%", "40%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"地点编号", "地点简称", "所在地点"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsObjectFixfeeDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_SYS_ITEM)) {

					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"设备名称", "规格型号"};
					retFields = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CODE", "VENDOR_NAME", "VENDOR_ID"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_BEIJIAN_ITEM)) {

					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"标签号", "设备名称", "规格型号"};
					retFields = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CODE", "BARCODE"};
					viewPercent = new String[]{"30%", "30%", "40%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					primaryKeys = new String[]{"SYSTEMID"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
				} else if(lookUpName.equals(LookUpConstant.LOOK_UP_PROJECT_AUTHORIZED)) {
					this.setOptions(req, res, LookUpConstant.LOOK_UP_PROJECT_AUTHORIZED );
					
					dispFieldNames = new String[]{"SEGMENT1", "PRJ_NAME", "PROJECT_TYPE"};
					dispFieldLabels = new String[]{"项目编号", "项目名称", "项目类型"};
					retFields = new String[]{"PRJ_ID", "PRJ_NAME"};
					viewPercent = new String[]{"10%", "60%", "30%"};
					qryFieldNames = new String[]{"SEGMENT1", "NAME"};
					qryFieldLabels = new String[]{"项目编号", "项目名称" };
					primaryKeys = new String[]{"PRJ_ID"};

					lookProp.setTotalWidth(800);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsPaProjectsAllDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_PROJECT)) {
					this.setOptions(req, res, LookUpConstant.LOOK_UP_PROJECT );
					
					dispFieldNames = new String[]{"SEGMENT1", "PRJ_NAME", "PROJECT_TYPE"};
					dispFieldLabels = new String[]{"项目编号", "项目名称", "项目类型"};
					retFields = new String[]{"PRJ_ID", "PRJ_NAME"};
					viewPercent = new String[]{"10%", "60%", "30%"};
					qryFieldNames = new String[]{"SEGMENT1", "NAME"};
					qryFieldLabels = new String[]{"项目编号", "项目名称" };
					primaryKeys = new String[]{"PRJ_ID"};

					lookProp.setTotalWidth(800);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsPaProjectsAllDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS)) { //资产流程查找资产
					dispFieldNames = new String[]{"TAG_NUMBER", "ASSETS_DESCRIPTION", "MODEL_NUMBER", "CURRENT_UNITS",
							"UNIT_OF_MEASURE"};
					dispFieldLabels = new String[]{"条码", "资产描述", "型号", "数量", "单位"};
					retFields = new String[]{"ASSET_ID", "FA_CATEGORY1", "FA_CATEGORY2", "ASSETS_DESCRIPTION",
							"FA_CATEGORY_CODE", "UNIT_OF_MEASURE",
							"LIFE_IN_YEARS", "MODEL_NUMBER", "TAG_NUMBER", "CURRENT_UNITS", "ASSETS_LOCATION",
							"PROJECT_NAME", "DATE_PLACED_IN_SERVICE", "IS_RETIREMENTS", "ASSET_NUMBER",
							"COST", "DEPRN_COST", "ASSETS_STATUS", "ASSIGNED_TO_NAME", "ASSIGNED_TO_NUMBER"};
					viewPercent = new String[]{"12%", "37%", "35%", "6%", "10%"};
					qryFieldNames = new String[]{"TAG_NUMBER", "ASSETS_DESCRIPTION", "MODEL_NUMBER"};
					qryFieldLabels = new String[]{"条码", "资产描述", "型号"};
					primaryKeys = new String[]{"ASSET_ID"};

					lookProp.setTotalWidth(800);
					lookProp.setMultipleChose(true);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsFaAssetsDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_PURVEY)) {
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"SEGMENT1", "VENDOR_NAME"};
					dispFieldLabels = new String[]{"供应商代码", "供应商名称"};
					retFields = new String[]{"SEGMENT1", "VENDOR_NAME", "VENDOR_ID"};
					viewPercent = new String[]{"20%", "80%"};
					qryFieldNames = new String[]{"SEGMENT1", "VENDOR_NAME"};
					qryFieldLabels = new String[]{"供应商代码", "供应商名称"};
					primaryKeys = new String[]{"VENDOR_ID"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_MANUFACTURER)) { //查找厂商
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"MANUFACTURER_CODE", "MANUFACTURER_NAME"};
					dispFieldLabels = new String[]{"厂商代码", "厂商名称"};
					retFields = new String[]{"MANUFACTURER_ID", "MANUFACTURER_CODE", "MANUFACTURER_NAME"};
					viewPercent = new String[]{"20%", "80%"};
					qryFieldNames = new String[]{"MANUFACTURER_CODE", "MANUFACTURER_NAME"};
					qryFieldLabels = new String[]{"厂商代码", "厂商名称"};
					primaryKeys = new String[]{"MANUFACTURER_ID"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsManufacturerDTO.class);
				} else if (lookUpName.equals("LOOK_UP_DIVERSITY")) { //查找差异类型
                    String multipleChose = req.getParameter("multipleChose");
                    if(!StrUtil.isEmpty(multipleChose) && multipleChose.equalsIgnoreCase("true")){
                        lookProp.setMultipleChose(true);
                    } else {
                        lookProp.setMultipleChose(false);
                    }
					dispFieldNames = new String[]{"DIFF_TYPE_CODE", "DIFF_TYPE"};
					dispFieldLabels = new String[]{"差异代码", "差异名称"};
					retFields = new String[]{"DIFF_TYPE_CODE", "DIFF_TYPE"};
					viewPercent = new String[]{"30%", "70%"};
					qryFieldNames = new String[]{"DIFF_TYPE_CODE", "DIFF_TYPE"};
					qryFieldLabels = new String[]{"差异代码", "差异名称"};
					primaryKeys = new String[]{"DIFF_TYPE_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
					
				} else if (lookUpName.equals("LOOK_UP_DIVERSITY_ZERO")) { //查找差异类型
                    String multipleChose = req.getParameter("multipleChose");
                    if(!StrUtil.isEmpty(multipleChose) && multipleChose.equalsIgnoreCase("true")){
                        lookProp.setMultipleChose(true);
                    } else {
                        lookProp.setMultipleChose(false);
                    }
					dispFieldNames = new String[]{"DIFF_TYPE_CODE", "DIFF_TYPE"};
					dispFieldLabels = new String[]{"差异代码", "差异名称"};
					retFields = new String[]{"DIFF_TYPE_CODE", "DIFF_TYPE"};
					viewPercent = new String[]{"30%", "70%"};
					qryFieldNames = new String[]{"DIFF_TYPE_CODE", "DIFF_TYPE"};
					qryFieldLabels = new String[]{"差异代码", "差异名称"};
					primaryKeys = new String[]{"DIFF_TYPE_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
					
				}else if (lookUpName.equals(LookUpConstant.LOOK_UP_CONTENT)) { //查找目录
                    String multipleChose = req.getParameter("multipleChose");
                    if(!StrUtil.isEmpty(multipleChose) && multipleChose.equalsIgnoreCase("true")){
                        lookProp.setMultipleChose(true);
                    } else {
                        lookProp.setMultipleChose(false);
                    }
					dispFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					dispFieldLabels = new String[]{"目录代码", "目录名称"};
					retFields = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					viewPercent = new String[]{"20%", "80%"};
					qryFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					qryFieldLabels = new String[]{"目录代码", "目录名称"};
					primaryKeys = new String[]{"CONTENT_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
					
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_DZYH_CONTENT)) { //查找低值易耗目录
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					dispFieldLabels = new String[]{"目录代码", "目录名称"};
					retFields = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					viewPercent = new String[]{"20%", "80%"};
					qryFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					qryFieldLabels = new String[]{"目录代码", "目录名称"};
					primaryKeys = new String[]{"CONTENT_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);

				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_CONTENT_NOMATCH_LNE)) { //查找没有对应逻辑网络元素的资产目录
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					dispFieldLabels = new String[]{"目录代码", "目录名称"};
					retFields = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					viewPercent = new String[]{"20%", "80%"};
					qryFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					qryFieldLabels = new String[]{"目录代码", "目录名称"};
					primaryKeys = new String[]{"CONTENT_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_CONTENT_NOMATCH_CEX)) { //查找没有对应投资分了的资产目录
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					dispFieldLabels = new String[]{"目录代码", "目录名称"};
					retFields = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					viewPercent = new String[]{"20%", "80%"};
					qryFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					qryFieldLabels = new String[]{"目录代码", "目录名称"};
					primaryKeys = new String[]{"CONTENT_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_CONTENT_NOMATCH_OPE)) { //查找没有对应业务平台素的资产目录
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					dispFieldLabels = new String[]{"目录代码", "目录名称"};
					retFields = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					viewPercent = new String[]{"20%", "80%"};
					qryFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					qryFieldLabels = new String[]{"目录代码", "目录名称"};
					primaryKeys = new String[]{"CONTENT_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_CONTENT_NOMATCH_NLE)) { //查找没有对应网络层次的资产目录
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					dispFieldLabels = new String[]{"目录代码", "目录名称"};
					retFields = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					viewPercent = new String[]{"20%", "80%"};
					qryFieldNames = new String[]{"CONTENT_CODE", "CONTENT_NAME"};
					qryFieldLabels = new String[]{"目录代码", "目录名称"};
					primaryKeys = new String[]{"CONTENT_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_INSTR)) {
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"仪具条码", "仪具名称", "规格型号"};
					retFields = new String[]{"BARCODE", "ITEM_NAME", "CNAME", "CREATION_DATE", "VENDOR_NAME", "INSTRU_USAGE", "ITEM_SPEC", "CURR_KEEP_USER", "DNAME"};
					viewPercent = new String[]{"20%", "40%", "40%"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"仪具条码", "仪具名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsInstrumentInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_KUCUN)) {
					dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"地址编号", "地点简称", "所在地点"};
					retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "ADDRESS_ID", "WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NO"};
					viewPercent = new String[]{"10%", "40%", "40%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"地址简称", "所在地点"};
					primaryKeys = new String[]{"ADDRESS_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsBjsAllotDDto.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_BF)) {
					dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"地址编号", "地址简称", "所在地点"};
					retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "ADDRESS_ID", "WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NO"};
					viewPercent = new String[]{"10%", "40%", "40%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"地址简称", "所在地点"};
					primaryKeys = new String[]{"ADDRESS_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsBjsAllotDDto.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_ADDRESS)) {
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"地址编号", "地址简称", "所在地点"};
					retFields = new String[]{"ADDRESS_ID", "WORKORDER_OBJECT_NAME"};
					viewPercent = new String[]{"18%", "35%", "40%"};
//                    qryFieldNames = new String[]{"ADDRESS_ID", "WORKORDER_OBJECT_NAME"};
//                    qryFieldLabels = new String[]{"组合地址", "地址名称"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"地点编号", "地点简称", "所在地点"};
					primaryKeys = new String[]{"ADDRESS_ID"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
					forwardURL = WebConstant.LOOK_UP_SERVLET;
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_CT_ADDRESS)) {
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"地址编号", "地址简称", "所在地点"};
					retFields = new String[]{"ADDRESS_ID", "WORKORDER_OBJECT_NAME"};
					viewPercent = new String[]{"18%", "35%", "40%"};
//                    qryFieldNames = new String[]{"ADDRESS_ID", "WORKORDER_OBJECT_NAME"};
//                    qryFieldLabels = new String[]{"组合地址", "地址名称"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"地点编号", "地点简称", "所在地点"};
					primaryKeys = new String[]{"ADDRESS_ID"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
					forwardURL = WebConstant.LOOK_UP_SERVLET;
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_PROJECT2)) { //仅仅供应基站地点的明细页面查找项目的需要
					dispFieldNames = new String[]{"SEGMENT1", "PROJECT_NAME", "PROJECT_TYPE"};
					dispFieldLabels = new String[]{"项目代码", "项目名称", "项目类型"};
					retFields = new String[]{"PROJECT_ID", "PROJECT_NAME","SEGMENT1"};
					viewPercent = new String[]{"10%", "60%", "30%"};
					qryFieldNames = new String[]{"SEGMENT1", "NAME"};
					qryFieldLabels = new String[]{"项目代码", "项目名称"};
					primaryKeys = new String[]{"PROJECT_ID",};

					lookProp.setTotalWidth(800);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsPaProjectsAllDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_OBJECT)) { //资产业务查找地点
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"地点代码", "地点名称"};
					retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "WORKORDER_OBJECT_NO"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"地点代码", "地点名称"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsObjectDTO.class);
					
				}  else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_OBJECT2)) { //查询备件仓库地点
                    lookProp.setMultipleChose(false);
                    dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
                    dispFieldLabels = new String[]{"地点代码", "地点名称"};
                    retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "WORKORDER_OBJECT_NO"};
                    viewPercent = new String[]{"40%", "50%"};
                    qryFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
                    qryFieldLabels = new String[]{"地点代码", "地点名称"};
                    primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};
                    lookProp.setTotalWidth(750);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(EtsObjectDTO.class);
                    
                } else if (lookUpName.equals(LookUpConstant.LOOK_UP_TF_ASSETS_OBJECT)) { //通服资产业务查找地点
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"地点代码", "地点名称"};
					retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "WORKORDER_OBJECT_NO"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"地点代码", "地点名称"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsObjectDTO.class);
					
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_CT_ASSETS_OBJECT)) { //村通资产业务查找地点
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"地点代码", "地点名称"};
					retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "WORKORDER_OBJECT_NO"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"地点代码", "地点名称"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_RCV)) { //资产业务查找目标保管人
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"LOGIN_NAME", "USERNAME"};
					dispFieldLabels = new String[]{"登陆名", "用户名"};
					retFields = new String[]{"USER_ID", "USERNAME"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"LOGIN_NAME", "USERNAME"};
					qryFieldLabels = new String[]{"登陆名", "用户名"};
					primaryKeys = new String[]{"USER_ID"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(SfUserDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOKE_UP_BEIJIAN)) {
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_AMOUNT"};
					dispFieldLabels = new String[]{"备件名称", "规格型号", "数量"};
					retFields = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_AMOUNT", "ITEM_CODE"};
					viewPercent = new String[]{"40%", "40%", "10%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"备件名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsBjsAllotDDto.class);
				} else if (lookUpName.equals(LookUpConstant.LOOKE_UP_BEIJIAN2)) {  //备件分配用(方案2)
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_AMOUNT"};
					dispFieldLabels = new String[]{"设备名称", "规格型号", "数量"};
					retFields = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_AMOUNT", "ITEM_CODE"};
					viewPercent = new String[]{"40%", "40%", "10%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsBjsAllotDDto.class);
				} else if (lookUpName.equals(LookUpConstant.SELECT_BARCODE)) {
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "BARCODE"};
					dispFieldLabels = new String[]{"备件名称", "规格型号", "条码"};
					retFields = new String[]{"BARCODE"};
					viewPercent = new String[]{"40%", "40%", "20%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"备件名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsBjsAllotDDto.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_FH)) {
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ITEM_QTY"};
					dispFieldLabels = new String[]{"备件条码", "备件名称", "规格型号", "数量"};
					retFields = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ITEM_QTY", "ITEM_CODE"};
					viewPercent = new String[]{"20%", "30%", "40%", "10%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"备件名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsBjsAllotDDto.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_FH2)) {
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"部件号", "设备名称", "规格型号"};
					retFields = new String[]{"BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC"};
					viewPercent = new String[]{"20%", "35%", "40%"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"部件号", "设备名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsItemTransLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_ITEM_BF)) {
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ITEM_QTY"};
					dispFieldLabels = new String[]{"备件条码", "备件名称", "规格型号", "数量"};
					retFields = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ITEM_QTY", "ITEM_CODE"};
					viewPercent = new String[]{"20%", "30%", "40%", "10%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"备件名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsBjsAllotDDto.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_ITEM_BF2)) {
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY"};
					dispFieldLabels = new String[]{"部件号", "设备名称", "规格型号", "现有量"};
					retFields = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY", "ITEM_CODE"};
					viewPercent = new String[]{"20%", "30%", "40%", "10%"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"部件号", "设备名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsItemTransLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_SYSITEM)) { //查找设备名称、规格
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"设备名称", "规格型号"};
					retFields = new String[]{"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_DEPT)) {//资产业务，查找目的部门
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"GROUP_CODE", "GROUPNAME"};
					dispFieldLabels = new String[]{"组别代码", "组别名称"};
					retFields = new String[]{"GROUP_ID", "GROUPNAME"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"GROUP_CODE", "GROUPNAME"};
					qryFieldLabels = new String[]{"组别代码", "组别名称"};
					primaryKeys = new String[]{"GROUP_ID"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(GroupDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJ_SYSTEM_ITEM)) {//新购入库,选择设备
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_UNIT"};
					dispFieldLabels = new String[]{"设备名称", "规格型号", "单位"};
					viewPercent = new String[]{"35%", "40%", "10%"};
					retFields = new String[]{"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "ITEM_UNIT"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJ_SYSTEM_ITEM_SX)) {//新购入库,选择设备--用于山西
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME", "ITEM_UNIT"};
					dispFieldLabels = new String[]{"设备名称", "规格型号", "设备厂商", "单位"};
					viewPercent = new String[]{"25%", "30%", "25%", "10%"};
					retFields = new String[]{"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME", "ITEM_UNIT"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJ_SPARE_CATEGORY)) {//新购入库/工程移交,选择备件
					lookProp.setMultipleChose(true);
                    dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY", "SPARE_USAGE", "VENDOR_NAME", "ITEM_UNIT"};
                    dispFieldLabels = new String[]{"设备名称", "设备型号", "设备类型", "用途", "厂商", "单位"};
                    viewPercent = new String[]{"10%", "15%", "10%", "10%", "10%", "5%"};
                    retFields = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY", "SPARE_USAGE", "VENDOR_NAME", "ITEM_UNIT", "QUANTITY"};
                    qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY", "SPARE_USAGE", "VENDOR_NAME"};
                    qryFieldLabels = new String[]{"设备名称", "设备型号", "设备类型", "用途", "厂商"};
                    primaryKeys = new String[]{"BARCODE"};

                    lookProp.setTotalWidth(1010);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(AmsSpareCategoryDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJ_ITEM_CATEGORY)) {//新购入库,选择设备  包含部件号
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME", "ITEM_UNIT"};
					dispFieldLabels = new String[]{"部件号", "设备名称", "规格型号", "设备厂商", "单位"};
					viewPercent = new String[]{"15%", "20%", "30%", "20%", "5%"};
					retFields = new String[]{"BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME", "ITEM_UNIT"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"部件号", "设备名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsSpareInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJ_ITEM_CATEGORY3)) {//新购入库,选择设备  包含物料编码
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME", "ITEM_UNIT"};
					dispFieldLabels = new String[]{"物料编码", "设备名称", "规格型号", "设备厂商", "单位"};
					viewPercent = new String[]{"15%", "20%", "30%", "20%", "5%"};
					retFields = new String[]{"BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME", "ITEM_UNIT"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"物料编码", "设备名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsSpareInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJ_ITEM_CATEGORY_SX)) {//新购入库,选择设备  包含部件号--用于山西
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME", "ITEM_UNIT"};
					dispFieldLabels = new String[]{"部件号", "设备名称", "规格型号", "设备厂商", "单位"};
					viewPercent = new String[]{"15%", "20%", "30%", "20%", "5%"};
					retFields = new String[]{"BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME", "ITEM_UNIT"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"部件号", "设备名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsSpareInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJSL_ITEM_INFO)) {//备件申领查找标签号
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"标签号", "设备名称", "规格型号"};
					viewPercent = new String[]{"25%", "35%", "40%"};
					retFields = new String[]{"BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsItemTransLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJSL_ITEM_INFO2)) {//备件申领查找标签号(方案2)
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY"};
					dispFieldLabels = new String[]{"部件号", "设备名称", "规格型号", "现有量"};
					viewPercent = new String[]{"20%", "25%", "40%", "10%"};
					retFields = new String[]{"BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY", "OBJECT_NO"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"部件号", "设备名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsItemTransLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJSL_ITEM_INFO3)) {//备件申领查找设备(NM)
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY"};
					dispFieldLabels = new String[]{"物料编码", "设备名称", "规格型号", "现有量"};
					viewPercent = new String[]{"20%", "25%", "40%", "10%"};
					retFields = new String[]{"BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY", "OBJECT_NO"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"物料编码", "设备名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsItemTransLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_HOUSE)) {//选择房屋类型
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"房屋名称", "房屋型号"};
					retFields = new String[]{"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC"};
					viewPercent = new String[]{"20%", "40%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"房屋名称", "房屋型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setTotalWidth(550);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsHouseInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.FLOW_AGENT_USER)) { //查找流程代理人
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"LOGIN_NAME", "USERNAME"};
					dispFieldLabels = new String[]{"登陆名", "用户名"};
					retFields = new String[]{"USER_ID", "USERNAME"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"LOGIN_NAME", "USERNAME"};
					qryFieldLabels = new String[]{"登陆名", "用户名"};
					primaryKeys = new String[]{"USER_ID"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(SfUserDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_ITEM_SIMPLE)) { //设备类别信息（单选）
					primaryKeys = new String[]{"ITEM_CODE"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "设备型号"};
					viewPercent = new String[]{"20%", "50%", "30%"};
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "VALUE"};
					dispFieldLabels = new String[]{"设备名称", "设备型号", "设备类别"};
					retFields = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CODE", "VALUE"};

					lookProp.setTotalWidth(800);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(false);
					lookProp.setDtoClass(AmsItemVersionDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_VENDORS)) { //供应商
					primaryKeys = new String[]{"VENDOR_ID"};
					qryFieldNames = new String[]{"VENDOR_ID", "VENDOR_NAME"};
					qryFieldLabels = new String[]{"供应商代码", "供应商名称"};
					viewPercent = new String[]{"50%", "50%"};
					dispFieldNames = new String[]{"VENDOR_ID", "VENDOR_NAME"};
					dispFieldLabels = new String[]{"供应商代码", "供应商名称"};
					retFields = new String[]{"VENDOR_ID", "VENDOR_NAME"};
					lookProp.setTotalWidth(800);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(false);
					lookProp.setDtoClass(EquipStatDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_LAND)) {//选择土地类型
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"名称", "类型"};
					retFields = new String[]{"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC"};
					viewPercent = new String[]{"20%", "40%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"土地名称", "土地类型"};
					primaryKeys = new String[]{"ITEM_CODE"};
					lookProp.setTotalWidth(550);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsLandInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_DAY)) {//租赁日期短信提醒设置查找截至日期
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "END_DATE"};
					dispFieldLabels = new String[]{"租赁资产条码", "名称", "描述", "截至日期"};
					retFields = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "END_DATE"};
					viewPercent = new String[]{"20%", "20%", "20%", "20%"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME"};
					qryFieldLabels = new String[]{"租赁资产条码", "名称"};
					primaryKeys = new String[]{"BARCODE"};
					lookProp.setCalPattern(CalendarConstant.LINE_PATTERN);
					lookProp.setTotalWidth(550);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsRentDeadlineDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_BARCODENO)) {//其它资产维护查找租赁资产条码信息
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"租赁资产条码", "名称", "描述", "地点编号", "所在地点"};
					retFields = new String[]{"SYSTEM_ID", "BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC"};
					viewPercent = new String[]{"15%", "15%", "15%", "15%", "35%"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"租赁资产条码", "名称", "地点编号", "所在地点"};
					primaryKeys = new String[]{"BARCODE"};
					lookProp.setTotalWidth(800);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsHouseInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_CABEL)) { //线缆信息
					primaryKeys = new String[]{"ITEM_CODE"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					viewPercent = new String[]{"20%", "50%", "30%"};
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY"};
					dispFieldLabels = new String[]{"类型名称", "规格型号", "设备类别"};
					retFields = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CODE", "ITEM_CATEGORY"};
					lookProp.setTotalWidth(800);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(false);
					lookProp.setDtoClass(AmsCabelInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOKE_UP_PORTAGE)) { //查找搬运地点信息
					primaryKeys = new String[]{"ATTRIBUTE1"};
					qryFieldNames = new String[]{"TRANS_OBJECT_CODE", "TRANS_OBJECT_NAME"};
					qryFieldLabels = new String[]{"地点编号", "地点简称"};
					viewPercent = new String[]{"20%", "50%", "30%"};
					dispFieldNames = new String[]{"TRANS_OBJECT_CODE", "TRANS_OBJECT_NAME", "VALUE"};
					dispFieldLabels = new String[]{"地点编号", "地点简称", "专业地点"};
					retFields = new String[]{"ATTRIBUTE1", "TRANS_OBJECT_CODE", "TRANS_OBJECT_NAME"};
					lookProp.setTotalWidth(800);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(false);
					lookProp.setDtoClass(EtsWorkorderTmpDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_BATCH)) {
					dispFieldNames = new String[]{"WORKORDER_BATCH", "WORKORDER_BATCH_NAME"};
					dispFieldLabels = new String[]{"任务号", "任务名称"};
					retFields = new String[]{"WORKORDER_BATCH", "WORKORDER_BATCH_NAME"};
					viewPercent = new String[]{"50%", "50%"};
					qryFieldNames = new String[]{"WORKORDER_BATCH", "WORKORDER_BATCH_NAME"};
					qryFieldLabels = new String[]{"任务号", "任务名称"};
					primaryKeys = new String[]{"WORKORDER_BATCH"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(false);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsWorkorderDTO.class);
				} else if (lookUpName.equals(LookUpConstant.FTMCK_OBJECT_NO)) {
					dispFieldNames = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"名称", "地点"};
					retFields = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					viewPercent = new String[]{"50%", "50%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"名称", "地点"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(false);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if (lookUpName.equals(LookUpConstant.FTMCK_ITEM)) {
					dispFieldNames = new String[]{"BATCH_NO", "ITEM_NAME", "ITEM_SPEC", "QUANTITY", "ITEM_UNIT"};
					dispFieldLabels = new String[]{"批次", "设备名称", "规格型号", "数量", "单位"};
					viewPercent = new String[]{"10%", "30%", "30%", "10%", "5%"};
					retFields = new String[]{"LINE_ID", "BATCH_NO", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "QUANTITY", "ITEM_UNIT","NOW_QTY"};
					qryFieldNames = new String[]{"BATCH_NO", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"批次", "设备名称", "规格型号"};
					primaryKeys = new String[]{"LINE_ID"};

					lookProp.setTotalWidth(850);
					lookProp.setMemorySpan(false);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(AmsInvStorageDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_PROJECT3)) {
					dispFieldNames = new String[]{"SEGMENT1", "PROJECT_NAME", "PROJECT_TYPE"};
					dispFieldLabels = new String[]{"项目代码", "项目名称", "项目类型"};
					retFields = new String[]{"PROJECT_ID", "SEGMENT1"};
					viewPercent = new String[]{"10%", "60%", "30%"};
					qryFieldNames = new String[]{"SEGMENT1", "NAME"};
					qryFieldLabels = new String[]{"项目代码", "项目名称"};
					primaryKeys = new String[]{"PROJECT_ID"};

					lookProp.setTotalWidth(800);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsPaProjectsAllDTO.class);
				}  else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_LOCATION)) {
					dispFieldNames = new String[]{"ASSETS_LOCATION_CODE", "ASSETS_LOCATION"};
					dispFieldLabels = new String[]{"MIS地点代码", "MIS地点描述"};
					retFields = new String[]{"ASSETS_LOCATION", "ASSETS_LOCATION_CODE"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"ASSETS_LOCATION_CODE","ASSETS_LOCATION"};
					qryFieldLabels = new String[]{"MIS地点代码","MIS地点描述"};
					primaryKeys = new String[]{"ASSETS_LOCATION"};

					lookProp.setTotalWidth(750);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsFaAssetsDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_TD_ASSETS_LOCATION)) {
					dispFieldNames = new String[]{"ASSETS_LOCATION_CODE", "ASSETS_LOCATION"};
					dispFieldLabels = new String[]{"MIS地点代码", "MIS地点描述"};
					retFields = new String[]{"ASSETS_LOCATION", "ASSETS_LOCATION_CODE"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"ASSETS_LOCATION_CODE","ASSETS_LOCATION"};
					qryFieldLabels = new String[]{"MIS地点代码","MIS地点描述"};
					primaryKeys = new String[]{"ASSETS_LOCATION"};

					lookProp.setTotalWidth(750);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsFaAssetsDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_CT_ASSETS_LOCATION)) {
					dispFieldNames = new String[]{"ASSETS_LOCATION_CODE", "ASSETS_LOCATION"};
					dispFieldLabels = new String[]{"MIS地点代码", "MIS地点描述"};
					retFields = new String[]{"ASSETS_LOCATION", "ASSETS_LOCATION_CODE"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"ASSETS_LOCATION_CODE","ASSETS_LOCATION"};
					qryFieldLabels = new String[]{"MIS地点代码","MIS地点描述"};
					primaryKeys = new String[]{"ASSETS_LOCATION"};

					lookProp.setTotalWidth(750);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsFaAssetsDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_ADDRESS)) {
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "HOUSE_ADDRESS"};
					dispFieldLabels = new String[]{"地点代码", "地址名称"};
					retFields = new String[]{"ADDRESS_ID", "HOUSE_ADDRESS"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME"};
					qryFieldLabels = new String[]{"地点代码", "地址名称"};
					primaryKeys = new String[]{"ADDRESS_ID"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
					forwardURL = WebConstant.LOOK_UP_SERVLET;
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_EXACT_EQUIP)) {//选择正式设备
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"ITEM_TYPE", "ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"设备分类", "设备名称", "规格型号"};
					viewPercent = new String[]{"20%", "30%", "40%"};
					retFields = new String[]{"ITEM_CODE", "ITEM_CATEGORY", "ITEM_TYPE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setTotalWidth(600);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_INSTRUMENT)) {//仪器仪表
					dispFieldNames = new String[]{"ORGNIZATION_NAME", "BARCODE", "ITEM_NAME", "ITEM_SPEC", "RESPONSIBILITY_NAME", "VENDOR_NAME", "ADDRESSLOC"};
					dispFieldLabels = new String[]{"公司", "仪器仪表条码", "仪器仪表名称", "规格型号", "责任人", "供应商", "地点"};
					retFields = new String[]{"ITEM_CODE", "BARCODE", "INSTRU_USAGE", "ITEM_NAME", "VENDOR_NAME", "ITEM_SPEC", "RESPONSIBILITY_NAME"};
					viewPercent = new String[]{"12%", "12%", "16%", "15%", "10%", "15%", "20%"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME"};
					qryFieldLabels = new String[]{"条码", "名称", "型号", "供应商"};
					primaryKeys = new String[]{"BARCODE"};
					lookProp.setTotalWidth(950);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsInstrumentInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_USER1)) { //查找人
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"LOGIN_NAME", "USERNAME"};
					dispFieldLabels = new String[]{"登陆名", "用户名"};
					retFields = new String[]{"USER_ID", "USERNAME"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"LOGIN_NAME", "USERNAME"};
					qryFieldLabels = new String[]{"登陆名", "用户名"};
					primaryKeys = new String[]{"USER_ID"};
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(SfUserDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_INSTR_ITEM)) {//选仪器仪表
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_UNIT", "VENDOR_NAME"};
					dispFieldLabels = new String[]{"设备名称", "规格型号", "单位", "供应商"};
					viewPercent = new String[]{"25%", "35%", "10%", "30%"};
					retFields = new String[]{"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "ITEM_UNIT", "VENDOR_ID", "VENDOR_NAME"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME"};
					qryFieldLabels = new String[]{"设备名称", "规格型号", "供应商"};
					primaryKeys = new String[]{"ITEM_CODE"};
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_RETURN)) {//仪器仪表归还人
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "RESPONSIBILITY_NAME", "BORROW_DATE"};
					dispFieldLabels = new String[]{"仪仪表条码", "仪器仪表名称", "规格型号", "责任人", "借用日期"};
					retFields = new String[]{"ITEM_CODE", "BARCODE", "ITEM_NAME", "VENDOR_NAME", "ITEM_SPEC", "RESPONSIBILITY_NAME", "INSTRU_USAGE"};
					viewPercent = new String[]{"15%", "20%", "20%", "20%", "20%"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME"};
					qryFieldLabels = new String[]{"仪表条码", "仪表名称", "规格型号", "供应商"};
					primaryKeys = new String[]{"BARCODE"};
					lookProp.setTotalWidth(950);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsInstrumentInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_CITYINSTR)) {//查找地市仪器仪表管理员
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"USER_NAME", "COMPANY_NAME"};
					dispFieldLabels = new String[]{"用户名称", "公司名称"};
					retFields = new String[]{"USER_ID", "ORGANIZATION_ID", "ADDRESS_ID", "USER_NAME", "COMPANY_NAME"};
					viewPercent = new String[]{"20%", "40%"};
					qryFieldNames = new String[]{"USER_NAME", "COMPANY_NAME"};
					qryFieldLabels = new String[]{"用户名称", "公司名称"};
					primaryKeys = new String[]{"USER_ID"};
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsInstrumentInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJSX_ITEM_INFO2)) {//备件送修查找设备(方案2) herry
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"部件号", "设备名称", "规格型号"};
					retFields = new String[]{"BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "ONHAND_QTY"};
					viewPercent = new String[]{"20%", "35%", "40%"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"部件号", "设备名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsItemTransLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJSX_ITEM_INFO3)) {//备件送修查找设备(NM) herry 2008-03-17
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "DISREPAIR_QUANTITY"};
					dispFieldLabels = new String[]{"物料编码", "设备名称", "规格型号", "待修数量"};
					retFields = new String[]{"BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "DISREPAIR_QUANTITY"};
					viewPercent = new String[]{"15%", "25%", "30%", "10%"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"物料编码", "设备名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsSpareInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_RESPUSER)) {//查找责任人
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"USERNAME", "RESPONSIBILITY_DEPT"};
					dispFieldLabels = new String[]{"用户名称", "责任部门"};
					retFields = new String[]{"USER_ID", "USERNAME", "DEPT_ID", "RESPONSIBILITY_DEPT"};
					viewPercent = new String[]{"20%", "40%"};
					qryFieldNames = new String[]{"USERNAME", "RESPONSIBILITY_DEPT"};
					qryFieldLabels = new String[]{"用户名称", "责任部门"};
					primaryKeys = new String[]{"USER_ID"};
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsInstrumentInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_VENRETURN)) {//仪器仪表厂商返还
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "REPAIRE_DATE"};
					dispFieldLabels = new String[]{"仪器仪表条码", "仪器仪表名称", "规格型号", "送修日期"};
					retFields = new String[]{"ITEM_CODE", "BARCODE", "ITEM_NAME", "ITEM_SPEC", "RESPONSIBILITY_NAME", "INSTRU_USAGE"};
					viewPercent = new String[]{"20%", "25%", "25%", "25%"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"仪器仪表条码", "仪器仪表名称", "规格型号"};
					primaryKeys = new String[]{"BARCODE"};
					lookProp.setTotalWidth(950);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsInstrumentInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.SPARE_RETURN)) {//备件归还(NM) ADDED BY HERRY 2008-3-13
					dispFieldNames = new String[]{"BATCH_NO", "BARCODE", "ITEM_NAME", "ITEM_SPEC", "QUANTITY", "RESPECT_RETURN_DATE", "RETURN_QTY"};
					dispFieldLabels = new String[]{"调拨单号", "物料编码", "设备名称", "规格型号", "借用数量", "预计归还日期", "已归还数量"};
					viewPercent = new String[]{"10%", "15%", "20%", "20%", "10%", "10%", "10%"};
					retFields = new String[]{"BATCH_NO", "BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC", "QUANTITY", "RESPECT_RETURN_DATE", "RETURN_QTY", "STORAGE_ID"};
					qryFieldNames = new String[]{"BATCH_NO", "BARCODE", "ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"调拨单号", "物料编码", "设备名称", "规格型号"};
					primaryKeys = new String[]{"STORAGE_ID"};
					lookProp.setTotalWidth(950);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(SpareReturnDTO.class);
				} else if (lookUpName.equals(LookUpConstant.SPARE_LOAN_OBJECT)) {//备件借出仓库和OU(NM) ADDED BY HERRY 2008-3-13
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"COMPANY", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"公司", "仓库名称", "仓库地点"};
					viewPercent = new String[]{"30%", "30%", "36%"};
					retFields = new String[]{"COMPANY", "ORGANIZATION_ID", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "WORKORDER_OBJECT_NO"};
					qryFieldNames = new String[]{"COUNTY_NAME", "WORKORDER_OBJECT_NAME"};
					qryFieldLabels = new String[]{"公司", "仓库名称"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};
					lookProp.setTotalWidth(750);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_SYSITEM)) { //查找管理资产设备名称、规格  ADDED BY ai 2008-3-25
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"设备名称", "规格型号"};
					retFields = new String[]{"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
					
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_ASSETS_SYSITEM_S)) { //查找管理资产设备名称、规格，单选用
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					dispFieldLabels = new String[]{"设备名称", "规格型号"};
					retFields = new String[]{"ITEM_CODE", "ITEM_NAME", "ITEM_SPEC"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC"};
					qryFieldLabels = new String[]{"设备名称", "规格型号"};
					primaryKeys = new String[]{"ITEM_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
					
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_MIS_USER)) {  //查找MIS用户
					dispFieldNames = new String[]{"EMPLOYEE_NUMBER", "USER_NAME"};
					dispFieldLabels = new String[]{"员工编号", "员工姓名"};
					retFields = new String[]{"EMPLOYEE_ID", "EMPLOYEE_NUMBER", "USER_NAME"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"EMPLOYEE_NUMBER", "USER_NAME"};
					qryFieldLabels = new String[]{"员工编号", "员工姓名"};
					primaryKeys = new String[]{"EMPLOYEE_ID"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsMisEmployeeDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_MIS_DEPT)) {  //查找MIS部门
					dispFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					dispFieldLabels = new String[]{"部门代码", "部门名称"};
					retFields = new String[]{"COMPANY_CODE", "DEPT_CODE", "DEPT_NAME"};
					viewPercent = new String[]{"35%", "55%"};
					qryFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					qryFieldLabels = new String[]{"部门代码", "部门名称"};
					primaryKeys = new String[]{"DEPT_CODE"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsMisDeptDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_SPECIALITY_DEPT)) {  //查找专业管理部门
					//String workorderNo = req.getParameter("workorderNo");
					dispFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					dispFieldLabels = new String[]{"部门代码", "部门名称"};
					retFields = new String[]{"DEPT_CODE", "DEPT_NAME"};
					viewPercent = new String[]{"35%", "55%"};
					qryFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					qryFieldLabels = new String[]{"部门代码", "部门名称"};
					primaryKeys = new String[]{"DEPT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsWorkorderDTO.class);
				}else if (lookUpName.equals(LookUpConstant.LOOK_UP_BJBF)) {//备件报废
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "SPARE_USAGE", "VENDOR_NAME", "ONHAND_QTY"};
					dispFieldLabels = new String[]{"部件号", "设备名称", "规格型号", "设备类别", "设备厂商", "可用量"};
					viewPercent = new String[]{"15%", "18%", "20%", "17%", "20%", "10%"};
					retFields = new String[]{"SPARE_ID", "BARCODE", "ITEM_NAME", "ITEM_SPEC", "SPARE_USAGE", "VENDOR_NAME", "ONHAND_QTY", "QUANTITY", "OBJECT_NO"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "SPARE_USAGE", "VENDOR_NAME"};
					qryFieldLabels = new String[]{"部件号", "设备名称", "规格型号", "设备类别", "厂商"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(1010);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsSpareCategoryDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_FXSQ)) {
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "SPARE_USAGE", "VENDOR_NAME", "ITEM_UNIT"};
					dispFieldLabels = new String[]{"部件号", "备件名称", "规格型号", "设备类别", "设备厂商", "单位"};
					viewPercent = new String[]{"15%", "20%", "20%", "15%", "15%", "10%"};
					retFields = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "SPARE_USAGE", "VENDOR_NAME", "ONHAND_QTY", "ITEM_UNIT", "QUANTITY"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "SPARE_USAGE", "VENDOR_NAME"};
					qryFieldLabels = new String[]{"部件号", "设备名称","规格型号", "设备类别",  "设备厂商"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(1010);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsSpareCategoryDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_DB)) {
                    lookProp.setMultipleChose(true);
                    dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY", "SPARE_USAGE", "VENDOR_NAME", "ITEM_UNIT"};
                    dispFieldLabels = new String[]{"设备名称", "设备型号", "设备类型", "用途", "厂商", "单位"};
                    viewPercent = new String[]{"10%", "15%", "10%", "10%", "10%", "5%"};
                    retFields = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY", "SPARE_USAGE", "VENDOR_NAME", "ONHAND_QTY", "ITEM_UNIT", "QUANTITY"};
                    qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY", "SPARE_USAGE", "VENDOR_NAME"};
                    qryFieldLabels = new String[]{"设备名称", "设备型号", "设备类型", "用途", "厂商"};
                    primaryKeys = new String[]{"BARCODE"};
                    lookProp.setTotalWidth(1010);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(AmsSpareCategoryDTO.class);
                } else if (lookUpName.equals(LookUpConstant.LOOK_UP_FXRK)) {//备件返修入库
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "SPARE_USAGE", "VENDOR_NAME", "ONHAND_QTY"};
					dispFieldLabels = new String[]{"部件号", "设备名称", "规格型号", "设备类别", "设备厂商", "数量"};
					viewPercent = new String[]{"15%", "20%", "20%", "15%", "20%", "10%"};
					retFields = new String[]{"SPARE_ID", "BARCODE", "ITEM_NAME", "ITEM_SPEC", "SPARE_USAGE", "VENDOR_NAME", "ONHAND_QTY", "QUANTITY", "OBJECT_NO"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "SPARE_USAGE", "VENDOR_NAME"};
					qryFieldLabels = new String[]{"部件号", "设备名称",  "规格型号","设备类别", "厂商"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(1010);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsSpareCategoryDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_DEPT1)) {
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					dispFieldLabels = new String[]{"部门代码", "部门名称"};
					viewPercent = new String[]{"30%", "60%"};
					retFields = new String[]{"DEPT_CODE", "DEPT_NAME"};
					qryFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					qryFieldLabels = new String[]{"部门代码", "部门名称"};
					primaryKeys = new String[]{"DEPT_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AssetsTagNumberQueryDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_TASK)) {
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"WORKORDER_BATCH", "WORKORDER_BATCH_NAME"};
					dispFieldLabels = new String[]{"任务编号", "任务名称"};
					viewPercent = new String[]{"30%", "60%"};
					retFields = new String[]{"WORKORDER_BATCH", "WORKORDER_BATCH_NAME"};
					qryFieldNames = new String[]{"WORKORDER_BATCH", "WORKORDER_BATCH_NAME"};
					qryFieldLabels = new String[]{"任务编号", "任务名称"};
					primaryKeys = new String[]{"BATCH_ID"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AssetsTagNumberQueryDTO.class);

				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_BJSX)) {//备件送修
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC","SPARE_USAGE", "VENDOR_NAME", "ONHAND_QTY"};
					dispFieldLabels = new String[]{"部件号", "设备名称", "规格型号","设备类别", "设备厂商", "数量"};
					viewPercent = new String[]{"15%", "20%", "20%", "15%","20%", "10%"};
					retFields = new String[]{"SPARE_ID", "BARCODE", "ITEM_NAME", "ITEM_SPEC", "SPARE_USAGE", "VENDOR_NAME", "ONHAND_QTY", "QUANTITY", "OBJECT_NO"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "SPARE_USAGE", "VENDOR_NAME"};
					qryFieldLabels = new String[]{"部件号", "设备名称", "规格型号", "设备类别", "厂商"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(1010);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsSpareCategoryDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_BJWXC)) {
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"VENDOR_NAME", "ADDRESS", "CONTACT", "PHONE", "FAX"};
					dispFieldLabels = new String[]{"供应商名称", "供应商地址", "联系人", "电话", "传真"};
					viewPercent = new String[]{"15%", "30%", "10%", "10%", "10%"};
					retFields = new String[]{"VENDOR_CODE", "VENDOR_NAME", "ADDRESS", "CONTACT", "PHONE", "FAX"};
					qryFieldNames = new String[]{"VENDOR_NAME", "ADDRESS", "CONTACT"};
					qryFieldLabels = new String[]{"供应商名称", "设备名称", "联系人"};
					primaryKeys = new String[]{"VENDOR_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsVendorInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_RESPONSIBILITY)) {//查找责任人
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"USERNAME", "RESPONSIBILITY_DEPT"};
					dispFieldLabels = new String[]{"用户名称", "责任部门"};
					retFields = new String[]{"USER_ID", "USERNAME", "DEPT_ID", "RESPONSIBILITY_DEPT"};
					viewPercent = new String[]{"20%", "40%"};
					qryFieldNames = new String[]{"USERNAME", "RESPONSIBILITY_DEPT"};
					qryFieldLabels = new String[]{"用户名称", "责任部门"};
					primaryKeys = new String[]{"USER_ID"};
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsInstrumentInfoDTO.class);
				} else if (lookUpName.equals(LookUpConstant.BJ_SPARE_CATEGORY1)) {//查找备件设备类别--用于山西，AMS_SPARE_CATEGORY中  ,单选形式
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY", "SPARE_USAGE", "VENDOR_NAME"};
					dispFieldLabels = new String[]{"部件号", "备件名称", "规格型号", "设备类别", "用途", "设备厂商"};
					viewPercent = new String[]{ "10%", "12%", "15%", "10%", "10%", "15%"};
					retFields = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY", "SPARE_USAGE", "VENDOR_NAME"};
					qryFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "VENDOR_NAME"};
					qryFieldLabels = new String[]{"部件号", "设备名称", "规格型号", "设备厂商"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(850);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsSpareCategoryDTO.class);
				  } else if (lookUpName.equals(LookUpConstant.LOOK_UP_MIS_INFO)) {//查找责任人
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"USER_NAME", "DEPT_NAME"};
					dispFieldLabels = new String[]{"用户名称", "责任部门"};
					retFields = new String[]{"EMPLOYEE_ID", "USER_NAME", "DEPT_CODE", "DEPT_NAME"};
					viewPercent = new String[]{"20%", "40%"};
					qryFieldNames = new String[]{"USER_NAME", "DEPT_NAME"};
					qryFieldLabels = new String[]{"用户名称", "责任部门"};
					primaryKeys = new String[]{"EMPLOYEE_ID"};
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsMisEmployeeDTO.class);
				   } else if (lookUpName.equals(LookUpConstant.LOOK_MAINTAIN_DEPT)) {//资产业务，查找维护部门
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"GROUP_ID", "GROUP_NAME"};
					dispFieldLabels = new String[]{"部门代码", "部门名称"};
					retFields = new String[]{"GROUP_ID", "GROUP_NAME"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"GROUP_ID", "GROUP_NAME"};
					qryFieldLabels = new String[]{"部门代码", "部门名称"};
					primaryKeys = new String[]{"GROUP_ID"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(GroupDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_USER_ID)) { //选择仓管员
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"USER_ID", "USER_NAME"};
					dispFieldLabels = new String[]{"仓管员ID", "仓管员姓名"};
					retFields = new String[]{"USER_ID", "USER_NAME"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"USER_ID", "USER_NAME"};
					qryFieldLabels = new String[]{"仓管员ID", "仓管员姓名"};
					primaryKeys = new String[]{"USER_ID"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(SfUserDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_UPDATED_USER)) { //选择修改人
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
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_WORKORDER_OBJECT_NO)) { //选择仓库
					dispFieldNames = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"仓库代码", "仓库名称", "仓库地点"};
					retFields = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					viewPercent = new String[]{"30%", "30%", "30%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_NO", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"仓库代码", "仓库名称", "仓库地点"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamInvStoremanDTO.class);
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
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_ITEM_CATEGORY2)) {//查找目录编号
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"CATALOG_CODE", "CATALOG_NAME", "UNIT"};
					dispFieldLabels = new String[]{"目录编号", "品名", "计量单位"};
					retFields = new String[]{"CATALOG_CODE", "CATALOG_NAME", "UNIT"};
					viewPercent = new String[]{"30%", "40%", "20%"};
					qryFieldNames = new String[]{"CATALOG_CODE", "CATALOG_NAME"};
					qryFieldLabels = new String[]{"目录编号", "品名"};
					primaryKeys = new String[]{"CATALOG_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_RESPONSIBILITY_DEPT)) {//查找使用部门
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					dispFieldLabels = new String[]{"部门代码", "部门名称"};
					retFields = new String[]{"DEPT_CODE", "DEPT_NAME"};
					viewPercent = new String[]{"30%", "40%"};
					qryFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					qryFieldLabels = new String[]{"部门代码", "部门名称"};
					primaryKeys = new String[]{"DEPT_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
					
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_RESPONSIBILITY_DEPT_OU)) {//查找责任部门，过滤OU
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					dispFieldLabels = new String[]{"部门代码", "部门名称"};
					retFields = new String[]{"DEPT_CODE", "DEPT_NAME"};
					viewPercent = new String[]{"30%", "40%"};
					qryFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					qryFieldLabels = new String[]{"部门代码", "部门名称"};
					primaryKeys = new String[]{"DEPT_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
					
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_RESPONSIBILITY_DEPT_OU2)) {//查找责任部门，过滤OU
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					dispFieldLabels = new String[]{"部门代码", "部门名称"};
					retFields = new String[]{"DEPT_CODE", "DEPT_NAME"};
					viewPercent = new String[]{"30%", "40%"};
					qryFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					qryFieldLabels = new String[]{"部门代码", "部门名称"};
					primaryKeys = new String[]{"DEPT_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
					
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_RESPONSIBILITY_USER)) {//查找领用人
					dispFieldNames = new String[]{"EMPLOYEE_ID", "EMPLOYEE_NUMBER", "USER_NAME"};
					dispFieldLabels = new String[]{"员工ID", "员工编号", "领用人姓名"};
					retFields = new String[]{"EMPLOYEE_ID", "EMPLOYEE_NUMBER", "USER_NAME"};
					viewPercent = new String[]{"30%", "30%", "30%"};
					qryFieldNames = new String[]{"EMPLOYEE_ID", "EMPLOYEE_NUMBER", "USER_NAME"};
					qryFieldLabels = new String[]{"员工ID", "员工编号", "领用人姓名"};
					primaryKeys = new String[]{"EMPLOYEE_ID"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EamDhBillLDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_COUNTY)) {//查找成本中心
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"COUNTY_CODE", "COUNTY_NAME"};
					dispFieldLabels = new String[]{"成本中心代码", "成本中心名称"};
					retFields = new String[]{"COUNTY_CODE", "COUNTY_NAME"};
					viewPercent = new String[]{"30%", "40%"};
					qryFieldNames = new String[]{"COUNTY_CODE", "COUNTY_NAME"};
					qryFieldLabels = new String[]{"成本中心代码", "成本中心名称"};
					primaryKeys = new String[]{"COUNTY_CODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsCountyDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_RES_DEPT)) {
                    lookProp.setMultipleChose(true);
                    dispFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
                    dispFieldLabels = new String[]{"部门代码", "部门名称"};
                    retFields = new String[]{"DEPT_CODE", "DEPT_NAME"};
                    viewPercent = new String[]{"20%", "50%"};
                    qryFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
                    qryFieldLabels = new String[]{"部门代码", "部门名称"};
                    primaryKeys = new String[]{"DEPT_CODE"};

                    lookProp.setTotalWidth(750);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(EamDhBillLDTO.class);
                } else if (lookUpName.equals(LookUpConstant.LOOK_UP_BF2)) {
                    dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
                    dispFieldLabels = new String[]{"地址编号", "地址简称", "所在地点"};
                    retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "ADDRESS_ID", "WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NO"};
                    viewPercent = new String[]{"10%", "40%", "40%"};
                    qryFieldNames = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
                    qryFieldLabels = new String[]{"地址简称", "所在地点"};
                    primaryKeys = new String[]{"ADDRESS_ID"};
                    lookProp.setMultipleChose(false);
                    lookProp.setTotalWidth(750);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(AmsBjsAllotDDto.class);
                }else if (lookUpName.equals(LookUpConstant.LOOK_UP_CHAN_MIS_DEPT)) {  //查找MIS部门
					dispFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					dispFieldLabels = new String[]{"部门代码", "部门名称"};
					retFields = new String[]{"COMPANY_CODE", "DEPT_CODE", "DEPT_NAME"};
					viewPercent = new String[]{"35%", "55%"};
					qryFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME"};
					qryFieldLabels = new String[]{"部门代码", "部门名称"};
					primaryKeys = new String[]{"DEPT_CODE"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsMisDeptDTO.class);
				}else if (lookUpName.equals(LookUpConstant.LOOK_UP_DEPT_IN)) { //资产业务查找地点
					lookProp.setMultipleChose(false);
					dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
					dispFieldLabels = new String[]{"地点代码", "地点名称"};
					retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "WORKORDER_OBJECT_NO"};
					viewPercent = new String[]{"40%", "50%"};
					qryFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
					qryFieldLabels = new String[]{"地点代码", "地点名称"};
					primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsObjectDTO.class);
				}else if (lookUpName.equals(LookUpConstant.LOOK_UP_DEPT_OUT)) {
					lookProp.setMultipleChose(true);
					dispFieldNames = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC","WORKORDER_OBJECT_NAME"};
					dispFieldLabels = new String[]{"设备条码", "设备名称", "规格型号","仓库名称"};
					viewPercent = new String[]{"25%", "25%", "20%","30%"};
					retFields = new String[]{"BARCODE", "ITEM_CODE", "ITEM_NAME", "ITEM_SPEC","EMPLOYEE_ID","USER_NAME","RESPONSIBILITY_DEPT","DEPT_NAME"};
					qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC","WORKORDER_OBJECT_NAME"};
					qryFieldLabels = new String[]{"设备名称", "规格型号","仓库名称"};
					primaryKeys = new String[]{"BARCODE"};

					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsItemTransLDTO.class);
				}else if (lookUpName.equals(LookUpConstant.LOOK_UP_TEAM)) {
                    lookProp.setMultipleChose(false);
                    dispFieldNames = new String[]{"TEAM_ID", "TEAM_NAME", "ORGNIZATION_NAME"};
                    dispFieldLabels = new String[]{"队伍号", "队伍名称", "公司名称"};
                    retFields = new String[]{"TEAM_ID", "TEAM_NAME",
                            "ORGNIZATION_NAME"};
                    viewPercent = new String[]{"20%", "40%", "40%"};
                    qryFieldNames = new String[]{"TEAM_ID", "TEAM_NAME"};
                    qryFieldLabels = new String[]{"队伍号", "队伍名称"};
                    primaryKeys = new String[]{"TEAM_ID"};

                    lookProp.setTotalWidth(750);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(AmsYjTeamDTO.class);
                } else if (lookUpName.equals(LookUpConstant.LOOK_UP_CK)) {   //仓库查询
                    dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
                    dispFieldLabels = new String[]{"地址编号", "地点简称", "所在地点"};
                    retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "ADDRESS_ID", "WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NO"};
                    viewPercent = new String[]{"10%", "40%", "40%"};
                    qryFieldNames = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
                    qryFieldLabels = new String[]{"地址简称", "所在地点"};
                    primaryKeys = new String[]{"ADDRESS_ID"};
                    lookProp.setMultipleChose(false);
                    lookProp.setTotalWidth(750);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(AmsBjsAllotDDto.class);
                } else if (lookUpName.equals(LookUpConstant.LOOK_UP_LOC2DESC)) { //选择地点第二段代码
					dispFieldNames = new String[]{"LOC2_CODE", "LOCATION"};
					dispFieldLabels = new String[]{"物理地点代码", "物理地点描述"};
//					retFields = new String[]{"LOC2_CODE", "LOCATION"};
					viewPercent = new String[]{"35%", "65%"};
					qryFieldNames = new String[]{"LOC2_CODE","LOCATION"};
					qryFieldLabels = new String[]{"物理地点代码","物理地点描述"};
					primaryKeys = new String[]{"LOC2_CODE",};

					lookProp.setTotalWidth(600);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_COUNTYCODE)) { //选择所属区域
					dispFieldNames = new String[]{"COUNTY_CODE", "COUNTY_NAME", "COMPANY"};
					dispFieldLabels = new String[]{"所属区域代码", "描述", "公司名称"};
					retFields = new String[]{"COUNTY_CODE", "COUNTY_NAME", "COMPANY"};
					viewPercent = new String[]{"20%", "40%", "40%"};
					qryFieldNames = new String[]{"COUNTY_CODE","COUNTY_NAME"};
					qryFieldLabels = new String[]{"区域代码","区域描述"};
					primaryKeys = new String[]{"COUNTY_CODE",};

					lookProp.setTotalWidth(600);
					lookProp.setMultipleChose(false);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if (lookUpName.equals(LookUpConstant.LOOK_UP_HJGH_OBJECT)) {
                    lookProp.setMultipleChose(false);
                    dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
                    dispFieldLabels = new String[]{"地点代码", "地点名称"};
                    retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "WORKORDER_OBJECT_NO"};
                    viewPercent = new String[]{"40%", "50%"};
                    qryFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_LOCATION"};
                    qryFieldLabels = new String[]{"地点代码", "地点名称"};
                    primaryKeys = new String[]{"WORKORDER_OBJECT_NO"};

                    lookProp.setTotalWidth(750);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(EtsObjectDTO.class);
                } else if (lookUpName.equals(LookUpConstant.BJ_SPARE_DX_CATEGORY)) {
                    lookProp.setMultipleChose(true);
                    dispFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY", "SPARE_USAGE", "VENDOR_NAME", "ITEM_UNIT"};
                    dispFieldLabels = new String[]{"设备名称", "设备型号", "设备类型", "用途", "厂商", "单位"};
                    viewPercent = new String[]{"10%", "15%", "10%", "10%", "10%", "5%"};
                    retFields = new String[]{"BARCODE", "ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY", "SPARE_USAGE", "VENDOR_NAME", "ITEM_UNIT", "QUANTITY", "ONHAND_QTY"};
                    qryFieldNames = new String[]{"ITEM_NAME", "ITEM_SPEC", "ITEM_CATEGORY", "SPARE_USAGE", "VENDOR_NAME"};
                    qryFieldLabels = new String[]{"设备名称", "设备型号", "设备类型", "用途", "厂商"};
                    primaryKeys = new String[]{"BARCODE"};
                    lookProp.setTotalWidth(1010);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(AmsSpareCategoryDTO.class);
                } else if (lookUpName.equals(LookUpConstant.LOOK_UP_BJCK)) {
                    dispFieldNames = new String[]{"WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
                    dispFieldLabels = new String[]{"地址编号", "地址简称", "所在地点"};
                    retFields = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION", "ADDRESS_ID", "WORKORDER_OBJECT_CODE", "WORKORDER_OBJECT_NO"};
                    viewPercent = new String[]{"10%", "40%", "40%"};
                    qryFieldNames = new String[]{"WORKORDER_OBJECT_NAME", "WORKORDER_OBJECT_LOCATION"};
                    qryFieldLabels = new String[]{"地址简称", "所在地点"};
                    primaryKeys = new String[]{"ADDRESS_ID"};
                    lookProp.setMultipleChose(false);
                    lookProp.setTotalWidth(750);
                    lookProp.setMemorySpan(true);
                    lookProp.setDtoClass(AmsBjsAllotDDto.class);
                }
                else if (lookUpName.equals("LOOK_UP_DEPT_TRUNLIST")) {//查找责任部门
                	String multipleChose = req.getParameter("multipleChose");
                    if(!StrUtil.isEmpty(multipleChose) && multipleChose.equalsIgnoreCase("true")){
                        lookProp.setMultipleChose(true);
                    } else {
                        lookProp.setMultipleChose(false);
                    }
				dispFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME", "COST_CENTER_NAME"};
				dispFieldLabels = new String[]{"部门代码", "部门名称", "成本中心"};
				retFields = new String[]{"DEPT_CODE", "DEPT_NAME", "COST_CENTER_NAME"};
				viewPercent = new String[]{"20%", "50%", "30%"};
				qryFieldNames = new String[]{"DEPT_CODE", "DEPT_NAME", "COST_CENTER_CODE"};
				qryFieldLabels = new String[]{"部门代码", "部门名称", "成本中心"};
				primaryKeys = new String[]{"DEPT_CODE"};

				lookProp.setTotalWidth(750);
				lookProp.setMemorySpan(true);
				lookProp.setDtoClass(EtsWorkorderDTO.class);
			}
				lookProp.setDisFieldNames(dispFieldNames);
				lookProp.setDisFieldLabels(dispFieldLabels);
				lookProp.setRetFields(retFields);
				lookProp.setViewPercent(viewPercent);
				lookProp.setQryFieldNames(qryFieldNames);
				lookProp.setQryFieldLabels(qryFieldLabels);
				lookProp.setPrimaryKeys(primaryKeys);
				lookProp.setModelClass(AMSLookUpModel.class);
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
	
	/**
	 * 下拉选项 - SJ ADD
	 * @param req
	 * @param res
	 * @param lookupName
	 * @throws PoolException
	 * @throws QueryException
	 */
	public void setOptions( HttpServletRequest req, HttpServletResponse res , String lookupName ) throws PoolException, QueryException{
		Connection conn = null;
		try {
			conn = DBManager.getDBConnection();
			SfUserDTO user = (SfUserDTO) SessionUtil.getUserAccount( req );
			OptionProducer op = new OptionProducer( user , conn );
			
			if( lookupName.equals( LookUpConstant.LOOK_UP_PROJECT ) ){
				String projectTypeOpt = op.getDictOption3( "PROJECT_TYPE" , "" , true );
				req.setAttribute( "PROJECT_TYPE_OPT" , projectTypeOpt );
			}
		} catch (PoolException e) {
			throw e;
		} catch (QueryException e) {
			throw e;
		}finally{
			DBManager.closeDBConnection( conn );
		}
	}
}
