package com.sino.ams.ct.bean;

/**
 * Created by MyEclipse.
 * User: yushibo
 * Date: 2008-12-08
 * Time: 10:26:17
 */
public interface LookUpCtConstant {
	String LOOK_UP_KEEP = "LOOK_UP_KEEP";
	String LOOK_UP_USER = "LOOK_UP_USER";
	String LOOK_UP_ITEM = "LOOK_UP_ITEM";
	String LOOK_UP_VENDOR = "LOOK_UP_VENDOR";
	String LOOK_UP_OBJECT = "LOOK_UP_OBJECT";
	String LOOK_UP_BTS = "LOOK_UP_BTS";
	String LOOK_UP_SYS_ITEM = "LOOK_UP_SYS_ITEM";
	String LOOK_UP_BEIJIAN_ITEM = "LOOK_UP_BEIJIAN_ITEM";
	String LOOK_UP_PROJECT = "LOOKUP_PROJECT";//项目
	String LOOK_UP_ASSETS = "LOOK_UP_ASSETS";//查找资产
	String LOOK_UP_PURVEY = "LOOK_UP_PURVEY";
	String LOOK_UP_ADDRESS = "LOOK_UP_ADDRESS";
	String LOOK_UP_PROJECT2 = "LOOKUP_PROJECT2";//项目2
	String LOOK_UP_PROJECT3 = "LOOK_UP_PROJECT3";//项目3,返回项目号
	String LOOK_UP_ASSETS_OBJECT = "LOOK_UP_ASSETS_OBJECT";//资产业务查找地点
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
	String LOOK_UP_ITEM_BF = "LOOK_UP_ITEM_BF";
	String LOOK_UP_ITEM_BF2 = "LOOK_UP_ITEM_BF2";
	String FTMCK_OBJECT_NO = "FTMBK_OBJECT_NO";//非条码设备出库选择仓库
	String FTMCK_ITEM = "FTMCK_ITEM";       //非条码设备出库选择设备
	String SELECT_BARCODE = "SELECT_BARCODE";
	String LOOK_UP_VENDORS = "LOOK_UP_VENDORS"; //供应商
	String LOOK_UP_ASSETS_LOCATION = "LOOK_UP_ASSETS_LOCATION";    //MIS地点
	String LOOK_UP_ASSETS_ADDRESS = "LOOK_UP_ASSETS_ADDRESS";//地点
	String LOOK_UP_EXACT_EQUIP = "LOOK_UP_EXACT_EQUIP"; // 正式的设备（相对临时）
	String LOOK_UP_INSTRUMENT = "LOOK_UP_INSTRUMENT";  //仪器仪表
	String LOOK_UP_USER1 = "LOOK_UP_USER1";//找人
	String LOOK_UP_INSTR_ITEM = "LOOK_UP_INSTR_ITEM";//仪器仪表找类别
	String LOOK_UP_RETURN = "LOOK_UP_RETURN"; //仪器仪表找归还人
	String LOOK_UP_CITYINSTR = "LOOK_UP_CITYINSTR";   //查找地市仪器仪表人员
	String LOOK_UP_VENRETURN = "LOOK_UP_VENRETURN"; //送修返还
	String LOOK_UP_RESPUSER = "LOOK_UP_RESPUSER";//责任人
	String LOOK_UP_ASSETS_SYSITEM = "LOOK_UP_ASSETS_SYSITEM";//管理资产设备名称、规格
	String LOOK_UP_MIS_USER = "LOOK_UP_MIS_USER";//MIS责任人
	String LOOK_UP_MIS_DEPT = "LOOK_UP_MIS_DEPT";//MIS责任部门
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
	
	//================================================以下是村通后添加的============================================
	String LOOK_UP_SYS_ITEM_NAME = "LOOK_UP_SYS_ITEM_NAME"; //查找ETS_SYSTEM_ITEM表中的ITEM_NAME字段(村通设备名称)
	String LOOK_UP_SYS_ITEM_SPEC = "LOOK_UP_SYS_ITEM_SPEC"; //查找ETS_SYSTEM_ITEM表中的ITEM_SPEC字段(村通设备型号)
	String LOOK_UP_ETS_FA_CT_ASSETS_DESCRIPTION = "LOOK_UP_ETS_FA_CT_ASSETS_DESCRIPTION"; //查找ETS_FA_CT_ASSETS表中的ASSETS_DESCRIPTION字段(村通资产名称)
	String LOOK_UP_ETS_FA_CT_ASSETS_MODEL_NUMBER = "LOOK_UP_ETS_FA_CT_ASSETS_MODEL_NUMBER"; //查找ETS_FA_CT_ASSETS表中的MODEL_NUMBER字段(村通资产型号)
}
