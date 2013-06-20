package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 接口程序信息 SfApi</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfApiDTO extends CheckBoxDTO{

	private int apiId = 0;
	private int projectId = 0;
	private int procedureId = 0;
	private int taskId = 0;
	private String projectName = "";
	private String procedureName = "";
	private String taskName = "";
	private int apiControl = 0;
	private String sfqueryopen = "";
	private String sfpostopen = "";
	private String sfquerysign = "";
	private String sfpostsign = "";
	private String sfquerycomplete = "";
	private String sfgroupreview = "";
	private String sfquerycyclereview = "";
	private String sfqueryconditionalflow = "";
	private String sfquerygroup = "";
	private String sfparallelflow = "";
	private String sfqueryassistflow = "";
	private String sfquerydistribute = "";
	private String sfquerygoback = "";
	private String sfquerysave = "";
	private String sfpostsave = "";
	private String apiName = "";
	private String apiControlStr = "";
    private int taskTid = 0;
    private String enabled = "";

    public String getApiControlStr() {
		return apiControlStr;
	}

	public void setApiControlStr(String apiControlStr) {
		this.apiControlStr = apiControlStr;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public SfApiDTO() {
		super();
	}

	/**
	 * 功能：设置接口程序信息属性 接口程序 ID  
	 * @param apiId String
	 */
	public void setApiId(int apiId){
		this.apiId = apiId;
	}

	/**
	 * 功能：设置接口程序信息属性 工程名称
	 * @param projectName String
	 */
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}

	/**
	 * 功能：设置接口程序信息属性 过程名称
	 * @param procedureName String
	 */
	public void setProcedureName(String procedureName){
		this.procedureName = procedureName;
	}

	/**
	 * 功能：设置接口程序信息属性 任务名称
	 * @param taskName String
	 */
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

    public void setTaskTid(int taskTid) {
        this.taskTid = taskTid;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
	 * 功能：设置接口程序信息属性 API 控制, bit 0 - SFQueryOpen, bit 1 - SFPostOpen, bit 2 - SFQuerySign, bit 3 - SFPostSign, bit 4 - SFQueryComplete, bit 5 - SFGroupReview, bit 6 - SFQueryCycleReview, bit 7 - SFQueryConditionalFlow, bit 8 - SFQueryGroup, bit 9 - SFParallelFlow, bit 10 - SFQueryAssistFlow, bit 11 - SFQueryDistribute, bit 12 - SFQueryGoBack, bit 13 - SFQuerySave, bit 14 - SFPostSave
	 * @param apiControl String
	 */
	public void setApiControl(int apiControl){
		this.apiControl = apiControl;
	}

	/**
	 * 功能：设置接口程序信息属性 SFQueryOpen
	 * @param sfqueryopen String
	 */
	public void setSfqueryopen(String sfqueryopen){
		this.sfqueryopen = sfqueryopen;
	}

	/**
	 * 功能：设置接口程序信息属性 SFPostOpen
	 * @param sfpostopen String
	 */
	public void setSfpostopen(String sfpostopen){
		this.sfpostopen = sfpostopen;
	}

	/**
	 * 功能：设置接口程序信息属性 SFQuerySign
	 * @param sfquerysign String
	 */
	public void setSfquerysign(String sfquerysign){
		this.sfquerysign = sfquerysign;
	}

	/**
	 * 功能：设置接口程序信息属性 SFPostSign
	 * @param sfpostsign String
	 */
	public void setSfpostsign(String sfpostsign){
		this.sfpostsign = sfpostsign;
	}

	/**
	 * 功能：设置接口程序信息属性 SFQueryComplete
	 * @param sfquerycomplete String
	 */
	public void setSfquerycomplete(String sfquerycomplete){
		this.sfquerycomplete = sfquerycomplete;
	}

	/**
	 * 功能：设置接口程序信息属性 SFGroupReview
	 * @param sfgroupreview String
	 */
	public void setSfgroupreview(String sfgroupreview){
		this.sfgroupreview = sfgroupreview;
	}

	/**
	 * 功能：设置接口程序信息属性 SFQueryCycleReview
	 * @param sfquerycyclereview String
	 */
	public void setSfquerycyclereview(String sfquerycyclereview){
		this.sfquerycyclereview = sfquerycyclereview;
	}

	/**
	 * 功能：设置接口程序信息属性 SFQueryConditionalFlow
	 * @param sfqueryconditionalflow String
	 */
	public void setSfqueryconditionalflow(String sfqueryconditionalflow){
		this.sfqueryconditionalflow = sfqueryconditionalflow;
	}

	/**
	 * 功能：设置接口程序信息属性 SFQueryGroup
	 * @param sfquerygroup String
	 */
	public void setSfquerygroup(String sfquerygroup){
		this.sfquerygroup = sfquerygroup;
	}

	/**
	 * 功能：设置接口程序信息属性 SFParallelFlow
	 * @param sfparallelflow String
	 */
	public void setSfparallelflow(String sfparallelflow){
		this.sfparallelflow = sfparallelflow;
	}

	/**
	 * 功能：设置接口程序信息属性 SFQueryAssistFlow
	 * @param sfqueryassistflow String
	 */
	public void setSfqueryassistflow(String sfqueryassistflow){
		this.sfqueryassistflow = sfqueryassistflow;
	}

	/**
	 * 功能：设置接口程序信息属性 SFQueryDistribute
	 * @param sfquerydistribute String
	 */
	public void setSfquerydistribute(String sfquerydistribute){
		this.sfquerydistribute = sfquerydistribute;
	}

	/**
	 * 功能：设置接口程序信息属性 SFQueryGoBack
	 * @param sfquerygoback String
	 */
	public void setSfquerygoback(String sfquerygoback){
		this.sfquerygoback = sfquerygoback;
	}

	/**
	 * 功能：设置接口程序信息属性 SFQuerySave
	 * @param sfquerysave String
	 */
	public void setSfquerysave(String sfquerysave){
		this.sfquerysave = sfquerysave;
	}

	/**
	 * 功能：设置接口程序信息属性 SFPostSave
	 * @param sfpostsave String
	 */
	public void setSfpostsave(String sfpostsave){
		this.sfpostsave = sfpostsave;
	}


	/**
	 * 功能：获取接口程序信息属性 接口程序 ID  
	 * @return String
	 */
	public int getApiId() {
		return this.apiId;
	}

	/**
	 * 功能：获取接口程序信息属性 工程名称
	 * @return String
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * 功能：获取接口程序信息属性 过程名称
	 * @return String
	 */
	public String getProcedureName() {
		return this.procedureName;
	}

    public String getEnabled() {
        return this.enabled;
    }

    /**
	 * 功能：获取接口程序信息属性 任务名称
	 * @return String
	 */
	public String getTaskName() {
		return this.taskName;
	}

    public int getTaskTid() {
        return this.taskTid;
    }

    /**
	 * 功能：获取接口程序信息属性 API 控制, bit 0 - SFQueryOpen, bit 1 - SFPostOpen, bit 2 - SFQuerySign, bit 3 - SFPostSign, bit 4 - SFQueryComplete, bit 5 - SFGroupReview, bit 6 - SFQueryCycleReview, bit 7 - SFQueryConditionalFlow, bit 8 - SFQueryGroup, bit 9 - SFParallelFlow, bit 10 - SFQueryAssistFlow, bit 11 - SFQueryDistribute, bit 12 - SFQueryGoBack, bit 13 - SFQuerySave, bit 14 - SFPostSave
	 * @return String
	 */
	public int getApiControl() {
		return this.apiControl;
	}

	/**
	 * 功能：获取接口程序信息属性 SFQueryOpen
	 * @return String
	 */
	public String getSfqueryopen() {
		return this.sfqueryopen;
	}

	/**
	 * 功能：获取接口程序信息属性 SFPostOpen
	 * @return String
	 */
	public String getSfpostopen() {
		return this.sfpostopen;
	}

	/**
	 * 功能：获取接口程序信息属性 SFQuerySign
	 * @return String
	 */
	public String getSfquerysign() {
		return this.sfquerysign;
	}

	/**
	 * 功能：获取接口程序信息属性 SFPostSign
	 * @return String
	 */
	public String getSfpostsign() {
		return this.sfpostsign;
	}

	/**
	 * 功能：获取接口程序信息属性 SFQueryComplete
	 * @return String
	 */
	public String getSfquerycomplete() {
		return this.sfquerycomplete;
	}

	/**
	 * 功能：获取接口程序信息属性 SFGroupReview
	 * @return String
	 */
	public String getSfgroupreview() {
		return this.sfgroupreview;
	}

	/**
	 * 功能：获取接口程序信息属性 SFQueryCycleReview
	 * @return String
	 */
	public String getSfquerycyclereview() {
		return this.sfquerycyclereview;
	}

	/**
	 * 功能：获取接口程序信息属性 SFQueryConditionalFlow
	 * @return String
	 */
	public String getSfqueryconditionalflow() {
		return this.sfqueryconditionalflow;
	}

	/**
	 * 功能：获取接口程序信息属性 SFQueryGroup
	 * @return String
	 */
	public String getSfquerygroup() {
		return this.sfquerygroup;
	}

	/**
	 * 功能：获取接口程序信息属性 SFParallelFlow
	 * @return String
	 */
	public String getSfparallelflow() {
		return this.sfparallelflow;
	}

	/**
	 * 功能：获取接口程序信息属性 SFQueryAssistFlow
	 * @return String
	 */
	public String getSfqueryassistflow() {
		return this.sfqueryassistflow;
	}

	/**
	 * 功能：获取接口程序信息属性 SFQueryDistribute
	 * @return String
	 */
	public String getSfquerydistribute() {
		return this.sfquerydistribute;
	}

	/**
	 * 功能：获取接口程序信息属性 SFQueryGoBack
	 * @return String
	 */
	public String getSfquerygoback() {
		return this.sfquerygoback;
	}

	/**
	 * 功能：获取接口程序信息属性 SFQuerySave
	 * @return String
	 */
	public String getSfquerysave() {
		return this.sfquerysave;
	}

	/**
	 * 功能：获取接口程序信息属性 SFPostSave
	 * @return String
	 */
	public String getSfpostsave() {
		return this.sfpostsave;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getProcedureId() {
		return procedureId;
	}

	public void setProcedureId(int procedureId) {
		this.procedureId = procedureId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

}