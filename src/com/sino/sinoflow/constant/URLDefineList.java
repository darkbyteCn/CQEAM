package com.sino.sinoflow.constant;

import com.sino.base.constant.web.WebActionConstant;

/**
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 */
public interface URLDefineList {

    //==================================框架类页面===========================================//
    String LOGIN_PAGE = "/login.jsp"; //登陆页面
    String COMMON_MSG_PAGE = "/publicMessage.jsp";//消息显示页面，其他页面采用include的形式包含该页面。
    String FIRST_LOGIN_PAGE = "/firstLogin.jsp"; //非MIS用户首次登录修改初始密码
    String PORTAL_SERVLET = "/servlet/com.sino.ams.log.servlet.PortalLoginServlet"; //portal验证Servlet
    String LOGIN_FRM_SERVLET = "/servlet/com.sino.ams.log.servlet.LoginFrmServlet";
    String HOME_PAGE = "/mainPage.jsp"; //主框架页面
    String HOME_PAGE_TRAY = "/mainPage.jsp?home=1"; //主框架页面--主页为待办区域
    String TIME_OUT_PAGE = "/timeOutPage.jsp"; //会话过期页面
    String NO_PRIVI_PAGE = "/noPriviPage.jsp"; //无权限页面
    String ERROR_PAGE = "/errorPages/errorPage.jsp"; //错误页面
    String TOP_PAGE = "/topPage.jsp"; //顶部菜单页面
    String MENU_PAGE = "/resourceMenu.jsp"; //左侧菜单树页面
    String SUCCESS_PAGE = "/public/successPage.jsp"; //操作成功返回页面
    String TEMP_PAGE = "/temPage.jsp"; //临时页面，用于定义开发人员的工作任务
    String WINDOW_CLOSE_PAGE = "/windowClose.jsp"; //转向关闭页面，可传返回值 retValue
    String WINDOW_SAVE_PROBLEM_PAGE = "/flow/saveError.jsp"; //保存错误显示
    String INDEX_PAGE = "/home.jsp";//首页信息
    String GET_MENU_PAGE = "/getMenuPage.jsp";//订制栏目
    //==================================框架类页面===========================================//

    //==================================栏目维护类页面===========================================//
    String RES_FRM_PAGE = "/system/resource/resourceFrm.jsp";//移过来的
    String RES_QRY_PAGE = "/system/resource/resourceQuery.jsp";//移过来的
    String RES_TREE_PAGE = "/system/resource/resourceTree.jsp";//移过来的
    String RES_DTL_PAGE = "/system/resource/resourceDetail.jsp";//移过来的
    String RES_QRY_SERVLET = "/servlet/com.sino.sinoflow.framework.resource.servlet.SfResDefineServlet?act=" + WebActionConstant.QUERY_ACTION;
    String RES_NEW_SERVLET = "/servlet/com.sino.sinoflow.framework.resource.servlet.SfResDefineServlet?act=" + WebActionConstant.NEW_ACTION;
    String RES_DTL_SERVLET = "/servlet/com.sino.sinoflow.framework.resource.servlet.SfResDefineServlet?act=" + WebActionConstant.DETAIL_ACTION;
    String RES_PRIVI_FRM = "/system/resource/resPriviFrm.jsp"; //移过来的
    String RES_PRIVI_TREE = "/system/resource/resPriviTree.jsp"; //移过来的
    String RES_PRIVI_QUERY = "/system/resource/resPriviQuery.jsp";//移过来的
    String PRIVI_QUERY_SERVLET = "/servlet/com.sino.sinoflow.framework.resource.servlet.SfResPrivsServlet?act=" + WebActionConstant.QUERY_ACTION;
    String WORK_PLAN_QUERY = "/plan/workPlanQuery.jsp";   //工作计划维护（查询）页面
    String WORK_PLAN_DETAIL = "/plan/workPlanDetail.jsp"; //工作计划详细页面
    String WORK_PLAN_SEARCH = "/plan/workPlanSearch.jsp";//工作计划查询页面
    String WORK_PLAN_PIGEONHOLE = "/plan/workPlanPigeonhole.jsp";//工作计划归档页面
    String WORK_PLAN_NEW = "/plan/workPlanNew.jsp";//新增工作计划页面
    String WORK_PLAN_S_DETAI = "/plan/workPlanSDetail.jsp";//工作计划详细页面（查询）
    String WORK_PLAN_QUERY_SERVLET = "/servlet/com.sino.ams.plan.servlet.AmsWorkPlanServlet?act=" + WebActionConstant.DETAIL_ACTION;

    //==================================栏目维护类页面===========================================//

    //==============================================用户信息维护类页面============================
    String AMSSINO_DEPT_QUERY_PAGE = "/system/group/amsSinoDeptQuery.jsp";
    String DEPT_GROUP_QUERY_PAGE = "/system/group/deptGroupQuery.jsp";
    String GROUP_QUERY_PAGE = "/system/group/groupQuery.jsp";
    String GROUP_SERVLET_PAGE = "/servlet/com.sino.sinoflow.user.servlet.SfGroupServlet?act=" + WebActionConstant.QUERY_ACTION;
    String GROUP_DETAIL_PAGE = "/system/group/groupDetail.jsp";
    String ROLE_QUERY_PAGE = "/system/role/roleQuery.jsp";
    String ROLE_DETAIL_PAGE = "/system/role/roleDetail.jsp";
    String ROLE_QUERY_SERVLET = "/servlet/com.sino.sinoflow.user.servlet.SfRoleServlet?act=" + WebActionConstant.QUERY_ACTION; //修改
    String USER_LIST_PAGE = "/system/user/userList.jsp";
    String APP_USER_LIST_PAGE = "/system/user/appUserList.jsp";
    String USER_INFO_DETAIL_PAGE = "/system/user/userInfoDetail.jsp";
    String USER_INFO_LIST_PAGE = "/system/user/userInfoList.jsp";
    String USER_LIST_SERVLET = "/servlet/com.sino.sinoflow.user.servlet.SfUserServlet?act=" + WebActionConstant.QUERY_ACTION;
    String USER_DETAIL_PAGE = "/system/user/userDetail.jsp";
    String APP_USER_DETAIL_PAGE = "/system/user/appUserDetail.jsp";
    String CHOOSE_USER_PAGE = "/workorder/util/chooseUser.jsp";
    String CHANGE_USER_PAGE ="/system/user/changeUserPassword.jsp";  //修改用户密码页面
    String CHANGE_USERPWD_SERVLET = "/servlet/com.sino.sinoflow.user.servlet.ChangeUserPasswordServlet";  //修改用户密码

    //==============================================用户信息维护类页面============================
    String EMPLOYEE_LIST_PAGE = "/system/employee/employeeList.jsp";  //领导信息录入页面，首页的查询页面
    String EMPLOYEE_DETAIL_PAGE = "/system/employee/employeeDetail.jsp";
    String EMPLOYEE_DETAIL_SERVLET = "/servlet/com.sino.sinoflow.employee.servlet.XfEmployeesServlet?act=&modelFlag='Y'";
    
	//=================================流程定义==============================================
    String PROJECT_QUERY_PAGE = "/system/project/projectQuery.jsp";//工程查询页面
    String PROJECT_SERVLET = "/servlet/com.sino.sinoflow.user.servlet/SfProjectServlet?act=";//工程Servlet
    
    String ADMIN_AUTHORITY_SERVLET = "/servlet/com.sino.sinoflow.servlet.SfAdminAuthorityServlet?act=";//工程管理员Servlet
    String ADMIN_AUTHORITY_QUERY_PAGE = "/system/adminAuthority/adminAuthorityQuery.jsp";//工程管理查询页面
    String ADMIN_AUTHORITY_DETAIL_PAGE = "/system/adminAuthority/adminAuthorityDetail.jsp";//工程管理员定义页面
	
	String APPLICATION_DETAIL_PAGE = "/flowTask/applicationDetail.jsp"; //应用定义页面
	String APPLICATION_QUERY_PAGE = "/flowTask/applicationQuery.jsp"; //应用查询页面
	String APPLICATION_SERVLET = "/servlet/com.sino.sinoflow.servlet.SfApplicationServlet?act=";//应用Servlet
	
	String API_QUERY_PAGE = "/flowTask/apiQuery.jsp";//接口程序查询 
	String API_DETAIL_PAGE = "/flowTask/apiDetail.jsp";//创建接口页面
	String API_SERVLET = "/servlet/com.sino.sinoflow.servlet.SfApiServlet?act=";//接口Servlet
	
	String VALIDATION_QUERY_PAGE = "/flowTask/validationQuery.jsp"; //合法性检查 查询页
	String VALIDATION_DETAIL_PAGE = "/flowTask/validationDetail.jsp"; //创建合法性检查页面
	String VALIDATION_SERVLET = "/servlet/com.sino.sinoflow.servlet.SfValidationServlet?act=";//合法性Servlet";
	
	String WORK_SCHEDULE_QUERY_PAGE = "/flowTask/workScheduleQuery.jsp";//工作时间表查询页面
	String WORK_SCHEDULE_DETAIL_PAGE = "/flowTask/workScheduleDetail.jsp";//工作时间表定义页面
	String WORK_SCHEDULE_SERLVET = "/servlet/com.sino.sinoflow.servlet.SfWorkScheduleServlet?act=";//工作时间定义Servlet
	
	String WORK_HOUR_QUARY = "/flowTask/workHourQuery.jsp";//工作时间查询页面
	String WORK_HOUR_DETAIL = "/flowTask/workHourDetail.jsp";//工作时间定义页面
	String WORK_HOUR_SERVLET = "/servlet/com.sino.sinoflow.servlet.SfWorkHourServlet?act=";//工作时间servlet
	
	String HOLIDAYS_DETAIL = "/flowTask/holidaysDetail.jsp";//节假日定义页面
	String HOLIDAYS_QUARY = "/flowTask/holidaysQuery.jsp";//节假日查询页面
	String HOLIDAYS_SERVLET = "/servlet/com.sino.sinoflow.servlet.SfHolidaysServlet?act=";//节假日servlet
	
	String AUTO_VALUE_QUERY_PAGE = "/flowTask/autoValueQuery.jsp";//自动赋值查询页面
	String AUTO_VALUE_DETAIL_PAGE = "/flowTask/autoValueDetail.jsp";//自动赋值定义页面
	String AUTO_VALUE_SERLVET = "/servlet/com.sino.sinoflow.servlet.SfAutoValueServlet?act=";//自动赋值Servlet
	
    String DELEGATION_QUERY_PAGE = "/flowTask/delegationQuery.jsp";//委托定义查询页面
    String DELEGATION_QUERY_PAGE2 = "/flowTask/delegationQuery2.jsp";//委托定义查询页面
    String DELEGATION_DETAIL_PAGE ="/flowTask/delegationDetail.jsp";//委托定义页面
    String DELEGATION_DETAIL_PAGE2 ="/flowTask/delegationDetail2.jsp";//委托定义页面
    String DELEGATION_SERLVET = "/servlet/com.sino.sinoflow.servlet.SfDelegationServlet?act=";//委托定义Servlet
    String DELEGATION_SERLVET2 = "/servlet/com.sino.sinoflow.servlet.SfDelegationServlet2?act=";//委托定义Servlet
	//=================================流程定义==============================================
	String TRASK_QUERY_PAGE = "/traskmoting/traskQueryPage.jsp"; //跟踪页面
	String TRASK_DETAIL_PAGE = "/traskmoting/traskDetailPage.jsp";//详细页面
	String TRASK_MOTING_QUERY_PAGE ="/traskmoting/traskMiotingQueryPage.jsp";
	String TRASK_USER_MONITING_PAGE ="/traskmoting/traskUserMonitoring.jsp"; //根据用户监控
	String TRASK_ROLE_MONITING_PAGE = "/traskmoting/traskRoleMonitoring.jsp"; //根据角色
	String TRASK_NAME_MONITING_PAGE = "/traskmoting/traskNameMonitoring.jsp"; //根据程序名字
	String TRASK_CHU_BAN_PAGE = null;//根据催办
	String TRASK_MOINTING_DETAIL_PAGE ="/traskmoting/traskMointingDetailPage.jsp";//监控详细页面
	String TRASK_OT_USER_MONITING_PAGE = "/traskmoting/traskOtUserMonitoring.jsp";
	String TRASK_OT_ROLE_MONITING_PAGE = "/traskmoting/traskOtRoleMonitoring.jsp";
	String TRASK_OT_NAME_MONITING_PAGE = "/traskmoting/traskOtNameMonitoring.jsp";
    String TRASK_ENOTE_MONITING_PAGE = "/traskmoting/traskEnoteMonitoring.jsp";

    String NOTIFICATION_TRAY_PAGE = "/flow/NotificationTray.jsp";//催办箱列表页面
	String SFENOTE_DETAIL_PAGE = "/flow/NotificationDetail.jsp";//催办箱详细显示页面
	String CHECK_BOX_TRAY_PAGE = "/flow/checkBoxTray.jsp";//查办箱列表页面
    String MONITOR_TRAY_PAGE = "/flow/monitorTray.jsp";

    String SFCOPY_DETAIL_PAGE = "/flow/copyDetail.jsp"; //抄送详细显示页面
}

