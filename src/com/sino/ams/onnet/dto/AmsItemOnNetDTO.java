package com.sino.ams.onnet.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-12-02
 * Time: 00:00:00
 * To change this template use File | Settings | File Templates.
 */

public class AmsItemOnNetDTO extends CheckBoxDTO{

	private String id = "";
	private String partNo = "";
	private int quantity=-1;
	private int organizationId=-1;
	private String remark = "";
	private SimpleCalendar lastUpdateDate = null;
	private String lastUpdateUser = "";
	private String itemCode = "";
	private String itemName = "";
	private String itemSpec = "";
    private String vendorId = "";
    private String vendorName = "";
    private String orgnizationName = "";
    private String spareUsage = "";
    private String partNoError = "";
    private String ouError = "";
    private String quantityError = "";
    private String itemCategory = "";   //设备类型
    
    public String getSpareUsage() {
        return spareUsage;
    }

    public void setSpareUsage(String spareUsage) {
        this.spareUsage = spareUsage;
    }

    public String getOrgnizationName() {
        return orgnizationName;
    }

    public void setOrgnizationName(String orgnizationName) {
        this.orgnizationName = orgnizationName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public AmsItemOnNetDTO() {
		super();
		this.lastUpdateDate = new SimpleCalendar();
	}

	/**
	 * 功能：设置设备在网数量属性 序列
	 * @param id String
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * 功能：设置设备在网数量属性 备件部件号
	 * @param partNo String
	 */
	public void setPartNo(String partNo){
		this.partNo = partNo;
	}

	/**
	 * 功能：设置设备在网数量属性 数量
	 * @param quantity String
	 */
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	/**
	 * 功能：设置设备在网数量属性 组织ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置设备在网数量属性 备注
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置设备在网数量属性 最后变动日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置设备在网数量属性 最后变动人
	 * @param lastUpdateUser String
	 */
	public void setLastUpdateUser(String lastUpdateUser){
		this.lastUpdateUser = lastUpdateUser;
	}


	/**
	 * 功能：获取设备在网数量属性 序列
	 * @return String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 功能：获取设备在网数量属性 备件部件号
	 * @return String
	 */
	public String getPartNo() {
		return this.partNo;
	}

	/**
	 * 功能：获取设备在网数量属性 数量
	 * @return String
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * 功能：获取设备在网数量属性 组织ID
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取设备在网数量属性 备注
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取设备在网数量属性 最后变动日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取设备在网数量属性 最后变动人
	 * @return String
	 */
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}


    public String getPartNoError() {
        return partNoError;
    }

    public void setPartNoError(String partNoError) {
        this.partNoError = partNoError;
    }

    public String getOuError() {
        return ouError;
    }

    public void setOuError(String ouError) {
        this.ouError = ouError;
    }

    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

    
}