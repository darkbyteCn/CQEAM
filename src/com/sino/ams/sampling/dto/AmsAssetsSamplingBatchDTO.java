package com.sino.ams.sampling.dto;

import com.sino.ams.bean.SyBaseSQLUtil;

/**
* <p>Title: 工单批表 AmsAssetsSamplingBatch</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsAssetsSamplingBatchDTO extends AmsAssetsSamplingTaskDTO {
    
	private String batchId = "";
	private String batchNo = "";
	private String batchRemark = "";
	private int organizationId = SyBaseSQLUtil.NULL_INT_VALUE;;

	public AmsAssetsSamplingBatchDTO() {
		super();
	}

	/**
	 * 功能：设置工单批表属性 工单批ID
	 * @param batchId String
	 */
	public void setBatchId(String batchId){
		this.batchId = batchId;
	}

	/**
	 * 功能：设置工单批表属性 工单批号
	 * @param batchNo String
	 */
	public void setBatchNo(String batchNo){
		this.batchNo = batchNo;
	}

	/**
	 * 功能：设置工单批表属性 批注释
	 * @param batchRemark String
	 */
	public void setBatchRemark(String batchRemark){
		this.batchRemark = batchRemark;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}


	/**
	 * 功能：获取工单批表属性 工单批ID
	 * @return String
	 */
	public String getBatchId() {
		return this.batchId;
	}

	/**
	 * 功能：获取工单批表属性 工单批号
	 * @return String
	 */
	public String getBatchNo() {
		return this.batchNo;
	}

	/**
	 * 功能：获取工单批表属性 批注释
	 * @return String
	 */
	public String getBatchRemark() {
		return this.batchRemark;
	}

	public int getOrganizationId() {
		return organizationId;
	}
}
