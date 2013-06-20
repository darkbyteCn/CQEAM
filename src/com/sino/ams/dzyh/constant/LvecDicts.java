package com.sino.ams.dzyh.constant;

import com.sino.ams.newasset.constant.AssetsDictConstant;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface LvecDicts extends AssetsDictConstant{
	String TASK_STATUS = "TASK_STATUS"; //任务状态
	String CHECK_TYPE = "CHECK_TYPE";//任务类别
	String CHKORDER_STATUS = "CHKORDER_STATUS";

	/*==========================任务状态========================*/
	String TSK_STATUS1_NEW = "ADD_NEW";
	String TSK_STATUS1_SAVE_TEMP = "SAVE_TEMP";
	String TSK_STATUS1_CANCELED = "CANCELED";
	String TSK_STATUS1_OPENING = "OPENING";
	String TSK_STATUS1_TIMOUT = "TIMED_OUT";

	String TSK_STATUS2_NEW = "新增";
	String TSK_STATUS2_SAVE_TEMP = "暂存";
	String TSK_STATUS2_CANCELED = "已取消";
	String TSK_STATUS2_OPENING = "开放中";
	String TSK_STATUS2_CLOSED = "已过期";

	String CHECK_BATCH = "CHK-BAT";//

	/*==========================任务类别===============================*/
	String CHK_TYPE1_DZYH = "DZYH";//DZYH
	String CHK_TYPE1_YQYB = "YQYB";//仪表盘点
	String[] TASK_TYPE1_LIST = {CHK_TYPE1_DZYH, CHK_TYPE1_YQYB};

	String CHK_TYPE2_DZYH = "低耗盘点";//低值易耗盘点
	String CHK_TYPE2_YQYB = "仪表盘点";//仪器仪表盘点
	String[] TASK_TYPE2_LIST = {CHK_TYPE2_DZYH, CHK_TYPE2_YQYB};

	/*==========================工单类别===============================*/
	String ORD_TYPE1_DZYH = "DZYH-CHK";//低耗盘点
	String ORD_TYPE1_YQYB = "YQYB-CHK";//仪表盘点
	String ORD_TYPE1_DHBS = "DHBS-CHK";//低耗补扫
	String[] ORD_TYPE1_LIST = {ORD_TYPE1_DZYH, ORD_TYPE1_YQYB, ORD_TYPE1_DHBS};

	String ORD_TYPE2_DZYH = "低耗盘点";//低值易耗盘点
	String ORD_TYPE2_YQYB = "仪表盘点";//仪器仪表盘点
	String ORD_TYPE2_DHBS = "低耗补扫";//低耗补扫
	String[] ORD_TYPE2_LIST = {ORD_TYPE2_DZYH, ORD_TYPE2_YQYB, ORD_TYPE2_DHBS};

	/*==========================工单状态========================*/

	String ORDER_STS1_NEW = "ADD_NEW";
	String ORDER_STS1_SAVE_TEMP = "SAVE_TEMP";
	String ORDER_STS1_CANCELED = "CANCELED";
	String ORDER_STS1_DISTRIBUTED = "DISTRIBUTED";
	String ORDER_STS1_DOWNLOADED = "DOWNLOADED";
	String ORDER_STS1_UPLOADED = "UPLOADED";
	String ORDER_STS1_ARCHIEVED = "ARCHIEVED";

	String ORDER_STS2_NEW = "新增";
	String ORDER_STS2_SAVE_TEMP = "暂存";
	String ORDER_STS2_CANCELED = "已取消";
	String ORDER_STS２_DISTRIBUTED = "已下发";
	String ORDER_STS２_DOWNLOADED = "已下载";
	String ORDER_STS2_UPLOADED = "已上载";
	String ORDER_STS２_ARCHIEVED = "已归档";

	/*==========================抽查工单状态========================*/

	String SCAN_REMARK = "PDA创建该条码";

	String LOCATION_CATEGORY_DZYH = "99";
	String LOCATION_CATEGORY_YQYB = "98";

	String CHECK_TOOLS = "CHECK_TOOLS";
	String CHECK_TOOLS1_PDA = "0";//PDA确认
	String CHECK_TOOLS1_WEB = "1";//WEB确认

	String CHECK_TOOLS2_PDA = "PDA确认";//PDA确认
	String CHECK_TOOLS2_WEB = "WEB确认";//WEB确认
	String ASORD_LINE_ARCH_REMARK = "低耗补扫工单自动归档";
	String ASORD_ITMCODE_ARCH_REMARK = "低耗补扫工单创建设备分类";
	String FIN_PROP_DZYH = "DZYH";//低值易耗，
	String ORDER_CATEGORY_4 = "4";//插入设备历史表的单据类型
	String INSTRU_ARCH_REMARK = "仪器仪表盘点工单WEB确认方式创建";
	String LOG_CHG_TYPE = "TYPE_CHECK";

	String YB_BR_STATUS1_ADD = "YB_BR_STATUS_ADD";//新增
	String YB_BR_STATUS1_WAPPROVE = "YB_BR_STATUS_WAPPRO";//待批
	String YB_BR_STATUS1_CANCEL = "YB_BR_STATUS_CANCEL";//已取消
	String YB_BR_STATUS1_APPROVE = "YB_BR_STATUS_APPROVE";//已批
	String YB_BR_STATUS1_NAPPRO = "YB_BR_STATUS_NAPPRO";//审批未通过
	String YB_BR_STATUS1_BORROW = "YB_BR_STATUS_BORROW";//借出
	String YB_BR_STATUS1_CLOSE = "YB_BR_STATUS_CLOSE";//关闭
	String YB_BR_STATUS1_RETURN = "YB_BR_STATUS_RETURN";//返还

	String YB_BR_STATUS2_ADD = "新增";//新增
	String YB_BR_STATUS2_WAPPROVE = "待批";//待批
	String YB_BR_STATUS2_CANCEL = "已取消";//已取消
	String YB_BR_STATUS2_APPROVE = "已批";//已批
	String YB_BR_STATUS2_NAPPRO = "审批未通过";//审批未通过
	String YB_BR_STATUS2_BORROW = "借出";//借出
	String YB_BR_STATUS2_CLOSE = "关闭";//关闭
	String YB_BR_STATUS2_RETURN = "返还";//返还

	String CATEGORY_YQYB = "YQYB";//仪器仪表
	String CATEGORY_DZYH = "DZYH";//低值易耗

	String ROLE_INSTR_APP_NM = "仪表审批人";
}
