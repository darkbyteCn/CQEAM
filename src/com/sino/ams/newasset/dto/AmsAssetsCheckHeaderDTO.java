package com.sino.ams.newasset.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;
import java.util.ArrayList;

public class AmsAssetsCheckHeaderDTO extends AmsAssetsCheckBatchDTO {
	// jeffery<2013-07-20>
	private String isYear = "";

	public String getIsYear() {
		return isYear;
	}

	public void setIsYear(String isYear) {
		this.isYear = isYear;
	}

	private String headerId = "";
	private String transNo = "";
	private SimpleCalendar downloadDate = null;
	private int downloadBy;
	private String downloadUser = "";
	private SimpleCalendar scanoverDate = null;
	private int scanoverBy;
	private String scanoverUser = "";
	private SimpleCalendar uploadDate = null;
	private int uploadBy;
	private String uploadUser = "";
	private SimpleCalendar archivedDate = null;
	private int archivedBy = -1;
	private String archivedUser = "";
	private String differenceReason = "";
	private String orderStatus = "";
	private String statusName = "";
	private String responsibleUser = "";
	private int implementBy = -1;
	private String implementUser = "";
	private int implementDays = 0;
	private SimpleCalendar startTime = null;
	private String objectCode = "";
	private String objectName = "";
	private String objectLocation = "";
	private String checkLocation = "";
	private SimpleCalendar deadlineDate = null;
	private String objectCategory = "";
	private String objectCategoryName = "";
	private String objectCategoryOpt = "";
	private String companyOpt = "";
	private String exportType = "";
	private String maintainCompany = "";
	private String maintainComapnyOpt = "";
	private String costCode = "";
	private String costName = "";
	private String costCenterOpt = "";
	private String deptOpt = "";
	private String groupOpt = "";
	private boolean hasPreviousOrder = false;
	private String tagNumber = "";
	private String assetsDescription = "";
	private String deptCode = "";
	private String responsibilityUser = "";
	private String averageChkTime = "";
	private String disabled = "";
	private String fromBarcode = "";
	private String toBarcode = "";
	private String threshold = "0";
	private String deptCategoryValues = "";
	private ArrayList deptCategoryCodes = new ArrayList(0);
	private String newLocation = "";
	private String checkTpye = "";
	private String qryBarcode = "";

	public String getQryBarcode() {
		return this.qryBarcode;
	}

	public void setQryBarcode(String qryBarcode) {
		this.qryBarcode = qryBarcode;
	}

	public String getCheckTpye() {
		return this.checkTpye;
	}

	public void setCheckTpye(String checkTpye) {
		this.checkTpye = checkTpye;
	}

	public String getDeptCategoryValues() {
		return this.deptCategoryValues;
	}

	public void setDeptCategoryValues(String deptCategoryValues) {
		this.deptCategoryValues = deptCategoryValues;
	}

	public ArrayList getDeptCategoryCodes() {
		return this.deptCategoryCodes;
	}

	public void setDeptCategoryCodes(ArrayList deptCategoryCodes) {
		this.deptCategoryCodes = deptCategoryCodes;
	}

	public String getFromBarcode() {
		return this.fromBarcode;
	}

	public void setFromBarcode(String fromBarcode) {
		this.fromBarcode = fromBarcode;
	}

	public String getToBarcode() {
		return this.toBarcode;
	}

	public void setToBarcode(String toBarcode) {
		this.toBarcode = toBarcode;
	}

	public AmsAssetsCheckHeaderDTO() {
		this.downloadDate = new SimpleCalendar();
		this.scanoverDate = new SimpleCalendar();
		this.uploadDate = new SimpleCalendar();
		this.archivedDate = new SimpleCalendar();
		this.startTime = new SimpleCalendar();
		this.deadlineDate = new SimpleCalendar();
	}

	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public void setDownloadDate(String downloadDate) throws CalendarException {
		this.downloadDate.setCalendarValue(downloadDate);
	}

	public void setDeadlineDate(String deadlineDate) throws CalendarException {
		this.deadlineDate.setCalendarValue(deadlineDate);
	}

	public void setDownloadBy(int downloadBy) {
		this.downloadBy = downloadBy;
	}

	public void setScanoverDate(String scanoverDate) throws CalendarException {
		this.scanoverDate.setCalendarValue(scanoverDate);
	}

	public void setScanoverBy(int scanoverBy) {
		this.scanoverBy = scanoverBy;
	}

	public void setUploadDate(String uploadDate) throws CalendarException {
		this.uploadDate.setCalendarValue(uploadDate);
	}

	public void setUploadBy(int uploadBy) {
		this.uploadBy = uploadBy;
	}

	public void setArchivedDate(String archivedDate) throws CalendarException {
		this.archivedDate.setCalendarValue(archivedDate);
	}

	public void setArchivedBy(int archivedBy) {
		this.archivedBy = archivedBy;
	}

	public void setDifferenceReason(String differenceReason) {
		this.differenceReason = differenceReason;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setResponsibleUser(String responsibleUser) {
		this.responsibleUser = responsibleUser;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public void setObjectLocation(String objectLocation) {
		this.objectLocation = objectLocation;
	}

	public void setImplementBy(int implementBy) {
		this.implementBy = implementBy;
	}

	public void setImplementUser(String implementUser) {
		this.implementUser = implementUser;
	}

	public void setImplementDays(int implementDays) {
		this.implementDays = implementDays;
	}

	public void setCheckLocation(String checkLocation) {
		this.checkLocation = checkLocation;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setArchivedUser(String archivedUser) {
		this.archivedUser = archivedUser;
	}

	public void setDownloadUser(String downloadUser) {
		this.downloadUser = downloadUser;
	}

	public void setScanoverUser(String scanoverUser) {
		this.scanoverUser = scanoverUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	public void setObjectCategoryOpt(String objectCategoryOpt) {
		this.objectCategoryOpt = objectCategoryOpt;
	}

	public void setCompanyOpt(String companyOpt) {
		this.companyOpt = companyOpt;
	}

	public void setStartTime(String startTime) throws CalendarException {
		this.startTime.setCalendarValue(startTime);
	}

	public String getHeaderId() {
		return this.headerId;
	}

	public String getTransNo() {
		return this.transNo;
	}

	public SimpleCalendar getDownloadDate() throws CalendarException {
		this.downloadDate.setCalPattern(getCalPattern());
		return this.downloadDate;
	}

	public SimpleCalendar getDeadlineDate() throws CalendarException {
		this.deadlineDate.setCalPattern(getCalPattern());
		return this.deadlineDate;
	}

	public int getDownloadBy() {
		return this.downloadBy;
	}

	public SimpleCalendar getScanoverDate() throws CalendarException {
		this.scanoverDate.setCalPattern(getCalPattern());
		return this.scanoverDate;
	}

	public int getScanoverBy() {
		return this.scanoverBy;
	}

	public SimpleCalendar getUploadDate() throws CalendarException {
		this.uploadDate.setCalPattern(getCalPattern());
		return this.uploadDate;
	}

	public int getUploadBy() {
		return this.uploadBy;
	}

	public SimpleCalendar getArchivedDate() throws CalendarException {
		this.archivedDate.setCalPattern(getCalPattern());
		return this.archivedDate;
	}

	public int getArchivedBy() {
		return this.archivedBy;
	}

	public String getDifferenceReason() {
		return this.differenceReason;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public String getResponsibleUser() {
		return this.responsibleUser;
	}

	public String getObjectCode() {
		return this.objectCode;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public String getObjectLocation() {
		return this.objectLocation;
	}

	public int getImplementBy() {
		return this.implementBy;
	}

	public String getImplementUser() {
		return this.implementUser;
	}

	public int getImplementDays() {
		return this.implementDays;
	}

	public SimpleCalendar getStartTime() throws CalendarException {
		this.startTime.setCalPattern(getCalPattern());
		return this.startTime;
	}

	public String getCheckLocation() {
		return this.checkLocation;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public String getArchivedUser() {
		return this.archivedUser;
	}

	public String getDownloadUser() {
		return this.downloadUser;
	}

	public String getScanoverUser() {
		return this.scanoverUser;
	}

	public String getUploadUser() {
		return this.uploadUser;
	}

	public String getObjectCategory() {
		return this.objectCategory;
	}

	public String getObjectCategoryOpt() {
		return this.objectCategoryOpt;
	}

	public String getCompanyOpt() {
		return this.companyOpt;
	}

	public String getObjectCategoryName() {
		return this.objectCategoryName;
	}

	public String getExportType() {
		return this.exportType;
	}

	public String getMaintainCompany() {
		return this.maintainCompany;
	}

	public String getMaintainComapnyOpt() {
		return this.maintainComapnyOpt;
	}

	public String getGroupOpt() {
		return this.groupOpt;
	}

	public String getCostCode() {
		return this.costCode;
	}

	public String getCostCenterOpt() {
		return this.costCenterOpt;
	}

	public String getDeptOpt() {
		return this.deptOpt;
	}

	public String getCostName() {
		return this.costName;
	}

	public String getTagNumber() {
		return this.tagNumber;
	}

	public String getDeptCode() {
		return this.deptCode;
	}

	public String getAssetsDescription() {
		return this.assetsDescription;
	}

	public String getResponsibilityUser() {
		return this.responsibilityUser;
	}

	public String getAverageChkTime() {
		return this.averageChkTime;
	}

	public String getDisabled() {
		return this.disabled;
	}

	public boolean hasPreviousOrder() {
		return this.hasPreviousOrder;
	}

	public void setObjectCategoryName(String objectCategoryName) {
		this.objectCategoryName = objectCategoryName;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public void setMaintainCompany(String maintainCompany) {
		this.maintainCompany = maintainCompany;
	}

	public void setMaintainComapnyOpt(String maintainComapnyOpt) {
		this.maintainComapnyOpt = maintainComapnyOpt;
	}

	public void setGroupOpt(String groupOpt) {
		this.groupOpt = groupOpt;
	}

	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}

	public void setCostCenterOpt(String costCenterOpt) {
		this.costCenterOpt = costCenterOpt;
	}

	public void setDeptOpt(String deptOpt) {
		this.deptOpt = deptOpt;
	}

	public void setCostName(String costName) {
		this.costName = costName;
	}

	public void setHasPreviousOrder(boolean hasPreviousOrder) {
		this.hasPreviousOrder = hasPreviousOrder;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setAssetsDescription(String assetsDescription) {
		this.assetsDescription = assetsDescription;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public void setResponsibilityUser(String responsibilityUser) {
		this.responsibilityUser = responsibilityUser;
	}

	public void setAverageChkTime(String averageChkTime) {
		this.averageChkTime = averageChkTime;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getThreshold() {
		return this.threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getNewLocation() {
		return this.newLocation;
	}

	public void setNewLocation(String newLocation) {
		this.newLocation = newLocation;
	}
}