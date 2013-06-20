package com.sino.sinoflow.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 管理员权限信息 SfAdminAuthority</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfAdminAuthorityDTO extends CheckBoxDTO{

	private int adminId = 0;
	private int userId = 0;
	private String projectName = "";
	private String groupName = "";
	private String loginName = "";
	private int orgId = 0;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public SfAdminAuthorityDTO() {
		super();
	}

	/**
	 * 功能：设置管理员权限信息属性 管理员 ID
	 * @param adminId String
	 */
	public void setAdminId(int adminId){
		this.adminId = adminId;
	}

	/**
	 * 功能：设置管理员权限信息属性 管理员的用户 ID
	 * @param userId String
	 */
	public void setUserId(int userId){
		this.userId = userId;
	}

	/**
	 * 功能：设置管理员权限信息属性 工程名称
	 * @param projectName String
	 */
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}

	/**
	 * 功能：设置管理员权限信息属性 组别名称
	 * @param groupName String
	 */
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	/**
	 * 功能：获取管理员权限信息属性 管理员 ID
	 * @return String
	 */
	public int getAdminId() {
		return this.adminId;
	}

	/**
	 * 功能：获取管理员权限信息属性 管理员的用户 ID
	 * @return String
	 */
	public int getUserId() {
		return this.userId;
	}

	/**
	 * 功能：获取管理员权限信息属性 工程名称
	 * @return String
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * 功能：获取管理员权限信息属性 组别名称
	 * @return String
	 */
	public String getGroupName() {
		return this.groupName;
	}

	public int getOrgId() {
		return orgId;
	}

}
