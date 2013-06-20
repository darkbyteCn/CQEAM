package com.sino.ams.newasset.report.model;

import java.util.ArrayList;
import java.util.List;

import com.sino.ams.appbase.model.AMSSQLProducer;
import com.sino.ams.newasset.dto.AmsAssetsChangedVDTO;
import com.sino.ams.system.user.dto.SfUserDTO;
import com.sino.base.db.sql.model.SQLModel;

public class AssetsChangeReportModel extends AMSSQLProducer {

	public AssetsChangeReportModel(SfUserDTO userAccount, AmsAssetsChangedVDTO dtoParameter) {
		super(userAccount, dtoParameter);
	}

	/**
	 * 功能：获取盘点统计报表SQL
	 * @return SQLModel
	 */
	public SQLModel getPageQueryModel() {
		SQLModel sqlModel = new SQLModel();
		List sqlArgs = new ArrayList();
		String sqlStr = "SELECT"
						+ " AACV.ORGANIZATION_ID,"
						+ " AACV.COMPANY,"
						+ " AACV.COMPANY_CODE,"
						+ " AACV.BARCODE,"
						+ " AACV.TAG_NUMBER,"
						+ " AACV.ITEM_NAME,"
						+ " AACV.ASSETS_DESCRIPTION,"
						+ " AACV.ITEM_SPEC,"
						+ " AACV.MODEL_NUMBER,"
						+ " AACV.EMPLOYEE_NUMBER,"
						+ " AACV.ASSIGNED_TO_NUMBER,"
						+ " AACV.USER_NAME,"
						+ " AACV.ASSIGNED_TO_NAME,"
						+ " AACV.WORKORDER_OBJECT_CODE,"
						+ " AACV.ASSETS_LOCATION_CODE,"
						+ " AACV.WORKORDER_OBJECT_LOCATION,"
						+ " AACV.ASSETS_LOCATION,"
						+ " AACV.AMS_COST_CODE,"
						+ " AACV.MIS_COST_CODE,"
						+ " AACV.AMS_COST_NAME,"
						+ " AACV.MIS_COST_NAME,"
						+ " AACV.CHANGED_CONTENT"
						+ " FROM"
						+ " AMS_ASSETS_CHANGED_V AACV"
						+ " WHERE"
						+ " AACV.COST_CENTER_CHANGED = ISNULL(?, AACV.COST_CENTER_CHANGED)"
						+ " AND AACV.TAG_NUMBER_CHANGED = ISNULL(?, AACV.TAG_NUMBER_CHANGED)"
						+ " AND AACV.ITEM_NAME_CHANGED = ISNULL(?, AACV.ITEM_NAME_CHANGED)"
						+ " AND AACV.ITEM_SPEC_CHANGED = ISNULL(?, AACV.ITEM_SPEC_CHANGED)"
						+ " AND AACV.USER_CHANGED = ISNULL(?, AACV.USER_CHANGED)"
						+ " AND AACV.LOCATION_CHANGED = ISNULL(?, AACV.LOCATION_CHANGED)"
						+ " AND AACV.ORGANIZATION_ID = ?";
		AmsAssetsChangedVDTO dto = (AmsAssetsChangedVDTO) dtoParameter;
		sqlArgs.add(dto.getCostCenterChanged());
		sqlArgs.add(dto.getTagNumberChanged());
		sqlArgs.add(dto.getItemNameChanged());
		sqlArgs.add(dto.getItemSpecChanged());
		sqlArgs.add(dto.getUserChanged());
		sqlArgs.add(dto.getLocationChanged());
		sqlArgs.add(userAccount.getOrganizationId());
		sqlModel.setSqlStr(sqlStr);
		sqlModel.setArgs(sqlArgs);
		return sqlModel;
	}
}
