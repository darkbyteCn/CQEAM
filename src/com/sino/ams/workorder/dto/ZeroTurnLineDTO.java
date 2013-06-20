package com.sino.ams.workorder.dto;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.dto.DTO;

public class ZeroTurnLineDTO implements DTO {
	private String isTd = "";// TD
	private int companyCodeTWO;
	private String manufacturerIdTWO="";
	private String addressIdTWO="";
	private String responsibilityDeptTWO="";
	private String responsibilityUserTWO="";
	private String specialityDeptTWO="";
	private String lneIdTWO="";
	private String opeIdTWO="";
	private String cexIdTWO="";
	private String nleIdTWO="";
	private String centerName="";
	private String pUserName="";
	private String  record = "";
	private String  diffResult = "";//差异处理结果
	
	private String  expectedDate = "";//预计到货日期
	
	private String  assetKey1 = "";
	private String  assetKey2 = "";
	private String  assetKey3 = "";
	private String  assetType = "";
	private String  isDeprn = "";
	private String  isAdjust = "";
	private String  attribute4 = "";
	private String  attribute5 = "";
	private String  misProcureCode = "";//U列
	private String  procureType = "";//R列
	private String  receiver = "";//S列
	private String  receiverContact = "";//T列

	
	public String getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}

	public String getMisProcureCode() {
		return misProcureCode;
	}

	public void setMisProcureCode(String misProcureCode) {
		this.misProcureCode = misProcureCode;
	}

	public String getProcureType() {
		return procureType;
	}

	public void setProcureType(String procureType) {
		this.procureType = procureType;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverContact() {
		return receiverContact;
	}

	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}

	public String getAssetKey1() {
		return assetKey1;
	}

	public void setAssetKey1(String assetKey1) {
		this.assetKey1 = assetKey1;
	}

	public String getAssetKey2() {
		return assetKey2;
	}

	public void setAssetKey2(String assetKey2) {
		this.assetKey2 = assetKey2;
	}

	public String getAssetKey3() {
		return assetKey3;
	}

	public void setAssetKey3(String assetKey3) {
		this.assetKey3 = assetKey3;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getIsDeprn() {
		return isDeprn;
	}

	public void setIsDeprn(String isDeprn) {
		this.isDeprn = isDeprn;
	}

	public String getIsAdjust() {
		return isAdjust;
	}

	public void setIsAdjust(String isAdjust) {
		this.isAdjust = isAdjust;
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

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getpUserName() {
		return pUserName;
	}

	public void setpUserName(String pUserName) {
		this.pUserName = pUserName;
	}

	private String companyCode = "";// 公司代码
	private String barcode = "";// 标签号
	private String contentCode = "";// --资产目录
	private String contentName = "";// 资产目录名称
	private String assetsDescription = "";// 资产名称
	private String itemSpec = "";// 规格型号（必输）
	private String itemQty = "";// 数量（必输）
	private String unitOfMeasure = "";// 数量单位（必输）
	private String manufacturerName = "";// 厂商（必输）
	private String manufacturerId = "";
	private String objectNo = "";// 地点编号（必输）
	private String workorderObjectName = "";// 地点名称（可选）
	private String responsibilityDept = "";// 责任部门（必输）
	private String responsibilityCode = "";// 责任部门（必输）
	private String responsibilityUser = "";// 责任人（必输）
	private String responsibilityName = "";// 责任人（必输）
	private String specialityDept = "";// 专业部门（可选）
	private String specialityName = "";// 专业部门（可选）
	private String specialityCode = "";// 专业部门（可选）
	
	/**
	 * @return the specialityCode
	 */
	public String getSpecialityCode() {
		return specialityCode;
	}

	/**
	 * @param specialityCode the specialityCode to set
	 */
	public void setSpecialityCode(String specialityCode) {
		this.specialityCode = specialityCode;
	}

	private String startDate = null; // 启用日期（可选）
	private String years = "";// 使用年限（必输）
	private String price = "";// 金额（必输）
	private String procureCode = "";// 采购单号（必输）
	private String costCenterCode = "";// 成本中心（必输）
	private String costCenterName = "";// 成本中心
	private String isShare = "";// 是否共享设备（可选）
	private String isBulid = "";// 是否共建设备（必输）
	private String lneId = "";// 逻辑网络元素（可选）
	private String opeId = "";// 业务平台（必输）
	private String cexId = "";// 投资分类（可选）
	private String nleId = "";// 网各层次（必输）
	private String remark = "";// 备注（可选）
	private String unitManager = "";// 单位资产管理员
	private String unitId = "";     //单位资产管理员（即USER_ID）
	private String computeDays = "";// 执行周期
	private String errorMessage = "";
	private String lineId = "";
	private String ExcelLineId = "";//Excel行号
	private String transId = "";  
//	private String transNo ="";    //交接工单号
    private String zeroNo = "";    //交接工单
	
	private int createdBy;//创建人
	private String createdByName = "";//创建人名称
	private String createDate = "";//创建日期
	private String transType = "";       //单据类型
	private String transStatus = "";     //单据状态
	private String financeProp = "ASSETS";
	private String checkedBarcode = ""; // 被勾选的条码
	private int organizationId;
	private String opinionType="";  //用做传参判断
	private List partBarcode=new ArrayList();
	private String prjId = "" ;
	private String transStatusVal = "";//单据状态名称
	private String addressId = "";
	private String itemCode = "";

	

	public int getCompanyCodeTWO() {
		return companyCodeTWO;
	}

	public void setCompanyCodeTWO(int companyCodeTWO) {
		this.companyCodeTWO = companyCodeTWO;
	}

	public String getManufacturerIdTWO() {
		return manufacturerIdTWO;
	}

	public void setManufacturerIdTWO(String manufacturerIdTWO) {
		this.manufacturerIdTWO = manufacturerIdTWO;
	}

	public String getAddressIdTWO() {
		return addressIdTWO;
	}

	public void setAddressIdTWO(String addressIdTWO) {
		this.addressIdTWO = addressIdTWO;
	}

	public String getResponsibilityDeptTWO() {
		return responsibilityDeptTWO;
	}

	public void setResponsibilityDeptTWO(String responsibilityDeptTWO) {
		this.responsibilityDeptTWO = responsibilityDeptTWO;
	}

	public String getResponsibilityUserTWO() {
		return responsibilityUserTWO;
	}

	public void setResponsibilityUserTWO(String responsibilityUserTWO) {
		this.responsibilityUserTWO = responsibilityUserTWO;
	}

	public String getSpecialityDeptTWO() {
		return specialityDeptTWO;
	}

	public void setSpecialityDeptTWO(String specialityDeptTWO) {
		this.specialityDeptTWO = specialityDeptTWO;
	}

	public String getLneIdTWO() {
		return lneIdTWO;
	}

	public void setLneIdTWO(String lneIdTWO) {
		this.lneIdTWO = lneIdTWO;
	}

	public String getOpeIdTWO() {
		return opeIdTWO;
	}

	public void setOpeIdTWO(String opeIdTWO) {
		this.opeIdTWO = opeIdTWO;
	}

	public String getCexIdTWO() {
		return cexIdTWO;
	}

	public void setCexIdTWO(String cexIdTWO) {
		this.cexIdTWO = cexIdTWO;
	}

	public String getNleIdTWO() {
		return nleIdTWO;
	}

	public void setNleIdTWO(String nleIdTWO) {
		this.nleIdTWO = nleIdTWO;
	}

	public String getIsTd() {
		return isTd;
	}

	public void setIsTd(String isTd) {
		this.isTd = isTd;
	}
	/**
	 * @return the responsibilityCode
	 */
	public String getResponsibilityCode() {
		return responsibilityCode;
	}

	/**
	 * @param responsibilityCode the responsibilityCode to set
	 */
	public void setResponsibilityCode(String responsibilityCode) {
		this.responsibilityCode = responsibilityCode;
	}

	/**
	 * @return the responsibilityName
	 */
	public String getResponsibilityName() {
		return responsibilityName;
	}

	/**
	 * @param responsibilityName the responsibilityName to set
	 */
	public void setResponsibilityName(String responsibilityName) {
		this.responsibilityName = responsibilityName;
	}

	/**
	 * @return the specialityName
	 */
	public String getSpecialityName() {
		return specialityName;
	}

	/**
	 * @param specialityName the specialityName to set
	 */
	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}

	/**
	 * @return the costCenterName
	 */
	public String getCostCenterName() {
		return costCenterName;
	}

	/**
	 * @param costCenterName the costCenterName to set
	 */
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	/**
	 * @return the prjId
	 */
	public String getPrjId() {
		return prjId;
	}

	/**
	 * @param prjId the prjId to set
	 */
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * @return the partBarcode
	 */
	public List getPartBarcode() {
		return partBarcode;
	}

	/**
	 * @param partBarcode the partBarcode to set
	 */
	public void setPartBarcode(List partBarcode) {
		this.partBarcode = partBarcode;
	}

	/**
	 * @return the opinionType
	 */
	public String getOpinionType() {
		return opinionType;
	}

	/**
	 * @param opinionType the opinionType to set
	 */
	public void setOpinionType(String opinionType) {
		this.opinionType = opinionType;
	}

	/**
	 * @return the organizationId
	 */
	public int getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return the checkedBarcode
	 */
	public String getCheckedBarcode() {
		return checkedBarcode;
	}

	/**
	 * @param checkedBarcode the checkedBarcode to set
	 */
	public void setCheckedBarcode(String checkedBarcode) {
		this.checkedBarcode = checkedBarcode;
	}

	/**
	 * @return the financeProp
	 */
	public String getFinanceProp() {
		return financeProp;
	}

	/**
	 * @param financeProp the financeProp to set
	 */
	public void setFinanceProp(String financeProp) {
		this.financeProp = financeProp;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

	public String getAssetsDescription() {
		return assetsDescription;
	}

	public void setAssetsDescription(String assetsDescription) {
		this.assetsDescription = assetsDescription;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getWorkorderObjectName() {
		return workorderObjectName;
	}

	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}

	public String getResponsibilityDept() {
		return responsibilityDept;
	}

	public void setResponsibilityDept(String responsibilityDept) {
		this.responsibilityDept = responsibilityDept;
	}

	public String getResponsibilityUser() {
		return responsibilityUser;
	}

	public void setResponsibilityUser(String responsibilityUser) {
		this.responsibilityUser = responsibilityUser;
	}

	public String getSpecialityDept() {
		return specialityDept;
	}

	public void setSpecialityDept(String specialityDept) {
		this.specialityDept = specialityDept;
	}


	public String getProcureCode() {
		return procureCode;
	}

	public void setProcureCode(String procureCode) {
		this.procureCode = procureCode;
	}

	public String getCostCenterCode() {
		return costCenterCode;
	}

	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	public String getIsShare() {
		return isShare;
	}

	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}

	public String getIsBulid() {
		return isBulid;
	}

	public void setIsBulid(String isBulid) {
		this.isBulid = isBulid;
	}

	public String getLneId() {
		return lneId;
	}

	public void setLneId(String lneId) {
		this.lneId = lneId;
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

	public String getNleId() {
		return nleId;
	}

	public void setNleId(String nleId) {
		this.nleId = nleId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnitManager() {
		return unitManager;
	}

	public void setUnitManager(String unitManager) {
		this.unitManager = unitManager;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getExcelLineId() {
		return ExcelLineId;
	}

	public void setExcelLineId(String excelLineId) {
		ExcelLineId = excelLineId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getItemQty() {
		return itemQty;
	}

	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getComputeDays() {
		return computeDays;
	}

	public void setComputeDays(String computeDays) {
		this.computeDays = computeDays;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getTransStatusVal() {
		return transStatusVal;
	}

	public void setTransStatusVal(String transStatusVal) {
		this.transStatusVal = transStatusVal;
	}

	public String getZeroNo() {
		return zeroNo;
	}

	public void setZeroNo(String zeroNo) {
		this.zeroNo = zeroNo;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getDiffResult() {
		return diffResult;
	}

	public void setDiffResult(String diffResult) {
		this.diffResult = diffResult;
	}
	
}