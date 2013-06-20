package com.sino.ams.newasset.lose.constant;

/**
 * 
 * @系统名称: 资产管理系统
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Jul 15, 2011
 */
public interface LoseAppConstant {
	String PROC_CODE = "lossrpt";
	String PROC_NAME = "资产挂失流程";
	
	String TRANS_TYPE = "ASS-LOSSRPT";
	String TRANS_TYPE_NAME = "资产挂失单";
	
	String ATT3_FILL_DATA = "FILL_DATA";	 //申请
	String ATT3_APPROVING = "APPROVING";	 //部门经理审核
	String ATT3_ASS_APPROVE = "ASS_APPROVE"; //实物部门审批
	
	/**
	 *  历史变更表中值
	 */
	String ORDER_DTL_URL = "";
	String ORDER_CATEGORY = "3";
	 
}
