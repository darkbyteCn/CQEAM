package com.sino.ams.sampling.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.sampling.dto.AmsSamplingTaskAssignDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;


/**
 * <p>Title: AmsSamplingTaskAssignModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsSamplingTaskAssignModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class AmsSamplingTaskAssignModel extends AMSSQLProducer {

	/**
	 * 功能：抽查任务分配表 AMS_SAMPLING_TASK_ASSIGN 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsSamplingTaskAssignDTO 本次操作的数据
	 */
	public AmsSamplingTaskAssignModel(SfUserDTO userAccount, AmsSamplingTaskAssignDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成抽查任务分配表 AMS_SAMPLING_TASK_ASSIGN数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsSamplingTaskAssignDTO dto = (AmsSamplingTaskAssignDTO) dtoParameter;
		String sqlStr = "INSERT INTO "
						+ " AMS_SAMPLING_TASK_ASSIGN("
						+ " TASK_ID,"
						+ " ORGANIZATION_ID,"
						+ " CREATION_DATE,"
						+ " CREATED_BY"
						+ ") VALUES (?, ?, GETDATE(), ?)";

		sqlArgs.add(dto.getTaskId());
		sqlArgs.add(dto.getOrganizationId());
		sqlArgs.add(userAccount.getUserId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：根据外键关联字段 taskId 构造数据删除SQL。
	 * 框架自动生成数据抽查任务分配表 AMS_SAMPLING_TASK_ASSIGN数据删除SQLModel，请根据实际需要修改。
	 * @param taskId String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByTaskIdModel(String taskId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
						+ " FROM"
						+ " AMS_SAMPLING_TASK_ASSIGN"
						+ " WHERE"
						+ " TASK_ID = ?";
		sqlArgs.add(taskId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键字段删除数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDeleteByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		AmsSamplingTaskAssignDTO dto = (AmsSamplingTaskAssignDTO) dtoParameter;
		if (foreignKey.equals("taskId")) {
			sqlModel = getDeleteByTaskIdModel(dto.getTaskId());
		}
		return sqlModel;
	}
}
