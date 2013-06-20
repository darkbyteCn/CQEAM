package com.sino.sinoflow.todo.constant;

/**
 * 
 * @系统名称: 
 * @功能描述: OA_TODO
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Nov 30, 2011
 */
public interface HNOAConstant {
	
	
	//写入OA的一些参数
	String OA_TODO_SYSID = "1009";			//系统ID，公文为1，BPM为2（统一待办分配），合同管理为1004，资产系统为1009
	String OA_TODO_SOURCE_ID = "PR";	//应用来源,省内应用为：PR
	//待办处理的优先级别
	String OA_TODO_PRI_LEVEL_ONE = "1"; 		//普通
	String OA_TODO_PRI_LEVEL_TWO = "2";		//紧急
	String OA_TODO_PRI_LEVEL_THREE = "3";	//特急
	String OA_TODO_PRI_LEVEL_FOUR = "4";
	String OA_TODO_PRI_DEFAULT = OA_TODO_PRI_LEVEL_ONE;
	
	String OA_TODO_DOC_TYPE = "资产管理系统";
	
	//新增、已办、注销
	String OA_TODO_TYPE_OPEN = "1";	//OPEN
	String OA_TODO_TYPE_CLOSE = "2";  	//CLOSE
	String OA_TODO_TYPE_CANCEL = "6"; //CANCEL
	
	String M_CONTENT_TYPE = "text/html; charset=GBK";
	
	//日志状态(开始/结束)
	String TODO_LOG_TYPE_START = "START";
	String TODO_LOG_TYPE_END = "END";
	
	
	String RESULT_CODE_SUCCESS = "1";
	String RESULT_CODE_FAILURE = "-1";
	
	String WS_CON_TIMEOUT = "30000";
	String WS_REV_TIMEOUT = "30000";
	
	long OA_TODO_THREAD_SLEEP_TIME = 60000;
}
