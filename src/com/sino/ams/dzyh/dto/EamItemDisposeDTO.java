package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 物资处置单据(EAM) EamItemDispose</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 张星
* @version 1.0
*/

public class EamItemDisposeDTO extends CommonRecordDTO{

	private String disposeId = "";	//盘点任务ID
	private String barcode = "";	//标签号(条码标签)
	private int systemid = SyBaseSQLUtil.NULL_INT_VALUE;	//Ets_item_info. SYSTEMID
	private String disposeReason = "";	//低值易耗处置原因
	private String disposeType = "";	//低值易耗处置方式
	private String remark = "";	//备注
	
	private String deptName="";	//使用部门
	private String workorderObjectName="";	//地点
	//==================================ETS_ITEM_INFO===========================================
	private String maintainUser="";	//保管人
	private String itemQty = "";	//设备数量
	private String price = "";	//单价
	private String responsibilityUser = "";	//领用人
	private String userName = "";	//领用人
	private SimpleCalendar lastLocChgDate = null;	//设备地点最后变动时间（领用日期）
	private String attribute3 = "";	//扩展属性3（厂家）
	private String eiiRemark="";	//备注
	
	//==================================ETS_SYSTEM_ITEM=========================================
	private String itemCategory2 = "";	//目录编号
	private String itemName = "";	//品名
	private String itemSpec = "";	//规格型号
	
	public EamItemDisposeDTO(){
		super();
		this.lastLocChgDate=new SimpleCalendar();
	}
	/**
	 * 功能：设置物资处置单据(EAM)属性 盘点任务ID（EAM_ITEM_DISPOSE.DISPOSE_ID）
	 * @param disposeId String
	 */
	public void setDisposeId(String disposeId){
		this.disposeId = disposeId;
	}

	/**
	 * 功能：设置物资处置单据(EAM)属性 标签号
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置物资处置单据(EAM)属性 Ets_item_info. SYSTEMID
	 * @param systemid String
	 */
	public void setSystemid(int systemid){
		this.systemid = systemid;
	}

	/**
	 * 功能：设置物资处置单据(EAM)属性 处置原因，参见字典“低值易耗处置原因”
	 * @param disposeReason String
	 */
	public void setDisposeReason(String disposeReason){
		this.disposeReason = disposeReason;
	}

	/**
	 * 功能：设置物资处置单据(EAM)属性 参见字典“低值易耗处置方式”
	 * @param disposeType String
	 */
	public void setDisposeType(String disposeType){
		this.disposeType = disposeType;
	}

	/**
	 * 功能：设置物资处置单据(EAM)属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：获取物资处置单据(EAM)属性 盘点任务ID（EAM_ITEM_DISPOSE.DISPOSE_ID）
	 * @return String
	 */
	public String getDisposeId() {
		return this.disposeId;
	}

	/**
	 * 功能：获取物资处置单据(EAM)属性 标签号
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取物资处置单据(EAM)属性 Ets_item_info. SYSTEMID
	 * @return String
	 */
	public int getSystemid() {
		return this.systemid;
	}

	/**
	 * 功能：获取物资处置单据(EAM)属性 处置原因，参见字典“低值易耗处置原因”
	 * @return String
	 */
	public String getDisposeReason() {
		return this.disposeReason;
	}

	/**
	 * 功能：获取物资处置单据(EAM)属性 参见字典“低值易耗处置方式”
	 * @return String
	 */
	public String getDisposeType() {
		return this.disposeType;
	}

	/**
	 * 功能：获取物资处置单据(EAM)属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	public String getMaintainUser() {
		return maintainUser;
	}

	public void setMaintainUser(String maintainUser) {
		this.maintainUser = maintainUser;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getItemQty() {
		return itemQty;
	}

	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}

	public SimpleCalendar getLastLocChgDate() throws CalendarException {
		lastLocChgDate.setCalPattern(getCalPattern());
		return lastLocChgDate;
	}

	public void setLastLocChgDate(String lastLocChgDate) throws CalendarException {
		this.lastLocChgDate.setCalendarValue(lastLocChgDate);
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getResponsibilityUser() {
		return responsibilityUser;
	}

	public void setResponsibilityUser(String responsibilityUser) {
		this.responsibilityUser = responsibilityUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWorkorderObjectName() {
		return workorderObjectName;
	}
	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}
	public String getEiiRemark() {
		return eiiRemark;
	}
	public void setEiiRemark(String eiiRemark) {
		this.eiiRemark = eiiRemark;
	}

}