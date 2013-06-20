package com.sino.ams.print.dto;

import com.sino.base.calen.SimpleCalendar;
import com.sino.base.dto.DTO;
import com.sino.base.exception.CalendarException;

/**
 * 
 * @系统名称: 标签打印历史
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Sep 17, 2011
 */
public class EtsBarcodePrintHistoryDTO implements DTO {
	private int orgId = 0;
	private String barcode = "";
	private int createdBy = 0; 
	private String type = "";
	private int trun=0;    //打印次数  
	private SimpleCalendar printTime=null;  //打印时间
	private String username="";    //打印人
	
	public EtsBarcodePrintHistoryDTO(){
		this.printTime =  new SimpleCalendar();
	}

	public int getTrun() {
		return trun;
	}

	public void setTrun(int trun) {
		this.trun = trun;
	}

	public SimpleCalendar getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) throws CalendarException {
		this.printTime.setCalendarValue(printTime);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
 

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
