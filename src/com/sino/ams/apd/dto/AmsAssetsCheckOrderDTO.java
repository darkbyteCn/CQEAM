package com.sino.ams.apd.dto;

import com.sino.ams.newasset.dto.AmsAssetsAddressVDTO;
import com.sino.base.calen.SimpleCalendar;

public class AmsAssetsCheckOrderDTO extends AmsAssetsAddressVDTO {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6599142101781993127L;
	private String transId = "";						//任务记录ID
	private String transNo = "" ;						//TRANS_NO        	    varchar(64) NOT NULL,
    private SimpleCalendar createDate = null;			//确认时间CRATE_DATE         	    datetime NULL,
    private String  creationType = "";                      //CREATE_TYPE      	    varchar(32) NULL,
    private String createValue = "";                      //CREATE_VALUE	        varchar(32) NULL,
    private String sendType = "";                          //SEND_TYPE 	            varchar(32) NULL,
    private String sendValue = "";                         //SEND_VALUE	            varchar(32) NULL,
    private int transUser =0;
    private String transName = "";
    
	public int getTransUser() {
		return transUser;
	}
	public void setTransUser(int transUser) {
		this.transUser = transUser;
	}
	public String getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public SimpleCalendar getCreateDate() {
		return createDate;
	}
	public void setCreateDate(SimpleCalendar createDate) {
		this.createDate = createDate;
	}
	
	public String getCreationType() {
		return creationType;
	}
	public void setCreationType(String creationType) {
		this.creationType = creationType;
	}
	public String getCreateValue() {
		return createValue;
	}
	public void setCreateValue(String createValue) {
		this.createValue = createValue;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getSendValue() {
		return sendValue;
	}
	public void setSendValue(String sendValue) {
		this.sendValue = sendValue;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
}
