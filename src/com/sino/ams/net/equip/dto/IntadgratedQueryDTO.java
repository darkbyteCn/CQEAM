package com.sino.ams.net.equip.dto;

import com.sino.ams.system.fixing.dto.EtsItemInfoDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: AMS_ASSETS_ADDRESS_V AmsAssetsAddressV</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class IntadgratedQueryDTO extends EtsItemInfoDTO{

	private String countyName = "";
	private String deptCode = "";
	private String deptName = "";
	private String workorderObjectCode = "";
	private String workorderObjectLocation = "";
	private String itemCategoryName = "";

	private String treeCategory = "";
	private String assetsCategory = "";
	private String companyName = "";
	private String exportType = "";

	private String faCategory1 = "";
	private String faCategory2 = "";
	private String assetsDescription = "";
	private String faCategoryCode = "";
	private String faCategoryName = "";

	private String unitOfMeasure = "";
	private String lifeInYears = "";
	private String modelNumber = "";
	private String tagNumber = "";
	private String currentUnits = "";
	private String assetsLocation = "";
	private SimpleCalendar datePlacedInService = null;
	private String isRetirements = "";
	private String assetNumber = "";
	private String assetsStatus = "";
	private String bookTypeCode = "";
	private String assignedToName = "";
	private String assignedToNumber = "";
	private String segment1 = "";
	private String segment2 = "";
	private String codeCombinationId = "";
	private String itemStatusName = "";
	private String transferType = "";//调拨单的哪一种类型：部门内，部门间，地市间

	private String cost = "";//原值--资产购入时的价值
	private String deprnCost = "";//净值--资产原值减去累计折旧后的值
	private String scrapValue = "";//残值--财务上应为定值
	private String depreciation = "";//累计折旧--从开始折旧日起算,按照一定的方法,例如平均折旧法,折旧到财务当期时的累计折旧额
	private String depreciationAccount = "";//折旧账户
	private String depreAccountOption = "";//折旧账户下拉列表
	private String faCategoryOption = "";//固定资产列表下拉框

	public IntadgratedQueryDTO() {
		super();
		this.datePlacedInService = new SimpleCalendar();
	}


	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 COUNTY_NAME
	 * @param countyName String
	 */
	public void setCountyName(String countyName){
		this.countyName = countyName;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 DEPT_CODE
	 * @param deptCode String
	 */
	public void setDeptCode(String deptCode){
		this.deptCode = deptCode;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 DEPT_NAME
	 * @param deptName String
	 */
	public void setDeptName(String deptName){
		this.deptName = deptName;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 WORKORDER_OBJECT_CODE
	 * @param workorderObjectCode String
	 */
	public void setWorkorderObjectCode(String workorderObjectCode){
		this.workorderObjectCode = workorderObjectCode;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 WORKORDER_OBJECT_LOCATION
	 * @param workorderObjectLocation String
	 */
	public void setWorkorderObjectLocation(String workorderObjectLocation){
		this.workorderObjectLocation = workorderObjectLocation;
	}

	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}

	public void setAssetsCategory(String assetsCategory) {
		this.assetsCategory = assetsCategory;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public void setTreeCategory(String treeCategory) {
		this.treeCategory = treeCategory;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 COUNTY_NAME
	 * @return String
	 */
	public String getCountyName() {
		return this.countyName;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 DEPT_CODE
	 * @return String
	 */
	public String getDeptCode() {
		return this.deptCode;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 DEPT_NAME
	 * @return String
	 */
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 WORKORDER_OBJECT_CODE
	 * @return String
	 */
	public String getWorkorderObjectCode() {
		return this.workorderObjectCode;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 WORKORDER_OBJECT_LOCATION
	 * @return String
	 */
	public String getWorkorderObjectLocation() {
		return this.workorderObjectLocation;
	}

	public String getItemCategoryName() {
		return itemCategoryName;
	}

	public String getAssetsCategory() {
		return assetsCategory;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getExportType() {
		return exportType;
	}

	public String getTreeCategory() {
		return treeCategory;
	}


	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 FA_CATEGORY1
	 * @param faCategory1 String
	 */
	public void setFaCategory1(String faCategory1){
		this.faCategory1 = faCategory1;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 FA_CATEGORY2
	 * @param faCategory2 String
	 */
	public void setFaCategory2(String faCategory2){
		this.faCategory2 = faCategory2;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 ASSETS_DESCRIPTION
	 * @param assetsDescription String
	 */
	public void setAssetsDescription(String assetsDescription){
		this.assetsDescription = assetsDescription;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 FA_CATEGORY_CODE
	 * @param faCategoryCode String
	 */
	public void setFaCategoryCode(String faCategoryCode){
		this.faCategoryCode = faCategoryCode;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 UNIT_OF_MEASURE
	 * @param unitOfMeasure String
	 */
	public void setUnitOfMeasure(String unitOfMeasure){
		this.unitOfMeasure = unitOfMeasure;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 LIFE_IN_YEARS
	 * @param lifeInYears String
	 */
	public void setLifeInYears(String lifeInYears){
		this.lifeInYears = lifeInYears;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 MODEL_NUMBER
	 * @param modelNumber String
	 */
	public void setModelNumber(String modelNumber){
		this.modelNumber = modelNumber;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 TAG_NUMBER
	 * @param tagNumber String
	 */
	public void setTagNumber(String tagNumber){
		this.tagNumber = tagNumber;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 CURRENT_UNITS
	 * @param currentUnits String
	 */
	public void setCurrentUnits(String currentUnits){
		this.currentUnits = currentUnits;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 ASSETS_LOCATION
	 * @param assetsLocation String
	 */
	public void setAssetsLocation(String assetsLocation){
		this.assetsLocation = assetsLocation;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 DATE_PLACED_IN_SERVICE
	 * @param datePlacedInService SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDatePlacedInService(String datePlacedInService) throws CalendarException{
		this.datePlacedInService.setCalendarValue(datePlacedInService);
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 IS_RETIREMENTS
	 * @param isRetirements String
	 */
	public void setIsRetirements(String isRetirements){
		this.isRetirements = isRetirements;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 ASSET_NUMBER
	 * @param assetNumber String
	 */
	public void setAssetNumber(String assetNumber){
		this.assetNumber = assetNumber;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 COST
	 * @param cost String
	 */
	public void setCost(String cost){
		this.cost = cost;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 DEPRN_COST
	 * @param deprnCost String
	 */
	public void setDeprnCost(String deprnCost){
		this.deprnCost = deprnCost;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 ASSETS_STATUS
	 * @param assetsStatus String
	 */
	public void setAssetsStatus(String assetsStatus){
		this.assetsStatus = assetsStatus;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 BOOK_TYPE_CODE
	 * @param bookTypeCode String
	 */
	public void setBookTypeCode(String bookTypeCode){
		this.bookTypeCode = bookTypeCode;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 ASSIGNED_TO_NAME
	 * @param assignedToName String
	 */
	public void setAssignedToName(String assignedToName){
		this.assignedToName = assignedToName;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 ASSIGNED_TO_NUMBER
	 * @param assignedToNumber String
	 */
	public void setAssignedToNumber(String assignedToNumber){
		this.assignedToNumber = assignedToNumber;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 SEGMENT1
	 * @param segment1 String
	 */
	public void setSegment1(String segment1){
		this.segment1 = segment1;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 SEGMENT2
	 * @param segment2 String
	 */
	public void setSegment2(String segment2){
		this.segment2 = segment2;
	}

	/**
	 * 功能：设置AMS_ASSETS_ADDRESS_V属性 CODE_COMBINATION_ID
	 * @param codeCombinationId String
	 */
	public void setCodeCombinationId(String codeCombinationId){
		this.codeCombinationId = codeCombinationId;
	}

	public void setScrapValue(String scrapValue) {
		this.scrapValue = scrapValue;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public void setItemStatusName(String itemStatusName) {
		this.itemStatusName = itemStatusName;
	}

	public void setDepreciation(String depreciation) {
		this.depreciation = depreciation;
	}

	public void setDepreciationAccount(String depreciationAccount) {
		this.depreciationAccount = depreciationAccount;
	}

	public void setDepreAccountOption(String depreAccountOption) {
		this.depreAccountOption = depreAccountOption;
	}

	public void setFaCategoryOption(String faCategoryOption) {
		this.faCategoryOption = faCategoryOption;
	}

	public void setFaCategoryName(String faCategoryName) {
		this.faCategoryName = faCategoryName;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 FA_CATEGORY1
	 * @return String
	 */
	public String getFaCategory1() {
		return this.faCategory1;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 FA_CATEGORY2
	 * @return String
	 */
	public String getFaCategory2() {
		return this.faCategory2;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 ASSETS_DESCRIPTION
	 * @return String
	 */
	public String getAssetsDescription() {
		return this.assetsDescription;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 FA_CATEGORY_CODE
	 * @return String
	 */
	public String getFaCategoryCode() {
		return this.faCategoryCode;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 UNIT_OF_MEASURE
	 * @return String
	 */
	public String getUnitOfMeasure() {
		return this.unitOfMeasure;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 LIFE_IN_YEARS
	 * @return String
	 */
	public String getLifeInYears() {
		return this.lifeInYears;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 MODEL_NUMBER
	 * @return String
	 */
	public String getModelNumber() {
		return this.modelNumber;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 TAG_NUMBER
	 * @return String
	 */
	public String getTagNumber() {
		return this.tagNumber;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 CURRENT_UNITS
	 * @return String
	 */
	public String getCurrentUnits() {
		return this.currentUnits;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 ASSETS_LOCATION
	 * @return String
	 */
	public String getAssetsLocation() {
		return this.assetsLocation;
	}


	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 DATE_PLACED_IN_SERVICE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getDatePlacedInService() throws CalendarException {
		datePlacedInService.setCalPattern(getCalPattern());
		return this.datePlacedInService;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 IS_RETIREMENTS
	 * @return String
	 */
	public String getIsRetirements() {
		return this.isRetirements;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 ASSET_NUMBER
	 * @return String
	 */
	public String getAssetNumber() {
		return this.assetNumber;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 COST
	 * @return String
	 */
	public String getCost() {
		return this.cost;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 DEPRN_COST
	 * @return String
	 */
	public String getDeprnCost() {
		return this.deprnCost;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 ASSETS_STATUS
	 * @return String
	 */
	public String getAssetsStatus() {
		return this.assetsStatus;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 BOOK_TYPE_CODE
	 * @return String
	 */
	public String getBookTypeCode() {
		return this.bookTypeCode;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 ASSIGNED_TO_NAME
	 * @return String
	 */
	public String getAssignedToName() {
		return this.assignedToName;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 ASSIGNED_TO_NUMBER
	 * @return String
	 */
	public String getAssignedToNumber() {
		return this.assignedToNumber;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 SEGMENT1
	 * @return String
	 */
	public String getSegment1() {
		return this.segment1;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 SEGMENT2
	 * @return String
	 */
	public String getSegment2() {
		return this.segment2;
	}

	/**
	 * 功能：获取AMS_ASSETS_ADDRESS_V属性 CODE_COMBINATION_ID
	 * @return String
	 */
	public String getCodeCombinationId() {
		return this.codeCombinationId;
	}

	public String getScrapValue() {
		return scrapValue;
	}

	public String getTransferType() {
		return transferType;
	}

	public String getItemStatusName() {
		return itemStatusName;
	}

	/**
	 * 功能：获取累计折旧
	 * @return String
	 */
	public String getDepreciation() {
		return depreciation;
	}

	public String getDepreciationAccount() {
		return depreciationAccount;
	}

	public String getDepreAccountOption() {
		return depreAccountOption;
	}

	public String getFaCategoryOption() {
		return faCategoryOption;
	}

	public String getFaCategoryName() {
		return faCategoryName;
	}
}
