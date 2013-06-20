package com.sino.ams.sampling.dto;

import com.sino.ams.bean.CommonRecordDTO;
import com.sino.ams.bean.SyBaseSQLUtil;

/**
* <p>Title: 抽查任务分配表 AmsSamplingTaskAssign</p>
* <p>Description: 程序自动生成DTO数据传输对象</p>
* <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
* <p>Company: 北京思诺博信息科技有限公司</p>
* @author 唐明胜
* @version 1.0
*/

public class AmsSamplingTaskAssignDTO extends CommonRecordDTO{

	private String taskId = "";
	private int organizationId;

	public AmsSamplingTaskAssignDTO() {
		super();
	}

	/**
	 * 功能：设置抽查任务分配表属性 工单任务ID
	 * @param taskId String
	 */
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}

	/**
	 * 功能：设置抽查任务分配表属性 组织
	 * @param organizationId String
	 */
	public void setOrganizationId(int organizationId){
		this.organizationId = organizationId;
	}

	/**
	 * 功能：获取抽查任务分配表属性 工单任务ID
	 * @return String
	 */
	public String getTaskId() {
		return this.taskId;
	}

	/**
	 * 功能：获取抽查任务分配表属性 组织
	 * @return String
	 */
	public int getOrganizationId() {
		return this.organizationId;
	}
}
