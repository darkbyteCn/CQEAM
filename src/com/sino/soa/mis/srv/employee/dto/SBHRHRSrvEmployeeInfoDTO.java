package com.sino.soa.mis.srv.employee.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-9-7
 * Time: 15:48:18
 * To change this template use File | Settings | File Templates.
 */
public class SBHRHRSrvEmployeeInfoDTO extends CheckBoxDTO {

	private String personId = "";
	private SimpleCalendar effectiveStartDate = null;
	private SimpleCalendar effectiveEndDate = null;
	private String employeeNumber = "";
	private String fullName = "";
	private int organizationID = SyBaseSQLUtil.NULL_INT_VALUE;
	private String organization = "";
	private SimpleCalendar lastUpdateDate = null;
	private String businessGroup = "";
	private String isEndbled = "";
	private String deptCode = "";
	private String assetsType = "";
    private String primaryFlag = "";
    private String startLastUpdateDate = "";
    private String endLastUpdateDate = "";
    
    private String companyCode = "";  

	public SBHRHRSrvEmployeeInfoDTO() {
		super();
		this.effectiveStartDate = new SimpleCalendar();
		this.effectiveEndDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();
	}
	/**
	 * @return the effectiveEndDate
	 */
	public SimpleCalendar getEffectiveEndDate()throws CalendarException {
		effectiveEndDate.setCalPattern(getCalPattern());
		return this.effectiveEndDate;
	}
	/**
	 * @param effectiveEndDate the effectiveEndDate to set
	 */
	public void setEffectiveEndDate(String effectiveEndDate)throws CalendarException {
		this.effectiveEndDate.setCalendarValue(effectiveEndDate);
	}
	/**
	 * @return the effectiveStartDate
	 */
	public SimpleCalendar getEffectiveStartDate() throws CalendarException {
		effectiveStartDate.setCalPattern(getCalPattern());
		return this.effectiveStartDate;
	}
	/**
	 * @param effectiveStartDate the effectiveStartDate to set
	 */
	public void setEffectiveStartDate(String effectiveStartDate)throws CalendarException {
		this.effectiveStartDate.setCalendarValue(effectiveStartDate);
	}
	/**
	 * @return the employeeNumber
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	/**
	 * @param employeeNumber the employeeNumber to set
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the personId
	 */
	public String getPersonId() {
		return personId;
	}
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	/**
	 * @return the lastUpdateDate
	 */
	public SimpleCalendar getLastUpdateDate()throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(String lastUpdateDate)throws CalendarException {
		this.lastUpdateDate=new SimpleCalendar(lastUpdateDate);
	}
	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	/**
	 * @return the organizationID
	 */
	public int getOrganizationID() {
		return organizationID;
	}
	/**
	 * @param organizationID the organizationID to set
	 */
	public void setOrganizationID(int organizationID) {
		this.organizationID = organizationID;
	}
	/**
	 * @return the businessGroup
	 */
	public String getBusinessGroup() {
		return businessGroup;
	}
	/**
	 * @param businessGroup the businessGroup to set
	 */
	public void setBusinessGroup(String businessGroup) {
		this.businessGroup = businessGroup;
	}

	/**
	 * @return the isEndbled
	 */
	public String getIsEndbled() {
		return isEndbled;
	}
	/**
	 * @param isEndbled the isEndbled to set
	 */
	public void setIsEndbled(String isEndbled) {
		this.isEndbled = isEndbled;
	}
	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}
	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getAssetsType() {
		return assetsType;
	}
	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

    public String getPrimaryFlag() {
        return primaryFlag;
    }

    public void setPrimaryFlag(String primaryFlag) {
        this.primaryFlag = primaryFlag;
    }

    public String getStartLastUpdateDate() {
        return startLastUpdateDate;
    }

    public void setStartLastUpdateDate(String startLastUpdateDate) {
        this.startLastUpdateDate = startLastUpdateDate;
    }

    public String getEndLastUpdateDate() {
        return endLastUpdateDate;
    }

    public void setEndLastUpdateDate(String endLastUpdateDate) {
        this.endLastUpdateDate = endLastUpdateDate;
    }
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}