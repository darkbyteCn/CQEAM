package com.sino.ams.constant;

import com.sino.base.constant.web.WebActionConstant;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public interface URLDefineList {

	//==================================框架类页面===========================================//
	String LOGIN_PAGE = "/login.jsp"; //登陆页面
	String COMMON_MSG_PAGE = "/publicMessage.jsp";//消息显示页面，其他页面采用include的形式包含该页面。
	String FIRST_LOGIN_PAGE = "/firstLogin.jsp"; //非MIS用户首次登录修改初始密码
	String PORTAL_SERVLET = "/servlet/com.sino.ams.log.servlet.PortalLoginServlet"; //portal验证Servlet
	String HN_PORTAL_SERVLET = "/servlet/com.sino.hn.portal.servlet.PortalLoginServlet"; //portal验证Servlet
	
	String LOGIN_FRM_SERVLET = "/servlet/com.sino.ams.log.servlet.LoginFrmServlet";
	String HOME_PAGE = "/mainPage.jsp"; //主框架页面
	String TIME_OUT_PAGE = "/timeOutPage.jsp"; //会话过期页面
	String NO_PRIVI_PAGE = "/noPriviPage.jsp"; //无权限页面
	String ERROR_PAGE = "/flow/errorPage.jsp"; //错误页面
	String TOP_PAGE = "/topPage.jsp"; //顶部菜单页面
	String MENU_PAGE = "/resourceMenu.jsp"; //左侧菜单树页面
	String SUCCESS_PAGE = "/public/successPage.jsp"; //操作成功返回页面
	String TEMP_PAGE = "/temPage.jsp"; //临时页面，用于定义开发人员的工作任务
	String WINDOW_CLOSE_PAGE = "/public/windowClose.jsp"; //转向关闭页面，可传返回值 retValue
	String INDEX_PAGE = "/home.jsp";//首页信息
	String INDEX_PAGE_NM = "/home_NM.jsp";//首页信息
	String GET_MENU_PAGE = "/getMenuPage.jsp";//订制栏目
	//==================================框架类页面===========================================//

	//==================================栏目维护类页面===========================================//
	String RES_FRM_PAGE = "/system/resource/resourceFrm.jsp";
	String RES_QRY_PAGE = "/system/resource/resourceQuery.jsp";
	String RES_TREE_PAGE = "/system/resource/resourceTree.jsp";
	String RES_DTL_PAGE = "/system/resource/resourceDetail.jsp";
	String RES_QRY_SERVLET = "/servlet/com.sino.ams.system.resource.servlet.SfResDefineServlet?act=" + WebActionConstant.QUERY_ACTION;
	String RES_NEW_SERVLET = "/servlet/com.sino.ams.system.resource.servlet.SfResDefineServlet?act=" + WebActionConstant.NEW_ACTION;
	String RES_DTL_SERVLET = "/servlet/com.sino.ams.system.resource.servlet.SfResDefineServlet?act=" + WebActionConstant.DETAIL_ACTION;
	String RES_PRIVI_FRM = "/system/resource/resPriviFrm.jsp";
	String RES_PRIVI_TREE = "/system/resource/resPriviTree.jsp";
	String RES_PRIVI_QUERY = "/system/resource/resPriviQuery.jsp";
	String PRIVI_QUERY_SERVLET = "/servlet/com.sino.ams.system.resource.servlet.SfResPrivsServlet?act=" + WebActionConstant.QUERY_ACTION;
	String WORK_PLAN_QUERY = "/system/plan/workPlanQuery.jsp";   //工作计划维护（查询）页面
	String WORK_PLAN_DETAIL = "/system/plan/workPlanDetail.jsp"; //工作计划详细页面
	String WORK_PLAN_SEARCH = "/system/plan/workPlanSearch.jsp";//工作计划查询页面
	String WORK_PLAN_PIGEONHOLE = "/system/plan/workPlanPigeonhole.jsp";//工作计划归档页面
	String WORK_PLAN_NEW = "/system/plan/workPlanNew.jsp";//新增工作计划页面
	String WORK_PLAN_S_DETAI = "/system/plan/workPlanSDetail.jsp";//工作计划详细页面（查询）
	String WORK_PLAN_QUERY_SERVLET = "/servlet/com.sino.ams.plan.servlet.AmsWorkPlanServlet?act=" + WebActionConstant.DETAIL_ACTION;

	//==================================栏目维护类页面===========================================//

	//==============================================用户信息维护类页面============================
	String GROUP_QUERY_PAGE = "/system/group/groupQuery.jsp";
	String GROUP_SERVLET_PAGE = "/servlet/com.sino.ams.system.user.servlet.SfGroupServlet?act=" + WebActionConstant.QUERY_ACTION;
	String GROUP_DETAIL_PAGE = "/system/group/groupDetail.jsp";
	String ROLE_QUERY_PAGE = "/system/role/roleQuery.jsp";
	String ROLE_DETAIL_PAGE = "/system/role/roleDetail.jsp";
	String ROLE_QUERY_SERVLET = "/servlet/com.sino.ams.system.user.servlet.SfRoleServlet?act=" + WebActionConstant.QUERY_ACTION;
	String USER_LIST_PAGE = "/system/user/userList.jsp";
	String USER_LIST_SERVLET = "/servlet/com.sino.ams.system.user.servlet.SfUserServlet?act=" + WebActionConstant.QUERY_ACTION;
	String USER_DETAIL_PAGE = "/system/user/userDetail.jsp";
	String CHOOSE_USER_PAGE = "/workorder/util/chooseUser.jsp";
	String CHANGE_USER_PAGE ="/system/user/changeUserPassword.jsp";  //修改用户密码页面
	String BARCODE_RECEIVE_PAGE = "/print/barcodeReceiveInfo.jsp";		 //标签领用维护页面
    String BARCODE_PRINT_PAGE = "/print/barcodePrint.jsp";  //标签领用打印确认维护页面
	String BARCODE_RECEIVE_DETAIL_PAGE = "/print/barcodeReceiveDetail.jsp";	//标签领用详细信息
	String BARCODE_MAX_MAINTAIN_PAGE = "/print/barcodeMaxMaintain.jsp";	//最大标签号维护页面
	String BARCODE_MAX_MAINTAIN_DETAIL = "/print/barcodeMaxMaintainDetail.jsp";	//最大标签号详细信息页面
	//==============================================用户信息维护类页面============================

	//=================================工单信息维护页面===================================
	String ORDER_DIFF_PAGE = "/workorder/order/orderDiffInfo.jsp";
	//工单查询
	String QUERY_INTEGRATION = "/workorder/queryIntegration.jsp"; //工单综合查询
	String TRUN_LIST_DIFFERENT = "/workorder/trunListDifferent.jsp"; //转资清单差异查询
	String TRUN_LIST_DIFFERENT_QUERY = "/workorder/trunListDifferentQuery.jsp"; //转资清单差异查询
	String TRUN_LIST_DITAIL_QUERY = "/workorder/trunListDetailQuery.jsp"; //交接工单明细查询
	String TRUN_LIST_QUERY = "/workorder/trunListQuery.jsp"; //转资清单查询
	String TRUN_LIST_PRINT_QUERY = "/workorder/trunListPrintQuery.jsp"; //转资条码打印
	String TRUN_LIST_PRINT_DETAIL = "/workorder/trunListPrintDetail.jsp"; //转资条码打印_大标签60*38mm
	String TRUN_LIST_PRINT_SIMPLE_DETAIL = "/workorder/trunListPrintSimpleDetail.jsp"; //转资条码打印_小标签60*10mm
    String TRUN_LIST_PRINT_HISTORY_DETAIL="/workorder/trunListPrintHistoryDetail.jsp"; //打印标签号的详细信息
	String ORDER_PROCESS = "/workorder/orderProcessView.jsp";   //工单监控
	String QUERY_BATCH = "/workorder/queryBatch.jsp";       //工单按任务批查询
	String ORDER_DETAIL = "/workorder/orderDetail.jsp";    //工单信息查询
	String ORDER_DETAIL_SERVLET = "/servlet/com.sino.ams.workorder.servlet.OrderDetailServlet";//工单详细信息
	String QUERY_INTEGRATION_SERVLET = "/servlet/com.sino.ams.workorder.servlet.QueryIntegrationServlet"; //工单任务详细信息
	String QUERY_BATCH_DTL = "/workorder/queryBatchDtl.jsp";   //工单任务详细信息
	//bts
	String WORKORDER_NEW = "/workorder/bts/batchForm_bts.jsp";   //基站专业
	String WORKORDER_TMP_PAGE = "/workorder/bts/workorderTmp.jsp"; //基站行
	String WORKORDER_CHOOSE_PAGE = "/workorder/bts/chooseWorkorders.jsp";  //创建公单
	String TMP_WORKORDER_INFO = "/workorder/bts/workorderTmpDetail.jsp";//基站行明细

	String WORKORDER_TMP_SERVLET = "/servlet/com.sino.ams.workorder.servlet.WorkOrderTmpServlet";
	String WORKORDER_CHOOSE_SERVLET = "/servlet/com.sino.ams.workorder.servlet.WorkorderChooseSevrlet";
	//data
	String WORKORDER_NEW_DATA = "/workorder/data/data_batchForm.jsp";   //数据专业
	String DATA_NEW = "/workorder/data/data.jsp";//数据专业
	String DATE_LINE = "/workorder/data/dateLine.jsp";//数据行专业
	String DATE_DETAIL = "/workorder/data/dateDetail.jsp";//数据行明细
    String HANDOVER_NEW = "/workorder/handover.jsp"; //创建交接工单
    String HANDOVER_DETAIL = "/workorder/handoverDetail.jsp"; //交接工单行明细

	String WORKORDER_TMP_PAGE_DATA = "/workorder/data/data_orderTmp.jsp";
	//    String WORKORDER_CHOOSE_PAGE_DATA = "/workorder/data/chooseWorkorders.jsp";
	String NET_BETTER = "/workorder/net/netBetter.jsp";   //网优专业
	String NET_BETTER_LINE = "/workorder/net/netBetterLine.jsp";//网优行信息
	String NET_BETTET_DETAIL = "/workorder/net/netBetterDetail.jsp";//网优行明细

	String CHANG_NEW = "/workorder/chang/chang.jsp";//交换专业
	String CHANG_LINE = "/workorder/chang/changLine.jsp"; //交换行信息
	String CHANG_DETAIL = "/workorder/chang/changDetail.jsp"; //交换行明细

	String ELECTRI_NEW = "/workorder/electric/electri.jsp";  //电力专业
	String ELECTRI_LINE = "/workorder/electric/electriLine.jsp";//电力行信息
	String ELECTRI_DETAIL = "/workorder/electric/electriDetail.jsp"; //电力行明细

	String MONITOR_NEW = "/workorder/monitor/monitor.jsp";  //监控专业
	String MONITOR_LINE = "/workorder/monitor/monitorLine.jsp"; //监控行信息
	String MOINTOR_DETAIL = "/workorder/monitor/mointorDetail.jsp"; //监控行明细

	String TRANSFER_NEW = "/workorder/transfer/transfer.jsp";  //传输专业
	String TRANSFER_LINE = "/workorder/transfer/transferLine.jsp";  //传输行信息
	String TRANSFER_DETAIL = "/workorder/transfer/transferDetail.jsp"; //传输行明细

	String TMP_WORKORDER_INFO_DATA = "/workorder/data/data_orderTmpDetail.jsp";
	String WOERK_PERSON_QUERY="/workorder/workPersonQuery.jsp"; //工单执行人综合查询
	String DISTRI_AGAIN ="/workorder/distriAgain.jsp";//工单重新分配查询页面
	String CONFIRM_IMPLEMENT = "/workorder/confirmImplement.jsp";//工单选择组别和执行人

	String SCAN_MONITOR_RPT = "/workorder/report/scanMonitorRpt.jsp";//巡检监控报表
	String SCAN_RESPONS_RPT = "/workorder/report/locResponsibleScan.jsp";//特定代维公司
	String MAIN_RES_LOC = "/workorder/report/responsibleLocs.jsp";//特定代维公司的责任地点
	String SCAN_LOC_Y = "/workorder/report/scanedLocs.jsp";//特定代维公司的责任地点
	String SCAN_LOC_N = "/workorder/report/notScanedLocs.jsp";//特定代维公司的责任地点

	String SCAN_ITEMS_RPT = "/workorder/report/itemResponsibleScan.jsp";//特定代维公司，对特定地点的扫描情况
	String LOC_ITEM = "/workorder/report/locationItems.jsp";//当前地点设备
	String LOC_SCANED_ITEM = "/workorder/report/locationScanedItems.jsp";//当前地点已巡检设备
	String LOC_DIFF_ITEM = "/workorder/report/locationDiffItems.jsp";//当前地点未巡检设备

	String GROUP_RESULT_RPT = "/workorder/report/groupResultRpt.jsp";//巡检结果报表(按部门统计)
	String GROUP_FRAME_RPT = "/workorder/report/groupFrm.jsp";//框架页面(按部门统计)
	String SCAN_GROUP_PAGE = "/workorder/report/scanGroup.jsp";//巡检组别信息(按部门统计)
	String GROUP_LOCATION_PAGE = "/workorder/report/groupLocations.jsp";//巡检地点详细信息(按部门统计)
	String GROUP_ITEM_PAGE = "/workorder/report/groupItems.jsp";//巡检设备详细信息(按部门统计)
	String COMP_ITEM_REPORT = "/workorder/report/compItemReport.jsp";//按公司统计巡检设备
	String COMP_ITEM_DETAIL = "/workorder/report/compItemDetail.jsp";//按公司巡检设备列表
	String COMP_OWN_ITEM = "/workorder/report/compOwnItems.jsp";
	String COMP_SCANED_ITEM = "/workorder/report/compScanedItems.jsp";
	String COMP_NOT_SCANED_ITEM = "/workorder/report/compNotScanedItems.jsp";

	String DEPT_ITEM_REPORT = "/workorder/report/deptItemReport.jsp";//按责任部门统计巡检设备
	String DEPT_ITEM_DETAIL = "/workorder/report/deptItemDetail.jsp";//按责任部门巡检设备列表
	String DEPT_OWN_ITEM = "/workorder/report/deptOwnItems.jsp";
	String DEPT_SCANED_ITEM = "/workorder/report/deptScanedItems.jsp";
	String DEPT_NOT_SCANED_ITEM = "/workorder/report/deptNotScanedItems.jsp";
	//=================================工单信息维护页面 end===================================

	//======================================begin=设备分配维护类页面============================
	String EQUIP_DETAIL_PAGE = "/system/item/equipMainInfo.jsp";
	String EQUIP_QUERY_PAGE = "/system/item/equipMainSearch.jsp";
	String EQUIP_QUERY_SERVLET = "/servlet/com.sino.ams.system.item.servlet.EtsSystemItemServlet?act=" + WebActionConstant.QUERY_ACTION;
	String DISTRI_EQUIP_PAGE = "/system/item/equipDistriSearch.jsp";
	String DISTRI_DETAIL_PAGE = "/system/item/equipDistriInfo.jsp";
	String DISTRI_EQUIP_QUERY_SERVLET = "/servlet/com.sino.ams.system.item.servlet.EquipDistriServlet?act=" + WebActionConstant.QUERY_ACTION;
	String SPARE_CONFIRM= "/system/part/sparePartConfirm.jsp";
	String CONFIRM_QUERY_SERVLET = "/servlet/com.sino.ams.system.part.servlet.PartConfirmServlet?act=" + WebActionConstant.QUERY_ACTION;
	//String SPARE_REJIGGER = "/system/part/spareRejigger.jsp"; //备件更改（合并）
	String SPARE_REJIGGER = "/system/part/spareRejigger.jsp"; //备件更改（合并）<******路径更改后****> 
	String SPARE_DISTRI = "/system/part/spareDistri.jsp";//备件分配确认(详细页面)
	String SPARE_QUERY = "/system/part/spareDistriQuery.jsp";// 备件分配查询页面
	String SPARE_STATISTIC = "/system/part/spareStatistics.jsp"; //备件统计页面
	String REASON_STATISTIC= "/system/part/reasonStatistics.jsp";//原因统计页面
	//==============================================设备分配维护类页面=end============================

	//==============================================代维维护类页面============================
	String TRUSTCOR_QUERY_SERVLET = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainCompanyServlet?act=" + WebActionConstant.QUERY_ACTION;
	String TRUSTCOR_QUERY_PAGE = "/system/trust/trustCompanyQuery.jsp";
	String TRUSTCOR_DETAIL_PAGE = "/system/trust/trustCompanyDetail.jsp";
	String TRUSTCOR_DETAIL_SERVLET = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainCompanyServlet?act=" + WebActionConstant.DETAIL_ACTION;

	String TRUSTCORUSR_QUERY_SERVLET = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainPeopleServlet?act=" + WebActionConstant.QUERY_ACTION;
	String TRUSTCORUSR_QUERY_PAGE = "/system/trust/trustCorpUsrQuery.jsp";
	String TRUSTCORUSR_DETAIL_PAGE = "/system/trust/trustCorpUsrDetail.jsp";

	String TRUSTCORRSP_SERVLET = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainResponsibilityServlet";
	String TRUSTCORRSP_QUERY_PAGE = "/system/trust/trustCorpRSPQuery.jsp";
	String CONFIRM_LOCATION_PAGE = "/system/trust/confirmLocation.jsp";
	String TRUSTCORRSP_CHAIN_PAGE = "/system/trust/trustCorpRSPChain.jsp";
	String TRUSTCORRSP_UNCHAIN_PAGE = "/system/trust/trustCorpRSPUnchain.jsp";
	String TRUST_ATTACH_FILE_PAGE = "/system/trust/uploadFile.jsp";
	String TRUST_ATTACH_FILE_SERVLET = "/servlet/com.sino.ams.system.trust.servlet.AmsMaintainFilesServlet?act=" + WebActionConstant.NEW_ACTION;
	//==============================================代维维护类页面============================

	//==============================================字典维护类页面============================
	String DICT_FRM_PAGE = "/system/dict/dictionaryFrm.jsp";
	String DICT_TREE_PAGE = "/system/dict/dictTree.jsp";
	String DICT_DETAIL_PAGE = "/system/dict/dictDetail.jsp";
	String DICT_TYPE_QRY_SERVLET = "/servlet/com.sino.ams.system.dict.servlet.EtsFlexValueSetServlet?act=" + WebActionConstant.QUERY_ACTION;
	String DICT_TYPE_QRY_PAGE = "/system/dict/dictTypeQuery.jsp";
	String DICT_TYPE_DETAIL_PAGE = "/system/dict/dictTypeDetail.jsp";
	String DICT_QUERYRY_SERVLET = "/servlet/com.sino.ams.system.dict.servlet.EtsFlexValuesServlet?act=" + WebActionConstant.QUERY_ACTION;
	String DICT_QUERY_PAGE = "/system/dict/dictQuery.jsp";
	
	String DICT_ANALYSE_FRM_PAGE = "/system/dict/dictionaryAnalyseFrm.jsp";
	String DICT_ANALYSE_TREE_PAGE = "/system/dict/dictAnalyseTree.jsp";
	String DICT_ANALYSE_DETAIL_PAGE = "/system/dict/dictAnalyseDetail.jsp";
	String DICT_ANALYSE_TYPE_QRY_SERVLET = "/servlet/com.sino.ams.system.dict.servlet.EtsFlexAnalyseValueSetServlet?act=" + WebActionConstant.QUERY_ACTION;
	String DICT_ANALYSE_TYPE_QRY_PAGE = "/system/dict/dictAnalyseTypeQuery.jsp";
	String DICT_ANALYSE_TYPE_DETAIL_PAGE = "/system/dict/dictAnalyseTypeDetail.jsp";
	String DICT_ANALYSE_QUERYRY_SERVLET = "/servlet/com.sino.ams.system.dict.servlet.EtsFlexAnalyseValuesServlet?act=" + WebActionConstant.QUERY_ACTION;
	String DICT_ANALYSE_QUERY_PAGE = "/system/dict/dictAnalyseQuery.jsp";
	//==============================================字典维护类页面============================

	//==============================================基站维修费页面============================
	String OBJECT_BTS = "/others/bts/objectBTS.jsp";
	String OBJECT_BTS_DETAIL = "/others/bts/objectBTSDetail.jsp";
	String OBJECT_ELE = "/others/ele/objectELE.jsp";
	String OBJECT_ELE_DETAIL = "/others/ele/objectELEDetail.jsp";
	String OBJECT_SPARE = "/others/spare/objectSpare.jsp";
	String OBJECT_SPARE_DETAIL = "/others/spare/objectSpareDetail.jsp";
	String OBJECT_BTS_QUERY = "/servlet/com.sino.ams.web.bts.servlet.EtsObjectFixfeeServlet?act=" + WebActionConstant.QUERY_ACTION;
	String OBJECT_ELE_QUERY = "/servlet/com.sino.ams.web.ele.servlet.EtsObjectEleServlet?act=" + WebActionConstant.QUERY_ACTION;
	String OBJECT_ITEM_QUERY = "/servlet/com.sino.ams.web.item.servlet.EtsItemFixfeeServlet?act=" + WebActionConstant.QUERY_ACTION;
	String ITEM_FIXING_QUERY = "/system/fixing/itemInfoQuery.jsp";
	String ITEM_FIXING_DETAIL = "/system/fixing/itemInfoDetail.jsp";
	String ITEM_FIXING_SEARCH = "/servlet/com.sino.ams.system.fixing.servlet.EtsItemInfoServlet?act=" + WebActionConstant.QUERY_ACTION;

	//==============================================基站维修费页面============================

	//============================= bigin：专业地点对照======================================================
	String COMPAR_SEARCH = "/system/comparison/groupComparSearch.jsp";
	String COMPAR_INFO = "/system/comparison/groupComparInfo.jsp";
	String GROUP_COMPAR_QUERY ="/system/comparison/groupLocQuery.jsp";
	String GROUP_COMPAR_DETAIL ="/system/comparison/groupLocInfo.jsp";
	//====================================end 组别专业对照===================================================

	//==============================================begin=项目信息维护页面============================
	String PROJECT_QUERY_PAGE = "/system/project/maintenance/projectMainSearch.jsp";
	String PROJECT_DETAIL_PAGE = "/system/project/maintenance/projectInfo.jsp";
	String PROJECT_QUERY_SERVLET = "/servlet/com.sino.ams.system.project.servlet.EtsPaProjectsAllServlet?act=" + WebActionConstant.QUERY_ACTION;
	//==============================================项目信息维护页面=end============================   //


	// ==============================================begin=区县信息维护页面============================
	String COUNTY_QUERY = "/system/county/countyQuery.jsp";
	String COUNTY_INFO = "/system/county/countyInfo.jsp";
	String COUNTY_QUERY_SERVLET= "/servlet/com.sino.ams.system.county.servlet.EtsCountyServlet?act=" + WebActionConstant.QUERY_ACTION;
	//==============================================区县信息维护页面=end============================

	//===============================================资产流程任务=============================
	String ASSETS_SERVLET = "/servlet/com.sino.ams.assets.servlet.AmsAssetsTransHServlet";
	String ASSETS_ODDTL_SERVLET = "/servlet/com.sino.ams.assets.servlet.AmsAssetsTransHServlet?act=" + WebActionConstant.DETAIL_ACTION;
	String ASSETS_TRANS_EDIT = "/assets/assetsTransEdit.jsp";
	String ASSETS_FRM_PAGE = "/assets/assetsFrm.jsp";
	String ASSETS_TREE_PAGE = "/assets/assetsTree.jsp";
	String ASSETS_QRY_PAGE = "/assets/assetsQuery.jsp";
	String ASSETS_QRY_SERVLET = "/servlet/com.sino.ams.assets.servlet.AmsFaAssetsServlet?act=" + WebActionConstant.QUERY_ACTION;
	String ASSETS_DTL_PAGE = "/assets/assetsDetail.jsp";//资产详细信息
	String ASSETS_SHARE_FLOW_PAGE = "/newassets/assetsShareFlow.jsp";	//资产共享流程页面
	String ASSETS_SHARE_FLOW_DEATIL_PAGE = "/assets/assetsShareFlowDeail.jsp"; //资产共享流程详细信息页面
	String ASSETS_SHARE_FLOW_EDIT_PAGE = "/newassets/assetsShareFlowEdit.jsp"; //修改资产共享流程详细信息页面

	//==============================================begin=地点维护页面============================
	//-------------------------------- 基站地点维护页面 -----------------------
	String BASEPOINT_QUERY_PAGE = "/system/basepoint/basePointSearch.jsp";
	String BASEPOINT_DETAIL_PAGE = "/system/basepoint/basePointInfo.jsp";
	String BASEPOINT_QUERY_SERVLET = "/servlet/com.sino.ams.system.basepoint.servlet.EtsObjectServlet?act=" + WebActionConstant.QUERY_ACTION;
	//--------------------------------- 交换机房地点维护页面--------------------
	String SWITCHES_QUERY_PAGE = "/system/switches/switchesSearch.jsp";
	String SWITCHES_DETAIL_PAGE = "/system/switches/switchesInfo.jsp";
	String SWITCHES_QUERY_SERVLET = "com.sino.ams.system.switches.servlet.EtsObjectServlet?act=" + WebActionConstant.QUERY_ACTION;
	String SWITCHES_SERVLET = "com.sino.ams.system.switches.servlet.EtsObjectServlet";
	//--------------------------------- 其它设备维护页面------------------------
	String OTHER_LOCATIONS_QUERY = "/system/specialty/otLocationQuery.jsp";   //其它地点
	String OTHER_LOCATIONS_INFO = "/system/specialty/otLocationInfo.jsp";
	String OTHER_EQUIPMENTS_QUERY = "/system/specialty/otEquipmentQuery.jsp";   //其它设备
	String OTHER_EQUIPMENTS_INFO = "/system/specialty/otEquipmentInfo.jsp";
	String OTHER_QUERY_SERVLET = "/servlet/com.sino.ams.system.specialty.servlet.OtLocsVindicateServlet?act=" + WebActionConstant.QUERY_ACTION;

	//==============================================地点维护页面=end============================

	//==============================================备件管理类页面============================
	String SPARE_IN_QRY_PAGE = "/nm/spare/sparePoInQuery.jsp";
	String SPARE_IN_DTL_PAGE = "/spare/sparePoInDetail.jsp";
	String SPARE_DB_QRY_PAGE = "/spare/bjdbQuery.jsp";     //调拨页面
	String SPARE_DB_DTL_PAGE = "/spare/bjdbDetail.jsp";
	//==============================================备件管理类页面============================

	//==============================================流程管理页面============================
	String PROCEDURE_QUERY_PAGE = "/system/procedure/procedureQuery.jsp";
	String PROCEDURE_DETAIL_PAGE = "/system/procedure/procedureDetail.jsp";
	//==============================================备件管理页面============================

	//========================================begin=====其他资产维护页面===========================
	//---------------------------------房屋维护页面===========================
	String HOUSE_QUERY_PAGE = "/system/house/houseSearch.jsp";
	String HOUSE_DETAIL_PAGE = "/system/house/houseInfo.jsp";
	String HOUSE_SERVLET = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet";
	String HOUSE_DETAIL_SERVLET = "/servlet/com.sino.ams.system.house.servlet.AmsHouseInfoServlet?act=" + WebActionConstant.DETAIL_ACTION;
	String HOUSE_ATTACH_FILE_PAGE = "/system/house/uploadItemFile.jsp";
	//---------------------------------土地维护页面===========================
	String LAND_QUERY_PAGE = "/system/house/landSearch.jsp";
	String LAND_DETAIL_PAGE = "/system/house/landInfo.jsp";
	String LAND_DETAIL_SERVLET = "/servlet/com.sino.ams.system.house.servlet.AmsLandInfoServlet?act=" + WebActionConstant.DETAIL_ACTION;
	//---------------------------------短信设置页面===========================
	String NOTE_QUERY_PAGE = "/system/note/noteSearch.jsp";
	String NOTE_DETAIL_PAGE = "/system/note/noteInfo.jsp";
	String NOTE_SERVLET = "/servlet/com.sino.ams.system.note.servlet.AmsRentDeadlineServlet";
	//==============================================房屋维护页面==end==========================

	//=============================================仓库权限维护页面===========================
	String INV_PRIVI_QUERY = "/nm/spare/invPrivi/invPriviQuery.jsp";
	String INV_PRIVI_SERVLET = "/servlet/com.sino.nm.ams.spare.invprivi.servlet.AmsInvPriviServlet?act=" + WebActionConstant.QUERY_ACTION;
	//==============================================仓库权限维护页面============================

	//=============================================备件版本控制页面===========================
	String ITEM_MAINTENANCE = "/spare/version/maintenance.jsp";
	String ITEM_MAINTENANCE_QUERY = "spare/version/maintenanceQuery.jsp";//
	//==============================================备件版本控制页面============================
	//=============================================仪具维护页面===========================
	String INSTRUMENT_QUERY = "/instrument/instrumentQuery.jsp";
	String INSTRUMENT_DETAIL = "/instrument/instrumentDeatli.jsp";
	//==============================================仪具维护页面============================

	//==============================================电缆维护页面============================
	String CABEL_INFO_QUERY = "/others/cabel/cableInfoQuery.jsp";
	String CABEL_INFO_DETAIL = "/others/cabel/cableInfoDetail.jsp";
	String CABEL_INFO_SERVLET = "/servlet/com.sino.ams.others.cabel.servlet.AmsCabelInfoServlet";

	//==============================================电缆维护页面============================

	//==============================================备件分配页面============================
	String ALLOT_PAGE = "/spare/allot/allot.jsp";
	String ALLOT_SUB_PAGE = "/spare/allot/OuList.jsp";
	String ALLOT_SERVLET = "/srvlet/com.sino.ams.spare.allot.servlet.AmsItemTransDServlet";
	//==============================================备件分配页面============================

	//==============================================错误提示页面============================
	String MESSAGE_PROCESS = "/servlet/com.sino.framework.servlet.MessageProcessServlet";
	//==============================================错误提示页面============================

	//==============================================地点查询页面============================
	String LOCUS_QUERY_PAGE = "/net/locus/locusQuery.jsp";

	String LOCUS_DETAIL_BTS_PAGE = "/net/locus/locusDetail.jsp";        //基站
	String LOCUS_DETAIL_COMM_PAGE = "/net/locus/locusCommDetail.jsp";  //其他
	String LOCUS_UNCHECK_QUERY_PAGE = "/net/locus/locusUncheckQuery.jsp";
	String LOCUS_SERVLET = "/servlet/com.sino.ams.net.locus.servlet.LocusServlet";
	//==============================================地点查询页面============================

	//==============================================设备查询页面============================
	String QRY_BY_PROJ_PAGE = "/net/equip/projQuery.jsp";
	String QRY_BY_BARCODE_PAGE = "/net/equip/barcodeQuery.jsp";
	String QRY_BY_SPEC_PAGE = "/net/equip/specQuery.jsp";
	String QRY_BY_CATE_PAGE = "/net/equip/cateQuery.jsp";
	String QRY_BY_LOCUS_PAGE = "/net/equip/locusQuery.jsp";
	String QRY_BY_ALLOT_PAGE = "/net/equip/allotQuery.jsp";
	String QRY_BY_DAIWEI_PAGE = "/net/equip/daiweiQuery.jsp";//按代维公司查询页面
	String QRY_ITEM_SERVLET = "/servlet/com.sino.ams.net.equip.servlet.ItemInfoServlet";
	String QRY_BY_INTEGRATEDSERVLET="/servlet/com.sino.ams.net.equip.servlet.IntegratedServlet";
	String QRY_BY_INTAGRATEDQUERY_SERVLET="/servlet/com.sino.ams.net.equip.servlet.IntagratedQueryServlet";
	String QRY_BY_INTEGRATED= "/net/equip/Integrated.jsp";
	String QRY_BY_INTAGRATEDQUERY="/net/equip/IntagratedQuery.jsp";
	String PLANTMESSAGE="/net/equip/system/plantMessage.jsp";
	String MIS_SPARE_PAGE = "/net/equip/misSpare.jsp";//MIS成套设备查询页面
	String QRY_SPARE_SERVLET = "/servlet/com.sino.ams.net.equip.servlet.MisSpareQueryServlet";//查询MIS成套设备SERVLET
	//==============================================设备查询页面============================

	//==============================================统计页面============================
	String SITUS_STATISTIC_QUERY = "/net/reportforms/situsStatForm.jsp";  //工单统计--按地点
	String ORDER_STATISTIC_QUERY = "/net/reportforms/orderStatLocation.jsp";  //工单统计--按地点(西安用)
	String CITY_COMPLETE_STATISTIC = "/net/reportforms/cityComplete.jsp"; //地市工单完成及时率
	String INDIVIDUAL_RATE_STATISTIC = "/net/reportforms/individualComRate.jsp"; //人员工单完成及时率
	String FALSE_RATE_STATISTIC = "/net/reportforms/falsityRate.jsp";   //工单错误率
	String PATROL_ORDER_DIFFERENT_QUERY = "/workorder/patrolOrderDifferentQuery.jsp"; //巡检清单差异查询
	//--设备现有量统计--
	String STAT_EQP_BY_LOCUS_PAGE = "/net/statistic/equip/locusEStat.jsp";     //按地点
	String STAT_EQP_BY_CATE_PAGE = "/net/statistic/equip/cateEStat.jsp";       //按分类
	String STAT_EQP_BY_CATE_PAGE2 = "/net/statistic/equip/cateIStat.jsp";       //按分类
	String STAT_EQP_BY_NAME_PAGE = "/net/statistic/equip/proviEStat.jsp";       //按分类
	String STAT_EQP_BY_VENDOR_PAGE = "/net/statistic/equip/vendorEStat.jsp";       //按供应商
	String STAT_EQP_BY_VENDOR_PAGE2 = "/net/statistic/equip/vendorIStat.jsp";       //按供应商

	String STAT_EQP_SERVLET = "/servlet/com.sino.ams.net.statistic.servlet.EquipStatServlet";    //公用servlet
	//--工单统计
	String STAT_WO_CHECK_PAGE = "/net/statistic/aviso/checkStat.jsp";
	String STAT_WO_MONTH_PAGE = "/net/statistic/aviso/monthStat.jsp";
	String STAT_WO_TIME_PAGE = "/net/statistic/aviso/timeStat.jsp";
	String STAT_WO_YEAR_PAGE = "/net/statistic/aviso/yearStat.jsp";
	String STAT_WO_SERVLET = "/servlet/com.sino.ams.net.statistic.servlet.AvisoStatServlet";
    String WORKORDER_STATISTICS_PAGE = "/newasset/report/workorderStatistics.jsp";
    String WORKORDER_STATISTICS_SERVLET = "/servlet/com.sino.ams.newasset.report.servlet.WorkorderStatisticsServlet";
    String LOGIN_STATISTICS_PAGE = "/newasset/report/loginStatistics.jsp";
    String LOGIN_STATISTICS_SERVLET = "/servlet/com.sino.ams.newasset.report.servlet.LoginStatisticsServlet";
	//--设备返修次数统计
	// String QRY_ITEM_REPAIR ="/net/statistic/aviso/yearStat.jsp";
	String QRY_ITEM_REPAIR = "/others/spare/itemrepair/itemRepairQuery.jsp";
	String QRY_VENDOR_REPAIR = "/others/spare/itemrepair/vendorRepairQuery.jsp";
	String REPAIR_STATISTIC = "/others/spare/itemrepair/repairStatistic.jsp";
	String REPAIR_STATISTIC_VENDOR = "/others/spare/itemrepair/repairStatisticVendor.jsp";
	//==============================================统计页面============================

	//=======================================无形资产页面==================
	String INTANGIBLE_QUERY = "/system/intangible/intangible.jsp"; //无形资产
	String RENT_SEARCH = "/system/rent/rentSearch.jsp";  //租赁资产
	String RENT_INFO = "/system/rent/rentInfo.jsp";//租赁资产详细
	String RENT_DETAIL = "/system/rent/rentDetail.jsp";  //租赁资产信息
	String RENT_HISTORY_SEARCH = "/system/rent/rentHistory.jsp";  //租赁资产变动历史
	
	//=======================================无形资产页面==================

	//===============================条码打印流程页面================================
	String BARCODE_PRINT_INFO="/print/barcodePrintInfo.jsp";//条码打印申领单据填写
	String BARCODE_PRINT_STATISTIC="/print/barcodePrintStatic.jsp" ;//条码打印统计
	String CHOOSE_PRINT_GROUP = "/print/printGroup.jsp";//标签申领人选择班组
	//================================条码打印流程页面===============================

	//============================== 匹配页面===========================================
	//==============================================资产匹配==
	// ==========================
	String FINANCE_PROP_SET_PAGE = "/match/financePropSet.jsp";
	String FINANCE_PROP_SET_SERVLET = "com.sino.ams.match.servlet.EtsItemMatchRecServlet";
	String UNUSED_ASSETS = "/match/unusedAssets.jsp";
	String CHANGED_ASSETS = "/match/changedAssets.jsp";
	String CHANGED_MATCHL = "/match/changedMatchL.jsp";   //转资匹配:工程物资信息
	String CHANGED_MATCHR = "/match/changedMatchR.jsp";   //转资匹配:MIS资产信息
	String CHANGED_MATCHR_NOMACTINGASSET = "/match/nomatchingPage.jsp";   //未匹配资产清单
	String CHANGED_MATCHR_MACTPROPERTY = "/match/mactProperty.jsp";//
	String BARCODE_MATCH = "/match/barcodeMatch.jsp";    //条码匹配
	String BARCODE_MATCH_SERVLET = "/servlet/com.sino.ams.match.servlet.BarcodeMatchServlet";
	String EQUIPMENT_INFO = "/match/overplusEquipment.jsp";  //资产匹配报表：剩余设备清单
	String ETS_ITEM_MATCHINFO = "/match/itemMatchAssit.jsp";
	String UNYOKE_PAGE = "/match/unyoke.jsp";       //           撤销资产匹配关系（暂定）
//   String RENT_CONFIRMED = "/servlet/com.sino.ams.system.rent.servlet.RentConfirmedServlet";       //  租赁资产确认=11，代管资产确认=13
//   String RENT_CONFIRMED_PAGE = "/system/rent/rentConfirmed.jsp";       //  租赁资产确认=11，代管资产确认=13
	String MIS_ASSETS = "/match/misEquScreen.jsp"; //mis资产屏蔽页面
	String PRACT_PAGE = "/match/amsPractInfo.jsp"; //实物资产信息
	String ACCOUNT_PAGE = "/match/amsAccountInfo.jsp"; //账物资产信息
	String UNSYNCHRONIZED_ASSETS = "/match/unsynchronizedAssets.jsp"; //未同步资产清单

	//==============================================资产匹配============================
	
	//========================================TD资产匹配===========================================
	String FINANCE_PROP_SET_TD_SERVLET = "com.sino.td.match.servlet.EtsItemMatchTdRecServlet";
	String BARCODE_MATCH_TD_SERVLET = "/servlet/com.sino.td.match.servlet.BarcodeMatchTdServlet";
	String BARCODE_MATCH_TD = "/td/match/barcodeMatchTd.jsp";    //td条码匹配
	String MANUAL_MATCH_TD_MIS="/td/match/manualMatchTdMIS.jsp";
	String MANUAL_MATCH_TD_AMS="/td/match/manualMatchTdAMS.jsp";
	String UNYOKE_TD_PAGE = "/td/match/unyokeTd.jsp";       //           撤销资产匹配关系TD
	String CHANGED_MATCHR_MACTPROPERTY_TD = "/td/match/matchedTdList.jsp";//
	String CHANGED_MATCHR_NOMACTINGASSET_TD = "/td/match/noMatchedTdList.jsp";   //未匹配资产清单
	//========================================TD资产匹配
	
	//===============地点匹配===================
	String AMS_MIS_LOC_MATCH = "/match/amsMisLocMatch/amsMisLocMatch.jsp";
	String AMS_MIS_LOC_INFO = "/match/amsMisLocMatch/amsMisLocInfo.jsp";
	String HIDE_MIS_PAGE = "/match/hideMisAsset.jsp";
	String AMS_LOGIC_NETWORK_MATCH_PAGE = "/match/amselementmatch/amsLogicNetworkMatch.jsp";	//逻辑网络元素属性对应关系页面
	String AMS_LOGIC_NETWORK_MATCH_DETAIL = "/match/amselementmatch/amsLogicNetworkMatchDetail.jsp";	//逻辑网络元素属性对应关系页面
	String AMS_CEX_MATCH_PAGE = "/match/amselementmatch/amsCexMatch.jsp";		//投资分类与资产目录对应关系页面
	String AMS_CEX_MATCH_DETAIL = "/match/amselementmatch/amsCexMatchDetail.jsp";	//投资分类与资产目录对应关系匹配页面
	String AMS_OPE_MATCH_PAGE = "/match/amselementmatch/amsOpeMatch.jsp";
	String AMS_OPE_MATCH_DETAIL = "/match/amselementmatch/amsOpeMatchDetail.jsp";
	String AMS_NLE_MATCH_PAGE = "/match/amselementmatch/amsNleMatch.jsp";
	String AMS_NLE_MATCH_DETAIL = "/match/amselementmatch/amsNleMatchDetail.jsp";
	
	String NLE_PAGE = "/system/manydimensions/nleQuery.jsp";
	String NLE_DETAIL_PAGE = "/system/manydimensions/nleDetail.jsp";
	String OPE_DETAIL_PAGE = "/system/manydimensions/opeDetail.jsp";
	String OPE_PAGE = "/system/manydimensions/opeQuery.jsp";
	String CEX_PAGE = "/system/manydimensions/cexQuery.jsp";
	String CEX_DETAIL_PAGE = "/system/manydimensions/cexDetail.jsp";
	String LNE_PAGE = "/system/manydimensions/lneQuery.jsp";
	String LNE_DETAIL_PAGE = "/system/manydimensions/lneDetail.jsp";
	//===============地点匹配===================
   
	//=================================匹配页面==============================================

	//=================================同步页面==============================================
	String EAM_NEW_LOCUS = "/synchronize/eamNewLocus.jsp";  //EAM新增地点同步
	      
	String EAM_NEW_SERVLET = "/servlet/com.sino.ams.synchronize.servlet.EamNewLocusServlet";
	String NET_ASSETS_UPDATE = "/synchronize/netAssetsUpdate.jsp";//网路资产调拨
	String ASSETS_UPDATE = "/synchronize/assetsUpdate.jsp";   //资产变更同步
    String ASSETS_CHANGE_SYN = "/synchronize/assetsChangeSyn.jsp";
	String ASSETS_BARCODE ="/synchronize/assetsBarCodeUpdate.jsp";//条码同步
	String ASSETS_COMMITS = "/synchronize/assetsCommit.jsp";//资产变动直接同步
	String ASSETS_DISCARDED_SYN = "/synchronize/assetsDiscardeds.jsp";//资产报废同步
	String TRANS_DETIL = "/synchronize/transStatusDetail.jsp";
	String TRANS_QUERY = "/synchronize/transStatusQuery.jsp";     //查询页面
	String MESSAGE_QRY_PAGE = "/system/message/messageQuery.jsp";
	String MESSAGE_DTL_PAGE = "/system/message/messageDetail.jsp";
	String MESSAGE_SERVLET = "/servlet/com.sino.ams.system.message.servlet.SfMsgCategoryServlet";

	String USER_LOG_PAGE = "/system/log/userActionLog.jsp";//用户操作日志
	String SYS_LOG_PAGE = "/system/log/sysRunLog.jsp";//系统运行记录日志

	String PA_ASSETS_QRY = "/match/prematch/paAssetsQuery.jsp";//转资准备清单查询界面
	String PA_MATCH_AMS = "/match/prematch/paMatchAmsQuery.jsp";//AMS待转资匹配界面
	String AUTO_MATCH_PAGE = "/match/prematch/autoMatchQuery.jsp";//转资匹配界面(自动匹配)
	String PA_MATCH_FRM = "/match/prematch/paMatchFrm.jsp";//匹配框架页面
	String MAN_MATCH_FRM = "/match/prematch/manualMatchFrm.jsp";//模糊匹配框架页面
	String PA_MATCH_MIS = "/match/prematch/paMatchMisQuery.jsp";//MIS待转资匹配界面
	String AUTO_MATCH_SERVLET = "/servlet/com.sino.ams.prematch.servlet.AutoMatchServlet";//转资匹配界面(自动匹配)
	String MANUAL_FRM_SERVLET = "/servlet/com.sino.ams.prematch.servlet.ManualMatchFrmServlet";//手工匹配框架servlet
	String MATCH_SERVLET = "/servlet/com.sino.ams.prematch.servlet.AmsPaMatchServlet";//匹配servlet
	String MATCHED_LIST_PAGE = "/match/prematch/matchedList.jsp";//已匹配界面
	String MATCH_RESULT = "/match/matchResult.jsp";//匹配监控报表页面
	String MATCH_RESULT_SERVLET = "/servlet/com.sino.ams.match.servlet.MatchResultServlet";//匹配监控报表servlet

//	String HDV_ORDER_SERVLET = "/workorder/handover/hdvOrderQuery.jsp";//交接工单查询
	String HDV_ORDER_SERVLET = "/servlet/com.sino.ams.workorder.servlet.HandOverBatchServlet";
	String HDV_ORDER_QUERY = "/workorder/handover/hdvOrderQuery.jsp";//交接工单查询
	String HDV_ORDER_EDIT = "/workorder/handover/hdvOrderEdit.jsp";//交接工单编辑
	String HDV_ORDER_DETL = "/workorder/handover/hdvOrderDetl.jsp";//交接工单详细信息

	String ADDRESS_TAG_PAGE = "/system/object/addressTagNumQuery.jsp";
	String OBJECT_QUERY_PAGE = "/system/object/objectQuery.jsp";
	String OBJECT_PRINT_DETAIL_PAGE = "/system/object/objectPrintDetail.jsp";
	String OBJECT_DETAIL_PAGE = "/system/object/objectDetail.jsp";
	String ASSETS_NOMATCH_RESPONSIBLE = "/system/object/assetsNoMatchResponsibleSearch.jsp"; //责任人与部门差异查询
	String OBJECT_LIST_PAGE	= "/system/object/objectList.jsp";//展现地点名称的中间字段
	String COMM_OBJECT_SERVLET = "/servlet/com.sino.ams.system.object.servlet.CommonObjectServlet";
	String OBJECT_MAX_PAGE = "/system/object/objectMax.jsp";
	String OBJECT_MAX_SERVLET = "/servlet/com.sino.ams.system.object.servlet.ObjectMaxServlet";

	String ADDRESS_CHANGED ="/synchronize/addressChanged.jsp"; //地点信息变更同步
	String ADDRESS_CHANGED_SERVLET ="/servlet/com.sino.ams.synchronize.servlet.AddressChangedServlet"; //地点信息变更同步Servlet

	//	=================================低值易耗类维护页面==============================================
	String DZYHCATALOG_FRM_PAGE = "/dzyh/catalog/dzyhCatalogFrm.jsp";
	String DZYHCATALOG_TREE_PAGE = "/dzyh/catalog/dzyhCatalogTree.jsp";
	String DZYHCATALOG_DETAIL_PAGE = "/dzyh/catalog/dzyhCatalogDetail.jsp";
	String DZYHCATALOG_TYPE_QRY_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.EamDhCatalogSetServlet?act=" + WebActionConstant.QUERY_ACTION;
	String DZYHCATALOG_TYPE_QRY_PAGE = "/dzyh/catalog/dzyhCatalogTypeQuery.jsp";
	String DZYHCATALOG_TYPE_DETAIL_PAGE = "/dzyh/catalog/dzyhCatalogTypeDetail.jsp";
	String DZYHCATALOG_QUERYRY_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.EamDhCatalogValuesServlet?act=" + WebActionConstant.QUERY_ACTION;
	String DZYHCATALOG_QUERY_PAGE = "/dzyh/catalog/dzyhCatalogQuery.jsp";
	//	=================================低值易耗类维护页面==============================================

	//	=================================低值易耗权限定义维护页面==============================================
	String DZYH_PRIVI_QRY = "/dzyh/privi/priviQuery.jsp";
	String DZYH_PRIVI_DTL = "/dzyh/privi/priviDetail.jsp";
	String DZYH_PRIVI_TOP = "/dzyh/privi/priviTop.jsp";
	String DZYH_PRIVI_LEFT = "/dzyh/privi/priviLeft.jsp";
	String DZYH_PRIVI_RIGHT = "/dzyh/privi/priviRight.jsp";
	String DZYH_PRIVI_FRM = "/dzyh/privi/priviFrm.jsp";
	String DZYH_PRIVI_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.EamDhPriviServlet";
	//String PRIVI_RIGHT_SERVLET = "/servlet/com.sino.ams.newasset.servlet.PriviRightServlet";
	//	=================================低值易耗权限定义维护页面==============================================
	
	//	================================= 低值易耗地点维护页面 =================================
	String DZYH_QUERY_PAGE = "/dzyh/address/addressSearch.jsp";
	String DZYH_DETAIL_PAGE = "/dzyh/address/addressInfo.jsp";
	String DZYH_QUERY_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.EtsObjectServlet?act=" + WebActionConstant.QUERY_ACTION;
	// ================================= 低值易耗地点维护页面 =================================
	
	// ================================= 仪器仪表地点维护页面 =================================
	String INSTRUMENT_QUERY_PAGE = "/instrument/instrumentAddressSearch.jsp";
	String INSTRUMENT_DETAIL_PAGE = "/instrument/instrumentAddressInfo.jsp";
	String INSTRUMENT_QUERY_SERVLET = "/servlet/com.sino.ams.instrument.servlet.AmsInstrumentEamYbObjectNoServlet?act=" + WebActionConstant.QUERY_ACTION;
	// ================================= 仪器仪表地点维护页面 =================================
	
	//	================================= 低值易耗台账查询页面 =================================
	String DZYH_TZHZ_QUERY_PAGE = "/dzyh/query/summaryQuery.jsp";
	String DZYH_TZ_QUERY_PAGE = "/dzyh/query/dzyhQuery.jsp";
	String DZYH_TZ_DETAIL_PAGE = "/dzyh/query/dzyhDetail.jsp";
	String DZYH_ENTER_QUERY_PAGE = "/dzyh/query/enterDzyhQuery.jsp";
	String DZYH_ENTER_DETAIL_PAGE = "/dzyh/query/enterDzyhDetail.jsp";
	String DZYH_ENTER_QRY_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.EamDhBillHServlet?act=" + WebActionConstant.QUERY_ACTION;
	String DZYH_ADD_QUERY_PAGE = "/dzyh/query/addDzyhQuery.jsp";
	String DZYH_ADD_PAGE = "/dzyh/query/addDzyhAdd.jsp";
	String DZYH_ADD_DETAIL_PAGE = "/dzyh/query/addDzyhDetail.jsp";
	String DZYH_ADD_QRY_SERVLET = "/servlet/com.sino.ams.dzyh.servlet.EamDhBillLServlet?act=" + WebActionConstant.QUERY_ACTION;

	String DZYH_FBTJ_QUERY_PAGE = "/dzyh/query/dzyhFBTJQuery.jsp";
	String DZYH_FBTJ_DETAIL_PAGE = "/dzyh/query/dzyhFBTJDetail.jsp";
	
	// ================================= 低值易耗台账查询页面 =================================
	
	// ================================= 低值易耗品盘点 =================================

	String DZYH_LOSS_QUERY_PAGE = "/dzyh/query/lossEquipmentQuery.jsp";
	String DZYH_LOSS_DETAIL_PAGE = "/dzyh/query/lossEquipmentDetail.jsp";
	String DZYH_CHECKIMP_QUERY_PAGE = "/dzyh/query/checkImplementQuery.jsp";
	String DZYH_CHECKIMP_DETAIL_PAGE = "/dzyh/query/checkImplementDetail.jsp";
	
	String DZYH_ITEMDISPOSE_QUERY_PAGE = "/dzyh/dispose/itemDisposeQuery.jsp";
	String DZYH_ITEMDISPOSE_DETAIL_PAGE = "/dzyh/dispose/itemDisposeDetail.jsp";
	String DZYH_DISPOSE_QUERY_PAGE = "/dzyh/dispose/dzyhDisposeQuery.jsp";
	String DZYH_DISPOSE_DETAIL_PAGE = "/dzyh/dispose/dzyhDisposeDetail.jsp";
	
	String DZYH_HISTORY_PAGE = "/dzyh/DzyhHistoryDetail.jsp";
	
	// ================================= 低值易耗品盘点 =================================
	
	//==============================================村通设备查询页面============================
	String QRY_ETS_ITEM_INFO_SERVLET = "/servlet/com.sino.ams.ct.servlet.EtsItemInfoServlet";
	String QRY_BY_ETS_PROJ_PAGE = "/ct/equip/projQuery.jsp";
	String QRY_BY_ETS_BARCODE_PAGE = "/ct/equip/barcodeQuery.jsp";
	String QRY_BY_ETS_SPEC_PAGE = "/ct/equip/specQuery.jsp";
	String QRY_BY_ETS_CATE_PAGE = "/ct/equip/cateQuery.jsp";
	String QRY_BY_ETS_LOCUS_PAGE = "/ct/equip/locusQuery.jsp";
	String QRY_BY_ETS_ALLOT_PAGE = "/ct/equip/allotQuery.jsp";
	String QRY_BY_ETS_DAIWEI_PAGE = "/ct/equip/daiweiQuery.jsp";//按代维公司查询页面
	//==============================================村通设备查询页面============================
	
	//==============================================村通资产报废查询页面==========================================
	String QRY_BY_ETS_FA_ASSETS_PAGE = "/ct/scrap/scrapQuery.jsp";
	String QRY_BY_ETS_FA_ASSETS_DETAIL_PAGE = "/ct/scrap/scrapDetail.jsp";
	String QRY_ETS_FA_ASSETS_SERVLET = "/servlet/com.sino.ams.ct.servlet.EtsFaAssetsServlet";
    String OD_ASSETS_QRY_PAGE = "/newasset/odAssetsQuery.jsp";//其它个人资产报废
	//==============================================村通资产报废查询页面==========================================	
	
	//==============================================仪具登记卡页面===============================================
	String INSTRUMENT_REGISTRATION = "/instrument/instrumentRegistration.jsp"; 
	String INSTRUMENT_REGISTRATION_DETAIL = "/instrument/instrumentRegistrationDetail.jsp";
	
	String INSTRUMENT_REGISTRATION_PAGE = "/equipment/instrumentTJQuery.jsp";
	
	String INSTRUMENT_USE_ITEMCATEGORY2_PAGE = "/equipment/YQYBuseItemCategory2Query.jsp"; 
	String INSTRUMENT_USE_BARCODE_PAGE = "/equipment/YQYBuseBarcodeQuery.jsp"; 
	String INSTRUMENT_REPAIR_ITEMCATEGORY2_PAGE = "/equipment/YQYBrepairItemCategory2Query.jsp"; 
	String INSTRUMENT_REPAIR_BARCODE_PAGE = "/equipment/YQYBrepairBarcodeQuery.jsp";

    //=====================================厂商维护页面=================================================
    String MANUFACTURER_QUERY_PAGE ="/system/manufacturer/manufacturerQuery.jsp";
    String MANUFACTURER_DETAIL_PAGE ="/system/manufacturer/manufacturerDetail.jsp";
    String MANUFACTURER_QUERY_SERVLET ="/servlet/com.sino.ams.system.manufacturer.servlet.ManufacturerServlet";
    //====================================闲置资产报表页面================================================
    String FREE_ITEM_QUERY ="/freeItem/freeItemQuery.jsp";
    String SPECIALTY_FREE_ITEM="/freeItem/specialtyItemQuery.jsp";
    String DEPT_FREE_ITEM="/freeItem/deptFreeItem.jsp";
    //====================================资产报废报表页面=====================================================
    String ITEM_WASTREL_QUERY="/freeItem/itemWastrelQuery.jsp";
    String SPECIALTY_WASTREL_QUERY="/freeItem/specialtyItemWastrel.jsp";
    String DEPT_WASTREL_QUERY="/freeItem/deptWastrelItem.jsp";
    String DISCARDED_ASSETS_SEARCH = "/newasset/report/discardedAssetsReport.jsp";
//  ====================================资产待报废报表页面=====================================================
    String TODISCARDED_ASSETS_SEARCH = "/newasset/report/todiscardedAssetsReport.jsp";
    String ASSETS_DISCARD_SEARCH="/newasset/AmsAssetsDiscardSearch.jsp";
    //==================================销售资产统计========================
    String SELL_ASSETS_QUERY="/freeItem/sellAssetsQuery.jsp";
    String SELL_ASSETS_REPORT = "/newasset/report/sellAssetsReport.jsp";
    //==============================出租资产统计======
    String RENT_ASSETS_QUERY="/freeItem/rentAssetsQuery.jsp";
    String RENT_ASSETS_REPORT = "/newasset/report/rentAssetsReport.jsp";
    String APP_AREA_RENT_ASSETS_REPORT = "/newasset/report/appAreaRentAssetsReport.jsp";//出租资产应用领域统计报表
    //===========================捐赠资产统计====
    String DONATE_ASSETS_QUERY="/freeItem/donateAssetsQuery.jsp";
    String DONATE_ASSETS_REPORT = "/newasset/report/donateAssetsReport.jsp";
    //===============================TD资产专业统计==================
    String SPECIALTY_TD_ASSETS="/freeItem/tdAssetsSpecialty.jsp" ;
    //============================村通资产专业统计=========
    String SPECIALTY_CT_ASSETS="/freeItem/ctAssetsSpecialty.jsp";
    String AREA_CT_ASSETS="/freeItem/ctAssetsArea.jsp";
    //=============================低值易耗统计=================
    String SPECIALTY_DH_ASSETS="/freeItem/dhAssetsSpecialty.jsp";
    String DEPT_DH_ASSETS="/freeItem/dhAssetsDept.jsp";
    String AREA_DH_ASSETS="/freeItem/dhAssetsArea.jsp";

    //===============================报表定义表的维护======
    String RPT_DEFINE_QUERY="/rptdefine/rptDefine.jsp";
    String RPT_DEFINE_DETAIL="/rptdefine/rptDefineDetail.jsp";
    String RPT_DEFINE_SERVLET="com.sino.ams.reportdefine.RPTDefineServlet?act="+ WebActionConstant.QUERY_ACTION;
    String RPT_PARAMETER_DETAIL="/rptdefine/rptParameterDetail.jsp";
    String RPT_PARAMETER_QUERY="/rptdefine/rptParameter.jsp";
    String RPT_PARAMETER_SERVLET="com.sino.ams.reportdefine.RPTParameterServlet?act="+ WebActionConstant.QUERY_ACTION;
    String RPT_VIEW_QUERY="/rptdefine/rptView.jsp";
    String RPT_VIEW_DETAIL="/rptdefine/rptViewDetail.jsp";
    String RPT_VIEW_SERVLET="com.sino.ams.reportdefine.RPTViewServlet?act="+ WebActionConstant.QUERY_ACTION;
    
    String BIRT_RPT_VIEW_LNE_PAGE ="/analyse/birtReport/rptLneParameter.jsp";
    String BIRT_RPT_VIEW_PE_PAGE ="/analyse/birtReport/rptPeParameter.jsp";
    String BIRT_RPT_VIEW_PR_PAGE ="/analyse/birtReport/rptPrParameter.jsp";
    //================================图表定义表的维护============================
    String GRAPH_GR_QUERY="/graphdefine/graphdefine.jsp";
    String GRAPH_GR_DETAIL="/graphdefine/graphdefinedetail.jsp";
    String GRAPH_GR_SERVLET="com.sino.ams.graphdefine.GraphDefineServlet?act="+ WebActionConstant.QUERY_ACTION;
    String GRAPH_PARAMETER_QUERY="/graphdefine/graphparameter.jsp";
    String GRAPH_PARAMETER_DETAIL="/graphdefine/graphparameterdetail.jsp";
    String GRAPH_PARAMETER_SERVLET="com.sino.ams.graphdefine.GraphParameterServlet?act="+ WebActionConstant.QUERY_ACTION;
    String GRAPH_VIEW_DEF_QUERY="/graphdefine/graphview.jsp";
    String GRAPH_VIEW_DEF_DETAIL="/graphdefine/graphviewdefdetail.jsp";
    String GRAPH_VIEW_DEF_ADD="/graphdefine/graphviewdefadd.jsp";
    String GRAPH_VIEW_DEF_SERVLET="com.sino.ams.graphdefine.GraphViewDefServlet?act="+ WebActionConstant.QUERY_ACTION;

    //=======================闲置流程页面====================
    String FREE_FLOW_QUERY="/freeflow/freeflow.jsp";
    String FREE_FLOW_EDIT="/freeflow/assetsFreeEdit.jsp";
    String FREE_FLOW_SERVLET="/servlet/com.sino.ams.freeflow.FreeFlowServlet";
    String TRANS_FREE_EDIT="/freeflow/assetsFreeEdit_42.jsp";
    String TRANS_FREE_DETAIL="/freeflow/assetsTransDetail.jsp";
    String TRANS_FREE_DETAIL_NOTHER="/freeflow/assetsTransDetail_42.jsp";
    String ORDER_APPROVE_SERVLET="/servlet/com.sino.ams.freeflow.OrderApproveServlet";
    String APPROVE_EDIT_PAGE="/freeflow/approve/assetsTransApprove.jsp";
    String APPROVE_DETAIL_PAGE="/freeflow/approve/transApproveDetail.jsp";
    String ASSETS_FREE_SEARCH="/freeflow/AmsAssetsFreeSearch.jsp";
    String ASSETS_FREE_REPORT_SEARCH="/newasset/report/FreeAssetsReport.jsp";
    String ASSETS_SHARE_SEARCH="/newassets/AmsAssetsShareSearch.jsp";
    String ASSETS_SELL_SEARCH="/newassets/AmsAssetsSellSearch.jsp";
    String ASSETS_RENT_SEARCH="/newassets/AmsAssetsRentSearch.jsp";
    String ASSETS_DONATE_SEARCH="/newassets/AmsAssetsDonateSearch.jsp";
    String HOUSE_RENT_CALL="/system/house/rentSearch.jsp";
    String HOUSE_DETAI_CALL="/system/house/rentInfoCall.jsp";
    String LAND_SEARCH_PAGE="/system/house/rent/dispositionLandSearch.jsp";
    String LAND_HOUSE_PAGE="/system/house/dispositionLandHouse.jsp";
    String ASSETS_SUB_SEARCH="/freeflow/AmsAssetsFreeSearch.jsp";
    

    String ASSETS_PASTE = "/system/paste/assetsPaste.jsp";   //条码粘贴覆盖率

    //================================动因维护页面 ============================  
    String DRIVER_INFO_PAGE ="/analyse/driverInfo/driverMainInfo.jsp";
    //================================动因对应关系维护页面 ============================  
    String DRIVER_MAPPING_QUERY_PAGE ="/analyse/driverMapping/driverMappingSearch.jsp";
    String DRIVER_MAPPING_DETAIL_PAGE ="/analyse/driverMapping/driverMappingDetail.jsp";
    
    //================================kpi指标维护 ============================ 
	String KPI_DEFINE_QUERY_PAGE = "/system/kpi/kpiDefineSearch.jsp";
	String KPI_DEFINE_EDIT_PAGE = "/system/kpi/kpiDefineEdit.jsp";

	//================================HELP相关页面 ============================ 
    String HLP_RES_FRM_PAGE = "/system/help/helpFrm.jsp";
    String HLP_RES_SEARCH_PAGE = "/system/help/helpSearch.jsp";
    String HLP_RES_QUERY_PAGE = "/system/help/helpQuery.jsp";
    String HLP_RES_DETAIL_PAGE = "";
    String HLP_RES_TREE_PAGE = "/system/help/helpTree.jsp";


    //==============================================车辆辅助信息页面===============================================
	String ETS_ITEM_INFO_EX_QUERY_PAGE = "/expand/etsItemInfoExQuery.jsp";
	String ETS_ITEM_INFO_EX_DETAIL_PAGE = "/expand/etsItemInfoExDetail.jsp";
	String ETS_ITEM_INFO_EX_QUEREN_QUERY_PAGE = "/expand/etsItemInfoExQueRenQuery.jsp";
	String ETS_ITEM_INFO_EX_QUEREN_DETAIL_PAGE = "/expand/etsItemInfoExQueRenDetail.jsp";
	String ETS_ITEM_INFO_EX_SEARCH_QUERY_PAGE = "/expand/etsItemInfoExSearchQuery.jsp";

	//================================================土地管理页面================================================
	String ETS_EX_LAND_QUERY_PAGE = "/expand/etsItemLandInfoSearch.jsp";
	String ETS_EX_LAND_DETAIL_PAGE = "/expand/etsItemLandInfoDetail.jsp";
	String ETS_EX_LAND_FILE_PAGE = "/expand/uploadItemFile.jsp";
	String ETS_EX_LAND_QUERY_SERVLET = "/servlet/com.sino.ams.expand.servlet.EtsItemLandInfoServlet?act=" + WebActionConstant.QUERY_ACTION;
    
	//================================================闲置资产报废提醒================================================
	String FA_MSG_QRY_PAGE = "/newasset/free/message/freeAssetsMsgQuery.jsp";
	String FA_MSG_DTL_PAGE = "/newasset/free/message/freeAssetsMsgDetail.jsp";
	String FA_MSG_SERVLET = "/servlet/com.sino.ams.newasset.message.servlet.FreeAssetsMsgSetAndQueryServlet";
    
	//========================================通服资产匹配==========================================
	String TF_MATCH_RESULT = "/match/tf/tfMatchResult.jsp";//通服匹配监控报表页面
	String TF_UNYOKE_PAGE = "/match/tf/tfUnyoke.jsp";      //撤销通服资产匹配关系（暂定）
	String TF_FINANCE_PROP_SET_PAGE = "/match/tf/tfFinancePropSet.jsp";
	String TF_FINANCE_PROP_SET_SERVLET = "com.sino.ams.match.servlet.TfEtsItemMatchRecServlet";
	String TF_CHANGED_MATCHR_MACTPROPERTY = "/match/tf/tfMactProperty.jsp";	//匹配通服资产清单
	String TF_CHANGED_MATCHR_NOMACTINGASSET = "/match/tf/tfNomatchingPage.jsp";	//未匹配通服资产清单
	
	String RENT_ASSETS_QRY_PAGE = "/newasset/rent/odAssetsQuery.jsp";//其它个人资产报废
	
	// ================================= 仪器仪表检修 ====================================================
	String INSTRUMENT_YB_CHK_HISTORY_SERVLET = "/servlet/com.sino.nm.ams.instrument.servlet.AmsInstrumentEamYbChkHistoryServlet";
	// ================================= 仪器仪表检修 ====================================================

    // ================================= 备品备件管理 ====================================================
	String WAREHOUSE_QUERY_PAGE = "/spare/warehouseDefine/warehouseQuery.jsp";//仓库地点查询
	String WAREHOUSE_DETAIL_PAGE = "/spare/warehouseDefine/warehouseDetail.jsp";//仓库地点维护
    String ON_NET_SEARCH = "/spare/onnet/itemOnNetSearch.jsp"; //设备现网量查询
	String ON_NET_DETAIL = "/spare/onnet/itemOnNetDetail.jsp"; //设备现网量维护
    String SPARE_CATEORY_CONFIRM= "/spare/spareConfirmQuery.jsp";//备件分类确认查询
    String SPARE_CATEORY_INFO = "/spare/spareConfirmInfo.jsp";//备件分类确认维护
	// ================================= 备品备件管理 ====================================================
	
}

