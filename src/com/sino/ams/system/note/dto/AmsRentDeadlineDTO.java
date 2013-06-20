package com.sino.ams.system.note.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 租期设置(EAM) AmsRentDeadline</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsRentDeadlineDTO extends CheckBoxDTO{

	private String deadlineId = "";
	private String barcode = "";
	private int noticeBefore;
	private int organizationId;
	private SimpleCalendar ctreationDate = null;
	private int createdBy;
	private SimpleCalendar lastUpdateDate = null;
	private int lastUpdateBy;
    private SimpleCalendar endDate = null;
    private String itemName = "";
	private String itemSpec = "";
	private String itemCategory = "";
    private String rentPerson ="";

    public String getRentPerson() {
        return rentPerson;
    }

    public void setRentPerson(String rentPerson) {
        this.rentPerson = rentPerson;
    }

    public AmsRentDeadlineDTO() {
		super();
		this.ctreationDate = new SimpleCalendar();
		this.lastUpdateDate = new SimpleCalendar();
		this.endDate = new SimpleCalendar();
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


    /**
	 * 功能：设置租期设置(EAM)属性 序列号
	 * @param deadlineId String
	 */
	public void setDeadlineId(String deadlineId){
		this.deadlineId = deadlineId;
	}

	/**
	 * 功能：设置租期设置(EAM)属性 租赁资产条码
	 * @param barcode String
	 */
	public void setBarcodeNo(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置租期设置(EAM)属性 提前通知天数
	 * @param noticeBefore String
	 */
	public void setNoticeBefore(int noticeBefore){
		this.noticeBefore = noticeBefore;
	}

	/**
	 * 功能：设置租期设置(EAM)属性 OU组织ID
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：设置租期设置(EAM)属性 创建日期
	 * @param ctreationDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCtreationDate(String ctreationDate) throws CalendarException{
		this.ctreationDate.setCalendarValue(ctreationDate);
	}

	/**
	 * 功能：设置租期设置(EAM)属性 创建人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置租期设置(EAM)属性 上次更新日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	/**
	 * 功能：设置租期设置(EAM)属性 上次更新人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}


	/**
	 * 功能：获取租期设置(EAM)属性 序列号
	 * @return String
	 */
	public String getDeadlineId() {
		return this.deadlineId;
	}

	/**
	 * 功能：获取租期设置(EAM)属性 租赁资产条码
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取租期设置(EAM)属性 提前通知天数
	 * @return String
	 */
	public int getNoticeBefore() {
		return this.noticeBefore;
	}

	/**
	 * 功能：获取租期设置(EAM)属性 OU组织ID
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 功能：获取租期设置(EAM)属性 创建日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getCtreationDate() throws CalendarException {
		if(ctreationDate != null){
			ctreationDate.setCalPattern(getCalPattern());
		}
		return this.ctreationDate;
	}

	/**
	 * 功能：获取租期设置(EAM)属性 创建人
	 * @return String
	 */
	public int getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * 功能：获取租期设置(EAM)属性 上次更新日期
	 * @return SimpleCalendar
	 * @throws CalendarException 设置的日历格式不合法时抛出该异常
	 */
	public SimpleCalendar getLastUpdateDate() throws CalendarException {
		if(lastUpdateDate != null){
			lastUpdateDate.setCalPattern(getCalPattern());
		}
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取租期设置(EAM)属性 上次更新人
	 * @return String
	 */
	public int getLastUpdateBy() {
		return this.lastUpdateBy;
	}


   	/**
	 * 功能：设置属性 截至日期
	 * @param endDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setEndDate(String endDate) throws CalendarException{
		this.endDate.setCalendarValue(endDate);
	}

   /**
	 * 功能：获取属性 截至日期
	 * @return SimpleCalendar
	 * @throws CalendarException
	 */
	public SimpleCalendar getEndDate() throws CalendarException {
		if (endDate != null) {
			endDate.setCalPattern(getCalPattern());
		}
		return this.endDate;
	}
}
