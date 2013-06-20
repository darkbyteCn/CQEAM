package com.sino.ams.constant;

/**
 * User: zhoujs
 * Date: 2007-10-9
 * Time: 21:00:18
 * Function:定义各流程名称
 */
public interface AmsFlowConstant {

	//=================工单流程名称定义============================

	String BTS_NEW = "基站工单任务审批流程(新建)";         //workorderType=10  & category="10"
	String NET_NEW = "网优工单任务审批流程(新建)";         //workorderType=10  & category="15"
	String CHANG_NEW = "交换工单任务审批流程(新建)";       //workorderType=10  & category="20"
	String ELECTRI_NEW = "电力工单任务审批流程(新建)";     //workorderType=10  & category="60"
	String TRANSFER_NEW = "传输工单任务审批流程(新建)";    //workorderType=10  & category="40"
	String DATA_NEW = "数据工单任务审批流程(新建)";        //workorderType=10  & category="50"
	String MONITOR_NEW = "监控工单任务审批流程(新建)";     //workorderType=10  & category="30"

	String BTS_EXT = "基站工单任务审批流程(扩容)";          //workorderType=11 & category="10"
	String NET_EXT = "网优工单任务审批流程(扩容)";          //workorderType=11 & category="15"
	String CHANG_EXT = "交换工单任务审批流程(扩容)";        //workorderType=11 & category="20"
	String ELECTRI_EXT = "电力工单任务审批流程(扩容)";      //workorderType=11 & category="60"
	String TRANSFER_EXT = "传输工单任务审批流程(扩容)";     //workorderType=11 & category="40"
	String DATA_EXT = "数据工单任务审批流程(扩容)";         //workorderType=11 & category="50"
	String MONITOR_EXT = "监控工单任务审批流程(扩容)";      //workorderType=11 & category="30"


	String BTS_SUB = "基站工单任务审批流程(减容)";           //workorderType="15" & category="10"
	String NET_SUB = "网优工单任务审批流程(减容)";           //workorderType="15" & category="15"
	String CHANG_SUB = "交换工单任务审批流程(减容)";         //workorderType="15" & category="20"
	String ELECTRI_SUB = "电力工单任务审批流程(减容)";       //workorderType="15" & category="60"
	String TRANSFER_SUB = "传输工单任务审批流程(减容)";      //workorderType="15" & category="40"
	String DATA_SUB = "数据工单任务审批流程(减容)";          //workorderType="15" & category="50"
	String MONITOR_SUB = "监控工单任务审批流程(减容)";       //workorderType="15" & category="30"


	String BTS_TRANS = "基站工单任务审批流程(搬迁)";         //workorderType="14" & category="10"
	String NET_TRANS = "网优工单任务审批流程(搬迁)";         //workorderType="14" & category="15"
	String CHANG_TRANS = "交换工单任务审批流程(搬迁)";       //workorderType="14" & category="20"
	String ELECTRI_TRANS = "电力工单任务审批流程(搬迁)";     //workorderType="14" & category="60"
	String TRANSFER_TRANS = "传输工单任务审批流程(搬迁)";    //workorderType="14" & category="40"
	String DATA_TRANS = "数据工单任务审批流程(搬迁)";        //workorderType="14" & category="50"
	String MONITOR_TRANS = "监控工单任务审批流程(搬迁)";     //workorderType="14" & category="30"


	String BTS_CHECK = "基站工单任务审批流程(巡检)";         //workorderType=12 & category="10"
	String NET_CHECK = "网优工单任务审批流程(巡检)";         //workorderType=12 & category="15"
	String CHANG_CHECK = "交换工单任务审批流程(巡检)";       //workorderType=12 & category="20"
	String ELECTRI_CHECK = "电力工单任务审批流程(巡检)";     //workorderType=12 & category="60"
	String TRANSFER_CHECK = "传输工单任务审批流程(巡检)";    //workorderType=12 & category="40"
	String DATA_CHECK = "数据工单任务审批流程(巡检)";        //workorderType=12 & category="50"
	String MONITOR_CHECK = "监控工单任务审批流程(巡检)";     //workorderType=12 & category="30"


	String BTS_FIX = "基站工单任务审批流程(维修)";           //workorderType="13" & category="10"
	String NET_FIX = "网优工单任务审批流程(维修)";           //workorderType="13" & category="15"
	String CHANG_FIX = "交换工单任务审批流程(维修)";         //workorderType="13" & category="20"
	String ELECTRI_FIX = "电力工单任务审批流程(维修)";       //workorderType="13" & category="60"
	String TRANSFER_FIX = "传输工单任务审批流程(维修)";      //workorderType="13" & category="40"
	String DATA_FIX = "数据工单任务审批流程(维修)";          //workorderType="13" & category="50"
	String MONITOR_FIX = "监控工单任务审批流程(维修)";       //workorderType="13" & category="30"


	String BTS_REP = "基站工单任务审批流程(替换)";           //workorderType="16" & category="10"
	String NET_REP = "网优工单任务审批流程(替换)";           //workorderType="16" & category="15"
	String CHANG_REP = "交换工单任务审批流程(替换)";         //workorderType="16" & category="20"
	String ELECTRI_REP = "电力工单任务审批流程(替换)";       //workorderType="16" & category="60"
	String TRANSFER_REP = "传输工单任务审批流程(替换)";      //workorderType="16" & category="40"
	String DATA_REP = "数据工单任务审批流程(替换)";          //workorderType="16" & category="50"
	String MONITOR_REP = "监控工单任务审批流程(替换)";       //workorderType="16" & category="30"
	
	String BTS_HDV = "基站工单任务审批流程(交接)";           //workorderType="18" & category="10"
	String NET_HDV = "网优工单任务审批流程(交接)";           //workorderType="18" & category="15"
	String CHANG_HDV = "交换工单任务审批流程(交接)";         //workorderType="18" & category="20"
	String ELECTRI_HDV = "电力工单任务审批流程(交接)";       //workorderType="18" & category="60"
	String TRANSFER_HDV = "传输工单任务审批流程(交接)";      //workorderType="18" & category="40"
	String DATA_HDV = "数据工单任务审批流程(交接)";          //workorderType="18" & category="50"
	String MONITOR_HDV = "监控工单任务审批流程(交接)";       //workorderType="18" & category="30"

	String WORKORDER_HAND_OVER = "交接工单任务审批流程(搬迁)";     //workorderType="17" category(专业)任意
}
