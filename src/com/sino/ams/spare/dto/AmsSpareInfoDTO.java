package com.sino.ams.spare.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CommonUtilityDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.math.AdvancedNumber;

/**
* <p>Title: AMS_SPARE_INFO AmsSpareInfo</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsSpareInfoDTO extends CommonUtilityDTO{

	private AdvancedNumber spareId = null;
	private String barcode = "";
	private String itemName = "";
	private String itemSpec = "";
	private String itemUnit = "";
	private AdvancedNumber objectNo = null;
	private AdvancedNumber quantity = null;
	private AdvancedNumber reserveQuantity = null;
	private AdvancedNumber organizationId = null;
	private AdvancedNumber itemCode = null;
	private String itemStatus = "";
	private String remark = "";
	private SimpleCalendar creationDate = null;
	private AdvancedNumber createdBy = null;
	private SimpleCalendar lastUpdateDate = null;
	private AdvancedNumber lastUpdateBy = null;

	public AmsSpareInfoDTO() {
		super();
		this.creationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();

		this.spareId = new AdvancedNumber();
		this.itemCode = new AdvancedNumber();
		this.objectNo = new AdvancedNumber();
		this.quantity = new AdvancedNumber();
		this.reserveQuantity = new AdvancedNumber();
		this.organizationId = new AdvancedNumber();
		this.createdBy = new AdvancedNumber();
		this.lastUpdateBy = new AdvancedNumber();
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 ID,无意义
	 * @param spareId AdvancedNumber
	 */
	public void setSpareId(AdvancedNumber spareId){
		this.spareId = spareId;
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 部件号
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 地点
	 * @param objectNo AdvancedNumber
	 */
	public void setObjectNo(AdvancedNumber objectNo){
		this.objectNo = objectNo;
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 数量
	 * @param quantity AdvancedNumber
	 */
	public void setQuantity(AdvancedNumber quantity){
		this.quantity = quantity;
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 保留量
	 * @param reserveQuantity AdvancedNumber
	 */
	public void setReserveQuantity(AdvancedNumber reserveQuantity){
		this.reserveQuantity = reserveQuantity;
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 组织ID
	 * @param organizationId AdvancedNumber
	 */
	public void setOrganizationId(AdvancedNumber organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 备件状态(正常,待修,送修)
	 * @param itemStatus String
	 */
	public void setItemStatus(String itemStatus){
		this.itemStatus = itemStatus;
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 REMARK
	 * @param remark String
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 CREATION_DATE
	 * @param creationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException{
		this.creationDate.setCalendarValue(creationDate);
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 CREATED_BY
	 * @param createdBy AdvancedNumber
	 */
	public void setCreatedBy(AdvancedNumber createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 LAST_UPDATE_DATE
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置AMS_SPARE_INFO属性 LAST_UPDATE_BY
	 * @param lastUpdateBy AdvancedNumber
	 */
	public void setLastUpdateBy(AdvancedNumber lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}


	/**
	 * 功能：获取AMS_SPARE_INFO属性 ID,无意义
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getSpareId() {
		return this.spareId;
	}

	/**
	 * 功能：获取AMS_SPARE_INFO属性 部件号
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取AMS_SPARE_INFO属性 地点
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getObjectNo() {
		return this.objectNo;
	}

	/**
	 * 功能：获取AMS_SPARE_INFO属性 数量
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getQuantity() {
		return this.quantity;
	}

	/**
	 * 功能：获取AMS_SPARE_INFO属性 保留量
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getReserveQuantity() {
		return this.reserveQuantity;
	}

	/**
	 * 功能：获取AMS_SPARE_INFO属性 组织ID
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取AMS_SPARE_INFO属性 备件状态(正常,待修,送修)
	 * @return String
	 */
	public String getItemStatus() {
		return this.itemStatus;
	}

	/**
	 * 功能：获取AMS_SPARE_INFO属性 REMARK
	 * @return String
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 功能：获取AMS_SPARE_INFO属性 CREATION_DATE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCreationDate() throws CalendarException {
		creationDate.setCalPattern(getCalPattern());
		return this.creationDate;
	}

	/**
	 * 功能：获取AMS_SPARE_INFO属性 CREATED_BY
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 功能：获取AMS_SPARE_INFO属性 LAST_UPDATE_DATE
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		lastUpdateDate.setCalPattern(getCalPattern());
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取AMS_SPARE_INFO属性 LAST_UPDATE_BY
	 * @return AdvancedNumber
	 */
	public AdvancedNumber getLastUpdateBy() {
		return this.lastUpdateBy;
	}

    public AdvancedNumber getItemCode() {
        return itemCode;
    }

    public void setItemCode(AdvancedNumber itemCode) {
        this.itemCode = itemCode;
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
}