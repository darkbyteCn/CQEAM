package com.sino.nm.ams.mss.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;
import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_yuyao
 * Date: 2011-4-26
 * Time: 11:40:22
 * To change this template use File | Settings | File Templates.
 */
public class MssAssetsAddLDTO extends CommonRecordDTO {

    private String headId = "";
    private String lineId = "";
    private String barcode = "";
    private String itemCode = "";
    private String itemName1 = "";
    private String itemSpec1 = "";
    private String respUser = "";
    private String employeeId = "";
    private String respDept = "";
    private String deptCode = "";
    private String remark1 = "";
    private String createUser = "";
    private String status = "";
    private String addressId = "";
    private String maintainUser = "";
    private String itemUsage = "";
    private String itemUsageStatus = "";
    private String cost = "";
    private SimpleCalendar createdDate = null;
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
    private String itemId = "";
	private String itemStatus = "";
	private String itemBrand = "";
	private String itemSerial = "";
	private String useBySystem = "";
	private String productId = "";
	private String memory = "";
	private String cpu = "";
	private String ipAddress = "";
	private String diskInformation = "";
	private String systemName = "";
	private String trusteeshipType = "";
	private String importantLevel = "";
	private String userLevel = "";
	private String itemCategory1 = "";
	private String itemCategory2 = "";
	private String enabled = "";
	private String disableDate = "";
	private String itemCategory3 = "";
	private String attribute1 = "";
	private String attribute2 = "";
	private String attribute3 = "";
	private String updateVersion = "";
	private String licenseNum = "";
	private String licenseName = "";
	private String softMedium = "";
	private String productNum = "";
	private String thirdCompany = "";
	private String secureLevel = "";
	private String completenessLevel = "";
	private String maintainDept = "";
	private String mssBarcode = "";
	private String workorderObjectName = "";

    public MssAssetsAddLDTO() {
        createdDate = new SimpleCalendar();
        fromDate = new SimpleCalendar();
        toDate = new SimpleCalendar();
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getMssBarcode() {
        return mssBarcode;
    }

    public void setMssBarcode(String mssBarcode) {
        this.mssBarcode = mssBarcode;
    }

    public String getCompletenessLevel() {
        return completenessLevel;
    }

    public String getMaintainDept() {
        return maintainDept;
    }

    public void setMaintainDept(String maintainDept) {
        this.maintainDept = maintainDept;
    }

    public void setCompletenessLevel(String completenessLevel) {
        this.completenessLevel = completenessLevel;
    }

    public String getSecureLevel() {
        return secureLevel;
    }

    public void setSecureLevel(String secureLevel) {
        this.secureLevel = secureLevel;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getSoftMedium() {
        return softMedium;
    }

    public void setSoftMedium(String softMedium) {
        this.softMedium = softMedium;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getThirdCompany() {
        return thirdCompany;
    }

    public void setThirdCompany(String thirdCompany) {
        this.thirdCompany = thirdCompany;
    }

    public String getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getItemSerial() {
        return itemSerial;
    }

    public void setItemSerial(String itemSerial) {
        this.itemSerial = itemSerial;
    }

    public String getUseBySystem() {
        return useBySystem;
    }

    public void setUseBySystem(String useBySystem) {
        this.useBySystem = useBySystem;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDiskInformation() {
        return diskInformation;
    }

    public void setDiskInformation(String diskInformation) {
        this.diskInformation = diskInformation;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getTrusteeshipType() {
        return trusteeshipType;
    }

    public void setTrusteeshipType(String trusteeshipType) {
        this.trusteeshipType = trusteeshipType;
    }

    public String getImportantLevel() {
        return importantLevel;
    }

    public void setImportantLevel(String importantLevel) {
        this.importantLevel = importantLevel;
    }

    public String getItemCategory1() {
        return itemCategory1;
    }

    public void setItemCategory1(String itemCategory1) {
        this.itemCategory1 = itemCategory1;
    }

    public String getItemCategory2() {
        return itemCategory2;
    }

    public void setItemCategory2(String itemCategory2) {
        this.itemCategory2 = itemCategory2;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getDisableDate() {
        return disableDate;
    }

    public void setDisableDate(String disableDate) {
        this.disableDate = disableDate;
    }

    public String getItemCategory3() {
        return itemCategory3;
    }

    public void setItemCategory3(String itemCategory3) {
        this.itemCategory3 = itemCategory3;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getItemUsage() {
        return itemUsage;
    }

    public void setItemUsage(String itemUsage) {
        this.itemUsage = itemUsage;
    }

    public String getItemUsageStatus() {
        return itemUsageStatus;
    }

    public void setItemUsageStatus(String itemUsageStatus) {
        this.itemUsageStatus = itemUsageStatus;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMaintainUser() {
        return maintainUser;
    }

    public void setMaintainUser(String maintainUser) {
        this.maintainUser = maintainUser;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getItemName1() {
        return itemName1;
    }

    public void setItemName1(String itemName1) {
        this.itemName1 = itemName1;
    }

    public String getItemSpec1() {
        return itemSpec1;
    }

    public void setItemSpec1(String itemSpec1) {
        this.itemSpec1 = itemSpec1;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getRespUser() {
        return respUser;
    }

    public void setRespUser(String respUser) {
        this.respUser = respUser;
    }

    public String getRespDept() {
        return respDept;
    }

    public void setRespDept(String respDept) {
        this.respDept = respDept;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getHeadId() {
        return headId;
    }

    public void setHeadId(String headId) {
        this.headId = headId;
    }

    public SimpleCalendar getCreatedDate() throws CalendarException {
        createdDate.setCalPattern(getCalPattern());
        return createdDate;
    }

    public void setCreatedDate(String createdDate) throws CalendarException {
        this.createdDate.setCalendarValue(createdDate);
    }

    public SimpleCalendar getFromDate() throws CalendarException {
        fromDate.setCalPattern(getCalPattern());
        return fromDate;
    }

    public void setFromDate(String fromDate) throws CalendarException {
        this.fromDate.setCalendarValue(fromDate);
    }

    public SimpleCalendar getToDate() throws CalendarException {
        toDate.setCalPattern(getCalPattern());
        return toDate;
    }

    public void setToDate(String toDate) throws CalendarException {
        this.toDate.setCalendarValue(toDate);
    }

}
