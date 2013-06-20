package com.sino.ams.newasset.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.exception.CalendarException;
import com.sino.base.exception.SQLModelException;
import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.constant.AssetsActionConstant;
import com.sino.ams.newasset.constant.AssetsDictConstant;
import com.sino.ams.newasset.dto.AmsAssetsCheckBatchDTO;
import com.sino.ams.system.user.dto.SfUserDTO;

/**
 * <p>
 * Title: AmsAssetsCheckBatchModel
 * </p>
 * <p>
 * Description:程序自动生成SQL构造器“AmsAssetsCheckBatchModel”，请根据需要自行修改
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: 北京思诺博信息技术有限公司
 * </p>
 * 
 * @author 唐明胜
 * @version 1.0
 */

public class AmsAssetsCheckBatchModel extends AMSSQLProducer {

	/**
	 * 功能：资产盘点批表(EAM) AMS_ASSETS_CHECK_BATCH 数据库SQL构造层构造函数
	 * 
	 * @param userAccount
	 *            SfUserDTO 代表本系统的最终操作用户对象
	 * @param dtoParameter
	 *            AmsAssetsCheckBatchDTO 本次操作的数据
	 */
	public AmsAssetsCheckBatchModel(SfUserDTO userAccount,
			AmsAssetsCheckBatchDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：框架自动生成资产盘点批表(EAM) AMS_ASSETS_CHECK_BATCH数据插入SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据插入用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataCreateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
			String sqlStr = "";
			if (dto.isFlow2End()) {
				sqlStr = "INSERT INTO "
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
						+ " ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, GETDATE(), ?, GETDATE(), ?, ?, ?)";
				sqlArgs.add(dto.getBatchId());
				sqlArgs.add(dto.getBatchNo());
				sqlArgs.add(dto.getCheckDept());
				sqlArgs.add(dto.getTaskDesc());
				sqlArgs.add(dto.getOrganizationId());
				sqlArgs.add(dto.getBatchImplementBy());
				sqlArgs.add(dto.getBatchStartTime());
				sqlArgs.add(dto.getBatchImplementDays());
				sqlArgs.add(dto.getBatchCheckLocation());
				sqlArgs.add(dto.getBatchStatus());
				sqlArgs.add(dto.getGroupId());
//				sqlArgs.add(dto.getDistributeDate());
				sqlArgs.add(userAccount.getUserId());
				// sqlArgs.add(dto.getCreationDate());
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(dto.getOrderType());
				sqlArgs.add(dto.getCostCenterCode());
			} else {
				sqlStr = "INSERT INTO " + " AMS_ASSETS_CHECK_BATCH("
						+ " BATCH_ID,"
						+ " BATCH_NO,"
						+ " CHECK_DEPT,"
						+ " TASK_DESC,"
						+ " ORGANIZATION_ID,"
						+ " BATCH_IMPLEMENT_BY,"
						// + " BATCH_START_TIME,"
						+ " BATCH_IMPLEMENT_DAYS," + " BATCH_CHECK_LOCATION,"
						+ " BATCH_STATUS," + " GROUP_ID," + " CREATION_DATE,"
						+ " CREATED_BY," + " ORDER_TYPE," + " COST_CENTER_CODE"
						+ ") VALUES ("
						+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, ?, ?)";
				sqlArgs.add(dto.getBatchId());
				sqlArgs.add(dto.getBatchNo());
				sqlArgs.add(dto.getCheckDept());
				sqlArgs.add(dto.getTaskDesc());
				sqlArgs.add(dto.getOrganizationId());
				sqlArgs.add(dto.getBatchImplementBy());
				// sqlArgs.add(dto.getBatchStartTime());
				sqlArgs.add(dto.getBatchImplementDays());
				sqlArgs.add(dto.getBatchCheckLocation());
				sqlArgs.add(dto.getBatchStatus());
				sqlArgs.add(dto.getGroupId());
				// sqlArgs.add(dto.getCreationDate());
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(dto.getOrderType());
				sqlArgs.add(dto.getCostCenterCode());
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产盘点批表(EAM) AMS_ASSETS_CHECK_BATCH数据更新SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据更新用SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel getDataUpdateModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();
		try {
			List sqlArgs = new ArrayList();
			AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
			String sqlStr = "";
			if (dto.isFlow2End()) {
				sqlStr = "UPDATE AMS_ASSETS_CHECK_BATCH" + " SET"
						+ " CHECK_DEPT = ?," + " BATCH_CHECK_LOCATION = ?,"
						+ " BATCH_IMPLEMENT_BY = ?," + " BATCH_START_TIME = ?,"
						+ " BATCH_IMPLEMENT_DAYS = ?," + " TASK_DESC = ?,"
						+ " BATCH_STATUS = ?,"
						+ " DISTRIBUTE_DATE = GETDATE(),"
						+ " DISTRIBUTE_BY = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?," + " COST_CENTER_CODE = ?"
						+ " WHERE" + " BATCH_ID = ?";
				sqlArgs.add(dto.getCheckDept());
				sqlArgs.add(dto.getBatchCheckLocation());
				sqlArgs.add(dto.getBatchImplementBy());
				sqlArgs.add(dto.getBatchStartTime());
				sqlArgs.add(dto.getBatchImplementDays());
				sqlArgs.add(dto.getTaskDesc());
				sqlArgs.add(dto.getBatchStatus());
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(dto.getCostCenterCode());
				sqlArgs.add(dto.getBatchId());
			} else {
				sqlStr = "UPDATE AMS_ASSETS_CHECK_BATCH" + " SET"
						+ " CHECK_DEPT = ?," + " BATCH_CHECK_LOCATION = ?,"
						+ " BATCH_IMPLEMENT_BY = ?," + " BATCH_START_TIME = ?,"
						+ " BATCH_IMPLEMENT_DAYS = ?," + " TASK_DESC = ?,"
						+ " BATCH_STATUS = ?,"
						+ " LAST_UPDATE_DATE = GETDATE(),"
						+ " LAST_UPDATE_BY = ?," + " COST_CENTER_CODE = ?"
						+ " WHERE" + " BATCH_ID = ?";

				sqlArgs.add(dto.getCheckDept());
				sqlArgs.add(dto.getBatchCheckLocation());
				sqlArgs.add(dto.getBatchImplementBy());
				sqlArgs.add(dto.getBatchStartTime());
				sqlArgs.add(dto.getBatchImplementDays());
				sqlArgs.add(dto.getTaskDesc());
				sqlArgs.add(dto.getBatchStatus());
				sqlArgs.add(userAccount.getUserId());
				sqlArgs.add(dto.getCostCenterCode());
				sqlArgs.add(dto.getBatchId());
			}
			sqlModel.setSqlStr(sqlStr);
			sqlModel.setArgs(sqlArgs);
		} catch (CalendarException ex) {
			ex.printLog();
			throw new SQLModelException(ex);
		}
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产盘点批表(EAM) AMS_ASSETS_CHECK_BATCH数据详细信息查询SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回数据详细信息查询用SQLModel
	 */
	public SQLModel getPrimaryKeyDataModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
		String sqlStr = "SELECT"
				+ " AACB.BATCH_ID,"
				+ " AACB.BATCH_NO,"
				+ " AACB.ORGANIZATION_ID,"
				+ " AACB.BATCH_START_TIME,"
				+ " AACB.BATCH_IMPLEMENT_DAYS,"
				+ " AACB.CREATION_DATE,"
				+ " AACB.TASK_DESC,"
				+ " AACB.BATCH_STATUS,"
				+ " AACB.ORDER_TYPE,"
				+ " ACCV.COST_CENTER_CODE,"
				+ " ACCV.COST_CENTER_NAME,"
				+ " AACB.COST_CENTER_CODE,"
				+ " dbo.APP_GET_FLEX_VALUE(AACB.ORDER_TYPE, 'ORDER_TYPE_ASSETS') ORDER_TYPE_NAME,"
				+ " EFV.VALUE BATCH_STATUS_NAME," + " AACB.CHECK_DEPT,"
				+ " AMD.DEPT_NAME CHECK_DEPT_NAME,"
				+ " SU2.USERNAME CREATED_USER,"
				+ " EO.WORKORDER_OBJECT_NO BATCH_CHECK_LOCATION,"
				+ " EO.WORKORDER_OBJECT_LOCATION CHECK_LOCATION_NAME,"
				+ " SU.USERNAME BATCH_IMPLEMENT_USER,"
				+ " SU.USER_ID BATCH_IMPLEMENT_BY,"
                + " SG.GROUP_ID,"
				+ " SG.GROUP_NAME GROUP_NAME" + " FROM"
				+ " AMS_ASSETS_CHECK_BATCH AACB,"
                + " AMS_COST_CENTER_V ACCV,"
				+ " ETS_OBJECT             EO," + " SF_USER                SU,"
				+ " SF_USER                SU2,"
				+ " ETS_FLEX_VALUE_SET     EFVS,"
				+ " ETS_FLEX_VALUES        EFV,"
				+ " SF_GROUP               SG," + " AMS_MIS_DEPT           AMD"
				+ " WHERE" + " AACB.CREATED_BY = SU2.USER_ID"
				+ " AND AACB.BATCH_IMPLEMENT_BY *= SU.USER_ID"
				+ " AND AACB.BATCH_CHECK_LOCATION *= EO.WORKORDER_OBJECT_NO"
				+ " AND AACB.COST_CENTER_CODE *= ACCV.COST_CENTER_CODE"
				+ " AND AACB.BATCH_STATUS = EFV.CODE"
				+ " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
				+ " AND AACB.GROUP_ID = SG.GROUP_ID"
				+ " AND AACB.CHECK_DEPT *= AMD.DEPT_CODE"
				+ " AND EFVS.CODE = ?" + " AND AACB.BATCH_ID = ?";
		sqlArgs.add(AssetsDictConstant.CHKORDER_STATUS);
		sqlArgs.add(dto.getBatchId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：框架自动生成资产盘点批表(EAM) AMS_ASSETS_CHECK_BATCH页面翻页查询SQLModel，请根据实际需要修改。
	 * 
	 * @return SQLModel 返回页面翻页查询SQLModel
	 * @throws SQLModelException
	 *             发生日历异常时转化为该异常抛出
	 */
	public SQLModel getPageQueryModel() throws SQLModelException {
		SQLModel sqlModel = new SQLModel();

		List sqlArgs = new ArrayList();
		AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
		String sqlStr = "SELECT"
				+ " AACB.BATCH_ID,"
				+ " AACB.BATCH_NO,"
				+ " AACB.TASK_DESC,"
				+ " AACB.BATCH_CHECK_LOCATION,"
				+ " AACB.BATCH_START_TIME,"
				+ " AACB.BATCH_IMPLEMENT_DAYS,"
				+ " AACB.CREATED_BY,"
				+ " AACB.CREATION_DATE,"
				+ " AACB.BATCH_STATUS,"
				+ " ACCV.COST_CENTER_CODE,"
				+ " ACCV.COST_CENTER_NAME,"
				+ " EFV.VALUE BATCH_SATUS_NAME,"
				+ " EOCM.COMPANY,"
				+ " (SELECT DISTINCT"
				+ " AMD.DEPT_NAME"
				+ " FROM"
				+ " AMS_MIS_DEPT AMD"
				+ " WHERE"
				+ " AACB.CHECK_DEPT = AMD.DEPT_CODE"
				+ " AND EOCM.COMPANY_CODE = AMD.COMPANY_CODE"
				+ " ) AS DEPT_NAME"
				+ " FROM"
				+ " AMS_ASSETS_CHECK_BATCH AACB,"
				+ " AMS_COST_CENTER_V ACCV,"
				+ " ETS_OU_CITY_MAP        EOCM,"
				+ " ETS_OBJECT             EO,"
				+ " ETS_FLEX_VALUE_SET     EFVS,"
				+ " ETS_FLEX_VALUES        EFV"
				+ " WHERE"
				+ " AACB.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
				+ " AND AACB.BATCH_CHECK_LOCATION *= EO.WORKORDER_OBJECT_NO"
				+ " AND AACB.COST_CENTER_CODE *= ACCV.COST_CENTER_CODE"
				+ " AND AACB.ORGANIZATION_ID *= ACCV.ORGANIZATION_ID"
				+ " AND AACB.BATCH_STATUS = EFV.CODE"
				+ " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
				+ " AND EFVS.CODE = ?"
				+ " AND AACB.BATCH_STATUS = dbo.NVL(?, AACB.BATCH_STATUS)"
				+ " AND AACB.ORGANIZATION_ID = ?"
				+ " AND AACB.CHECK_DEPT = dbo.NVL(?,AACB.CHECK_DEPT)"
				+ " AND AACB.TASK_DESC LIKE dbo.NVL(?,AACB.TASK_DESC)"
				+ " AND (?='' OR AACB.CREATION_DATE =null OR AACB.CREATION_DATE='' OR AACB.CREATION_DATE >= ?)"
				+ " AND (?='' OR AACB.CREATION_DATE =null OR AACB.CREATION_DATE='' OR dateadd(day,-1,AACB.CREATION_DATE) <= ?)"
//				+ " AND dateadd(day,-1,AACB.CREATION_DATE) <= ISNULL(?, AACB.CREATION_DATE)"
				+ " ";
		sqlArgs.add(AssetsDictConstant.CHKORDER_STATUS);
		String srcPage = dto.getSrcPage();
		if (srcPage.equals(AssetsActionConstant.DISTRIBUTE_ACTION)) {
			dto.setBatchStatus(AssetsDictConstant.APPROVED);
		} else if (srcPage.equals(AssetsActionConstant.CANCEL_ACTION)
				|| srcPage.equals("")) {
			dto.setBatchStatus(AssetsDictConstant.SAVE_TEMP);
		}
		sqlArgs.add(dto.getBatchStatus());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlArgs.add(dto.getCheckDept());
		sqlArgs.add(dto.getTaskDesc());
		try {
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getStartDate());
			sqlArgs.add(dto.getEndDate());
			sqlArgs.add(dto.getEndDate());
		} catch (CalendarException e) {
			e.printLog();
			throw new SQLModelException(e);
		}
		sqlStr += " ORDER BY CREATION_DATE DESC";
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);

		return sqlModel;
	}

	public SQLModel getImpLocationModel(String locationCode) {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
		String sqlStr = "SELECT EOCM.COMPANY COMPANY_NAME,"
				+ " EC.COUNTY_NAME," + " EFV.VALUE OBJECT_CATEGORY,"
				+ " EO.WORKORDER_OBJECT_NO CHECK_LOCATION,"
				+ " EO.WORKORDER_OBJECT_CODE OBJECT_CODE,"
				+ " EO.WORKORDER_OBJECT_NAME OBJECT_NAME,"
				+ " EO.WORKORDER_OBJECT_LOCATION OBJECT_LOCATION"
                + " FROM"
				+ " ETS_OBJECT         EO,"
                + " ETS_COUNTY         EC,"
				+ " ETS_FLEX_VALUE_SET EFVS,"
				+ " ETS_FLEX_VALUES    EFV,"
				+ " ETS_OU_CITY_MAP    EOCM"
				+ " WHERE"
				+ " EO.COUNTY_CODE *= EC.COUNTY_CODE"
				+ " AND EO.ORGANIZATION_ID *= EC.ORGANIZATION_ID"
				+ " AND EO.ORGANIZATION_ID = EOCM.ORGANIZATION_ID"
				+ " AND EO.OBJECT_CATEGORY = EFV.CODE"
				+ " AND EFV.FLEX_VALUE_SET_ID = EFVS.FLEX_VALUE_SET_ID"
				+ " AND EFVS.CODE = ?"
                + " AND EO.ORGANIZATION_ID = ?"
				+ " AND EO.WORKORDER_OBJECT_CODE IN ('" + locationCode + "')"
				+ " AND (EO.DISABLE_DATE ='' OR EO.DISABLE_DATE > GETDATE() OR EO.DISABLE_DATE IS NULL)";
		sqlArgs.add(AssetsDictConstant.OBJECT_CATEGORY);
		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：构造下发盘点工单的SQL
	 * 
	 * @return SQLModel
	 */
	public SQLModel getDistributeModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
		String sqlStr = "UPDATE  AMS_ASSETS_CHECK_BATCH   SET"
				+ " BATCH_STATUS = ?,"
				+ " DISTRIBUTE_DATE = GETDATE(),"
				+ " DISTRIBUTE_BY = ?,"
				+ " LAST_UPDATE_DATE = GETDATE(),"
				+ " LAST_UPDATE_BY = ?" + " WHERE" + " BATCH_ID = ?";
		sqlArgs.add(dto.getBatchStatus());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(dto.getBatchId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：撤销单据
	 * 
	 *            String
	 * @return SQLModel
	 */
	public SQLModel getBatchCancelModel() {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "UPDATE" + " AMS_ASSETS_CHECK_BATCH " + " SET"
				+ " BATCH_STATUS = ?,"
				+ " LAST_UPDATE_DATE = GETDATE(),"
				+ " LAST_UPDATE_BY = ?" + " WHERE" + " BATCH_ID = ? ";
		List sqlArgs = new ArrayList();
		sqlArgs.add(AssetsDictConstant.CANCELED);
		sqlArgs.add(userAccount.getUserId());
        AmsAssetsCheckBatchDTO dto = (AmsAssetsCheckBatchDTO) dtoParameter;
		sqlArgs.add(dto.getBatchId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}

	/**
	 * 功能：撤销单据
	 * 
	 * @param batchId
	 *            String
	 * @return SQLModel
	 */
	public SQLModel getHeaderCancelModel(String batchId) {
		SQLModel sqlModel = new SQLModel();
		String sqlStr = "UPDATE"
                + " AMS_ASSETS_CHECK_HEADER"
                + " SET"
				+ " ORDER_STATUS = ?,"
				+ " LAST_UPDATE_DATE = GETDATE(),"
				+ " LAST_UPDATE_BY = ?"
                + " WHERE"
                + " BATCH_ID = ? ";
		List sqlArgs = new ArrayList();
		sqlArgs.add(AssetsDictConstant.CANCELED);
		sqlArgs.add(userAccount.getUserId());
		sqlArgs.add(batchId);
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
