package com.sino.ams.dzyh.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.base.calen.SimpleCalendar;

/**
* <p>Title: (低值易耗品)执行工单查询(EAM) </p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 张星
* @version 1.0
*/

public class EamCheckImplementDTO extends CommonRecordDTO{

	//==============================================EAM_DH_CHECK_HEADER===========================
	private String headerId = "";	//盘点单序列号
	private int batchId;	//盘点批序列号
	private int checkTaskId;	//盘点任务ID
	private String orderNo = "";	//单据号(工单编号)
	private int checkLocation;	//盘点地点(工单地点)
	private SimpleCalendar implementDays = null;	//执行周期
	private int implementBy;	//执行人
	private String userName = "";	//执行人
	private SimpleCalendar downloadDate = null;		//下载日期
	private SimpleCalendar uploadDate = null;		//上传日期/实际完成日期(提交日期)
	
	//==============================================EAM_DH_CHECK_BATCH===========================
	private String deptCode = "";	//部门代码
	private String deptName = "";	//盘点部门(执行部门)

	//================================================ETS_0BJECT==============================
	private String workorderObjectName = "";	//地点
	
	//===============================EAM_CHECK_TASK==============================================
	private String taskName="";	//任务名称
	private String checkType="";	//任务盘点类型

	public EamCheckImplementDTO(){
		super();
		this.implementDays=new SimpleCalendar();
		this.downloadDate=new SimpleCalendar();
		this.uploadDate=new SimpleCalendar();
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public int getCheckLocation() {
		return checkLocation;
	}

	public void setCheckLocation(int checkLocation) {
		this.checkLocation = checkLocation;
	}

	public int getCheckTaskId() {
		return checkTaskId;
	}

	public void setCheckTaskId(int checkTaskId) {
		this.checkTaskId = checkTaskId;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public SimpleCalendar getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(SimpleCalendar downloadDate) {
		this.downloadDate = downloadDate;
	}

	public String getHeaderId() {
		return headerId;
	}

	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}

	public int getImplementBy() {
		return implementBy;
	}

	public void setImplementBy(int implementBy) {
		this.implementBy = implementBy;
	}

	public SimpleCalendar getImplementDays() {
		return implementDays;
	}

	public void setImplementDays(SimpleCalendar implementDays) {
		this.implementDays = implementDays;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public SimpleCalendar getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(SimpleCalendar uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getWorkorderObjectName() {
		return workorderObjectName;
	}

	public void setWorkorderObjectName(String workorderObjectName) {
		this.workorderObjectName = workorderObjectName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}