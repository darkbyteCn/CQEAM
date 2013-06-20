package com.sino.sinoflow.todo.dto;

import com.sino.base.dto.DTO;

/**
 * 
 * @系统名称: 
 * @功能描述: OA_TODO
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Nov 29, 2011
 */
public class OaTodoDTO implements DTO{

	private String docId = ""; 
	private String workId = "";
	private String userId = "";
	private String title = "";
	private String startTime = "";
	private String todoUrl = "";
	private String pri = "" ; 
	private String todoType = "";
	
	//docType 内容是“公司收文”、“部门收文”、“付款申请”等说明文字（合同管理、报账平台）
	private String docType = "";
	private String sender = "";
	private String sourceId = "";
	private String sysId = "" ;
	private String closeTime = "";
	
    private String resultCode="";
    private String resultDesc=""; 
	
	 
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
  
	public String getTodoUrl() {
		return todoUrl;
	}
	public void setTodoUrl(String todoUrl) {
		this.todoUrl = todoUrl;
	}

	public String getPri() {
		return pri;
	}
	public void setPri(String pri) {
		this.pri = pri;
	}
	public String getTodoType() {
		return todoType;
	}
	public void setTodoType(String todoType) {
		this.todoType = todoType;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}  

	
}