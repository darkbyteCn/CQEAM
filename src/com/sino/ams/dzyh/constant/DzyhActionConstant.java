package com.sino.ams.dzyh.constant;

import com.sino.ams.newasset.constant.AssetsActionConstant;

public interface DzyhActionConstant extends AssetsActionConstant {

	String CHAXUN_ACTION="CHAXUN_ACTION";	//低值易耗查询页面
	String WEIHU_ACTION="WEIHU_ACTION";	//低值易耗维护页面

	/*====================以下为唐明胜加入=======================*/
	String CAL_MUL_TASK = "CAL_MUL_TASK";
	String SUB_MUL_TASK = "SUB_MUL_TASK";

	String DISTRI_ORDER = "DISTRI_ORDER";
	String OPEN_DEPT = "OPEN_DEPT";//打开部门选择页面
	String SELECT_DEPT = "SELECT_DEPT";//点击确定操作，此时需要根据选择的部门自动将部门下的地点全部填写到工单上
	String LOG_HISTORY = "LOG_HISTORY";//察看某标签号的变动历史
}
