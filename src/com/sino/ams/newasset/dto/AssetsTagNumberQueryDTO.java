package com.sino.ams.newasset.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleTime;
import com.sino.base.calen.SimpleDate;
import com.sino.base.exception.TimeException;
import com.sino.base.util.StrUtil;

/**
 * User: srf
 * Date: 2008-3-31
 * Time: 12:05:59
 * Function:
 */
public class AssetsTagNumberQueryDTO extends CommonRecordDTO {
	private String assetsDescription = "";
	private String modelNumber = "";
	private String tagNumber = "";
	private String assignedToName = "";
	private String assetsType = "";
	private int organizationId = -1;
	private String userName = "";
	private String deptName = "";
	private String type = "";
	private String workorderBatch = "";
	private String workorderBatchName = "";
	private String workorderObjectName = "";
	private int userId;
	private String deptCode = "";
	private SimpleCalendar fromDate = null;
	private SimpleCalendar toDate = null;
	private String objectName = "";
	private String responsibilityUser = "";
	private String responsibilityDept = "";

	private String costCenter = "";
	private String areaCode = "";
	private String workorderObjectCode = "";
	private String toTagNumber = "";
	private String costCenterOpt = "";
	private String projectNumber = "";
	private String projectName = "";
	private String barcode = "";
	private String itemName = "";
	private String itemSpec = "";
	private String financeProp = "";
	private String maintainUser = "";


    public String getMaintainUser() {
        return maintainUser;
    }

    public void setMaintainUser(String maintainUser) {
        this.maintainUser = maintainUser;
    }

    public String getFinanceProp() {
        return financeProp;
    }

    public void setFinanceProp(String financeProp) {
        this.financeProp = financeProp;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getResponsibilityUser() {
		return responsibilityUser;
	}

	public void setResponsibilityUser(String responsibilityUser) {
		this.responsibilityUser = responsibilityUser;
	}

	public String getResponsibilityDept() {
		return responsibilityDept;
	}

	public void setResponsibilityDept(String responsibilityDept) {
		this.responsibilityDept = responsibilityDept;
	}

	public AssetsTagNumberQueryDTO() {
		fromDate = new SimpleCalendar();
		toDate = new SimpleCalendar();
	}


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getWorkorderBatch() {
		return workorderBatch;
	}

	public void setWorkorderBatch(String workorderBatch) {
		this.workorderBatch = workorderBatch;
	}

	public String getWorkorderBatchName() {
		return workorderBatchName;
	}

	public void setWorkorderBatchName(String workorderBatchName) {
		this.workorderBatchName = workorderBatchName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getWorkorderObjectName() {
		return workorderObjectName;
	}

	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getAssetsType() {
		return assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

	public void setFromDate(String fromDate) throws CalendarException {
		this.fromDate.setCalendarValue(fromDate);
	}

	public SimpleCalendar getFromDate() throws CalendarException {
		fromDate.setCalPattern(getCalPattern());
		return this.fromDate;
	}

	public void setToDate(String toDate) throws CalendarException {
		this.toDate.setCalendarValue(toDate);
	}

	public SimpleCalendar getToDate() throws CalendarException {
		toDate.setCalPattern(getCalPattern());
		return this.toDate;
	}


	/**
	 * 功能：构造查询条件截至日期的最后一秒所的日历对象。免去应用中每个查询SQL自己构造截至日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getSQLToDate(){
		SimpleCalendar sqlEndCal = new SimpleCalendar();
		if (!StrUtil.isEmpty(toDate.toString())) {
			try {
				SimpleDate date = toDate.getSimpleDate();
				SimpleTime time = SimpleTime.getEndTime();
				sqlEndCal = new SimpleCalendar(date, time);
			} catch (TimeException ex) {
				ex.printLog();
			}
		}
		return sqlEndCal;
	}

	public String getAssignedToName() {
		return assignedToName;
	}

	public void setAssignedToName(String assignedToName) {
		this.assignedToName = assignedToName;
	}

	public String getAssetsDescription() {
		return assetsDescription;
	}

	public void setAssetsDescription(String assetsDescription) {
		this.assetsDescription = assetsDescription;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getWorkorderObjectCode() {
		return workorderObjectCode;
	}

	public void setWorkorderObjectCode(String workorderObjectCode) {
		this.workorderObjectCode = workorderObjectCode;
	}

	public String getToTagNumber() {
		return toTagNumber;
	}

	public String getCostCenterOpt() {
		return costCenterOpt;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setToTagNumber(String toTagNumber) {
		this.toTagNumber = toTagNumber;
	}

	public void setCostCenterOpt(String costCenterOpt) {
		this.costCenterOpt = costCenterOpt;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
