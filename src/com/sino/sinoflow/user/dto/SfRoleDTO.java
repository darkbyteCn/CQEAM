package com.sino.sinoflow.user.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: SfRole</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfRoleDTO extends CheckBoxDTO{
	
	private int roleId = 0;
	private String roleName = "";
	private String description = "";
	private int sortno = 0;
	private String isInner = "";
	private int createdBy = -1;
	private int lastUpdateBy = -1;
	private String projectName;
	private String roleDesc = "";
	private String enabled = "";
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * 功能：设置DTO属性 roleId
	 * @param roleId String
	 */
	public void setRoleId(int roleId){
		this.roleId = roleId;
	}

	/**
	 * 功能：设置DTO属性 rolename
	 * @param roleName String
	 */
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	/**
	 * 功能：设置DTO属性 description
	 * @param description String
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * 功能：设置DTO属性 sortno
	 * @param sortno String
	 */
	public void setSortno(int sortno){
		this.sortno = sortno;
	}

	/**
	 * 功能：设置DTO属性 isInner
	 * @param isInner String
	 */
	public void setIsInner(String isInner){
		this.isInner = isInner;
	}


	/**
	 * 功能：设置DTO属性 createdBy
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}


	/**
	 * 功能：设置DTO属性 lastUpdateBy
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}

	/**
	 * 功能：获取DTO属性 roleId
	 * @return String
	 */
	public int getRoleId(){
		return this.roleId;
	}

	/**
	 * 功能：获取DTO属性 rolename
	 * @return String
	 */
	public String getRoleName(){
		return this.roleName;
	}

	/**
	 * 功能：获取DTO属性 description
	 * @return String
	 */
	public String getDescription(){
		return this.description;
	}

	/**
	 * 功能：获取DTO属性 sortno
	 * @return String
	 */
	public int getSortno(){
		return this.sortno;
	}

	/**
	 * 功能：获取DTO属性 isInner
	 * @return String
	 */
	public String getIsInner(){
		return this.isInner;
	}

	/**
	 * 功能：获取DTO属性 createdBy
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}


	/**
	 * 功能：获取DTO属性 lastUpdateBy
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}