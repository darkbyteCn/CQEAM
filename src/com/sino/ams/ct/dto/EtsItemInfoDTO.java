package com.sino.ams.ct.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: 村通设备管理 EtsItemInfo</p>
 * <p>Description: 程序自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 * @author 于士博
 * @version 1.0
 */
public class EtsItemInfoDTO extends CommonRecordDTO {

	private String systemid = "";  //系统ID(序列号)
	
	private String faBarcode = ""; //对应MIS固定资产条码;禁止使用字段
	
	private String vendorBarcode = ""; //对应厂商条码
	
	private int itemQty ; //设备数量
	
	private SimpleCalendar disableDate = null; //失效日期

	private String remark = ""; //备注
	
	private SimpleCalendar startDate = null; //启用日期

	private String itemCode = ""; //分类编码
	
	private int projectid ; //工程ID
	 
	private String itemStatus = ""; //设备状态
	
	private String attribute1 = ""; //扩展属性1;租赁属性
	
	private String attribute2 = ""; //扩展属性2
	
	private String sendtomisFlag = ""; //同步到MIS标志
	
	private String misItemname = ""; //MIS设备名称
	
	private String misItemspec = ""; //MIS规格型号
	
	private String assetId = ""; //所属资产ID
	
	private String addressId = ""; //资产地点ID
	
	private int financeProp = 0; //财务属性
	
	private String attribute3 = ""; //扩展属性3(仪器仪表用途)
	
	private String parentBarcode = ""; //父标签号
	
	private SimpleCalendar lastLocChgDate = null; //设备地点最后变动时间
	
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE; //组织ID
	
	private String barcode = ""; //标签号
	
	private String isParent = ""; //是否是父设备
	
	private int responsibilityUser ; //责任人
	
	private String responsibilityDept = ""; //责任部门
	
	private String maintainUser = ""; //维护人员
	
	private String maintainDept = ""; //使用部门
	
	private String price = ""; //单价
	
	private String isTmp = ""; //1:临时设备
	
	private String qryType = ""; //此属性用来判断按那种条件进行查询，例如：按条码查询或者按设备名称查询
	
	private SimpleCalendar minTime = null; //最小启用日期
	
    private SimpleCalendar maxTime = null; //最大启用日期
	
	private String daiwei = ""; //代维公司
	
	//=====================================以下是ETS_SYSTEM_ITEM表中的属性======================================//
	private String itemCategory2 = ""; //目录编号
	
	//=====================================以下是父类的DTO要用到的子类的属性=======================================//
	
	private int objectCategory = 0; //地点类型
	
	private int itemCategory; //设备分类
	
	private String itemName = ""; //设备名称
	
	private String name = ""; //工程名字
	private String projectName = ""; //工程名字
	
	private String itemSpec = ""; //设备型号
	
	private String workorderObjectName = ""; //地点名称
	
	private int invType = 0; //ETS_FLEX_VALUE_SET表中CODE字段说对应的值的名字是仓库类型，即：字典集合
	
	private String countyCode = ""; //ETS_COUNTY表中的数据字典，为查询城市名称
	
	private String workorderObjectNo = ""; //ETS_OBJECT表的主键
	
	private String key = ""; //村通资产匹配页面用到的查询条件框

	private String assetNumber = "";
	private String isRetirements = "";
	private String itemCategoryName = "";
	private String workorderObjectCode = "";
	private String employeeNumber = "";
	private String responsibilityUserName = "";
	private String responsibilityDeptName = "";
	private String maintainUserName = "";
	private String vendorName = "";
	private String itemStatusName = "";
	private String tagNumber = "";
	private String faCategory1 = "";
	private String faCategory2 = "";
	private String assetsDescription = "";
	private String modelNumber = "";
	private String currentUnits = "";
	private String unitOfMeasure = "";
	private String assetsLocationCode = "";
	private String assetsLocation = "";
	private String assignedToNumber = "";
	private String assignedToName = "";
	private String bookTypeCode = "";
	private String bookTypeName = "";
	private String depreciationAccount = "";
	private String depreciationAccountName = "";
	private SimpleCalendar assetsCreateDate = null;
	private SimpleCalendar datePlacedInService = null;
	private String cost = "";
	private String originalCost = "";
	private String cost0 = "";
	private String depreciation = "";
	private String deprnCost = "";
	private String lifeInYears = "";
	private String deptName = "";
	private String company = "";
	

	public EtsItemInfoDTO() {
		super();
		this.disableDate = new SimpleCalendar();
		this.startDate = new SimpleCalendar();
		this.lastLocChgDate = new SimpleCalendar();
		this.minTime = new SimpleCalendar();
		this.maxTime = new SimpleCalendar();
		this.assetsCreateDate = new SimpleCalendar();
		this.datePlacedInService = new SimpleCalendar();
	}
	
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getWorkorderObjectNo() {
		return workorderObjectNo;
	}

	public void setWorkorderObjectNo(String workorderObjectNo) {
		this.workorderObjectNo = workorderObjectNo;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public int getInvType() {
		return invType;
	}

	public void setInvType(int invType) {
		this.invType = invType;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	public int getObjectCategory() {
		return objectCategory;
	}

	public void setObjectCategory(int objectCategory) {
		this.objectCategory = objectCategory;
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 系统ID(序列号)
	 * @param systemid String
	 */
	public void setSystemid(String systemid){
		this.systemid = systemid;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 对应MIS固定资产条码;禁止使用字段
	 * @param faBarcode String
	 */
	public void setFaBarcode(String faBarcode){
		this.faBarcode = faBarcode;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 对应厂商条码
	 * @param vendorBarcode String
	 */
	public void setVendorBarcode(String vendorBarcode){
		this.vendorBarcode = vendorBarcode;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 设备数量
	 * @param itemQty String
	 */
	public void setItemQty(int itemQty){
		this.itemQty = itemQty;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 失效日期
	 * @param disableDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setDisableDate(String disableDate) throws CalendarException{
		this.disableDate.setCalendarValue(disableDate);
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 启用日期

	 * @param startDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartDate(String startDate) throws CalendarException{
		this.startDate.setCalendarValue(startDate);
	}
	
	public void setAssetsCreateDate(String assetsCreateDate) throws CalendarException{
		this.assetsCreateDate.setCalendarValue(assetsCreateDate);
	}

	public void setDatePlacedInService(String datePlacedInService) throws CalendarException{
		this.datePlacedInService.setCalendarValue(datePlacedInService);
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 分类编码
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 工程ID
	 * @param projectid String
	 */
	public void setProjectid(int projectid){
		this.projectid = projectid;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 设备状态
	 * @param itemStatus String
	 */
	public void setItemStatus(String itemStatus){
		this.itemStatus = itemStatus;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 扩展属性1;租赁属性
	 * @param attribute1 String
	 */
	public void setAttribute1(String attribute1){
		this.attribute1 = attribute1;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 扩展属性2
	 * @param attribute2 String
	 */
	public void setAttribute2(String attribute2){
		this.attribute2 = attribute2;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 同步到MIS标志
	 * @param sendtomisFlag String
	 */
	public void setSendtomisFlag(String sendtomisFlag){
		this.sendtomisFlag = sendtomisFlag;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 MIS设备名称
	 * @param misItemname String
	 */
	public void setMisItemname(String misItemname){
		this.misItemname = misItemname;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 MIS规格型号
	 * @param misItemspec String
	 */
	public void setMisItemspec(String misItemspec){
		this.misItemspec = misItemspec;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 所属资产ID
	 * @param assetId String
	 */
	public void setAssetId(String assetId){
		this.assetId = assetId;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 资产地点ID
	 * @param addressId String
	 */
	public void setAddressId(String addressId){
		this.addressId = addressId;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 财务属性
	 * @param financeProp String
	 */
	public void setFinanceProp(int financeProp){
		this.financeProp = financeProp;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 扩展属性3(仪器仪表用途)
	 * @param attribute3 String
	 */
	public void setAttribute3(String attribute3){
		this.attribute3 = attribute3;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 父标签号
	 * @param parentBarcode String
	 */
	public void setParentBarcode(String parentBarcode){
		this.parentBarcode = parentBarcode;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 设备地点最后变动时间
	 * @param lastLocChgDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastLocChgDate(String lastLocChgDate) throws CalendarException{
		this.lastLocChgDate.setCalendarValue(lastLocChgDate);
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 组织ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 标签号
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 是否是父设备
	 * @param isParent String
	 */
	public void setIsParent(String isParent){
		this.isParent = isParent;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 责任人
	 * @param responsibilityUser String
	 */
	public void setResponsibilityUser(int responsibilityUser){
		this.responsibilityUser = responsibilityUser;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 责任部门
	 * @param responsibilityDept String
	 */
	public void setResponsibilityDept(String responsibilityDept){
		this.responsibilityDept = responsibilityDept;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 维护人员
	 * @param maintainUser String
	 */
	public void setMaintainUser(String maintainUser){
		this.maintainUser = maintainUser;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 使用部门
	 * @param maintainDept String
	 */
	public void setMaintainDept(String maintainDept){
		this.maintainDept = maintainDept;
	}

	/**
	 * 功能：设置标签号信息(EAM)属性 1:临时设备
	 * @param isTmp String
	 */
	public void setIsTmp(String isTmp){
		this.isTmp = isTmp;
	}


	/**
	 * 功能：获取标签号信息(EAM)属性 系统ID(序列号)
	 * @return String
	 */
	public String getSystemid() {
		return this.systemid;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 对应MIS固定资产条码;禁止使用字段
	 * @return String
	 */
	public String getFaBarcode() {
		return this.faBarcode;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 对应厂商条码
	 * @return String
	 */
	public String getVendorBarcode() {
		return this.vendorBarcode;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 设备数量
	 * @return String
	 */
	public int getItemQty() {
		return this.itemQty;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 失效日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getDisableDate() {
		try {
			disableDate.setCalPattern(getCalPattern());
		} catch (CalendarException e) {
			e.printLog();
		}
		return this.disableDate;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 启用日期

	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getStartDate() throws CalendarException {
		startDate.setCalPattern(getCalPattern());
		return this.startDate;
	}
	
	public SimpleCalendar getAssetsCreateDate() throws CalendarException {
		assetsCreateDate.setCalPattern(getCalPattern());
		return this.assetsCreateDate;
	}
	
	public SimpleCalendar getDatePlacedInService() throws CalendarException {
		datePlacedInService.setCalPattern(getCalPattern());
		return this.datePlacedInService;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 分类编码
	 * @return String
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 工程ID
	 * @return String
	 */
	public int getProjectid() {
		return this.projectid;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 设备状态
	 * @return String
	 */
	public String getItemStatus() {
		return this.itemStatus;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 扩展属性1;租赁属性
	 * @return String
	 */
	public String getAttribute1() {
		return this.attribute1;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 扩展属性2
	 * @return String
	 */
	public String getAttribute2() {
		return this.attribute2;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 同步到MIS标志
	 * @return String
	 */
	public String getSendtomisFlag() {
		return this.sendtomisFlag;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 MIS设备名称
	 * @return String
	 */
	public String getMisItemname() {
		return this.misItemname;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 MIS规格型号
	 * @return String
	 */
	public String getMisItemspec() {
		return this.misItemspec;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 所属资产ID
	 * @return String
	 */
	public String getAssetId() {
		return this.assetId;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 资产地点ID
	 * @return String
	 */
	public String getAddressId() {
		return this.addressId;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 财务属性
	 * @return String
	 */
	public int getFinanceProp() {
		return this.financeProp;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 扩展属性3(仪器仪表用途)
	 * @return String
	 */
	public String getAttribute3() {
		return this.attribute3;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 父标签号
	 * @return String
	 */
	public String getParentBarcode() {
		return this.parentBarcode;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 设备地点最后变动时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastLocChgDate() throws CalendarException {
		lastLocChgDate.setCalPattern(getCalPattern());
		return this.lastLocChgDate;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 组织ID
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 标签号
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 是否是父设备
	 * @return String
	 */
	public String getIsParent() {
		return this.isParent;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 责任人
	 * @return String
	 */
	public int getResponsibilityUser() {
		return this.responsibilityUser;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 责任部门
	 * @return String
	 */
	public String getResponsibilityDept() {
		return this.responsibilityDept;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 维护人员
	 * @return String
	 */
	public String getMaintainUser() {
		return this.maintainUser;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 使用部门
	 * @return String
	 */
	public String getMaintainDept() {
		return this.maintainDept;
	}

	/**
	 * 功能：获取标签号信息(EAM)属性 1:临时设备
	 * @return String
	 */
	public String getIsTmp() {
		return this.isTmp;
	}
	
	public void setQryType(String qryType) {
		this.qryType = qryType;
	}
	
	public String getQryType() {
		return this.qryType;
	}

	public String getDaiwei() {
		return daiwei;
	}

	public void setDaiwei(String daiwei) {
		this.daiwei = daiwei;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getWorkorderObjectName() {
		return workorderObjectName;
	}

	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}
	
	public SimpleCalendar getMaxTime() {
		return maxTime;
	}
	
	public void setMaxTime(String maxTime) {
		this.maxTime = new SimpleCalendar(maxTime);
	}

	/*
	public SimpleCalendar getMaxTime() throws CalendarException {
		maxTime.setCalPattern(getCalPattern());
		return this.maxTime;
	}

	public void setMaxTime(String maxTime) throws CalendarException {
		this.maxTime.setCalendarValue(maxTime);
	}
	*/

	public SimpleCalendar getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = new SimpleCalendar(minTime);
    }
    /*
	public SimpleCalendar getMinTime() throws CalendarException {
		minTime.setCalPattern(getCalPattern());
		return this.minTime;
	}

	public void setMinTime(String minTime) throws CalendarException {
		this.minTime.setCalendarValue(minTime);
	}
	*/

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getItemCategory2() {
		return itemCategory2;
	}

	public void setItemCategory2(String itemCategory2) {
		this.itemCategory2 = itemCategory2;
	}



	public String getAssetNumber() {
		return assetNumber;
	}



	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}



	public String getIsRetirements() {
		return isRetirements;
	}



	public void setIsRetirements(String isRetirements) {
		this.isRetirements = isRetirements;
	}



	public String getItemCategoryName() {
		return itemCategoryName;
	}



	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}



	public String getWorkorderObjectCode() {
		return workorderObjectCode;
	}

	public void setWorkorderObjectCode(String workorderObjectCode) {
		this.workorderObjectCode = workorderObjectCode;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getResponsibilityUserName() {
		return responsibilityUserName;
	}

	public void setResponsibilityUserName(String responsibilityUserName) {
		this.responsibilityUserName = responsibilityUserName;
	}

	public String getResponsibilityDeptName() {
		return responsibilityDeptName;
	}

	public void setResponsibilityDeptName(String responsibilityDeptName) {
		this.responsibilityDeptName = responsibilityDeptName;
	}

	public String getMaintainUserName() {
		return maintainUserName;
	}

	public void setMaintainUserName(String maintainUserName) {
		this.maintainUserName = maintainUserName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getItemStatusName() {
		return itemStatusName;
	}

	public void setItemStatusName(String itemStatusName) {
		this.itemStatusName = itemStatusName;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getFaCategory1() {
		return faCategory1;
	}

	public void setFaCategory1(String faCategory1) {
		this.faCategory1 = faCategory1;
	}

	public String getFaCategory2() {
		return faCategory2;
	}

	public void setFaCategory2(String faCategory2) {
		this.faCategory2 = faCategory2;
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

	public String getCurrentUnits() {
		return currentUnits;
	}

	public void setCurrentUnits(String currentUnits) {
		this.currentUnits = currentUnits;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getAssetsLocationCode() {
		return assetsLocationCode;
	}

	public void setAssetsLocationCode(String assetsLocationCode) {
		this.assetsLocationCode = assetsLocationCode;
	}

	public String getAssetsLocation() {
		return assetsLocation;
	}

	public void setAssetsLocation(String assetsLocation) {
		this.assetsLocation = assetsLocation;
	}

	public String getAssignedToNumber() {
		return assignedToNumber;
	}

	public void setAssignedToNumber(String assignedToNumber) {
		this.assignedToNumber = assignedToNumber;
	}

	public String getAssignedToName() {
		return assignedToName;
	}

	public void setAssignedToName(String assignedToName) {
		this.assignedToName = assignedToName;
	}

	public String getBookTypeCode() {
		return bookTypeCode;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	public String getDepreciationAccount() {
		return depreciationAccount;
	}

	public void setDepreciationAccount(String depreciationAccount) {
		this.depreciationAccount = depreciationAccount;
	}

	public String getDepreciationAccountName() {
		return depreciationAccountName;
	}

	public void setDepreciationAccountName(String depreciationAccountName) {
		this.depreciationAccountName = depreciationAccountName;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(String originalCost) {
		this.originalCost = originalCost;
	}

	public String getCost0() {
		return cost0;
	}

	public void setCost0(String cost0) {
		this.cost0 = cost0;
	}

	public String getDepreciation() {
		return depreciation;
	}

	public void setDepreciation(String depreciation) {
		this.depreciation = depreciation;
	}

	public String getDeprnCost() {
		return deprnCost;
	}

	public void setDeprnCost(String deprnCost) {
		this.deprnCost = deprnCost;
	}

	public String getLifeInYears() {
		return lifeInYears;
	}

	public void setLifeInYears(String lifeInYears) {
		this.lifeInYears = lifeInYears;
	}



	public String getDeptName() {
		return deptName;
	}



	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}



	public String getCompany() {
		return company;
	}



	public void setCompany(String company) {
		this.company = company;
	}

}
