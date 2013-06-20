package com.sino.ams.match.amsMisLocMatch.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: Zyun
 * Date: 2007-11-21
 * Time: 19:43:46
 * To change this template use File | Settings | File Templates.
 */
public class AmsMisLocMatchDTO extends CheckBoxDTO {
    private String barcode = "";
	private SimpleCalendar endDate = null;
    private String itemSpec="";
    private String itemCode = "";
    private String systemId = "";
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
	private String itemName = "";
    private String workorderObjectLocation = "";
    private String workorderObjectCode = "";
    private String workorderObjectNo = "";
    private String systemid = "";
    private String assetsLocation = "";
    private int organizationId;
    private String noAndLocation = "";
    private String EAMworkObjectLocation = "";
    private String MISAssetsLocation = "";

    public String getAssetsLocationCode() {
        return assetsLocationCode;
    }

    public void setAssetsLocationCode(String assetsLocationCode) {
        this.assetsLocationCode = assetsLocationCode;
    }

    private String assetsLocationCode = "";

    public String getEAMworkObjectLocation() {
        return EAMworkObjectLocation;
    }

    public void setEAMworkObjectLocation(String EAMworkObjectLocation) {
        this.EAMworkObjectLocation = EAMworkObjectLocation;
    }

    public String getMISAssetsLocation() {
        return MISAssetsLocation;
    }

    public void setMISAssetsLocation(String MISAssetsLocation) {
        this.MISAssetsLocation = MISAssetsLocation;
    }
//    private String workorderObjectLocation = "";

    public String getNoAndLocation() {
        return noAndLocation;
    }

    public void setNoAndLocation(String noAndLocation) {
        this.noAndLocation = noAndLocation;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getWorkorderObjectNo() {
        return workorderObjectNo;
    }

    public void setWorkorderObjectNo(String workorderObjectNo) {
        this.workorderObjectNo = workorderObjectNo;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getAssetsLocation() {
        return assetsLocation;
    }

    public void setAssetsLocation(String assetsLocation) {
        this.assetsLocation = assetsLocation;
    }

    public String getWorkorderObjectCode() {
        return workorderObjectCode;
    }

    public void setWorkorderObjectCode(String workorderObjectCode) {
        this.workorderObjectCode = workorderObjectCode;
    }

    public String getWorkorderObjectLocation() {
        return workorderObjectLocation;
    }

    public void setWorkorderObjectLocation(String workorderObjectLocation) {
        this.workorderObjectLocation = workorderObjectLocation;
    }

    public AmsMisLocMatchDTO() {
//		this.rentDate = new SimpleCalendar();
		this.endDate = new SimpleCalendar();
		this.fromDate = new SimpleCalendar();
		this.toDate = new SimpleCalendar();
	}

	public SimpleCalendar getToDate() throws CalendarException {
		toDate.setCalPattern(getCalPattern());
        return this.toDate;
    }

    public void setToDate(String toDate)throws CalendarException{
		this.toDate.setCalendarValue(toDate);
	}

    public SimpleCalendar getFromDate() throws CalendarException {
		fromDate.setCalPattern(getCalPattern());
        return this.fromDate;
    }

    public void setFromDate(String fromDate)throws CalendarException{
		this.fromDate.setCalendarValue(fromDate);
	}

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

   public String getItemCode(){
		return this.itemCode;
	}

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }
    /**
	 * 功能：设置租赁房屋(EAM)属性 房屋条码
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 功能：设置租赁房屋(EAM)属性 截至日期
	 * @param endDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setEndDate(String endDate) throws CalendarException{
		this.endDate.setCalendarValue(endDate);
	}


	/**
	 * 功能：获取租赁房屋(EAM)属性 房屋条码
	 * @return String
	 */
	public String getBarcode(){
		return this.barcode;
	}

	/**
	 * 功能：获取租赁房屋(EAM)属性 截至日期
	 * @return SimpleCalendar
	 * @throws CalendarException
	 */
	public SimpleCalendar getEndDate() throws CalendarException {
		endDate.setCalPattern(getCalPattern());
		return this.endDate;
	}

	public String getItemName() {
		return itemName;
	}

}
