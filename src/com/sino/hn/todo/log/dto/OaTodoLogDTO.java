package com.sino.hn.todo.log.dto;

import com.sino.sinoflow.todo.dto.OaTodoDTO;

/**
 * 
 * @系统名称: 
 * @功能描述: OA_TODO_LOG 日志
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Nov 30, 2011
 */
public class OaTodoLogDTO extends OaTodoDTO {

	private String beginSendTime = "";
	private String endSendTime = "";
	private String eamMsg = "";
	private String resultCode = "";
	private String resultDesc = "";

	public String getBeginSendTime() {
		return beginSendTime;
	}

	public void setBeginSendTime(String beginSendTime) {
		this.beginSendTime = beginSendTime;
	}

	public String getEndSendTime() {
		return endSendTime;
	}

	public void setEndSendTime(String endSendTime) {
		this.endSendTime = endSendTime;
	}

	public String getEamMsg() {
		return eamMsg;
	}

	public void setEamMsg(String eamMsg) {
		this.eamMsg = eamMsg;
	}

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
	 
}
