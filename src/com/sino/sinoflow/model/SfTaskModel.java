package com.sino.sinoflow.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.sql.BaseSQLProducer;
import com.sino.sinoflow.dto.SfTaskDTO;
import com.sino.sinoflow.user.dto.SfUserBaseDTO;


/**
 * <p>Title: SfTaskModel</p>
 * <p>Description:程序自动生成SQL构造器“SfTaskModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author Hing
 * @version 1.0
 */


public class SfTaskModel extends BaseSQLProducer {

//	private SfUserBaseDTO sfUser = null;

	/**
	 * 功能：任务属性 SF_TASK 数据库SQL构造层构造函数
	 * @param userAccount SfUserBaseDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter SfTaskDTO 本次操作的数据
	 */
	public SfTaskModel(SfUserBaseDTO userAccount, SfTaskDTO dtoParameter) {
		super(userAccount, dtoParameter);
//		sfUser = userAccount;
	}

	/**
	 * 功能：框架自动生成任务属性 SF_TASK数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 */
	public SQLModel getDataCreateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfTaskDTO sfTask = (SfTaskDTO)dtoParameter;
		String sqlStr = "INSERT INTO "
			+ " SF_TASK("
			+ " PROCEDURE_ID,"
			+ " TASK_NAME,"
			+ " TASK_DESC,"
			+ " GROUP_NAME,"
			+ " ROLE_NAME,"
			+ " FLOW_TYPE,"
			+ " API,"
			+ " DURATION,"
			+ " DURATION_UNIT,"
            + " URL,"
            + " DIV_RIGHT,"
			+ " DIV_HIDDEN,"
			+ " CONTROL_TYPE,"
			+ " CYCLE_TYPE,"
            + " CYCLE_URL,"
            + " REVIEW_GROUP,"
			+ " REVIEW_ROLE,"
			+ " REVIEW_DESC,"
            + " REVIEW_URL,"
            + " REVIEW_DIV_RIGHT,"
			+ " REVIEW_DIV_HIDDEN,"
            + " COPY_FLAG,"
            + " COPY_REASON,"
            + " COPY_HIDDEN_DIV,"
            + " COPY_URL,"
            + " ATTRIBUTE_1,"
            + " ATTRIBUTE_2,"
            + " ATTRIBUTE_3,"
            + " ATTRIBUTE_4,"
            + " ATTRIBUTE_5,"
            + " TID"
            + ") VALUES ("
			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
        sqlModel.setSqlStr(sqlStr);
		sqlArgs.add(sfTask.getProcedureId());
		sqlArgs.add(sfTask.getTaskName());
		sqlArgs.add(sfTask.getTaskDesc());
		sqlArgs.add(sfTask.getGroupName());
		sqlArgs.add(sfTask.getRoleName());
		sqlArgs.add(sfTask.getFlowType());
		sqlArgs.add(sfTask.getApi());
		sqlArgs.add(sfTask.getDuration());
		sqlArgs.add(sfTask.getDurationUnit());
        sqlArgs.add(sfTask.getUrl());
        sqlArgs.add(sfTask.getDivRight());
		sqlArgs.add(sfTask.getDivHidden());
		sqlArgs.add(sfTask.getControlType());
		sqlArgs.add(sfTask.getCycleType());
        sqlArgs.add(sfTask.getCycleUrl());
        sqlArgs.add(sfTask.getReviewGroup());
		sqlArgs.add(sfTask.getReviewRole());
		sqlArgs.add(sfTask.getReviewDesc());
        sqlArgs.add(sfTask.getReviewUrl());
        sqlArgs.add(sfTask.getReviewDivRight());
		sqlArgs.add(sfTask.getReviewDivHidden());
        sqlArgs.add(sfTask.getCopyFlag());
        sqlArgs.add(sfTask.getCopyReason());
        sqlArgs.add(sfTask.getCopyHiddenDiv());
        sqlArgs.add(sfTask.getCopyUrl());
        sqlArgs.add(sfTask.getAttribute1());
        sqlArgs.add(sfTask.getAttribute2());
        sqlArgs.add(sfTask.getAttribute3());
        sqlArgs.add(sfTask.getAttribute4());
        sqlArgs.add(sfTask.getAttribute5());
        sqlArgs.add(sfTask.getTid());

		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成任务属性 SF_TASK数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 */
	public SQLModel getDataUpdateModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfTaskDTO sfTask = (SfTaskDTO)dtoParameter;
		String sqlStr = "UPDATE SF_TASK"
			+ " SET"
			+ " PROCEDURE_ID = ?,"
			+ " TASK_NAME = ?,"
			+ " TASK_DESC = ?,"
			+ " GROUP_NAME = ?,"
			+ " ROLE_NAME = ?,"
			+ " FLOW_TYPE = ?,"
			+ " API = ?,"
			+ " DURATION = ?,"
			+ " DURATION_UNIT = ?,"
            + " URL = ?,"
            + " DIV_RIGHT = ?,"
			+ " DIV_HIDDEN = ?,"
			+ " CONTROL_TYPE = ?,"
			+ " CYCLE_TYPE = ?,"
            + " CYCLE_URL = ?,"
            + " REVIEW_GROUP = ?,"
			+ " REVIEW_ROLE = ?,"
			+ " REVIEW_DESC = ?,"
            + " REVIEW_URL = ?,"
            + " REVIEW_DIV_RIGHT = ?,"
			+ " REVIEW_DIV_HIDDEN = ?,"
            + " COPY_FLAG = ?,"
            + " COPY_REASON = ?,"
            + " COPY_HIDDEN_DIV = ?,"
            + " COPY_URL = ?,"
            + " ATTRIBUTE_1 = ?,"
            + " ATTRIBUTE_2 = ?,"
            + " ATTRIBUTE_3 = ?,"
            + " ATTRIBUTE_4 = ?,"
            + " ATTRIBUTE_5 = ?,"
            + " TID = ?"
            + " WHERE"
			+ " TASK_ID = ?";
		
        sqlModel.setSqlStr(sqlStr);
		sqlArgs.add(sfTask.getProcedureId());
		sqlArgs.add(sfTask.getTaskName());
		sqlArgs.add(sfTask.getTaskDesc());
		sqlArgs.add(sfTask.getGroupName());
		sqlArgs.add(sfTask.getRoleName());
		sqlArgs.add(sfTask.getFlowType());
		sqlArgs.add(sfTask.getApi());
		sqlArgs.add(sfTask.getDuration());
		sqlArgs.add(sfTask.getDurationUnit());
        sqlArgs.add(sfTask.getUrl());
        sqlArgs.add(sfTask.getDivRight());
		sqlArgs.add(sfTask.getDivHidden());
		sqlArgs.add(sfTask.getControlType());
		sqlArgs.add(sfTask.getCycleType());
        sqlArgs.add(sfTask.getCycleUrl());
        sqlArgs.add(sfTask.getReviewGroup());
		sqlArgs.add(sfTask.getReviewRole());
		sqlArgs.add(sfTask.getReviewDesc());
        sqlArgs.add(sfTask.getReviewUrl());
        sqlArgs.add(sfTask.getReviewDivRight());
		sqlArgs.add(sfTask.getReviewDivHidden());
		sqlArgs.add(sfTask.getTaskId());
        sqlArgs.add(sfTask.getCopyFlag());
        sqlArgs.add(sfTask.getCopyReason());
        sqlArgs.add(sfTask.getCopyHiddenDiv());
        sqlArgs.add(sfTask.getCopyUrl());
        sqlArgs.add(sfTask.getAttribute1());
        sqlArgs.add(sfTask.getAttribute2());
        sqlArgs.add(sfTask.getAttribute3());
        sqlArgs.add(sfTask.getAttribute4());
        sqlArgs.add(sfTask.getAttribute5());
        sqlArgs.add(sfTask.getTid());

		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成任务属性 SF_TASK数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfTaskDTO sfTask = (SfTaskDTO)dtoParameter;
		String sqlStr = "DELETE FROM"
				+ " SF_TASK"
				+ " WHERE"
				+ " TASK_ID = ?";
        sqlModel.setSqlStr(sqlStr);
    	sqlArgs.add(sfTask.getTaskId());
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成任务属性 SF_TASK数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfTaskDTO sfTask = (SfTaskDTO)dtoParameter;
		String sqlStr = "SELECT "
			+ " PROCEDURE_ID,"
			+ " TASK_ID,"
			+ " TASK_NAME,"
			+ " TASK_DESC,"
			+ " GROUP_NAME,"
			+ " ROLE_NAME,"
			+ " FLOW_TYPE,"
			+ " API,"
			+ " DURATION,"
			+ " DURATION_UNIT,"
            + " URL,"
            + " DIV_RIGHT,"
			+ " DIV_HIDDEN,"
			+ " CONTROL_TYPE,"
			+ " CYCLE_TYPE,"
            + " CYCLE_URL,"
            + " REVIEW_GROUP,"
			+ " REVIEW_ROLE,"
			+ " REVIEW_DESC,"
            + " REVIEW_URL,"
            + " REVIEW_DIV_RIGHT,"
			+ " REVIEW_DIV_HIDDEN,"
            + " COPY_FLAG,"
            + " COPY_REASON,"
            + " COPY_HIDDEN_DIV,"
            + " COPY_URL,"
            + " ATTRIBUTE_1,"
            + " ATTRIBUTE_2,"
            + " ATTRIBUTE_3,"
            + " ATTRIBUTE_4,"
            + " ATTRIBUTE_5,"
            + " TID"
            + " FROM"
			+ " SF_TASK"
			+ " WHERE"
			+ " TASK_ID = ?";

        sqlModel.setSqlStr(sqlStr);
		sqlArgs.add(sfTask.getTaskId());
		
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成任务属性 SF_TASK多条数据信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回多条数据信息查询用SQLModel
	 */
	public SQLModel getMuxDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfTaskDTO sfTask = (SfTaskDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " PROCEDURE_ID,"
			+ " TASK_ID,"
			+ " TASK_NAME,"
			+ " TASK_DESC,"
			+ " GROUP_NAME,"
			+ " ROLE_NAME,"
			+ " FLOW_TYPE,"
			+ " API,"
			+ " DURATION,"
			+ " DURATION_UNIT,"
            + " URL,"
            + " DIV_RIGHT,"
			+ " DIV_HIDDEN,"
			+ " CONTROL_TYPE,"
			+ " CYCLE_TYPE,"
            + " CYCLE_URL,"
            + " REVIEW_GROUP,"
			+ " REVIEW_ROLE,"
			+ " REVIEW_DESC,"
            + " REVIEW_URL,"
            + " REVIEW_DIV_RIGHT,"
			+ " REVIEW_DIV_HIDDEN,"
            + " COPY_FLAG,"
            + " COPY_REASON,"
            + " COPY_HIDDEN_DIV,"
            + " COPY_URL,"
            + " ATTRIBUTE_1,"
            + " ATTRIBUTE_2,"
            + " ATTRIBUTE_3,"
            + " ATTRIBUTE_4,"
            + " ATTRIBUTE_5,"
            + " TID"
            + " FROM"
			+ " SF_TASK"
			+ " WHERE"
			+ " (? <= 0 OR PROCEDURE_ID = ?)"
			+ " AND (? <= 0 OR TASK_ID = ?)"
			+ " AND (? = '' OR TASK_NAME LIKE ?)"
			+ " AND (? = '' OR TASK_DESC LIKE ?)"
			+ " AND (? = '' OR GROUP_NAME LIKE ?)"
			+ " AND (? = '' OR ROLE_NAME LIKE ?)"
			+ " AND (? <= 0 OR FLOW_TYPE = ?)"
			+ " AND (? = '' OR API LIKE ?)"
			+ " AND (? <= 0 OR DURATION = ?)"
			+ " AND (? <= 0 OR DURATION_UNIT = ?)"
            + " AND (? = '' OR URL LIKE ?)"
            + " AND (? = '' OR DIV_RIGHT LIKE ?)"
			+ " AND (? = '' OR DIV_HIDDEN LIKE ?)"
			+ " AND (? <= 0 OR CONTROL_TYPE = ?)"
			+ " AND (? <= 0 OR CYCLE_TYPE = ?)"
            + " AND (? = '' OR CYCLE_URL LIKE ?)"
            + " AND (? = '' OR REVIEW_GROUP LIKE ?)"
			+ " AND (? = '' OR REVIEW_ROLE LIKE ?)"
			+ " AND (? = '' OR REVIEW_DESC LIKE ?)"
            + " AND (? = '' OR REVIEW_URL LIKE ?)"
            + " AND (? = '' OR REVIEW_DIV_RIGHT LIKE ?)"
			+ " AND (? = '' OR REVIEW_DIV_HIDDEN LIKE ?)"
            + " AND (? <= 0 OR COPY_FLAG = ?)"
            + " AND (? = '' OR COPY_REASON LIKE ?)"
            + " AND (? = '' OR COPY_HIDDEN_DIV LIKE ?)"
            + " AND (? = '' OR COPY_URL LIKE ?)"
            + " AND (? = '' OR ATTRIBUTE_1 LIKE ?)"
            + " AND (? = '' OR ATTRIBUTE_2 LIKE ?)"
            + " AND (? = '' OR ATTRIBUTE_3 LIKE ?)"
            + " AND (? = '' OR ATTRIBUTE_4 LIKE ?)"
            + " AND (? = '' OR ATTRIBUTE_5 LIKE ?)"
            + " AND (? <= 0 OR TID = ?)";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(sfTask.getProcedureId());
		sqlArgs.add(sfTask.getProcedureId());
		sqlArgs.add(sfTask.getTaskId());
		sqlArgs.add(sfTask.getTaskId());
		sqlArgs.add(sfTask.getTaskName());
		sqlArgs.add(sfTask.getTaskName());
		sqlArgs.add(sfTask.getTaskDesc());
		sqlArgs.add(sfTask.getTaskDesc());
		sqlArgs.add(sfTask.getGroupName());
		sqlArgs.add(sfTask.getGroupName());
		sqlArgs.add(sfTask.getRoleName());
		sqlArgs.add(sfTask.getRoleName());
		sqlArgs.add(sfTask.getFlowType());
		sqlArgs.add(sfTask.getFlowType());
		sqlArgs.add(sfTask.getApi());
		sqlArgs.add(sfTask.getApi());
		sqlArgs.add(sfTask.getDuration());
		sqlArgs.add(sfTask.getDuration());
		sqlArgs.add(sfTask.getDurationUnit());
		sqlArgs.add(sfTask.getDurationUnit());
        sqlArgs.add(sfTask.getUrl());
        sqlArgs.add(sfTask.getUrl());
        sqlArgs.add(sfTask.getDivRight());
		sqlArgs.add(sfTask.getDivRight());
		sqlArgs.add(sfTask.getDivHidden());
		sqlArgs.add(sfTask.getDivHidden());
		sqlArgs.add(sfTask.getControlType());
		sqlArgs.add(sfTask.getControlType());
		sqlArgs.add(sfTask.getCycleType());
		sqlArgs.add(sfTask.getCycleType());
        sqlArgs.add(sfTask.getCycleUrl());
        sqlArgs.add(sfTask.getCycleUrl());
        sqlArgs.add(sfTask.getReviewGroup());
		sqlArgs.add(sfTask.getReviewGroup());
		sqlArgs.add(sfTask.getReviewRole());
		sqlArgs.add(sfTask.getReviewRole());
		sqlArgs.add(sfTask.getReviewDesc());
		sqlArgs.add(sfTask.getReviewDesc());
        sqlArgs.add(sfTask.getReviewUrl());
        sqlArgs.add(sfTask.getReviewUrl());
        sqlArgs.add(sfTask.getReviewDivRight());
		sqlArgs.add(sfTask.getReviewDivRight());
		sqlArgs.add(sfTask.getReviewDivHidden());
		sqlArgs.add(sfTask.getReviewDivHidden());
        sqlArgs.add(sfTask.getCopyFlag());
        sqlArgs.add(sfTask.getCopyFlag());
        sqlArgs.add(sfTask.getCopyReason());
        sqlArgs.add(sfTask.getCopyReason());
        sqlArgs.add(sfTask.getCopyHiddenDiv());
        sqlArgs.add(sfTask.getCopyHiddenDiv());
        sqlArgs.add(sfTask.getCopyUrl());
        sqlArgs.add(sfTask.getCopyUrl());
        sqlArgs.add(sfTask.getAttribute1());
        sqlArgs.add(sfTask.getAttribute1());
        sqlArgs.add(sfTask.getAttribute2());
        sqlArgs.add(sfTask.getAttribute2());
        sqlArgs.add(sfTask.getAttribute3());
        sqlArgs.add(sfTask.getAttribute3());
        sqlArgs.add(sfTask.getAttribute4());
        sqlArgs.add(sfTask.getAttribute4());
        sqlArgs.add(sfTask.getAttribute5());
        sqlArgs.add(sfTask.getAttribute5());
        sqlArgs.add(sfTask.getTid());
        sqlArgs.add(sfTask.getTid());

		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 procedureId 构造查询数据SQL。
	 * 框架自动生成数据任务属性 SF_TASK详细信息查询SQLModel，请根据实际需要修改。
	 * @param procedureId String 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByProcedureIdModel(int procedureId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT "
			+ " TASK_ID,"
			+ " TASK_NAME,"
			+ " TASK_DESC,"
			+ " GROUP_NAME,"
			+ " ROLE_NAME,"
			+ " FLOW_TYPE,"
			+ " API,"
			+ " DURATION,"
			+ " DURATION_UNIT,"
            + " URL,"
            + " DIV_RIGHT,"
			+ " DIV_HIDDEN,"
			+ " CONTROL_TYPE,"
			+ " CYCLE_TYPE,"
            + " CYCLE_URL,"
            + " REVIEW_GROUP,"
			+ " REVIEW_ROLE,"
			+ " REVIEW_DESC,"
            + " REVIEW_URL,"
            + " REVIEW_DIV_RIGHT,"
			+ " REVIEW_DIV_HIDDEN,"
            + " COPY_FLAG,"
            + " COPY_REASON,"
            + " COPY_HIDDEN_DIV,"
            + " COPY_URL,"
            + " ATTRIBUTE_1,"
            + " ATTRIBUTE_2,"
            + " ATTRIBUTE_3,"
            + " ATTRIBUTE_4,"
            + " ATTRIBUTE_5,"
            + " TID"
            + " FROM"
			+ " SF_TASK"
			+ " WHERE"
			+ " PROCEDURE_ID = ?";

        sqlModel.setSqlStr(sqlStr);

		sqlArgs.add(procedureId);
		
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：根据外键获取数据
	 * @param foreignKey 传入的外键字段名称。
	 * @return SQLModel
	 */
	public SQLModel getDataByForeignKeyModel(String foreignKey) {
		SQLModel sqlModel = null;
		SfTaskDTO sfTask = (SfTaskDTO)dtoParameter;
		if(foreignKey.equals("procedureId")){
			sqlModel = getDataByProcedureIdModel(sfTask.getProcedureId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 procedureId 构造数据删除SQL。
	 * 框架自动生成数据任务属性 SF_TASK数据删除SQLModel，请根据实际需要修改。
	 * @param procedureId String 
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByProcedureIdModel(int procedureId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE "
/*
            + " TASK_ID,"
			+ " TASK_NAME,"
			+ " TASK_DESC,"
			+ " GROUP_NAME,"
			+ " ROLE_NAME,"
			+ " FLOW_TYPE,"
			+ " API,"
			+ " DURATION,"
			+ " DURATION_UNIT,"
            + " URL,"
            + " DIV_RIGHT,"
			+ " DIV_HIDDEN,"
			+ " CONTROL_TYPE,"
			+ " CYCLE_TYPE,"
            + " CYCLE_URL,"
            + " REVIEW_GROUP,"
			+ " REVIEW_ROLE,"
			+ " REVIEW_DESC,"
            + " REVIEW_URL,"
            + " REVIEW_DIV_RIGHT,"
			+ " REVIEW_DIV_HIDDEN,"
            + " COPY_FLAG,"
            + " COPY_REASON,"
            + " COPY_HIDDEN_DIV,"
            + " COPY_URL,"
            + " ATTRIBUTE_1,"
            + " ATTRIBUTE_2,"
            + " ATTRIBUTE_3,"
            + " ATTRIBUTE_4,"
            + " ATTRIBUTE_5,"
            + " TID"
*/
            + " FROM"
			+ " SF_TASK"
			+ " WHERE"
			+ " PROCEDURE_ID = ?";
        sqlModel.setSqlStr(sqlStr);
		sqlArgs.add(procedureId);
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
		SfTaskDTO sfTask = (SfTaskDTO)dtoParameter;
		if(foreignKey.equals("procedureId")){
			sqlModel = getDeleteByProcedureIdModel(sfTask.getProcedureId());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成任务属性 SF_TASK页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		SfTaskDTO sfTask = (SfTaskDTO)dtoParameter;
			String sqlStr = "SELECT "
			+ " PROCEDURE_ID,"
			+ " CONVERT(INT,TASK_ID) TASK_ID,"
			+ " TASK_NAME,"
			+ " TASK_DESC,"
			+ " GROUP_NAME,"
			+ " ROLE_NAME,"
			+ " FLOW_TYPE,"
			+ " API,"
			+ " DURATION,"
			+ " DURATION_UNIT,"
            + " URL,"
            + " DIV_RIGHT,"
			+ " DIV_HIDDEN,"
			+ " CONTROL_TYPE,"
			+ " CYCLE_TYPE,"
            + " CYCLE_URL,"
            + " REVIEW_GROUP,"
			+ " REVIEW_ROLE,"
			+ " REVIEW_DESC,"
            + " REVIEW_URL,"
            + " REVIEW_DIV_RIGHT,"
			+ " REVIEW_DIV_HIDDEN,"
            + " COPY_FLAG,"
            + " COPY_REASON,"
            + " COPY_HIDDEN_DIV,"
            + " COPY_URL,"
            + " ATTRIBUTE_1,"
            + " ATTRIBUTE_2,"
            + " ATTRIBUTE_3,"
            + " ATTRIBUTE_4,"
            + " ATTRIBUTE_5,"
            + " TID"
            + " FROM"
			+ " SF_TASK"
			+ " WHERE"
			+ " (? <= 0 OR PROCEDURE_ID = ?)"
			+ " AND (? <= 0 OR TASK_ID = ?)"
			+ " AND (? = '' OR TASK_NAME LIKE ?)"
			+ " AND (? = '' OR TASK_DESC LIKE ?)"
			+ " AND (? = '' OR GROUP_NAME LIKE ?)"
			+ " AND (? = '' OR ROLE_NAME LIKE ?)"
			+ " AND (? <= 0 OR FLOW_TYPE = ?)"
			+ " AND (? = '' OR API LIKE ?)"
			+ " AND (? <= 0 OR DURATION = ?)"
			+ " AND (? <= 0 OR DURATION_UNIT = ?)"
            + " AND (? = '' OR URL LIKE ?)"
            + " AND (? = '' OR DIV_RIGHT LIKE ?)"
			+ " AND (? = '' OR DIV_HIDDEN LIKE ?)"
			+ " AND (? = '' OR CONTROL_TYPE LIKE ?)"
			+ " AND (? <= 0 OR CYCLE_TYPE = ?)"
            + " AND (? = '' OR CYCLE_URL LIKE ?)"
            + " AND (? = '' OR REVIEW_GROUP LIKE ?)"
			+ " AND (? = '' OR REVIEW_ROLE LIKE ?)"
			+ " AND (? = '' OR REVIEW_DESC LIKE ?)"
            + " AND (? = '' OR REVIEW_URL LIKE ?)"
            + " AND (? = '' OR REVIEW_DIV_RIGHT LIKE ?)"
			+ " AND (? = '' OR REVIEW_DIV_HIDDEN LIKE ?)"
            + " AND (? <= 0 OR COPY_FLAG = ?)"
            + " AND (? = '' OR COPY_REASON LIKE ?)"
            + " AND (? = '' OR COPY_HIDDEN_DIV LIKE ?)"
            + " AND (? = '' OR COPY_URL LIKE ?)"
            + " AND (? = '' OR ATTRIBUTE_1 LIKE ?)"
            + " AND (? = '' OR ATTRIBUTE_2 LIKE ?)"
            + " AND (? = '' OR ATTRIBUTE_3 LIKE ?)"
            + " AND (? = '' OR ATTRIBUTE_4 LIKE ?)"
            + " AND (? = '' OR ATTRIBUTE_5 LIKE ?)"
            + " AND (? <= 0 OR TID = ?)";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(sfTask.getProcedureId());
		sqlArgs.add(sfTask.getProcedureId());
		sqlArgs.add(sfTask.getTaskId());
		sqlArgs.add(sfTask.getTaskId());
		sqlArgs.add(sfTask.getTaskName());
		sqlArgs.add(sfTask.getTaskName());
		sqlArgs.add(sfTask.getTaskDesc());
		sqlArgs.add(sfTask.getTaskDesc());
		sqlArgs.add(sfTask.getGroupName());
		sqlArgs.add(sfTask.getGroupName());
		sqlArgs.add(sfTask.getRoleName());
		sqlArgs.add(sfTask.getRoleName());
		sqlArgs.add(sfTask.getFlowType());
		sqlArgs.add(sfTask.getFlowType());
		sqlArgs.add(sfTask.getApi());
		sqlArgs.add(sfTask.getApi());
		sqlArgs.add(sfTask.getDuration());
		sqlArgs.add(sfTask.getDuration());
		sqlArgs.add(sfTask.getDurationUnit());
		sqlArgs.add(sfTask.getDurationUnit());
        sqlArgs.add(sfTask.getUrl());
        sqlArgs.add(sfTask.getUrl());
        sqlArgs.add(sfTask.getDivRight());
		sqlArgs.add(sfTask.getDivRight());
		sqlArgs.add(sfTask.getDivHidden());
		sqlArgs.add(sfTask.getDivHidden());
		sqlArgs.add(sfTask.getControlType());
		sqlArgs.add(sfTask.getControlType());
		sqlArgs.add(sfTask.getCycleType());
		sqlArgs.add(sfTask.getCycleType());
        sqlArgs.add(sfTask.getCycleUrl());
        sqlArgs.add(sfTask.getCycleUrl());
        sqlArgs.add(sfTask.getReviewGroup());
		sqlArgs.add(sfTask.getReviewGroup());
		sqlArgs.add(sfTask.getReviewRole());
		sqlArgs.add(sfTask.getReviewRole());
		sqlArgs.add(sfTask.getReviewDesc());
		sqlArgs.add(sfTask.getReviewDesc());
        sqlArgs.add(sfTask.getReviewUrl());
        sqlArgs.add(sfTask.getReviewUrl());
        sqlArgs.add(sfTask.getReviewDivRight());
		sqlArgs.add(sfTask.getReviewDivRight());
		sqlArgs.add(sfTask.getReviewDivHidden());
		sqlArgs.add(sfTask.getReviewDivHidden());
        sqlArgs.add(sfTask.getCopyFlag());
        sqlArgs.add(sfTask.getCopyFlag());
        sqlArgs.add(sfTask.getCopyReason());
        sqlArgs.add(sfTask.getCopyReason());
        sqlArgs.add(sfTask.getCopyHiddenDiv());
        sqlArgs.add(sfTask.getCopyHiddenDiv());
        sqlArgs.add(sfTask.getCopyUrl());
        sqlArgs.add(sfTask.getCopyUrl());
        sqlArgs.add(sfTask.getAttribute1());
        sqlArgs.add(sfTask.getAttribute1());
        sqlArgs.add(sfTask.getAttribute2());
        sqlArgs.add(sfTask.getAttribute2());
        sqlArgs.add(sfTask.getAttribute3());
        sqlArgs.add(sfTask.getAttribute3());
        sqlArgs.add(sfTask.getAttribute4());
        sqlArgs.add(sfTask.getAttribute4());
        sqlArgs.add(sfTask.getAttribute5());
        sqlArgs.add(sfTask.getAttribute5());
        sqlArgs.add(sfTask.getTid());
        sqlArgs.add(sfTask.getTid());

		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

    /**
     * 功能：从工程名称与过程名称找第一个任务
     * @param projName 工程名称
     * @param procName 过程名称
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getFirstTaskModel(String projName, String procName) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
            + " ST.PROCEDURE_ID,"
            + " ST.TASK_ID,"
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
            + " ST.GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.FLOW_TYPE,"
            + " ST.API,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.URL,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " ST.CYCLE_URL,"
            + " ST.REVIEW_GROUP,"
            + " ST.REVIEW_ROLE,"
            + " ST.REVIEW_DESC,"
            + " ST.REVIEW_URL,"
            + " ST.REVIEW_DIV_RIGHT,"
            + " ST.REVIEW_DIV_HIDDEN,"
            + " ST.TID,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL,"
            + " ST.ATTRIBUTE_1,"
            + " ST.ATTRIBUTE_2,"
            + " ST.ATTRIBUTE_3,"
            + " ST.ATTRIBUTE_4,"
            + " ST.ATTRIBUTE_5"
            + " FROM"
            + " SF_TASK ST, SF_FLOW SF, SF_PROJECT_V SPV, SF_PROCEDURE SP, SF_TASK ST2"
            + " WHERE"
            + " SPV.PROJECT_NAME = ?"
            + " AND SPV.PROJECT_ID = SP.PROJECT_ID"
            + " AND SP.PROCEDURE_NAME = ?"
            + " AND ST.PROCEDURE_ID = SP.PROCEDURE_ID"
            + " AND SF.PROCEDURE_ID = SP.PROCEDURE_ID"
            + " AND ST2.TASK_NAME = 'START'"
            + " AND SF.FROM_TASK_ID = ST2.TASK_ID"
            + " AND SF.TO_TASK_ID = ST.TASK_ID";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(projName);
        sqlArgs.add(procName);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：从工程名称与过程名称找下一个任务
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getNextTaskModel() {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        SfTaskDTO sfTask = (SfTaskDTO)dtoParameter;
            String sqlStr = "SELECT "
            + " ST.PROCEDURE_ID,"
            + " ST.TASK_ID,"
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
            + " ST.GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.FLOW_TYPE,"
            + " ST.API,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.URL,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " ST.CYCLE_URL,"
            + " ST.REVIEW_GROUP,"
            + " ST.REVIEW_ROLE,"
            + " ST.REVIEW_DESC,"
            + " ST.REVIEW_URL,"
            + " ST.REVIEW_DIV_RIGHT,"
            + " ST.REVIEW_DIV_HIDDEN,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL,"
            + " ATTRIBUTE_1,"
            + " ATTRIBUTE_2,"
            + " ATTRIBUTE_3,"
            + " ATTRIBUTE_4,"
            + " ATTRIBUTE_5"
            + " FROM"
            + " SF_TASK ST, SF_FLOW SF"
            + " WHERE"
            + " ST.PROCEDURE_ID = ?"
            + " AND SF.PROCEDURE_ID = ?"
            + " AND SF.FROM_TASK_ID = ?"
            + " AND SF.TO_TASK_ID = ST.TASK_ID";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(sfTask.getProcedureId());
        sqlArgs.add(sfTask.getProcedureId());
        sqlArgs.add(sfTask.getTaskId());

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：获取过程任务列表
     * @param procedureId  procedure ID
     * @return SQLModel
     */
    public SQLModel getProcedureTaskModel(int procedureId){
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT " 
        				+ " TID," 
        				+ " TASK_NAME " 
        				+ " FROM " 
        				+ " SF_TASK " 
        				+ " WHERE PROCEDURE_ID = ?"
                        + " AND TASK_NAME <> 'SPLIT'"
                        + " AND TASK_NAME <> 'JOIN'"
        				+ " ORDER BY TASK_ID";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(procedureId);
        
        sqlModel.setArgs(sqlArgs);
        
        return sqlModel;
    } 

    /**
     * 功能：获取过程任务列表
     * @param procedureId  procedure ID
     * @return SQLModel
     */
    public SQLModel getProcedureTaskModel3(int procedureId){
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
        				+ " TID,"
        				+ " ST.TASK_NAME "
        				+ " FROM "
        				+ " SF_TASK ST"
        				+ " WHERE ST.PROCEDURE_ID = ?"
                        + " AND ST.TASK_NAME <> 'SPLIT'"
                        + " AND ST.TASK_NAME <> 'JOIN'"
                        + " AND ST.TID NOT IN"
                        + " (SELECT SA.TASK_TID"
                        + " FROM SF_API SA, SF_PROCEDURE SP, SF_PROJECT SPJ"
                        + " WHERE SP.PROCEDURE_ID = ?"
                        + " AND SP.PROJECT_ID = SPJ.PROJECT_ID"
                        + " AND SPJ.PROJECT_NAME = SA.PROJECT_NAME"
                        + " AND SP.PROCEDURE_NAME = SA.PROCEDURE_NAME)"
                        + " ORDER BY TASK_ID";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(procedureId);
        sqlArgs.add(procedureId);

        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    /**
     * 功能：获取过程任务列表
     * @param procedureId procedure ID
     * @return SQLModel
     */
    public SQLModel getProcedureTaskModel2(int procedureId){
    	SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT " 
        				+ " TID," 
        				+ " TASK_NAME "
        				+ " FROM " 
        				+ " SF_TASK " 
        				+ " WHERE PROCEDURE_ID = ?"
                        + " AND TASK_NAME <> 'SPLIT'"
                        + " AND TASK_NAME <> 'JOIN'"
                        + " ORDER BY TASK_ID";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(procedureId);
        
        sqlModel.setArgs(sqlArgs);
        
        return sqlModel;
    }
    
    /**
     * 功能：从工程名称与过程名称找第一个任务
     * @param taskId 任务 ID
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getWaitTypeModel(int taskId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " dbo.SFK_GET_JOIN_STATUS2(ST.TASK_ID, 0, NULL) WAIT_TYPE"
            + " FROM"
            + " SF_TASK ST"
            + " WHERE"
            + " ST.TASK_ID = ?";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(taskId);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：从工程名称与过程名称找第一个任务
     * @param fromTask 前一任务 ID
     * @param toTask 后一任务 ID
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel isNextTaskModel(int fromTask, int toTask) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " FLOW_ID"
            + " FROM"
            + " SF_FLOW SF"
            + " WHERE"
            + " SF.FROM_TASK_ID = ?"
            + " AND SF.TO_TASK_ID = ?";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(fromTask);
        sqlArgs.add(toTask);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：从工程名称与过程名称找第一个任务
     * @param fromTask 前一任务 ID
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel isNextJoinModel(int fromTask) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " ST.TASK_ID"
            + " FROM"
            + " SF_FLOW SF, SF_TASK ST"
            + " WHERE"
            + " SF.FROM_TASK_ID = ?"
            + " AND SF.TO_TASK_ID = ST.TASK_ID"
            + " AND ST.TASK_NAME = 'JOIN'";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(fromTask);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：下一任务是否 STOP
     * @param fromTask 前一任务 ID
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel isNextStopModel(int fromTask) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " ST.TASK_ID"
            + " FROM"
            + " SF_FLOW SF, SF_TASK ST"
            + " WHERE"
            + " SF.FROM_TASK_ID = ?"
            + " AND SF.TO_TASK_ID = ST.TASK_ID"
            + " AND ST.TASK_NAME = 'STOP'";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(fromTask);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    /**
     * 功能：从工程名称与过程名称找第一个任务
     * @param taskId 任务 ID
     * @param handler 继承 * 的组别
     * @param plus 继承 + 的组别
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getMultipleTaskCountModel(int taskId, String handler, String plus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " ST.TASK_ID,"
            + " SPJ.PROJECT_NAME,"
            + " SP.PROCEDURE_NAME,"
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
            + " dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?)GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.API,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " dbo.SFK_GET_USERS_REALNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) REAL_NAMES,"
            + " dbo.SFK_GET_USERS_USERNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) USERNAMES,"
            + " ST.FLOW_TYPE TASK_FLOW_TYPE,"
            + " SF.FLOW_ID,"
            + " SF.FLOW_DESC,"
            + " SF.HINT, "
            + " SF.FLOW_CODE,"
            + " SF.FLOW_PROP,"
            + " SF.FLOW_TYPE,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL"
            + " FROM"
            + " SF_TASK ST, SF_FLOW SF, SF_PROCEDURE SP, SF_PROJECT SPJ"
            + " WHERE"
            + " SF.FROM_TASK_ID = ?"
            + " AND ST.TASK_ID = SF.TO_TASK_ID"
            + " AND SP.PROCEDURE_ID = SF.PROCEDURE_ID"
            + " AND SPJ.PROJECT_ID = SP.PROJECT_ID";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(taskId);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    /**
     * 功能：从工程名称与过程名称找第一个任务
     * @param taskId 任务 ID
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getCurrentTaskModel(int taskId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " ST.TASK_ID,"
            + " SPJ.PROJECT_NAME,"
            + " SP.PROCEDURE_NAME,"
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
            + " ST.GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.REVIEW_GROUP,"
            + " ST.REVIEW_ROLE,"
            + " ST.REVIEW_DESC,"
            + " ST.REVIEW_URL,"
            + " ST.REVIEW_DIV_RIGHT,"
            + " ST.REVIEW_DIV_HIDDEN,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.API,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL,"
            + " ST.ATTRIBUTE_1,"
            + " ST.ATTRIBUTE_2,"
            + " ST.ATTRIBUTE_3,"
            + " ST.ATTRIBUTE_4,"
            + " ST.ATTRIBUTE_5"                    
            + " FROM"
            + " SF_TASK ST, SF_PROCEDURE SP, SF_PROJECT SPJ"
            + " WHERE"
            + " ST.TASK_ID = ?"
            + " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID"
            + " AND SP.PROJECT_ID = SPJ.PROJECT_ID";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(taskId);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }    

    public SQLModel getPreviousTaskModel(String actId) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " SAL.SFACT_TASK_ID TASK_ID,"
            + " SPJ.PROJECT_NAME PROJECT_NAME,"
            + " SAL.SFACT_PROC_NAME PROCEDURE_NAME,"
            + " SAL.SFACT_TASK_NAME TASK_NAME,"
            + " SAL.SFACT_TASK_DESC TASK_DESC,"
            + " GET_MASK_GROUP(SAL.SFACT_TASK_GROUP, SAL.SFACT_HANDLER_GROUP, SAL.SFACT_PLUS_GROUP) GROUP_NAME,"
            + " SAL.SFACT_TASK_ROLE ROLE_NAME,"
            + " SAL.SFACT_TASK_DURATION DURATION,"
            + " SAL.SFACT_TASK_WORK_TYPE DURATION_UNIT,"
            + " SAI.SFACT_TASK_API_NAME API,"
            + " SAI.SFACT_TASK_DIV_RIGHT DIV_RIGHT,"
            + " SAI.SFACT_TASK_HIDDEN DIV_HIDDEN,"
            + " SAI.SFACT_TASK_CTL CONTROL_TYPE,"
            + " SAI.SFACT_TASK_CYCLE_TYPE CYCLE_TYPE,"
            + " SU.USERNAME REAL_NAMES,"
            + " SU.LOGIN_NAME USERNAMES,"
            + " 0 TASK_FLOW_TYPE,"
            + " 0 FLOW_ID,"
            + " '' FLOW_DESC,"
            + " '' HINT,"
            + " '' FLOW_CODE,"
            + " 0 FLOW_PROP,"
            + " 0 FLOW_TYPE,"
            + " 0 COPY_FLAG,"
            + " '' COPY_REASON,"
            + " '' COPY_HIDDEN_DIV,"
            + " '' COPY_URL,"
            + " SAI.SFACT_TASK_ATTRIBUTE_1 ATTRIBUTE_1,"
            + " SAI.SFACT_TASK_ATTRIBUTE_2 ATTRIBUTE_2,"
            + " SAI.SFACT_TASK_ATTRIBUTE_3 ATTRIBUTE_3,"
            + " SAI.SFACT_TASK_ATTRIBUTE_4 ATTRIBUTE_4,"
            + " SAI.SFACT_TASK_ATTRIBUTE_5 ATTRIBUTE_5"
            + " FROM"
            + " SF_ACT_INFO SAI, SF_PROJECT SPJ, SF_PROCEDURE SP, SF_ACT_LOG SAL, SF_USER SU"
            + " WHERE"
            + " SAI.SFACT_ACT_ID = ?"
            + " AND SAI.SFACT_RETURN_ACT_ID = SAL.SFACT_ACT_ID"
            + " AND SP.PROCEDURE_ID = SAL.SFACT_PROC_ID"
            + " AND SPJ.PROJECT_ID = SP.PROJECT_ID"
            + " AND SAL.SFACT_SIGN_USER = SU.LOGIN_NAME";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(actId);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：从工程名称与过程名称找第一个任务
     * @param procId 过程 ID
     * @param taskName 任务名称
     * @param handler 继承 * 的组别
     * @param plus 继承 + 的组别
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getTaskByNameModel(int procId, String taskName, String handler, String plus, String msg) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " ST.TASK_ID,"
            + " SPJ.PROJECT_NAME,"
            + " SP.PROCEDURE_NAME,"
            + " SP.PROCEDURE_ID,"        
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
            + " dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?) GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.API,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " dbo.SFK_GET_USERS_REALNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) REAL_NAMES,"
            + " dbo.SFK_GET_USERS_USERNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) USERNAMES,"
            + " ST.FLOW_TYPE TASK_FLOW_TYPE,"
            + " '' FLOW_ID,"
            + " ? FLOW_DESC,"
            + " ? HINT,"
            + " '' FLOW_CODE,"
            + " '0' FLOW_PROP,"
            + " '0' FLOW_TYPE,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL,"
            + " ST.ATTRIBUTE_1,"
            + " ST.ATTRIBUTE_2,"
            + " ST.ATTRIBUTE_3,"
            + " ST.ATTRIBUTE_4,"
            + " ST.ATTRIBUTE_5"
            + " FROM"
            + " SF_TASK ST, SF_PROCEDURE SP, SF_PROJECT SPJ"
            + " WHERE"
            + " ST.PROCEDURE_ID = ?"
            + " AND ST.TASK_NAME = ?"
            + " AND SPJ.PROJECT_ID = SP.PROJECT_ID";
        if(!taskName.equals("STOP")) {
            sqlStr += " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID";
        }
        
        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(msg);
        sqlArgs.add(msg);
        sqlArgs.add(procId);
        sqlArgs.add(taskName);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
    
    /**
     * 功能：找出所有在传入条件下的下一任务
     * @param taskId 任务 ID
     * @param flowCode 流控制码
     * @param handler 继承 * 的组别
     * @param plus  继承 + 的组别
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getTaskCountModel(int taskId, String flowCode, String handler, String plus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " ST.TASK_ID,"
            + " SPJ.PROJECT_NAME,"
            + " SP.PROCEDURE_NAME,"
            + " SP.PROCEDURE_ID,"
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
            + " dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?) GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.API,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " dbo.SFK_GET_USERS_REALNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) REAL_NAMES,"
            + " dbo.SFK_GET_USERS_USERNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) USERNAMES,"
            + " ST.FLOW_TYPE TASK_FLOW_TYPE,"
            + " SF.FLOW_ID,"
            + " SF.FLOW_DESC,"
            + " SF.HINT,"
            + " SF.FLOW_CODE,"
            + " SF.FLOW_PROP,"
            + " SF.FLOW_TYPE,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL,"
            + " ST.ATTRIBUTE_1,"
            + " ST.ATTRIBUTE_2,"
            + " ST.ATTRIBUTE_3,"
            + " ST.ATTRIBUTE_4,"
            + " ST.ATTRIBUTE_5"
            + " FROM"
            + " SF_TASK ST, SF_FLOW SF, SF_PROCEDURE SP, SF_PROJECT SPJ"
            + " WHERE"
            + " SF.FROM_TASK_ID = ?"
            + " AND ST.TASK_ID = SF.TO_TASK_ID"
            + " AND (? = '' OR SF.FLOW_CODE = ?)"
            + " AND SP.PROCEDURE_ID = SF.PROCEDURE_ID"
            + " AND SPJ.PROJECT_ID = SP.PROJECT_ID"
            + " ORDER BY SF.FLOW_CODE";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(taskId);
        sqlArgs.add(flowCode);
        sqlArgs.add(flowCode);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：找出所有在传入条件下的下一任务
     * @param taskId 任务 ID
     * @param flowCode 流控制码
     * @param handler 继承 * 的组别
     * @param plus  继承 + 的组别
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getTaskCountModel2(int taskId, String flowCode, String handler, String plus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " ST.TASK_ID,"
            + " SPJ.PROJECT_NAME,"
            + " SP.PROCEDURE_NAME,"
            + " SP.PROCEDURE_ID,"
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
//            + " ST.GROUP_NAME,"
//            + " ? HANDLER_GROUP,"
//            + " ? PLUS_GROUP,"
            + " dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?) GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.API,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " dbo.SFK_GET_USERS_REALNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) REAL_NAMES,"
            + " dbo.SFK_GET_USERS_USERNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) USERNAMES,"
            + " ST.FLOW_TYPE TASK_FLOW_TYPE,"
            + " SF.FLOW_ID,"
            + " SF.FLOW_DESC,"
            + " SF.HINT,"
            + " SF.FLOW_CODE,"
            + " SF.FLOW_PROP,"
            + " SF.FLOW_TYPE,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL,"
            + " ST.ATTRIBUTE_1,"
            + " ST.ATTRIBUTE_2,"
            + " ST.ATTRIBUTE_3,"
            + " ST.ATTRIBUTE_4,"
            + " ST.ATTRIBUTE_5"
            + " FROM"
            + " SF_TASK ST, SF_FLOW SF, SF_PROCEDURE SP, SF_PROJECT SPJ"
            + " WHERE"
            + " SF.FROM_TASK_ID = ?"
            + " AND ST.TASK_ID = SF.TO_TASK_ID"
            + " AND (? = '' OR SF.FLOW_CODE = ?)"
            + " AND SP.PROCEDURE_ID = SF.PROCEDURE_ID"
            + " AND SPJ.PROJECT_ID = SP.PROJECT_ID"
            + " ORDER BY SF.FLOW_CODE";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(taskId);
        sqlArgs.add(flowCode);
        sqlArgs.add(flowCode);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：找出所有在传入条件下的下一任务
     * @param taskId 任务 ID
     * @param flowCode 流控制码
     * @param handler 继承 * 的组别
     * @param plus  继承 + 的组别
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getTaskCountModel3(int taskId, String flowCode, String handler, String plus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " ST.TASK_ID,"
            + " SPJ.PROJECT_NAME,"
            + " SP.PROCEDURE_NAME,"
            + " SP.PROCEDURE_ID,"
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
//            + " ST.GROUP_NAME,"
            + " ? HANDLER_GROUP,"
            + " ? PLUS_GROUP,"
            + " dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?) GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.API,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " ST.FLOW_TYPE TASK_FLOW_TYPE,"
            + " SF.FLOW_ID,"
            + " SF.FLOW_DESC,"
            + " SF.HINT,"
            + " SF.FLOW_CODE,"
            + " SF.FLOW_PROP,"
            + " SF.FLOW_TYPE,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL,"
            + " ST.ATTRIBUTE_1,"
            + " ST.ATTRIBUTE_2,"
            + " ST.ATTRIBUTE_3,"
            + " ST.ATTRIBUTE_4,"
            + " ST.ATTRIBUTE_5"
            + " FROM"
            + " SF_TASK ST, SF_FLOW SF, SF_PROCEDURE SP, SF_PROJECT SPJ"
            + " WHERE"
            + " SF.FROM_TASK_ID = ?"
            + " AND ST.TASK_ID = SF.TO_TASK_ID"
            + " AND (? = '' OR SF.FLOW_CODE = ?)"
            + " AND SP.PROCEDURE_ID = SF.PROCEDURE_ID"
            + " AND SPJ.PROJECT_ID = SP.PROJECT_ID"
            + " ORDER BY SF.FLOW_CODE";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(taskId);
        sqlArgs.add(flowCode);
        sqlArgs.add(flowCode);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：找出传入条件下的任务
     * @param procId 过程 ID
     * @param taskName 任务名称
     * @param handler 继承 * 的组别
     * @param plus  继承 + 的组别
     * @param msg 保存的 app_msg
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getTaskInfo(int procId, String taskName, String handler, String plus, String msg) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " ST.TASK_ID,"
            + " SPJ.PROJECT_NAME,"
            + " SP.PROCEDURE_NAME,"
            + " SP.PROCEDURE_ID,"
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
            + " dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?) GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.API,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " dbo.SFK_GET_USERS_REALNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) REAL_NAMES,"
            + " dbo.SFK_GET_USERS_USERNAME(SPJ.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) USERNAMES,"
            + " ST.FLOW_TYPE TASK_FLOW_TYPE,"
            + " '' FLOW_ID,"
            + " '' FLOW_DESC,"
            + " ?  HINT,"
            + " '' FLOW_CODE,"
            + " '0' FLOW_PROP,"
            + " '0' FLOW_TYPE,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL,"
            + " ST.ATTRIBUTE_1,"
            + " ST.ATTRIBUTE_2,"
            + " ST.ATTRIBUTE_3,"
            + " ST.ATTRIBUTE_4,"
            + " ST.ATTRIBUTE_5"
            + " FROM"
            + " SF_TASK ST, SF_PROCEDURE SP, SF_PROJECT SPJ"
            + " WHERE"
            + " SP.PROCEDURE_ID = ?"
            + " AND ST.TASK_NAME = ?"
            + " AND SPJ.PROJECT_ID = SP.PROJECT_ID";
        if(!taskName.equals("STOP")) {
            sqlStr += " AND SP.PROCEDURE_ID = ST.PROCEDURE_ID";
        }

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(msg);
        sqlArgs.add(procId);
        sqlArgs.add(taskName);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：从工程名称与过程名称找子流的第一个任务
     * @param projName 工程名称
     * @param procName 过程名称
     * @param group 组别
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getFirstTaskWithGroupModel(String projName, String procName, String group) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
            + " ST.TASK_ID,"
            + " SPV.PROJECT_NAME,"
            + " SP.PROCEDURE_NAME,"
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
            + " ? GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.API,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " dbo.SFK_GET_USERS_REALNAME(SPV.PROJECT_NAME, ?, ST.ROLE_NAME) REAL_NAMES,"
            + " dbo.SFK_GET_USERS_USERNAME(SPV.PROJECT_NAME, ?, ST.ROLE_NAME) USERNAMES,"
            + " ST.FLOW_TYPE TASK_FLOW_TYPE,"
            + " SF.FLOW_ID,"
            + " SF.FLOW_DESC,"
            + " SF.HINT,"
            + " SF.FLOW_CODE,"
            + " SF.FLOW_PROP,"
            + " SF.FLOW_TYPE,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL,"
            + " ST.ATTRIBUTE_1,"
            + " ST.ATTRIBUTE_2,"
            + " ST.ATTRIBUTE_3,"
            + " ST.ATTRIBUTE_4,"
            + " ST.ATTRIBUTE_5"
            + " FROM"
            + " SF_TASK ST, SF_FLOW SF, SF_PROJECT_V SPV, SF_PROCEDURE SP, SF_TASK ST2"
            + " WHERE"
            + " SPV.PROJECT_NAME = ?"
            + " AND SPV.PROJECT_ID = SP.PROJECT_ID"
            + " AND SP.PROCEDURE_NAME = ?"
            + " AND ST.PROCEDURE_ID = SP.PROCEDURE_ID"
            + " AND SF.PROCEDURE_ID = SP.PROCEDURE_ID"
            + " AND ST2.TASK_NAME = 'START'"
            + " AND SF.FROM_TASK_ID = ST2.TASK_ID"
//            + " AND SF.FROM_TASK_ID = 1"
            + " AND SF.TO_TASK_ID = ST.TASK_ID";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(group);
        sqlArgs.add(group);
        sqlArgs.add(group);
        sqlArgs.add(projName);
        sqlArgs.add(procName);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：从工程名称与过程名称找子流的第一个任务
     * @param projName 工程名称
     * @param procName 过程名称
     * @param handler 继承 * 的组别
     * @param plus  继承 + 的组别
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getSubflowFirstTaskModel(String projName, String procName, String handler, String plus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT "
            + " ST.TASK_ID,"
            + " SPV.PROJECT_NAME,"
            + " SP.PROCEDURE_ID,"
            + " SP.PROCEDURE_NAME,"
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
            + " dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?) GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.API,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " dbo.SFK_GET_USERS_REALNAME(SPV.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) REAL_NAMES,"
            + " dbo.SFK_GET_USERS_USERNAME(SPV.PROJECT_NAME, dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?), ST.ROLE_NAME) USERNAMES,"
            + " ST.FLOW_TYPE TASK_FLOW_TYPE,"
            + " SF.FLOW_ID,"
            + " SF.FLOW_DESC,"
            + " SF.HINT,"
            + " SF.FLOW_CODE,"
            + " SF.FLOW_PROP,"
            + " SF.FLOW_TYPE,"
            + " ST.TID,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL,"
            + " ST.ATTRIBUTE_1,"
            + " ST.ATTRIBUTE_2,"
            + " ST.ATTRIBUTE_3,"
            + " ST.ATTRIBUTE_4,"
            + " ST.ATTRIBUTE_5,"
            + " ? HANDLER_GROUP,"
            + " ? PLUS_GROUP"
            + " FROM"
            + " SF_TASK ST, SF_FLOW SF, SF_PROJECT_V SPV, SF_PROCEDURE SP, SF_TASK ST2"
            + " WHERE"
            + " SPV.PROJECT_NAME = ?"
            + " AND SPV.PROJECT_ID = SP.PROJECT_ID"
            + " AND SP.PROCEDURE_NAME = ?"
            + " AND ST.PROCEDURE_ID = SP.PROCEDURE_ID"
            + " AND SF.PROCEDURE_ID = SP.PROCEDURE_ID"
            + " AND ST2.TASK_NAME = 'START'"
            + " AND SF.FROM_TASK_ID = ST2.TASK_ID"
//            + " AND SF.FROM_TASK_ID = 1"
            + " AND SF.TO_TASK_ID = ST.TASK_ID";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(projName);
        sqlArgs.add(procName);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能: 找出返回任务
     * @param taskId 任务 ID
     * @param returnTime 此任务的比对时间, 找出的任务是最后一个最接近此时间的任条
     * @param caseID 案件 ID
     * @param handler 继承 * 的组别
     * @param plus  继承 + 的组别
     * @return SQLModel 返回页面翻页查询SQLModel
     */
    public SQLModel getBackTaskModel(int taskId, String returnTime, String caseID, String handler, String plus) {
        SQLModel sqlModel = new SQLModel();
        List sqlArgs = new ArrayList();
            String sqlStr = "SELECT "
            + " ST.TASK_ID,"
            + " SPJ.PROJECT_NAME,"
            + " SP.PROCEDURE_NAME,"
            + " ST.TASK_NAME,"
            + " ST.TASK_DESC,"
            + " dbo.SFK_GET_MASK_GROUP(ST.GROUP_NAME, ?, ?) GROUP_NAME,"
            + " ST.ROLE_NAME,"
            + " ST.DURATION,"
            + " ST.DURATION_UNIT,"
            + " ST.API,"
            + " ST.DIV_RIGHT,"
            + " ST.DIV_HIDDEN,"
            + " ST.CONTROL_TYPE,"
            + " ST.CYCLE_TYPE,"
            + " SAL.SFACT_PICK_USER REAL_NAMES,"
            + " SAL.SFACT_PICK_USER USERNAMES,"
            + " ST.FLOW_TYPE TASK_FLOW_TYPE,"
            + " SF.FLOW_ID,"
            + " SF.FLOW_DESC,"
            + " SF.HINT,"
            + " SF.FLOW_CODE,"
            + " SF.FLOW_PROP,"
            + " SF.FLOW_TYPE,"
            + " ST.COPY_FLAG,"
            + " ST.COPY_REASON,"        
            + " ST.COPY_HIDDEN_DIV,"
            + " ST.COPY_URL,"        
            + " ST.ATTRIBUTE_1,"
            + " ST.ATTRIBUTE_2,"
            + " ST.ATTRIBUTE_3,"
            + " ST.ATTRIBUTE_4,"
            + " ST.ATTRIBUTE_5"
            + " FROM"
            + " SF_TASK ST, SF_FLOW SF, SF_PROCEDURE SP, SF_PROJECT SPJ, SF_ACT_LOG SAL"
            + " WHERE"
            + " SF.TO_TASK_ID = ?"
            + " AND ST.TASK_ID = SF.FROM_TASK_ID"
            + " AND SP.PROCEDURE_ID = SF.PROCEDURE_ID"
            + " AND SPJ.PROJECT_ID = SP.PROJECT_ID"
            + " AND SAL.SFACT_TASK_NAME = ST.TASK_NAME"
            + " AND (? = '' OR SAL.SFACT_COMPLETE_DATE <= CONVERT(DATETIME, ?))"
            + " AND SAL.SFACT_CASE_ID = ?"
            + " ORDER BY SAL.SFACT_COMPLETE_DATE DESC";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(handler);
        sqlArgs.add(plus);
        sqlArgs.add(taskId);
        sqlArgs.add(returnTime);
        sqlArgs.add(caseID);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }

    /**
     * 功能：得到最后提交的任务
     * @param procName 过程名
     * @return SQLModel 返回页面翻页查询SQLModel
     * @throws com.sino.base.exception.SQLModelException 发生日历异常时转化为该异常抛出
     */
    public SQLModel getLattestTaskModel(String procName) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ST.*"
                + " FROM SF_TASK ST,"
                + " (SELECT TASK_NAME, Max(TASK_ID) TASK_ID "
                + " FROM SF_TASK  GROUP BY TASK_NAME) T"
                + " WHERE ST.TASK_ID = T.TASK_ID"
                + " AND (? = '' OR ST.TASK_NAME LIKE ?)";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(procName);
        sqlArgs.add(procName);

        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel getStartStopTask(int id) throws SQLModelException {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT ST.*"
                + " FROM SF_TASK ST";
        if(id == -1) {
            sqlStr += " WHERE ST.TASK_NAME = 'START'";
        } else {
            sqlStr += " WHERE ST.TASK_NAME = 'STOP'";
        }

        sqlModel.setSqlStr(sqlStr);
        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel insertIfNotExists(int taskId, String taskName, int procId, int tid) {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        String sqlStr =
//                " INSERT INTO SF_TASK(TASK_ID, PROCEDURE_ID, TASK_NAME, FLOW_TYPE, TID) SELECT"
//                + " DISTINCT ?, ?, ?, 0, ?"
                " INSERT INTO SF_TASK(PROCEDURE_ID, TASK_NAME, FLOW_TYPE, TID) SELECT"
                + " DISTINCT ?, ?, 0, ?";
//                + " FROM SF_TASK";
       if(taskId == -1)
            sqlStr += " WHERE NOT EXISTS(SELECT NULL FROM SF_TASK WHERE TASK_NAME = 'START')";
       else if(taskId == -2)
            sqlStr += " WHERE NOT EXISTS(SELECT NULL FROM SF_TASK WHERE TASK_NAME = 'STOP')";
       else
            sqlStr += " WHERE NOT EXISTS(SELECT NULL FROM SF_TASK WHERE TASK_ID = " + String.valueOf(taskId) + ")";

        sqlModel.setSqlStr(sqlStr);

        sqlArgs.add(procId);
        sqlArgs.add(taskName);
        sqlArgs.add(tid);

        sqlModel.setArgs(sqlArgs);

        return sqlModel;
    }

    public SQLModel clearOldProjectsTasks() {
        SQLModel sqlModel = new SQLModel();

        String sqlStr = "DELETE SF_TASK"
                + " WHERE PROCEDURE_ID NOT IN"
                + " (SELECT DISTINCT SP.PROCEDURE_ID"
                + " FROM SF_PROCEDURE SP"
                + " WHERE EXISTS (SELECT NULL"
                + " FROM SF_ACT_INFO SAI"
                + " WHERE SAI.SFACT_PROC_ID = SP.PROCEDURE_ID)"
                + " OR EXISTS (SELECT NULL"
                + " FROM SF_PROJECT_V SFV"
                + " WHERE SFV.PROJECT_ID = SP.PROJECT_ID))"
                + " AND TASK_NAME <> 'START'"
                + " AND TASK_NAME <> 'STOP'";

        sqlModel.setSqlStr(sqlStr);

        return sqlModel;
    }

    public SQLModel getUserModel(int taskId, String caseId) {
        SQLModel sqlModel = new SQLModel();

        List sqlArgs = new ArrayList();
        String sqlStr = "SELECT SAL.SFACT_SIGN_USER"
                + " FROM SF_ACT_LOG SAL"
                + " WHERE SAL.SFACT_TASK_ID = ?"
                + " AND SAL.SFACT_CASE_ID = ?";

        sqlModel.setSqlStr(sqlStr);
        sqlArgs.add(taskId);
        sqlArgs.add(caseId);

        sqlModel.setArgs(sqlArgs);
        return sqlModel;
    }
}