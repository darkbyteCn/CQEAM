package com.sino.ams.workorder.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 工单设备明细表--工单提交时(EAM) EtsWorkorderDtl</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class EtsWorkorderDtlDTO extends CheckBoxDTO{

	private String workorderNo = "";
	private String barcode = "";
	private int itemStatus;
	private int itemQty;
	private String remark = "";
	private String itemCode = "";
    private int addressId;
    private String boxNo = "";
	private String netUnit = "";
	private String itemRemark = "";
	private SimpleCalendar creationDate = null;
	private int createdBy;
    private String parentBarcode="";

    private String prjectId;
    private String workorderObjectNo="";
    private String workorderType="";
    private SimpleCalendar uploadDate=null;
    private String itemName="";
    private String itemSpec="";
    private String itemCategory="";
    private String responsibilityDept = "";//责任部门
    private String responsibilityUser;//责任人
    private String maintainUser = "";//维护人员（代维人员）

	private String specialityDept = "";


    public EtsWorkorderDtlDTO() {
		super();
		this.creationDate = new SimpleCalendar();
		this.uploadDate = new SimpleCalendar();
	}
	public void setSpecialityDept(String dept) {
    	this.specialityDept = dept;
    }

    protected String getSpecialityDept() {
    	return this.specialityDept;
    }
    
    public String getSpecialityDeptCode() {
    	int start = this.specialityDept.lastIndexOf("[");
    	int end = this.specialityDept.lastIndexOf("]");
    	String code = "";
    	
    	if(start != -1 && end != -1 && start < end - 1) {
    		code = this.specialityDept.substring(start + 1, end);
    	}
    	
    	return code;
    }
	/**
	 * 功能：设置工单设备明细表--工单提交时(EAM)属性 工单号
	 * @param workorderNo String
	 */
	public void setWorkorderNo(String workorderNo){
		this.workorderNo = workorderNo;
	}

	/**
	 * 功能：设置工单设备明细表--工单提交时(EAM)属性 标签号
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置工单设备明细表--工单提交时(EAM)属性 设备状态
	 * @param itemStatus AdvancedNumber
	 */
	public void setItemStatus(int  itemStatus){
		this.itemStatus = itemStatus;
	}

	/**
	 * 功能：设置工单设备明细表--工单提交时(EAM)属性 数量
	 * @param itemQty AdvancedNumber
	 */
	public void setItemQty(int  itemQty){
		this.itemQty = itemQty;
	}

	/**
	 * 功能：设置工单设备明细表--工单提交时(EAM)属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置工单设备明细表--工单提交时(EAM)属性 设备代码
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置工单设备明细表--工单提交时(EAM)属性 机柜编号
	 * @param boxNo String
	 */
	public void setBoxNo(String boxNo){
		this.boxNo = boxNo;
	}

	/**
	 * 功能：设置工单设备明细表--工单提交时(EAM)属性 网元编号
	 * @param netUnit String
	 */
	public void setNetUnit(String netUnit){
		this.netUnit = netUnit;
	}

	/**
	 * 功能：设置工单设备明细表--工单提交时(EAM)属性 备注：用于条码专业，名称，型号
	 * @param itemRemark String
	 */
	public void setItemRemark(String itemRemark){
		this.itemRemark = itemRemark;
	}

	/**
	 * 功能：设置工单设备明细表--工单提交时(EAM)属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置工单设备明细表--工单提交时(EAM)属性 创建人
	 * @param createdBy AdvancedNumber
	 */
	public void setCreatedBy(int  createdBy){
		this.createdBy = createdBy;
	}


	/**
	 * 功能：获取工单设备明细表--工单提交时(EAM)属性 工单号
	 * @return String
	 */
	public String getWorkorderNo() {
		return this.workorderNo;
	}

	/**
	 * 功能：获取工单设备明细表--工单提交时(EAM)属性 标签号
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取工单设备明细表--工单提交时(EAM)属性 设备状态
	 * @return AdvancedNumber
	 */
	public int  getItemStatus() {
		return this.itemStatus;
	}

	/**
	 * 功能：获取工单设备明细表--工单提交时(EAM)属性 数量
	 * @return AdvancedNumber
	 */
	public int  getItemQty() {
		return this.itemQty;
	}

	/**
	 * 功能：获取工单设备明细表--工单提交时(EAM)属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取工单设备明细表--工单提交时(EAM)属性 设备代码
	 * @return String
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 功能：获取工单设备明细表--工单提交时(EAM)属性 机柜编号
	 * @return String
	 */
	public String getBoxNo() {
		return this.boxNo;
	}

	/**
	 * 功能：获取工单设备明细表--工单提交时(EAM)属性 网元编号
	 * @return String
	 */
	public String getNetUnit() {
		return this.netUnit;
	}

	/**
	 * 功能：获取工单设备明细表--工单提交时(EAM)属性 备注：用于条码专业，名称，型号
	 * @return String
	 */
	public String getItemRemark() {
		return this.itemRemark;
	}

	/**
	 * 功能：获取工单设备明细表--工单提交时(EAM)属性 创建日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * 功能：获取工单设备明细表--工单提交时(EAM)属性 创建人
	 * @return AdvancedNumber
	 */
	public int  getCreatedBy() {
		return this.createdBy;
	}


    public String getWorkorderObjectNo() {
        return workorderObjectNo;
    }

    public void setWorkorderObjectNo(String workorderObjectNo) {
        this.workorderObjectNo = workorderObjectNo;
    }

    public String getWorkorderType() {
        return workorderType;
    }

    public void setWorkorderType(String workorderType) {
        this.workorderType = workorderType;
    }


    public String getParentBarcode() {
        return parentBarcode;
    }

    public void setParentBarcode(String parentBarcode) {
        this.parentBarcode = parentBarcode;
    }


    public SimpleCalendar getUploadDate() throws CalendarException {
        uploadDate.setCalPattern(getCalPattern());
        return uploadDate;
    }

    public void setUploadDate(SimpleCalendar uploadDate) throws CalendarException {
        this.uploadDate.setCalendarValue( uploadDate);
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getPrjectId() {
        return prjectId;
    }

    public void setPrjectId(String prjectId) {
        this.prjectId = prjectId;
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

    public String getMaintainUser() {
        return maintainUser;
    }

    public void setMaintainUser(String maintainUser) {
        this.maintainUser = maintainUser;
    }
}