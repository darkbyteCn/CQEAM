package com.sino.soa.mis.srv.dealProjectDiversity.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 2012-2-13
 * To change this template use File | Settings | File Templates.
 */
public class DealProjectDiversityCheckDTO extends CheckBoxDTO {
    private String bookTypeCode = "";
    private String bookTypeCodeOption = "";
    private String projectNo = "";
    private String misProjectId = "";
    private String projectName = "";
    private String projectNumber = "";
    private String projectType = "";
    private String diffTypeCode = "";           //差异类型Code
    private String diffType = "";               //差异类型
    private String paValue = "";
    private String eamValue = "";
    private String misProjectId2="";
    private String projectNumber2 = "";
    public String getProjectNumber2() {
		return projectNumber2;
	}

	public void setProjectNumber2(String projectNumber2) {
		this.projectNumber2 = projectNumber2;
	}

	public String getMisProjectId2() {
		return misProjectId2;
	}

	public void setMisProjectId2(String misProjectId2) {
		this.misProjectId2 = misProjectId2;
	}

	public String getPaValue() {
		return paValue;
	}

	public void setPaValue(String paValue) {
		this.paValue = paValue;
	}

	public String getEamValue() {
		return eamValue;
	}

	public void setEamValue(String eamValue) {
		this.eamValue = eamValue;
	}

	private String taskNumber = "";
    private String taskName = "";
    private String tagNumber = "";
    private String assetName = "";
    private String assetDescription = "";
    private String assetNumber = "";
    private String assetCategory = "";
    private String assetCategoryDesc = "";
    private String assetLocation = "";
    private String assetUnits = "";
    private String unitOfMeasure = "";
    private String capitalizedCost = "";
    private String capitalizedDate = "";
    private String assetKeyCcidDesc = "";
    private String assetEmployee = "";
    private String employeeName = "";
    private String employeeNumber = "";
    private String depreciationExpenseCcid = "";
    private String modelNumber = "";
    private String manufactorerName = "";
    private String datePlacedInService = "";
    private String faPeriodName = "";
    private String locationId = "";
    private String locationCode = "";
    private String taskId = "";
    private String projectId = "";
    private String projectAssetId = "";
    private String attribute4 = "";
    private String attribute5 = "";
    private String attribute6 = "";
    private String attribute7 = "";

    private String error = "";

    public String getBookTypeCodeOption() {
		return bookTypeCodeOption;
	}

	public void setBookTypeCodeOption(String bookTypeCodeOption) {
		this.bookTypeCodeOption = bookTypeCodeOption;
	}
	

    public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	
	public String getMisProjectId() {
		return misProjectId;
	}

	public void setMisProjectId(String misProjectId) {
		this.misProjectId = misProjectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getDiffTypeCode() {
		return diffTypeCode;
	}

	public void setDiffTypeCode(String diffTypeCode) {
		this.diffTypeCode = diffTypeCode;
	}

	public String getDiffType() {
		return diffType;
	}

	public void setDiffType(String diffType) {
		this.diffType = diffType;
	}

	public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
    }

    public String getAssetCategoryDesc() {
        return assetCategoryDesc;
    }

    public void setAssetCategoryDesc(String assetCategoryDesc) {
        this.assetCategoryDesc = assetCategoryDesc;
    }

    public String getAssetLocation() {
        return assetLocation;
    }

    public void setAssetLocation(String assetLocation) {
        this.assetLocation = assetLocation;
    }

    public String getAssetUnits() {
        return assetUnits;
    }

    public void setAssetUnits(String assetUnits) {
        this.assetUnits = assetUnits;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getCapitalizedCost() {
        return capitalizedCost;
    }

    public void setCapitalizedCost(String capitalizedCost) {
        this.capitalizedCost = capitalizedCost;
    }

    public String getCapitalizedDate() {
        return capitalizedDate;
    }

    public void setCapitalizedDate(String capitalizedDate) {
        this.capitalizedDate = capitalizedDate;
    }

    public String getAssetKeyCcidDesc() {
        return assetKeyCcidDesc;
    }

    public void setAssetKeyCcidDesc(String assetKeyCcidDesc) {
        this.assetKeyCcidDesc = assetKeyCcidDesc;
    }

    public String getAssetEmployee() {
        return assetEmployee;
    }

    public void setAssetEmployee(String assetEmployee) {
        this.assetEmployee = assetEmployee;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getDepreciationExpenseCcid() {
        return depreciationExpenseCcid;
    }

    public void setDepreciationExpenseCcid(String depreciationExpenseCcid) {
        this.depreciationExpenseCcid = depreciationExpenseCcid;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getManufactorerName() {
        return manufactorerName;
    }

    public void setManufactorerName(String manufactorerName) {
        this.manufactorerName = manufactorerName;
    }

    public String getDatePlacedInService() {
        return datePlacedInService;
    }

    public void setDatePlacedInService(String datePlacedInService) {
        this.datePlacedInService = datePlacedInService;
    }

    public String getFaPeriodName() {
        return faPeriodName;
    }

    public void setFaPeriodName(String faPeriodName) {
        this.faPeriodName = faPeriodName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectAssetId() {
        return projectAssetId;
    }

    public void setProjectAssetId(String projectAssetId) {
        this.projectAssetId = projectAssetId;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

}
