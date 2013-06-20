package com.sino.ams.sampling.dao;


import java.sql.Connection;

import com.sino.ams.appbase.dao.AMSBaseDAO;
import com.sino.ams.sampling.dto.AmsSamplingTaskAssignDTO;
import com.sino.ams.sampling.model.AmsSamplingTaskAssignModel;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.dto.DTO;
import com.sino.framework.dto.BaseUserDTO;

/**
 * <p>Title: AmsSamplingTaskAssignDAO</p>
 * <p>Description:程序自动生成服务程序“AmsSamplingTaskAssignDAO”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsSamplingTaskAssignDAO extends AMSBaseDAO {

	/**
	 * 功能：抽查任务分配表 AMS_SAMPLING_TASK_ASSIGN 数据访问层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsSamplingTaskAssignDTO 本次操作的数据
	 * @param conn Connection 数据库连接，由调用者传入。
	 */
	public AmsSamplingTaskAssignDAO(SfUserDTO userAccount, AmsSamplingTaskAssignDTO dtoParameter, Connection conn) {
		super(userAccount, dtoParameter, conn);
	}

	/**
	 * 功能：SQL生成器BaseSQLProducer的初始化。
	 * @param userAccount BaseUserDTO 本系统最终操作用户类
	 * @param dtoParameter DTO 本次操作的数据
	 */
	protected void initSQLProducer(BaseUserDTO  userAccount, DTO dtoParameter) {
		AmsSamplingTaskAssignDTO dto = (AmsSamplingTaskAssignDTO)dtoParameter;
		SfUserDTO user = (SfUserDTO)userAccount;
		sqlProducer = new AmsSamplingTaskAssignModel(user, dto);
	}
}
