package com.sino.ams.spare.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: su
 * Date: 2010-1-7
 * Time: 16:22:44
 * To change this template use File | Settings | File Templates.
 */
public class SpareHistoryDTO extends CommonRecordDTO {
    private String barcode = "";
    private String transNo = "";
	private String transType = "";
    private int organizationId  = -1;
	private String itemName = "";
	private String itemSpec = "";
	private String itemCategory = "";
    private String spareUsage = "";
    private String vendorId = "";
	private String vendorName = "";
    private String createdUser = "";
    private int fromCompany = -1;
    private int toCompany = -1;

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSpareUsage() {
		return spareUsage;
	}

	public void setSpareUsage(String spareUsage) {
		this.spareUsage = spareUsage;
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

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

    public String getTransNo() {
		return this.transNo;
	}

    public String getTransType() {
		return this.transType;
	}
     public int getFromCompany() {
        return fromCompany;
    }

    public void setFromCompany(int fromCompany) {
        this.fromCompany = fromCompany;
    }
     public int getToCompany() {
        return toCompany;
    }

    public void setToCompany(int toCompany) {
        this.toCompany = toCompany;
    }
}
