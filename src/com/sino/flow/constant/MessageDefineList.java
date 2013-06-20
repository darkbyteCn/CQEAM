package com.sino.flow.constant;

public interface MessageDefineList {
    String SYSTEM_ERROR = "系统发生错误，请与管理员联系！";
    String LOGIN_ERROR = "用户名或密码错误！";
    String NO_PRIVI_ERROR = "非法访问！";
    String APPLY_SAVE_SUCCESS = "付款申请保存成功！";
    String APPLY_SAVE_FAIL = "付款申请保存失败！";
    String APPLY_SUBMIT_APPROVE_SUCCESS = "提交审批成功！";
    String APPLY_SUBMIT_APPROVE_FAIL = "提交审批失败！";
    String NO_QUERY_TYPE = "查询类型不能为空！";
    String QUERY_ERROR = "数据库查询错误！";
    String GET_CONN_ERROR = "获取数据库连接错误！";
    String GET_PAGE_DATA_ERROR="获取页面数据失败！";
    String PARSER_ERROR = "解析数据出错！";
    String PRODUCE_WEB_DATA_ERROR = "产生页面数据出错！";
    String FILE_UPLOAD_ERROR = "文件上载错误！";
    String FILE_UPLOAD_SUCCESS = "文件上载成功！";
    String FILE_SIZE_OVER_ERROR = "上载文件大小超过限定值！";
    String DATA_NOT_EXISTS = "你请求的数据不存在！";
    String SESSION_TIME_OUT = "你还未登录或会话已过期，请重新登录！";
    String REJECT_APPLY_SUCCESS = "退回申请成功！";
    String REJECT_APPLY_FAIL = "退回申请失败！";
    String PASS_APPLY_SUCCESS = "通过申请成功！";
    String PASS_APPLY_FAIL = "通过申请失败！";
    String CANCEL_ORDER_SUCCESS = "取消申请成功！";
    String CANCEL_ORDER_FAIL = "取消申请失败！";
    String CONTAINER_ERROR="查询的字段在数据库中不存在";
}
