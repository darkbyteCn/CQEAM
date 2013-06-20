package com.sino.soa.mis.srv.assetretire.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;

/**
 * User: zhoujs
 * Date: 2009-5-12 14:13:56
 * Functiion:接口资产报废DTO
 */
public class AssetRetirementDTO extends CommonRecordDTO {

	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE ;
	private String organizationOpt = "";
    private String barcode = "";
    private String itemname = "";
	
	
    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationOpt() {
        return organizationOpt;
    }

    public void setOrganizationOpt(String organizationOpt) {
        this.organizationOpt = organizationOpt;
    }

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
}