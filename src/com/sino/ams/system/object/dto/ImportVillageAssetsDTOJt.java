package com.sino.ams.system.object.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-5-25
 * Time: 10:05:15
 * To change this template use File | Settings | File Templates.
 */
public class ImportVillageAssetsDTOJt extends CommonRecordDTO {
    private String companyCode = "";
    private String barcode = "";
    private String itemName = "";
    private String itemSpec = "";
    private String employeeNumber = "";
    private String employeeName = "";
    private String workorderObjectCode = "";
    private String equipmentPerformance = "";
    private String contentCode = "";
    private String contentName = "";
    private String workorderObjectName = "";
    private String specialityDept = "";
    private String maintainUser = "";
    private String maintainDept = "";
    private String price = "";
    private String villageStartDate = "";
    private String remark = "";
    private String itemCode = "";
    private String addressId = "";
    private String employeeId = "";
    private String userId = "";
    private String rentId = "";
    private String systemid = "";
    private String responsibilityDept = "";

    private String power = "";
    private String rentPerson = "";
    private String tenancy = "";
    private String yearRental = "";
    private String monthReantal = "";
    private String rentStartDate = "";//获取EXCEL日期特殊处理
    private String rentEndDate = "";//获取EXCEL日期特殊处理

    private String itemQty = "";
    private String projectid = "";
    private String projectName = "";
    private String lneId = "";
    private String cexId = "";
    private String opeId = "";
    private String nleId = "";
    private String manufacturerId = "";
    private String isShare = "";
    private String financeProp = "";
    private String actualQty = "";
    private String constructStatus = "";
    private String tfNetAssetValue = "";
    private String tfDeprnCost = "";
    private String tfDepreciation = "";

    private String assetsCreateDate ="";
    private String dateRetired ="" ;
    private String manufacturerName ="";
    private String depreciationAccount ="";
    private String deprnLeftMonth="";
    private String scrapValue="";
    private String deprnAmount="";
    private String ytdDeprn="";
    private String deprnReserve="";
    private String impairAmount="";
    private String ytdImpairment="";
    private String impairReserve="";
    private int assetId;


   

	/**
	 * @return the assetId
	 */
	public int getAssetId() {
		return assetId;
	}

	/**
	 * @param assetId the assetId to set
	 */
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	/**
	 * @return the deprnLeftMonth
	 */
	public String getDeprnLeftMonth() {
		return deprnLeftMonth;
	}

	/**
	 * @param deprnLeftMonth the deprnLeftMonth to set
	 */
	public void setDeprnLeftMonth(String deprnLeftMonth) {
		this.deprnLeftMonth = deprnLeftMonth;
	}

	/**
	 * @return the scrapValue
	 */
	public String getScrapValue() {
		return scrapValue;
	}

	/**
	 * @param scrapValue the scrapValue to set
	 */
	public void setScrapValue(String scrapValue) {
		this.scrapValue = scrapValue;
	}

	/**
	 * @return the deprnAmount
	 */
	public String getDeprnAmount() {
		return deprnAmount;
	}

	/**
	 * @param deprnAmount the deprnAmount to set
	 */
	public void setDeprnAmount(String deprnAmount) {
		this.deprnAmount = deprnAmount;
	}

	/**
	 * @return the ytdDeprn
	 */
	public String getYtdDeprn() {
		return ytdDeprn;
	}

	/**
	 * @param ytdDeprn the ytdDeprn to set
	 */
	public void setYtdDeprn(String ytdDeprn) {
		this.ytdDeprn = ytdDeprn;
	}

	/**
	 * @return the deprnReserve
	 */
	public String getDeprnReserve() {
		return deprnReserve;
	}

	/**
	 * @param deprnReserve the deprnReserve to set
	 */
	public void setDeprnReserve(String deprnReserve) {
		this.deprnReserve = deprnReserve;
	}

	/**
	 * @return the impairAmount
	 */
	public String getImpairAmount() {
		return impairAmount;
	}

	/**
	 * @param impairAmount the impairAmount to set
	 */
	public void setImpairAmount(String impairAmount) {
		this.impairAmount = impairAmount;
	}

	/**
	 * @return the ytdImpairment
	 */
	public String getYtdImpairment() {
		return ytdImpairment;
	}

	/**
	 * @param ytdImpairment the ytdImpairment to set
	 */
	public void setYtdImpairment(String ytdImpairment) {
		this.ytdImpairment = ytdImpairment;
	}

	/**
	 * @return the impairReserve
	 */
	public String getImpairReserve() {
		return impairReserve;
	}

	/**
	 * @param impairReserve the impairReserve to set
	 */
	public void setImpairReserve(String impairReserve) {
		this.impairReserve = impairReserve;
	}

	/**
	 * @return the manufacturerName
	 */
	public String getManufacturerName() {
		return manufacturerName;
	}

	/**
	 * @param manufacturerName the manufacturerName to set
	 */
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	/**
	 * @return the depreciationAccount
	 */
	public String getDepreciationAccount() {
		return depreciationAccount;
	}

	/**
	 * @param depreciationAccount the depreciationAccount to set
	 */
	public void setDepreciationAccount(String depreciationAccount) {
		this.depreciationAccount = depreciationAccount;
	}

	/**
	 * @return the dateRetired
	 */
	public String getDateRetired() {
		return dateRetired;
	}

	/**
	 * @param dateRetired the dateRetired to set
	 */
	public void setDateRetired(String dateRetired) {
		this.dateRetired = dateRetired;
	}

	/**
	 * @return the assetsCreateDate
	 */
	public String getAssetsCreateDate() {
		return assetsCreateDate;
	}

	/**
	 * @param assetsCreateDate the assetsCreateDate to set
	 */
	public void setAssetsCreateDate(String assetsCreateDate) {
		this.assetsCreateDate = assetsCreateDate;
	}


	public String getTfDepreciation() {
        return tfDepreciation;
    }

    public void setTfDepreciation(String tfDepreciation) {
        this.tfDepreciation = tfDepreciation;
    }

    public String getTfNetAssetValue() {
        return tfNetAssetValue;
    }

    public void setTfNetAssetValue(String tfNetAssetValue) {
        this.tfNetAssetValue = tfNetAssetValue;
    }

    public String getTfDeprnCost() {
        return tfDeprnCost;
    }

    public void setTfDeprnCost(String tfDeprnCost) {
        this.tfDeprnCost = tfDeprnCost;
    }

    public String getConstructStatus() {
        return constructStatus;
    }

    public void setConstructStatus(String constructStatus) {
        this.constructStatus = constructStatus;
    }

    public String getActualQty() {
        return actualQty;
    }

    public void setActualQty(String actualQty) {
        this.actualQty = actualQty;
    }

    public String getFinanceProp() {
        return financeProp;
    }

    public void setFinanceProp(String financeProp) {
        this.financeProp = financeProp;
    }

    public String getShare() {
        return isShare;
    }

    public void setShare(String share) {
        isShare = share;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getNleId() {
        return nleId;
    }

    public void setNleId(String nleId) {
        this.nleId = nleId;
    }

    public String getOpeId() {
        return opeId;
    }

    public void setOpeId(String opeId) {
        this.opeId = opeId;
    }

    public String getCexId() {
        return cexId;
    }

    public void setCexId(String cexId) {
        this.cexId = cexId;
    }

    public String getLneId() {
        return lneId;
    }

    public void setLneId(String lneId) {
        this.lneId = lneId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVillageStartDate() {
        return villageStartDate;
    }

    public void setVillageStartDate(String villageStartDate) {
        this.villageStartDate = villageStartDate;
    }

    public String getRentStartDate() {
        return rentStartDate;
    }

    public void setRentStartDate(String rentStartDate) {
        this.rentStartDate = rentStartDate;
    }

    public String getRentEndDate() {
        return rentEndDate;
    }

    public void setRentEndDate(String rentEndDate) {
        this.rentEndDate = rentEndDate;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getWorkorderObjectCode() {
        return workorderObjectCode;
    }

    public void setWorkorderObjectCode(String workorderObjectCode) {
        this.workorderObjectCode = workorderObjectCode;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getEquipmentPerformance() {
        return equipmentPerformance;
    }

    public void setEquipmentPerformance(String equipmentPerformance) {
        this.equipmentPerformance = equipmentPerformance;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getWorkorderObjectName() {
        return workorderObjectName;
    }

    public void setWorkorderObjectName(String workorderObjectName) {
        this.workorderObjectName = workorderObjectName;
    }

    public String getSpecialityDept() {
        return specialityDept;
    }

    public void setSpecialityDept(String specialityDept) {
        this.specialityDept = specialityDept;
    }

    public String getMaintainDept() {
        return maintainDept;
    }

    public void setMaintainDept(String maintainDept) {
        this.maintainDept = maintainDept;
    }

    public String getMaintainUser() {
        return maintainUser;
    }

    public void setMaintainUser(String maintainUser) {
        this.maintainUser = maintainUser;
    }

    public String getRentPerson() {
        return rentPerson;
    }

    public void setRentPerson(String rentPerson) {
        this.rentPerson = rentPerson;
    }

    public String getTenancy() {
        return tenancy;
    }

    public void setTenancy(String tenancy) {
        this.tenancy = tenancy;
    }

    public String getYearRental() {
        return yearRental;
    }

    public void setYearRental(String yearRental) {
        this.yearRental = yearRental;
    }

    public String getMonthReantal() {
        return monthReantal;
    }

    public void setMonthReantal(String monthReantal) {
        this.monthReantal = monthReantal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
