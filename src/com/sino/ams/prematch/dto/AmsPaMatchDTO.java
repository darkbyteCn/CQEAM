package com.sino.ams.prematch.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: EAM系统资产实物与MIS转资准备清单预匹配 AmsPaMatch</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsPaMatchDTO extends AmsPaAssetsDTO{

	private String groupId = "";
	private String printTimes = "";
	private int lastPrintBy;
	private SimpleCalendar lastPrintDate = null;
	private SimpleCalendar synchronizedDate = null;
	private String systemId = "";
	private String remark = "";

	//以下补充AMS系统的属性
	private String barcode = "";
	private String itemCategory = "";
	private String itemCategoryName = "";
	private String itemName = "";
	private String itemSpec = "";
	private String workorderObjectCode = "";
	private String workorderObjectName = "";
	private String projectNumberAms = "";
	private String projectNameAms = "";
	private String userName = "";
	private String srcPage = "";
	//以上补充AMS系统的属性

	public AmsPaMatchDTO() {
		super();
		this.lastPrintDate = new SimpleCalendar();
		this.synchronizedDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 匹配组别ID
	 * @param groupId String
	 */
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 打印次数
	 * @param printTimes String
	 */
	public void setPrintTimes(String printTimes){
		this.printTimes = printTimes;
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 上次打印人
	 * @param lastPrintBy String
	 */
	public void setLastPrintBy(int lastPrintBy){
		this.lastPrintBy = lastPrintBy;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public void setWorkorderObjectCode(String workorderObjectCode) {
		this.workorderObjectCode = workorderObjectCode;
	}

	public void setProjectNameAms(String projectNameAms) {
		this.projectNameAms = projectNameAms;
	}

	public void setProjectNumberAms(String projectNumberAms) {
		this.projectNumberAms = projectNumberAms;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSrcPage(String srcPage) {
		this.srcPage = srcPage;
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 上次打印时间
	 * @param lastPrintDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastPrintDate(String lastPrintDate) throws CalendarException{
		this.lastPrintDate.setCalendarValue(lastPrintDate);
	}

	/**
	 * 功能：设置EAM系统资产实物与MIS转资准备清单预匹配属性 同步时间
	 * @param synchronizedDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setSynchronizedDate(String synchronizedDate) throws CalendarException{
		this.synchronizedDate.setCalendarValue(synchronizedDate);
	}


	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 匹配组别ID
	 * @return String
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 打印次数
	 * @return String
	 */
	public String getPrintTimes() {
		return this.printTimes;
	}

	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 上次打印人
	 * @return String
	 */
	public int getLastPrintBy() {
		return this.lastPrintBy;
	}

	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 上次打印时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastPrintDate() throws CalendarException {
		lastPrintDate.setCalPattern(getCalPattern());
		return this.lastPrintDate;
	}

	/**
	 * 功能：获取EAM系统资产实物与MIS转资准备清单预匹配属性 同步时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getSynchronizedDate() throws CalendarException {
		synchronizedDate.setCalPattern(getCalPattern());
		return this.synchronizedDate;
	}

	public String getBarcode() {
		return barcode;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public String getItemCategoryName() {
		return itemCategoryName;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public String getWorkorderObjectCode() {
		return workorderObjectCode;
	}

	public String getProjectNameAms() {
		return projectNameAms;
	}

	public String getProjectNumberAms() {
		return projectNumberAms;
	}

	public String getUserName() {
		return userName;
	}

	public String getWorkorderObjectName() {
		return workorderObjectName;
	}

	public String getSystemId() {
		return systemId;
	}

	public String getRemark() {
		return remark;
	}

	public String getSrcPage() {
		return srcPage;
	}
}
