package com.sino.ams.dzyh.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.dzyh.constant.DzyhActionConstant;
import com.sino.ams.dzyh.constant.LvecDicts;
import com.sino.ams.dzyh.dto.EamDhCheckBatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.file.FileUtil;
import com.sino.base.util.ArrUtil;


/**
 * <p>Title: EamDhCheckBatchModel</p>
 * <p>Description:程序自动生成SQL构造器“EamDhCheckBatchModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */

public class EamDhCheckBatchModel extends AMSSQLProducer {

/**
	 * 功能：低值易耗盘点工单批表 EAM_DH_CHECK_BATCH 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter EamDhCheckBatchDTO 本次操作的数据
	 */
	public EamDhCheckBatchModel(SfUserDTO userAccount, EamDhCheckBatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成低值易耗盘点工单批表 EAM_DH_CHECK_BATCH数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhCheckBatchDTO dto = (EamDhCheckBatchDTO) dtoParameter;
			String act = dto.getAct();
			String sqlStr = "INSERT INTO"
							+ " EAM_DH_CHECK_BATCH("
							+ " BATCH_ID,"
							+ " BATCH_NO,"
							+ " CHECK_TASK_ID,"
							+ " CHECK_DEPT,"
							+ " ORGANIZATION_ID,"
							+ " BATCH_STATUS,"
							+ " DISTRIBUTE_DATE,"
							+ " DISTRIBUTE_BY,"
							+ " CREATION_DATE,"
							+ " CREATED_BY"
							+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";

			if (act.equals(DzyhActionConstant.SAVE_ACTION)) {
				dto.setDistributeBy( -1 );
				dto.setDistributeDate("");
				dto.setBatchStatus(LvecDicts.ORDER_STS1_SAVE_TEMP);
			} else if (act.equals(DzyhActionConstant.DISTRI_ORDER)) {
				dto.setDistributeBy(userAccount.getUserId());
				dto.setCurrCalendar("distributeDate");
				dto.setBatchStatus(LvecDicts.ORDER_STS1_DISTRIBUTED);
			}

			sqlArgs.add(dto.getBatchId());
			sqlArgs.add(dto.getBatchNo());
			sqlArgs.add(dto.getCheckTaskId());
			sqlArgs.add(dto.getCheckDept());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(dto.getBatchStatus());
			sqlArgs.add(dto.getDistributeDate());
			sqlArgs.add(dto.getDistributeBy());
			sqlArgs.add(userAccount.getUserId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成低值易耗盘点工单批表 EAM_DH_CHECK_BATCH数据更新SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhCheckBatchDTO dto = (EamDhCheckBatchDTO) dtoParameter;
			String act = dto.getAct();
			if (act.equals(DzyhActionConstant.SAVE_ACTION)) {
				dto.setDistributeBy( -1 );
				dto.setDistributeDate("");
				dto.setBatchStatus(LvecDicts.ORDER_STS1_SAVE_TEMP);
			} else if (act.equals(DzyhActionConstant.DISTRI_ORDER)) {
				dto.setDistributeBy(userAccount.getUserId());
				dto.setCurrCalendar("distributeDate");
				dto.setBatchStatus(LvecDicts.ORDER_STS1_DISTRIBUTED);
			}
			String sqlStr = "UPDATE EAM_DH_CHECK_BATCH"
							+ " SET"
							+ " CHECK_TASK_ID = ?,"
							+ " CHECK_DEPT = ?,"
							+ " BATCH_STATUS = ?,"
							+ " DISTRIBUTE_DATE = ?,"
							+ " DISTRIBUTE_BY = ?,"
							+ " LAST_UPDATE_DATE = GETDATE(),"
							+ " LAST_UPDATE_BY = ?"
							+ " WHERE"
							+ " BATCH_ID = ?"
							+ " AND BATCH_STATUS = ?";

			sqlArgs.add(dto.getCheckTaskId());
			sqlArgs.add(dto.getCheckDept());
			sqlArgs.add(dto.getBatchStatus());
			sqlArgs.add(dto.getDistributeDate());
			sqlArgs.add(dto.getDistributeBy());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getBatchId());
			sqlArgs.add(LvecDicts.ORDER_STS1_SAVE_TEMP);

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成低值易耗盘点工单批表 EAM_DH_CHECK_BATCH数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		EamDhCheckBatchDTO dto = (EamDhCheckBatchDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " EDCB.BATCH_ID,"
						+ " EDCB.BATCH_NO,"
						+ " EDCB.CHECK_TASK_ID,"
						+ " EDCB.CHECK_DEPT,"
						+ " EDCB.ORGANIZATION_ID,"
						+ " AMS_PUB_PKG.GET_ORGNIZATION_NAME(EDCB.ORGANIZATION_ID) ORGANIZATION_NAME,"
						+ " EDCB.BATCH_STATUS,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(EDCB.BATCH_STATUS, 'CHKORDER_STATUS') BATCH_STATUS_VALUE,"
						+ " EDCB.DISTRIBUTE_DATE,"
						+ " EDCB.DISTRIBUTE_BY,"
						+ " AMS_PUB_PKG.GET_USER_NAME(EDCB.DISTRIBUTE_BY) DISTRIBUTE_USER,"
						+ " EDCB.CREATION_DATE,"
						+ " EDCB.CREATED_BY,"
						+ " AMS_PUB_PKG.GET_USER_NAME(EDCB.CREATED_BY) CREATED_USER,"
						+ " EDCB.LAST_UPDATE_DATE,"
						+ " EDCB.LAST_UPDATE_BY,"
						+ " AMS_PUB_PKG.GET_USER_NAME(EDCB.LAST_UPDATE_BY) UPDATE_USER,"
						+ " AMD.DEPT_NAME CHECK_DEPT_NAME,"
						+ " ECT.TASK_NAME,"
						+ " ECT.START_DATE,"
						+ " ECT.END_DATE,"
						+ " ECT.CHECK_TYPE,"
						+ " AMS_PUB_PKG.GET_FLEX_VALUE(ECT.CHECK_TYPE, 'CHECK_TYPE') CHECK_TYPE_VALUE,"
						+" CASE WHEN ECT.CHECK_TYPE='DZYH' THEN 99 ELSE 98 END OBJECT_CATEGORY"
						+ " FROM"
						+ " AMS_MIS_DEPT       AMD,"
						+ " EAM_CHECK_TASK     ECT,"
						+ " EAM_DH_CHECK_BATCH EDCB"
						+ " WHERE"
						+ " EDCB.CHECK_TASK_ID = ECT.CHECK_TASK_ID"
						+ " AND EDCB.CHECK_DEPT *= AMD.DEPT_CODE"
						+ " AND EDCB.BATCH_ID = ?";
		sqlArgs.add(dto.getBatchId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			EamDhCheckBatchDTO dto = (EamDhCheckBatchDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " EDCB.BATCH_ID,"
							+ " EDCB.BATCH_NO,"
							+ " EDCB.CREATION_DATE,"
							+ " EDCB.CHECK_DEPT,"
							+ " AMD.DEPT_NAME CHEC_DEPT_NAME,"
							+ " EDCB.BATCH_STATUS,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(EDCB.BATCH_STATUS, 'CHKORDER_STATUS') BATCH_STATUS_VALUE,"
							+ " AMS_PUB_PKG.GET_ORGNIZATION_NAME(EDCB.ORGANIZATION_ID) ORGNIZATION_NAME,"
							+ " ECT.TASK_NAME,"
							+ " ECT.START_DATE,"
							+ " ECT.END_DATE,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ECT.CHECK_TYPE, 'CHECK_TYPE') CHECK_TYPE_VALUE,"
							+ " ECT.TASK_STATUS,"
							+ " AMS_PUB_PKG.GET_FLEX_VALUE(ECT.TASK_STATUS, 'TASK_STATUS') TASK_STATUS_VALUE"
							+ " FROM"
							+ " EAM_CHECK_TASK     ECT,"
							+ " EAM_DH_CHECK_BATCH EDCB,"
							+ " AMS_MIS_DEPT       AMD"
							+ " WHERE"
							+ " ECT.CHECK_TASK_ID = EDCB.CHECK_TASK_ID"
							+ " AND EDCB.CHECK_DEPT *= AMD.DEPT_CODE"
							+ " AND ECT.CHECK_TYPE = dbo.NVL(?, ECT.CHECK_TYPE)"
							+ " AND EDCB.BATCH_NO LIKE dbo.NVL(?, EDCB.BATCH_NO)"
							+ " AND EDCB.BATCH_STATUS = dbo.NVL(?, EDCB.BATCH_STATUS)"
							+ " AND ECT.TASK_NAME LIKE dbo.NVL(?, ECT.TASK_NAME)"
							+ " AND ECT.TASK_STATUS = dbo.NVL(?, ECT.TASK_STATUS)"
							+ " AND ECT.START_DATE >= ISNULL(?, ECT.START_DATE)"
							+ " AND ECT.START_DATE <= ISNULL(?, ECT.START_DATE)"
							+ " AND ECT.END_DATE >= ISNULL(?, ECT.END_DATE)"
							+ " AND ECT.END_DATE <= ISNULL(?, ECT.END_DATE)"
							+ " AND EDCB.ORGANIZATION_ID = ?";

			sqlArgs.add(dto.getCheckType());
			sqlArgs.add(dto.getBatchNo());
			sqlArgs.add(dto.getBatchStatus());
			sqlArgs.add(dto.getTaskName());
			sqlArgs.add(dto.getTaskStatus());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getSQLEndDate());
			sqlArgs.add(dto.getFromDate());
			sqlArgs.add(dto.getSQLToDate());
			sqlArgs.add(userAccount.getOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);

			try {
				FileUtil.saveStrContent(sqlModel.toString(), "C:\\batch.sql");
			} catch (Exception ex1) {
			}
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：构造批量撤销工单批SQL
	 * @param batchIdArr String[]
	 * @return SQLModel
	 */
	public SQLModel getMulBatchCancelModel(String[] batchIdArr){
		SQLModel sqlModel = new SQLModel();
		String batchIds = ArrUtil.arrToSqlStr(batchIdArr);
		List sqlArgs = new ArrayList();
		String sqlStr = "UPDATE"
						+ " EAM_DH_CHECK_BATCH EDCB"
						+ " SET"
						+ " EDCB.BATCH_STATUS     = ?,"
						+ " EDCB.LAST_UPDATE_DATE = GETDATE(),"
						+ " EDCB.LAST_UPDATE_BY   = ?"
						+ " WHERE"
						+ " EDCB.BATCH_ID IN (" + batchIds + ")"
						+ " AND EDCB.BATCH_STATUS = ?";

		sqlArgs.add(LvecDicts.ORDER_STS1_CANCELED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(LvecDicts.ORDER_STS1_SAVE_TEMP);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
