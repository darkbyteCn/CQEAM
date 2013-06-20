package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;


/**
 * <p>Title: SfLinkFieldModel</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfMultipleSearchModel {


	/**
	 * 功能：取得 SF_APPLICATION 里定义的所有 LINK_xxx 值
	 */
	public SfMultipleSearchModel() {
	}

	/**
	 * 功能：取得 SF_APPLICATION 里定义的所有 LINK_xxx 值。
     * @param taskId task ID
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getLinkFieldsModel(String taskId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
            		String sqlStr = "SELECT "
                        + " SPJ.PROJECT_NAME,"
                        + " SPJ.DESCRIPTION PROJ_DESC,"
                        + " SP.PROCEDURE_NAME,"
                        + " SP.DESCRIPTION PROC_DESC,"
            			+ " ST.TASK_NAME,"
            			+ " ST.TASK_DESC,"
                        + " ST.DURATION,"
                        + " ST.GROUP_NAME,"
            			+ " ST.ROLE_NAME,"
                        + " dbo.SFK_GET_USERS_USERNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) USERNAMES,"
            			+ " ST.API,"
            			+ " ST.DIV_RIGHT,"
            			+ " ST.DIV_HIDDEN,"
            			+ " ST.CONTROL_TYPE,"
            			+ " ST.CYCLE_TYPE,"
            			+ " dbo.SFK_GET_USERS_REALNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) REAL_NAMES,"
            			+ " ST.FLOW_TYPE TASK_FLOW_TYPE,"
            			+ " SF.FLOW_ID,"
            			+ " SF.FLOW_DESC,"
            			+ " SF.FLOW_CODE,"
            			+ " SF.FLOW_PROP,"
            			+ " SF.FLOW_TYPE"
            			+ " FROM"
            			+ " SF_TASK ST, SF_FLOW SF, SF_PROCEDURE SP, SF_PROJECT SPJ"
            			+ " WHERE"
            			+ " ST.TASK_ID = ?"
            			+ " AND ST.TASK_ID = SF.TO_TASK_ID"
            			+ " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID"
            			+ " AND SPJ.PROJECT_ID = SP.PROJECT_ID";
			
		sqlArgs.add(taskId);
		
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

}