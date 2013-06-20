package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 流转过程，在办流转信息 SfActInfo</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfActInfoDTO extends CheckBoxDTO{

	private String sfactActId = "";
	private int sfactAppdefId = 0;
	private String sfactUrl = "";
	private String sfactApplId = "";
	private int sfactDocType = 0;
	private String sfactCaseId = "";
	private int sfactPrjFileId = 0;
    private String sfactProjName = "";
    private int sfactProcId = 0;
	private String sfactProcName = "";
	private String sfactProcDesc = "";
	private int sfactTaskId = 0;
	private String sfactTaskName = "";
	private String sfactTaskDesc = "";
	private String sfactTaskGroup = "";
	private String sfactTaskRole = "";
	private int sfactTaskType = 0;
	private float sfactTaskDuration = 0;
	private int sfactTaskWorkType = 0;
	private String sfactTaskApiName = "";
	private String sfactTaskUrl = "";
	private String sfactTaskAttribute1 = "";
	private String sfactTaskAttribute2 = "";
	private String sfactTaskAttribute3 = "";
	private String sfactTaskAttribute4 = "";
	private String sfactTaskAttribute5 = "";
	private String sfactTaskDivRight = "";
	private String sfactTaskHidden = "";
	private int sfactTaskCtl = 0;
	private int sfactTaskCycleType = 0;
	private String sfactTaskCycleUrl = "";
	private String sfactTaskCommentGroup = "";
	private String sfactTaskCommentRole = "";
	private String sfactTaskCommentInfo = "";
	private String sfactTaskCommentUrl = "";
	private String sfactHandlerGroup = "";
	private String sfactTaskCommentDiv = "";
	private String sfactTaskCommentHide = "";
	private int sfactActStatus = 0;
	private String sfactApplMsg = "";
	private String sfactCommentApplMsg = "";
	private int sfactCommentQty = 0;
	private String sfactCommentUsers = "";
	private int sfactCommentType = 0;
	private String sfactCompleteDate = "";
	private double sfactCompleteRealDuration = 0;
	private int sfactCompleteStatus = 0;
	private String sfactCompleteUser = "";
	private double sfactCompleteWorkDuration = 0;
	private String sfactComposeUser = "";
	private String sfactCreateDt = "";
	private int sfactCycleType = 0;
	private int sfactCycleQty = 0;
	private String sfactCycleUsers = "";
	private int sfactDeliveryPriority = 0;
    private String sfactTaskUsers = "";
    private String sfactUserMsg = "";
	private String sfactFromActId = "";
	private String sfactFromDate = "";
	private String sfactFromProcName = "";
	private String sfactFromProjName = "";
	private int sfactFromTaskId = 0;
	private String sfactFromTaskUser = "";
	private String sfactHandler = "";
	private double sfactLagReal = 0;
	private double sfactLagWork = 0;
	private int sfactLeadDayMode = 0;
	private String sfactNextProcName = "";
	private int sfactNextTaskId = 0;
	private String sfactNextTaskName = "";
	private String sfactPickDate = "";
	private int sfactPickStatus = 0;
	private String sfactPickUser = "";
	private String sfactSignDate = "";
	private String sfactSignDueDate = "";
	private int sfactSignStatus = 0;
	private String sfactSignUser = "";
	private String sfactSortColumn1 = "";
	private String sfactSortColumn2 = "";
	private String sfactSortColumn3 = "";
	private String sfactApplColumn1 = "";
	private String sfactApplColumn2 = "";
	private String sfactApplColumn3 = "";
	private String sfactApplColumn4 = "";
	private String sfactApplColumn5 = "";
	private String sfactApplColumn6 = "";
	private String sfactApplColumn7 = "";
	private String sfactApplColumn8 = "";
	private String sfactApplColumn9 = "";
	private String sfactSuspendDesc = "";
	private int sfactSuspendFlag = 0;
	private String sfactSplitTaskId = "";
	private int sfactPauseId = 0;
	private String sfactCaseLockDate = "";
	private int sfactCaseLockStatus = 0;
	private String sfactCaseLockUser = "";
	private int sfactAssistFlowNum = 0;
	private String sfactAssistFlowAct = "";
	private String sfactParallelFlowAct = "";
	private String sfactFromCaseId = "";
	private String sfactReturnTime = "";
	private String sfactReturnActId = "";
    private String sfactPlusGroup = "";
    private int sfactTaskTid = 0;

    private int copyFlag = 0;

    public SfActInfoDTO() {
		super();
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 ACT_ID（每次流转完成到下一节点都生成新ACTID）
	 * @param sfactActId String
	 */
	public void setSfactActId(String sfactActId){
		this.sfactActId = sfactActId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 应用定义ID
	 * @param sfactAppdefId String
	 */
	public void setSfactAppdefId(int sfactAppdefId){
		this.sfactAppdefId = sfactAppdefId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 当前页面使用的URL
	 * @param sfactUrl String
	 */
	public void setSfactUrl(String sfactUrl){
		this.sfactUrl = sfactUrl;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 应用 APP_ID
	 * @param sfactApplId String
	 */
	public void setSfactApplId(String sfactApplId){
		this.sfactApplId = sfactApplId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 ACT文档类型 0:标准, 1:阅示, 2:会签, 3:并流
	 * @param sfactDocType String
	 */
	public void setSfactDocType(int sfactDocType){
		this.sfactDocType = sfactDocType;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 CASEID（随流程全周期不变化）为空时，生成CASEID，同时写入case信息表
	 * @param sfactCaseId String
	 */
	public void setSfactCaseId(String sfactCaseId){
		this.sfactCaseId = sfactCaseId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_PRJ_FILE_ID
	 * @param sfactPrjFileId String
	 */
	public void setSfactPrjFileId(int sfactPrjFileId){
		this.sfactPrjFileId = sfactPrjFileId;
	}

    /**
     * 功能：设置流转过程，在办流转信息属性 SFACT_PRJ_FILE_ID
     * @param sfactProjName String
     */
    public void setSfactProjName(String sfactProjName){
        this.sfactProjName = sfactProjName;
    }

    /**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_PROC_ID
	 * @param sfactProcId String
	 */
	public void setSfactProcId(int sfactProcId){
		this.sfactProcId = sfactProcId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_PROC_NAME
	 * @param sfactProcName String
	 */
	public void setSfactProcName(String sfactProcName){
		this.sfactProcName = sfactProcName;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_PROC_DESC
	 * @param sfactProcDesc String
	 */
	public void setSfactProcDesc(String sfactProcDesc){
		this.sfactProcDesc = sfactProcDesc;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_ID
	 * @param sfactTaskId String
	 */
	public void setSfactTaskId(int sfactTaskId){
		this.sfactTaskId = sfactTaskId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_NAME
	 * @param sfactTaskName String
	 */
	public void setSfactTaskName(String sfactTaskName){
		this.sfactTaskName = sfactTaskName;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_DESC
	 * @param sfactTaskDesc String
	 */
	public void setSfactTaskDesc(String sfactTaskDesc){
		this.sfactTaskDesc = sfactTaskDesc;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_GROUP
	 * @param sfactTaskGroup String
	 */
	public void setSfactTaskGroup(String sfactTaskGroup){
		this.sfactTaskGroup = sfactTaskGroup;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_ROLE
	 * @param sfactTaskRole String
	 */
	public void setSfactTaskRole(String sfactTaskRole){
		this.sfactTaskRole = sfactTaskRole;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_TYPE
	 * @param sfactTaskType String
	 */
	public void setSfactTaskType(int sfactTaskType){
		this.sfactTaskType = sfactTaskType;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_DURATION
	 * @param sfactTaskDuration String
	 */
	public void setSfactTaskDuration(float sfactTaskDuration){
		this.sfactTaskDuration = sfactTaskDuration;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_WORK_TYPE
	 * @param sfactTaskWorkType String
	 */
	public void setSfactTaskWorkType(int sfactTaskWorkType){
		this.sfactTaskWorkType = sfactTaskWorkType;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_API_NAME
	 * @param sfactTaskApiName String
	 */
	public void setSfactTaskApiName(String sfactTaskApiName){
		this.sfactTaskApiName = sfactTaskApiName;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_URL
	 * @param sfactTaskUrl String
	 */
	public void setSfactTaskUrl(String sfactTaskUrl){
		this.sfactTaskUrl = sfactTaskUrl;
	}

    /**
     * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_1
     * @param sfactTaskTid String
     */
    public void setSfactTaskTid(int sfactTaskTid){
        this.sfactTaskTid = sfactTaskTid;
    }

    /**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_1
	 * @param sfactTaskAttribute1 String
	 */
	public void setSfactTaskAttribute1(String sfactTaskAttribute1){
		this.sfactTaskAttribute1 = sfactTaskAttribute1;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_2
	 * @param sfactTaskAttribute2 String
	 */
	public void setSfactTaskAttribute2(String sfactTaskAttribute2){
		this.sfactTaskAttribute2 = sfactTaskAttribute2;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_3
	 * @param sfactTaskAttribute3 String
	 */
	public void setSfactTaskAttribute3(String sfactTaskAttribute3){
		this.sfactTaskAttribute3 = sfactTaskAttribute3;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_4
	 * @param sfactTaskAttribute4 String
	 */
	public void setSfactTaskAttribute4(String sfactTaskAttribute4){
		this.sfactTaskAttribute4 = sfactTaskAttribute4;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_5
	 * @param sfactTaskAttribute5 String
	 */
	public void setSfactTaskAttribute5(String sfactTaskAttribute5){
		this.sfactTaskAttribute5 = sfactTaskAttribute5;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_READ_ONLY
	 * @param sfactTaskDivRight String
	 */
	public void setSfactTaskDivRigth(String sfactTaskDivRight){
		this.sfactTaskDivRight = sfactTaskDivRight;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_HIDDEN
	 * @param sfactTaskHidden String
	 */
	public void setSfactTaskHidden(String sfactTaskHidden){
		this.sfactTaskHidden = sfactTaskHidden;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_CTL
	 * @param sfactTaskCtl String
	 */
	public void setSfactTaskCtl(int sfactTaskCtl){
		this.sfactTaskCtl = sfactTaskCtl;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_CYCLE_TYPE
	 * @param sfactTaskCycleType String
	 */
	public void setSfactTaskCycleType(int sfactTaskCycleType){
		this.sfactTaskCycleType = sfactTaskCycleType;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_CYCLE_URL
	 * @param sfactTaskCycleUrl String
	 */
	public void setSfactTaskCycleUrl(String sfactTaskCycleUrl){
		this.sfactTaskCycleUrl = sfactTaskCycleUrl;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_COMMENT_GROUP
	 * @param sfactTaskCommentGroup String
	 */
	public void setSfactTaskCommentGroup(String sfactTaskCommentGroup){
		this.sfactTaskCommentGroup = sfactTaskCommentGroup;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_COMMENT_ROLE
	 * @param sfactTaskCommentRole String
	 */
	public void setSfactTaskCommentRole(String sfactTaskCommentRole){
		this.sfactTaskCommentRole = sfactTaskCommentRole;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_COMMENT_INFO
	 * @param sfactTaskCommentInfo String
	 */
	public void setSfactTaskCommentInfo(String sfactTaskCommentInfo){
		this.sfactTaskCommentInfo = sfactTaskCommentInfo;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_COMMENT_URL
	 * @param sfactTaskCommentUrl String
	 */
	public void setSfactTaskCommentUrl(String sfactTaskCommentUrl){
		this.sfactTaskCommentUrl = sfactTaskCommentUrl;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_HANDLER_PROP
	 * @param sfactHandlerGroup String
	 */
	public void setSfactHandlerGroup(String sfactHandlerGroup){
		this.sfactHandlerGroup = sfactHandlerGroup;
	}

    /**
     * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_HANDLER_PROP
     * @param sfactPlusGroup String
     */
    public void setSfactPlusGroup(String sfactPlusGroup){
        this.sfactPlusGroup = sfactPlusGroup;
    }

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_COMMENT_READ
	 * @param sfactTaskCommentDiv String
	 */
	public void setSfactTaskCommentDiv(String sfactTaskCommentDiv){
		this.sfactTaskCommentDiv = sfactTaskCommentDiv;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_TASK_COMMENT_HIDE
	 * @param sfactTaskCommentHide String
	 */
	public void setSfactTaskCommentHide(String sfactTaskCommentHide){
		this.sfactTaskCommentHide = sfactTaskCommentHide;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_ACT_STATUS
	 * @param sfactActStatus String
	 */
	public void setSfactActStatus(int sfactActStatus){
		this.sfactActStatus = sfactActStatus;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_APPL_MSG
	 * @param sfactApplMsg String
	 */
	public void setSfactApplMsg(String sfactApplMsg){
		this.sfactApplMsg = sfactApplMsg;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_COMMENT_APPL_MSG
	 * @param sfactCommentApplMsg String
	 */
	public void setSfactCommentApplMsg(String sfactCommentApplMsg){
		this.sfactCommentApplMsg = sfactCommentApplMsg;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_COMMENT_QTY
	 * @param sfactCommentQty String
	 */
	public void setSfactCommentQty(int sfactCommentQty){
		this.sfactCommentQty = sfactCommentQty;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_COMMENT_USERS
	 * @param sfactCommentUsers String
	 */
	public void setSfactCommentUsers(String sfactCommentUsers){
		this.sfactCommentUsers = sfactCommentUsers;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_COMMENT_TYPE
	 * @param sfactCommentType String
	 */
	public void setSfactCommentType(int sfactCommentType){
		this.sfactCommentType = sfactCommentType;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_COMPLETE_DATE
	 * @param sfactCompleteDate String
	 */
	public void setSfactCompleteDate(String sfactCompleteDate) {
		this.sfactCompleteDate = sfactCompleteDate;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_COMPLETE_REAL_DURATION
	 * @param sfactCompleteRealDuration String
	 */
	public void setSfactCompleteRealDuration(double sfactCompleteRealDuration){
		this.sfactCompleteRealDuration = sfactCompleteRealDuration;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_COMPLETE_STATUS
	 * @param sfactCompleteStatus String
	 */
	public void setSfactCompleteStatus(int sfactCompleteStatus){
		this.sfactCompleteStatus = sfactCompleteStatus;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_COMPLETE_USER
	 * @param sfactCompleteUser String
	 */
	public void setSfactCompleteUser(String sfactCompleteUser){
		this.sfactCompleteUser = sfactCompleteUser;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_COMPLETE_WORK_DURATION
	 * @param sfactCompleteWorkDuration String
	 */
	public void setSfactCompleteWorkDuration(double sfactCompleteWorkDuration){
		this.sfactCompleteWorkDuration = sfactCompleteWorkDuration;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_COMPOSE_USER
	 * @param sfactComposeUser String
	 */
	public void setSfactComposeUser(String sfactComposeUser){
		this.sfactComposeUser = sfactComposeUser;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_CREATE_DT
	 * @param sfactCreateDt String
	 */
	public void setSfactCreateDt(String sfactCreateDt) {
		this.sfactCreateDt = sfactCreateDt;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 0:未指定  1:顺序处理
	 * @param sfactCycleType String
	 */
	public void setSfactCycleType(int sfactCycleType){
		this.sfactCycleType = sfactCycleType;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_CYCLE_QTY
	 * @param sfactCycleQty String
	 */
	public void setSfactCycleQty(int sfactCycleQty){
		this.sfactCycleQty = sfactCycleQty;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_CYCLE_USERS
	 * @param sfactCycleUsers String
	 */
	public void setSfactCycleUsers(String sfactCycleUsers){
		this.sfactCycleUsers = sfactCycleUsers;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 0:正常  1:紧急  2:特急
	 * @param sfactDeliveryPriority String
	 */
	public void setSfactDeliveryPriority(int sfactDeliveryPriority){
		this.sfactDeliveryPriority = sfactDeliveryPriority;
	}

    /**
     * 功能：设置流转过程，在办流转信息属性 0:正常  1:紧急  2:特急
     * @param sfactTaskUsers String
     */
    public void setSfactTaskUsers(String sfactTaskUsers){
        this.sfactTaskUsers = sfactTaskUsers;
    }

    /**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_USER_MSG
	 * @param sfactUserMsg String
	 */
	public void setSfactUserMsg(String sfactUserMsg){
		this.sfactUserMsg = sfactUserMsg;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_FROM_ACT_ID
	 * @param sfactFromActId String
	 */
	public void setSfactFromActId(String sfactFromActId){
		this.sfactFromActId = sfactFromActId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_FROM_DATE
	 * @param sfactFromDate String
	 */
	public void setSfactFromDate(String sfactFromDate) {
		this.sfactFromDate = sfactFromDate;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_FROM_PROC_NAME
	 * @param sfactFromProcName String
	 */
	public void setSfactFromProcName(String sfactFromProcName){
		this.sfactFromProcName = sfactFromProcName;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_FROM_PROJ_NAME
	 * @param sfactFromProjName String
	 */
	public void setSfactFromProjName(String sfactFromProjName){
		this.sfactFromProjName = sfactFromProjName;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_FROM_TASK_ID
	 * @param sfactFromTaskId String
	 */
	public void setSfactFromTaskId(int sfactFromTaskId){
		this.sfactFromTaskId = sfactFromTaskId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 转发用户ID
	 * @param sfactFromTaskUser String
	 */
	public void setSfactFromTaskUser(String sfactFromTaskUser){
		this.sfactFromTaskUser = sfactFromTaskUser;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 经办人信息
	 * @param sfactHandler String
	 */
	public void setSfactHandler(String sfactHandler){
		this.sfactHandler = sfactHandler;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 从文档发过来到签收之间的工作时间差值
	 * @param sfactLagReal String
	 */
	public void setSfactLagReal(double sfactLagReal){
		this.sfactLagReal = sfactLagReal;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 从文档发送过来到签收之间的真实时间差值
	 * @param sfactLagWork String
	 */
	public void setSfactLagWork(double sfactLagWork){
		this.sfactLagWork = sfactLagWork;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 以工时还是日期为超时依据
	 * @param sfactLeadDayMode String
	 */
	public void setSfactLeadDayMode(int sfactLeadDayMode){
		this.sfactLeadDayMode = sfactLeadDayMode;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 下一节点过程名称（LOG专用）
	 * @param sfactNextProcName String
	 */
	public void setSfactNextProcName(String sfactNextProcName){
		this.sfactNextProcName = sfactNextProcName;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 下一节点任务（LOG专用）
	 * @param sfactNextTaskId String
	 */
	public void setSfactNextTaskId(int sfactNextTaskId){
		this.sfactNextTaskId = sfactNextTaskId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 下一节点描述（LOG专用）
	 * @param sfactNextTaskName String
	 */
	public void setSfactNextTaskName(String sfactNextTaskName){
		this.sfactNextTaskName = sfactNextTaskName;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_PICK_DATE
	 * @param sfactPickDate String
	 */
	public void setSfactPickDate(String sfactPickDate) {
		this.sfactPickDate = sfactPickDate;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_PICK_STATUS
	 * @param sfactPickStatus String
	 */
	public void setSfactPickStatus(int sfactPickStatus){
		this.sfactPickStatus = sfactPickStatus;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_PICK_USER
	 * @param sfactPickUser String
	 */
	public void setSfactPickUser(String sfactPickUser){
		this.sfactPickUser = sfactPickUser;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 签收日期
	 * @param sfactSignDate String
	 */
	public void setSfactSignDate(String sfactSignDate) {
		this.sfactSignDate = sfactSignDate;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 应转发时间
	 * @param sfactSignDueDate String
	 */
	public void setSfactSignDueDate(String sfactSignDueDate){
		this.sfactSignDueDate = sfactSignDueDate;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_SIGN_STATUS
	 * @param sfactSignStatus String
	 */
	public void setSfactSignStatus(int sfactSignStatus){
		this.sfactSignStatus = sfactSignStatus;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_SIGN_USER
	 * @param sfactSignUser String
	 */
	public void setSfactSignUser(String sfactSignUser){
		this.sfactSignUser = sfactSignUser;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 工作栏主分类
	 * @param sfactSortColumn1 String
	 */
	public void setSfactSortColumn1(String sfactSortColumn1){
		this.sfactSortColumn1 = sfactSortColumn1;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 工作栏次分类
	 * @param sfactSortColumn2 String
	 */
	public void setSfactSortColumn2(String sfactSortColumn2){
		this.sfactSortColumn2 = sfactSortColumn2;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 工作栏其他分类
	 * @param sfactSortColumn3 String
	 */
	public void setSfactSortColumn3(String sfactSortColumn3){
		this.sfactSortColumn3 = sfactSortColumn3;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 关键字
	 * @param sfactApplColumn1 String
	 */
	public void setSfactApplColumn1(String sfactApplColumn1){
		this.sfactApplColumn1 = sfactApplColumn1;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 主题
	 * @param sfactApplColumn2 String
	 */
	public void setSfactApplColumn2(String sfactApplColumn2){
		this.sfactApplColumn2 = sfactApplColumn2;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 其它
	 * @param sfactApplColumn3 String
	 */
	public void setSfactApplColumn3(String sfactApplColumn3){
		this.sfactApplColumn3 = sfactApplColumn3;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 流程－应用交换字段1
	 * @param sfactApplColumn4 String
	 */
	public void setSfactApplColumn4(String sfactApplColumn4){
		this.sfactApplColumn4 = sfactApplColumn4;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 流程－应用交换字段2
	 * @param sfactApplColumn5 String
	 */
	public void setSfactApplColumn5(String sfactApplColumn5){
		this.sfactApplColumn5 = sfactApplColumn5;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 流程－应用交换字段3
	 * @param sfactApplColumn6 String
	 */
	public void setSfactApplColumn6(String sfactApplColumn6){
		this.sfactApplColumn6 = sfactApplColumn6;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 流程－应用交换字段4
	 * @param sfactApplColumn7 String
	 */
	public void setSfactApplColumn7(String sfactApplColumn7){
		this.sfactApplColumn7 = sfactApplColumn7;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 流程－应用交换字段5
	 * @param sfactApplColumn8 String
	 */
	public void setSfactApplColumn8(String sfactApplColumn8){
		this.sfactApplColumn8 = sfactApplColumn8;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 流程－应用交换字段6
	 * @param sfactApplColumn9 String
	 */
	public void setSfactApplColumn9(String sfactApplColumn9){
		this.sfactApplColumn9 = sfactApplColumn9;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_SUSPEND_DESC
	 * @param sfactSuspendDesc String
	 */
	public void setSfactSuspendDesc(String sfactSuspendDesc){
		this.sfactSuspendDesc = sfactSuspendDesc;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_SUSPEND_FLAG
	 * @param sfactSuspendFlag String
	 */
	public void setSfactSuspendFlag(int sfactSuspendFlag){
		this.sfactSuspendFlag = sfactSuspendFlag;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 SFACT_SPLIT_TASK_ID
	 * @param sfactSplitTaskId String
	 */
	public void setSfactSplitTaskId(String sfactSplitTaskId){
		this.sfactSplitTaskId = sfactSplitTaskId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 暂停ID
	 * @param sfactPauseId String
	 */
	public void setSfactPauseId(int sfactPauseId){
		this.sfactPauseId = sfactPauseId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 锁定日期
	 * @param sfactCaseLockDate String
	 */
	public void setSfactCaseLockDate(String sfactCaseLockDate){
		this.sfactCaseLockDate = sfactCaseLockDate;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 锁定状态
	 * @param sfactCaseLockStatus String
	 */
	public void setSfactCaseLockStatus(int sfactCaseLockStatus){
		this.sfactCaseLockStatus = sfactCaseLockStatus;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 锁定人
	 * @param sfactCaseLockUser String
	 */
	public void setSfactCaseLockUser(String sfactCaseLockUser){
		this.sfactCaseLockUser = sfactCaseLockUser;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 辅流数量
	 * @param sfactAssistFlowNum String
	 */
	public void setSfactAssistFlowNum(int sfactAssistFlowNum){
		this.sfactAssistFlowNum = sfactAssistFlowNum;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 辅流ACTID字符串（；分隔）
	 * @param sfactAssistFlowAct String
	 */
	public void setSfactAssistFlowAct(String sfactAssistFlowAct){
		this.sfactAssistFlowAct = sfactAssistFlowAct;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 并发ACTID字符串（；分隔）
	 * @param sfactParallelFlowAct String
	 */
	public void setSfactParallelFlowAct(String sfactParallelFlowAct){
		this.sfactParallelFlowAct = sfactParallelFlowAct;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 上一CASEID(辅流、子流专用)
	 * @param sfactFromCaseId String
	 */
	public void setSfactFromCaseId(String sfactFromCaseId){
		this.sfactFromCaseId = sfactFromCaseId;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 退回开始时间点
	 * @param sfactReturnTime String
	 */
	public void setSfactReturnTime(String sfactReturnTime) {
		this.sfactReturnTime = sfactReturnTime;
	}

	/**
	 * 功能：设置流转过程，在办流转信息属性 退回开始ACTID
	 * @param sfactReturnActId String
	 */
	public void setSfactReturnActId(String sfactReturnActId){
		this.sfactReturnActId = sfactReturnActId;
	}


	/**
	 * 功能：获取流转过程，在办流转信息属性 ACT_ID（每次流转完成到下一节点都生成新ACTID）
	 * @return String
	 */
	public String getSfactActId() {
		return this.sfactActId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 应用定义ID
	 * @return String
	 */
	public int getSfactAppdefId() {
		return this.sfactAppdefId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 当前页面使用的URL
	 * @return String
	 */
	public String getSfactUrl() {
		return this.sfactUrl;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 应用 APP_ID
	 * @return String
	 */
	public String getSfactApplId() {
		return this.sfactApplId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 ACT文档类型 0:标准, 1:阅示, 2:会签, 3:并流
	 * @return String
	 */
	public int getSfactDocType() {
		return this.sfactDocType;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 CASEID（随流程全周期不变化）为空时，生成CASEID，同时写入case信息表
	 * @return String
	 */
	public String getSfactCaseId() {
		return this.sfactCaseId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_PRJ_FILE_ID
	 * @return String
	 */
	public int getSfactPrjFileId() {
		return this.sfactPrjFileId;
	}

    /**
     * 功能：获取流转过程，在办流转信息属性 SFACT_PRJ_FILE_ID
     * @return String
     */
    public String getSfactProjName() {
        return this.sfactProjName;
    }

    /**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_PROC_ID
	 * @return String
	 */
	public int getSfactProcId() {
		return this.sfactProcId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_PROC_NAME
	 * @return String
	 */
	public String getSfactProcName() {
		return this.sfactProcName;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_PROC_DESC
	 * @return String
	 */
	public String getSfactProcDesc() {
		return this.sfactProcDesc;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_ID
	 * @return String
	 */
	public int getSfactTaskId() {
		return this.sfactTaskId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_NAME
	 * @return String
	 */
	public String getSfactTaskName() {
		return this.sfactTaskName;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_DESC
	 * @return String
	 */
	public String getSfactTaskDesc() {
		return this.sfactTaskDesc;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_GROUP
	 * @return String
	 */
	public String getSfactTaskGroup() {
		return this.sfactTaskGroup;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_ROLE
	 * @return String
	 */
	public String getSfactTaskRole() {
		return this.sfactTaskRole;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_TYPE
	 * @return String
	 */
	public int getSfactTaskType() {
		return this.sfactTaskType;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_DURATION
	 * @return String
	 */
	public float getSfactTaskDuration() {
		return this.sfactTaskDuration;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_WORK_TYPE
	 * @return String
	 */
	public int getSfactTaskWorkType() {
		return this.sfactTaskWorkType;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_API_NAME
	 * @return String
	 */
	public String getSfactTaskApiName() {
		return this.sfactTaskApiName;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_URL
	 * @return String
	 */
	public String getSfactTaskUrl() {
		return this.sfactTaskUrl;
	}

    /**
     * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_1
     * @return String
     */
    public int getSfactTaskTid() {
        return this.sfactTaskTid;
    }

    /**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_1
	 * @return String
	 */
	public String getSfactTaskAttribute1() {
		return this.sfactTaskAttribute1;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_2
	 * @return String
	 */
	public String getSfactTaskAttribute2() {
		return this.sfactTaskAttribute2;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_3
	 * @return String
	 */
	public String getSfactTaskAttribute3() {
		return this.sfactTaskAttribute3;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_4
	 * @return String
	 */
	public String getSfactTaskAttribute4() {
		return this.sfactTaskAttribute4;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_ATTRIBUTE_5
	 * @return String
	 */
	public String getSfactTaskAttribute5() {
		return this.sfactTaskAttribute5;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_READ_ONLY
	 * @return String
	 */
	public String getSfactTaskDivRight() {
		return this.sfactTaskDivRight;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_HIDDEN
	 * @return String
	 */
	public String getSfactTaskHidden() {
		return this.sfactTaskHidden;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_CTL
	 * @return String
	 */
	public int getSfactTaskCtl() {
		return this.sfactTaskCtl;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_CYCLE_TYPE
	 * @return String
	 */
	public int getSfactTaskCycleType() {
		return this.sfactTaskCycleType;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_CYCLE_URL
	 * @return String
	 */
	public String getSfactTaskCycleUrl() {
		return this.sfactTaskCycleUrl;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_COMMENT_GROUP
	 * @return String
	 */
	public String getSfactTaskCommentGroup() {
		return this.sfactTaskCommentGroup;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_COMMENT_ROLE
	 * @return String
	 */
	public String getSfactTaskCommentRole() {
		return this.sfactTaskCommentRole;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_COMMENT_INFO
	 * @return String
	 */
	public String getSfactTaskCommentInfo() {
		return this.sfactTaskCommentInfo;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_COMMENT_URL
	 * @return String
	 */
	public String getSfactTaskCommentUrl() {
		return this.sfactTaskCommentUrl;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_HANDLER_PROP
	 * @return String
	 */
	public String getSfactHandlerGroup() {
		return this.sfactHandlerGroup;
	}

    /**
     * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_HANDLER_PROP
     * @return String
     */
    public String getSfactPlusGroup() {
        return this.sfactPlusGroup;
    }

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_COMMENT_READ
	 * @return String
	 */
	public String getSfactTaskCommentDiv() {
		return this.sfactTaskCommentDiv;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_TASK_COMMENT_HIDE
	 * @return String
	 */
	public String getSfactTaskCommentHide() {
		return this.sfactTaskCommentHide;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_ACT_STATUS
	 * @return String
	 */
	public int getSfactActStatus() {
		return this.sfactActStatus;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_APPL_MSG
	 * @return String
	 */
	public String getSfactApplMsg() {
		return this.sfactApplMsg;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_COMMENT_APPL_MSG
	 * @return String
	 */
	public String getSfactCommentApplMsg() {
		return this.sfactCommentApplMsg;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_COMMENT_QTY
	 * @return String
	 */
	public int getSfactCommentQty() {
		return this.sfactCommentQty;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_COMMENT_USERS
	 * @return String
	 */
	public String getSfactCommentUsers() {
		return this.sfactCommentUsers;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_COMMENT_TYPE
	 * @return String
	 */
	public int getSfactCommentType() {
		return this.sfactCommentType;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_COMPLETE_DATE
	 * @return String
	 */
	public String getSfactCompleteDate() {
		return this.sfactCompleteDate;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_COMPLETE_REAL_DURATION
	 * @return String
	 */
	public double getSfactCompleteRealDuration() {
		return this.sfactCompleteRealDuration;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_COMPLETE_STATUS
	 * @return String
	 */
	public int getSfactCompleteStatus() {
		return this.sfactCompleteStatus;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_COMPLETE_USER
	 * @return String
	 */
	public String getSfactCompleteUser() {
		return this.sfactCompleteUser;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_COMPLETE_WORK_DURATION
	 * @return String
	 */
	public double getSfactCompleteWorkDuration() {
		return this.sfactCompleteWorkDuration;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_COMPOSE_USER
	 * @return String
	 */
	public String getSfactComposeUser() {
		return this.sfactComposeUser;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_CREATE_DT
	 * @return String
	 */
	public String getSfactCreateDt() {
		return this.sfactCreateDt;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 0:未指定  1:顺序处理
	 * @return String
	 */
	public int getSfactCycleType() {
		return this.sfactCycleType;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_CYCLE_QTY
	 * @return String
	 */
	public int getSfactCycleQty() {
		return this.sfactCycleQty;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_CYCLE_USERS
	 * @return String
	 */
	public String getSfactCycleUsers() {
		return this.sfactCycleUsers;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 0:正常  1:紧急  2:特急
	 * @return String
	 */
	public int getSfactDeliveryPriority() {
		return this.sfactDeliveryPriority;
	}

    /**
     * 功能：获取流转过程，在办流转信息属性 0:正常  1:紧急  2:特急
     * @return String
     */
    public String getSfactTaskUsers() {
        return this.sfactTaskUsers;
    }

    /**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_USER_MSG
	 * @return String
	 */
	public String getSfactUserMsg() {
		return this.sfactUserMsg;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_FROM_ACT_ID
	 * @return String
	 */
	public String getSfactFromActId() {
		return this.sfactFromActId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_FROM_DATE
	 * @return String
	 */
	public String getSfactFromDate()  {
		return this.sfactFromDate;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_FROM_PROC_NAME
	 * @return String
	 */
	public String getSfactFromProcName() {
		return this.sfactFromProcName;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_FROM_PROJ_NAME
	 * @return String
	 */
	public String getSfactFromProjName() {
		return this.sfactFromProjName;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_FROM_TASK_ID
	 * @return String
	 */
	public int getSfactFromTaskId() {
		return this.sfactFromTaskId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 转发用户ID
	 * @return String
	 */
	public String getSfactFromTaskUser() {
		return this.sfactFromTaskUser;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 经办人信息
	 * @return String
	 */
	public String getSfactHandler() {
		return this.sfactHandler;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 从文档发过来到签收之间的工作时间差值
	 * @return String
	 */
	public double getSfactLagReal() {
		return this.sfactLagReal;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 从文档发送过来到签收之间的真实时间差值
	 * @return String
	 */
	public double getSfactLagWork() {
		return this.sfactLagWork;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 以工时还是日期为超时依据
	 * @return String
	 */
	public int getSfactLeadDayMode() {
		return this.sfactLeadDayMode;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 下一节点过程名称（LOG专用）
	 * @return String
	 */
	public String getSfactNextProcName() {
		return this.sfactNextProcName;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 下一节点任务（LOG专用）
	 * @return String
	 */
	public int getSfactNextTaskId() {
		return this.sfactNextTaskId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 下一节点描述（LOG专用）
	 * @return String
	 */
	public String getSfactNextTaskName() {
		return this.sfactNextTaskName;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_PICK_DATE
	 * @return String
	 */
	public String getSfactPickDate() {
		return this.sfactPickDate;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_PICK_STATUS
	 * @return String
	 */
	public int getSfactPickStatus() {
		return this.sfactPickStatus;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_PICK_USER
	 * @return String
	 */
	public String getSfactPickUser() {
		return this.sfactPickUser;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 签收日期
	 * @return String
	 */
	public String getSfactSignDate() {
		return this.sfactSignDate;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 应转发时间
	 * @return String
	 */
	public String getSfactSignDueDate() {
		return this.sfactSignDueDate;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_SIGN_STATUS
	 * @return String
	 */
	public int getSfactSignStatus() {
		return this.sfactSignStatus;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_SIGN_USER
	 * @return String
	 */
	public String getSfactSignUser() {
		return this.sfactSignUser;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 工作栏主分类
	 * @return String
	 */
	public String getSfactSortColumn1() {
		return this.sfactSortColumn1;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 工作栏次分类
	 * @return String
	 */
	public String getSfactSortColumn2() {
		return this.sfactSortColumn2;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 工作栏其他分类
	 * @return String
	 */
	public String getSfactSortColumn3() {
		return this.sfactSortColumn3;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 关键字
	 * @return String
	 */
	public String getSfactApplColumn1() {
		return this.sfactApplColumn1;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 主题
	 * @return String
	 */
	public String getSfactApplColumn2() {
		return this.sfactApplColumn2;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 其它
	 * @return String
	 */
	public String getSfactApplColumn3() {
		return this.sfactApplColumn3;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 流程－应用交换字段1
	 * @return String
	 */
	public String getSfactApplColumn4() {
		return this.sfactApplColumn4;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 流程－应用交换字段2
	 * @return String
	 */
	public String getSfactApplColumn5() {
		return this.sfactApplColumn5;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 流程－应用交换字段3
	 * @return String
	 */
	public String getSfactApplColumn6() {
		return this.sfactApplColumn6;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 流程－应用交换字段4
	 * @return String
	 */
	public String getSfactApplColumn7() {
		return this.sfactApplColumn7;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 流程－应用交换字段5
	 * @return String
	 */
	public String getSfactApplColumn8() {
		return this.sfactApplColumn8;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 流程－应用交换字段6
	 * @return String
	 */
	public String getSfactApplColumn9() {
		return this.sfactApplColumn9;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_SUSPEND_DESC
	 * @return String
	 */
	public String getSfactSuspendDesc() {
		return this.sfactSuspendDesc;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_SUSPEND_FLAG
	 * @return String
	 */
	public int getSfactSuspendFlag() {
		return this.sfactSuspendFlag;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 SFACT_SPLIT_TASK_ID
	 * @return String
	 */
	public String getSfactSplitTaskId() {
		return this.sfactSplitTaskId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 暂停ID
	 * @return String
	 */
	public int getSfactPauseId() {
		return this.sfactPauseId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 锁定日期
	 * @return String
	 */
	public String getSfactCaseLockDate() {
		return this.sfactCaseLockDate;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 锁定状态
	 * @return String
	 */
	public int getSfactCaseLockStatus() {
		return this.sfactCaseLockStatus;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 锁定人
	 * @return String
	 */
	public String getSfactCaseLockUser() {
		return this.sfactCaseLockUser;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 辅流数量
	 * @return String
	 */
	public int getSfactAssistFlowNum() {
		return this.sfactAssistFlowNum;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 辅流ACTID字符串（；分隔）
	 * @return String
	 */
	public String getSfactAssistFlowAct() {
		return this.sfactAssistFlowAct;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 并发ACTID字符串（；分隔）
	 * @return String
	 */
	public String getSfactParallelFlowAct() {
		return this.sfactParallelFlowAct;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 上一CASEID(辅流、子流专用)
	 * @return String
	 */
	public String getSfactFromCaseId() {
		return this.sfactFromCaseId;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 退回开始时间点
	 * @return String
	 */
	public String getSfactReturnTime() {
		return this.sfactReturnTime;
	}

	/**
	 * 功能：获取流转过程，在办流转信息属性 退回开始ACTID
	 * @return String
	 */
	public String getSfactReturnActId() {
		return this.sfactReturnActId;
	}

    public int getCopyFlag() {
        return this.copyFlag;
    }

    public void setCopyFlag(int copyFlag) {
        this.copyFlag = copyFlag;
    }   
}