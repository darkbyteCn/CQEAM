package com.sino.soa.td.srv.assetretire.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;

/**
 * User: wangzp
 * Date: 2011-9-17 
 * Functiion:接口资产报废DTO_TD
 */
public class TDAssetRetirementDTO extends CommonRecordDTO {

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