package com.sino.ams.newasset.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

public class AmsAssetsTransLineDTO extends AmsAssetsAddressVDTO
{
  private String transId = "";
  private String transNo = "";
  private String lineId;
  private String assignedToLocation = "";
  private String assignedToLocationName = "";
  private SimpleCalendar assignedDate = null;
  private SimpleCalendar confirmDate = null;
  private String transType = "";
  private String transTypeValue = "";
  private String hrDeptId = "";
  private String hrDeptOption = "";
  private String oldAddressId = "";
  private String oldLocation = "";
  private String oldLocationCode = "";
  private String oldLocationName = "";
  private String oldResponsibilityUser = "";
  private String oldResponsibilityDept = "";
  private String oldResponsibilityUserName = "";
  private String oldResponsibilityDeptName = "";
  private int toOrganizationId;
  private String lineStatus = "";
  private int receivedUser;
  private SimpleCalendar receivedDate = null;
  private String oldDepreciationAccount = "";
  private String oldFaCategoryCode = "";
  private String oldFaCategoryName = "";
  private SimpleCalendar lineTransDate = null;
  private String faContentCode = "";
  private String faContentName = "";

  private String lineReason = "";
  private String softInuseVersion = "";
  private String softDevalueVersion = "";
  private String sumDepreciation = "";
  private String netValue = "";
  private float prepareDevalue;
  private String userName = "";
  private String barcode = "";
  private String responsibilityUser = "";
  private String newBarcode = "";
  private float impairReserve;
  private float deprnCost;
  private String manufacturerName = "";

  private String recycleValue = "";
  private String assetsStatusDes = "";
  private int assetsStatus;
  private float retirementCost;
  private String assetsType = "";
  private String rejectType = "";
  private String rejectTypeName = "";
  private String rejectTypeOpt = "";
  private String deprnLeftMonth = "";

  private String confirmYN = "";
  private String transTypeDefine = "";
  private String fromObjectNo = "";
  private String toObjectNo = "";
  private String fromObjectName = "";
  private String toObjectName = "";
  private String remarkTransNo = "";
  private String errorMsg = "";

  private String importantFlag = "";

  private String remark = "";

  private String excelLineId = "";

  public String getErrorMsg()
  {
    return this.errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public String getTransTypeDefine() {
    return this.transTypeDefine;
  }

  public void setTransTypeDefine(String transTypeDefine) {
    this.transTypeDefine = transTypeDefine;
  }

  public String getConfirmYN() {
    return this.confirmYN;
  }

  public String getExcelLineId()
  {
    return this.excelLineId;
  }

  public void setExcelLineId(String excelLineId) {
    this.excelLineId = excelLineId;
  }

  public void setConfirmYN(String confirmYN) {
    this.confirmYN = confirmYN;
  }

  public String getDeprnLeftMonth() {
    return this.deprnLeftMonth;
  }

  public void setDeprnLeftMonth(String deprnLeftMonth) {
    this.deprnLeftMonth = deprnLeftMonth;
  }

  public String getAssetsType() {
    return this.assetsType;
  }

  public String getRejectType() {
    return this.rejectType;
  }

  public void setRejectType(String rejectType) {
    this.rejectType = rejectType;
  }

  public String getRejectTypeName() {
    return this.rejectTypeName;
  }

  public void setRejectTypeName(String rejectTypeName) {
    this.rejectTypeName = rejectTypeName;
  }

  public String getRejectTypeOpt() {
    return this.rejectTypeOpt;
  }

  public void setRejectTypeOpt(String rejectTypeOpt) {
    this.rejectTypeOpt = rejectTypeOpt;
  }

  public void setAssetsType(String assetsType) {
    this.assetsType = assetsType;
  }

  public float getRetirementCost() {
    return this.retirementCost;
  }

  public void setRetirementCost(float retirementCost) {
    this.retirementCost = retirementCost;
  }

  public float getImpairReserve() {
    return this.impairReserve;
  }

  public void setImpairReserve(float impairReserve) {
    this.impairReserve = impairReserve;
  }

  public float getDeprnCost() {
    return this.deprnCost;
  }

  public void setDeprnCost(float deprnCost) {
    this.deprnCost = deprnCost;
  }

  public String getManufacturerName() {
    return this.manufacturerName;
  }

  public void setManufacturerName(String manufacturerName) {
    this.manufacturerName = manufacturerName;
  }

  public AmsAssetsTransLineDTO()
  {
    this.assignedDate = new SimpleCalendar();
    this.confirmDate = new SimpleCalendar();
    this.receivedDate = new SimpleCalendar();
    this.lineTransDate = new SimpleCalendar();
    setPrimaryKeyName("lineId");
  }

  public void setTransId(String transId)
  {
    this.transId = transId;
  }

  public void setLineId(String lineId)
  {
    this.lineId = lineId;
  }

  public void setAssignedToLocation(String assignedToLocation)
  {
    this.assignedToLocation = assignedToLocation;
  }

  public void setTransType(String transType) {
    this.transType = transType;
  }

  public void setHrDeptOption(String hrDeptOption) {
    this.hrDeptOption = hrDeptOption;
  }

  public void setHrDeptId(String hrDeptId) {
    this.hrDeptId = hrDeptId;
  }

  public void setOldLocation(String oldLocation) {
    this.oldLocation = oldLocation;
  }

  public void setOldResponsibilityDept(String oldResponsibilityDept) {
    this.oldResponsibilityDept = oldResponsibilityDept;
  }

  public void setOldResponsibilityUser(String oldResponsibilityUser) {
    this.oldResponsibilityUser = oldResponsibilityUser;
  }

  public void setAssignedToLocationName(String assignedToLocationName) {
    this.assignedToLocationName = assignedToLocationName;
  }

  public void setOldResponsibilityDeptName(String oldResponsibilityDeptName) {
    this.oldResponsibilityDeptName = oldResponsibilityDeptName;
  }

  public void setOldResponsibilityUserName(String oldResponsibilityUserName) {
    this.oldResponsibilityUserName = oldResponsibilityUserName;
  }

  public void setOldLocationName(String oldLocationName) {
    this.oldLocationName = oldLocationName;
  }

  public void setToOrganizationId(int toOrganizationId) {
    this.toOrganizationId = toOrganizationId;
  }

  public void setLineStatus(String lineStatus) {
    this.lineStatus = lineStatus;
  }

  public void setReceivedUser(int receivedUser) {
    this.receivedUser = receivedUser;
  }

  public void setTransNo(String transNo) {
    this.transNo = transNo;
  }

  public void setLineTransDate(String lineTransDate) throws CalendarException {
    this.lineTransDate.setCalendarValue(lineTransDate);
  }

  public void setOldDepreciationAccount(String oldDepreciationAccount) {
    this.oldDepreciationAccount = oldDepreciationAccount;
  }

  public void setOldFaCategoryCode(String oldFaCategoryCode) {
    this.oldFaCategoryCode = oldFaCategoryCode;
  }

  public void setOldFaCategoryName(String oldFaCategoryName) {
    this.oldFaCategoryName = oldFaCategoryName;
  }

  public void setFaContentCode(String faContentCode) {
    this.faContentCode = faContentCode;
  }

  public void setFaContentName(String faContentName) {
    this.faContentName = faContentName;
  }

  public void setLineReason(String lineReason) {
    this.lineReason = lineReason;
  }

  public void setNetValue(String netValue) {
    this.netValue = netValue;
  }

  public void setPrepareDevalue(float prepareDevalue) {
    this.prepareDevalue = prepareDevalue;
  }

  public void setSoftDevalueVersion(String softDevalueVersion) {
    this.softDevalueVersion = softDevalueVersion;
  }

  public void setSoftInuseVersion(String softInuseVersion) {
    this.softInuseVersion = softInuseVersion;
  }

  public void setSumDepreciation(String sumDepreciation) {
    this.sumDepreciation = sumDepreciation;
  }

  public void setAssignedDate(String assignedDate)
    throws CalendarException
  {
    this.assignedDate.setCalendarValue(assignedDate);
  }

  public void setReceivedDate(String receivedDate)
    throws CalendarException
  {
    this.receivedDate.setCalendarValue(receivedDate);
  }

  public void setConfirmDate(String confirmDate)
    throws CalendarException
  {
    this.confirmDate.setCalendarValue(confirmDate);
  }

  public String getTransId()
  {
    return this.transId;
  }

  public String getLineId()
  {
    return this.lineId;
  }

  public String getAssignedToLocation()
  {
    return this.assignedToLocation;
  }

  public SimpleCalendar getAssignedDate()
    throws CalendarException
  {
    this.assignedDate.setCalPattern(getCalPattern());
    return this.assignedDate;
  }

  public SimpleCalendar getReceivedDate()
    throws CalendarException
  {
    this.receivedDate.setCalPattern(getCalPattern());
    return this.receivedDate;
  }

  public SimpleCalendar getConfirmDate()
    throws CalendarException
  {
    this.confirmDate.setCalPattern(getCalPattern());
    return this.confirmDate;
  }

  public String getTransType() {
    return this.transType;
  }

  public String getHrDeptOption() {
    return this.hrDeptOption;
  }

  public String getHrDeptId() {
    return this.hrDeptId;
  }

  public String getOldLocation() {
    return this.oldLocation;
  }

  public String getOldResponsibilityDept() {
    return this.oldResponsibilityDept;
  }

  public String getOldResponsibilityUser() {
    return this.oldResponsibilityUser;
  }

  public String getAssignedToLocationName() {
    return this.assignedToLocationName;
  }

  public String getOldResponsibilityDeptName() {
    return this.oldResponsibilityDeptName;
  }

  public String getOldResponsibilityUserName() {
    return this.oldResponsibilityUserName;
  }

  public String getOldLocationName() {
    return this.oldLocationName;
  }

  public int getToOrganizationId() {
    return this.toOrganizationId;
  }

  public String getLineStatus() {
    return this.lineStatus;
  }

  public int getReceivedUser() {
    return this.receivedUser;
  }

  public String getTransNo() {
    return this.transNo;
  }

  public SimpleCalendar getLineTransDate() throws CalendarException {
    this.lineTransDate.setCalPattern(getCalPattern());
    return this.lineTransDate;
  }

  public String getOldDepreciationAccount() {
    return this.oldDepreciationAccount;
  }

  public String getOldFaCategoryCode() {
    return this.oldFaCategoryCode;
  }

  public String getOldFaCategoryName() {
    return this.oldFaCategoryName;
  }

  public String getFaContentCode() {
    return this.faContentCode;
  }

  public String getFaContentName() {
    return this.faContentName;
  }

  public String getLineReason() {
    return this.lineReason;
  }

  public String getNetValue() {
    return this.netValue;
  }

  public float getPrepareDevalue() {
    return this.prepareDevalue;
  }

  public String getSoftDevalueVersion() {
    return this.softDevalueVersion;
  }

  public String getSoftInuseVersion() {
    return this.softInuseVersion;
  }

  public String getSumDepreciation() {
    return this.sumDepreciation;
  }

  public String getOldLocationCode() {
    return this.oldLocationCode;
  }

  public void setOldLocationCode(String oldLocationCode) {
    this.oldLocationCode = oldLocationCode;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getResponsibilityUser() {
    return this.responsibilityUser;
  }

  public void setResponsibilityUser(String responsibilityUser) {
    this.responsibilityUser = responsibilityUser;
  }

  public String getBarcode() {
    return this.barcode;
  }

  public String getNewBarcode() {
    return this.newBarcode;
  }

  public String getOldAddressId() {
    return this.oldAddressId;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public void setNewBarcode(String newBarcode) {
    this.newBarcode = newBarcode;
  }

  public void setOldAddressId(String oldAddressId) {
    this.oldAddressId = oldAddressId;
  }

  public String getRecycleValue()
  {
    return this.recycleValue;
  }

  public void setRecycleValue(String recycleValue)
  {
    this.recycleValue = recycleValue;
  }

  public String getAssetsStatusDes()
  {
    return this.assetsStatusDes;
  }

  public void setAssetsStatusDes(String assetsStatusDes)
  {
    this.assetsStatusDes = assetsStatusDes;
  }

  public int getAssetsStatus()
  {
    return this.assetsStatus;
  }

  public void setAssetsStatus(int assetsStatus)
  {
    this.assetsStatus = assetsStatus;
  }

  public String getTransTypeValue() {
    return this.transTypeValue;
  }

  public void setTransTypeValue(String transTypeValue) {
    this.transTypeValue = transTypeValue;
  }

  public String getImportantFlag() {
    return this.importantFlag;
  }

  public void setImportantFlag(String importantFlag) {
    this.importantFlag = importantFlag;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getFromObjectNo() {
    return this.fromObjectNo;
  }

  public void setFromObjectNo(String fromObjectNo) {
    this.fromObjectNo = fromObjectNo;
  }

  public String getToObjectNo() {
    return this.toObjectNo;
  }

  public void setToObjectNo(String toObjectNo) {
    this.toObjectNo = toObjectNo;
  }

  public String getFromObjectName() {
    return this.fromObjectName;
  }

  public void setFromObjectName(String fromObjectName) {
    this.fromObjectName = fromObjectName;
  }

  public String getToObjectName() {
    return this.toObjectName;
  }

  public void setToObjectName(String toObjectName) {
    this.toObjectName = toObjectName;
  }

  public String getRemarkTransNo() {
    return this.remarkTransNo;
  }

  public void setRemarkTransNo(String remarkTransNo) {
    this.remarkTransNo = remarkTransNo;
  }
}