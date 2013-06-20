package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 组别属性 SfGroup</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfGroupDTO extends CheckBoxDTO{

	private String projectName = "";
	private int groupId = 0;
	private String groupName = "";
	private int parentId = 0;
	private String groupDesc = "";
	private String enabled = "";

	public SfGroupDTO() {
		super();

	}

	/**
	 * 功能：设置组别属性属性 工程名称
	 * @param projectName String
	 */
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}

	/**
	 * 功能：设置组别属性属性 组别 ID
	 * @param groupId String
	 */
	public void setGroupId(int groupId){
		this.groupId = groupId;
	}

	/**
	 * 功能：设置组别属性属性 组别名称
	 * @param groupName String
	 */
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	/**
	 * 功能：设置组别属性属性 父组别 ID
	 * @param parentId String
	 */
	public void setParentId(int parentId){
		this.parentId = parentId;
	}

	/**
	 * 功能：设置组别属性属性 描述
	 * @param groupDesc String
	 */
	public void setGroupDesc(String groupDesc){
		this.groupDesc = groupDesc;
	}

	/**
	 * 功能：设置组别属性属性 ENABLED
	 * @param enabled String
	 */
	public void setEnabled(String enabled){
		this.enabled = enabled;
	}


	/**
	 * 功能：获取组别属性属性 工程名称
	 * @return String
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * 功能：获取组别属性属性 组别 ID
	 * @return String
	 */
	public int getGroupId() {
		return this.groupId;
	}

	/**
	 * 功能：获取组别属性属性 组别名称
	 * @return String
	 */
	public String getGroupName() {
		return this.groupName;
	}

	/**
	 * 功能：获取组别属性属性 父组别 ID
	 * @return String
	 */
	public int getParentId() {
		return this.parentId;
	}

	/**
	 * 功能：获取组别属性属性 描述
	 * @return String
	 */
	public String getGroupDesc() {
		return this.groupDesc;
	}

	/**
	 * 功能：获取组别属性属性 ENABLED
	 * @return String
	 */
	public String getEnabled() {
		return this.enabled;
	}

}