package com.sino.ams.sampling.constant;

import com.sino.ams.newasset.constant.AssetsDictConstant;


/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface SamplingDicts extends AssetsDictConstant{
	String SAMPLING_TYPE = "SAMPLING_TYPE";//抽查类别
	String TASK_STATUS = "TASK_STATUS";//任务状态

	/*==========================任务状态========================*/
	String TSK_STATUS1_NEW = "ADD_NEW";
	String TSK_STATUS1_SAVE_TEMP = "SAVE_TEMP";
	String TSK_STATUS1_CANCELED = "CANCELED";
	String TSK_STATUS1_OPENING = "OPENING";
	String TSK_STATUS1_CLOSED = "CLOSED";

	String TSK_STATUS2_NEW = "新增";
	String TSK_STATUS2_SAVE_TEMP = "暂存";
	String TSK_STATUS2_CANCELED = "已取消";
	String TSK_STATUS2_OPENING = "开放中";
	String TSK_STATUS2_CLOSED = "已关闭";

	/*==========================任务状态========================*/

	/*==========================任务类别========================*/
	String TSK_TYPE1_NEW = "ASSETS-COUNT";
	String TSK_TYPE1_SAVE_TEMP = "ASSETS-LOCATION";
	String TSK_TYPE1_CANCELED = "BTS-COUNT";

	String TSK_TYPE2_NEW = "按资产数量";
	String TSK_TYPE2_SAVE_TEMP = "按资产地点";
	String TSK_TYPE2_CANCELED = "按基站数量";

	/*==========================任务类别========================*/

	String TASK_NO_MARK = "SAMP-TSK";//抽查任务编号
	String BATH_NO_MARK = "SAMP-BAT";//抽查批编号
	String ORDR_NO_MARK = "ASS-SAM";//抽查工单编号
	String ASS_SAM_PDA = "抽查";//PDA端显示的名称
	String SAM_ORDER_STATUS = "SAM_ORDER_STATUS";//抽查工单状态

	/*==========================抽查工单状态========================*/

	String ORDER_STS1_NEW = "ADD_NEW";
	String ORDER_STS1_SAVE_TEMP = "SAVE_TEMP";
	String ORDER_STS1_CANCELED = "CANCELED";
	String ORDER_STS1_DISTRIBUTED = "DISTRIBUTED";
	String ORDER_STS1_DOWNLOADED = "DOWNLOADED";
	String ORDER_STS1_ARCHIEVED = "ARCHIEVED";

	String ORDER_STS2_NEW = "新增";
	String ORDER_STS2_SAVE_TEMP = "暂存";
	String ORDER_STS2_CANCELED = "已取消";
	String ORDER_STS２_DISTRIBUTED = "已下发";
	String ORDER_STS２_DOWNLOADED = "已下载";
	String ORDER_STS２_ARCHIEVED = "已归档";

	/*==========================抽查工单状态========================*/

	String SCAN_REMARK = "PDA创建该条码";

	String BATCH_REMARK_PROV = "省公司创建工单批";
	String BATCH_REMARK_SELF = "本公司创建工单批";
}
