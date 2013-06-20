package com.sino.ams.system.user.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: SfUserRight</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfUserRightDTO extends CheckBoxDTO{
	private int groupId ;
	private String roleId = "";
	private int userId ;
	private Timestamp creationDate = null;
	private int createdBy;
	private Timestamp lastUpdateDate = null;
	private int lastUpdateBy;

	private String groupCode = "";
	private String groupName = "";
	private String roleName = "";
	private String groupProp = "";
	private String deptId = "";
	private String deptName = "";
	/**
	 * 功能：设置DTO属性 groupId
	 * @param groupId String
	 */
	public void setGroupId(int groupId){
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
		if(!StrUtil.isEmpty(creationDate)){
			SimpleCalendar cal = new SimpleCalendar(creationDate);
			this.creationDate = cal.getSQLTimestamp();
		}
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
		if(!StrUtil.isEmpty(lastUpdateDate)){
			SimpleCalendar cal = new SimpleCalendar(lastUpdateDate);
			this.lastUpdateDate = cal.getSQLTimestamp();
		}
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

	public void setGroupName(String groupname) {
		this.groupName = groupname;
	}

	public void setRoleName(String rolename) {
		this.roleName = rolename;
	}

	public void setGroupProp(String groupProp) {
		this.groupProp = groupProp;
	}

	/**
	 * 功能：获取DTO属性 groupId
	 * @return String
	 */
	public int getGroupId(){
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
	public Timestamp getCreationDate(){
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
	public Timestamp getLastUpdateDate(){
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

	public String getGroupName() {
		return groupName;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getGroupProp() {
		return groupProp;
	}

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
