package com.sino.soa.mis.srv.PageInquiryAssetDistribution.dto;

import java.util.Date;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 资产会计期状态 SrvAssetPeriodStatus</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* user:wangzp
* function:查询资产分配行信息（分页）
*/

public class InquiryAssetDistributionDTO extends CheckBoxDTO{
	
	private String orgOption = "";
	private String assetsType = "";
    private String startLastUpdateDate = ""; //最后更新开始时间
    private String endLastUpdateDate = "";   //最后更新结束时间
	private String assetId = "";             //资产ID n
	private String bookTypeCode = "";        //资产账簿
	private String assetNumber = "";         //资产编号	
	private String distributionId = "";      //分配行ID n
	private String lastUpdateDate = "";
	private String department = "";
	private String location = "";
	private String status = "";
	private String locationId = "";
	private String codeCombination = "";
	private String distributionUnit = "";         //n
	private String assignedName = "";      
	private String assignedTo ="";                //n
	private String assignedNumber = "";
	private String expenseCodeCombinationId = ""; //n
	
	public InquiryAssetDistributionDTO(){
		super();
		
	}

	public String getOrgOption() {
		return orgOption;
	}

	public void setOrgOption(String orgOption) {
		this.orgOption = orgOption;
	}

	public String getAssetsType() {
		return assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
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

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getBookTypeCode() {
		return bookTypeCode;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getDistributionId() {
		return distributionId;
	}

	public void setDistributionId(String distributionId) {
		this.distributionId = distributionId;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getCodeCombination() {
		return codeCombination;
	}

	public void setCodeCombination(String codeCombination) {
		this.codeCombination = codeCombination;
	}

	public String getDistributionUnit() {
		return distributionUnit;
	}

	public void setDistributionUnit(String distributionUnit) {
		this.distributionUnit = distributionUnit;
	}

	public String getAssignedName() {
		return assignedName;
	}

	public void setAssignedName(String assignedName) {
		this.assignedName = assignedName;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getAssignedNumber() {
		return assignedNumber;
	}

	public void setAssignedNumber(String assignedNumber) {
		this.assignedNumber = assignedNumber;
	}

	public String getExpenseCodeCombinationId() {
		return expenseCodeCombinationId;
	}

	public void setExpenseCodeCombinationId(String expenseCodeCombinationId) {
		this.expenseCodeCombinationId = expenseCodeCombinationId;
	}

	
}   

