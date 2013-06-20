package com.sino.sinoflow.user.dto;

import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: SfUserRight</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.""
*/

public class SfUserRightDTO extends CheckBoxDTO{
	private String groupId = "";
	private String roleId = "";
	private int userId = 0;
	private String creationDate = "";
	private int createdBy = -1;
	private String lastUpdateDate = "";
	private int lastUpdateBy = -1;

	private String groupCode = "";
	private String groupname = "";
	private String rolename = "";
	/**
	 * 功能：设置DTO属性 groupId
	 * @param groupId String
	 */
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}

	/**
	 * 功能：设置DTO属性 roleId
	 * @param roleId String
	 */
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}

	/**
	 * 功能：设置DTO属性 userId
	 * @param userId String
	 */
	public void setUserId(int userId){
		this.userId = userId;
	}

	/**
	 * 功能：设置DTO属性 creationDate
	 * @param creationDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
/*
        if(!StrUtil.isEmpty(creationDate)){
			SimpleCalendar cal = new SimpleCalendar(creationDate);
			this.creationDate = cal.getSQLTimestamp();
		}
*/
        this.creationDate = creationDate;
    }

	/**
	 * 功能：设置DTO属性 createdBy
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置DTO属性 lastUpdateDate
	 * @param lastUpdateDate Timestamp
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
/*
        if(!StrUtil.isEmpty(lastUpdateDate)){
			SimpleCalendar cal = new SimpleCalendar(lastUpdateDate);
			this.lastUpdateDate = cal.getSQLTimestamp();
		}
*/
        this.lastUpdateDate = lastUpdateDate;
    }

	/**
	 * 功能：设置DTO属性 lastUpdateBy
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	/**
	 * 功能：获取DTO属性 groupId
	 * @return String
	 */
	public String getGroupId(){
		return this.groupId;
	}

	/**
	 * 功能：获取DTO属性 roleId
	 * @return String
	 */
	public String getRoleId(){
		return this.roleId;
	}

	/**
	 * 功能：获取DTO属性 userId
	 * @return String
	 */
	public int getUserId(){
		return this.userId;
	}

	/**
	 * 功能：获取DTO属性 creationDate
	 * @return Timestamp
	 */
	public String getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取DTO属性 createdBy
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取DTO属性 lastUpdateDate
	 * @return Timestamp
	 */
	public String getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取DTO属性 lastUpdateBy
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public String getGroupname() {
		return groupname;
	}

	public String getRolename() {
		return rolename;
	}

}
