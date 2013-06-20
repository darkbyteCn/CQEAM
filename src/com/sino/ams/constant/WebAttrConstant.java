package com.sino.ams.constant;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface WebAttrConstant {

    String TRUE_VALUE = "Y";
    String FALSE_VALUE = "N";
    String PAGE_TITLE="PAGE_TITLE";

    String ORDER_NO_AUTO_PRODUCE = "完成时自动生成";
    String LOOKUP_PROP = "LOOKUP_PROP";//暂放一下，可能稍后不需要


    String ROOT_RESOURCE = "ROOT_RESOURCE";//用户授权访问的根菜单资源
    String MENU_RESOURCE = "MENU_RESOURCE";//用户授权访问的左边菜单资源
    String FIRST_RESOURCE = "FIRST_RESOURCE";//用户授权访问的第一个顶级菜单
    String MENU_OPTION = "MENU_OPTION";   //父栏目
    String SMALL_MENU_OPTION = "SMALL_MENU_OPTION";//子栏目
    String TRANS_TYPE = "TRANS_TYPE";  //单据类型
    String RES_DATA = "RES_DATA";
    String RESOURCE_OPTION = "RESOURCE_OPTION";//菜单选项
    String ALL_ROLE_OPTION = "ALL_ROLE_OPTION";
    String VIEW_ROLE_OPTION = "VIEW_ROLE_OPTION";
    String MENU_TREE = "MENU_TREE";//栏目定义用树形菜单
    String RES_PRIV_DTO = "RES_PRIV_DTO";//栏目权限数据

    String ENABLED_OPTION = "ENABLED_OPTION";//有效性下拉选项
    String GROUP_OPTION = "ALL_GROUP_OPTION";//组别选项
    String ROLE_OPTION = "ROLE_OPTION";//角色下拉框
    String FLOW_GROUP_OPTION = "FLOW_GROUP_OPTION";  //流程组别
    String GROUP_CATEGORY = "GROUP_CATEGORY";   //组别专业
    String GROUP_DTOSET = "GROUP_DTOSET";//组别DTOSET
    String GROUP_ATTR = "GROUP_ATTR";//组别信息
    String ROLE_ATTR = "ROLE_ATTR";//角色信息
    String USER_ATTR = "USER_ATTR";//用户信息
    String ORG_GROUP_ATTR = "";//组织下组别信息--用户维护用
    String ALL_ROLE_ATTR = "ALL_ROLE_ATTR";//所有角色信息--用户维护用
    String USER_RIGHT_ATTR = "USER_RIGHT_ATTR";//用户权限--用户维护用
    String ALL_ORG_OPTION = "ALL_ORG_OPTION";//公司选项
    String COUNTY_ATTR = "COUNTY_ATTR";// 区县信息
    String RENT_DTO = "RENT_DTO";//租赁DTO
    String IS_RENT_OPTION = "IS_RENT_OPTION";//是否租赁选项
    String HOUSE_USAGE_OPTION = "HOUSE_USAGE";// 房屋用途下拉选项
    String HOUSE_STATUS_OPTION = "HOUSE_STATUS"; //  房屋状态下拉选项
    String IS_CERTIFICATE_OPTION = "IS_CERTIFICATE_OPTION"; //是否有房屋产权证下拉选项
    String ISLAND_CERTIFICATE_OPTION = "IS_CERTIFICATE";//是否有土地使用证
    String DZYH_DTO = "DZYH_DTO"; //   低值易耗
    String USER_OBJECT_DTO = "USER_OBJECT_DTO";//用户地点DTO
    //==================下拉列表定义start===========================
    String PROJECT_OPTION = "PROJECT_OPTION";//项目工程下拉列表
    String USER_OPTION = "USER_OPTION";//用户选择下拉列表
    String CATEGORY_OPTION = "CATEGORY_OPTION";//专业选择下拉列表
    //==================下拉列表定义end===========================
    String DIFF_COUNT = "DIFF_COUNT";//差异数量
    String MATCHABLE = "MATCHABLE"; //
    String CUR_SCAN_DTL = "CUR_SCAN_DTL";//当前扫描结果
    String PRE_SCAN_DTL = "PRE_SCAN_DTL";//上次巡检扫描结果
    String DIFF_SCAN_DTL = "DIFF_SCAN_DTL"; //差异结果
    String DIFF_DTOSET = "DIFF_DOTSET";//差异DTOSet
    String BOUNDEN = "BOUNDEN";//责任人列表
    String WORKORDER_BATCH_ATTR = "WORKORDER_BATCH_ATTR";//工单批信息
    String WORKORDER_ATTR = "WORKORDER_ATTR";//工单信息
    String WORKORDER_DATAS = "WORKORDER_DATAS";//工单批中的工单信息
    String WORKORDER_LOC_ROWSET = "WORKORDER_LOC_ROWSET";//工单地点数据集合信息
    String WORK_PLAN_DTO = "WORK_PLAN_DTO";    //工作计划维护DTO常量
    String PLAN_STATUS_OPTION = "PLAN_STATUS_OPTION";  //  状态下拉列表
    String PLAN_TYPE_OPTION = "PLAN_TYPE_OPTION";  //  工单类型下拉列表
    String OBJECT_BTS_DTO = "OBJECT_BTS_DTO";       //基站维护DTO
    String OBJECT_ELE_DTO = "OBJECT_ELE_DTO";      //基站电费的维护
    String ETS_OBJECT_DTO = "ETS_OBJECT_DTO";  //资产地点表(EAM)DTO
    String ETS_WORKORDER_DTO = "ETS_WORKORDER_DTO";  //工单DTO
    String ETS_WORKORDER_TMP_DTO = "ETS_WORKORDER_TMP_DTO";  //临时工单DTO
    String ETS_WO_SCHEME_RST = "ETS_WO_SCHEME_RST";//工单配置信息RowSet
    String CUR_OBJ_SCHEME_RST = "CUR_OBJ_SCHEME_RST";//工单当前地点设备配置信息RowSet
    String ETS_WORKORDER_BATCH_DTO = "ETS_WORKORDER_BATCH_DTO";  //工单批DTO
    String ETS_WORKORDER_ITEM_DTO = "ETS_WORKORDER_ITEM_DTO"; //备件维护
    String ETS_ITEM_DTO = "ETS_ITEM_DTO";//设备维护
    String AMS_INSTRUMENT_DTO = "EAM_INSTRUMENT_DTO";//仪器维护
    String AMS_INSTRUMENT_REGISTRATION = "AMS_INSTRUMENT_REGISTRATION"; //仪器登记卡信息
    String AMS_INSTRUMENT_INV_OUT = "EAM_INSTRUMENT_INV_OUT"; //仪器仪表借用实物出库
    String AMS_INSTRUMENT_RETURN = "EAM_INSTRUMENT_RETURN"; //仪器仪表借用返还
    String AMS_INSTRUMENT_REPAIR = "EAM_INSTRUMENT_REPAIR"; //仪器仪表送修
    String AMS_INSTRUMENT_REPAIR_RETURN = "EAM_INSTRUMENT_REPAIR_RETURN"; //仪器仪表送修返还
    String AMS_INSTRUMENT_CHK_HISTORY = "EAM_INSTRUMENT__CHK_HISTORY"; //仪器仪表检修历史
    String AMS_INSTRUMENT_WORKORDER_OBJECT_NO = "EAM_INSTRUMENT_WORKORDER_OBJECT_NO"; //仪器仪表地点维护
    String TASK_NAME_EXIST = "TASK_NAME_EXIST"; //任务名称存在
    String TASK_NAME_NOT_EXIST = "TASK_NAME_NOT_EXIST"; //任务名称不存在
    String AMS_INSTRUMENTH_DTO = "AMS_INSTRUMENTH_DTO"; //头信息
    String AMS_INSTRUMENTL_DTO = "EAM_INSTRUMENTL_DTO";//行信息
    String AMS_ITEMH_REPAIR = "EAM_ITEMH_REPAIR";// 头信息
    String AMS_ITEML_REPAIR = "EAM_ITEMH_REPAIR";// 行信息
    String OBJECT_CATEGORY_DTO = "OBJECT_CATEGORY_DTO"; //ETS_OBJECT_CATEGOY DTO
    String BARCODE_PRINT_DTO = "BARCODE_PRINT_DTO"; //EAM_BARCODE_PRINT DTO
    String BARCODE_RECEIVE_DTO = "BARCODE_RECEIVE_DTO";   //标签领用DTO
    

    String BARCODE_MAX_MAINTAIN_DTO = "BARCODE_MAX_MAINTAIN_DTO";  //最大标签维护DTO
    String ON_NET_DTO = "ON_NET_DTO";  // EAM_ITEM_ON_NET DTO
    String AMS_SPARE_DTO = "EAM_SPARE_DTO";//EAM_SPARE_DTO
    String AMS_ELEMENT_MATCH_DTO = "EAM_ELEMENT_MATCH_DTO";        //网络特征属性对应关系DTO
    String MANUFACTURER_DTO = "MANUFACTURER_DTO";     //厂商信息DTO
    String IMPINFO_DTO = "IMPINFO_DTO";     //发布信息DTO
    //---------------------------------------------------------------------------
    String COUNTY_OPTION = "COUNTY_OPTION";//区县下拉选项
    String CITY_OPTION = "CITY_OPTION";//地市下拉选项
    String MAINTAIN_CORP_OPTION = "MAINTAIN_CORP_OPTION";//代维公司下拉选项
    String AREA_OPTION = "AREA_OPTION";//区域类型下拉选项

    String INV_CATEGORY = "70";
    String INV_CATEGORY_MAX = "80";

    String MAINTAIN_CORP_ATTR = "MAINTAIN_CORP_ATTR"; //代维公司信息
    String MAINTAIN_CORP_USR_ATTR = "MAINTAIN_CORP_USR_ATTR";//代维公司人员信息
    String MAINTAIN_CORP_RSP_ATTR = "MAINTAIN_CORP_RSP_ATTR";//代维公司责任信息
    String ATTACH_FILE_ATTR = "ATTACH_FILE_ATTR";//是否上载附件
    String ATTACH_FILES = "ATTACH_FILES";//代维公司相关附件
    String DEPT_CATEGORYS = "DEPT_CATEGORYS"; //员工所属部门分类下拉选项
    //---------------------------------------------------------------------------
    String PORCDURE_ATTR = "PORCDURE_ATTR";//流程信息
    //---------------------------------------------------------------------------
    String OU_OPTION = "OU_OPTION"; //ou下拉选项
    String OU_OPTION2 = "OU_OPTION2"; //ou下拉选项
    String DIS_OU_OPTION = "DIS_OU_OPTION";//物料分配组织
    String EQUIPMENT_OPTION = "EQUIPMENT"; //设备分类下拉选项
    String ETS_SYSTEM_ITEM_DTO = "ETS_SYSTEM_ITEM"; //设备分类表(EAM)DTO
    String ETS_SYSTEM_DISTRIBUTE_DTO = "ETS_SYSTEM_DISTRIBUTE"; //物料组织分配表DTO
    String AMS_HOUSE_INFO_DTO = "AMS_HOUSE_INFO_DTO"; //租赁房屋表DTO
    String AMS_LAND_INFO_DTO = "AMS_LAND_INFO_DTO"; //土地维护DTO
    String AMS_RENT_DEADLINE_DTO = "AMS_RENT_DEADLINE_DTO";//租期设置表DTO
    String ITEM_UNIT_OPTION = "ITEM_UNIT_OPTION";//计量单位
    String PROJECT_TYPE_OPTION = "PROJECT_TYPE_OPTION";//项目类型
    String PROJECT_STATUS_OPTION = "PROJECT_STATUS_OPTION";//项目状态
    String PAY_TYPE_OPTION = "PAY_TYPE_OPTION";//付款方式下拉选项
    String LAND_AREA_UNIT_OPTION = "LAND_AREA_UNIT_OPTION";  //地积单位下拉选项
    String AREA_UNIT_OPTION = "AREA_UNIT_OPTION"; //面积单位下拉选项 （房屋面积）
    String IS_MATCH_OPTION = "IS_MATCH_OPTION";  //是否匹配的下拉选项
    String IS_SHARE_OPTION = "IS_SHARE_OPTION"; //是否共享下拉菜单
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
    //----------------------------------------------------------------//

    String ETS_PA_PROJECTS_ALL_DTO = "ETS_PA_PROJECTS_ALL"; //项目信息维护DTO
    String ETS_OBJECT_ATTRIBUTE_DTO = "ETS_OBJECT_ATTRIBUTE"; //资产地点属性扩展弹性域表DTO
    //----------------------------------------------------------------//
    String INSTRU_STATUS = "INSTRU_STATUS";//仪器是否完整下拉框
    String TRANS_STATUS = "TRANS_STATUS";//单据状态
    String SPARE_REASON = "SPARE_REASON";//备件出入库原因
    String INV_OPTION = "INV_OPTION";//仓库下拉列表
    String CHECK_OPTION = "CHECK_OPTION";//巡检模式下拉列表
    String BORROW_OPTION = "BORROW_OPTION";//仪器仪表地市管理员选项
    //工单附件及处理意见
    String SUGGETION_DTO = "SUGGETION_DTO";//工单处理意见
    String ORDER_FILE_DTO = "ORDER_FILE_DTO";//工单附件

    //----------------------------------------------------------------//
    String WAREHOUSE_ATTR = "WAREHOUSE_ATTR";//仓库信息
    String WAREHOUSE_TYPE_OPTION = "WAREHOUSE_TYPE_OPTION";//仓库类型下拉列表
    String BUSIHOUSE_TYPE_OPTION = "BUSIHOUSE_TYPE_OPTION";//业务类型下拉列表
    String CODE_EXIST = "CODE_EXIST";
    String CODE_NOT_EXIST = "CODE_NOT_EXIST";
    String CATEGORY_EXIST = "CATEGORY_EXIST";
    String CATEGORY_NOT_EXIST = "CATEGORY_NOT_EXISTT";
    String OBJECT_CODE = "OBJECT_CODE";

    //--------------------------------------资产台账类----------------------------------//
    String ASSETS_TREE = "ASSETS_TREE";//资产台账树
    String ASSETS_DATA = "ASSETS_DATA";//资产相信信息/

    //--------------------------------------仓库权限定义类----------------------------------//
    String INV_PRIVI_DTO = "INV_PRIVI_ATTR"; //仓库权限DTO

    //--------------------------------------备件版本控制定义类----------------------------------//
    String AMS_ITEM_VERSION_DTO = "EAM_ITEM_VERSION_DTO";       //版本DTO
    //-----------------------------------------------------------------------------------------//

    //-----------------------------------------------------------------------------------------//
    String CABEL_INFO_DTO = "CABEL_INFO_DTO";           //电缆DTO
    String SPREAD_TYPE_OPTION = "SPREAD_TYPE_OPTION";         //铺设方式opt
    String CABEL_USAGE_OPTION = "CABEL_USAGE_OPTION";         //电缆用途opt
    //-----------------------------------------------------------------------------------------//


    //-----------------------------------------------------------------------------------------//
    String TRANS_D_DTO = "TRANS_D_DTO";        //备件分配信息DTO
    String TRANS_D_DTOS = "TRANS_D_DTOS";    //备件分配信息DTO组
    String ALLOT_H_DTO = "ALLOT_H_DTO";//备件分配头信息
    String ALLOT_D_DTO = "ALLOT_D_DTO";  //备件分配行信息
    String SPARE_CATEGORY_DTO = "SPARE_CATEGORY_DTO";   //部件号维护
    //-----------------------------------------------------------------------------------------//


    //-----------------------------------------------------------------------------------------//
    String LOCUS_DTO = "LOCUS_DTO";  //地点DTO
    String LOCUS_FLAG = "LOCUS_FLAG"; //地点类型标记（objectCategory 组别 ）；
    String UNCHECK = "225";                       //未巡检
    //-----------------------------------------------------------------------------------------//


    //-----------------------------------------------------------------------------------------//
    String BY_PROJECTID = "BY_PROJECTID";         //按项目 查询统计
    String BY_BARCODE = "BY_BARCODE";             //按条码 查询统计
    String BY_SPEC = "BY_SPEC";                    //按专业 查询统计
    String BY_CATEGORY = "BY_CATEGORY";          //按分类 查询统计
    String BY_LOCUS = "BY_LOCUS";                 //按地点 查询统计
    String BY_ALLOT = "BY_ALLOT";                 //按仓库 查询统计
    String BY_CITY = "BY_CITY";                   //按城市 查询统计
    String BY_COUNTY = "BY_COUNTY";              //按区县 查询统计
    String BY_PROV = "BY_PROV";                   //按省  查询统计
    String BY_NAME = "BY_NAME";                   //按名称 查询统计
    String BY_VENDOR = "BY_VENDOR";               //供应商 查询统计
    String BY_DAIWEI = "BY_DAIWEI";               //按代维公司 查询统计

    //--工单统计--

    String BY_MONTH = "BY_MONTH";                 //按月     统计
    String BY_CHECK = "BY_CHECK";                   //巡检    统计
    String BY_YEAR = "BY_YEAR";                   //按年     统计
    String BY_TIME = "BY_TIME";                   //按时间段 统计

    String AVISO_DTO = "AVISO_DTO";
    String LAST_FIVE_YEAR_OPTION = "Last_FIVE_YEAR_OPTION";
    String FULL_MONTH_OPTION = "FULL_MONTH_OPTION";
    String ITEM_INFO_DTO = "ITEM_INFO";          //设备DTO
    String ITEM_TYPE_OPTION = "ITEM_TYPE_OPTION";
    String FINANCE_PROP_OPTION = "FINANCE_PROP_OPTION";
    String OBJECT_CATEGORY_OPTION = "OBJECT_CATEGORY_OPTION";
    String ITEM_CATEGORY_OPTION = "ITEM_CATEGORY_OPTION";
    String INV_TYPE_OPTION = "INV_TYPE_OPTION";
    String ITEM_STATUS_OPTION = "ITEM_STATUS_OPTION";
    String LEFT_CATEGORY_OPTION = "LEFT_CATEGORY_OPTION"; //专业选择
    String RIGHT_CATEGORY_OPTION = "RIGHT_CATEGORY_OPTION"; //专业已存在
    String PRINT_OPTION = "PRINT_OPTION"; //标签申领人所选的ou下拉选项

    //-----------------------------------工单查询类型--------------------------------------------//
    String WOR_STATUS_NEW = "10"; //新增
    String WOR_STATUS_DEPLOY = "11"; //已下发
    String WOR_STATUS_DOWNLOAD = "12"; //已下载
    String WOR_STATUS_UPLOAD = "13"; //已上传
    String WOR_STATUS_ARCHIVED = "14"; //已归档
    String WOR_STATUS_CANCELED = "15"; //已取消
    String WOR_STATUS_OVERTIME = "1";    //超时
    String WOR_STATUS_INTEGAZATION = "76";    //工单综合查询
    String WOR_STATUS_BATCH = "2";     //工单按任务批查询
    //-----------------------------------------------------------------------------------------//

    //-------------------------------------------匹配方式(临时)--------------------------------//
    String MATCH_MODE_SPARE = "0";        //备件确认
    String MATCH_MODE_SPARE_RET = "1";   //撤销备件关系
    String MATCH_MODE_PRJMTL = "2";      //工程物资确认
    String MATCH_MODE_PRJMTL_RET = "3"; //撤销工程物资匹配关系
    String MATCH_MODE_OTHER = "4";       //设备屏蔽
    String MATCH_MODE_0THER_RET = "5";  //撤销设备屏蔽
    String MATCH_MODE_CHANGED_ASSETS = "6"; //    6：转资匹配
    String MATHC_MODE_CHANGED_ASSETS_RET = "7";  //    7：撤销资产匹配关系
    String MATCH_MODE_BARCODE = "8"; //    8：设备－资产条码匹配
    //    9：设备－资产人工匹配
    String MATCH_MODE_UNUSED_ASSETS = "10";//未使用设备删除
    String MATCH_MODE_RENT = "11";//租赁资产确认
    String MATCH_MODE_RENT_RET = "12";//撤销租赁资产确认
    String MATCH_MODE_DG = "13";//代管资产确认
    String MATCH_MODE_DG_RET = "14";//撤销代管资产确认
    String SCREEN_EXPROT = "15";// MIS屏蔽资产导入
    String MATCH_MODE_LC = "16";//低值易耗资产确认
    String MATCH_MODE_CT = "17";//村通资产确认
    String MATCH_MODE_LC_RET = "18";//低值易耗资产撤销
    String MATCH_MODE_TD = "19";//TD资产确认
    String MATCH_MODE_TD_RET = "20";//TD资产撤销

    //-----------------------------------------------------------------------------------------//

    //---------------------------------------地点匹配-------------------------------------------//
    String AMS_LOCATION_OPTION = "AMS_LOCATION_OPTION"; //ams未匹配的下拉框
    String MIS_LOCATION_OPTION = "AMS_LOCATION_OPTION";  //mis未匹配的下拉框
    //-----------------------------------------------------------------------------------------//
    //-----------------------------------------------------------------------------------------//

    //-----------------------------------------资产匹配--------------------------------------------//
    String CHANGED_ASSETS_LEFT = "CHANGED_ASSETS_LEFT";//转资匹配:工程物资信息
    String CHANGED_ASSETS_RIGHT = "CHANGED_ASSETS_RIGHT";//转资匹配:MIS资产信息
    String CHANGED_ASSETS_MACT = "CHANGED_ASSETS_MACT";
    String ETS_EQUIPMENT_DTO = "ETS_EQUIPMENT_DTO";//资产匹配报表：剩余设备清单
    String BARCODE_MATCH_DTO = "BARCODE_MATCH_DTO";//资产条码匹配
    String ETS_EQUIPMENT = "1";//设备资产分类：1：设备类
    String ETS_PRACTINFO_DTO = "ETS_PRACTINFO_DTO";

    //-----------------------------------------备品备件--------------------------------------------//
    String ETS_SPARE_DTO = "ETS_SPARE_DTO"; //备品备件DTO
    String SPARE_VENDOR_OPTION = "SPARE_VENDOR_OPTION";
    String SPARE_FROM_OBJECT_OPTION = "SPARE_FROM_OBJECT_OPTION";

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

    String RESPONSI_LOCATIONS = "RESPONSI_LOCATIONS";//责任地点
    String SCANED_LOCATIONS_Y = "SCANED_LOCATIONS_Y";//已巡检地点
    String SCANED_LOCATIONS_N = "SCANED_LOCATIONS_N";//未巡检地点
    String WORKORDER_DTO = "WORKORDER_DTO";//查询参数


    String RESPONSI_ITEMS = "RESPONSI_ITEMS";//责任设备
    String SCANED_ITEMS_Y = "SCANED_ITEMS_Y";//已巡检设备
    String SCANED_ITEMS_N = "SCANED_ITEMS_N";//未巡检设备

    String GROUP_LOCATIONS = "GROUP_LOCATIONS";
    String GROUP_ITEMS = "GROUP_ITEMS";
    String OU_DTO = "OU_DTO";//查询参数
    String DEPT_DTO = "DEPT_DTO";//查询参数

    String SPARE_CATEGORY_OPTION = "SPARE_CATEGORY_OPTION";
    String VENDOR_OPTION = "VENDOR_OPTION";

    String DEFAULT_PASSWORD = "eamprd";

    //-----------------------------低值易耗维护所需-----------------------------------//
    int DZYH_TRUE_VALUE = 1;
    String DZYH_FALSE_VALUE = "0";
    String FIRST_DZYH = "FIRST_DZYH";//
    String DZYH_TREE_HTML = "DZYH_TREE_HTML";
    String DZYH_TREE_JS = "DZYH_TREE_JS";
    String DZYH_TYPE_DATA = "DZYH_TYPE_DATA";
    String DZYH_DATA = "DZYH_DATA";
    String DZYH_ENABLED_RADIO = "ENABLED_RADIO";//有效性单选按钮
    String DZYH_BARCODE_FLAG = "DZYH_BARCODE_FLAG";//低值易耗条码标识
    String DZYH_COMMON_FLAG = "DZYH_COMMON_FLAG";//低值易耗常用标识
    String UNIT_OF_MEASURE = "19";//计量单位
    String DZYH_BILL_STATUS = "DZYH_BILL_STATUS";//低值易耗单据状态
    String DZYH_DISPOSE_REASON = "DZYH_DISPOSE_REASON";//低值易耗处置原因
    String DZYH_DISPOSE_TYPE = "DZYH_DISPOSE_TYPE";//低值易耗处置方式
    String DZYH_HISTORY_DATA = "DZYH_HISTORY_DATA";

    String DZYH_BILL_ORG_OPT = "DZYH_BILL_ORG_OPT";//低值易耗单据状态下拉框
    String DZYH_BILL_STATUS_OPT = "DZYH_BILL_STATUS_OPT";//低值易耗单据状态下拉框
    String DZYH_CATEGORY2_OPT = "DZYH_CATEGORY2_OPT";//低值易耗目录编号下拉框
    String DZYH_ITEM_NAME_OPT = "DZYH_ITEM_NAME_OPT";//低值易耗品名下拉框
    String DZYH_ITEM_SPEC_OPT = "DZYH_ITEM_SPEC_OPT";//低值易耗规格类型下拉框
    String DZYH_RESPONSIBILITY_DEPT_OPT = "DZYH_RESPONSIBILITY_DEPT_OPT";//低值易耗使用部门下拉框
    String DZYH_RESPONSIBILITY_USER_OPT = "DZYH_RESPONSIBILITY_USER_OPT";//低值易耗领用人下拉框
    String DZYH_ADDRESS_NAME_OPT = "DZYH_ADDRESS_NAME_OPT";//低值易耗地点下拉框
    String DZYH_PARENT_OPT = "DZYH_PARENT_OPT";//低值易耗下拉框
    String DZYH_BARCODE_OPT = "DZYH_PBARCODE_OPT";//条码标识下拉框
    String DZYH_COMMON_OPT = "DZYH_COMMON_OPT";//常用标识下拉框
    String DZYH_UNIT_OF_MEASURE_OPT = "DZYH_UNIT_OF_MEASURE_OPT";//计量单位下拉框
    String DZYH_DISPOSE_REASON_OPT = "DZYH_DISPOSE_REASON_OPT";//低值易耗处置原因下拉框
    String DZYH_DISPOSE_TYPE_OPT = "DZYH_DISPOSE_TYPE_OPT";//低值易耗处置方式下拉框

    //-----------------------------------------------------------------------------------//

    //-----------------------------低值易耗权限定义维护所需-----------------------------------//
    String DZYH_PRIVI_DATA = "DZYH_PRIVI_DATA";
    String DZYH_ENABLED_OPTION = "DZYH_ENABLED_OPTION";
    String DZYH_DEFAULTFLAG_OPTION = "DZYH_DEFAULTFLAG_OPTION";
    //-----------------------------------------------------------------------------------//

    //-----------------------------动因维护所需-----------------------------------//
    String FILE_VERSION_OPTION = "FILE_VERSION_OPTION";
    String DRIVER_CATEGORY_OPTION = "DRIVER_CATEGORY_OPTION";// 动因分类下拉框
    String DRIVER_NAME_OPTION = "DRIVER_NAME_OPTION";// 动因名称下拉框

    //-----------------------------动因对应关系维护所需-----------------------------------//
    String MAPPING_CATEGORY_OPTION = "MAPPING_CATEGORY_OPTION";
    String DIRVER_RELATION_OPTION = "DIRVER_RELATION_OPTION";
    String DRIVER_MAPPING_DTO = "DRIVER_MAPPING_DTO";
    String DIRVER_CODE_OPTION = "DIRVER_CODE_OPTION";

    String RESOURCE_DATA="RESOURCE_DATA";
    
    //-----------------------------其它个人报废资产所需-----------------------------------//
    String OTHER_DISCARDED_ITEM = "OTHER_DISCARDED_ITEM";//其它个人报废资产
    
    //----------------------------------资产属性----------------------------------------//
    String FINANCE_PROP = "FINANCE_PROP";//资产属性
     
    String ALL_RES_NAME = "ALL_RES_NAME";
    String SEARCH_ADVANCE = "ADVANCE"; //高级查询
    
    String FIELDS_EXIST = "FIELDS_EXIST";
	String FIELDS_NOT_EXIST = "FIELDS_NOT_EXIST";
	
	String REC_BARCODE_DTO = "REC_BARCODE_DTO"; //标签领用DTO
}

