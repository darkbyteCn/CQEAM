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
public interface WebAttrConstant {

    String TRUE_VALUE = "Y";
    String FALSE_VALUE = "N";

    String ROOT_RESOURCE = "ROOT_RESOURCE";//用户授权访问的根菜单资源
    String MENU_RESOURCE = "MENU_RESOURCE";//用户授权访问的左边菜单资源
    String FIRST_RESOURCE = "FIRST_RESOURCE";//用户授权访问的第一个顶级菜单
    String MENU_OPTION = "MENU_OPTION";   //父栏目
    String SMALL_MENU_OPTION = "SMALL_MENU_OPTION";//子栏目
    String TRANS_TYPE="TRANS_TYPE";  //单据类型
    String RES_DATA = "RES_DATA";
    String RESOURCE_OPTION = "RESOURCE_OPTION";//菜单选项
    String ALL_ROLE_OPTION = "ALL_ROLE_OPTION";//所有的角色

    String OTHER_ROLE_OPTION = "OTHER_ROLE_OPTION";//其他未选角色
    
    String ALL_GROUP_OPTION = "ALL_GROUP_OPTION";//所有的组别
    String VIEW_ROLE_OPTION = "VIEW_ROLE_OPTION";//已选角色
    String VIEW_GROUP_OPTION = "VIEW_GROUP_OPTION";//已选组别
    String MENU_TREE = "MENU_TREE";//栏目定义用树形菜单
    String RES_PRIV_DTO = "RES_PRIV_DTO";//栏目权限数据

    String ENABLED_OPTION = "ENABLED_OPTION";//有效性下拉选项
    String GROUP_OPTION = "ALL_GROUP_OPTION";//组别选项
	String ROLE_OPTION = "ROLE_OPTION";//角色下拉框
    String FLOW_GROUP_OPTION = "FLOW_GROUP_OPTION";  //流程组别
    String GROUP_CATEGORY = "GROUP_CATEGORY";   //组别专业
    String GROUP_DTOSET = "GROUP_DTOSET";//组别DTOSET
    String TASK_FLOW_ROWSET = "TASK_FLOW_ROWSET"; //任务-流向 ROWSET
    String MULTI_TASK_FLOW_ROWSET = "MULTI_TASK_FLOW_ROWSET"; //任务-流向 ROWSET
    String REVIEW_USER_ROWSET = "REVIEW_USER_ROWSET"; //会签人员或组别 ROWSET
    String REVIEW_STATUS_ROWSET = "REVIEW_STATUS_ROWSET"; //会签人员或组别 ROWSET
    String CYCLE_USER_ROWSET = "CYCLE_USER_ROWSET"; //会签人员或组别 ROWSET
    String CYCLE_USERS_INFO = "CYCLE_USERS_INFO";       //会签人员状态信息
    String CYCLE_TYPE_INFO = "CYCLE_TYPE_INFO";
    String CYCLE_ALLUSERS_INFO = "CYCLE_ALLUSERS_INFO";
    String CYCLE_SELECTEDUSERS_INFO = "CYCLE_SELECTEDUSERS_INFO";
    String DEPT_GROUP_ATTR = "DEPT_GROUP_ATTR"; //部门组别对应信息
    String GROUP_ATTR = "GROUP_ATTR";//组别信息
    String ROLE_ATTR = "ROLE_ATTR";//角色信息
    String USER_ATTR = "USER_ATTR";//用户信息
    String ORG_GROUP_ATTR = "";//组织下组别信息--用户维护用
    String ALL_ROLE_ATTR = "ALL_ROLE_ATTR";//所有角色信息--用户维护用

    String OPERATOR_ATTR = "OPERATOR_ATTR";
    
    String BACKMANAGER_ATTR="BACKMANAGER_ATTR";//信访终结后续管理信息

    //---------------------------------------------------------------------------
    String PROCEDURE_ATTR = "PROCEDURE_ATTR";//流程信息
    String PROCEDURE_TEMP_FILE = "PROCEDURE_TEMP_FILE";

    //-----------------------------字典维护所需-----------------------------------//
    String FIRST_DICT = "FIRST_DICT";//
    String TREE_HTML = "TREE_HTML";
    String TREE_JS = "TREE_JS";
    String DICT_TYPE_DATA = "DICT_TYPE_DATA";
    String DICT_DATA = "DICT_DATA";
    String IS_INNER_RADIO = "IS_INNER_RADIO";//是否内置单选按钮
    String ENABLED_RADIO = "ENABLED_RADIO";//有效性单选按钮
    String MAINTAIN_RADIO = "MAINTAIN_RADIO";//可维护性单选按钮
    String DICT_PARENT_OPT = "DICT_PARENT_OPT";//字典分类下拉框

    //-----------------------------------------同步操作--------------------------------------------//
    String SYSCHRONIZE_DTO = "SYSCHRONIZE_DTO";
	String MESSAGE_DATA = "MESSAGE_DATA";

	String EFFECTIVE_Y = "Y";
	String EFFECTIVE_N = "N";
	String EFFECTIVE_YES = "是";
	String EFFECTIVE_NO = "否";
	String[] EFFECTIVE_CAP = {EFFECTIVE_YES, EFFECTIVE_NO};
	String[] EFFECTIVE_VAL = {EFFECTIVE_Y, EFFECTIVE_N};

	String RESEND_Y = "Y";
	String RESEND_N = "N";
	String RESEND_YES = "是";
	String RESEND_NO = "否";
	String[] RESEND_CAP = {RESEND_YES, RESEND_NO};
	String[] RESEND_VAL = {RESEND_Y, RESEND_N};

	String COLLECT_Y = "Y";
	String COLLECT_N = "N";
	String COLLECT_YES = "是";
	String COLLECT_NO = "否";
	String[] COLLECT_CAP = {COLLECT_YES, COLLECT_NO};
	String[] COLLECT_VAL = {COLLECT_Y, COLLECT_N};

    //---------------------------------------流程使用 DTO-----------------------------------------//
    String SF_APPLICATION_DTO = "SF_APPLICATION";   // 应用定义信息
    String SF_ACT_INFO_DTO = "SF_ACT_INFO";          // 流转信息
    String SF_CASE_INFO_DTO = "SF_CASE_INFO"; 

    String SF_ACTIDS_INFO = "SF_ACTIDS_INFO";

    String SF_ACT_PAUSE_DTO = "SF_ACT_PAUSE";

    //	-----------------------------------------流程维护--------------------------------------------//
	String PROJECT_OPTION_STR = "PROJECT_OPTION_STR";//工程 OPTION HTML。 新添加
	String GROUP_OPTION_STR = "GROUP_OPTION_STR";//组别的OPTION HTML。 新添加

	String PROJECT_OPTION_STR_ALL = "PROJECT_OPTION_STR_ALL"; //工程是否选中,下拉列表的选项是否处于选中状态
    String PROCEDURE_OPTION_STR_SELECT = "PROCEDURE_OPTION_STR_SELECT";
    String GROUP_OPTION_STR_SELECT = "GROUP_OPTION_STR_SELECT"; //组别是否选中,下拉列表是否处于选中状态
	String ROLE_OPTION_STR_SELECT = "ROLE_OPTION_STR_SELECT";//角色是否选中，下拉列表是否处于选中状态

	String PROJECT_GROUP_OPTION_STR = "PROJECT_GROUP_OPTION_STR"; //所选工程所对应的组别。实现菜单联动
	String PROJECT_ROLE_OPTION_STR = "PROJECT_GROUP_OPTION_STR"; //所选工程所对应的角色。实现菜单联动
	String USER_OPTION_STR = "USER_OPTION_STR"; //用户下拉列表
	String WORKSCHEDULE_ATTR = "WORKSCHEDULE_ATTR"; 
	String APP_ATTR = "APP_ATTR";
	String PROJECT_PROCEDURE_OPTION = "PROJECT_PROCEDURE_OPTION"; 
	String DELEGATION_ATT = "DELEGATION_ATT";
	String API_ATTR = "API_ATTR";
    String API_OPTION_STR = "API_OPTION_STR";
    String VALIDATION_ATTR = "VALIDATION_ATTR";
	String AUTOVALUE_ATTR = "AUTOVALUE_ATTR";
	String ADMINAUTHORITY_ATTR = "ADMINAUTHORITY_ATTR";
	String PROCEDURE_TASK_OPTION_STR = "PROCEDURE_TASK_OPTION_STR";//对应过程下的任务
	String HOLIDAYS_OPTION_STR = "HOLIDAYS_OPTION_STR";//节假日下拉列表
	String WORKHOUR_OPTION_STR = "WORKHOUR_OPTION_STR";//工作时间

    String APP_DATAID = "app_dataID";
    String SF_APPDATAID = "sf_appDataID";
    String SINOFLOW_WEB_OBJECT = "sf_object";
    String SINOFLOW_STATUS = "SINOFLOW_STAUTS";
    String SINOFLOW_NEW_CASE = "SINOFLOW_NEW_CASE";

    String SINOFLOW_CURSTATUS = "SINOFLOW_CURSTATUS";

    //流程工作栏
    String SF_KEYWORD = "SF_KEYWORD";
    String SF_SUBJECT = "SF_SUBJECT";
    String SF_RANGE = "SF_RANGE";
    String SF_OTHERS = "SF_OTHERS";
    String FROM_DATE = "FROM_DATE";
    String TO_DATE = "TO_DATE";
    String SORT_TYPE = "SORT_TYPE";
    String SORT_TYPE_STR = "SORT_TYPE_STR";
    String TYPE = "TYPE";
    String SF_CREATEBY = "SF_CREATEBY";
    String SF_INTRAY_TOP_LIST = "SF_INTRAY_TOP_LIST";
    String SF_PENDINGTRAY_TOP_LIST = "SF_PENDINGTRAY_TOP_LIST";
    String SF_OUTTRAY_TOP_LIST = "SF_OUTTRAY_TOP_LIST";
    String SF_INTRAY_BOTTOM_LIST = "SF_INTRAY_BOTTOM_LIST";
    String SF_PENDINGTRAY_BOTTOM_LIST = "SF_PENDINGTRAY_BOTTOM_LIST";
    String SF_OUTTRAY_BOTTOM_LIST = "SF_OUTTRAY_BOTTOM_LIST";

    String SF_ACT_COPY_INFO = "SF_ACT_COPY_INFO";
    String SF_NOTICESTRAY_LIST = "SF_NOTICESTRAY_LIST";
}

