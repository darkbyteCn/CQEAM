package com.sino.ams.newasset.constant;

import com.sino.base.constant.web.WebActionConstant;

/**
 *
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface AssetsURLList {
    //===============================================资产流程任务=============================
    String ASSETS_TRANS_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsTransHeaderServlet";
    String ASSETS_FREE_SERVLET = "/servlet/com.sino.ams.newasset.free.servlet.FreeAssetsHeaderServlet";
    String ASSETS_QUERY_PAGE = "/newasset/assetsTrans.jsp";
    String ORDER_QUERY_PAGE = "/newasset/orderQuery.jsp";
    String ORDER_PRINT_QUERY = "/newasset/printQuery.jsp";
    String PRINT_DETAIL_PAGE = "/newasset/orderPrintDetail.jsp"; //资产调拨单详细信息
    String ORDER_QUERY_SERVLET = "/servlet/com.sino.ams.newasset.servlet.OrderQueryServlet";
    String ORDER_PRINT_SERVLET = "/servlet/com.sino.ams.newasset.servlet.OrderPrintServlet";
    String TRANS_EDIT_PAGE = "/newasset/assetsTransEdit.jsp";
    String ASSETS_DISCARD_PAGE = "/newasset/assetsDiscardData.jsp";
    String TRANS_EDIT_PAGE_NM = "/newasset/assetsTransEdit_42.jsp";
    String FREE_EDIT_PAGE = "/newasset/free/freeAssetsEdit.jsp"; //减值单详细信息
    String FREE_DETAIL_PAGE = "/newasset/free/freeAssetsDetail.jsp"; //减值单详细信息
    String TRANS_DETAIL_PAGE = "/newasset/assetsTransDetail.jsp"; //资产调拨单详细信息
    String TRANS_DETAIL_PAGE_NM = "/newasset/assetsTransDetail_42.jsp"; //资产调拨单详细信息
    String ORDER_APPROVE_SERVLET = "/servlet/com.sino.ams.newasset.servlet.OrderApproveServlet";
    String APPROVE_EDIT_PAGE = "/newasset/assetsTransApprove.jsp"; //资产调拨单审批页面
    String APPROVE_EDIT_NM = "/newasset/assetsTransApprove_42.jsp"; //资产调拨单审批页面(内蒙古)
    String APPROVE_DETL_PAGE = "/newasset/transApproveDetail.jsp"; //资产调拨单审批后信息
    String APPROVE_DETL_NM = "/newasset/transApproveDetail_42.jsp"; //资产单据审批后信息(内蒙古)
    String APPROVE_DISCARD_PAGE = "/newasset/discardApprove.jsp"; //资产报废审批信息clearApprove
    String APPROVE_DISCARD_DTL_PAGE = "/newasset/discardApproveDetail.jsp"; //资产报废审批信息clearApprove
    String RCV_DETAIL_PAGE = "/newasset/assetsRcvDetail.jsp"; //资产调拨单接收详细信息
    String ASSETS_DEPT_SELECT = "/newasset/deptSelect.jsp";
    String GROUP_CHOOSE_SERVLET = "/servlet/com.sino.ams.newasset.servlet.GroupChooseServlet";
    String ASSETS_GROUP_SELECT = "/newasset/chooseGroup.jsp";
    String ASSETS_SUBMIT_SUCCESS = "/newasset/success.jsp";
    String ASSETS_TRANS_EDIT = "/newasset/assetsTransEdit.jsp";
    String ASSETS_FRM_PAGE = "/newasset/assetsFrm.jsp";
    String OD_ASSETS_SERVLET = "/servlet/com.sino.ams.newasset.servlet.OdEtsFaAssetsServlet";//其它个人作废资产
    String OD_ASSETS_FRM_PAGE = "/newasset/odAssetsFrm.jsp";//其它个人作废资产
    String ASSETS_FRM_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AssetsFrmServlet";
    String ASSETS_TOP_PAGE = "/newasset/assetsTop.jsp";
    String ASSETS_TOP_PAGE2 = "/newasset/assetsTop2.jsp";
    String ASSETS_TREE_PAGE = "/newasset/assetsTree.jsp";
    String ASSETS_QRY_PAGE = "/newasset/assetsQuery.jsp";
    String OD_ASSETS_QRY_PAGE = "/newasset/odAssetsQuery.jsp";
    String ADMIN_CONFIRM_PAGE = "/newasset/adminConfirmQuery.jsp";
    String ASSETS_QRY_PAGE2 = "/newasset/assetsQuery2.jsp";
    String ASSETS_QRY_SERVLET = "/servlet/com.sino.ams.newasset.servlet.EtsFaAssetsServlet?act=" + AssetsActionConstant.QUERY_ACTION;
    String ASSETS_DTL_PAGE = "/newasset/assetsDetail.jsp"; //资产详细信息
    String ASSETS_DTL_CT_PAGE = "/ct/equip/assetsDetail.jsp"; //村通资产详细信息
//	String ITEM_DTL_PAGE = "/newasset/itemDetail.jsp"; //设备详细信息
    String ASSETS_PRIVI_QRY = "/newasset/priviQuery.jsp";
    String ASSETS_PRIVI_DTL = "/newasset/priviDetail.jsp";
    String ASSETS_PRIVI_TOP = "/newasset/priviTop.jsp";
    String ASSETS_PRIVI_LEFT = "/newasset/priviLeft.jsp";
    String ASSETS_PRIVI_RIGHT = "/newasset/priviRight.jsp";
    String ASSETS_PRIVI_FRM = "/newasset/priviFrm.jsp";
    String ASSETS_PRIVI_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsPriviServlet";
    String PRIVI_RIGHT_SERVLET = "/servlet/com.sino.ams.newasset.servlet.PriviRightServlet";
    String ASSETS_STS_PROVINCE = "/newasset/assetsProvinceStatis.jsp";
    String ASSETS_STS_COMPANY = "/newasset/assetsCompanyStatis.jsp";
    String ASSETS_STS_DEPT = "/newasset/assetsDeptStatis.jsp";
    String ASSETS_STS_PERSONAL = "/newasset/assetsPersonalStatis.jsp";
    String ASSETS_RCV_QRY = "/newasset/assetsReceiveQuery.jsp";
    String ASSETS_ASSIGN_QRY = "/newasset/assetsAssignQuery.jsp";
    String ASSETS_RCV_SERVLRT = "/servlet/com.sino.ams.newasset.servlet.AssetsReceiveServlet";
    String ASSETS_ASSIGN_FRM = "/newasset/assignFrm.jsp";
    String ASSETS_ASSIGN_TOP = "/newasset/assignTop.jsp";
    String ASSETS_ASSIGN_LEFT = "/newasset/assignLeft.jsp";
    String ASSIGN_LEFT_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AssignLeftServlet";
    String ASSIGN_DEPT_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AssignDeptServlet";
    String ASSIGN_PERSON_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AssignPersonServlet";
    String ASSIGN_USER_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AssignUserServlet";
    String ASSETS_ASSIGN_PERSON = "/newasset/assignPerson.jsp";
    String ASSETS_ASSIGN_USER = "/newasset/assignUser.jsp";
    String ASSETS_ASSIGN_USER2 = "/newasset/assignUser2.jsp";
    String ASSETS_ASSIGN_LOCATION = "/newasset/assignLocation.jsp";
    String ASSETS_ASSIGN_DEPT = "/newasset/assignDept.jsp";
    String ASSETS_ASSIGN_RIGHT = "/newasset/assignRight.jsp";
    String ASSETS_ASSIGN_FRM_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AssignFrmServlet";
    String ASSIGN_RIGHT_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AssignRightServlet";
    String ASSETS_DISCARD_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AssetsDiscardServlet";
    String DISCARD_QRY_PAGE = "/newasset/discardOrderQuery.jsp";
    String DISCARD_EDIT_PAGE = "/newasset/discardOrderEdit.jsp";
    String ASSETS_CLEAR_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AssetsClearServlet";
    String CLEAR_QRY_PAGE = "/newasset/clearOrderQuery.jsp";
    String CLEAR_EDIT_PAGE = "/newasset/clearOrderEdit.jsp";
    String CHECK_BATC_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckBatchServlet";
    String CHECK_APPR_SERVLET = "/servlet/com.sino.ams.newasset.servlet.CheckApproveServlet";
    String BATCH_QRY_PAGE = "/newasset/checkBatchQuery.jsp";
    String BATCH_EDIT_PAGE = "/newasset/checkBatchEdit.jsp";
    String BATCH_DETAIL_PAGE = "/newasset/checkBatchDetail.jsp";
    String BATCH_APPROVE_EDIT = "/newasset/checkApproveEdit.jsp";
    String CHECK_HEADER_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsCheckHeaderServlet";
    String ORDER_ANALYZE_SERVLET = "/servlet/com.sino.ams.newasset.servlet.CheckOrderAnalyzeServlet";
    String HEADER_QRY_PAGE = "/newasset/checkOrderQuery.jsp";
    String ANALYZE_ORDER_QRY = "/newasset/chkOrderAnalyzeQuery.jsp";
    String ANALYZE_ORDER_DTL = "/newasset/chkOrderAnalyzeDetail.jsp";
    String HEADER_EDIT_PAGE = "/newasset/checkOrderDetail.jsp";
    String ARCHIVE_ORDER_SERVLET = "/servlet/com.sino.ams.newasset.servlet.ChkOrderArchiveServlet";
    String ARCHIVE_ORDER_QRY = "/newasset/archiveOrderQuery.jsp";
    String ORDER_ARCHIVE_QRY = "/newasset/orderArchiveQuery.jsp";
    String ORDER_ARCHIVE_SERVLET = "/servlet/com.sino.ams.newasset.servlet.OrderArchiveServlet";
    String ARCHIVE_ORDER_DTL = "/newasset/archiveOrderDetail.jsp";
    String CUST_QRY_SET_PAGE = "/newasset/customQuerySet.jsp";
    String CUST_QRY_PAGE = "/newasset/customQuery.jsp";
    String CUST_QRY_SET_SERVLET = "/servlet/com.sino.ams.newasset.servlet.CustomQuerySetServlet";
    String CUST_QRY_SERVLET = "/servlet/com.sino.ams.newasset.servlet.CustomQueryServlet";
    String BARCODE_HISTORY_PAGE = "/newasset/barcodeHistory.jsp";
    String LOCATION_QUERY_SERVLET = "/servlet/com.sino.ams.newasset.servlet.LoctionQueryServlet";
    String LOCATION_QUERY_PAGE = "/newasset/locationQuery.jsp";
    String LOCATION_DETAIL_PAGE = "/newasset/locationDetail.jsp";
    String DEPT_QRY_PAGE = "/newasset/deptQuery.jsp"; //部门查询界面
    String DEPT_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AmsMisDeptServlet"; //部门查询界面
    String COMM_QRY_PARA = "/newasset/commQueryPara.jsp";
    String COMM_QRY_RESULT = "/newasset/commonQueryResult.jsp";
    String FA_CAT_QRY_PAGE = "/newasset/faCatQuery.jsp";
    String FA_CAT_QRY_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AmsFaCategoryVServlet";
    String FA_CONTENT_PAGE = "/newasset/faDictContent.jsp";
    String FA_CONTENT_SERVLET = "/servlet/com.sino.ams.newasset.servlet.ContentPriviServlet"; //
    String RCV_HEADER_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsRcvHeaderServlet";
    String RCV_ORDER_DETAIL = "/newasset/rcvOrderDetail.jsp";
    String RCV_ORDER_APPROVE = "/newasset/rcvOrderApprove.jsp";
    String RCV_ORDER_QUERY = "/newasset/rcvOrderQuery.jsp";
    String ADMIN_CONFIRM_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AdminConfirmServlet";
    String ITEM_DATA_PAGE = "/newasset/itemData.jsp";
    String ITEM_FRM_PAGE = "/newasset/itemFrm.jsp";
    String ITEM_BOTTOM_PAGE = "/newasset/itemBottom.jsp";
    String ITEM_DETAIL_PAGE = "/newasset/itemDetail.jsp";
    String ITEM_MAINTAIN_SERVLET = "/servlet/com.sino.ams.newasset.servlet.ItemMaintainServlet";

    String MANY_DIMENSION_PAGE = "/system/manydimensions/manyDimensions.jsp";
    String MANY_DIMENSION_DATA_PAGE = "/system/manydimensions/ManyDimensionsData.jsp";
    String MANY_DIMENSION_BOTTOM_PAGE = "/system/manydimensions/ManyDimensionsBottom.jsp";
    String MANY_DIMENSION_FRM_PAGE = "/system/manydimensions/ManyDimensionsFrm.jsp";
    String MANY_DIMENSION_SERVLET = "/servlet/com.sino.ams.system.manydimensions.servlet.ManyDimensionsServlet";
    String MANY_DIMENSION_BOTTOM_SERVLET = "/servlet/com.sino.ams.system.manydimensions.servlet.ManyDimensionsBottomServlet";

    String ITEM_BOTTOM_SERVLET = "/servlet/com.sino.ams.newasset.servlet.ItemMainBottomServlet";
    String ITEM_LOG_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AmsItemCorrectLogServlet";
    String FREE_ASSETS_QRY = "/newasset/freeAssetsQuery.jsp";//闲置资产查询页面
    String MIS_ASSETS_QRY = "/newasset/misAssetsQuery.jsp";//MIS资产查询页面
    String MIS_DEPRN_ASSETS_QRY = "/newasset/misDeprnAssetsQuery.jsp";//MIS资产折旧查询页面
    String MIS_DISCARDED_ASSETS_QRY = "/newasset/misDiscardedAssetsQuery.jsp";//MIS资产报废查询页面
    String RCV_PRINT_QRY = "/newasset/rcvPrintQuery.jsp";//接收单据打印查询页面
    String RCV_ORDER_PRINT = "/newasset/rcvOrderPrint.jsp";//接收单据打印详细页面
    String ASSETS_INVI_PAGE = "/newasset/assetsInvi.jsp";//个人工单查询
    String ASSETS_INVI_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AssetsInviServlet";//个人工单查询

    String SPECIAL_ASSETS_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AmsSpecialAssetsServlet";
    String SPECIAL_ASSETS_QUERY_PAGE = "/newasset/specialAssetsPage.jsp";//特殊资产创建页面
    String SPECIAL_ASSETS_EDIT_PAGE = "/newasset/specialAssetsEdit.jsp";
    String SPECIAL_ASSETS_DETAIL_PAGE = "/newasset/specialAssetsDetail.jsp"; //特殊资产调拨单详细信息
    String SPECIAL_APPROVE_EDIT_PAGE = "/newasset/specialassetsTransApprove.jsp"; //特殊资产调拨单审批页面
    String SPECIAL_ORDER_APPROVE_SERVLET = "/servlet/com.sino.ams.newasset.servlet.SpecialOrderApproveServlet";
    String SPECIAL_APPROVE_DETL_PAGE = "/newasset/specialApproveDetail.jsp"; //特殊资产调拨单审批后信息


//=====================================以下是报表部分======================================
    String COMP_LOC_RPT = "/newasset/report/compLocationReport.jsp";
    String LOC_DTL_RPT = "/newasset/report/locDetailReport.jsp";
    String OWN_LOC_RPT = "/newasset/report/ownLocations.jsp";
    String SCANED_LOC_RPT = "/newasset/report/scanedLocations.jsp";
    String NOTSCANED_LOC_RPT = "/newasset/report/notScanedLocations.jsp";

    String COMP_ITEM_RPT = "/newasset/report/compItemReport.jsp";
    String LOC_ITEM_RPT = "/newasset/report/locItemReport.jsp";
    String ITEM_CAT_RPT = "/newasset/report/itemCatDetailReport.jsp";

    String OWN_ITEM_RPT = "/newasset/report/ownItems.jsp";
    String SCANED_ITEM_RPT = "/newasset/report/scanedItems.jsp";
    String DIFF_ITEM_RPT = "/newasset/report/diffItems.jsp";

    String SCAN_MONITOR_RPT = "/newasset/report/checkMonitorRpt.jsp";
    String SCAN_LOC_DTL = "/newasset/report/resLocDetailReport.jsp";

    String MAIN_RES_LOC = "/newasset/report/responsibleLocs.jsp";//特定代维公司的责任地点
    String SCAN_LOC_Y = "/newasset/report/scanedLocs.jsp";//特定代维公司的责任地点
    String SCAN_LOC_N = "/newasset/report/notScanedLocs.jsp";//特定代维公司的责任地点

    String SCAN_ITEMS_RPT = "/newasset/report/itemResponsibleScan.jsp";//特定代维公司，对特定地点的扫描情况

    String GROUP_CHK_RPT = "/newasset/report/groupCheckResult.jsp";
    String GROUP_FRAME_RPT = "/newasset/report/groupCheckFrm.jsp";//框架页面(按部门统计)
    String SCAN_GROUP_PAGE = "/newasset/report/checkGroup.jsp";
    String GROUP_LOCATION_PAGE = "/newasset/report/groupLocations.jsp";//巡检地点详细信息(按部门统计)
    String GROUP_ITEM_PAGE = "/newasset/report/groupItems.jsp";//盘点设备详细信息(按部门统计)
    String DEPT_ITEM_RPT = "/newasset/report/deptItemReport.jsp";

    String COST_DIFF_RPT = "/newasset/report/costDiffReport.jsp";//成本中心盘点差异报表
    String COST_DTL_RPT = "/newasset/report/costDtlReport.jsp";//成本中心盘点差异报表详细页面(框架页面)
    String COST_OWN_ASSETS = "/newasset/report/costOwnAssets.jsp";//成本中心盘点差异报表(账上资产)
    String COST_SCANED_ITEMS = "/newasset/report/costScanedItems.jsp";//成本中心盘点差异报表(以扫描设备)
    String COST_NOT_SCANED_ASSETS = "/newasset/report/costNotScanedAssets.jsp";//成本中心盘点差异报表(未扫描资产)

    String DEPT_DIFF_RPT = "/newasset/report/deptDiffReport.jsp";//部门盘点差异报表
    String DEPT_DIFF_DETAIL = "/newasset/report/deptDiffReportDetail.jsp";//部门盘点差异的详细情况
    String DEPT_OWN_ASSETS ="/newasset/report/deptOwnAssets.jsp"; //部门盘点差异报表(账上资产)
    String DEPT_SCANED_ITEMS ="/newasset/report/deptScanedItems.jsp"; //部门盘点差异报表(已扫描设备)
    String DEPT_NOT_SCANED_ASSETS ="/newasset/report/deptNotScanedAssets.jsp"; //部门盘点差异报表(未扫描设备)

    String FA_ANALYSIS_RPT = "/newasset/report/faAnalyseReport.jsp";//资产变动分析(第一层报表)
    String OU_ASSETS_RPT = "/newasset/report/orgAssetsReport.jsp";//资产变动分析(第二层特定OU报表)
    String OU_ASSETS_RPT_2 = "/newasset/report/orgAssetsReport_2.jsp";//资产变动分析(第二层特定OU报表)
    String OU_ASSETS_RPT_3 = "/newasset/report/orgAssetsReport_3.jsp";//资产变动分析(第二层特定OU报表)
    String CHECK_REPORT = "/newasset/report/checkReport.jsp";//盘点统计报表";
    String BTS_MONITOR_RPT = "/newasset/report/btsMonitorReport.jsp";//基站盘点用时监控报表1";
    String BTS_DETAIL_RRP = "/newasset/report/btsDetailReport.jsp";//基站盘点用时监控报表2";
    String COST_CHECK_REPORT = "/newasset/report/costCheckReport.jsp";//盘点统计报表";
    String ASSETS_CHG_REPORT = "/newasset/report/assetsChangeReport.jsp";//盘点统计报表";

    String CHECK_DETAIL_REPORT = "/newasset/report/checkDetailReport.jsp";//盘点明细统计报表";
    String PERSON_CHECK_REPORT = "/newasset/report/personCheckReport.jsp";//个人盘点报表

    String CHECK_RESULT_RPT = "/newasset/report/checkResult.jsp";//盘点结果统计报表--外层统计页(按OU)";
    String COST_CHECK_RESULT = "/newasset/report/costCheckResult.jsp";//盘点结果统计报表--外层统计页(按部门)";
    String CHECK_RESULT_FRM = "/newasset/report/checkResultFrm.jsp";//盘点结果统计报表--框架页";
    String CHECK_RESULT_RPT_1 = "/newasset/report/checkResult1.jsp";//盘点结果统计报表--账实一致";
    String CHECK_RESULT_RPT_2 = "/newasset/report/checkResult2.jsp";//盘点结果统计报表--资产属性不一致";
    String CHECK_RESULT_RPT_3 = "/newasset/report/checkResult3.jsp";//盘点结果统计报表--有卡无物(1)";
    String CHECK_RESULT_RPT_4 = "/newasset/report/checkResult4.jsp";//盘点结果统计报表--有物无卡";
    String CHECK_RESULT_RPT_5 = "/newasset/report/checkResult5.jsp";//盘点结果统计报表--有卡无物(2)";
    String CHECK_RESULT_RPT_6 = "/newasset/report/checkResult6.jsp";//无需PDA扫描清单

    String NEW_FA_CHK_REPORT = "/newasset/report/newCheckResult.jsp";//盘点结果统计报表--外层统计页(按OU)";
	String NEW_FA_FRAME = "/newasset/report/newAssetsFrm.jsp";//盘点结果统计报表--外层统计页(按OU)";
	String NEW_FA_REPORT = "/newasset/report/newAssetsReport.jsp";//盘点结果统计报表--外层统计页(按OU)";
	String NEW_FA_SCANED = "/newasset/report/newAssetsScaned.jsp";//盘点结果统计报表--外层统计页(按OU)";
	String NEW_FA_NOT_SCANED = "/newasset/report/newAssetsNotScaned.jsp";//盘点结果统计报表--外层统计页(按OU)";
	String NEW_FA_IDENTICAL = "/newasset/report/newAssetsIdentical.jsp";//盘点结果统计报表--外层统计页(按OU)";
    String NEW_FA_DIFFERENT = "/newasset/report/newAssetsDifferent.jsp";//盘点结果统计报表--外层统计页(按OU)";
    String NEW_CHECK_RESULT_DEPT = "/newasset/report/newCheckResultDept.jsp";//新增资产盘点监控（按部门）;
    String NEW_FA_FRAME_DEPT = "/newasset/report/newAssetsDeptFrm.jsp";//新增资产盘点监控（按部门）"明细FREAM";
    String NEW_FA_REPORT_DEPT = "/newasset/report/newAssetsDeptReport.jsp";//新增资产盘点监控（按部门，新增资产）
    String NEW_FA_SCANED_DEPT = "/newasset/report/newAssetsScanedDeptReport.jsp";//新增资产盘点监控（按部门,扫描资产）
    String NEW_FA_NOT_SCANED_DEPT = "/newasset/report/newAssetsNotScanedDeptReport.jsp";//新增资产盘点监控（按部门,扫描资产）
    String NEW_FA_IDENTICAL_DEPT = "/newasset/report/newAssetsIdenticalDeptReport.jsp";//新增资产盘点监控（按部门,账实一致）
    String NEW_FA_DIFFERENT_DEPT = "/newasset/report/newAssetsDifferentDeptReport.jsp";
    String NEW_FA_ASSETS_MONITOR_REPORT = "/newasset/report/newAssetsMonitorReport.jsp";    //新增资产监控报表
    String NEW_FA_ASSETS_MONITOR_FRAME = "/newasset/report/newAssetsMonitorFrm.jsp";  //新增资产监控报表--点击明细
    String NEW_FA_ASSETS_BY_MIS = "/newasset/report/newAssetsMonitorByMis.jsp"; //新增资产监控报表--MIS新增资产
    String NEW_FA_ASSETS_MONITOR_TOTAL_SCANED = "/newasset/report/newAssetsMonitorTotalScaned.jsp";//新增资产监控报表--点击明细--期间实际扫描资产明细(按OU)";
    String NEW_FA_ASSETS_MONITOR_SCANED = "/newasset/report/newAssetsMonitorScaned.jsp";//新增资产监控报表--点击明细--已扫描资产明细(按OU)";
    String NEW_FA_ASSETS_MONITOR_NEED_SCANED = "/newasset/report/newAssetsMonitorNeedScaned.jsp";//新增资产监控报表--点击明细--已完成新转资资产并且需PDA扫描确认明细(按OU)";
	String NEW_FA_ASSETS_MONITOR_NOT_SCANED = "/newasset/report/newAssetsMonitorNotScaned.jsp";//新增资产监控报表--点击明细--未扫描资产统计页(按OU)";
	String NEW_FA_ASSETS_MONITOR_IDENTICAL = "/newasset/report/newAssetsMonitorIdentical.jsp";//新增资产监控报表--点击明细--账实一致资产统计页(按OU)";
    String NEW_FA_ASSETS_MONITOR_DIFFERENT = "/newasset/report/newAssetsMonitorDifferent.jsp";//新增资产监控报表--点击明细--账实不一致资产统计页(按OU)";


//--------------------------------固定资产整体构成与分布情况-------------------------------------------------
    String SPECIAL_ASSETS_REPORT = "/newasset/report/assetsSpecialReport.jsp";//专业资产构成统计报表
    String DEPT_ASSETS_REPORT = "/newasset/report/assetsDeptReport.jsp";//部门资产构成统计报表
    String AREA_ASSETS_REPORT = "/newasset/report/assetsAreaReport.jsp";//区域资产构成统计报表
    String INVEST_ASSETS_REPORT = "/newasset/report/assetsInvestReport.jsp";//投资方向分类统计报表
    String INVEST_FREE_ASSETS_REPORT = "/newasset/report/freeAssetsInvestReport.jsp";//闲置资产投资方向分类统计报表
    String LOSE_ASSETS_REPORT = "/newasset/report/loseAssetsReport.jsp";//资产盘亏统计报表
    String CT_AREA_ASSETS_REPORT = "/newasset/report/ctAssetsAreaReport.jsp";  //村通资产区域统计
    String CT_SPECIAL_ASSETS_REPORT = "/newasset/report/ctAssetsSpecialReport.jsp";//村通资产专业统计报表
    String CT_DEPT_ASSETS_REPORT = "/newasset/report/assetsCTDeptReport.jsp";//村通资产部门统计报表
    String UNUSE_DEPT_ASSETS_REPORT = "/newasset/report/assetsUnuseDeptReport.jsp";	//闲置资产部门统计
    String DISCARDED_DEPT_ASSETS_REPORT = "/newasset/report/assetsDiscardedDeptReport.jsp";//报废资产部门统计
    String TODISCARDED_DEPT_ASSETS_REPORT = "/newasset/report/assetsToDiscardedDeptReport.jsp";//待报废资产部门统计
    String DH_DEPT_ASSETS_REPORT = "/newasset/report/assetsDHDeptReport.jsp";//低值易耗资产部门统计
    String DH_AREA_ASSETS_REPORT = "/newasset/report/assetsDHAreaReport.jsp";//低值易耗资产区域统计报表
    String DH_SPECIAL_ASSETS_REPORT = "/newasset/report/assetsDHSpecialReport.jsp";//低值易耗资产专业统计报表
    String DH_CATEGORY_ASSETS_REPORT = "/newasset/report/assetsDHCategoryReport.jsp";//低值易耗资产目录构成分布统计报表
    String FREE_SPECIAL_ASSETS_REPORT = "/newasset/report/assetsFreeSpecialReport.jsp";//闲置资产专业统计报表
    String DISCARDED_SPECIAL_ASSETS_REPORT = "/newasset/report/assetsDiscardedSpecialReport.jsp";//报废资产专业统计报表
    String TODISCARDED_SPECIAL_ASSETS_REPORT = "/newasset/report/assetsToDiscardedSpecialReport.jsp";//待报废资产专业统计报表
    String TD_SPECIAL_ASSETS_REPORT = "/newasset/report/assetsTDSpecialReport.jsp";//TD资产专业统计报表
    String TD_AREA_ASSETS_REPORT = "/newasset/report/assetsTDAreaReport.jsp";//TD资产区域统计报表
    String TD_DEPT_ASSETS_REPORT = "/newasset/report/assetsTDDeptReport.jsp";//TD资产部门统计
    String RENT_DEPT_ASSETS_REPORT = "/newasset/report/assetsRentDeptReport.jsp";//经营租赁资产专业统计报表
    String SPECIAL_IMPAIRMENT_ASSETS_REPORT = "/newasset/report/impairmentAssetsSpecialReport.jsp";//减值资产按专业资产构成统计报表
    String AREA_IMPAIRMENT_ASSETS_REPORT = "/newasset/report/impairmentAssetsAreaReport.jsp";//减值资产区域资产构成统计报表
    String IMPAIRMENT_ASSETS_REPORT = "/newasset/report/impairmentAssetsReport.jsp";//资产减值情况

//--------------------------------各流程资产状态与执行情况-资产新增-------------------------------------------
    String SPECIAL_NEW_ASSETS_REPORT = "/newasset/report/assetsNewSpecialReport.jsp";//投资方向分类统计报表(新增)
    String SPECIAL_FREE_ASSETS_REPORT = "/newasset/report/freeAssetsSpecialReport.jsp";//闲置资产应用领域统计报表

//------------------------------------------管理指标类报表-------------------------------------------------
    String LOSE_RATE_REPORT = "/newasset/report/assetsLoseRateReport.jsp";//资产盘亏率（公司层面）
    String MATCH_RATE_REPORT = "/newasset/report/assetsMatchRateReport.jsp";//年底盘点资产账实相符率（公司层面）
    String DISCARD_RATE_REPORT = "/newasset/report/assetsDiscardRateReport.jsp";//若干个月以上未处置的报废资产比率


    String ASSETS_SUB_EDIT_PAGE = "/newasset/sub/assetsSubEdit.jsp";
    String ASSETS_QUERY_PAGE_SHAN = "/newasset/sub/assetsTransShan.jsp";
    String ASSETS_SUB_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AmsAssetsJianzhirServlet";
    String EFA_REPORT = "/newasset/report/efaReport.jsp";

    String ASSETS_GENERAL_VER_PAGE = "/newasset/report/assetsGenVer.jsp";//管理指标类报表（表数据已有报表）
    String ASSETS_GENERAL_VER_DETAIL = "/newasset/report/assetsGenVerDetail.jsp";

    String ASSETS_IN_DATA_QUERY = "/newasset/report/assetsInDataQuery.jsp";//报表数据统一录入查询界面
    String ASSETS_IN_DATA_INFO = "/newasset/report/assetsInDataDetial.jsp";//报表数据统一录入界面
    String ASSETS_IN_DATA_SERVLET = "/servlet/com.sino.ams.newasset.report.servlet.ReportInDataServlet?act=" + WebActionConstant.QUERY_ACTION;
    String ASSETS_IN_DATA_REPROT = "/newasset/report/assetsInDataReport.jsp";//管理指标类报表（数据录入类报表）

//=====================================其他======================================
    String CHECK_DATA_PAGE = "/newasset/checkDataQuery.jsp";//查询实物台帐中找不到的正在盘点的资产
    String DZYH_COM_RPT = "/newasset/report/dzyhComReport.jsp";//低值易耗统计(公司)
    String DZYH_DEPT_RPT = "/newasset/report/dzyhDeptReport.jsp";//低值易耗统计(部门)

    String LASTING_COM_RPT = "/newasset/report/lastingComReport.jsp";//租赁资产统计(公司)
    String LASTING_DEPT_RPT = "/newasset/report/lastingDeptReport.jsp";//租赁资产统计(部门)

    String VILLAGE_COM_PRT = "/newasset/report/villageComReport.jsp";//村通代管资产统计(公司)
    String VILLAGE_DEPT_PRT = "/newasset/report/villageDeptReport.jsp";//村通代管资产统计(部门)

    String COST_NO_SAME_QRY = "/newasset/costNoSameQuery.jsp";//统计EFA条码对应成本中与EFA责任人对应成本中心不对应

    String TABLESPACE_MONITOR_QRY = "/newasset/amsTablespaceMoniQry.jsp";//查看表空间大小

    String JOB_CONTROL_QRY = "/newasset/amsJobControlQry.jsp";//自动JOB运行情况

    String SYN_JOB_QRY = "/newasset/amsSynJobQry.jsp";//同步JOB运行情况

    String INVALID_OBJECT_QRY = "/newasset/amsInvalidObjectQry.jsp";//查看是否有未编译的包

    String ITEM_STATUS_ERROR = "/newasset/itemStatusError.jsp";//处理EFA已报废但EII还正常的条码

    String COST_DEPT_REC_MATCH = "/newasset/costDeptRecMatch.jsp";

    String ASSETS_TURN_PKG = "/synchronize/assetsTurnQry.jsp";

    //=====================================抽查工单统计======================================
    String SAMPLING_CHK_REPORT = "/sampling/report/samplingChkResult.jsp";
    String SAMPLING_FRM_REPORT = "/sampling/report/samplingChkFrm.jsp";
    String SAMPLING_ONE_REPORT = "/sampling/report/samplingOneReport.jsp";
    String SAMPLING_TWO_REPORT = "/sampling/report/samplingTwoReport.jsp";
    String SAMPLING_THREE_REPORT = "/sampling/report/samplingThreeReport.jsp";
    String SAMPLING_FOUR_REPORT = "/sampling/report/samplingFourReport.jsp";

    //=====================================实物台账查询======================================
    String ITEM_FRM_QRY = "/newasset/itemFrmQuery.jsp";
    String ITEM_FRM_TREE = "/servlet/com.sino.ams.newasset.servlet.itemFrmTreeServlet";
    String ITEM_FRM_TREE_QRY = "/newasset/itemFrmTree.jsp";
    String ITEM_MAINTAIN_SERVLET3 = "/servlet/com.sino.ams.newasset.servlet.ItemMaintainServlet3";
    
    //=====================================资产调拨======================================
    String ASSETS_ALLOCATION_QUERY = "/newasset/allocation/assetsAllocationQuery.jsp";
    String ASSETS_ALLOCATION_SERVLET = "/servlet/com.sino.ams.newasset.allocation.servlet.AmsAssetsAllocationHeaderServlet";
    String ASSETS_ALLOCATION_EDIT = "/newasset/allocation/assetsAllocationEdit.jsp";
    String ASSETS_ALLOCATION_DETAIL = "/newasset/allocation/assetsAllocationDetail.jsp";
    String ASSETS_ALLOCATION_APPROVE = "/newasset/allocation/assetsAllocationApprove.jsp";
    String ASSETS_ALLOCATION_APPROVE_SERVLET = "/servlet/com.sino.ams.newasset.allocation.servlet.AmsAssetsAllocationApproveServlet";

    String ASSETS_RENT_PAGE = "/newasset/rent/assetsEdit.jsp";
    String ASSETS_DETAIL_PAGE = "/newasset/rent/assetsDetail.jsp";
    
    String ITEM_HISTORY_PAGE = "/newasset/itemHistory.jsp";
    
    
    String OBJECT_ASSETS_PAGE = "/newasset/objectAssets.jsp";
    
    String AAT_CONFIRM_YN_SERVLET = "/servlet/com.sino.ams.newasset.servlet.AATConfirmYNServlet";
    String AAT_CONFIRM_YN_PAGE = "/newasset/aatConfirmYnQuery.jsp";
    
    String ASSETS_DEVALUE_SERVLET = "/servlet/com.sino.ams.newasset.devalue.servlet.DevalueAssetsHeaderServlet";
    String DEVALUE_EDIT_PAGE = "/newasset/devalue/devalueAssetsEdit.jsp"; //减值单编辑信息
    String DEVALUE_DETAIL_PAGE = "/newasset/devalue/devalueAssetsDetail.jsp"; //减值单详细信息
    String DEVALUE_QUERY_PAGE = "/newasset/devalue/devalueQuery.jsp"; //减值单查询
    String DEVALUE_DETAIL = "/newasset/devalue/devalueDetail.jsp"; //减值单查询详细
    
    //信息部物资管理
    String INFORMATION_MATERIAL_SERVLET = "/servlet/com.sino.nm.ams.newasset.servlet.InformationMaterialManageServlet";
    String INFORMATION_MATERIAL_QUERY = "/nm/newasset/informationMaterialManage.jsp";
    String INFORMATION_MATERIAL_ADD = "/nm/newasset/addInformationMaterial.jsp";

    //=====================================备品备件管理======================================
    String BJ_VENDOR_QUERY = "/spare/bjVendorQuery.jsp";//备件维修公司查询
    String BJ_VENDOR_INFO = "/spare/bjVendorDetail.jsp";//备件维修公司维护
    String BJ_VENDOR_SERVLET = "/servlet/com.sino.ams.spare.servlet.BjVendorServlet?act=" + WebActionConstant.QUERY_ACTION;
    String SPARE_VENDOR_QUERY = "/spare/spareVendorQuery.jsp";//备件厂商查询
    String SPARE_VENDOR_INFO = "/spare/spareVendorDetail.jsp";//备件厂商维护
    String SPARE_VENDOR_SERVLET = "/servlet/com.sino.ams.spare.servlet.SpareVendorServlet?act=" + WebActionConstant.QUERY_ACTION;
    //=====================================备品备件管理======================================
    String SPARE_APPLY_SERVLET = "/servlet/com.sino.ams.spare.servlet.BjslServlet";
    
    
}
