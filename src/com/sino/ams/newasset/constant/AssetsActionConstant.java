package com.sino.ams.newasset.constant;

import com.sino.ams.constant.AMSActionConstant;
import com.sino.base.constant.web.WebActionConstant;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public interface AssetsActionConstant extends WebActionConstant,
		AMSActionConstant {
	String ASSIGN_ACTION = "ASSIGN_ACTION"; //分配操作
	String CONFIRM_ACTION = "CONFIRM_ACTION"; //确认操作
	String ARCHIVE_ACTION = "ARCHIVE_ACTION"; //归档操作
	String SAVE_MTL_PRIVI = "SAVE_MTL_PRIVI"; //保存资产专业权限(用于专业管理员)
	String DEL_MTL_PRIVI = "DEL_MTL_PRIVI"; ; //删除资产专业权限(用于专业管理员)
	String EPT_DTL_ACTION = "EPT_DTL_ACTION";//导出详细信息
	String GET_TARGET_OU = "GET_TARGET_OU";//获取资产调拨中的接收公司
    String IMP_LOCATION_CODE_ACTION = "IMP_LOCATION_CODE_ACTION"; //导入盘点地点编号信息
    String BACK_ACTION = "BACK_ACTION"; //盘点工单退回操作
    String PRINT_BARCODE_ACTION = "PRINT_BARCODE_ACTION"; //打印
}
