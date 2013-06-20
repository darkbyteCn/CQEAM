package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 低值易耗盘点工单行表 EamDhCheckLine</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EamDhCheckLineDTO extends EamDhCheckHeaderDTO{

	private String barcode = "";
	private String itemCode = "";
	private String itemCategory = "";
	private String itemName = "";
	private String itemSpec = "";
	private String itemCategory2 = "";
	private String itemCategory2Value = "";
	private String catalogValueId = "";
	private int price = 0;
	private int responsibilityUser = SyBaseSQLUtil.NULL_INT_VALUE;
	private String responsibilityUserName = "";
	private String responsibilityDept = "";
	private String responsibilityDeptName = "";
	private String maintainUser = "";
	private int vendorId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String vendorName = "";
	private String scanItemCode = "";
	private String scanItemCategory = "";
	private String scanItemName = "";
	private String scanItemSpec = "";
	private String scanItemCategory2 = "";
	private String scanCatalogValueId = "";
	private float scanPrice = 0;
	private SimpleCalendar scanStartDate = null;
	private int scanResponsibilityUser = SyBaseSQLUtil.NULL_INT_VALUE ;
	private String scanResponsibilityUserName = "";
	private String scanResponsibilityDept = "";
	private String scanResponsibilityDeptName = "";
	private String scanMaintainUser = "";
	private int scanVendorId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String scanVendorName = "";
	private String archItemCode = "";
	private String archItemCategory = "";
	private String archItemName = "";
	private String archItemSpec = "";
	private String archItemCategory2 = "";
	private String archCatalogValueId = "";
	private float archPrice = 0;
	private SimpleCalendar archStartDate = null;
	private int archResponsibilityUser = SyBaseSQLUtil.NULL_INT_VALUE;
	private String archResponsibilityDept = "";
	private String archResponsibilityUserName = "";
	private String archResponsibilityDeptName = "";
	private String archMaintainUser = "";
	private int archVendorId = SyBaseSQLUtil.NULL_INT_VALUE;
	private String archVendorName = "";
	private String itemCodeDiffReason = "";
	private String addressDiffReason = "";
	private String userDiffReason = "";
	private String deptDiffReason = "";
	private String category2DiffReason = "";
	private String vendorDiffReason = "";
	private String priceDiffReason = "";
	private String startDateDiffReason = "";
	private String mainUserDiffReason = "";
	private int confirmUser = SyBaseSQLUtil.NULL_INT_VALUE;
	private SimpleCalendar confirmDate = null;
	private String systemStatus = "";
	private String scanStatus = "";
	private String archiveStatus = "";
	private String archiveRemark = "";
	private String archToTempInv = "";
	private String confirmUserName = "";

	private SimpleCalendar scanDate = null;
	private String netUnit = "0000";
	private String boxNo = "0000";
	private String itemCategoryValue = "";
	private String scanItemCategoryValue = "";
	private String archItemCategoryValue = "";

	public EamDhCheckLineDTO() {
		super();
		this.scanStartDate = new SimpleCalendar();
		this.archStartDate = new SimpleCalendar();
		this.confirmDate = new SimpleCalendar();
		this.scanDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 标签号
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 原分类代码
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 原设备分类
	 * @param itemCategory String
	 */
	public void setItemCategory(String itemCategory){
		this.itemCategory = itemCategory;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 原设备名称
	 * @param itemName String
	 */
	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 原规格型号
	 * @param itemSpec String
	 */
	public void setItemSpec(String itemSpec){
		this.itemSpec = itemSpec;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 原目录编号
	 * @param itemCategory2 String
	 */
	public void setItemCategory2(String itemCategory2){
		this.itemCategory2 = itemCategory2;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 原单价
	 * @param price String
	 */
	public void setPrice(int price){
		this.price = price;
	}


	/**
	 * 功能：设置低值易耗盘点工单行表属性 原责任人ID
	 * @param responsibilityUser String
	 */
	public void setResponsibilityUser(int responsibilityUser){
		this.responsibilityUser = responsibilityUser;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 原责任人姓名
	 * @param responsibilityUserName String
	 */
	public void setResponsibilityUserName(String responsibilityUserName){
		this.responsibilityUserName = responsibilityUserName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 责任部门代码
	 * @param responsibilityDept String
	 */
	public void setResponsibilityDept(String responsibilityDept){
		this.responsibilityDept = responsibilityDept;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 责任部门名称
	 * @param responsibilityDeptName String
	 */
	public void setResponsibilityDeptName(String responsibilityDeptName){
		this.responsibilityDeptName = responsibilityDeptName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 原使用人
	 * @param maintainUser String
	 */
	public void setMaintainUser(String maintainUser){
		this.maintainUser = maintainUser;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 原厂家ID
	 * @param vendorId String
	 */
	public void setVendorId(int vendorId){
		this.vendorId = vendorId;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 原厂家名称
	 * @param vendorName String
	 */
	public void setVendorName(String vendorName){
		this.vendorName = vendorName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的分类代码
	 * @param scanItemCode String
	 */
	public void setScanItemCode(String scanItemCode){
		this.scanItemCode = scanItemCode;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的设备分类
	 * @param scanItemCategory String
	 */
	public void setScanItemCategory(String scanItemCategory){
		this.scanItemCategory = scanItemCategory;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的设备名称
	 * @param scanItemName String
	 */
	public void setScanItemName(String scanItemName){
		this.scanItemName = scanItemName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的规格型号
	 * @param scanItemSpec String
	 */
	public void setScanItemSpec(String scanItemSpec){
		this.scanItemSpec = scanItemSpec;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的目录编号
	 * @param scanItemCategory2 String
	 */
	public void setScanItemCategory2(String scanItemCategory2){
		this.scanItemCategory2 = scanItemCategory2;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的单价
	 * @param scanPrice String
	 */
	public void setScanPrice(float scanPrice){
		this.scanPrice = scanPrice;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的启用日期
	 * @param scanStartDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setScanStartDate(String scanStartDate) throws CalendarException{
		this.scanStartDate.setCalendarValue(scanStartDate);
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的责任人ID
	 * @param scanResponsibilityUser String
	 */
	public void setScanResponsibilityUser(int scanResponsibilityUser){
		this.scanResponsibilityUser = scanResponsibilityUser;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的责任人姓名
	 * @param scanResponsibilityUserName String
	 */
	public void setScanResponsibilityUserName(String scanResponsibilityUserName){
		this.scanResponsibilityUserName = scanResponsibilityUserName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的责任部门代码
	 * @param scanResponsibilityDept String
	 */
	public void setScanResponsibilityDept(String scanResponsibilityDept){
		this.scanResponsibilityDept = scanResponsibilityDept;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的责任部门名称
	 * @param scanResponsibilityDeptName String
	 */
	public void setScanResponsibilityDeptName(String scanResponsibilityDeptName){
		this.scanResponsibilityDeptName = scanResponsibilityDeptName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的使用人
	 * @param scanMaintainUser String
	 */
	public void setScanMaintainUser(String scanMaintainUser){
		this.scanMaintainUser = scanMaintainUser;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的厂家ID
	 * @param scanVendorId String
	 */
	public void setScanVendorId(int scanVendorId){
		this.scanVendorId = scanVendorId;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描提交的厂家名称
	 * @param scanVendorName String
	 */
	public void setScanVendorName(String scanVendorName){
		this.scanVendorName = scanVendorName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档分类代码
	 * @param archItemCode String
	 */
	public void setArchItemCode(String archItemCode){
		this.archItemCode = archItemCode;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档设备分类
	 * @param archItemCategory String
	 */
	public void setArchItemCategory(String archItemCategory){
		this.archItemCategory = archItemCategory;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档设备名称
	 * @param archItemName String
	 */
	public void setArchItemName(String archItemName){
		this.archItemName = archItemName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档设备型号
	 * @param archItemSpec String
	 */
	public void setArchItemSpec(String archItemSpec){
		this.archItemSpec = archItemSpec;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档目录编号
	 * @param archItemCategory2 String
	 */
	public void setArchItemCategory2(String archItemCategory2){
		this.archItemCategory2 = archItemCategory2;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档单价
	 * @param archPrice String
	 */
	public void setArchPrice(float archPrice){
		this.archPrice = archPrice;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档启用日期
	 * @param archStartDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setArchStartDate(String archStartDate) throws CalendarException{
		this.archStartDate.setCalendarValue(archStartDate);
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档责任人ID
	 * @param archResponsibilityUser String
	 */
	public void setArchResponsibilityUser(int archResponsibilityUser){
		this.archResponsibilityUser = archResponsibilityUser;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档责任部门代码
	 * @param archResponsibilityDept String
	 */
	public void setArchResponsibilityDept(String archResponsibilityDept){
		this.archResponsibilityDept = archResponsibilityDept;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档责任人姓名
	 * @param archResponsibilityUserName String
	 */
	public void setArchResponsibilityUserName(String archResponsibilityUserName){
		this.archResponsibilityUserName = archResponsibilityUserName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档责任部门名称
	 * @param archResponsibilityDeptName String
	 */
	public void setArchResponsibilityDeptName(String archResponsibilityDeptName){
		this.archResponsibilityDeptName = archResponsibilityDeptName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档使用人
	 * @param archMaintainUser String
	 */
	public void setArchMaintainUser(String archMaintainUser){
		this.archMaintainUser = archMaintainUser;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档厂家ID
	 * @param archVendorId String
	 */
	public void setArchVendorId(int archVendorId){
		this.archVendorId = archVendorId;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档厂家名称
	 * @param archVendorName String
	 */
	public void setArchVendorName(String archVendorName){
		this.archVendorName = archVendorName;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 设备类别变更原因
	 * @param itemCodeDiffReason String
	 */
	public void setItemCodeDiffReason(String itemCodeDiffReason){
		this.itemCodeDiffReason = itemCodeDiffReason;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 地点变更到在途库原因
	 * @param addressDiffReason String
	 */
	public void setAddressDiffReason(String addressDiffReason){
		this.addressDiffReason = addressDiffReason;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 责任人变更原因
	 * @param userDiffReason String
	 */
	public void setUserDiffReason(String userDiffReason){
		this.userDiffReason = userDiffReason;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 责任部门变更原因
	 * @param deptDiffReason String
	 */
	public void setDeptDiffReason(String deptDiffReason){
		this.deptDiffReason = deptDiffReason;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 目录编号变更原因
	 * @param category2DiffReason String
	 */
	public void setCategory2DiffReason(String category2DiffReason){
		this.category2DiffReason = category2DiffReason;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 厂家变更原因
	 * @param vendorDiffReason String
	 */
	public void setVendorDiffReason(String vendorDiffReason){
		this.vendorDiffReason = vendorDiffReason;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 单价变更原因
	 * @param priceDiffReason String
	 */
	public void setPriceDiffReason(String priceDiffReason){
		this.priceDiffReason = priceDiffReason;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 启用日期变更原因
	 * @param startDateDiffReason String
	 */
	public void setStartDateDiffReason(String startDateDiffReason){
		this.startDateDiffReason = startDateDiffReason;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 使用人变更原因
	 * @param mainUserDiffReason String
	 */
	public void setMainUserDiffReason(String mainUserDiffReason){
		this.mainUserDiffReason = mainUserDiffReason;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 当前行WEB方式确认人
	 * @param confirmUser String
	 */
	public void setConfirmUser(int confirmUser){
		this.confirmUser = confirmUser;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 当前行WEB方式确认时间
	 * @param confirmDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setConfirmDate(String confirmDate) throws CalendarException{
		this.confirmDate.setCalendarValue(confirmDate);
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 系统状态(仅代表盘点地点有无该设备)
	 * @param systemStatus String
	 */
	public void setSystemStatus(String systemStatus){
		this.systemStatus = systemStatus;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 扫描状态(仅代表地点有无该设备)
	 * @param scanStatus String
	 */
	public void setScanStatus(String scanStatus){
		this.scanStatus = scanStatus;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 归档状态(0:以扫描结果为准；1:以目前状态为准)
	 * @param archiveStatus String
	 */
	public void setArchiveStatus(String archiveStatus){
		this.archiveStatus = archiveStatus;
	}
	/**
	 * 功能：设置低值易耗盘点工单行表属性 核实备注(以扫描结果为准；以目前状态为准)
	 * @param archiveRemark String
	 */
	public void setArchiveRemark(String archiveRemark){
		this.archiveRemark = archiveRemark;
	}

	/**
	 * 功能：设置低值易耗盘点工单行表属性 是否归档到在途库
	 * @param archToTempInv String
	 */
	public void setArchToTempInv(String archToTempInv){
		this.archToTempInv = archToTempInv;
	}

	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public void setNetUnit(String netUnit) {
		this.netUnit = netUnit;
	}

	public void setArchItemCategoryValue(String archItemCategoryValue) {
		this.archItemCategoryValue = archItemCategoryValue;
	}

	public void setItemCategoryValue(String itemCategoryValue) {
		this.itemCategoryValue = itemCategoryValue;
	}

	public void setScanItemCategoryValue(String scanItemCategoryValue) {
		this.scanItemCategoryValue = scanItemCategoryValue;
	}

	public void setItemCategory2Value(String itemCategory2Value) {
		this.itemCategory2Value = itemCategory2Value;
	}

	public void setArchCatalogValueId(String archCatalogValueId) {
		this.archCatalogValueId = archCatalogValueId;
	}

	public void setCatalogValueId(String catalogValueId) {
		this.catalogValueId = catalogValueId;
	}

	public void setScanCatalogValueId(String scanCatalogValueId) {
		this.scanCatalogValueId = scanCatalogValueId;
	}

	public void setScanDate(String scanDate) throws CalendarException {
		this.scanDate.setCalendarValue(scanDate);
	}


	/**
	 * 功能：获取低值易耗盘点工单行表属性 标签号
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 原分类代码
	 * @return String
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 原设备分类
	 * @return String
	 */
	public String getItemCategory() {
		return this.itemCategory;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 原设备名称
	 * @return String
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 原规格型号
	 * @return String
	 */
	public String getItemSpec() {
		return this.itemSpec;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 原目录编号
	 * @return String
	 */
	public String getItemCategory2() {
		return this.itemCategory2;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 原单价
	 * @return String
	 */

	public int getPrice() {
		return this.price;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 原责任人ID
	 * @return String
	 */
	public int getResponsibilityUser() {
		return this.responsibilityUser;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 原责任人姓名
	 * @return String
	 */
	public String getResponsibilityUserName() {
		return this.responsibilityUserName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 责任部门代码
	 * @return String
	 */
	public String getResponsibilityDept() {
		return this.responsibilityDept;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 责任部门名称
	 * @return String
	 */
	public String getResponsibilityDeptName() {
		return this.responsibilityDeptName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 原使用人
	 * @return String
	 */
	public String getMaintainUser() {
		return this.maintainUser;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 原厂家ID
	 * @return String
	 */
	public int getVendorId() {
		return this.vendorId;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 原厂家名称
	 * @return String
	 */
	public String getVendorName() {
		return this.vendorName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的分类代码
	 * @return String
	 */
	public String getScanItemCode() {
		return this.scanItemCode;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的设备分类
	 * @return String
	 */
	public String getScanItemCategory() {
		return this.scanItemCategory;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的设备名称
	 * @return String
	 */
	public String getScanItemName() {
		return this.scanItemName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的规格型号
	 * @return String
	 */
	public String getScanItemSpec() {
		return this.scanItemSpec;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的目录编号
	 * @return String
	 */
	public String getScanItemCategory2() {
		return this.scanItemCategory2;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的单价
	 * @return String
	 */
	public float getScanPrice() {
		return this.scanPrice;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getScanStartDate() throws CalendarException {
		scanStartDate.setCalPattern(getCalPattern());
		return this.scanStartDate;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的责任人ID
	 * @return String
	 */
	public int getScanResponsibilityUser() {
		return this.scanResponsibilityUser;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的责任人姓名
	 * @return String
	 */
	public String getScanResponsibilityUserName() {
		return this.scanResponsibilityUserName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的责任部门代码
	 * @return String
	 */
	public String getScanResponsibilityDept() {
		return this.scanResponsibilityDept;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的责任部门名称
	 * @return String
	 */
	public String getScanResponsibilityDeptName() {
		return this.scanResponsibilityDeptName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的使用人
	 * @return String
	 */
	public String getScanMaintainUser() {
		return this.scanMaintainUser;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的厂家ID
	 * @return String
	 */
	public int getScanVendorId() {
		return this.scanVendorId;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描提交的厂家名称
	 * @return String
	 */
	public String getScanVendorName() {
		return this.scanVendorName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档分类代码
	 * @return String
	 */
	public String getArchItemCode() {
		return this.archItemCode;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档设备分类
	 * @return String
	 */
	public String getArchItemCategory() {
		return this.archItemCategory;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档设备名称
	 * @return String
	 */
	public String getArchItemName() {
		return this.archItemName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档设备型号
	 * @return String
	 */
	public String getArchItemSpec() {
		return this.archItemSpec;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档目录编号
	 * @return String
	 */
	public String getArchItemCategory2() {
		return this.archItemCategory2;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档单价
	 * @return String
	 */
	public float getArchPrice() {
		return this.archPrice;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档启用日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getArchStartDate() throws CalendarException {
		archStartDate.setCalPattern(getCalPattern());
		return this.archStartDate;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档责任人ID
	 * @return String
	 */
	public int getArchResponsibilityUser() {
		return this.archResponsibilityUser;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档责任部门代码
	 * @return String
	 */
	public String getArchResponsibilityDept() {
		return this.archResponsibilityDept;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档责任人姓名
	 * @return String
	 */
	public String getArchResponsibilityUserName() {
		return this.archResponsibilityUserName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档责任部门名称
	 * @return String
	 */
	public String getArchResponsibilityDeptName() {
		return this.archResponsibilityDeptName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档使用人
	 * @return String
	 */
	public String getArchMaintainUser() {
		return this.archMaintainUser;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档厂家ID
	 * @return String
	 */
	public int getArchVendorId() {
		return this.archVendorId;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档厂家名称
	 * @return String
	 */
	public String getArchVendorName() {
		return this.archVendorName;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 设备类别变更原因
	 * @return String
	 */
	public String getItemCodeDiffReason() {
		return this.itemCodeDiffReason;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 地点变更到在途库原因
	 * @return String
	 */
	public String getAddressDiffReason() {
		return this.addressDiffReason;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 责任人变更原因
	 * @return String
	 */
	public String getUserDiffReason() {
		return this.userDiffReason;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 责任部门变更原因
	 * @return String
	 */
	public String getDeptDiffReason() {
		return this.deptDiffReason;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 目录编号变更原因
	 * @return String
	 */
	public String getCategory2DiffReason() {
		return this.category2DiffReason;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 厂家变更原因
	 * @return String
	 */
	public String getVendorDiffReason() {
		return this.vendorDiffReason;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 单价变更原因
	 * @return String
	 */
	public String getPriceDiffReason() {
		return this.priceDiffReason;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 启用日期变更原因
	 * @return String
	 */
	public String getStartDateDiffReason() {
		return this.startDateDiffReason;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 使用人变更原因
	 * @return String
	 */
	public String getMainUserDiffReason() {
		return this.mainUserDiffReason;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 当前行WEB方式确认人
	 * @return String
	 */
	public int getConfirmUser() {
		return this.confirmUser;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 当前行WEB方式确认时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getConfirmDate() throws CalendarException {
		confirmDate.setCalPattern(getCalPattern());
		return this.confirmDate;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 系统状态(仅代表盘点地点有无该设备)
	 * @return String
	 */
	public String getSystemStatus() {
		return this.systemStatus;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 扫描状态(仅代表地点有无该设备)
	 * @return String
	 */
	public String getScanStatus() {
		return this.scanStatus;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 归档状态(0:以扫描结果为准；1:以目前状态为准)
	 * @return String
	 */
	public String getArchiveStatus() {
		return this.archiveStatus;
	}
	/**
	 * 功能：获取低值易耗盘点工单行表属性 核实备注(以扫描结果为准；以目前状态为准)
	 * @return String
	 */
	public String getArchiveRemark() {
		return this.archiveRemark;
	}

	/**
	 * 功能：获取低值易耗盘点工单行表属性 是否归档到在途库
	 * @return String
	 */
	public String getArchToTempInv() {
		return this.archToTempInv;
	}

	public String getConfirmUserName() {
		return confirmUserName;
	}

	public String getBoxNo() {
		return boxNo;
	}

	public String getNetUnit() {
		return netUnit;
	}

	public SimpleCalendar getScanDate() throws CalendarException {
		scanDate.setCalPattern(getCalPattern());
		return scanDate;
	}

	public String getArchItemCategoryValue() {
		return archItemCategoryValue;
	}

	public String getItemCategoryValue() {
		return itemCategoryValue;
	}

	public String getScanItemCategoryValue() {
		return scanItemCategoryValue;
	}

	public String getItemCategory2Value() {
		return itemCategory2Value;
	}

	public String getArchCatalogValueId() {
		return archCatalogValueId;
	}

	public String getCatalogValueId() {
		return catalogValueId;
	}

	public String getScanCatalogValueId() {
		return scanCatalogValueId;
	}
}
