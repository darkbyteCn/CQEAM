package com.sino.ams.system.resource.dto;

import java.sql.Timestamp;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
* <p>Title: SfResPrivs</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class SfResPrivsDTO extends CheckBoxDTO{
	private String roleId = "";
	private int systemId ;
	private Timestamp creationDate = null;
	private int createdBy = -1;
	private Timestamp lastUpdateDate = null;
	private int lastUpdateBy = -1;
	private String roleName = "";
	private String resName = "";

	/**
	 * 功能：设置DTO属性 roleId
	 * @param roleId String
	 */
	public void setRoleId(String roleId){
		this.roleId = roleId;
	}

	/**
	 * 功能：设置DTO属性 resId
	 * @param resId String
	 */
	public void setSystemId(int resId){
		this.systemId = resId;
	}

    public void setResId(int resId) {
        this.systemId = resId;
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

	public void setResName(String resName) {
		this.resName = resName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 功能：获取DTO属性 roleId
	 * @return String
	 */
	public String getRoleId(){
		return this.roleId;
	}

	/**
	 * 功能：获取DTO属性 resId
	 * @return String
	 */
	public int getSystemId(){
		return this.systemId;
	}

    public int getResId() {
        return this.systemId;
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

	public String getResName() {
		return resName;
	}

	public String getRoleName() {
		return roleName;
	}
}
