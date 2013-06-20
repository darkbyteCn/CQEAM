package com.sino.ams.system.rent.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;

/**
* <p>Title: 租赁房屋(EAM) AmsHouseInfo</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class RentDTO extends CheckBoxDTO{

	private String barcode = "";
	private String rentPerson = "";
	private SimpleCalendar rentDate = null;
	private String houseAddress = "";
	private int floorNumber;
	private String houseNo = "";
	private int houseArea ;
	private String areaUnit = "";
	private String rental = "";
	private String moneyUnit = "";
	private String payType = "";
	private SimpleCalendar endDate = null;
    private String itemSpec="";
    private String itemCode = "";
    private String systemId = "";
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
	private String itemName = "";
    private String workorderObjectLocation = "";
    private String workorderObjectCode = "";
    private String isRent = "";
    private String rentUsage = "";
    private String rentId = "";
    private String maintainDeptName = "";
    private String remark = "";
    private String maintainDept = "";
    private String responsibilityDept = "";
    private String username = "";
    private double tenancy = 0d ;
    private String yearRental = "";
    private String monthReantal = "";
    private String addressloc = "";
    private String addressId = "";
    private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
    private String maintainUser = "";
    
    private String historyId = "";
    private String toRentTime = "";
    private String accessType = "";   //访问类型
    private String disabled = "";		//是否有效
    
    private String contentName = "";  //资产类别名称
    
    //资产所属公司名称
    private String companyName = "";
    
    //资产所属责任部门名称
    private String responsibilityDeptName = "";
    

    public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getMaintainUser() {
        return maintainUser;
    }

    public void setMaintainUser(String maintainUser) {
        this.maintainUser = maintainUser;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    private String deptId = "";

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId = SyBaseSQLUtil.NULL_INT_VALUE ;

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    private String itemQty = "";

    public String getAddressloc() {
        return addressloc;
    }

    public void setAddressloc(String addressloc) {
        this.addressloc = addressloc;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getMaintainDept() {
        return maintainDept;
    }

    public void setMaintainDept(String maintainDept) {
        this.maintainDept = maintainDept;
    }

    public String getResponsibilityDept() {
        return responsibilityDept;
    }

    public void setResponsibilityDept(String responsibilityDept) {
        this.responsibilityDept = responsibilityDept;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTenancy() {
        return tenancy;
    }

    public void setTenancy(double tenancy) {
        this.tenancy = tenancy;
    } 

    public String getYearRental() {
        return yearRental;
    }

    public void setYearRental(String yearRental) {
        this.yearRental = yearRental;
    }

    public String getMonthReantal() {
        return monthReantal;
    }

    public void setMonthReantal(String monthReantal) {
        this.monthReantal = monthReantal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRent() {
        return isRent;
    }

    public void setRent(String rent) {
        isRent = rent;
    }

    public String getMaintainDeptName() {
        return maintainDeptName;
    }

    public void setMaintainDeptName(String maintainDeptName) {
        this.maintainDeptName = maintainDeptName;
    }



    public String getRentId() {
       return this.rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public void setRentUsage(String rentUsage){
        this.rentUsage = rentUsage;
    }

   	public String getRentUsage() {
		return this.rentUsage;
	} 

    public String getIsRent() {
        return isRent;
    }

    public void setIsRent(String isRent) {
        this.isRent = isRent;
    }

    private String has = "";

    public String getHas() {
        return has;
    }

    public void setHas(String has) {
        this.has = has;
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

    public RentDTO() {
		this.rentDate = new SimpleCalendar();
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

	/**
	 * 功能：设置租赁房屋(EAM)属性 出租人(可为自然人或法人)
	 * @param rentPerson String
	 */
	public void setRentPerson(String rentPerson){
		this.rentPerson = rentPerson;
	}

	/**
	 * 功能：设置租赁房屋(EAM)属性 租赁日期
	 * @param rentDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setRentDate(String rentDate) throws CalendarException{
		this.rentDate.setCalendarValue(rentDate);
	}

	/**
	 * 功能：设置租赁房屋(EAM)属性 所在地址
	 * @param houseAddress String
	 */
	public void setHouseAddress(String houseAddress){
		this.houseAddress = houseAddress;
	}

	/**
	 * 功能：设置租赁房屋(EAM)属性 楼层
	 * @param floorNumber String
	 */
	public void setFloorNumber(int floorNumber){
		this.floorNumber = floorNumber;
	}

	/**
	 * 功能：设置租赁房屋(EAM)属性 房屋编号
	 * @param houseNo String
	 */
	public void setHouseNo(String houseNo){
		this.houseNo = houseNo;
	}

	/**
	 * 功能：设置租赁房屋(EAM)属性 房屋面积
	 * @param houseArea String
	 */
	public void setHouseArea(int houseArea){
		this.houseArea = houseArea;
	}

	/**
	 * 功能：设置租赁房屋(EAM)属性 面积单位
	 * @param areaUnit String
	 */
	public void setAreaUnit(String areaUnit){
		this.areaUnit = areaUnit;
	}

	/**
	 * 功能：设置租赁房屋(EAM)属性 租金
	 * @param rental String
	 */
	public void setRental(String rental){
		this.rental = rental;
	}

	/**
	 * 功能：设置租赁房屋(EAM)属性 租金单位
	 * @param moneyUnit String
	 */
	public void setMoneyUnit(String moneyUnit){
		this.moneyUnit = moneyUnit;
	}

	/**
	 * 功能：设置租赁房屋(EAM)属性 付款方式
	 * @param payType String
	 */
	public void setPayType(String payType){
		this.payType = payType;
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
	 * 功能：获取租赁房屋(EAM)属性 出租人(可为自然人或法人)
	 * @return String
	 */
	public String getRentPerson(){
		return this.rentPerson;
	}

	/**
	 * 功能：获取租赁房屋(EAM)属性 租赁日期
	 * @return SimpleCalendar
	 * @throws CalendarException
	 */
	public SimpleCalendar getRentDate() throws CalendarException {
		rentDate.setCalPattern(getCalPattern());
		return this.rentDate;
	}

	/**
	 * 功能：获取租赁房屋(EAM)属性 所在地址
	 * @return String
	 */
	public String getHouseAddress(){
		return this.houseAddress;
	}

	/**
	 * 功能：获取租赁房屋(EAM)属性 楼层
	 * @return String
	 */
	public int getFloorNumber(){
		return this.floorNumber;
	}

	/**
	 * 功能：获取租赁房屋(EAM)属性 房屋编号
	 * @return String
	 */
	public String getHouseNo(){
		return this.houseNo;
	}

	/**
	 * 功能：获取租赁房屋(EAM)属性 房屋面积
	 * @return String
	 */
	public int getHouseArea(){
		return this.houseArea;
	}

	/**
	 * 功能：获取租赁房屋(EAM)属性 面积单位
	 * @return String
	 */
	public String getAreaUnit(){
		return this.areaUnit;
	}

	/**
	 * 功能：获取租赁房屋(EAM)属性 租金
	 * @return String
	 */
	public String getRental(){
		return this.rental;
	}

	/**
	 * 功能：获取租赁房屋(EAM)属性 租金单位
	 * @return String
	 */
	public String getMoneyUnit(){
		return this.moneyUnit;
	}

	/**
	 * 功能：获取租赁房屋(EAM)属性 付款方式
	 * @return String
	 */
	public String getPayType(){
		return this.payType;
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


    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

	public String getToRentTime() {
		return toRentTime;
	}

	public void setToRentTime(String toRentTime) {
		this.toRentTime = toRentTime;
	}

	public String getHistoryId() {
		return historyId;
	}

	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getResponsibilityDeptName() {
		return responsibilityDeptName;
	}

	public void setResponsibilityDeptName(String responsibilityDeptName) {
		this.responsibilityDeptName = responsibilityDeptName;
	}
	
}
