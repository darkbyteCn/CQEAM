package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 应用信息 SfApplication</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfApplicationDTO extends CheckBoxDTO{

	private int appId = 0;
    private String isFlowProcess = "";
    private int projectId = 0;
    private String projectName = "";
	private String procedureName = "";
    private String groupName = "";
    private String roleName = "";
    private String categoryName = "";
	private String appName = "";
    private String url="";
    private String appDataClass = "";
	private String appDataSqlmodel = "";
	private int windowType = 0;
	private int finishMessage = 0;
	private int confirmFinish = 0;
	private int allowOperation = 0;
	private String sortColumn1 = "";
	private String sortColumn2 = "";
	private String sortColumn3 = "";
	private String appColumn1 = "";
	private String appColumn2 = "";
	private String appColumn3 = "";
	private String appColumn4 = "";
	private String appColumn5 = "";
	private String appColumn6 = "";
	private String appColumn7 = "";
	private String appColumn8 = "";
	private String appColumn9 = "";
	private String linkProjectNameField = "";
	private String linkProjectDescField = "";
	private String linkProcedureNameField = "";
	private String linkProcedureDescField = "";
	private String linkTaskNameField = "";
	private String linkTaskDescField = "";
	private String linkTaksDurationField = "";
	private String linkGroupField = "";
	private String linkRoleFiled = "";
	private String linkUsersField = "";
	private String linkFromProjectField = "";
	private String linkFormProcedureField = "";
	private String linkFromUserField = "";
	private String linkFormDateField = "";
	private String linkFormTaskIdField = "";
	private String linkHandleGroupField = "";
	private String linkHandlerField = "";
	private String linkSignStatusField = "";
	private String linkSignDateField = "";
	private String linkTaskDueField = "";
	private String linkReviewGroupField = "";
	private String linkReviewRoleField = "";
	private String linkReviewUsersField = "";
	private String linkReviewStatusField = "";
	private String linkDeliveryPriorityField = "";
	private String linkUserMessageField = "";
	private String linkNextCyclePropField = "";
	private String viewInList = "";
    private String trayType = "";
    private String procUrl = "";

    public String getViewInList() {
		return viewInList;
	}

	public void setViewInList(String viewInList) {
		this.viewInList = viewInList;
	}

	public SfApplicationDTO() {
		super();
	}

	/**
	 * 功能：设置应用信息属性 应用 ID
	 * @param appId String
	 */
	public void setAppId(int appId){
		this.appId = appId;
	}

    /**
     * 功能：设置应用信息属性 流程应用, Y:是 N:否
     * @param isFlowProcess String
     */
    public void setIsFlowProcess(String isFlowProcess){
        this.isFlowProcess = isFlowProcess;
    }


    /**
	 * 功能：设置应用信息属性 工程名称
	 * @param projectName String
	 */
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}

	/**
	 * 功能：设置应用信息属性 过程名称
	 * @param procedureName String
	 */
	public void setProcedureName(String procedureName){
		this.procedureName = procedureName;
	}

    /**
     * 功能：设置应用信息属性 组别名称
     * @param groupName String
     */
    public void setGroupName(String groupName){
        this.groupName = groupName;
    }

    /**
	 * 功能：设置应用信息属性 显示应用分类名称
	 * @param categoryName String
	 */
	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	/**
	 * 功能：设置应用信息属性 显示应用名称
	 * @param appName String
	 */
	public void setAppName(String appName){
		this.appName = appName;
	}

    /**
     * 功能：设置应用信息属性 应用 URL
     * @param url String
     */
    public void setUrl(String url){
        this.url = url;
    }

    /**
	 * 功能：设置应用信息属性 应用数据接口类名
	 * @param appDataClass String
	 */
	public void setAppDataClass(String appDataClass){
		this.appDataClass = appDataClass;
	}

	/**
	 * 功能：设置应用信息属性 应用数据 SQLModel, 此 SQLModel 为空则表示应用需自己读取应用的数据
	 * @param appDataSqlmodel String
	 */
	public void setAppDataSqlmodel(String appDataSqlmodel){
		this.appDataSqlmodel = appDataSqlmodel;
	}

	/**
	 * 功能：设置应用信息属性 应用显示窗口类型, 0:右框架中 1:新窗口
	 * @param windowType String
	 */
	public void setWindowType(int windowType){
		this.windowType = windowType;
	}

	/**
	 * 功能：设置应用信息属性 完成时传送信息, 0:禁止 1:允许
	 * @param finishMessage String
	 */
	public void setFinishMessage(int finishMessage){
		this.finishMessage = finishMessage;
	}

	/**
	 * 功能：设置应用信息属性 确认信息框, 0:禁止 1:允许
	 * @param confirmFinish String
	 */
	public void setConfirmFinish(int confirmFinish){
		this.confirmFinish = confirmFinish;
	}

	/**
	 * 功能：设置应用信息属性 允许操作项, bit 0:取消, bit 1:特送, bit 2:退回, bit 3:查看流程
	 * @param allowOperation String
	 */
	public void setAllowOperation(int allowOperation){
		this.allowOperation = allowOperation;
	}

	/**
	 * 功能：设置应用信息属性 主分类
	 * @param sortColumn1 String
	 */
	public void setSortColumn1(String sortColumn1){
		this.sortColumn1 = sortColumn1;
	}

	/**
	 * 功能：设置应用信息属性 次分类
	 * @param sortColumn2 String
	 */
	public void setSortColumn2(String sortColumn2){
		this.sortColumn2 = sortColumn2;
	}

	/**
	 * 功能：设置应用信息属性 其它分类
	 * @param sortColumn3 String
	 */
	public void setSortColumn3(String sortColumn3){
		this.sortColumn3 = sortColumn3;
	}

	/**
	 * 功能：设置应用信息属性 关键字域名, 应用表单中使用于关键字的域名
	 * @param appColumn1 String
	 */
	public void setAppColumn1(String appColumn1){
		this.appColumn1 = appColumn1;
	}

	/**
	 * 功能：设置应用信息属性 主题域名, 应用表单中使用于主题的域名
	 * @param appColumn2 String
	 */
	public void setAppColumn2(String appColumn2){
		this.appColumn2 = appColumn2;
	}

	/**
	 * 功能：设置应用信息属性 其它域名, 应用表单中使用于其它的域名
	 * @param appColumn3 String
	 */
	public void setAppColumn3(String appColumn3){
		this.appColumn3 = appColumn3;
	}

	/**
	 * 功能：设置应用信息属性 域名 1, 保存的应用表单域名
	 * @param appColumn4 String
	 */
	public void setAppColumn4(String appColumn4){
		this.appColumn4 = appColumn4;
	}

	/**
	 * 功能：设置应用信息属性 域名 2, 保存的应用表单域名
	 * @param appColumn5 String
	 */
	public void setAppColumn5(String appColumn5){
		this.appColumn5 = appColumn5;
	}

	/**
	 * 功能：设置应用信息属性 域名 3, 保存的应用表单域名
	 * @param appColumn6 String
	 */
	public void setAppColumn6(String appColumn6){
		this.appColumn6 = appColumn6;
	}

	/**
	 * 功能：设置应用信息属性 域名 4, 保存的应用表单域名
	 * @param appColumn7 String
	 */
	public void setAppColumn7(String appColumn7){
		this.appColumn7 = appColumn7;
	}

	/**
	 * 功能：设置应用信息属性 域名 5, 保存的应用表单域名
	 * @param appColumn8 String
	 */
	public void setAppColumn8(String appColumn8){
		this.appColumn8 = appColumn8;
	}

	/**
	 * 功能：设置应用信息属性 域名 6, 保存的应用表单域名
	 * @param appColumn9 String
	 */
	public void setAppColumn9(String appColumn9){
		this.appColumn9 = appColumn9;
	}

	/**
	 * 功能：设置应用信息属性 工程名称放置域
	 * @param linkProjectNameField String
	 */
	public void setLinkProjectNameField(String linkProjectNameField){
		this.linkProjectNameField = linkProjectNameField;
	}

	/**
	 * 功能：设置应用信息属性 工程描述放置域
	 * @param linkProjectDescField String
	 */
	public void setLinkProjectDescField(String linkProjectDescField){
		this.linkProjectDescField = linkProjectDescField;
	}

	/**
	 * 功能：设置应用信息属性 过程名称放置域
	 * @param linkProcedureNameField String
	 */
	public void setLinkProcedureNameField(String linkProcedureNameField){
		this.linkProcedureNameField = linkProcedureNameField;
	}

	/**
	 * 功能：设置应用信息属性 过程描述放置域
	 * @param linkProcedureDescField String
	 */
	public void setLinkProcedureDescField(String linkProcedureDescField){
		this.linkProcedureDescField = linkProcedureDescField;
	}

	/**
	 * 功能：设置应用信息属性 当前任务名称放置域
	 * @param linkTaskNameField String
	 */
	public void setLinkTaskNameField(String linkTaskNameField){
		this.linkTaskNameField = linkTaskNameField;
	}

	/**
	 * 功能：设置应用信息属性 当前任务描述放置域
	 * @param linkTaskDescField String
	 */
	public void setLinkTaskDescField(String linkTaskDescField){
		this.linkTaskDescField = linkTaskDescField;
	}

	/**
	 * 功能：设置应用信息属性 当前任务预定时间放置域
	 * @param linkTaksDurationField String
	 */
	public void setLinkTaksDurationField(String linkTaksDurationField){
		this.linkTaksDurationField = linkTaksDurationField;
	}

	/**
	 * 功能：设置应用信息属性 当前任务指定组别放置域
	 * @param linkGroupField String
	 */
	public void setLinkGroupField(String linkGroupField){
		this.linkGroupField = linkGroupField;
	}

	/**
	 * 功能：设置应用信息属性 当前任务指定角色放置域
	 * @param linkRoleFiled String
	 */
	public void setLinkRoleFiled(String linkRoleFiled){
		this.linkRoleFiled = linkRoleFiled;
	}

	/**
	 * 功能：设置应用信息属性 当前角色所有用户放置域, 如有多个用户, 则以 ‘;” 分隔
	 * @param linkUsersField String
	 */
	public void setLinkUsersField(String linkUsersField){
		this.linkUsersField = linkUsersField;
	}

	/**
	 * 功能：设置应用信息属性 转发工程名称放置域
	 * @param linkFromProjectField String
	 */
	public void setLinkFromProjectField(String linkFromProjectField){
		this.linkFromProjectField = linkFromProjectField;
	}

	/**
	 * 功能：设置应用信息属性 转发过程名称放置域
	 * @param linkFormProcedureField String
	 */
	public void setLinkFormProcedureField(String linkFormProcedureField){
		this.linkFormProcedureField = linkFormProcedureField;
	}

	/**
	 * 功能：设置应用信息属性 转发人放置域
	 * @param linkFromUserField String
	 */
	public void setLinkFromUserField(String linkFromUserField){
		this.linkFromUserField = linkFromUserField;
	}

	/**
	 * 功能：设置应用信息属性 转发时间放置域
	 * @param linkFormDateField String
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLinkFormDateField(String linkFormDateField) throws CalendarException{
		this.linkFormDateField = linkFormDateField;
	}

	/**
	 * 功能：设置应用信息属性 转发任务 ID 放置域
	 * @param linkFormTaskIdField String
	 */
	public void setLinkFormTaskIdField(String linkFormTaskIdField){
		this.linkFormTaskIdField = linkFormTaskIdField;
	}

	/**
	 * 功能：设置应用信息属性 继承组别放置域
	 * @param linkHandleGroupField String
	 */
	public void setLinkHandleGroupField(String linkHandleGroupField){
		this.linkHandleGroupField = linkHandleGroupField;
	}

	/**
	 * 功能：设置应用信息属性 经办人放置域, 如有多个经办人, 则以 “;”分隔
	 * @param linkHandlerField String
	 */
	public void setLinkHandlerField(String linkHandlerField){
		this.linkHandlerField = linkHandlerField;
	}

	/**
	 * 功能：设置应用信息属性 是否签收放置域, Y, N
	 * @param linkSignStatusField String
	 */
	public void setLinkSignStatusField(String linkSignStatusField){
		this.linkSignStatusField = linkSignStatusField;
	}

	/**
	 * 功能：设置应用信息属性 签收时间放置域
	 * @param linkSignDateField String
	 */
	public void setLinkSignDateField(String linkSignDateField){
		this.linkSignDateField = linkSignDateField;
	}

	/**
	 * 功能：设置应用信息属性 应转发时间放置域
	 * @param linkTaskDueField String
	 */
	public void setLinkTaskDueField(String linkTaskDueField){
		this.linkTaskDueField = linkTaskDueField;
	}

	/**
	 * 功能：设置应用信息属性 阅示组别放置域, 以 “;” 分隔
	 * @param linkReviewGroupField String
	 */
	public void setLinkReviewGroupField(String linkReviewGroupField){
		this.linkReviewGroupField = linkReviewGroupField;
	}

	/**
	 * 功能：设置应用信息属性 阅示角色放置域
	 * @param linkReviewRoleField String
	 */
	public void setLinkReviewRoleField(String linkReviewRoleField){
		this.linkReviewRoleField = linkReviewRoleField;
	}

	/**
	 * 功能：设置应用信息属性 所有可阅示用户放置域, 以 “; “分隔
	 * @param linkReviewUsersField String
	 */
	public void setLinkReviewUsersField(String linkReviewUsersField){
		this.linkReviewUsersField = linkReviewUsersField;
	}

	/**
	 * 功能：设置应用信息属性 当前为阅示状态放置域, Y, N
	 * @param linkReviewStatusField String
	 */
	public void setLinkReviewStatusField(String linkReviewStatusField){
		this.linkReviewStatusField = linkReviewStatusField;
	}

	/**
	 * 功能：设置应用信息属性 转发紧急程度放置域, 0-正常, 1-紧急, 2-特急
	 * @param linkDeliveryPriorityField String
	 */
	public void setLinkDeliveryPriorityField(String linkDeliveryPriorityField){
		this.linkDeliveryPriorityField = linkDeliveryPriorityField;
	}

	/**
	 * 功能：设置应用信息属性 传送信息放置域
	 * @param linkUserMessageField String
	 */
	public void setLinkUserMessageField(String linkUserMessageField){
		this.linkUserMessageField = linkUserMessageField;
	}

	/**
	 * 功能：设置应用信息属性 下一任务会签组别或人员放置域, 格式为 (RC1: USER1;USER2;…;|RC_2:USER1;USER2;…;|…). RC1 代表第一条出流的分流控制码, USER1, USER2 等代表该分流指向的会签任务所有可能的参与者; RC2, RC3, … 等代表第二到最后一条出流的分流控制码, 若该任务为直流, 则 RC1 为空, 而返回 (:USER1,USER2,…;)
	 * @param linkNextCyclePropField String
	 */
	public void setLinkNextCyclePropField(String linkNextCyclePropField){
		this.linkNextCyclePropField = linkNextCyclePropField;
	}

    public void setTrayType(String trayType) {
        this.trayType = trayType;
    }

    /**
	 * 功能：获取应用信息属性 应用 ID  
	 * @return String
	 */
	public int getAppId() {
		return this.appId;
	}

    /**
     * 功能：获取应用信息属性 流程应用, Y:是 N:否
     * @return String
     */
    public String getIsFlowProcess() {
        return this.isFlowProcess;
    }

    /**
	 * 功能：获取应用信息属性 工程名称
	 * @return String
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * 功能：获取应用信息属性 过程名称
	 * @return String
	 */
	public String getProcedureName() {
		return this.procedureName;
	}

    /**
     * 功能：获取应用信息属性 组别名称
     * @return String
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
	 * 功能：获取应用信息属性 显示应用分类名称
	 * @return String
	 */
	public String getCategoryName() {
		return this.categoryName;
	}

	/**
	 * 功能：获取应用信息属性 显示应用名称
	 * @return String
	 */
	public String getAppName() {
		return this.appName;
	}

    /**
     * 功能：获取应用信息属性 应用 URL  
     * @return String
     */
    public String getUrl() {
        return this.url;
    }

    /**
	 * 功能：获取应用信息属性 应用数据接口类名
	 * @return String
	 */
	public String getAppDataClass() {
		return this.appDataClass;
	}

	/**
	 * 功能：获取应用信息属性 应用数据 SQLModel, 此 SQLModel 为空则表示应用需自己读取应用的数据
	 * @return String
	 */
	public String getAppDataSqlmodel() {
		return this.appDataSqlmodel;
	}

	/**
	 * 功能：获取应用信息属性 应用显示窗口类型, 0:右框架中 1:新窗口
	 * @return String
	 */
	public int getWindowType() {
		return this.windowType;
	}

	/**
	 * 功能：获取应用信息属性 完成时传送信息, 0:禁止 1:允许
	 * @return String
	 */
	public int getFinishMessage() {
		return this.finishMessage;
	}

	/**
	 * 功能：获取应用信息属性 确认信息框, 0:禁止 1:允许
	 * @return String
	 */
	public int getConfirmFinish() {
		return this.confirmFinish;
	}

	/**
	 * 功能：获取应用信息属性 允许操作项, bit 0:取消, bit 1:特送, bit 2:退回, bit 3:查看流程
	 * @return String
	 */
	public int getAllowOperation() {
		return this.allowOperation;
	}

	/**
	 * 功能：获取应用信息属性 主分类
	 * @return String
	 */
	public String getSortColumn1() {
		return this.sortColumn1;
	}

	/**
	 * 功能：获取应用信息属性 次分类
	 * @return String
	 */
	public String getSortColumn2() {
		return this.sortColumn2;
	}

	/**
	 * 功能：获取应用信息属性 其它分类
	 * @return String
	 */
	public String getSortColumn3() {
		return this.sortColumn3;
	}

	/**
	 * 功能：获取应用信息属性 关键字域名, 应用表单中使用于关键字的域名
	 * @return String
	 */
	public String getAppColumn1() {
		return this.appColumn1;
	}

	/**
	 * 功能：获取应用信息属性 主题域名, 应用表单中使用于主题的域名
	 * @return String
	 */
	public String getAppColumn2() {
		return this.appColumn2;
	}

	/**
	 * 功能：获取应用信息属性 其它域名, 应用表单中使用于其它的域名
	 * @return String
	 */
	public String getAppColumn3() {
		return this.appColumn3;
	}

	/**
	 * 功能：获取应用信息属性 域名 1, 保存的应用表单域名
	 * @return String
	 */
	public String getAppColumn4() {
		return this.appColumn4;
	}

	/**
	 * 功能：获取应用信息属性 域名 2, 保存的应用表单域名
	 * @return String
	 */
	public String getAppColumn5() {
		return this.appColumn5;
	}

	/**
	 * 功能：获取应用信息属性 域名 3, 保存的应用表单域名
	 * @return String
	 */
	public String getAppColumn6() {
		return this.appColumn6;
	}

	/**
	 * 功能：获取应用信息属性 域名 4, 保存的应用表单域名
	 * @return String
	 */
	public String getAppColumn7() {
		return this.appColumn7;
	}

	/**
	 * 功能：获取应用信息属性 域名 5, 保存的应用表单域名
	 * @return String
	 */
	public String getAppColumn8() {
		return this.appColumn8;
	}

	/**
	 * 功能：获取应用信息属性 域名 6, 保存的应用表单域名
	 * @return String
	 */
	public String getAppColumn9() {
		return this.appColumn9;
	}

	/**
	 * 功能：获取应用信息属性 工程名称放置域
	 * @return String
	 */
	public String getLinkProjectNameField() {
		return this.linkProjectNameField;
	}

	/**
	 * 功能：获取应用信息属性 工程描述放置域
	 * @return String
	 */
	public String getLinkProjectDescField() {
		return this.linkProjectDescField;
	}

	/**
	 * 功能：获取应用信息属性 过程名称放置域
	 * @return String
	 */
	public String getLinkProcedureNameField() {
		return this.linkProcedureNameField;
	}

	/**
	 * 功能：获取应用信息属性 过程描述放置域
	 * @return String
	 */
	public String getLinkProcedureDescField() {
		return this.linkProcedureDescField;
	}

	/**
	 * 功能：获取应用信息属性 当前任务名称放置域
	 * @return String
	 */
	public String getLinkTaskNameField() {
		return this.linkTaskNameField;
	}

	/**
	 * 功能：获取应用信息属性 当前任务描述放置域
	 * @return String
	 */
	public String getLinkTaskDescField() {
		return this.linkTaskDescField;
	}

	/**
	 * 功能：获取应用信息属性 当前任务预定时间放置域
	 * @return String
	 */
	public String getLinkTaksDurationField() {
		return this.linkTaksDurationField;
	}

	/**
	 * 功能：获取应用信息属性 当前任务指定组别放置域
	 * @return String
	 */
	public String getLinkGroupField() {
		return this.linkGroupField;
	}

	/**
	 * 功能：获取应用信息属性 当前任务指定角色放置域
	 * @return String
	 */
	public String getLinkRoleFiled() {
		return this.linkRoleFiled;
	}

	/**
	 * 功能：获取应用信息属性 当前角色所有用户放置域, 如有多个用户, 则以 ‘;” 分隔
	 * @return String
	 */
	public String getLinkUsersField() {
		return this.linkUsersField;
	}

	/**
	 * 功能：获取应用信息属性 转发工程名称放置域
	 * @return String
	 */
	public String getLinkFromProjectField() {
		return this.linkFromProjectField;
	}

	/**
	 * 功能：获取应用信息属性 转发过程名称放置域
	 * @return String
	 */
	public String getLinkFormProcedureField() {
		return this.linkFormProcedureField;
	}

	/**
	 * 功能：获取应用信息属性 转发人放置域
	 * @return String
	 */
	public String getLinkFromUserField() {
		return this.linkFromUserField;
	}

	/**
	 * 功能：获取应用信息属性 转发时间放置域
	 * @return String
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public String getLinkFormDateField() throws CalendarException {
		return this.linkFormDateField;
	}

	/**
	 * 功能：获取应用信息属性 转发任务 ID 放置域
	 * @return String
	 */
	public String getLinkFormTaskIdField() {
		return this.linkFormTaskIdField;
	}

	/**
	 * 功能：获取应用信息属性 继承组别放置域
	 * @return String
	 */
	public String getLinkHandleGroupField() {
		return this.linkHandleGroupField;
	}

	/**
	 * 功能：获取应用信息属性 经办人放置域, 如有多个经办人, 则以 “;”分隔
	 * @return String
	 */
	public String getLinkHandlerField() {
		return this.linkHandlerField;
	}

	/**
	 * 功能：获取应用信息属性 是否签收放置域, Y, N
	 * @return String
	 */
	public String getLinkSignStatusField() {
		return this.linkSignStatusField;
	}

	/**
	 * 功能：获取应用信息属性 签收时间放置域
	 * @return String
	 */
	public String getLinkSignDateField() {
		return this.linkSignDateField;
	}

	/**
	 * 功能：获取应用信息属性 应转发时间放置域
	 * @return String
	 */
	public String getLinkTaskDueField() {
		return this.linkTaskDueField;
	}

	/**
	 * 功能：获取应用信息属性 阅示组别放置域, 以 “;” 分隔
	 * @return String
	 */
	public String getLinkReviewGroupField() {
		return this.linkReviewGroupField;
	}

	/**
	 * 功能：获取应用信息属性 阅示角色放置域
	 * @return String
	 */
	public String getLinkReviewRoleField() {
		return this.linkReviewRoleField;
	}

	/**
	 * 功能：获取应用信息属性 所有可阅示用户放置域, 以 “; “分隔
	 * @return String
	 */
	public String getLinkReviewUsersField() {
		return this.linkReviewUsersField;
	}

	/**
	 * 功能：获取应用信息属性 当前为阅示状态放置域, Y, N
	 * @return String
	 */
	public String getLinkReviewStatusField() {
		return this.linkReviewStatusField;
	}

	/**
	 * 功能：获取应用信息属性 转发紧急程度放置域, 0-正常, 1-紧急, 2-特急
	 * @return String
	 */
	public String getLinkDeliveryPriorityField() {
		return this.linkDeliveryPriorityField;
	}

	/**
	 * 功能：获取应用信息属性 传送信息放置域
	 * @return String
	 */
	public String getLinkUserMessageField() {
		return this.linkUserMessageField;
	}

	/**
	 * 功能：获取应用信息属性 下一任务会签组别或人员放置域, 格式为 (RC1: USER1;USER2;…;|RC_2:USER1;USER2;…;|…). RC1 代表第一条出流的分流控制码, USER1, USER2 等代表该分流指向的会签任务所有可能的参与者; RC2, RC3, … 等代表第二到最后一条出流的分流控制码, 若该任务为直流, 则 RC1 为空, 而返回 (:USER1,USER2,…;)
	 * @return String
	 */
	public String getLinkNextCyclePropField() {
		return this.linkNextCyclePropField;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

    public String getTrayType() {
        return this.trayType;
    }

    public void setProcUrl(String procUrl) {
        this.procUrl = procUrl;
    }

    public String getProcUrl() {
        return this.procUrl;
    }
}