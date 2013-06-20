package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;

/**
* <p>Title: (低值易耗品)部门遗失设备(EAM) </p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 张星
* @version 1.0
*/

public class LossEquipmentDTO extends CommonRecordDTO{

	private String dhChgLogId = "";	//系统ID(序列号)
	private int fromAddressId = SyBaseSQLUtil.NULL_INT_VALUE;	//原地点
	
	//==============================================ETS_ITEM_INFO===========================
	private String barcode = "";	//条码标签
	private int itemQty = SyBaseSQLUtil.NULL_INT_VALUE;	//设备数量
	private float price = 0;	//单价
	private String responsibilityDept = "";	//使用部门
	private SimpleCalendar lastLocChgDate = null;	//设备地点最后变动时间（领用日期）
	private int responsibilityUser = SyBaseSQLUtil.NULL_INT_VALUE;	//领用人
	private int maintainUser = SyBaseSQLUtil.NULL_INT_VALUE;	//保管人
	private int addressId = SyBaseSQLUtil.NULL_INT_VALUE;	//资产地点ID
	private String attribute3 = "";	//扩展属性3（厂家）
	private String remark = "";		//备注

	private String deptName = "";	//使用部门
	private String userName = "";	//保管人
	
	//==============================================ETS_SYSTEM_ITEM===========================
	private String itemCategory2 = "";	//目录编号
	private String itemName = "";		//品名
	private String itemSpec = "";		//规格型号
	
	//================================================ETS_0BJECT==============================
	private String workorderObjectName = "";	//地点
	
	//===============================EAM_CHECK_TASK==============================================
	private String checkTaskId="";	//盘点任务ID
	private String taskName="";	//任务名称
	private String checkType="";	//任务盘点类型

	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public LossEquipmentDTO(){
		super();
		this.lastLocChgDate=new SimpleCalendar();
	}
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDhChgLogId() {
		return dhChgLogId;
	}

	public void setDhChgLogId(String dhChgLogId) {
		this.dhChgLogId = dhChgLogId;
	}

	public int getFromAddressId() {
		return fromAddressId;
	}

	public void setFromAddressId(int fromAddressId) {
		this.fromAddressId = fromAddressId;
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

	public int getMaintainUser() {
		return maintainUser;
	}

	public void setMaintainUser(int maintainUser) {
		this.maintainUser = maintainUser;
	}

	public String getResponsibilityDept() {
		return responsibilityDept;
	}

	public void setResponsibilityDept(String responsibilityDept) {
		this.responsibilityDept = responsibilityDept;
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

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getCheckTaskId() {
		return checkTaskId;
	}

	public void setCheckTaskId(String checkTaskId) {
		this.checkTaskId = checkTaskId;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

	public SimpleCalendar getLastLocChgDate() {
		return lastLocChgDate;
	}

	public void setLastLocChgDate(SimpleCalendar lastLocChgDate) {
		this.lastLocChgDate = lastLocChgDate;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getResponsibilityUser() {
		return responsibilityUser;
	}

	public void setResponsibilityUser(int responsibilityUser) {
		this.responsibilityUser = responsibilityUser;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}