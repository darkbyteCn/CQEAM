package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * <p>Title: SinoIES</p>
 * <p>Description: 河南移动IES系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author ai
 * @date: 2008-3-13
 * 新增管理资产
 */
public class AssetsAddDTO extends CommonRecordDTO {

    private String headId = "";
    private String billNo = "";
    private String status;
    private String createUser;
    private SimpleCalendar createdDate = null;
    private String remark = "";
    private SimpleCalendar fromDate = null;
    private SimpleCalendar toDate = null;
    private String itemName = "";
    private String itemSpec = "";
    private String barcod = "";
    
	private String specialityDeptOption = "";
	private String specialityDept = "";
	private String specDept = "";

    public String getSpecDept() {
		return specDept;
	}

	public void setSpecDept(String specDept) {
		this.specDept = specDept;
	}

	public String getSpecialityDeptOption() {
		return specialityDeptOption;
	}

	public void setSpecialityDeptOption(String specialityDeptOption) {
		this.specialityDeptOption = specialityDeptOption;
	}

	public String getSpecialityDept() {
		return specialityDept;
	}

	public void setSpecialityDept(String specialityDept) {
		this.specialityDept = specialityDept;
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

    public String getBarcod() {
        return barcod;
    }

    public void setBarcod(String barcod) {
        this.barcod = barcod;
    }

    public AssetsAddDTO() {
        createdDate = new SimpleCalendar();
        fromDate = new SimpleCalendar();
        toDate = new SimpleCalendar();
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHeadId() {
        return headId;
    }

    public void setHeadId(String headId) {
        this.headId = headId;
    }

    public SimpleCalendar getFromDate() throws CalendarException {
        fromDate.setCalPattern(getCalPattern());
        return fromDate;
    }

    public void setFromDate(String fromDate) throws CalendarException {
        this.fromDate.setCalendarValue(fromDate);
    }

    public SimpleCalendar getToDate() throws CalendarException {
        toDate.setCalPattern(getCalPattern());
        return toDate;
    }

    public void setToDate(String toDate) throws CalendarException {
        this.toDate.setCalendarValue(toDate);
    }

    public SimpleCalendar getCreatedDate() throws CalendarException {
        createdDate.setCalPattern(getCalPattern());
        return createdDate;
    }

    public void setCreatedDate(String createdDate) throws CalendarException {
        this.createdDate.setCalendarValue(createdDate);
    }

}
