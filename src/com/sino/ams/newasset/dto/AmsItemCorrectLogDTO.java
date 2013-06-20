package com.sino.ams.newasset.dto;

import com.sino.ams.bean.CommonRecordDTO;

/**
* <p>Title: 资产台账维护日志 AmsItemCorrectLog</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsItemCorrectLogDTO extends CommonRecordDTO{

	private String logId = "";
	private String barcode = "";
	private String correctContent = "";
	private String createdUser = "";

	public AmsItemCorrectLogDTO() {
		super();
	}

	/**
	 * 功能：设置资产台账维护日志属性 日志序列号
	 * @param logId String
	 */
	public void setLogId(String logId){
		this.logId = logId;
	}

	/**
	 * 功能：设置资产台账维护日志属性 标签号
	 * @param barcode String
	 */
	public void setBarcode(String barcode){
		this.barcode = barcode;
	}

	/**
	 * 功能：设置资产台账维护日志属性 修改内容
	 * @param correctContent String
	 */
	public void setCorrectContent(String correctContent){
		this.correctContent = correctContent;
	}

	/**
	 * 功能：获取资产台账维护日志属性 日志序列号
	 * @return String
	 */
	public String getLogId() {
		return this.logId;
	}

	/**
	 * 功能：获取资产台账维护日志属性 标签号
	 * @return String
	 */
	public String getBarcode() {
		return this.barcode;
	}

	/**
	 * 功能：获取资产台账维护日志属性 修改内容
	 * @return String
	 */
	public String getCorrectContent() {
		return this.correctContent;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
}
