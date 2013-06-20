package com.sino.ams.constant;

import com.sino.base.constant.web.WebActionConstant;

/**
 * Created by IntelliJ IDEA.
 * User: V-yuanshuai
 * Date: 2007-9-29
 * Time: 11:11:51
 */
public interface AMSActionConstant extends WebActionConstant {
	String DISABLED_ACTION = "DISABLED_ACTION";//失效操作
	String EFFICIENT_ACTION = "EFFICIENT_ACTION";//批量生效操作
	String INURE_ACTION = "INURE_ACTION";  //生效操作
	String QUERY_DETAIL = "QUERY_DETAIL";//查询详细页面，不需编辑
	String TRANSFER_ACTION = "TRANSFER_ACTION";//调出，用于调拨流程
	String DISCARD_ACTION = "DISCARD_ACTION";//报废操作
	String DISTRIBUTE_ACTION = "DISTRIBUTE_ACTION"; //分配操作
	String BUILD_NO_ACTION = "BUILD_NO_ACTION";//生成barcodeNo操作
	String MATCH_ACTION = "MATCH_ACTION";//匹配操作
	String SCREEN_ASSET_ACTION = "SCREEN_ASSET_ACTION";//撤消屏蔽
	String CONFIRM_ACTION = "CONFIRM_ACTION";//确认操作
	String REJIGGER_ACTION ="REJIGGER_ACTION";//合并操作
	String INSTEAD_ACTION = "INSTEAD_ACTION";//更改操作
	String STATISTICS_ACTION = "STATISTICS_ACTION";//统计操作
	String REASON_STATISTICS = "REASON_STATISTICS"; //原因统计
	String BORROW_ACTION ="BORROW_ACTION";  //借出操作
	String REPAIR_ACTION = "REPAIR_ACTION"; //送修操作
	String TEMP_SAVE = "TEMP_SAVE_ACTION"; //暂存操作
	String INIT_ACTION = "INIT_ACTION";//初始化操作
	String YEAR_QUERY_ACTION ="YEAR_QUERY_ACTION"; //年度返修统计操作
	String LOC_QUERY_ACTION ="LOC_QUERY_ACTION"; //地市返修统计操作
	String YEAR_EXPORT_ACTION = "YEAR_EXPORT_ACTION";//导出的操作。
	String LOC_EXPORT_ACTION = "LOC_EXPORT_ACTION";//导出的操作。
	String IMPORT_ACTION = "IMPORT_ACTION";//导入操作，将数据从其他数据源复制到本数据源
	String ENABLE_ACTION = "ENABLE_ACTION";//生效操作
	String VALIDATE_ACTION = "VALIDATE_ACTION";//校验操作
	String VALIDATE_LOCATION_ACTION = "VALIDATE_LOCATION_ACTION";//校验操作
	String CHANGE_COUNTY = "CHANGE_COUNTY";//改变OU时需改变区县
	String CHANGE_COUNTYS ="CHANGE_COUNTYS";//地点维护中市改变区县
    String MENU_TREE="MENU_TREE";
    String EXCEL_IMP="EXCEL_IMP";
    String VALIDATE_BTSNO = "VALIDATE_BTSNO"; //验证基站或营业厅编号是否存在
    String VALIDATE_WORKORDEROBJECTNAME = "VALIDATE_WORKORDEROBJECTNAME"; //验证地点名称是否存在
}
