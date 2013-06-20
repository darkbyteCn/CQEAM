package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.newasset.dto.AmsAssetsCheckHeaderDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.framework.dto.BaseUserDTO;
import com.sino.framework.security.dto.ServletConfigDTO;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: 中国移动资产实物管理系统</p>
 * <p>Copyright: 北京思诺博信息技术有限公司版权所有Copyright (c) 2007</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author 唐明胜
 * @version 1.0
 */

public class ChkOrderPDACreateModel extends AMSSQLProducer {
	private ServletConfigDTO servletConfig = null;

	public ChkOrderPDACreateModel(BaseUserDTO userAccount, AmsAssetsCheckHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}


	/**
	 * 功能：设置servletConfig
	 * @param servletConfig ServletConfigDTO
	 */
	public void setServletConfig(ServletConfigDTO servletConfig) {
		this.servletConfig = servletConfig;
	}


	/**
	 * 功能：创建工单任务批
	 * @param dto 工单任务批批数据
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getOrderBatchCreateModel(AmsAssetsCheckBatchDTO dto) throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			String sqlStr = "INSERT INTO "
							+ " AMS_ASSETS_CHECK_BATCH("
							+ " BATCH_ID,"
							+ " BATCH_NO,"
							+ " CHECK_DEPT,"
							+ " TASK_DESC,"
							+ " ORGANIZATION_ID,"
							+ " BATCH_IMPLEMENT_BY,"
							+ " BATCH_START_TIME,"
							+ " BATCH_IMPLEMENT_DAYS,"
							+ " BATCH_CHECK_LOCATION,"
							+ " BATCH_STATUS,"
							+ " GROUP_ID,"
							+ " DISTRIBUTE_DATE,"
							+ " DISTRIBUTE_BY,"
							+ " CREATION_DATE,"
							+ " CREATED_BY,"
							+ " ORDER_TYPE,"
							+ " COST_CENTER_CODE"
							+ ") VALUES ("
							+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			sqlArgs.add(dto.getBatchId());
			sqlArgs.add(dto.getBatchNo());
			sqlArgs.add(dto.getCheckDept());
			sqlArgs.add(dto.getTaskDesc());
			sqlArgs.add(userAccount.getOrganizationId());
			sqlArgs.add(dto.getBatchImplementBy());
			sqlArgs.add(dto.getBatchStartTime());
			sqlArgs.add(dto.getBatchImplementDays());
			sqlArgs.add(dto.getBatchCheckLocation());
			sqlArgs.add(dto.getBatchStatus());
			sqlArgs.add(dto.getGroupId());
			sqlArgs.add(dto.getDistributeDate());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getCreationDate());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getOrderType());
			sqlArgs.add(dto.getCostCenterCode());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：创建工单头
	 * @return SQLModel
	 * @throws SQLModelException
	 */
	public SQLModel getOrderHeaderCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
							+ " AMS_ASSETS_CHECK_HEADER("
							+ " HEADER_ID,"
							+ " BATCH_ID,"
							+ " CHECK_LOCATION,"
							+ " TRANS_NO,"
							+ " IMPLEMENT_BY,"
							+ " START_TIME,"
							+ " IMPLEMENT_DAYS,"
							+ " ORGANIZATION_ID,"
							+ " ORDER_STATUS,"
							+ " DISTRIBUTE_DATE,"
							+ " DISTRIBUTE_BY,"
							+ " CREATED_BY,"
							+ " ORDER_TYPE,"
							+ " CHECK_CATEGORY,"
							+ " ARCHIVED_BY"
							+ ") VALUES ("
							+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			sqlArgs.add(dto.getHeaderId());
			sqlArgs.add(dto.getBatchId());
			sqlArgs.add(dto.getCheckLocation());
			sqlArgs.add(dto.getTransNo());
			sqlArgs.add(dto.getImplementBy());
			sqlArgs.add(dto.getStartTime());
			sqlArgs.add(dto.getImplementDays());
			sqlArgs.add(dto.getOrganizationId());
			sqlArgs.add(dto.getOrderStatus());
			sqlArgs.add(dto.getDistributeDate());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getOrderType());
			sqlArgs.add(dto.getCheckCategory());
			sqlArgs.add(dto.getArchivedBy());
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：创建工单行
	 * @return SQLModel
	 */
	public SQLModel getOrderLineCreateModel() {
		AmsAssetsCheckHeaderModel modelProducer = null;
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO)dtoParameter;
		modelProducer = new AmsAssetsCheckHeaderModel(userAccount, dto);
		modelProducer.setServletConfig(servletConfig);
		return modelProducer.getLocAssetsSaveModel();
	}

	/**
	 * 功能：获取判断所在地点是否有早期未归档工单的SQL
	 * @return SQLModel
	 */
	public SQLModel getHasPreviousOrderModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsCheckHeaderDTO dto = (AmsAssetsCheckHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " 1"
						+ " FROM"
						+ " AMS_ASSETS_CHECK_HEADER AACH,"
						+ " AMS_ASSETS_CHECK_BATCH  AACB"
						+ " WHERE"
						+ " AACH.BATCH_ID = AACB.BATCH_ID"
						+ " AND AACH.CHECK_LOCATION = ?"
						+ " AND AACH.ORDER_STATUS <> ?"
						+ " AND AACH.ORDER_STATUS <> ?"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACH.CHECK_CATEGORY = ?)"
						+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AACB.COST_CENTER_CODE = ?)";
		List sqlArgs = new ArrayList();
		sqlArgs.add(dto.getCheckLocation());
		sqlArgs.add(AssetsDictConstant.CHK_STATUS_CANCELED);
		sqlArgs.add(AssetsDictConstant.CHK_STATUS_ARCHIEVED);
		sqlArgs.add(dto.getCheckCategory());
		sqlArgs.add(dto.getCheckCategory());
		sqlArgs.add(dto.getCostCenterCode());
		sqlArgs.add(dto.getCostCenterCode());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
