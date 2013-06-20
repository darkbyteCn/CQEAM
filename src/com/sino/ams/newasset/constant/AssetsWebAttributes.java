package com.sino.ams.newasset.constant;

import com.sino.ams.constant.WebAttrConstant;


/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface AssetsWebAttributes extends WebAttrConstant {
	String ORDER_AUTO_PROD = "完成时自动生成";
	String PROC_ARG_SCRIPT = "PROC_ARG_SCRIPT"; //流程所需要的参数脚本

	String DEPT_OPTIONS = "DEPT_OPTIONS";
	
	String USER_OPTIONS = "USER_OPTIONS";
	
	String GROUP_OPTIONS = "GROUP_OPTIONS";
    String ITEM_STATUS_OPTIONS = "ITEM_STATUS_OPTIONS";//设备状态
    String SHARE_STATUS_OPTIONS = "SHARE_STATUS_OPTIONS";//设备共享状态
    String ITEM_UNIT_OPTIONS = "ITEM_UNIT_OPTIONS";//计量单位
    String ITEM_DISCARD_OPTIONS = "ITEM_DISCARD_OPTIONS";//报废类型
    String DEAL_TYPE_OPTIONS = "";//处置类型

    String ORDER_HEAD_DATA = "ORDER_HEAD_DATA";
    String ZERO_TURN_DATA= "ZERO_TURN_DATA";//零购资产
	String ORDER_LINE_DATA = "ORDER_LINE_DATA";
	String PRIVI_DEPT_CODES = "PRIVI_DEPT_CODES";
	String IS_COMPANY_MANAGER = "IS_COMPANY_MANAGER";
	String IS_PROVINCE_MANAGER = "IS_PROVINCE_MANAGER";

    String ADDRESS_HEAD_DATA = "ADDRESS_HEAD_DATA";     //地点信息
	
	String ASSETS_TREE_CONFIRM = "ASSETS_TREE_CONFIRM"; //个人待确认资产
	String ASSETS_TREE_TRANSFER = "ASSETS_TREE_TRANSFER"; //调拨资产
	String ASSETS_TREE_DISCARD = "ASSETS_TREE_DISCARD"; //报废资产
	String ASSETS_TREE_CLEAR = "ASSETS_TREE_CLEAR"; //处置资产
	String ASSETS_TREE_PERSON = "ASSETS_TREE_PERSON"; //个人资产
	String ASSETS_TREE_DEPART = "ASSETS_TREE_DEPART"; //部门资产
	String ASSETS_TREE_COMPAN = "ASSETS_TREE_COMPAN"; //公司资产
	String ASSETS_TREE_PROVIN = "ASSETS_TREE_PROVIN"; //全省资产
	String LOCATION_TREE_QUERY = "LOCATION_TREE_QUERY"; //地点查询
	String ASSETS_TREE_COMM_QUERY = "ASSETS_TREE_COMM_QUERY"; //资产综合查询
	String ASSETS_TREE_CUST_QUERY = "ASSETS_TREE_CUST_QUERY"; //资产自定义查询

	String RENT_ASSETS_TREE_PERSON = "RENT_ASSETS_TREE_PERSON"; //个人租赁资产
	String RENT_ASSETS_TREE_DEPART = "RENT_ASSETS_TREE_DEPART"; //部门租赁资产
	String RENT_ASSETS_TREE_COMPAN = "RENT_ASSETS_TREE_COMPAN"; //公司租赁资产
	String RENT_ASSETS_TREE_PROVIN = "RENT_ASSETS_TREE_PROVIN"; //全省租赁资产

	String ASSETS_PERSON = "个人资产"; //个人资产
	String ASSETS_DEPART = "部门资产"; //部门资产
	String ASSETS_COMPAN = "公司资产"; //公司资产
	String ASSETS_PROVIN = "全省资产"; //全省资产
	String ASSETS_CONFIRM = "待确认资产"; //个人待确认资产
	String ASSETS_TRANSFER = "调出资产"; //调出资产
	String ASSETS_DISCARD = "报废资产";
	String ASSETS_CLEAR = "处置资产";
	String LOCATION_QUERY = "地点查询";
	String ASSETS_COMM_QUERY = "综合查询";
	String ASSETS_CUST_QUERY = "自定义查询";

	String RENT_ASSETS_PERSON = "个人租赁资产"; //个人租赁资产
	String RENT_ASSETS_DEPART = "部门租赁资产"; //部门租赁资产
	String RENT_ASSETS_COMPAN = "公司租赁资产"; //公司租赁资产
	String RENT_ASSETS_PROVIN = "全省租赁资产"; //全省租赁资产

	String[] ASSETS_TREE_TYPES = {ASSETS_TREE_PERSON, ASSETS_TREE_DEPART,
								 ASSETS_TREE_COMPAN, ASSETS_TREE_PROVIN,
								 ASSETS_TREE_CONFIRM,
								 ASSETS_TREE_TRANSFER, /*ASSETS_TREE_DISCARD, ASSETS_TREE_CLEAR,*/
								 LOCATION_TREE_QUERY, ASSETS_TREE_CUST_QUERY,
								 ASSETS_TREE_COMM_QUERY};
	String[] CAPTIONS_TREE_TYPES = {ASSETS_PERSON, ASSETS_DEPART, ASSETS_COMPAN,
								   ASSETS_PROVIN, ASSETS_CONFIRM,
								   ASSETS_TRANSFER,
								   /*ASSETS_DISCARD, ASSETS_CLEAR, */
								   LOCATION_QUERY, ASSETS_CUST_QUERY,
								   ASSETS_COMM_QUERY};

	String[] RENT_ASSETS_TYPES = {RENT_ASSETS_TREE_PERSON, RENT_ASSETS_TREE_DEPART,
								 RENT_ASSETS_TREE_COMPAN, RENT_ASSETS_TREE_PROVIN,
								 /*ASSETS_TREE_CONFIRM, ASSETS_TREE_TRANSFER, */
								 /*ASSETS_TREE_DISCARD, ASSETS_TREE_CLEAR,*/
								 /*LOCATION_TREE_QUERY, ASSETS_TREE_CUST_QUERY, ASSETS_TREE_COMM_QUERY*/};
	String[] RENT_CAPTIONS_TYPES = {RENT_ASSETS_PERSON, RENT_ASSETS_DEPART, RENT_ASSETS_COMPAN,
								   RENT_ASSETS_PROVIN, /*ASSETS_CONFIRM,
											  ASSETS_TRANSFER,*/
								   /*ASSETS_DISCARD, ASSETS_CLEAR, */
								   /*LOCATION_QUERY, ASSETS_CUST_QUERY,
											  ASSETS_COMM_QUERY*/};

	String ASSETS_TREE = "ASSETS_TREE"; //资产台账树
	String ASSETS_DATA = "ASSETS_DATA"; //资产相信信息
	String PRIVI_DATA = "PRIVI_DATA"; //资产权限数据
	String PRIVI_ROLE = "PRIVI_ROLE"; //资产权限数据
	String TREE_CATEGORY = "TREE_CATEGORY"; //资产树形展示类别
	String TRANSFER_TYPE = "TRANSFER_TYPE"; //根据调拨类别树形展示调拨资产
	String PRIVI_RADIO = "PRIVI_RADIO"; //资产权限数据
	String ASSETS_RADIO = "ASSETS_RADIO"; //资产按钮
	String PRIVI_DEPT_TREE = "PRIVI_DEPT_TREE"; //部门树形结构
	String ALL_USER_OPTION = "ALL_USER_OPTION"; //待选用户列表
	String EXIST_USER_OPTION = "EXIST_USER_OPTION"; //已选用户列表
	String EXPORT_SELECTED_ASSETS = "EXPORT_SELECTED_ASSETS"; //导出选择的资产
	String EXPORT_QUERY_ASSETS = "EXPORT_QUERY_ASSETS"; //导出查询的资产
	String ASSIGN_PERSONS = "ASSIGN_PERSONS"; //MIS员工下拉列表
	String ASSIGN_USERS = "ASSIGN_USERS"; //SinoEAM系统用户下拉列表
	String ASSIGN_LOCATIONS = "ASSIGN_LOCATIONS"; //地点下拉列表
	String ASSIGN_DEPTS = "ASSIGN_DEPTS"; //部门下拉列表

	String CHECK_BATCH_DATA = "CHECK_BATCH_DATA"; //盘点批数据
	String CHECK_HEADER_DATAS = "CHECK_HEADER_DATA"; //盘点批中的盘点工单数据
	String CHECK_HEADER_DATA = "CHECK_HEADER_DATA"; //盘点工单头数据
	String CHECK_LINE_DATAS = "CHECK_LINE_DATAS"; //盘点工单行数据
	String ORDER_STATUS_OPT = "ORDER_STATUS_OPT"; //盘点工单状态下拉框

	String TRANSFER_TYPE_OPTION = "TRANSFER_TYPE_OPTION"; //调拨单类型下拉框
	String DEPRE_ACCOUNT_OPTION = "DEPRE_ACCOUNT_OPTION"; //折旧账户下拉框
	String FA_CATEGORY_OPTION = "FA_CATEGORY_OPTION"; //资产类别下拉框
	String ALL_QRY_FIELDS = "ALL_QRY_FIELDS"; //可选查询字段
	String CHK_QRY_FIELDS = "CHK_QRY_FIELDS"; //已选查询字段
	String ALL_DIS_FIELDS = "ALL_DIS_FIELDS"; //可选显示字段
	String CHK_DIS_FIELDS = "CHK_DIS_FIELDS"; //已选显示字段

//用于综合查询
	String CUST_QUERY_PARA = "CUST_QUERY_PARA"; //查询字段构造的参数界面
	String COMM_QUERY_HEAD = "COMM_QUERY_HEAD"; //显示字段构造的数据列表头
	String HEADER_DIV_TOP = "HEADER_DIV_TOP"; //表头层距离顶端像素
	String DATA_DIV_TOP = "DATA_DIV_TOP"; //数据层距离顶端像素
	String TABLE_WIDTH = "TABLE_WIDTH"; //数据表格宽度
	String TD_WIDTH = "TD_WIDTH"; //数据表格列宽度
	String DATA_DIV_HEIGHT = "DATA_DIV_HEIGHT"; //数据层高度
	String ASSETS_DYNAMIC_URL = "ASSETS_DYNAMIC_URL";
	String ASS_PROP = "ASS_PROP";

	String COMM_QUERY_PARA = "COMM_QUERY_PARA"; //查询字段构造的参数界面--保留给蒋涛使用

//用于综合查询
	String BARCODE_HISTORY_DATA = "BARCODE_HISTORY_DATA";
	String RENT_HISTORY_DATA = "RENT_HISTORY_DATA";			//租赁资产变更历史
	String LOCATION_DATA = "LOCATION_DATA"; //资产地点数据
	String LOCATION_ASSETS_DATA = "LOCATION_ASSETS_DATA"; //地点下的资产数据

	String PRINT_TRANS_OUT = "PRINT_TRANS_OUT"; //调出单据打印(用于调出方)
	String PRINT_TRANS_IN = "PRINT_TRANS_IN"; //调入单据打印(用于接收方)

	String PRINT_TRANS_OUT_CAP = "调出打印"; //调出单据打印(用于调出方)
	String PRINT_TRANS_IN_CAP = "调入打印"; //调入单据打印(用于接收方)

	String[] PRINT_TRANS_VALUE = {PRINT_TRANS_OUT, PRINT_TRANS_IN};
	String[] PRINT_TRANS_CAP = {PRINT_TRANS_OUT_CAP, PRINT_TRANS_IN_CAP};
	String PRINT_RADIO = "PRINT_RADIO"; //调拨单据打印单选按钮

	String MTL_PRIVI_Y = "MTL_PRIVI_Y"; //已赋权限
	String MTL_PRIVI_N = "MTL_PRIVI_N"; //待赋权限

	String MTL_PRIVI_YES = "已赋权限"; //已赋权限
	String MTL_PRIVI_NO = "待赋权限"; //待赋权限

	String[] MTL_PRIVI_VAL = {MTL_PRIVI_Y, MTL_PRIVI_N};
	String[] MTL_PRIVI_CAP = {MTL_PRIVI_YES, MTL_PRIVI_NO};
	String MTL_PRIVI_RADIO = "MTL_PRIVI_RADIO";


	String ASSIGN_COST_CENTER = "ASSIGN_COST_CENTER"; //分配部门
	String ASSIGN_RESPONSIBLE_USER = "ASSIGN_RESPONSIBLE_USER"; //分配责任人
	String ASSIGN_MAINTAIN_USER = "ASSIGN_MAINTAIN_USER"; //分配使用人

	String ASS_COST_CENTER = "分配部门"; //分配成本中心
	String ASS_RESPONSIBLE_USER = "分配责任人"; //分配责任人
	String ASS_MAINTAIN_USER = "分配使用人"; //分配使用人

	String[] ASSIGN_VAL = {ASSIGN_MAINTAIN_USER, ASSIGN_RESPONSIBLE_USER,
						  ASSIGN_COST_CENTER};
	String[] ASSIGN_CAP = {ASS_MAINTAIN_USER, ASS_RESPONSIBLE_USER,
						  ASS_COST_CENTER};
	String ASSIGN_RADIO = "ASSIGN_RADIO";
	String BOOK_TYPE_OPTION = "BOOK_TYPE_OPTION";
	String RCV_ORDER_HEAD = "RCV_ORDER_HEAD"; //接收单头
	String RCV_ORDER_LINE = "RCV_ORDER_LINE"; //接收单行
	String RCV_APP_CONTENT = "RCV_APP_CONTENT";
	String APPROVE_CONTENT = "APPROVE_CONTENT";
	String BARCODE_LOGS = "BARCODE_LOGS";//标签号维护日志

	String REPORT_DATA = "REPORT_DATA";//报表数据
    String IS_GROUP_PID = "IS_GROUP_PID";//流程用
    String FINCE_PROP_MAPS = "FINCE_PROP_MAPS";//实物台账查询统计资产属性数量
    String TRANS_TYPE_OPTION = "TRANS_TYPE_OPTION";//工单类型下拉菜单，用于个人工单查询
    String MANAGE_INDICATORS_OPTION = "MANAGE_INDICATORS_OPTION";//管理指标类报表类型
    String IS_FINANCE_GROUP = "IS_FINANCE_GROUP";//是否属于财务部
    String IS_SPECIAL_GROUP = "IS_SPECIAL_GROUP";//是否属于实物部门
    
    String TD_ASSETS_TYPE ="TD";

    String ITEM_TREE = "ITEM_TREE";

    String EXPORT_TIP_MSG = "<div id=\"$$$exportTipMsg$$$\" style=\"position:absolute; bottom:45%; left:5; z-index:10; visibility:hidden\">\n"
						  + "\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
						  + "\t\t<tr>\n"
						  + "\t\t\t<td width=\"30%\"></td>\n"
						  + "\t\t\t<td bgcolor=\"#ff9900\">\n"
						  + "\t\t\t\t<table width=\"100%\" height=\"60\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\">\n"
						  + "\t\t\t\t\t<tr>\n"
						  + "\t\t\t\t\t\t<td bgcolor=\"#eeeeee\" align=\"center\">正在导出数据，请稍候......<img\n" +
            "                                src=\"/images/wait.gif\" alt=\"\"></td>\n"
						  + "\t\t\t\t\t</tr>\n"
						  + "\t\t\t\t</table>\n"
						  + "\t\t\t</td>\n"
						  + "\t\t\t<td width=\"30%\"></td>\n"
						  + "\t\t</tr>\n"
						  + "\t</table>\n"
						  + "</div>";//查询时使用的提示信息

}
