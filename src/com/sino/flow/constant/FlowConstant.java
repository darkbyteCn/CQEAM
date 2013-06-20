package com.sino.flow.constant;

/**
 * <p>Title: SinoCPS</p>
 * <p>Description: 河南移动集中核算系统：可能用到的字典定义</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */
public interface FlowConstant {
	String TASK_BEGIN_MARK = "0"; //流程中第一个节点的起始节点标示
	String PROC_STATUS_NORMAL = "0"; //流程当前状态：0表示正常
	String PROC_STATUS_COMPLETE = "2"; //流程当前状态：2表示结束
	String PROC_STATUS_PAUSE = "3"; //流程当前状态：3表示暂停
	String TASK_FINISH_MARK = "9999"; //流程结束
	String TASK_STATUS_FINISHED = "1"; //当前节点的完成状态：1表示完成
	String TASK_STATUS_NOT_FINISHED = "0"; //当前节点的完成状态 ：0表示未完成
	String TASK_PROP_START = "1"; //节点属性：1表示起始节点
	String TASK_PROP_MEDIATE = "2"; //节点属性：2表示中间节点
	String TASK_PROP_END = "3"; //节点属性：3表示终止节点
	String FLOW_CODE_NEXT = "9"; //同意，流程转向下一级
	String FLOW_CODE_PREV = "10"; //不同意，流程转向上一级
	String ACTIVITY_NEW = "0"; //流程创建
	String ACTIVITY_CANCEL = "1"; //流程撤消
	String ACTIVITY_AGAIN = "2"; //流程活动，再次提交申请
    final String CANCEL_APPLY = "CANCEL_APPLY";
	String APPLY_STATUS_PASS = "PASS"; //调用flow方法传递的参数，
	String APPLY_STATUS_NOT_PASS = "NOTPASS"; //调用flow方法传递的参数，
	String APPLY_STATUS_CANCEL = "CANCEL"; //调用flow方法传递的参数，
	// String XX="22";

	//**流程类型，根据各个应用来加**//
	String PROC_TYPE_CEN = "集中核算流程";

	//***流向类型用于分流，默认为1*//
	String FLOW_TYPE_DEFALUT = "1"; //

	//**节点类型，1按职位找人，2 按userId找人***//
	String TASK_TYPE_POSITION = "1";
	String TASK_TYPE_USER = "2";

	//短信发送方式
	String SEND_TYPE_ONCE = "1"; //实时发送
	String SEND_TYPE_GATHER = "2"; //汇总发送

	//**在哪个系统中有任务***//
	String MSG_AT_SYSTEM = "集中核算系统中，有新的待办单据："; //短信提醒时，带的头信息，因为每个系统都不一样，因此写在这里。
	String FLOW_FILE_PATH = "flow";

	String APPROVE_CONTENT_NEW = "填写申请";
	String APPROVE_CONTENT_AGREE = "同意";
	String APPROVE_CONTENT_REJECT = "不同意";
	String APPROVE_CONTENT_END = "同意，审批结束";
	String APPROVE_CONTENT_SUBMIT = "提交审批";
	String APPROVE_CONTENT_RESUBMIT = "再次提交审批";
}
