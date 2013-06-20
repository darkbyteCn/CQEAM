package com.sino.ams.net.locus.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.math.AdvancedNumber;

/**
* <p>Title: LOCUS Locus</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class LocusDTO extends CheckBoxDTO{

	private AdvancedNumber workorderObjectNo = null;
	private String workorderObjectCode = "";
	private String workorderObjectName = "";
	private String workorderObjectLocation = "";
	private AdvancedNumber organizationId = null;
	private AdvancedNumber countyCode = null;
	private SimpleCalendar disableDate = null;
	private String remark = "";
	private String objectCategory = "";
	private String category = "";
	private AdvancedNumber isall = null;
	private AdvancedNumber isTempAddr = null;
	private SimpleCalendar creationDate = null;
	private AdvancedNumber createdBy = null;
	private SimpleCalendar lastUpdateDate = null;
	private AdvancedNumber lastUpdateBy = null;
	private AdvancedNumber projectId = null;

	public LocusDTO() {
		super();
		this.disableDate = new SimpleCalendar();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();

		this.workorderObjectNo = new AdvancedNumber();
		this.organizationId = new AdvancedNumber();
		this.countyCode = new AdvancedNumber();

		this.isall = new AdvancedNumber();
		this.isTempAddr = new AdvancedNumber();
		this.createdBy = new AdvancedNumber();
		this.lastUpdateBy = new AdvancedNumber();
		this.projectId = new AdvancedNumber();
	}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
	 * 功能：设置LOCUS属性 WORKORDER_OBJECT_NO
	 * @param workorderObjectNo AdvancedNumber
	 */
	public void setWorkorderObjectNo(AdvancedNumber workorderObjectNo){
		this.workorderObjectNo = workorderObjectNo;
	}

	/**
	 * 功能：设置LOCUS属性 WORKORDER_OBJECT_CODE
	 * @param workorderObjectCode String
	 */
	public void setWorkorderObjectCode(String workorderObjectCode){
		this.workorderObjectCode = workorderObjectCode;
	}

	/**
	 * 功能：设置LOCUS属性 WORKORDER_OBJECT_NAME
	 * @param workorderObjectName String
	 */
	public void setWorkorderObjectName(String workorderObjectName){
		this.workorderObjectName = workorderObjectName;
	}

	/**
	 * 功能：设置LOCUS属性 WORKORDER_OBJECT_LOCATION
	 * @param workorderObjectLocation String
	 */
	public void setWorkorderObjectLocation(String workorderObjectLocation){
		this.workorderObjectLocation = workorderObjectLocation;
	}

	/**
	 * 功能：设置LOCUS属性 ORGANIZATION_ID
	 * @param organizationId AdvancedNumber
	 */
	public void setOrganizationId(AdvancedNumber organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置LOCUS属性 COUNTY_CODE
	 * @param countyCode AdvancedNumber
	 */
	public void setCountyCode(AdvancedNumber countyCode){
		this.countyCode = countyCode;
	}

	/**
	 * 功能：设置LOCUS属性 DISABLE_DATE
	 * @param disableDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDisableDate(String disableDate) throws CalendarException{
		this.disableDate.setCalendarValue(disableDate);
	}

	/**
	 * 功能：设置LOCUS属性 REMARK
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置LOCUS属性 OBJECT_CATEGORY
	 * @param objectCategory AdvancedNumber
	 */
	public void setObjectCategory(String objectCategory){
		this.objectCategory = objectCategory;
	}

	/**
	 * 功能：设置LOCUS属性 ISALL
	 * @param isall AdvancedNumber
	 */
	public void setIsall(AdvancedNumber isall){
		this.isall = isall;
	}

	/**
	 * 功能：设置LOCUS属性 IS_TEMP_ADDR
	 * @param isTempAddr AdvancedNumber
	 */
	public void setIsTempAddr(AdvancedNumber isTempAddr){
		this.isTempAddr = isTempAddr;
	}

	/**
	 * 功能：设置LOCUS属性 CREATION_DATE
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置LOCUS属性 CREATED_BY
	 * @param createdBy AdvancedNumber
	 */
	public void setCreatedBy(AdvancedNumber createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置LOCUS属性 LAST_UPDATE_DATE
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置LOCUS属性 LAST_UPDATE_BY
	 * @param lastUpdateBy AdvancedNumber
	 */
	public void setLastUpdateBy(AdvancedNumber lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}

	/**
	 * 功能：设置LOCUS属性 PROJECT_ID
	 * @param projectId AdvancedNumber
	 */
	public void setProjectId(AdvancedNumber projectId){
		this.projectId = projectId;
	}


	/**
	 * 功能：获取LOCUS属性 WORKORDER_OBJECT_NO
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getWorkorderObjectNo() {
		return this.workorderObjectNo;
	}

	/**
	 * 功能：获取LOCUS属性 WORKORDER_OBJECT_CODE
	 * @return String
	 */
	public String getWorkorderObjectCode() {
		return this.workorderObjectCode;
	}

	/**
	 * 功能：获取LOCUS属性 WORKORDER_OBJECT_NAME
	 * @return String
	 */
	public String getWorkorderObjectName() {
		return this.workorderObjectName;
	}

	/**
	 * 功能：获取LOCUS属性 WORKORDER_OBJECT_LOCATION
	 * @return String
	 */
	public String getWorkorderObjectLocation() {
		return this.workorderObjectLocation;
	}

	/**
	 * 功能：获取LOCUS属性 ORGANIZATION_ID
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取LOCUS属性 COUNTY_CODE
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getCountyCode() {
		return this.countyCode;
	}

	/**
	 * 功能：获取LOCUS属性 DISABLE_DATE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDisableDate() throws CalendarException {
		disableDate.setCalPattern(getCalPattern());
		return this.disableDate;
	}

	/**
	 * 功能：获取LOCUS属性 REMARK
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取LOCUS属性 OBJECT_CATEGORY
	 * @return AdvancedNumber
	 */
	public String getObjectCategory() {
		return this.objectCategory;
	}

	/**
	 * 功能：获取LOCUS属性 ISALL
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getIsall() {
		return this.isall;
	}

	/**
	 * 功能：获取LOCUS属性 IS_TEMP_ADDR
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getIsTempAddr() {
		return this.isTempAddr;
	}

	/**
	 * 功能：获取LOCUS属性 CREATION_DATE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * 功能：获取LOCUS属性 CREATED_BY
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 功能：获取LOCUS属性 LAST_UPDATE_DATE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取LOCUS属性 LAST_UPDATE_BY
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	/**
	 * 功能：获取LOCUS属性 PROJECT_ID
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getProjectId() {
		return this.projectId;
	}

}