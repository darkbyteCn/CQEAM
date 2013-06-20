package com.sino.pda.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */
public class TaskDownloadModel{
	private int organizationId = -1;

	public TaskDownloadModel(int organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * 功能：获取本OU可下载的抽查任务列表
	 * @return SQLModel
	 */
	public SQLModel getTaskDownloadModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AAST.TASK_NO,"
						+ " AAST.TASK_NAME,"
						+ " (CASE WHEN AAST.END_DATE >= GETDATE() THEN 'Y' ELSE 'N' END) ENABLED"
						+ " FROM   AMS_ASSETS_SAMPLING_TASK AAST,"
						+ " AMS_SAMPLING_TASK_ASSIGN ASTA"
						+ " WHERE  AAST.TASK_ID = ASTA.TASK_ID"
						+ " AND ASTA.ORGANIZATION_ID = ?"
						+ " AND AAST.TASK_STATUS = 'OPENING'";

		sqlArgs.add(organizationId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：判断传递的OU是否合法的OU数据
	 * @return SQLModel
	 */
	public SQLModel getOrgValidModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " ETS_OU_CITY_MAP EOCM"
						+ " WHERE"
						+ " EOCM.ORGANIZATION_ID = ?";
		sqlArgs.add(organizationId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
