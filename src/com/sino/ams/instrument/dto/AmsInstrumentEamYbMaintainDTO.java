package com.sino.ams.instrument.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

public class AmsInstrumentEamYbMaintainDTO extends CommonRecordDTO {

//-----------------------------------------以下是EAM_YB_MAINTAIN(仪器仪表维修表)中的字段--------------------------//
	private String maintainId = ""; //EAM_YB_MAINTAIN_S.NEXTVAL
	private String barcode = ""; //仪表条码  ，  标签编号(在EAM_YB_BORROW_LOG中代表仪表条码)
	private String barcode1 = ""; //为了区分页面上的同名变量
	private String status = ""; //状态,见字典"YB_STATUS"
	private int repairUserId = SyBaseSQLUtil.NULL_INT_VALUE; //送修人
	private SimpleCalendar repairDate = null; //送修日期
	private SimpleCalendar returnDatePlan = null; //预计归还时间
	private SimpleCalendar returnDateActual = null; //实际归还日期
	private int returnUserId = SyBaseSQLUtil.NULL_INT_VALUE; //返还操作人
	private String remark = ""; //备注
	private int orgId = SyBaseSQLUtil.NULL_INT_VALUE; //组织ID
	
//----------------------------------------以下是ETS_SYSTEM_ITEM表中的字段---------------------------------------//
	private String itemCategory2 = ""; //目录编号
	private String itemName = ""; //品名
	private String itemSpec = ""; //规格型号
	private String itemUnit = ""; //计量单位
	private String itemCode = ""; //分类编码
	
//----------------------------------------以下是ETS_ITEM_INFO表中的字段-----------------------------------------//
	private String vendorBarcode = ""; //物品编号
	private String attribute3 = ""; //生产厂商，仪表用途
	private SimpleCalendar startDate = null; //启用日期
	
//--------------------------------------以下是本类的构造方法和SET、GET方法----------------------------------------//
	public AmsInstrumentEamYbMaintainDTO() {
		this.repairDate = new SimpleCalendar();
		this.returnDatePlan = new SimpleCalendar();
		this.returnDateActual = new SimpleCalendar();
		this.startDate = new SimpleCalendar();
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 启用日期
	 * @param startDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setStartDate(String startDate) throws CalendarException{
		this.startDate.setCalendarValue(startDate);
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
	
	/**
	 * 功能：设置标签号信息(EAM)属性 送修日期
	 * @param repairDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setRepairDate(String repairDate) throws CalendarException{
		this.repairDate.setCalendarValue(repairDate);
	}
	
	/**
	 * 功能：获取标签号信息(EAM)属性 送修日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getRepairDate() throws CalendarException {
		repairDate.setCalPattern(getCalPattern());
		return this.repairDate;
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 预计归还时间
	 * @param returnDatePlan SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setReturnDatePlan(String returnDatePlan) throws CalendarException{
		this.returnDatePlan.setCalendarValue(returnDatePlan);
	}
	
	/**
	 * 功能：获取标签号信息(EAM)属性 预计归还时间
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getReturnDatePlan() throws CalendarException {
		returnDatePlan.setCalPattern(getCalPattern());
		return this.returnDatePlan;
	}
	
	/**
	 * 功能：设置标签号信息(EAM)属性 实际归还日期
	 * @param returnDateActual SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setReturnDateActual(String returnDateActual) throws CalendarException{
		this.returnDateActual.setCalendarValue(returnDateActual);
	}
	
	/**
	 * 功能：获取标签号信息(EAM)属性 实际归还日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getReturnDateActual() throws CalendarException {
		returnDateActual.setCalPattern(getCalPattern());
		return this.returnDateActual;
	}

	public String getMaintainId() {
		return maintainId;
	}

	public void setMaintainId(String maintainId) {
		this.maintainId = maintainId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRepairUserId() {
		return repairUserId;
	}

	public void setRepairUserId(int repairUserId) {
		this.repairUserId = repairUserId;
	}

	public int getReturnUserId() {
		return returnUserId;
	}

	public void setReturnUserId(int returnUserId) {
		this.returnUserId = returnUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getItemCategory2() {
		return itemCategory2;
	}

	public void setItemCategory2(String itemCategory2) {
		this.itemCategory2 = itemCategory2;
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

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getVendorBarcode() {
		return vendorBarcode;
	}

	public void setVendorBarcode(String vendorBarcode) {
		this.vendorBarcode = vendorBarcode;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getBarcode1() {
		return barcode1;
	}

	public void setBarcode1(String barcode1) {
		this.barcode1 = barcode1;
	}
}
