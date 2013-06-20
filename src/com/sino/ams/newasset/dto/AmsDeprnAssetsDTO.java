package com.sino.ams.newasset.dto;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

public class AmsDeprnAssetsDTO extends EtsFaAssetsDTO {
	
	

	public AmsDeprnAssetsDTO() {
		super();
		this.lastUpdateDate = new SimpleCalendar();
	}

	//资产账簿
	private String bookTypeCode = "";

//	资产唯一标识
	private int assetId= -1;
	
//	资产标签号
	private String tagNumber = "";
	
//	固定资产编号	    
	private String assetNumber = "";
	   
//	固定资产名称  
	private String description = "";
	  
//	所属公司  
	private String segment1 = "";
	  
//	所属一级部门  
	private String segment2 = "";
	  
//	固定资产原值  
	private float cost = 0;
	  
//	净值   
	private String netBookValue = "";
	   
//	当月减值准备  
	private String ptdImpairment = "";
	  
//	本年减值准备   
	private String ytdImpairment = "";
	  
//	累计减值准备   
	private String impairmentReserve = "";
	  
//	当月折旧  
	private String ptdDeprn = "";
	  
//	本年折旧  
	private String ytdDeprn = "";
	  
//	累计折旧   
	private String deprnReserve = "";
	  
//	折旧期间  
	private String periodName = "";
	  
//	折旧剩余月数   
	private String deprnLeftMonth = "";
	   
//	最后更新日期  
	private SimpleCalendar lastUpdateDate = null;
	  
//	公司Select选项
	private String orgOption = "";

//	组织OU
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;
	
	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getBookTypeCode() {
		return bookTypeCode;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getDeprnLeftMonth() {
		return deprnLeftMonth;
	}

	public void setDeprnLeftMonth(String deprnLeftMonth) {
		this.deprnLeftMonth = deprnLeftMonth;
	}

	public String getDeprnReserve() {
		return deprnReserve;
	}

	public void setDeprnReserve(String deprnReserve) {
		this.deprnReserve = deprnReserve;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImpairmentReserve() {
		return impairmentReserve;
	}

	public void setImpairmentReserve(String impairmentReserve) {
		this.impairmentReserve = impairmentReserve;
	}

	public String getPtdImpairment() {
		return ptdImpairment;
	}

	public void setPtdImpairment(String ptdImpairment) {
		this.ptdImpairment = ptdImpairment;
	}


	public SimpleCalendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) throws CalendarException {
		this.lastUpdateDate.setCalendarValue(lastUpdateDate);
	}

	public String getNetBookValue() {
		return netBookValue;
	}

	public void setNetBookValue(String netBookValue) {
		this.netBookValue = netBookValue;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public String getPtdDeprn() {
		return ptdDeprn;
	}

	public void setPtdDeprn(String ptdDeprn) {
		this.ptdDeprn = ptdDeprn;
	}

	public String getSegment1() {
		return segment1;
	}

	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}

	public String getSegment2() {
		return segment2;
	}

	public void setSegment2(String segment2) {
		this.segment2 = segment2;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getYtdDeprn() {
		return ytdDeprn;
	}

	public void setYtdDeprn(String ytdDeprn) {
		this.ytdDeprn = ytdDeprn;
	}

	public String getYtdImpairment() {
		return ytdImpairment;
	}

	public void setYtdImpairment(String ytdImpairment) {
		this.ytdImpairment = ytdImpairment;
	}

	public String getOrgOption() {
		return orgOption;
	}

	public void setOrgOption(String orgOption) {
		this.orgOption = orgOption;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

}
