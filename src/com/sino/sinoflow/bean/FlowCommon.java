package com.sino.sinoflow.bean;

public interface FlowCommon {

	/**
	 * 功能：处理流程信息
	 *
	 * @param isSubmit boolean
	 * @return boolean
	 */
	public abstract boolean processProcedure(boolean isSubmit);

	/**
	 * 撤销整个流程
	 *
	 * @return
	 */
	public abstract boolean processDel();

	/**
	 * 取消流程
	 *
	 * @return
	 */
	public abstract boolean processCancel();

	/**
	 * 退回时调用的方法
	 *
	 * @return boolean
	 */
	public abstract boolean reject();

	public abstract String getFlowCode() throws Exception;

}