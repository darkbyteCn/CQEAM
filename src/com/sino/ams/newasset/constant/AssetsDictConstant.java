package com.sino.ams.newasset.constant;

import com.sino.ams.constant.DictConstant;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface AssetsDictConstant extends DictConstant {
	String LOCATION_TMP_YES = "1"; //临时地点
	String LOCATION_TMP_NO = "0"; //非临时地点
	String STATUS_YES = "Y"; //肯定状态
	String STATUS_NO = "N"; //否定状态

	//附件类型
	String FILE_TYPE_SCANNED = "SCANNED"; //扫描件
	String FILE_TYPE_OTHERS = "OTHERS"; //一般附件
	String DOC_TYPE = "UPLOAD"; //判断是上传还是查看
	String DOC_TYPE2 = "LOOK"; //判断是上传还是查看

	String TRANS_INN_DEPT = "INN_DEPT"; //部门内调拨
	String TRANS_BTW_DEPT = "BTW_DEPT"; //部门间调拨
	String TRANS_BTW_COMP = "BTW_COMP"; //公司间调拨
	String TRANS_INN_DEPT_RFU = "INN_DEPT_RFU"; //紧急调拨(补汇总)

	String END_INN_DEPT = "END_INN_DEPT"; //部门内调拨结束节点值
	String END_BTW_DEPT = "END_BTW_DEPT"; //部门间调拨结束节点值
	String END_BTW_COMP = "END_BTW_COMP"; //公司间调拨结束节点值
	String END_INN_DEPT_RFU = "END_INN_DEPT_RFU"; //紧急调拨(补汇总)结束节点值
	String OTHER_FLOW = "OTHER"; //走其他路线，不需要结束流程


	String[] TRANS_OPT_VALUES = {TRANS_INN_DEPT, TRANS_BTW_DEPT, TRANS_BTW_COMP, TRANS_INN_DEPT_RFU, OTHER_FLOW};
	String[] TRANS_OPT_LABELS = {"部门内调拨单", "部门间调拨单", "公司间调拨单", "紧急调拨单(补汇总)", "其它"};
	String[] TRANS_OPT_LABELS_TD = {"TD部门内调拨单", "TD部门间调拨单", "TD公司间调拨单"};

	String SPEC_ASS_RED = "ASS-RED"; //特殊资产调拨单
	String SPEC_ASS_DIS = "ASS-DIS"; //特殊资产报废单
	String SPEC_ASS_FREE = "ASS-FREE"; //特殊资产闲置单

	String[]SP_ASSETS_OPT_VALUES = {SPEC_ASS_RED,SPEC_ASS_DIS,SPEC_ASS_FREE};
	String[]SP_ASSETS_OPT_LABELS = {"调拨单","报废单","闲置单"};

	String SPEC_DG_ASSETS = "DG_ASSETS";
	String SPEC_TD_ASSETS = "TD_ASSETS";
	String SPEC_RENT_ASSETS = "RENT_ASSETS";
	String SPEC_DH_ASSETS = "DH_ASSETS";

	String[]ASSETS_TYPE_OPT_VALUES ={SPEC_DG_ASSETS,SPEC_TD_ASSETS,SPEC_RENT_ASSETS,SPEC_DH_ASSETS};
	String[]ASSETS_TYPE_OPT_LABELS = {"村通、代管资产","TD资产","租赁资产","低值易耗资产"};

	String DEFAULT_YES = "Y"; //默认状态--Y
	String DEFAULT_NO = "N"; //默认状态--N

	String ORDER_STS_RECEIVED = "RECEIVED"; //已接收：资产管理员已经接收调拨资产
	String ORDER_STS_ASSIGNED = "ASSIGNED"; //已分配：资产管理员已经分配资产到责任人
	String ORDER_STS_CONFIRMD = "CONFIRMD"; //已确认：责任人已经确认收到资产

	String ASSETS_STATUS = "ASSETS_STATUS"; //资产状态
	String ASSETS_STATUS_DISCARDED = "DISCARDED"; //资产状态--已报废
    String ASSETS_STAY_DISCARDED = "TO_DISCARD";//资产状态--待报废
    String ASSETS_STATUS_CLEARED = "DISCARDED_YCZ"; //资产状态--已报废已处置
	String ASSETS_STATUS_FREED = "FREE"; //资产状态--已闲置
	String ASSETS_STATUS_SHARE = "SHARE";     //资产状态--已共享
	String ASSETS_STATUS_SUB = "IMPAIRMENT"; //资产状态--已减值
	String ASSETS_STATUS_SELL = "SELL";	//资产状态销售
	String ASSETS_STATUS_RENT = "RENT";	//资产状态--出租
	String ASSETS_STATUS_DONATION = "DONATE";	//资产状态--捐赠
	String ASSETS_STATUS_PRE_DISCARDE = "PRE_DISCARDE";	//资产状态--预报废
	String ASSETS_STATUS_EXCHANGE = "EXCHANGE";	//资产状态--置换
	String ASSETS_STATUS_SEND_REPAIR = "SEND_REPAIR";	//资产状态--送修

	String ASSETS_STS_NORMAL = "0"; //正常
	String ASSETS_STS_DISCARD = "1"; //报废
	String ASSETS_STS_CLEAR = "2"; //处置
	String ADOPT_SCAN_RESULT = "0"; //以扫描结果为准
	String ADOPT_SYST_STATUS = "1"; //以系统状态为准
    String ITEM_STATUS = "ITEM_STATUS"; //设备状态
    String SHARE_STATUS = "SHARE_STATUS"; //设备状态
    String DIS_TYPE = "DIS_TYPE"; //报废类型
    String DEAL_TYPE = "DEAL_TYPE";//处置类型

    //start===============管理指标类报表类型=========================//
	String MANAGE_INDICATORS = "MANAGE_INDICATORS"; //管理指标类报表类型
	//end===============管理指标类报表类型=========================//

    //start===============资产单据类型补充=========================//
	String ASS_CHK = "ASS-CHK"; //盘点工单
	String ASS_RCV = "ASS-RCV"; //资产接收单
	String ASS_DIS = "ASS-DIS"; //资产baofei单
	String ASS_FREE = "ASS-FREE"; //资产闲置单
	String ASS_LOSSRPT = "ASS-LOSSRPT"; //资产挂失单 
	
	String ASS_SUB = "ASS-SUB"; //资产减值单
	String ASS_SHARE = "ASS-SHARE";	//资产共享单
	String RNT_CHK = "RNT-CHK"; //租赁盘点
	String INS_CHK = "INS-CHK"; //仪表盘点(含仪器)
	String ASS_SELL = "ASS-SELL"; //资产销售单
	String ASS_RENT = "ASS-RENT"; //资产出租单
	String ASS_DONA = "ASS-DONA"; //资产捐赠单
	String ASS_RENTINTO ="ASS-RENTINTO";//经营租入单
	String ASS_DEVALUE = "ASS-DEVALUE"; //资产减值单
	String ASS_WASTEFORECAST ="ASS-WASTEFORECAST";//资产预报废单
	String ASS_REPAIR ="ASS-REPAIR";//资产预报废单
	//end===============资产单据类型补充=========================//

	//start===============盘点工单状态=========================//
	String CHKORDER_STATUS = "CHKORDER_STATUS"; //盘点工单状态
	String CHK_STATUS_SAVE_TEMP = "SAVE_TEMP"; //暂存
	String CHK_STATUS_CANCELED = "CANCELED"; //已撤销
	String CHK_STATUS_IN_PROCESS = "IN_PROCESS"; //审批中
	String CHK_STATUS_REJECTED = "REJECTED"; //已退回
	String CHK_STATUS_APPROVED = "APPROVED"; //已审批
	String CHK_STATUS_DISTRUIBUTED = "DISTRIBUTED"; //已下发
	String CHK_STATUS_DOWNLOADED = "DOWNLOADED"; //已下载
	String CHK_STATUS_UPLOADED = "UPLOADED"; //已上载
	String CHK_STATUS_ARCHIEVED = "ARCHIEVED"; //已归档

	String CHKD_STATUS_SAVE_TEMP = "暂存"; //暂存
	String CHKD_STATUS_CANCELED = "已撤销"; //已撤销
	String CHKD_STATUS_IN_PROCESS = "审批中"; //审批中
	String CHKD_STATUS_REJECTED = "已退回"; //已退回
	String CHKD_STATUS_APPROVED = "已审批"; //已审批
	String CHKD_STATUS_DISTRUIBUTED = "已下发"; //已下发
	String CHKD_STATUS_DOWNLOADED = "已下载"; //已下载
	String CHKD_STATUS_UPLOADED = "已上载"; //已上载
	String CHKD_STATUS_ACHIEVED = "已归档"; //已归档


	String CHKORDER_STATUS_NEW = "新增";
	String CHKORDER_STATUS_DISTRUIBUTED = "已下发";
	String CHKORDER_STATUS_DOWNLOADED = "已下载";
	String CHKORDER_STATUS_UPLOADED = "已上传";
	String CHKORDER_STATUS_ACHIEVED = "已归档";
	String CHKORDER_STATUS_CANCELE = "已撤销";
	//end===============盘点工单状态=========================//


	String TRANSFER_INN_DEPT = "INN_DEPT"; //部门内调拨
	String TRANSFER_BTW_DEPT = "BTW_DEPT"; //部门间调拨
	String TRANSFER_BTW_COMP = "BTW_COMP"; //OU间调拨

	String PROCEDURE_NAME_TRANSFER = "资产调拨流程";
	String PROCEDURE_TRANS_INN_DEPT = "资产调拨流程(部门内)";
	String PROCEDURE_TRANS_BTW_DEPT = "资产调拨流程(部门间)";
	
	String PROCEDURE_TRANS_INN_DEPT_RFU = "资产紧急调拨流程(补汇总)";
	
    String PROCEDURE_ITEM_TRANS_INN_DEPT = "实物调拨流程(部门内)";
	String PROCEDURE_ITEM_TRANS_BTW_DEPT = "实物调拨流程(部门间)";
	String PROCEDURE_ITEM_TRANS_BTW_COMP = "实物调拨流程(公司间)";
	String PROCEDURE_TRANS_BTW_COMP = "资产调拨流程(公司间)";
	String PROCEDURE_NAME_TRANSFER_TD = "TD资产调拨流程";
	String PROCEDURE_TRANS_BTW_COMP_TD = "TD资产调拨流程(公司间)";
	String PROCEDURE_NAME_DISCARD = "资产报废流程";
	String PROCEDURE_NAME_DISCARD_TD = "TD资产报废流程";
	String PROCEDURE_NAME_CLEAR = "资产处置流程";
	String PROCEDURE_NAME_CHECK = "资产盘点流程";
	String PROCEDURE_NAME_FREE = "资产闲置流程";
	String PROCEDURE_NAME_SUB = "资产减值流程";
	String PROCEDURE_NAME_RCV = "资产调拨接收审批流程";
	String PROCEDURE_NAME_SHARE = "资产共享管理流程";
	String PROCEDURE_NAME_SELL = "资产销售流程";
	String PROCEDURE_NAME_RENT = "资产出租流程";
	String PROCEDURE_NAME_DONA = "资产捐赠流程";
	String PROCEDURE_NAME_REPAIRRETURN = "资产送修返还流程";
	String PROCEDURE_NAME_REPAIR = "资产送修流程";
	String PROCEDURE_NAME_WASTEFORECAST = "资产预报废流程";
	String PROCEDURE_NAME_REPEAL="资产预报废撤销流程";
	String PROCEDURE_NAME_REPLACEMENT = "资产置换流程";
	String PROCEDURE_NAME_BORROW = "资产借用流程";
	String PROCEDURE_NAME_RETURN = "资产送还流程";
	String PROCEDURE_NAME_SPECIAL_TRANSFER ="特殊资产审批流程";

	String ASSIGNED_BOTH_NOT = "1"; //责任人和责任部门均未分配
	String ASSIGNED_PERSON_NOT = "2"; //责任人未分配
	String ASSIGNED_DEPT_NOT = "3"; //责任部门未分配
	String ASSIGNED_PERSON_YES = "4"; //责任人已分配
	String ASSIGNED_DEPT_YES = "5"; //责任部门已分配
	String ASSIGNED_BOTH_YES = "6"; //责任人和责任部门均已分配

	String ASSIGNED_BOTH_NOT_L = "均未分配";
	String ASSIGNED_PERSON_NOT_L = "责任人未分配";
	String ASSIGNED_DEPT_NOT_L = "责任部门未分配";
	String ASSIGNED_PERSON_YES_L = "责任人已分配";
	String ASSIGNED_DEPT_YES_L = "责任部门已分配";
	String ASSIGNED_BOTH_YES_L = "均已分配";

	String[] ASSIGNED_VALUE_LIMIT = {ASSIGNED_BOTH_NOT, ASSIGNED_PERSON_NOT,
									ASSIGNED_DEPT_NOT, ASSIGNED_PERSON_YES,
									ASSIGNED_DEPT_YES, ASSIGNED_BOTH_YES};
	String[] ASSIGNED_LABEL_LIMIT = {ASSIGNED_BOTH_NOT_L, ASSIGNED_PERSON_NOT_L,
									ASSIGNED_DEPT_NOT_L,
									ASSIGNED_PERSON_YES_L, ASSIGNED_DEPT_YES_L,
									ASSIGNED_BOTH_YES_L};

	String ARCHIVE_AS_SCAN = "0"; //以扫描状态为准
	String ARCHIVE_AS_CURR = "1"; //以目前状态为准

	String ARCHIVE_SCAN_REMARK = "以扫描状态为准";
	String ARCHIVE_CURR_REMARK = "以目前状态为准";
	String TRANS_ASSETS = "TRANS_ASSETS"; //调拨资产，用于资产分配
	String OWNER_ASSERS = "OWNER_ASSERS"; //原有资产，用于资产分配

	String TRANS_ASSETS_L = "调拨资产"; //调拨资产，用于资产分配
	String OWNER_ASSERS_L = "原有资产"; //原有资产，用于资产分配

	String DEPRE_ACCOUNT_SET_NAME = "DEPRE_ACCOUNT_SET_NAME"; //折旧账户

	String AMS_FA_CATEGORY_V = "AMS_FA_CATEGORY_V"; //资产类别视图(需要作为动态视图创建)
	String AMS_ACCOUNT_V = "AMS_ACCOUNT_V"; //折旧账户视图(需要作为动态视图创建)
	String ASS_CHK_TASK = "CHK-TASK"; //盘点任务
	String FIELD_FOR_QUERY = "FOR_QUERY"; //查询用字段
	String FIELD_FOR_DISPL = "FOR_DISPL"; //显示用字段

	String ASS_CHK_PAD = "盘点"; //资产盘点工单在PDA端的描述(仅仅是为了向前兼容)
	String INS_CHK_PAD = "仪表盘点"; //仪表盘点工单在PDA端的描述
	String RNT_CHK_PAD = "租赁盘点"; //租赁资产盘点工单在PDA端的描述
	String ASS_CHK_SRV = "资产盘点"; //资产盘点工单在服务端端的描述


	String SACN_YES = "5"; //扫描到盘点资产
	String SACN_NO = "6"; //未扫描到盘点资产
	String FA_CONTENT_CODE = "FA_CONTENT_CODE"; //固定资产目录代码
	String FA_CONTENT_NET = "NET-ASSETS"; //网络类资产
	String FA_CONTENT_MGR = "MGR-ASSETS"; //管理类资产
	String COMM_FILE = "COMM_FILE"; //普通附件
	String SCAN_FILE = "COMM_FILE"; //扫描附件
	String SPLIT_FLOW = "SPLIT_FLOW"; //用于分流(综合部还是网络部)前的节点的隐藏属性
    String SPLIT_FLOW2 = "SPLIT_FLOW2";//用于部门间调拨判断是否财务部提交的申请
    String SPLIT_FLOW3 = "SPLIT_FLOW3"; //用于部门间和公司間调拨判断是否直接提交給調入部門經理的申请
    String FINANCE_DEPT = "财务部";
    String SYN_STATUS_YES = "1"; //已同步
	String SYN_STATUS_NO = "0"; //未同步

	String PROVINCE_CODE_NM = DictConstant.PROVINCE_CODE_NM; //内蒙移动公司省代码
	String PROVINCE_CODE_SX = DictConstant.PROVINCE_CODE_JIN; //山西移动公司省代码
	String PROVINCE_CODE_SN = DictConstant.PROVINCE_CODE_SHAN; //陕西移动公司省代码
	String TRANS_EDIT_YES = "EDIT_YES"; //资产调拨流程中的可编辑节点
	String GROUP_PROP_COMM = "0"; //普通组别
	String GROUP_PROP_SPEC = "1"; //专业组别
	String MGR_DPT = "MGR-DPT";
	String RCV_DPT = "RCV-DPT";
	String ACHIEVE_ROLE="%归档人%";
	String CANCAL_REASON = "暂存后撤销单据";

	String ANALYZE_CATEGORY_1 = "1";//按应用领域分析
	String ANALYZE_CATEGORY_2 = "2";//按资产类别分析
	String ANALYZE_CATEGORY_3 = "3";//按节(类项目节)分析
	String ANALYZE_CATEGORY_4 = "4";//盘点结果分析

	String CHECK_RESULT_1 = "1";//账实一致
	String CHECK_RESULT_2 = "2";//条码一致：存在其他不一致情况
	String CHECK_RESULT_3 = "3";//有卡无物
	String CHECK_RESULT_4 = "4";//有物无卡
	String CHECK_RESULT_5 = "5";//对于已盘点地点的资产统计有卡无物
    String CHECK_RESULT_6 = "6";//无需PDA扫描清单

    String NEW_TAG_NODE = "NEW_TAG_NODE";//生成新标签节点
	String TRANS_OUT_REMARK = "公司间资产调拨(调出)报废";
	String TRANS_IN_REMARK = "公司间资产调拨(调入)创建";
	String EDIT_ACCOUNT = "EDIT_ACCOUNT";//用于公司间调拨中可以编辑选择接收费用账户的设置

    String ORDER_TYPE_ASSETS = "ORDER_TYPE_ASSETS"; //资产单据类型，用于个人工单查询
    String[] TRANS_TYPE_VALUES = {CREATE, SAVE_TEMP, CANCELED, COMPLETED, REJECTED, IN_PROCESS, APPROVED, RECEIVED, ASSIGNED, CONFIRMD, DISTRIBUTED, DOWNLOADED, UPLOADED, ARCHIEVED};
	String[] TRANS_TYPE_LABELS = {"新增", "暂存", "撤消", "完成", "退回", "审批中", "已审批", "已接收", "已分配", "已确认", "已下发", "已下载", "已上载", "已归档"};

	String EMERGENT_LEVEL = "EMERGENT_LEVEL"; //业务紧急程度
	
	String PROCEDURE_NAME_DEVALUE = "资产减值流程";
}
