package com.sino.ams.instrument.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.CheckBoxDTO;
import com.sino.base.exception.CalendarException;
import com.sino.base.util.StrUtil;

/**
 * Created by IntelliJ IDEA.
 * User: yuyao
 * Date: 2007-10-24
 * Time: 22:09:20
 * To change this template use File | Settings | File Templates.
 */
public class AmsInstrumentLDTO extends CheckBoxDTO {

	private String barcode = "";
	private String itemCode = "";
	private String instruUsage = "";
	private SimpleCalendar creationDate = null;
	private int createdBy = SyBaseSQLUtil.NULL_INT_VALUE;
	private SimpleCalendar lastUpdateDate = null;
	private int lastUpdateBy = SyBaseSQLUtil.NULL_INT_VALUE;
	private String currKeepUser = "";
    private String itemName="";
    private String barcodeNo1="";
    private String itemCategory="";
    private String itemSpec="";
    private String vendorName="";
    private int vendorId=SyBaseSQLUtil.NULL_INT_VALUE;
    private int borrowUser=SyBaseSQLUtil.NULL_INT_VALUE;
    private String borrowDate="";
    private int  confirmUser=SyBaseSQLUtil.NULL_INT_VALUE;
    private String confirmDate="";
    private String transNo="";
    private String createName="";
    private int lineId=SyBaseSQLUtil.NULL_INT_VALUE;
    private int transId=SyBaseSQLUtil.NULL_INT_VALUE;
    private String barcodeStatus ="";
    private String cname="";
    private String dname="";
    private String responsibilityName="";
    private String isFull = "";
    private String instruStatus = "";
    private String isFullOption = "";
    private String instruStatusOtpion = "";


    public String getFullOption() {
        return isFullOption;
    }

    public void setFullOption(String fullOption) {
        isFullOption = fullOption;
    }

    public String getInstruStatusOtpion() {
        return instruStatusOtpion;
    }

    public void setInstruStatusOtpion(String instruStatusOtpion) {
        this.instruStatusOtpion = instruStatusOtpion;
    }

    public String getFull() {
        return isFull;
    }

    public void setFull(String full) {
        isFull = full;
    }

    public String getInstruStatus() {
        return instruStatus;
    }

    public void setInstruStatus(String instruStatus) {
        this.instruStatus = instruStatus;
    }


    public String getResponsibilityName() {
        return responsibilityName;
    }

    public void setResponsibilityName(String responsibilityName) {
        this.responsibilityName = responsibilityName;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBarcodeStatus() {
        return barcodeStatus;
    }

    public void setBarcodeStatus(String barcodeStatus) {
        this.barcodeStatus = barcodeStatus;
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public int getBorrowUser() {
        return borrowUser;
    }

    public void setBorrowUser(int borrowUser) {
        this.borrowUser = borrowUser;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(int confirmUser) {
        this.confirmUser = confirmUser;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
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

    public String getBarcodeNo1() {
        return barcodeNo1;
    }

    public void setBarcodeNo1(String barcodeNo1) {
        this.barcodeNo1 = barcodeNo1;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
	 * 功能：设置仪器仪表管理(EAM)属性 仪具条码
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置仪器仪表管理(EAM)属性 分类代码
	 * @param itemCode String
	 */
	public void setItemCode(String itemCode){
		this.itemCode = itemCode;
	}

	/**
	 * 功能：设置仪器仪表管理(EAM)属性 仪具用途
	 * @param instruUsage String
	 */
	public void setInstruUsage(String instruUsage){
		this.instruUsage = instruUsage;
	}

	/**
	 * 功能：设置仪器仪表管理(EAM)属性 创建日期
	 * @param creationDate SimpleCalendar
	 * @throws com.sino.base.exception.CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setCreationDate(String creationDate) throws CalendarException {
		if(!StrUtil.isEmpty(creationDate)){
			this.creationDate = new SimpleCalendar(creationDate);
		}
	}

	/**
	 * 功能：设置仪器仪表管理(EAM)属性 责任人
	 * @param createdBy String
	 */
	public void setCreatedBy(int createdBy){
		this.createdBy = createdBy;
	}

	/**
	 * 功能：设置仪器仪表管理(EAM)属性 上次修改日期
	 * @param lastUpdateDate SimpleCalendar
	 * @throws CalendarException 传入值不是正确的日期或者是基础库不能识别的格式时抛出该异常
	 */
	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException{
		if(!StrUtil.isEmpty(lastUpdateDate)){
			this.lastUpdateDate = new SimpleCalendar(lastUpdateDate);
		}
	}

	/**
	 * 功能：设置仪器仪表管理(EAM)属性 上次修改人
	 * @param lastUpdateBy String
	 */
	public void setLastUpdateBy(int lastUpdateBy){
		this.lastUpdateBy = lastUpdateBy;
	}

	/**
	 * 功能：设置仪器仪表管理(EAM)属性 当前使用人
	 * @param currKeepUser String
	 */
	public void setCurrKeepUser(String currKeepUser){
		this.currKeepUser = currKeepUser;
	}


	/**
	 * 功能：获取仪器仪表管理(EAM)属性 仪具条码
	 * @return String
	 */
	public String getBarcode(){
		return this.barcode;
	}

	/**
	 * 功能：获取仪器仪表管理(EAM)属性 分类代码
	 * @return String
	 */
	public String getItemCode(){
		return this.itemCode;
	}

	/**
	 * 功能：获取仪器仪表管理(EAM)属性 仪具用途
	 * @return String
	 */
	public String getInstruUsage(){
		return this.instruUsage;
	}

	/**
	 * 功能：获取仪器仪表管理(EAM)属性 创建日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getCreationDate(){
		return this.creationDate;
	}

	/**
	 * 功能：获取仪器仪表管理(EAM)属性 责任人
	 * @return String
	 */
	public int getCreatedBy(){
		return this.createdBy;
	}

	/**
	 * 功能：获取仪器仪表管理(EAM)属性 上次修改日期
	 * @return SimpleCalendar
	 */
	public SimpleCalendar getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	/**
	 * 功能：获取仪器仪表管理(EAM)属性 上次修改人
	 * @return String
	 */
	public int getLastUpdateBy(){
		return this.lastUpdateBy;
	}

	/**
	 * 功能：获取仪器仪表管理(EAM)属性 当前使用人
	 * @return String
	 */
	public String getCurrKeepUser(){
		return this.currKeepUser;
	}

}