package com.sino.sinoflow.constant;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public interface DictConstant {
	//start==============================字典分类=================================//
	String ITEM_TYPE = "ITEM_TYPE"; //物料类型
	String OBJECT_CATEGORY = "OBJECT_CATEGORY"; //地点类型
	String WORKORDER_TYPE = "WORKORDER_TYPE"; //工单类型
	String WORKORDER_STATUS = "WORKORDER_STATUS"; //工单状态
	String INV_TYPE = "INV_TYPE"; //仓库类型
	String SPREAD_TYPE = "SPREAD_TYPE"; //线缆铺设方式
	String CABEL_USAGE = "CABEL_USAGE"; //线缆用途
	String ORDER_STATUS = "ORDER_STATUS"; //单据状态
	String ASSETS_BUSINESS_TYPE = "ASSETS_BUSINESS_TYPE"; //资产业务类型
	String INV_BUSINESS_TYPE = "INV_BUSINESS_TYPE"; //库存业务类型
	String ORDER_TYPE_ASSETS = "ORDER_TYPE_ASSETS"; //资产单据类型
	String ORDER_TYPE_SPARE = "ORDER_TYPE_SPARE"; //库存单据类型
	String FLOW_STATUS = "FLOW_STATUS"; //流程业务状态
	String PROCESS_ACTION = "PROCESS_ACTION"; //数据处理类型
	String ORDER_ITEM_STATUS = "ORDER_ITEM_STATUS"; //工单设备状态
	String INS_BUSINESS_TYPE = "INS_BUSINESS_TYPE"; //仪器仪表业务类型
	String TIME_MEASURE_UNIT = "TIME_MEASURE_UNIT"; //时间计量单位
	String PLAN_STATUS = "PLAN_STATUS"; //计划状态
	String UNIT_OF_MEASURE = "UNIT_OF_MEASURE"; //计量单位
	String CHECK_MODLE = "CHECK_MODLE"; //巡检模式
	String PROJECT_TYPE = "PROJECT_TYPE"; //项目类型
	String PROJECT_STATUS = "PROJECT_STATUS"; //项目状态
	String INV_PRIVI = "INV_PRIVI"; //库存权限
	String FINANCE_PROP = "FINANCE_PROP"; //财务属性
	String ASSETS_STATUS = "ASSETS_STATUS"; //管理资产状态
	String OU_STATUS = "OU_STATUS"; //全部OU
	String PAY_TYPE = "PAY_TYPE";  //付款方式
	String LAND_AREA_UNIT = "LAND_AREA_UNIT";   //土地单位 （地积）
	String AREA_UNIT = "AREA_UNIT";//面积单位
	String HOUSE = "HOUSE"; //房屋
	String LAND = "LAND";   //土地
	String INTANGIBLE = "INTANGIBLE"; //无行资产
	String RENT = "RENT";//租赁资产信息
	String DG = "DG";//代管资产
	String ITEM_STATUS = "ITEM_STATUS";//设备状态
	String HOUSE_USAGE = "HOUSE_USAGE";//房屋用途
	String HOUSE_STATUS = "HOUSE_STATUS";//房屋状态
	String SPARE_REASON = "SPARE_REASON";//备件出入库原因
//end==============================字典分类=================================//

//===================二级字典=============================

	//start=================================工单类型===========================//
	String ORDER_TYPE_NEW = "10"; //新建工单
	String ORDER_TYPE_EXT = "11"; //扩容工单
	String ORDER_TYPE_CHECK = "12"; //巡检工单
	String ORDER_TYPE_FIX = "13"; //维修工单
	String ORDER_TYPE_TRANS = "14"; //搬迁工单
	String ORDER_TYPE_SUB = "15"; //减容工单
	String ORDER_TYPE_REP = "16"; //替换工单
//end=================================工单类型===========================//

	//start=================================工单状态===========================//
	String WORKORDER_STATUS_NEW = "10"; //新增
	String WORKORDER_STATUS_DISTRUIBUTED = "11"; //已下发
	String WORKORDER_STATUS_DOWNLOADED = "12"; //已下载
	String WORKORDER_STATUS_UPLOADED = "13"; //已上传
	String WORKORDER_STATUS_ACHIEVED = "14"; //已归档
	String WORKORDER_STATUS_CANCELE = "15"; //已取消
//end=================================工单状态===========================//

	//start===============================专业分类===========================//
	String CATEGORY_BTS = "BTS"; //基站
	String CATEGORY_DATA = "DATA"; //数据
	String CATEGORY_ELEC = "ELEC"; //电力
	String CATEGORY_EXCHG = "EXCHG"; //交换
	String CATEGORY_BSC = "BSC"; //BSC监控交换
	String CATEGORY_TRANS = "TRANS"; //传输
	String CATEGORY_NETOPT = "NETOPT"; //网优
	String CATEGORY_HOUSE = "HOUSE"; //租赁房屋
	String CATEGORY_LAND = "LAND"; //租赁土地
	String CATEGORY_INSTRU = "INSTRUMENT"; //仪器仪表
//end===============================专业分类===========================//

	//start====================工单节点(暂定--2007-09-22 zhoujs)============//
	String WORKORDER_NODE = "WORKORDER_NODE";
	String WORKORDER_NODE_NEW = "PROCESS1"; //登记
	String WORKORDER_NODE_APPROVE = "PROCESS2"; //审核
	String WORKORDER_NODE_DISTRUIBUTE = "PROCESS3"; //下发
	String WORKORDER_NODE_DOWNLOADED = "PROCESS4"; //已下载
	String WORKORDER_NODE_UPLODADED = "PROCESS5"; //已扫描上传
	String WORKORDER_NODE_ACHIEVE = "PROCESS6"; //下发
	String WORKORDER_NODE_CANCEL = "PROCESS7"; //撤销
//end======================工单节点(暂定--2007-09-22 zhoujs)===========//

	//start=========================工单查询类型===============================//
	String WOR_STATUS_NEW = "10"; //新增
	String WOR_STATUS_DEPLOY = "11"; //已下发
	String WOR_STATUS_DOWNLOAD = "12"; //已下载
	String WOR_STATUS_UPLOAD = "13"; //已上传
	String WOR_STATUS_ARCHIVED = "14"; //已归档
	String WOR_STATUS_CANCELED = "15"; //已取消
	String WOR_STATUS_OVERTIME = "1";    //超时
	String WOR_STATUS_INTEGAZATION = "76";    //工单综合查询
	String WOR_STATUS_BATCH = "2";     //工单按任务批查询
//end===========================工单状态===============================//

	//start=========================流程处理类型===========================//
	String FLOW_SAVE = "FLOW_SAVE"; //暂存
	String FLOW_COMPLETE = "FLOW_COMPLETE"; //完成
	String FLOW_TO = "FLOW_TO"; //特送
	String FLOW_BACK = "FLOW_BACK"; //退回
//end=========================流程处理类型===========================//

	//start=============================资产流程任务类型======================//
//    String ASSETS_FLOW_REDEPLOY = "RE_DEPLOY"; //资产调拨流程//该常量废弃不用
//    String ASSETS_FLOW_CLEAR = "CLEAR"; //资产清理流程//该常量废弃不用
//    String ASSETS_FLOW_DISCARD = "DISCARD"; //资产报废流程//该常量废弃不用
//    String ASSETS_FLOW_DOTATION = "DOTATION"; //资产赠与流程//该常量废弃不用
//end=============================资产流程任务类型======================//

	//start===============单据状态(资产业务、备件业务=========================//
	String SAVE_TEMP = "SAVE_TEMP"; //暂存
	String IN_PROCESS = "IN_PROCESS"; //处理中
	String REJECTED = "REJECTED"; //退回
	String APPROVED = "APPROVED"; //已审批--流程审批完成
	String COMPLETED = "COMPLETED"; //完成--单据代表的业务完成
	String CANCELED = "CANCELED"; //撤消
	String CREATE = "CREATE"; //新建
	String ACCEPTED = "ACCEPTED"; //待接受
	String SCANING = "SCANING"; //待扫描

//end===============单据状态(资产业务、备件业务=========================//

	//start===============资产网络地点类型=========================//
	String NETADDR_BTS = "10"; //基站地点
	String NETADDR_DATA = "50"; //数据机房
	String NETADDR_TRANS = "40"; //传输机房
	String NETADDR_EXCHG = "20"; //交换机房
	String NETADDR_BSC = "30"; //BSC监控机房
	String NETADDR_NETOPT = "15"; //网优地点
	String NETADDR_ELE = "60"; //电力机房
	String NETADDR_OTHERS = "80" ;//其他地点
//end===============资产网络地点类型=========================//

	//start===============备件仓库类型=========================//
	String INV_NORMAL = "71"; //正常库
	String INV_TO_REPAIR = "72"; //待修库
	String INV_SEND_REPAIR = "73"; //送修库
	String INV_ON_WAY = "74"; //在途库
	String INV_DISCARD = "75"; //报废库
//end===============备件仓库类型=========================//

	//start===============线缆铺设方式=========================//
	String CABEL_COVER_LINE = "1"; //直埋
	String CABEL_IN_SKY = "2"; //架空
	String CABEL_IN_WATER = "3"; //水底
//end===============线缆铺设方式=========================//

	//start===============线缆用途=========================//
	String CABEL_USAGE_1 = "1"; //一级干线
	String CABEL_USAGE_2 = "2"; //二级干线
	String CABEL_USAGE_LOCAL = "3"; //本地网线路
//end===============线缆用途=========================//

	//start===============资产单据类型=========================//
	String ASS_ALO = "ASS-RED"; //调拨单
	String ASS_DIS = "ASS-DIS"; //报废单
	String ASS_FAV = "ASS-FAV"; //赠与单
	String ASS_CLR = "ASS-CLR"; //清理单
//end===============资产单据类型=========================//

	//start===============备件单据类型=========================//
	String BJBF = "BJBF"; //报废单
	String BJCK = "BJCK"; //出库单
	String BJDB = "BJDB"; //调拨单
	String BJFH = "BJFH"; //送修返还单
	String BJFK = "BJFK"; //返库
	String BJRK = "BJRK"; //新购入库单
	String BJSL = "BJSL"; //申领单
	String BJSX = "BJSX"; //送修单
	String BJFP = "BJFP";  //分配
	String BJGH = "BJGH";  //归还,用于NM
	String TMRK = "TMRK"; //其它条码设备入库
	String TMCK = "TMCK"; //其它条码设备出库
	String FTMRK = "FTMRK"; //其它非条码设备入库
	String FTMCK = "FTMCK"; //其它非条码设备出库
	String BJPD = "BJPD"; //备件盘点
	String FXSQ="FXSQ";//返修申请
//end===============备件单据类型=========================//

	//start===============设备扫描状态=========================//
	String SCAN_STATUS_NEW = "0"; //新增
	String SCAN_STATUS_EXISTS = "5"; //有
	String SCAN_STATUS_NONE = "6"; //无
	String SCAN_STATUS_OFFLINE = "7"; //换下设备
	String SCAN_STATUS_REMAIN = "8";//剩余设备
//end===============设备扫描状态=========================//

	//start===============工作计划状态=========================//
	String PLAN_STUS_NEW = "1"; //新建
	String PLAN_STUS_TIMEDOUT = "3"; //过期
	String PLAN_STUS_RECEIVED = "4"; //已接收
//end===============工作计划状态=========================//

	//start===============工程项目状态=========================//
	String PRJ_STS_APPROVED = "APPROVED"; //已审批
	String PRJ_STS_CLOSED = "CLOSED"; //已关闭
	String PRJ_STS_UNAPPROVED = "UNAPPROVED"; //未审批
//end===============工程项目状态=========================//

	//start===============库存权限=========================//
	String INV_PRIVI_APP = "INV_APPLY"; //备件申领
	String INV_PRIVI_OUT = "INV_OUT"; //备件出库
	String INV_PRIVI_RPI = "INV_REPAIR_IN"; //送修返还
	String INV_PRIVI_RVI = "INV_RCV_IN"; //接收入库
	String INV_PRIVI_BDI = "INV_BAD_IN"; //坏件入库
	String INV_PRIVI_BDR = "INV_BAD_RETURN"; //坏件归还
	String INV_PRIVI_QRY = "INV_QUERY"; //备件查询
	String INV_PRIVI_ODP = "INV_ORDER_PRINT"; //单据打印
//end===============库存权限=========================//

	//start===============设备财务属性=========================//
	String FIN_PROP_PRJ = "PRJ_MTL"; //工程物资
	String FIN_PROP_ASSETS = "ASSETS"; //资产
	String FIN_PROP_SPARE = "SPARE"; //备件
	String FIN_PROP_OTHER = "OTHER"; //其他
	String FIN_PROP_UNKNOW = "UNKNOW";  //未知
//end===============设备财务属性=========================//

	//start===============库存专用=========================//
	String HAS_PRIVI_YES = "1";
	String HAS_PRIVI_NO = "0";
	//end===============库存专用=========================//
	//start===============仪具=========================//
	String INS_BRW = "INS-BRW";   //借用
	String INS_RET = "INS-RET";   //返还
	String INS_CHECK = "INS-CHK";
	String INS_REP = "INS-REP";  //送修
	String INS_VRE = "INS-VRE";//供应商返还

	String BORROW = "BORROW";    //借用
	String DISCARDED = "DISCARDED";  //报废
	String SEND_REPAIR = "SEND_REPAIR";//送修
//end===============仪具=========================//

	//end===============统计报表=========================//
	//start===============返修情况统计=========================//
	String ITEM_REPAIR_QUERY = "1";    //设备返修量-按名称
	String VENTOR_REPAIR_QUERY = "2";    //设备返修量-按厂家
	String REPAIR_STATISTIC_ITEM_NAME = "3";  //设备返修率-按名称
	String REPAIR_STATISTIC_VENDOR = "4";  //设备返修率-按厂家
//end===============返修情况统计=========================//

//start===============设备状态=========================//
	String ITEM_STATUS_CLEARED = "CLEARED";//已处置
	String ITEM_STATUS_DISCARDED = "DISCARDED";//已报废
	String ITEM_STATUS_NORMAL = "NORMAL";//正常
	String ITEM_STATUS_ON_WAY = "ON_WAY";//在途
	String ITEM_STATUS_SEND_REPAIR = "SEND_REPAIR";//送修：用于备件
	String ITEM_STATUS_TO_DISCARD = "TO_DISCARD";//待报废：用于备件
	String ITEM_STATUS_TO_REPAIR = "TO_REPAIR";//待修：用于备件
//end===============设备状态=========================//


//start==========================记录设备变动历史的单据类型============================//
	String LOG_ORDER_WORK = "1";//工单
	String LOG_ORDER_INV = "2";//库存单据
	String LOG_ORDER_ASSETS = "3";//资产单据
//end==========================记录设备变动历史的单据类型============================//

//begin====================省公司代码======================
	String PROVINCE_CODE_NM = "42";//内蒙移动公司省代码
	String PROVINCE_CODE_JIN = "41";//山西移动公司省代码
	String PROVINCE_CODE_SHAN= "40";//陕西移动公司省代码
//end===================省公司代码==================

	String EXPORT_RES_LOC = "EXPORT_RES_LOC";//导出责任地点
	String EXPORT_SCAN_LOC_Y = "EXPORT_SCAN_LOC_Y";//导出已巡检地点
	String EXPORT_SCAN_LOC_N = "EXPORT_SCAN_LOC_N";//导出未巡检地点

	String EXPORT_LOC_ITEM = "EXPORT_LOC_ITEM";//导出当前地点设备清单
	String EXPORT_SCAN_ITEM = "EXPORT_SCAN_ITEM";//导出最近巡检结果
	String EXPORT_DIFF_ITEM = "EXPORT_DIFF_ITEM";//导出差异情况

	String OWN_ITEM = "OWN_ITEM";//拥有设备
	String SCAN_ITEM_Y = "SCAN_ITEM_Y";//已巡检设备
	String SCAN_ITEM_N = "SCAN_ITEM_N";//未巡检设备
    
    String MLAND ="土地";
    String MHOUSE ="房屋";
    String HOUSE_AND_LAND ="房地合一";
    String UNSETTLED = "未处理";
    String SETTLED ="已处理";

    String ORG_PROVENCE = "省公司";

    final String SYBASE_DEFAULT_DATE = "1900-01-01";
}
