package com.sino.ams.sampling.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.dto.AmsAssetsSamplingTaskDTO;
import com.sino.ams.system.user.dto.SfUserDTO;


/**
 * <p>Title: AmsAssetsSamplingTaskModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsSamplingTaskModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsAssetsSamplingTaskModel extends AMSSQLProducer {

	/**
	 * 功能：资产抽查任务表 AMS_ASSETS_SAMPLING_TASK 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsSamplingTaskDTO 本次操作的数据
	 */
	public AmsAssetsSamplingTaskModel(SfUserDTO userAccount, AmsAssetsSamplingTaskDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成资产抽查任务表 AMS_ASSETS_SAMPLING_TASK数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
							+ " AMS_ASSETS_SAMPLING_TASK("
							+ " TASK_ID,"
							+ " TASK_NO,"
							+ " TASK_NAME,"
							+ " TASK_DESC,"
							+ " START_DATE,"
							+ " END_DATE,"
							+ " SAMPLING_TYPE,"
							+ " SAMPLING_RATIO,"
							+ " CREATED_OU,"
							+ " CREATION_DATE,"
							+ " CREATED_BY,"
							+ " TASK_STATUS,"
							+ " OPENED_DATE,"
							+ " OPENED_BY"
							+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?, ?)";

			sqlArgs.add(dto.getTaskId());
			sqlArgs.add(dto.getTaskNo());
			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getTaskDesc());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getSamplingType());
			sqlArgs.add(dto.getSamplingRatio());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getTaskStatus());
			sqlArgs.add(dto.getOpenedDate());
			sqlArgs.add(dto.getOpenedBy());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产抽查任务表 AMS_ASSETS_SAMPLING_TASK数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO) dtoParameter;
			String sqlStr = "UPDATE AMS_ASSETS_SAMPLING_TASK"
							+ " SET"
							+ " TASK_NAME = ?,"
							+ " TASK_DESC = ?,"
							+ " START_DATE = ?,"
							+ " END_DATE = ?,"
							+ " SAMPLING_TYPE = ?,"
							+ " SAMPLING_RATIO = ?,"
							+ " LAST_UPDATE_DATE = GETDATE(),"
							+ " LAST_UPDATE_BY = ?,"
							+ " TASK_STATUS = ?"
							+ " WHERE"
							+ " TASK_ID = ?"
							+ " AND CREATED_OU = ?";

			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getTaskDesc());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getSamplingType());
			sqlArgs.add(dto.getSamplingRatio());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getTaskStatus());
			sqlArgs.add(dto.getTaskId());
			sqlArgs.add(userAccount.getOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：获取资产抽查任务表 AMS_ASSETS_SAMPLING_TASK 任务取消SQL
	 * @return SQLModel 资产抽查任务表 AMS_ASSETS_SAMPLING_TASK 任务取消SQL
	 */
	public SQLModel getTaskCancelModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO) dtoParameter;
		String sqlStr = "UPDATE AMS_ASSETS_SAMPLING_TASK"
						+ " SET"
						+ " TASK_STATUS = ?,"
						+ " CANCELED_DATE = GETDATE(),"
						+ " CANCELED_BY = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " TASK_ID = ?"
						+ " AND TASK_STATUS = ?"
						+ " AND CREATED_OU = ?";

		sqlArgs.add(SamplingDicts.TSK_STATUS1_CANCELED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTaskId());
		sqlArgs.add(SamplingDicts.TSK_STATUS1_SAVE_TEMP);
		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取资产抽查任务表 AMS_ASSETS_SAMPLING_TASK 状态变更SQL
	 * @return SQLModel 资产抽查任务表 AMS_ASSETS_SAMPLING_TASK 任务取消SQL
	 */
	public SQLModel getTaskCloseModel(){
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO) dtoParameter;
		String sqlStr = "UPDATE AMS_ASSETS_SAMPLING_TASK"
						+ " SET"
						+ " TASK_STATUS = ?,"
						+ " OPENED_DATE = NULL,"
						+ " OPENED_BY = NULL,"
						+ " CLOSED_DATE = GETDATE(),"
						+ " CLOSED_BY = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " TASK_ID = ?"
						+ " AND TASK_STATUS = ?"
						+ " AND CREATED_OU = ?";


		sqlArgs.add(SamplingDicts.TSK_STATUS1_CLOSED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTaskId());
		sqlArgs.add(SamplingDicts.TSK_STATUS1_OPENING);
		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：获取资产抽查任务表 AMS_ASSETS_SAMPLING_TASK 状态变更SQL
	 * @return SQLModel 资产抽查任务表 AMS_ASSETS_SAMPLING_TASK 任务取消SQL
	 */
	public SQLModel getTaskOpenModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO) dtoParameter;
		String sqlStr = "UPDATE AMS_ASSETS_SAMPLING_TASK"
						+ " SET"
						+ " TASK_STATUS = ?,"
						+ " OPENED_DATE = GETDATE(),"
						+ " OPENED_BY = ?,"
						+ " CLOSED_DATE = NULL,"
						+ " CLOSED_BY = NULL,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " TASK_ID = ?"
						+ " AND TASK_STATUS = ?"
						+ " AND CREATED_OU = ?";

		sqlArgs.add(SamplingDicts.TSK_STATUS1_OPENING);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getTaskId());
		sqlArgs.add(SamplingDicts.TSK_STATUS1_CLOSED);
		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产抽查任务表 AMS_ASSETS_SAMPLING_TASK数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " AAST.TASK_ID,"
						+ " AAST.TASK_NO,"
						+ " AAST.TASK_NAME,"
						+ " AAST.TASK_DESC,"
						+ " AAST.START_DATE,"
						+ " AAST.END_DATE,"
						+ " AAST.SAMPLING_TYPE,"
						+ " dbo.APP_GET_FLEX_VALUE(AAST.SAMPLING_TYPE, 'SAMPLING_TYPE') SAMPLING_TYPE_VALUE,"
						+ " AAST.SAMPLING_RATIO,"
						+ " AAST.CREATED_OU,"
						+ " EOCM.COMPANY CREATED_OU_NAME,"
						+ " AAST.CREATION_DATE,"
						+ " SU1.USERNAME CREATED_USER,"
						+ " AAST.LAST_UPDATE_DATE,"
						+ " AAST.LAST_UPDATE_BY,"
						+ " AAST.TASK_STATUS,"
						+ " dbo.APP_GET_FLEX_VALUE(AAST.TASK_STATUS, 'TASK_STATUS') TASK_STATUS_VALUE,"
						+ " AAST.CLOSED_DATE,"
						+ " AAST.CLOSED_BY,"
						+ " SU3.USERNAME CLOSED_USER,"
						+ " AAST.OPENED_DATE,"
						+ " AAST.OPENED_BY,"
						+ " SU2.USERNAME OPENED_USER,"
						+ " AAST.CANCELED_DATE,"
						+ " AAST.CANCELED_BY,"
						+ " SU4.USERNAME CANCELED_USER"
						+ " FROM"
						+ " ETS_OU_CITY_MAP          EOCM,"
						+ " SF_USER                  SU1,"
						+ " SF_USER                  SU2,"
						+ " SF_USER                  SU3,"
						+ " SF_USER                  SU4,"
						+ " AMS_ASSETS_SAMPLING_TASK AAST"
						+ " WHERE"
						+ " AAST.CREATED_OU = EOCM.ORGANIZATION_ID"
						+ " AND AAST.CREATED_BY = SU1.USER_ID"
						+ " AND AAST.OPENED_BY *= SU2.USER_ID"
						+ " AND AAST.CLOSED_BY *= SU3.USER_ID"
						+ " AND AAST.CANCELED_BY *= SU4.USER_ID"
						+ " AND AAST.TASK_ID = ?";
		sqlArgs.add(dto.getTaskId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产抽查任务表 AMS_ASSETS_SAMPLING_TASK页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " AAST.TASK_ID,"
							+ " AAST.TASK_NO,"
							+ " AAST.TASK_NAME,"
							+ " AAST.START_DATE,"
							+ " AAST.END_DATE,"
							+ " AAST.SAMPLING_TYPE,"
							+ " dbo.APP_GET_FLEX_VALUE(AAST.SAMPLING_TYPE, 'SAMPLING_TYPE') SAMPLING_TYPE_VALUE,"
							+ " AAST.SAMPLING_RATIO,"
							+ " AAST.CREATED_OU,"
							+ " EOCM.COMPANY CREATED_OU_NAME,"
							+ " AAST.CREATION_DATE,"
							+ " SU1.USERNAME CREATED_USER,"
							+ " AAST.LAST_UPDATE_DATE,"
							+ " AAST.LAST_UPDATE_BY,"
							+ " AAST.TASK_STATUS,"
							+ " dbo.APP_GET_FLEX_VALUE(AAST.TASK_STATUS, 'TASK_STATUS') TASK_STATUS_VALUE,"
							+ " AAST.CLOSED_DATE,"
							+ " AAST.CLOSED_BY,"
							+ " SU3.USERNAME CLOSED_USER,"
							+ " AAST.OPENED_DATE,"
							+ " AAST.OPENED_BY,"
							+ " SU2.USERNAME OPENED_USER,"
							+ " AAST.CANCELED_DATE,"
							+ " AAST.CANCELED_BY,"
							+ " SU4.USERNAME CANCELED_USER"
							+ " FROM"
							+ " ETS_OU_CITY_MAP          EOCM,"
							+ " SF_USER                  SU1,"
							+ " SF_USER                  SU2,"
							+ " SF_USER                  SU3,"
							+ " SF_USER                  SU4,"
							+ " AMS_ASSETS_SAMPLING_TASK AAST"
							+ " WHERE"
							+ " AAST.CREATED_OU = EOCM.ORGANIZATION_ID"
							+ " AND AAST.CREATED_BY = SU1.USER_ID"
							+ " AND AAST.OPENED_BY *= SU2.USER_ID"
							+ " AND AAST.CLOSED_BY *= SU3.USER_ID"
							+ " AND AAST.CANCELED_BY *= SU4.USER_ID"
							+ " AND ( " + SyBaseSQLUtil.isNull() + " OR AAST.SAMPLING_TYPE = ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + " OR AAST.TASK_STATUS = ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + " OR AAST.TASK_NO LIKE ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + " OR AAST.TASK_NAME LIKE ?)"
                            + " AND ( " + SyBaseSQLUtil.isNull() + " OR AAST.CREATION_DATE >= ?)"
                            + " AND ( " + SyBaseSQLUtil.isNull() + " OR AAST.CREATION_DATE <= ?)"
                            + " AND ( " + SyBaseSQLUtil.isNull() + " OR AAST.END_DATE >= ?)"
                            + " AND ( " + SyBaseSQLUtil.isNull() + " OR AAST.END_DATE <= ?)";
			sqlArgs.add(dto.getSamplingType());
			sqlArgs.add(dto.getSamplingType());
			sqlArgs.add(dto.getTaskStatus());
			sqlArgs.add(dto.getTaskStatus());
			sqlArgs.add(dto.getTaskNo());
			sqlArgs.add(dto.getTaskNo());
			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getFromDate());
			sqlArgs.add(dto.getFromDate());
			sqlArgs.add(dto.getToDate());
			sqlArgs.add(dto.getToDate());
			if(!userAccount.isProvinceUser()){
				sqlStr += " AND (AAST.CREATED_OU = ?"
					+ " OR EXISTS("
					+ " SELECT"
					+ " NULL"
					+ " FROM"
					+ " AMS_SAMPLING_TASK_ASSIGN ASTA"
					+ " WHERE"
					+ " AAST.TASK_ID = ASTA.TASK_ID"
					+ " AND (AAST.TASK_STATUS = ?"
					+ " OR AAST.TASK_STATUS = ?)"
					+ " AND ASTA.ORGANIZATION_ID = ?))";
				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(SamplingDicts.TSK_STATUS1_OPENING);
				sqlArgs.add(SamplingDicts.TSK_STATUS1_CLOSED);
				sqlArgs.add(userAccount.getOrganizationId());
			} else {
				if(dto.getSampledOu() != -1){
					sqlStr += " AND EXISTS("
						+ " SELECT"
						+ " 1"
						+ " FROM"
						+ " AMS_SAMPLING_TASK_ASSIGN ASTA"
						+ " WHERE"
						+ " AAST.TASK_ID = ASTA.TASK_ID"
						+ " AND ASTA.ORGANIZATION_ID = ?)";
					sqlArgs.add(dto.getSampledOu());
				}
			}
            sqlStr += " ORDER BY AAST.CREATION_DATE DESC";
            sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}


	/**
	 * 功能：构造批量取消任务的SQL
	 * @param taskIdArr String[]
	 * @return SQLModel
	 */
	public SQLModel getMultipleCancelModel(String[] taskIdArr) {
		SQLModel sqlModel = new SQLModel();
		String taskIds = ArrUtil.arrToSqlStr(taskIdArr);
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE AMS_ASSETS_SAMPLING_TASK"
						+ " SET"
						+ " CANCELED_DATE = GETDATE(),"
						+ " CANCELED_BY = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?,"
						+ " TASK_STATUS = ?"
						+ " WHERE"
						+ " TASK_ID IN (" + taskIds + ")"
						+ " AND TASK_STATUS = ?"
						+ " AND CREATED_OU = ?";
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(SamplingDicts.TSK_STATUS1_CANCELED);
		sqlArgs.add(SamplingDicts.TSK_STATUS1_SAVE_TEMP);
		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：构造批量取消任务的SQL
	 * @param taskIdArr String[]
	 * @return SQLModel
	 */
	public SQLModel getMultiplePublishModel(String[] taskIdArr) {
		SQLModel sqlModel = new SQLModel();
		String taskIds = ArrUtil.arrToSqlStr(taskIdArr);
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE AMS_ASSETS_SAMPLING_TASK"
						+ " SET"
						+ " OPENED_DATE = GETDATE(),"
						+ " OPENED_BY = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?,"
						+ " TASK_STATUS = ?"
						+ " WHERE"
						+ " TASK_ID IN (" + taskIds + ")"
						+ " AND TASK_STATUS = ?"
						+ " AND CREATED_OU = ?";
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(SamplingDicts.TSK_STATUS1_OPENING);
		sqlArgs.add(SamplingDicts.TSK_STATUS1_SAVE_TEMP);
		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setArgs(sqlArgs);
		sqlModel.setSqlStr(sqlStr);


		return sqlModel;
	}

	/**
	 * 功能：构造批量关闭任务的SQL
	 * @param taskIdArr String[]
	 * @return SQLModel
	 */
	public SQLModel getMultipleCloseModel(String[] taskIdArr) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String taskIds = ArrUtil.arrToSqlStr(taskIdArr);
		String sqlStr = "UPDATE AMS_ASSETS_SAMPLING_TASK"
						+ " SET"
						+ " TASK_STATUS = ?,"
						+ " OPENED_DATE = NULL,"
						+ " OPENED_BY = NULL,"
						+ " CLOSED_DATE = GETDATE(),"
						+ " CLOSED_BY = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " TASK_ID IN (" + taskIds + ")"
						+ " AND TASK_STATUS = ?";
//						+ " AND CREATED_OU = ?";
		sqlArgs.add(SamplingDicts.TSK_STATUS1_CLOSED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(SamplingDicts.TSK_STATUS1_OPENING);
//		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造批量打开任务的SQL
	 * @param taskIdArr String[]
	 * @return SQLModel
	 */
	public SQLModel getMultipleOpenModel(String[] taskIdArr) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String taskIds = ArrUtil.arrToSqlStr(taskIdArr);
		String sqlStr = "UPDATE AMS_ASSETS_SAMPLING_TASK"
						+ " SET"
						+ " TASK_STATUS = ?,"
						+ " OPENED_DATE = GETDATE(),"
						+ " OPENED_BY = ?,"
						+ " CLOSED_DATE = NULL,"
						+ " CLOSED_BY = NULL,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?"
						+ " WHERE"
						+ " TASK_ID IN (" + taskIds + ")"
						+ " AND TASK_STATUS = ?";
//						+ " AND CREATED_OU = ?";

		sqlArgs.add(SamplingDicts.TSK_STATUS1_OPENING);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(SamplingDicts.TSK_STATUS1_CLOSED);
//		sqlArgs.add(userAccount.getOrganizationId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造根据任务号获取任务信息SQL
	 * @return SQLModel
	 */
	public SQLModel getDataByTaskNoModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " AAST.TASK_ID,"
						+ " AAST.TASK_NO,"
						+ " AAST.TASK_NAME,"
						+ " AAST.TASK_DESC,"
						+ " AAST.START_DATE,"
						+ " AAST.END_DATE,"
						+ " AAST.SAMPLING_TYPE,"
						+ " dbo.APP_GET_FLEX_VALUE(AAST.SAMPLING_TYPE, 'SAMPLING_TYPE') SAMPLING_TYPE_VALUE,"
						+ " AAST.SAMPLING_RATIO,"
						+ " AAST.CREATED_OU,"
						+ " EOCM.COMPANY CREATED_OU_NAME,"
						+ " AAST.CREATION_DATE,"
						+ " SU1.USERNAME CREATED_USER,"
						+ " AAST.LAST_UPDATE_DATE,"
						+ " AAST.LAST_UPDATE_BY,"
						+ " AAST.TASK_STATUS,"
						+ " dbo.APP_GET_FLEX_VALUE(AAST.TASK_STATUS, 'TASK_STATUS') TASK_STATUS_VALUE,"
						+ " AAST.CLOSED_DATE,"
						+ " AAST.CLOSED_BY,"
						+ " SU3.USERNAME CLOSED_USER,"
						+ " AAST.OPENED_DATE,"
						+ " AAST.OPENED_BY,"
						+ " SU2.USERNAME OPENED_USER,"
						+ " AAST.CANCELED_DATE,"
						+ " AAST.CANCELED_BY,"
						+ " SU4.USERNAME CANCELED_USER"
						+ " FROM"
						+ " ETS_OU_CITY_MAP          EOCM,"
						+ " SF_USER                  SU1,"
						+ " SF_USER                  SU2,"
						+ " SF_USER                  SU3,"
						+ " SF_USER                  SU4,"
						+ " AMS_ASSETS_SAMPLING_TASK AAST"
						+ " WHERE"
						+ " AAST.CREATED_OU = EOCM.ORGANIZATION_ID"
						+ " AND AAST.CREATED_BY = SU1.USER_ID"
						+ " AND AAST.OPENED_BY *= SU2.USER_ID"
						+ " AND AAST.CLOSED_BY *= SU3.USER_ID"
						+ " AND AAST.CANCELED_BY *= SU4.USER_ID"
						+ " AND AAST.TASK_NO = ?";
		sqlArgs.add(dto.getTaskNo());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
	public SQLModel getDataByTaskNmaeModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingTaskDTO dto = (AmsAssetsSamplingTaskDTO) dtoParameter;
		String sqlStr = 
			"SELECT AAST.TASK_NAME" + 
			"  FROM AMS_ASSETS_SAMPLING_TASK AAST" + 
			" WHERE AAST.TASK_NAME = ?";
		sqlArgs.add(dto.getTaskName());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
	
}
