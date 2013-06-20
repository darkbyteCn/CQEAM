package com.sino.ams.system.comparison.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 工单对象使用专业，定义一个专业可以对哪几个专业创建工单(EAM) EtsObjectCategory</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsObjectCategoryDTO extends CheckBoxDTO{

	private String systemid="" ;
	private String objectCategory="" ;
	private String searchCategory="" ;
	private int organizationId ;
	private String remark = "";
	private SimpleCalendar creationDate = null;
	private int createdBy ;
	private SimpleCalendar lastUpdateDate = null;
	private int lastUpdateBy ;
    private int company;

    public EtsObjectCategoryDTO() {
		super();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();
	}


	/**
	 * 功能：设置工单对象使用专业，定义一个专业可以对哪几个专业创建工单(EAM)属性 REMARK
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置工单对象使用专业，定义一个专业可以对哪几个专业创建工单(EAM)属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}


	/**
	 * 功能：设置工单对象使用专业，定义一个专业可以对哪几个专业创建工单(EAM)属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}


	/**
	 * 功能：获取工单对象使用专业，定义一个专业可以对哪几个专业创建工单(EAM)属性 REMARK
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取工单对象使用专业，定义一个专业可以对哪几个专业创建工单(EAM)属性 创建日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}


	/**
	 * 功能：获取工单对象使用专业，定义一个专业可以对哪几个专业创建工单(EAM)属性 上次修改日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}


	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}


	public String getObjectCategory() {
		return objectCategory;
	}


	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}


	public String getSearchCategory() {
		return searchCategory;
	}


	public void setSearchCategory(String searchCategory) {
		this.searchCategory = searchCategory;
	}


	public int getOrganizationId() {
		return organizationId;
	}


	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}


	public int getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}


	public int getLastUpdateBy() {
		return lastUpdateBy;
	}


	public void setLastUpdateBy(int lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}


	public int getCompany() {
		return company;
	}


	public void setCompany(int company) {
		this.company = company;
	}

//
//	public void setCreationDate(SimpleCalendar creationDate) {
//		this.creationDate = creationDate;
//	}
//
//
//	public void setLastUpdateDate(SimpleCalendar lastUpdateDate) {
//		this.lastUpdateDate = lastUpdateDate;
//	}

}