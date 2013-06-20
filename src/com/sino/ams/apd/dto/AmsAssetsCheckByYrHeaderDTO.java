package com.sino.ams.apd.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.newasset.constant.AssetsWebAttributes;
import com.sino.base.calen.SimpleCalendar;
import com.sino.base.exception.CalendarException;

public class AmsAssetsCheckByYrHeaderDTO extends CommonRecordDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8703638110529907329L;
	private String transId = "";     					//工单记录ID
	private String transType = "";   					//类别
	private String transTypeValue = "";                 //单据类型名称
    private SimpleCalendar  creationDate = null;		//创建时间
    private int createdBy =0;							//创建人
    private String createdByName = "";   			    //创建人姓名
    private int organizationId = 0;						//创建人机构
    private String transStatus = "";   					//盘点状态
    private String transStatusValue = "";               //盘点状态描述
//    private SimpleCalendar taskStartDate =null;         //任务开始日期
//    private SimpleCalendar taskEndDate =null;           //任务结束日期
//    private SimpleCalendar  basicDateBegin = null;		//盘点基准日开始范围
//    private SimpleCalendar  basicDateEnd = null;		//盘点基准日结束范围
    
    
    public AmsAssetsCheckByYrHeaderDTO() {
        super();
        setPrimaryKeyName("transId");
    }
    
	public int getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	public String getTransStatusValue() {
		return transStatusValue;
	}
	public void setTransStatusValue(String transStatusValue) {
		this.transStatusValue = transStatusValue;
	}
	
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getTransTypeValue() {
		return transTypeValue;
	}
	public void setTransTypeValue(String transTypeValue) {
		this.transTypeValue = transTypeValue;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public SimpleCalendar getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(SimpleCalendar creationDate) {
		this.creationDate = creationDate;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
    
}
