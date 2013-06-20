package com.sino.flow.constant;

public interface ReqAttributeList {
    String ERROR_MSG = "ERROR_MSG";//用于显示错误信息
    String SHOW_MSG = "SHOW_MSG";//用于显示提示信息
    String APPLY_DETAIL_DATA = "APPLY_DETAIL_DATA";//申请单信息
    String TASK_DATA = "TASK_DATA";//节点任务信息
    String TASK_OPTIONS = "TASK_OPTIONS";//下一节点选择下拉框
    String APP_USER_OPTIONS = "APP_USER_OPTIONS";//下一节点办理人选择下拉框
    String APP_DETAIL_HEADER="APP_DETAIL_HEADER";//查询详细信息的申请单信息
    String APP_DETAIL_LINES="APP_DETAIL_LINES";//查询详细信息的审批信息
    String NEXT_TASK_ID = "NEXT_TASK_ID";//下一节点
    String APPROVE_FLOW_DATA = "APPLY_FLOW_DATA";//当前节点流转信息
    String APPROVE_DATAS = "APPROVE_DATAS";//所有审批意见
    String DEPT_POSITION_DATA = "DEPT_POSITION_DATA";//用户所属部门及职位.
    String NEED_POP_WINDOW = "NEED_POP_WINDOW";
    String TASK_PROP = "TASK_PROP";
    String FLOW_PARM_DATA="FLOW_PARM_DATA";
    String TO_PROCESS_TASKS = "TO_PROCESS_TASKS";//待处理任务
    String APPROVE_CONTENT_DATA="APPROVE_CONTENT_DATA";//审批意见内容
    String ALERT_AREA_TOTAL="ALERT_AREA_TOTAL";//警醒区数据
    String OUT_BOX_TOTAL="OUT_BOX_TOTAL";//发件箱总数
    String PERSONAL_TOTAL="PERSONAL_TOTAL";//个人申请总数
    String CANCEL_TOTAL="CANCEL_TOTAL";//个人可以取消申请的总数
    String ALL_TOTAL="ALL_TOTAL";//所有申请总数;
    String INBOX_DATA="INBOX_DATA";//收件箱数据
    String INBOX_COUNT="INBOX_COUNT";//收件箱数据
    String OUTBOX_DATA="OUTBOX_DATA";//发件箱数据
    String HANDLE_DATA="HANDLE_DATA";//在办箱数据
    String PERSONAL_DATA="PERSONAL_DATA";//个人申请数据
    String ALL_DATA="ALL_DATA"; //所有申请数据
    String CANCEL_DATA="CANCEL_DATA";//取消申请数据
}
