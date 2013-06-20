package com.sino.ams.sampling.model;


import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.bean.SyBaseSQLUtil;
import com.sino.ams.sampling.constant.SamplingDicts;
import com.sino.ams.sampling.dto.AmsAssetsSamplingHeaderDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.base.util.ArrUtil;


/**
 * <p>Title: AmsAssetsSamplingHeaderModel</p>
 * <p>Description:程序自动生成SQL构造器“AmsAssetsSamplingHeaderModel”，请根据需要自行修改</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: 北京思诺博信息技术有限公司</p>
 * @author mshtang
 * @version 1.0
 */


public class AmsAssetsSamplingHeaderModel extends AMSSQLProducer {

	/**
	 * 功能：抽查工单表 AMS_ASSETS_SAMPLING_HEADER 数据库SQL构造层构造函数
	 * @param userAccount SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter AmsAssetsSamplingHeaderDTO 本次操作的数据
	 */
	public AmsAssetsSamplingHeaderModel(SfUserDTO userAccount, AmsAssetsSamplingHeaderDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成抽查工单表 AMS_ASSETS_SAMPLING_HEADER数据插入SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
			String sqlStr = "INSERT INTO "
							+ " AMS_ASSETS_SAMPLING_HEADER("
							+ " HEADER_ID,"
							+ " BATCH_ID,"
							+ " TASK_ID,"
							+ " SAMPLING_LOCATION,"
							+ " ORDER_NO,"
							+ " IMPLEMENT_BY,"
							+ " START_TIME,"
							+ " IMPLEMENT_DAYS,"
							+ " DISTRIBUTE_DATE,"
							+ " DISTRIBUTE_BY,"
							+ " ORGANIZATION_ID,"
							+ " ORDER_STATUS,"
							+ " CREATION_DATE,"
							+ " CREATED_BY,"
							+ " CREATED_OU"
							+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?)";

			sqlArgs.add(dto.getHeaderId());
			sqlArgs.add(dto.getBatchId());
			sqlArgs.add(dto.getTaskId());
			sqlArgs.add(dto.getSamplingLocation());
			sqlArgs.add(dto.getOrderNo());
			sqlArgs.add(dto.getImplementBy());
			sqlArgs.add(dto.getStartTime());
			sqlArgs.add(dto.getImplementDays());
			sqlArgs.add(dto.getDistributeDate());
			sqlArgs.add(dto.getDistributeBy());
			sqlArgs.add(dto.getSampledOu());
			sqlArgs.add(dto.getOrderStatus());
			sqlArgs.add(userAccount.getUserId());
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
	 * 功能：构造工单更新SQL
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
			String sqlStr = "UPDATE"
							+ " AMS_ASSETS_SAMPLING_HEADER"
							+ " SET"
							+ " ORDER_STATUS = ?,"
							+ " SAMPLING_LOCATION = ?,"
							+ " IMPLEMENT_BY      = ?,"
							+ " START_TIME        = ?,"
							+ " IMPLEMENT_DAYS    = ?,"
							+ " DISTRIBUTE_DATE   = ?,"
							+ " DISTRIBUTE_BY     = ?,"
							+ " LAST_UPDATE_DATE  = GETDATE(),"
							+ " LAST_UPDATE_BY    = ?"
							+ " WHERE"
							+ " HEADER_ID = ?"
							+ " AND ORDER_STATUS = ?";

			sqlArgs.add(dto.getOrderStatus());
			sqlArgs.add(dto.getSamplingLocation());
			sqlArgs.add(dto.getImplementBy());
			sqlArgs.add(dto.getStartTime());
			sqlArgs.add(dto.getImplementDays());
			sqlArgs.add(dto.getDistributeDate());
			sqlArgs.add(dto.getDistributeBy());
			sqlArgs.add(userAccount.getUserId());
			sqlArgs.add(dto.getHeaderId());
			sqlArgs.add(SamplingDicts.ORDER_STS1_SAVE_TEMP);

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}


	/**
	 * 功能：框架自动生成抽查工单表 AMS_ASSETS_SAMPLING_HEADER数据删除SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	public SQLModel getDataDeleteModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
		String sqlStr = "DELETE FROM"
						+ " AMS_ASSETS_SAMPLING_HEADER"
						+ " WHERE"
						+ " HEADER_ID = ?";
		sqlArgs.add(dto.getHeaderId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成抽查工单表 AMS_ASSETS_SAMPLING_HEADER数据详细信息查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " AAST.TASK_NO, \n"
						+ " AAST.TASK_NAME, \n"
						+ " AAST.TASK_DESC, \n"
						//+ " STR_REPLACE(CONVERT(VARCHAR, (CASE AAST.START_DATE WHEN NULL THEN '' ELSE AAST.START_DATE END), 111), '/', '-') START_DATE, \n"
						//+ " STR_REPLACE(CONVERT(VARCHAR, (CASE AAST.END_DATE WHEN NULL THEN '' ELSE AAST.END_DATE END), 111), '/', '-') END_DATE, \n"
						+ " STR_REPLACE(CONVERT(VARCHAR, (CASE CHARINDEX('1900', AAST.START_DATE) WHEN 8 THEN NULL ELSE AAST.START_DATE END), 111), '/', '-') START_DATE, \n"
						+ " STR_REPLACE(CONVERT(VARCHAR, (CASE CHARINDEX('1900', AAST.END_DATE) WHEN 8 THEN NULL ELSE AAST.END_DATE END), 111), '/', '-') END_DATE, \n"
						+ " AASB.BATCH_NO, \n"
						+ " AASB.BATCH_REMARK, \n"
						+ " AASH.HEADER_ID, \n"
						+ " AASH.ORDER_NO, \n"
						+ " EO.WORKORDER_OBJECT_CODE SAMPLING_LOCATION_CODE, \n"
						+ " EO.WORKORDER_OBJECT_NAME SAMPLING_LOCATION_NAME, \n"
						//+ " STR_REPLACE(CONVERT(VARCHAR, (CASE AASH.START_TIME WHEN NULL THEN '' ELSE AASH.START_TIME END), 111), '/', '-') START_TIME, \n"
						+ " STR_REPLACE(CONVERT(VARCHAR, (CASE CHARINDEX('1900', AASH.START_TIME) WHEN 8 THEN NULL ELSE AASH.START_TIME END), 111), '/', '-') START_TIME, \n"
						+ " AASH.IMPLEMENT_DAYS, \n"
						+ " dbo.APP_GET_FLEX_VALUE(AASH.ORDER_STATUS, 'CHKORDER_STATUS') ORDER_STATUS_VALUE, \n"
						+ " dbo.APP_GET_USER_NAME(AASH.IMPLEMENT_BY) IMPLEMENT_USER, \n"
						+ " dbo.APP_GET_USER_NAME(AASH.DISTRIBUTE_BY) DISTRIBUTE_USER, \n"
						+ " dbo.APP_GET_USER_NAME(AASH.DOWNLOAD_BY) DOWNLOAD_USER, \n"
						+ " dbo.APP_GET_USER_NAME(AASH.UPLOAD_BY) UPLOAD_USER, \n"
						+ " dbo.APP_GET_USER_NAME(AASH.CREATED_BY) CREATED_USER, \n"
						+ " AASH.CREATION_DATE, \n"
						+ " AASH.DISTRIBUTE_DATE, \n"
						+ " AASH.DOWNLOAD_DATE, \n"
						+ " AASH.UPLOAD_DATE \n"
						+ " FROM \n"
						+ " ETS_OBJECT                 EO, \n"
						+ " AMS_ASSETS_SAMPLING_TASK   AAST, \n"
						+ " AMS_ASSETS_SAMPLING_BATCH  AASB, \n"
						+ " AMS_ASSETS_SAMPLING_HEADER AASH \n"
						+ " WHERE \n"
						+ " AASH.TASK_ID = AASB.TASK_ID \n"
						+ " AND AASH.BATCH_ID = AASB.BATCH_ID \n"
						+ " AND AASB.TASK_ID = AAST.TASK_ID \n"
						+ " AND AASH.SAMPLING_LOCATION *= EO.WORKORDER_OBJECT_NO \n"
						+ " AND AASH.HEADER_ID = ? \n";
		sqlArgs.add(dto.getHeaderId());

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：根据外键关联字段 taskId 构造查询数据SQL。
	 * 框架自动生成数据抽查工单表 AMS_ASSETS_SAMPLING_HEADER详细信息查询SQLModel，请根据实际需要修改。
	 * @param taskId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByTaskIdModel(String taskId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AASH.*,"
						+ " EO.WORKORDER_OBJECT_NO,"
						+ " EO.WORKORDER_OBJECT_NAME,"
						+ " SUI.USERNAME IMPLEMENT_USER,"
						+ " AAST.TASK_NO,"
						+ " AAST.TASK_NAME,"
						+ " AAST.CREATION_DATE TASK_CREATION_DATE,"
						+ " AAST.START_DATE,"
						+ " AAST.END_DATE,"
						+ " EOCM.COMPANY DISTRIBUTE_COMPANY,"
						+ " SUC.USERNAME CREATED_USER"
						+ " FROM"
						+ " ETS_OU_CITY_MAP            EOCM,"
						+ " SF_USER                    SUC,"
						+ " SF_USER                    SUI,"
						+ " ETS_OBJECT                 EO,"
						+ " AMS_ASSETS_SAMPLING_TASK   AAST,"
						+ " AMS_ASSETS_SAMPLING_HEADER AASH"
						+ " WHERE"
						+ " AASH.TASK_ID = AAST.TASK_ID"
						+ " AND AAST.CREATED_OU = EOCM.ORGANIZATION_ID"
						+ " AND AAST.CREATED_BY = SUC.USER_ID"
						+ " AND AASH.IMPLEMENT_BY = SUI.USER_ID"
						+ " AND AASH.SAMPLING_LOCATION = EO.WORKORDER_OBJECT_NO"
						+ " AND AAST.TASK_ID = ?";
		sqlArgs.add(taskId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

/**
	 * 功能：根据外键关联字段 taskId 构造查询数据SQL。
	 * 框架自动生成数据抽查工单表 AMS_ASSETS_SAMPLING_HEADER详细信息查询SQLModel，请根据实际需要修改。
	 * @param batchId String
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	private SQLModel getDataByBatchIdModel(String batchId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AASH.*,"
						+ " EO.WORKORDER_OBJECT_CODE SAMPLING_LOCATION_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME SAMPLING_LOCATION_NAME,"
						+ " SUI.USERNAME IMPLEMENT_USER,"
						+ " SUC.USERNAME CREATED_USER,"
						+ " AAST.TASK_NO,"
						+ " AAST.TASK_NAME,"
						+ " AAST.CREATION_DATE TASK_CREATION_DATE,"
						+ " AAST.START_DATE,"
						+ " AAST.END_DATE,"
						+ " EOCMC.COMPANY CREATED_OU_NAME,"
						+ " EOCMI.COMPANY IMPLEMENT_OU_NAME,"
						+ " AASB.BATCH_NO"
						+ " FROM"
						+ " ETS_OU_CITY_MAP            EOCMC,"
						+ " ETS_OU_CITY_MAP            EOCMI,"
						+ " SF_USER                    SUC,"
						+ " SF_USER                    SUI,"
						+ " ETS_OBJECT                 EO,"
						+ " AMS_ASSETS_SAMPLING_TASK   AAST,"
						+ " AMS_ASSETS_SAMPLING_BATCH  AASB,"
						+ " AMS_ASSETS_SAMPLING_HEADER AASH"
						+ " WHERE"
						+ " AAST.TASK_ID = AASB.TASK_ID"
						+ " AND AAST.TASK_ID = AASH.TASK_ID"
						+ " AND AASB.BATCH_ID = AASH.BATCH_ID"
						+ " AND AASH.SAMPLING_LOCATION *= EO.WORKORDER_OBJECT_NO"
						+ " AND AAST.CREATED_BY = SUC.USER_ID"
						+ " AND AASH.IMPLEMENT_BY *= SUI.USER_ID"
						+ " AND AAST.CREATED_OU = EOCMC.ORGANIZATION_ID"
						+ " AND AASH.ORGANIZATION_ID = EOCMI.ORGANIZATION_ID"
						+ " AND AASB.BATCH_ID = ?"
						;
		sqlArgs.add(batchId);

		sqlModel.setSqlStr(sqlStr);
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
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
		if (foreignKey.equals("taskId")) {
			sqlModel = getDataByTaskIdModel(dto.getTaskId());
		} else if (foreignKey.equals("batchId")) {
			sqlModel = getDataByBatchIdModel(dto.getBatchId());
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成抽查工单表 AMS_ASSETS_SAMPLING_HEADER页面翻页查询SQLModel，请根据实际需要修改。
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException 发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
			String sqlStr = "SELECT"
							+ " AASH.*,"
							+ " dbo.APP_GET_FLEX_VALUE(AASH.ORDER_STATUS, 'CHKORDER_STATUS') ORDER_STATUS_VALUE,"
							+ " EO.WORKORDER_OBJECT_CODE SAMPLING_LOCATION_CODE,"
							+ " EO.WORKORDER_OBJECT_NAME SAMPLING_LOCATION_NAME,"
							+ " SUI.USERNAME IMPLEMENT_USER,"
							+ " SUC.USERNAME TASK_CREATED_USER,"
							+ " AAST.TASK_NO,"
							+ " AAST.TASK_NAME,"
							+ " AAST.CREATION_DATE TASK_CREATION_DATE,"
							+ " AAST.START_DATE,"
							+ " AAST.END_DATE,"
							+ " EOCMC.COMPANY CREATED_OU_NAME,"
							+ " EOCMI.COMPANY SAMPLED_OU_NAME,"
							+ " AASB.BATCH_NO"
							+ " FROM"
							+ " ETS_OU_CITY_MAP            EOCMC,"
							+ " ETS_OU_CITY_MAP            EOCMI,"
							+ " SF_USER                    SUC,"
							+ " SF_USER                    SUI,"
							+ " ETS_OBJECT                 EO,"
							+ " AMS_ASSETS_SAMPLING_TASK   AAST,"
							+ " AMS_SAMPLING_TASK_ASSIGN   ASTA,"
							+ " AMS_ASSETS_SAMPLING_BATCH  AASB,"
							+ " AMS_ASSETS_SAMPLING_HEADER AASH"
							+ " WHERE"
							+ " AAST.CREATED_BY = SUC.USER_ID"
							+ " AND AAST.CREATED_OU = EOCMC.ORGANIZATION_ID"
							+ " AND AAST.TASK_ID = ASTA.TASK_ID"
							+ " AND ASTA.TASK_ID = AASB.TASK_ID"
							+ " AND ASTA.ORGANIZATION_ID = AASB.ORGANIZATION_ID"
							+ " AND AASB.BATCH_ID = AASH.BATCH_ID"
							+ " AND AASH.SAMPLING_LOCATION *= EO.WORKORDER_OBJECT_NO"
							+ " AND AASH.IMPLEMENT_BY *= SUI.USER_ID"
							+ " AND AASH.ORGANIZATION_ID = EOCMI.ORGANIZATION_ID"
							+ " AND ( ? = -1  OR EOCMI.ORGANIZATION_ID = ?)"//工单执行公司
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AASH.ORDER_NO LIKE ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR EO.WORKORDER_OBJECT_NAME LIKE ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR SUI.USERNAME LIKE ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAST.TASK_NO LIKE ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAST.END_DATE >= ?)"
							+ " AND ( " + SyBaseSQLUtil.isNull() + "  OR AAST.END_DATE <= ?)"
							+ " AND AASH.ORDER_STATUS = ?"
							+ " AND AASH.CREATED_OU = ?";

			if(userAccount.isProvinceUser()){
				sqlArgs.add(dto.getSampledOu());
				sqlArgs.add(dto.getSampledOu());
			} else {
				sqlArgs.add(userAccount.getOrganizationId());
				sqlArgs.add(userAccount.getOrganizationId());
			}
			sqlArgs.add(dto.getOrderNo());
			sqlArgs.add(dto.getOrderNo());
			sqlArgs.add(dto.getSamplingLocationName());
			sqlArgs.add(dto.getSamplingLocationName());
			sqlArgs.add(dto.getImplementUser());
			sqlArgs.add(dto.getImplementUser());
			sqlArgs.add(dto.getTaskNo());
			sqlArgs.add(dto.getTaskNo());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(SamplingDicts.ORDER_STS1_SAVE_TEMP);
			sqlArgs.add(userAccount.getOrganizationId());

			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);

//			try {
//				FileUtil.saveStrContent(sqlModel.toString(), "C:\\BATCH_ORDER.SQL");
//			} catch (FileException ex1) {
//			}
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：构造工单批量撤销SQL
	 * @param headerIdArr 工单ID
	 * @return SQLModel 返回工单批量撤销SQLModel
	 */
	public SQLModel getMulOrderCanceledModel(String[] headerIdArr) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String headerIds = ArrUtil.arrToSqlStr(headerIdArr);
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_SAMPLING_HEADER"
						+ " SET"
						+ " CANCELED_DATE     = GETDATE(),"
						+ " CANCELED_BY       = ?,"
						+ " ORDER_STATUS      = ?,"
						+ " LAST_UPDATE_DATE  = GETDATE(),"
						+ " LAST_UPDATE_BY    = ?"
						+ " WHERE"
						+ " HEADER_ID IN (" + headerIds + ")"
						+ " AND ORDER_STATUS = ?";

		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(SamplingDicts.ORDER_STS1_CANCELED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(SamplingDicts.ORDER_STS1_SAVE_TEMP);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造工单批量下发SQL
	 * @param headerIdArr 工单ID
	 * @return SQLModel 返回工单批量下发SQL
	 */
	public SQLModel getMulOrderDistributedModel(String[] headerIdArr) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String headerIds = ArrUtil.arrToSqlStr(headerIdArr);
		String sqlStr = "UPDATE"
						+ " AMS_ASSETS_SAMPLING_HEADER"
						+ " SET"
						+ " DISTRIBUTE_DATE   = GETDATE(),"
						+ " DISTRIBUTE_BY     = ?,"
						+ " ORDER_STATUS      = ?,"
						+ " LAST_UPDATE_DATE  = GETDATE(),"
						+ " LAST_UPDATE_BY    = ?"
						+ " WHERE"
						+ " HEADER_ID IN (" + headerIds + ")"
						+ " AND ORDER_STATUS = ?";

		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(SamplingDicts.ORDER_STS1_CANCELED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(SamplingDicts.ORDER_STS1_SAVE_TEMP);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造工单批量下发SQL
	 * @return SQLModel 返回工单批量下发SQL
	 */
	public SQLModel getTempSavedOrderModelByBatchId() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
		String sqlStr = "SELECT"
						+ " AASH.*,"
						+ " EO.WORKORDER_OBJECT_CODE SAMPLING_LOCATION_CODE,"
						+ " EO.WORKORDER_OBJECT_NAME SAMPLING_LOCATION_NAME,"
						+ " SUI.USERNAME IMPLEMENT_USER,"
						+ " SUC.USERNAME CREATED_USER,"
						+ " AAST.TASK_NO,"
						+ " AAST.TASK_NAME,"
						+ " AAST.CREATION_DATE TASK_CREATION_DATE,"
						+ " AAST.START_DATE,"
						+ " AAST.END_DATE,"
						+ " EOCMC.COMPANY CREATED_OU_NAME,"
						+ " EOCMI.COMPANY IMPLEMENT_OU_NAME,"
						+ " AASB.BATCH_NO"
						+ " FROM"
						+ " ETS_OU_CITY_MAP            EOCMC,"
						+ " ETS_OU_CITY_MAP            EOCMI,"
						+ " SF_USER                    SUC,"
						+ " SF_USER                    SUI,"
						+ " ETS_OBJECT                 EO,"
						+ " AMS_ASSETS_SAMPLING_TASK   AAST,"
						+ " AMS_ASSETS_SAMPLING_BATCH  AASB,"
						+ " AMS_ASSETS_SAMPLING_HEADER AASH"
						+ " WHERE"
						+ " AAST.TASK_ID = AASB.TASK_ID"
						+ " AND AAST.TASK_ID = AASH.TASK_ID"
						+ " AND AASB.BATCH_ID = AASH.BATCH_ID"
						+ " AND AASH.SAMPLING_LOCATION *= EO.WORKORDER_OBJECT_NO"
						+ " AND AAST.CREATED_BY = SUC.USER_ID"
						+ " AND AASH.IMPLEMENT_BY *= SUI.USER_ID"
						+ " AND AAST.CREATED_OU = EOCMC.ORGANIZATION_ID"
						+ " AND AASH.ORGANIZATION_ID = EOCMI.ORGANIZATION_ID"
						+ " AND AASH.ORDER_STATUS = ?"
						+ " AND AASB.BATCH_ID = ?";

		sqlArgs.add(SamplingDicts.ORDER_STS1_SAVE_TEMP);
		sqlArgs.add(dto.getBatchId());

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
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
		if (foreignKey.equals("batchId")) {
			sqlModel = getDeleteByBatchIdModel(dto.getBatchId());
		}
		return sqlModel;
	}

	/**
	 * 功能：根据外键关联字段 batchId 构造数据删除SQL。
	 * 框架自动生成数据抽查工单行表 AMS_ASSETS_SAMPLING_LINE数据删除SQLModel，请根据实际需要修改。
	 * @param batchId String
	 * @return SQLModel 返回数据删除用SQLModel
	 */
	private SQLModel getDeleteByBatchIdModel(String batchId) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "DELETE FROM"
						+ " AMS_ASSETS_SAMPLING_HEADER"
						+ " WHERE"
						+ " ORDER_STATUS = 'SAVE_TEMP'"
						+ " AND BATCH_ID = ?";
		sqlArgs.add(batchId);

		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}


	/**
	 * 功能：获取指定单据下的标签号
	 * @param includeAdded boolean 是否包含PDA扫描后，工单上传新加入的设备。
	 * @return SQLModel
	 */
	public SQLModel getOrderBarcodesModel(boolean includeAdded, String headerId) {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AASL.BARCODE,"
						+ " AASL.ITEM_CODE,"
						+ " AASL.RESPONSIBILITY_USER,"
						+ " AASL.RESPONSIBILITY_DEPT"
						+ " FROM"
						+ " AMS_ASSETS_SAMPLING_LINE AASL"
						+ " WHERE"
						+ " AASL.HEADER_ID = ?";
		if (!includeAdded) {
			sqlStr += " AND  " + SyBaseSQLUtil.isNotNull("AASL.BATCH_ID") + " ";
		}
//		sqlArgs.add(dto.getHeaderId());
		sqlArgs.add(headerId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造根据工单编号获取工单ID的SQL
	 * @return SQLModel
	 */
	public SQLModel getIdByOrderNoModel() {
		SQLModel sqlModel = new SQLModel();
		AmsAssetsSamplingHeaderDTO dto = (AmsAssetsSamplingHeaderDTO) dtoParameter;
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AASH.HEADER_ID"
						+ " FROM"
						+ " AMS_ASSETS_SAMPLING_HEADER AASH"
						+ " WHERE"
						+ " AASH.ORDER_NO = ?";
		sqlArgs.add(dto.getOrderNo());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
