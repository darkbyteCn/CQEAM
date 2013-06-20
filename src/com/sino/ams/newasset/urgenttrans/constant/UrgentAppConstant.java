package com.sino.ams.newasset.urgenttrans.constant;

/**
 * 
 * @系统名称: 资产管理系统
 * @功能描述: 紧急调拨
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Aug 1, 2011
 */
public interface UrgentAppConstant {
	String PROC_NAME = "urgentTransApp";
	String TRANS_TYPE = "ASS-TRS";
	String TRANS_TYPE_NAME = "网络调拨单"; //资产紧急调拨单
	
	String ATT3_FILL_DATA = "FILL_DATA";	 //申请 
	String ATT3_ASS_APPROVE = "ASS_APPROVE"; //部门资产管理员审批
	String ATT3_ASS_APPROVED = "ASS_APPROVED";	 //部门经理审核 
	String ATT3_SUPER_ASS_APPROVE = "SUPER_ASS_APPROVE";//上级主管部门资产管理员审核
	
	String FLOW_CODE_END = "END";
	
	//PDA部分
	String PDA_CREATE_TYPE_NAME = "紧急调拨"; //资产紧急调拨单
	int DOWNLOAD_TYPE_OUT = 1;
	int DOWNLOAD_TYPE_IN = 2;
	String OUT_ORDER_NAME = "紧急调出";
	String IN_ORDER_NAME = "紧急调入";
	
	String STATUS_TRANS_OUT = "TRANS_OUT"; //调出
	String STATUS_TRANS_IN = "TRANS_IN";   //调入
	String STATUS_ARCHIVED = "ARCHIEVED";   //归档
	
	/**
	 *  历史变更表中值
	 */
	String ORDER_DTL_URL = "";
	String ORDER_CATEGORY = "3";
}
