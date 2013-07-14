package com.sino.ams.yearchecktaskmanager.util;


public  interface AssetsCheckTaskConstant {

	//任务名称
	//第一，二级
	String ORDER_NAME ="年度盘点任务";
	//第三级任务
	String ORDER_NAME_NON_ADDRESS ="非实地资产年度盘点任务";
	String ORDER_NAME_ADDRESS_WIRELESS ="实地无线资产年度盘点任务";
	String ORDER_NAME_ADDRESS_NON_WIRELESS ="实地非无线资产年度盘点任务";
	
	//资产类型
	String ASSETS_TYPE_NON_ADDRESS ="ASSETS_TYPE_NON_ADDRESS"; //非实地
	String ASSETS_TYPE_ADDRESS_WIRELESS ="ASSETS_TYPE_ADDRESS_WIRELESS"; //实地无线
	String ASSETS_TYPE_ADDRESS_NON_WIRELESS ="ASSETS_TYPE_ADDRESS_NON_WIRELESS"; //实地非无线
	
	//非实地资产下发方式
	String NON_ADDRESS_METHOD_FOR_CITY_MANAGER = "NON_ADDRESS_METHOD_FOR_CITY_MANAGER"; //下给地市管理员
	String NON_ADDRESS_METHOD_FOR_DEPT_MANAGER = "NON_ADDRESS_METHOD_FOR_DEPT_MANAGER"; //下给地市部门管理员
	String NON_ADDRESS_METHOD_FOR_SOME_PERSON ="NON_ADDRESS_METHOD_FOR_SOME_PERSON";//下给资产责任人或者特定人员
	
	//非实地资产种类
	String NON_ADDRESS_CATEGORY_SOFTWIRE = "NON_ADDRESS_CATEGORY_SOFTWARE";//软件类
	String NON_ADDRESS_CATEGORY_CLIENT = "NON_ADDRESS_CATEGORY_CLIENT";//客户端类
	String NON_ADDRESS_CATEGORY_PIPELINE ="NON_ADDRESS_CATEGORY_PIPELINE";//光缆、杆路及管道
	
	// 任务工单类型
	 String ORDER_TYPE_ASS_CHK_TASK = "ASS-CHK-TASK"; // 盘点任务工单类型
	 //String ORDER_TYPE_NON_ADDRESS = "NON-ADDRESS"; // 非实地盘点任务工单
	 //非实地资产
	 String ORDER_TYPE_NON_ADDRESS_SOFTWARE= "NON-ADDRESS-SOFTWARE"; // 非实地[软件类]
	 String ORDER_TYPE_NON_ADDRESS_CLIENT = "NON-ADDRESS-CLIENT"; // 非实地[客户端类]
	 String ORDER_TYPE_NON_ADDRESS_PIPELINE = "NON-ADDRESS-PIPELINE"; // 非实地[光缆、杆路及管道类]
	 //实地资产
	 String ORDER_TYPE_ADDRESS_WIRELESS = "ADDRESS-WIRELESS"; // 实地无线类
	 String ORDER_TYPE_ADDRESS_NON_WIRELESS = "ADDRESS-NON-WIRELESS";// 实地非无线类

	// 已创建，已下发，执行中，完成，撤销
	 String DO_CREATE = "DO_CREATE";
	 String DO_SEND = "DO_SEND";
	 String DO_COMPLETE = "DO_COMPLETE";
	 String DO_CANCLE = "DO_CANCLE";
	 
	 String YEAR_TASK_NAME = "年度盘点任务";
	 String LOOK_UP_CITY_MANAGER = "LOOK_UP_CITY_MANAGER"; //查找地市公司资产管理员
	 String ORDER_NUMBER = "下发后自动生成";
	 
	 //任务接收人角色
	 String ORDER_RECIVE_ROLE_TO_CITY = "公司资产管理员"; //省到地市
	 String ORDER_RECIVE_ROLE_TO_DEPT_FOR_NON_ADDRESS = "部门资产管理员"; //地市到部门,非实地资产
	 String ORDER_RECIVE_ROLE_TO_DEPT_FOR_ADDRESS_WIRELESS = "实地无线资产盘点责任人"; //实地无线
	 String ORDER_RECIVE_ROLE_TO_DEPT_FOR_ADDRESS_NON_WIRELESS = "部门资产管理员"; //实地非无线
	 String ORDER_RECIVE_ROLE_TO_COMMON_USER = "系统用户"; //第四级任务
	 
	 //资产大类
	 String ASSETS_BIG_CLASS_MIS = "MIS"; //上市
	 String ASSETS_BIG_CLASS_TD = "TD"; //TD
	 String ASSETS_BIG_CLASS_TF = "TF"; //通服
	 String ASSETS_BIG_CLASS_TT = "TT"; //铁通
	 String ASSETS_BIG_CLASS_ALL = "ALL"; //全部
	 
//jeffery
	 
	 String CHECKED="CHECKED";//盘到
	 
	 String CHECKED_NAME="盘到";
	 
	 String CHECKED_LOSS="CHECKED_LOSS";//盘亏
	 
	 String CHECKED_LOSS_NAME="盘亏";
	 
	 String CHECKED_NOT_MACTH="CHECKED_NOT_MACTH";//信息不符
	 
	 String CHECKED_NOT_MACTH_NAME="信息不符";
	 
	 String DO_RETURN = "DO_RETURN";//已退回
	 

}
