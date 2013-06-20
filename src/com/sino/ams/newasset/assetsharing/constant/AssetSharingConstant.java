package com.sino.ams.newasset.assetsharing.constant;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.base.constant.web.WebActionConstant;

/**
 * 资源共享流程功能专用 常量
 * @author xiaohua
 *
 */
public  interface AssetSharingConstant extends WebActionConstant,
AMSActionConstant{
	String ASSET_SHARE_EDIT="/newasset/share/shareAssetsEdit.jsp";//资源共享 申请页面
	String ASSET_SHARE_DETAIL="/newasset/share/shareAssetsDetail.jsp";//资源共享 申请页面
	String ASSET_SHARE_PAGEDQUERY="/newasset/share/shareAssetsPageQuery.jsp";//资源共享 查询
	String ASSET_SHARE_PAGEDQUERY_DETAIL="/newasset/share/shareAssetsPageQueryDetail.jsp";//资源共享 查询
	String ASSET_SHARE_CODE="ASS-SHARE";//资源共享 CODE
	String PRINT_QUERY_ACTION="PRINT_QUERY_ACTION";//
	String DTO_SET_HEADER="DTO_SET_HEADER";//
	String ASSET_SHARE_CODE_DESC="资产共享";//资源共享
	String ASSET_SHARE_DETAIL_ACTION="DETAIL_ACTION";//资源共享
	String ASSET_SHARE_EDITABLE="EDITABLE";//流程是否能编辑
	String ASSET_SHARE_TASK_NAME0="填制资产共享申请单";//
	String ASSET_SHARE_TASK_NAME1="部门经理审核";//
	String ASSET_SHARE_TABLE_NAME="AMS_ASSETS_TRANS_HEADER";//
	String ASSET_SHARE_SERVLET="/servlet/com.sino.ams.newasset.assetsharing.servlet.AssetSharingServlet";//资源共享
	String Trans_No="完成时自动生成";
	String SHARE_SF_APP_NAME="SHARE_ASSETS";//资产共享 应用名
	String OPERATE_SUCCESS="OPERATE_SUCCESS";//"操作成功";
	String OPERATE_FAILURE="OPERATE_FAILURE";//"操作失败";
	String OPERATE_SUCCESS_VALUE="操作成功";//"操作成功";
	String OPERATE_FAILURE_VALUE="操作失败";//"操作失败";
	String SHARE_TITLE1="共享单据查询";//"操作成功";
	String SHARE_TITLE2="共享单据打印查询";//"操作失败";
	 
}
