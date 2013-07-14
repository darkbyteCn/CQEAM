package com.sino.ams.yearchecktaskmanager.dto;

public class AssetsYearCheckSendOneTimeDTO {
	private String orderType =""; //工单类型
	private int orderSum; //工单个数
	private String flag ="";//创建结果
	private String errorMessage="";//失败原因
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public int getOrderSum() {
		return orderSum;
	}
	public void setOrderSum(int orderSum) {
		this.orderSum = orderSum;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
