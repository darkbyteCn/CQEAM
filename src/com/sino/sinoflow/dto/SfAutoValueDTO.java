package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 自动赋值信息 SfAutoValue</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfAutoValueDTO extends CheckBoxDTO{

	private int autoValueId = 0;
	private String projectName = "";
	private String procedureName = "";
	private String taskName = "";
	private String fieldName = "";
	private String fieldDesc = "";
	private int assignOn = 0;
	private int assignType = 0;
	private int assignValue = 0;
	private String meno = "";
	private int projectId = 0;
	private int procedureId = 0;
	private int taskId= 0;
    private int taskTid = 0;

    /* 何时赋值为方便页面上显示 */
	private boolean qs = false;
	private boolean wc = false;
	private boolean zc = false;
	private boolean ts = false;
	private boolean th = false;

	public SfAutoValueDTO() {
		super();
	}

	/**
	 * 功能：设置自动赋值信息属性 AUTO_VALUE_ID
	 * @param autoValueId String
	 */
	public void setAutoValueId(int autoValueId){
		this.autoValueId = autoValueId;
	}

	/**
	 * 功能：设置自动赋值信息属性 工程名称
	 * @param projectName String
	 */
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}

	/**
	 * 功能：设置自动赋值信息属性 过程名称
	 * @param procedureName String
	 */
	public void setProcedureName(String procedureName){
		this.procedureName = procedureName;
	}

	/**
	 * 功能：设置自动赋值信息属性 任务名称
	 * @param taskName String
	 */
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

    /**
     * 功能：设置自动赋值信息属性 任务名称
     * @param taskTid String
     */
    public void setTaskTid(int taskTid){
        this.taskTid = taskTid;
    }

    /**
	 * 功能：设置自动赋值信息属性 域名
	 * @param fieldName String
	 */
	public void setFieldName(String fieldName){
		this.fieldName = fieldName;
	}

	/**
	 * 功能：设置自动赋值信息属性 域描述
	 * @param fieldDesc String
	 */
	public void setFieldDesc(String fieldDesc){
		this.fieldDesc = fieldDesc;
	}

	/**
	 * 功能：设置自动赋值信息属性 何时赋值
	 * @param assignOn String
	 */
	public void setAssignOn(int assignOn){
		this.assignOn = assignOn;
	}

	/**
	 * 功能：设置自动赋值信息属性 赋值方式
	 * @param assignType String
	 */
	public void setAssignType(int assignType){
		this.assignType = assignType;
	}

	/**
	 * 功能：设置自动赋值信息属性 赋值内容
	 * @param assignValue String
	 */
	public void setAssignValue(int assignValue){
		this.assignValue = assignValue;
	}

	/**
	 * 功能：设置自动赋值信息属性 备注
	 * @param meno String
	 */
	public void setMeno(String meno){
		this.meno = meno;
	}


	/**
	 * 功能：获取自动赋值信息属性 AUTO_VALUE_ID
	 * @return String
	 */
	public int getAutoValueId() {
		return this.autoValueId;
	}

	/**
	 * 功能：获取自动赋值信息属性 工程名称
	 * @return String
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * 功能：获取自动赋值信息属性 过程名称
	 * @return String
	 */
	public String getProcedureName() {
		return this.procedureName;
	}

	/**
	 * 功能：获取自动赋值信息属性 任务名称
	 * @return String
	 */
	public String getTaskName() {
		return this.taskName;
	}

    /**
     * 功能：获取自动赋值信息属性 任务名称
     * @return String
     */
    public int getTaskTid() {
        return this.taskTid;
    }

    /**
	 * 功能：获取自动赋值信息属性 域名
	 * @return String
	 */
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * 功能：获取自动赋值信息属性 域描述
	 * @return String
	 */
	public String getFieldDesc() {
		return this.fieldDesc;
	}

	/**
	 * 功能：获取自动赋值信息属性 何时赋值
	 * @return String
	 */
	public int getAssignOn() {
		return this.assignOn;
	}

	/**
	 * 功能：获取自动赋值信息属性 赋值方式
	 * @return String
	 */
	public int getAssignType() {
		return this.assignType;
	}

	/**
	 * 功能：获取自动赋值信息属性 赋值内容
	 * @return String
	 */
	public int getAssignValue() {
		return this.assignValue;
	}

	/**
	 * 功能：获取自动赋值信息属性 备注
	 * @return String
	 */
	public String getMeno() {
		return this.meno;
	}

	public boolean isQs() {
		return qs;
	}

	public void setQs(boolean qs) {
		this.qs = qs;
	}

	public boolean isWc() {
		return wc;
	}

	public void setWc(boolean wc) {
		this.wc = wc;
	}

	public boolean isZc() {
		return zc;
	}

	public void setZc(boolean zc) {
		this.zc = zc;
	}

	public boolean isTs() {
		return ts;
	}

	public void setTs(boolean ts) {
		this.ts = ts;
	}

	public boolean isTh() {
		return th;
	}

	public void setTh(boolean th) {
		this.th = th;
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