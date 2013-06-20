package com.sino.sinoflow.framework.security.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
* <p>Title: 用戶职位权限信息 SfUserAuthority</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfUserAuthorityDTO extends CheckBoxDTO{

	private int authorityId = 0;
	private int userId = 0;
	private String department = "";
	private String position = "";
	private String groupName = "";
	private String roleName = "";

	public SfUserAuthorityDTO() {
		super();
	}

	/**
	 * 功能：设置用戶职位权限信息属性 用戶权限 ID
	 * @param authorityId AdvancedNumber
	 */
	public void setAuthorityId(int authorityId){
		this.authorityId = authorityId;
	}

	/**
	 * 功能：设置用戶职位权限信息属性 用戶 ID
	 * @param userId AdvancedNumber
	 */
	public void setUserId(int userId){
		this.userId = userId;
	}

	/**
	 * 功能：设置用戶职位权限信息属性 部门
	 * @param department String
	 */
	public void setDepartment(String department){
		this.department = department;
	}

	/**
	 * 功能：设置用戶职位权限信息属性 职位
	 * @param position String
	 */
	public void setPosition(String position){
		this.position = position;
	}

	/**
	 * 功能：设置用戶职位权限信息属性 组别
	 * @param groupName String
	 */
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	/**
	 * 功能：设置用戶职位权限信息属性 角色
	 * @param roleName String
	 */
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}


	/**
	 * 功能：获取用戶职位权限信息属性 用戶权限 ID
	 * @return AdvancedNumber
	 */
	public int getAuthorityId() {
		return this.authorityId;
	}

	/**
	 * 功能：获取用戶职位权限信息属性 用戶 ID
	 * @return AdvancedNumber
	 */
	public int getUserId() {
		return this.userId;
	}

	/**
	 * 功能：获取用戶职位权限信息属性 部门
	 * @return String
	 */
	public String getDepartment() {
		return this.department;
	}

	/**
	 * 功能：获取用戶职位权限信息属性 职位
	 * @return String
	 */
	public String getPosition() {
		return this.position;
	}

	/**
	 * 功能：获取用戶职位权限信息属性 组别
	 * @return String
	 */
	public String getGroupName() {
		return this.groupName;
	}

	/**
	 * 功能：获取用戶职位权限信息属性 角色
	 * @return String
	 */
	public String getRoleName() {
		return this.roleName;
	}

}
