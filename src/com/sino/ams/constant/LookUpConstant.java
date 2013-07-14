package com.sino.ams.constant;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-9-24
 * Time: 10:26:17
 */
public interface LookUpConstant {
	String LOOK_UP_CHAN_MIS_DEPT="LOOK_UP_CHAN_MIS_DEPT";//MIS责任部门
	String LOOK_UP_KEEP = "LOOK_UP_KEEP";
	String LOOK_UP_USER = "LOOK_UP_USER";
	String LOOK_UP_ITEM = "LOOK_UP_ITEM";
	String LOOK_UP_VENDOR = "LOOK_UP_VENDOR";
	String LOOK_UP_OBJECT = "LOOK_UP_OBJECT";
	String LOOK_UP_BTS = "LOOK_UP_BTS";
	String LOOK_UP_CT_BTS = "LOOK_UP_CT_BTS";
	String LOOK_UP_SYS_ITEM = "LOOK_UP_SYS_ITEM";
	String LOOK_UP_BEIJIAN_ITEM = "LOOK_UP_BEIJIAN_ITEM";
	String LOOK_UP_PROJECT = "LOOKUP_PROJECT";//项目
	String LOOK_UP_ASSETS = "LOOK_UP_ASSETS";//查找资产
	String LOOK_UP_PURVEY = "LOOK_UP_PURVEY";
	String LOOK_UP_ADDRESS = "LOOK_UP_ADDRESS";
	String LOOK_UP_CT_ADDRESS = "LOOK_UP_CT_ADDRESS";
	String LOOK_UP_PROJECT2 = "LOOKUP_PROJECT2";//项目2
	String LOOK_UP_PROJECT3 = "LOOK_UP_PROJECT3";//项目3,返回项目号
	String LOOK_UP_ASSETS_OBJECT = "LOOK_UP_ASSETS_OBJECT";//资产业务查找地点
	
	String LOOK_UP_TF_ASSETS_OBJECT = "LOOK_UP_TF_ASSETS_OBJECT";//通服资产业务查找地点
	
	String LOOK_UP_CT_ASSETS_OBJECT = "LOOK_UP_ASSETS_OBJECT";
	String LOOK_UP_ASSETS_RCV = "LOOK_UP_ASSETS_RCV";//资业务接收人
	String LOOK_UP_SYSITEM = "LOOK_UP_SYSITEM";//设备名称、规格
	String LOOK_UP_DEPT = "LOOK_UP_DEPT";//资产业务查找目的部门
	String BJ_SYSTEM_ITEM = "BJ_SYSTEM_ITEM";//查找设备类型
	String BJ_SYSTEM_ITEM_SX = "BJ_SYSTEM_ITEM_SX";//查找设备类型--用于山西
	String BJ_ITEM_CATEGORY = "BJ_ITEM_CATEGORY";//查找设备类型(包含部件号)
	String BJ_ITEM_CATEGORY3 = "BJ_ITEM_CATEGORY3";//查找设备类型(包含物料编码) FOR NM
	String BJ_ITEM_CATEGORY_SX = "BJ_ITEM_CATEGORY_SX";//查找设备类型(包含部件号)--用于山西
	String BJ_SPARE_CATEGORY = "BJ_SPARE_CATEGORY";//查找设备类型(包含部件号)--用于山西，AMS_SPARE_CATEGORY中，复选框
	String BJ_SPARE_CATEGORY1 = "BJ_SPARE_CATEGORY1";//查找设备类型--用于山西，AMS_SPARE_CATEGORY中  ,单选形式
	String BJSL_ITEM_INFO = "BJSL_ITEM_INFO";//备件申领查找标签号
	String BJSL_ITEM_INFO2 = "BJSL_ITEM_INFO2";//备件申领查找标签号(方案2)
	String BJSL_ITEM_INFO3 = "BJSL_ITEM_INFO3";//备件申领查找标签号(方案2)
	String BJSX_ITEM_INFO2 = "BJSX_ITEM_INFO2";//备件送修查找标签号(方案2)
	String BJSX_ITEM_INFO3 = "BJSX_ITEM_INFO3";//备件送修查找标签号(NM)
	String BJSXFH_ITEM_INFO = "BJSXFH_ITEM_INFO"; //备件送修返还查询设备(NM)
	String SPARE_RETURN = "SPARE_RETURN";   //备件归还(NM)
	String SPARE_LOAN_OBJECT = "SPARE_LOAN_OBJECT";   //备件借出仓库和OU(NM)
	String LOOK_UP_HOUSE = "LOOK_UP_HOUSE";//查找房屋类型
	String FLOW_AGENT_USER = "FLOW_AGENT_USER";//流程代理人
	String LOOK_UP_ITEM_SIMPLE = "LOOK_UP_ITEM_SIMPLE";    //设备类型详细信息（单选）
	String LOOK_UP_LAND = "LOOK_UP_LAND";//查找土地类型
	String LOOK_UP_DAY = "LOOK_UP_DAY";//租赁日期短信设置查找截至日期
	String LOOK_UP_INSTR = "LOOK_UP_INSTR"; // 仪器
	String LOOK_UP_BARCODENO = "LOOK_UP_BARCODENO";  //其它资产维护查找BARCODENO
	String LOOK_UP_CABEL = "LOOK_UP_CABEL";         //线缆类型
	String LOOKE_UP_BEIJIAN = "LOOKE_UP_BEIJIAN";
	String LOOKE_UP_BEIJIAN2 = "LOOKE_UP_BEIJIAN2"; //备件分配(方案2)
	String LOOKE_UP_PORTAGE = "LOOKE_UP_PORTAGE";// 工单查找搬迁地点
	String LOOK_UP_BATCH = "LOOK_UP_BATCH";    //按任务批查询工单
	String LOOK_UP_FH = "LOOK_UP_FH";
	String LOOK_UP_FH2 = "LOOK_UP_FH2";    //方案2
	String LOOK_UP_KUCUN = "LOOK_UP_KUCUN";
	String LOOK_UP_BF = "LOOK_UP_BF";
    String LOOK_UP_BF2 = "LOOK_UP_BF2";
    String LOOK_UP_BF3 = "LOOK_UP_BF3";
	String LOOK_UP_ITEM_BF = "LOOK_UP_ITEM_BF";
	String LOOK_UP_ITEM_BF2 = "LOOK_UP_ITEM_BF2";
	String FTMCK_OBJECT_NO = "FTMBK_OBJECT_NO";//非条码设备出库选择仓库
	String FTMCK_ITEM = "FTMCK_ITEM";       //非条码设备出库选择设备
	String SELECT_BARCODE = "SELECT_BARCODE";
	String LOOK_UP_VENDORS = "LOOK_UP_VENDORS"; //供应商
	String LOOK_UP_ASSETS_LOCATION = "LOOK_UP_ASSETS_LOCATION";    //MIS地点
	String LOOK_UP_TD_ASSETS_LOCATION = "LOOK_UP_TD_ASSETS_LOCATION";    //TD资产MIS地点
	String LOOK_UP_CT_ASSETS_LOCATION = "LOOK_UP_CT_ASSETS_LOCATION";
	String LOOK_UP_ASSETS_ADDRESS = "LOOK_UP_ASSETS_ADDRESS";//地点
	String LOOK_UP_EXACT_EQUIP = "LOOK_UP_EXACT_EQUIP"; // 正式的设备（相对临时）
	String LOOK_UP_INSTRUMENT = "LOOK_UP_INSTRUMENT";  //仪器仪表
	String LOOK_UP_USER1 = "LOOK_UP_USER1";//找人
	String LOOK_UP_USER_WITH_DEPT="LOOK_UP_USER_WITH_DEPT";//查找用户及部门
	String LOOK_UP_INSTR_ITEM = "LOOK_UP_INSTR_ITEM";//仪器仪表找类别
	String LOOK_UP_RETURN = "LOOK_UP_RETURN"; //仪器仪表找归还人
	String LOOK_UP_CITYINSTR = "LOOK_UP_CITYINSTR";   //查找地市仪器仪表人员
	String LOOK_UP_VENRETURN = "LOOK_UP_VENRETURN"; //送修返还
	String LOOK_UP_RESPUSER = "LOOK_UP_RESPUSER";//责任人
	String LOOK_UP_ASSETS_SYSITEM = "LOOK_UP_ASSETS_SYSITEM";//管理资产设备名称、规格
	String LOOK_UP_ASSETS_SYSITEM_S = "LOOK_UP_ASSETS_SYSITEM_S";//管理资产设备名称、规格，单选用
	String LOOK_UP_MIS_USER = "LOOK_UP_MIS_USER";//MIS责任人
	String LOOK_UP_MIS_DEPT = "LOOK_UP_MIS_DEPT";//MIS责任部门
    String LOOK_UP_COUNTY = "LOOK_UP_COUNTY";
    String LOOK_UP_BJBF = "LOOK_UP_BJBF"; //查找报废备件
	String LOOK_UP_FXSQ = "LOOK_UP_FXSQ";
	String LOOK_UP_DB = "LOOK_UP_DB";
	String LOOK_UP_FXRK = "LOOK_UP_FXRK";  //查找带修库数量
	String LOOK_UP_DEPT1 = "LOOK_UP_DEPT1";
	String LOOK_UP_TASK = "LOOK_UP_TASK";
	String LOOK_UP_BJSX = "LOOK_UP_BJSX";//备件送修
	String LOOK_UP_BJWXC ="LOOK_UP_BJWXCS";//查找备件维修厂商信息
	String LOOK_UP_RESPONSIBILITY = "LOOK_UP_RESPONSIBILITY";
	String LOOK_UP_MIS_INFO = "LOOK_UP_MIS_INFO";//查找mis的责任人和责任部门
	String LOOK_MAINTAIN_DEPT = "LOOK_MAIN_DEPT";//查找维护部门
	String COST_CENTER ="COST_CENTER";//查找成本中心
	String LOOK_UP_LOCATION = "LOOK_UP_LOCATION";//查找地点
	String LOOK_UP_MIS_RES = "LOOK_UP_MIS_RES";//查找地点
	String LOOK_UP_MIS_RES_USER = "LOOK_UP_MIS_RES_USER";//查找地点
	String LOOK_UP_USER_ID = "LOOK_UP_USER_ID"; //查找仓管员
	String LOOK_UP_UPDATED_USER = "LOOK_UP_UPDATED_USER"; //查找修改人
	String LOOK_UP_WORKORDER_OBJECT_NO = "LOOK_UP_WORKORDER_OBJECT_NO"; //查找仓库
	String LOOK_UP_WORKORDER_OBJECT_NO_NAME = "LOOK_UP_WORKORDER_OBJECT_NO_NAME"; //查找EAM_DH_BILL_L表中的仓库
	String LOOK_UP_CATALOG_VALUE_ID = "LOOK_UP_CATALOG_VALUE_ID"; //查找目录编号
	String LOOK_UP_RESPONSIBILITY_DEPT = "LOOK_UP_RESPONSIBILITY_DEPT"; //查找使用部门
	
	String LOOK_UP_RESPONSIBILITY_DEPT_OU = "LOOK_UP_RESPONSIBILITY_DEPT_OU"; //查找责任部门，过滤OU
	String LOOK_UP_RESPONSIBILITY_DEPT_OU2 = "LOOK_UP_RESPONSIBILITY_DEPT_OU2"; //查找责任部门，过滤OU
	
	String LOOK_UP_RESPONSIBILITY_USER = "LOOK_UP_RESPONSIBILITY_USER"; //查找领用人 
	String LOOK_UP_ITEM_CATEGORY2 = "LOOK_UP_ITEM_CATEGORY2"; //查找ETS_SYSTEM_ITEM中的ITEM_CATEGORY2字段
	String LOOK_UP_VENDOR_BARCODE = "LOOK_UP_VENDOR_BARCODE"; //查找物品编号
    String LOOK_UP_MANUFACTURER = "LOOK_UP_MANUFACTURER"; //查找厂商
    String LOOK_UP_CONTENT = "LOOK_UP_CONTENT";//查找目录
    String LOOK_UP_CONTENT_NOMATCH_LNE = "LOOK_UP_CONTENT_NOMATCH_LLE";    //查找没有对应逻辑网络元素属性的资产目录
    String LOOK_UP_CONTENT_NOMATCH_CEX = "LOOK_UP_CONTENT_NOMATCH_CEX";    //查找没有对应投资分类属性的资产目录
    String LOOK_UP_CONTENT_NOMATCH_OPE = "LOOK_UP_CONTENT_NOMATCH_OPE";    //查找没有对应业务平台属性的资产目录
    String LOOK_UP_CONTENT_NOMATCH_NLE = "LOOK_UP_CONTENT_NOMATCH_NLE";    //查找没有对应网络层次属性的资产目录

    String LOOK_UP_USER_RIGHT = "LOOK_UP_USER_RIGHT";
    
    String LOOK_UP_RES_DEPT = "LOOK_UP_RES_DEPT";
    String LOOK_UP_DZYH_CONTENT = "LOOK_UP_DZYH_CONTENT";	//低值易耗
    String LOOK_UP_ITEM_ADDRES = "LOOK_UP_ITEM_ADDRES";//查找专业责任人（低值易耗台帐用）

    String LOOK_UP_SYS_ITEM_DZYH = "LOOK_UP_SYS_ITEM_DZYH";
    
	String LOOK_UP_DEPT_IN = "LOOK_UP_DEPT_IN";//查找部门条码物资入库有权限地点
	String LOOK_UP_DEPT_OUT = "LOOK_UP_DEPT_OUT";//查找部门条码物资入库有权限地点
    
	
	String LOOK_UP_TEAM = "LOOK_UP_TEAM";
	String LOOK_UP_CK = "LOOK_UP_CK";
	
	String LOOK_UP_LOC2DESC = "LOOK_UP_LOC2DESC"; //查询地点第二段代码
	String LOOK_UP_COUNTYCODE = "LOOK_UP_COUNTYCODE"; //查找所属区域(第一段)
	
	String LOOK_UP_ASSETS_OBJECT2 = "LOOK_UP_ASSETS_OBJECT2";//查询备件仓库地点
    String LOOK_UP_HJGH_OBJECT = "LOOK_UP_HJGH_OBJECT";
    String BJ_SPARE_DX_CATEGORY = "BJ_SPARE_DX_CATEGORY";
    String LOOK_UP_BJCK = "LOOK_UP_BJCK";
    String LOOK_UP_DX_OBJECT = "LOOK_UP_DX_OBJECT";
    String LOOK_UP_SXFH = "LOOK_UP_SXFH";
    
	String LOOK_UP_DEPT_TRUNLIST = "LOOK_UP_DEPT_TRUNLIST";//查找责任部门（转资工单明细查询）
	String LOOK_UP_USER_NAME = "LOOK_UP_USER_NAME";//查找责任人（转资工单明细查询）
}

