package com.sino.ams.system.procedure.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 流转过程定义 SfProcedureDef</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfProcedureDefDTO extends CheckBoxDTO{

	private int procId ;
	private String procName = "";
	private String description = "";
	private String appTableName = "";
	private String appPkName = "";
	private int organizationId;
	private String disabled = "";
	private String approvePath = "";
	private String editPath = "";
	private String queryPath = "";
	private String cancelPath = "";


	/**
	 * 功能：设置流转过程定义属性 流程序列号
	 * @param procId String
	 */
	public void setProcId(int procId){
		this.procId = procId;
	}

	/**
	 * 功能：设置流转过程定义属性 流程名称
	 * @param procName String
	 */
	public void setProcName(String procName){
		this.procName = procName;
	}

	/**
	 * 功能：设置流转过程定义属性 流程描述
	 * @param description String
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * 功能：设置流转过程定义属性 应用表名称
	 * @param appTableName String
	 */
	public void setAppTableName(String appTableName){
		this.appTableName = appTableName;
	}

	/**
	 * 功能：设置流转过程定义属性 应用表主键
	 * @param appPkName String
	 */
	public void setAppPkName(String appPkName){
		this.appPkName = appPkName;
	}

	/**
	 * 功能：设置流转过程定义属性 OU组织编号
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置流转过程定义属性 是否失效，默认为否'N'
	 * @param disabled String
	 */
	public void setDisabled(String disabled){
		this.disabled = disabled;
	}

	/**
	 * 功能：设置流转过程定义属性 审批应用的路径
	 * @param approvePath String
	 */
	public void setApprovePath(String approvePath){
		this.approvePath = approvePath;
	}

	/**
	 * 功能：设置流转过程定义属性 编辑应用的路径
	 * @param editPath String
	 */
	public void setEditPath(String editPath){
		this.editPath = editPath;
	}

	/**
	 * 功能：设置流转过程定义属性 查询应用路径
	 * @param queryPath String
	 */
	public void setQueryPath(String queryPath){
		this.queryPath = queryPath;
	}

	/**
	 * 功能：设置流转过程定义属性 取消应用路径
	 * @param cancelPath String
	 */
	public void setCancelPath(String cancelPath){
		this.cancelPath = cancelPath;
	}


	/**
	 * 功能：获取流转过程定义属性 流程序列号
	 * @return String
	 */
	public int getProcId(){
		return this.procId;
	}

	/**
	 * 功能：获取流转过程定义属性 流程名称
	 * @return String
	 */
	public String getProcName(){
		return this.procName;
	}

	/**
	 * 功能：获取流转过程定义属性 流程描述
	 * @return String
	 */
	public String getDescription(){
		return this.description;
	}

	/**
	 * 功能：获取流转过程定义属性 应用表名称
	 * @return String
	 */
	public String getAppTableName(){
		return this.appTableName;
	}

	/**
	 * 功能：获取流转过程定义属性 应用表主键
	 * @return String
	 */
	public String getAppPkName(){
		return this.appPkName;
	}

	/**
	 * 功能：获取流转过程定义属性 OU组织编号
	 * @return String
	 */
	public int getOrganizationId(){
		return this.organizationId;
	}

	/**
	 * 功能：获取流转过程定义属性 是否失效，默认为否'N'
	 * @return String
	 */
	public String getDisabled(){
		return this.disabled;
	}

	/**
	 * 功能：获取流转过程定义属性 审批应用的路径
	 * @return String
	 */
	public String getApprovePath(){
		return this.approvePath;
	}

	/**
	 * 功能：获取流转过程定义属性 编辑应用的路径
	 * @return String
	 */
	public String getEditPath(){
		return this.editPath;
	}

	/**
	 * 功能：获取流转过程定义属性 查询应用路径
	 * @return String
	 */
	public String getQueryPath(){
		return this.queryPath;
	}

	/**
	 * 功能：获取流转过程定义属性 取消应用路径
	 * @return String
	 */
	public String getCancelPath(){
		return this.cancelPath;
	}

}