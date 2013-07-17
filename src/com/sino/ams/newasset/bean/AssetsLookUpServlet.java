package com.sino.ams.newasset.bean;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sino.ams.bean.OptionProducer;
import com.sino.ams.constant.LookUpConstant;
import com.sino.ams.newasset.assetsharing.dto.AssetSharingLineDTO;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.constant.AssetsLookUpConstant;
import com.sino.ams.newasset.constant.AssetsMessageKeys;
import com.sino.ams.newasset.dto.AmsAccountVDTO;
import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.ams.newasset.dto.AmsAssetsPriviDTO;
import com.sino.ams.newasset.dto.AmsAssetsTransLineDTO;
import com.sino.ams.newasset.dto.AmsCexDTO;
import com.sino.ams.newasset.dto.AmsFaCategoryVDTO;
import com.sino.ams.newasset.dto.AmsLneDTO;
import com.sino.ams.newasset.dto.AmsMisCostDTO;
import com.sino.ams.newasset.dto.AmsMisEmployeeDTO;
import com.sino.ams.newasset.dto.AmsNleDTO;
import com.sino.ams.newasset.dto.AmsOpeDTO;
import com.sino.ams.newasset.dto.AmsSnDTO;
import com.sino.ams.newasset.lease.dto.LeaseLineDTO;
import com.sino.ams.newasset.rolequery.dto.SfRoleQueryDTO;
import com.sino.ams.plan.dto.AmsWorkPlanDTO;
import com.sino.ams.system.basepoint.dto.EtsObjectDTO;
import com.sino.ams.system.cost.dto.CostCenterDTO;
import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.ams.system.item.dto.EtsSystemItemDTO;
import com.sino.ams.system.procedure.dto.MisDeptDTO;
import com.sino.ams.system.project.dto.EtsPaProjectsAllDTO;
import com.sino.ams.system.user.dto.EtsOuCityMapDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.ams.workorder.dto.ZeroTurnLineDTO;
import com.sino.ams.yearchecktaskmanager.dto.EtsObjectTaskDTO;
import com.sino.base.constant.message.MessageConstant;
import com.sino.base.constant.web.WebConstant;
import com.sino.base.db.conn.DBManager;
import com.sino.base.exception.PoolException;
import com.sino.base.exception.QueryException;
import com.sino.base.log.Logger;
import com.sino.base.lookup.LookUpProp;
import com.sino.base.message.Message;
import com.sino.base.util.StrUtil;
import com.sino.base.web.ServletForwarder;
import com.sino.framework.security.bean.SessionUtil;
import com.sino.framework.servlet.BaseServlet;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统：资产管理模块的LookUpServlet</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class AssetsLookUpServlet extends BaseServlet {
	/**
	 * 所有的Servlet都必须实现的方法。
	 *
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performTask(HttpServletRequest req, HttpServletResponse res) throws
			ServletException, IOException {
		String forwardURL = "";
		String lookUpName = req.getParameter("lookUpName");
		lookUpName = StrUtil.nullToString(lookUpName);
		Message message = SessionUtil.getMessage(req);
		try {
			if (lookUpName.equals("")) {
				message = getMessage(AssetsMessageKeys.INVALID_REQ);
				message.setIsError(true);
				message.setNeedClose(true);
				forwardURL = MessageConstant.MSG_PRC_SERVLET;
			} else {
				LookUpProp lookProp = new LookUpProp(lookUpName);
				String[] dispNames = null;
				String[] retFields = null;
				String[] dispLabels = null;
				String[] viewPercent = null;
				String[] qryNames = null;
				String[] qryLabels = null;
				String[] primaryKeys = null;
                String contentReadio = "";
                if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_DEPT)) { //选择接收部门
					dispNames = new String[] {"COMPANY_CODE", "TO_COMPANY_NAME", "TO_DEPT_NAME"};
					dispLabels = new String[] {"公司代码", "公司名称", "部门名称"};
					viewPercent = new String[] {"8%", "30%", "58%"};
					retFields = new String[] {"TO_ORGANIZATION_ID",
								"COMPANY_CODE", "TO_COMPANY_NAME", "TO_DEPT",
								"TO_DEPT_NAME","TO_GROUP"};
					qryNames = new String[] {"COMPANY_NAME", "DEPT_NAME"};
					qryLabels = new String[] {"公司名称", "部门名称"};
					primaryKeys = new String[] {"TO_DEPT"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(MisDeptDTO.class);
				}else if(lookUpName.equals(AssetsLookUpConstant.LOOK_UP_DEPT_NM)){ //必须指定专业资产管理员的部门 选择接收部门
					dispNames = new String[] {"COMPANY_CODE", "TO_COMPANY_NAME",
								"TO_DEPT_NAME"};
					dispLabels = new String[] {"公司代码", "公司名称", "部门名称"};
					viewPercent = new String[] {"8%", "30%", "58%"};
					retFields = new String[] {"TO_ORGANIZATION_ID",
								"COMPANY_CODE", "TO_COMPANY_NAME", "TO_DEPT",
								"TO_DEPT_NAME"};
					qryNames = new String[] {"COMPANY_NAME", "DEPT_NAME"};
					qryLabels = new String[] {"公司名称", "部门名称"};
					primaryKeys = new String[] {"TO_DEPT"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(MisDeptDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_ADDRESS)) { //资产业务查找地点
					dispNames = new String[] {"WORKORDER_OBJECT_CODE",
								"TO_OBJECT_NAME",/* "WORKORDER_OBJECT_LOCATION",*/
								"COUNTY_NAME", "COMPANY"};
					dispLabels = new String[] {"地点编码", "地点名称", "所在区县",
								 "公司名称"};
					viewPercent = new String[] {"16%",  "36%", "16%", "12%"};
					qryNames = new String[] {"WORKORDER_OBJECT_CODE",
							   "WORKORDER_OBJECT_NAME"};
					qryLabels = new String[] {"地点编码", "地点名称"};
					primaryKeys = new String[] {"TO_OBJECT_NO"};

					lookProp.setTotalWidth(875);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_RECIIVER)) { //资产业务查找目标责任人
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"DEPT_NAME", "COMPANY"};
					dispLabels = new String[] {"员工姓名", "员工工号", "部门名称", "公司名称"};
					viewPercent = new String[] {"14%", "13%", "45%", "25%"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER"};
					qryLabels = new String[] {"员工姓名", "员工工号"};
					primaryKeys = new String[] {"EMPLOYEE_ID"};
					lookProp.setTotalWidth(746);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsMisEmployeeDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_ASSETS)) { //资产流程查找资产
					dispNames = new String[] {"BARCODE", "ASSET_NUMBER",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
								"RESPONSIBILITY_USER_NAME", "DEPT_NAME"};
					dispLabels = new String[] {"资产标签", "资产编号", "资产名称", "资产型号",
								 "原值", "启用日期", "累计折旧", "责任人", "责任部门"};
					viewPercent = new String[] {"12%", "8%", "17%", "14%", "7%",
								  "10%", "7%", "8%", "14%"};
					qryNames = new String[] {"ITEM_NAME", "BARCODE"};
					retFields = dispNames;
					qryLabels = new String[] {"描述关键字", "标签号"};
					primaryKeys = new String[] {"BARCODE"};

					lookProp.setTotalWidth(820);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				}else if(lookUpName.equals(AssetsLookUpConstant.LOOK_UP_RENT)){
					 dispNames = new String[] {"BARCODE","ITEM_NAME","ITEM_SPEC",
								"ITEM_QTY", "USER_NAME", "DEPT_NAME"};
					dispLabels = new String[] {"资产标签", "资产名称", "资产型号","数量", "责任人", "责任部门"};
					viewPercent = new String[] {"10%", "10%","15%","8%", "8%","20%"};
					qryNames = new String[] {"ITEM_NAME", "BARCODE",
							   "ITEM_SPEC", "USER_NAME"};
					qryLabels = new String[] {"资产名称", "标签号", "规格型号", "责任人"};
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(820);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_TRANSFER_ASSETS )
						|| lookUpName.equals(AssetsLookUpConstant.
													 LOOK_TRANSFER_DIS_ASSETS ) ) { //资产调拨流程查找资产
//					dispNames = new String[] {"BARCODE", "ASSET_NUMBER", "ITEM_NAME","ITEM_SPEC",
//							"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
//							"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
//							"OLD_RESPONSIBILITY_USER_NAME",
//							"OLD_RESPONSIBILITY_DEPT_NAME","OLD_LOCATION_CODE",
//							"OLD_LOCATION_NAME", "MANUFACTURER_NAME" };
//					dispLabels = new String[] {"资产标签", "资产编号", "设备名称", "设备型号", "资产名称", "资产型号",
//							 "原值", "启用日期", "净值", "责任人", "责任部门", "地点编号", "地点简称", "厂商" };
//					viewPercent = new String[] {"8%", "4%","5%","5%", "5%",   "5%", "4%", "4%", "4%", "3%",    "9%", "8%", "9%","9%" };
				
					dispNames = new String[] {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME", "SPECIALITY_DEPT_NAME", "OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME","IMPORTANT_FLAG"};
					dispLabels = new String[] {"资产标签", "资产编号", "设备名称", "设备型号", "资产名称", "资产型号",
								 "原值", "启用日期", "净值","责任人", "责任部门", "实物管理部门", "地点编号", "地点简称", "厂商", "重要资产"};
					viewPercent = new String[] {"8%", "4%","5%","5%", "5%", "5%", "4%", "4%", "4%", "3%", "9%", "8%", "8%", "9%","9%","4%"};
                    retFields = new String[]  {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST","SCRAP_VALUE",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME", "SPECIALITY_DEPT_NAME", "OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME","DEPRN_LEFT_MONTH","SUM_DEPRECIATION","IMPAIR_RESERVE","RETIREMENT_COST","CURRENT_UNITS","OLD_FA_CATEGORY_CODE","OLD_RESPONSIBILITY_USER_NAME","OLD_DEPRECIATION_ACCOUNT","OLD_LOCATION","OLD_RESPONSIBILITY_USER","OLD_RESPONSIBILITY_DEPT","IMPORTANT_FLAG","UNIT_OF_MEASURE"};
                    if( lookUpName.equals(AssetsLookUpConstant.
											 LOOK_TRANSFER_ASSETS ) ){
                    	qryNames = new String[] {"ASSETS_DESCRIPTION", "BARCODE",
 							   "OLD_RESPONSIBILITY_USER_NAME", "OLD_LOCATION_CODE"};
                    	qryLabels = new String[] {"资产名称", "标签号", "责任人", "地点"};
                    }else{
                    	qryNames = new String[] {"ASSETS_DESCRIPTION", "BARCODE",  "OLD_LOCATION_CODE"};
                    	qryLabels = new String[] {"资产名称", "标签号",  "地点"};
                    }
                    
					
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(800);
					
					String transferType = StrUtil.nullToString( req.getParameter( "transferType" ) );
					if( transferType.equals( AssetsDictConstant.TRANS_BTW_COMP ) ){
						lookProp.setCountPages( false );
					}
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
					
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_TRANSFER_ASSETS_RFU )) { //查找调拨资产(紧急调拨补汇总单据用)
					dispNames = new String[] {
								"TRANS_NO",  
								"BARCODE", "ASSET_NUMBER", "ASSETS_DESCRIPTION", "MODEL_NUMBER", "CURRENT_UNITS", "UNIT_OF_MEASURE", 
								"FROM_OBJECT_NAME", "TO_OBJECT_NAME"};
					dispLabels = new String[] {
								"调拨单号", 
								"标签号", "资产编号", "资产名称", "资产型号", "数量", "单位",
								"调出地点", "调入地点"};
					viewPercent = new String[] {
								"14%", 
								"8%", "7%", "12%", "10%", "8%", "5%", 
								"18%", "18%"};
					retFields = new String[]  {
								"REMARK", "OLD_LOCATION", "ASSIGNED_TO_LOCATION", "OLD_RESPONSIBILITY_DEPT", 
								"RESPONSIBILITY_DEPT", "OLD_RESPONSIBILITY_USER", "RESPONSIBILITY_USER", 
								"TRANS_TYPE_DEFINE", "TRANS_ID", "TRANS_NO", 
								"BARCODE", "ASSET_ID", "ASSET_NUMBER", "ASSETS_DESCRIPTION", "MODEL_NUMBER", "CURRENT_UNITS", "UNIT_OF_MEASURE", 
								"FROM_OBJECT_NO", "TO_OBJECT_NO", "FROM_OBJECT_NAME", "TO_OBJECT_NAME"};
					
					qryNames = new String[] {"TRANS_NO", "ASSETS_DESCRIPTION", "BARCODE", "FROM_OBJECT_NAME"};
					qryLabels = new String[] {"调拨单号", "资产名称", "标签号", "地点"};
					primaryKeys = new String[] {"TRANS_NO", "BARCODE"};
					lookProp.setTotalWidth(800);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);

				}else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_TRANSFER_ITEM)) { //资产调拨流程查找资产
					dispNames = new String[] {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME","OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME"};
					dispLabels = new String[] {"资产标签", "资产编号", "设备名称", "设备型号", "资产名称", "资产型号",
								 "原值", "启用日期", "净值", "责任人", "责任部门", "地点编号", "地点简称", "厂商"};
					viewPercent = new String[] {"8%", "4%","6%","6%", "6%", "6%", "4%", "4%", "4%", "3%", "10%", "8%", "15%","9%"};
                    qryNames = new String[] {"ASSETS_DESCRIPTION", "BARCODE", "OLD_LOCATION_CODE","OLD_RESPONSIBILITY_USER_NAME"};
					qryLabels = new String[] {"资产名称", "标签号", "地点","责任人"};
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(850);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_TRANSFER_ASSETS_TD)) { //TD资产调拨流程查找资产
					dispNames = new String[] {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME", "SPECIALITY_DEPT_NAME", "OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME"};
					dispLabels = new String[] {"资产标签", "资产编号", "设备名称", "设备型号", "资产名称", "资产型号",
								 "原值", "启用日期", "净值", "责任人", "责任部门", "实物管理部门", "地点编号", "地点简称", "厂商"};
					viewPercent = new String[] {"8%", "4%","6%","6%", "6%", "6%", "4%", "4%", "4%", "3%", "10%", "8%", "8%", "10%","9%"};
                    retFields = new String[]  {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
								"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
								"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME","OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME","DEPRN_LEFT_MONTH","SUM_DEPRECIATION","IMPAIR_RESERVE","RETIREMENT_COST","CURRENT_UNITS","OLD_FA_CATEGORY_CODE","OLD_RESPONSIBILITY_USER_NAME","OLD_DEPRECIATION_ACCOUNT","OLD_LOCATION","OLD_RESPONSIBILITY_USER","OLD_RESPONSIBILITY_DEPT"};
                    qryNames = new String[] {"ASSETS_DESCRIPTION", "BARCODE",
							   "OLD_RESPONSIBILITY_USER_NAME", "OLD_LOCATION_CODE"};
					qryLabels = new String[] {"资产名称", "标签号", "责任人", "地点"};
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(850);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				}else if(lookUpName.equals(AssetsLookUpConstant.LOOK_SPECIAL_ASSETS)){//特殊资产
					dispNames = new String[]{"BARCODE","ASSETS_TYPE_NAME","ASSETS_DESCRIPTION","MODEL_NUMBER","ITEM_QTY","ITEM_STATUS_NAME",
							"ITEM_CATEGORY_NAME","OLD_RESPONSIBILITY_DEPT_NAME","OLD_RESPONSIBILITY_USER_NAME","OLD_LOCATION_CODE","OLD_LOCATION_NAME","MANUFACTURER_NAME"};
					dispLabels = new String[]{"资产标签","资产类型","资产名称","资产型号","数量","资产状态",
							"资产专业","责任部门","责任人","地点代码","地点名称","厂商"};
					viewPercent = new String[]{"5%", "4%","6%","6%", "6%", "6%", "4%", "4%", "10%", "8%", "10%","15%"};
					qryNames = new String[] {"ITEM_NAME", "BARCODE",
							   "RESPONSIBILITY_USER_NAME", "WORKORDER_OBJECT_CODE"};
					qryLabels = new String[] {"资产名称", "标签号", "责任人", "地点代码"};
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(820);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_COMPANY)) { //资产流程查找公司
					
					String isMult = req.getParameter("multipleChose");
					boolean multipleChose = false;
					if( !StrUtil.isEmpty( isMult ) ){
						 multipleChose = Boolean.parseBoolean( isMult );
					}
					
					dispNames = new String[] {"COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"公司代码", "公司名称"};
					viewPercent = new String[] {"25%", "50%"};
					retFields = new String[] {"ORGANIZATION_ID", "COMPANY_CODE",
								"COMPANY_NAME"};
					qryNames = new String[] {"COMPANY_NAME"};
					qryLabels = new String[] {"公司名称"};
					primaryKeys = new String[] {"ORGANIZATION_ID"};
					lookProp.setTotalWidth(440);
					lookProp.setMultipleChose( multipleChose );
					lookProp.setDtoClass(EtsOuCityMapDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_MIS_COMPANY)) { //资产流程查找公司
					
					String isMult = req.getParameter("multipleChose");
					boolean multipleChose = false;
					if( !StrUtil.isEmpty( isMult ) ){
						 multipleChose = Boolean.parseBoolean( isMult );
					}
					
					dispNames = new String[] {"COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"公司代码", "公司名称"};
					viewPercent = new String[] {"25%", "50%"};
					retFields = new String[] {"ORGANIZATION_ID", "COMPANY_CODE",
								"COMPANY_NAME"};
					qryNames = new String[] {"COMPANY_NAME"};
					qryLabels = new String[] {"公司名称"};
					primaryKeys = new String[] {"ORGANIZATION_ID"};
					lookProp.setTotalWidth(440);
					lookProp.setMultipleChose( multipleChose );
					lookProp.setDtoClass(EtsOuCityMapDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_PRI_DEPT)) { //资产流程查找部门
					dispNames = new String[] {"COMPANY_CODE", "COMPANY_NAME",
								"DEPT_CODE", "DEPT_NAME"};
					dispLabels = new String[] {"公司代码", "公司名称", "部门代码", "部门名称"};
					viewPercent = new String[] {"15%", "30%", "15%", "35%"};
					retFields = dispNames;
					qryNames = new String[] {"COMPANY_NAME", "DEPT_NAME"};
					qryLabels = new String[] {"公司名称", "部门名称"};
					primaryKeys = new String[] {"DEPT_CODE"};
					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_PRI_ROLE)) { //资产流程查找角色
					dispNames = new String[] {"ROLE_NAME", "ROLE_DESC"};
					dispLabels = new String[] {"角色名称", "角色描述"};
					viewPercent = new String[] {"48%", "48%"};
					retFields = dispNames;
					qryNames = new String[] {"ROLE_NAME"};
					qryLabels = new String[] {"角色名称"};
					primaryKeys = new String[] {"ROLE_ID"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER)) { //资产流程查找用户
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"用户姓名", "用户员工号", "用户登录名", "公司代码",
								 "公司名称"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"姓名", "员工号", "登录名"};
					primaryKeys = new String[] {"USER_ID"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);					
				}
				else if(lookUpName.equals(LookUpConstant.LOOK_UP_USER_WITH_DEPT))
				{
					dispNames = new String[]{"USER_NAME","EMPLOYEE_NUMBER","LOGIN_NAME","DEPT_NAME","GROUP_NAME"};
					dispLabels = new String[]{"用户姓名","用户员工号","登陆名","部门","组名"};
					retFields = new String[]{"USER_ID", "USER_NAME","EMPLOYEE_NUMBER","LOGIN_NAME", "DEPT_NAME","DEPT_CODE","GROUP_NAME","GROUP_ID"};
					viewPercent = new String[]{"15%", "15%","15%","27%","28%"};
					qryNames = new String[]{"USER_NAME","EMPLOYEE_NUMBER","LOGIN_NAME"};
					qryLabels = new String[]{"姓名","员工号","登陆名" };
					primaryKeys = new String[]{"USER_ID"};

					lookProp.setMultipleChose(false);
					lookProp.setTotalWidth(750);
					lookProp.setMemorySpan(true);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);								
				}
				else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER_CHECK_BATCH)) { //资产盘点单任务批，选择归档人
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"用户姓名", "用户员工号", "用户登录名", "公司代码",
								 "公司名称"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"姓名", "员工号", "登录名"};
					primaryKeys = new String[] {"USER_ID"};
	
					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				
			} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ZERO_USER)) { //资产流程查找用户
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
				dispLabels = new String[] {"用户姓名", "用户员工号", "用户登录名", "公司代码",
							 "公司名称"};
				viewPercent = new String[] {"15%", "30%", "15%", "15%",
							  "20%"};
				retFields = new String[] {"USER_ID", "COMPANY_CODE",
							"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME"};
				qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
						   "LOGIN_NAME"};
				qryLabels = new String[] {"姓名", "员工号", "登录名"};
				primaryKeys = new String[] {"USER_ID"};

				lookProp.setTotalWidth(700);
				lookProp.setMultipleChose(false);
				lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				
			}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER_ZERO_ACHIEVE)) { //归档人
				dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
						"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
				dispLabels = new String[] {"用户姓名", "用户员工号", "用户登录名", "公司代码",
							 "公司名称"};
				viewPercent = new String[] {"15%", "30%", "15%", "15%",
							  "20%"};
				retFields = new String[] {"USER_ID", "COMPANY_CODE",
							"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME"};
				qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
						   "LOGIN_NAME"};
				qryLabels = new String[] {"姓名", "员工号", "登录名"};
				primaryKeys = new String[] {"USER_ID"};
	
				lookProp.setTotalWidth(700);
				lookProp.setMultipleChose(false);
				lookProp.setDtoClass(AmsAssetsPriviDTO.class);
			
			}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER_ACHIEVE)) { //归档人
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"用户姓名", "用户员工号", "用户登录名", "公司代码",
								 "公司名称"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"姓名", "员工号", "登录名"};
					primaryKeys = new String[] {"USER_ID"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
					
				} else if (lookUpName.equals("LOOK_UP_SAMPLING_USER")) { //抽查工单中选择执行人
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"用户姓名", "用户员工号", "用户登录名", "公司代码",
								 "公司名称"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"姓名", "员工号", "登录名"};
					primaryKeys = new String[] {"USER_ID"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				} else if(lookUpName.equals(AssetsLookUpConstant.LOOK_UP_USER_BY_ORGANAZATION)){ //得到指定OU下的用户
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"用户姓名", "用户员工号", "用户登录名", "公司代码",
								 "公司名称"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"姓名", "员工号", "登录名"};
					primaryKeys = new String[] {"USER_ID"};
	
					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);					
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_PERSON)) { //资产业务查找人员以作分配
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
								"DEPT_NAME", "COMPANY"};
					dispLabels = new String[] {"员工姓名", "员工编号", "部门名称", "公司名称"};
					viewPercent = new String[] {"14%", "13%", "45%", "25%"};
					qryNames = new String[] {"USERNAME", "EMPLOYEE_NUMBER"};
					qryLabels = new String[] {"员工姓名", "员工编号"};
					
					retFields = new String[] { "USER_NAME", "DEPT_NAME" , "DEPT_CODE", "EMPLOYEE_ID" , "EMPLOYEE_NUMBER" , "COMPANY" , "ORGANIZATION_ID" };
					
					primaryKeys = new String[] {"EMPLOYEE_ID"};

					lookProp.setTotalWidth(746);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(SfUserDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_LOCATION)) { //查找地点
					dispNames = new String[] {"COMPANY_NAME", "COUNTY_NAME",
								"OBJECT_CATEGORY", "OBJECT_CODE", "OBJECT_NAME"};
					dispLabels = new String[] {"公司名称", "成本中心", "地点专业", "地点代码","地点名称"};
					viewPercent = new String[] {"10%", "19%", "10%", "18%", "40%"};
					retFields = new String[] {"CHECK_LOCATION", "OBJECT_CODE",	"OBJECT_NAME"};
					qryNames = new String[] {"WORKORDER_OBJECT_CODE",
							   "WORKORDER_OBJECT_NAME", "COUNTY_NAME"};
					qryLabels = new String[] {"地点代码", "地点名称", "成本中心"};
					primaryKeys = new String[] {"CHECK_LOCATION"};

//                    contentReadio = "Y";

                    lookProp.setTotalWidth(950);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(EtsObjectDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_FACAT_CODE)) {
					dispNames = new String[] {"FA_CAT_CODE_1", "FA_CAT_NAME_1",
								"FA_CAT_CODE_2", "FA_CAT_NAME_2",
								"FA_CAT_CODE_3", "FA_CAT_NAME_3"};
					dispLabels = new String[] {"类别1代码", "类别1名称", "类别2代码",
								 "类别2名称", "类别3代码", "类别3名称"};
					viewPercent = new String[] {"10%", "15%", "10%", "30%",
								  "10%", "20%"};
					qryNames = new String[] {"FA_CAT_NAME_1", "FA_CAT_NAME_2",
							   "FA_CAT_NAME_3"};
					qryLabels = new String[] {"类别1名称", "类别2名称", "类别3名称"};
					primaryKeys = new String[] {"CONTENT_CODE"};

					lookProp.setTotalWidth(850);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsFaCategoryVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_ACCOUNT)) { //查找某部门下的地点以下盘点工单
					dispNames = new String[] {"ACCOUNT_NAME_1","ACCOUNT_NAME_2","ACCOUNT_NAME_3",
								"ACCOUNT_NAME_4", "ACCOUNT_NAME_5",
								"ACCOUNT_NAME_6", "ACCOUNT_NAME_7"};
					dispLabels = new String[] {"公司段","成本中心段","会计科目段", "品牌段", "项目段", "内部往来段",
								 "备用段"};
					viewPercent = new String[] {"15%", "15%", "33%", "8%", "8%", "8%", "8%"};
					qryNames = new String[] {"ACCOUNT_NAME_2", "ACCOUNT_NAME_3"};
					qryLabels = new String[] {"成本中心段", "会计科目段"};
					primaryKeys = new String[] {"ACCOUNT_CODE"};
					retFields = new String[] {"ACCOUNT_CODE", "ACCOUNT_NAME"};
					lookProp.setTotalWidth(800);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAccountVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.
											 LOOK_UP_VENDOR)) { //查找某部门下的地点以下盘点工单
					dispNames = new String[] {"VENDOR_NAME", "VENDOR_NUMBER",
								"VENDOR_NAME_ALT"};
					dispLabels = new String[] {"厂商名称", "厂商编号", "厂商别名"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					viewPercent = new String[] {"30%", "30%", "30%"};
					primaryKeys = new String[] {"VENDOR_ID"};
					retFields = dispNames;
					lookProp.setTotalWidth(600);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				}else if (lookUpName.equals("PROJECT_NO")) {           //项目编号
					dispNames = new String[] {"PROJECT_NUMBER","MIS_PROJECT_ID", "PROJECT_NAME","PROJECT_TYPE"};
					dispLabels = new String[] {"项目编号","MIS项目Id", "项目名称", "项目类型"};
					retFields = new String[] {"PROJECT_NUMBER", "MIS_PROJECT_ID", "PROJECT_NAME"};
					viewPercent = new String[] {"10%", "10%", "50%", "30%"};
					qryNames = new String[] {"PROJECT_NUMBER","MIS_PROJECT_ID", "NAME","PROJECT_TYPE"};
					qryLabels = new String[] {"项目编号","MIS项目Id", "项目名称", "项目类型"};
					primaryKeys = new String[] {"PROJECT_ID"};
			
					lookProp.setTotalWidth(700);
			        String multipleChose = req.getParameter("multipleChose");
			        if(!StrUtil.isEmpty(multipleChose) && multipleChose.equalsIgnoreCase("true")){
			            lookProp.setMultipleChose(true);
			        } else {
			            lookProp.setMultipleChose(false);
			        }
					lookProp.setDtoClass(EtsPaProjectsAllDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_PROJECT)) {
					 
//					this.setOptions(req, res, LookUpConstant.LOOK_UP_PROJECT );
					
					dispNames = new String[] {"PROJECT_NUMBER", "PROJECT_NAME",
								"PROJECT_TYPE"};
					dispLabels = new String[] {"项目编号", "项目名称", "项目类型"};
					retFields = new String[] {"PROJECT_NUMBER", "PROJECT_NAME"};
					viewPercent = new String[] {"10%", "60%", "30%"};
					qryNames = new String[] {"PROJECT_NUMBER", "NAME","PROJECT_TYPE"};
					qryLabels = new String[] {"项目编号", "项目名称", "项目类型"};
					primaryKeys = new String[] {"PROJECT_ID"};

					lookProp.setTotalWidth(700);
                    String multipleChose = req.getParameter("multipleChose");
                    if(!StrUtil.isEmpty(multipleChose) && multipleChose.equalsIgnoreCase("true")){
                        lookProp.setMultipleChose(true);
                    } else {
                        lookProp.setMultipleChose(false);
                    }
					lookProp.setDtoClass(EtsPaProjectsAllDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_COST)) {
					
					String isMult = req.getParameter("multipleChose");
					boolean multipleChose = false;
					if( !StrUtil.isEmpty( isMult ) ){
						 multipleChose = Boolean.parseBoolean( isMult );
					}
					
					dispNames = new String[] {"COST_CODE", "COST_NAME"};
					dispLabels = new String[] {"成本中心代码", "成本中心名称"};
					viewPercent = new String[] {"30%", "65%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"COST_CODE"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose( multipleChose );
					lookProp.setDtoClass(AmsMisCostDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_ITEMNAME)) {
					dispNames = new String[] {"ITEM_NAME","ITEM_SPEC","VALUE"};
					dispLabels = new String[] {"设备名称","规格型号","设备专业"};
					viewPercent = new String[] {"40%","40%","20%"};
					qryNames = new String[] {"ITEM_NAME","ITEM_SPEC"};
					qryLabels = new String[] {"设备名称","规格型号"};
					primaryKeys = new String[] {"ITEM_NAME"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsSystemItemDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_MISLOC)) {
					String isMult = req.getParameter("multipleChose");
					boolean multipleChose = false;
					if( !StrUtil.isEmpty( isMult ) ){
						 multipleChose = Boolean.parseBoolean( isMult );
					}
					
					dispNames = new String[] {"ASSETS_LOCATION_CODE", "ASSETS_LOCATION"};
					dispLabels = new String[] {"地点代码", "地点名称"};
					viewPercent = new String[] {"50%", "40%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"ASSETS_LOCATION_CODE"};
					lookProp.setMultipleChose( multipleChose );
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				}  else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_TDLOC)) {
					dispNames = new String[] {"ASSETS_LOCATION_CODE", "ASSETS_LOCATION"};
					dispLabels = new String[] {"地点代码", "地点名称"};
					viewPercent = new String[] {"50%", "40%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"ASSETS_LOCATION_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_LNE)) {
					dispNames = new String[] {"NET_CATEGORY1", "NET_CATEGORY2", "NET_UNIT_CODE", "LOG_NET_ELE", "ENG_AB"};
					dispLabels = new String[] {"网络专业1", "网络专业2", "网元代码", "逻辑网络元素", "英文缩写"};
					viewPercent = new String[] {"15%", "15%", "15%", "30%", "15%"};
					qryNames = new String[] {"NET_CATEGORY1", "NET_CATEGORY2", "NET_UNIT_CODE", "LOG_NET_ELE"};
					qryLabels = new String[] {"网络专业1", "网络专业2", "网元代码", "逻辑网络元素"};
					primaryKeys = new String[] {"AMS_LNE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsLneDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_LNE_NOMATCH_AMS)) {
					dispNames = new String[] {"NET_CATEGORY1", "NET_CATEGORY2", "NET_UNIT_CODE", "LOG_NET_ELE", "ENG_AB"};
					dispLabels = new String[] {"网络专业1", "网络专业2", "网元代码", "逻辑网络元素", "英文缩写"};
					viewPercent = new String[] {"15%", "15%", "15%", "30%", "15%"};
					qryNames = new String[] {"NET_CATEGORY1", "NET_CATEGORY2", "NET_UNIT_CODE", "LOG_NET_ELE"};
					qryLabels = new String[] {"网络专业1", "网络专业2", "网元代码", "逻辑网络元素"};
					primaryKeys = new String[] {"AMS_LNE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsLneDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_CEX)) {
					dispNames = new String[] {"INVEST_CATEGORY1", "INVEST_CATEGORY2", "INVEST_CAT_CODE", "INVEST_CAT_NAME"};
					dispLabels = new String[] {"投资大类", "投资中类", "投资分类代码", "投资分类名称"};
					viewPercent = new String[] {"25%", "25%", "25%", "20%"};
                    qryNames = new String[] {"INVEST_CATEGORY1", "INVEST_CATEGORY2", "INVEST_CAT_CODE", "INVEST_CAT_NAME"};
					qryLabels = new String[] {"投资大类", "投资中类", "投资分类代码", "投资分类名称"};
					primaryKeys = new String[] {"AMS_CEX_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsCexDTO.class);

				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_CEX_NOMATCH_AMS)) {
					dispNames = new String[] {"INVEST_CATEGORY1", "INVEST_CATEGORY2", "INVEST_CAT_CODE", "INVEST_CAT_NAME"};
					dispLabels = new String[] {"投资大类", "投资中类", "投资分类代码", "投资分类名称"};
					viewPercent = new String[] {"25%", "25%", "25%", "20%"};

					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"AMS_CEX_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsCexDTO.class);

				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_OPE)) {
					dispNames = new String[] {"OPE_CODE", "OPE_NAME"};
					dispLabels = new String[] {"业务平台编码", "业务平台名称"};
					viewPercent = new String[] {"40%", "50%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"AMS_OPE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsOpeDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_OPE_NOMATCH_AMS)) {
					dispNames = new String[] {"OPE_CODE", "OPE_NAME"};
					dispLabels = new String[] {"业务平台编码", "业务平台名称"};
					viewPercent = new String[] {"40%", "50%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"AMS_OPE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsOpeDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_NLE)) {
					dispNames = new String[] {"LNE_CODE", "LNE_NAME"};
					dispLabels = new String[] {"网络层次编码", "网络层次名称"};
					viewPercent = new String[] {"40%", "50%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"AMS_LNE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsNleDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_SN)) {
					dispNames = new String[] {"SN_CODE", "SN_NAME"};
					dispLabels = new String[] {"支持网设备类型编码", "支持网设备类型名称"};
					viewPercent = new String[] {"40%", "50%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"SN_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsSnDTO.class);
				}else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_NLE_NOMATCH_AMS)) {
					dispNames = new String[] {"LNE_CODE", "LNE_NAME"};
					dispLabels = new String[] {"网络层次编码", "网络层次名称"};
					viewPercent = new String[] {"40%", "50%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"AMS_LNE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsNleDTO.class);
				}
                
				else if (lookUpName.equals(AssetsLookUpConstant.
						LOOK_TRANSFER_ASSETS_OTHER )) { //资产调拨流程查找资产
					//资产标签号、资产名称、规格型号、资产数量、计量单位、启用日期、地点描述、生产厂商、责任人、责任部门
					dispNames = new String[] {"BARCODE", "ASSETS_DESCRIPTION","MODEL_NUMBER", 
								"ITEM_QTY", "UNIT_OF_MEASURE",  "DATE_PLACED_IN_SERVICE",
								"OLD_RESPONSIBILITY_USER_NAME",
								"OLD_RESPONSIBILITY_DEPT_NAME","OLD_LOCATION_CODE",
								"OLD_LOCATION_NAME", "MANUFACTURER_NAME"};
					dispLabels = new String[] {"资产标签",  "设备名称", "设备型号", "资产数量","计量单位",
								  "启用日期",   "责任人", "责任部门", "地点编号", "地点简称", "厂商"};
					viewPercent = new String[] {"8%", "6%","6%", "6%", "6%",  "4%",  "4%", "10%", "8%", "15%","9%"};
					qryNames = new String[] {"ASSETS_DESCRIPTION", "BARCODE", "OLD_LOCATION_CODE"};
					qryLabels = new String[] {"资产名称", "标签号", "地点"};
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(850);
					lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_ASSETS_LEASE  )) { //资产续租查找资产
					dispNames = new String[] {"BARCODE", 
								"ITEM_NAME",
								"ITEM_SPEC",  //"ASSET_NUMBER","资产编号","ASSETS_DESCRIPTION", "资产名称",  "MODEL_NUMBER", "资产型号",
								"WORKORDER_OBJECT_LOCATION", 
								"RESPONSIBILITY_USER_NAME", 
								"RENT_DATE",
								"END_DATE",
								"RENT_PERSON",
								"CONTRACT_NUMBER", 
								"CONTRACT_NAME",
								"TENANCY",
								"YEAR_RENTAL",
								"MONTH_RENTAL",
								"REMARK" 
								};
					dispLabels = new String[] {"资产标签",  "设备名称", "规格型号", 
								 "资产地点描述", "责任人",    "起租日期", "止租日期", "签约单位", "合同编号", "合同名称",   "租期", "年租金", "月租金", "备注"};
					viewPercent = new String[] {"8%", "4%","6%","6%", "6%",    "6%", "4%", "4%", "4%", "3%",   "10%", "8%", "15%","9%"};
					qryNames = new String[] {"RENT_PERSON", "WORKORDER_OBJECT_LOCATION",
							   "RESPONSIBILITY_USER_NAME" }; //, "END_DATE" , "END_DATE"
					qryLabels = new String[] {"签约单位", "地点", "责任人"}; //, "止租日期起", "止租日期止"
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(850);
					lookProp.setDtoClass(LeaseLineDTO.class);
				}else if(lookUpName.equals(AssetsLookUpConstant.LOOK_ASSETS_SHARE)) {// 选择共享资产
					
					dispNames = new String[] {"BARCODE", 
							"ITEM_NAME",
							"ITEM_SPEC", 
							"WORKORDER_OBJECT_LOCATION", 
							"SPECIALITY_DEPT_NAME",
							"EMPLOYEE_NUMBER",
							"USER_NAME",
							"START_DATE",
							"CONTENT_CODE",
							"CONTENT_NAME"
							};
				dispLabels = new String[] {"资产标签",  "资产名称", "规格型号", 
							 "资产地点描述", "实物管理部门", "责任人编号", "责任人", "启用日期",  "资产目录", "资产目录描述"};
				viewPercent = new String[] {"8%", "8%", "8%", "12%", "12%", "6%", "6%", "8%", "8%", "10%"};
				qryNames = new String[] {"BARCODE","CONTENT_NAME", "WORKORDER_OBJECT_LOCATION",
						   "USER_NAME" }; //, "END_DATE" , "END_DATE"
				qryLabels = new String[] {"标签号","资产目录", "资产地点", "责任人"}; //, "止租日期起", "止租日期止"
				primaryKeys = new String[] {"SYSTEMID"};
				lookProp.setTotalWidth(850);
				lookProp.setMultipleChose(true);
				lookProp.setDtoClass(AssetSharingLineDTO.class);

				}else if(lookUpName.equals(AssetsLookUpConstant.LOOK_ASSETS_DEVALUE)) {// 选择可减值资产

				dispNames = new String[] {"BARCODE", "ITEM_NAME", "ITEM_SPEC", 
							"WORKORDER_OBJECT_LOCATION", "SPECIALITY_DEPT_NAME",
							"EMPLOYEE_NUMBER", "USER_NAME", "START_DATE",
							"CONTENT_CODE", "CONTENT_NAME"
							};
				dispLabels = new String[] {"资产标签",  "资产名称", "规格型号", 
							 "资产地点描述", "实物管理部门", "责任人编号", "责任人", "启用日期",  "资产目录", "资产目录描述"};
				viewPercent = new String[] {"8%", "8%","8%","12%", "12%", "6%", "6%", "8%", "8%", "10%"};
				qryNames = new String[] {"BARCODE","CONTENT_NAME", "WORKORDER_OBJECT_LOCATION", "USER_NAME" }; //, "END_DATE" , "END_DATE"
				qryLabels = new String[] {"标签号","资产目录", "资产地点", "责任人"}; //, "止租日期起", "止租日期止"
				
                retFields = new String[]  {"BARCODE", "ASSET_NUMBER","ITEM_NAME","ITEM_SPEC",
						"ASSETS_DESCRIPTION", "MODEL_NUMBER", "COST",
						"DATE_PLACED_IN_SERVICE", "DEPRN_COST",
						"MANUFACTURER_NAME", "DEPRN_LEFT_MONTH", "DEPRECIATION",
						"IMPAIR_RESERVE", "RETIREMENT_COST", "CURRENT_UNITS",
						"UNIT_OF_MEASURE", "START_DATE"};
				
				primaryKeys = new String[] {"SYSTEMID"};
				lookProp.setTotalWidth(850);
				lookProp.setMultipleChose(true);
				lookProp.setDtoClass(AssetSharingLineDTO.class);
				
				} else if (lookUpName.equals("LOOK_ASSETS")) {//选择资产
					dispNames = new String[] {"BARCODE", 
							"ITEM_NAME",
							"ITEM_SPEC", 
							"WORKORDER_OBJECT_LOCATION", 
							"EMPLOYEE_NUMBER",
							"RESPONSIBILITY_USER_NAME",
							"SPECIALITY_DEPT_NAME",
							"START_DATE",
							"CONTENT_CODE",
							"CONTENT_NAME"
							};
				dispLabels = new String[] {"资产标签", "资产名称", "规格型号", 
							 "资产地点描述", "责任人编号", "责任人", "实物管理部门", "启用日期",  "资产目录", "资产目录描述"};
				viewPercent = new String[] {"8%", "8%","8%", "12%", "6%", "6%", "12%", "8%", "8%", "10%"};
				qryNames = new String[] {"BARCODE", "ITEM_NAME", "WORKORDER_OBJECT_LOCATION" }; 
				qryLabels = new String[] {"资产标签号", "资产名称", "资产地点"}; 
				primaryKeys = new String[] {"SYSTEMID"};
				lookProp.setTotalWidth(1024);
				lookProp.setMultipleChose(true);
				lookProp.setDtoClass(AmsAssetsTransLineDTO.class);
				}else if (lookUpName.equals("LOOK_ZEROTURN_ASSETS")) {//选择零购资产
						dispNames = new String[] {
							                   "BARCODE",     //"标签号", 
							                   "PROCURE_CODE",//采购单号
							                   "EXPECTED_DATE",//预计到货日期
							                   "CONTENT_CODE",//"资产目录", 
								               "CONTENT_NAME",//"资产目录名称", 
								               "ASSETS_DESCRIPTION",//"资产名称", 
								               "ITEM_SPEC",//"规格型号", 
								               "ITEM_QTY",//"数量",
								               "UNIT_OF_MEASURE",//"数量单位",
								               "MANUFACTURER_NAME",//"厂商",
								               "OBJECT_NO",//"地点编号",
								               "WORKORDER_OBJECT_NAME",//"地点名称",
								               "RESPONSIBILITY_DEPT",//"责任部门",
								               "RESPONSIBILITY_USER",//"责任人",
//								               "SPECIALITY_DEPT",//"专业部门",
//								               "PRICE",//"金额",
								               "COST_CENTER_CODE"//,//"成本中心",
//								               "CREATION_DATE", 
//								 			   "CREATED_BY",
//								 			   "RECORD",// "BC",//BARCODE
//								 			   "COMPANY_CODE",// "公司代码",
//								               "REMARK"//"备注"
								};
					dispLabels = new String[] {//
							                   "标签号", 
							                   "采购单号",
							                   "预计到货日期",
							                   "资产目录", 
								               "资产目录名称", 
								               "资产名称", 
								               "规格型号", 
								               "数量",
								               "数量单位",
								               "厂商",
								               "地点编号",
								               "地点名称",
								               "责任部门",
								               "责任人",
//								               "专业部门",
//								               "金额",
								               "成本中心"//,
//								               "创建日期",
//											   "创建人",
//											   "码",
//											   "公司编码",
//								               "备注"
								               };
					viewPercent = new String[] 
					                       {//
											"6%",//"标签号", 
											"7%",//采购单号
											"7%",//预计到货日期
											"6%",//"资产目录", 
											"7%",//"资产目录名称", 
											"7%",//"资产名称", 30
											"7%",//"规格型号", 
											"5%",//"数量",
											"7%",//"数量单位",
											"6%",//"厂商",
											"7%",//"地点编号",
											"7%",//"地点名称",
											"7%",//"责任部门",
											"7%",//"责任人",
//											"4%",//"专业部门",
//											"4%",//"金额",
											"7%"/*,//"成本中心",
											"4%",//"创建日期",
											"4%",//"创建人",
											"4%",//"创建人",
											"4%",//"公司编码",
							                "4%"*/};//"备注" 
					retFields = new String[] {
							   "BARCODE",     //"标签号", 
			                   "PROCURE_CODE",//采购单号
			                   "EXPECTED_DATE",//预计到货日期
			                   "CONTENT_CODE",//"资产目录", 
				               "CONTENT_NAME",//"资产目录名称", 
				               "ASSETS_DESCRIPTION",//"资产名称", 
				               "ITEM_SPEC",//"规格型号", 
				               "ITEM_QTY",//"数量",
				               "UNIT_OF_MEASURE",//"数量单位",
				               "MANUFACTURER_NAME",//"厂商",
				               "OBJECT_NO",//"地点编号",
				               "WORKORDER_OBJECT_NAME",//"地点名称",
				               "RESPONSIBILITY_DEPT",//"责任部门",
				               "RESPONSIBILITY_USER",//"责任人",
				               "SPECIALITY_DEPT",//"专业部门",
				               "PRICE",//"金额",
				               "COST_CENTER_CODE",//"成本中心",
				               "CREATION_DATE", 
				 			   "CREATED_BY",
				 			   "RECORD",// "BC",//BARCODE
				 			   "COMPANY_CODE",// "公司代码",
				               "REMARK"//"备注"
							};
					qryNames = new String[] {"PROCURE_CODE", "COST_CENTER_CODE" }; 
					qryLabels = new String[] {"采购单号", "成本中心"}; 
					primaryKeys = new String[] {"BARCODE"};
					lookProp.setTotalWidth(1024);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(ZeroTurnLineDTO.class);
			     }else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CONTENT)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"资产类别代码", "资产类别名称"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CONTENTS)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"资产目录代码", "资产 目录名称"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_LNE)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"资产类别代码", "资产类别名称"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CEX)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"资产类别代码", "资产类别名称"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_OPE)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"资产类别代码", "资产类别名称"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_NLE)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"资产类别代码", "资产类别名称"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(EtsItemInfoDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_FA_CONTENT_LINE)) {
                    dispNames = new String[] {"CONTENT_CODE", "CONTENT_NAME"};
					dispLabels = new String[] {"资产类别代码", "资产类别名称"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CONTENT_CODE"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_LNE_LINE)) {
                    dispNames = new String[] {"LNE_CODE", "LNE_NAME"};
					dispLabels = new String[] {"逻辑网络元素编码", "逻辑网络元素名称"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"LNE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_CEX_LINE)) {
                    dispNames = new String[] {"CEX_CODE", "CEX_NAME"};
					dispLabels = new String[] {"投资分类编码", "投资分类名称"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"CEX_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_OPE_LINE)) {
                    dispNames = new String[] {"OPE_CODE", "OPE_NAME"};
					dispLabels = new String[] {"业务平台编码", "业务平台名称"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"OPE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				} else if (lookUpName.equals(AssetsLookUpConstant.LOOK_UP_NLE_LINE)) {
                    dispNames = new String[] {"NLE_CODE", "NLE_NAME"};
					dispLabels = new String[] {"网络层次编码", "网络层次名称"};
					viewPercent = new String[] {"40%", "60%"};
					qryNames = dispNames;
					qryLabels = dispLabels;
					primaryKeys = new String[] {"NLE_ID"};
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsAddressVDTO.class);
				}else if(lookUpName.equals("SF_RES_DEFINE")){
					dispNames = new String[] { "RES_NAME", "RES_URL"};
					dispLabels = new String[] {"栏目名称", "栏目URL"};
					viewPercent = new String[] { "50%", "50%" };
					qryNames = new String[] { "RES_NAME","RES_URL" };
					qryLabels = new String[] { "栏目名称", "栏目URL" };
//					primaryKeys = new String[] { "SYSTEM_ID" };
					// lookProp.setTotalWidth(746);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(SfRoleQueryDTO.class);
				}else if(lookUpName.equals("OU_CITY_MAP")){
					dispNames = new String[] { "ORGANIZATION_ID", "COMPANY"};
					dispLabels = new String[] {"地点ID", "地点名称"};
					viewPercent = new String[] { "50%", "50%" };
					qryNames = new String[] {"COMPANY" };
					qryLabels = new String[] {  "地点名称" };
//					primaryKeys = new String[] { "SYSTEM_ID" };
					// lookProp.setTotalWidth(746);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(SfRoleQueryDTO.class);
				} else if(lookUpName.equals("LOOK_UP_ALL_USER")){
					dispNames = new String[] { "USER_NAME", "LOGIN_NAME"};
					dispLabels = new String[] {"用户名称", "登录名"};
					viewPercent = new String[] { "50%", "50%" };
					qryNames = new String[] {"USERNAME" };
					qryLabels = new String[] {  "用户名称" };
//					primaryKeys = new String[] { "SYSTEM_ID" };
					// lookProp.setTotalWidth(746);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(SfUserDTO.class);
				}else if (lookUpName.equals("LOOK_UP_MANAGER")) { //资产管理员
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
				    dispLabels = new String[] {"用户姓名", "用户员工号", "用户登录名", "公司代码",
							 "公司名称"};
				    viewPercent = new String[] {"15%", "30%", "15%", "15%",
							  "20%"};
				    retFields = new String[] {"USER_ID", "COMPANY_CODE",
							"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME"};
				    qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
						   "LOGIN_NAME"};
				    qryLabels = new String[] {"姓名", "员工号", "登录名"};
				    primaryKeys = new String[] {"USER_ID"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				
			   } else if(lookUpName.equals("LOOK_UP_ORUSER")){ //得到指定OU下的用户
					dispNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							"LOGIN_NAME", "COMPANY_CODE", "COMPANY_NAME"};
					dispLabels = new String[] {"用户姓名", "用户员工号", "用户登录名", "公司代码",
								 "公司名称"};
					viewPercent = new String[] {"15%", "30%", "15%", "15%",
								  "20%"};
					retFields = new String[] {"USER_ID", "COMPANY_CODE",
								"COMPANY_NAME", "USER_NAME", "EMPLOYEE_NUMBER",
								"LOGIN_NAME"};
					qryNames = new String[] {"USER_NAME", "EMPLOYEE_NUMBER",
							   "LOGIN_NAME"};
					qryLabels = new String[] {"姓名", "员工号", "登录名"};
					primaryKeys = new String[] {"USER_ID"};
	
					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(false);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);					
				}else if (lookUpName.equals("LOOK_UP_CITY")) { //资产管理员
					dispNames = new String[] {"COMPANY", "BOOK_TYPE_NAME"};
				    dispLabels = new String[] {"公司名称", "资产账簿"};
				    viewPercent = new String[] { "50%", "50%"};
				    retFields = new String[] {"COMPANY_CODE", "BOOK_TYPE_CODE"};
				    qryNames = new String[] {"COMPANY_CODE", "COMPANY"
						   };
				    qryLabels = new String[] {"公司编码", "公司名称"};
				    primaryKeys = new String[] {"COMPANY_CODE"};

					lookProp.setTotalWidth(700);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(AmsAssetsPriviDTO.class);
				
			   }else if (lookUpName.equals("LOOK_UP_LOCATION_TASK")) { //查找地点
					dispNames = new String[] {"COMPANY_NAME", "COUNTY_NAME",
								"OBJECT_CATEGORY", "OBJECT_CODE", "OBJECT_NAME"};
					dispLabels = new String[] {"公司名称", "成本中心", "地点专业", "地点代码","地点名称"};
					viewPercent = new String[] {"10%", "19%", "10%", "18%", "40%"};
					retFields = new String[] {"CHECK_LOCATION", "OBJECT_CODE",	"OBJECT_NAME"};
					qryNames = new String[] {"WORKORDER_OBJECT_CODE",
							   "WORKORDER_OBJECT_NAME", "COUNTY_NAME"};
					qryLabels = new String[] {"地点代码", "地点名称", "成本中心"};
					primaryKeys = new String[] {"CHECK_LOCATION"};

//                   contentReadio = "Y";

                   lookProp.setTotalWidth(950);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(EtsObjectTaskDTO.class);
				} else if (lookUpName.equals("LOOK_UP_TASK")) {//查找成本中心
					dispLabels = new String[] {"成本中心代码", "成本中心名称"};
					viewPercent = new String[] {"30%", "60%"};
					retFields = new String[] {"COST_CENTER_CODE", "COST_CENTER_NAME"};
					qryNames = new String[] {"COST_CENTER_CODE",
							   "COST_CENTER_NAME"};
					qryLabels = new String[] {"成本中心代码", "成本中心名称"};
					primaryKeys = new String[] {"COST_CENTER_CODE"};
	
	               lookProp.setTotalWidth(950);
					lookProp.setMultipleChose(true);
					lookProp.setDtoClass(EtsObjectTaskDTO.class);
				} 
				lookProp.setCalPattern(LINE_PATTERN);
				lookProp.setDisFieldNames(dispNames);
				lookProp.setDisFieldLabels(dispLabels);
				lookProp.setRetFields(retFields);
				lookProp.setViewPercent(viewPercent);
				lookProp.setQryFieldNames(qryNames);
				lookProp.setQryFieldLabels(qryLabels);
				lookProp.setPrimaryKeys(primaryKeys);
                lookProp.setContentReadio(contentReadio);
                lookProp.setModelClass(AssetsLookUpModel.class);
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
