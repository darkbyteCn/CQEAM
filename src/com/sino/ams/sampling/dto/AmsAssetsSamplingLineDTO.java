package com.sino.ams.sampling.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 抽查工单行表 AmsAssetsSamplingLine</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsAssetsSamplingLineDTO extends AmsAssetsSamplingHeaderDTO{

	private String barcode = "";
	private String systemStatus = "";
	private String scanStatus = "";
	private String remark = "";
	private String itemCode = "";
	private String itemCategory = "";
	private String itemCategoryValue = "";
	private String itemName = "";
	private String itemSpec = "";
	private String responsibilityUser = "";
	private String responsibilityUserName = "";
	private String responsibilityDept = "";
	private String responsibilityDeptName = "";
	private String maintainUser = "";
	private String scanItemCode = "";
	private String scanItemCategory = "";
	private String scanItemCategoryValue = "";
	private String scanItemName = "";
	private String scanItemSpec = "";
	private SimpleCalendar scanStartDate = null;
	private String scanResponsibilityUser = "";
	private String scanResponsibilityUserName = "";
	private String scanResponsibilityDept = "";
	private String scanResponsibilityDeptName = "";
	private String scanMaintainUser = "";

	private SimpleCalendar scanDate = null;
	private String netUnit = "0000";
	private String boxNo = "0000";

	public AmsAssetsSamplingLineDTO() {
		super();
		this.scanStartDate = new SimpleCalendar();
		this.scanDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置抽查工单行表属性 设备条码
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置抽查工单行表属性 系统状态
	 * @param systemStatus String
	 */
	public void setSystemStatus(String systemStatus){
		this.systemStatus = systemStatus;
	}

	/**
	 * 功能：设置抽查工单行表属性 扫描状态
	 * @param scanStatus String
	 */
	public void setScanStatus(String scanStatus){
		this.scanStatus = scanStatus;
	}

	/**
	 * 功能：设置抽查工单行表属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置抽查工单行表属性 设备分类代码(系统)
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置抽查工单行表属性 设备专业(系统)
	 * @param itemCategory String
	 */
	public void setItemCategory(String itemCategory){
		this.itemCategory = itemCategory;
	}

	/**
	 * 功能：设置抽查工单行表属性 设备名称(系统)
	 * @param itemName String
	 */
	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	/**
	 * 功能：设置抽查工单行表属性 设备型号(系统)
	 * @param itemSpec String
	 */
	public void setItemSpec(String itemSpec){
		this.itemSpec = itemSpec;
	}


	/**
	 * 功能：设置抽查工单行表属性 责任人(系统)
	 * @param responsibilityUser String
	 */
	public void setResponsibilityUser(String responsibilityUser){
		this.responsibilityUser = responsibilityUser;
	}

	/**
	 * 功能：设置抽查工单行表属性 责任部门(系统)
	 * @param responsibilityDept String
	 */
	public void setResponsibilityDept(String responsibilityDept){
		this.responsibilityDept = responsibilityDept;
	}

	/**
	 * 功能：设置抽查工单行表属性 使用人(系统)
	 * @param maintainUser String
	 */
	public void setMaintainUser(String maintainUser){
		this.maintainUser = maintainUser;
	}

	/**
	 * 功能：设置抽查工单行表属性 设备分类代码(扫描)
	 * @param scanItemCode String
	 */
	public void setScanItemCode(String scanItemCode){
		this.scanItemCode = scanItemCode;
	}

	/**
	 * 功能：设置抽查工单行表属性 设备专业(扫描)
	 * @param scanItemCategory String
	 */
	public void setScanItemCategory(String scanItemCategory){
		this.scanItemCategory = scanItemCategory;
	}

	/**
	 * 功能：设置抽查工单行表属性 设备名称(扫描)
	 * @param scanItemName String
	 */
	public void setScanItemName(String scanItemName){
		this.scanItemName = scanItemName;
	}

	/**
	 * 功能：设置抽查工单行表属性 设备型号(扫描)
	 * @param scanItemSpec String
	 */
	public void setScanItemSpec(String scanItemSpec){
		this.scanItemSpec = scanItemSpec;
	}

	/**
	 * 功能：设置抽查工单行表属性 启用日期(扫描)
	 * @param scanStartDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setScanStartDate(String scanStartDate) throws CalendarException{
		this.scanStartDate.setCalendarValue(scanStartDate);
	}

	/**
	 * 功能：设置抽查工单行表属性 责任人(扫描)
	 * @param scanResponsibilityUser String
	 */
	public void setScanResponsibilityUser(String scanResponsibilityUser){
		this.scanResponsibilityUser = scanResponsibilityUser;
	}

	/**
	 * 功能：设置抽查工单行表属性 责任部门(扫描)
	 * @param scanResponsibilityDept String
	 */
	public void setScanResponsibilityDept(String scanResponsibilityDept){
		this.scanResponsibilityDept = scanResponsibilityDept;
	}

	/**
	 * 功能：设置抽查工单行表属性 使用人(扫描)
	 * @param scanMaintainUser String
	 */
	public void setScanMaintainUser(String scanMaintainUser){
		this.scanMaintainUser = scanMaintainUser;
	}

	public void setResponsibilityDeptName(String responsibilityDeptName) {
		this.responsibilityDeptName = responsibilityDeptName;
	}

	public void setResponsibilityUserName(String responsibilityUserName) {
		this.responsibilityUserName = responsibilityUserName;
	}

	public void setItemCategoryValue(String itemCategoryValue) {
		this.itemCategoryValue = itemCategoryValue;
	}

	public void setScanItemCategoryValue(String scanItemCategoryValue) {
		this.scanItemCategoryValue = scanItemCategoryValue;
	}

	public void setScanResponsibilityDeptName(String scanResponsibilityDeptName) {
		this.scanResponsibilityDeptName = scanResponsibilityDeptName;
	}

	public void setScanResponsibilityUserName(String scanResponsibilityUserName) {
		this.scanResponsibilityUserName = scanResponsibilityUserName;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public void setNetUnit(String netUnit) {
		this.netUnit = netUnit;
	}

	public void setScanDate(String scanDate) throws CalendarException {
		this.scanDate.setCalendarValue(scanDate);
	}

	/**
	 * 功能：获取抽查工单行表属性 设备条码
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取抽查工单行表属性 系统状态
	 * @return String
	 */
	public String getSystemStatus() {
		return this.systemStatus;
	}

	/**
	 * 功能：获取抽查工单行表属性 扫描状态
	 * @return String
	 */
	public String getScanStatus() {
		return this.scanStatus;
	}

	/**
	 * 功能：获取抽查工单行表属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取抽查工单行表属性 设备分类代码(系统)
	 * @return String
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 功能：获取抽查工单行表属性 设备专业(系统)
	 * @return String
	 */
	public String getItemCategory() {
		return this.itemCategory;
	}

	/**
	 * 功能：获取抽查工单行表属性 设备名称(系统)
	 * @return String
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * 功能：获取抽查工单行表属性 设备型号(系统)
	 * @return String
	 */
	public String getItemSpec() {
		return this.itemSpec;
	}

	/**
	 * 功能：获取抽查工单行表属性 责任人(系统)
	 * @return String
	 */
	public String getResponsibilityUser() {
		return this.responsibilityUser;
	}

	/**
	 * 功能：获取抽查工单行表属性 责任部门(系统)
	 * @return String
	 */
	public String getResponsibilityDept() {
		return this.responsibilityDept;
	}

	/**
	 * 功能：获取抽查工单行表属性 使用人(系统)
	 * @return String
	 */
	public String getMaintainUser() {
		return this.maintainUser;
	}

	/**
	 * 功能：获取抽查工单行表属性 设备分类代码(扫描)
	 * @return String
	 */
	public String getScanItemCode() {
		return this.scanItemCode;
	}

	/**
	 * 功能：获取抽查工单行表属性 设备专业(扫描)
	 * @return String
	 */
	public String getScanItemCategory() {
		return this.scanItemCategory;
	}

	/**
	 * 功能：获取抽查工单行表属性 设备名称(扫描)
	 * @return String
	 */
	public String getScanItemName() {
		return this.scanItemName;
	}

	/**
	 * 功能：获取抽查工单行表属性 设备型号(扫描)
	 * @return String
	 */
	public String getScanItemSpec() {
		return this.scanItemSpec;
	}

	/**
	 * 功能：获取抽查工单行表属性 启用日期(扫描)
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getScanStartDate() throws CalendarException {
		scanStartDate.setCalPattern(getCalPattern());
		return this.scanStartDate;
	}

	/**
	 * 功能：获取抽查工单行表属性 责任人(扫描)
	 * @return String
	 */
	public String getScanResponsibilityUser() {
		return this.scanResponsibilityUser;
	}

	/**
	 * 功能：获取抽查工单行表属性 责任部门(扫描)
	 * @return String
	 */
	public String getScanResponsibilityDept() {
		return this.scanResponsibilityDept;
	}

	/**
	 * 功能：获取抽查工单行表属性 使用人(扫描)
	 * @return String
	 */
	public String getScanMaintainUser() {
		return this.scanMaintainUser;
	}

	public String getResponsibilityDeptName() {
		return responsibilityDeptName;
	}

	public String getResponsibilityUserName() {
		return responsibilityUserName;
	}

	public String getItemCategoryValue() {
		return itemCategoryValue;
	}

	public String getScanItemCategoryValue() {
		return scanItemCategoryValue;
	}

	public String getScanResponsibilityDeptName() {
		return scanResponsibilityDeptName;
	}

	public String getScanResponsibilityUserName() {
		return scanResponsibilityUserName;
	}

	public SimpleCalendar getScanDate() throws CalendarException {
		scanDate.setCalPattern(getCalPattern());
		return scanDate;
	}

	public String getBoxNo() {
		return boxNo;
	}

	public String getNetUnit() {
		return netUnit;
	}
}
