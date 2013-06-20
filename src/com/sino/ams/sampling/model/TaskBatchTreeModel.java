package com.sino.ams.sampling.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.dto.AmsAssetsSamplingTaskDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

/**
 *
 * <p>Title: SinoAMS</p>
 * <p>Description: 山西移动实物资产管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有CopyrightCopyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class TaskBatchTreeModel extends AMSSQLProducer {
    
	public TaskBatchTreeModel(SfUserDTO userAccount, AmsAssetsSamplingTaskDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：构造任务树形菜单
	 * @return SQLModel
	 */
	public SQLModel getTaskTreeModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "";
		if(!userAccount.isProvinceUser()){
			sqlStr = "SELECT"
					 + " EOCM.COMPANY CREATED_OU_NAME,"
//					 + " EOCM.COMPANY,"
					 + " AAST.TASK_NO || '[' || AAST.TASK_NAME || ']' TASK_TREE_NAME,"
					 + " AASB.BATCH_NO"
					 + " FROM"
					 + " ETS_OU_CITY_MAP           EOCM,"
					 + " AMS_ASSETS_SAMPLING_TASK  AAST,"
					 + " AMS_SAMPLING_TASK_ASSIGN  ASTA,"
					 + " AMS_ASSETS_SAMPLING_BATCH AASB"
					 + " WHERE"
					 + " AAST.CREATED_OU = EOCM.ORGANIZATION_ID"
					 + " AND AAST.TASK_ID = ASTA.TASK_ID"
					 + " AND ASTA.ORGANIZATION_ID *= AASB.ORGANIZATION_ID"
					 + " AND ASTA.ORGANIZATION_ID *= AASB.CREATED_OU"
					 + " AND ASTA.TASK_ID *= AASB.TASK_ID"
					 + " AND AAST.TASK_STATUS = ?"
					 + " AND ASTA.ORGANIZATION_ID = ?"
					 + " ORDER BY"
					 + " EOCM.ORGANIZATION_ID,"
					 + " AAST.TASK_NO,"
					 + " AASB.BATCH_NO";
		} else{
			sqlStr = "SELECT"
					 + " EOCM.COMPANY SAMPLED_OU_NAME,"
//					 + " EOCM.COMPANY,"
					 + " AAST.TASK_NO || '[' || AAST.TASK_NAME || ']' TASK_TREE_NAME,"
					 + " AASB.BATCH_NO"
					 + " FROM"
					 + " ETS_OU_CITY_MAP           EOCM,"
					 + " AMS_ASSETS_SAMPLING_TASK  AAST,"
					 + " AMS_SAMPLING_TASK_ASSIGN  ASTA,"
					 + " AMS_ASSETS_SAMPLING_BATCH AASB"
					 + " WHERE"
					 + " AAST.TASK_ID = ASTA.TASK_ID"
					 + " AND ASTA.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
					 + " AND ASTA.ORGANIZATION_ID *= AASB.ORGANIZATION_ID"
					 + " AND ASTA.TASK_ID *= AASB.TASK_ID"
					 + " AND AAST.TASK_STATUS = ?"
					 + " AND AAST.CREATED_OU = ?"
					 + " ORDER BY"
					 + " EOCM.ORGANIZATION_ID,"
					 + " AAST.TASK_NO,"
					 + " AASB.BATCH_NO";
		}
		sqlArgs.add(SamplingDicts.TSK_STATUS1_OPENING);
		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
