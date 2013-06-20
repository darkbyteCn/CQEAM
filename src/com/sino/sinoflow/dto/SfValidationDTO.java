package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 合法性检查信息 SfValidation</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfValidationDTO extends CheckBoxDTO{

	private int validateId = 0;
	private int projectId = 0;
	private int procedureId = 0;
	private int taskId = 0;
	private String projectName = "";
	private String procedureName = "";
	private String taskName = "";
	private String fieldName = "";
	private String fieldDesc = "";
	private int validationType = 0;
	private int sizeType = 0;
	private int checkSize = 0;
	private String matchPattern = "";
	private String memo = "";
	private boolean empty = false;
    private int taskTid = 0;

    public boolean getEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public SfValidationDTO() {
		super();
	}

	/**
	 * 功能：设置合法性检查信息属性 合法性检查 ID
	 * @param validateId String
	 */
	public void setValidateId(int validateId){
		this.validateId = validateId;
	}

	/**
	 * 功能：设置合法性检查信息属性 工程名称
	 * @param projectName String
	 */
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}

	/**
	 * 功能：设置合法性检查信息属性 过程名称
	 * @param procedureName String
	 */
	public void setProcedureName(String procedureName){
		this.procedureName = procedureName;
	}

	/**
	 * 功能：设置合法性检查信息属性 任务名称
	 * @param taskName String
	 */
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

    public void setTaskTid(int taskTid) {
        this.taskTid = taskTid;
    }

    /**
	 * 功能：设置合法性检查信息属性 域名称
	 * @param fieldName String
	 */
	public void setFieldName(String fieldName){
		this.fieldName = fieldName;
	}

	/**
	 * 功能：设置合法性检查信息属性 域描述
	 * @param fieldDesc String
	 */
	public void setFieldDesc(String fieldDesc){
		this.fieldDesc = fieldDesc;
	}

	/**
	 * 功能：设置合法性检查信息属性 检查方式, BIT 0 - NOT NULL, BIT 1 - PATTERN MATCH, BIT 2 - SIZE CHECK
	 * @param validationType String
	 */
	public void setValidationType(int validationType){
		this.validationType = validationType;
	}

	/**
	 * 功能：设置合法性检查信息属性 长度控制方式, 1:EQ 2:LT 3:GT
	 * @param sizeType String
	 */
	public void setSizeType(int sizeType){
		this.sizeType = sizeType;
	}


	/**
	 * 功能：设置合法性检查信息属性 匹配样式, 可以用通配符 “*”, “?”,如”2008-*-???”
	 * @param matchPattern String
	 */
	public void setMatchPattern(String matchPattern){
		this.matchPattern = matchPattern;
	}

	/**
	 * 功能：设置合法性检查信息属性 备注
	 * @param memo String
	 */
	public void setMemo(String memo){
		this.memo = memo;
	}


	/**
	 * 功能：获取合法性检查信息属性 合法性检查 ID
	 * @return String
	 */
	public int getValidateId() {
		return this.validateId;
	}

	/**
	 * 功能：获取合法性检查信息属性 工程名称
	 * @return String
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * 功能：获取合法性检查信息属性 过程名称
	 * @return String
	 */
	public String getProcedureName() {
		return this.procedureName;
	}

	/**
	 * 功能：获取合法性检查信息属性 任务名称
	 * @return String
	 */
	public String getTaskName() {
		return this.taskName;
	}

    public int getTaskTid() {
        return this.taskTid;
    }

    /**
	 * 功能：获取合法性检查信息属性 域名称
	 * @return String
	 */
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * 功能：获取合法性检查信息属性 域描述
	 * @return String
	 */
	public String getFieldDesc() {
		return this.fieldDesc;
	}

	/**
	 * 功能：获取合法性检查信息属性 检查方式, BIT 0 - NOT NULL, BIT 1 - PATTERN MATCH, BIT 2 - SIZE CHECK
	 * @return String
	 */
	public int getValidationType() {
		return this.validationType;
	}

	/**
	 * 功能：获取合法性检查信息属性 长度控制方式, 1:EQ 2:LT 3:GT
	 * @return String
	 */
	public int getSizeType() {
		return this.sizeType;
	}


	/**
	 * 功能：获取合法性检查信息属性 匹配样式, 可以用通配符 “*”, “?”,如”2008-*-???”
	 * @return String
	 */
	public String getMatchPattern() {
		return this.matchPattern;
	}

	/**
	 * 功能：获取合法性检查信息属性 备注
	 * @return String
	 */
	public String getMemo() {
		return this.memo;
	}

	public int getCheckSize() {
		return checkSize;
	}

	public void setCheckSize(int checkSize) {
		this.checkSize = checkSize;
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